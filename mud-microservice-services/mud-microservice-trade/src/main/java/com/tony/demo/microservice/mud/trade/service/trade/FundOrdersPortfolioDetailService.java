package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.trade.service.bean.req.UserFundOrdersPortfolioDetailRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.UserFundOrdersPortfolioDetailResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaUserFundOrdersPortfolioDetailDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaUserFundOrdersPortfolioDetailDOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合基金订单服务
 * <p>
 * Created by Tony on 07/06/2017.
 */
@Service
public class FundOrdersPortfolioDetailService {

    @Autowired
    private RaUserFundOrdersPortfolioDetailDOMapper mapper;

    @Transactional
    public int save(UserFundOrdersPortfolioDetailRequest request) throws Exception {
        if (request == null)
            return 0;
        RaUserFundOrdersPortfolioDetailDO entity = new RaUserFundOrdersPortfolioDetailDO();
        BeanUtils.copyProperties(request, entity);
        return mapper.insert(entity);
    }

    public UserFundOrdersPortfolioDetailResponse findByOrderId(String orderId) throws Exception {
        UserFundOrdersPortfolioDetailResponse response = new UserFundOrdersPortfolioDetailResponse();
        RaUserFundOrdersPortfolioDetailDO record = mapper.selectByPrimaryOrderId(orderId);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }

    @Transactional
    public int modifyByPrimaryKey(UserFundOrdersPortfolioDetailRequest request) throws Exception {
        if (request == null)
            return 0;
        RaUserFundOrdersPortfolioDetailDO record = new RaUserFundOrdersPortfolioDetailDO();
        BeanUtils.copyProperties(request, record);
        return mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 获取用户未受理的订单信息
     *
     * @param userId 用户ID
     * @return 未受理订单列表
     * @throws Exception
     */
    public List<UserFundOrdersPortfolioDetailResponse> findIntermediateByUserId(Integer userId) throws Exception {
        List<RaUserFundOrdersPortfolioDetailDO> records = mapper.selectIntermediateByUserId(userId);
        if (records == null || records.size() == 0)
            return new ArrayList<>();
        return ConvertUtils.convert(records, UserFundOrdersPortfolioDetailResponse.class);
    }

    /**
     * 查询用户和基金公司已确认过的订单集
     *
     * @param userId
     * @param portfolioId
     * @param fundCode
     * @return
     * @throws Exception
     */
    public List<UserFundOrdersPortfolioDetailResponse> findUserConfirmOrders(int userId, int portfolioId, String fundCode) throws Exception {
        List<RaUserFundOrdersPortfolioDetailDO> records = mapper.selectUserConfirmOrders(userId, portfolioId, fundCode);
        if (records == null || records.size() == 0)
            return new ArrayList<>();
        return ConvertUtils.convert(records, UserFundOrdersPortfolioDetailResponse.class);
    }
}
