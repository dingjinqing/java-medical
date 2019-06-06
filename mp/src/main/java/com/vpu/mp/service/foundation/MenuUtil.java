package com.vpu.mp.service.foundation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description 菜单工具类，只支持2级菜单
 * @author 新国
 *
 */
public class MenuUtil {
	@Data
	@AllArgsConstructor
	public static class MenuIndex {
		public Integer topIndex;
		public Integer subIndex;
	}

	@Data
	@NoArgsConstructor
	public static class Menu {
		public String name;
		public String enName;
		public String linkUrl;
		public String imageUrl;
		public String imageHoverUrl;
		public boolean check = false;
		public boolean hide = false;
		public boolean disableSetRoleFirstLink = false;
		public boolean subMenuHasRole = false;
		public String recommendPic;
		public String subGroupName;
		public Integer topIndex;
		public String[] privilgeNames;
		public List<Menu> subMenu;
	};

	/**
	 * 得到权限对应菜单
	 * 
	 * @param menuJsonPath
	 * @param authorityJsonPath
	 * @param privilegeList
	 * @return
	 */
	public static List<Menu> getRoleMenu(String menuJsonPath, String authorityJsonPath, String[] privilegeList) {
		List<Menu> menuList = loadMenu(menuJsonPath);
		if (privilegeList == null) {
			return menuList;
		}
		ArrayList<String> roleLinkList = getRoleLinkList(authorityJsonPath, privilegeList);
		checkMenuList(menuList,null,roleLinkList,privilegeList);
		return menuList;
	}

	/**
	 * 标记菜单权限
	 * @param menuList
	 * @param parentMenu
	 * @param roleLinkList
	 * @param privilegeList
	 */
	protected static void checkMenuList(List<Menu> menuList, Menu parentMenu, ArrayList<String> roleLinkList,
			String[] privilegeList) {
		Boolean isSetMenuLink = false;
		for (Menu menu : menuList) {
			if (Arrays.asList(privilegeList).indexOf(menu.getEnName()) != -1) {
				menu.setCheck(true);
			}
			if (roleLinkList != null) {
				// 角色权限前缀法则确定权限
				String linkPrefix = menu.getLinkUrl().split("\\?")[0];
				for (String linkUrl : roleLinkList) {
					if (linkUrl.equals(linkPrefix) || linkPrefix.startsWith(linkUrl + "/")) {
						menu.setCheck(true);
					}
				}
			}

			// 子菜单有权限，主菜单标记
			if (menu.isCheck() && parentMenu != null) {
				parentMenu.setSubMenuHasRole(true);
			}

			// 子菜单的第一个有权限链接赋值给主菜单
			if (parentMenu != null && !isSetMenuLink && !parentMenu.isDisableSetRoleFirstLink() && menu.isCheck()) {
				parentMenu.setLinkUrl(menu.getLinkUrl());
				isSetMenuLink = true;
			}
			
			if(menu.getSubMenu() !=null) {
				checkMenuList(menu.subMenu,menu,roleLinkList,privilegeList);
			}
			
		}
	}

	/**
	 * 得到路径对应菜单选中索引
	 * 
	 * @param menuList
	 * @param uri
	 * @param reqTopIndex
	 * @param reqSubIndex
	 * @return
	 */
	public static MenuIndex getMenuIndex(List<Menu> menuList, String uri, Integer reqTopIndex, Integer reqSubIndex) {
		MenuIndex menuIndex = new MenuIndex(0, 0);
		Boolean setTopIndex = false;
		Boolean setSubIndex = false;
		if (reqTopIndex != null && reqTopIndex >= 0 && reqTopIndex < menuList.size()) {
			menuIndex.setTopIndex(reqTopIndex);
			setTopIndex = true;
		}

		if (reqSubIndex != null && reqSubIndex >= 0) {
			menuIndex.setTopIndex(reqSubIndex);
			setSubIndex = true;
		}
		if (setTopIndex && setSubIndex) {
			return menuIndex;
		}

		String[] uriSegments = uri.split("\\?")[0].split("\\/");
		Integer maxMatches = 0;
		for (int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			if (menu.getSubMenu() != null) {
				for (int j = 0; j < menu.getSubMenu().size(); j++) {
					Menu subMenu = menu.getSubMenu().get(j);
					int matches = matcheNumber(uriSegments, subMenu.getLinkUrl());
					if (matches > maxMatches) {
						maxMatches = matches;
						menuIndex.setTopIndex(i);
						menuIndex.setSubIndex(j);
					}
				}
			}
		}
		return menuIndex;
	}

	/**
	 * URI最大匹配数量
	 * 
	 * @param uriSegments
	 * @param menuLinkUrl
	 * @return
	 */
	protected static int matcheNumber(String[] uriSegments, String menuLinkUrl) {
		String[] linkSegments = menuLinkUrl.split("\\?")[0].split("\\/");
		int matches = 0;
		for (int i = 0; i < uriSegments.length; i++) {
			if (linkSegments.length - 1 > i && uriSegments[i].equals(linkSegments[i])) {
				matches++;
			}
		}
		return matches;
	}

	/**
	 * 得到角色权限对应菜单前缀URL列表
	 * 
	 * @param privilegeList
	 * @return
	 */
	protected static ArrayList<String> getRoleLinkList(String authorityJsonPath, String[] privilegeList) {
		if (authorityJsonPath == null) {
			return null;
		}
		List<Menu> authorityMenu = loadMenu(authorityJsonPath);
		ArrayList<String> roleLinkList = new ArrayList<String>();
		for (String privilege : privilegeList) {
			for (Menu menu : authorityMenu) {
				List<String> names = Arrays.asList(menu.getPrivilgeNames());
				if (menu.getEnName().equals(privilege) || names.indexOf(privilege) != -1) {
					if (menu.getLinkUrl() != null && roleLinkList.indexOf(menu.getLinkUrl()) == -1) {
						roleLinkList.add(menu.getLinkUrl());
					}
					break;
				}
			}
		}
		return roleLinkList;
	}

	/**
	 * 判断路径是否有权限
	 * 
	 * @param menuList
	 * @param path
	 * @return
	 */
	public static Boolean isRoleAccess(List<Menu> menuList, String path) {
		for (Menu menu : menuList) {
			int result = checkMenuItemPrivilege(menu, path);
			if (result == 1) {
				return true;
			}
			if (result == -11) {
				return false;
			}

		}
		return true;
	}

	/**
	 * 检查菜单项路径的权限
	 * 
	 * @param menu
	 * @param path
	 * @return 1 代表符合路径规则，且有菜单访问权限 -1 代表符合路径规则，且没有菜单访问权限 0 代表不符合路径规则
	 */
	public static int checkMenuItemPrivilege(Menu menu, String path) {
		String linkUrl = menu.getLinkUrl().split("\\?")[0];
		String linkPrefix = path.split("\\?")[0];
		String segment = "/";
		if (linkUrl.equals(linkPrefix) || linkPrefix.startsWith(linkUrl + segment)) {
			if (menu.getSubMenu() != null) {
				return menu.isSubMenuHasRole() ? 1 : -1;
			}
			return menu.isCheck() ? 1 : -1;
		}
		return 0;
	}

	/**
	 * 加载JSON菜单
	 * 
	 * @param jsonPath
	 * @return
	 */
	public static List<Menu> loadMenu(String jsonPath) {
		String json = Util.loadResource(jsonPath);
		try {
			ArrayList<?> list = Util.parseJSON(json, ArrayList.class);
			return convertMenuList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static List<Menu> convertMenuList(Object arrayList) {
		if (arrayList == null) {
			return null;
		}
		try {
			List<Menu> menuList = new ArrayList<Menu>();
			ArrayList<?> list = (ArrayList<?>) arrayList;
			for (int i = 0; i < list.size(); i++) {
				Menu menu = convertMenu(list.get(i));
				if (menu != null) {
					menuList.add(menu);
				}
			}
			return menuList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected static Menu convertMenu(Object item) {
		if (item == null) {
			return null;
		}
		try {
			Map<String, Object> map = (Map<String, Object>) item;
			Menu menu = new Menu();
			menu.setEnName(Util.convert(map.get("enName"), String.class, ""));
			menu.setName(Util.convert(map.get("name"), String.class, ""));
			menu.setImageUrl(Util.convert(map.get("imageUrl"), String.class, ""));
			menu.setImageHoverUrl(Util.convert(map.get("imageHoverUrl"), String.class, ""));
			menu.setLinkUrl(Util.convert(map.get("linkUrl"), String.class, ""));
			menu.setRecommendPic(Util.convert(map.get("recommendPic"), String.class, ""));
			menu.setSubGroupName(Util.convert(map.get("subGroupName"), String.class, ""));
			menu.setHide(Util.convert(map.get("hide"), Boolean.class, false));
			menu.setDisableSetRoleFirstLink(Util.convert(map.get("disableSetRoleFirstLink"), Boolean.class, false));
			menu.setTopIndex(Util.convert(map.get("topIndex"), Integer.class, 0));
			menu.setPrivilgeNames(Util.convert(map.get("privilgeNames"), String[].class, null));
			menu.setCheck(false);
			menu.setSubMenuHasRole(false);
			menu.setSubMenu(convertMenuList(map.get("subMenu")));
			return menu;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
