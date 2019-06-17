<#include ("system.header")
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<style>
    .panel-body table tbody tr td{
        display: inline-block;
        padding: 5px;
    }
    .panel-body table tbody tr:first-child td:last-child{
        margin-left: 14px;
    }
    .panel-body table tbody tr:last-child td:last-child{
        margin-left: 14px;
    }
</style>
<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/category/list">分类管理列表</a>
    </li>
    {{--<li <#if ($nav_type==2)class="active"</#if>><a href="#" data-toggle="tab" url="/system/category/add">分类管理添加</a>--!}
    {{--</li>--!}
    <#if ($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="#">分类管理编辑</a></li>
    </#if>
</ul>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>


<form action="/system/category/list" name="form1" id="form1" method="post" >
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="cat_id" id="cat_id" value="">
    <input type="hidden" name="cat_img"	id="cat_img" value="">
    <input type="hidden" name="parent_id"	id="parent_id" value="">
    <input type="hidden" name="cat_type" id="cat_type" value="${cat_type!}">
    {{ csrf_field()!}
    <div class="box panel panel-body">
        <table cellspacing='1' cellpadding='3'>
            <tr>
                <td>{{ trans("system/category.top_name.parent_cate")!}：</td>
                <td>
                    <select name="level0" id="level0" onchange="select_cat(0);">
                        <option value="0">{{ trans("system/category.top_name.select_cat")!}</option>
                        <#list ($cat_list as $item)
                            <option value="${item->cat_id!}" <#if ($cat_id == $item->cat_id) selected="selected" </#if>>${item->cat_name!}</option>
                        </#list>
                    </select>
                    <select name="level1" id="level1" onchange="select_cat(1);" style="display: none">
                    </select>
                    <select name="level2" id="level2"  style="display: none">
                    </select>
                </td>
            <tr>
                <td>{{ trans("system/category.top_name.cat_name")!}：</td>
                <td><input type="text" name="cat_name" maxlength="20" value="${cat_name!}" size="34">

            </tr>
            <tr>
                <td>{{ trans("system/category.top_name.first")!}：</td>
                <td>
                    <input type='text' name='first'/>
                    <input type="button" class="primary" value="{{ trans("system/category.top_name.add")!}" name="add" class="button">
                </td>
            </tr>
        </table>
    </div>
    <div class="box panel main-table">
        {{--显示分类管理细节--!}
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th>{{ trans("system/category.cat_id")!}</th>
                    <th width="50px">{{ trans("system/category.level")!}</th>
                    <th>{{ trans("system/category.cat_name")!}</th>
                    <th>{{ trans("system/category.top_name.first")!}</th>
                    <th width="210px">操作</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr>
                        <td  style='text-align:center'>${item['cat_id']!}</td>
                        <td width="50px" style="padding-left:${item['level']*20!}px;">${item['level']!}</td>
                        <td style="padding-left:${item['level']*20!}px;">{!! $item['cat_name']  !!}</td>
                        <td  style='text-align:center'>${item['first']!}</td>
                        <td width="210px" style='text-align:center'>
                            <a href="/system/category/edit?cat_id=${item['cat_id']!}">{{ trans("system/category.bottom_name.edit")!}</a>&nbsp;&nbsp;
                            <a href="javascript:void(0);" onclick="remove('${item['cat_id']!}')">{{ trans("system/category.bottom_name.delete")!}</a>&nbsp;&nbsp;
                        </td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>

</form>
<script type="text/javascript" src="/js/system/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/system/jImageManager.js"></script>
<script>
    function remove(id) {
        if (confirm("确定删除此行数据吗？")) {
            $("#act").val('del');
            $("#cat_id").val(id);
            $("#form1").submit();
        }
    }

    $("#main-table").FixedHead({tableLayout: "fixed"});

    $('input[name="add"]').click(function(){
        var cat_name = $("input[name='cat_name']").val();
        if($('#level2').val()>0){
            $("#parent_id").val($('#level2').val());
        }else if($('#level1').val()>0) {
            $("#parent_id").val($('#level1').val());
        }else if($('#level0').val()>0) {
            $("#parent_id").val($('#level0').val());
        }
        if(cat_name == null || cat_name == '') {
            art.dialog.tips("分类名称不能为空");
            return false;
        }else{
            add_cat();
        }
    });

    function add_cat(id) {
        $("#act").val('add');
        $("#form1").submit();
    }
    function select_cat(level,parent_id) {
        var url = "/system/category/level/list";
        var param = {};
        param.level = parseInt(level+1);
        if (parent_id == null || parent_id == '') {
            param.parent_id = $('#level' + level).val();
        }else{
            param.parent_id = parent_id;
        }
        if(level==0){
            $('#level1').html( '');
            $('#level1').hide();
            $('#level2').html( '');
            $('#level2').hide();
        }
        var op = '<option value="0">{{ trans("system/category.top_name.select_cat")!}</option>';
        util.ajax_json(url, function (d) {
            if (d && d.error == 0) {
                var next_level = parseInt(level+1).toString();
                var html = '';
                var cat_id = 0;
                for (var i in d.content){
                    var t = d.content[i];
                    cat_id = d.content[0].cat_id;
                    html += '<option value="'+t.cat_id+'"';
                    if(i==0) {
                       // html += ' selected="selected" ';
                    }
                    html += '>'+ t.cat_name +'</option>';
                }
                setTimeout(function () {
                    if(html) {
                        $('#level' + next_level).html(op + html);
                        $('#level' + next_level).show();
                    }else{
                        $('#level' + next_level).html( html);
                        $('#level' + next_level).hide();
                    }
                    if(param.level<=2 && cat_id>0) {
                        //select_cat(param.level,cat_id);
                    }
                }, 500);
                return true;
            } else if (d && d.error < 0) {
                art.dialog.tips('获取数据失败');
                return false;
            }
        }, param);
    }
</script>

<#include ("system.footer")
