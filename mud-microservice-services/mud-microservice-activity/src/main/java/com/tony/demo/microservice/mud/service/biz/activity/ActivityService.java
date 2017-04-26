package com.tony.demo.microservice.mud.service.biz.activity;

import com.github.pagehelper.PageInfo;
import com.tony.demo.microservice.mud.common.utils.BeanUtilsPlus;
import com.tony.demo.microservice.mud.common.utils.ConvertUtils;
import com.tony.demo.microservice.mud.dao.entity.ActivityDO;
import com.tony.demo.microservice.mud.dao.repository.ActivityRepository;
import com.tony.demo.microservice.mud.service.model.req.ActivityReq;
import com.tony.demo.microservice.mud.service.model.res.ActivityRes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 客户活动管理
 * <p>
 * Created by Tony on 13/02/2017.
 */
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    private CacheService cacheService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * @param page
     * @param size
     * @return
     */
    public PageInfo<ActivityDO> findAll(int page, int size) {
        Page<ActivityDO> pageList = activityRepository.findByDf(0, new PageRequest(page - 1, size));
        return new PageInfo<>(ConvertUtils.convert(pageList));
    }

    public ActivityRes findOne(long id) throws Exception {
        ActivityDO activityDO = activityRepository.findOne(id);
        ActivityRes res = new ActivityRes();
        if (activityDO != null)
            BeanUtils.copyProperties(activityDO, res);
        return res;
    }

    @Transactional
    public ActivityRes modify(ActivityReq activityReq) throws Exception {
        ActivityDO activityDO = activityRepository.findOne(activityReq.getId());
        BeanUtils.copyProperties(activityReq, activityDO, BeanUtilsPlus.ignoreProperties(activityReq));
        ActivityDO updateDO = activityRepository.save(activityDO);
        ActivityRes res = new ActivityRes();
        BeanUtils.copyProperties(updateDO, res);
        cacheService.cacheActivity(res);
        return res;
    }

    @Transactional
    public ActivityRes save(ActivityReq activityReq) throws Exception {
        ActivityRes res = new ActivityRes();
        if (activityReq == null)
            return res;
        activityReq.setState(1);
        activityReq.setType(1);
        activityReq.setDf(0);
        activityReq.setCreateTime(new Date());

        ActivityDO activityDO = new ActivityDO();
        BeanUtils.copyProperties(activityReq, activityDO);
        ActivityDO saveDO = activityRepository.save(activityDO);

        BeanUtils.copyProperties(saveDO, res);
        cacheService.cacheActivity(res);
        return res;
    }

    @Transactional
    public ActivityRes remove(ActivityReq activityReq) throws Exception {
        activityReq.setDf(1);
        ActivityRes activityRes = modify(activityReq);
        cacheService.delCacheActivity(activityRes.getId());
        return activityRes;
    }

    public List<ActivityRes> findAllNoPage() throws InstantiationException, IllegalAccessException {
        List<ActivityDO> records = activityRepository.findByDf(0);
        return ConvertUtils.convert(records, ActivityRes.class);
    }
}
