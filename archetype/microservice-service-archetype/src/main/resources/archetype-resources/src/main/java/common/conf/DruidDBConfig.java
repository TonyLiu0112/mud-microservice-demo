#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.common.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * alibaba druid connection pool configuration
 * <p>
 * Created by Tony on 07/02/2017.
 */
@Configuration
@MapperScan("com.tony.demo.microservice.mud.service.${artifactId}.dao.mapper")
public class DruidDBConfig {
    private Logger logger = Logger.getLogger(DruidDBConfig.class);

    @Value("${symbol_dollar}{spring.datasource.url}")
    private String dbUrl;

    @Value("${symbol_dollar}{spring.datasource.username}")
    private String username;

    @Value("${symbol_dollar}{spring.datasource.password}")
    private String password;

    @Value("${symbol_dollar}{spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${symbol_dollar}{spring.datasource.initialSize}")
    private int initialSize;

    @Value("${symbol_dollar}{spring.datasource.minIdle}")
    private int minIdle;

    @Value("${symbol_dollar}{spring.datasource.maxActive}")
    private int maxActive;

    @Value("${symbol_dollar}{spring.datasource.maxWait}")
    private int maxWait;

    @Value("${symbol_dollar}{spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${symbol_dollar}{spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${symbol_dollar}{spring.datasource.validationQuery}")
    private String validationQuery;

    @Value("${symbol_dollar}{spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${symbol_dollar}{spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${symbol_dollar}{spring.datasource.testOnReturn}")
    private boolean testOnReturn;

    @Value("${symbol_dollar}{spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${symbol_dollar}{spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${symbol_dollar}{spring.datasource.filters}")
    private String filters;

    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;

    @Bean     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }
}
