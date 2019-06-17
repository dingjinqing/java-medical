<#include ("system.header")
<link rel="stylesheet" href="/css/system/goods_edit.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/order_all.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/shop_decorate.css?v=1.0.0" type="text/css"/>
<style type="text/css">
    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
        color: #5a8bff;
    }

    .tb-decorate-a .share_span {
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 24px;
        left: -130px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }

    .tb-decorate-a .share_span .share_sj {
        position: absolute;
        right: 135px;
        top: -7px;
    }

    .tb-decorate-a .share_span span {
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }

    .tb-decorate-a .share_span .code_imgs {
        display: block;
        margin: 0 auto;
    }

    .tb-decorate-a .share_span a {
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
    }

    .share_link {
        padding-top: 15px;
        width: 100%;
    }

    .share_link input {
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }

    .share_link button {
        float: right;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
    }

    .add-child-btn:hover {
        background-color: #447af9;
    }
    .tb-decorate-list{
        width: 95%;
    }
    .tb-decorate-list>tbody>tr>td{
        padding:15px 0;
    }
    .tb-decorate-list>thead>tr>th{
        border:none;
        background: #f5f5f5;
        padding:15px 0;
    }
    .tb_paging{
        float: none;
        width: 95%;
        margin-top: 20px;
    }
    li.clearfix > div {
        float: left;
        margin-right: 25px;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        margin-left: 15px;
    }
    input[type="text"], li.clearfix select{
        width: 150px;
        height: 30px;
        padding-left: 12px;
    }

</style>
<div style="min-width: 1090px;">
    <form name="formData" action="/system/form/decoration/list" id="form1" method="post" style="padding: 0">
        {{--<div class="title">--!}
            {{--<span><a href="/system/new/market?top_index=4">营销管理</a> / </span>--!}
            {{--<span style="color: #666;">表单统计</span>--!}
        {{--</div>--!}
        <div class="main-container">

            {{--筛选--!}
            <ul class="check_input">
                <li class="clearfix" style="padding-top: 15px">
                    <div>
                        <span>状态</span>
                        <select name="state" id="">
                            <option value="-1">全部</option>
                            <option <#if ($post_data['state'] == '0')selected </#if> value="0">未发布</option>
                            <option <#if ($post_data['state'] == '1')selected </#if> value="1">已发布</option>
                            <option <#if ($post_data['state'] == '2')selected </#if> value="2">已关闭</option>
                        </select>
                    </div>
                    <div>
                        <span>创建时间</span>
                        <input type="text" placeholder="" name="start_time" value="${post_data['start_time']!}"
                               onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"  autocomplete="off">
                        至
                        <input type="text" placeholder="" name="end_time" value="${post_data['end_time']!}"
                               onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})"  autocomplete="off">
                    </div>
                    <div>
                        <span>表单名称</span>
                        <input name="page_name" type="text" placeholder="请输入表单名称" value="${post_data['page_name']!}">
                        <button class="btn_searchinfo">筛选</button>
                    </div>
                </li>
            </ul>
            <div class="return-goods-box" style="padding-top: 0;margin-top: 20px">
                {{ csrf_field()!}
                <input type="hidden" name="shop_id" value="${request['shop_id']!}">

                <input type="hidden" name="del">
                <input type="hidden" name="close">
                <input type="hidden" name="publish">
                <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="15%">表单名称</th>
                                    <th width="15%">创建时间</th>
                                    <th width="15%">反馈数</th>
                                    <th width="15%">状态</th>
                                    <th width="20%">有效期</th>
                                    <th width="25%" hidden>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list ($data_list as $item)
                                    <tr>
                                        <td>
                                            <span>${item->page_name!}</span>
                                        </td>
                                        <td>${item->create_time!}</td>
                                        <td>${item->submit_num!}</td>
                                        <td>
                                            <#if ($item->state == 0)
                                                未发布
                                            <#elseif>($item->state == 1)
                                                已发布
                                            <#elseif>($item->state == 2)
                                                已关闭
                                            </#if>
                                        </td>
                                        <td><#if ($item->is_forever_valid)
                                                永久有效 <#if ($item->status == 2)（进行中）</#if>
                                            <#else>
                                                ${item->start_time!} - ${item->end_time!}
                                                <#if ($item->status == 1)
                                                    （未开始）
                                                <#elseif>($item->status == 2)
                                                    （进行中）
                                                <#elseif>($item->status == 3)
                                                    （已结束）
                                                </#if>
                                            </#if>
                                        </td>
                                        <td class="tb-decorate-a" hidden>
                                            <#if ($item->state == 0)
                                                <a href="/system/form/decoration/page?id=${item->page_id!}&top_index=4" link="/system/form/decoration/page?id=${item->page_id!}&top_index=4" class="goods_edition" target="_blank" data-title="表单数量" data-name="form_num" edit="1">编辑</a>
                                                <a href="javascript:void(0)" class="publish" page_id="${item->page_id!}">发布</a>
                                            <#elseif>($item->state == 1 && ($item->status == 1 || $item->status == 2))
                                                <a href="/admin/market/form/page?id=${item->page_id!}&top_index=4" target="_blank">查看</a>
                                                <div class="erweima">
                                                    <a href="javascript:void(0)" class="hover_share" page_id="${item->page_id!}"> 分享</a>
                                                    <div class="share_span">
                                                        <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" class="share_sj">
                                                        <span>扫一扫，分享给好友吧~</span>
                                                        <img src="/image/system/qrcode.png" alt="" width="120px" class="code_imgs">
                                                        <a href="javascript:void(0);" download="" class="down_imgs">下载二维码</a>
                                                        <span class="share_link">
                                                        <input type="text" value="" id="fe_text"/>
                                                        <button class="btn_copy" id="d_clip_button"
                                                                data-clipboard-target="fe_text">复制</button>
                                                    </span>
                                                    </div>
                                                </div>
                                            <#else>
                                                <a href="/system/form/decoration/page?id=${item->page_id!}&top_index=4">查看</a>
                                            </#if>
                                            <a href="javascript:void(0)" class="del" page_id="${item->page_id!}">删除</a>
                                                <a href="/system/form/decoration/page?copy_id=${item->page_id!}&top_index=4" target="_blank">复制</a>
                                            <#if ($item->state == 1 || $item->state == 2)
                                                <a href="/system/form/feedback/list?page_id=${item->page_id!}&top_index=4">查看反馈</a>
                                            </#if>
                                            <br/>
                                            <#if ($item->state == 1)
                                                <a href="javascript:void(0)" class="close-link" page_id="${item->page_id!}">关闭</a>
                                            </#if>
                                                <div class="erweima">
                                                    <a href="javascript:void(0)" class="hover_pictoral" page_id="${item->page_id!}"> 下载海报</a>
                                                    <div class="share_span" style="left: -150px;">
                                                        <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" class="share_sj" style="right: 100px;">
                                                        <span>扫一扫，下载海报，分享给好友吧~</span>
                                                        <img src="/image/system/qrcode.png" alt="" width="120px"
                                                             class="code_imgs">
                                                        <a href="javascript:void(0);" download="" class="down_imgs">下载二维码</a>
                                                        <a href="javascript:void(0);" style="margin-left: 10px;" class="down_pictorial" download="">下载海报</a>
                                                        <span class="share_link">
                                                            <input type="text" value="" id="fe_text"/>
                                                            <button class="btn_copy" id="d_clip_button"
                                                                    data-clipboard-target="fe_text">复制</button>
                                                        </span>
                                                    </div>
                                                </div>

                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>

                            <table border="0" style="border:none;width: 95%;margin-left: 9px;" class="tb_paging">
                                <tr>
                                    <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                        <a href="#"
                                           onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                               size="5"
                                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                        <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("system/common.page.jump_page")!}</a>
                                    </td>

                                </tr>
                            </table>
                        </div>
                    </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">

    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }

    $(".del").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="del"]').val(_this.attr("page_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".publish").click(function () {
        var data = getPowerInfo('num_config','form_num','','表单数量',1,1);
        if(data.tip != 1){
            var _this = $(this);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要发布吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    $('input[name="publish"]').val(_this.attr("page_id"));
                    $("#form1").submit();
                    layer.close(index);
                });
            });
        }
    });
    $(".close-link").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要关闭吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="close"]').val(_this.attr("page_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });

    $(".hover_share").hover(function () {
        var page_id = $(this).attr("page_id");
        var that = $(this);
        util.ajax_json('/system/form/page/qr/code', function (d) {
            if (d && d.error == 0) {
                that.next().find('.code_imgs').attr("src", d.content.qrcode_img);
                that.next().find('.down_imgs').attr("href", d.content.qrcode_img);
                that.next().find("#fe_text").val(d.content.type_url);
                $('.share_span').hide();
                that.next().show();
            } else {
                util.mobile_alert(d.message);
            }
        }, {page_id: page_id});
    }, function () {
        $(this).parent().find('.share_span').hide();
    });
    $(".hover_pictoral").hover(function () {
        var page_id = $(this).attr("page_id");
        var that = $(this);
        util.ajax_json('/system/form/getqrcode', function (d) {
            if (d && d.error == 0) {
                that.next().find('.code_imgs').attr("src", d.content.qrcode_img);
                that.next().find('.down_imgs').attr("href", d.content.qrcode_img);
                that.next().find('.down_pictorial').attr('href', d.content.type_url);
                that.next().find("#fe_text").val(d.content.full_type_url);
                $('.share_span').hide();
                that.next().show();
            } else {
                util.mobile_alert(d.message);
            }
        }, {page_id: page_id});
    }, function () {
        $(this).parent().find('.share_span').hide();
    });
    $('.share_span').hover(function () {
        $('.share_span').hide();
        $(this).show();
    }, function () {
        $(this).hide();
    });
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })

    $(".btn_searchinfo").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    })
</script>

<#include ("system.footer")
