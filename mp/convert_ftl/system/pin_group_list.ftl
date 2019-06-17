<#include ("system.header")
<link rel="stylesheet" href="/css/system/pin_group.css?v=1.0.0" type="text/css" />
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
        top: 22px;
        left: -165px;
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
        right: 100px;
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
    .pagination{
        display: none;
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
    .paging_footer{
        margin-top: 20px;
    }
</style>
<div style="min-width: 1090px;">
    {{--<div class="title">--!}
        {{--<div>--!}
            {{--<span><a href="/admin/market/view?top_index=4" >营销管理</a> / </span>--!}
            {{--<span style="color: #666;">多人拼团</span>--!}
        {{--</div>--!}
    {{--</div>--!}
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if ($request['nav'] == 0) class="active" </#if>>
                    <a href="/system/pin/group/list?nav=0&shop_id=${request['shop_id']!}">全部拼团活动</a>
                </li>
                <li <#if ($request['nav'] == 1) class="active" </#if>>
                    <a href="/system/pin/group/list?nav=1&shop_id=${request['shop_id']!}">进行中</a>
                </li>
                <li <#if ($request['nav'] == 2) class="active" </#if>>
                    <a href="/system/pin/group/list?nav=2&shop_id=${request['shop_id']!}">未开始</a>
                </li>
                <li <#if ($request['nav'] == 3) class="active" </#if>>
                    <a href="/system/pin/group/list?nav=3&shop_id=${request['shop_id']!}">已过期</a>
                </li>
                <li <#if ($request['nav'] == 4) class="active" </#if>>
                    <a href="/system/pin/group/list?nav=4&shop_id=${request['shop_id']!}">已停用</a>
                </li>
                {{--<li <#if ($request['nav'] == 5) class="active" </#if>>--!}
                    {{--<a href="/system/pin/group/list?nav=5">新建拼团</a>--!}
                {{--</li>--!}
            </ul>
        </div>
        <div class="btn_adds">
            <a href="/system/pin/group/add?nav=5" target="_blank" hidden>添加拼团活动</a>
        </div>
    </div>
    <div class="main-container" style="padding-top: 0;">
        <form action="/system/pin/group/list?nav=${request['nav']!}" method="post" id="form1" name="form1">
            {{ csrf_field()!}
            <input type="hidden" name="shop_id" value="${request['shop_id']!}">
        {{--<div class="box panel panel-body list-center-fee">
            <div class="form-inline shop-template-container">
                <button type="button" class="coupon-search">搜索</button>
            </div>
        </div>--!}
        <div class="return-pin-group-box" style="padding-top: 10px;">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                                <tr class="get-list-th">
                                    <th width="12%"> 活动名称 </th>
                                    <th width="12%"> 商品名称 </th>
                                    <th width="12%"> 有效期 </th>
                                    <th width="10%"> 活动状态 </th>
                                    <th width="6%"> 成团人数 </th>
                                    <th width="11%"> 成团订单数 </th>
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
                                        <#if ($item->pin_group_status == 1)
                                            进行中
                                        <#elseif>($item->pin_group_status == 2)
                                            未开始
                                        <#elseif>($item->pin_group_status == 3)
                                            已过期
                                        <#elseif>($item->pin_group_status == 4)
                                            已停用
                                        </#if>
                                    </td>
                                    <td>${item->limit_amount!}</td>
                                    <td>${pinGroupSum[$item->id] ?? 0!}</td>
                                    <td class="tb-decorate-a" hidden>
                                        <#if  (in_array($item->pin_group_status, [1, 2]))
                                        <a href="/system/pin/group/add?id=${item->id!}&nav=5" target="_blank">
                                            编辑
                                        </a><br/>
                                        <div class="erweima">
                                            <a href="##" class="hover_share" item="${item->id!}">分享</a>
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
                                        <#if  (in_array($item->pin_group_status, [1, 2, 4]))
                                            <a href="javascript:void(0);" class="abort" action="or_enable" status="<#if  ($item->status == 1) 0 <#else> 1 </#if> " item="${item->id!}" end_time="${item->end_time!}">
                                                <#if  (in_array($item->status, [1, 2])) 停用 <#else> 启用 </#if>
                                            </a><br/>
                                        </#if>
                                        <#if  ($item->pin_group_status != 2)
                                        <a href="/system/pin/group/detail?id=${item->id!}">
                                            参团明细
                                        </a><br/>
                                        <a href="/system/activity/order/list?top_index=4&goods_type=1&act_id=${item->id!}">
                                            查看拼团订单
                                        </a><br/>
                                        <a href="/system/user/source/detail?top_index=4&source=groupbuy&act_id=${item->id!}">
                                            获取新用户明细
                                        </a><br/>
                                        <a href="/system/order/list?pin_group_fail=1&pin_group_id=${item->id!}">
                                            退款失败订单
                                        </a><br>
                                        </#if>

                                        <#if ($item->pin_group_status == 4 || $item->pin_group_status == 3)
                                            <a href="#" class="abort" action="delete" pin_id="${item->id!}">删除</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            <div class="paging_footer">

               <#include ("system.jump_page_system")
            </div>
        </div>
        </form>
    </div>
</div>
<script>
    $('.return-pin-group-box .tb-decorate-a .abort').click(function () {
        var action = $(this).attr('action');
        if (action == 'or_enable') {
            var param = {
                id: $(this).attr('item'),
                action: action,
                status: $(this).attr('status')
            };
            var _this = $(this);
            if ($(this).attr('status') == 0) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.alert('<div style="text-align: center;">' + '确认要停用吗？' + '</div>', {
                        title: ['提示', 'text-align:center;padding: 0px;']
                        , area: '260px'
                        , closeBtn: 0
                        , btn: ['确定', '取消']
                    }, function (index) {
                        util.ajax_json('/system/pin/group/list', function (response) {
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
            }else if(_this.attr('status') == 1){
                var myDate = new Date();
                //获取当前年
                var year=myDate.getFullYear();
                //获取当前月
                var month=myDate.getMonth()+1;
                //获取当前日
                var date=myDate.getDate();
                var h=myDate.getHours();       //获取当前小时数(0-23)
                var m=myDate.getMinutes();     //获取当前分钟数(0-59)
                var s=myDate.getSeconds();
                var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
                if(_this.attr("end_time")<= now){
                    util.mobile_alert("该活动已过期，不可启用");
                    return false;
                }
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.alert('<div style="text-align: center;">' + '确认要启用吗？' + '</div>', {
                        title: ['提示', 'text-align:center;padding: 0px;']
                        , area: '260px'
                        , closeBtn: 0
                        , btn: ['确定', '取消']
                    }, function (index) {
                        util.ajax_json('/system/pin/group/list', function (res) {
                            if (res.error == 0) {
                                util.mobile_alert("启用成功");
                                location.reload();
                            } else {
                                util.mobile_alert(res.message);
                            }
                        }, param)
                        layer.close(index);
                    });
                });
                return;
            }
        } else if(action == 'del') {
            var param = {
                id: $(this).attr('item'),
                action: action
            };
        }
        util.ajax_json('/system/pin/group/list', function (response) {
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
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    util.ajax_json('/system/pin/group/list', function (response) {
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
        var pin_group_id = $(this).attr("item");
        var that = $(this);
        util.ajax_json('/system/pin/group/getqrcode', function(response){
            if(response && response.error==0){
                that.parent().find('.code_imgs').attr("src", response.content.qrcode_img);
                that.parent().find('.down_imgs').attr("href", response.content.qrcode_img);
                that.parent().find("#fe_text").val(response.content.type_url);
                $('.share_span').hide();
                that.parent().find('.share_span').show();
            }else{
                util.mobile_alert(response.message);
            }
        },{pin_group_id:pin_group_id});
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
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/system/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")

