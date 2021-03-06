### git2consul
> git2consul是consul社区的一个开源项目,用于consul实现从git拉取分布式数据的工具

#### basic contain

```bash
$ docker run -d -p 8400:8400 -p 8500:8500 -p 8600:53/udp --name consul-service --hostname discovery-service progrium/consul -server -bootstrap
```

#### step1
    
```bash
$ mkdir /Users/Tony/Documents/workspace-docker/consul/git2consul
```    
    
#### step2    

```bash
$ cat <<EOF > /Users/Tony/Documents/workspace-docker/consul/git2consul/config.json
{
  "version": "1.0",
  "repos" : [{
    "name" : "mud_configuration",
    "url" : "https://github.com/TonyLiu0112/mud-microservice-configuration-repository.git",
    "branches" : ["master"],
    "hooks": [{
      "type" : "polling",
      "interval" : "1"
    }]
  }]
}
EOF
```
#### step3

```bash
$ CONSUL_IP=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' consul-service)
```

#### step4

```bash
$ docker run -d --name git2consul -v /Users/Tony/Documents/workspace-docker/consul/git2consul:/etc/git2consul.d cimpress/git2consul --endpoint $CONSUL_IP --port 8500 --config-file /etc/git2consul.d/config.json
```