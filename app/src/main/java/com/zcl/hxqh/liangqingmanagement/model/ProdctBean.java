package com.zcl.hxqh.liangqingmanagement.model;

import java.io.Serializable;

public class ProdctBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String appid;
	private int url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public int getUrl() {
		return url;
	}

	public void setUrl(int url) {
		this.url = url;
	}

	public ProdctBean(String name,String appid, int url) {
		super();
		this.name = name;
		this.appid = appid;
		this.url = url;
	}

}
