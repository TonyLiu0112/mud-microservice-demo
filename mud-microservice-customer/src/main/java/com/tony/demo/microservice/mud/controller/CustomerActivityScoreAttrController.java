package com.tony.demo.microservice.mud.controller;

import com.tony.demo.microservice.mud.AbstractController;
import com.tony.demo.microservice.mud.results.PageRequest;
import com.tony.demo.microservice.mud.services.biz.customer.CustomerActivityScoreAttrService;
import com.tony.demo.microservice.mud.services.model.req.CustomerActivityScoreAttrReq;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 客户活动得分属性管理rest controller
 * <p>
 * Created by Tony on 16/02/2017.
 */
@RestController
@RequestMapping("/customer/score/attr")
public class CustomerActivityScoreAttrController extends AbstractController {

    private Logger logger = Logger.getLogger(CustomerActivityScoreAttrController.class);

    private final CustomerActivityScoreAttrService customerActivityScoreAttrService;

    @Autowired
    public CustomerActivityScoreAttrController(CustomerActivityScoreAttrService customerActivityScoreAttrService) {
        this.customerActivityScoreAttrService = customerActivityScoreAttrService;
    }
 
    @GetMapping(value = "findAll")
    public Map<String, Object> findAll(@RequestParam(value = "customerActivityId")long customerActivityId, HttpServletRequest httpReq){
    	PageRequest pageRequest = getPageRequest(httpReq);
    	try {
			return pageData(customerActivityScoreAttrService.findAll(customerActivityId, pageRequest.getPageNum(), pageRequest.getPageSize()), pageRequest);
		} catch (Exception e) {
			logger.error("Failed to find all customer activity score attr list.",e);
			return fail();
		}
    }
    
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable(value = "id") long id) throws Exception {
        try {
            return data(customerActivityScoreAttrService.findOne(id));
        } catch (Exception e) {
            logger.error("Failed to find all customer activity score attr info.", e);
            return fail();
        }
    }

    @PostMapping(value = "/save")
    public Map<String, Object> save(@RequestBody CustomerActivityScoreAttrReq customerActivityAttrReq) throws Exception {
        try {
            return data(customerActivityScoreAttrService.save(customerActivityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to save customer activity score attr info.", e);
            return fail();
        }
    }

    @PutMapping(value = "/modify")
    public Map<String, Object> modify(@RequestBody CustomerActivityScoreAttrReq customerActivityAttrReq) throws Exception {
        try {
            return data(customerActivityScoreAttrService.modify(customerActivityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to modify customer activity score attr info.", e);
            return fail();
        }
    }

    @DeleteMapping(value = "/remove")
    public Map<String, Object> remove(@RequestBody CustomerActivityScoreAttrReq customerActivityAttrReq) throws Exception {
        try {
            customerActivityScoreAttrService.remove(customerActivityAttrReq);
            return success();
        } catch (Exception e) {
            logger.error("Failed to remove customer activity score attr info.", e);
            return fail();
        }
    }

}
