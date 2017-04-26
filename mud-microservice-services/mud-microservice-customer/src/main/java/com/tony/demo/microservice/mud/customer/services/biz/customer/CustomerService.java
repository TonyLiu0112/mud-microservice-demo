package com.tony.demo.microservice.mud.customer.services.biz.customer;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.common.utils.BeanUtilsPlus;
import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.customer.dao.entity.CustomerDO;
import com.tony.demo.microservice.mud.customer.dao.mapper.CustomerDOMapper;
import com.tony.demo.microservice.mud.customer.dao.repository.CustomerRepository;
import com.tony.demo.microservice.mud.customer.services.biz.CacheService;
import com.tony.demo.microservice.mud.customer.services.model.req.CustomerReq;
import com.tony.demo.microservice.mud.customer.services.model.res.CustomerRes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户服务
 * <p>
 * Created by Tony on 13/02/2017.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerDOMapper customerDOMapper;

    private final CacheService cacheService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerDOMapper customerDOMapper, CacheService cacheService) {
        this.customerRepository = customerRepository;
        this.customerDOMapper = customerDOMapper;
        this.cacheService = cacheService;
    }

    public CustomerRes findOne(long id) throws Exception {
        CustomerDO customerDO = customerRepository.findOne(id);
        CustomerRes res = new CustomerRes();
        if (customerDO != null)
            BeanUtils.copyProperties(customerDO, res);
        return res;
    }

    public PageInfo findAll(int page, int size) {
        Page<CustomerDO> pageList = customerRepository.findByDf(0, new PageRequest(page - 1, size));
        return new PageInfo<>(ConvertUtils.convert(pageList));
    }

    @Transactional
    public CustomerRes modify(CustomerReq customerReq) throws Exception {
        CustomerDO customerDO = customerRepository.findOne(customerReq.getId());
        BeanUtils.copyProperties(customerReq, customerDO, BeanUtilsPlus.ignoreProperties(customerReq));
        CustomerDO updateDO = customerRepository.save(customerDO);
        CustomerRes res = new CustomerRes();
        BeanUtils.copyProperties(updateDO, res);
        cacheService.cacheCustomer(res);
        return res;
    }

    @Transactional
    public CustomerRes save(CustomerReq customerReq) throws Exception {
        customerReq.setDf(0);
        customerReq.setCreateTime(new Date());
        CustomerDO customerDO = new CustomerDO();
        BeanUtils.copyProperties(customerReq, customerDO);
        CustomerDO saveDO = customerRepository.save(customerDO);
        CustomerRes res = new CustomerRes();
        BeanUtils.copyProperties(saveDO, res);
        cacheService.cacheCustomer(res);
        return res;
    }

    @Transactional
    public CustomerRes remove(CustomerReq customerReq) throws Exception {
        customerReq.setDf(1);
        CustomerRes customerRes = modify(customerReq);
        cacheService.delCacheCustomer(customerRes.getId());
        return customerRes;
    }

    /**
     * 获得所有客户列表
     *
     * @return
     * @throws Exception
     */
    public List<CustomerRes> list() throws Exception {
        List<CustomerDO> records = customerDOMapper.selectList();
        List<CustomerRes> responses = new ArrayList<>();
        for (CustomerDO record : records) {
            CustomerRes res = new CustomerRes();
            BeanUtils.copyProperties(record, res);
            responses.add(res);
        }
        return responses;
    }

    public List<CustomerRes> findAllNoPage() {
        List<CustomerDO> list = customerRepository.findByDf(0);
        List<CustomerRes> responses = new ArrayList<>();
        for (CustomerDO record : list) {
            CustomerRes res = new CustomerRes();
            BeanUtils.copyProperties(record, res);
            responses.add(res);
        }
        return responses;
    }
}
