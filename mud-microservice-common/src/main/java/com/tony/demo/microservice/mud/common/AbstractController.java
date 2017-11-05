package com.tony.demo.microservice.mud.common;

import com.tony.demo.microservice.mud.common.content.code.APICode;
import com.tony.demo.microservice.mud.common.results.APIResponse;
import com.tony.demo.microservice.mud.common.results.PageRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.tony.demo.microservice.mud.common.AbstractController.ResponseKey.*;
import static com.tony.demo.microservice.mud.common.content.code.APICode.*;

/**
 * 最高抽象控制器，不提供controller服务，仅封装公用方法
 * <p>
 * Generator controller
 */
@Deprecated
public abstract class AbstractController implements MessageSourceAware {

    private MessageSource messageSource;

    enum ResponseKey {
        failure, success, msg, results, code
    }

    public Map<String, Object> fail() {
        return fail(FAILED);
    }

    public Map<String, Object> fail(APICode code) {
        return data(code.getCode(), code.getMessage());
    }

    public Map<String, Object> fail(String code, Object[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put(failure.name(), true);
        String message = messageSource.getMessage(code, args, "Fail!", null);
        map.put(msg.name(), message);
        return map;
    }

    public APIResponse failAPIResponse() {
        return new APIResponse(UNKNOW.getCode(), UNKNOW.getMessage());
    }

    public Map<String, Object> data(Object data) {
        return data(data, SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public Map<String, Object> data(Object data, String cd, String message) {
        Map<String, Object> map = new HashMap<>();
        map.put(results.name(), data);
        map.put(code.name(), cd);
        map.put(msg.name(), message);
        return map;
    }

    public Map<String, Object> data(Object data, APICode successCode) {
        return data(data, successCode.getCode(), successCode.getMessage());
    }

    public Map<String, Object> pageData(Object data, PageRequest pageRequest) {
        Map<String, Object> map = data(data);
        map.put("draw", pageRequest.getDraw());
        return map;
    }

    private Map<String, Object> data(String code, String message) {
        return this.data(null, code, message);
    }

    private Map<String, Object> data(APICode code) {
        return this.data(code.getCode(), code.getMessage());
    }

    public Map<String, Object> success() {
        return data(SUCCESS);
    }

    public Map<String, Object> success(APICode code) {
        return data(code);
    }

    public APIResponse successAPIResponse() {
        return new APIResponse(SUCCESS.getCode(), SUCCESS.getMessage());
    }

    public PageRequest getPageRequest(HttpServletRequest httpReq) {
        int orderIndex = Integer.parseInt(httpReq.getParameter("order[0][column]"));
        int size = Integer.parseInt(httpReq.getParameter("length"));
        int start = Integer.parseInt(httpReq.getParameter("start"));
        PageRequest pageRequest = new PageRequest();
        pageRequest.setOrderColumn(httpReq.getParameter("columns[" + orderIndex + "][data]"));
        pageRequest.setOrderType(httpReq.getParameter("order[" + orderIndex + "][dir]"));
        pageRequest.setPageSize(size);
        pageRequest.setPageNum((start + size) / size);
        pageRequest.setDraw(Integer.parseInt(httpReq.getParameter("draw")));
        return pageRequest;
    }

    public void loadImage(HttpServletResponse httpRes, String fileFullName) throws Exception {
        OutputStream outputStream = httpRes.getOutputStream();
        httpRes.setHeader("Access-Control-Allow-Origin", "*");
        httpRes.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        httpRes.setHeader("Access-Control-Max-Age", "3600");
        httpRes.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        httpRes.setHeader("Access-Control-Allow-Credentials", "true");
        httpRes.setHeader("Pragma", "No-cache");
        httpRes.setHeader("Cache-Control", "no-cache");
        httpRes.setDateHeader("Expires", 0);
        httpRes.setContentType("image/png");
        IOUtils.write(IOUtils.toByteArray(new FileInputStream(fileFullName)), outputStream);
        IOUtils.closeQuietly(outputStream);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
