package com.tony.demo.microservice.mud.user.config.properties;

public class PoolProperties {

    private String maxIdle;
    private String total;
    private String maxWaitMillis;

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(String maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }
}
