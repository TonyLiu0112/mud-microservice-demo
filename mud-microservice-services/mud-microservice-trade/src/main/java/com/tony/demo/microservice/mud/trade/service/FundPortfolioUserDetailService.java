package com.tony.demo.microservice.mud.trade.service;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioUserDetailDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioUserDetailDOMapper;
import com.tony.demo.microservice.mud.trade.service.bean.req.FundPortfolioUserDetailRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioUserDetailResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundNetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 用户组合明细服务
 * <p>
 * Created by Tony on 07/06/2017.
 */
@Service
public class FundPortfolioUserDetailService {

    @Autowired
    private RaFundPortfolioUserDetailDOMapper fundPortfolioUserDetailDOMapper;

    @Autowired
    private FundNetService fundNetService;

    @Transactional
    public int save(FundPortfolioUserDetailRequest request) throws Exception {
        if (request == null)
            return 0;
        RaFundPortfolioUserDetailDO entity = new RaFundPortfolioUserDetailDO();
        BeanUtils.copyProperties(request, entity);
        return fundPortfolioUserDetailDOMapper.insert(entity);
    }

    /**
     * 根据组合id、基金代码、用户id
     * 更新赎回的份额、市值数据
     *
     * @param share       份额
     * @param portfolioId 组合id
     * @param fundCode    基金代码
     * @param userId      用户id
     * @return
     */
    @Transactional
    public int modifyShare4Redemption(String share, Integer portfolioId, String fundCode, Integer userId) {
        RaFundPortfolioUserDetailDO record = fundPortfolioUserDetailDOMapper.selectUserDetail(portfolioId, fundCode, userId);
        if (record == null)
            return 0;
        Optional<RaFundNetResponse> latestByFundCode = fundNetService.findLatestByFundCode(fundCode);
        BigDecimal shareDecimal = new BigDecimal(share);
        BigDecimal uptShare = record.getShare().subtract(shareDecimal);
        record.setShare(uptShare.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : uptShare);
        record.setMarketVal(uptShare.multiply(latestByFundCode.get().getNet()));
        return fundPortfolioUserDetailDOMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 查询用户组合明细
     * 不考虑份额情况
     *
     * @param userId      用户ID
     * @param portfolioId 组合ID
     * @return
     * @throws Exception
     */
    public List<FundPortfolioUserDetailResponse> findByUserIdAndPortfolioId(Integer userId, Integer portfolioId) throws Exception {
        List<RaFundPortfolioUserDetailDO> records = fundPortfolioUserDetailDOMapper.selectByUserIdAndPortfolioId(userId, portfolioId);
        if (records == null)
            return new ArrayList<>();
        return ConvertUtils.convert(records, FundPortfolioUserDetailResponse.class);
    }

    /**
     * 查询用户组合明细
     * 用户份额不能为0
     *
     * @param userId      用户ID
     * @param portfolioId 组合ID
     * @return
     * @throws Exception
     */
    public List<FundPortfolioUserDetailResponse> findUsableUserDetails(Integer userId, Integer portfolioId) throws Exception {
        List<RaFundPortfolioUserDetailDO> records = fundPortfolioUserDetailDOMapper.selectUsableUserDetails(userId, portfolioId);
        if (records == null)
            return new ArrayList<>();
        return ConvertUtils.convert(records, FundPortfolioUserDetailResponse.class);
    }

    /**
     * 获取用户组合明细历史
     *
     * @param userId      用户id
     * @param portfolioId 组合id
     * @param fundCode    基金代码
     * @return
     * @throws Exception
     */
    public FundPortfolioUserDetailResponse findUserHistoryDetail(Integer userId, Integer portfolioId, String fundCode) throws Exception {
        FundPortfolioUserDetailResponse response = new FundPortfolioUserDetailResponse();
        RaFundPortfolioUserDetailDO record = fundPortfolioUserDetailDOMapper.selectUserDetail(portfolioId, fundCode, userId);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }

    /**
     * 根据主键id
     * 修改已提供的数据集
     *
     * @param modifyReq 用户基金交易请求
     * @return
     * @throws Exception
     */
    @Transactional
    public int modifyShareByPK(FundPortfolioUserDetailRequest modifyReq) throws Exception {
        if (modifyReq == null)
            return 0;
        RaFundPortfolioUserDetailDO record = new RaFundPortfolioUserDetailDO();
        BeanUtils.copyProperties(modifyReq, record);
        return fundPortfolioUserDetailDOMapper.updateByPrimaryKeySelective(record);
    }

}