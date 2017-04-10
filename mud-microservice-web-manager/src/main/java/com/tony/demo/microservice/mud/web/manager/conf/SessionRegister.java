package com.tony.demo.microservice.mud.web.manager.conf;

import org.apache.log4j.Logger;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * 将session的配置注册到servlet过滤器中
 * 确保每一个请求都能被spring的SessionRepositoryFilter拦截
 * <p>
 * Created by Tony on 10/04/2017.
 */
public class SessionRegister extends AbstractHttpSessionApplicationInitializer {

    private Logger logger = Logger.getLogger(SessionRegister.class);

    public SessionRegister() {
        super(SessionConfig.class);
        logger.info("Success to register SessionConfig.");
    }
}
