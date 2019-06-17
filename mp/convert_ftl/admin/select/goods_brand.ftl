<html>
<head>
    <script type="text/javascript" src="{{asset('js/jquery-1.11.1.min.js')!}"></script>
    <script type="text/javascript" src="{{asset('js/admin/util.js')!}"></script>
    <link href="{{asset('css/admin/common.css')!}?v=1.1.2" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="{{asset('css/admin/select_sort.css')!}?v=1.0.0" type="text/css" />
    <link rel="stylesheet" href="{{asset('css/admin/select/select_label.css')!}?v=1.0.2" type="text/css" />
</head>
<body>
<div id="set-category">
    <#if (!$data_list)
        <div class="no_category">
            <input id="select_ids" value="" hidden>
            <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无品牌</p>
            </div>
        </div>
    <#else>
        <div class="select-page">
            <form action="{{url('/admin/frame/goods/brand/select')!}" method="post" id="form1">
                <div class="select-input-area">
                    <input id="select_ids" name="select_ids" value="${select_ids!}" hidden>
                    <input id="page_all_ids" value="${page_all_ids!}" hidden>
                    <input id="all_label_ids" value="${all_label_ids!}" hidden>
                    <div class="select-input-row">
                        品牌名称：<input name="keywords" type="text" value="${options['keywords']!}">
                    </div>
                    <div class="select-input-row">
                        品牌分类：
                        <select name="classify_id" id="">
                            <option value="0">请选择</option>
                            <#list ($classify_list as $key => $item)
                                <option value="${key!}"
                                        <#if ($options['classify_id'] == $key)selected="selected"</#if>>${item!}</option>
                            </#list>
                        </select>
                    </div>
                    <div><button class="btn_search">查询</button></div>
                </div>
                <div class="select-content">
                    <table>
                        <thead>
                        <tr>
                            <td width="15%">
                                <#if  (count($data_list) != 0)
                                    <input class="select-page-ids" type="checkbox"/>本页全选
                                </#if>
                            </td>
                            <th width="30%">品牌名称</th>
                            <th width="30%">品牌分类</th>
                            <th width="25%">创建时间</th>
                        </tr>
                        </thead>
                        <tbody  class="select-table-tbody">
                        <#list  ($data_list as $item)
                            <tr class="<#if  (in_array($item->id,explode(',',$select_ids))) has-select-row </#if>">
                                <td><input class="select-row" type="checkbox" value="${item->id!}"
                                           <#if  (in_array($item->id,explode(',',$select_ids))) checked </#if>/></td>
                                <td>${item->brand_name!}</td>
                                <td>${classify_list[$item->classify_id]!}</td>
                                <td>${item->add_time!}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <div>
                        <div class="all_check" style="width: 10%; float: left;line-height: 50px;<#if (count($data_list) == 0) display:none </#if>" >
                            <input class="select-all-ids" type="checkbox"/>全选
                        </div>
                        <div class="jump_page" style="<#if (count($data_list) != 0) width: 90%; </#if> float: left"><#include ('admin.jump_page_admin')</div>
                    </div>
                </div>

            </form>
        </div>
    </#if>
</div>
<script type="text/javascript" src="{{asset('js/admin/select/multiple_select.js')!}?v=1.0.5"></script>
</body>
</html>