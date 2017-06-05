## 使用docker部署

查看网络状态
    
    docker network ls

### 构建MySQL镜像

根据mysql文件夹下Dockerfile构建

    docker build -t mud/mysql:latest .

### 构建Redis镜像

Dockerfile:
    
    FROM redis
    EXPOSE 6379
    COPY redis.conf /usr/local/etc/redis/redis.conf
    CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]
    
构建脚本
    
    docker build -t mud/redis:latest .
        
### 构建基础应用镜像

依赖maven的docker插件,使用命令构建

    mvn clean package docker:build
    
### 使用docker-compose启动应用

单个docker实例中，构建分布式应用参考:
    [docker-compose.yml](https://github.com/TonyLiu0112/mud-microservice-demo/blob/master/docker/docker-compose.yml)
    
如果docker实例有多个，并且分散在不同的虚拟机中，请使用docker Swarm工具构建集群
>待定....

### MacOS下 docker镜像文件dockerDocker.qcow2过大解决办法

使用`brew`安装`qemu`
    
    brew install qemu
    
登录docker虚拟机

    screen ~/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/tty
    
在docker虚拟机中执行下面命令，需要花费几分钟

    dd if=/dev/zero of=/var/tempfile
    rm /var/tempfile

退出虚拟机，并关闭docker后，在本地终端执行如下命令
    
    
    $ cd ~/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux
    $ mv Docker.qcow2 Docker.qcow2.original
    $ du -hs Docker.qcow2.original
    12G     Docker.qcow2.original
    $ qemu-img convert -O qcow2 Docker.qcow2.original Docker.qcow2
    $ rm Docker.qcow2.original
    $ du -hs Docker.qcow2
    772M    Docker.qcow2
