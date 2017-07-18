package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.trade.service.bean.res.PortfolioNetResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.PortfolioNetTrendResponse;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundPortfolioDetailResponse;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundNetDO;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioMasterDO;
import com.tony.demo.microservice.mud.trade.dao.entity.RaFundPortfolioSystemHistoryDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioMasterDOMapper;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundPortfolioSystemHistoryDOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 组合基金总市值服务
 * <p>
 * Created by Jianbo on 08/07/2017.
 */
@Service
public class FundPortfolioSystemHistoryService {
    private Logger logger = LoggerFactory.getLogger(FundPortfolioSystemHistoryService.class);
    @Autowired
    private RaFundPortfolioMasterDOMapper fundPortfolioMasterDOMapper;

    @Autowired
    private FundPortfolioDetailService fundPortfolioDetailService;

    @Autowired
    private FundNetService fundNetService;

    @Autowired
    private RaFundPortfolioSystemHistoryDOMapper raFundPortfolioSystemHistoryDOMapper;

    public int insert(RaFundPortfolioSystemHistoryDO raFundPortfolioSystemHistoryDO) throws Exception {
        if (raFundPortfolioSystemHistoryDO == null)
            return 0;
        return raFundPortfolioSystemHistoryDOMapper.insert(raFundPortfolioSystemHistoryDO);
    }

    /**
     * 获得某基金组合的净值趋势图数据
     * @param portfolioId
     * @return
     */
    public PortfolioNetTrendResponse getPortfolioNetTrendResponse(int portfolioId){
        PortfolioNetTrendResponse portfolioNetTrendResponse=new PortfolioNetTrendResponse();
        portfolioNetTrendResponse.setLastMonth(getPortfolioNetByPortfolioIdAndMonth(portfolioId,1,"近一个月"));
        portfolioNetTrendResponse.setLastThreeMonths(getPortfolioNetByPortfolioIdAndMonth(portfolioId,3,"近三个月"));
        portfolioNetTrendResponse.setLastHalfYear(getPortfolioNetByPortfolioIdAndMonth(portfolioId,6,"近半年"));
        portfolioNetTrendResponse.setLastYear(getPortfolioNetByPortfolioIdAndMonth(portfolioId,12,"近一年"));
        return portfolioNetTrendResponse;

    }

    private PortfolioNetResponse getPortfolioNetByPortfolioIdAndMonth(int portfolioId,int monthNum,String dateName){
        PortfolioNetResponse response=new PortfolioNetResponse();
        List<String> netDateList=new ArrayList<>();
        List<Float> netList=new ArrayList<>();
        List<RaFundPortfolioSystemHistoryDO> list=findByPortfolioIdAndRecentMonth(portfolioId,monthNum);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(RaFundPortfolioSystemHistoryDO ra:list){
            Date date = new Date((long)ra.getRecordeDate()*1000);
            netDateList.add(simpleDateFormat.format(date));
            netList.add(ra.getPortfolioNet().floatValue());
        }
        response.setDataName(dateName);
        response.setNetArr(netList);
        response.setNetDateArr(netDateList);
        return response;
    }
    /**
     * 获得某基金组合近n月的净值数据，根据最近月份数和组合id查询
     * @param portfolioId
     * @param recentMonthNum  最近几个月 N
     * @return
     */
    public List<RaFundPortfolioSystemHistoryDO> findByPortfolioIdAndRecentMonth(int portfolioId,int recentMonthNum){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -recentMonthNum); // 目前时间减n月
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time= c.getTimeInMillis()/1000;
        return raFundPortfolioSystemHistoryDOMapper.selectByPortfolioIdAndDate(portfolioId,(int)time);

    }






    /**
     * 计算某个组合的总市值
     * @param portfolioId
     * @param assumedValue
     * @throws Exception
     */
    private void countFundPortfolioTotalMarketValue(int portfolioId,double assumedValue) throws Exception {
        //计算基金组合总市值
        logger.info("计算组合"+portfolioId+"基金总市值开始计算...");
        //通过组合id获取组合中的基金列表
        List<RaFundPortfolioDetailResponse> portfolioFundList=fundPortfolioDetailService.findByPortfolioId(portfolioId);
        //遍历组合中的基金,
        List<String> fundCodeList=new ArrayList<String>();
        for(RaFundPortfolioDetailResponse raFundPortfolioDetailResponse: portfolioFundList){
            String fundCode= raFundPortfolioDetailResponse.getFundCode();
            fundCodeList.add(fundCode);
        }
        String[] fundCodeArr=new String[fundCodeList.size()];
        fundCodeList.toArray(fundCodeArr);
        //先找出组合中最晚开始产生净值的基金，以其产生净值日期为准开始计算，其余更早产生净值的基金忽略
        RaFundNetDO latestBeginFundNetDO= fundNetService.findLatestBeginNetByFundCodeArr(fundCodeArr);
        logger.info("计算组合"+portfolioId+"基金总市值"+"已找到最晚开始产生净值的基金..."+latestBeginFundNetDO.getFundCode());
        //再找出组合中最早中断净值的基金，以其中断净值日期为结束日期计算，其余有更新净值的基金忽略
        RaFundNetDO earliestBreakFundNetDO= fundNetService.findEarliestBreakNetByFundCodeArr(fundCodeArr);
        logger.info("计算组合"+portfolioId+"基金总市值"+"已找到最早中断净值的基金..."+earliestBreakFundNetDO.getFundCode());
        //根据最晚开始产生净值的基金编码查出所有净值记录
        List<RaFundNetDO> latestOriginNetFundNetList =fundNetService.findAllByFundCode(latestBeginFundNetDO.getFundCode());
        //获取该组合基金产生总净值的日期列表
        List<String> totalNetDateList=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //该组合最早中断日期为
        String earliestBreakDate=sdf.format(earliestBreakFundNetDO.getNetDate());
        for(RaFundNetDO raFundNetDO : latestOriginNetFundNetList){
            String s=sdf.format(raFundNetDO.getNetDate());
            totalNetDateList.add(s);
            if(s.equals(earliestBreakDate)){//这种方法要求组合中的基金在中间的同一天没有缺失净值记录，理应如此
                break;
            }
        }
        logger.info("计算组合"+portfolioId+"基金总市值"+"已获得该组合净值日期列表..."+totalNetDateList.size());
        //获得组合中基金有效净值列表（起点日期相同）
        List<RaFundNetDO> portfolioValidFundNetList =fundNetService.findByFundCodeArrAndBeginNetDate(fundCodeArr,totalNetDateList.get(0));
        Map<String,TempPortfolioFund> tempPortfolioFundMap=new HashMap<>();
        for(RaFundPortfolioDetailResponse raFundPortfolioDetailResponse: portfolioFundList){
            TempPortfolioFund tpf=new TempPortfolioFund();
            String fundCode= raFundPortfolioDetailResponse.getFundCode();
            tpf.setFundCode(fundCode);
            tpf.setPercent(raFundPortfolioDetailResponse.getPercentBegin());
            BigDecimal originNet=fundNetService.findByFundCodeAndNetDate(fundCode,totalNetDateList.get(0)).getNet();//获得组合中基金的初始净值
            //logger.info("初始"+originNet);
            tpf.setOriginNet(originNet);
            tempPortfolioFundMap.put(fundCode,tpf);
        }

        for(String date :totalNetDateList){
            //根据日期计算该天组合中所有基金的市值总和
            double totalMarketValueByDate=countPortfolioFundTotalMarketValueByDate(tempPortfolioFundMap,portfolioValidFundNetList,date,assumedValue);
            logger.info("计算组合"+portfolioId+"基金总市值"+date+"市值"+totalMarketValueByDate);
            //将组合总市值写入表
            RaFundPortfolioSystemHistoryDO raFundPortfolioSystemHistoryDO=new RaFundPortfolioSystemHistoryDO();
            raFundPortfolioSystemHistoryDO.setPortfolioId(portfolioId);
            int time=(int)(sdf.parse(date).getTime()/1000);
            raFundPortfolioSystemHistoryDO.setRecordeDate(time);
            DecimalFormat df=new DecimalFormat(".##");
            String st=df.format(totalMarketValueByDate);
            raFundPortfolioSystemHistoryDO.setPortfolioMarketVal(new BigDecimal(st));
            raFundPortfolioSystemHistoryDO.setPortfolioInvestedCost(new BigDecimal(assumedValue));
            BigDecimal accumulatedProfit=raFundPortfolioSystemHistoryDO.getPortfolioMarketVal().subtract(raFundPortfolioSystemHistoryDO.getPortfolioInvestedCost());
            raFundPortfolioSystemHistoryDO.setPortfolioAccumulatedProfit(accumulatedProfit);
            double portfolioNet=totalMarketValueByDate/assumedValue;
            DecimalFormat df2=new DecimalFormat(".####");
            String st2=df2.format(portfolioNet);
            raFundPortfolioSystemHistoryDO.setPortfolioNet(new BigDecimal(st2));
            insert(raFundPortfolioSystemHistoryDO);
            logger.info("计算组合"+portfolioId+"基金总市值"+date+"市值已记录入数据表");
        }
        logger.info("计算组合"+portfolioId+"基金总市值已完成");

    }


    public void fundPortfolioTotalMarketCapCountTask(double assumedValue){
        try {
            logger.info("计算组合基金总市值任务开始...");
            //获取所有组合
            List<RaFundPortfolioMasterDO> list = fundPortfolioMasterDOMapper.selectAll();
            //遍历组合表
            for (RaFundPortfolioMasterDO raFundPortfolioMasterDO : list) {
                int portfolioId = raFundPortfolioMasterDO.getPortfolioId();
                //计算该组合总市值
                countFundPortfolioTotalMarketValue(portfolioId,assumedValue);
            }
            logger.info("计算组合基金总市值任务已完成");
        }catch (Exception e){
            logger.error("基金组合总市值计算任务出错...",e);
        }
    }

    /**
     * 计算某日期组合的总市值
     * @param map
     * @param portfolioValidFundNetList
     * @param netDate
     * @param assumedValue
     * @return
     */
    private double countPortfolioFundTotalMarketValueByDate(Map<String,TempPortfolioFund> map,List<RaFundNetDO> portfolioValidFundNetList,String netDate,double assumedValue){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        double totalMarketValue=0;
        logger.info("portfolioValidFundNetList"+portfolioValidFundNetList.size()+"netDate"+netDate);
        for(RaFundNetDO raFundNetDO :portfolioValidFundNetList){
            String date=sdf.format(raFundNetDO.getNetDate());
            if(date.equals(netDate)){
                String fundCode=raFundNetDO.getFundCode();
                TempPortfolioFund tempPortfolioFund=map.get(fundCode);
                //当前用初始占比作为实际占比
                double percent= Double.parseDouble(tempPortfolioFund.getPercent()+"");
                //logger.info("percent"+percent);
                double originNet=Double.parseDouble(tempPortfolioFund.getOriginNet()+"");
                double net=Double.parseDouble(raFundNetDO.getNet()+"");
                //该日期该基金市值=(假定购买总金额*该基金在组合中的占比/该基金起始净值)*该日期基金净值
                double marketValue= (assumedValue*percent/originNet)*net;
                totalMarketValue+=marketValue;
            }
        }
        return totalMarketValue;
    }

    private class TempPortfolioFund{
        private String fundCode;
        private BigDecimal percent;//组合中的占比
        private BigDecimal originNet;//起始净值

        public String getFundCode() {
            return fundCode;
        }

        public void setFundCode(String fundCode) {
            this.fundCode = fundCode;
        }

        public BigDecimal getPercent() {
            return percent;
        }

        public void setPercent(BigDecimal percent) {
            this.percent = percent;
        }

        public BigDecimal getOriginNet() {
            return originNet;
        }

        public void setOriginNet(BigDecimal originNet) {
            this.originNet = originNet;
        }
    }







}
