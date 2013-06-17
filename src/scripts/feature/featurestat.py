#!/usr/bin/python
#-*-coding:utf-8-*-
import os
import sys
'''
统计各个featrue的值的个数 sort | uniq -c 

'''
def loadNamesMeta(filename):
    lines = open(filename, "r").readlines()
    index = 0
    dict = {}
    for item in lines:
        dict[index] = item.strip().split()[0]
        index += 1
    return dict

def doStat(name_dict, stat_result, filename, bad_result):
    lines = open(filename, "r").readlines()
    for item in lines:
        item = item.strip()
        item_info = item.split("\t")
        if len(item_info) <> len(name_dict) :
            continue
        for index in xrange(len(item_info)):
            if index == 0:
                continue
            if index not in stat_result:
                stat_result[index] = {}
            value = item_info[index]
            if value not in stat_result[index] :
                stat_result[index][value] = 0
            stat_result[index][value] += 1

files = {"conv.*":"other.name", "bid.*":"bid.name", "clk.*":"other.name", "imp.*":"other.name"}

dir = "../../Zip/"
if os.path.exists(dir) and os.path.isdir(dir):
    pass
else:
    print "please put the data at ../../Zip/"
    sys.exit(1)



dict_meta = {}
dict_meta["bid.name"] = loadNamesMeta("bid.name")
dict_meta["other.name"] = loadNamesMeta("other.name")

all_stat = {}
for item in files:
    lines = os.popen("ls %s%s"%(dir, item)).readlines()
    name_dict = dict_meta[files[item]]
    stat_result = {}
    bad_result = []
    for filename in lines:
        filename = filename.strip()
        doStat(name_dict, stat_result, filename, bad_result)
    all_stat[item] = stat_result
    #break


for item in files:
    name_dict = dict_meta[files[item]]
    if item not in all_stat:
        continue
    value_dict = all_stat[item]
    print item
    for index in value_dict:
        print "\t" +  name_dict[index], len(value_dict[index])
        #for e in value_dict[index]:
        #    print "\t\t" + e , value_dict[index][e]
