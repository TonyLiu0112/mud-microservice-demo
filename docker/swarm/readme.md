# 使用docker部署swarm集群

## 环境说明

#### 虚拟机
>ubuntu-16.04.2-server-amd64

    ubuntu1: 10.211.55.9

    ubuntu2: 10.211.55.10

    ubuntu3: 10.211.55.11

### 打开docker监听2375端口
> 使用办法一,一直出现2375端口无法打开的情况，参考 [Issue](https://github.com/docker/swarm/issues/2704)

1. 办法一

		sudo vi /etc/default/docker
    
	在文件最后一行添加如下内容

    	DOCKER_OPTS="$DOCKER_OPTS -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock"
    	
2. 办法二
	
		// 暂停已存在的docker服务
		sudo service docker stop
		// 启动服务
		sudo nohup docker daemon -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock --cluster-store=consul://<consul 服务地址>:8500 --cluster-advertise=<docker 服务地址>:2375 &
    
## 构建swarm集群


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
    
#### 测试可用性

	docker -H 10.211.55.9:4000 run -d redis

可以看到运行通过55.9这台机器的manager，redis运行在集群的任意一台机器上了