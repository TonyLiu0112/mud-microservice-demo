package com.tony.demo.microservice.mud.services.recommendation.dao.mapper;

import com.tony.demo.microservice.mud.services.recommendation.dao.entity.RecommendationDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecommendationDOMapper {
    int insert(RecommendationDO record);

    int insertSelective(RecommendationDO record);

    RecommendationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecommendationDO record);

    int updateByPrimaryKey(RecommendationDO record);
}