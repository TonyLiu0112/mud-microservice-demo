package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioMasterResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioMasterDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioMasterDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 基金组合模板服务
 * <p>
 * Created by Tony on 07/06/2017.
 */
@Service
public class FundPortfolioService {

    private final RaFundPortfolioMasterDOMapper fundPortfolioMasterDOMapper;

    @Autowired
    public FundPortfolioService(RaFundPortfolioMasterDOMapper fundPortfolioMasterDOMapper) {
        this.fundPortfolioMasterDOMapper = fundPortfolioMasterDOMapper;
    }

    /**
     * 查询组合模板明细
     * @param portfolioId
     * @return
     * @throws Exception
     */
    public Optional<FundPortfolioMasterResponse> findByPortfolioId(Integer portfolioId) throws Exception {
        FundPortfolioMasterResponse response = new FundPortfolioMasterResponse();
        RaFundPortfolioMasterDO record = fundPortfolioMasterDOMapper.selectByPrimaryKey(portfolioId);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return Optional.of(response);
    }

}
