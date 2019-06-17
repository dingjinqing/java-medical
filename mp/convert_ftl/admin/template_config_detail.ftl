<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/template_config.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .right_name{
        height: 30px;
        line-height: 30px;
        padding-left: 12px;
    }
    .right_textarea{
        padding: 10px 0 0 12px;
        height: 120px;
    }
    .tem_right_link{
        width: auto;
    }
    .tem_right_send{
        margin-top: 0;
    }
    .tem_right_send input[type="text"]{
        height: 30px;
        padding-left: 12px;
        margin: 0 0 0 5px;
    }
    .detail_ul{
        border: 1px solid #eee;
        background: #fff;
        padding: 0px 10px 10px 30px;
        margin: 10px 0;
    }
    .detail_ul li{
        list-style-type: disc;
        margin: 10px 0 0 0;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span><a href="/admin/market/message/template/list?top_index=4">消息模板</a></span> / </span><span style="color: #666;">查看消息模板</span>
</div>
<div class="main-container">
    <div class="template_info">
        {{--<ul class="custom_title clearfix">--!}
            {{--<li><a href="##">查看消息模板</a></li>--!}
        {{--</ul>--!}
        <div class="template_content" style="margin-top: 0;padding-top: 15px;">
            <div class="prompt">
                <img src="http://${image_domain!}/image/admin/notice_img.png" />
                <span>每人每天最多发送一条自定义模板消息，请勿发送骚扰信息，违者将受到微信官方相应处罚，严重者将被封禁小程序</span>
            </div>
            <div class="clearfix">
                <div class="template_left">
                    <div class="left_title"></div>
                    <div class="left_info">
                        <div class="left_template">
                            <div class="left_temlpate_top">
                                <img src="http://${image_domain!}/image/admin/shop_logo_default.png" width="20px" style="margin-top: -3px;" />
                                <span>此处为小程序的名称</span>
                                <span class="fr">...</span>
                            </div>
                            <div class="left_template_center">
                                <div class="left_tem_type">商家活动通知</div>
                                <div class="left_tem_time">2018年6月11日</div>
                                <div class="left_tem_prompt">业务标题</div>
                                <div class="left_tem_title">${detail->title!}</div>
                                <div class="left_tem_content clearfix">
                                    <div class="fl">业务内容</div>
                                    <div class="fr">${detail->content!}</div>
                                </div>
                            </div>
                            <div class="left_bottom">进入小程序查看</div>
                            <div class="left_bottom">拒收通知</div>
                        </div>
                    </div>
                </div>
                <div class="template_right">
                    <ul class="template_right_ul">
                        <li class="clearfix">
                            <div class="fl">
                                <em>*</em><span>消息名称：</span>
                            </div>
                            <div class="tem_right">
                                <div class="right_ipt right_name">
                                    ${detail->name!}
                                </div>
                                <div class="right_prompt">只作为商家记录使用，用户不会看到这个名称</div>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div class="fl">
                                <em>*</em><span>消息类型：</span>
                            </div>
                            <div class="tem_right">
                                <div class="right_ipt right_name">
                                    商家活动通知
                                </div>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div class="fl">
                                <em>*</em><span>业务标题：</span>
                            </div>
                            <div class="tem_right">
                                <div class="right_ipt right_name">
                                    ${detail->title!}
                                </div>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div class="fl">
                                <em>*</em><span>业务内容：</span>
                            </div>
                            <div class="tem_right">
                                <div class="choose_template">
                                    <div class="right_ipt right_textarea" style="border:none">
                                        ${detail->content!}
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div class="fl">
                                <em>*</em><span>进入小程序查看：</span>
                            </div>
                            <div class="tem_right">
                                <div class="tem_right_link">${detail->page_link!}</div>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div class="fl">
                                <em>*</em><span>参与活动人群：</span>
                            </div>
                            <div class="tem_right" style="margin-top: 6px;">
                                <div class="right_prompt" style="margin: 0;">以下筛选条件为“或”关系</div>
                                <ul class="detail_ul">
                                    <#if ($sendConition['custom_box'])
                                        <#if ($sendConition['have_pay'])
                                        <li class="screen_list">
                                            <span>${sendConition['have_pay']!}</span>
                                            <span>天内有交易记录</span>
                                        </li>
                                        </#if>
                                        <#if ($sendConition['no_pay'])
                                        <li class="screen_list">
                                            <span>${sendConition['no_pay']!}</span>
                                            <span>天内无交易记录</span>
                                        </li>
                                        </#if>
                                        <#if ($sendConition['min_count'])
                                        <li class="screen_list screen_list1">
                                            <span>累积购买次数大于</span>
                                            <span>${sendConition['min_count']!}</span>
                                            <span>次</span>
                                        </li>
                                        </#if>
                                        <#if ($sendConition['max_count'])
                                        <li class="screen_list screen_list1">
                                            <span>累积购买次数小于</span>
                                            <span>${sendConition['max_count']!}</span>
                                            <span>次</span>
                                        </li>
                                        </#if>
                                        <#if ($sendConition['min_ave_price'])
                                        <li class="screen_list screen_list1">
                                            <span>购买商品均价大于</span>
                                            <span>${sendConition['min_ave_price']!}</span>
                                            <span>元</span>
                                        </li>
                                        </#if>

                                        <#if ($sendConition['max_ave_price'])
                                        <li class="screen_list screen_list1">
                                            <span>购买商品均价小于</span>
                                            <span>${sendConition['max_ave_price']!}</span>
                                            <span>元</span>
                                        </li>
                                        </#if>
                                    

                                        <#if ($sendConition['point_start_time'] && $sendConition['point_end_time'])
                                        <li class="screen_list screen_list1">
                                            <span>指定</span>
                                            <span>${sendConition['point_start_time']!}--${sendConition['point_end_time']!}</span>
                                            <span>有登录记录的</span>
                                        </li>
                                        </#if>
                                    </#if>

                                    <#if ($sendConition['cart_box'] == 1)
                                    <li class="screen_list screen_list1">
                                        <span>30天内在本店内有加入购物车行为，但没有支付的用户</span>
                                    </li>
                                    </#if>
                                    <#if ($sendConition['goods_box'] == 1 && $sendConition['goods_ids'])
                                    <li class="screen_list screen_list1">
                                        <span>指定购买商品人群</span>
                                    </li>
                                    </#if>

                                    <#if ($sendConition['member_box'] == 1 && $sendConition['send_member'])
                                    <li class="screen_list screen_list1">
                                        <span>指定的会员</span>
                                    </li>
                                    </#if>

                                    <#if ($sendConition['card_list'])
                                    <li class="screen_list_card screen_list2">
                                        <#list  ($sendConition['card_list'] as $item)
                                        <div>
                                            <span>持有</span>
                                            <span>${cardList[$item]!}</span>
                                            <span>会员卡</span>
                                        </div>
                                        </#list>
                                    </li>
                                    </#if>
                                    <#if ($sendConition['tag_list'])
                                    <li class="screen_list_label screen_list2">
                                        <#list  ($sendConition['tag_list'] as $item)
                                        <div>
                                            <span>属于</span>
                                            <span>${tagList[$item]!}</span>
                                            <span>会员标签</span>
                                        </div>
                                        </#list>
                                    </li>
                                    </#if>
                                </ul>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div class="fl">
                                <em>*</em><span>发送时间：</span>
                            </div>
                            <div class="tem_right">
                            <#if  ($detail->send_action == 1)
                                <div class="tem_right_send" style="margin: 0;">
                                    <label for="now_send">
                                        <input id="now_send" value="1" type="radio" name="send_action" checked />
                                        立即发送
                                    </label>
                                </div>
                            </#if>
                            <#if  ($detail->send_action == 2)
                                <div class="tem_right_send">
                                    <label for="continue_send">
                                        <input id="continue_send" value="2" type="radio" name="send_action" checked/>
                                        持续发送
                                    </label>
                                    <div>
                                        <input type="text" placeholder="请选择日期" id="startDate" name="start_time" value="${detail->start_time!}" readonly />
                                        至
                                        <input type="text" placeholder="请选择日期" id="endDate" name="end_time" value="${detail->end_time!}" readonly />
                                    </div>
                                    <div class="right_prompt">所有可送达的用户均会第一时间收到一次此消息</div>
                                </div>
                            </#if>
                            <#if  ($detail->send_action == 3)
                                <div class="tem_right_send">
                                    <label for="timing_send">
                                        <input id="timing_send" value="3" type="radio" name="send_action" <#if  ($detail->send_action == 3) checked </#if> />
                                        定时发送
                                        <input type="text" placeholder="请选择日期" name="task_start_time" value="${detail->start_time!}" readonly />
                                    </label>
                                </div>
                            </#if>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        {{--<div class="template_footer">
            <a class="save" href="">返回</a>
        </div>--!}
    </div>
</div>
<script type="text/javascript">
    $('.template_footer').width($('.template_info').width());
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','message_template','sub_4','消息推送',0);
</script>
