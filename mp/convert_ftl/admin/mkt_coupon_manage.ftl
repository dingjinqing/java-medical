<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css?v=1.0.0">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
        color: #5a8bff;
    }
    .add-child-btn:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .add-child-btn:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[name='page']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .search-bl{
        width: 228px;
        height: 30px;
        margin: 10px 10px;
        display: inline-block;
        border-radius: 3px;
        border: 1px solid #ccc;
        line-height: 30px;
    }
    .primary{
        width: 195px;
        height: 26px;
        background-color: #fff;
        border: none;
        color: #333;
        font-size: 14px;
        padding-left: 8px;
        outline:none;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        line-height: 30px;
    }
    .add-child-btn{
        right: 22px;
        left: auto;
        height: 30px;
        line-height: 30px !important;
        padding: 0px 10px;
    }
    .add-child-ul{
        height: 50px;
    }
    .vip_exclusive{
        display: inline-block;
        border: 1px #c5a050 solid;
        padding: 0px 3px;
        color: #c5a050;
        border-radius: 2px;
        font-size: 12px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">{{ trans("admin/market_manage.coupon_manage_title")!}</span>
    </div>
    <div class="main-container">
        {{--<div class="coupon_type">--!}
            {{--<ul>--!}
                {{--<li class="normal_type actives">--!}
                    {{--<a href="/admin/market/coupon/manage?nav=1 ">普通优惠券</a>--!}
                {{--</li>--!}
                {{--<li class="fenlie_type">--!}
                    {{--<a href="/admin/coupon/split?nav=1" link="/admin/coupon/split?nav=1" class="edition_type">分裂优惠券</a>--!}
                {{--</li>--!}
                {{--<li class="give_to_sb">--!}
                    {{--<a href="/admin/market/grant/list" link="/admin/market/grant/list" class="edition_type">定向发券</a>--!}
                {{--</li>--!}
                {{--<li class="active_to_sb">--!}
                    {{--<a href="/admin/market/activityreward/list?nav=1">活动送券</a>--!}
                {{--</li>--!}
            {{--</ul>--!}
        {{--</div>--!}


        {{--普通优惠券部分--!}
        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li <#if ($nav_type==0)class="active"</#if>>
                        <a href="/admin/market/coupon/manage?nav=0" >全部优惠券</a>
                    </li>
                    <li <#if ($nav_type==1)class="active"</#if>>
                        <a href="/admin/market/coupon/manage?nav=1">进行中</a>
                    </li>
                    <li <#if ($nav_type==2)class="active"</#if>>
                        <a href="/admin/market/coupon/manage?nav=2" >未开始</a>
                    </li>
                    <li <#if ($nav_type==3)class="active"</#if>>
                        <a href="/admin/market/coupon/manage?nav=3">已过期</a>
                    </li>
                    <li <#if ($nav_type==4)class="active"</#if>>
                        <a href="/admin/market/coupon/manage?nav=4">已停用</a>
                    </li>
                </ul>
            </div>
            <script>
                // // tab切换
                // $("[data-toggle='tab']").click(function () {
                //     var url_arr = ['/admin/market/coupon/manage', '/admin/market/coupon/manage'];
                //     var idx = $(this).parent().index();
                //     if (url_arr[idx] != undefined) {
                //         window.location.href = url_arr[idx];
                //     }
                // });
            </script>
            <form action="/admin/market/coupon/manage" method="post" id="form1">
                {{csrf_field()!}
            <ul class="add-child-ul">
                <li>
                     <span style="position: absolute;left: 0px" >
                        <span style="padding-left: 30px">优惠券名称</span>
                        <span class="search-bl">
                            <input type="text" name='keywords' value="${keywords!}" placeholder="请输入优惠券名称"
                                   class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span>
                        <button class="btn_searchinfo">查询</button>
                    </span>
                    <a href="/admin/market/coupon/add" class="add-child-btn" target="_blank">{{ trans('admin/market_manage.coupon_manage.add_coupon')!}</a>
                </li>
            </ul>
            <div class="return-goods-box">
                <form action="/admin/market/coupon/manage" method="post" id="form1">
                    {{csrf_field()!}
                    <input name="del" type="hidden">
                    <input name="enable" type="hidden">
                    <input name="delete" type="hidden">
                    <input name="nav" type="hidden" value="${nav_type!}">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr>
                                <th width="13%">{{ trans("admin/market_manage.coupon_manage.coupon_name")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.use_score")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.value")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.lower_fee")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.count")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.get_limit")!}</th>
                                <th width="14%">{{ trans("admin/market_manage.coupon_manage.date_limit")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.get_person")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.give_person")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.used")!}</th>
                                <th width="15%">{{ trans("admin/market_manage.coupon_manage.operation")!}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if ($coupon_list)
                                <#list ($coupon_list as $item)
                                    <tr>
                                        <td>
                                            <#if ($item->card_id)
                                            <span class="vip_exclusive">会员专享
                                            </span>
                                            </#if>
                                            ${item->act_name!}
                                        </td>
                                        <td>
                                            <#if  ($item->use_score == 1)${item->score_number!}积分兑换
                                            <#else> 不需要兑换
                                            </#if>
                                        </td>
                                        <td><#if ($item->act_code=='voucher')${item->denomination!}元 <#else>${item->denomination!}折 </#if></td>
                                        <td><#if ($item->use_consume_restrict ==0) 无限制<#else> <#if  ($item->least_consume == 0)无限制 <#else> ${item->least_consume!}</#if> </#if></td>
                                        <td>${item->surplus!}</td>
                                        <td><#if  ($item->receive_per_person == 0) 不限制 <#else> 限领${item->receive_per_person!}张 </#if> </td>
                                        <td><#if ($item->validity >0)领取开始${item->validity!}天内有效 <#else> ${item->start_time!}<br/>至<br/>${item->end_time!}</#if></td>
                                        <td>${item->receive_person!}/${item->receive_amount!}</td>
                                        <td>${item->giveout_person!}/${item->giveout_amount!}</td>
                                        <td>${item->used_amount!}</td>
                                        <td class="tb-decorate-a">
                                            <#if (($item->end_time >= date("Y-m-d H:i:s",time()) && $item->end_time && $item->enabled==1) || ($item->validity >0 && $item->enabled==1))
                                            <a href="/admin/market/coupon/edit?id=${item->id!}" target="_blank">{{ trans("admin/market_manage.coupon_manage.edit")!}</a>&nbsp;-&nbsp;
                                            <div class="erweima">
                                                <a href="##" identity_id="${item->alias_code!}" class="hover_share" type="5">{{ trans("admin/market_manage.coupon_manage.share")!}</a>
                                            </div> -
                                            <a  href="javascript:void(0)" class="abort" act_id="${item->id!}">{{ trans("admin/market_manage.coupon_manage.stop_use")!}</a>&nbsp;-&nbsp;
                                            <#elseif>($item->enabled==0)
                                                <a  href="javascript:void(0)" class="enable" act_id="${item->id!}" end_time="${item->end_time!}" validity="${item->validity!}">启用</a>&nbsp;-&nbsp;
                                            </#if>
                                            <a href="/admin/public/coupon/get/list?top_index=${top_index!}&sub_index=${sub_index!}&id=${item->id!}" target="_blank">{{ trans("admin/market_manage.coupon_manage.get_detail")!}</a>
                                            <#if ($nav_type == 3 || $nav_type == 4 || $item->enabled == 0 || $item->end_time < date("Y-m-d H:i:s",time()) && $item->validity <=0)
                                                &nbsp;-&nbsp;<a href="#" coupon_id="${item->id!}" class="del">删除</a>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
                    <div class="paging_footer">
            <#include "/admin/jump_page_admin.ftl">
                    </div>
            </form>
         </div>
        </div>
        {{--普通优惠券部分结束--!}


    </div>
</div>
<#include ('admin.share_common')
<script>
    function gopage(page) {
        var last_page = '${coupon_list -> lastPage()!}';
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    $('.abort').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="del"]').val(_this.attr('act_id'));
                $("#form1").submit();
                layer.close(index);
            });
        });
   });
    $(".enable").click(function () {
        var _this = $(this);
        if(_this.attr("validity") <= 0 || _this.attr("validity")==""){
            var myDate = new Date();
            //获取当前年
            var year=myDate.getFullYear();
            //获取当前月
            var month=myDate.getMonth()+1;
            //获取当前日
            var date=myDate.getDate();
            var h=myDate.getHours();       //获取当前小时数(0-23)
            var m=myDate.getMinutes();     //获取当前分钟数(0-59)
            var s=myDate.getSeconds();
            var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
            if(_this.attr("end_time")<= now){
                util.mobile_alert("该活动已过期，不可启用");
                return false;
            }
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="enable"]').val(_this.attr('act_id'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $('.btn_copy').click(function(e){
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
                $('input[name="delete"]').val(_this.attr("coupon_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
