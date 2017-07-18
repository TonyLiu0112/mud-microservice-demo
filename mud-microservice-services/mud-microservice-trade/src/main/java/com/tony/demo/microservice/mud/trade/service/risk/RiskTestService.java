package com.tony.demo.microservice.mud.trade.service.risk;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioRiskTestResultDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioRiskTestMappingDOMapper;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioRiskTestResultDOMapper;
import com.tony.demo.microservice.mud.trade.dao.mapper.RiskTestMapper;
import com.tony.demo.microservice.mud.trade.exception.BusinessException;
import com.tony.demo.microservice.mud.trade.exception.UnEvaluatingException;
import com.tony.demo.microservice.mud.trade.service.trade.FundPortfolioService;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioMasterResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RiskTradeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 风险测评服务
 * <p>
 * Created by zhangxiaomei on 2017/6/22.
 */
@Service
public class RiskTestService {

    private Logger logger = LoggerFactory.getLogger(RiskTestService.class);

    private final RiskTestMapper riskTestMapper;
    private final RaFundPortfolioRiskTestResultDOMapper raFundPortfolioRiskTestResultDOMapper;
    private final FundPortfolioService fundPortfolioService;
    private final RaFundPortfolioRiskTestMappingDOMapper raFundPortfolioRiskTestMappingDOMapper;

    @Autowired
    public RiskTestService(RiskTestMapper riskTestMapper, RaFundPortfolioRiskTestResultDOMapper raFundPortfolioRiskTestResultDOMapper, FundPortfolioService fundPortfolioService, RaFundPortfolioRiskTestMappingDOMapper raFundPortfolioRiskTestMappingDOMapper) {
        this.riskTestMapper = riskTestMapper;
        this.raFundPortfolioRiskTestResultDOMapper = raFundPortfolioRiskTestResultDOMapper;
        this.fundPortfolioService = fundPortfolioService;
        this.raFundPortfolioRiskTestMappingDOMapper = raFundPortfolioRiskTestMappingDOMapper;
    }

    /**
     * 查询所有问题列表
     *
     * @return 问题列表集合
     * @throws BusinessException
     */
    public List<Map> findRiskQuestions() throws BusinessException {
        try {
            return riskTestMapper.selectAllTestQuestions();
        } catch (Exception e) {
            logger.error("selectAllTestQuestions error:查询风险测评题目异常{}", e);
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
            logger.error("queryRiskTestResult error:判断当前用户之前是否已风险测评异常{}", e);
            throw new BusinessException("queryRiskTestResult error:判断当前用户之前是否已风险测评异常", e);
        }
    }

    /**
     * 提交风险测评结果
     *
     * @param userId           用户id
     * @param score            测评得分
     * @param investmentPeriod 测评期限
     * @return count 成功的条数
     * @throws BusinessException
     */
    public int submitRiskTestResults(int userId, int score, int investmentPeriod) throws BusinessException {
        try {
            int count = 0;
            //根据风险测评的得分查询风险等级
            int riskPrefer = raFundPortfolioRiskTestMappingDOMapper.getRiskPrefer(score);
            RaFundPortfolioRiskTestResultDO riskTestResult = new RaFundPortfolioRiskTestResultDO();
            riskTestResult.setUserId(userId);
            riskTestResult.setScore(score);
            riskTestResult.setInvestmentPeriod((byte) investmentPeriod);
            riskTestResult.setRiskPrefer((byte) riskPrefer);
            //给用户自定义的风险等级赋值 -1
            riskTestResult.setCustomRiskPrefer((byte) -1);
            //判断是否存在userId的测评结果,如果存在，则update记录，否则insert记录
            RaFundPortfolioRiskTestResultDO raFundPortfolioRiskTestResultDO = raFundPortfolioRiskTestResultDOMapper.queryRiskTestResult(userId);
            if (raFundPortfolioRiskTestResultDO == null) {
                //生成流水号
                int MaxId = raFundPortfolioRiskTestResultDOMapper.queryMaxId();
                riskTestResult.setId(MaxId);
                count = raFundPortfolioRiskTestResultDOMapper.insert(riskTestResult);
            } else {
                riskTestResult.setId(raFundPortfolioRiskTestResultDO.getId());
                count = raFundPortfolioRiskTestResultDOMapper.updateByPrimaryKeySelective(riskTestResult);
            }
            return count;
        } catch (Exception e) {
            logger.error("submitRiskTestResults error:提交用户风险测评结果异常{}", e);
            throw new BusinessException("submitRiskTestResults error:提交用户风险测评结果异常", e);
        }
    }

    /**
     * 用户是否属于风险申购交易
     * 如果申购的组合的所属风险大于用户评测结果系统评定的风险，则存在风险
     *
     * @param userId 用户id
     * @return true: 风险交易 false: 非风险交易
     * @throws Exception
     */
    public RiskTradeResponse isRiskTrade(int userId, int portfolioId) throws Exception {
        RaFundPortfolioRiskTestResultDO record = raFundPortfolioRiskTestResultDOMapper.queryRiskTestResult(userId);
        if (record == null) {
            logger.warn("未找到用户评测结果");
            throw new UnEvaluatingException();
        }
        FundPortfolioMasterResponse portfolioResponse = fundPortfolioService.findByPortfolioId(portfolioId);
        return new RiskTradeResponse(portfolioResponse.getRiskPrefer() > record.getRiskPrefer());
    }

    /**
     * 根据用户id获取用户风险评测结果
     *
     * @param userId
     * @return 用户风险评测结果集合
     */
    public RaFundPortfolioRiskTestResultDO selectByUserId(Integer userId) {
        RaFundPortfolioRiskTestResultDO rfprtr = raFundPortfolioRiskTestResultDOMapper.queryRiskTestResult(userId);
        return rfprtr;
    }

    /**
     * 修改风险类型和投资期限
     *
     * @param rfprr
     * @return
     */
    public boolean updateByEntity(RaFundPortfolioRiskTestResultDO rfprr) {
        int rs = raFundPortfolioRiskTestResultDOMapper.updateByPrimaryKeySelective(rfprr);
        boolean flag = Boolean.FALSE;
        if (rs > 0) {
            flag = Boolean.TRUE;
        }
        return flag;
    }

}
