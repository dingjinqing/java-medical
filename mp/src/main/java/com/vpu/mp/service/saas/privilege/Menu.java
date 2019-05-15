package com.vpu.mp.service.saas.privilege;

import java.util.List;

public class Menu {
	public String name;
	public String enName;
	public String linkUrl;
	public String imageUrl;
	public String imageHUrl;
	public boolean check;
	public List<Menu> subMenu;
	
	public Menu(String name, String enName, String linkUrl, String imageUrl, String imageHUrl, boolean check,
			List<Menu> subMenu) {
		super();
		this.name = name;
		this.enName = enName;
		this.linkUrl = linkUrl;
		this.imageUrl = imageUrl;
		this.imageHUrl = imageHUrl;
		this.check = check;
		this.subMenu = subMenu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageHUrl() {
		return imageHUrl;
	}

	public void setImageHUrl(String imageHUrl) {
		this.imageHUrl = imageHUrl;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public List<Menu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	
	
	
}
