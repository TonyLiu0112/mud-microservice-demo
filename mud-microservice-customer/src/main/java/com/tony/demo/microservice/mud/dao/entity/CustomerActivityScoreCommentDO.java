package com.tony.demo.microservice.mud.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer_activity_score_cmt")
public class CustomerActivityScoreCommentDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long customerActivityScoreId;

    private Long userPartyId;

    private String content;

    private Date time;

    private Integer good;

    private String uniqueCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerActivityScoreId() {
        return customerActivityScoreId;
    }

    public void setCustomerActivityScoreId(Long customerActivityScoreId) {
        this.customerActivityScoreId = customerActivityScoreId;
    }

    public Long getUserPartyId() {
        return userPartyId;
    }

    public void setUserPartyId(Long userPartyId) {
        this.userPartyId = userPartyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode == null ? null : uniqueCode.trim();
    }
}