# -*- coding: UTF-8 -*-
import os
import sys
import subprocess
import psutil
import utils


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
		if(line.name == 'Python'):
			if(cmd[1].find('test.py') > -1):
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

## 对关注进程合法性检验
def is_illegal(target_list):
	num = 9
	t_cmds = [1] * num;
	t_cmds[0] = ['adb','','-s','ignore' ,'shell']
	t_cmds[1] = ['adb','','-s', 'ignore','logcat','-v', 'time']
	#t_cmds[2] = ['adb', '','-P', 'ignore','-s','ignore', 'logcat','-v','threadtime']
	t_cmds[2] = ['player','','--vm-name','ignore']
	t_cmds[3] = ['Python','','ignore','ignore']
	t_cmds[4] = ['sh','','-c','ignore']
	t_cmds[5] = ['node','','ignore','-p','ignore','-bp','ignore','-U','ignore']
	t_cmds[6] = ['VBoxHeadless','','--comment','ignore','startvm','ignore','ignore','ignore']
	t_cmds[7] = ['adb','','-s','ignore','logcat']
	t_cmds[8] = ['adb','','-P','ignore','fork-server','server','--reply-fd','ignore']
	is_illegal = True
	for pro in target_list:
		cmd = pro.cmdline
		for t_cmd in t_cmds:
			if(t_cmd[0] == pro.name):
				#t_cmd[1] = "1"
				is_match = True
				if(len(cmd) != len(t_cmd) -1):##一定不匹配
					continue
				for j in range(2, len(t_cmd) -1):
					if(t_cmd[j] == 'ignore'):
						continue
					if(t_cmd[j] != cmd[j - 1]):# -1 是因为在第二位添加了一个标识位.如果不相等，那么说明，不匹配
						is_match == False
				if(is_match == True):##如果匹配则将相应进程标志为 1，说明该进程存在，（但是不一定合法，还需合法性验证）
					t_cmd[1] = "1"
		if(is_illegal_pro(pro) == False):
			print "不合法进程："
			print_info(pro)
			is_illegal = False
	
	for t_cmd in t_cmds:
		if(t_cmd[1] == ''):
			str = ""
			for t in t_cmd:
				str+=("  "+ t)
			print "没有该进程:" + str
			is_illegal = False
	return is_illegal

def is_illegal_pro(pro):
	cmd = pro.cmdline
	if(pro.name == 'adb' and cmd[1] == '-s' and cmd[3] == 'logcat' and cmd[2] == ''):
		return False

	return True



def start(num):
	str1 = "python /Users/yuhaiqiang/Documents/Android_Monitor/android_mobile_creative_monitor/scripts/test.py " + num + " > /Users/yuhaiqiang/Desktop/log/test.log"
	subprocess.Popen(str1, shell = True)
	
def getTargetList():
	targets = list()
	targets.append('adb')
	targets.append('player')
	targets.append('Python')
	targets.append('sh')
	targets.append('node')
	targets.append('VBoxHeadless')
	target_list = list()
	for target in targets:
		pro_list = is_exist_in_process_list(target)##返回该名字的进程列表
		if(len(pro_list) != 0):#如果为0 ，则没有匹配进程
			print target + '进程共 '+str(len(pro_list))+' 个 匹配如下：'
			for pro in pro_list:
				if(get_need(pro) == True):
					print_info(pro)
					target_list.append(pro)

		else:
			print target + ':没有找到相关进程'
	return target_list

def guard(isKill):
	target_list = getTargetList()
	succ = is_illegal(target_list)
	if(succ == False):
		pass
		if(isKill == "1"):
			killAll(target_list)
	return succ
	#print str(succ)

def is_other_guard(pro):
	pid = os.getpid()
	if(pid == pro.pid):
		return False
	else:
		return True

def killGuard():
	pro_list = is_exist_in_process_list('Python')
	print "杀死其他监控进程"
	for pro in pro_list:##杀死其他监控进程
		if(is_other_guard(pro) == True):
			kill(pro.pid)
			print_info(pro)
	killAll(getTargetList())#杀死模拟器相关进程

def main():
	guard("2")
	utils.sleep(5)
	killGuard()
	utils.sleep(5)
	startup()

def startup():
	startapp = sys.argv[1]
	if(guard("1") == False):#如果当前不合法，那么重启
		start(startapp)
	while True:
		utils.sleep(90)
		succ = guard("2")
		if(succ == False):##如果状态不合法，那么进入下一阶段
			utils.sleep(60*1)#睡一会，继续判断
			succ = guard("1")#r如果不合法，根据输入杀死进程
			utils.sleep(10)
			if(succ == False):#如果不合法，那么重启，进入下层循环
				start(startapp)
				continue
			else:
				utils.sleep(60*5)
				continue#合法，进入下层循环
		else:#进入下一层循环，继续判断
			utils.sleep(60*5)
			continue
				



if __name__ == '__main__':
	main()