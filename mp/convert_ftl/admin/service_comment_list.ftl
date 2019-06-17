<#include "/admin/header.ftl">
<link href="/css/admin/goods_comment.css?v=1.1.6" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.27" type="text/css"/>

<style type="text/css">
    .comm_back{
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        left: 0;
        background: rgba(0,0,0,0.7);
        display: none;
    }
    .close_comm{
        position: fixed;
        top: 10px;
        right: 20px;
        display: none;
        z-index: 301;
        cursor: pointer;
    }
    #myCarousel img{
        margin: 0 auto;
    }
    .comm_all{
        width: 100%;
        position: absolute;
        top: 100px;
        left: 0;
        z-index: 300;
    }
    .coupon_type{
        background: #fff;
        padding:10px 0 0;
    }
    .coupon_type ul{
        list-style:none;
        background: #f5f5f5;
        width: 97%;
        margin:0 auto;
        border:1px solid #f3f3f3;
    }
    .coupon_type ul:after{
        content: '';
        display: block;
        clear: both;
    }
    .coupon_type ul li{
        float: left;
        width: 100px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        cursor: pointer;
    }
    .coupon_type ul li a{
        display: block;
        width: 100%;
        height:100%;
    }
    .coupon_type ul .actives{
        background: #fff;
    }
    .tab_navs1{
        list-style: none;
        background:#fff;
        /*padding: 10px 0 0px 1%;*/
        /*margin:10px 1% 0 1%;*/
    }
    .tab_navs1:after{
        content: "";
        display: block;
        clear: both;
    }
    .tab_navs1 li{
        float: left;
        width: 6%;
        text-align: center;
        padding-bottom: 10px;
    }
    .tab_navs1 li a{
        color: #000;
    }
    .tab_navs1 .active{
        border-bottom: 2px solid #578dfa;
    }
    .tab_navs1 li a:focus, .tab_navs li a:hover{
        text-decoration: none;
    }
    .checkbox_prev{
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        width: 13px;
        height: 13px;
        background: url(/image/admin/square_no.png) no-repeat;
        background-size: 100%;
        margin-left: 20px;
        position: relative;
        top: 1px;
        margin-right: 4px;
    }
    .checkbox_prev:checked{
        width: 13px;
        height: 13px;
        background: url(/image/admin/square_yes.png) no-repeat;
        background-size: 100%;
    }
    .goods_mess .goods_message .good_name{
        width: 60%;
        word-break: break-all;
    }
    .user_message div{
        margin-left: 0;
    }
    .tb_paging input{
        height: 13px;
    }
    .btns_group input[type='button']{
        height:30px;
        border: 1px solid #578dfa;
        color: #578dfa;
        line-height: 24px;
    }
    .btns_group input[type='button']:hover{
        background-color: #fff;
    }
    .btns_group input[type='button']:focus{
        background-color: #fff;
    }
    .btns_group input[type='button']:active{
        background-color: #fff;
    }
</style>
    <div class="title">
        {{--<span>服务管理 / </span>--!}
        {{--<span style="color: #666;">评价管理</span>--!}
        <span><a href="/admin/store/manage/list?top_index=6" style="color:  black">门店列表（${store_info->store_name!}）</a> /</span>
        <span><a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6"  style="color:  #666">评价管理</a></span>
        {{--<span> / ${store_info->store_name!}</span>--!}
    </div>
<div class="order-container">
    <div class="coupon_type">
        <ul>
            <li class="normal_type ">
                <a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6 ">预约管理</a>
            </li>
            <li class="fenlie_type">
                <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" class="edition_type">服务管理</a>
            </li>
            <li class="fenlie_type">
                <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" class="edition_tech" title="技师管理">${technician_title!}管理</a>
            </li>
            <li class="give_to_sb actives">
                <a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6">评价管理</a>
            </li>
        </ul>
    </div>
    <div style="padding: 10px 20px 0px 1.5%;margin: 0px;background-color: #ffffff">
    <ul id="tab" class="tab_navs1" style="border-bottom:1px solid #eee;margin-top: 10px">
        <li <#if ($nav==0)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/store/services/comment/list?nav=0&store_id=${store_id!}&top_index=6">评价记录</a>
        </li>
        <li <#if ($nav==1)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/store/services/comment/list?nav=1&store_id=${store_id!}&top_index=6">评价审核</a></li>
    </ul>
    </div>
    <script>
        // tab切换
        $("[data-toggle='tab']").click(function () {
            var url = $(this).attr("url");
            if (url != undefined) {
                window.location.href = url;
            }
        });
    </script>
    {{--<#if ($nav==1)--!}
        {{--<div class="audit_states clearfix">--!}
            {{--<div class="topBorder"></div>--!}
            {{--<input type="radio" name="aduit_state" id="no_aduit" value="0" style="margin-left: 0" <#if ($comment == 0) checked </#if>><span>不用审核</span>--!}
            {{--<input type="radio" name="aduit_state" id="last_aduit" value="1" <#if ($comment == 1) checked </#if>><span>先发后审</span>--!}
            {{--<input type="radio" name="aduit_state" id="first_aduit" value="2" <#if ($comment == 2) checked </#if>><span>先审后发</span>--!}
            {{--<button class="save_btn"  style="float: right;">保存</button>--!}
        {{--</div>--!}
    {{--</#if>--!}
    <#if ($data_list)
        <form <#if ($service_id>0) action="/admin/store/services/comment/list?nav=${nav!}&service_id=${service_id!}&top_index=6" <#else> action="/admin/store/services/comment/list?nav=${nav!}&top_index=6" </#if> method="post" id="form1">
            {{ csrf_field()!}
                <li class="order-info">
                    {{ csrf_field()!}
                    <input name="act" type="hidden">
                    <input name="id" type="hidden">
                    <input name="all" type="hidden">
                    <input name="cid" type="hidden">
                    <ul class="clearfix">
                        <li class="order-info-li clearfix">
                            <div class="fl">
                                <span>订单号</span>
                                <input type="text" name="order_sn" placeholder="输入订单号" value="${order_sn!}"/>
                            </div>
                            <div class="fl">
                                <span>服务名称</span>
                                <input type="text" name="service_name" placeholder="输入服务名称" value="${service_name!}"/>
                            </div>
                            <div class="fl">
                                <span>门店</span>
                                <select name="store_id">
                                    <option value="0">选择门店</option>
                                    <#list ($store as $st)
                                        <option value="${st->store_id!}" <#if ($st->store_id == $store_id)selected </#if>>${st->store_name!}</option>
                                    </#list>
                                </select>
                            </div>
                        </li>
                        <li class="order-info-li clearfix">
                            <div class="fl">
                                <span>会员手机号</span>
                                <input type="text" name="mobile" placeholder="输入会员手机号" value="${mobile!}"/>
                            </div>
                            <div class="fl">
                                <span>${technician_title!}名称</span>
                                <input type="text" name="technician" placeholder="输入${technician_title!}名称" value="${technician!}"/>
                            </div>
                            <div class="fl">
                                <span for="comment_star">评价星级</span>
                                <select name="level" id="comment_star">
                                    <option value="0" <#if ($level == 0) selected </#if>>全部</option>
                                    <option value="1" <#if ($level == 1) selected </#if>>一星</option>
                                    <option value="2" <#if ($level == 2) selected </#if>>二星</option>
                                    <option value="3" <#if ($level == 3) selected </#if>>三星</option>
                                    <option value="4" <#if ($level == 4) selected </#if>>四星</option>
                                    <option value="5" <#if ($level == 5) selected </#if>>五星</option>
                                </select>
                            </div>
                            <button  class="search_btn" style="float: right;margin-top:2px">搜索</button>

                        </li>
                        <li class="order-info-li clearfix">
                            <div class="fl">
                                <#if ($nav == 1)
                                    <span for="comment_star">审核状态</span>
                                    <select name="flag" id="flag">
                                        <option value="-1" <#if ($flag == -1) selected </#if>>全部</option>
                                        <option value="0" <#if ($flag == 0) selected </#if>>待审核</option>
                                        <option value="1" <#if ($flag == 1) selected </#if>>通过</option>
                                        <option value="2" <#if ($flag == 2) selected </#if>>未通过</option>
                                    </select>
                                </#if>
                            </div>
                        </li>
                    </ul>
            </div>
            <div class="box panel main-table">
                <div class="list-bottom">
                    <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                        <thead>
                        <tr>
                            <th></th>
                            <th width="18%">服务信息</th>
                            <th <#if ($nav == 1)  width="14%" <#else> width="17%" </#if> >用户信息</th>
                            <th  <#if ($nav == 1)  width="20%" <#else> width="26%" </#if>>评价内容</th>
                            <th width="8%">${technician_title!}名称</th>
                            <th width="8%">评价时间</th>
                            <th width="8%">匿名评价</th>
                            <#if ($nav ==1) <th width="8%">审核状态</th> </#if>
                            <th  <#if ($nav == 1)  width="17%" <#else> width="11%" </#if>>操作</th>
                        </tr>
                        </thead>
                        <tbody class="list">
                        <#list ($data_list as $item)
                            <tr>
                                <td align="center" width="5%" style="border-right: none;" >
                                    <input type="checkbox" name="checkbox" cid="${item->id!}" class="checkbox_prev">
                                </td>
                                <td class="goods_mess">
                                    <div class="order_sns">
                                        订单号：${item->order_sn!}
                                    </div>
                                    <div class="goods_message">
                                        <div class="good_img"><img src="${item->service_img[0]!}" alt=""></div>
                                        <div class="good_name">${item->service_name!}</div>
                                    </div>
                                </td>
                                <td class="user_message">
{{--                                    <#if ($item->username)--!}
                                        <div class="user_name">
                                            <p title="${item->username!}">用户名：<a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #0E70CA">${item->username!}</a></p>
                                        </div>
                                    {{--</#if>--!}
                                    <div class="user_mobile">手机号：${item->user_mobile!}</div>
                                </td>
                                <td class="comm_message">
                                    <div class="comm_star" star_amount="${item->commstar!}">评分：
                                        <img src="http://${image_domain!}/image/admin/comstar_${item->commstar!}.png" alt="">
                                    </div>
                                    <#if ($item->tag)
                                        <div class="comm_biao">
                                            <span>标签：</span>
                                            <#list ($item->tag as $row)
                                                <span class="biaoqian">${row!}</span>
                                            </#list>
                                        </div>
                                    </#if>
                                    <div class="comm_detail">
                                        <div>评价：${item->comm_note!}</div>
                                    </div>
                                    <div class="comm_detail">
                                        <#if ($item->comm_img)
                                            <#list ($item->comm_img as $row)
                                                <img src="${row!}" width="65px" height="65px" class="click_comm" />
                                            </#list>
                                        </#if>
                                    </div>
                                </td>
                                <td class="">
                                    <div class="" >
                                        <#if ($item->technician_id > 0)
                                        <a href="/admin/store/services/technician/add?technician_id=${item->technician_id!}&store_id=${store_id!}&top_index=6" style="color: #0E70CA">${item->technician_name!}</a>
                                        <#else>
                                        无
                                        </#if>
                                    </div>
                                </td>
                                <td>${item->in_time!}</td>
                                <td>
                                    <#if ( $item->anonymousflag == 0 )否<#else>是</#if>
                                </td>
                                <#if ($nav ==1) <td>  <#if ($item->flag == 0)待审核 <#elseif>($item->flag == 1)通过 <#else> 未通过 </#if> </td> </#if>
                                <td cid="${item->id!}">
                                    <#if ($nav == 0)
                                        <input type="button" class="dele_btn" value="删除评价">
                                    <#else>
                                        <input type="button" class="dele_btn" value="删除评价" style="margin-bottom: 10px">
                                        <#if ($item->flag == 0)
                                            <input type="button" class="ok_btn" value="通过">
                                            <input type="button" class="refused_btn" value="拒绝">
                                        <#elseif>($item->flag == 2)
                                            <input type="button" class="ok_btn" value="通过">
                                        <#else>
                                            <input type="button" class="refused_btn" value="拒绝">
                                        </#if>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <#if (count($data_list) <= 0)
                        <table style="width: 100%;border: 1px solid #eee;position: relative;margin-top: 16px;background: #fff">
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
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td style="padding-left: 15px;"  class="btns_group">
                                    <input type="checkbox" name="checkbox" class="checkbox_prev" style="border: 0;" id="select-all"> 全选
                                    <input name="all_pass" type="button" id="all_pass" value="通过" class="down tb-btm-left" <#if ($nav==0) hidden </#if>>
                                    <input name="all_no" type="button" id="all_no" value="拒绝" class="down tb-btm-left" <#if ($nav==0) hidden </#if>>
                                    <input name="all_delete" type="button" id="all_delete" value="删除" class="down tb-btm-left">

                                </td>
                                <td style="text-align:right;">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                    <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text" value="${data_list->currentPage()!}" style="width: 40px;height: 25px;text-align: center"
                                           size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                    <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                                </td>
                            </tr>
                        </table>
                    </#if>
                </div>
            </div>
        </form>
        <div class="comm_back"></div>
        <img src="http://${image_domain!}/image/wxapp/close_icon.png" class="close_comm" />
        <div class="comm_all">
            <div id="myCarousel" class="carousel slide">
                <div class="carousel-inner">
                </div>
                <a class="carousel-control left" href="#myCarousel" data-slide="prev"></a>
                <a class="carousel-control right" href="#myCarousel" data-slide="next"></a>
            </div>
        </div>
        <script>
            function gopage(page) {
                var last_page = '${data_list -> lastPage()!}';
                if(page > last_page) {
                    page = last_page;
                }
                $("#page").val(page);
                $("#form1").submit();
            }
            var page_home = '${data_list->currentPage()!}'; //当前页码数
            var page_all = '${data_list->count!}'            //总页码数
            var btn_home = $('.paging a:nth-of-type(1)');
            var btn_prev = $('.paging a:nth-of-type(2)');
            var btn_next = $('.paging a:nth-of-type(3)');
            var btn_last = $('.paging a:nth-of-type(4)');
            if(page_home == 1){
                btn_home.removeAttr('onClick');
                btn_home.css("cursor", "default");
                btn_prev.hide();
            }
            if(page_all == page_home){
                btn_last.removeAttr('onClick');
                btn_last.css("cursor", "default");
                btn_next.hide();
            }
        </script>
    </#if>
</div>
<script src="/js/admin/service_comment.js?v=1.0.5" type="text/javascript"></script>

<script type="text/javascript">
    //$("#main-table").FixedHead({tableLayout: "fixed"});
    var screen_wid = $(window).width();
    var img_wid = screen_wid*0.168*0.27;
    $(".good_img").css("width",img_wid);
    $(".good_img").css("height",img_wid);
    $(".good_name").css("height",img_wid);
    // $(".save_btn").click(function(){
    //     var data = {};
    //     data.service_comment =$('input[name="aduit_state"]:checked').val();
    //     util.ajax_json('/admin/ajax/servicecomment/cfg',function(d){
    //         if(d&&d.error ==0){
    //             util.mobile_alert(d.content);
    //             window.location.reload();
    //         }
    //         else{
    //             util.mobile_alert(d.error);
    //         }
    //     },data);
    // });

    $(".refused_btn").click(function(){
        $('input[name="act"]').val('refuse');
        $('input[name="id"]').val($(this).parent().attr("cid"));
        $("#form1").submit();
    });
    $(".ok_btn").click(function(){
        $('input[name="act"]').val('pass');
        $('input[name="id"]').val($(this).parent().attr("cid"));
        $("#form1").submit();
    });

</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    //点击查看评价原图
    $('.carousel').carousel('pause');//禁止轮播 自动轮播
    $('.comm_message').on('click','.click_comm',function(){
        $('.comm_back').show();
        $('.close_comm').show();
        $('.comm_all').css('padding-bottom','30px');
        var img_src = [];
        var img_index = $(this).index();
        $.each($(this).parent().find('img'),function(){  //获取当前评论的图片路径
            img_src.push($(this).attr('src'));
        });
        var item_html = '';
        $.each(img_src,function(k,v){   //将路径赋给轮播
            if(img_index == k){
                item_html += '<div class="item active">';
                item_html += '<img src="' + v + '" alt="' + k + ' slide">';
                item_html += '</div>';
            }else{
                item_html += '<div class="item">';
                item_html += '<img src="' + v + '" alt="' + k + ' slide">';
                item_html += '</div>';
            }
        });
        $('.carousel-inner').append(item_html);
        $('.comm_back,.close_comm,.carousel-inner').click(function(){
            $('.comm_back').hide();
            $('.close_comm').hide();
            $('.comm_all').css('padding-bottom','0px');
            item_html = '';
            $('.carousel-inner').html('');
        });
    });

    $('.user_name').hover(function(){
        $(this).find('span').show();
    },function(){
        $(this).find('span').hide();
    });

    $(".dele_btn").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="act"]').val('del');
                $('input[name="id"]').val(_this.parent().attr("cid"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
</script>
