# -*- coding: UTF-8 -*-
import os
import sys
import subprocess
import psutil
import utils
import test
import path

DEVICES = {"1":'QZone' ,"2":'BaiDuTieBa' ,"3":'youtube',"4":'QZone_2',"5":'BaiDuTieBa_2',"6":'youtube_2'}

##打印进程信息
def print_info(line):
	try:
		print str(line.pid) + '  : ' +  line.name + str(line.cmdline)
	except psutil.AccessDenied:
		print str(line.pid)+ '  :  ' + line.name
		print '没有权限访问cmdline'
		return
	except psutil.NoSuchProcess:
		print '没有该进程'
		return


####判断该进程是否在进程列表中
def is_exist_in_process_list(name):
	process_list = psutil.get_process_list()
	res_list = list()
	for line in process_list:
		try:
			if(line.name == name):
				res_list.append(line)
		except psutil.AccessDenied:
				pass
		except psutil.NoSuchProcess:
				pass
	return res_list

###判断该进程是否是我们监视的目标进程,过滤掉不关注的
def get_need(line):
	try:
		cmd = line.cmdline
		if(line.name == 'adb'):
			if(cmd[1] == '-s' or cmd[1] =='-P'):
				if(len(cmd) >= 4):
					pass
					#if(cmd[3] == 'fork-server'):
						#return False
				return True
		if(line.name == path.getPath('pythonName')):
			if(len(cmd) >= 1 and cmd[1].find('test.py') > -1):
				return True
		if(line.name == 'player'):
			return True
		if(line.name == 'node'):
			return True
		if(line.name == 'sh'):
			return True
		if(line.name == 'VBoxHeadless'):
			return True
		return False
	except psutil.AccessDenied:
		return
	except psutil.NoSuchProcess:
		return

def kill(pid):
    cmd = "kill -9 %d" % int(pid)
    os.system(cmd)

def killAll(target_list):
	print '杀死全部相关进程....'
	for pro in target_list:
		print_info(pro)

	for target in target_list:
		kill(target.pid)
def killDevice(config):
	target_list = getTargetList(config)
	res = is_illegal(target_list,config)
	p = psutil.Process(config['pid'])
	print "当前应用"
	print_info(p)
	parent = p.parent
	parent = parent.parent
	print "当前应用的监控进程:"
	print_info(parent)
	res['matchlist'].append(parent)
	killAll(res['matchlist'])

def isChildProcess(pro, pid):
	i = 10
	if(pro.pid == pid):
		return True
	while i > 0:
		try:
			if(pro.parent == None):
				i = 0
			if(pro.parent.pid == pid):
				return True
			pro = pro.parent
		except:
			pass
		i = i - 1
	return False
## 对关注进程合法性检验
def is_illegal(target_list, config):
	pid = config['pid']
	ip = config['ip']
	playerName = config['playerName']
	argv1 = sys.argv[1]
	argv2 = sys.argv[2]
	num = 8
	t_cmds = [1] * num;
	ignore = "ignore"
	t_cmds[0] = ['adb','','-s',ignore ,'shell']
	t_cmds[1] = ['adb','','-s', ignore,'logcat','-v', 'time']
	#t_cmds[2] = ['adb', '','-P', 'ignore','-s','ignore', 'logcat','-v','threadtime']
	t_cmds[2] = ['player','','--vm-name', ignore]
	t_cmds[3] = [path.getPath('pythonName') ,'','ignore:', ignore, ignore]
	#t_cmds[4] = ['sh','','-c', ignore]##近似
	t_cmds[7] = ['node','','ignore:','-p','ignore:','-bp','ignore:','-U',ignore]
	t_cmds[4] = ['VBoxHeadless','','--comment', playerName,'startvm','ignore:','ignore:','ignore:']
	t_cmds[5] = ['adb','','-s',ignore ,'logcat']
	t_cmds[6] = [path.getPath('pythonName') ,'','test.py','ignore','ignore']
	#t_cmds[8] = ['adb','','-P','ignore:','fork-server','server','--reply-fd','ignore:']
	is_illegal = True
	matchPros = list()
	print "子进程如下"
	for pro in target_list:
		print_info(pro)
	print "子进程如上"
	for pro in target_list:
		#if(isChildProcess(pro, pid) == False and pro.name != "VBoxHeadless"):
		#	continue
		try:
			cmd = pro.cmdline
			for t_cmd in t_cmds:
				if(t_cmd[0] == pro.name):
					#t_cmd[1] = "1"
					is_match = True
					if(t_cmd[1] == "1"):##如果找到那么就不找了
						continue
					if(len(cmd) != len(t_cmd) -1):##一定不匹配
						continue
					for j in range(2, len(t_cmd) -1):
						if(t_cmd[j].find(ignore) > -1):
							continue
						if(cmd[j - 1].find(t_cmd[j]) == -1):# -1 是因为在第二位添加了一个标识位.如果不相等，那么说明，不匹配
							is_match = False
						if(t_cmd[0] == "VBoxHeadless" and j == 3 and len(cmd[ j - 1]) != len(t_cmd[j])):##如果VBox
							is_match = False
					if(is_match == True):##如果匹配则将相应进程标志为 1，说明该进程存在，（但是不一定合法，还需合法性验证）
						t_cmd[1] = "1"
						matchPros.append(pro) ##加入匹配列表
						for j in range(2, len(t_cmd) -1):
							t_cmd[j] = t_cmd[j][0:7]
							t_cmd[j] +=  cmd[j -1]

			if(is_illegal_pro(pro) == False):
				print "不合法进程："
				print_info(pro)
				is_illegal = False
		except:
			continue

	res = {}
	for t_cmd in t_cmds:
		if(t_cmd[1] == ''):
			str = ""
			for t in t_cmd:
				str+=("  "+ t)
			print "没有该进程:" + str
			is_illegal = False
	res['status'] = is_illegal
	res['matchlist'] = matchPros
	for pro in matchPros:
		print_info(pro)
	return res

def is_illegal_pro(pro):
	cmd = pro.cmdline
	if(pro.name == 'adb' and cmd[1] == '-s' and cmd[3] == 'logcat' and cmd[2] == ''):
		return False
	return True

def getDeviceId():
       p = os.popen("adb devices")
       #print popen.stdout
       #print str
       txt = p.read()  # get output
       p.close()
       strlist = txt.split(' ')
       #print len(strlist)
       #print strlist
       device = strlist[len(strlist)-1]  #last one contains ip
       device = device.split('\n')  #remove the 'advices',you can print to see
       #print device
       device = device[1].split('\t')   # split the ip and 'advices'
       return device[0]


def start(num):
	try:
		if(sys.argv[2] != ""):
			num = num + " " +sys.argv[2]
	except:
		pass
	str1 = "python "+path.getPath('root')+"scripts/test.py " + num #+ " > /Users/yuhaiqiang/Desktop/log/test.log"
	p = subprocess.Popen(str1, shell = True)
	playerName = DEVICES[sys.argv[1]]
	utils.sleep(50)

	ip = getDeviceId()
	config = {}
	config['pid'] = p.pid
	config['ip'] = ip
	config['playerName'] = playerName
	return config

def getTargetList(config):
	targets = list()
	targets.append('adb')
	targets.append('player')
	targets.append(path.getPath('pythonName'))
	targets.append('sh')
	targets.append('node')
	targets.append('VBoxHeadless')
	print config['pid']
	target_list = list()
	for target in targets:
		pro_list = is_exist_in_process_list(target)##返回该名字的进程列表
		if(len(pro_list) != 0):#如果为0 ，则没有匹配进程
			print target + '进程共 '+str(len(pro_list))+' 个 匹配如下：'
			for pro in pro_list:
				if(isChildProcess(pro, config['pid']) == True or pro.name == "VBoxHeadless"):
					if(get_need(pro) == True):
						print_info(pro)
						target_list.append(pro)

		else:
			print target + ':没有找到相关进程'
	return target_list

def guard(isKill, config):
	target_list = getTargetList(config)
	succ = is_illegal(target_list,config)
	if(succ['status'] != True):
		pass
		if(isKill == "1"):
			killAll(succ['matchlist'])
	return succ['status']
	#print str(succ)

def is_other_guard(pro):
	pid = os.getpid()
	if(pid == pro.pid):
		return False
	else:
		return True

def killGuard(config):
	pro_list = is_exist_in_process_list(path.getPath('pythonName'))
	print "杀死其他监控进程"
	for pro in pro_list:##杀死其他监控进程
		if(is_other_guard(pro) == True):
			kill(pro.pid)
			print_info(pro)
	killAll(getTargetList(config))#杀死模拟器相关进程
def getDevice(num):
    pythonName = path.getPath('pythonName')
    if(pythonName == False):
        pythonName = 'Python'
    devicePros = is_exist_in_process_list(pythonName)
    currPId = os.getpid()
    for pro in devicePros:
        print_info(pro)
        try:
            cmd = pro.cmdline
            ####杀死启动本模拟器的其他进程组
            if(cmd[1].find("test.py") > -1 and cmd[2] == num and isChildProcess(pro,currPId) == False):
                print_info(pro)
                return pro
        except psutil.AccessDenied:
            pass
        except psutil.NoSuchProcess:
            pass
    return False
def getConfigByNum(num):
	device_name = DEVICES[num]
	config  = {}
	device = getDevice(num)
	if(device != False):
		config['pid'] = device.pid
	else:
		return False##不存在该应用
	config['playerName'] = DEVICES[num]
	pros = is_exist_in_process_list("sh")
	for pro in pros:
		try:
			cmd = pro.cmdline
			if(len(cmd) >= 2 and cmd[2].find(device_name) > -1):
				index = cmd[2].find(device_name) + 1 + len(device_name)
				ip = cmd[2][index: index + 19]
				config['ip'] = ip
		except psutil.AccessDenied:
			pass
		except psutil.NoSuchProcess:
			pass
	if(config.has_key('ip') != True):
		config['ip'] = 'ignore:'
	print device_name + "配置 :" + str(config)
	return config

def is_boot_detect_alive():
	pros = is_exist_in_process_list("python")
	if(len(pros) == 0):
		pros = is_exist_in_process_list("Python")
	boots = list()
	for pro in pros:
		try:
			cmd = pro.cmdline
			if(cmd[1].find('boot_detect') > -1):
				boots.append(pro)
		except Exception,e:
			print str(e)
	return boots

def killBootDetects():
	pros = is_boot_detect_alive()
	for pro in pros:
		kill(pro.pid)
		print "杀死一个启动锁监控进程"
def restartBootDetect():
	killBootDetects()
	cmd = "python "+path.getPath('root')+"scripts/boot_detect.py"
	subprocess.Popen(cmd, shell = True)
	print "重启 启动锁 监控进程"
def main():
	#guard("2")
	startapp = sys.argv[1]
	restartBootDetect()
	if(getDevice(startapp) != False):
		print "该应用已经启动，现在重启"
	else:
		print "启动应用..." + DEVICES[sys.argv[1]]
	while True:
		#try:
			startup()
		#except Exception,e:
		#	print str(e)
		#	utils.sleep(5)
		#	continue

#### 目前主要问题，在于如果adb,sh, 等进程如果均失去正常状态，那么如何区分这个进程属于自己，
####如果一个应用处于启动阶段，很多进程状态处于无效状态
####
###
###
def startup():
	startapp = sys.argv[1]
	if(startapp=="0"):
		return
	config = getConfigByNum(startapp)
	if(config != False):
		killDevice(config)
	config = start(startapp)
	while True:
		utils.sleep(90)
		print "第一轮判断"
		succ = guard("2", config)##"2"是假杀死，只是验证，不真杀
		if(succ != True):##如果状态不合法，那么进入下一阶段
			utils.sleep(90*1)#睡一会，继续判断
			print "第二轮判断"
			succ = guard("1", config)#r如果不合法，根据输入杀死进程，真杀
			if(succ != True):#如果不合法，那么重启，进入下层循环
				config = start(startapp)
				continue
			else:
				#utils.sleep(60*3)
				continue#合法，进入下层循环
		else:#进入下一层循环，继续判断
			#utils.sleep(60*3)
			continue

if __name__ == '__main__':
    main()
