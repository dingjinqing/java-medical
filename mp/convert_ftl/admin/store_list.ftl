<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/store_manage.css?v=1.0.2">
<style type="text/css">
    .tb-decorate-list>tbody>tr>td{
        height:50px;
    }
    a:hover,a:focus{
        text-decoration: none;
    }
    .content_top .btn_add_groups:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .content_top .btn_add_groups:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .btn_add_groups{
        margin-bottom: 0;
        /*float: right;*/
        background-color: #5A8BFF;
        border: 1px solid #5A8BFF;
        color: #ffffff;
        width: 80px;
        height:30px;
        line-height: 30px;
        text-align: center;
    }
    .btn_add_groups a{
        color: #fff;
    }

    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
        color: #5a8bff;
    }
    .search_shop_id {
        width: 180px;
        height: 30px;
        color: #333;
        font-size: 14px;
        border: 1px solid #eee;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span>{{ trans("admin/store_manage.store_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/store_manage.store_list.title")!}</span>
    </div>
    <div class="main_container">
        <div class="list_content">
            <form action="/admin/store/manage/list" method="post" id="form1">
                <input type="hidden" name="del">
            <div class="content_top">
                <div class="filter_sth">
                    <label for="">门店分组：</label>
                    <select name="group" id="group" onchange="submitForm();">
                        <option value="0">全部</option>
                        <#list ($group_list as $item)
                        <option value="${item->group_id!}" <#if ($group ==$item->group_id) selected </#if>>${item->group_name!}</option>
                        </#list>
                    </select>
                    <label for="" style="margin-left: 50px;">是否已授权POS：</label>
                    <select name="is_auth_pos" onchange="submitForm()">
                        <option value="0">全部</option>
                        <option value="1" <#if  ($input['is_auth_pos'] == 1) selected </#if>>是</option>
                        <option value="2" <#if  ($input['is_auth_pos'] == 2) selected </#if>>否</option>
                    </select>
                    <label for="" style="margin-left: 50px;"></label>
                    <input type="text" value="${input['keywords']!}" class="search_shop_id" name="keywords" placeholder="门店名称|编码|负责人"/>
                    <img src="/image/admin/search.png" alt="" id="search" style="margin: 0 0 3px -28px;">
                </div>
                <div class="btn_add_groups" style="margin-left: 0">
                    <a href="/admin/store/manage/create" link="/admin/store/manage/create" target="_blank" class="goods_edition" data-title="门店数量" data-name="store_num" edit="0">新建门店</a>
                </div>
                <div class="system_info" style="margin: 0;float: right;">
                    <p class="system_info_prompt">
                        <#if ($version['self']['num']<0)
                            当前版本为${version['self']['version_name']!}，<span>不限制</span>门店个数
                        <#else>
                            当前版本为${version['self']['version_name']!}，还可创建 <span>${version['self']['num']-$version['self']['use'] >0 ? $version['self']['num']-$version['self']['use'] : 0!}</span>个门店
                        </#if>
                        <img src="http://${image_domain!}/image/admin/system_icon.png" />
                    </p>
                    <img src="http://${image_domain!}/image/admin/system_shadow.png" class="system_shadow" />
                    <div class="system_info_content">
                        <div class="system_info_content_top">
                            <#if ($version['all'])
                                <#list ($version['all'] as $k=>$ver)
                                    <#if ($ver['num']<0)
                                        <#if ($k!=0)，</#if>${ver['version_name']!}<span class="system_v1">不限制</span>门店个数
                                    <#else>
                                        <#if ($k!=0)，</#if>${ver['version_name']!}最多创建<span class="system_v1">${ver['num']!}</span>个门店
                                    </#if>
                                </#list>
                            </#if>
                            {{--基础版最多创建<span class="system_v1">5</span>个门店，高级版最多创建<span class="system_v2">20</span>个门店，旗舰版不限制--!}
                        </div>
                        <div class="system_info_content_bottom">
                            <a href="/admin/version/notice?mod_name=店铺数量" target="_blank">了解更多</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="list_table">
                    {{csrf_field()!}
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="10%">{{ trans("admin/store_manage.store_list.store_name")!}</th>
                                    <th width="7%">门店编码</th>
                                    <th width="10%">{{ trans("admin/store_manage.group_list.group_name")!}</th>
                                    <th width="10%">{{ trans("admin/store_manage.store_list.store_address")!}</th>
                                    <th width="10%">{{ trans("admin/store_manage.store_list.leader")!}</th>
                                    <th width="10%">{{ trans("admin/store_manage.store_list.mobile")!}</th>
                                    <th width="10%">{{ trans("admin/store_manage.store_list.business_time")!}</th>
                                    <th width="10%">{{ trans("admin/store_manage.store_list.business_state")!}</th>
                                    <#if ($store_buy == 1 && $version_store_pay == 1)
                                        <th width="6%">门店买单</th>
                                    </#if>
                                    <th width="17%">{{ trans("admin/store_manage.store_list.operation")!}</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list ($data_list as $item)
                                    <tr store_id="${item->store_id!}">
                                        <td>${item->store_name!}</td>
                                        <td><#if  (!empty($item->pos_shop_id)) ${item->pos_shop_id!}</#if></td>
                                        <td>${item->group_name!}</td>
                                        <td>${item->area!} ${item->address!}</td>
                                        <td>${item->manager!}</td>
                                        <td>${item->mobile!}</td>
                                        <td>${item->opening_time!} - ${item->close_time!} </td>
                                        <td>
                                            <span class="business_state" style="display: inline-block;margin-right: 10px"> <#if ($item->business_state) 营业 <#else> 未营业 </#if> </span>
                                            <a href="javascript:void(0);" class="close-store" store_id="${item->store_id!}"><#if ($item->business_state) 歇业 <#else> 开业 </#if></a>
                                        </td>
                                        <#if ($store_buy == 1 && $version_store_pay == 1)
                                        <td class="tb-decorate-a">
                                            <div class="erweima">
                                                <a href="##" class="hover_share" identity_id="${item->store_id!}" type="6">买单码</a>
                                            </div>
                                        </td>
                                        </#if>
                                        <td class="tb-decorate-a">
                                            <a href="/admin/store/manage/create?store_id=${item->store_id!}" link="/admin/store/manage/create?store_id=${item->store_id!}" target="_blank" class="goods_edition" data-title="门店数量" data-name="store_num" edit="1">编辑</a>
                                            <a href="/admin/store/goods/list?store_id=${item->store_id!}">商品管理</a>
                                            <a href="/admin/store/verification/list?store_id=${item->store_id!}">核销员管理</a>
                                            <br>
                                            <a href="#" class="del" store_id="${item->store_id!}">删除</a>
                                            <a href="/admin/store/services/reserve/list?store_id=${item->store_id!}&top_index=6">门店管理</a>
                                            <div class="erweima">
                                                <a href="##" class="hover_share" code_type="1" identity_id="${item->store_id!}" type="28">分享</a>
                                            </div>
                                            {{--<div class="btn_add_groups" style="margin-left: 40px"><a href="/admin/store/services/reserve/list?store_id=${item->store_id!}">门店管理</a></div>--!}

                                            {{--<a href="/admin/store/services/reserve/list?store_id=${item->store_id!}" style="margin-top: 5px;display: inline-block;">预约管理</a>--!}
                                            {{--<a href="/admin/store/services/technician/list?store_id=${item->store_id!}" style="margin-top: 5px;display: inline-block">技师管理</a>--!}
                                            {{--<a href="/admin/store/services/service/list?store_id=${item->store_id!}" style="margin-top: 5px;display: inline-block">服务列表</a>--!}
                                            {{--暂时的入口--!}
                                            {{--<a href="/admin/store/services/comment/list?store_id=${item->store_id!}">评价管理</a>--!}
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
            </div>
            </form>
        </div>
    </div>
</div>
<#include ('admin.share_common')
<script>
    function submitForm(){
        $("#page").val(1);
        $("#form1").submit();
    }

    $('.abort').click(function(){
        $('input[name="del"]').val($(this).attr('act_id'));
        $("#form1").submit();
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
                $('input[name="del"]').val(_this.attr("store_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    $('.search_shop_id').keydown(function () {
        if (event.keyCode == 13 || event.which == 13) {
            submitForm();
        }
    })
    $('#search').click(function () {
        submitForm();
    })
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script src="{{asset('js/admin/store_manage.js')!}?v=1.0.3" type="text/javascript"></script>
