package com.vpu.mp.service.pojo.shop.official.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.vpu.mp.service.foundation.util.RegexUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公众号模版消息
 * @author 卢光耀
 * @date 2019-08-22 10:22
 *
*/
public enum MpTemplateConfig {
    /**
     * 消息推送
     */
    ACTIVITY_CONFIG(
        "OPENTM415477060",
        "业务处理结果通知",
        "{{first.DATA}}业务类型：{{keyword1.DATA}}业务内容：{{keyword2.DATA}}处理结果：" +
            "{{keyword3.DATA}}操作时间：{{keyword4.DATA}}{{remark.DATA}}"),

    PUSHMSG(
            "OPENTM411665252",
            "反馈结果通知",
            "{{first.DATA}}回复者：{{keyword1.DATA}}回复时间：{{keyword2.DATA}}回复内容：{{keyword3.DATA}}{{remark.DATA}}"),

    COUPON_EXPIRE(
            "OPENTM408237933",
            "服务到期提醒",
            "{{first.DATA}}业务号码：{{keyword1.DATA}}业务类型：{{keyword2.DATA}}到期时间：{{keyword3.DATA}}{{remark.DATA}}"),

    APPOINTMENT_REMINDER(
            "OPENTM414204481",
            "预约到期提醒",
            "{{first.DATA}}单据号：{{keyword1.DATA}}开始时间：{{keyword2.DATA}}{{remark.DATA}}"),

    APPOINTMENT_SUCCESS(
        "OPENTM410419150",
        "预约成功通知",
        "{{first.DATA}}服务名称：{{keyword1.DATA}}预约日期：{{keyword2.DATA}}预约人：{{keyword3.DATA}}联系电话：{{keyword4.DATA}}预约机构：{{keyword5.DATA}}{{remark.DATA}}"),

    PAYMENT_REMINDER(
            "OPENTM401751289",
            "订单待付款提醒",
            "{{first.DATA}}订单编号：{{keyword1.DATA}}支付金额：{{keyword2.DATA}}{{remark.DATA}}"),

    LOTTERY_RESULTS(
            "OPENTM412181311",
            "抽奖结果通知",
            "{{first.DATA}}奖品名称：{{keyword1.DATA}}中奖时间：{{keyword2.DATA}}{{remark.DATA}}"),

    GROUP_BUY_RESULTS(
            "OPENTM414768654",
            "拼团结果提醒",
            "{{first.DATA}}拼团状态：{{keyword1.DATA}}商品明细：{{keyword2.DATA}}拼团价格：{{keyword3.DATA}}剩余人数：{{keyword4.DATA}}时间：{{keyword5.DATA}}{{remark.DATA}}"),

    SERVICE_ORDER_CANCEL(
            "OPENTM207847150",
                "预约取消通知",
                "{{first.DATA}}预约项目：{{keyword1.DATA}}预约时间：{{keyword2.DATA}}取消原因：{{keyword3.DATA}}{{remark.DATA}}"),
    GROUP_SUCCESS(
            "OPENTM400932513",
            "拼团成功通知",
            "{{first.DATA}}商品名称：{{keyword1.DATA}}团长：{{keyword2.DATA}}成团人数：{{keyword3.DATA}}{{remark.DATA}}");
	/**
	 * 模板编号
	 */
	private String templateNo;

	/**
	 * 标题
	 */
    private String title;

	/**
	 * 内容，样例： 您好，您已购买成功。商品信息：{{name.DATA}}{{remark.DATA}}
	 */
    private String content;
    /**
     * 颜色设置
     */
    private Map<String,String> colors;



    @JsonCreator
    public static MpTemplateConfig getConfig(String templateNo){
        for(MpTemplateConfig item : values()){
            if(item.getTemplateNo().equals(templateNo) ){
                return item;
            }
        }
        return null;
    }

    MpTemplateConfig(String templateNo,String title,String content,String[][] color){
        final Map<String, String> map = new HashMap<>((int) (color.length * 1.5));
        this.templateNo = templateNo;
        this.title = title;
        this.content = content;
        List<String> list = RegexUtil.getSubStrList("{{",".",content);
        for(String s: list){
            map.put(s,"#173177");
        }
        for( int i =0, len = color.length; i<len;i++ ){
            String[] object = color[i];
            map.put(object[0],object[1]);
        }
        this.colors = map;
    }
    MpTemplateConfig(String templateNo,String title,String content){
        List<String> list = RegexUtil.getSubStrList("{{",".",content);
        final Map<String, String> map = new HashMap<>((int) (list.size() * 1.5));
        this.templateNo = templateNo;
        this.title = title;
        this.content = content;
        for(String s: list){
            map.put(s,"#173177");
        }
        this.colors = map;
    }
    @JsonValue
    public String getTemplateNo() {
        return templateNo;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Map<String,String> getColors() {
        return colors;
    }
}
