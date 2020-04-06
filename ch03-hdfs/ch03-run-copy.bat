@echo off

@rem 用cmd script运行
hadoop fs -copyFromLocal ../input/docs/quangle.txt \ hdfs://localhost/user/tom/quangle.txt
@remhadoop fs -copyFromLocal ../input/docs/quangle.txt /D:/user/tom/quangle.txt