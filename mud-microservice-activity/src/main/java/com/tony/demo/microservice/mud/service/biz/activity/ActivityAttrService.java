package com.tony.demo.microservice.mud.service.biz.activity;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.dao.entity.ActivityAttrDO;
import com.tony.demo.microservice.mud.dao.repository.ActivityAttrRepository;
import com.tony.demo.microservice.mud.service.model.req.ActivityAttrReq;
import com.tony.demo.microservice.mud.service.model.res.ActivityAttrRes;
import com.tony.demo.microservice.mud.utils.BeanUtilsPlus;
import com.tony.demo.microservice.mud.utils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 活动属性管理
 * <p>
 * Created by Tony on 16/02/2017.
 */
@Service
public class ActivityAttrService {

    private final ActivityAttrRepository activityAttrRepository;

    @Autowired
    public ActivityAttrService(ActivityAttrRepository activityAttrRepository) {
        this.activityAttrRepository = activityAttrRepository;
    }

    public PageInfo<ActivityAttrRes> findByActivityId(long activityId, int page, int size) throws Exception {
        Page<ActivityAttrDO> pageList = activityAttrRepository.findByDfAndActivityId(0, activityId, new PageRequest(page - 1, size));
        Page<ActivityAttrRes> pages = pageList.map(new Converter<ActivityAttrDO, ActivityAttrRes>() {
            @Override
            public ActivityAttrRes convert(ActivityAttrDO source) {
                ActivityAttrRes attrRes = new ActivityAttrRes();
                BeanUtils.copyProperties(source, attrRes);
                return attrRes;
            }
        });
        return new PageInfo<>(ConvertUtils.convert(pages));
    }

    public ActivityAttrRes findOne(long id) throws Exception {
        ActivityAttrRes res = new ActivityAttrRes();
        ActivityAttrDO activityAttrDO = activityAttrRepository.findOne(id);
        if (activityAttrDO != null)
            BeanUtils.copyProperties(activityAttrDO, res);
        return res;
    }

    @Transactional
    public ActivityAttrRes save(ActivityAttrReq req) throws Exception {
        req.setDf(0);
        ActivityAttrDO activityAttrDO = new ActivityAttrDO();
        BeanUtils.copyProperties(req, activityAttrDO);
        ActivityAttrDO saveDO = activityAttrRepository.save(activityAttrDO);
        ActivityAttrRes res = new ActivityAttrRes();
        BeanUtils.copyProperties(saveDO, res);
        return res;
    }

    @Transactional
    public ActivityAttrRes modify(ActivityAttrReq req) throws Exception {
        if (req == null || req.getId() == 0)
            return new ActivityAttrRes();
        ActivityAttrDO activityAttrDO = activityAttrRepository.findOne(req.getId());
        BeanUtils.copyProperties(req, activityAttrDO, BeanUtilsPlus.ignoreProperties(req));
        ActivityAttrDO updateDO = activityAttrRepository.save(activityAttrDO);
        ActivityAttrRes res = new ActivityAttrRes();
        BeanUtils.copyProperties(updateDO, res);
        return res;
    }

    @Transactional
    public ActivityAttrRes remove(ActivityAttrReq req) throws Exception {
        if (req == null || req.getId() == 0)
            return new ActivityAttrRes();
        req.setDf(1);
        return modify(req);
    }

}
