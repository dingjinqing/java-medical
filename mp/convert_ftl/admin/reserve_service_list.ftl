<#include "/admin/header.ftl">
<style>
    .coupon_type{
        background: #fff;
        padding:10px 0 0;
    }
    .coupon_type ul{
        list-style:none;
        background: #f5f5f5;
        width: 97%;
        margin:0 auto;
        border:1px solid #f3f3f3;
    }
    .coupon_type ul:after{
        content: '';
        display: block;
        clear: both;
    }
    .coupon_type ul li{
        float: left;
        width: 100px;
        height: 40px;
        line-height: 40px;
        /*text-align: center;*/
        cursor: pointer;
    }
    .coupon_type ul li a{
        display: block;
        width: 100%;
        height:100%;
        color: black;
    }
    .coupon_type ul .actives{
        background: #fff;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
    }
    .item-menu a{
        margin: 0;
    }
    .share_td .erweima{
        position: relative;
        display: inline-block;
    }
</style>
<link href="/css/admin/reserve_service_list.css?v=1.0.5" rel="stylesheet" type="text/css"/>
<div class="goods-container">
    <div class="title">
        <span><a href="/admin/store/manage/list?top_index=6" style="color:  black;margin-left: 0px">门店列表（${store->store_name!}）</a> /</span>
        <span><a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6"  style="color:  #666;margin-left: 0px">服务管理</a></span>
        {{--<span> / ${store->store_name!}</span>--!}
    </div>
    <div class="main-container">
        <div class="coupon_type">
            <ul>
                <li class="normal_type ">
                    <a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6 ">预约管理</a>
                </li>
                <li class="fenlie_type actives">
                    <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" class="edition_type">服务管理</a>
                </li>
                <li class="fenlie_type">
                    <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" class="edition_tech" title="技师管理">${technician_title!}管理</a>
                </li>
                <li class="give_to_sb">
                    <a href=/admin/store/services/comment/list?store_id=${store_id!}&top_index=6">评价管理</a>
                </li>
            </ul>
        </div>
        <div class="list-center">
            <form name="form" action="/admin/store/services/service/list?store_id=${store_id!}&top_index=6"  id="form1" method="post">
                {{ csrf_field()!}
                <input type="hidden" name="service_id">
                <input type="hidden" name="act">
                <input type="hidden" name="del">

                <div class="clearfix top-banner">
                    <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6"  class="create-good actiove">服务列表</a>
                    <a href="/admin/store/services/service/type?store_id=${store_id!}&top_index=6"  class="create-good">分类管理</a>
                    <a href="/admin/store/services/service/add?store_id=${store_id!}&top_index=6"  class="create-good" target="_blank">添加服务</a>
                </div>
                <div class="ath_search">
                    <select name="cat_id" id="cat_id" onchange="submitForm();">
                        <option value="0">请选择服务分类</option>
                        <#list ($cat as $item)
                            <option value="${item->cat_id!}" <#if ($item->cat_id==$input['cat_id']) selected </#if>>${item->cat_name!}</option>
                        </#list>
                    </select>
                    <input type="text" name="keywords" placeholder="搜索服务" onkeydown="if (event.keyCode==13 || event.which==13) search(this.value);" value="${input['keywords']!}">
                    <button class="btn_searchinfo">查询</button>
                </div>


                <div class="list-bottom" style="margin-top:10px">
                    <table align="center" class="list-bottom-show">
                        <thead>
                            <tr id="list-bottom-top">
                                <th width="50px"></th>
                                <th width="180px">服务名称</th>
                                {{--<th width="180px">价格 | 订金</th>--!}
                                <th width="180px">价格</th>
                                <th width="120px">服务分类</th>
                                <th width="180px">销量</th>
                                <th width="200px">添加时间</th>
                                <th width="120px">服务模式</th>
                                <th width="100px">状态</th>
                                <th width="300px">操作</th>
                            </tr>
                        </thead>
                        <tbody id="" class="list">
                        <#list ($data_list as $item)
                            <tr>
                                <td width="50px"><input type="checkbox" service_id="${item->id!}"></td>
                                <td width="180px">
                                    <img src="${item->service_img[0]!}" class="tab_img">
                                    <span style="width: 85px;display: -webkit-box;float: left;word-break: break-all;text-overflow: ellipsis;-webkit-box-orient: vertical;-webkit-line-clamp: 2;overflow: hidden;">${item->service_name!}</span>
                                </td>
                                {{--<td width="180px">${item->service_price!} | ${item->service_subsist!}</td>--!}
                                <td width="180px">${item->service_price!}</td>
                                <td width="120px">${item->cat_name!}</td>
                                <td width="180px">${item->sale_num!}</td>
                                <td width="200px">${item->add_time!}</td>
                                <td width="250px">
                                    <#if ($item->service_type == 1)
                                        服务+时间+<span title="<#if (is_array($item->technician_list))
                                            <#list ($item->technician_list as $technician)${technician->technician_name!}；</#list>
                                            <#else>没有配置${technician_title!}</#if>" >${technician_title!}</span>
                                    <#elseif>($item->service_type == 0)
                                        服务+时间
                                    </#if>
                                </td>
                                <td align="center" width="11%" class="on_sale">
                                    <#if ($item->service_shelf == 1)
                                        已上架
                                    <#elseif>($item->service_shelf == 0)
                                        已下架
                                    <#else>
                                        已过期
                                    </#if>
                                </td>
                                <td width="300px" class="share_td">
                                    <a href="/admin/store/services/service/add?store_id=${store_id!}&service_id=${item->id!}&top_index=6" target="_blank">编辑</a>
                                    <#if ($item->service_shelf == 1)
                                        <a href="javascript:void(0);" class="change-onSale" service_id="${item->id!}" act="off_sale">{{ trans("admin/shop.list-bottom-operate.li_done")!}</a>
                                    <#elseif>($item->service_shelf == 0)
                                        <a href="javascript:void(0);" class="change-onSale" service_id="${item->id!}" act="on_sale">{{ trans("admin/shop.list-bottom-operate.li_on")!}</a>
                                    </#if>
                                    <div class="erweima">
                                        <a href="##" class="hover_share" identity_id="${item->id!}" type="3">分享</a>
                                    </div><br>
                                    <a href="/admin/store/services/comment/list?service_id=${item->id!}&store_id=${store_id!}&top_index=6" service_id="${item->id!}" target="_blank">查看评价</a>
                                    <a href="#" class="del" service_id="${item->id!}">删除</a>
                                </td>
                            </tr>
                            </#list>
                        </tbody>
                        <#if (count($data_list) <= 0)
                            <table style="width: 100%;border: 1px solid #eee;position: relative;margin-top: 16px;">
                                <tr>
                                    <td style="width: 30px;height: 33px; margin: 18px auto auto auto;display: flex;justify-content: center;align-items: center;" >
                                        <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</td>
                                </tr>
                            </table>
                        <#else>
                            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table" border="0">
                                <tr >
                                    <td style="padding-left: 15px;">
                                        <span style="position:relative;">&nbsp;&nbsp;
                                            <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" style="top: -1px;" />
                                            <input type="checkbox" id="select-all" />&nbsp;&nbsp;
                                            <label for="select-all">全选</label>
                                        </span>
                                        <input name="off_sale" type="button" value="下架" class="down tb-btm-left off_sale">
                                        <input name="on_sale" type="button" value="上架" class="down tb-btm-left on_sale">
                                    </td>
                                    <td align="right">
                                        <#include "/admin/jump_page_admin.ftl">
                                    </td>
                                </tr>
                            </table>
                        </#if>

                    </table>


                </div>
            </form>
        </div>
    </div>
</div>
<#include ('admin.share_common')

<script src="/js/admin/reserve_service.js?v=1.0.1" type="text/javascript"></script>
<script>
    function submitForm(){
        $("#page").val(1);
        $("#form1").submit();
    }

    window.search = function(keywords){
        if(keywords==''){
            util.mobile_alert('请输入服务名称查询');
            return;
        }
        $("#page").val(1);
        $("#form1").submit();
    };

    $(".change-onSale").click(function(){
        var obj = $(this);
        var data={};
        data.act = $(this).attr("act");
        data.service_id = $(this).attr("service_id");
        data.store_id = $(".store_id").val();
        util.ajax_json('/admin/store/services/service/sale',function(d){
            if(d&&d.error==0){
                if(data.act=='off_sale'){
                    obj.attr("act",'on_sale');
                    obj.html('上架');
                    obj.parent().parent().find('.on_sale').html('已下架');
                }else{
                    obj.attr("act",'off_sale');
                    obj.html('下架');
                    obj.parent().parent().find('.on_sale').html('已上架');
                }
                util.mobile_alert(d.content);
            }
            else{
                util.mobile_alert(d.message);
            }
        },data);
    });


    $('input[type="button"]').click(function(){
        var service_id = [0];
        $('input[type="checkbox"]:checked').each(function () {
            service_id.push($(this).attr('service_id'));
        });
        if (service_id.length == 1) {
            util.mobile_alert('未选择任何服务');
            return false;
        }
        else {
            $('input[name="act"]').val($(this).attr('name'));
            $('input[name="service_id"]').val(service_id);
            $("#form1").submit();
        }
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
                $('input[name="del"]').val(_this.attr("service_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','service','sub_5','服务管理',0);
</script>


