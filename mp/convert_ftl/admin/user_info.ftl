<html>
    <head>
        <link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css" />
        <link rel="stylesheet" href="/css/admin/user_manage.css?v=1.0.0" type="text/css" />
        <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
        <link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.0" type="text/css"/>
        <meta name="csrf-token" content="{{ csrf_token()!}">
    </head>
    <body>
        <div>
            <div class="main-container">
                <div class="goods-box">
                    <form name="formData" <#if ($user->user_id)action="/admin/user/manage/edit?user_id=${user->user_id!}" <#else> action="/admin/user/add" </#if> id="form1" method="post" >
                        {{ csrf_field()!}
                        <input type="hidden" name="spec_info"/>
                        <div class="goods-box-edit">
                            <div class="goods-edit-basic">
                                <table width="100%" class="basic-table">
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.sex")!}</td>
                                        <td>
                                            <select name="sex" id="sex">
                                                <option value="" <#if (!$user->sex) selected </#if>>请选择用户性别</option>
                                                <option value="m" <#if ($user->sex == 'm')selected="selected" </#if>>男</option>
                                                <option value="f"  <#if ($user->sex == 'f')selected="selected" </#if>>女</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.birthday")!}</td>
                                        <td>
                                            <select name="birthday_year" class="add_user_sel" id="birthday_year">
                                                <option value="0">请选择</option>
                                                <#list ($year as $item)
                                                    <option value="${item!}"  <#if ($user->birthday_year == $item)selected="selected" </#if>>${item!}</option>
                                                </#list>
                                            </select>&nbsp;年&nbsp;
                                            <select name="birthday_month" class="add_user_sel" id="birthday_month">
                                                <option value="0">请选择</option>
                                                <#list ($month as $item)
                                                    <option value="${item!}" <#if ($user->birthday_month == $item)selected="selected" </#if>>${item!}</option>
                                                </#list>
                                            </select>&nbsp;月&nbsp;
                                            <select name="birthday_day" class="add_user_sel" id="birthday_day">
                                                <option value="0">请选择</option>
                                                <#list ($day as $item)
                                                    <option value="${item!}" <#if ($user->birthday_day == $item)selected="selected" </#if>>${item!}</option>
                                                </#list>
                                            </select>&nbsp;日&nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.real_name")!}</td>
                                        <td>
                                            <input type="text" name="real_name"
                                                   value="${user->real_name!}"
                                                   placeholder="{{ trans("admin/user_info.input_real_indetify")!}" id="real_name"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.in_area")!}</td>
                                        <td>
                                            <select name="province_code" class="add_user_sel" id="province_code">
                                                <option value="" <#if (!$user->province_code) selected </#if>>请选择</option>
                                                <#list ($province as $item)
                                                    <option value="${item->province_id!}" <#if ($user->province_code == $item->province_id)selected="selected" </#if>>${item->name!}</option>
                                                </#list>
                                            </select>
                                            <select name="city_code" class="add_user_sel" id="city_code">
                                                <option value="" <#if (!$user->city_code) selected </#if>>请选择</option>
                                                <#list ($city as $item)
                                                    <option value="${item->city_id!}" <#if ($user->city_code == $item->city_id)selected="selected" </#if>>${item->name!}</option>
                                                </#list>
                                            </select>
                                            <select name="district_code" class="add_user_sel" id="district_code">
                                                <option value="" <#if (!$user->district_code) selected </#if>>请选择</option>
                                                <#list ($district as $item)
                                                    <option value="${item->district_id!}" <#if ($user->district_code == $item->district_id)selected="selected" </#if>>${item->name!}</option>
                                                </#list>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.hunyin")!}</td>
                                        <td>
                                            <select name="marital_status" id="marital_status">
                                                <option value="0">请选择</option>
                                                <option value="1" <#if ($user->marital_status == 1)selected="selected" </#if>>未婚</option>
                                                <option value="2" <#if ($user->marital_status == 2)selected="selected" </#if>>已婚</option>
                                                <option value="3" <#if ($user->marital_status == 3)selected="selected" </#if>>保密</option>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.month_money")!}</td>
                                        <td>
                                            <select name="monthly_income" id="monthly_income">
                                                <option value="0">请选择</option>
                                                <#list ($income as $k => $item)
                                                    <option value="${k!}" <#if ($user->monthly_income == $k)selected="selected" </#if>>${item!}</option>
                                                </#list>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.indeify")!}</td>
                                        <td><input type="text" name="cid" value="${user->cid!}" placeholder="{{ trans('admin/user_info.indefiy_code')!}" id="cid"/></td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.study")!}</td>
                                        <td>
                                            <select name="education" id="education">
                                                <option value="0">请选择</option>
                                                <#list ($education as $k => $item)
                                                    <option value="${k!}" <#if ($user->education == $k)selected="selected" </#if>>${item!}</option>
                                                </#list>
                                            </select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="right">{{ trans("admin/user_info.in_hangye")!}</td>
                                        <td>
                                            <select name="industry_info" id="industry_info">
                                                <option value="0">请选择</option>
                                                <#list ($industry as $k => $item)
                                                    <option value="${k!}" <#if ($user->industry_info == $k)selected="selected" </#if>>${item!}</option>
                                                </#list>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
        <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
        <script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/util.js"></script>
        <script language="JavaScript" src="/js/admin/util.js?v=1.0.9"></script>
        <script type="text/javascript">
            $("select[name='province_code']").change(function(){
                var pid= $(this).val();
                var data = {};
                data.province_id = pid;
                util.ajax_json('/admin/ajax/user/address',function(d){
                    if(d && d.error == 0){
                        var html = '';
                        var first_city_id = d.content.city[0].city_id;
                        $.each(d.content.city,function(i,e){
                            html +='<option value="'+e.city_id+'" >'+e.name+'</option>';
                        });
                        $("select[name='city_code']").empty().append(html);
                        html='';
                        $.each(d.content.district,function(i,e){
                            html +='<option value="'+e.district_id+'" >'+e.name+'</option>';
                        });
                        $("select[name='district_code']").empty().append(html);
                    }
                },data)
            });
            $("select[name='city_code']").change(function(){
                var cid= $(this).val();
                var data = {};
                data.city_id = cid;
                util.ajax_json('/admin/ajax/user/address',function(d){
                    if(d && d.error == 0){
                        var html = '';
                        $.each(d.content,function(i,e){
                            html +='<option value="'+e.district_id+'" >'+e.name+'</option>';
                        });
                        $("select[name='district_code']").empty().append(html);
                    }
                },data)
            });
        </script>
    </body>
</html>



