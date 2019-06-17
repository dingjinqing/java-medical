<#include ("system.header")
<link href="/css/admin/goods_comment.css?v=1.1.5" rel="stylesheet" type="text/css"/>
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
    <span>商品管理 / </span>
    <span style="color: #666;">评价管理</span>
</div>
<ul id="tab" class="tab_navs">
    <li <#if ($nav==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/good/comment?nav=0&shop_id=${shop_id!}">评价记录</a>
    </li>
    <li <#if ($nav==1)class="active"</#if>><a href="#" data-toggle="tab" url="/system/good/comment?nav=1&shop_id=${shop_id!}">评价审核</a></li>
</ul>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>
<#if ($nav==1)
    <div class="audit_states clearfix">
        <div class="topBorder"></div>
        <input type="radio" name="aduit_state" id="no_aduit" value="0" style="margin-left: 0" <#if ($comment == 0) checked </#if>><span>不用审核</span>
        <input type="radio" name="aduit_state" id="last_aduit" value="1" <#if ($comment == 1) checked </#if>><span>先发后审</span>
        <input type="radio" name="aduit_state" id="first_aduit" value="2" <#if ($comment == 2) checked </#if>><span>先审后发</span>
        <button class="save_btn"  style="float: right;">保存</button>
    </div>
</#if>
<#if ($data_list)
<form <#if ($goods_id>0) action="/system/good/comment?nav=${nav!}&goods_id=${goods_id!}&shop_id=${shop_id!}" <#else> action="/system/good/comment?nav=${nav!}&shop_id=${shop_id!}" </#if> method="post" id="form1">
    {{ csrf_field()!}
    <input name="act" type="hidden">
    <input name="id" type="hidden">
    <input type="hidden" name="all">
    <input type="hidden" name="cid">
    <div class="search_banner clearfix" style="<#if ($nav==1)margin-top: 10px;padding-top:10px;</#if>">
        <#if ($nav==0)<div class="topBorder"></div></#if>
        <label for="order_sn">订单编号</label>
        <input type="text" value="${order_sn!}" id="order_sn" name="order_sn" placeholder="输入订单编号" <#if ($nav == 1)style="width:10%;" </#if>>
        <label for="goods_name" >商品名称</label>
        <input type="text" value="${goods_name!}" id="goods_name" name="goods_name" placeholder="输入商品名称" <#if ($nav == 1)style="width:10%;" </#if>>
        <label for="member_phone">会员手机号</label>
        <input type="text" value="${mobile!}" id="member_phone" name="mobile" placeholder="输入会员手机号" <#if ($nav == 1)style="width:11%;" </#if>>
        <#if ($nav == 1)
            <label for="comment_star">审核状态</label>
            <select name="flag" id="flag">
                <option value="-1" <#if ($flag == -1) selected </#if>>全部</option>
                <option value="0" <#if ($flag == 0) selected </#if>>待审核</option>
                <option value="1" <#if ($flag == 1) selected </#if>>通过</option>
                <option value="2" <#if ($flag == 2) selected </#if>>未通过</option>
            </select>
        </#if>
        <label for="comment_star">评价星级</label>
        <select name="level" id="comment_star">
            <option value="0" <#if ($level == 0) selected </#if>>全部</option>
            <option value="1" <#if ($level == 1) selected </#if>>一星</option>
            <option value="2" <#if ($level == 2) selected </#if>>二星</option>
            <option value="3" <#if ($level == 3) selected </#if>>三星</option>
            <option value="4" <#if ($level == 4) selected </#if>>四星</option>
            <option value="5" <#if ($level == 5) selected </#if>>五星</option>
        </select>
        <button  class="search_btn" style="float: right;margin-top:2px">搜索</button>
    </div>
    <div class="box panel main-table">
        <div class=" list-bottom">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th></th>
                    <th width="22%">商品信息</th>
                    <th width="17%">用户信息</th>
                    <th <#if ($nav == 1)  width="21%" <#else> width="26%" </#if>>评价内容</th>
                    <th width="8%">评价时间</th>
                    <th width="8%">匿名评价</th>
                    <#if ($nav ==1) <th width="8%">审核状态</th> </#if>
                    <th <#if ($nav == 1)  width="14%" <#else> width="11%" </#if>>操作</th>
                </tr>
                </thead>
                <tbody  class="list">
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
                                <div class="good_img"><img src="${item->goods_img!}" alt=""></div>
                                <div class="good_name">${item->goods_name!}</div>
                            </div>
                        </td>
                        <td class="user_message">
                            <#if ($item->username)
                                <div class="user_name">
                                    <p title="${item->username!}">用户名：<a href="/system/user/center?user_id=${item->user_id!}&shop_id=${shop_id!}" style="color: #0E70CA">${item->username!}</a></p>
                                </div>
                            </#if>
                            <div class="user_mobile">手机号：${item->mobile!}</div>
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
                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                size="5"
                                onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                    </td>
                </tr>
            </table>
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
<script src="/js/admin/good_comment.js?v=1.0.4" type="text/javascript"></script>

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
<script type="text/javascript">
    //$("#main-table").FixedHead({tableLayout: "fixed"});
    var screen_wid = $(window).width();
    var img_wid = screen_wid*0.168*0.27;
    $(".good_img").css("width",img_wid);
    $(".good_img").css("height",img_wid);
    $(".good_name").css("height",img_wid);
    $(".save_btn").click(function(){
        var data = {};
        data.comment =$('input[name="aduit_state"]:checked').val();
        util.ajax_json('/system/comment/cfg?shop_id=${shop_id!}',function(d){
            if(d&&d.error ==0){
                util.mobile_alert(d.content);
                window.location.reload();
            }
            else{
                util.mobile_alert(d.message);
            }
        },data);
    });

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
<#include ("system.footer")
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
            layer.alert('<div style="text-align: center;">' + '确认要删除么' + '</div>', {
                title: false
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
