#!/bin/bash
a=`date +%Y-%m-%d`
b=`date +%H-%M-%S`
time=${b}
home=/home/zingfront
log_dir="${home}/dev/logs/${a}"
if [ ! -d $log_dir ]; then
mkdir $log_dir
fi
if [ -f ${home}/dev/logs/log ];then
#mv ${home/dev/logs/log $log_dir/${time}
cat ${home}/dev/logs/log > $log_dir/${time}
#touch ${home}/dev/logs/log
echo "" > ${home}/dev/logs/log
echo "归档文件:${log_dir}"/"${time}"
else
touch ${home}/dev/logs/log
fi
