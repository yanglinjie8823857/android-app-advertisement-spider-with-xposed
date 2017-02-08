# -*- coding: UTF-8 -*-
import os
import psutil
import utils
import path

lock = path.getPath('root') + "boot.lock"
def detect():
    #lock = path.getPath('boot_path') + 'boot.lock'
    loop = 0
    while(os.path.isfile(lock) == True and loop < 60):
        loop = loop+1
        utils.sleep(1)
    if(os.path.isfile(lock) == True):
        os.remove(lock)

def main():
    while(True):
        utils.sleep(2)
        detect()

if(__name__ == "__main__"):
    main()
