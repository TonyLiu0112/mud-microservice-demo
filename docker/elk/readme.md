# 使用ELK(Elasticsearch、Logstash、Kibana)日志分析
> 单机测试方案，分布式系统考虑使用logstash的代理

大致思路如下
1. 业务系统通过logback、logstash写日志到本地日志目录`/data/mud-microservice/logs/mud-manager/logstash`
2. 将本地日志目录映射到ELK容器路径
3. 添加本地logstash配置文件，修改ELK容器获取日志的日志源
4. 构建容器

## logstash配置

在本地创建`02-beats-input.conf`，添加如下内容

    input {
      file {
        codec => json
        path => "/data/mud-microservice/logs/*/logstash/*.json"
      }
    }

## 运行docker镜像

    docker run -d \ 
        -v /data/mud-microservice/logs/:/data/mud-microservice/logs/ \ 
        -v /data/mud-microservice/logs/logbash/config/02-beats-input.conf:/etc/logstash/conf.d/02-beats-input.conf \
        -- name elk \
        -p 5601:5601 sebp/elk
        
## 访问UI

    http://localhost:5601
    
访问业务系统后，Kibana界面可以看到日志分析数据

## 参考官方配置文档

[配置文档](http://elk-docker.readthedocs.io/)
