<#include "/admin/header.ftl">

<link href="/css/admin/deliver_fee_list.css?v=0.1.8" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .btn-primary:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-primary:focus{
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
<div class="title">
    <span>商品管理 / </span>
    <span  style="color: #666;">运费模板</span>
    {{--<span style="color: #666;">运费模板列表</span>--!}
</div>
<div class="main-container">
    <#include ("admin.deliver_fee_header")
    <script>
        // tab切换
        $("[data-toggle='tab']").click(function () {
            var url = $(this).attr("url");
            if (url != undefined) {
                window.location.href = url;
            }
        });
    </script>

    <div class="box panel panel-body list-center-fee">
        <div class="form-inline shop-template-container">
            <label class="fee-pad">店铺默认运费模板：</label>
            <select name="ship_is_free" class="form-control ship_is_free" ship_is_free="${shop_deliver_template->ship_is_free!}">
                <option value="0" <#if ($shop_deliver_template->ship_is_free != 1) selected </#if> >统一运费</option>
                <option value="1" <#if ($shop_deliver_template->ship_is_free == '1') selected </#if> >满额包邮</option>
            </select>&nbsp;&nbsp;
            <span class="ship_fee0 <#if ($shop_deliver_template->ship_is_free == '1') hide </#if> ">
                运费：<input type="text" name="ship_fee" value="${shop_deliver_template->ship_fee or 10!}"
                          class="form-control ship_fee"
                          style="width: 60px">元</span>
            <span class="ship_fee1 <#if ($shop_deliver_template->ship_is_free != '1') hide </#if>">
            订单金额>=<input type="text" name="start_ship_order_gmv"
                         value="${shop_deliver_template->start_ship_order_gmv or 100!}"
                         class="form-control start_ship_order_gmv"
                         style="width: 60px">元时包邮，
            否则运费为<input type="text" name="no_satisfy_ship_fee"
                        value="${shop_deliver_template->no_satisfy_ship_fee or 10!}"
                        class="form-control no_satisfy_ship_fee"
                        style="width: 60px">元</span>
            <button type="button" class="btn btn-primary btn-save-shop-template">保存配置</button>
        </div>
    </div>


    <form action="/admin/goods/deliver/template/list" name="form1" id="form1" method="post">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="page" value="">
        <input type="hidden" name="deliver_template_id" id="deliver_template_id" value="">
        {{ csrf_field()!}
        <div class="box panel main-table deliver-fee-list" >
            <div class="layer layer-fee">

                <#if (count($data_list)>0)
                    <div class="box panel panel-body" style="padding:0px;" data_list="${data_list[0]->deliver_template_id!}">
                        <#list ( $data_list as $item)
                        <table class="table table-bordered" style="width: 100%;"
                               deliver_template_id="${item->deliver_template_id!}" data-row="${item->area_info!}">
                            <tr>
                                <td colspan="7" class="area-template-header" style="border-top: 0 !important;">
                                    <strong style="padding-left: 5px;">${item->template_name!}
                                        <#if  ($item->content->has_fee_0_condition==1)（已指定条件包邮）</#if>
                                    </strong>

                                    <div class="pull-right">
                                        <a href="javascript:" deliver_template_id="${item->deliver_template_id!}"
                                           class="clone-template" flag="${item->flag!}">复制模板</a>|
                                        <#if  ($item->flag == 0)
                                        <a href="${edit_url!}?deliver_template_id=${item->deliver_template_id!}">修改</a>|
                                        <#else>
                                        <a href="${edit_url!}?m=edit_weight_template&deliver_template_id=${item->deliver_template_id!}">修改</a>|
                                        </#if>
                                        <a href="javascript:" deliver_template_id="${item->deliver_template_id!}"
                                           class="del-template">删除</a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th style="width: 300px">可配送区域</th>
                                <th>首件（件）</th>
                                <th>运费（元）</th>
                                <th>续件（件）</th>
                                <th>续费（元）</th>
                            </tr>
                            <#if  (!empty($item->content->data_list))
                                <#list  ($item->content->data_list as $key=>$item2)
                                <#if  ($key == 0 && $item2->limit_deliver_area != "1" || $key>0)
                                <tr class="tr-area" align="center">
                                    <td>${item2->area_text!}                   </td>
                                    <td>${item2->first_num!}                   </td>
                                    <td>${item2->first_fee!}                    </td>
                                    <td>${item2->continue_num!}                 </td>
                                    <td>${item2->continue_fee!}                 </td>
                                </tr>
                                </#if>
                            </#list>
                            </#if>
                        </table>
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td style="background: #eee;"></td>
                                </tr>
                            </table>
                        </#list>
                            <#include "/admin/jump_page_admin.ftl">
                    </div>
                <#else>
                <div class="box box-blank">
                    <div class="jumbotron">
                        <h2>你还没有添加运费模板
                            <a href="${add_url!}" class="btn btn-primary">添加新模板</a>
                        </h2>
                    </div>
                </div>
                </#if>

            </div>
        </div>

    </form>
</div>

<script>
    // function remove(id) {
    //     if (confirm("确定删除此行数据吗？")) {
    //         $("#act").val('del');
    //         $("#").val(id);
    //         $("#form1").submit();
    //     }
    // }

    $("#main-table").FixedHead({tableLayout: "fixed"});
</script>

<script language="JavaScript" src="/js/admin/deliver_fee_list.js?v=1.1.5"></script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
