<#include ("system.header")
<link rel="stylesheet" href="/css/system/user_list.css">
<link rel="stylesheet" href="/css/system/coupon_manage.css?v=1.0.1">
<link rel="stylesheet" href="/css/system/order_all.css" type="text/css" />
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

</style>
<div style="min-width: 1090px;">
    {{--<div class="title">--!}
        {{--<span><a href="/system/market/view?top_index=4">{{ trans("system/market_manage.market_manage_title")!}</a> / </span>--!}
        {{--<span style="color: #666;">限时降价</span>--!}
    {{--</div>--!}
    <div class="main-container">

        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li <#if ($nav_type==0)class="active"</#if>>
                        <a href="/system/market/reduce/list?nav=0&shop_id=${shop_id!}" >全部限时降价活动</a>
                    </li>
                    <li <#if ($nav_type==1)class="active"</#if>>
                        <a href="/system/market/reduce/list?nav=1&shop_id=${shop_id!}">进行中</a>
                    </li>
                    <li <#if ($nav_type==2)class="active"</#if>>
                        <a href="/system/market/reduce/list?nav=2&shop_id=${shop_id!}" >未开始</a>
                    </li>
                    <li <#if ($nav_type==3)class="active"</#if>>
                        <a href="/system/market/reduce/list?nav=3&shop_id=${shop_id!}">已过期</a>
                    </li>
                    <li <#if ($nav_type==4)class="active"</#if>>
                        <a href="/system/market/reduce/list?nav=4&shop_id=${shop_id!}">已停用</a>
                    </li>
                    <#if ($shop_flag == 2)
                    <li>
                        <a href="/system/market/reduce/price/list?shop_id=${shop_id!}">批量降价</a>
                    </li>
                    <li>
                        <a href="/system/market/reduce/profit/list?shop_id=${shop_id!}">批量加价率</a>
                    </li>
                    </#if>
                </ul>
            </div>
            {{--<ul class="add-child-ul">--!}
                {{--<li>--!}
                    {{--<a href="/system/market/reduce/add?type=1&top_index=4" class="add-child-btn" target="_blank">--!}
                        {{--添加限时降价活动--!}
                    {{--</a>--!}
                {{--</li>--!}
            {{--</ul>--!}
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
                                    <th width="16%">活动名称</th>
                                    <th width="12%">商品数量</th>
                                    <th width="15%">有效期</th>
                                    <th width="10%">活动状态</th>
                                    <th width="10%">付款订单数</th>
                                    <th width="10%">付款用户数</th>
                                    <th width="12%">付款总金额</th>
                                    {{--<th width="15%">操作</th>--!}
                                </tr>
                                </thead>
                                <tbody>
                                    <#list ($data_list as $item)
                                    <tr>
                                        <td><#if  ($item->add_type==1)
                                                <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">批量改价</span>
                                                <#elseif> ($item->add_type==2)
                                                <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">批量加价率</span>
                                            </#if>${item->name!}</td>
                                        <td>${item->goods_num!}</td>
                                        <td>
                                            ${item->start_time!}
                                            <br>
                                            至
                                            <br>
                                            ${item->end_time!}
                                        </td>
                                        <td>
                                            <#if ($item->status==0)
                                                已停用
                                            <#elseif>($item->end_time < date("Y-m-d H:i:s",time()))
                                                已过期

                                            <#elseif>($item->start_time < date("Y-m-d H:i:s",time()))
                                                进行中
                                            <#elseif>($item->start_time > date("Y-m-d H:i:s",time()))
                                                未开始
                                            </#if>
                                        </td>
                                        <td>${item->order_num!}</td>
                                        <td>${item->user_num!}</td>
                                        <td>${item->money!}</td>
                                        {{--<td>--!}
                                            {{--<a href="/system/market/reduce/add?top_index=4&id=${item->id!}"--!}
                                               {{--<#if ($item->status != 1) hidden--!}
                                               {{--<#if ($item->status == 1 || $item->end_time < date("Y-m-d H:i:s",time()))--!}
                                                {{--</#if> target="_blank">编辑</a>--!}
                                            {{--<a  href="javascript:void(0)" class="abort" act_id="${item->id!}"--!}
{{--                                            <#if ($item->status == 1 || $item->end_time < date("Y-m-d H:i:s",time()))--!}
                                            {{--<#if ($item->nav != 2)hidden </#if> >停用</a>--!}
                                            {{--<#if ($item->status == 0)--!}
                                                {{--<a  href="javascript:void(0)" class="restart" act_id="${item->id!}" end_time="${item->end_time!}">启用</a>--!}
                                            {{--</#if>--!}
                                            {{--<#if ($item->start_time < date("Y-m-d H:i:s",time()))--!}
                                                {{--<a href="/system/orders/activity/order/list?top_index=4&act_id=${item->id!}&goods_type=6" target="_blank">订单明细</a>--!}
                                            {{--</#if>--!}
                                            {{--<#if ($item->nav != 2)--!}
                                                {{--<a href="#" class="del" reduce_id="${item->id!}">删除</a>--!}
                                            {{--</#if>--!}
                                        {{--</td>--!}
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="paging_footer">
                        <#include ("system.jump_page_system")
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
                // $("#form1").submit();
                util.ajax_json("/system/market/reduce/list",function (res) {
                    if(res.error == 0){
                        util.mobile_alert("停用成功");
                        location.reload();
                    }
                },{id:$('input[name="del"]').val(),act:'disable'})

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
                    id : _this.attr("act_id"),
                    act:'enable'
                };
                util.ajax_json('/system/market/reduce/list', function (res) {
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
                $('input[name="delete"]').val(_this.attr("reduce_id"));
                // $("#form1").submit();
                var param = {
                    id : _this.attr("reduce_id"),
                    act:'del'
                };
                util.ajax_json('/system/market/reduce/list', function (res) {
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
    })

    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/system/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")
<script type="text/javascript">
    //getPowerInfo('main_config','reduce_price','sub_4','限时降价',0);
</script>
