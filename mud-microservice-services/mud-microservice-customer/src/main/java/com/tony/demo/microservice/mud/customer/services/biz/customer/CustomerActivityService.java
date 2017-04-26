package com.tony.demo.microservice.mud.customer.services.biz.customer;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.common.utils.BeanUtilsPlus;
import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityDO;
import com.tony.demo.microservice.mud.customer.dao.repository.CustomerActivityRepository;
import com.tony.demo.microservice.mud.customer.services.biz.CacheService;
import com.tony.demo.microservice.mud.customer.services.model.req.CustomerActivityReq;
import com.tony.demo.microservice.mud.customer.services.model.res.CustomerActivityRes;
import com.tony.demo.microservice.mud.customer.services.model.res.CustomerRes;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户活动服务
 * <p>
 * Created by Tony on 15/02/2017.
 */
@Service
public class CustomerActivityService {

    private Logger logger = Logger.getLogger(CustomerActivityService.class);

    private final CustomerActivityRepository customerActivityRepository;
    private final CustomerService customerService;
    //    @Autowired
//    private ActivityService activityService;
    private final CacheService cacheService;

    @Autowired
    public CustomerActivityService(CustomerActivityRepository customerActivityRepository, CustomerService customerService, CacheService cacheService) {
        this.customerActivityRepository = customerActivityRepository;
        this.customerService = customerService;
        this.cacheService = cacheService;
    }

    public PageInfo<CustomerActivityRes> findAll(int pageNum, int pageSize) throws Exception {
        Page<CustomerActivityDO> page = customerActivityRepository.findByDf(0, new PageRequest(pageNum - 1, pageSize));
        Page<CustomerActivityRes> resPage = page.map(source -> {
            CustomerActivityRes res = new CustomerActivityRes();
            BeanUtils.copyProperties(source, res);
            try {
                res.setCustomer(customerService.findOne(res.getCustomerId()));
//                    res.setActivity(activityService.findOne(res.getActivityId()));
            } catch (Exception e) {
                logger.error("Copy page info error.", e);
                throw new RuntimeException(e);
            }
            return res;
        });
        return new PageInfo<>(ConvertUtils.convert(resPage));
    }

    public CustomerActivityRes findOne(long id) throws Exception {
        CustomerActivityDO customerActivityDO = customerActivityRepository.findOne(id);
        CustomerActivityRes res = new CustomerActivityRes();
        if (customerActivityDO != null) {
            BeanUtils.copyProperties(customerActivityDO, res);
            res.setCustomer(customerService.findOne(res.getCustomerId()));
//            res.setActivity(activityService.findOne(res.getActivityId()));
        }
        return res;
    }

    @Transactional
    public CustomerActivityRes save(CustomerActivityReq customerActivityReq) throws Exception {
        customerActivityReq.setDf(0);
        customerActivityReq.setState(1);
        customerActivityReq.setCreateTime(new Date());
        CustomerActivityDO customerActivityDO = new CustomerActivityDO();
        BeanUtils.copyProperties(customerActivityReq, customerActivityDO);
        CustomerActivityDO saveDO = customerActivityRepository.save(customerActivityDO);
        CustomerActivityRes res = new CustomerActivityRes();
        BeanUtils.copyProperties(saveDO, res);
        cacheService.cacheCustomerActivity(res);
        return res;
    }

    @Transactional
    public CustomerActivityRes modify(CustomerActivityReq customerActivityReq) throws Exception {
        if (customerActivityReq == null || customerActivityReq.getId() == 0)
            return new CustomerActivityRes();
        CustomerActivityDO customerActivityDO = customerActivityRepository.findOne(customerActivityReq.getId());
        BeanUtils.copyProperties(customerActivityReq, customerActivityDO, BeanUtilsPlus.ignoreProperties(customerActivityReq));
        CustomerActivityDO updateDo = customerActivityRepository.save(customerActivityDO);
        CustomerActivityRes res = new CustomerActivityRes();
        BeanUtils.copyProperties(updateDo, res);
        cacheService.cacheCustomerActivity(res);
        return res;
    }

    @Transactional
    public CustomerActivityRes remove(CustomerActivityReq customerActivityReq) throws Exception {
        if (customerActivityReq == null || customerActivityReq.getId() == 0)
            return new CustomerActivityRes();
        customerActivityReq.setDf(1);
        CustomerActivityRes customerActivityRes = modify(customerActivityReq);
        cacheService.delCustomerActivity(customerActivityRes);
        return customerActivityRes;
    }

    public List<CustomerActivityRes> list() throws InstantiationException, IllegalAccessException {
        List<CustomerActivityDO> list = customerActivityRepository.findByDf(0);
        return ConvertUtils.convert(list, CustomerActivityRes.class);
    }

    public List<CustomerRes> findByActivityId(long activityId) throws Exception {
        List<CustomerActivityDO> list = customerActivityRepository.findByActivityId(activityId);
        return list.stream().map(record -> {
            try {
                return customerService.findOne(record.getCustomerId());
            } catch (Exception e) {
                return new CustomerRes();
            }
        }).collect(Collectors.toList());
    }
}
