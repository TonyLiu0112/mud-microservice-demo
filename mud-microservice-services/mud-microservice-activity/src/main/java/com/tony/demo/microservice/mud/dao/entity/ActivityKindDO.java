package com.tony.demo.microservice.mud.dao.entity;

import javax.persistence.*;

@Entity
@Table(name = "activity_kind")
public class ActivityKindDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long activityId;

    private Long activityKindId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityKindId() {
        return activityKindId;
    }

    public void setActivityKindId(Long activityKindId) {
        this.activityKindId = activityKindId;
    }
}