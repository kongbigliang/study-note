### 安装linux
```
docker pull centos:7
# 查看本地镜像
docker images
# 创建并运行容器
docker run -d -i -t --name centos7 [镜像id] /bin/bash
# 查看容器id
docker container ls
# 进入使用容器
docker exec -it [容器id] bash
# 安装...
yum install -y net-tools
yum install -y wget
yum install -y vim*
```

### 安装zookeeper
```
docker search zookeeper
docker pull zookeeper
docker run -p 2181:2181 --name zookeeper zookeeper
当光标停止后，按Ctrl+C，然后通过docker ps 来查看容器的信息。
docker ps -a
docker exec -it [容器id] /bin/bash
```
