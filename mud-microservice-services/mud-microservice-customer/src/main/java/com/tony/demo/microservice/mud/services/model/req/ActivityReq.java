package com.tony.demo.microservice.mud.services.model.req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ActivityReq {
	private long id;
	
	private String name;
	
	private String code;
	
	private Date createTime;
	
	private Integer state;
	
	private Integer type;
	
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm",timezone = "GMT+8")
	private Date activityTime;
	
	private String url;
	
	private Integer df;
	
	private String title;
	
	private String description;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getActivityTime() {
		return activityTime;
	}

	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDf() {
		return df;
	}

	public void setDf(Integer df) {
		this.df = df;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
