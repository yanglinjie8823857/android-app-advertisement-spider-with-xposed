# -*- coding: UTF-8 -*-

import time
import os
import subprocess

from appium import webdriver

import utils
import data
import qqaccounts


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

def quit(udid,driver):
		utils.sleep(1)
		subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 70 100", shell = True)
		utils.sleep(3)
		driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '设置')]").click()

		utils.sleep(3)
		try:
			driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '账号管理')]").click()
		except Exception,e:
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 100 200", shell = True)
		utils.sleep(4)
		subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 800 10 200", shell = True)
		utils.sleep(1)
		subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 800 10 200", shell = True)
		utils.sleep(1)
		subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 800 10 200", shell = True)
		utils.sleep(1)
		subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 800 10 200", shell = True)
		utils.sleep(1)
		subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 800 10 200", shell = True)
		utils.sleep(2)
		subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 800 10 200", shell = True)
		utils.sleep(2)
		try:
			driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '退出当前账号')]").click()
		except:
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 500 1100", shell = True)
		print "fahwei9ufhuwehfiuehwofheiouhfuehiu"
		utils.sleep(2)
		try:
			driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '确认退出')]").click()
		except Exception,e:
			print "whatwhatwhatwhatwhatwhat"
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 500 780", shell = True)
		utils.sleep(10)


def operate(port, udid):
	for key in qqaccounts.ACCOUNT_INFO:
		qqaccounts.queue.put(qqaccounts.ACCOUNT_INFO[key])
	while not qqaccounts.queue.empty():
		accounts.append(qqaccounts.queue.get())
	desired_caps = {}
	desired_caps['platformVersion'] = '4.2'
	desired_caps['deviceName'] = 'useless'
	desired_caps['platformName'] = 'Android'
	desired_caps['appPackage'] = 'com.tencent.mobileqq'
	desired_caps['appActivity'] = 'com.tencent.mobileqq.activity.SplashActivity'
	desired_caps['newCommandTimeout'] = 2000
	driver = webdriver.Remote("http://localhost:"+port+"/wd/hub", desired_caps)
	utils.sleep(5)

	while hasAccount():
		account = getAccount()
		is_next = True
		while is_next:
			try:
				driver.find_element_by_xpath("//android.widget.RadioButton[contains(@text,'电话')]").click()
				quit(udid, driver)
			except Exception ,e:
				pass
			if(is_next):
				try:
					print "本次账号："+str(account)
					pasd = account[1]
					driver.find_element_by_xpath("//android.widget.EditText[contains(@index,0)]").send_keys(account[0])#帐号
					utils.sleep(3)
					driver.find_element_by_xpath("//android.widget.EditText[contains(@index,2)]").clear()#密
					utils.sleep(5)
					driver.find_element_by_xpath("//android.widget.EditText[contains(@index,2)]").send_keys(pasd)#密
					utils.sleep(5)
					driver.find_element_by_xpath("//android.widget.Button[contains(@text,'登 录')]").click()
					utils.sleep(8)
					try:
						driver.find_element_by_xpath("//android.widget.Button[contains(@text,'登 录')]").click()
						utils.sleep(2)
						driver.quit()
						utils.sleep(3)
						driver = webdriver.Remote("http://localhost:"+port+"/wd/hub", desired_caps)
						utils.sleep(3)
						quit(udid,driver)
						driver.find_element_by_xpath("//android.widget.EditText[contains(@index,0)]").send_keys(account[0])#帐号
						utils.sleep(3)
						driver.find_element_by_xpath("//android.widget.EditText[contains(@index,2)]").send_keys(pasd)
						utils.sleep(2)
						driver.find_element_by_xpath("//android.widget.Button[contains(@text,'登 录')]").click()
					except:
						pass
					
					utils.sleep(3)
					is_next = False
				except Exception, e:
					print str(e)
		try:
			subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
			utils.sleep(5)
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 700  80", shell = True)
			utils.sleep(4)
			driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '加好友')]").click()
			utils.sleep(3)
			try:
				driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '按条件查找陌生人')]").click()
			except :
				subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 200  740", shell = True)
			utils.sleep(3)
			driver.find_element_by_xpath("//android.widget.Button[contains(@text, '查找')]").click()
		except:
			continue
		i = 10
		while i > 0:
			utils.sleep(4)
			print "0000000000000000"
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 400 300", shell = True)
			utils.sleep(7)
			print "111111111111111"
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 600 1130", shell = True)#加好友
			print "2222222"
			utils.sleep(5)
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 700 100", shell = True)#下一步
			utils.sleep(5)
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 700 100", shell = True)#发送
			#driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '发送')]").click()
			try:
				driver.find_element_by_xpath("//android.widget.Button[contains(@text,'确 定')]").location
				subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
			except:
				pass
				#subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)

			utils.sleep(4)
			subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
			utils.sleep(4)
			subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 800 10 500", shell = True)
			i = i - 1
		subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
		utils.sleep(2)
		subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
		utils.sleep(2)
		subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
		utils.sleep(3)
		quit(udid, driver)
	driver.quit()
