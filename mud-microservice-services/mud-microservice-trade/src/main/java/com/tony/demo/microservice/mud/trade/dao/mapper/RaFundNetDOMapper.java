package com.tony.demo.microservice.mud.trade.dao.mapper;

import com.tony.demo.microservice.mud.trade.dao.entity.RaFundNetDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RaFundNetDOMapper {
    int insert(RaFundNetDO record);

    int insertSelective(RaFundNetDO record);

    RaFundNetDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RaFundNetDO record);

    int updateByPrimaryKey(RaFundNetDO record);

    RaFundNetDO selectLatestByFundCode(@Param("fundCode") String fundCode);

    RaFundNetDO selectLatestBeginNetByFundCodeArrStr(@Param("fundCodeArrStr") String fundCodeArrStr);

    RaFundNetDO selectEarliestBreakNetByFundCodeArrStr(@Param("fundCodeArrStr") String fundCodeArrStr);

    RaFundNetDO selectByFundCodeAndNetDate(@Param("fundCode") String fundCode, @Param("netDate") String netDate);

    List<RaFundNetDO> selectByFundCodeArrStr(@Param("fundCodeArrStr") String fundCodeArrStr);

    List<RaFundNetDO> selectByFundCodeArrStrAndBeginNetDate(@Param("fundCodeArrStr") String fundCodeArrStr, @Param("beginNetDate") String beginNetDate);

    List<RaFundNetDO> selectByFundCodeArrStrAndNetDate(@Param("fundCodeArrStr") String fundCodeArrStr, @Param("netDate") String netDate);

    RaFundNetDO selectByFundCodeAndDate(RaFundNetDO record);

    List<RaFundNetDO> selectAllByFundCode(@Param("fundCode") String fundCode);
}