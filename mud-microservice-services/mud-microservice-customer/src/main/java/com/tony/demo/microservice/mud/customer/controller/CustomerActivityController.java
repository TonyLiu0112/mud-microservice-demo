package com.tony.demo.microservice.mud.customer.controller;

import com.tony.demo.microservice.mud.common.AbstractController;
import com.tony.demo.microservice.mud.common.results.PageRequest;
import com.tony.demo.microservice.mud.customer.services.biz.customer.CustomerActivityService;
import com.tony.demo.microservice.mud.customer.services.model.req.CustomerActivityReq;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 客户活动管理rest controller
 * <p>
 * Created by Tony on 16/02/2017.
 */
@RestController
@RequestMapping("/customer/activity")
public class CustomerActivityController extends AbstractController {

    private Logger logger = Logger.getLogger(CustomerActivityController.class);

    private final CustomerActivityService customerActivityService;

    @Autowired
    public CustomerActivityController(CustomerActivityService customerActivityService) {
        this.customerActivityService = customerActivityService;
    }

    @GetMapping(value = "/findAll")
    public Map<String, Object> findAll(HttpServletRequest request) throws Exception {
    	PageRequest pageRequest = getPageRequest(request);
        return pageData(customerActivityService.findAll(pageRequest.getPageNum(), pageRequest.getPageSize()), pageRequest);
    }

    @GetMapping(value = "/findByActivityId")
    public Map<String, Object> findAll(@RequestParam("activityId") long activityId) throws Exception {
        return data(customerActivityService.findByActivityId(activityId));
    }



    @GetMapping(value = "/{id}")
    public Map<String, Object> findByPK(@PathVariable("id") Long id) throws Exception {
        try {
            return data(customerActivityService.findOne(id));
        } catch (Exception e) {
            logger.error("Failed to find one customer activity info.", e);
            return fail();
        }
    }

    @PostMapping(value = "/save")
    public Map<String, Object> save(@RequestBody CustomerActivityReq customerActivityReq) throws Exception {
        try {
            return data(customerActivityService.save(customerActivityReq));
        } catch (Exception e) {
            logger.error("Failed to save customer activity info.", e);
            return fail();
        }
    }

    @PutMapping(value = "/modify")
    public Map<String, Object> modifyByPK(@RequestBody CustomerActivityReq customerActivityReq) throws Exception {
        try {
            return data(customerActivityService.modify(customerActivityReq));
        } catch (Exception e) {
            logger.error("Failed to modify customer activity info.", e);
            return fail();
        }
    }

    @DeleteMapping(value = "/remove")
    public Map<String, Object> removeByPK(@RequestBody CustomerActivityReq customerActivityReq) throws Exception {
        try {
            return data(customerActivityService.remove(customerActivityReq));
        } catch (Exception e) {
            logger.error("Failed to remove customer activity info.", e);
            return fail();
        }
    }

}
