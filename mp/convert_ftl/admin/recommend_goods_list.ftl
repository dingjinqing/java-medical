<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/recommend_goods_manage.css?v=1.0.4" type="text/css" />
<style>
    .paging_footer table td{
        border: none;
    }
    .paging_footer table td a{
        display: inline-block;
    }
    .reco_table table thead tr{
        background: #f5f5f5;
        height: 50px;
        border: 1px solid #eee;
    }
</style>
<div class="title">
    <span>商品管理 / </span><span>商品推荐</span>
</div>
<div class="recommend_container">
    <form action="/admin/goods/recommend/list?top_index=2" method="post" id="form1">
        {{ csrf_field()!}
        <div class="reco_title clearfix">
            <div class="search_area ">
                <input name="recommend_name" type="text" value="${options['recommend_name']!}" placeholder="请输入模板名称">
                <a href="##" class="btn_searchs">查询</a>
            </div>
            <a href="/admin/goods/recommend/config?top_index=2" class="btn_add_reco" target="_blank">新建商品推荐模板</a>
        </div>
        <input hidden id="have_recommend_pages" value="{{json_encode($have_recommend_pages)!}">
        <div class="reco_table">
            <table width="100%" style="margin-bottom: 20px">
                <thead>
                    <td width="20%">模板名称</td>
                    <td width="20%">更新时间</td>
                    <td width="20%">应用页面</td>
                    <td width="20%">模板状态</td>
                    <td width="20%">操作</td>
                </thead>
                <tbody>
                    <#if ($data_list)
                        <#list ($data_list as $recommend)
                        <tr>
                            <td>${recommend->recommend_name!}</td>
                            <td>${recommend->update_time!}</td>
                            <td>
                                <div class="enen">
                                    <div class="pages_url">
                                        <#if ($recommend->page_names)
                                            <#list ($recommend->page_names as $page_name)
                                                <span>${page_name!}</span>
                                            </#list>
                                        </#if>
                                    </div>
                                    <div style="flex: 1" class="btn_set" use_pages="${recommend->recommend_use_page!}"
                                         status="${recommend->status!}" id="${recommend->id!}"
                                         ><a href="javascript:void(0)">设置</a></div>
                                </div>
                            </td>
                            <td>
                                <#if ($recommend->status == 1)已停用
                                <#else>已启用
                                </#if>
                            </td>
                            <td>
                                <input hidden name="stop_recommend" >
                                <input hidden name="delete_recommend" >
                                <a href="/admin/goods/recommend/config?top_index=2&id=${recommend->id!}" class="btn_edit">编辑</a>
                                <#if ($recommend->status == 1)
                                    <a href="javascript:void(0)" class="btn_start" id="${recommend->id!}">启用</a>
                                <#else>
                                    <a href="javascript:void(0)" class="btn_stop" id="${recommend->id!}">停用</a>
                                </#if>
                                <a href="javascript:void(0)" class="btn_delete" id="${recommend->id!}">删除</a>
                            </td>
                        </tr>
                        </#list>
                    </#if>
                </tbody>
            </table>
            <div class="paging_footer">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>

    </form>
    <div id="change_pages" style="display: none;">
        <table width="100%">
            <thead>
                <tr>
                    {{--<td><input type="checkbox" name="choose_all"></td>--!}
                    <td colspan="2">应用页面</td>
                    <td>说明</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]"  value="cart"></td>
                    <td>购物车页</td>
                    <td>展示在购物车页底部，用于商品推荐</td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]" value="orderlist"></td>
                    <td>订单列表页</td>
                    <td>展示在订单列表页底部，用于商品推荐</td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]" value="bargainitem"></td>
                    <td>砍价活动页</td>
                    <td>展示在砍价活动页底部，用于商品推荐</td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]" value="groupbuyitem"></td>
                    <td>参团活动页</td>
                    <td>展示在参团活动页底部，用于商品推荐</td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]" value="search"></td>
                    <td>商品列表页</td>
                    <td>展示在商品列表页底部，用于商品推荐</td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]" value="payment"></td>
                    <td>支付成功页</td>
                    <td>展示在支付成功页底部，用于商品推荐</td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]" value="order_complete"></td>
                    <td>订单完成页</td>
                    <td>展示在订单完成页底部，用于商品推荐</td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="recommend_use_page[]" value="new_search"></td>
                    <td>商品搜索页</td>
                    <td>展示在商品搜索页底部，用于商品推荐 </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">

    //设置的弹框
    $('.btn_set').on('click',function(){

        var use_pages = $.parseJSON($(this).attr('use_pages'));
        var have_recommend_pages = $.parseJSON($('#have_recommend_pages').val());
        var id = $.parseJSON($(this).attr('id'));
        var status = $.parseJSON($(this).attr('status'));
        var _this = $(this);
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var layer = layui.layer;
            layer.open({
                type: 1
                , title: ['选择应用位置', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '500px'
                , content: $('#change_pages') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success: function (index, layero) {
                    var layer_frame = $('#change_pages');
                    layer_frame.find("[name='recommend_use_page[]']").removeAttr('checked');
                    layer_frame.find("[name='recommend_use_page[]']").each(function () {
                        var use_pages_arr = util.objectToArray(use_pages);
                        $(this).unbind();
                        $(this).css("background","");
                        if ($.inArray($(this).val(),use_pages_arr) > -1) {
                            $(this).prop('checked','checked');
                        } else {
                            var has_recommend_pages = util.objectToArray(have_recommend_pages);
                            if (status == 0 && $.inArray($(this).val(),has_recommend_pages) > -1) {
                                $(this).click(function () {
                                    return false;
                                });
                                $(this).css("background","url(/image/admin/cant_choose.png) no-repeat");
                            }
                        }
                    });
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var check_use_pages = [];
                    $('#change_pages').find("[name='recommend_use_page[]']:checked").each(function(){
                        check_use_pages.push($(this).val());
                    });
                    if(check_use_pages.length == 0){
                        util.mobile_alert('请选择应用页面');
                        return false;
                    }
                    util.ajax_json('/admin/ajax/recommend/config/update',function (res) {
                        console.log(res)
                        if (res.error == 0) {
                            var html = '';
                            for(var i in res.content.page_names) {
                                html += '<span>' + res.content.page_names[i] + '</span>';
                            }
                            $('#have_recommend_pages').val(res.content.have_recommend_pages);
                            util.mobile_alert('修改成功');
                            _this.attr('use_pages',JSON.stringify(check_use_pages));
                            _this.parent().find(".pages_url").html(html);
                            layer.close(index);
                        } else {
                            util.mobile_alert(res.message);
                        }
                        $('#change_pages').hide()
                    },{act:"update",id:id,status:status,recommend_use_page:check_use_pages});
                    // layer.close(index);
                }
                , btn2: function(index, layero){
                    //按钮取消的回调
                    $('#change_pages').hide()

                }
                , cancel: function (index, node) {
                    $('#change_pages').hide()
                }

            });
        });
    });

    //停用二次确认
    $(".btn_stop").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用该模板吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $("[name='stop_recommend']").val(_this.attr('id'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    //启用二次确认
    $(".btn_start").click(function () {
        var use_pages = $(this).parent().parent().find("btn_set").attr('use_pages');
        util.ajax_json('/admin/ajax/recommend/config/update',function (res) {
            if (res.error == 0) {
                setTimeout(util.mobile_alert(res.content),2000)

                $("#page").val(1);
                $("#form1").submit();
            } else {
                util.mobile_alert(res.message);
            }
        },{act:"start",id:$(this).attr('id')})
    });
    //删除二次确认
    $(".btn_delete").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该模板吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="delete_recommend"]').val(_this.attr("id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })

    $(".btn_searchs").click(function (){
        $("#page").val(1);
        $("#form1").submit();
    })
</script>