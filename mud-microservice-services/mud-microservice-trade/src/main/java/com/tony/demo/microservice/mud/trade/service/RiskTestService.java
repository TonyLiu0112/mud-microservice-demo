package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioRiskTestResultDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioRiskTestResultDOMapper;
import com.tony.demo.microservice.mud.trade.dao.mapper.RiskTestMapper;
import com.tony.demo.microservice.mud.trade.exception.BusinessException;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioMasterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 风险测评服务
 * <p>
 * Created by zhangxiaomei on 2017/6/22.
 */
@Service
public class RiskTestService {

    private Logger log = LoggerFactory.getLogger(RiskTestService.class);

    private final RiskTestMapper riskTestMapper;

    private final RaFundPortfolioRiskTestResultDOMapper raFundPortfolioRiskTestResultDOMapper;

    private final FundPortfolioService fundPortfolioService;

    @Autowired
    public RiskTestService(RiskTestMapper riskTestMapper, RaFundPortfolioRiskTestResultDOMapper raFundPortfolioRiskTestResultDOMapper, FundPortfolioService fundPortfolioService) {
        this.riskTestMapper = riskTestMapper;
        this.raFundPortfolioRiskTestResultDOMapper = raFundPortfolioRiskTestResultDOMapper;
        this.fundPortfolioService = fundPortfolioService;
    }


    /**
     * 查询所有问题列表
     *
     * @return 问题列表集合
     * @throws BusinessException
     */
    public List<Map> findQuestions() throws BusinessException {
        try {
            return riskTestMapper.selectAllTestQuestions();
        } catch (Exception e) {
            log.error("selectAllTestQuestions error:查询风险测评题目异常{}", e);
            throw new BusinessException("selectAllTestQuestions error:查询风险测评题目异常", e);
        }
    }

    /**
     * 用户是否已测评
     *
     * @param userId 用户id
     * @return true: 已测评 false: 未测评
     * @throws BusinessException
     */
    public boolean judgeRiskTest(int userId) throws BusinessException {
        try {
            return raFundPortfolioRiskTestResultDOMapper.queryRiskTestResult(userId) != null;
        } catch (Exception e) {
            log.error("queryRiskTestResult error:判断当前用户之前是否风险测评过异常{}", e);
            throw new BusinessException("queryRiskTestResult error:判断当前用户之前是否风险测评过异常", e);
        }
    }

    /**
     * 用户是否属于风险交易申购
     *
     * @param userId 用户id
     * @return true: 风险交易 false: 非风险交易
     * @throws Exception
     */
    public boolean isRiskTrade(int userId, int portfolioId) throws Exception {
        RaFundPortfolioRiskTestResultDO record = raFundPortfolioRiskTestResultDOMapper.queryRiskTestResult(userId);
        Optional<FundPortfolioMasterResponse> masterOptional = fundPortfolioService.findByPortfolioId(portfolioId);
        if (masterOptional.isPresent())
            throw new BusinessException();
        return !(record == null || record.getCustomRiskPrefer() == null)
                && masterOptional.get().getRiskPrefer() > record.getRiskPrefer();
    }

}
