package com.tony.demo.microservice.mud.controller;

import com.tony.demo.microservice.mud.AbstractController;
import com.tony.demo.microservice.mud.results.PageRequest;
import com.tony.demo.microservice.mud.services.biz.customer.CustomerActivityAttrService;
import com.tony.demo.microservice.mud.services.model.req.CustomerActivityAttrReq;
import com.tony.demo.microservice.mud.utils.FileUtil;
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
 * 客户活动属性管理rest controller
 * <p>
 * Created by Tony on 16/02/2017.
 */
@RestController
@RequestMapping("/customer/activity/attr")
public class CustomerActivityAttrController extends AbstractController {

    private Logger logger = Logger.getLogger(CustomerActivityAttrController.class);

    private final CustomerActivityAttrService customerActivityAttrService;
    @Value("${upload.image.path.customer.activity.attr}")
    private String customerActivityAttrPath;

    @Autowired
    public CustomerActivityAttrController(CustomerActivityAttrService customerActivityAttrService) {
        this.customerActivityAttrService = customerActivityAttrService;
    }
    
    @GetMapping(value = "/findAll")
    public Map<String, Object> findAll(@RequestParam(value = "customerActivityId") long customerActivityId, HttpServletRequest httpReq){
    	PageRequest pageRequest = getPageRequest(httpReq);
    	try {
			return pageData(customerActivityAttrService.findByCustomerActivityId(customerActivityId, pageRequest.getPageNum(), pageRequest.getPageSize()), pageRequest);
		} catch (Exception e) {
			logger.error("Failed to find all customer activity attr list.",e);
			return fail();
		}
    }
    
    @GetMapping(value = "/{id}")
    public Map<String, Object> findOne(@PathVariable(value = "id") long id) throws Exception {
        try {
            return data(customerActivityAttrService.findOne(id));
        } catch (Exception e) {
            logger.error("Failed to find all customer activity attr info.", e);
            return fail();
        }
    }

    @PostMapping(value = "/save")
    public Map<String, Object> save(@RequestBody CustomerActivityAttrReq customerActivityAttrReq) throws Exception {
        try {
            return data(customerActivityAttrService.save(customerActivityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to save customer activity attr info.", e);
            return fail();
        }
    }

    @PutMapping(value = "/modify")
    public Map<String, Object> modify(@RequestBody CustomerActivityAttrReq customerActivityAttrReq) throws Exception {
        try {
            return data(customerActivityAttrService.modify(customerActivityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to modify customer activity attr info.", e);
            return fail();
        }
    }

    @DeleteMapping(value = "/remove")
    public Map<String, Object> remove(@RequestBody CustomerActivityAttrReq customerActivityAttrReq) throws Exception {
        try {
            return data(customerActivityAttrService.remove(customerActivityAttrReq));
        } catch (Exception e) {
            logger.error("Failed to remove customer activity attr info.", e);
            return fail();
        }
    }

    @PostMapping(value = "uploadImage")
    public Map<String, Object> uploadImg(CustomerActivityAttrReq request, @RequestParam(name = "imageFile") MultipartFile imageFile) {
        try {
            String path = FileUtil.getPath(customerActivityAttrPath + File.separator + request.getCustomerActivityId() + File.separator);
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
