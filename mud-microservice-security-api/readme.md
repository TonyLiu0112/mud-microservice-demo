## 描述
### API key 认证服务器
> 1. API key实现JWT认证方式
> 2. 对称加密
> 3. 非对称加密

### 密钥生成步骤
> 使用java keytool 工具生成

1. 生成证书

        keytool -genkeypair -alias mud-api -keyalg RSA -keypass mud#234 -keystore ~/Downloads/mud-api.jks -storepass mud#234
        
    mud-api.jks文件请妥善保管，密码请务必记住
    
    > 注意：这里的两个密码需要一致，否则会遇到异常信息        
    
2. 获取公钥
        
        keytool -list -rfc --keystore mud-api.jks | openssl x509 -inform pem -pubkey
        
   可以得到如下信息
   
        -----BEGIN PUBLIC KEY-----
        MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhu9cIVXqavX9Yr8JCyw7
        K4JQ0M2BYZ2YcxWdVQt60gBRSoQWX/8lpF7WSdiGzK9UggdzV+YL7O9st3Pss9HH
        tKTWM759QzFzJfRdyrmargUw9GeAtaRLdFNLOuoBGD4it7K2Yo7rhEIOCioxqiKb
        wRe72ywVFV9l6fGZkK+LgB5P7KBmvplZ6TQezqV9bJpmNv+dJPcJQDNiBQ1EO6VW
        E+jRCWWHXIBuVEU6Uvwvcy61QFYm8vOlcdTqz5kn0P9mJjRPMEM+WH/Lu8PcbC9Q
        LlzUIzjDuBlybJ0y1TSwVaRdC7dSobNBLwBvh8NBTm4MyUaRNQeTHYMQpWjUmf7W
        HQIDAQAB
        -----END PUBLIC KEY-----
        -----BEGIN CERTIFICATE-----
        MIIDYTCCAkmgAwIBAgIEMM232jANBgkqhkiG9w0BAQsFADBhMQ4wDAYDVQQGEwVj
        aGluYTERMA8GA1UECBMIc2hhbmdoYWkxETAPBgNVBAcTCHNoYW5naGFpMQwwCgYD
        VQQKEwNtdWQxDDAKBgNVBAsTA211ZDENMAsGA1UEAxMEVG9ueTAeFw0xNzA0MjAw
        NzI3MThaFw0xNzA3MTkwNzI3MThaMGExDjAMBgNVBAYTBWNoaW5hMREwDwYDVQQI
        EwhzaGFuZ2hhaTERMA8GA1UEBxMIc2hhbmdoYWkxDDAKBgNVBAoTA211ZDEMMAoG
        A1UECxMDbXVkMQ0wCwYDVQQDEwRUb255MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A
        MIIBCgKCAQEAhu9cIVXqavX9Yr8JCyw7K4JQ0M2BYZ2YcxWdVQt60gBRSoQWX/8l
        pF7WSdiGzK9UggdzV+YL7O9st3Pss9HHtKTWM759QzFzJfRdyrmargUw9GeAtaRL
        dFNLOuoBGD4it7K2Yo7rhEIOCioxqiKbwRe72ywVFV9l6fGZkK+LgB5P7KBmvplZ
        6TQezqV9bJpmNv+dJPcJQDNiBQ1EO6VWE+jRCWWHXIBuVEU6Uvwvcy61QFYm8vOl
        cdTqz5kn0P9mJjRPMEM+WH/Lu8PcbC9QLlzUIzjDuBlybJ0y1TSwVaRdC7dSobNB
        LwBvh8NBTm4MyUaRNQeTHYMQpWjUmf7WHQIDAQABoyEwHzAdBgNVHQ4EFgQUlEDS
        YbnoaOmVVjdBE0N9teM9Vv4wDQYJKoZIhvcNAQELBQADggEBAHU9FFiYXQPpHLr5
        RJpHNsxnKhEK5MH1060K4q5Ki+Pl4sL56QkqXgalRfOg88MTpDhbMnvhM+auRuDl
        e5DnwKPWCHj+dvYidH88czq0OamwrgSjjufUfnron0mGle52chANmFSjkbomIwwe
        lo15xuFiVLDy6cN7LRVaMFCOEouCCJOjsUGRwXF+dyQtfeLJ4jK1K8FyXHFOU7vm
        YcxZkUUxGkw7K0j+EhFXbLLzPr2/VKzpvsQNfsSGXDE1Oiy3SfB78VOEUud/C0jI
        MKKO2D5EvJkv+avf1rgk86EI+2vYXJEh0d724d725Y2U76NPoYGobNtYCjAdm9c0
        Jy92hG8=
        -----END CERTIFICATE-----
        
   我们只要其中的公钥
        
        -----BEGIN PUBLIC KEY-----
        MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhu9cIVXqavX9Yr8JCyw7
        K4JQ0M2BYZ2YcxWdVQt60gBRSoQWX/8lpF7WSdiGzK9UggdzV+YL7O9st3Pss9HH
        tKTWM759QzFzJfRdyrmargUw9GeAtaRLdFNLOuoBGD4it7K2Yo7rhEIOCioxqiKb
        wRe72ywVFV9l6fGZkK+LgB5P7KBmvplZ6TQezqV9bJpmNv+dJPcJQDNiBQ1EO6VW
        E+jRCWWHXIBuVEU6Uvwvcy61QFYm8vOlcdTqz5kn0P9mJjRPMEM+WH/Lu8PcbC9Q
        LlzUIzjDuBlybJ0y1TSwVaRdC7dSobNBLwBvh8NBTm4MyUaRNQeTHYMQpWjUmf7W
        HQIDAQAB
        -----END PUBLIC KEY-----