<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/check_order.css?v=1.0.0" type="text/css"/>
<style>
    [name="pay_time"]{
        margin-left:0 !important;
    }
    .name-img {
        width: 60px;
        height: 60px;
        float: left;
        border-radius: 2px;
        border: 1px solid #ccc;
        margin: 2px 7px 0 0;
    }
    .list-name {
        display: block;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 3;
        overflow: hidden;
        text-align:left;
    }
    .check_tb_body td {
        border: 1px solid #eee;
        color: #666;
        padding: 15px 10px;
        text-align: center;
    }
    .return_order ,.get_return{
        color: #5a8bff;
        cursor: pointer;
        margin-top: 10px;
        display: block;
    }
    .order-tb-head {
        background: #f5f5f5;
        border: 1px solid #eee;
    }
    .order-tb-head span {
        display: inline-block;
        color: #666;
    }
    .span1 {
        margin-left: 20px;
    }
    .span2 {
        margin-left: 50px;
    }
    .span3 {
        margin-left: 60px;
    }
    .check_info_li .fl input{
        width:200px;
    }
    .check_info_li .fl{
        width:315px;
    }
    .check_info_li .fl select {
        width: 200px;
        border: 1px solid #ccc;
        height: 30px;
        padding-left: 12px;
    }
    .btn-choose, .btn-excel {
        width: 85px;
        height: 30px;
        border: 1px solid #ccc;
        background: #f5f5f5;
        color: #666;
        margin-left: 15px;
    }
    .btn-excel:hover {
        background-color: #fff !important;
        border-color: #447af9 !important;
        color: #447af9;
        text-decoration: none;
    }
    .return_info{
        text-align: center;
        line-height:55px;
        padding: 10px 0;
        border-bottom:1px solid rgb(238, 238, 238);
    }
    .return_content{
        text-align: left;
        padding:16px;
        line-height:25px;
        border-bottom:1px solid rgb(238, 238, 238);
    }
    .return_info input{
        height: 30px;
        text-align:center;
        border-radius: 2px;
        border: 1px solid #ccc;
    }
    .clearfix{
        zoom: 1;
    }
    .clearfix::after{
        content: '';
        visibility: hidden;
        display: block;
        font-size: 0;
        clear: both;
        height: 0;
    }
    .check_info_li .time_choose {
        width: 570px;
    }
    .return_tips{
        color:#999;
    }
    .have_border{
        padding-bottom: 10px;
        border-bottom: 1px solid #eee;
    }
    .order_banner ul li:hover{
        cursor: pointer;
    }
</style>
<div class="title">
    <span>订单管理 / </span><span style="color: #666;">会员卡订单</span>
</div>

    <div class="order-container">
        <div class="order_banner">
            <ul class="clearfix">
                <li <#if ($type_nav == 0) class="active" </#if> id="usercardorder_li">会员卡订单</li>
                <li <#if ($type_nav == 1) class="active" </#if> id="couponpackorder_li">优惠券礼包订单</li>
            </ul>
        </div>

<#if ($type_nav == 0)
<form action="/admin/orders/card/list" method="post" id="form1">
            {{ csrf_field()!}

        {{--会员卡订单--!}
    <input name="act" id="act" value="" hide>
    <input name="return_flag" id="return_flag" value="${request['return_flag']!}" hide>
        <div class="order_serarch_info" style="padding-bottom: 0">
            <ul class="clearfix">
                <li class="check_info_li clearfix">
                    <div class="fl">
                        <span>下单用户信息</span>
                        <input type="text" value="${request['user_info']!}" name="user_info" placeholder="请输入下单用户姓名/手机号">
                    </div>
                    <div class="fl">
                        <span>订单号</span>
                        <input type="text" value="${request['order_sn']!}" name="order_sn" placeholder="请输入订单号">
                    </div>
                    <div class="fl">
                    <span>会员卡号</span>
                        <input type="text" value="${request['card_no']!}" name="card_no" placeholder="">
                    </div>
                </li>
                <li class="check_info_li clearfix">
                    <div class="fl time_choose">
                        <span>下单时间</span>
                        <input type="text" name="start_time" placeholder="" value="${request['start_time']!}" onclick="picker();" autocomplete="off">
                        -
                        <input type="text" name="end_time" placeholder="" value="${request['end_time']!}" onclick="picker();" autocomplete="off">
                    </div>
                    <div class="fl">
                        <span>会员卡类型</span>
                        <select name="card_type" id="">
                            <option value="" selected>请选择会员卡类型</option>
                            <option value="1" <#if ($request['card_type'] == 1) selected </#if>>普通会员卡</option>
                            <option value="2" <#if ($request['card_type'] == 2) selected </#if>>限次会员卡</option>
                        </select>
                    </div>
                    <div class="fl" style="width: 205px;">
                        <button type="button" class="btn_search">筛选</button>
                        <button type="button" class="btn-excel btn_excel">导出表格</button>
                    </div>
                </li>
            </ul>
        </div>
        <div class="check_list">
            <ul class="cl_banner clearfix">
                <li <#if ($request['return_flag'] != 1) class="nav_active" </#if>>
                    <a href="/admin/orders/card/list">全部</a>
                </li>
                <li <#if ($request['return_flag'] == 1) class="nav_active" </#if>>
                    <a href="/admin/orders/card/list?return_flag=1">退款订单</a>
                </li>
            </ul>
            <div class="checklist_table">
                <table width="100%">
                    <thead>
                        <tr>
                            <td width="25%">商品</td>
                            <td width="10%">会员卡号</td>
                            <td width="10%">单价</td>
                            <td width="15%">下单用户信息</td>
                            <td width="15%">下单时间</td>
                            <td width="10%">订单状态</td>
                            <td width="10%">支付金额</td>
                        </tr>
                        <tr class="jiange">
                            <td colspan="4"></td>
                        </tr>
                    </thead>
                    <tbody>
                        <#list ($data_list as $item)
                        <tr class="order-tb-head">
                            <td colspan="8">
                                <span class="span1">
                                    <span style="color: #999;">订单号：</span>${item->order_sn!}
                                </span>
                            </td>
                        </tr>
                        <tr class="check_tb_body">
                            <td>
                                <span>
                                    <span class="list-name">
                                        <#if ($item->card_type == 0)
                                        <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">普通会员卡</span>
                                        <#elseif>($item->card_type == 1)
                                        <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">限次会员卡</span>
                                        </#if>
                                        <span style="margin-left: 5px;">
                                            <a href="/admin/user/member/edit?id=${item->card_id!}&card_type=${item->card_type!}&top_index=5">${item->card_name!}</a>
                                        </span>
                                    </span>
                                </span>
                            </td>
                            <td>${item->card_no!}</td>
                            <td><#if ($item->use_score > 0) ${item->order_amount*100!} 积分 <#else> ${item->order_amount!}元  </#if></td>
                            <td><a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #5A8BFF;">${item->username!}<br/>${item->mobile!}</a></td>
                            <td>${item->add_time!}</td>
                            <#if ($item->return_flag < 2)
                                <td>订单完成<span class="return_order" order_id="${item->order_id!}" money_paid="${item->money_paid!}" use_account="${item->use_account!}" use_score="${item->use_score!}"  pay_type="${item->pay_type!}" order_id="${item->order_id!}" order_amount="${item->order_amount!}">手动退款</span></td>
                            <#else>
                                <td>已退款<span class="get_return" use_score="${item->use_score!}" pay_type="${item->pay_type!}" return_score="${item->return_score!}" return_account="${item->return_account!}" return_money="${item->return_money!}" return_time="${item->return_time!}">退款详情</span></td>
                            </#if>
                            <td>${item->money_paid!}元</td>
                        </tr>
                        <tr>
                            <td clcolspan="8"></td>
                        </tr>
                       </#list>
                    </tbody>
                </table>
            </div>
            <div class="clearfix">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
</form>
</#if>

<#if ($type_nav == 1)
<form action="/admin/orders/couponpackage/list" method="post" id="form2">
    {{ csrf_field()!}

    {{--优惠券礼包订单--!}
    <input name="act" id="act" value="" hide>
    <input name="return_flag" id="return_flag" value="${request['return_flag']!}" hide>
    <div class="order_serarch_info" style="padding-bottom: 0">
        <ul class="clearfix">
            <li class="check_info_li clearfix">
                <div class="fl">
                    <span>优惠券包</span>
                    <input type="text" value="${request['pack_name']!}" name="pack_name">
                </div>
                <div class="fl">
                    <span>订单号</span>
                    <input type="text" value="${request['order_sn']!}" name="order_sn" placeholder="请输入订单号">
                </div>
                <div class="fl" style="margin-left: 40px">
                    <span>下单用户信息</span>
                    <input type="text" value="${request['user_info']!}" name="card_no" placeholder="">
                </div>
            </li>
            <li class="check_info_li clearfix">
                <div class="fl time_choose">
                    <span>下单时间</span>
                    <input type="text" name="start_time" placeholder="" value="${request['start_time']!}" onclick="picker();" autocomplete="off">
                    -
                    <input type="text" name="end_time" placeholder="" value="${request['end_time']!}" onclick="picker();" autocomplete="off">
                </div>
                <div class="fl" style="width: 205px;">
                    <button type="button" class="btn_search btn_search_2">筛选</button>
                    <button type="button" class="btn-excel btn_excel_2">导出表格</button>
                </div>
            </li>
        </ul>
    </div>
    <div class="check_list">
        <ul class="cl_banner clearfix">
            <li <#if ($request['return_flag'] != 1) class="nav_active" </#if>>
                <a href="/admin/orders/couponpackage/list?top_index=3">全部</a>
            </li>
            <li <#if ($request['return_flag'] == 1) class="nav_active" </#if>>
                <a href="/admin/orders/couponpackage/list?top_index=3&return_flag=1">退款订单</a>
            </li>
        </ul>
        <div class="checklist_table coupon_table">
            <table width="100%">
                <thead>
                <tr>
                    <td width="15%">优惠券包</td>
                    <td width="15%">订单号</td>
                    <td width="10%">单价</td>
                    <td width="15%">下单用户信息</td>
                    <td width="15%">下单时间</td>
                    <td width="10%">订单状态</td>
                    <td width="10%">支付金额</td>
                </tr>
                </thead>
                <tbody>
                <#list ($data_list as $item)
                    <tr>
                        <td width="15%">${item->pack_name!}</td>
                        <td width="15%">${item->order_sn!}</td>
                        <td width="10%">
                            <#if ($item->money_paid > 0 || $item->use_account > 0)
                                ${item->money_paid + $item->use_account!}元
                            <#elseif>($item->use_score > 0)
                                ${item->use_score!}积分
                            <#else>
                                免费
                            </#if>
                        </td>
                        <td width="15%">
                            <a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->username!}</a><br>
                            <a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->mobile!}</a>
                        </td>
                        <td width="15%">${item->add_time!}</td>
                        <td width="10%">
                            <#if ($item->return_flag < 2)
                                <div>订单完成</div>
                                <a href="##" class="manual_return" order_id="${item->order_id!}" money_paid="${item->money_paid!}" use_account="${item->use_account!}" use_score="${item->use_score!}" member_card_balance="${item->member_card_balance!}"  pay_code="${item->pay_code!}" order_id="${item->order_id!}" order_amount="${item->order_amount!}" surplus_amount="${item->surplus_amount!}">手动退款</a>
                            <#else>
                                <div>已退款</div>
                                <a href="##" class="retuen_detail" order_id="${item->order_id!}" return_money="${item->return_money!}" return_account="${item->return_account!}" return_score="${item->return_score!}"  pay_type="${item->pay_type!}" return_card_balance="${item->return_card_balance!}" return_time="${item->return_time!}">退款详情</a>
                            </#if>
                        </td>
                        <td width="10%">${item->money_paid + $item->use_account!}元</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="clearfix">
            <#include "/admin/jump_page_admin.ftl">
        </div>
    </div>
</form>
</#if>

    </div>

    {{--优惠礼包手动退款--!}
    <div id="manual_return_money" hidden>
        {{--退钱--!}
        <div class="return_infos coupon_return_money">
            <div class="each_line">请输入退款金额：</div>
            <div class="each_line">
                退余额：<input type="text" id="couponpack_return_account"> 元 <span class="return_tips">最多可退<span>10</span></span>
            </div>
            <div class="each_line">
                退会员卡余额：<input type="text" id="couponpack_return_member_card_balance"> 元 <span class="return_tips">最多可退<span>10</span></span>
            </div>
            <div class="each_line">
                退现金：<input type="text" id="couponpack_return_money"> 元 <span class="return_tips">最多可退<span>10</span></span>
            </div>

        </div>
        {{--退积分--!}
        <div class="return_infos coupon_return_score">
            <div class="each_line">请输入退款金额：</div>
            <div class="each_line">
                退积分：<input type="text" id="couponpack_return_score"> 积分 <span class="return_tips">最多可退<span>10</span></span>
            </div>
        </div>
        <div class="return_infos">
            <div class="each_line">
                <div>剩余<span id="surplus_amount_span">2</span>张优惠券未发放，是否继续发放：</div>
                <div class="if_send">
                    <label for="send_yes">
                        <input type="radio" name="if_send" value="0" checked id="send_yes"> 停止发放
                    </label>
                    <label for="send_no">
                        <input type="radio" name="if_send" value="1" id="send_no"> 继续发放
                    </label>
                </div>
            </div>
        </div>
    </div>
    {{--退款详情--!}
    <div id="return_detail" hidden>
        <div class="return_infos">
            <div class="each_line have_border return_detail_time">
                退款时间：<span></span>
            </div>
            <div class="each_line return_detail_account">
                已退余额：<span></span>元
            </div>
            <div class="each_line return_detail_card_balance">
                已退会员卡余额：<span></span>元
            </div>
            <div class="each_line return_detail_money">
                已退现金：<span></span>元
            </div>
            <div class="each_line return_detail_score">
                已退积分：<span></span>积分
            </div>
        </div>
    </div>
<script>
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    }

    $(".btn_excel").click(function () {
        $('#page').val(1);
        $("#act").val("export_csv");
        $("#form1").submit();
        $("#act").val('');
    })

    $(".btn_search").click(function () {
        $("#act").val('');
        $('#page').val(1);
        $("#form1").submit();
    })

    $(".btn_excel_2").click(function () {
        $('#page').val(1);
        $("#act").val("export_excel");
        $("#form2").submit();
        $("#act").val('');
    })

    $(".btn_search_2").click(function () {
        $("#act").val('');
        $('#page').val(1);
        $("#form2").submit();
    })

    $("#usercardorder_li").click(function(){
        window.location.href='/admin/orders/card/list?top_index=3';
    })
    $("#couponpackorder_li").click(function(){
        window.location.href='/admin/orders/couponpackage/list?top_index=3';
    })

    $(".return_order").on('click',function() {
        var order_amount = $(this).attr('order_amount');
        var pay_type = $(this).attr('pay_type');
        var use_score = $(this).attr('use_score');
        var use_account = $(this).attr('use_account');
        var money_paid = $(this).attr('money_paid');
        var order_id = $(this).attr('order_id');
        var content = '';
        if(use_score>0){
            content = '<div class="return_info">' +
                '积分：<input id="return_score"  value="'+use_score*100+'" name="return_score" placeholder="请输入退款积分" /> 积分' +
                '</div>';
        }else{
            content = '<div class="return_info">' +
                '<div>余额：<input id="return_account"  value="'+use_account+'" name="return_account" placeholder="请输入退款余额" /> 元' + '</div><div>' +
                '现金：<input id="return_money"  value="'+money_paid+'" name="return_money"  placeholder="请输入退款金额" /> 元' +
                '</div></div>';
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({title: ['请输入退款金额，并确认','text-align:center;padding: 0px;'],
                type: 1,
                offset: 'auto',
                area: '300px',
                btn: ['确定','取消'],
                btnAlign: 'r',
                content: content,
                success: function (layero, index) {

                }
                , yes:  function (index, layero) {

                    $("input[name='order_id']").val(order_id);
                    var msg = '';
                    var return_score = 0;
                    var return_account = 0;
                    var return_money = 0;
                    if(use_score>0) {
                        var return_score = $('#return_score').val();
                        if( parseFloat(return_score) > parseFloat(use_score*100)){
                            msg = '退款积分不能大于支付积分';
                        }
                    }else{
                        var return_account = $('#return_account').val();
                        var return_money = $('#return_money').val();
                        if( parseFloat(return_account) > parseFloat(use_account)){
                            msg = '退款余额不能大于支付余额';
                        }else if( parseFloat(return_money) > parseFloat(money_paid)){
                            msg = '退款现金不能大于支付现金';
                        }
                    }
                    if(msg != ''){
                        layer.msg(msg);
                    }else{
                        var param = {
                            order_id:order_id,
                            return_score:return_score,
                            return_account:return_account,
                            return_money:money_paid,

                        }
                        getAuthorityDetail(1,"","card_order_return","","card_order_return",param)
                        // util.ajax_json('/admin/orders/card/return',function (res) {
                        //     // console.log(res)
                        //     if (res.error == 0) {
                        //         util.mobile_alert('退款成功');
                        //         $("#form1").submit();
                        //         // window.location.href = '/admin/market/presale/list?nav=1';
                        //     } else {
                        //         util.mobile_alert(res.message);
                        //     }
                        // },{order_id: order_id, return_score: return_score,  return_account: return_account, return_money: money_paid, order_id: order_id})
                        layer.close(index);
                    }
                }
                , btn2: function (index, layero) {
                    layer.close(index);
                }

            });
        });
    });
    $(".get_return").on('click',function() {
        var use_score = $(this).attr('use_score');
        var return_score = $(this).attr('return_score').substring(0,$(this).attr('return_score').length-3);
        var return_account = $(this).attr('return_account');
        var return_money = $(this).attr('return_money');
        var return_time = $(this).attr('return_time');
        var pay_type = $(this).attr('pay_type');
        var content = '';
        if(use_score > 0){
            content = '<div class="return_content">' +
                '退款时间：' + return_time + '<br/>' +
                '已退积分：' + return_score +
                '</div>';
        }else{
            content = '<div class="return_content">' +
                '退款时间：' + return_time + '<br/>' +
                '已退余额：' + return_account + '<br/>' +
                '已退现金：' + return_money +
                '</div>';
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({title: ['退款详情','text-align:center;padding: 0px;'],
                type: 1,
                offset: 'auto',
                area: '300px',
                btn: ['确定'],
                btnAlign: 'r',
                content: content,
                success: function (layero, index) {

                }
                , yes:  function (index, layero) {
                        layer.close(index);
                }

            });
        });
    });

//    优惠礼包订单手动退款
    $(document).on("click",".manual_return",function () {
        var order_amount = $(this).attr('order_amount');
        var use_score = $(this).attr('use_score');
        $('#couponpack_return_score').next().find('span').html(use_score + '积分');
        var use_account = $(this).attr('use_account');
        $('#couponpack_return_account').next().find('span').html(use_account + '元');
        var money_paid = $(this).attr('money_paid');
        $('#couponpack_return_money').next().find('span').html(money_paid + '元');
        var member_card_balance = $(this).attr('member_card_balance');
        $('#couponpack_return_member_card_balance').next().find('span').html(member_card_balance + '元');
        var order_id = $(this).attr('order_id');
        if(use_score == '' || use_score <= 0){
            $('.coupon_return_score').hide();
        }else{
            $('.coupon_return_money').hide();
        }
        if(use_account == '' || use_account <= 0){
            $('#couponpack_return_account').parent().hide();
        }
        if(money_paid == '' || money_paid <= 0){
            $('#couponpack_return_money').parent().hide();
        }
        if(member_card_balance == '' || member_card_balance <= 0){
            $('#couponpack_return_member_card_balance').parent().hide();
        }
        var surplus_amount = $(this).attr('surplus_amount');
        $('#surplus_amount_span').html(surplus_amount);
        if(use_score == '' || use_score == 0){
            var height = "300px";
        }else{
            var height = "300px";
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var _this = $(this);
            //触发事件
            layer.open({
                type: 1
                ,title: ['手动退款','text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: ['400px',height]
                ,id: 'layerDemoD' //防止重复弹出
                ,content: $('#manual_return_money') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['确定','取消']
                ,btnAlign: 'r' //按钮居右
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    $("input[name='order_id']").val(order_id);
                    var msg = '';
                    var return_score = $('#couponpack_return_score').val();
                    var return_account = $('#couponpack_return_account').val();
                    var return_money = $('#couponpack_return_money').val();
                    var return_member_card_balance = $('#couponpack_return_member_card_balance').val();
                    var still_send_flag = $('input[name="if_send"]:checked').val();
                    if(use_score>0) {
                        if( parseFloat(return_score) > parseFloat(use_score*100)){
                            msg = '退款积分不能大于支付积分';
                        }
                    }else{
                        if( parseFloat(return_account) > parseFloat(use_account)){
                            msg = '退款余额不能大于支付余额';
                        }
                        if( parseFloat(return_money) > parseFloat(money_paid)){
                            msg = '退款现金不能大于支付现金';
                        }
                        if( parseFloat(member_card_balance) > parseFloat(return_member_card_balance)){
                            msg = '退款会员卡余额不能大于支付金额';
                        }
                    }
                    if(msg != ''){
                        layer.msg(msg);
                        return false;
                    }else{
                        var param = {
                            order_id:order_id,
                            return_score:return_score,
                            return_account:return_account,
                            return_money:return_money,
                            return_member_card_balance:return_member_card_balance,
                            still_send_flag:still_send_flag
                        }
                        util.ajax_json('/admin/orders/couponpackage/return',function (res) {
                            if (res.error == 0) {
                                util.mobile_alert('退款成功');
                                $("#form2").submit();
                            } else {
                                util.mobile_alert(res.message);
                            }
                        },param)
                    }
                    layer.close(index);

                },btn2: function(index, layero){
                    //按钮取消的回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }, end: function () {
                    $('#strategy_set').hide();
                }
            });
        });
    })
//    退款详情
    $(document).on("click",".retuen_detail",function () {
        var return_time = $(this).attr('return_time');
        var return_money = $(this).attr('return_money');
        var return_account = $(this).attr('return_account');
        var return_score = parseInt($(this).attr('return_score'));
        var return_card_balance = $(this).attr('return_card_balance');
        $('#return_detail .return_detail_time span').html(return_time);
        $('#return_detail .return_detail_account span').html(return_account);
        $('#return_detail .return_detail_card_balance span').html(return_card_balance);
        $('#return_detail .return_detail_money span').html(return_money);
        $('#return_detail .return_detail_score span').html(return_score);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var _this = $(this);
            //触发事件
            layer.open({
                type: 1
                ,title: ['退款详情','text-align:center;padding: 0px;']
                ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,area: ['400px','280px']
                ,id: 'layerDemoD' //防止重复弹出
                ,content: $('#return_detail') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,btn: ['确定']
                ,btnAlign: 'r' //按钮居右
                ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function(index, layero){ //保存按钮的回调
                    layer.close(index)
                },btn2: function(index, layero){
                    //按钮取消的回调
                    //return false 开启该代码可禁止点击该按钮关闭
                }, end: function () {
                    $('#strategy_set').hide();
                }
            });
        });
    })
</script>

<#include "/admin/footer.ftl">