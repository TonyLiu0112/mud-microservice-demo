package com.tony.demo.microservice.mud.controller;

import com.tony.demo.microservice.mud.AbstractController;
import com.tony.demo.microservice.mud.results.PageRequest;
import com.tony.demo.microservice.mud.service.biz.activity.ActivityService;
import com.tony.demo.microservice.mud.service.model.req.ActivityReq;
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
@RequestMapping("/activity")
public class ActivityController extends AbstractController {

    private Logger logger = Logger.getLogger(ActivityController.class);

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping(value = "list")
    public Map<String, Object> findAll(HttpServletRequest httpReq) {
        PageRequest pageRequest = getPageRequest(httpReq);
        return pageData(activityService.findAll(pageRequest.getPageNum(), pageRequest.getPageSize()), pageRequest);
    }

    @GetMapping(value = "listNoPage")
    public Map<String, Object> findAllNoPage() {
        return data(activityService.findAllNoPage());
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> findByPK(@PathVariable("id") Long id) throws Exception {
        try {
            return data(activityService.findOne(id));
        } catch (Exception e) {
            logger.error(e);
            return fail();
        }
    }

    @PutMapping(value = "/modify")
    public Map<String, Object> modifyByPK(@RequestBody ActivityReq activityReq) throws Exception {
        try {
            return data(activityService.modify(activityReq));
        } catch (Exception e) {
            logger.error("Failed to modify activity info.", e);
            return fail();
        }

    }

    @PostMapping(value = "/save")
    public Map<String, Object> save(@RequestBody ActivityReq activityReq) throws Exception {
        try {
            return data(activityService.save(activityReq));
        } catch (Exception e) {
            logger.error("Failed to save activity info.", e);
            return fail();
        }
    }

    @DeleteMapping(value = "/remove")
    public Map<String, Object> removeByPK(@RequestBody ActivityReq activityReq) throws Exception {
        try {
            return data(activityService.remove(activityReq));
        } catch (Exception e) {
            logger.error("Failed to remove activity info.", e);
            return fail();
        }
    }
}
