# -*- coding: UTF-8 -*-

def getPath(key):
    path = {}
    path['test.py'] = "/home/zingfront/dev/monitor/scripts/test.py"
    path['player'] = "/home/zingfront/dev/genymotion/genymotion/player"
    path['main.js'] = "/home/zingfront/dev/appium/build/lib/main.js"
    path['log'] = "/home/zingfront/dev/monitor/log/log"
    path['lock'] = "/home/zingfront/dev/monitor/"
    path['root'] = '/home/zingfront/dev/monitor/'
    path['pythonName'] = 'python'
    if(path.has_key(key)):
        return path[key]
    else:
        return False
