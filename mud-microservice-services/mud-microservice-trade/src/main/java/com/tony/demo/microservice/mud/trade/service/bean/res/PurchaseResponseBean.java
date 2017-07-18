package com.tony.demo.microservice.mud.trade.service.bean.res;

import java.util.Map;

/**
 *
 */
public class PurchaseResponseBean {

    private String successful;

    private Map<String, String> results;

    public Map<String, String> getResults() {
        return results;
    }

    public void setResults(Map<String, String> results) {
        this.results = results;
    }

    public String getSuccessful() {
        return successful;
    }

    public void setSuccessful(String successful) {
        this.successful = successful;
    }
}
