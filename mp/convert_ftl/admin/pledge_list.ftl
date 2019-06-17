<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pledge_list.css?v=1.0.2" type="text/css"/>
<div style="min-width: 1090px">
    <div class="title">
        <div>
            <span>基础配置/ </span>
            <span style="color: #666;">服务承诺</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="all_content">
                <div class="instead_title">
                    <div class="it_left">
                        <div class="bold_one">服务承诺</div>
                        <div>1.“服务承诺”是店铺对用户承诺能做到的服务和服务质量。是重视消费者利益，保证自己的产品质量、售后服务</div>
                        <div style="padding-left: 15px">不发布虚假信息，无欺诈消费者的行为。</div>
                        <div>2.服务承诺开启后，用户即可在手机端商品详情中看到，请您如实履行。</div>
                    </div>
                    <div class="it_right">
                        <input type="checkbox" class="switch pass_box" id="checkbox1" <#if ($pledge_switch == 1) checked </#if> name="" value="user_export">
                        <label for="checkbox1" class="switch"></label>
                        <span class="checkbox1_span" style="color: rgb(153, 153, 153);"><#if ($pledge_switch == 1) 已开启 <#else> 已关闭 </#if></span>
                    </div>
                </div>
                <div class="all_config_item">
                    <div class="add_top">
                        <div class="add_pledge" edit="0">+添加服务承诺</div>
                        <span>最多可以添加20条</span>
                    </div>
                    <div style="margin-bottom: 15px;">
                        <table width="100%" class="contain_table">
                            <thead>
                                <tr>
                                    <td width="15%">服务名称</td>
                                    <td width="15%">图标</td>
                                    <td width="50%">承诺说明</td>
                                    <td width="20%">操作</td>
                                </tr>
                            </thead>
                            <tbody>
                                <#list ($data_list as $data)
                                    <tr class="plede_tr">
                                        <td width="15%">${data->pledge_name!}</td>
                                        <td width="15%">
                                            <img src="http://${image_domain!}${data->pledge_logo!}" alt="">
                                        </td>
                                        <td width="50%" style="padding: 8px 45px">${data->pledge_content!}</td>
                                        <td width="20%">
                                            <input type="checkbox" <#if ($data->state == 1) checked="checked" </#if> class="switch pass_box" id="pledge_list_${data->id!}" name="pledge_list[]" pledge_id="${data->id!}">
                                            <label for="pledge_list_${data->id!}" class="switch"></label>
                                            <span style="color: rgb(153, 153, 153);"></span>
                                            <a  href="javascript:void(0)" pledge_id="${data->id!}"  class="edit show_tip" edit="1" data-tips="编辑" style="display: inline; position: static;margin-left: 10px">
                                                <img src="http://${image_domain!}/image/admin/decorate_editor.png" alt="">
                                            </a>
                                            <a href="javascript:void(0)" class="del show_tip" data-tips="删除" pledge_id="${data->id!}"  style="display: inline; position: static;">
                                                <img src="http://${image_domain!}/image/admin/decorate_delete.png" alt="">
                                            </a>
                                        </td>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="template hide">
    <table>
        <tr class="plede_tr">
            <td width="15%"></td>
            <td width="15%">
                <img src="" alt="">
            </td>
            <td width="50%" style="padding: 8px 45px"></td>
            <td width="20%">
                <input type="checkbox" class="switch pass_box" id="" name="pledge_list[]" >
                <label for="pledge_list_1" class="switch"></label>
                <span style="color: rgb(153, 153, 153);">已关闭</span>
                <a  href="javascript:void(0)"  class="edit show_tip" edit="1" data-tips="编辑" style="display: inline; position: static;margin-left: 10px">
                    <img src="http://${image_domain!}/image/admin/decorate_editor.png" alt="">
                </a>
                <a href="javascript:void(0)" class="del show_tip" data-tips="删除"  style="display: inline; position: static;">
                    <img src="http://${image_domain!}/image/admin/decorate_delete.png" alt="">
                </a>
            </td>
        </tr>
    </table>
</div>
<div class="promise_content" style="display: none;">
     <ul class="assess_set_ul">
         <li class="clearfix">
             <div class="li_left"><em>*</em>服务名称：</div>
             <div class="li_right">
                 <input type="text" name="service_name" maxlength="5">
             　  <div class="text_tips1">最多可填写5字</div>
             </div>
         </li>
         <li class="clearfix">
             <div class="li_left"><em>*</em>图标：</div>
             <div class="li_right" style="margin-top:10px">
                 <input type="hidden" name="service_path" value="">
                 <div class="add_image deco_add_img">
                     <img src="" alt="">
                 </div>
                 <span class="text_tips">尺寸：30px * 30px</span>
             </div>
         </li>
         <li class="clearfix">
             <div class="li_left"><em>*</em>承诺说明：</div>
             <div class="li_right" style="margin-top:10px;">
             <textarea name="service_desc" maxlength="300"></textarea>
             <p class="text_tips" style="margin-top: -5px">最多可填写300字</p >
             </div>
         </li>
     </ul>
</div>

<#include "/admin/footer.ftl">
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/image_common.js"></script>
<script type="text/javascript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="/js/admin/pledge_list.js?v=1.0.5"></script>
