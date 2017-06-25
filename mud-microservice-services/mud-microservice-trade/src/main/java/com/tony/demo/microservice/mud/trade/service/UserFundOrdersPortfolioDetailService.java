package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.trade.service.bean.req.UserFundOrdersPortfolioDetailRequest;
import com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioDetailDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaUserFundOrdersPortfolioDetailDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组合基金订单服务
 * <p>
 * Created by Tony on 07/06/2017.
 */
@Service
public class UserFundOrdersPortfolioDetailService {

    private final RaUserFundOrdersPortfolioDetailDOMapper mapper;

    @Autowired
    public UserFundOrdersPortfolioDetailService(RaUserFundOrdersPortfolioDetailDOMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    public int save(UserFundOrdersPortfolioDetailRequest request) {
        if (request == null)
            return 0;
        RaUserFundOrdersPortfolioDetailDO entity = new RaUserFundOrdersPortfolioDetailDO();
        BeanUtils.copyProperties(request, entity);
        return mapper.insert(entity);
    }

}
