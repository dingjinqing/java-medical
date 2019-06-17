<#include "/admin/header.ftl">

<link href="/css/admin/deliver_fee.css?v=0.1.11" rel="stylesheet" type="text/css"/>
<link href="/css/admin/deliver_fee_list.css?v=0.2.2" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .btn-save:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .btn-save:focus{
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
    .add_deliver_weight{
        border-top: 1px solid #f2f2f2;
        background: #f8f8fa;
        padding: 10px;
        text-align: center;
    }
    .btn-save{
        width: 70px;
        height: 30px;
        border: none;
        background: #5A8BFF;
        color: #fff;
    }

</style>
<div class="title">
    <span>商品管理 / </span>
    <span  style="color: #666;">运费模板</span>
</div>
<div class="main-container fix_every_footer">
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

    <form enctype="multipart/form-data" action="${act_url!}" name="form1" id="form1" method="post">
        <input type="hidden" name="act" id="act" value="${act!}">
        {{  csrf_field()!}
        <div class="box panel">
            <div class="panel-body " style="padding: 0;">
                <table class="tab_body show table">
                    {{--<#if  ($msg)--!}
                        {{--<tr class="msg-info">--!}
                            {{--<td colspan="2">--!}
                                {{--<div class="alert alert-block alert-info alert-dismissible" style="margin-bottom: 0">--!}
                                    {{--<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span--!}
                                                {{--aria-hidden="true">&times;</span></button>--!}
                                    {{--${msg!}--!}
                                {{--</div>--!}
                            {{--</td>--!}
                        {{--</tr>--!}
                    {{--</#if>--!}
                    <tr class="moban-name">
                        <td width="90px" align="left" style="padding-left: 4px;">
                            模板名称
                        </td>
                        <td>
                            <form action="${act_url!}" name="form1" id="form1" method="post">
                                <input type="text" name="template_name" id="template_name" value="{!! $data_list->template_name !!}" class="form-control" style="width: 220px" />
                                <input type="hidden" name="deliver_template_id" id="deliver_template_id"
                                       value="${data_list->deliver_template_id!}" />
                                <input type="hidden" name="template_content" id="template_content" />
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top" align="left" style="padding-left: 4px;">
                            配送区域
                        </td>
                        <td>


                            <div class="alert alert-block alert-warning" id="except-area">
                                <label>
                                    <input type="checkbox" name="limit_deliver_area" style="width: 15px;height:15px;"
                                           value="1" class="limit_deliver_area"  id="limit-deliver-area"
                                           <#if ($data_list->content['data_list'][0]['limit_deliver_area']==1) checked </#if>
                                    >除可配送区域外，其他不可配送
                                </label>
                            </div>


                            <div class="template-container">
                                <table class="table table-bordered def_fee_template" style="width: 78%;<#if ($data_list->content['data_list'][0]['limit_deliver_area']==1) display:none; </#if> ">
                                    <tr>
                                        <td colspan=7 id="other-area">
                                            <div class="form-inline">
                                                <span>其他区域运费：</span>

                                                <input type="text" name="first_num" value="${data_list->content['data_list'][0]['first_num'] or 1!}"
                                                       class="form-control first_num"
                                                       style="width: 80px">&nbsp;件内，

                                                <input type="text" name="first_fee" value="${data_list->content['data_list'][0]['first_fee'] or 0!}"
                                                       class="form-control first_fee"
                                                       style="width: 80px">&nbsp;元，每增加

                                                <input type="text" name="continue_num"
                                                       value="${data_list->content['data_list'][0]['continue_num'] or 1!}"
                                                       class="form-control continue_num"
                                                       style="width: 80px">&nbsp;件，增加运费

                                                <input type="text" name="continue_fee"
                                                       value="${data_list->content['data_list'][0]['continue_fee'] or 0!}"
                                                       class="form-control continue_fee"
                                                       style="width: 80px">&nbsp;元
                                            </div>
                                        </td>
                                    </tr>

                                </table>

                                <table id="deliver_template_tbl" class="table table-bordered" style="width: 1000px;">
                                    <tr>
                                        <th class="add-th" style="width: 300px">可配送区域</th>
                                        <th class="add-th">首件（件）</th>
                                        <th class="add-th">运费（元）</th>
                                        <th class="add-th">续件（件）</th>
                                        <th class="add-th" style="border-right-width: 1px;">续费（元）</th>
                                    </tr>
                                    <#if ($data_list->content)
                                        <#list ($data_list->content['data_list'] as $item)
                                            <#if  ($loop->first)
                                            <#else>
                                                <tr class="tr-area">
                                                    <td>
                            <span area-data-code="${item['area_list']!}"
                                  area-data-text="${item['area_text']!}">${item['area_text']!}
                                <input type='hidden' value="${item['area_text']!}"/>
                            </span>

                                                        <div class="pull-right">
                                                            <a href="javascript:void(0);" class="edit-area">编辑</a>
                                                            <a href="javascript:void(0);" class="del-deliver">删除</a>
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <input type="text" name="first_num" value="${item['first_num'] or 1!}"
                                                               class="form-control first_num" style="width: 80px">
                                                    </td>
                                                    <td>
                                                        <input type="text" name="first_fee" value="${item['first_fee'] or 0!}"
                                                               class="form-control first_fee" style="width: 80px">
                                                    </td>
                                                    <td>
                                                        <input type="text" name="continue_num"
                                                               value="${item['continue_num'] or 1!}"
                                                               class="form-control continue_num" style="width: 80px">
                                                    </td>
                                                    <td>
                                                        <input type="text" name="continue_fee"
                                                               value="${item['continue_fee'] or 0!}"
                                                               class="form-control continue_fee" style="width: 80px">
                                                    </td>
                                                </tr>
                                            </#if>

                                        </#list>
                                    </#if>
                                    <tr>
                                        <td colspan="7" class="add-td">
                                            <a href="javascript:void(0);" class="add-deliver-area">指定可配送区域和运费</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="checkbox">
                                <label><input type="checkbox" name="fee_0_cbk" style="width: 15px;height:15px;"
                                              class="fee-0-cbk" <#if ($data_list->content['has_fee_0_condition']==1) checked </#if>
                                              value="1">指定条件包邮（可选）</label>
                            </div>

                            <table id="deliver_fee_0_tbl" class="table table-bordered <#if ($data_list->content['has_fee_0_condition']!=1) hide </#if>"
                                   style="width: 100%">
                                <tr>
                                    <th class="text-left" style="width: 300px">包邮可配送区域</th>
                                    <th>设置包邮条件</th>
                                </tr>
                                <#if ($data_list->content)
                                    <#list ($data_list->content['fee_0_data_list'] as $item)
                                        <tr class="tr-fee-0-area">
                                            <td>
                            <span area-data-code="${item['area_list']!}"
                                  area-data-text="${item['area_text']!}">${item['area_text']!}</span>

                                                <div class="pull-right">
                                                    <a href="javascript:void(0);" class="edit-fee-0-area">编辑</a>
                                                    <a href="javascript:void(0);" class="del-fee-0-deliver">删除</a>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="form-inline">
                                                    <select name="fee_0_condition" class="form-control fee-0-condition">
                                                        <option value="1" <#if ($item['fee_0_condition']==
                                    '1') selected </#if>>件数</option>
                                                        <option value="2" <#if ($item['fee_0_condition']==
                                    '2') selected </#if>>金额</option>
                                                        <option value="3" <#if ($item['fee_0_condition']==
                                    '3') selected </#if>>件数+金额</option>
                                                    </select>&nbsp;&nbsp;

                                                    <span class="fee_0_con1 <#if ($item['fee_0_condition'] != '1') hide </#if>">
                                            满&nbsp;<input type="text" name="fee_0_con1_num"
                                                          value="${item['fee_0_con1_num'] or 1!}"
                                                          class="form-control fee_0_con1_num"
                                                          style="width: 60px">&nbsp;件包邮
                                        </span>
                                                    <span class="fee_0_con2 <#if ($item['fee_0_condition'] != '2') hide </#if>">
                                            满&nbsp;<input type="text" name="fee_0_con2_fee"
                                                          value="${item['fee_0_con2_fee'] or 0!}"
                                                          class="form-control fee_0_con2_fee"
                                                          style="width: 60px">&nbsp;元包邮
                                        </span>
                                                    <span class="fee_0_con3 <#if ($item['fee_0_condition'] != '3') hide </#if>">
                                            满&nbsp;<input type="text" name="fee_0_con3_num"
                                                          value="${item['fee_0_con3_num'] or 1!}"
                                                          class="form-control fee_0_con3_num"
                                                          style="width: 60px">&nbsp;件，
                                            <input type="text" name="fee_0_con3_fee"
                                                   value="${item['fee_0_con3_fee'] or 0!}"
                                                   class="form-control fee_0_con3_fee" style="width: 60px">元包邮
                                        </span>

                                                </div>
                                            </td>
                                        </tr>
                                    </#list>
                                </#if>
                                <tr style="border-top:1px solid #E0DEDB;">
                                    <td colspan="2">
                                        <a href="javascript:void(0);" class="add-fee-0-deliver-area">指定可包邮配送区域和条件</a>
                                    </td>
                                </tr>
                            </table>

                        </td>
                    </tr>
                    {{--<tr>--!}
                        {{--<td valign="top">&nbsp;</td>--!}
                        {{--<td>--!}
                            {{----!}
                        {{--</td>--!}
                    {{--</tr>--!}
                </table>
            </div>

        </div>
    </form>
    <div id="template" class="hide">

        <div id="area_template" style="width: 600px;height: 400px;overflow-y: auto">
            <table class="table table-striped">
                <tr>
                    <td colspan="2">
                        <label>
                            <input type="checkbox" name="sel_all" class="sel-all">全选
                        </label>
                    </td>
                </tr>
                <#list  ($deliver_area as $item)
                    <tr class="province-row">
                        <td width="100px">
                            <label><input type="checkbox" name="province[]" province_id="${item->province_id!}"
                                          province_name="${item->province_name!}"
                                          value="${item->province_id!}">${item->province_name!}

                            </label>
                        </td>
                        <td>
                            <div class="city-list">
                                <#list  ($item->city_list as $item2)
                                    <div class="city-item">
                                        <label><input class="city-cbx" type="checkbox" name="city[]"
                                                      its_province_id="${item->province_id!}"
                                                      city_id="${item2->city_id!}" value="${item2->city_id!}"
                                                      city_name="${item2->city_name!}">${item2->city_name!}
                                            <span class="sel-num"></span>
                                        </label>
                                        <img class="expand" src="http://${image_domain!}/image/admin/expand.png">

                                        <div class="district-list" city_id="${item2->city_id!}">
                                            <#list  ($item2->district_list as $item3)
                                                <label>
                                                    <input type="checkbox" name="district[]" class="district-cbx"
                                                           its_province_id="${item->province_id!}"
                                                           its_city_id="${item2->city_id!}"
                                                           district_id="${item3->district_id!}"
                                                           value="${item3->district_id!}"
                                                           district_name="${item3->district_name!}">
                                                    ${item3->district_name!}
                                                </label>
                                            </#list>
                                        </div>
                                    </div>
                                </#list>
                                <div class="clear"></div>
                            </div>
                        </td>
                    </tr>
                </#list>
            </table>
        </div>

        <table class="area-row-info">
            <tr class="tr-area">
                <td>
                    <span area-data-code="">北京</span>

                    <div class="pull-right">
                        <a href="javascript:void(0);" class="edit-area">编辑</a>
                        <a href="javascript:void(0);" class="del-deliver">删除</a>
                    </div>
                </td>
                <td>
                    <input type="text" name="first_num" value="1" class="form-control first_num" style="width: 80px">
                </td>
                <td>
                    <input type="text" name="first_fee" value="0" class="form-control first_fee" style="width: 80px">
                </td>
                <td>
                    <input type="text" name="continue_num" value="1" class="form-control continue_num" style="width: 80px">
                </td>
                <td>
                    <input type="text" name="continue_fee" value="0" class="form-control continue_fee" style="width: 80px">
                </td>
            </tr>
        </table>

        <table class="fee-0-area-row-info">
            <tr class="tr-fee-0-area">
                <td>
                    <span area-data-code="" area-data-text=""></span>

                    <div class="pull-right">
                        <a href="javascript:void(0);" class="edit-fee-0-area">编辑</a>
                        <a href="javascript:void(0);" class="del-fee-0-deliver">删除</a>
                    </div>
                </td>
                <td>

                    <div class="form-inline">
                        <select name="fee_0_condition" class="form-control fee-0-condition">
                            <option value="1">件数</option>
                            <option value="2">金额</option>
                            <option value="3">件数+金额</option>
                        </select>&nbsp;&nbsp;

                        <span class="fee_0_con1">
                            满&nbsp;<input type="text" name="fee_0_con1_num" value="1" class="form-control fee_0_con1_num"
                                          style="width: 60px">&nbsp;件包邮
                        </span>
                        <span class="fee_0_con2 hide">
                            满&nbsp;<input type="text" name="fee_0_con2_fee" value="0" class="form-control fee_0_con2_fee"
                                          style="width: 60px">&nbsp;元包邮
                        </span>
                        <span class="fee_0_con3 hide">
                            满&nbsp;<input type="text" name="fee_0_con3_num" value="1" class="form-control fee_0_con3_num"
                                          style="width: 60px">件，
                            <input type="text" name="fee_0_con3_fee" value="0" class="form-control fee_0_con3_fee"
                                   style="width: 60px">&nbsp;元包邮
                        </span>
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="add_deliver_weight fix_footer">
        <button class="btn-save ">
            <#if  (!$data_list->deliver_template_id)添加模板<#else>保存</#if>
        </button>
    </div>
</div>

<#include "/admin/footer.ftl">
<script type="text/javascript">
    window.deliver_area = @json($deliver_area);
    window.condition_deliver_area = @json($deliver_area);
    var hasSaved = true;
</script>
<script language="JavaScript" src="/js/admin/deliver_fee.js?v=1.0.6">
</script>
<script>
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
</script>
<script type="text/javascript">

    util.inputChange();
    util.selectChange();
    util.checkboxChange();
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
</script>
