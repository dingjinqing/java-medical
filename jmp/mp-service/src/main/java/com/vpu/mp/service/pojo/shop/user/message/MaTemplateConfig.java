package com.vpu.mp.service.pojo.shop.user.message;

import com.vpu.mp.service.foundation.util.RegexUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * 小程序消息模版类
 * @author 卢光耀
 * @date 2019-08-23 10:06
 *
*/
@Getter
public enum MaTemplateConfig {
    /**
     * 消息推送
     */
    ACTIVITY_CONFIG(
        "AT0654",
        "申请进度通知",
        "业务名称{{keyword1.DATA}}业务状态{{keyword2.DATA}}",
        new Integer[]{1, 2},
                    1);


    private String id;
    private String title;
    private String content;
    private Integer[] keywordIds;
    private Integer emphasisKeywordSn;
    private String[][] colors;

    MaTemplateConfig(String id,String title,String content,Integer[] keywordIds,
                     Integer emphasisKeywordSn,String[][] colors){
        this.id = id;
        this.title = title;
        this.content = content;
        this.keywordIds = keywordIds;
        this.emphasisKeywordSn = emphasisKeywordSn;
        this.colors = colors;
    }
    MaTemplateConfig(String id,String title,String content,Integer[] keywordIds,
                     Integer emphasisKeywordSn){
        this.id = id;
        this.title = title;
        this.content = content;
        this.keywordIds = keywordIds;
        this.emphasisKeywordSn = emphasisKeywordSn;
        List<String> list = RegexUtil.getSubStrList("{{",".",content);
        this.colors = new String[list.size()][2];
        for (int i = 0,len = list.size(); i < len; i++) {
            this.colors[i][0] = list.get(i);
            this.colors[i][0] = "#173177";
        }
    }
}
