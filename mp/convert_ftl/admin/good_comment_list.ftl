<#include "/admin/header.ftl">
<link href="/css/admin/goods_comment.css?v=1.1.9" rel="stylesheet" type="text/css"/>
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
        z-index: 3;
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
    .add_answer{
        cursor: pointer;
    }
    .main-table{
        background-color: #fff;
        padding: 10px;
    }
    .hideComment{
        float:right;
        margin:6px 20px 0 0;
    }
    .hideComment .tips{
        color:#999;
    }
    label.switch {
        display: inline-block;
        background-color: #ddd;
        height: 20px;
        width: 40px;
        cursor: pointer;
        border-radius: 25px;
        margin-bottom: 0;
        vertical-align: middle;
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    }
    input.switch:checked~label {
        background-color: #f7931e;
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    } 
    input.switch {
        display: none;
    }
    label.switch::before {
        content: '';
        position: relative;
        display: block;
        left: 0;
        border-radius: 25px;
        height: 100%;
        width: 20px;
        background-color: white;
        opacity: 1;
        box-shadow: 1px 1px 1px 1px rgba(0, 0, 0, 0.08);
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    }
    input.switch:checked~label:before {
        left: 21px;
        transition: all .5s ease;
        -webkit-transition: all .5s ease;
    }
</style>
<div class="title">
    <span>商品管理 / </span>
    <span style="color: #666;">评价管理</span>
</div>
<ul id="tab" class="tab_navs">
    <li <#if ($nav==0)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/list?nav=0">评价记录</a>
    </li>
    <li <#if ($nav==1)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/list?nav=1">评价审核</a></li>

    <#if ($addCommentSwitch == 1)
        <li <#if ($nav==2)class="active"</#if> style="width: 8%;"><a href="#" data-toggle="tab" url="/admin/goods/comment/add/manage?nav=2">商品列表</a></li>
    </#if>
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
        <div class="hideComment">
            <input type="checkbox" class="switch" id="checkbox1" name="comment_status" <#if ($comment_status == 0) checked </#if>>
            <label for="checkbox1" class="switch"></label>
            <span style="color: rgb(0, 0, 0);">已关闭</span>
            <span class="tips">设置前端是否隐藏未填写心得的评价</span>
        </div>
    </div>
</#if>
<#if ($data_list)
<form <#if ($goods_id>0) action="/admin/goods/comment/list?nav=${nav!}&goods_id=${goods_id!}" <#else> action="/admin/goods/comment/list?nav=${nav!}" </#if> method="post" id="form1">
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
    <div class="box panel main-table clearfix">
        <div class=" list-bottom">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th></th>
                    <th width="18%" style="border-left: none">商品信息</th>
                    <th width="14%">用户信息</th>
                    <th <#if ($nav == 1)  width="16%" <#else> width="21%" </#if>>评价内容</th>
                    <th width="15%">评论回复</th>
                    <th width="8%">评价时间</th>
                    <th width="7%">匿名评价</th>
                    <#if ($nav ==1) <th width="8%">审核状态</th> </#if>
                    <th <#if ($nav == 1)  width="12%" <#else> width="9%" </#if>>操作</th>
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
                                    <p title="${item->username!}">用户名：<#if ($item->is_shop_add == 1)${item->username!} <#else> <a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #0E70CA">${item->username!} </#if></a></p>
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
                        <td class="reply" cid="${item->id!}" style="text-align: left">
                            <div class="add_answer  <#if ($item->answer_id)hide </#if>" style="text-align: center">编写回复</div>
                            <div class="huifu  <#if (!$item->answer_id)hide </#if>">
                                <p class="slh"><span class="rlh_span">回复：</span>${item->content!}</p>
                                <div class="td_bottom">
                                    <span class="zk_sq hide">展开<img src="/image/admin/mp_de_collapse.png" class="zk_img"></span>
                                    <input type="button" class="dele_btn_reply" value="删除回复">
                                </div>
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
            <#if ($data_list->count == 0)
                <table style="width: 100%;border: 1px solid #eee;position: relative;margin-top: 16px;">
                    <tr>
                        <td style="width: 30px;height: 33px; margin: 18px auto auto auto;display: flex;justify-content: center;align-items: center;border:none !important;" >
                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                        </td>
                    </tr>
                    <tr>
                        <td style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center;border:none !important;">暂无相关数据</td>
                    </tr>
                </table>
            <#else>
            <table width="100%" border="0" class="tb_paging">
                <tr>
                    <td style="padding-left: 15px;border-left:none;"  class="btns_group">
                        <input type="checkbox" name="checkbox" class="checkbox_prev" style="border: 0;" id="select-all"> 全选
                        <input name="all_pass" type="button" id="all_pass" value="通过" class="down tb-btm-left" <#if ($nav==0) hidden </#if>>
                        <input name="all_no" type="button" id="all_no" value="拒绝" class="down tb-btm-left" <#if ($nav==0) hidden </#if>>
                        <input name="all_delete" type="button" id="all_delete" value="删除" class="down tb-btm-left">
                    </td>
                    <td style="text-align:right;border-left:none;">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                        <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                        <a href="#"
                           onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                        <a href="#"
                           onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                        <a href="#"
                           onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                        <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                size="5"
                                onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"  onkeyup="value=value.replace(/[^\d.]/g,'')" autocomplete="off">{{ trans("admin/common.page.page")!}
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
<script src="/js/admin/good_comment.js?v=1.0.5" type="text/javascript"></script>
<div id = "comment_answer" hidden>
    <div class="text_warn">
        <img src="/image/admin/notice_img.png">
        <span>回复提交后不可修改，请谨慎提交</span>
    </div>
    <div class="qm_text">
        <textarea name="content" id="content" placeholder="请输入回复内容"></textarea>
        <span ><span class="change_number">0</span>/500</span>
    </div>
</div>
<script>
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(parseInt(page) > parseInt(last_page)) {
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
        util.ajax_json('/admin/ajax/comment/cfg',function(d){
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
    });

    //文本超出4行的控制
    $(".slh").each(function(){
        var  _this=$(this);
        var need_height=parseInt(_this.css('line-height'))*4;
        var fontsize=parseInt(_this.css('font-size'));
        var rel_width=_this.width();
        var lin_num=Math.floor(rel_width/fontsize);
        var need_len=lin_num*4;
        var rel_height=_this.height();
        if(rel_height>need_height){
            _this.height(need_height);
            var text=_this.text();
            var text_len=_this.text().length;
            _this.text(_this.text().substring(0,need_len-1));
            _this.html(_this.text()+'…');
            var inhtml = _this.html();
            var array = inhtml.split("回复：");
            var inhtmlColor ="<span class='rlh_span'>回复：</span>"+array[1];
            _this.html(inhtmlColor );
            _this.siblings().find('.zk_sq').removeClass('hide');
            _this.siblings().find('.zk_sq').click(function () {
                if(! _this.is(".newtext")) {
                    var zksq = _this.siblings().find('.zk_sq');
                    if (zksq.text() == '展开') {
                        zksq.html("收起" + '<img src="/image/admin/mp_de_expand.png">');
                        _this.height(rel_height);
                        _this.text(text);
                        console.log(_this.text());
                        var inhtml = _this.html();
                        var array = inhtml.split("回复：");
                        var inhtmlColor = "<span class='rlh_span'>回复：</span>" + array[1];
                        _this.html(inhtmlColor);
                    } else {
                        zksq.html("展开" + '<img src="/image/admin/mp_de_collapse.png">');
                        _this.height(need_height);
                        _this.text(_this.text().substring(0, need_len - 1));
                        _this.html(_this.text() + '…');
                        var inhtml = _this.html();
                        var array = inhtml.split("回复：");
                        var inhtmlColor = "<span class='rlh_span'>回复：</span>" + array[1];
                        _this.html(inhtmlColor);
                    }!});
        }
    });

    $("#content").on("input propertychange", function() {
        var $this = $(this),
            _val = $.trim($this.val()),
            count = "";
        if (_val.length > 500) {
            $this.val(_val.substring(0, 500));
        }
        count = $.trim($this.val()).length;
        $(".change_number").text(count);
    });


    //删除回复
    $(".dele_btn_reply").click(function () {
        var _this = $(this);
        var comment_id = _this.parent().parent().parent().attr("cid");
        console.log(comment_id);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/ajax/comment/ans', function (response) {
                    console.log(response);
                    if (response.error == 0) {
                        // util.mobile_alert('回复失败，请重新回复');
                        layer.msg(response.message);
                    }else{
                        // layer.msg(response.message);
                        _this.parent().parent().parent().find(".huifu").addClass('hide');
                        _this.parent().parent().find(".slh").empty();
                        _this.parent().parent().find(".slh").text("回复：");
                        _this.parent().parent().parent().find(".add_answer").removeClass('hide');
                        // $("#form1").submit();
                        layer.close(index);
                        // $("#del_answer_"+comment_id).hide();
                        // $("#add_answer_"+comment_id).show();
                        //
                        // $("#content_"+comment_id).hide();
                    }
                }, {act: "del", id: comment_id });
                // $('input[name="act"]').val('del');
                // $('input[name="id"]').val(_this.parent().attr("cid"));

                // $("#form1").submit();

            });
        });
    });



    var comment = '';
    $(".add_answer").click(function () {
        var _this = $(this);
        var comment_id = _this.parent().attr("cid");
        // console.log(comment_id);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['回复', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['500px','430px']
                , content: $("#comment_answer")//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                }
                , yes: function (index, layero) {
                    var comment_content = $("#content").val();
                    comment = comment_content;
                    //保存按钮的回调
                    if ($("#content").val()==''){
                        layer.msg('请填写回复内容',{time:2*1000});
                    }else{
                        util.ajax_json('/admin/ajax/comment/ans', function (response) {
                            console.log(response);
                            if (response.error == 0) {
                                // util.mobile_alert('回复失败，请重新回复');
                            }else{
                                _this.addClass('hide');
                                _this.parent().find('.huifu').find(".slh").removeAttr("style");
                                _this.parent().find('.huifu').find(".slh").html("");
                                _this.parent().find('.huifu').removeClass('hide');
                                _this.siblings().find('.zk_sq').addClass('hide');
                                var  slh=_this.parent().find('.huifu').find('.slh');
                                slh.siblings().addClass('newtext2');
                                slh.addClass("newtext");
                                    slh.html("");
                                    slh.text("回复："+comment_content);
                                    //slh.html(content_text);
                                    var need_height=parseInt(slh.css('line-height'))*4;
                                    var fontsize=parseInt(slh.css('font-size'));
                                    var rel_width=slh.width();
                                    var lin_num=Math.floor(rel_width/fontsize);
                                    var need_len=lin_num*4;
                                    var rel_height=slh.height();
                                    var newtext1 = comment_content;
                                    if(rel_height>need_height){
                                        slh.height(need_height);
                                        //var newtext_len=slh.text().length;
                                        slh.text(comment_content.substring(0,need_len-3));
                                        slh.html(slh.text()+ '…');
                                        var inhtml = slh.html();
                                        //var array = inhtml.split("回复：");
                                        var inhtmlColor ="<span class='rlh_span'>回复：</span>"+inhtml;
                                        slh.html(inhtmlColor );
                                        slh.siblings().find('.zk_sq').removeClass('hide');
                                        var i = 0;
                                        $('.newtext2').find('.zk_sq').click(function(){
                                            i++;
                                            newtext1 = comment;
                                            if($(this).text()=='展开' && i%2!=0){
                                                $(this).html("收起"+'<img src="/image/admin/mp_de_expand.png">');
                                                slh.height(rel_height);
                                                slh.text(newtext1);
                                                var inhtml = slh.html();
                                                var inhtmlColor ="<span class='rlh_span'>回复：</span>"+inhtml;
                                                slh.html(inhtmlColor );
                                                return false;
                                            }else if(i%2==0){
                                                $(this).html("展开"+'<img src="/image/admin/mp_de_collapse.png">');
                                                slh.height(need_height);
                                                slh.text(newtext1.substring(0,need_len-1));
                                                slh.html(newtext1+'…');
                                                var inhtml = slh.html();
                                                var inhtmlColor ="<span class='rlh_span'>回复：</span>"+inhtml;
                                                slh.html(inhtmlColor );
                                                return false;
                                            }

                                        });
                                    }
                                $("#content").val('');
                                $(".change_number").text(0);
                                layer.close(index);
                            }
                        }, {act: "add", content: comment_content, id: comment_id });

                    }
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
        //}
        // else{
        //     util.mobile_alert('没有筛选出任何用户，请重新选择')
        // }
        // }, $("#form2").serialize());
    });
    $('input.switch').change(function(){
        let $this = $(this);
        let type = 0
        if($this.is(':checked')){
            $this.parent('.hideComment').find('span').eq(0).text('已开启')
            type = 0
        } else {
            $this.parent('.hideComment').find('span').eq(0).text('已关闭')
            type = 1
        }
        util.ajax_json('/admin/ajax/comment/status', function (res) {
            if (res.error == 0) {
                if(type == 0){
                    util.mobile_alert('已开启')                
                } else {
                    util.mobile_alert('已关闭')                                    
                }
            } else {
                util.mobile_alert(res.message)
            }
        }, {is_open:type})
    })
</script>
