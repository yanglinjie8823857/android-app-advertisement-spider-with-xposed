# -*- coding: UTF-8 -*-
import time
import os
import threading
import subprocess

from appium import webdriver

import utils

PATH = lambda p: os.path.abspath(
os.path.join(os.path.dirname(__file__), p)
)

def clickTuiJian(udid,driver):
        try:
            subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)#返回，如果弹出其他消息框，此时可以返回操作关闭消息框
	    utils.sleep(1)
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 100 1100", shell = True)
	    utils.sleep(1)
            driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'首页')]").click()
            utils.sleep(1)
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 400 10 1000", shell = True)
            utils.sleep(1)
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 48 200 800 200", shell = True)# label swipe
            utils.sleep(2)
        except:
            pass
        try:
            driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'" + '推荐' + "')]").click()
        except Exception ,e:
            print str(e)
        utils.sleep(1)
        try:
            driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'首页')]").click()
            driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'首页')]").click()
        except:
            pass
        try:
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 48 200 800 200", shell = True)# label swipe
            utils.sleep(2)
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 48 200 800 200", shell = True)# label swipe
            utils.sleep(2)
            driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'" + '推荐' + "')]").click()
        except Exception ,e:
            print str(e)
            driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'首页')]").click()
            utils.sleep(1)
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 48 200 800 200", shell = True)# label swipe
            utils.sleep(1)
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 48 200 800 200", shell = True)# label swipe
            utils.sleep(2)
            driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'" + '推荐' + "')]").click()
        utils.sleep(2)

def start(port, desired_caps):
    j = 3
    while j >0 :
        try:
            driver = webdriver.Remote("http://localhost:"+port+"/wd/hub", desired_caps)
            break
        except Exception,e:
            print str(e)
            j = j - 1
            utils.sleep(5)
            print " 打开失败，再次尝试"
    return driver


def operate(port,udid):
        print "start BaiDuTieBa:"+str(port)+'--'+str(udid)
        desired_caps = {}
        desired_caps['platformName'] = 'Android'
        desired_caps['platformVersion'] = '4.2'
        desired_caps['deviceName'] = 'useless'
        desired_caps['appPackage'] = 'com.baidu.tieba'
        desired_caps['newCommandTimeout'] = 2000
        desired_caps['appActivity'] = 'com.baidu.tieba.tblauncher.MainTabActivity' 

        utils.sleep(8)

        n = 200000000
        
        while(n>0):
            try:
                driver = start(port, desired_caps)
                utils.sleep(5)
                clickTuiJian(udid, driver)
                #subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 1000 10 400", shell = True)
                #subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 400 10 1000", shell = True)
                #utils.sleep(1)
                i = 14

                subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 1000 10 100", shell = True)
                utils.sleep(3)
                while(i>0):
               
                    #driver.swipe(10,1000,10,100,2000)
                    try:
                        driver.find_element_by_xpath("//android.widget.TextView[contains(@text, '百度贴吧')]").location
                        driver.quit()
                        utils.sleep(1)
                        driver = start(port, desired_caps)
                        utils.sleep(10)
                    except Exception,e:
                        pass
                    subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 10 400", shell = True)
                    # try 主要解决，点击操作可能无法进入贴吧，通过判断是否在首页解决，如果在，则刷新，同时再次点击
                    try:
                        utils.sleep(1)
                    	driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'首页')]").click()##如果点击成功，那么说明，没有进入帖子，再滑动一下，点击
                        utils.sleep(1)
                        subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 1000 10 100", shell = True)
                    	utils.sleep(2)
                        subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 10 400", shell = True)
                    except Exception:
                    	print ''
                    finally :
                    	print ''

                    utils.sleep(2)
                    j = 15
                    while(j>0):
                            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 1000 10 100", shell = True)
                            utils.sleep(1)
                            j = j - 1
                    subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
                    utils.sleep(7)
                    subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)#返回两次，为了解决登陆后弹出框的问题
                    utils.sleep(1)
                    subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 10 1000 10 100", shell = True)
                    utils.sleep(2)
                    i = i - 1
                driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'首页')]").click()
                driver.quit()
                utils.sleep(3)
            except Exception,e:
                print str(e)
		try:
                	driver.quit()
		except Exception,e:
			print str(e)
            n = n - 1
            if(n%200 == 0):
                utils.sleep(20*60)
            utils.sleep(1)
