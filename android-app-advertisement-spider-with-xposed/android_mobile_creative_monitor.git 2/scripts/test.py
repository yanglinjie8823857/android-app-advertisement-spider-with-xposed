# -*- coding: UTF-8 -*-
import time
import os
import threading
import subprocess
import Queue

import appium_python
import utils
import data
import sys
import baidutiebascript
import youtube
import path
import qq
#import youtube_search
APP_MAP = {
    1 : appium_python.operate,
    2 : baidutiebascript.operate,
    3 : youtube.operate,
    4 : appium_python.operate,
    5 : baidutiebascript.operate,
    6 : youtube.operate,
    }
start_machine_list = list()
start_appium_list = list()
start_adb_list = list()
DEVICES = ('QZone' ,'BaiDuTieBa' ,'youtube','QZone_2','BaiDuTieBa_2','youtube_2',)
DEVICE_LIST = (DEVICES[int(sys.argv[1])-1],)


def start_machine(device_name):
    print "start player" + device_name
    p = subprocess.Popen(path.getPath("player") + " --vm-name '" + device_name + "'", shell = True)
    start_machine_list.append(p.pid)
    print p.pid

def start_appium_server(port, bootstrap, udid):
    # 无阻塞调用shell命令
    p = subprocess.Popen(path.getPath("main.js") + " -p '"+ port +"' -bp '"+ bootstrap +"' -U '"+ udid +"'"
        , shell = True)
    start_appium_list.append(p.pid)

def start_adb_logcat(udid, fileName):
    print 'adblogcat {} {}'.format(udid, fileName)
    p = subprocess.Popen("adb -s '"+ udid +"' logcat|grep Xposed>"+ fileName +"", shell = True)
    start_adb_list.append(p.pid)
def getDevice():
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
       print device[0]
       return device[0]

def main():
    currentTime = time.strftime('%Y_%m_%d_%H_%M',time.localtime(time.time()))
    PORT = 4723+int(sys.argv[1])-1
    BOOTSTRAP = 2251+int(sys.argv[1])-1
    #UDID = 101 + int(sys.argv[1]) - 1
    for key in data.ACCOUNT_INFO:
        data.queue.put(data.ACCOUNT_INFO[key])
    start_thread_list = list()
    appium_thread_list = list()
    operate_thread_list = list()
    adblogcat_thread_list = list()
    machineUdid_list = list()
    machinePort_list = list()
    print 'before'
    #id = os.getpid()
    #cmd = "kill -9 %d" % int(pid)
    #os.system(cmd)
    #targetApk = input("choose the app you want to operate: 1. qzone  2. baidu_tieba \n")
    #operateType = APP_MAP[targetApk]
    lock = path.getPath('root') + 'boot.lock'
    #设置启动文件锁
    while(os.path.isfile(lock) == True):
        print "其他模拟器正在启动，10s后启动..."
        utils.sleep(10)
    fp = open(lock,'w')
    fp.close()

    # 启动模拟器
    for device_name in DEVICE_LIST:
        start_thread_list.append(threading.Thread(target = start_machine, args = (device_name,)))

    for thread in start_thread_list:
        thread.start()
    print "start virtual"
    utils.sleep(40)

    device_id = ''
    #popen = subprocess.Popen("adb devices",shell = True)
    #str = popen.stdout
    # 启动appium_server
    for device_name in DEVICE_LIST:
        #machineUdid = '192.168.57.{}:5555'.format(UDID)
        machineUdid = getDevice()
        device_id = machineUdid
        machineUdid_list.append(machineUdid)
        machinePort = '{}'.format(PORT)
        machinePort_list.append(machinePort)
        machineBootStrap = '{}'.format(BOOTSTRAP)
        appium_thread_list.append(threading.Thread(target = start_appium_server,
            args = (machinePort,machineBootStrap,machineUdid)))
        PORT = PORT + 1
        BOOTSTRAP = BOOTSTRAP + 1
        #UDID = UDID + 1
    #删除文件锁，同时将ip存入文件中
    try:
        res = os.remove(lock)
    except:
        pass
    device_name = DEVICE_LIST[0]
    fp = open(path.getPath("root") + device_name+'.info','w')
    fp.write(device_id)
    fp.close()

    print('start adblogcat')
    for udid in machineUdid_list:
        fileName = path.getPath("log") + device_name+'_{}_{}'.format(udid, currentTime)
        adblogcat_thread_list.append(threading.Thread(target = start_adb_logcat,
            args = (udid,fileName)))
    for thread in adblogcat_thread_list:
        thread.start()
    print('start appium')
    for thread in appium_thread_list:
        thread.start()

    utils.sleep(40)###appium启动慢

    i = 0
    for id in DEVICE_LIST:
        operate_thread_list.append(threading.Thread(target=APP_MAP.get(int(sys.argv[1])),
            args=(machinePort_list[i],machineUdid_list[i])))
        i = i + 1




    utils.sleep(10)

    for thread in operate_thread_list:
        thread.start()

    for thread in operate_thread_list:
        thread.join()
    utils.sleep(10)
    for start in start_machine_list:
        cmd = "kill -9 %d" % int(start)
        #print "执行"
        os.system(cmd)

    for start in start_appium_list:
        cmd = "kill -9 %d" % int(start)
        #print "执行"
        os.system(cmd)
    for start in start_adb_list:
        cmd = "kill -9 %d" % int(start)
        #print "执行"
        os.system(cmd)
    pid = os.getpid()
    cmd = "kill -9 %d" % int(pid)
    os.system(cmd)

if __name__ == "__main__":
    main()


