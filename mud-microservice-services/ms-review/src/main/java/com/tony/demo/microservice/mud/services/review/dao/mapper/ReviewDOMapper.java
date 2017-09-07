package com.tony.demo.microservice.mud.services.review.dao.mapper;

import com.tony.demo.microservice.mud.services.review.dao.entity.ReviewDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewDOMapper {

    int insert(ReviewDO record);

    int insertSelective(ReviewDO record);

    ReviewDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReviewDO record);

    int updateByPrimaryKey(ReviewDO record);
}