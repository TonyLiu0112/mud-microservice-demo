## 配置中心
> 1. 配置文件统一管理
> 2. 配置更改后自动刷新到各个应用系统

## 高可用

> 需要保证配置中心高可用，需要保证以下的条件

* rabbitmq高可用: 由于配置刷新的功能依赖于rabbitmq，所以需要配置中心高可用，首先需要保证rabbitmq高可用
* 配置中心本身的高可用: 可以将配置中心配置成集群模式

## 注意事项

> 如果想通过service-id的方式在eureka上发现配置中心集群服务
> 客户端的配置需要配置在bootstrap.yml中，如果配置在application.yml中，配置无效

## RSA加密配置

> 对于仓库中敏感数据，使用非对称加密

1. 生成密钥文件

        keytool -genkey -alias server -keypass server -keyalg RSA -keysize 1024 -validity 365 -keystore server.jks -storepass qazxsw
        
2. 配置密钥

        encrypt:
          key-store:
            location: classpath:/server.jks    # 本地
            password: letmein                  # 对应的storepass
            alias: mytestkey                   # 别名
            secret: changeme                   # 对应keypass
            
3. 生成密文

> 如果明文中包含特殊字符，需要设置Content-Type: text/plain，否则解密时候将无法使用，例如:
> `url -H "Content-Type: text/plain" http://localhost:8888/encrypt -d jdbc:mysql://localhost:3306/guard?characterEncoding=utf8`

        curl http://localhost:8888/encrypt -d 明文
        
4. 使用密文
    
    
        redis:
          host: localhost
          port: 6379
          password: '{cipher}AIAVymXfW1eYiIBs9O7Wfp62Z0uG+GIfSRkU9pU+kRroeT1inlDTxzv+5hSgUb5hKNQgumjAwBqr2Y8LoSE5r+zocr3T5DPAfk81YW/fMa2SBU3ZCoJ/pwmfiQxTSQKrRFoApiMhvO8rNSJwYLikDDushSuStjburqnlBlWmESCcRJlFCWr3FN87UnImP/AJ1yQmsi7oQS2WANGyLVcPWyfX'