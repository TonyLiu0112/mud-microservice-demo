package com.tony.demo.microservice.mud.dao.repository;

import com.tony.demo.microservice.mud.dao.entity.CustomerDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for CustomerEntity
 * <p>
 * Created by Tony on 09/02/2017.
 */
public interface CustomerRepository extends JpaRepository<CustomerDO, Long> {
	Page<CustomerDO> findByDf(int df, Pageable pageable);

	List<CustomerDO> findByDf(int df);

}
