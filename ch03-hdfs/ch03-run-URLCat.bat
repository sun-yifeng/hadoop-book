@echo off

@rem 设置hadoop的环境变量(当前目录的上级目录)
set HADOOP_CLASSPATH=../hadoop-examples.jar;
@rem 删除out
@rem hadoop fs -rm -f -R output
@rem 用cmd script运行
hadoop URLCat hdfs://localhost/user/tom/quangle.txt