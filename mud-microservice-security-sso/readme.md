## 描述
> 1. SSO单点登入
> 2. 分布式session的管理

## Spring OAuth2 SOO 流程

 1. 在未登入状态下请求资源服务器的资源（通常是网关服务），假设这里请求/env资源
 2. 资源请求被网关服务的FilterSecurityInterceptor（是一个选民选举决定器）拒绝服务，并通过302重定向到网关的login服务。
 3. login的请求重新开启过滤链，并被OAuth2ClientAuthenticationProcessingFilter拦截，拦截后尝试使用OAuth2RestTemplate完成认证，获得访问token。
 4. 使用OAuth2RestTemplate认证时候，尝试从OAuth2RestTemplate的context对象中获取可用的token信息，但因第一次访问，获取失败，抛出UserRedirectRequiredException，将用户通过302重定向到SSO服务器的/oauth/authorize请求。
 5. SSO服务器尝试获取/oauth/authorize资源，处理结果同2，返回SSO登入页面.
 6. 用户登入，调用SSO工程的login请求，SSO工程通过UsernamePasswordAuthenticationFilter过滤器完成用户登入用户名密码认证操作
 7. 认证成功后，在SavedRequestAwareAuthenticationSuccessHandler的onAuthenticationSuccess方法中，重定向到SSO服务器的/oauth/authorize请求
 8. 在/oauth/authorize请求中，构建oauth的认证信息，并返回一个RedirectView的ModelAndView对象，对象中包含认证信息，redrect的url就是网关服务器的login服务
 9. 网关服务接收到SSO重定向过来的login服务后，做认证，因为SSO已经返回了认证信息，所以认证通过，并在SavedRequestAwareAuthenticationSuccessHandler的成功认证的回掉方法中重定向到/env的资源请求下
 
## ClientDetailsServiceConfigurer:

客户端明细信息的服务配置类
1. clientId: (required) the client id.
2. secret: (required for trusted clients) the client secret, if any.
3. scope: The scope to which the client is limited. If scope is undefined or empty (the default) the client is not limited by scope.
4. authorizedGrantTypes: Grant types that are authorized for the client to use. Default value is empty.
5. authorities: Authorities that are granted to the client (regular Spring Security authorities).

## Token管理

### AuthorizationServerTokenServices
- 当创建访问令牌时，必须存储身份验证，以便接受访问令牌的资源可以稍后引用。
- 访问令牌用于加载用于授权其创建的认证

### token的存储
- InMemoryTokenStore
    内存模式，适合单项目使用，在开发的阶段可以使用这个模式
- JdbcTokenStore
    数据库存储，在集群模式下，将token信息存储到关系型数据库中
- JSON Web Token (JWT) version
    将授权数据编码到令牌本身，没有后端存储，将全部信息发放给客户端，这样导致无法轻易撤销令牌，使用场景是发放一个短期的令牌；

### SSO服务器修改配置
- AuthorizationServerConfigurer接口
    - AuthorizationServerConfigurerAdapter
重写AuthorizationServerConfigurerAdapter即可；
重写后可以替换token的存储方式、client details的存储方式；

### 构建SSO注意事项

1. 重写AuthorizationCodeServices并交给spring管理
    
    
        @Bean
        public AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }
        
2. 重写TokenStore并交给spring管理


        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

3. 重写ClientDetailsService并交给spring管理


        @Bean
        public ClientDetailsService clientDetails() {
            return new JdbcClientDetailsService(dataSource);
        }
    
4. 对于多个SSO下游服务集群，如果使用zuul作为SSO网关，配置上需要注意，如果使用ribbon来负载，
不要通过eureka来发现服务，请使用静态ribbon静态列表方式，前者会有重定向的问题，eg:
    
    
        zuul:
          routes:
            auth-server:
              path: /security/**
              serviceId: auth
              sensitive-headers:
        
        ribbon:
          eureka:
            enabled: false
          eager-load:
            enabled: true
        
        auth:
          ribbon:
            listOfServers: http://localhost:9998/,http://localhost:9997/
            NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RoundRobinRule