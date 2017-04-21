package com.tony.demo.microservice.mud.controller;

import com.tony.demo.microservice.mud.AbstractController;
import com.tony.demo.microservice.mud.results.PageRequest;
import com.tony.demo.microservice.mud.services.biz.customer.CustomerService;
import com.tony.demo.microservice.mud.services.model.req.CustomerReq;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 活动控制器
 * <p>
 * Created by Tony on 13/02/2017.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

    private Logger logger = Logger.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "list")
    public Map<String, Object> findAll(HttpServletRequest request) {
    	PageRequest pageRequest = getPageRequest(request);
        return pageData(customerService.findAll(pageRequest.getPageNum(),pageRequest.getPageSize()),pageRequest);
    }
    
    @GetMapping(value = "listNoPage")
    public Map<String,Object> findAllNoPage(){
    	return data(customerService.findAllNoPage());
    }
    
    @GetMapping(value = "/{id}")
    public Map<String, Object> findByPK(@PathVariable("id") Long id) throws Exception {
        try {
            return data(customerService.findOne(id));
        } catch (Exception e) {
            logger.error(e);
            return fail();
        }
    }

    @PutMapping(value = "/modify")
    public Map<String, Object> modifyByPK(@RequestBody CustomerReq customerReq) throws Exception {
        try {
            return data(customerService.modify(customerReq));
        } catch (Exception e) {
            logger.error("Failed to modify customer info.", e);
            return fail();
        }

    }

    @PostMapping(value = "/save")
    public Map<String, Object> save(@RequestBody CustomerReq customerReq) throws Exception {
        try {
            return data(customerService.save(customerReq));
        } catch (Exception e) {
            logger.error("Failed to save customer info.", e);
            return fail();
        }
    }

    @DeleteMapping(value = "/remove")
    public Map<String, Object> removeByPK(@RequestBody CustomerReq customerReq) throws Exception {
        try {
            return data(customerService.remove(customerReq));
        } catch (Exception e) {
            logger.error("Failed to remove customer info.", e);
            return fail();
        }
    }
}
