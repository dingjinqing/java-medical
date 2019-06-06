<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/shop_decorate.css?v=1.0.0" type="text/css" />
<style type="text/css">
    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
        color: #5a8bff;
    }
    .tb-decorate-a .share_span{
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 25px;
        left: -125px;
        width: 285px;
        text-align: center;
        z-index: 9;
        display: none;
    }
    .tb-decorate-a .share_span .share_sj{
        position: absolute;
        right: 140px;
        top: -7px;
    }
    .tb-decorate-a .share_span span{
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }
    .tb-decorate-a .share_span .code_imgs{
        display: block;
        margin:0 auto;
    }
    .tb-decorate-a .share_span a{
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
    }
    .share_link{
        padding-top: 15px;
        width: 100%;
    }
    .share_link input{
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }
    .share_link button{
        float: right;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
    }
    .add-child-btn:hover{
        background-color: #447af9;
    }
    .primary {
        width: 195px;
        height: 26px;
        background-color: #fff;
        border: none;
        color: #333;
        font-size: 14px;
        padding-left: 8px;
        outline: none;
    }
    .search-bl {
        width: 228px;
        height: 30px;
        margin: 10px 10px;
        display: inline-block;
        border-radius: 3px;
        border: 1px solid #ccc;
        line-height: 30px;
    }
    .btn_searchinfo {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        line-height: 30px;
        border-radius: 2px;
    }
    .add_btn_page {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 115px;
        height: 30px;
        line-height: 30px;
        float: right;
        margin-top: 10px;
        padding-left: 15px;
        border-radius: 2px;
    }
    .add-child-ul li{
        display: inline-block;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>小程序管理 / </span>
        <span style="color: #666;">页面分类</span>
    </div>
    <div class="main-container" style="padding: 0px">
        <form name="formData" action=""  id="form1" method="post" >
            <ul class="add-child-ul">
                <li style="margin-left: 20px">
                     <span style="display: inline-block;">
                        <span>页面分类</span>
                        <span class="search-bl">
                            <input type="text" name="keywords" value="${input_map['keywords']!}" placeholder="请输入页面分类名称" class="primary">
                            <img src="http://mpimg.zx.cn/image/admin/search.png" alt="" id="search">
                        </span>
                        <button class="btn_searchinfo">查询</button>
                    </span>
                </li>
                <li style="float: right;margin-right: 20px">
                    <a href="javascript:void(0)" class="add_btn_page add_edit" flag="0">添加页面分类</a>
                </li>
            </ul>
            <div class="return-goods-box" style="padding: 0px 20px;">
                <input type="hidden" name="del">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list" style="width: 100%;margin-left: 0px">
                            <thead>
                            <tr>
                                <th width="28%">页面分类</th>
                                <th>包含页面数量</th>
                                <th width="25%">创建时间</th>
                                <th width="31%">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list data_list as data >
                                <tr>
                                    <td width="28%">${data.name!}</td>
                                    <td><a href="/admin/manage/decorate/list?cat_id=${data.id!}" class="" target="_blank">${data.count!}</a></td>
                                    <td width="25%">${data.create_time!}</td>
                                    <td width="31%">
                                        <a href="javascript:void(0)" class="add_edit" flag="${data.name!}" id="${data.id!}">编辑</a>
                                        -
                                        <a href="javascript:void(0)" class="del" id="${data.id!}">删除</a>
                                        -
                                        <a href="/admin/manage/decorate/list?cat_id=${data.id!}" class="" target="_blank">查看页面</a>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                    <div class="clearfix" style="margin-top: 20px">
                        <#import "/admin/jump_page_admin.ftl" as pt>
                        <@pt.page_template data_list=data_list page=page image_domain=image_domain />
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<#noparse>
<script type="text/javascript">
    $(".del").click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="del"]').val(_this.attr("id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });

    $(".add_edit").click(function () {
        var _this = $(this);
        if(_this.attr("flag") == 0){
            //添加
            var tit = '添加页面分类';
            var text = '';
        }else{
            //编辑
            var text = _this.attr("flag");
            var tit = '编辑页面分类';
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;padding: 20px 0px; border-bottom: 1px solid rgb(238, 238, 238)">' + '<div><em style="padding: 0 5px;color: red">*</em><span>页面分类名称：</span><input type="text" value="'+text+'" name="cat_name" placeholder=""></div>' + '</div>', {
                title: [tit, 'text-align:center;padding: 0px;']
                , area: '365px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                if($("input[name='cat_name']").val().length<1){
                    util.mobile_alert("请填写页面分类名称");
                    return false;
                }
                if($("input[name='cat_name']").val().length>10){
                    util.mobile_alert("页面分类名称不得超过十个字");
                    return false;
                }
                var param = {
                    'name':$("input[name='cat_name']").val(),
                    'id':_this.attr("id")
                }
                util.ajax_json('/admin/manage/classification/judge?act=name',function (r) {
                    if(r && r.error == 0){
                        util.ajax_json("/admin/manage/classification/list",function (res) {
                            if(res && res.error==0){
                                if(tit == '编辑页面分类'){
                                    util.mobile_alert("修改成功");
                                }else{
                                    util.mobile_alert("添加成功");
                                }
                                location.reload();
                            }else{
                                util.mobile_alert("操作失败");
                            }
                        },param)
                    }else{
                        util.mobile_alert("页面分类名称重复");
                    }
                },param)

                layer.close(index);
            });
        });
    })
    $("input[name='cat_name']").keyup(function () {
        if($(this).val().length>0){
            util.mobile_alert("页面分类名称不得超过十个字");
            return false;
        }
    })
    //新建微页面
    $('.add-child-btn').click(function(){
        //getPowerInfo('num_config','decorate_num','','装修页面数量');
        var data = getPowerInfo('num_config','decorate_num','','装修页面数量',1,0);
        //console.log(data);
        if(data.tip != 1){
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type: 2
                    , title: ['选择页面模板', 'text-align:center;padding: 0px;']
                    , offset: '95px' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['716px','500px']
                    , content: '/admin/frame/decorate/choose' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
//                , btn: ['确定', '取消']
//                , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,success: function (layero, index) {

                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var iframe = layer.getChildFrame('body', index);
                        var param = {
                            // "admin_message":iframe.contents().find("textarea[name='admin_message']").val(),
                        };
                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调
                    }
                });
            });
        }
    })
</script>
</#noparse>
<#include "/admin/footer.ftl">
