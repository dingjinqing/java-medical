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
    .add-version-btn {
        background-color: #5a8bff;
        border: 1px solid #5A8BFF;
        color: #fff;
        font-size: 14px;
        margin: 20px 0 20px 30px;
        padding: 3px 10px;
    }
    .add-version-btn:hover{
         background-color: #447af9;
         color: #fff;
     }
    .pop_up_container{
        padding: 20px 16px;
        border-bottom: 1px solid #eee;
    }
    .add_link_container p+p{
        margin-top:20px;
    }
    .add_link_container p input{
        height: 30px;
        border: 1px solid #ccc;
        width: 250px;
        padding-left:3px;
    }
    #add_link{
        display: none;
    }
    #add_link input[name="app_name"]{
        margin-left:14px;
    }
    .tb-decorate-list {
        width: 90%;
    }
    .text-warning {
        float: left;
        //height: 30px;
        line-height: 30px;
        padding: 0 20px;
        color: #666;
        font-size: 12px;
        background-color: #fff7eb;
        border: 1px solid #ffd5a3;
    }
    .no_button{
        background-color: #d2d2d2;
        border: 1px solid #d2d2d2;
        color: #fff;
    }
    .no_button:hover {
        background-color: #d2d2d2;
        color: #fff;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/shop_decorate.wx_manage")!} / </span>
        <span style="color: #666;">${title!}</span>
    </div>
    <div class="main-container">
        <ul class="add-child-ul">
            <li>
                <a href="#" class="add-child-btn" >添加小程序链接</a>
                <a href="#" class="add-version-btn <#if ($add_version && $add_version->flag==0)no_button </#if>" ><#if ($add_version && $add_version->flag==0) 已提交版本申请 <#elseif>($add_version && $add_version->flag==1) 申请发布新版本 <#else> 申请发布新版本 </#if></a>
                <div class="system_info" style="display: inline-block;float: none;">
                    <p class="system_info_prompt">
                        <#if ($add_version && $add_version->flag==0) 已于${add_version->add_time!}提交版本申请，等待发布新版本 <#elseif>($add_version && $add_version->flag==1) 已于${add_version->update_time!}发布审核，可以修改跳转链接后，再次申请发布 <#else> 添加小程序跳转链接后可以提交发布新版本 </#if>
                        {{--<img src="http://${image_domain!}/image/admin/system_icon.png" />--!}
                    </p>
                </div>
                <br/>
                <div class="text-warning" style="width: 86.6%;margin: 0px 0 10px 30px;">
                    <img src="http://${image_domain!}/image/admin/notice_img.png" alt="">
                    注意事项：<br/>
                    &nbsp;&nbsp;1、小程序跳转可添加数量最多是8个，提交审核时只能提交最新的8个。<br/>
                    &nbsp;&nbsp;2、每次修改后，需重新提交版本申请，待审核通过后方可生效。<br/>
                    &nbsp;&nbsp;3、申请后，微信官方审核时间是1~7个工作日，请耐心等待。<br/>

                    {{--每个小程序可跳转到其他小程序的数量限制是10个；其中小程序购物单专用一个，每次提交审核时会提取列表中最新启用的9条提交，审核通过后在修改是不管用的，需要等到下个版本提交审核，配置的链接才会生效。编辑好列表后，可以提交申请发布新版本，我们会在每个工作日再更新一个版本，审核时间以小程序官网审核结果为准，一般不超过7个工作日。--!}
                </div>
            </li>
        </ul>

        <div class="return-goods-box">
            <form name="formData" action="/admin/manage/jump/list"  id="form1" method="post" >
                {{ csrf_field()!}
                <input type="hidden" name="act">
                <input type="hidden" name="flag">
                <input type="hidden" name="app_id">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                                <tr>
                                    <th width="20%">小程序名称</th>
                                    <th width="25%">小程序AppID</th>
                                    <th width="15%">添加时间</th>
                                    <th width="10%">可用状态</th>
                                    <th width="10%">启用状态</th>
                                    <th width="20%">{{ trans("admin/shop_decorate.shop_decorate_list.operation")!}</th>
                                </tr>
                            </thead>
                            <tbody>
                            <#if ($result->count())
                            <#list ($result as $item)
                                <tr>
                                    <td>
                                        <span>${item->app_name!}</span>
                                    </td>
                                    <td>${item->app_id!}</td>
                                    <td>${item->add_time!}</td>
                                    <td><#if ($item->usable==1)可用 <#else> 不可用 </#if></td>
                                    <td><#if ($item->flag)已停用<#else>使用中</#if></td>
                                    <td class="tb-decorate-a">
                                        <a href="javascript:void(0)" class="del" app_id="${item->app_id!}">{{ trans("admin/shop_decorate.shop_decorate_list.delete")!}</a>
                                        &nbsp;-&nbsp;
                                        <a href="javascript:void(0)" class="stop" app_id="${item->app_id!}" flag="${item->flag!}"><#if (!$item->flag) 停用 <#else> 启用 </#if></a>
                                    </td>
                                </tr>
                               </#list>
                                </#if>
                            </tbody>
                        </table>
                        <#if (!$result->count())
                            <div class="no_data_style" style="width: 90%;border: 1px solid #eee;height: 100px;margin-top: 10px;margin-left: 10px;">
                                <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                </div>
                                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                            </div>
                        </#if>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div id="add_link">
    <div class="add_link_container pop_up_container">
        <form name="form2" action="/admin/manage/jump/list"  id="form2" method="post" >
            {{ csrf_field()!}
            <input type="hidden" name="act" value="add">
            <p class="mini_program_name">小程序名称：<input type="text" name="app_name" placeholder="小程序名称" ></p>
            <p class="mini_program_appid">小程序AppID：<input type="text" name="app_id" placeholder="小程序AppID"></p>
        </form>
    </div>
</div>

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
                $('input[name="act"]').val('del');
                $('input[name="app_id"]').val(_this.attr("app_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".stop").click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            var str = '<div style="text-align: center;">' + '停用后，小程序装修就不能使用此小程序内的链接了，确认要停用吗？' + '</div>';
            if(_this.attr("flag")==1){
                str = '<div style="text-align: center;">' + '启用后，如果是不可用状态，下个版本提交审核后就可以使用了，确定启用吗？' + '</div>';
            }
            layer.alert(str, {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="act"]').val('stop');
                $('input[name="app_id"]').val(_this.attr("app_id"));
                $('input[name="flag"]').val(_this.attr("flag"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });

    //新建微页面
    $('.add-child-btn').click(function(){
        //getPowerInfo('num_config','decorate_num','','装修页面数量');
        var data = getPowerInfo('num_config','decorate_num','','装修页面数量',1,0);
        //console.log(data);
        layui.use('layer', function(){
                var $ = layui.jquery, layer = layui.layer;
                layer.open({
                    type:1,
                    title: ['提示', 'text-align:center;padding: 0px;'],
                    offset: 'auto',
                    area: '410px',
                    content: $('#add_link'),
                    btn: ['确定', '取消'],
                    btnAlign: 'r' ,
                    shade: [0.3, '#000'],
                    yes:function(index, layero){
                       if($.trim($('.mini_program_name input').val()) === ''){
                            util.mobile_alert('请输入小程序名称')
                            return false;
                       }
                       if($.trim($('.mini_program_appid input').val()) === ''){
                            util.mobile_alert('请输入小程序AppID')
                            return false;
                       }
                       // $("#form2").submit();
                        var bizData = {
                            app_name : $.trim($('.mini_program_name input').val()),
                            app_id:$.trim($('.mini_program_appid input').val()),
                            act:'add'
                        };
                        util.ajax_json("/admin/manage/jump/list", function (response) {
                            if (response && response.error == 0) {
                                util.mobile_alert(response.message);
                                window.location.reload();
                            }else{
                                util.mobile_alert(response.message);
                            }
                        }, bizData);
                       layer.close(index);
                    },
                    btn2:function(index,layero){
                       layer.close(index);
                    }
                })
            })
    });
    //提交新版本申请
    $('.add-version-btn').click(function(){
        var bizData = {
            act:'add_version'
        };
        util.ajax_json("/admin/manage/jump/version", function (response) {
            if (response && response.error == 0) {
                util.mobile_alert(response.message);
                window.location.reload();
            }else{
                util.mobile_alert(response.message);
            }
        }, bizData);
    })
</script>
<#include "/admin/footer.ftl">
