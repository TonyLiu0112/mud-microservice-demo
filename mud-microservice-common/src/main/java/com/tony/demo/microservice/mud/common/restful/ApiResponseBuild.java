package com.tony.demo.microservice.mud.common.restful;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Restful API 响应构造器
 * <p>
 * Created by Tony on 01/07/2017.
 */
public class ApiResponseBuild {

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> ok(Object data) {
        return new RestResponse(data).send(HttpStatus.OK);
    }

    /**
     * 服务器成功返回用户请求的数据，该操作是幂等的
     *
     * @return
     */
    public static ResponseEntity<RestResponse> ok() {
        return ApiResponseBuild.ok(null);
    }

    /**
     * 新建或修改数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> created(Object data) {
        return new RestResponse(data).send(HttpStatus.CREATED);
    }

    /**
     * 新建或修改数据成功
     *
     * @return
     */
    public static ResponseEntity<RestResponse> created() {
        return ApiResponseBuild.created(null);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> accepted(Object data) {
        return new RestResponse(data).send(HttpStatus.ACCEPTED);
    }

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     *
     * @return
     */
    public static ResponseEntity<RestResponse> accepted() {
        return ApiResponseBuild.accepted(null);
    }

    /**
     * 用户删除数据成功
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> deleted(Object data) {
        return new RestResponse(data).send(HttpStatus.NO_CONTENT);
    }

    /**
     * 用户删除数据成功
     *
     * @return
     */
    public static ResponseEntity<RestResponse> deleted() {
        return ApiResponseBuild.deleted(null);
    }

    /**
     * 错误的请求
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> badRequest(Object data) {
        return new RestResponse(data).send(HttpStatus.BAD_REQUEST);
    }

    /**
     * 错误的请求
     *
     * @return
     */
    public static ResponseEntity<RestResponse> badRequest() {
        return ApiResponseBuild.badRequest(null);
    }

    /**
     * 用户访问未授权
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> unauthorized(Object data) {
        return new RestResponse(data).send(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 用户访问未授权
     *
     * @return
     */
    public static ResponseEntity<RestResponse> unauthorized() {
        return ApiResponseBuild.unauthorized(null);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> forbidden(Object data) {
        return new RestResponse(data).send(HttpStatus.FORBIDDEN);
    }

    /**
     * 用户访问获得授权，但无访问权限
     *
     * @return
     */
    public static ResponseEntity<RestResponse> forbidden() {
        return ApiResponseBuild.forbidden(null);
    }

    /**
     * 资源未找到
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> notFound(Object data) {
        return new RestResponse(data).send(HttpStatus.NOT_FOUND);
    }

    /**
     * 资源未找到
     *
     * @return
     */
    public static ResponseEntity<RestResponse> notFound() {
        return ApiResponseBuild.notFound(null);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> unprocesableEntity(Object data) {
        return new RestResponse(data).send(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * POST PUT PATCH 请求对象参数验证错误
     *
     * @return
     */
    public static ResponseEntity<RestResponse> unprocesableEntity() {
        return ApiResponseBuild.unprocesableEntity(null);
    }

    /**
     * 请求被锁定 不可用
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> locked(Object data) {
        return new RestResponse(data).send(HttpStatus.LOCKED);
    }

    /**
     * 请求被锁定 不可用
     * @return
     */
    public static ResponseEntity<RestResponse> locked() {
        return ApiResponseBuild.locked(null);
    }

    /**
     * 内部服务错误，请和管理员联系
     *
     * @param data
     * @return
     */
    public static ResponseEntity<RestResponse> serverError(Object data) {
        return new RestResponse(data).send(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 内部服务错误，请和管理员联系
     *
     * @return
     */
    public static ResponseEntity<RestResponse> serverError() {
        return ApiResponseBuild.serverError(null);
    }


}
