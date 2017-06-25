package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.trade.service.bean.req.FundPortfolioUserMasterRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserMasterResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundNetResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserMasterDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioUserMasterDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 用户组合服务
 * <p>
 * Created by Tony on 07/06/2017.
 */
@Service
public class FundPortfolioUserMasterService {

    private final RaFundPortfolioUserMasterDOMapper portfolioUserMasterDOMapper;
    private final FundNetService fundNetService;

    @Autowired
    public FundPortfolioUserMasterService(RaFundPortfolioUserMasterDOMapper portfolioUserMasterDOMapper, FundNetService fundNetService) {
        this.portfolioUserMasterDOMapper = portfolioUserMasterDOMapper;
        this.fundNetService = fundNetService;
    }

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
        Optional<RaFundNetResponse> latestByFundCode = fundNetService.findLatestByFundCode(fundCode);
        BigDecimal shareDecimal = new BigDecimal(applyShare);
        BigDecimal uptShare = record.getPortfolioShare().subtract(shareDecimal);
        record.setPortfolioShare(uptShare.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : uptShare);
        BigDecimal subNet = shareDecimal.multiply(latestByFundCode.get().getNet());
        record.setPortfolioMarketVal(record.getPortfolioMarketVal().subtract(subNet));
        return portfolioUserMasterDOMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 用户组合持仓数据查询
     *
     * @param userId      用户id
     * @param portfolioId 组合id
     * @return
     */
    public FundPortfolioUserMasterResponse findUserPosition(Integer userId, Integer portfolioId) {
        FundPortfolioUserMasterResponse response = new FundPortfolioUserMasterResponse();
        RaFundPortfolioUserMasterDO record = portfolioUserMasterDOMapper.selectByPortfolioIdAndUserId(portfolioId, userId);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }
}
