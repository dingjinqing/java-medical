<#include "/admin/header.ftl">
<link href="/css/admin/goods_label.css?v=1.0.9" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .create-good{
        border:1px solid #5a8bff;
    }
    .create-good:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    .create-good:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    #search {
        margin-left: 5px;
        margin-bottom: 4px;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
    }
    .goods_new{
        padding-left: 12px;
    }
    .goods_ck{
        margin-left: -14px;
    }
    thead>tr>th{
        background: #f5f5f5 !important;
    }
    thead>tr{
        border: 1px solid #eee;
    }


</style>

<div class="goods-container">
    <div class="title">
        <div>
            <span>商品管理 / </span>
            <span style="color: #666;">商品标签</span>
        </div>
    </div>
    <div class="main-container">
        <form name="form" action="/admin/goods/label/list?top_index=2"  id="form1" method="post">
            <input type="hidden" name="del">
            <div class="list-center">
                {{ csrf_field()!}
                <input name="goods_id" type="hidden">
                <input name="all" type="hidden">
                <input name="nav" type="hidden" value="${nav!}">
                <span>
                        <span style="padding-left: 30px">标签名称</span>
                        <span class="search-bl">
                            <input type="text" name='keywords' value="${keywords!}" placeholder="请输入标签名称"
                                   class="primary" >
                        </span>
                        <button class="btn_searchinfo">查询</button>
                    </span>
                <a href="/admin/goods/label/add" link="/admin/goods/label/add"  class="create-good goods_edition" target="_blank" data-title="添加商品标签"  edit="0">添加商品标签</a>
            </div>

            <div class="list-bottom" style="background: #fff;padding: 10px">
                <table align="center" class="list-bottom-show">
                    <thead>
                    <tr id="list-bottom-top">
                        <th>标签名称</th>
                        <th>更新时间</th>
                        <th>前端应用模块</th>
                        <th>优先级</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="" class="list">
                        <#list  ($data_list as $item)
                        <tr>
                            <td align="center" width="20%" style="position: relative;" >
                                <span>${item->name!}</span>
                            </td>
                            <td align="center"  class="update_time" width="20%" style=" position: relative;">
                                <span>
                                 ${item->update_time!}
                                </span>
                            </td>
                            <td align="center" width="20%"  class="label_mod">
                                <div class="mod_content">
                                    <div class="label_fl pages_url">
                                        <#if ($item->goods_detail)
                                        <span class="all_label_list"  >商品详情页</span>
                                        </#if>
                                        <#if ($item->goods_list)
                                        <span class="all_label_list"  >商品列表页</span>
                                        </#if>
                                        <#if ($item->goods_select)
                                            <span class="all_label_list"  >商品筛选页</span>
                                        </#if>
                                    </div>
                                    <div class="card_info btn_set" label_id="${item->id!}" goods_detail="${item->goods_detail!}" goods_select="${item->goods_select!}" goods_list="${item->goods_list!}" list_pattern = "${item->list_pattern!}">
                                        <a href="##" style="color:#5E88FC;">设置</a>
                                    </div>
                                </div>

                            </td>
                            <td align="center" width="20%">
                                <span>${item->level!}</span>
                            </td>
                            <td align="center" width="20%" class="share_td">
                                <div>
                                    <span class="goods_new">
                                 <a href="/admin/goods/label/add?id=${item->id!}&nav=${nav!}">编辑</a>
                                    </span>
                                    <span class="goods_new">
                                        <a class="del" id="${item->id!}">删除</a>
                                    </span>
                                </div>
                            </td>
                        </tr>
                            </#list>
                    </tbody>
                    <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table" border="0" style="border-left: none">
                        <tr >
                            <td align="right">
                               <#include "/admin/jump_page_admin.ftl">
                            </td>
                        </tr>
                    </table>

                </table>
            </div>
        </form>
    </div>
</div>

<div id="set-label" style="width: auto;height: auto;border-bottom: 1px solid #eee;display: none">
    <div class="text_warn">
        <img src="/image/admin/notice_img.png">
        <span>可以在这里选择商品标签应用位置</span>
    </div>
    <div class="clearfix label-detail" style="margin-top: 10px">
        <input type="checkbox"  name="goods_detail" style="top: 1px;margin-left: 10px;" >
        <div class="fl" style="width: 70px;display: inline-block;float: none">商品详情页</div>
    </div>
    <div class="clearfix label-list" style="margin-top: 10px">
        <input type="checkbox"  name="goods_list" class="gd_list" style="top: 1px;margin-left: 10px;">
        <div class="fl" style="width: 70px;display: inline-block;float: none">商品列表页</div>
    </div>
    <div class="clearfix label-list" style="margin-top: 10px">
        <input type="checkbox"  name="goods_select"  style="top: 1px;margin-left: 10px;">
        <div class="fl" style="width: 70px;display: inline-block;float: none">商品筛选页</div>
    </div>
    <div class="label-content" style="display: none">
        <span class="re_title" style="text-align: left !important; margin-left: 5px;float: none"><em style="color: red">* </em>标签样式：</span>
        <div class="label-flex">
            <div>
                <input type="radio" name="list_pattern" value="1" checked>
                <img src="http://${image_domain!}/image/admin/label_1.png">
            </div>
            <div>
                <input type="radio" name="list_pattern" value="2">
                <img src="http://${image_domain!}/image/admin/label_2.png">
            </div>
            <div>
                <input type="radio" name="list_pattern" value="3">
                <img src="http://${image_domain!}/image/admin/label_3.png">
            </div>
            <div>
                <input type="radio" name="list_pattern" value="4">
                <img src="http://${image_domain!}/image/admin/label_4.png">
            </div>
        </div>
    </div>
</div>

<script>
    if($(".gd_list").prop("checked")){
        $(".label-content").show();
        $("#set-label").css("border-bottom","none");
    }else{
        $(".label-content").hide();
        $("#set-label").css("border-bottom","1px solid #eee");
    }



    $(".del").click(function () {
        var _this = $(this);
        var id = $(this).attr('id');
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="del"]').val(id);
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(document).on("click",'.gd_list',function () {
        if($(".gd_list").prop("checked")){
            $(".label-content").show();
            $("#set-label").css("border-bottom","none");
        }else{
            $(".label-content").hide();
            $("#set-label").css("border-bottom","1px solid #eee");
        }
    });

    $('.btn_set').on('click',function(){
        var goods_detail = $(this).attr('goods_detail');
        var goods_list = $(this).attr('goods_list');
        var goods_select = $(this).attr('goods_select');
        var list_pattern = $(this).attr('list_pattern');
        // console.log(goods_list);
        // console.log(goods_detail);
        // var have_recommend_pages = $.parseJSON($('#have_recommend_pages').val());
        var id = $(this).attr('label_id');
        // var status = $.parseJSON($(this).attr('status'));
        var _this = $(this);
        layui.use('layer', function () { //独立版的layer无需执行这一句
            layer.open({
                type: 1
                , title: ['选择应用位置', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['590px','']
                , content: $('#set-label') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success: function (index, layero) {
                    console.log(index)
                    var layer_frame = $('#set-label');
                    layer_frame.find("[name='goods_detail']").removeAttr('checked');
                    layer_frame.find("[name='goods_list']").removeAttr('checked');
                    layer_frame.find("[name='goods_select']").removeAttr('checked');
                    layer_frame.find("[name='goods_detail']").each(function () {
                        // var use_pages_arr = util.objectToArray(use_pages);
                        $(this).unbind();
                        $(this).css("background","");
                        if (goods_detail == 1) {
                            $(this).prop('checked','checked');
                        }
                    });
                    layer_frame.find("[name='goods_select']").each(function () {
                        // var use_pages_arr = util.objectToArray(use_pages);
                        $(this).unbind();
                        $(this).css("background","");
                        if (goods_select == 1) {
                            $(this).prop('checked','checked');
                        }
                    });
                    layer_frame.find("[name='goods_list']").each(function () {
                        // var use_pages_arr = util.objectToArray(use_pages);
                        $(this).unbind();
                        $(this).css("background","");
                        if (goods_list == 1) {
                            $(this).prop('checked','checked');
                            $('.label-content').show();
                            $('.label-content').find('input[value="'+list_pattern+'"]').prop('checked','checked');
                        }else{
                            $('.label-content').hide();
                        }
                    });
                }
                , yes: function (index, layero) { //保存按钮的回调
                    console.log(index)
                    var goods_detail = $('input[name="goods_detail"]').prop("checked") == false ? 0:1;
                    var goods_select = $('input[name="goods_select"]').prop("checked") == false ? 0:1;
                    var goods_list = $('input[name="goods_list"]').prop("checked") == false ? 0:1;
                    var list_pattern = $('input[name="list_pattern"]:checked').attr("value");
                    console.log(list_pattern);
                    // if($('input[name="goods_detail"]').prop("checked") == false && $('input[name="goods_list"]').prop("checked") == false){
                    //     util.mobile_alert("请选择前端应用模块");
                    //     return false;
                    // }

                    util.ajax_json('/admin/goods/label/update',function (res) {
                        console.log(res)
                        if (res.error == 0) {
                            var html = '';
                            for(var i in res.content.page_names) {
                                html += '<span>' + res.content.page_names[i] + '</span>';
                            }
                            $('#have_recommend_pages').val(res.content.have_recommend_pages);
                            util.mobile_alert('修改成功');
                            _this.attr('goods_detail',goods_detail);
                            _this.attr('goods_list',goods_list);
                            _this.attr('goods_select',goods_select);
                            _this.attr('list_pattern',list_pattern);
                            _this.parent().parent().parent().find(".update_time").html('<span>'+res.content.update_time+'</span>');
                            _this.parent().find(".pages_url").html(html);
                            $("#set-label").hide();
                            layer.close(index);
                        } else {
                            util.mobile_alert(res.message);
                        }
                    },{act:"update",id:id,goods_detail: goods_detail, goods_select:goods_select,goods_list:goods_list, list_pattern:list_pattern});
                    // layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                    $("#set-label").hide();
                },cancel:function (index, layero) {
                    $("#set-label").hide();
                }
            });
        });
    });
</script>
<#include "/admin/footer.ftl">
