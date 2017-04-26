package com.tony.demo.microservice.mud.customer.services.biz.customer;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.common.utils.BeanUtilsPlus;
import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityAttrDO;
import com.tony.demo.microservice.mud.customer.dao.repository.CustomerActivityAttrRepository;
import com.tony.demo.microservice.mud.customer.services.biz.CacheService;
import com.tony.demo.microservice.mud.customer.services.model.req.CustomerActivityAttrReq;
import com.tony.demo.microservice.mud.customer.services.model.res.CustomerActivityAttrRes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 客户活动属性管理
 * <p>
 * Created by Tony on 16/02/2017.
 */
@Service
public class CustomerActivityAttrService {

    private final CustomerActivityAttrRepository customerActivityAttrRepository;
    private final CacheService cacheService;

    @Autowired
    public CustomerActivityAttrService(CustomerActivityAttrRepository customerActivityAttrRepository, CacheService cacheService) {
        this.customerActivityAttrRepository = customerActivityAttrRepository;
        this.cacheService = cacheService;
    }

    public PageInfo<CustomerActivityAttrRes> findByCustomerActivityId(long customerActivityId, int page, int size) throws Exception {
        Page<CustomerActivityAttrDO> list = customerActivityAttrRepository.findByDfAndCustomerActivityId(0, customerActivityId, new PageRequest(page - 1, size));
        Page<CustomerActivityAttrRes> pages = list.map(source -> {
            CustomerActivityAttrRes attrRes = new CustomerActivityAttrRes();
            BeanUtils.copyProperties(source, attrRes);
            return attrRes;
        });
        return new PageInfo<>(ConvertUtils.convert(pages));
    }

    public CustomerActivityAttrRes findOne(long id) throws Exception {
        CustomerActivityAttrRes res = new CustomerActivityAttrRes();
        CustomerActivityAttrDO customerActivityAttrDO = customerActivityAttrRepository.findOne(id);
        if (customerActivityAttrDO != null)
            BeanUtils.copyProperties(customerActivityAttrDO, res);
        return res;
    }

    @Transactional
    public CustomerActivityAttrRes save(CustomerActivityAttrReq req) throws Exception {
        req.setDf(0);
        CustomerActivityAttrDO customerActivityAttrDO = new CustomerActivityAttrDO();
        BeanUtils.copyProperties(req, customerActivityAttrDO);
        CustomerActivityAttrDO saveDO = customerActivityAttrRepository.save(customerActivityAttrDO);
        CustomerActivityAttrRes res = new CustomerActivityAttrRes();
        BeanUtils.copyProperties(saveDO, res);
        cacheService.cacheGeneratorAttrField(res);
        return res;
    }

    @Transactional
    public CustomerActivityAttrRes modify(CustomerActivityAttrReq req) throws Exception {
        if (req == null || req.getId() == 0)
            return new CustomerActivityAttrRes();
        CustomerActivityAttrDO customerActivityAttrDO = customerActivityAttrRepository.findOne(req.getId());
        BeanUtils.copyProperties(req, customerActivityAttrDO, BeanUtilsPlus.ignoreProperties(req));
        CustomerActivityAttrDO updateDO = customerActivityAttrRepository.save(customerActivityAttrDO);
        CustomerActivityAttrRes res = new CustomerActivityAttrRes();
        BeanUtils.copyProperties(updateDO, res);
        cacheService.cacheGeneratorAttrField(res);
        return res;
    }

    @Transactional
    public CustomerActivityAttrRes remove(CustomerActivityAttrReq req) throws Exception {
        if (req == null || req.getId() == 0)
            return new CustomerActivityAttrRes();
        req.setDf(1);
        CustomerActivityAttrRes res = modify(req);
        cacheService.delCacheGeneratorAttrField(res);
        return res;
    }

    public List<CustomerActivityAttrRes> findByCustomerActivityId(Long id) throws Exception {
        return ConvertUtils.convert(customerActivityAttrRepository.findByCustomerActivityIdAndDf(id, 0), CustomerActivityAttrRes.class);
    }
}
