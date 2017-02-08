# -*- coding: UTF-8 -*-
import time

def sleep(seconds):
    for i in range(1, seconds + 1):
        time.sleep(1)
        print 'sleep {}'.format(i)