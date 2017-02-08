# -*- coding: UTF-8 -*-
import Queue
import time
import os
import MySQLdb
import paramiko
import mysql.connector
import sshtunnel
queue = Queue.Queue()

ACCOUNT_INFO = {
     1:("Racing Rivals"),
     2:("Clash of Crime San Andreas PRO"),
     3:("Bike Racing Mania"),
     4:("Real Drift Car Racing"),
     5:("Draw Rider +"),
     6:("Riptide GP"),
     7:("Draftmaster Pro"),
     8:("Police Car Driving"),
     9:("City Driving 3D"),
     10:("Slingshot Racing"),

}

accounts = list()

is_start = False
index = 0
num = 0 ###标志已经读取几个账号

def hasAccount():
    global num
    print "length:" + str(len(accounts))
    if(num > len(accounts)):
        return False
    else:
        return True


def ssh(ip,port, user, passwd):
    try:
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(ip, port, user, passwd,timeout=3)
        return ssh
    except Exception,e:
     print str(e)
     print("ssh_cmd err.")




def getAccount():
    global num
    global index
    global is_start
    num = num + 1
    if(hasAccount() == False):
        return  False
    if(is_start == False):###如果没启动，通过时间戳计算起始随机值，读取账号，避免每次启动总是从一个账号开始
        index = int(time.time()*100) % len(accounts)
        is_start = True##标志启动过了
        return accounts[index]
    else:
        index = index + 1   ##索引加一，但是可能会超过长度，所以获取账号时需要对其取余
        return accounts[index%len(accounts)]
for key in ACCOUNT_INFO:
     queue.put(ACCOUNT_INFO[key])

while not queue.empty():
     accounts.append(queue.get())

def connect():
     with sshtunnel.SSHTunnelForwarder(
             ('47.88.100.4', 1422),
             ssh_username= 'work',
             ssh_password= 'ZxywlwxH,#3721',
             remote_bind_address=("socialpeta001.mysql.rds.aliyuncs.com", 3306)#,
             #local_bind_address=("localhost", 3306)
     ) as tunnel:
         connection = mysql.connector.connect(
             user='socialpeta',
             passwd= 'zfQzygWcs321',
             host="127.0.0.1",
             database= "sp_profile",
             port= 3306)

#connect()

