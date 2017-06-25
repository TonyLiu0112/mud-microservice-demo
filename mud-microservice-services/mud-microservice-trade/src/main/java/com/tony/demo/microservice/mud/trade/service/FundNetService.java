package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundNetResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundNetDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundNetDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基金净值服务
 * <p>
 * Created by Tony on 21/06/2017.
 */
@Service
public class FundNetService {

    @Autowired
    private RaFundNetDOMapper raFundNetDOMapper;

    /**
     * 根据基金代码获取最新净值
     *
     * @return
     */
    RaFundNetResponse findLatestByFundCode(String fundCode) {
        RaFundNetResponse response = new RaFundNetResponse();
        RaFundNetDO record = raFundNetDOMapper.selectLatestByFundCode(fundCode);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }

}
