<#include ("system.header")
<link rel="stylesheet" href="/css/system/sec_kill.css?v=1.0.0" type="text/css" />
<style>
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
        top: 20px;
        left: -175px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }
    .tb-decorate-a .a{
        width: 28px;
        height:30px;
        display: inline-block;
    }
    .tb-decorate-a .share_span .share_sj{
        position: absolute;
        right: 90px;
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
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .return-sec-kill-box{
        padding-top: 10px;
    }
    .tb_paging tr td a {
        display: inline-block;
        border: 1px solid #dedede;
        padding: 0px 8px;
        height: 30px;
        line-height: 30px;
        margin-left: 5px;
    }
    .tb_paging input {
        height: 30px;
        border: 1px solid #dedede;
        text-align: center;
        padding-left: 0;
    }
</style>
<div style="min-width: 1090px;">
    {{--<div class="title">--!}
        {{--<div>--!}
            {{--<span><a href="/system/new/market?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>--!}
            {{--<span style="color: #666;">秒杀</span>--!}
        {{--</div>--!}
    {{--</div>--!}
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if ($request['nav'] == 0) class="active" </#if>>
                    <a href="/system/sec/kill/list?nav=0&shop_id=${request['shop_id']!}">全部秒杀活动</a>
                </li>
                <li <#if ($request['nav'] == 1) class="active" </#if>>
                    <a href="/system/sec/kill/list?nav=1&shop_id=${request['shop_id']!}">进行中</a>
                </li>
                <li <#if ($request['nav'] == 2) class="active" </#if>>
                    <a href="/system/sec/kill/list?nav=2&shop_id=${request['shop_id']!}">未开始</a>
                </li>
                <li <#if ($request['nav'] == 3) class="active" </#if>>
                    <a href="/system/sec/kill/list?nav=3&shop_id=${request['shop_id']!}">已过期</a>
                </li>
                <li <#if ($request['nav'] == 4) class="active" </#if>>
                    <a href="/system/sec/kill/list?nav=4&shop_id=${request['shop_id']!}">已停用</a>
                </li>
            </ul>
        </div>
        <div class="btn_adds">
            <a href="/system/sec/kill/add" target="_blank" hidden>添加秒杀活动</a>
        </div>
    </div>

    <div class="main-container" style="padding-top: 0px">
        <form action="/system/sec/kill/list?nav=${request['nav']!}" method="post" id="form1" name="form1">
            {{ csrf_field()!}
            <input type="hidden" name="shop_id" value="${request['shop_id']!}">

            {{--<div class="box panel panel-body list-center-fee">
                <div class="form-inline shop-template-container">
                    <button type="button" class="coupon-search">搜索</button>
                </div>
            </div>--!}
            <div class="return-sec-kill-box">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr class="get-list-th">
                                <th width="12%"> 活动名称 </th>
                                <th width="12%"> 商品名称 </th>
                                <th width="12%"> 有效期 </th>
                                <th width="10%"> 活动状态 </th>
                                <th width="6%"> 商品交易数量 </th>
                                <th width="11%"> 单用户最大购买数量 </th>
                                <th width="13%" hidden> 操作 </th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list ($list as $item)
                                <tr>
                                    <td>${item->name!}</td>
                                    <td> ${item->goods_name!}</td>
                                    <td>${item->start_time!} <br/>至<br/> ${item->end_time!}</td>
                                    <td>
                                        <#if ($item->sec_kill_status == 1)
                                            进行中
                                        <#elseif>($item->sec_kill_status == 2)
                                            未开始
                                        <#elseif>($item->sec_kill_status == 3)
                                            已过期
                                        <#elseif>($item->sec_kill_status == 4)
                                            已停用
                                        </#if>
                                    </td>
                                    <td>${item->sale_num!}</td>
                                    <td>${item->limit_amount!}</td>
                                    <td class="tb-decorate-a" hidden>
                                        <#if  (in_array($item->sec_kill_status, [1, 2]))
                                            <a href="/system/sec/kill/add?id=${item->sk_id!}" target="_blank">
                                                编辑
                                            </a><br/>
                                            <div class="erweima">
                                                <a href="##" class="hover_share" item="${item->sk_id!}">分享</a>
                                                {{--分享弹出二维码--!}
                                                <div class="share_span">
                                                    <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" class="share_sj">
                                                    <span>扫一扫，分享给好友吧~</span>
                                                    <img src="http://${image_domain!}/image/system/qrcode.png" alt="" width="120px" class="code_imgs">
                                                    <a href="##" download=""  class="down_imgs">下载二维码</a>
                                                    <span class="share_link">
                                                    <input type="text" value="" id="fe_text" />
                                                    <button class="btn_copy" id="d_clip_button" data-clipboard-target="fe_text">复制</button>
                                                </span>
                                                </div>
                                            </div>
                                            <br/>
                                        </#if>
                                        {{-- <#if  (in_array($item->pin_group_status, [3, 4]))
                                            <a href="javascript:void(0);" class="abort" action="del" item="${item->id!}">
                                                删除
                                            </a><br/>
                                        </#if>
                                        --!}
                                        <#if  (in_array($item->sec_kill_status, [1, 2, 4]))
                                            <a href="javascript:void(0);" class="abort" action="or_enable" status="<#if  ($item->status == 0) 1 <#else> 0 </#if> " item="${item->sk_id!}">
                                                <#if  (in_array($item->sec_kill_status, [1, 2])) 停用 <#else> 启用 </#if>
                                            </a><br/>
                                        </#if>
                                        <#if  ($item->sec_kill_status != 2)
                                            <a href="/system/sec/kill/detail?id=${item->sk_id!}">
                                                查看秒杀用户
                                            </a><br/>
                                        </#if>
                                        <#if ($item->sec_kill_status == 4 || $item->sec_kill_status == 3)
                                            <a href="#" class="abort" action="delete" pin_id="${item->sk_id!}">删除</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="clearfix" style="margin-top: 20px;">
                    <table width="100%" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("admin/common.page.page_info",
                            [
                                'perPage' => $list->perPage(),
                                'currentPage' => $list->currentPage(),
                                'count' => $list->count,
                                'total' => $list->total()
                            ]
                        )!}
                                <a href="#" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${list->currentPage() + 1!});">{{ trans("system/common.page.next_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${list->currentPage()!}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    function gopage(page) {
        var last_page = '${list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    var page_home = '${list->currentPage()!}'; //当前页码数
    var page_all = '${list->count!}'            //总页码数
    $('.return-sec-kill-box .tb-decorate-a .abort').click(function () {
        var action = $(this).attr('action');
        if (action == 'or_enable') {
            var param = {
                id: $(this).attr('item'),
                action: action,
                status: $(this).attr('status')
            };
            if ($(this).attr('status') == 0) {
                var lay_message = '确认要停用吗？';
            }else{
                var lay_message = '确认要启用吗？';
            }

            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + lay_message + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    util.ajax_json('/admin/market/seckill/list', function (response) {
                        if (response.error == 0) {
                            $("#form1").submit();
                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, param)
                    layer.close(index);
                });
            });
            return;

        } else if(action == 'del') {
            var param = {
                id: $(this).attr('item'),
                action: action
            };
        }
        util.ajax_json('/system/sec/kill/list', function (response) {
            if (response.error == 0) {
                $("#form1").submit();
            } else {
                util.mobile_alert(response.message);
            }
        }, param)
        if(action == 'delete') {
            var param = {
                id: $(this).attr('pin_id'),
                action: action,
            };
            var _this = $(this);
            console.log(param);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    util.ajax_json('/system/sec/kill/list', function (response) {
                        if (response.error == 0) {
                            $("#form1").submit();
                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, param)
                    layer.close(index);
                });
            });
        }
    })
    $(".hover_share").hover(function(){
        var sk_id = $(this).attr("item");
        var that = $(this);
        util.ajax_json('/system/sec/kill/getqrcode', function(response){
            if(response && response.error==0){
                that.parent().find('.code_imgs').attr("src", response.content.qrcode_img);
                that.parent().find('.down_imgs').attr("href", response.content.qrcode_img);
                that.parent().find("#fe_text").val(response.content.type_url);
                that.parent().find('.share_span').show();
            }else{
                util.mobile_alert(response.message);
            }
        },{sk_id:sk_id});
    },function(){
        $(this).parent().find('.share_span').hide();
    });
    $('.share_span').hover(function () {
        $(this).show();
    },function () {
        $(this).hide();
    })
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
</script>
<script src="/js/system/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")

