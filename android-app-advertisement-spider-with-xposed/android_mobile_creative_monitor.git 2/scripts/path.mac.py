# -*- coding: UTF-8 -*-

def getPath(key):
    path = {}
    path['test.py'] = "/Users/yuhaiqiang/Documents/Android_Monitor/android_mobile_creative_monitor/scripts/test.py"
    path['player'] = "/Applications/Genymotion.app/Contents/MacOS/player.app/Contents/MacOS/player"
    path['main.js'] = "/Applications/Appium.app/Contents/Resources/node_modules/appium/build/lib/main.js"
    path['log'] = "/Users/yuhaiqiang/Desktop/log/"
    path['boot_path'] = "/Users/yuhaiqiang/Documents/Android_Monitor/android_mobile_creative_monitor/"
    path['root'] = '/Users/yuhaiqiang/Documents/Android_Monitor/android_mobile_creative_monitor/'
    path['pythonName'] = 'Python'
    if(path.has_key(key)):
        return path[key]
    else:
        return False
