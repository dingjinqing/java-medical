<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css?v=1.0.1">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .btn_to_give a:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn_to_give a:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
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
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">支付有礼</span>
    </div>
    <div class="main-container">

        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li <#if ($nav_type==0)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=0&top_index=4" >全部支付有礼活动</a>
                    </li>
                    <li <#if ($nav_type==1)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=1&top_index=4">进行中</a>
                    </li>
                    <li <#if ($nav_type==2)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=2&top_index=4" >未开始</a>
                    </li>
                    <li <#if ($nav_type==3)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=3&top_index=4">已过期</a>
                    </li>
                    <li <#if ($nav_type==4)class="active"</#if>>
                        <a href="/admin/market/payreward/list?nav=4&top_index=4">已停用</a>
                    </li>
                </ul>
            </div>
            <ul class="add-child-ul">
                <li>
                    <a href="/admin/market/payreward/add" class="add-child-btn" target="_blank">
                        添加支付有礼活动
                    </a>
                </li>
            </ul>
            <div class="return-goods-box">
                <form action="" method="post" id="form1">
                    {{csrf_field()!}
                    <input name="del" type="hidden">
                    <input name="delete" type="hidden">
                    <input name="nav" type="hidden" value="${nav_type!}">
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="12%">{{ trans("admin/market_manage.split_coupon_list.activity_name")!}</th>
                                    <th>触发条件</th>
                                    <th width="25%">{{ trans("admin/market_manage.split_coupon_list.activity_date_limit")!}</th>
                                    <th>活动类型</th>
                                    <th>{{ trans("admin/market_manage.split_coupon_list.activity_state")!}</th>
                                    <th width="21%">{{ trans("admin/market_manage.split_coupon_list.operation")!}</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list ($data_list as $item)
                                    <tr>
                                        <td>${item->activity_names!}</td>
                                        <td>满${item->least_money!}元
                                            <#if ($item->recommend_type==2)
                                                <br>购买指定商品
                                            </#if>
                                        </td>
                                        <td>${item->act_start_time!}
                                            <br>
                                            至
                                            <br>
                                            ${item->act_end_time!}</td>
                                        <td><#if ($item->type==1) 分裂优惠券 <#elseif>($item->type == 2) 幸运大抽奖 <#elseif>($item->type == 3) 支付送券 <#elseif>($item->type == 4) 自定义 </#if></td>
                                        <td>
                                            <#if ($item->enabled==0)
                                                已停用
                                            <#elseif>($item->act_end_time < date("Y-m-d H:i:s",time()))
                                                已过期

                                            <#elseif>($item->act_start_time < date("Y-m-d H:i:s",time()))
                                                进行中
                                            <#elseif>($item->act_start_time > date("Y-m-d H:i:s",time()))
                                                未开始
                                            </#if>
                                        </td>
                                        <td>
                                            <a <#if ($item->type==1) href="/admin/market/coupon/edit?id=${item->id!}&type=1"
                                               <#elseif>($item->type==2) href="/admin/market/lottery/payreward?id=${item->id!}&type=2"
                                               <#elseif>($item->type==3) href="/admin/market/payreward/coupon?id=${item->id!}&type=3"
                                               <#elseif>($item->type==4) href="/admin/market/payreward/payrewardurl?id=${item->id!}&type=4"
                                               </#if>
                                               <#if ($item->enabled == 0 || $item->act_end_time < date("Y-m-d H:i:s",time()))
                                               hidden </#if> target="_blank">编辑</a>
                                            <a  href="javascript:void(0)" class="abort" act_id="${item->id!}"
                                                <#if ($item->enabled == 0 || $item->act_end_time < date("Y-m-d H:i:s",time()))
                                                hidden </#if> >停用</a>
                                            <#if ($item->enabled == 0)
                                                <a  href="javascript:void(0)" class="restart" act_id="${item->id!}" end_time="${item->act_end_time!}">启用</a>
                                            </#if>
                                            <#if ($item->act_start_time < date("Y-m-d H:i:s",time())&&$item->type!=4)
                                                <a <#if ($item->type==1) href="/admin/market/payreward/split?id=${item->id!}"
                                                   <#elseif>($item->type==2) href="/admin/market/lottery/detail?top_index=4&source=pay&act_id=${item->id!}&id=${item->lottery!}&sub_index=${sub_index!}"
                                                   {{--<#elseif>($item->type==3) href="/admin/market/payreward/coupon/detail?top_index=4&act_id=${item->id!}"--!}
                                                   <#elseif>($item->type==3) href="/admin/public/coupon/get/list?sub_index=${sub_index!}&top_index=4&type=3&pay_act_id=${item->id!}"
                                                   </#if> target="_blank">活动明细</a>
                                            </#if>
                                            <#if ($item->enabled==0 || $item->act_end_time < date("Y-m-d H:i:s",time()))
                                                <a href="#" class="del" coupon_id="${item->id!}">删除</a>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
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
    </div>
</div>
<script>

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
            });
        });
    });
    $(".restart").click(function () {
        var _this = $(this);
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
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用吗？' + '</div>', {
                title: ['提示', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {
                    restart : _this.attr("act_id"),
                };
                util.ajax_json('/admin/market/payreward/list', function (res) {
                    if (res.error == 0) {
                        util.mobile_alert("启用成功");
                        location.reload();
                    } else {
                        util.mobile_alert(res.message);
                    }
                }, param)
                layer.close(index);
            });
        });
        return;
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
    // var left =  $('.left-menu-content .item-menu:nth-child(5)');
    // left.find("img").attr("src","/image/admin/icon_left/icon_payreward_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});

    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    // getPowerInfo('main_config','coupon_split','sub_4','分裂优惠券',0);
    getPowerInfo('main_config','pay_reward','sub_4','支付有礼',0);
</script>
