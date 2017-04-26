package com.tony.demo.microservice.mud.activity.controller;

import com.tony.demo.microservice.mud.activity.activity.ActivityAttrService;
import com.tony.demo.microservice.mud.activity.model.req.ActivityAttrReq;
import com.tony.demo.microservice.mud.common.AbstractController;
import com.tony.demo.microservice.mud.common.results.PageRequest;
import com.tony.demo.microservice.mud.common.utils.FileUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * 活动属性管理rest controller
 * <p>
 * Created by Tony on 16/02/2017.
 */
@RestController
@RequestMapping("/activity/attr")
public class ActivityAttrController extends AbstractController {

    private Logger logger = Logger.getLogger(ActivityAttrController.class);

    private final ActivityAttrService activityAttrService;

    @Value("${upload.image.path.activity.attr}")
    private String activityAttrPath;

    @Autowired
    public ActivityAttrController(ActivityAttrService activityAttrService) {
        this.activityAttrService = activityAttrService;
    }

    @GetMapping(value = "/findAll")
    public Map<String, Object> findAll(@RequestParam(value = "activityId") long activityId, HttpServletRequest httpReq) throws Exception {
        try {
            PageRequest pageRequest = getPageRequest(httpReq);
            return pageData(activityAttrService.findByActivityId(activityId, pageRequest.getPageNum(), pageRequest.getPageSize()), pageRequest);
        } catch (Exception e) {
            logger.error("Failed to find all activity attr list.", e);
            return fail();
        }
    }

    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable(value = "id") long id) throws Exception {
        try {
            return data(activityAttrService.findOne(id));
        } catch (Exception e) {
            logger.error("Failed to find all activity attr info.", e);
            return fail();
        }
    }

    @PostMapping(value = "/save")
    public Map<String, Object> save(@RequestBody ActivityAttrReq activityAttrReq) throws Exception {
        try {
            return data(activityAttrService.save(activityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to save activity attr info.", e);
            return fail();
        }
    }

    @PutMapping(value = "/modify")
    public Map<String, Object> modify(@RequestBody ActivityAttrReq activityAttrReq) throws Exception {
        try {
            return data(activityAttrService.modify(activityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to modify activity attr info.", e);
            return fail();
        }
    }

    @DeleteMapping(value = "/remove")
    public Map<String, Object> remove(@RequestBody ActivityAttrReq activityAttrReq) throws Exception {
        try {
            return data(activityAttrService.remove(activityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to remove activity attr info.", e);
            return fail();
        }
    }

    @PostMapping(value = "uploadImage")
    public Map<String, Object> uploadImg(ActivityAttrReq request, @RequestParam(name = "imageFile") MultipartFile imageFile) {
        try {
            String path = FileUtil.getPath(activityAttrPath + File.separator + request.getActivityId() + File.separator);
            String fileName = FileUtil.getRangeFileName("png");
            String outFile = path + fileName;
            FileOutputStream outputStream = new FileOutputStream(outFile);
            IOUtils.write(imageFile.getBytes(), outputStream);
            IOUtils.closeQuietly(outputStream);
            return data(outFile);
        } catch (Exception e) {
            logger.error("Failed to upload image.", e);
            return fail();
        }
    }

}
