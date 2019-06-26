package com.ticket.scan.dao;

public class AuthorityInfo {

	/** 店铺id */
	private String id;
	/** 店铺名称 */
	private String name;
	/** 店铺LOGO */
	private String logo;
	/** 店铺简介 */
	private String intro;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
}
