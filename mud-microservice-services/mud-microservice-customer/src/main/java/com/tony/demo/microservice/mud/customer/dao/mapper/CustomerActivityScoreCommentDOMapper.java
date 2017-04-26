package com.tony.demo.microservice.mud.customer.dao.mapper;

import com.tony.demo.microservice.mud.customer.dao.entity.CustomerActivityScoreCommentDO;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerActivityScoreCommentDOMapper {
    int insert(CustomerActivityScoreCommentDO record);

    int insertSelective(CustomerActivityScoreCommentDO record);

    CustomerActivityScoreCommentDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomerActivityScoreCommentDO record);

    int updateByPrimaryKey(CustomerActivityScoreCommentDO record);
}