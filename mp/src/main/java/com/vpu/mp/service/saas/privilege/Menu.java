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
}
