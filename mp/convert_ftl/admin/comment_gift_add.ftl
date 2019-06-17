<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pay_courtesy.css?v=1.0.1" type="text/css" />
<style>
    /* .card_add{
        display:inline-block;
        vertical-align: top;
        height: 98px;
        margin-bottom: 10px;
    }
    .coupon_div{
        display:block;
        width: 360px;
    }
    .coupon_list{
        display:inline-block;
        float:none;
    }
    .card_add img {
        margin-top: 14px;
    }
    .pay_right{
        width: 490px;
    }
    .pay_courtesy {
        width: 835px;
    }
    .coupon_list_bottom{
        border-bottom-left-radius: 8px;
        border-bottom-right-radius: 8px;
    } */
    .pay_left {
        position: relative;
    }

    .custom_popup_container{
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        width: 230px;
        top: 150px;
    }

    .custom_top {
        position: absolute;
        width: 20px;
        height: 20px;
        right: 24px;
    }

    .custom_xian {
        position: absolute;
        width: 1px;
        background-color: #fff;
        height: 20px;
        top: 20px;
        right: 34px;

    }

    .img_content {
        width: 100%;
        background: #fff;
        height: 270px;
        margin-top: 40px;
        border-radius: 5px;
        display: flex;
        justify-content: center;
        align-items: center;
        overflow: hidden;
    }

    .no_custom_img {
        text-align: center;
    }

    .no_custom_img span {
        color: #999;
        display: block;
        margin-top: 15px;
    }

    .no_custom_img img {
        width: 80px;
        height: 80px;
    }

    .img_content>img {
        width: 100%;
        height: 100%;
    }

    .add_image {
        width: 70px;
        height: 70px;
        background: url(/image/admin/btn_add.png) no-repeat;
        box-shadow: 0 0 0 #fff;
        /* background-position: center 48%;
        background-size: 50%; */
        padding-top: 40px;
        padding-left: 8px;
        color: #9a9a9a;
        border: none;
        margin-right: 10px;
        float: left;
    }

    .logo_img {
        float: left;
        display: none;
        border: 1px solid #eee;
        position: relative;
        margin-right: 10px;
    }

    .logo_img img {
        width: 70px;
        height: 70px;
    }

    .logo_img span {
        text-align: center;
        width: 100%;
        line-height: 18px;
        position: absolute;
        left: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.5);
        color: #fff;
        font-size: 12px;
        display: none;
        cursor: pointer;
    }

    [type="button"]:hover,
    [type="button"]:active,
    [type="button"]:focus,
    [type="reset"]:hover,
    [type="reset"]:active,
    [type="reset"]:focus {
        background-color: transparent;
        border-color: #e5e5e5;
    }

    .pay_right+.pay_right {
        margin-top: 10px;
    }

    .tips {
        color: #999;
    }

    .choose_goods {
        margin-top: 15px;
    }

    .add_goods>span:first-child {
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5A8BFF;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        display: inline-block;
    }

    .add_goods>span:first-child>img {
        vertical-align: baseline;
    }

    .add_goods>span:last-child {
        margin-left: 10px;
    }

    .add_goods {
        margin-top: 10px;
    }

    .choose_goods {
        display: none;
    }

    .choose_link {
        background-color: #fff;
        color: #000;
        width: 80px;
        line-height: 28px;
        border: 1px solid #dbdbdb;
    }

    .custom_popup_bg {
        position: absolute;
        width: 100%;
        background: #000;
        height: 577px;
        top: 55px;
        opacity: 0.3;
    }

    .pay_courtesy {
        width: 840px;
    }

    .pay_right {
        width: 500px;
    }

    .condition_box {
        margin-top: 10px;
    }

    .condition_box [type="checkbox"] {
        top: 2px;
    }

    .template_right_ul .fl {
        width: 90px;
    }

    .tem_right {
        float: unset;
        overflow: hidden;
    }

    .type_radio label {
        width: 100px;
    }

    .gift_box>div,
    .custom_popup_container,
    .condition_box {
        display: none;
    }
    .popup_container{
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        width: 196px;
        top: 240px;
        height:170px;
        background-color:#ffdfbb;
        border-radius: 8px;
    }
    .images1{
        width: 240px;
        position: absolute;
        top: -110px;
        left: 50%;
        transform: translateX(-50%)
    }
    .images2{
        position: absolute;
        top: -70px;
        left: 50%;
        transform: translateX(-50%);
        z-index: 10;
        width: 220px;
    }
    .popup_title{
        position: absolute;
        font-weight: 600;
        font-size: 18px;
        color: #fff;
        top: -18px;
        z-index: 11;
        left: 50%;
        transform: translateX(-50%);
    }
    .popup_title2{
        text-align: center;
        margin-top: 40px;
        color: #e97600;
        font-size: 15px;
        font-weight: 600;
    }
    .gift_info{
        text-align: center;
        margin-top: 20px;
        color: #e97600;
        font-size: 18px;
        font-weight: 600;
    }
    .gift_tips{
        text-align: center;
        margin-top: 20px;
        font-size: 14px;
        color: #333;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span>评价有礼</span>
    </div>
    <div class="main-container fix_every_footer">
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="ids" value="${data_list->id!}">
            <div class="pay_content">
                <ul class="tab clearfix">
                    <li <#if ($nav_type==0)class="active" </#if>>
                        <a href="/admin/market/comment/gift/list?nav=0">全部评价有礼活动</a>
                    </li>
                    <li <#if ($nav_type==1)class="active" </#if>>
                        <a href="/admin/market/comment/gift/list?nav=1">进行中</a>
                    </li>
                    <li <#if ($nav_type==2)class="active" </#if>>
                        <a href="/admin/market/comment/gift/list?nav=2">未开始</a>
                    </li>
                    <li <#if ($nav_type==3)class="active" </#if>>
                        <a href="/admin/market/comment/gift/list?nav=3">已过期</a>
                    </li>
                    <li <#if ($nav_type==4)class="active" </#if>>
                        <a href="/admin/market/comment/gift/list?nav=4">已停用</a>
                    </li>
                    <li <#if ($nav_type==5)class="active" </#if>><a
                            href="/admin/market/comment/gift/add"><#if ($data_list->id)编辑${data_list->name!}活动
                            <#else>添加评价有礼活动</#if></a></li>
                </ul>
                <div class="pay_courtesy clearfix">
                    <div class="pay_left">
                        <div class="left_title"></div>
                        <img src="http://${image_domain!}/image/admin/comment_gift_bg.jpg" width="100%" />
                        <div class="custom_popup_bg"></div>
                        <div class="custom_popup_container">
                            <img src="http://${image_domain!}/image/wxapp/split_btn1.png" alt="" class="custom_top">
                            <i class="custom_xian"></i>
                            <div class="img_content">
                                <div class="no_custom_img">
                                    <img src="http://${image_domain!}/image/admin/no_custom_img.png" alt="">
                                    <span>
                                        活动图片
                                    </span>
                                </div>
                                <img src="" alt="" style="display:none;">
                            </div>
                        </div>
                        <div class="popup_container">
                            <img src="http://${image_domain!}/image/wxapp/award_bg.png" class="images1" alt="">
                            <img src="http://${image_domain!}/image/wxapp/is_award1.png" class="images2" alt="">
                            <div class="popup_title">恭 喜 您!</div>
                            <p class="popup_title2">获得</p>
                            <p class="gift_info">奖励内容</p>
                            <p class="gift_tips">快去查看吧</p>
                        </div>
                    </div>
                    <div class="pay_right">
                        <div class="pay_right_title">活动信息</div>
                        <ul class="template_right_ul">
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>活动名称：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <input type="text" placeholder="最多支持10个字" name="name"
                                            class="activity_names" id="activity_names"
                                            value="${data_list->name!}" />
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>活动有效期：</span>
                                </div>
                                <div class="tem_right">
                                    <p class="right_ipt">
                                        <label><input type="radio" name="is_forever" <#if ($data_list->is_forever == 0) checked </#if> value="0"> 固定时间</label>
                                        <input <#if ($edit==1 && $data_list->start_time
                                        <= date("Y-m-d H:i:s")) disabled </#if> type="text" name="start_time"
                                            value="${data_list->start_time!}" id="actstartDate" onfocus="picker()"
                                            autocomplete="off" placeholder="生效时间" style="width:130px" />
                                        <span>至</span>
                                        <input <#if ($edit==1 && $data_list->start_time
                                        <= date("Y-m-d H:i:s")) disabled </#if> type="text" name="end_time"
                                            value="${data_list->end_time!}" id="actendDate" onfocus="picker()"
                                            autocomplete="off" placeholder="过期时间" style="width:130px" />
                                    </p>
                                    <p>
                                        <label><input type="radio" name="is_forever"  <#if ($data_list->is_forever == 1) checked </#if> value="1"> 永久有效</label>
                                    </p>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>触发条件：</span>
                                </div>
                                <div class="tem_right">
                                    <label><input type="radio" name="act_condition_type" <#if ($data_list->condition == 0 ) checked </#if> value="0">评价即送</label>
                                    <label style="margin-left:30px;"><input type="radio" name="act_condition_type"
                                    <#if ($data_list->condition == 1 ) checked </#if> value="1">自定义</label>
                                    <div class="condition_box">
                                        <label><input type="checkbox" name="condition_type[]" <#if ($data_list->condition == 1 && $id>0 && in_array(1,$data_list->cons)) checked </#if> value="1">
                                            <span>晒图</span></label>
                                        <label style="margin-left:30px;"><input type="checkbox" name="condition_type[]"
                                                value="2" <#if ($data_list->condition == 1 && $id>0 && in_array(2,$data_list->cons)) checked </#if>> <span>五星好评</span></label>
                                        <label style="margin-left:30px;"><input type="checkbox" name="condition_type[]"
                                                value="3" <#if ($data_list->condition == 1 && $id>0 && in_array(3,$data_list->cons)) checked </#if>></label>
                                        心得超过<input type="text" name="words" value="${data_list->words!}"
                                            style="width:50px;border: 1px solid #dbdbdb;height: 30px;padding-left: 12px;margin:0 10px">字
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="pay_right">
                        <div class="pay_right_title">评价奖励</div>
                        <ul class="template_right_ul">
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em>
                                    <span>评价奖励：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="type_radio">
                                        <label><input type="radio" name="award_type" <#if ($data_list->award_type == 1) checked </#if> value="1" id="score"
                                                checked>积分</label>
                                        <label><input type="radio" name="award_type" <#if ($data_list->award_type == 2) checked </#if> value="2" id="coupon" >优惠券</label>
                                        <label><input type="radio" name="award_type" <#if ($data_list->award_type == 3) checked </#if> value="3" id="account">余额</label>
                                        <label><input type="radio" name="award_type" <#if ($data_list->award_type == 4) checked </#if> value="4" id="lottery">幸运大抽奖</label>
                                        <label><input type="radio" name="award_type" <#if ($data_list->award_type == 5) checked </#if> value="5" id="custom">自定义</label>
                                    </div>
                                    <div class="gift_box right_ipt">
                                        <div class="coupon_box">
                                            <span><em>*</em>优惠券：</span>
                                            <select name="award_coupon" id="" class="award_coupon">
                                                <option value="0">未选择</option>
                                                <#if ($coupon_list)
                                                    <#list ($coupon_list as $value)
                                                        <option value="${value->id!}" <#if ($data_list->award_type == 2 && $data_list->award == $value->id) selected </#if> num="${value->surplus!}">${value->act_name!}</option>
                                                    </#list>
                                                </#if>
                                            </select>
                                            <p class="tips">优惠券可用库存<span class="coupon_num"
                                                    style="color:#5a8bff">0</span>份</p>
                                        </div>
                                        <div class="score_box">
                                            <span><em>*</em>积分：</span>
                                            <input style="width:150px" type="text" name="score" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="请输入积分"
                                            <#if ($data_list->award_type == 1) value="${data_list->award!}" <#else> value="" </#if>>
                                        </div>
                                        <div class="account_box">
                                            <span><em>*</em>余额：</span>
                                            <input style="width:150px" type="text" name="account" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="请输入金额"
                                            <#if ($data_list->award_type == 3) value="${data_list->award!}" <#else> value="" </#if>>
                                        </div>
                                        <div class="lottery_box">
                                            <span><em>*</em>幸运大抽奖：</span>
                                            <select name="lottery_name" id="" class="lottery_name">
                                                <option value="0">未选择</option>
                                                <#list  ($lottery as $l)
                                                    <option value="${l->id!}"  <#if ($data_list->award_type == 4 && $data_list->award == $value->id) selected </#if>>${l->lottery_name!}</option>
                                                    </#list>
                                            </select>
                                        </div>
                                        <div class="custom_box">
                                            <div>
                                                <span style="float:left"><em>*</em>活动图片：</span>
                                                <div class="tem_right" style="margin-top:7px;">
                                                    <input type="hidden" name="payreward_logo"
                                                        value="${data_list->img!}">
                                                    <div class="logo_img" style="display: none;">
                                                        <img src="" alt="">
                                                        <span class="choose_img">重新选择</span>
                                                    </div>
                                                    <input type="button" value="" class="add_image"
                                                        style="display: block;">
                                                    <span
                                                        style="margin-top: 25px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：560px
                                                        * 700px</span>
                                                </div>
                                            </div>
                                            <div style="margin-top: 10px;">
                                                <span style="float:left;line-height: 30px;"><em>*</em>设置链接：</span>
                                                <div class="tem_right">
                                                    <div class="right_ipt">
                                                        <input type="text" name="link"
                                                            value="${data_list->link!}">
                                                        <button class="choose_link">选择链接</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="award_num" style="margin-top: 10px;">
                                            <span><em>*</em>奖品分数：</span>
                                            <input style="width:150px" type="text" name="award_num" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder=""
                                                value="${data_list->award_num!}"> 份
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="fix_footer">
                <div class="pay_save">保存</div>
            </div>
        </form>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script>
    inputTest()
    var hasSaved = true;
    var act_end_time = "${act_end_time!}"
    function picker() {
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm',
                autoUpdateOnChanged: false
            }
        );
    }
    $(".add_image,.choose_img").click(function () {
        var that = $(this);
        $.jImageManager({
            img_width: 560,
            img_height: 700,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                $('.logo_img').show()
                $('.logo_img img').attr('src', path);
                $('input[name="payreward_logo"]').val(path);
                that.hide();
                $('.img_content > img').attr('src', path);
                $('.img_content > img').show();
                $('.no_custom_img').hide();
            }
        });
        hasSaved = false;
    });
    $(document).on('mouseenter', '.logo_img', function () {
        $('.logo_img').find('span').show()
    });
    $(document).on('mouseleave', '.logo_img', function () {
        $('.logo_img').find('span').hide()
    });

    var activity_names = $('.activity_names').get(0);
    var flag = false;
    activity_names.addEventListener('compositionstart', function () {
        console.log(1);
        flag = true;
        6
    });
    activity_names.addEventListener('compositionend', function () {
        console.log(2)
        flag = false
        limitLength(this.value, '20', '', 'activity_names')
    });
    activity_names.addEventListener('input', function () {
        console.log(3)
        limitLength(this.value, '20', '', 'activity_names')
    });
    function limitLength(value, byteLength, title, attribute) {
        if (!flag) {
            var newvalue = value.replace(/[^\x00-\xff]/g, "**");
            var length = newvalue.length;
            if (length * 1 <= byteLength * 1) {
                return;
            }
            var limitDate = newvalue.substr(0, byteLength);
            var count = 0;
            var limitvalue = "";
            for (var i = 0; i < limitDate.length; i++) {
                var flat = limitDate.substr(i, 1);
                if (flat == "*") {
                    count++;
                }
            }
            var size = 0;
            var istar = newvalue.substr(byteLength * 1 - 1, 1);

            if (count % 2 == 0) {
                size = count / 2 + (byteLength * 1 - count);
                limitvalue = value.substr(0, size);
            } else {
                size = (count - 1) / 2 + (byteLength * 1 - count);
                limitvalue = value.substr(0, size);
            }
            document.getElementById(attribute).value = limitvalue;
            return;
        }
    }
    $('.choose_link').click(function (e) {
        e.preventDefault();
        var ipt_link = $(this).prev();
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择链接', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['800px', '460px']
                , content: '/admin/frame/decoration/link' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index) {
                    var iframe = layer.getChildFrame('body', index);
                    var link = iframe.contents().find('tr[data-back="false"]').find(".link").text();
                    var appid = iframe.contents().find('tr[data-back="false"]').find(".appid").text();
                    ipt_link.val(link);
                    ipt_link.attr('data-appid', appid);
                    layer.close(index);
                }
            });
        });
    });
    // $('[name="gift_type"]').click(function () {
    //     inputTest()
    // })

    // $('[name="act_time_type"]').click(function () {
    //     inputTest()
    // })

    // $('[name="act_condition_type"]').click(function () {
    //     inputTest()
    // })
    $('select,input').change(function(){
        inputTest();
    })
    $('input[type="text"]').keyup(function(){
        inputTest();
    })
    function inputTest() {
        if ($('[name="award_type"]:checked').val() == 1) {
            $('.gift_box > div').hide();
            $('.score_box,.award_num').show();
            if($('[name="score"]').val() != ''){
                $('.gift_info').text($('[name="score"]').val()+'积分')
            } else {
                $('.gift_info').text('奖励内容');
            }
        } else if ($('[name="award_type"]:checked').val() == 2) {
            $('.gift_box > div').hide();
            $('.coupon_box,.award_num').show();
            if($('[name="award_coupon"]').val() != 0){
                $('.gift_info').text('优惠券：'+ $('[name="award_coupon"] option:selected').text())
            } else {
                $('.gift_info').text('奖励内容');
            }
        } else if ($('[name="award_type"]:checked').val() == 3) {
            $('.gift_box > div').hide();
            $('.account_box,.award_num').show();
            if($('[name="account"]').val() != ''){
                $('.gift_info').text($('[name="account"]').val()+'余额')
            } else {
                $('.gift_info').text('奖励内容');
            }
        } else if ($('[name="award_type"]:checked').val() == 4) {
            $('.gift_box > div').hide();
            $('.lottery_box,.award_num').show();
            if($('[name="lottery_name"]').val() != 0){
                $('.gift_info').text($('[name="lottery_name"] option:selected').text())
            } else {
                $('.gift_info').text('奖励内容');
            }
        } else if ($('[name="award_type"]:checked').val() == 5) {
            $('.gift_box > div').hide();
            $('.custom_box,.award_num').show();
        }

        if ($('[name="award_type"]:checked').val() == 5){
            $('.custom_popup_container').show()
            $('.popup_container').hide()
        } else {
            $('.custom_popup_container').hide()
            $('.popup_container').show()
        }
        if ($('[name="is_forever"]:checked').val() == 0) {
            $('[name="is_forever"]').parent().siblings().show()
        } else {
            $('[name="is_forever"]').parent().siblings().hide()
        }

        if ($('[name="act_condition_type"]:checked').val() == 0) {
            $('.condition_box').hide()
        } else {
            $('.condition_box').show()
        }
        if($('[name="award_coupon"] option:selected').val() != 0){
            $('.coupon_num').text($('[name="award_coupon"] option:selected').attr('num'))
        }

        if ($.trim($('input[name="payreward_logo"]').val()) != '') {
            $('.img_content > img').attr('src', $('input[name="payreward_logo"]').val());
            $('.img_content > img').show();
            $('.no_custom_img').hide();
        }
    }

    $('.pay_save').click(function () {
        if ($('[name="name"]').val() == '') {
            util.mobile_alert('请输入活动名称');
            return false;
        }
        if ($('[name="is_forever"]:checked').val() == 0 && ($('[name="start_time"]').val() == '' || $('[name="end_time"]').val() == '')) {
            util.mobile_alert('请输入活动时间');
            return false;
        }
        if ($('[name="act_condition_type"]:checked').val() == 1 && $('[name="condition_type[]"]:checked').length == 0) {
            util.mobile_alert('请至少选择一项触发条件');
            return false;
        }
        if ($('[name="award_type"]:checked').val() == 1) {
            if ($('[name="score"]').val() == '') {
                util.mobile_alert('请输入积分');
                return false;
            }
        } else if ($('[name="award_type"]:checked').val() == 2) {
            if ($('[name="award_coupon"]').val() == 0) {
                util.mobile_alert('请选择优惠券');
                return false;
            }
        } else if ($('[name="award_type"]:checked').val() == 3) {
            if ($('[name="account"]').val() == '') {
                util.mobile_alert('请输入金额');
                return false;
            }
        } else if ($('[name="award_type"]:checked').val() == 4) {
            if ($('[name="lottery_name"]').val() == 0) {
                util.mobile_alert('请选择大抽奖活动');
                return false;
            }

        } else if ($('[name="award_type"]:checked').val() == 5) {
            if ($('[name="payreward_logo"]').val() == '') {
                util.mobile_alert('请选择图片');
                return false;
            }
            if ($('[name="link"]').val() == '') {
                util.mobile_alert('请选择链接');
                return false;
            }
        }

        if(isNaN($('[name="award_num"]').val()) || $('[name="award_num"]').val() <= 0){
            util.mobile_alert('请输入奖品份数');
            return false;
        }
        if($('[name="award_type"]:checked').val() == 2 && $('[name="award_num"]').val() > $('.coupon_num').text()){
            util.mobile_alert('奖品份数不得超过现有优惠券数量');
            return false;
        }
        $('#form1').submit();
    })
</script>
{{--<script>--!}
    {{--getPowerInfo('main_config', 'comment_gift', 'sub_4', '评价有礼', 0);--!}
{{--</script>--!}