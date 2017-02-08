# -*- coding: UTF-8 -*-

import time
import os
import subprocess

from appium import webdriver

import utils
import data
import sys

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
        #index = 1
        is_start = True##标志启动过了
        return accounts[index]
    else:
        index = index + 1   ##索引加一，但是可能会超过长度，所以获取账号时需要对其取余
        return accounts[index%len(accounts)]

def skip(driver,udid):
    utils.sleep(20)
    driver.find_element_by_xpath("//android.widget.Button[contains(@text,'验证')]").click()
    utils.sleep(3)
    subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
    utils.sleep(2)

def operate(port, udid):
    print('operate')
    PATH = lambda p: os.path.abspath(
            os.path.join(os.path.dirname(__file__), p)
            )
    print '{} {}'.format(port , udid)

    desired_caps = {}
    desired_caps['platformName'] = 'Android'
    desired_caps['platformVersion'] = '4.2'
    desired_caps['deviceName'] = 'useless'
  #  desired_caps['app'] = PATH('/Users/xujin/Downloads/QQkongjian_95.apk')
    #desired_caps['udid'] = udid
    desired_caps['appPackage'] = 'com.qzone'
    desired_caps['newCommandTimeout'] = 2000
    desired_caps['appActivity'] = 'com.tencent.sc.activity.SplashActivity'
    while True:
        try:
            driver = webdriver.Remote("http://localhost:"+ port +"/wd/hub", desired_caps)
            break
        except:
            utils.sleep(2)
            continue

    while not data.queue.empty():
        accounts.append(data.queue.get())


    print sys.argv[2]
    while hasAccount():
        #try:
            utils.sleep(5)
            account = getAccount()
            print "账号：" + str(account)
            try:
                driver.find_element_by_xpath("//android.widget.EditText[contains(@index,0)]").send_keys(account[0])#帐号
                driver.find_element_by_xpath("//android.widget.LinearLayout[1]/android.widget.FrameLayout/android.widget.EditText[contains(@index,0)]").send_keys(account[1])#密码
                utils.sleep(3)
                driver.find_element_by_class_name("android.widget.Button").click()

                isSkip = sys.argv[2]### 说明没有第二个参数，那么直接跳过
                if isSkip == "1":
                    isYanzheng = True
                    while isYanzheng:
                        #utils.sleep(10)
                        print "20miao"
                        try:
                            driver.find_element_by_xpath("//android.widget.Button[contains(@text,'验证')]").click()
                            utils.sleep(3)
                        except:
                            isYanzheng = False
                else:
                    ##print "fhjpeohfpoiehfpoawhjfejhoffhjpeohfpoiehfpoawhjfejhoffhjpeohfpoiehfpoawhjfejhof"
                    try:
                        skip(driver ,udid)
                        continue
                    except Exception ,e:
                        print str(e)
                        pass
            except Exception,e:
                print str(e)
                utils.sleep(3)

            try:
                utils.sleep(2)
                i = 20
                isNext=False
                while(i >1):
                    try:
                        driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'动态')]").click()
                    except:
                        try:
                            driver.find_element_by_xpath("//android.widget.EditText[contains(@index,0)]").location
                            isNext=True
                            break
                        except:
                            subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
                    i = i - 1
                    utils.sleep(4)
                if isNext==True:
                    continue
                subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 82", shell = True)
                utils.sleep(2)
                driver.find_element_by_xpath("//android.widget.FrameLayout/android.view.View/android.widget.TextView[contains(@index,1)]").click()
                utils.sleep(5)
            except:
                continue



        #except Exception ,e:
            #print e
            #continue

    driver.quit()
