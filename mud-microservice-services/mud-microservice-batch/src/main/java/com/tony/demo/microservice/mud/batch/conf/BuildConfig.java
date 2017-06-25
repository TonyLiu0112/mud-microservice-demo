package com.tony.demo.microservice.mud.batch.conf;

import com.tony.demo.microservice.mud.batch.BatchApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * Build project with WAR file.
 * <p>
 * Created by Tony on 10/02/2017.
 */
@Configuration
public class BuildConfig extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BatchApplication.class);
    }
}
