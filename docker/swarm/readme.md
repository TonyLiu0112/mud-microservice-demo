### 使用docker部署swarm集群

### 环境说明

#### 虚拟机
>ubuntu-16.04.2-server-amd64

    ubuntu1: 10.211.55.9

    ubuntu2: 10.211.55.10

    ubuntu3: 10.211.55.11

### 打开docker监听2375端口

    sudo vi /etc/default/docker
    
在文件最后一行添加如下内容

    DOCKER_OPTS="$DOCKER_OPTS -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock"
    
### 启动swarm


#### 管理节点
一主一备保证高可用

    sudo docker run -d -p 4000:4000 swarm manage -H :4000 --replication --advertise 10.211.55.9:4000 consul://10.211.55.9:8500
    sudo docker run -d -p 4000:4000 swarm manage -H :4000 --replication --advertise 10.211.55.10:4000 consul://10.211.55.9:8500
    
#### 工作节点

    sudo docker run -d swarm join --advertise=10.211.55.9:2375 consul://10.211.55.9:8500
    sudo docker run -d swarm join --advertise=10.211.55.10:2375 consul://10.211.55.9:8500
    sudo docker run -d swarm join --advertise=10.211.55.11:2375 consul://10.211.55.9:8500
    
#### 检查状态

    sudo docker -H 10.211.55.9:4000 info
    
### 问题

每个节点2375端口无法启动,参考[ISSUE](https://github.com/docker/swarm/issues/2704)