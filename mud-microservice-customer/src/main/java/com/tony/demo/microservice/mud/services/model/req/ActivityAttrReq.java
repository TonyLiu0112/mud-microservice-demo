package com.tony.demo.microservice.mud.services.model.req;

public class ActivityAttrReq {
	private Long id;
	
	private Long activityId;
	
	private String attrValue;
	
	private String attrName;

	private Integer attrType;
	
	private Integer df;
	
	public Integer getDf() {
		return df;
	}

	public void setDf(Integer df) {
		this.df = df;
	}

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

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public Integer getAttrType() {
		return attrType;
	}

	public void setAttrType(Integer attrType) {
		this.attrType = attrType;
	}
}
