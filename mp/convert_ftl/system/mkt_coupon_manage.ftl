<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .tb-decorate-a{
        position: relative;
    }
    .tb-decorate-a .share_span{
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 48px;
        left:-90px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }
    .tb-decorate-a .hover_share{
        width: 28px;
        height:30px;
        display: inline-block;
    }
    .tb-decorate-a .share_span .share_sj{
        position: absolute;
        right: 120px;
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
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .add-child-btn:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
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
    .return-goods-box .paging_footer{
        margin-top: 20px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>{{ trans("admin/market_manage.market_manage_title")!} / </span>
        <span style="color: #666;">{{ trans("admin/market_manage.coupon_manage_title")!}</span>
    </div>
    <div class="main-container">
        <div class="coupon_type">
            <ul>
                <li class="normal_type actives">
                    <a href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=1 ">普通优惠券</a>
                </li>
                <li class="fenlie_type">
                    <a href="/system/coupon/split?shop_id=${data->shop_id!}&nav=1">分裂优惠券</a>
                </li>
                <li class="give_to_sb">
                    <a href="/system/coupon/grant/list?shop_id=${data->shop_id!}">定向发券</a>
                </li>
            </ul>
        </div>


        {{--普通优惠券部分--!}
        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li <#if ($nav_type==0)class="active"</#if>>
                        <a href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=0" >所有优惠券</a>
                    </li>
                    <li <#if ($nav_type==1)class="active"</#if>>
                        <a href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=1">进行中</a>
                    </li>
                    <li <#if ($nav_type==2)class="active"</#if>>
                        <a href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=2" >未生效</a>
                    </li>
                    <li <#if ($nav_type==3)class="active"</#if>>
                        <a href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=3">已过期</a>
                    </li>
                    <li <#if ($nav_type==4)class="active"</#if>>
                        <a href="/system/coupon/manage?shop_id=${data->shop_id!}&nav=4">已停用</a>
                    </li>
                </ul>
            </div>
            <script>
            </script>
            <ul class="add-child-ul">
                <li>
                    <a href="/system/coupon/add?shop_id=${data->shop_id!}" class="add-child-btn" target="_blank">{{ trans('admin/market_manage.coupon_manage.add_coupon')!}</a>
                </li>
            </ul>
            <div class="return-goods-box">
                <form action="/system/coupon/manage" method="post" id="form1">
                    {{csrf_field()!}
                    <input name="del" type="hidden">
                    <input name="delete" type="hidden">
                    <input name="nav" type="hidden" value="${nav_type!}">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr>
                                <th width="16%">{{ trans("admin/market_manage.coupon_manage.coupon_name")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.value")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.lower_fee")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.count")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.get_limit")!}</th>
                                <th width="17%">{{ trans("admin/market_manage.coupon_manage.date_limit")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.get_person")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.give_person")!}</th>
                                <th width="7%">{{ trans("admin/market_manage.coupon_manage.used")!}</th>
                                <th width="18%">{{ trans("admin/market_manage.coupon_manage.operation")!}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if ($coupon_list)
                                <#list ($coupon_list as $item)
                                    <tr>
                                        <td>
                                            <span>${item->act_name!}</span>
                                        </td>
                                        <td><#if ($item->act_code=='voucher')${item->denomination!}元 <#else>${item->denomination!}折 </#if></td>
                                        <td><#if ($item->use_consume_restrict ==0) 无限制<#else> <#if  ($item->least_consume == 0)无限制 <#else> ${item->least_consume!}</#if> </#if></td>
                                        <td>${item->surplus!}</td>
                                        <td><#if  ($item->receive_per_person == 0) 不限制 <#else> 限领${item->receive_per_person!}张 </#if> </td>
                                        <td><#if ($item->validity >0)领取开始${item->validity!}天内有效 <#else> ${item->start_time!} 至 ${item->end_time!}</#if></td>
                                        <td>${item->receive_person!}/${item->receive_amount!}</td>
                                        <td>${item->giveout_person!}/${item->giveout_amount!}</td>
                                        <td>${item->used_amount!}</td>
                                        <td class="tb-decorate-a">
                                            {{--<a href="/system/coupon/edit?shop_id=${data->shop_id!}&id=${item->id!}" target="_blank">{{ trans("admin/market_manage.coupon_manage.edit")!}</a>&nbsp;-&nbsp;--!}
                                            {{--<a href="##" alias_code="${item->alias_code!}" <#if ($nav_type == 3 || $nav_type == 4 || $item->enabled == 0 ||--!}
                                            {{--$item->end_time < date("Y-m-d H:i:s",time()) && $item->validity <=0) hidden--!}
                                               {{--<#else> class="hover_share" </#if>>{{ trans("admin/market_manage.coupon_manage.share")!}</a>--!}
                                            <span <#if ($nav_type == 3 || $nav_type == 4 || $item->enabled == 0 || $item->end_time < date("Y-m-d H:i:s",time()) && $item->validity <=0) hidden </#if>>&nbsp;-&nbsp;</span>
                                            <#if ($item->enabled ==1)<a  href="javascript:void(0)" class="abort" act_id="${item->id!}">{{ trans("admin/market_manage.coupon_manage.stop_use")!}</a><#else> 已停用 </#if> &nbsp;-&nbsp;
                                            <a href="/system/coupon/get/list?shop_id=${data->shop_id!}&id=${item->id!}" target="_blank">{{ trans("admin/market_manage.coupon_manage.get_detail")!}</a>
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
                                            <#if ($nav_type == 3 || $nav_type == 4 || $item->enabled == 0 || $item->end_time < date("Y-m-d H:i:s",time()) && $item->validity <=0)
                                                &nbsp;-&nbsp;<a href="#" coupon_id="${item->id!}" class="del">删除</a>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
                    <div class="paging_footer">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$coupon_list->perPage(),'currentPage'=>$coupon_list->currentPage(),'count'=>$coupon_list->count,'total'=>$coupon_list->total(),])!}
                                    <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${coupon_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${coupon_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${coupon_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text" value="${coupon_list->currentPage()!}"
                                           size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                </td>
                            </tr>
                        </table>
                    </div>
                </form>
        </div>
        </div>
        {{--普通优惠券部分结束--!}


    </div>
</div>
<script>
    function gopage(page) {
        var last_page = '${coupon_list -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }

    $('.abort').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用吗？' + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="del"]').val(_this.attr('act_id'));
                $("#form1").submit();
                layer.close(index);
            });
        });
   });
    $(".hover_share").hover(function(){
        var alias_code = $(this).attr("alias_code");
        var that = $(this);
        util.ajax_json('/system/coupon/getqrcode',function(d){
            if(d&&d.error==0){
                that.parent().find('.code_imgs').attr("src",d.content.qrcode_img);
                that.parent().find('.down_imgs').attr("href",d.content.qrcode_img);
                that.parent().find("#fe_text").val(d.content.type_url);
                that.parent().find('.share_span').show();
            }else{
                util.mobile_alert(d.message);
            }
        },{'alias_code':alias_code});
        // $(this).parent().find('.share_span').show();
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
    $(".del").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除么' + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="delete"]').val(_this.attr("coupon_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
</script>
<script>
    var page_home = '${coupon_list->currentPage()!}'; //当前页码数
    var page_all = '${coupon_list->count!}'            //总页码数
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")
