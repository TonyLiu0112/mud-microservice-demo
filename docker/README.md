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