package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.trade.service.bean.req.OrderMasterRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.OrderMasterResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioMasterDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaUserFundOrdersPortfolioMasterDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单流水Master服务
 * <p>
 * Created by Tony on 11/07/2017.
 */
@Service
public class OrderMasterService {

    @Autowired
    private RaUserFundOrdersPortfolioMasterDOMapper mapper;

    @Transactional
    public int save(OrderMasterRequest orderMasterRequest) {
        if (orderMasterRequest == null)
            return 0;
        RaUserFundOrdersPortfolioMasterDO record = new RaUserFundOrdersPortfolioMasterDO();
        BeanUtils.copyProperties(orderMasterRequest, record);
        return mapper.insert(record);
    }

    public List<OrderMasterResponse> findByPortfolioId(Integer portfolioId, int userId) throws InstantiationException, IllegalAccessException {
        List<OrderMasterResponse> responses = new ArrayList<>();
        List<RaUserFundOrdersPortfolioMasterDO> records = mapper.selectByPortfolioId(portfolioId, userId);
        if (records == null || records.size() == 0)
            return responses;
        return ConvertUtils.convert(records, OrderMasterResponse.class);
    }
}
