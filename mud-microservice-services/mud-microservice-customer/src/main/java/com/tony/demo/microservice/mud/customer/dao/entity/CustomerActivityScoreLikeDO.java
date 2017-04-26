package com.tony.demo.microservice.mud.customer.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_activity_score_like")
public class CustomerActivityScoreLikeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long scoreId;

    private Long userPartyId;

    private String uniqueCode;

    private Date likeTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getUserPartyId() {
        return userPartyId;
    }

    public void setUserPartyId(Long userPartyId) {
        this.userPartyId = userPartyId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode == null ? null : uniqueCode.trim();
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }
}