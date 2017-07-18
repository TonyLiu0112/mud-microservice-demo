package com.tony.demo.microservice.mud.trade.service.trade;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundNetDO;
import com.tony.demo.microservice.mud.trade.dao.mapper.RaFundNetDOMapper;
import com.tony.demo.microservice.mud.trade.service.bean.req.RaFundNetRequest;
import com.tony.demo.microservice.mud.trade.service.bean.res.RaFundNetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 基金净值服务
 * <p>
 * Created by Tony on 21/06/2017.
 */
@Service
public class FundNetService {

    private final RaFundNetDOMapper raFundNetDOMapper;

    @Autowired
    public FundNetService(RaFundNetDOMapper raFundNetDOMapper) {
        this.raFundNetDOMapper = raFundNetDOMapper;
    }

    /**
     * 根据基金代码获取最新净值
     *
     * @return
     */
    RaFundNetResponse findLatestByFundCode(String fundCode) {
        RaFundNetResponse response = new RaFundNetResponse();
        RaFundNetDO record = raFundNetDOMapper.selectLatestByFundCode(fundCode);
        if (record != null)
            BeanUtils.copyProperties(record, response);
        return response;
    }

    /**
     * 根据基金代码和指定净值日期获取净值
     *
     * @return
     */
    RaFundNetDO findByFundCodeAndNetDate(String fundCode, String netDate) {
        RaFundNetDO raFundNetDO = raFundNetDOMapper.selectByFundCodeAndNetDate(fundCode, netDate);
        return raFundNetDO;
    }


    /**
     * 根据一组基金代码获取最晚开始产生净值的记录
     *
     * @return
     */
    RaFundNetDO findLatestBeginNetByFundCodeArr(String[] fundCodeArr) {
        RaFundNetResponse response = new RaFundNetResponse();
        StringBuilder builder = new StringBuilder();
        for (String str : fundCodeArr) {
            builder.append("'" + str + "',");
        }
        String fundCodeArrStr = builder.toString();
        fundCodeArrStr = fundCodeArrStr.substring(0, fundCodeArrStr.length() - 1);
        RaFundNetDO record = raFundNetDOMapper.selectLatestBeginNetByFundCodeArrStr(fundCodeArrStr);
        return record;
    }

    /**
     * 根据一组基金代码获取最早中断净值的记录
     *
     * @return
     */
    RaFundNetDO findEarliestBreakNetByFundCodeArr(String[] fundCodeArr) {
        RaFundNetResponse response = new RaFundNetResponse();
        StringBuilder builder = new StringBuilder();
        for (String str : fundCodeArr) {
            builder.append("'" + str + "',");
        }
        String fundCodeArrStr = builder.toString();
        fundCodeArrStr = fundCodeArrStr.substring(0, fundCodeArrStr.length() - 1);
        RaFundNetDO record = raFundNetDOMapper.selectEarliestBreakNetByFundCodeArrStr(fundCodeArrStr);
        return record;
    }


    /**
     * 根据一组基金代码获取净值
     *
     * @return
     */
    List<RaFundNetDO> findByFundCodeArr(String[] fundCodeArr) {
        StringBuilder builder = new StringBuilder();
        for (String str : fundCodeArr) {
            builder.append("'" + str + "',");
        }
        String fundCodeArrStr = builder.toString();
        fundCodeArrStr = fundCodeArrStr.substring(0, fundCodeArrStr.length() - 1);
        List<RaFundNetDO> list = raFundNetDOMapper.selectByFundCodeArrStr(fundCodeArrStr);
        return list;
    }

    /**
     * 根据一组基金代码和指定起始日期获取净值
     *
     * @return
     */
    List<RaFundNetDO> findByFundCodeArrAndBeginNetDate(String[] fundCodeArr, String beginNetDate) {
        StringBuilder builder = new StringBuilder();
        for (String str : fundCodeArr) {
            builder.append("'" + str + "',");
        }
        String fundCodeArrStr = builder.toString();
        fundCodeArrStr = fundCodeArrStr.substring(0, fundCodeArrStr.length() - 1);
        List<RaFundNetDO> list = raFundNetDOMapper.selectByFundCodeArrStrAndBeginNetDate(fundCodeArrStr, beginNetDate);
        return list;
    }


    /**
     * 根据一组基金代码和指定日期获取该日期基金净值记录
     *
     * @return
     */
    List<RaFundNetDO> findByFundCodeArrAndNetDate(String[] fundCodeArr, String netDate) {
        StringBuilder builder = new StringBuilder();
        for (String str : fundCodeArr) {
            builder.append("'" + str + "',");
        }
        String fundCodeArrStr = builder.toString();
        fundCodeArrStr = fundCodeArrStr.substring(0, fundCodeArrStr.length() - 1);
        List<RaFundNetDO> list = raFundNetDOMapper.selectByFundCodeArrStrAndNetDate(fundCodeArrStr, netDate);
        return list;
    }

    List<RaFundNetDO> findAllByFundCode(String fundCode) {
        List<RaFundNetDO> list = raFundNetDOMapper.selectAllByFundCode(fundCode);
        return list;
    }


    @Transactional
    public int save(RaFundNetRequest raFundNetRequest) throws Exception {
        if (raFundNetRequest == null)
            return 0;
        RaFundNetDO record = new RaFundNetDO();
        BeanUtils.copyProperties(raFundNetRequest, record);
        RaFundNetDO raFundNetDO = raFundNetDOMapper.selectByFundCodeAndDate(record);
        if (raFundNetDO != null)
            return 0;
        return raFundNetDOMapper.insert(record);
    }
}
