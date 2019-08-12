package com.vpu.mp.service.pojo.shop.market.message;


import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 消息推送通用查询类
 * @author 卢光耀
 * @date 2019-08-09 10:02
 *
*/
@Data
public class MessageTemplateQuery extends BasePageParam {
    /** 用户ID */
    private Integer userId;
    /** 用户昵称 */
    private String userName;
    /** 用户手机号 */
    private String userMobile;
    /** 是否关注公众号 */
    private Boolean isConcernWP;
    /** 消息名称 */
    private String messageName;
    /** 业务标题 */
    private String businessTitle;
    /** 发送时间 */
    private Timestamp sendTime;
    /** 筛选日期 */
    private Integer screeningDate;
    /** 发送类型 */
    private Integer sendType;
    /** 用户是否已点击 */
    private Boolean isOnClick;
}
