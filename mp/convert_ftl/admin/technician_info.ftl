<#include "/admin/header.ftl">
<style>
    body{
        padding-bottom: 40px;
    }
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
        text-align: center;
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
</style>
<link href="/css/admin/technician_info.css?v=1.1.1" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/technician_list.css?v=1.0.3" type="text/css" />
<div class="title">
    {{--<span>技师管理 / </span>--!}
    {{--<span style="color: #666;">${title.$technician_title!}</span>--!}
    <span><a href="/admin/store/manage/list?top_index=6" style="color:  black">门店列表（${store->store_name!}）</a> /</span>
    <span><a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6"  style="color:  #666">${technician_title!}管理</a></span>
    {{--<span> / ${store->store_name!}</span>--!}
</div>
<div class="main-container fix_every_footer">
    <div class="coupon_type">
        <ul>
            <li class="normal_type ">
                <a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6 ">预约管理</a>
            </li>
            <li class="fenlie_type">
                <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" class="edition_type">服务管理</a>
            </li>
            <li class="fenlie_type actives">
                <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" class="edition_tech" title="技师管理">${technician_title!}管理</a>
            </li>
            <li class="give_to_sb">
                <a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6">评价管理</a>
            </li>
        </ul>
    </div>
    <div class="list_tech">
        <div class="">
            <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6"  class="techtab">${technician_title!}列表</a>
            <a href="/admin/store/services/technician/group?store_id=${store_id!}&top_index=6"  class="techtab">${technician_title!}分组</a>
            <a href="/admin/store/services/technician/add?store_id=${store_id!}&top_index=6"  class="techtab techactive">添加${technician_title!}</a>
        </div>
    </div>
    <form action="/admin/store/services/technician/add?top_index=6" method="post" id="form4">
        {{ csrf_field()!}
        {{--<div class="technician_title">添加${technician_title!}</div>--!}
        <div class="add_content">
            <#if ($technician_info->id)
                <input type="text" id="id" name="id" hidden value="${technician_info->id!}">
            </#if>
            <input type="text" id="store_id" name="store_id" hidden value="${store_id!}">
            <div class="tech_name">
                <label for="technician_name"><span>*</span>${technician_title!}姓名：</label>
                <input type="text" id="technician_name" name="technician_name" value="${technician_info->technician_name!}" placeholder="输入${technician_title!}姓名" >
            </div>
            <div class="tech_phone">
                <label for="technician_mobile"><span>*</span>手机号码：</label>
                <input type="text" id="technician_mobile" name="technician_mobile"  value="${technician_info->technician_mobile!}" placeholder="输入${technician_title!}手机号码">
            </div>
            <div class="tech_img clearfix">
                <input type="hidden" id="bg_img_path" name="bg_img_path" value="${technician_info->bg_img_path ?? "image/admin/tech_moren.png"!}">
                <label><span></span>头像：</label>
                <div class="add_tech_img">
                    <#if ($technician_info->bg_img_path)
                        <img src="http://${image_domain!}/${technician_info->bg_img_path!}" alt="头像">
                    <#else>
                        <img src="http://${image_domain!}/image/admin/tech_moren.png" alt="头像">
                    </#if>
                    <p>更改</p>
                </div>
            </div>
{{--            {{print_r($technician_info)!}--!}
            <div class="tech_intro">
                <label for="technician_introduce"><span></span>介绍：</label>
                <input type="text" id="technician_introduce" name="technician_introduce" value="${technician_info->technician_introduce!}" placeholder="请用一句话介绍${technician_title!}">
            </div>
            <div class="tech_group">
                <input type="hidden" name="technician_groups">
                <label for="technician_group"><span></span>所属分组：</label>
                <select name="group_id" id="">
                    <option value="0">请选择</option>
                    <#list ($technician_group as $group)
                        <option value="${group->group_id!}" <#if ($technician_info->group_id==$group->group_id) selected </#if>>${group->group_name!}</option>
                    </#list>
                </select>
            </div>
            <div class="service_detail clearfix">
                <label for="technician_group"><span></span>服务项目：</label>
                <div class="sd_right">
                    <div class="choose_radio">
                        <input type="radio" name="service_type" id="all_service" <#if ($technician_info->service_type==0) checked </#if> value="0">全部项目
                        <input type="radio" name="service_type" id="sth_service" <#if ($technician_info->service_type==1) checked </#if> value="1">部分项目
                    </div>
                    <div class="choose_detail">
                        <table width="100%" border="1px" borderColor="#eee" class="service_table">
                            <thead>
                                <tr>
                                    <td width="80px">
                                        <input type="checkbox" class="all_choosed">全选
                                    </td>
                                    <td width="300px">服务项目</td>
                                </tr>
                            </thead>
                            <tbody>
                                <#list ($service_list as $service)
                                <tr>
                                    <#if ($technician_info->service_type==1 && count(json_decode($technician_info->service_list, true))>0)
                                        <td width="80px"><input type="checkbox" class="service" name="service_list[]" <#if (in_array($service->id, json_decode($technician_info->service_list, true))) checked </#if> value="${service->id!}"></td>
                                    <#else>
                                        <td width="80px"><input type="checkbox" class="service" name="service_list[]" value="${service->id!}"></td>
                                    </#if>
                                    <td width="300px">
                                        <div>${service->service_name!}</div>
                                        {{--<div class="child_service"><input type="radio" class="spec" name="service_spec[]" service_spec_id="1">香辣的</div>--!}
                                        {{--<div class="child_service"><input type="radio" class="spec" name="service_spec[]" service_spec_id="2">麻辣的</div>--!}
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="remark clearfix">
                <label for="">备注：</label>
                <textarea name="remarks" id="remarks" cols="30" rows="10"placeholder="备注不超过200字">${technician_info->remarks!}</textarea>
            </div>
        </div>
        <div class="tech_btn_group fix_footer">
        <a href="##" class="tech_btn_add">保存</a>
        <a href="##" class="tech_btn_cancel">取消</a>
    </div>
    </form>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
    var hasSaved = true;

    util.inputChange();
    util.selectChange();
    util.radioChange('service_type');
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/admin/technician_info.js?v=1.0.3" type="text/javascript"></script>
<script type="text/javascript">
    getPowerInfo('main_config','technician','sub_5','技师管理',0);
</script>
