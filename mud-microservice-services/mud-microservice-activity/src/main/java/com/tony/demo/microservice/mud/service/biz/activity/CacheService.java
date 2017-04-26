package com.tony.demo.microservice.mud.service.biz.activity;

import com.kiisoo.tp.common.redis3.RedisClient;
import com.tony.demo.microservice.mud.common.content.cache.ActivityKey;
import com.tony.demo.microservice.mud.common.content.cache.CustomerActivityKey;
import com.tony.demo.microservice.mud.common.content.cache.CustomerKey;
import com.tony.demo.microservice.mud.service.model.res.*;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Cache operation.
 * <p>
 * Created by Tony on 22/03/2017.
 */
@Service
public class CacheService {

    private final static String CUSTOMER = CustomerKey.KEY.key();
    private final static String ACTIVITY = ActivityKey.KEY.key();
    private final static String SCORE_ATTR = "SCORE_ATTR_";
    private final static String SCORE_ATTR_ORDER = "SCORE_ATTR_ORDER_";
    private final static String GENERATOR_ATTR = "GENERATOR_ATTR_";

//    @Autowired
//    private CustomerActivityService customerActivityService;

    /**
     * 缓存客户信息
     *
     * @param customer
     */
    public void cacheCustomer(CustomerRes customer) {
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_ID.key(), customer.getId().toString());
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_NAME.key(), StringUtils.isEmpty(customer.getName()) ? "" : customer.getName());
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_CODE.key(), StringUtils.isEmpty(customer.getCode()) ? "" : customer.getCode());
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_ADDRESS.key(), StringUtils.isEmpty(customer.getAddress()) ? "" : customer.getAddress());
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_CONTACT_PHONE.key(), StringUtils.isEmpty(customer.getContactPhone()) ? "" : customer.getContactPhone());
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_CONTACT_NAME.key(), StringUtils.isEmpty(customer.getContactName()) ? "" : customer.getContactName());
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_INDUSTRY_CODE.key(), StringUtils.isEmpty(customer.getIndustryCode()) ? "" : customer.getIndustryCode());
        RedisClient.hset(CUSTOMER + customer.getId(), CustomerKey.FIELD_INDUSTRY_NAME.key(), StringUtils.isEmpty(customer.getIndustryName()) ? "" : customer.getIndustryName());
    }

    /**
     * 缓存活动信息
     *
     * @param activity
     */
    public void cacheActivity(ActivityRes activity) {
        RedisClient.hset(ACTIVITY + activity.getId(), ActivityKey.FIELD_ID.key(), activity.getId().toString());
        RedisClient.hset(ACTIVITY + activity.getId(), ActivityKey.FIELD_NAME.key(), activity.getName());
        RedisClient.hset(ACTIVITY + activity.getId(), ActivityKey.FIELD_CODE.key(), activity.getCode());
        RedisClient.hset(ACTIVITY + activity.getId(), ActivityKey.FIELD_STATE.key(), activity.getState().toString());
        RedisClient.hset(ACTIVITY + activity.getId(), ActivityKey.FIELD_TYPE.key(), activity.getType().toString());
        RedisClient.hset(ACTIVITY + activity.getId(), ActivityKey.FIELD_ACTIVITY_TIME.key(), activity.getActivityTime().getTime() + "");
        RedisClient.hset(ACTIVITY + activity.getId(), ActivityKey.FIELD_URL.key(), activity.getUrl());
    }

    /**
     * 缓存客户活动基本信息
     *
     * @param customerActivityRes
     */
    public void cacheCustomerActivity(CustomerActivityRes customerActivityRes) {
        String basicKey = CustomerActivityKey.KEY.key() + customerActivityRes.getCustomerId() + "_" + customerActivityRes.getActivityId();
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_URL.key(), StringUtil.isBlank(customerActivityRes.getUrl()) ? "" : customerActivityRes.getUrl());
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_ID.key(), customerActivityRes.getId().toString());
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_ACTIVITY_ID.key(), customerActivityRes.getActivityId().toString());
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_CUSTOMER_ID.key(), customerActivityRes.getCustomerId().toString());
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_CREATE_TIME.key(), customerActivityRes.getCreateTime().getTime() + "");
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_START_TIME.key(), customerActivityRes.getStartTime().getTime() + "");
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_END_TIME.key(), customerActivityRes.getEndTime().getTime() + "");
        RedisClient.hset(basicKey, CustomerActivityKey.FIELD_STATE.key(), customerActivityRes.getState().toString());
    }

    /**
     * 缓存客户活动自定义评分属性
     *
     * @param record
     * @param res
     */
    public void cacheScoreAttr(CustomerActivityRes record, List<CustomerActivityScoreAttrRes> res) {
        // 自定义游戏结果属性
        String attrKey = SCORE_ATTR + record.getCustomerId() + "_" + record.getActivityId();
        // 排序属性
        String orderKey = SCORE_ATTR_ORDER + record.getCustomerId() + "_" + record.getActivityId();
        for (CustomerActivityScoreAttrRes soreAttr : res) {
            RedisClient.hset(attrKey, soreAttr.getAttrName(), soreAttr.getAttrValue());
            if (StringUtil.isNotBlank(soreAttr.getSort()))
                RedisClient.hset(orderKey, soreAttr.getAttrValue(), soreAttr.getSort());
        }
    }

    /**
     * 缓存客户活动自定义基本属性
     *
     * @param record
     * @param res
     */
    public void cacheCustomerActivityAttr(CustomerActivityRes record, List<CustomerActivityAttrRes> res) {
        // 自定义基本属性
        String attrKey = GENERATOR_ATTR + record.getCustomerId() + "_" + record.getActivityId();
        for (CustomerActivityAttrRes attrRes : res)
            RedisClient.hset(attrKey, attrRes.getAttrName(), attrRes.getAttrValue());
    }

    /**
     * 缓存单个自定义基本属性
     *
     * @param res
     * @throws Exception
     */
    public void cacheGeneratorAttrField(CustomerActivityAttrRes res) throws Exception {
//        CustomerActivityRes record = customerActivityService.findOne(res.getCustomerActivityId());
//        if (record == null || record.getId() == null)
//            return;
//        String attrKey = GENERATOR_ATTR + record.getCustomerId() + "_" + record.getActivityId();
//        RedisClient.hset(attrKey, res.getAttrName(), res.getAttrValue());
    }

    /**
     * 缓存多个自定义评分属性
     *
     * @param res
     * @throws Exception
     */
    public void cacheScoreAttrField(CustomerActivityScoreAttrRes res) throws Exception {
//        CustomerActivityRes record = customerActivityService.findOne(res.getCustomerActivityId());
//        if (record == null || record.getId() == null)
//            return;
//        String attrKey = GENERATOR_ATTR + record.getCustomerId() + "_" + record.getActivityId();
//        String orderKey = SCORE_ATTR_ORDER + record.getCustomerId() + "_" + record.getActivityId();
//        RedisClient.hset(attrKey, res.getAttrName(), res.getAttrValue());
//        if (StringUtil.isNotBlank(res.getSort()))
//            RedisClient.hset(orderKey, res.getAttrValue(), res.getSort());
    }

    /**
     * 删除活动缓存
     *
     * @param customerId
     */
    public void delCacheActivity(long customerId) {
        RedisClient.del(ACTIVITY + customerId);
    }

    /**
     * 删除客户缓存
     *
     * @param activityId
     */
    public void delCacheCustomer(long activityId) {
        RedisClient.del(CUSTOMER + activityId);
    }

    /**
     * 删除客户活动缓存信息
     *
     * @param record
     */
    public void delCustomerActivity(CustomerActivityRes record) {
        String basicKey = CustomerActivityKey.KEY.key() + record.getCustomerId() + "_" + record.getActivityId();
        RedisClient.del(basicKey);
        String attrKey = SCORE_ATTR + record.getCustomerId() + "_" + record.getActivityId();
        String orderKey = SCORE_ATTR_ORDER + record.getCustomerId() + "_" + record.getActivityId();
        String generatorAttrKey = GENERATOR_ATTR + record.getCustomerId() + "_" + record.getActivityId();
        RedisClient.del(attrKey);
        RedisClient.del(orderKey);
        RedisClient.del(generatorAttrKey);
    }

    /**
     * 删除单个自定义基本字段
     *
     * @param res
     * @throws Exception
     */
    public void delCacheGeneratorAttrField(CustomerActivityAttrRes res) throws Exception {
//        CustomerActivityRes record = customerActivityService.findOne(res.getCustomerActivityId());
//        if (record == null || record.getId() == null)
//            return;
//        String attrKey = GENERATOR_ATTR + record.getCustomerId() + "_" + record.getActivityId();
//        RedisClient.hdel(attrKey, res.getAttrName());
    }

    /**
     * 删除单个自定义评分字段
     *
     * @param res
     * @throws Exception
     */
    public void delCacheScoreAttrField(CustomerActivityScoreAttrRes res) throws Exception {
//        CustomerActivityRes record = customerActivityService.findOne(res.getCustomerActivityId());
//        if (record == null || record.getId() == null)
//            return;
//        String attrKey = GENERATOR_ATTR + record.getCustomerId() + "_" + record.getActivityId();
//        String orderKey = SCORE_ATTR_ORDER + record.getCustomerId() + "_" + record.getActivityId();
//        RedisClient.hdel(attrKey, res.getAttrName());
//        if (StringUtil.isNotBlank(res.getSort()))
//            RedisClient.hdel(orderKey, res.getAttrValue());
    }

}
