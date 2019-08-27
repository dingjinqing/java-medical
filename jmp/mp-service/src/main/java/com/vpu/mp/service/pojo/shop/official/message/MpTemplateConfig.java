package com.vpu.mp.service.pojo.shop.official.message;

import java.util.List;
import java.util.Map;

import com.vpu.mp.service.foundation.util.RegexUtil;
import lombok.Builder;
import lombok.Getter;

/**
 * 公众号模版消息
 * @author 卢光耀
 * @date 2019-08-22 10:22
 *
*/
@Getter
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
            "{{first.DATA}}回复者：{{keyword1.DATA}}回复时间：{{keyword2.DATA}}回复内容：{{keyword3.DATA}}{{remark.DATA}}");
	
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
    private String[][] colors;

    MpTemplateConfig(String templateNo,String title,String content,String[][] color){
        this.templateNo = templateNo;
        this.title = title;
        this.content = content;
        this.colors = color;
    }
    MpTemplateConfig(String templateNo,String title,String content){
        this.templateNo = templateNo;
        this.title = title;
        this.content = content;
        List<String> list = RegexUtil.getSubStrList("{{",".",content);
        this.colors = new String[list.size()][2];
        for (int i = 0,len = list.size(); i < len; i++) {
            this.colors[i][0] = list.get(i);
            this.colors[i][0] = "#173177";
        }
    }
}
