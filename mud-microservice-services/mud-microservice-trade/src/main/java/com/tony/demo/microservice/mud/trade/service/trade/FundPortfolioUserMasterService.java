package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundPortfolioUserMasterRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserMasterResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundRedemptionWarnResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundNetResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserMasterDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioUserMasterDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户组合服务
 * <p>
 * Created by Tony on 07/06/2017.
 */
@Service
public class FundPortfolioUserMasterService {

    /**
     * 一年时间戳
     */
    private final Integer timestampOfYear = 365 * 24 * 60 * 60;

    /**
     * 三年时间戳
     */
    private final Integer timestampOf3Year = 3 * 365 * 24 * 60 * 60;

    @Autowired
    private RaFundPortfolioUserMasterDOMapper portfolioUserMasterDOMapper;

    @Autowired
    private FundNetService fundNetService;

    /**
     * 存储用户组合信息
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public int save(FundPortfolioUserMasterRequest request) throws Exception {
        if (request == null)
            return 0;
        RaFundPortfolioUserMasterDO entity = new RaFundPortfolioUserMasterDO();
        BeanUtils.copyProperties(request, entity);
        return portfolioUserMasterDOMapper.insert(entity);
    }

    @Transactional
    public int modify(FundPortfolioUserMasterRequest request) throws Exception {
        if (request == null)
            return 0;
        RaFundPortfolioUserMasterDO entity = new RaFundPortfolioUserMasterDO();
        BeanUtils.copyProperties(request, entity);
        return portfolioUserMasterDOMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 修改用户组合的份额数、市值和总投入成本数
     *
     * @param applyShare  申请份额
     * @param portfolioId 组合id
     * @param userId      用户id
     * @return
     */
    @Transactional
    public int modifyShare4Redemption(String applyShare, Integer portfolioId, String fundCode, Integer userId) {
        RaFundPortfolioUserMasterDO record = portfolioUserMasterDOMapper.selectByPortfolioIdAndUserId(portfolioId, userId);
        if (record == null)
            return 0;
        RaFundNetResponse latestByFundCode = fundNetService.findLatestByFundCode(fundCode);
        BigDecimal shareDecimal = new BigDecimal(applyShare);
        BigDecimal uptShare = record.getPortfolioShare().subtract(shareDecimal);
        record.setPortfolioShare(uptShare.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : uptShare);
        BigDecimal crtMv = shareDecimal.multiply(latestByFundCode.getNet());
        record.setPortfolioMarketVal(record.getPortfolioMarketVal().subtract(crtMv));
        return portfolioUserMasterDOMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 用户组合持仓数据查询
     *
     * @param userId      用户id
     * @param portfolioId 组合id
     * @return
     */
    public FundPortfolioUserMasterResponse findUserPosition(Integer userId, Integer portfolioId) throws Exception {
        FundPortfolioUserMasterResponse response = new FundPortfolioUserMasterResponse();
        RaFundPortfolioUserMasterDO record = portfolioUserMasterDOMapper.selectByPortfolioIdAndUserId(portfolioId, userId);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }

    /**
     * 用户赎回操作 年限检查提示
     *
     * @param userId      用户ID
     * @param portfolioId 组合ID
     * @return 提示信息
     * @throws Exception
     */
    public FundRedemptionWarnResponse ageLimitCheck(int userId, int portfolioId) throws Exception {
        FundPortfolioUserMasterResponse userPosition = this.findUserPosition(userId, portfolioId);
        if (userPosition.getAipCycle() == 2) {
            if ((System.currentTimeMillis() / 1000) - userPosition.getAddTime() < timestampOfYear)
                return new FundRedemptionWarnResponse(true, userPosition.getAipCycle());
        }
        if (userPosition.getAipCycle() == 3) {
            if ((System.currentTimeMillis() / 1000) - userPosition.getAddTime() < timestampOf3Year)
                return new FundRedemptionWarnResponse(true, userPosition.getAipCycle());
        }
        return new FundRedemptionWarnResponse(false, userPosition.getAipCycle());
    }

    public List<FundPortfolioUserMasterResponse> findByUserId(Integer userId) throws Exception {
        List<RaFundPortfolioUserMasterDO> records = portfolioUserMasterDOMapper.selectByUserId(userId);
        if (records == null || records.size() == 0)
            return new ArrayList<>();
        return ConvertUtils.convert(records, FundPortfolioUserMasterResponse.class);
    }
}
