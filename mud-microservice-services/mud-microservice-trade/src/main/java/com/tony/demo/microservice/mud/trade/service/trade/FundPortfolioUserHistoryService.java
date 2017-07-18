package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.trade.service.bean.req.RaFundPortfolioUserHistoryRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.PortfolioIncomeResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserHistoryDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioUserHistoryDOMapper;
import com.tony.demo.microservice.mud.trade.dao.repository.PortfolioIncomeRO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户组合历史记录表
 * 组合收益
 * <p>
 * Created by Tony on 29/06/2017.
 */
@Service
public class FundPortfolioUserHistoryService {

    @Autowired
    private RaFundPortfolioUserHistoryDOMapper historyDOMapper;

    @Transactional
    public int save(RaFundPortfolioUserHistoryRequest request) throws Exception {
        if (request == null)
            return 0;
        RaFundPortfolioUserHistoryDO record = new RaFundPortfolioUserHistoryDO();
        BeanUtils.copyProperties(request, record);
        return historyDOMapper.insert(record);
    }

    public List<PortfolioIncomeResponse> findPortfolioIncomes(Integer portfolioId, Integer userId) throws InstantiationException, IllegalAccessException {
        List<PortfolioIncomeRO> records = historyDOMapper.findByPortfolioIdUserId(portfolioId, userId);
        if (records == null || records.size() == 0)
            return new ArrayList<>();
        return ConvertUtils.convert(records, PortfolioIncomeResponse.class);
    }
}
