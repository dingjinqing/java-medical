<#include "/admin/header.ftl">
<link href="/css/admin/membership_card.css?v=1.1.5" rel="stylesheet" type="text/css" />
<style type="text/css">
    a:focus,
    a:hover {
        text-decoration: none;
    }

    .erweima {
        position: relative;
        display: inline-block;
    }

    .mc_usestate span {
        display: inline-block;
        transform: scale(0.8);
        transform-origin: left;
        padding: 2px 8px;
        border: 1px solid #fff;
        border-radius: 12px;
    }

    .card_box {
        float: left;
        margin-right: 1%;
        margin-bottom: 20px;
        background-color: #fff;
        border-radius: 10px;
        height: 255px;
        box-shadow: 0px 2px 16px 0px #e5e5e5;
    }

    .card_condition {
        margin: 0 7px;
        border-bottom: 1px solid #eee;
        padding-bottom: 10px;
    }

    .card_condition p:first-of-type {
        margin-top: 160px;
    }

    .card_condition p {
        margin-top: 5px;
        word-break: break-all;
        width: 256px;
        height: 32px;
        font-size: 12px;
        font-weight: 600;
    }

    .card_condition p span {
        font-weight: 400;
    }

    .card_info_box {
        padding: 0 10px;
    }

    .card_info_box img {
        float: left;
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }

    .card_time_name {
        margin-left: 50px;
    }
    .erweima{
        width:22px;
    }
</style>
<div class="title">
    <span>会员管理 / </span><span
        style="color: #666;"><#if ($tab_type==0)普通会员卡<#elseif>($tab_type==1)限次会员卡<#elseif>($tab_type==2)等级会员卡</#if></span>
</div>
<ul id="tab" class="tab_navs">
    <li <#if ($tab_type==0)class="active_card" </#if>><a href="#" data-toggle="tab"
            url="/admin/user/member/list?tab_type=0">普通会员卡</a>
    </li>
    <li <#if ($tab_type==1)class="active_card" </#if>><a href="#" data-edit="1" data-toggle="tab"
            url="/admin/user/member/list?tab_type=1">限次会员卡</a>
    </li>
    <li <#if ($tab_type==2)class="active_card" </#if>><a href="#" data-edit="2" data-toggle="tab"
            url="/admin/user/member/list?tab_type=2">等级会员卡</a>
    </li>
    {{--<li <#if ($tab_type==1)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/shop/user/wx_card">微信会员卡</a></li>--!}
</ul>
<div class="card_list">
    <div class="topBorder"></div>
    <#if ($tab_type == 2)
    <#if ($data_list)
    <#list ($data_list as $item)
    <div class="card_box clearfix">
        <div class="card_cont" id="${item->id!}" card_back="${item->bg_type!}" card_color="${item->bg_color!}"
            card_img="${item->bg_img!}" <#if ($item->flag==2) style="background:#ddd;" <#else> <#if ($item->bg_type==0 &&
            $item->bg_color!='')style="background:${item->bg_color!}" <#elseif>($item->bg_type==1 &&
            $item->bg_img!='')style='background: url("http://${image_domain!}/${item->bg_img!}")
            no-repeat;background-size:254px;' </#if> </#if>>
            <#if ($item->flag==2)
            <img src="http://${image_domain!}/image/admin/card_no_use.png" class="card_no_use" />
            </#if>
            <img src="http://${image_domain!}/image/admin/card_no_use.png" class="card_no_use"
                style="display: none;" />
            <div class="card_info_box clearfix">
                <img src="${shop_logo->shop_avatar!}" alt="">
                <div class="card_time_name">
                    <span style="color:#fff">${item->card_name!}</span>
                    <span class="mc_name_span" style="color:#fff;">{{strtoupper($item->grade)!}</span>
                    <!-- <span>等级会员卡</span> -->
                </div>
                <div class="mc_grade">
                    升级条件：
                    <#if (json_decode($item->grade_condition)->grade_score)
                    <p style="text-indent: 2em;">累积获得积分达到{{json_decode($item->grade_condition)->grade_score!}分 </p>
                    </#if>
                    <#if (json_decode($item->grade_condition)->grade_money)
                    <p style="text-indent: 2em;">累积消费金额达到{{json_decode($item->grade_condition)->grade_money!}元</p>
                    </#if>
                </div>

                <div class="card_edit" style="position: absolute;right: 10px;bottom: 10px;" >
                    <a href="/admin/user/member/edit?id=${item->id!}&card_type=2" target="_blank"><img
                            src="http://${image_domain!}/image/admin/card_edit.png" alt="编辑"></a>
                    <#if ($item->flag ==1)
                    <div class="erweima">
                        <a href="##" class="hover_share" identity_id="${item->id!}" type="4"><img
                                src="http://${image_domain!}/image/admin/card_share_new.png" alt="分享"></a>
                    </div>
                        </#if>
                        <#if ($item->flag ==2 || $item->flag == 3)
                        <a href="#" class="del" card_id="${item->id!}"><img
                                src="http://${image_domain!}/image/admin/card_del.png" alt="删除"></a>
                        </#if>
                        <#if ($item->flag==1)
                        <a href="#" <#if ($item->flag==1) class="stop_use" </#if> card_id="${item->id!}"
                            flag="${item->flag!}" use_count="${item->use_count!}"><img
                                src="http://${image_domain!}/image/admin/card_disable.png" alt="停用"></a>
                        <#elseif>($item->flag==2)
                        <a href="#" class="start_use pause" card_id="${item->id!}" flag="${item->flag!}"
                            grade="1"><img src="http://${image_domain!}/image/admin/card_enable.png" alt="启用"></a>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="card_condition">
                <p style="height: 17px;">领取条件:
                    <span>
                        <#if ($item->activation == 0)
                        无需激活;
                        <#else>
                        需要激活;
                        <#if ($item->examine == 0)
                                无需审核;
                        <#else>
                        需要审核;
                        </#if>
                        </#if>
                    </span>
                </p>
                <p>会员权益:
                    <span>
                        <#if ($item->power == 0 && $item->discount > 0)
                        会员折扣${item->discount!}折;
                        </#if>
                        <#if ($item->pay_own_good == 1)
                        会员专享商品;
                        </#if>
                        <#if ($item->buy_score_flag)
                        积分奖励
                        </#if>
                    </span>
                </p>
            </div>
            <div class="mc_edit">
                <a href="/admin/user/manage/list?user_card=${item->id!}&amp;top_index=5">查看会员</a>
                <#if ($item->flag == 1 && $item->activation == 1 && $item->examine == 1)
                <a href="/admin/user/member/examine?card_id=${item->id!}&nav=1"> - 激活审核</a>
                </#if>
            </div>
        </div>
        </#list>
        </#if>
        <#else>
        <#if ($data_list)
        <#list ($data_list as $item)
        <div class="card_box clearfix">
            <div class="card_cont charge_count" id="${item->id!}" card_back="${item->bg_type!}"
                card_color="${item->bg_color!}" card_img="${item->bg_img!}" <#if ($item->flag==2 || $item->flag==3)
                style="background:#ddd;" <#else> <#if ($item->bg_type==0 &&
                $item->bg_color!='')style="background:${item->bg_color!}" <#elseif>($item->bg_type==1 &&
                $item->bg_img!='')style='background: url("http://${image_domain!}/${item->bg_img!}")
                no-repeat;background-size:254px;' </#if> </#if>>
                <#if ($item->flag==2)
                <img src="http://${image_domain!}/image/admin/card_no_use.png" class="card_no_use" />
                </#if>
                <div class="card_status">
                    <#if ($item->flag==1) 使用中
                    <#elseif>($item->flag==2) 停止使用
                    <#elseif>($item->flag==3) 已过期
                    </#if>
                </div>
                <img src="http://${image_domain!}/image/admin/card_no_use.png" class="card_no_use"
                    style="display: none;" />
                <div class="card_info_box clearfix">
                    <img src="${shop_logo->shop_avatar!}" alt="">
                    <div class="card_time_name">
                        <p class="card_name">${item->card_name!}</p>
                        <#if ($item->receive_day != "" && $item->expire_type == 1)
                        <div class="mc_youxiaoqi">
                            自领取之日起${item->receive_day!}<#if ($item->date_type == 0)日内有效
                            <#elseif>($item->date_type == 1)周内有效
                            <#elseif>($item->date_type == 2)月内有效
                            </#if>
                        </div>
                        </#if>
                        <#if ($item->expire_type == 0 && ($item->start_time !=""&& $item->end_time !=""))
                        <div class="mc_youxiaoqi">
                            <span>${item->start_time!}</span> - <span>${item->end_time!}</span>
                        </div>
                        </#if>
                        <#if ($item->expire_type == 2)
                        <div class="mc_youxiaoqi">永久有效</div>
                        </#if>
                    </div>
                    <div class="card_edit">
                        <a href="/admin/user/member/edit?id=${item->id!}&card_type=${tab_type!}&top_index=5"
                            target="_blank" class="edit"><img src="http://${image_domain!}/image/admin/card_edit.png"
                                alt="编辑"></a>
                        <#if ($item->flag ==1)
                        <div class="erweima">
                            <a href="##" class="hover_share" identity_id="${item->id!}" type="4"><img
                                    src="http://${image_domain!}/image/admin/card_share_new.png" alt="分享"></a>
                        </div>
                        </#if>
                        <#if ($item->flag ==2 || $item->flag == 3)
                        <a href="#" class="del" card_id="${item->id!}"><img
                                src="http://${image_domain!}/image/admin/card_del.png" alt="删除"></a>
                        </#if>

                        <#if ($item->flag==1)
                        <a href="#" class="pause" card_id="${item->id!}" flag="${item->flag!}"
                            <#if ($item->send_money == '' && $item->charge_money == '' && $item->card_type==0)
                            cardtype="zhekou" </#if> ><img src="http://${image_domain!}/image/admin/card_disable.png"
                                alt="停用"></a>
                        <#elseif>($item->flag==2)
                        <a href="#" class="start_use pause" card_id="${item->id!}" flag="${item->flag!}"
                            <#if ($item->send_money == '' && $item->charge_money == '' && $item->card_type==0)
                            cardtype="zhekou" </#if> ><img src="http://${image_domain!}/image/admin/card_enable.png"
                                alt="启用"></a>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="card_condition">
                <p style="height: 17px;">领取条件:
                    <span>
                        <#if ($item->is_pay == 0)
                        直接领取;
                        <#elseif>($item->is_pay == 1)
                        <#if ($item->pay_type == 0)
                        现金购买;
                        <#else>
                        积分购买;
                        </#if>
                        <#elseif>($item->is_pay == 2)
                        <#if ($item->receive_action == 1)
                        领取码;
                        <#else>
                        卡号密码;
                        </#if>
                        </#if>
                        <#if ($item->activation == 0)
                        无需激活;
                        <#else>
                        需要激活;
                        <#if ($item->examine == 0)
                                    无需审核;
                        <#else>
                        需要审核;
                        </#if>
                        </#if>
                    </span>
                </p>
                <p>会员权益:
                    <span>
                        <#if ($item->power == 0 && $item->discount > 0)
                        会员折扣${item->discount!}折;
                        </#if>
                        <#if ($item->pay_own_good == 1)
                        会员专享商品;
                        </#if>
                        <#if ($item->card_type == 1 && $item->store_list != -1)
                        门店兑换${item->count ?? 0!}次;
                        </#if>
                        <#if ($item->card_type == 1 && $item->is_exchang != 0)
                        商品兑换${item->exchang_count ?? 0!}次;
                        </#if>
                        <#if ($item->charge_money != '')
                        充值奖励;
                        </#if>
                        <#if ($item->buy_score != '')
                        积分奖励;
                        </#if>
                        <#if ($item->card_type == 1 && $item->store_list == -1 && $item->is_exchang == 0)
                        无
                        </#if>
                    </span>
                </p>
            </div>
            <div class="mc_edit">
                <a href="/admin/user/manage/list?user_card=${item->id!}&top_index=5">查看会员</a>
                <#if ($item->flag == 1 && $item->activation == 1 && $item->examine == 1)
                <a href="/admin/user/member/examine?card_id=${item->id!}&nav=1"> - 激活审核 </a>
                </#if>
                <#if ($item->is_pay ==2)
                <a href="/admin/user/member/code/receivelist?top_index=5&card_id=${item->id!}"> - 领取详情</a>
                </#if>
                <#if ($item->card_type == 1)
                <a href="/admin/user/member/exchang/order?card_id=${item->id!}"> - 查看订单</a>
                </#if>
            </div>
        </div>

        </#list>
        </#if>
        </#if>
        <div class="new_card" <#if ($tab_type==2 && count($data_list)>=9) hidden </#if>>
            <a <#if ($tab_type==0)href="/admin/user/member/create?top_index=5" <#elseif>($tab_type==1)
                href="/admin/user/member/create?card_type=1&top_index=5" <#elseif>($tab_type==2)
                href="/admin/user/member/create?card_type=2&top_index=5" </#if>
                style="width: 100%;height: 100%;display: block;" target="_blank">
                <img src="http://${image_domain!}/image/admin/add_card.png" alt=""
                    style="margin-left: 40%;margin-top: 10%">
                <div class="btn_create" style="margin-top: 5%">添加会员卡</div>
            </a>

        </div>
    </div>
    <div id="set_stop_use">
        <div class="stop_prompt">
            共<span class="use_count">98</span>位会员拥有该卡
            {{--停用后不可再启用，您可选择停用该卡后，拥有该卡的用户批量更换为其他等级卡，也可直接停用，若直接停用本卡则已经领取的等级用户卡列表也会停用该等级卡，不在享受等级权限--!}
        </div>
        <div class="grade_stop_radio">
            <label for="direct_stop">
                <input type="radio" name="grade_flag" value="0" id="direct_stop" checked />
                直接停用
            </label>
            <label for="change_stop">
                <input type="radio" name="grade_flag" value="1" id="change_stop" />
                停用后置换为其他等级卡
            </label>
        </div>
        <div class="grade_stop_select1 grade_no_content">
            停用后拥有该卡用户默认设置为:
            <select name="new_card_id" id="new_card_id">
                <option value="">请选择会员卡</option>
                <#list ($grade_card as $item)
                <option value="${item->id!}" grade="${item->grade!}">${item->card_name!}</option>
                </#list>
            </select>
            <div>直接停用本卡，拥有本卡的用户批量更换为设置的新等级卡</div>
        </div>
        <div class="grade_stop_select0 grade_no_content">
            直接停用本卡，则已经领取的等级用户也会停用该等级卡，不再享受等级会员权限
        </div>
    </div>
    
    <#include ("admin.share_common")
    <#include "/admin/footer.ftl">
    <script type="text/javascript">
        //版本控制不启用
        var version_charge = "${version_charge!}";
        var version_count = "${version_count!}";
        if (version_charge == -1) {
            $(".charge_count .pause").each(function (i, v) {
                if ($(v).attr("cardtype") != "zhekou") {
                    $(v).removeClass("pause");
                }
            });
        }
        if (version_count == -1) {
            $(".charge_count .pause").each(function (i, v) {
                if ($(v).attr("cardtype") != "zhekou") {
                    $(v).removeClass("pause");
                }
                //            $(v).removeClass("pause");
            });
        }
        // tab切换
        $("[data-toggle='tab']").click(function () {
            if ($(this).attr('data-edit') == 1) {
                var data = getPowerInfo('main_config', 'count_card', 'sub_3', '限次会员卡', 1);
                //console.log(data);
                if (data.content == -1) {
                    return;
                }
            }
            if ($(this).attr('data-edit') == 2) {
                var data = getPowerInfo('main_config', 'grade_card', 'sub_3', '等级会员卡', 1);
                //console.log(data);
                if (data.content == -1) {
                    return;
                }
            }
            var url = $(this).attr("url");
            if (url != undefined) {
                window.location.href = url;
            }
        });

        $("#main-table").FixedHead({ tableLayout: "fixed" });

        $(document).on("click", ".pause", function () {
            var obj = $(this);
            var card_id = $(this).attr('card_id');
            var flag = $(this).attr('flag') == 1 ? 2 : 1;
            var param = { id: card_id, act: 'pause', 'flag': flag };
            //alert(JSON.stringify(param));
            util.ajax_json('/admin/user/member/list', function (d) {
                if (d && d.error == 0) {
                    if (d.content) {
                        console.log(obj)
                        var flag = obj.attr('flag') == 1 ? 2 : 1;
                        var card_back = obj.parent().parent().parent().attr('card_back');
                        var card_color = obj.parent().parent().parent().attr('card_color');
                        var card_img = obj.parent().parent().parent().attr('card_img');
                        obj.attr('flag', flag);
                        obj.html(flag == 1 ? '<img src="http://${image_domain!}/image/admin/card_disable.png" alt="停用">' : '<img src="http://${image_domain!}/image/admin/card_enable.png" alt="启用">');
                        if (flag == 1) {
                            obj.removeClass('start_use');
                            if (card_back == 0 && card_color != '') {
                                obj.parent().parent().parent().css('background', card_color);
                            }
                            if (card_back == 0 && card_color == '') {
                                obj.parent().parent().parent().css('background', '#e6cb96');
                            }
                            if (card_back == 1) {
                                obj.parent().parent().parent().css('background', 'url("http://${image_domain!}/' + card_img + '") no-repeat');
                                obj.parent().parent().parent().css('background-size', '254pxt');
                            }
                            obj.parent().parent().parent().find('.card_no_use').hide();
                            if (obj.attr('grade') != 1) {
                                obj.prev().html('<img src="http://${image_domain!}/image/admin/card_share_new.png" alt="分享">');
                            }
                        } else {
                            obj.addClass('start_use');
                            obj.parent().parent().parent().css('background', '#ddd');
                            obj.parent().parent().parent().find('.card_no_use').show();
                            obj.prev().find('.hover_share').html('<img src="http://${image_domain!}/image/admin/card_del.png" alt="删除">');
                        }

                        obj.parent().parent().parent().find('.card_status').text(flag == 1 ? '使用中' : '停止使用');
                        util.mobile_alert('操作成功');
                        location.reload();
                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                }
            }, param);
        });
        $('.btn_copy').click(function (e) {
            e.preventDefault();
            let prev = $(this).prev();
            prev[0].select();
            document.execCommand("Copy");
        })
        $(".del").click(function () {
            var _this = $(this);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    var param = { del: _this.attr("card_id") };
                    util.ajax_json('/admin/user/member/list', function (d) {
                        if (d && d.error == 0) {
                            location.reload();
                        }
                    }, param);
                    // $('input[name="del"]').val(_this.attr("card_id"));
                    // $("#form1").submit();
                    layer.close(index);
                });
            });
        })
    </script>
    <script type="text/javascript">
        var tab_type = "${tab_type!}";
        if (tab_type == 1) {
            getPowerInfo('main_config', 'count_card', 'sub_3', '限次会员卡', 0);
        } else if (tab_type == 2) {
            getPowerInfo('main_config', 'grade_card', 'sub_3', '等级会员卡', 0);
        }
        $('.stop_use').click(function () {
            var obj = $(this);
            $("#set_stop_use").find('.use_count').text(obj.attr('use_count'));
            $("#set_stop_use").find("option").each(function (i, v) {
                if ($(v).val() == obj.attr('card_id')) {
                    $(v).css("display", 'none');
                }
            });
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 1
                    , title: ['提示', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['360px', '245px']
                    , content: $('#set_stop_use') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , skin: 'grad_demo'
                    , yes: function (index, layero) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        if ($('#set_stop_use input[name="grade_flag"]:checked').val() == 1 && $('#new_card_id').val() == '') {
                            util.mobile_alert("请选择要替换的等级卡！");
                            return false;
                        }
                        var card_id = obj.attr('card_id');
                        var flag = 2;
                        var param = { id: card_id, act: 'pause2', 'flag': flag, grade_flag: $('#set_stop_use input[name="grade_flag"]:checked').val(), new_card_id: $('#new_card_id').val() };
                        //alert(JSON.stringify(param));
                        util.ajax_json('/admin/user/member/list', function (d) {
                            if (d && d.error == 0) {
                                if (d.content) {
                                    var flag = obj.attr('flag') == 1 ? 2 : 1;
                                    var card_back = obj.parent().parent().parent().attr('card_back');
                                    var card_color = obj.parent().parent().parent().attr('card_color');
                                    var card_img = obj.parent().parent().parent().attr('card_img');
                                    obj.attr('flag', flag);
                                    if (flag == 1) {
                                        obj.removeClass('start_use');
                                        if (card_back == 0 && card_color != '') {
                                            obj.parent().parent().parent().css('background', card_color);
                                        }
                                        if (card_back == 0 && card_color == '') {
                                            obj.parent().parent().parent().css('background', '#e6cb96');
                                        }
                                        if (card_back == 1) {
                                            obj.parent().parent().parent().css('background', 'url("http://${image_domain!}/' + card_img + '") no-repeat');
                                            obj.parent().parent().parent().css('background-size', '254pxt');
                                        }
                                        obj.parent().parent().parent().find('.card_no_use').hide();
                                    } else {
                                        obj.addClass('start_use');
                                        obj.parent().parent().parent().css('background', '#ddd');
                                        obj.parent().parent().parent().find('.card_no_use').show();
                                    }
                                    obj.removeClass("stop_use");
                                    obj.addClass("pause");
                                    util.mobile_alert('操作成功');
                                    location.reload();
                                } else if (d && d.error > 0) {
                                    layer.msg(d.message);
                                }
                            }
                        }, param);
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                    }
                });
            });
        });
        $('input[name="grade_flag"]').click(function () {
            if ($(this).val() == 1) {
                $('.grade_stop_select1').show();
                $('.grade_stop_select0').hide();
            } else {
                $('.grade_stop_select1').hide();
                $('.grade_stop_select0').show();
            }
        });
        $('.card_edit').on('mouseenter', 'a>img', function () {
            $(this).after($('<span>').attr('class', 'card_tips').text($(this).attr('alt')));
        })
        $('.card_edit').on('mouseout', '', function () {
            $(this).parent().find('.card_tips').remove();
        })
    </script>