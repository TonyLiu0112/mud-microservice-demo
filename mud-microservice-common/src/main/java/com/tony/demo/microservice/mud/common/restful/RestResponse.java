package com.tony.demo.microservice.mud.common.restful;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Restful API 响应对象
 * <p>
 * Created by Tony on 01/07/2017.
 */
public class RestResponse {

    private HttpStatus status;
    private Object data;
    private String error;

    public RestResponse() {
        this(null);
    }

    public RestResponse(Object data) {
        this.data = data;
        this.error = null;
    }

    public ResponseEntity<RestResponse> send(HttpStatus status) {
        this.status = status;
        return new ResponseEntity<>(this, status);
    }

    public ResponseEntity<RestResponse> send(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
        return new ResponseEntity<>(this, status);
    }

    public Object getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

}
