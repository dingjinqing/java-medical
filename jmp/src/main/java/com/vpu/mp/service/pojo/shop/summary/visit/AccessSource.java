package com.vpu.mp.service.pojo.shop.summary.visit;

import com.vpu.mp.service.pojo.shop.summary.ChartInfo;

/**
 * 访问来源 key 对应关系
 *
 * @author 郑保乐
 */
public enum AccessSource implements ChartInfo {

    MP_HISTORY_LIST(1, "小程序历史列表"),
    SEARCH(2, "搜索"),
    SESSION(3, "会话"),
    QR_CODE_SCAN(4, "扫一扫二维码"),
    OFFICIAL_ACCOUNT_HOME_PAGE(5, "公众号主页"),
    CHAT_TOP(6, "聊天顶部"),
    SYSTEM_DESKTOP(7, "系统桌面"),
    MP_HOME_PAGE(8, "小程序主页"),
    NEARBY_MP(9, "附近的小程序"),
    OTHER(10, "其他"),
    TEMPLATE_MSG(11, "模板消息"),
    OFFICIAL_ACCOUNT_MENU(13, "公众号菜单"),
    APP_SHARE(14, "APP分享"),
    PAY_FINISH_PAGE(15, "支付完成页"),
    LONG_PRESS_QR_CODE(16, "长按识别二维码"),
    GALLERY_QR_CODE(17, "相册选取二维码"),
    OFFICIAL_ACCOUNT_ARTICLE(18, "公众号文章"),
    CASH_WALLET(19, "钱包"),
    CARD_WALLET(20, "卡包"),
    OTHER_MP(22, "其他小程序"),
    OTHER_MP_CALLBACK(23, "其他小程序返回"),
    COUPON_SHOP_LIST(24, "卡券适用门店列表"),
    SEARCH_INPUT_SHORTCUT_ENTRY(25, "搜索框快捷入口"),
    MP_CUSTOM_SERVICE_MSG(26, "小程序客服消息"),
    OFFICIAL_ACCOUNT_BROADCAST(27, "公众号下发"),
    TASK_BAR_HISTORY(29, "任务栏-最近使用"),
    LONG_PRESS_MP_MENU_ROUND_POINT(30, "长按小程序菜单圆点"),
    WIFI_CONNECTED_PAGE(31, "连wifi成功页"),
    CITY_SERVICE(32, "城市服务"),
    WX_AD(33, "微信广告"),
    OTHER_MOBILE_APP(34, "其他移动应用"),
    DISCOVER_ENTRY_MY_MP(35, "发现入口-我的小程序"),
    TASK_BAR_MY_MP(36, "任务栏-我的小程序");

    private Integer index;
    private String source;

    AccessSource(Integer index, String source) {
        this.index = index;
        this.source = source;
    }

    public Integer getIndex() {
        return index;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String getName() {
        return getSource();
    }

    @Override
    public Integer getKey() {
        return index;
    }
}
