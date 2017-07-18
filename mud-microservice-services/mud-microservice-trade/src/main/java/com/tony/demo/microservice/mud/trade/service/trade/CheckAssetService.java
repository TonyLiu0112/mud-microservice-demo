package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserMasterDO;
import com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioDetailDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioUserHistoryDOMapper;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioUserMasterDOMapper;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaUserFundOrdersPortfolioDetailDOMapper;
import com.tony.demo.microservice.mud.trade.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查看资产服务
 * <p>
 */
@Service
public class CheckAssetService {

    private static Logger log = LoggerFactory.getLogger(CheckAssetService.class);
    private final RaFundPortfolioUserHistoryDOMapper raFundPortfolioUserHistoryDOMapper;
    private final RaFundPortfolioUserMasterDOMapper raFundPortfolioUserMasterDOMapper;
    private final RaUserFundOrdersPortfolioDetailDOMapper raUserFundOrdersPortfolioDetailDOMapper;

    @Autowired
    public CheckAssetService(RaFundPortfolioUserHistoryDOMapper raFundPortfolioUserHistoryDOMapper, RaFundPortfolioUserMasterDOMapper raFundPortfolioUserMasterDOMapper, RaUserFundOrdersPortfolioDetailDOMapper raUserFundOrdersPortfolioDetailDOMapper) {
        this.raFundPortfolioUserHistoryDOMapper = raFundPortfolioUserHistoryDOMapper;
        this.raFundPortfolioUserMasterDOMapper = raFundPortfolioUserMasterDOMapper;
        this.raUserFundOrdersPortfolioDetailDOMapper = raUserFundOrdersPortfolioDetailDOMapper;
    }

    /**
     * 我的财富
     *
     * @param userId 用户id
     * @return Map<String, Object>
     * @throws BusinessException
     */
    public Map<String, Object> myTreasure(Integer userId) throws BusinessException {
        try {
            //用户昨日组合盈亏
            BigDecimal yesterdayProfitZH = BigDecimal.ZERO;
            //用户组合累计盈亏
            BigDecimal accumulatedProfitZH = BigDecimal.ZERO;
            //工薪宝累计盈亏
            BigDecimal accumulatedProfitGXB = BigDecimal.ZERO;
            //工薪宝昨日盈亏
            BigDecimal yesterdayProfitGXB = BigDecimal.ZERO;
            //工薪宝总资产
            BigDecimal totalPropertyGXB = BigDecimal.ZERO;
            //已持仓组合总资产
            BigDecimal totalPropertyPositionZH = BigDecimal.ZERO;
            //交易中组合总资产
            BigDecimal totalPropertyTradingZH = BigDecimal.ZERO;
            //组合总资产
            BigDecimal totalPropertyZH = BigDecimal.ZERO;
            //返回我的财富的所有数据信息
            Map<String, Object> myTreasureMap = new HashMap<String, Object>();

            //获取用户组合昨日盈亏
            yesterdayProfitZH = queryYesterdayProfitZH(userId);
            //获取用户组合的累计盈亏
            accumulatedProfitZH = queryAccumulatedProfitZH(userId);
            //获取用户工薪宝的昨日盈亏，累计盈亏，总资产
            accumulatedProfitGXB = new BigDecimal("");
            yesterdayProfitGXB = new BigDecimal("");
            totalPropertyGXB = new BigDecimal("");
            //查询已持仓的组合资产详情
            //得到组合i的市值，组合名称
            List<RaFundPortfolioUserMasterDO> positionGroupFundListZH = getPositionGroupFundList(userId);
            //获取已持仓的组合的总资产
            for (int i = 0; i < positionGroupFundListZH.size(); i++) {
                totalPropertyPositionZH = totalPropertyPositionZH.add(positionGroupFundListZH.get(i).getPortfolioMarketVal());
            }
            //查询交易中的组合资产详情
            //获取组合id，组合名称，金额，份额，所属工作日，交易类型
            //根据同一个用户，同一个组合id，同一个所属工作日，同一个交易类型合并
            List<RaUserFundOrdersPortfolioDetailDO> tradingGroupFundListZH = getTradingGroupFundList(userId);
            //获取交易中的总资产
            for (int i = 0; i < tradingGroupFundListZH.size(); i++) {
                totalPropertyTradingZH = totalPropertyTradingZH.add(tradingGroupFundListZH.get(i).getAmount());
            }
            //组合中资产=已持仓的组合总资产+交易中的组合总资产
            totalPropertyZH = totalPropertyPositionZH.add(totalPropertyTradingZH);

            //用户昨日总盈亏=组合昨日盈亏+工薪宝昨日盈亏 yesterdayProfit
            myTreasureMap.put("yesterdayProfit", yesterdayProfitZH.add(yesterdayProfitGXB));
            //用户累计盈亏=组合累计盈亏+工薪宝累计盈亏 accumulatedProfit
            myTreasureMap.put("accumulatedProfit", accumulatedProfitZH.add(accumulatedProfitGXB));
            //总资产=组合总资产+工薪宝totalProperty
            myTreasureMap.put("totalProperty", totalPropertyZH.add(totalPropertyGXB));
            //组合总资产totalPropertyZH
            myTreasureMap.put("totalPropertyZH", totalPropertyZH);
            //工薪宝总资产totalPropertyGXB
            myTreasureMap.put("totalPropertyGXB", totalPropertyGXB);
            //已持仓的组合资产类positionGroupFundListZH
            myTreasureMap.put("positionGroupFundListZH", positionGroupFundListZH);
            //交易中的组合资产类tradingGroupFundListZH
            myTreasureMap.put("tradingGroupFundListZH", tradingGroupFundListZH);

            return myTreasureMap;
        } catch (Exception e) {
            log.error("myTreasure error:查看我的财富异常{}", e);
            throw new BusinessException("myTreasure error:查看我的财富异常", e);
        }
    }

    /**
     * 查询用户的组合昨日盈亏
     *
     * @return BigDecimal 用户的组合昨日盈亏
     * @throws BusinessException
     */
    public BigDecimal queryYesterdayProfitZH(Integer userId) throws BusinessException {
        try {
            //获取系统当前日期
            Date systemDate = new Date();
            SimpleDateFormat matter1 = new SimpleDateFormat("yyyyMMdd");
            String stringDate = matter1.format(systemDate);
            int recordeDate = Integer.parseInt(stringDate);
            return raFundPortfolioUserHistoryDOMapper.queryYesterdayProfit(userId, recordeDate);
        } catch (Exception e) {
            log.error("queryYesterdayProfitZH error:查询用户的组合昨日盈亏异常{}", e);
            throw new BusinessException("queryYesterdayProfitZH error:查询用户的组合昨日盈亏异常", e);
        }
    }

    /**
     * 查询用户的组合累计盈亏
     *
     * @return BigDecimal 用户的组合累计盈亏
     * @throws BusinessException
     */
    public BigDecimal queryAccumulatedProfitZH(Integer userId) throws BusinessException {
        try {
            return raFundPortfolioUserMasterDOMapper.queryAccumulatedProfit(userId);
        } catch (Exception e) {
            log.error("queryAccumulatedProfitZH error:查询用户的组合累计盈亏异常{}", e);
            throw new BusinessException("queryAccumulatedProfitZH error:查询用户的组合累计盈亏异常", e);
        }
    }

    /**
     * 查询工薪宝资产详情
     *
     * @return SubAccount 工薪宝类对象，获取工薪宝的昨日盈亏，累计盈亏，总资产
     * @throws BusinessException
     */
    public Object querySubAccountGXB(Integer userId) throws BusinessException {
        return null;
    }

    /**
     * 查询已持仓的组合资产
     *
     * @return List<RaFundPortfolioUserMasterDO> 已持仓组合资产对象集合
     * @throws BusinessException
     */
    public List<RaFundPortfolioUserMasterDO> getPositionGroupFundList(Integer userId) throws BusinessException {
        try {
            List<RaFundPortfolioUserMasterDO> raFundPortfolioUserMasterDO = raFundPortfolioUserMasterDOMapper.selectByUserId(userId);
            return raFundPortfolioUserMasterDO;
        } catch (Exception e) {
            log.error("getUserMasterFund error:查询已持仓的组合资产异常{}", e);
            throw new BusinessException("getUserMasterFund error:查询已持仓的组合资产异常", e);
        }
    }

    /**
     * 查询交易中的组合资产
     *
     * @return List<RaUserFundOrdersPortfolioDetailDO> 交易中组合资产对象集合
     * @throws BusinessException
     */
    public List<RaUserFundOrdersPortfolioDetailDO> getTradingGroupFundList(Integer userId) throws BusinessException {
        try {
            //获取组合id，组合名称，金额，份额，所属工作日，交易类型
            //根据同一个用户，同一个组合id，同一个所属工作日，同一个交易类型合并
            List<RaUserFundOrdersPortfolioDetailDO> raUserFundOrdersPortfolioDetailDO = raUserFundOrdersPortfolioDetailDOMapper.selectTradingGroupFundList(userId);
            return raUserFundOrdersPortfolioDetailDO;
        } catch (Exception e) {
            log.error("getUserMasterFund error:查询交易中的组合资产异常{}", e);
            throw new BusinessException("getUserMasterFund error:查询交易中的组合资产异常", e);
        }
    }

}
