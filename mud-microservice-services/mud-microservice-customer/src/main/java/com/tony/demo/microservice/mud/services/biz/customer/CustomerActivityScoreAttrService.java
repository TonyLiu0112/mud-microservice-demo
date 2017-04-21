package com.tony.demo.microservice.mud.services.biz.customer;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.dao.entity.CustomerActivityScoreAttrDO;
import com.tony.demo.microservice.mud.dao.repository.CustomerActivityScoreAttrRepository;
import com.tony.demo.microservice.mud.services.biz.CacheService;
import com.tony.demo.microservice.mud.services.model.req.CustomerActivityScoreAttrReq;
import com.tony.demo.microservice.mud.services.model.res.CustomerActivityScoreAttrRes;
import com.tony.demo.microservice.mud.utils.BeanUtilsPlus;
import com.tony.demo.microservice.mud.utils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 客户活动得分属性服务
 * <p>
 * Created by Tony on 16/02/2017.
 */
@Service
public class CustomerActivityScoreAttrService {

    private final CustomerActivityScoreAttrRepository scoreAttrRepository;
    private final CacheService cacheService;

    @Autowired
    public CustomerActivityScoreAttrService(CustomerActivityScoreAttrRepository scoreAttrRepository, CacheService cacheService) {
        this.scoreAttrRepository = scoreAttrRepository;
        this.cacheService = cacheService;
    }

    public PageInfo<CustomerActivityScoreAttrRes> findAll(long customerActivityId, int page, int size) throws Exception {
        Page<CustomerActivityScoreAttrDO> list = scoreAttrRepository.findByDfAndCustomerActivityId(0, customerActivityId, new PageRequest(page - 1, size));
        Page<CustomerActivityScoreAttrRes> pages = list.map(source -> {
            CustomerActivityScoreAttrRes attrRes = new CustomerActivityScoreAttrRes();
            BeanUtils.copyProperties(source, attrRes);
            return attrRes;
        });
        return new PageInfo<>(ConvertUtils.convert(pages));
    }

    public CustomerActivityScoreAttrRes findOne(long id) throws Exception {
        CustomerActivityScoreAttrRes res = new CustomerActivityScoreAttrRes();
        CustomerActivityScoreAttrDO scoreAttrDO = scoreAttrRepository.findOne(id);
        if (scoreAttrDO != null)
            BeanUtils.copyProperties(scoreAttrDO, res);
        return res;
    }

    @Transactional
    public CustomerActivityScoreAttrRes save(CustomerActivityScoreAttrReq req) throws Exception {
        req.setDf(0);
        CustomerActivityScoreAttrDO scoreAttrDO = new CustomerActivityScoreAttrDO();
        BeanUtils.copyProperties(req, scoreAttrDO);
        CustomerActivityScoreAttrDO saveDO = scoreAttrRepository.save(scoreAttrDO);
        CustomerActivityScoreAttrRes res = new CustomerActivityScoreAttrRes();
        BeanUtils.copyProperties(saveDO, res);
        cacheService.cacheScoreAttrField(res);
        return res;
    }

    @Transactional
    public CustomerActivityScoreAttrRes modify(CustomerActivityScoreAttrReq req) throws Exception {
        if (req == null || req.getId() == 0)
            return new CustomerActivityScoreAttrRes();
        CustomerActivityScoreAttrDO scoreAttrDO = scoreAttrRepository.findOne(req.getId());
        BeanUtils.copyProperties(req, scoreAttrDO, BeanUtilsPlus.ignoreProperties(req));
        CustomerActivityScoreAttrDO updateDO = scoreAttrRepository.save(scoreAttrDO);
        CustomerActivityScoreAttrRes res = new CustomerActivityScoreAttrRes();
        BeanUtils.copyProperties(updateDO, res);
        cacheService.cacheScoreAttrField(res);
        return res;
    }

    @Transactional
    public void remove(CustomerActivityScoreAttrReq req) throws Exception {
        if (req == null || req.getId() == 0)
            return;
        req.setDf(1);
        CustomerActivityScoreAttrRes res = modify(req);
        cacheService.delCacheScoreAttrField(res);
    }

    public List<CustomerActivityScoreAttrRes> findByCustomerActivityId(Long id) throws InstantiationException, IllegalAccessException {
        List<CustomerActivityScoreAttrDO> list = scoreAttrRepository.findByCustomerActivityIdAndDf(id, 0);
        return ConvertUtils.convert(list, CustomerActivityScoreAttrRes.class);
    }
}
