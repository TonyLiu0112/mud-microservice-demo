package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.trade.dao.entity.*;
import com.tony.demo.microservice.mud.trade.dao.mapper.*;
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
 * 查看组合持仓[赎回或申购]
 * Created by Yuxin.Tian on 2017-06-28.
 */
@Service
public class ViewPortfolioPositionsService {

    private static Logger log = LoggerFactory.getLogger(ViewPortfolioPositionsService.class);

    private final RaFundPortfolioUserDetailDOMapper raFundPortfolioUserDetailDOMapper;
    private final RaFundPortfolioUserHistoryDOMapper raFundPortfolioUserHistoryDOMapper;
    private final RaFundPortfolioUserMasterDOMapper raFundPortfolioUserMasterDOMapper;
    private final RaFundPortfolioRiskMatrixDOMapper raFundPortfolioRiskMatrixDOMapper;
    private final RaUserFundOrdersPortfolioDetailDOMapper raUserFundOrdersPortfolioDetailDOMapper;

    /**
     * 业务类型
     **/
    public final String BUS_TYPE_CONFIRMATION = "1";//[申购]确认中
    public final String BUS_TYPE_REDEMPTION = "2";//赎回中

    @Autowired
    public ViewPortfolioPositionsService(RaFundPortfolioUserDetailDOMapper raFundPortfolioUserDetailDOMapper, RaFundPortfolioUserHistoryDOMapper raFundPortfolioUserHistoryDOMapper, RaFundPortfolioUserMasterDOMapper raFundPortfolioUserMasterDOMapper, RaFundPortfolioRiskMatrixDOMapper raFundPortfolioRiskMatrixDOMapper, RaUserFundOrdersPortfolioDetailDOMapper raUserFundOrdersPortfolioDetailDOMapper) {
        this.raFundPortfolioUserDetailDOMapper = raFundPortfolioUserDetailDOMapper;
        this.raFundPortfolioUserHistoryDOMapper = raFundPortfolioUserHistoryDOMapper;
        this.raFundPortfolioUserMasterDOMapper = raFundPortfolioUserMasterDOMapper;
        this.raFundPortfolioRiskMatrixDOMapper = raFundPortfolioRiskMatrixDOMapper;
        this.raUserFundOrdersPortfolioDetailDOMapper = raUserFundOrdersPortfolioDetailDOMapper;
    }


    /**
     * 赎回中
     * 根据组合id和userid获取总资产
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return 用户某个组合的总资产
     */
    public BigDecimal getRedemptionTotalAssetsByPortfolioIdAndUserId(Integer portfolioId, Integer userId)
            throws BusinessException {
        BigDecimal totalAssets;
        try {
            RaFundPortfolioUserDetailDO rfpud = new RaFundPortfolioUserDetailDO();
            rfpud.setPortfolioId(portfolioId);
            rfpud.setUserId(userId);
            totalAssets = raFundPortfolioUserDetailDOMapper.getTotalAssetsByEntity(rfpud);
        } catch (Exception e) {
            log.error("getRedemptionTotalAssetsByPortfolioIdAndUserId error:获取总资产异常{}", e);
            throw new BusinessException("getRedemptionTotalAssetsByPortfolioIdAndUserId error:获取总资产异常", e);
        }
        return totalAssets;
    }

    /**
     * 赎回中
     * 获取昨日盈亏
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @param recordeDate 业务日期 [八位数字]
     * @return 用户某个组合昨日盈亏
     */
    public BigDecimal getRedemptionYesterdayProfitAndLoss(Integer portfolioId, Integer userId, Integer recordeDate)
            throws BusinessException {
        BigDecimal ypral = BigDecimal.ZERO;
        try {
            RaFundPortfolioUserHistoryDO rfpuh = new RaFundPortfolioUserHistoryDO();
            rfpuh.setPortfolioId(portfolioId);
            rfpuh.setUserId(userId);
            rfpuh.setRecordeDate(recordeDate);
            ypral = raFundPortfolioUserHistoryDOMapper.getYesterdayProfitAndLossByEntity(rfpuh);
        } catch (Exception e) {
            log.error("getRedemptionYesterdayProfitAndLoss error:获取昨日盈亏异常{}", e);
            throw new BusinessException("getRedemptionYesterdayProfitAndLoss error:获取昨日盈亏异常", e);
        }
        return ypral;
    }

    /**
     * 赎回中
     * 获取累积盈亏
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return 用户某个组合的累计盈亏
     */
    public BigDecimal getRedemptionTotalProfitAndLoss(Integer portfolioId, Integer userId)
            throws BusinessException {
        BigDecimal tpal = BigDecimal.ZERO;
        try {
            RaFundPortfolioUserMasterDO rfpum = new RaFundPortfolioUserMasterDO();
            rfpum.setUserId(userId);
            rfpum.setPortfolioId(portfolioId);
            tpal = raFundPortfolioUserMasterDOMapper.getTotalProfitAndLoss(rfpum);
        } catch (Exception e) {
            log.error("getRedemptionTotalProfitAndLoss error:获取累积盈亏异常{}", e);
            throw new BusinessException("getRedemptionTotalProfitAndLoss error:获取累积盈亏异常", e);
        }
        return tpal;
    }

    /**
     * 赎回中
     * 获取组合基金列表并按照sn升序排序
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return 用户某个组合的所有基金列表
     */
    public List getRedemptionFundList(Integer portfolioId, Integer userId) throws BusinessException {
        List<RaFundPortfolioUserDetailDO> list = null;
        try {
            RaFundPortfolioUserDetailDO rfpud = new RaFundPortfolioUserDetailDO();
            rfpud.setPortfolioId(portfolioId);
            rfpud.setUserId(userId);
            list = raFundPortfolioUserDetailDOMapper.getRedemptionFundListByOrderEntity(rfpud);
        } catch (Exception e) {
            log.error("getRedemptionFundList error:获取组合基金列表异常{}", e);
            throw new BusinessException("getRedemptionFundList error:获取组合基金列表异常", e);
        }
        return list;
    }

    /**
     * 赎回中
     * 获取底部描述文字[风险评级和投资期限]
     *
     * @param portfolioId 组合id
     * @return 获取购买组合时的风险评级和投资期限[对象]
     */
    public RaFundPortfolioRiskMatrixDO getRedemptionDescTip(Integer portfolioId) throws BusinessException {
        RaFundPortfolioRiskMatrixDO tmp = null;
        try {
            List<RaFundPortfolioRiskMatrixDO> list = raFundPortfolioRiskMatrixDOMapper.getFPRMListByPortfolioId(portfolioId);
            if (list == null || list.size() < 1) {
                return null;
            }
            tmp = list.get(0);
        } catch (Exception e) {
            log.error("getRedemptionDescTip error:获取风险评级和投资期限异常{}", e);
            throw new BusinessException("getRedemptionDescTip error:获取风险评级和投资期限异常", e);
        }
        return tmp;
    }

    /**
     * 根据日期获取昨日日期
     *
     * @param date 日期
     * @return 昨日日期 [八位数字]
     */
    public Integer getYesterdayDate(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Integer recordeDate = Integer.valueOf(sdf.format(calendar.getTime()));
        return recordeDate;
    }


    /**
     * 获取赎回中的页面数据
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return 赎回中的页面数据
     */
    public Map<String, Object> getRedemptionData(Integer portfolioId, Integer userId) throws BusinessException {
        Map<String, Object> map = new HashMap<String, Object>();
        //总资产
        map.put("TotalAssets", getRedemptionTotalAssetsByPortfolioIdAndUserId(portfolioId, userId));
        //昨日盈亏
        Integer recordeDate = getYesterdayDate(new Date());
        map.put("YesterdayProfitAndLoss", getRedemptionYesterdayProfitAndLoss(portfolioId, userId, recordeDate));
        //累计盈亏
        map.put("TotalProfitAndLoss", getRedemptionTotalProfitAndLoss(portfolioId, userId));
        //基金列表
        map.put("FundList", getRedemptionFundList(portfolioId, userId));
        //底部风险评级和投资期限
        map.put("PortfolioRisk", getRedemptionDescTip(portfolioId));
        return map;
    }


    /**
     * [申购]确认中
     * 获取申购[确认中]总资产
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return 用户某个组合的申购总资产
     */
    public BigDecimal getConfirmationTotalAssets(Integer portfolioId, Integer userId) throws BusinessException {
        BigDecimal totalAssets = BigDecimal.ZERO;
        try {
            RaUserFundOrdersPortfolioDetailDO rufpd = new RaUserFundOrdersPortfolioDetailDO();
            rufpd.setPortfolioId(portfolioId);
            rufpd.setUserId(userId);
            /** 交易类型 1 买入 2 赎回 3 充值 4提现 6分红[目前仅有买入（申购）和赎回] **/
            rufpd.setOrderType(Integer.valueOf(BUS_TYPE_CONFIRMATION));
            totalAssets = raUserFundOrdersPortfolioDetailDOMapper.getTotalAssetsByEntity(rufpd);
        } catch (Exception e) {
            log.error("getConfirmationTotalAssets error:获取申购[确认中]总资产异常{}", e);
            throw new BusinessException("getConfirmationTotalAssets error:获取申购[确认中]总资产异常", e);
        }
        return totalAssets;
    }

    /**
     * [申购]确认中
     * 获取申购的组合昨日盈亏[当前申购确认中的为0]
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return 0
     */
    private BigDecimal getConfirmationYesterdayProfitAndLoss(Integer portfolioId, Integer userId) {
        return BigDecimal.ZERO;
    }

    /**
     * [申购]确认中
     * 获取申购的组合累计盈亏[当前申购确认中的为0]
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return 0
     */
    private BigDecimal getConfirmationTotalProfitAndLoss(Integer portfolioId, Integer userId) {
        return BigDecimal.ZERO;
    }

    /**
     * [申购]确认中
     * 获取申购的组合中基金列表
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     */
    private List getConfirmationFundList(Integer portfolioId, Integer userId) throws BusinessException {
        List<RaFundPortfolioUserDetailDO> list = null;
        try {
            RaUserFundOrdersPortfolioDetailDO rufpd = new RaUserFundOrdersPortfolioDetailDO();
            rufpd.setPortfolioId(portfolioId);
            rufpd.setUserId(userId);
            /** 交易类型 1 买入 2 赎回 3 充值 4提现 6分红[目前仅有买入（申购）和赎回] **/
            rufpd.setOrderType(Integer.valueOf(BUS_TYPE_CONFIRMATION));
            list = raFundPortfolioUserDetailDOMapper.getConfirmationFundListByOrderEntity(rufpd);
        } catch (Exception e) {
            log.error("getConfirmationFundList error:获取申购的组合中基金列表异常{}", e);
            throw new BusinessException("getConfirmationFundList error:获取申购的组合中基金列表异常", e);
        }
        return list;
    }


    /**
     * 获取[申购]确认中的页面数据
     *
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return [申购]确认中的页面数据
     */
    public Map<String, Object> getConfirmationData(Integer portfolioId, Integer userId) throws BusinessException {
        Map<String, Object> map = new HashMap<String, Object>();
        //总资产
        map.put("TotalAssets", getConfirmationTotalAssets(portfolioId, userId));
        //昨日盈亏
        map.put("YesterdayProfitAndLoss", getConfirmationYesterdayProfitAndLoss(portfolioId, userId));
        //累计盈亏
        map.put("TotalProfitAndLoss", getConfirmationTotalProfitAndLoss(portfolioId, userId));
        //基金列表
        map.put("FundList", getConfirmationFundList(portfolioId, userId));
        return map;
    }


}
