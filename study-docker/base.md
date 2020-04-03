docker小记
=====

做镜像
```
docker build -t project .
```

查看镜像
```
docker images
```

进入交互式命令行 查看镜像结构
```
docker run -i -t [image id] /bin/bash
docker run -i -t [image id] /bin/sh
```
退出
```
exit
```

启动容器
```
docker run [-d] [-t 18095:8095] [image id]
参数说明
    -d：后台模式
    -p：将容器8095端口绑定到宿主机的18095端口上
```

查看容器内的标准输出
```
docker logs [container id]
```

查询容器
```
docker ps
docker ps -a
docker container ls
```

杀容器进程
```
docker kill [container id]
```

删除容器
```
docker rm [container id]
```

删除镜像
```
docker rmi [名称]
# 强制删除镜像
docker rmi -f [image id]
```

docker安装nginx
```
# 拉取官方最新版本的镜像
docker pull nginx:latest

# 运行容器
docker run --name nginx-test -p 8080:80 -d nginx
参数说明：
    --name nginx-test：容器名称。
    -p 8080:80： 端口进行映射，将本地 8080 端口映射到容器内部的 80 端口。
    -d nginx： 设置容器在在后台一直运行。
 
访问服务：
127.0.0.0:8080
```

推送镜像到私库
```
1.修改/etc/docker/daemon.json文件(没有则新增)，加入：
{
  "insecure-registries": ["192.168.25.30:5000"]
}
重启docker
systemctl restart docker.service

2.登录远程docker
docker login 192.168.25.30:5000
输入账密：
username/password

3.推送镜像：
docker tag project:latest 192.168.25.30:5000/project:1.2
docker push 192.168.25.30:5000/project:1.2

4.拉取镜像
docker pull 192.168.25.30:5000/project:1.2
```

docker安装portainer
```
1.查询当前有哪些Portainer镜像
docker search portainer
2.下载镜像
docker pull portainer/portainer
3.运行portainer 注意容器内端口一定是9000
docker run -d -p 9000:9000 \
    --restart=always \
    -v /var/run/docker.sock:/var/run/docker.sock \
    --name prtainer-test \
    portainer/portainer
访问：http://192.168.25.213:9000/#/init/admin
账密：admin/12345678
```
