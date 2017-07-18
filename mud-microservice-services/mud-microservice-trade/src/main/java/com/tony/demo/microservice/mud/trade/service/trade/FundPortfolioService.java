package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.trade.content.FundTypeCode;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioMasterDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioMasterDOMapper;
import com.tony.demo.microservice.mud.trade.service.bean.req.Product;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioDetailResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.FundPortfolioMasterResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundPortfolioDetailResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基金组合模板服务
 * <p>
 * Created by Tony on 07/06/2017.
 */
@Service
public class FundPortfolioService {

    @Autowired
    private RaFundPortfolioMasterDOMapper fundPortfolioMasterDOMapper;

    @Autowired
    private FundPortfolioDetailService fundPortfolioDetailService;

    /**
     * 查询组合模板明细
     *
     * @param portfolioId
     * @return
     * @throws Exception
     */
    public FundPortfolioMasterResponse findByPortfolioId(Integer portfolioId) throws Exception {
        FundPortfolioMasterResponse response = new FundPortfolioMasterResponse();
        RaFundPortfolioMasterDO record = fundPortfolioMasterDOMapper.selectByPrimaryKey(portfolioId);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }

    /**
     * 根据参数获取组合模版集合
     *
     * @param rfpm
     * @return
     */
    public List<RaFundPortfolioMasterDO> getPortfolioMasterListByEntity(RaFundPortfolioMasterDO rfpm) {
        List<RaFundPortfolioMasterDO> list = fundPortfolioMasterDOMapper.getPortfolioMasterListByEntity(rfpm);
        return list;
    }

    public FundPortfolioDetailResponse findPortfolioDetail(Integer portfolioId) throws Exception {
        FundPortfolioDetailResponse response = new FundPortfolioDetailResponse();
        FundPortfolioMasterResponse portfolioMasterResponse = this.findByPortfolioId(portfolioId);
        response.setPortfolioId(portfolioId);
        response.setPortfolioName(portfolioMasterResponse.getPortfolioName());
        List<RaFundPortfolioDetailResponse> details = fundPortfolioDetailService.findByPortfolioId(portfolioId);
        Map<String, BigDecimal> funds = new HashMap<>();
        Map<FundTypeCode, BigDecimal> types = new HashMap<FundTypeCode, BigDecimal>() {{
            put(FundTypeCode.MONEY_FUND, BigDecimal.ZERO);
            put(FundTypeCode.BOND_FUND, BigDecimal.ZERO);
            put(FundTypeCode.A_FUND, BigDecimal.ZERO);
            put(FundTypeCode.OVERSEAS_ASSETS, BigDecimal.ZERO);
            put(FundTypeCode.HK_FUND, BigDecimal.ZERO);
            put(FundTypeCode.OTHER_FUND, BigDecimal.ZERO);
        }};
        for (RaFundPortfolioDetailResponse detail : details) {
            Product product = new Product();
            funds.put(product.getJjcpmc(), detail.getPercentBegin());
            BigDecimal sum = detail.getPercentBegin().add(types.get(FundTypeCode.getByType(detail.getFundType())));
            types.put(FundTypeCode.getByType(detail.getFundType()), sum);
        }
        response.setFunds(funds);
        List<FundPortfolioDetailResponse.Holder> holders = new ArrayList<>();
        for (Map.Entry<FundTypeCode, BigDecimal> entry : types.entrySet()) {
            FundPortfolioDetailResponse.Holder holder = response.new Holder();
            holder.setName(entry.getKey().getName());
            holder.setValue(entry.getValue());
            holders.add(holder);
        }
        response.setData(holders);
        return response;
    }


}
