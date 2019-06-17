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
        left: -190px;
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
        right: 75px;
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
    .tb-decorate-list>thead>tr>th{
        background: #eef1f6;
        font-weight: normal;
        height: 38px;
    }
</style>
<div style="min-width: 1090px;">
    {{--<div class="title">--!}
        {{--<div>--!}
            {{--<span><a href="/system/new/market?top_index=4">营销管理</a> / </span>--!}
            {{--<span style="color: #666;">砍价</span>--!}
        {{--</div>--!}
    {{--</div>--!}
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs clearfix">
                <li <#if (empty($nav))class="active"</#if>><a href="/system/bargain/list?shop_id=${request['shop_id']!}">全部砍价活动</a></li>
                <li <#if ($nav == 1)class="active"</#if>><a href="/system/bargain/list?nav=1&shop_id=${request['shop_id']!}">进行中</a></li>
                <li <#if ($nav == 2)class="active"</#if>><a href="/system/bargain/list?nav=2&shop_id=${request['shop_id']!}">未开始</a></li>
                <li <#if ($nav == 3)class="active"</#if>><a href="/system/bargain/list?nav=3&shop_id=${request['shop_id']!}">已过期</a></li>
                <li <#if ($nav == 4)class="active"</#if>><a href="/system/bargain/list?nav=4&shop_id=${request['shop_id']!}">已停用</a></li>
            </ul>
        </div>
{{--        {{print_r($list)!}--!}
        <div class="btn_adds">
            <a href="/system/bargain/add?nav=5" target="_blank" hidden>添加砍价活动</a>
        </div>
    </div>
    <div class="main-container" style="padding-top: 0px">
        <form action="" method="post" id="form1" name="form1">
            <input type="hidden" name="shop_id" value="${request['shop_id']!}">
            {{ csrf_field()!}
            <div class="return-pin-group-box"  style="padding-top: 10px">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                                <tr class="get-list-th">
                                    <th style='width: 200px;'> 砍价活动名称 </th>
                                    <th style='width: 180px;'> 有效期 </th>
                                    <th > 活动状态 </th>
                                    <th style='width: 270px;'> 商品名称 </th>
                                    <th > 商品库存 </th>
                                    <th > 砍价库存 </th>
                                    <th > 成功数量 </th>
                                    <th > 发起砍价人数 </th>
                                    <th hidden> 操作 </th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list ($list as $item)
                                    <tr>
                                        <td > ${item->bargain_name!} </td>
                                        <td > ${item->start_time.'至'.$item->end_time!} </td>
                                        <td > ${item->status_name!}</td>
                                        <td > ${item->goods_name!} </td>
                                        <td > ${item->goods_number!} </td>
                                        <td > ${item->stock > $item->goods_number ? $item->goods_number : $item->stock!} </td>
                                        <td > ${item->success_number!} </td>
                                        <td > ${item->bargain_user_number!} </td>
                                        <td class="tb-decorate-a" hidden>
                                            <input hidden name="stop_bargain">
                                            <input hidden name="delete_bargain">
                                            <#if  (in_array($item->show_status, [0, 1]))
                                                <a href="/system/bargain/add?id=${item->id!}&nav=5" target="_blank">
                                                    编辑
                                                </a><br/>
                                                <div class="erweima">
                                                    <a href="javascript:void(0);" class="hover_share" item_id="${item->id!}">
                                                        分享
                                                    </a>
                                                    <div class="share_span">
                                                        <img src="http://${image_domain!}/image/admin/img_home/img_sj.png" class="share_sj">
                                                        <span>扫一扫，分享给好友吧~</span>
                                                        <img src="" alt="" width="120px" class="code_imgs">
                                                        <a href="##" download=""  class="down_imgs">下载二维码</a>
                                                        <span class="share_link">
                                                            <input type="text" value="" id="fe_text" />
                                                            <button class="btn_copy" id="d_clip_button" data-clipboard-target="fe_text">复制</button>
                                                        </span>
                                                    </div>
                                                </div><br/>
                                                <a href="javascript:void(0);" class="stop_bargain" item_id="${item->id!}">
                                                    停用
                                                </a><br/>
                                            <#elseif>(in_array($item->show_status, [2,3]))
                                                <#if (in_array($item->show_status, [3]))
                                                <a href="javascript:void(0);" class="restart_bargain" item_id="${item->id!}" end_time="${item->end_time!}">
                                                    启用
                                                </a><br/>
                                                </#if>
                                                <a href="javascript:void(0);" class="delete_bargain" item_id="${item->id!}">
                                                    删除
                                                </a><br/>
                                            </#if>
                                            <#if  (!in_array($item->show_status, [1]))
                                                <a href="/system/activity/order/list?top_index=4&goods_type=3&act_id=${item->id!}">
                                                    查看砍价订单
                                                </a><br/>
                                                <a href="/system/user/source/detail?top_index=4&source=bargain&act_id=${item->id!}">
                                                    获取新用户明细
                                                </a><br/>
                                                <a href="/system/bargain/record/list?id=${item->id!}">
                                                    查看发起砍价用户
                                                </a><br/>
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
    $(".stop_bargain").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="stop_bargain"]').val(_this.attr("item_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".restart_bargain").click(function () {
        var _this = $(this);
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
                var param = {
                    restart_bargain : _this.attr("item_id"),
                };
                util.ajax_json('/system/bargain/list', function (res) {
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
    })
    $(".delete_bargain").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="delete_bargain"]').val(_this.attr("item_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })

    $(".hover_share").hover(function(){
        var bargain_id = $(this).attr("item_id");
        var _this = $(this);
        util.ajax_json('/system/bargain/getqrcode', function(response){
            if(response && response.error==0){
                _this.parent().find('.code_imgs').attr("src", response.content.qrcode_img);
                _this.parent().find('.down_imgs').attr("href", response.content.qrcode_img);
                _this.parent().find("#fe_text").val(response.content.type_url);
                $('.share_span').hide();
                _this.parent().find('.share_span').show();
            }else{
                util.mobile_alert(response.message);
            }
        },{bargain_id:bargain_id});
    },function(){
        $(this).parent().find('.share_span').hide();
    });

    // $(".hover_share").hover(function(){
    //     var pin_group_id = $(this).attr("item");
    //     var that = $(this);
    //     util.ajax_json('/system/pin/group/getqrcode', function(response){
    //         if(response && response.error==0){
    //             that.parent().find('.code_imgs').attr("src", response.content.qrcode_img);
    //             that.parent().find('.down_imgs').attr("href", response.content.qrcode_img);
    //             that.parent().find("#fe_text").val(response.content.type_url);
    //             that.parent().find('.share_span').show();
    //         }else{
    //             util.mobile_alert(response.message);
    //         }
    //     },{pin_group_id:pin_group_id});
    // },function(){
    //     $(this).parent().find('.share_span').hide();
    // });
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
    var left =  $('.left-menu-content .item-menu:nth-child(2)');
    left.find("img").attr("src","/image/system/icon_left/bargain_h.png");
    left.find("a").css("background","#2E3144");
    left.find("span").css({"color":"white","opacity":"1"});

    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/system/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")
