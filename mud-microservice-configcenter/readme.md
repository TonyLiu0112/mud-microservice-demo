## 配置中心
> 1. 配置文件统一管理
> 2. 配置更改后自动刷新到各个应用系统

## 注意事项

> 如果想通过service-id的方式在eureka上发现配置中心集群服务
> 客户端的配置需要配置在bootstrap.yml中，如果配置在application.yml中，配置无效