1 win10下安装说明：
https://www.jianshu.com/p/aa8cfaa26790

2 查看版本(没有-符号)
hadoop version

3 启动
sbin/start-dsf.sh
sbin/start-yarn.sh
sbin/mr-jobhistory-daemon.sh start historyserver
--
sbin/start-all.sh

4 访问
http://localhost:50070 访问namenode界面
http://localhost:8088  访问资源管理器界面
http://localhost:19888 查看历史服务器

5 停止
sbin/mr-jobhistory-daemon.sh stop historyserver
sbin/stop-yarn.sh
sbin/stop-dsf.sh
--
sbin/stop-all
