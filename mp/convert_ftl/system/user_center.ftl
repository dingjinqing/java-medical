<#include ("system.header")
<link rel="stylesheet" href="/css/system/shop_pv.css" type="text/css" />
<link rel="stylesheet" href="/css/system/user_list.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/user_center.css?v=1.0.2" type="text/css" />

<div class="main-container">
    <div class="info">
        <div class="info_title">
            <span>基本信息</span>
            <span class="info_edit basic_info_edit">编辑</span>
        </div>
        <div class="info_content basic_info_content">
            <div class="basic_main clearfix">
                <div class="user_head">
                    <img <#if ($user->user_avatar) src="${user->user_avatar!}" <#else> src="/image/admin/head_icon.png" </#if>/>
                </div>
                <div class="user_basic">
                    <ul>
                        <li class="clearfix">
                            <div class="user_nickname">
                                昵称：${user->username!}
                            </div>
                        </li>
                        <li class="clearfix">
                            <div>
                                姓名：<#if ($user->real_name) ${user->real_name!} <#else> 未知 </#if>
                            </div>
                            <div>
                                邀请人：<#if ($user->invite_name) ${user->invite_name!} <#else> 暂无 </#if>
                            </div>
                            <div>成为客户：{{substr($user->create_time,0,10)!}
                                <#if ($user->create_time_text <= 1)(1天内)
                                <#elseif>(1< $user->create_time_text && $user->create_time_text <= 7)(${user->create_time_text!}天内)
                                <#elseif>(30 >= $user->create_time_text && $user->create_time_text >7)
                                    (1个月内)
                                <#elseif>($user->create_time_text>30)
                                    ({{intval($user->create_time_text/30)!}个月内)
                                <#elseif>($user->create_time_text>365)
                                    ({{intval($user->create_time_text/365)!}年内)
                                </#if>
                            </div>
                        </li>
                        <li class="clearfix">
                            <div>最近浏览：{{substr($user->last_login_time,0,10)!}
                                <#if ($user->last_login_time)
                                    <#if ($user->last_login_time_text <= 1)(1天内)
                                    <#elseif>(1< $user->last_login_time_text && $user->last_login_time_text <= 7)(${user->last_login_time_text!}天内)
                                    <#elseif>(30 >= $user->last_login_time_text && $user->last_login_time_text >7)
                                        (1个月内)
                                    <#elseif>($user->last_login_time_text>30)
                                        ({{intval($user->last_login_time_text/30)!}个月内)
                                    <#elseif>($user->last_login_time_text>365)
                                        ({{intval($user->last_login_time_text/365)!}年内)
                                    </#if>
                                <#else>
                                    暂未浏览
                                </#if>
                            </div>
                            <div>手机号：<#if ($user->mobile) ${user->mobile!} <#else> 未知 </#if></div>
                            <div>openid：<#if ($user->wx_openid) ${user->wx_openid!} <#else> 未知 </#if></div>
                        </li>
                    </ul>
                    <ul class="info_hide">
                        <li class="clearfix">
                            <div>来源渠道：${user->source!}</div>
                            <div>生日：<#if ($user->birthday_year > 0)${user->birthday_year!}-${user->birthday_month!}-${user->birthday_day!} <#else> 未知 </#if></div>
                            <div>教育程度：<#if ($user->education > 0 ) ${education[$user->education]!} <#else> 未知 </#if></div>
                            <div>常住地：<#if ($user->province)${user->province!} ${user->city!} ${user->district!}<#else> 未知 </#if></div>
                        </li>
                        <li class="clearfix">
                            <div>所在行业：<#if ($user->industry_info > 0)${industry[$user->industry_info]!} <#else> 未知 </#if></div>
                            <div>婚姻状况：<#if ($user->marital_status==1)未婚 <#elseif>($user->marital_status==2)已婚 <#elseif>($user->marital_status==3)保密 <#else> 未知 </#if></div>
                            <div>月收入：<#if ($user->monthly_income > 0) ${income[$user->monthly_income]!} <#else> 未知 </#if></div>
                            <div>身份证：<#if ($user->cid)${user->cid!} <#else> 未知 </#if></div>
                        </li>
                        <li class="clearfix user_address">
                            <div style="width: 195px;">性别：<#if ($user->sex == 'f') 女 <#elseif>($user->sex == 'm') 男 <#else> 未知 </#if></div>
                            <#if (count($user_address)>0)
                            <div>地址：</div>
                            <ul style="font-size: 12px;">
                                <#list ($user_address as $item)
                                <li address_id="${item->address_id!}">${item->complete_address!}</li>
                                </#list>
                            </ul>
                            <#else>
                            <div>地址：暂未添加</div>
                            </#if>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="look_more_info">查看更多</div>
        </div>
    </div>
    <div class="info">
        <div class="info_title">
            <span>标签信息</span>
            <span class="info_edit label_info_edit" data-method="offset" data-type="label1">编辑</span>
        </div>
        <div class="info_content label_info_content">
            <#list ($user_tag as $item)
            <span tag_id="${item->tag_id!}">${item->tag_name!}</span>
            </#list>
        </div>
    </div>
    <div class="info">
        <div class="info_title">
            <span>资产信息</span>
        </div>
        <div class="money_info_content">
            <ul class="clearfix">
                <li class="clearfix li-label">
                    <div class="asset_icon">
                        <img src="http://${image_domain!}/image/admin/asset1.png" />
                    </div>
                    <div class="asset_type">
                        <span>普通卡</span>
                        <span class="shu">|</span>
                        <span class="asset_type_set asset_common_set card_set" card_type="0">设置</span>
                        <div class="card_num" style="color: #4869dc;cursor:pointer"  onclick="javascript:location.href='/system/user/card/list?shop_id=${shop_id!}&card_type=0&user_id=${user->user_id!}'">{{count($user_comment_card)!}</div>
                        <div class="hide comment_card_list">
                            <#list ($user_comment_card as $item)
                                <span card_id="${item->card_id!}" card_no="${item->card_no!}">${item->card_name!}</span>
                            </#list>
                        </div>
                    </div>
                </li>
                <li class="clearfix li-label">
                    <div class="asset_icon">
                        <img src="http://${image_domain!}/image/admin/asset2.png" />
                    </div>
                    <div class="asset_type">
                        <span>限次卡</span>
                        <span class="shu">|</span>
                        <span class="asset_type_set asset_limit_set card_set" card_type="1">设置</span>
                        <div class="card_num" style="color: #f44854;cursor:pointer" onclick="javascript:location.href='/system/user/card/list?shop_id=${shop_id!}&card_type=1&user_id=${user->user_id!}'">{{count($user_count_card)!}</div>
                        <div class="hide count_card_list">
                            <#list ($user_count_card as $item)
                                <span card_id="${item->card_id!}" card_no="${item->card_no!}">${item->card_name!}</span>
                            </#list>
                        </div>
                    </div>
                </li>

                <li class="clearfix li-label">
                    <div class="asset_icon">
                        <img src="http://${image_domain!}/image/admin/grade_card_icon.png" />
                    </div>
                    <div class="asset_type">
                        <span>等级卡</span>
                        <span class="shu">|</span>
                        <span class="asset_type_set asset_account_set">设置</span>
                        <div style="color: #ef9f3e;cursor:pointer" onclick="javascript:location.href='/system/user/account/list?shop_id=${shop_id!}&user_id=${user->user_id!}'">${user->account!}</div>
                    </div>
                </li>


                <li class="clearfix li-label">
                    <div class="asset_icon">
                        <img src="http://${image_domain!}/image/admin/asset3.png" />
                    </div>
                    <div class="asset_type">
                        <span>储值余额</span>
                        <span class="shu">|</span>
                        <span class="asset_type_set asset_account_set">设置</span>
                        <div style="color: #ef9f3e;cursor:pointer" onclick="javascript:location.href='/system/user/account/list?shop_id=${shop_id!}&user_id=${user->user_id!}'">${user->account!}</div>
                    </div>
                </li>
                <li class="clearfix li-label">
                    <div class="asset_icon">
                        <img src="http://${image_domain!}/image/admin/asset5.png" />
                    </div>
                    <div class="asset_type">
                        <span>可用积分</span>
                        <span class="shu">|</span>
                        <span class="asset_type_set asset_score_set">设置</span>
                        <div style="color: #e158aa; cursor:pointer" onclick="javascript:location.href='/system/user/score/list?shop_id=${shop_id!}&user_id=${user->user_id!}'">${user->score!}</div>
                    </div>
                </li>
                <li class="clearfix li-label">
                    <div class="asset_icon">
                        <img src="http://${image_domain!}/image/admin/asset4.png" />
                    </div>
                    <div class="asset_type">
                        <span>可用优惠券数</span>
                        {{--<span class="shu">|</span>--!}
                        {{--<span class="asset_type_set asset_coupon_set">设置</span>--!}
                        <div style="color: #2a9edb;cursor:pointer" onclick="javascript:location.href='/system/coupon/get/list?shop_id=${shop_id!}&type=1&user_id=${user->user_id!}'">{{count($coupon)!}</div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="info">
        <div class="info_title">
            <span>交易统计</span>
            <a href="/system/order/list?shop_id=${shop_id!}&top_index=3&user_id=${user->user_id!}" class="info_edit count_info_edit">订单列表</a>
        </div>
        <div class="count_info_content">
            <div>
                <p>最近下单时间</p>
                <div>
                    <#if (empty($order[0]->add_time))
                        暂未下单
                    <#else>
                        <#if ($last_add_order <= 1)1天内
                        <#elseif>(1< $last_add_order && $last_add_order <= 7)${last_add_order!}天内
                        <#elseif>(30 >= $last_add_order && $last_add_order >7)
                             1个月内
                        <#elseif>($last_add_order>30)
                            {{intval($last_add_order/30)!}个月内
                        <#elseif>($last_add_order>365)
                            {{intval($last_add_order/365)!}年内
                        </#if>
                    </#if>
                </div>
            </div>
            <div>
                <p>客单价</p>
                <div>
                    <#if (count($order) < 1)
                        ￥ 0.00
                        <#elseif>(count($order)>1)
                        ￥ {{number_format($order_money/count($order),2)!}
                </#if></div>
            </div>
            <div>
                <p>累计消费金额</p>
                <div>￥{{number_format($order_money,2)!}</div>
            </div>
            <div>
                <p>累计消费订单数</p>
                <div>{{count($order)!}</div>
            </div>
            <div>
                <p>累计退款金额</p>
                <div>￥{{number_format($retun_order_money,2)!}</div>
            </div>
            <div>
                <p>累计退款订单数</p>
                <div>{{count($return_order)!}</div>
            </div>
        </div>
    </div>
</div>

<div id="set-money" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <div class="exchange_old">
        <span>当前金额:</span>
        <input type="text" class="money_dis" disabled />
    </div>
    <div style="margin-bottom: 10px;">
        <span>增加金额:</span>
        <input type="text" class="amount" value=""  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
        <span>（*当余额为正时，增加余额；余额为负时，减少余额*）</span>
    </div>
    <div>
        <span>增加备注:</span>
        <input type="text" class="remark" value="" size="200" style="width: 450px;"/>
    </div>
</div>
<div id="set-integral" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <div class="exchange_old">
        <span>当前积分:</span>
        <input type="text" class="score_dis" disabled />
    </div>
    <div style="margin-bottom: 10px;">
        <span>增加积分:</span>
        <input type="text" class="score" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
        <span>（*当积分为正时，增加积分；积分为负时，减少积分*）</span>
    </div>
    <div>
        <span>增加备注:</span>
        <input type="text" class="remark" value="" size="200" style="width: 450px;"/>
    </div>
</div>
<div id="set-label">
    <label style="color: #a3a3a3;font-size: 12px;">
        一个用户最多可以打5个标签，超过数量的标签将不再被添加给该用户
    </label>
    <div>

    </div>
    <ul>
        <li><input type="text" placeholder="搜索标签" class="ipt_search" /></li>
        <#if ($tag)
            <#list ($tag as $item)
                <li class="label_list" tag_id="${item->tag_id!}" data-title="${item->tag_name!}">
                    ${item->tag_name!}
                </li>
            </#list>
        <#else>
            <li>你还没有设置过标签<a href="/system/tag/list">标签管理</a></li>
        </#if>
    </ul>
</div>
<div id="set-card">
    <p>你可以在这里编辑该会员的会员卡信息，添加/删除会员卡</p>
    <div class="clearfix">
        <div class="fl">会员卡</div>
        <ul class="set_card_ul">
            <input type="hidden" class="user_card"/>

            <li class="set_card_last set_comment_card" style="display:none">
                <#if ($comment_card)
                    <button class="btn_add_card">添加</button>
                </#if>
                <select class="card_clone comment_clone" style="height:25px;">
                    <option value="" class="card_sel">选择会员卡</option>
                    <#list ($comment_card as $item)
                        <option value="${item->id!}" class="option">${item->card_name!}</option>
                    </#list>
                </select>
            </li>
            <li class="set_card_last set_count_card" style="display:none">
                <#if ($count_card)
                    <button class="btn_add_card">添加</button>
                </#if>
                <select class="card_clone count_clone" style="height:25px;">
                    <option value="" class="card_sel">选择会员卡</option>
                    <#list ($count_card as $item)
                        <option value="${item->id!}" class="option">${item->card_name!}</option>
                    </#list>
                </select>
            </li>
        </ul>
    </div>
</div>
<select class="card_clone2">
    <option value="" class="card_sel">选择会员卡</option>
    <#list ($comment_card as $item)
        <option value="${item->id!}" class="option">${item->card_name!}</option>
    </#list>
</select>
<select class="card_clone3">
    <option value="" class="card_sel">选择会员卡</option>
    <#list ($count_card as $item)
        <option value="${item->id!}" class="option">${item->card_name!}</option>
    </#list>
</select>

<script type="text/javascript">
    var info_flag = 0;
    $('.look_more_info').click(function () {
        if(info_flag == 0){
            $('.info_hide').show();
            $(this).html('收起');
            info_flag = 1;
        }else if(info_flag == 1){
            $('.info_hide').hide();
            $(this).html('查看更多');
            info_flag = 0;
        }
    });
    var user_id = "${user->user_id!}"
</script>

<script type="text/javascript" src="/js/admin/user_center.js?v=1.0.3"></script>
<#include ("system.footer")