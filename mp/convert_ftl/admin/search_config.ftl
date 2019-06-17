<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/search_config.css?v=1.0.1" type="text/css" />
<style>
    .config_right_info input[type="checkbox"]{
        display: none;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>基础配置 / </span>
        <span style="color: #666;">${title!}</span>
    </div>
    <div class="main-container">
        <div class="order-info fix_every_footer">
            <form action="/admin/config/search/config" method="post" id="form1">
            {{ csrf_field()!}
                <div class="clearfix">
                    <div class="config_left">
                        <img src="http://${image_domain!}/image/admin/search_config_left1.png" alt="">
                        <div class="may_not">
                            <div class="search_history">
                                <div class="sh_title">搜索历史</div>
                                <div class="some_history_item clearfix">
                                    <div class="each_sh_item">热水壶保温壶</div>
                                    <div class="each_sh_item">瓜子花生八宝粥</div>
                                    <div class="each_sh_item">啤酒饮料矿泉水</div>
                                    <div class="each_sh_item">春季时尚汇女装</div>
                                    <div class="each_sh_item">2019年新款女装红色丽人春季精选</div>
                                </div>
                            </div>
                            <div class="search_recommend">
                                <div class="sh_title" style="margin-top: 10px">热门搜索</div>
                                <div class="some_recommend_item clearfix">
                                    <#list  ((array)$hot_words as $words)
                                    <div class="each_sh_item">${words!}</div>
                                    </#list>
                                </div>
                            </div>
                        </div>
                        <img src="http://${image_domain!}/image/admin/search_config_left2.png" alt="">
                    </div>
                    <div class="config_right">
                        <div class="config_right_info">
                            <div class="info_title">默认搜索</div>
                            <div class="info_content">
                                <div class="moren_config">
                                    <div class="if_all">
                                        <input type="radio" name="title_action" value="1" <#if (!$title_action || $title_action == 1) checked </#if>>不设置 <span>(前端显示"请输入商品关键字")</span>
                                        <input type="radio" name="title_action" value="2" <#if  ($title_action == 2) checked </#if> style="margin-left: 60px">全部商品
                                    </div>
                                    <div class="if_all">
                                        <input type="radio" name="title_action" value="3" <#if  ($title_action == 3) checked </#if>>自定义
                                        <input type="text" name="title_custom" placeholder="热词" class="title_words" value="${title_custom!}" maxlength="15">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="config_right_info">
                            <input type="checkbox" name="is_open_history" value="1" <#if  (!isset($is_open_history) || $is_open_history) checked </#if>>
                            <div class="info_title">搜索历史</div>
                            <div class="info_content">
                                <div>
                                    <#if  (isset($is_open_history) && !$is_open_history)
                                    <div class="store_pay_fl " img_id="0" style="background: url('/image/admin/off_1.png') left top / 100% 100% no-repeat;">
                                        <label>
                                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_basics history_show" style="right:20px"/>
                                        </label>
                                    </div>
                                    <#else>
                                    <div class="store_pay_fl " img_id="1" style="background: url('/image/admin/on_1.png') left top / 100% 100% no-repeat;">
                                        <label>
                                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_basics history_show" >
                                        </label>
                                    </div>
                                    </#if>
                                    <span>
                                        <#if  (!isset($is_open_history) || $is_open_history)
                                            已开启
                                            <#else>
                                            已关闭
                                        </#if>
                                    </span>
                                    <span class="tips">启用后，可方便用户搜索</span>
                                </div>
                            </div>
                        </div>
                        <div class="config_right_info">
                            <input type="checkbox" name="is_open_hot_words" value="1" <#if  ($is_open_hot_words) checked </#if>>
                            <div class="info_title">搜索热词</div>
                            <div class="info_content">
                                <div style="margin-bottom: 20px">
                                    <#if  ($is_open_hot_words)
                                    <div class="store_pay_fl " img_id="1" style="background: url('/image/admin/on_1.png') left top / 100% 100% no-repeat;">
                                        <label>
                                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_basics hot_show" />
                                        </label>
                                    </div>
                                        <#else>
                                    <div class="store_pay_fl " img_id="0" style="background: url('/image/admin/off_1.png') left top / 100% 100% no-repeat;">
                                        <label>
                                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable config_basics hot_show" style="right:20px"/>
                                        </label>
                                    </div>
                                    </#if>
                                    <#if  ($is_open_hot_words)
                                    <span>已开启</span>
                                        <#else>
                                        <span>已关闭</span>
                                    </#if>
                                    <span class="tips">启用后，引导用户购买热搜商品 最多可添加十条</span>
                                </div>
                                <div class="btn_add_words">+添加热词</div>
                                <div class="hot_area">
                                    <#if  (empty($hot_words))
                                        <div class="each_words clearfix">
                                            <div class="ew_left">热词1：</div>
                                            <input type="text" name="hot_words[]" value="" maxlength="15">
                                            <a href="##" class="btn_del">删除</a>
                                        </div>
                                        <#else>
                                        <#list  ((array)$hot_words as $words)
                                        <div class="each_words clearfix">
                                            <div class="ew_left">热词${loop->iteration!}：</div>
                                            <input type="text" name="hot_words[]" value="${words!}" maxlength="15">
                                            <a href="##" class="btn_del">删除</a>
                                        </div>
                                        </#list>
                                    </#if>
                                </div>
                            </div>
                        </div>
                        <div class="reco_tips">注：”猜你喜欢“请在”商品管理“-”商品推荐“中配置</div>
                        <div class="some_reco">
                            <a href="/admin/goods/recommend/config" target="_blank">新建商品推荐</a> |
                            <a href="/admin/goods/recommend/list" target="_blank">管理商品推荐</a>
                        </div>
                    </div>
                </div>
            </form>
            <div class="fix_footer">
                <span class="btn_footer_save" style="width: 90px;line-height: 28px">保存</span>
            </div>
        </div>
    </div>
</div>
<div class="each_words clearfix no_show">
    <div class="ew_left">热词1：</div>
    <input type="text" name="hot_words[]" value="">
    <a href="##" class="btn_del">删除</a>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    // $(".config_basics").each(function () {
    //     var img_id = parseInt($(this).parent().parent().attr("img_id"));
    //     if(img_id == 1 ){
    //         $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
    //         $(this).animate({right:"0px"});
    //         $(this).parent().parent().next().html('已开启');
    //         $('[name="is_open_history"]').val(1);
    //     } else {
    //         $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
    //         $(this).animate({right:"20px"});
    //         $(this).parent().parent().next().html('已关闭');
    //         $('[name="is_open_history"]').val(0);
    //     }
    // });
    $(".config_basics").click(function () {
        var img_id = parseInt($(this).parent().parent().attr("img_id"));
        if(img_id == 0 ){
            $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
            $(this).animate({right:"0px"});
            $(this).parent().parent().next().html('已开启');
        } else {
            $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
            $(this).animate({right:"20px"});
            $(this).parent().parent().next().html('已关闭');
        }
    });
//    搜索历史的显示隐藏
    if($(".history_show").parent().parent().attr("img_id") == 1){
        $(".search_history").css("display",'block')
    }else{
        $(".search_history").css("display",'none')
    }
    $(".history_show").click(function () {
        if($(".history_show").parent().parent().attr("img_id") == 1){
            $(".search_history").css("display",'block');
            $('[name="is_open_history"]').prop('checked', true);
        }else{
            $(".search_history").css("display",'none');
            $('[name="is_open_history"]').prop('checked', false);
        }
    })

//热词推荐的显示隐藏
    if($(".hot_show").parent().parent().attr("img_id") == 1){
        $(".search_recommend").css("display",'block')
    }else{
        $(".search_recommend").css("display",'none')
    }
    $(".hot_show").click(function () {
        if($(".hot_show").parent().parent().attr("img_id") == 1){
            $(".search_recommend").css("display",'block');
            $('[name="is_open_hot_words"]').prop('checked', true);
        }else{
            $(".search_recommend").css("display",'none');
            $('[name="is_open_hot_words"]').prop('checked', false);
        }
    })

//添加热词
    $(".btn_add_words").click(function () {
        var all_length = $('.hot_area').children('.each_words').length;
        var new_one = $(".no_show").clone();
        new_one.removeClass('no_show');
        new_one.find(".ew_left").text("热词"+parseInt(all_length+1)+"：")
        new_one.appendTo($('.hot_area'));
        if(all_length >= 9){
            $(".btn_add_words").css('display','none')
        }
    })
//    删除热词
    $(document).on("click",".btn_del",function () {
        $(this).parent().remove();
        var all_length = $('.hot_area').children('.each_words').length;
        $(".hot_area .ew_left").each(function () {
            $(this).text("热词"+parseInt($(this).parent().index()+1)+"：")
        })
        if($(this).parent().find('input[type="text"]').val() != ""){
            var this_val = $(this).parent().find('input[type="text"]').val()
            $(".some_recommend_item div").each(function () {
                if($(this).text() == this_val){
                    $(this).remove();
                }
            })
        }
        if(all_length <= 9){
            $(".btn_add_words").css('display','block')
        }
    })
//    左边的热词
    $(document).on("blur",".each_words input[type='text']",function () {
        if($(this).val() == ''){
            util.mobile_alert("请填写热词内容");
            return false
        }
        $(".some_recommend_item").html("");
        for(var i=1;i<$(".each_words input[type='text']").length;i++){
            if($('.each_words:nth-of-type(' + i +') input[type="text"]').val() != "") {

                $(".some_recommend_item").append("<div class='each_sh_item'>" + $('.each_words:nth-of-type(' + i + ') input[type="text"]').val() + "</div>")
            }
        }

    })
    $('.btn_footer_save').click(function () {
        util.ajax_json('/admin/config/search/config', function (response) {
            util.mobile_alert(response.message);
            if (response.error == 0) {
                location.reload();
            }
        }, $('#form1').serialize())
    })
</script>