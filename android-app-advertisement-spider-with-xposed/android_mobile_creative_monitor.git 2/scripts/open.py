#!/usr/bin/env python2
#-*-encoding:utf-8-*-

import os,sys
def listdir(dir,myfile):
    #myfile.write(dir + '\n')
    fielnum = 0
    list = os.listdir(dir)  #列出目录下的所有文件和目录
    for line in list:
        filepath = os.path.join(dir,line)
        if os.path.isdir(filepath):  #如果filepath是目录，则再列出该目录下的所有文件
            #myfile.write('   ' + line + '//'+'\n')
            print "dir:"+filepath
            listdir(filepath, myfile)
            #for li in os.listdir(filepath):
                #if li.count('$') == 0:
                    #myfile.write(dir+'//'+li + '\n')
                    #fielnum = fielnum + 1
        elif os.path:   #如果filepath是文件，直接列出文件名
            
            if line.count('$') == 0:
                print "file:"+line
                print myfile.write(dir+'/'+line + '\n')
                #fielnum = fielnum + 1
    #myfile.write('all the file num is '+ str(fielnum))
dir = raw_input('please input the path:')
myfile = open(dir+"/"+'list.txt','a')
listdir(dir,myfile)
