<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/shop_decorate.css?v=1.0.0" type="text/css" />
<style type="text/css">
    .notWidth{
        width: 18px !important;
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
        margin-right: 35px;
        margin-top: 10px;
        padding-left: 15px;
    }
    .cat_select{
        width: 228px;
        border: 1px solid #ccc;
        height: 30px;
        padding-left: 12px;
        border-radius: 3px;
    }
    .checkbox_prev {
        top: 1px;
        display: none;
    }
    input[type='checkbox'].ipt_checkbox {
        margin: 0px 0px 0 0;
        position: relative;
        top: 2px;
    }
    .member_list_footer {
        background: #fff;
        padding: 0 0 10px;
        height: 50px;
        line-height: 50px;
    }
    .tb_paging {
        float: right;
        width: 55% !important;
    }
    .member_list_footer .btn_opera {
        margin: 0 0 0 35px;
    }
    .add-child-ul li{
        display: inline-block;
    }
    .tb-decorate-a > a+a{
        margin-left: 10px;
    }
    .tb-decorate-a > div{
        margin-left: 10px;
    }
    .tips{
        position: absolute;
        background: url('/image/admin/decorate_tips.png');
        width: 38px;
        height: 24px;
        padding: 0;
        color: #fff;
        font-size: 12px;
        line-height: 19px;
        text-align: center;
        top: -28px;
        left: -12px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/shop_decorate.wx_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/shop_decorate.page_decorate")!}</span>
    </div>
    <div class="main-container" style="padding: 0px;">
        <form name="formData" action="/admin/manage/decorate/list"  id="form1" method="post" >
            <ul class="add-child-ul">
                <li>
                    <a href="#" class="add-child-btn" style="border-radius: 2px;margin-left: 20px;">新建微页面</a>
                    <div class="system_info" style="display: inline-block;float: none;">
                        <p class="system_info_prompt">
                            <#if ($version['self']['num']<0)
                                当前版本为${version['self']['version_name']!}， <span>不限制</span>微页面个数
                            <#else>
                                当前版本为${version['self']['version_name']!}，还可创建 <span>${version['self']['num']-$version['self']['use'] >0 ? $version['self']['num']-$version['self']['use'] : 0!}</span>页微页面
                            </#if>
                            {{--当前版本为高级版，还可创建 <span>5</span>页微页面--!}
                            <img src="http://${image_domain!}/image/admin/system_icon.png" />
                        </p>
                        <img src="http://${image_domain!}/image/admin/system_shadow.png" class="system_shadow" />
                        <div class="system_info_content">
                            <div class="system_info_content_top">
                                <#if ($version['all'])
                                    <#list ($version['all'] as $k=>$ver)
                                        <#if ($ver['num']>0)
                                            <#if ($k!=0)，</#if>${ver['version_name']!}最多创建<span class="system_v1">${ver['num']!}</span>页微页面
                                        <#else>
                                            <#if ($k!=0)，</#if>${ver['version_name']!}<span class="system_v1">不限制</span>微页面个数
                                        </#if>
                                    </#list>
                                </#if>
                                {{--基础版最多创建<span class="system_v1">5</span>页微页面，高级版最多创建<span class="system_v2">20</span>页微页面，旗舰版不限制--!}
                            </div>
                            <div class="system_info_content_bottom">
                                <a href="/admin/version/notice?mod_name=装修页面数量" target="_blank">了解更多</a>
                            </div>
                        </div>
                    </div>
                </li>
                <li style="float: right;margin-right: 20px">
                     <span style="display: inline-block;">
                        <span style="padding-left: 30px">页面名称</span>
                        <span class="search-bl">
                            <input type="text" name="keywords" value="${request['keywords']!}" placeholder="请输入页面名称" class="primary">
                            <img src="http://mpimg.zx.cn/image/admin/search.png" alt="" id="search">
                        </span>
                         <span style="padding-left: 30px">页面分类</span>
                        <span class="search-bl" style="border: none">
                            <select name="cat_id" id="" class="cat_select">
                                <option value="0" selected>请选择页面分类</option>
                                <#list ($cat_list as $id=>$cat)
                                    <option value="${id!}" <#if ($request['cat_id'] && $request['cat_id'] == $id) selected </#if>>${cat!}</option>
                                </#list>
                            </select>
                        </span>
                        <button class="btn_searchinfo">查询</button>
                    </span>
                </li>
            </ul>

            <div class="return-goods-box" style="padding: 0px 20px;">

                {{ csrf_field()!}
                <input type="hidden" name="del">
                <input type="hidden" name="index">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list" style="width: 100%;margin-left: 0px">
                            <thead>
                            <tr>
                                <th></th>
                                <th width="28%">{{ trans("admin/shop_decorate.shop_decorate_list.page_name")!}</th>
                                <th width="20%">{{ trans("admin/shop_decorate.shop_decorate_list.create_time")!}</th>
                                <th width="15%">{{ trans("admin/shop_decorate.shop_decorate_list.is_index")!}</th>
                                <th width="15%">{{ trans("admin/shop_decorate.page_classification")!}</th>
                                <th width="20%">{{ trans("admin/shop_decorate.shop_decorate_list.operation")!}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list ($data_list as $item)
                                <tr>
                                    <td>
                                        <img src="http://mpimg.zx.cn/image/admin/square_no.png" alt="" class="checkbox_prev">
                                        <input type="checkbox" name="checkbox" class="ipt_checkbox" page_id="${item->page_id!}">
                                    </td>
                                    <td>
                                        <span>${item->page_name!}</span>
                                    </td>
                                    <td>${item->create_time!}</td>
                                    <td><#if ($item->page_type)是<#elseif>(!$item->page_type)<a href="javascript:void(0)" class="index" page_id="${item->page_id!}">{{ trans("admin/shop_decorate.shop_decorate_list.set_index")!}</a></#if></td>
                                    <td>${cat_list[$item->cat_id]!} <a href="javascript:void(0)" class="btn_cat" page_id="${item->page_id!}">设置</a></td>
                                    <td class="tb-decorate-a">
                                        <a href="/admin/manage/decorate/page?id=${item->page_id!}" target="_blank" link="/admin/manage/decorate/page?id=${item->page_id!}" class="goods_edition show_tip" data-title="装修页面数量" data-name="decorate_num" edit="1" data-tips="编辑"><img src="/image/admin/decorate_editor.png" alt=""></a>
                                        <#if (!$item->page_type)<a href="javascript:void(0)" class="del show_tip" data-tips="删除" page_id="${item->page_id!}"><img src="/image/admin/decorate_delete.png" alt=""></a></#if>
                                        <a href="/admin/manage/decorate/page?copy_id=${item->page_id!}" data-tips="复制" class="show_tip"><img src="/image/admin/decorate_copy.png" alt=""></a>
                                        <a href="##" class="hover_share show_tip notWidth"   identity_id="${item->page_id!}" type="7" data-tips="分享"><img src="/image/admin/decorate_share.png" alt=""></a>
                                    </td>
                                </tr>
                               </#list>
                            </tbody>
                        </table>
                    </div>
                    <#if ($data_list->count != 0)
                        <div class="member_list_footer">
                    <span>
                        <span style="position: relative; margin-left: 12px;">
                            <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" />
                            <input type="checkbox" id="ipt_all" />
                            <label for="ipt_all">{{ trans("admin/user_list.check_all")!}</label>
                        </span>
                        <a href="##" name="batch_delete" class="btn_opera btn_cat" style="color: #5a8bff;">批量设置分类</a>
{{--                        <a href="##" name="batch_tag" class="btn_opera batch_tag">{{ trans("admin/user_list.add_label")!}</a>--!}
                    </span>
                            <table width="100%" border="0" class="tb_paging">
                                <tr>
                                    <td style="text-align:right;">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                        <a href="#" style="background: rgb(250, 250, 250);" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                        <a href="#" style="background: rgb(250, 250, 250);"
                                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                        <a href="#" style="background: rgb(250, 250, 250);"
                                           onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                        <a href="#" style="background: rgb(250, 250, 250);"
                                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                               size="5"
                                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')" autocomplete="off"
                                        >{{ trans("admin/common.page.page")!}
                                        <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    <#else>
                        <div style="width: 100%;padding: 0px 0px 12px 0px;background: #fff;">
                            <div style="border: 1px solid #eee;">
                                <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                </div>
                                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                            </div>
                        </div>
                    </#if>
                </div>
            </div>
        </form>
    </div>
</div>

<div id="cat_select" style="display: none;padding: 20px 0px;padding-left: 36px;   border-bottom: 1px solid rgb(238, 238, 238)">
    <select name="cat_id" id="" class="cat_select">
        <option value=""  selected>请选择页面分类</option>
        <#list ($cat_list as $id=>$cat)
            <option value="${id!}" <#if ($request['cat_id'] && $request['cat_id'] == $id) selected </#if>>${cat!}</option>
        </#list>
    </select>
</div>
<#include ('admin.share_common')
<script type="text/javascript">
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(parseInt(page) > parseInt(last_page)){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

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
                $('input[name="del"]').val(_this.attr("page_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });

    });
    $(".index").click(function(){
        $('input[name="index"]').val($(this).attr("page_id"));
        $("#form1").submit();
    });
    $("#ipt_all").click(function () {
        var isChecked = $("#ipt_all").prop("checked");
        if($(this).is(':checked')) {
            $(this).prev().attr('src','/image/admin/square_yes.png');
            $(this).parents('#form1').find('.tb-decorate-list').find(".ipt_checkbox").each(function(){
                if(!$(this).is(':checked'))
                    $(this).click();
            })
        }else{
            $(this).prev().attr('src','/image/admin/square_no.png');
            $(this).parents('#form1').find('.tb-decorate-list').find(".ipt_checkbox").each(function(){
                if($(this).is(':checked'))
                    $(this).click();
            })
        }
    });

    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
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
    //设置页面分类
    $(".btn_cat").click(function () {
        var _this = $(this);
        var batch_id = [];
        if(!_this.attr("page_id")){
            $('.tb-decorate-list input[name="checkbox"]:checked').each(function () {
                batch_id.push($(this).attr('page_id'));
            });
            if (batch_id.length == 0) {
                util.mobile_alert('请选择页面');
                return false;
            }
        }else{
            batch_id.push($(this).attr('page_id'));
        }

        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open( {
                type: 1
                , title: ['设置页面分类', 'text-align:center;padding: 0px;']
                , area: '300px'
                , content:$('#cat_select')
                , closeBtn: 0
                , btn: ['确定', '取消']
                , yes: function (index, layero) { //保存按钮的回调
                    var param = {
                        'cat_id':$("#cat_select select[name='cat_id']").val(),
                        'page_id':batch_id
                    }
                    util.ajax_json("/admin/manage/decorate/list",function (res) {
                        if(res && res.error==0){
                            util.mobile_alert("设置成功");
                            location.reload();
                        }else{
                            util.mobile_alert("设置失败");
                        }
                    },param)
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            })
        });
    })
        $('.tb-decorate-a').on("mouseenter",'a.show_tip img',function(){
            $(this).parent().css({
                "display":"inline-block",
                "position" : "relative"
            })
            $('<div class="tips"></div>').html($(this).parent().data('tips')).appendTo(this.parentElement);
        }).on('mouseleave','a.show_tip img',function(){
            $(this).parent().css({
                "display":"inline",
                "position":"static"
            })
            $(this).parent().find('.tips').remove();
        });
</script>
<#include "/admin/footer.ftl">
