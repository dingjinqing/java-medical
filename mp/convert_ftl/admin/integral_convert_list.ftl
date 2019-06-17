<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/integral_manage.css?v=1.0.6" type="text/css" />
<style>
    .paging_footer{
        margin-top: 0px;
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
    input[name='page']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging tr td,.tb_paging tr td a{
        color: #333;
        font-size: 14px;
    }
    .tb_paging{
        border: 0 !important;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span style="color: #666;">积分兑换</span>
</div>
<div class="main_container">
    <div class="list_nav">
        <ul class="clearfix">
            <li <#if  (empty($request['nav'])) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=0&top_index=4">全部积分兑换活动</a>
            </li>
            <li <#if  ($request['nav'] == 1) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=1&top_index=4">进行中</a>
            </li>
            <li <#if  ($request['nav'] == 2) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=2&top_index=4">未开始</a>
            </li>
            <li <#if  ($request['nav'] == 3) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=3&top_index=4">已过期</a>
            </li>
            <li <#if  ($request['nav'] == 4) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=4&top_index=4">已停用</a>
            </li>
        </ul>
    </div>
    <div class="btn_add">
        <a href="/admin/market/integral/goods/add?top_index=4&nav=5" target="_blank">添加积分兑换活动</a>
    </div>
</div>
<div class="main_container" style="padding-top: 0px">
    <form action="" method="post" id="form1">
        {{ csrf_field()!}
        <input type="hidden" name="act"/>
        <input type="hidden" name="id"/>
        <div class="convert_table" style="padding-top: 10px">
            <#if ($list->count != 0)
            <table width="100%">
                <thead>
                    <tr>
                        <td width="10%">活动名称</td>
                        <td width="20%">商品名称</td>
                        <td width="15%">有效期</td>
                        <td width="10%">兑换现金(元)</td>
                        <td width="10%">兑换积分数</td>
                        <td width="5%">商品库存</td>
                        <td width="5%">积分兑换库存</td>
                        <td width="5%">已兑换数量</td>
                        <td width="5%">兑换用户数</td>
                        <td width="15%">操作</td>
                    </tr>
                </thead>
                <tbody>
                <#list  ($list as $item)
                    <tr>
                        <td width="10%">${item->name!}</td>
                        <td width="20%">
                            <div class="goods_info clearfix">
                                <div class="goods_img">
                                    <img src="${item->goods_img!}" alt="">
                                </div>
                                <div class="goods_names">
                                    <p class="g_names">${item->goods_name!}</p>
                                </div>
                            </div>
                        </td>
                        <td width="15%">
                            ${item->start_time!}<br/>
                            至<br/>
                            ${item->end_time!}<br/>
                        </td>
                        <td width="10%">${item->money_sum ?? 0!}</td>
                        <td width="10%">${item->score_sum ?? 0!}</td>
                        <td width="5%">${item->goods_number!}</td>
                        <td width="5%">${item->stock_sum!}</td>
                        <td width="5%">${item->number_sum ?? 0!}</td>
                        <td width="5%">${item->user_sum!}</td>
                        <td width="15%" >
                            <#if  (in_array($item->nav, [1, 2]))
                            <a href="/admin/market/integral/goods/add?id=${item->id!}&top_index=4&nav=5" class="btn_edit"> ${item->nav==1? "编辑" : "查看"!}</a>
                            <div class="erweima" style="position: relative;">
                                <a href="#" class="hover_share" identity_id="${item->id!}" type="16">分享</a>
                            </div>

                            </#if>
                            <#if  ($item->nav != 4 && $item->nav != 3)
                            <a class="close_integral" item="${item->id!}" style="cursor: pointer;">停用</a>
                            </#if>
                            <#if  ($item->nav == 4)
                                <a class="restart_integral" item="${item->id!}" style="cursor: pointer;" end_time="${item->end_time!}">启用</a>
                            </#if>
                            <#if  (in_array($item->nav, [3, 4]))
                            <a class="del_integral" item="${item->id!}" style="cursor: pointer;">删除</a>
                            </#if>
                            <#if  ($item->nav != 1)
                            <a href="/admin/orders/activity/order/list?goods_type=4&act_id=${item->id!}&top_index=4">查看积分兑换订单</a>
                            <a href="/admin/user/source/detail?source=integral&act_id=${item->id!}&top_index=4">获取新用户明细</a>
                            <a href="/admin/market/integral/convert/user/list?id=${item->id!}&top_index=4" class="btn_detail">查看积分兑换用户</a>
                            </#if>

                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <#else>
                <div>
                    <table width="100%">
                        <thead>
                        <tr>
                            <td width="20%">商品名称</td>
                            <td width="15%">有效期</td>
                            <td width="10%">兑换积分数</td>
                            <td width="10%">商品库存</td>
                            <td width="10%">积分兑换库存</td>
                            <td width="10%">已兑换数量</td>
                            <td width="10%">兑换用户数</td>
                            <td width="15%">操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list  ($list as $item)
                            <tr>
                                <td width="20%">
                                    <div class="goods_info clearfix">
                                        <div class="goods_img">
                                            <img src="${item->goods_img!}" alt="">
                                        </div>
                                        <div class="goods_names">
                                            <p class="g_names">${item->goods_name!}</p>
                                        </div>
                                    </div>
                                </td>
                                <td width="15%">
                                    ${item->start_time!}<br/>
                                    至<br/>
                                    ${item->end_time!}<br/>
                                </td>
                                <td width="10%">${item->score_sum ?? 0!}</td>
                                <td width="10%">${item->goods_number!}</td>
                                <td width="10%">${item->stock_sum!}</td>
                                <td width="10%">${item->number_sum ?? 0!}</td>
                                <td width="10%">${item->user_sum!}</td>
                                <td width="15%" >
                                    <#if  (in_array($item->nav, [1, 2]))
                                        <a href="/admin/market/integral/goods/add?id=${item->id!}&top_index=4&nav=5" class="btn_edit"> ${item->nav==1? "编辑" : "查看"!}</a>
                                        <div class="erweima" style="position: relative;">
                                            <a href="/admin/market/integral/mall/getqrcode" class="hover_share" identity_id="${item->id!}" type="16">分享</a>
                                        </div>

                                    </#if>
                                    <#if  ($item->nav != 4 && $item->nav != 3)
                                        <a class="close_integral" item="${item->id!}" style="cursor: pointer;">停用</a>
                                    </#if>
                                    <#if  ($item->nav == 4)
                                        <a class="restart_integral" item="${item->id!}" style="cursor: pointer;" end_time="${item->end_time!}">启用</a>
                                    </#if>
                                    <#if  (in_array($item->nav, [3, 4]))
                                        <a class="del_integral" item="${item->id!}" style="cursor: pointer;">删除</a>
                                    </#if>
                                    <#if  ($item->nav != 1)
                                        <a href="/admin/orders/activity/order/list?goods_type=4&act_id=${item->id!}&top_index=4">查看积分兑换订单</a>
                                        <a href="/admin/user/source/detail?source=integral&act_id=${item->id!}&top_index=4">获取新用户明细</a>
                                        <a href="/admin/market/integral/convert/user/list?id=${item->id!}&top_index=4" class="btn_detail">查看积分兑换用户</a>
                                    </#if>

                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
                <div class="paging_footer">
                    <style>
                        .paging_footer{
                            padding: 0px 0px;
                            margin-top: 12px;
                        }
                    </style>
                    <div style="width: 100%;border: 1px solid #eee;">
                        <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                        </div>
                        <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                    </div>
                </div>
            </#if>
        </div>
        <#if ($list->count != 0)
        <div class="paging_footer">
            <table width="100%" border="0" class="tb_paging">
                <tr>
                    <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$list->perPage(),'currentPage'=>$list->currentPage(),'count'=>$list->count,'total'=>$list->total(),])!}
                        <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                        <a href="#"
                           onClick="return gopage(${list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                        <a href="#"
                           onClick="return gopage(${list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                        <a href="#"
                           onClick="return gopage(${list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                        <input id="page" name="page" type="text" value="${list->currentPage()!}"
                               size="5"
                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                        <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                    </td>
                </tr>
            </table>
        </div>
        </#if>
    </form>

</div>
<#include ('admin.share_common')
<script>
    function gopage(page) {
        var last_page = '${list -> lastPage()!}';
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        $("[name='act']").val('');
        $("[name='id']").val('');
        $("#page").val(page);
        $("#form1").submit();
    }
    $(".close_integral").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("[name='act']").val("close");
                $("[name='id']").val(that.attr('item'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".restart_integral").click(function () {
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
                    restart_integral : _this.attr("item"),
                };
                util.ajax_json('/admin/market/integral/convert/list', function (res) {
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
    $(".del_integral").click(function () {
        var that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗?' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("[name='act']").val("del");
                $("[name='id']").val(that.attr('item'));
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
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<#include "/admin/footer.ftl">
<script>
    getPowerInfo('main_config','integral_goods','sub_4','积分商品',0);
</script>
