# -*- coding: UTF-8 -*-

import time
import os
import subprocess

from appium import webdriver

import utils
import data


def operate(port, udid):
    print('operate')
    utils.sleep(50)
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
    j = 10
    while j > 0:
        try:
            driver = webdriver.Remote("http://localhost:"+port+"/wd/hub", desired_caps)
            break
        except:
            j = j - 1
            print "youtube打开应用失败，两秒后再次尝试"
            utils.sleep(2)
            if(j == 0):
                return
    utils.sleep(15)


    try:
        subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 300 500 100 500", shell = True)
        utils.sleep(5)
        driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'Gaming')]").click()
        utils.sleep(1)
    except Exception,e:
        print str(e)
    subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 300 800 300 200", shell = True)#向上 划一下
    utils.sleep(3)
    subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 10 500", shell = True) #点击进入一个视频
    utils.sleep(5)
    loop = 5
    while loop > 0:
        i = 0
        num = 20###进入 40个视频
        while num > 0:
            try:
                start_loc = driver.find_element_by_class_name("android.widget.Switch").location;
                print str(start_loc)
                x = start_loc['x']
                y = start_loc['y']
                if(y > 700):
                    subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 300 800 300 700", shell = True)#向上 划一下
                    continue
                first_y = int(y) + 100
                subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 100 " + str(first_y), shell = True) #点击进入一个视频
                num = num - 1
                i = 0
                utils.sleep(30)
            except Exception,e:
                print str(e)
                try:####如果出现Comments，那么点击comment上一个视频
                    start_loc = driver.find_element_by_xpath("//android.widget.TextView[contains(@text,'Comments')]").location;
                    y = start_loc['y']
                    last_y = int(y) - 170
                    subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 100 " + str(last_y), shell = True) #点击进入一个视频
                except Exception,e:
                    i = i + 1
                    if(i == 20):
                        subprocess.Popen("adb -s '"+ udid +"' shell input keyevent 4", shell = True)
                        i = 0
                        num = num - 2
                        utils.sleep(3)
                        continue
                    subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 300 800 300 700", shell = True)#向上 划一下

        driver.quit()
        utils.sleep(4)
        driver = webdriver.Remote("http://localhost:"+port+"/wd/hub", desired_caps)
        utils.sleep(5)
        try:
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen swipe 300 800 300 200", shell = True)#向上 划一下
            utils.sleep(3)
            subprocess.Popen("adb -s '"+ udid +"' shell input touchscreen tap 10 500", shell = True) #点击进入一个视频
            utils.sleep(5)
        except Exception,e:
            print str(e)
        loop = loop - 1### 重启一次应用
