<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/shop_decorate.css?v=1.0.0" type="text/css"/>
<style type="text/css">
    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
        color: #5a8bff;
    }
    .downPoster .share_span1 {
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

    .downPoster .share_span1 .share_sj {
        position: absolute;
        right: 135px;
        top: -7px;
    }

    .downPoster .share_span1 span {
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }

    .downPoster .share_span1.code_imgs {
        display: block;
        margin: 0 auto;
    }

    .downPoster .share_span1 a {
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
    }

    .downPoster .share_link {
        padding-top: 15px;
        width: 100%;
    }

    .downPoster .share_link input {
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }

    .downPoster .share_link button {
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

</style>
<div style="min-width: 1090px;">
    <form name="formData" action="/admin/market/form/list" id="form1" method="post" style="padding: 0">
        <div class="title">
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">表单统计</span>
        </div>
        <div class="main-container">
            <ul class="add-child-ul">
                <li>
                    <a href="/admin/market/form/page?top_index=4" link="/admin/market/form/page?top_index=4" class="add-child-btn goods_edition" data-title="表单数量" data-name="form_num" edit="0">添加表单</a>
                    <div class="system_info" style="display: inline-block;float: none;">
                        <p class="system_info_prompt">
                            <#if ($version['self']['num']<0)
                                当前版本为${version['self']['version_name']!}，<span>不限制</span>表单个数
                            <#else>
                                当前版本为${version['self']['version_name']!}，还可创建 <span>${version['self']['num']-$version['self']['use'] >0 ? $version['self']['num']-$version['self']['use'] : 0!}</span>个表单
                            </#if>
                            <img src="http://${image_domain!}/image/admin/system_icon.png" />
                        </p>
                        <img src="http://${image_domain!}/image/admin/system_shadow.png" class="system_shadow" />
                        <div class="system_info_content">
                            <div class="system_info_content_top">
                                <#if ($version['all'])
                                    <#list ($version['all'] as $k=>$ver)
                                        <#if ($ver['num']<0)
                                            <#if ($k!=0)，</#if>${ver['version_name']!}<span class="system_v1">不限制</span>表单个数
                                        <#else>
                                            <#if ($k!=0)，</#if>${ver['version_name']!}最多创建<span class="system_v1">${ver['num']!}</span>个表单
                                        </#if>
                                    </#list>
                                </#if>
                            </div>
                            <div class="system_info_content_bottom">
                                <a href="/admin/version/notice?mod_name=表单数量" target="_blank">了解更多</a>
                            </div>
                        </div>
                </li>
            </ul>
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
            <div class="return-goods-box" style="padding-top: 0">
                {{ csrf_field()!}
                <input type="hidden" name="del">
                <input type="hidden" name="close">
                <input type="hidden" name="publish">
                <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="15%">表单名称</th>
                                    <th width="18%">创建时间</th>
                                    <th width="15%">反馈数</th>
                                    <th width="15%">状态</th>
                                    <th width="20%">有效期</th>
                                    <th width="22%">操作</th>
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
                                        <td class="tb-decorate-a">
                                            <#if ($item->state == 0)
                                                <a href="/admin/market/form/page?id=${item->page_id!}&top_index=4" link="/admin/market/form/page?id=${item->page_id!}&top_index=4" class="goods_edition" target="_blank" data-title="表单数量" data-name="form_num" edit="1">编辑</a>
                                                <a href="javascript:void(0)" class="publish" page_id="${item->page_id!}">发布</a>
                                            <#elseif>($item->state == 1 && ($item->status == 1 || $item->status == 2))
                                                <a href="/admin/market/form/page?id=${item->page_id!}&top_index=4" target="_blank">查看</a>
                                                <div class="erweima">
                                                    <a href="javascript:void(0)" class="hover_share" identity_id="${item->page_id!}" type="9"> 分享</a>
                                                </div>
                                            <#else>
                                                <a href="/admin/market/form/page?id=${item->page_id!}&top_index=4">查看</a>
                                            </#if>
                                            <a href="javascript:void(0)" class="del" page_id="${item->page_id!}">删除</a>
                                                <a href="/admin/market/form/page?copy_id=${item->page_id!}&top_index=4" target="_blank">复制</a>
                                            <#if ($item->state == 1 || $item->state == 2)
                                                <div>
                                                    <a style="color: #5A8BFF;" href="/admin/market/form/feedback/list?page_id=${item->page_id!}&top_index=4">查看反馈</a>
                                                    <a style="color: #5A8BFF;" href="/admin/market/form/feedback/statistics?page_id=${item->page_id!}&top_index=4">反馈统计</a>
                                                </div>
                                            </#if>
                                            {{--<br/>--!}
                                            <#if ($item->state == 1)
                                                <a href="javascript:void(0)" class="close-link" page_id="${item->page_id!}">关闭</a>
                                            </#if>
                                                <div class="erweima downPoster" style="display: block">
                                                    <a href="javascript:void(0)" class="hover_pictoral" page_id="${item->page_id!}"> 下载海报</a>
                                                    <div class="share_span1">
                                                        <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" class="share_sj">
                                                        <span>扫一扫，下载海报，分享给好友吧~</span>
                                                        <img src="/image/system/qrcode.png" alt="" width="120px"
                                                             class="code_imgs">
                                                        <a href="javascript:void(0);" download="" class="down_imgs">下载二维码</a>
                                                        <a href="javascript:void(0);" class="down_pictorial" download="">下载海报</a>
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
                            <#if (!$data_list->count())
                                <table style="width: 95%;border: 1px solid #eee;position: relative;margin-top: 12px;margin-left: 10px">
                                    <tr>
                                        <td style="width: 30px;height: 33px; margin: 18px auto auto auto;display: flex;justify-content: center;align-items: center;" >
                                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</td>
                                    </tr>
                                </table>
                            <#else>
                            <table border="0" style="border:none;width: 95%;margin-left: 9px;" class="tb_paging">
                                <tr>
                                    <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                        <a href="#"
                                           onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                        <a href="#"
                                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                               size="5"
                                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                        <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                                    </td>

                                </tr>
                            </table>
                            </#if>
                        </div>
                    </div>
            </div>
        </div>
    </form>
</div>
<#include ('admin.share_common')
<script type="text/javascript">

    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(parseInt(page )> parseInt(last_page)) {
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

    $(".hover_pictoral").hover(function () {
        var page_id = $(this).attr("page_id");
        var that = $(this);
        util.ajax_json('/admin/market/form/getqrcode', function (d) {
            if (d && d.error == 0) {
                that.next().find('.code_imgs').attr("src", d.content.qrcode_img);
                that.next().find('.down_imgs').attr("href", d.content.qrcode_img);
                that.next().find('.down_pictorial').attr('href', d.content.type_url);
                that.next().find("#fe_text").val(d.content.full_type_url);
                $('.share_span1').hide();
                that.next().show();
            } else {
                util.mobile_alert(d.message);
            }
        }, {page_id: page_id});
    }, function () {
        $(this).parent().find('.share_span').hide();
    });
    $('.share_span1').hover(function () {
        $('.share_span1').hide();
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

<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','form_decoration','sub_4','表单统计',0);
</script>
