# -*- coding: UTF-8 -*-

import time
import os
import subprocess

from appium import webdriver

import utils
import data
import appname

def operate(port, udid):
    PATH = lambda p: os.path.abspath(
        os.path.join(os.path.dirname(__file__), p)
    )
    
    print '{} {}'.format(port , udid)
    

    desired_caps = {}
    desired_caps['platformName'] = 'Android'
    desired_caps['platformVersion'] = '6.0'
    desired_caps['deviceName'] = 'useless'
  #  desired_caps['app'] = PATH('/Users/xujin/Downloads/QQkongjian_95.apk')
    #desired_caps['udid'] = udid
    desired_caps['appPackage'] = 'com.google.android.youtube'
    desired_caps['newCommandTimeout'] = 2000
    desired_caps['appActivity'] = 'com.google.android.youtube.HomeActivity'
    driver = webdriver.Remote("http://localhost:"+port+"/wd/hub", desired_caps)
    utils.sleep(15)
    driver.find_element_by_xpath("//apg[contains(@index,1)]/android.widget.TextView[contains(@index, 0)]").click()
    utils.sleep(2)
    while appname.hasAccount():
    	account = appname.getAccount()
    	try:
    		driver.find_element_by_xpath("//android.widget.EditText[contains(@index, 0)]").send_keys(account)
    	except:
    		utils.sleep(10)
    		continue
    	utils.sleep(2)
    	subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 66", shell = True)#
    	utils.sleep(10)
    driver.quit()
