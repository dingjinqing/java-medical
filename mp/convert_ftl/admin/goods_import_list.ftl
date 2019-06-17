<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_import_list.css?v=1.0.1" type="text/css"/>
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">${title!}</span>
</div>
<div class="main_container">
    <div class="goods_import_container">
        <div class="goods_title clearfix">
            <div class="fl">说明：商品导入一般需要几到十几分钟，完成时会提示和自动刷新页面，并新增导入操作记录，导入完成之前请勿关闭页面或在此页面进行其他操作，以免造成数据错误</div>
            <div class="fr">
                <span class="goods_import">商品导入</span>
            </div>
        </div>
        <div class="order-info">
            <form action="" method="post" id="form_choose">
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>批次号</span>
                            <input type="text" name="id" value="${option['id']!}" placeholder='请输入批次号' />
                        </div>
                        <div class="fl" style="width: auto;">
                            <span>{{ trans("admin/user_list.operate_time")!}</span>
                            <input  style="width: 150px; height: 30px;" type="text" name="start_time" value="${option['start_time']!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" />
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input  style="width: 150px; height: 30px;" type="text" name="end_time" value="${option['end_time']!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off" />
                        </div>
                        <span class="btn-choose">{{ trans("admin/user_list.choose")!}</span>
                    </li>
                </ul>
            </form>
        </div>
        <table class="goods_import_table" width="100%;">
            <thead>
                <tr>
                    <th>批次号</th>
                    <th>操作时间</th>
                    <th>导入类型</th>
                    <th>成功数量(sku)</th>
                    <th>失败数量(sku)</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            <#list  ($list as $item)
                <tr>
                    <td>${item->id!}</td>
                    <td>${item->add_time!}</td>
                    <td><#if ($item->is_update == 0)新增<#else>更新</#if></td>
                    <td>${item->success_num!}</td>
                    <td>${item->total_num - $item->success_num!}</td>
                    <td>
                        <#if  ($item->total_num - $item->success_num > 0)
                        <a href="/admin/goods/export?batch_id=${item->id!}">下载失败数据</a>
                        <br>
                        </#if>

                        <#if (!empty($item->import_file_path))
                        <a href="/admin/goods/import/file/export?filePath=${item->import_file_path!}">下载原文件</a>
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="clearfix no_data">
            <#include "/admin/jump_page_admin.ftl">
        </div>
    </div>
</div>

<#include "/admin/footer.ftl">
<div class="goods_import_popup">
    <form action="" method="POST" id="form2">
        {{csrf_field()!}
        <p class="import_popup_title">第一步：模板下载</p>
        <div class="card_box">
            <div>
                <span>下载模板：</span>
                <a href="{{ main_url('doc/goods.xlsx')!}" style="color: #5a8bff;">商品导入文件模板</a>
            </div>
        </div>
        <p class="import_popup_title">第二步：选择类型</p>
        <div class="card_box">
            <div>
                <input type="radio" name="is_update" value="0" checked >新增&nbsp;&nbsp;  
                <input type="radio" name="is_update" value="1">更新
            </div>
            <div class="file_upload_explain">
                <p>说明：默认为新增，若选择新增对商家编码重复的商品不做更新操作；若选择更新即可更新商品也可新增商品</p>
            </div>
        </div>
        <p class="import_popup_title">第三步：数据导入</p>
        <div class="file_upload">
            <div>
                <span style="vertical-align: middle">上传文件：</span>
                <div class="file_box clearfix">
                    <span class="fl filetext"></span>
                    <span class="fr" onclick="document.getElementById('file').click()">浏览...</span>
                </div>
                <input type="file" name="goods_import" id="file" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[0].innerText=this.value">
            </div>
            <div class="file_upload_explain">
                <p>导入规则：文件当前仅支持excel格式；每次导入商品条数请不要超过<span style="color: red">2000</span>条</p>
            </div>
        </div>
    </form>
</div>
<script>
    $(".btn-choose").click(function () {
        $("#form_choose").submit();
    })
    
    $(".goods_import").click(function(){
        layui.use('layer', function() {
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['商品导入', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['714px','auto']
                , content: $('.goods_import_popup')
                , btn: ['导入']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    var formData = new FormData();
                    formData.append('goods_import', $('#file')[0].files[0]);
                    formData.append('is_update', $('input[name="is_update"]:checked').val());

                    $.ajaxSetup({
                        contentType : false,
                        processData : false
                    });
                    util.ajax_json('/admin/goods/import', function (response) {
                        if (response.error == 0) {
                            util.mobile_alert(response.content);
                            setTimeout(function () {
                                layer.close(index);
                                location.reload();
                            }, 2000);
                                
                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, formData)
                    $('.goods_import_popup').hide()
                    layer.close(index);
                }
                ,cancel:function(){
                    $('.goods_import_popup').hide()
                }
            });
        });
    })
</script>