#!/bin/bash
if [ $# != 2  ];then
    echo "请输入两个参数"
    exit 1
fi
python ~/Documents/Android_Monitor/android_mobile_creative_monitor/scripts/guard.py ${1} ${2}
