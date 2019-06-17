<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css" />
<link rel="stylesheet" href="/css/admin/add_assess.css?v=1.0.0" type="text/css" />
<style>
    .preview_subject_title {
        padding-left: 20px;
        line-height: 56px;
    }

    a,
    a:hover,
    a:link {
        color: #5a8bff;
    }

    select {
        width: 170px;
        line-height: 30px;
        height: 30px;
        border: 1px solid #ccc;
    }

    .subject_box {
        background-color: #fff;
        margin-top: 15px;
        padding: 10px 0;
        border: 1px solid #ddd;
    }

    .assess_content {
        max-width: 1000px;
    }

    .btn_save a {
        color: #fff !important;
    }

    .preview_subject_title {
        text-align: center;
        padding-left: 0;
        font-weight: 600;
    }

    .preview_top {
        overflow: hidden;
        position: unset;
        margin: auto;
        transform: none;
    }

    .preview_pic {
        margin: auto;
        margin-top: 10px;
        width: 90%;
        border-radius: 6px;
    }

    em {
        color: red
    }

    .gift_box input[type="text"] {
        width: 140px;
    }

    .preview_title {
        border-bottom: 1px solid #ccc;
    }

    .in_li {
        margin-right: 10px;
        width: 90px;
    }

    div.condition_box {
        padding-left: 10px;
        background-color: #fff;
        border-radius: 6px;
        width: 420px;
    }

    .depict_content {
        margin-top: 10px;
    }

    .preview_bottom {
        width: 280px;
        height: 100px;
        background-color: #fff;
        margin: auto;
        margin-top: 20px;
        border-radius: 6px;
    }

    .preview_bottom {
        width: 280px;
        background-color: #fff;
        margin: auto;
        margin-top: 20px;
        border-radius: 6px;
    }

    .preview_bottom>img {
        width: 100px;
        height: 100px;
    }

    .preview_bottom>div {
        margin-top: 28px;
        padding-right: 20px;
        line-height: 25px;
        text-align: right;
    }

    .choose_goods {
        margin-left: 0px;
        width: 120px;
        /* height: 30px; */
        line-height: 30px;
        text-align: center;
        color: #5A8BFF;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        display: inline-block;
    }

    .goods_modal {
        border: none;
        margin-top: 10px;
        margin-bottom: 0;
        margin-left: none;
    }

    .goods_modal th {
        padding: 10px 0;
        border: 1px solid #eee;
    }

    .goods_table th,
    .cat_table th,
    .goods_modal th,
    .cat_modal th,
    .sort_table th {
        background: #F8F8F8;
    }

    .goods_table td,
    .goods_modal td,
    .cat_modal td,
    .sort_table td {
        border: 1px solid #ddd;
        background: #fff;
    }

    .goods_modal,
    .cat_modal {
        display: none;
    }

    .goods_img {
        float: left;
        margin: 0 5px 0 0 !important;
    }

    .goods_img img {
        width: 40px;
    }

    .goods_info {
        margin: 0 auto !important;
        width: 180px;
    }

    .goods_name {
        width: 135px;
        height: 40px;
        float: left;
        line-height: 20px !important;
        margin-left: 0px !important;
        margin-bottom: 0px !important;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">${title!}</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/assess/list?nav=0">全部测评</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=1">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=2">未开始</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=3">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=4">已停用</a>
                </li>
                <li class="active">
                    <a href="javascript:;">${title!}</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="act" value="${act!}">
            <input type="hidden" name="result_id" value="${result_id!}">
            <input type="hidden" name="assess_id" value="${assess_id!}">
            <div class="assess_container">
                <div class="assess_content clearfix">
                    <div class="fl left_content">
                        <div class="fl_title">
                            <div>测评结果</div>
                        </div>
                        <div class="assess_preview">
                            <h4 class="preview_subject_title">测试测试测试测试测试</h4>
                            <div class="preview_top">
                                <#if ($assess_info->assess_judge_type == 1)
                                <div class="preview_title clearfix">
                                    <span class="fl">得分：89</span>
                                    <span class="fr">超过80%参与者</span>
                                </div>
                                </#if>
                                <div class="preview_pic">
                                    <div class="no_pic_style">
                                        <img src="http://${image_domain!}/image/admin/assess_no_pic.png" alt="">
                                        <span>
                                            图片
                                        </span>
                                    </div>
                                    <img src="" alt="" style="display: none;">
                                </div>
                                <div class="preview_depict">
                                    <div class="depict_content">
                                        结果描述
                                    </div>
                                </div>
                            </div>
                            <div class="preview_bottom clearfix">
                                <img src="/image/admin/assess_coupon.png" class="fl" alt="">
                                <div class="fr">
                                    <p style="font-weight: 800">活动<span style="color:#e0292c">奖品</span></p>
                                    <p style="color:#999;">点击领取奖品</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="right_content">
                        <div class="assess_set_info">
                            <div class="assess_set_title">结果设置</div>
                            <ul class="assess_set_ul">
                                <li class="clearfix">
                                    <#if ($assess_info->assess_judge_type == 1)
                                    <div class="li_left"><em>*</em> 根据得分判断：</div>
                                    <div class="li_right">
                                        <span class="tips">设置用户答题得分在指定区间时跳转到此结果</span>
                                    </div>
                                    <#else>
                                    <div class="li_left"><em>*</em> 根据选项判断：</div>
                                    <div class="li_right">
                                        <span class="tips">用户选择指定选项时跳转到此结果,（可在设置选项时绑定已添加测试结果）</span>
                                    </div>
                                    </#if>
                                </li>
                                <#if ($assess_info->assess_judge_type == 1)
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>结果分值区间：</div>
                                    <div class="li_right">
                                        <input type="text" name="min_point" value="${result_info['min_point']!}" <#if ($assess_info->pub_flag == 1) readonly </#if>> 至 <input type="text" name="max_point" value="${result_info['max_point']!}" <#if ($assess_info->pub_flag == 1) readonly </#if>>
                                        　<p class="tips">例：0分至30分；31分至50分</p>
                                    </div>
                                </li>
                                </#if>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>测试结果：</div>
                                    <div class="li_right">
                                        <input type="text" name="result" value="${result_info['result']!}">
                                        　<span class="tips">最多可填写10字</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left">图片（选填）：</div>
                                    <div class="li_right img_set" style="margin-top:10px">
                                        <input type="hidden" name="result_img_path" value="${result_info['result_img_path']!}">
                                        <div class="upload_img" style="display: none;">
                                            <img src="${result_info['result_img_path']!}" alt="${result_info['result_img_path']!}">
                                            <span class="choose_img">重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_image" style="display: block;">
                                        <span
                                            style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：690px
                                            * 440px</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>结果描述：</div>
                                    <div class="li_right" style="margin-top:10px;">
                                        <textarea name="result_desc" cols="54" rows="8" maxlength="300"
                                            style="line-height: 1;resize: none;border-color: #ccc;" >${result_info['result_desc']!}</textarea>
                                        <p class="tips" style="text-align:right;margin-right:87px;">最多可填写300字</p>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>测试奖励：</div>
                                    <div class="li_right">
                                        <div class="type_radio">
                                            <label><input type="radio" name="reward_type" value="0" <#if ($result_info['reward_type'] == 0) checked </#if> <#if ($assess_info->pub_flag == 1) disabled </#if>>不设置</label>
                                            <label><input type="radio" name="reward_type" value="1"
                                                    id="coupons" <#if ($result_info['reward_type'] == 1) checked </#if> <#if ($assess_info->pub_flag == 1) disabled </#if>>优惠券</label>
                                            <label><input type="radio" name="reward_type" value="2"
                                                    id="gifts" <#if ($result_info['reward_type'] == 2) checked </#if> <#if ($assess_info->pub_flag == 1) disabled </#if>>奖品</label>
                                            <label><input type="radio" name="reward_type" value="3"
                                                    id="scores" <#if ($result_info['reward_type'] == 3) checked </#if> <#if ($assess_info->pub_flag == 1) disabled </#if>>积分</label>
                                            <label><input type="radio" name="reward_type" value="4" id="yues" <#if ($result_info['reward_type'] == 4) checked </#if>>余额</label>
                                            <label><input type="radio" name="reward_type" value="5"
                                                    id="owns" <#if ($result_info['reward_type'] == 5) checked </#if> <#if ($assess_info->pub_flag == 1) disabled </#if>>自定义</label>
                                            <#if ($assess_info->pub_flag == 1)
                                            <input type="hidden" name="reward_type" value="${result_info['reward_type']!}">
                                            </#if>
                                        </div>
                                        <div class="gift_box">
                                            <div class="coupon_box">
                                                <span><em>*</em>优惠券：</span>
                                                <select name="award_coupon" id="" class="award_coupon">
                                                    <option value="0" surplus='0'>未选择</option>
                                                    <#list ($coupon_list as $coupon)
                                                    <option value="${coupon->id!}" surplus="${coupon->surplus!}" <#if ($result_info['reward_info']['coupon_id'] == $coupon->id)  selected </#if>>${coupon->act_name!}</option>
                                                    </#list>
                                                </select>
                                                <p class="tips">优惠券可用库存<span class="coupon_num"
                                                        style="color:#5a8bff">0</span>份</p>
                                            </div>
                                            <div class="goods_box">
                                                <span><em>*</em>赠送赠品：</span>
                                                <span class="choose_goods">
                                                    <img src="http://${image_domain!}/image/admin/icon_jia.png"
                                                        alt="" />选择商品
                                                    <input type="hidden" name="recommend_goods_id"
                                                        value="${result_info['reward_info']['prd_id']!}">
                                                </span>
                                                <div>
                                                    <span>赠品有效期:</span>
                                                    <input type="text" placeholder="默认保留7天"
                                                        onkeyup="value=value.replace(/[^\d]/g,'')" name="expire_time" value="${result_info['reward_info']['expire_time']!}"> 天
                                                    <p class="tips">中奖用户需在有效期内领取，过期后将无法领取</p>
                                                </div>
                                                <div class="goods_area" style="margin:0;padding-right:5px">
                                                    <#if ($result_info['goods'])
                                                    <table class="goods_table"
                                                        goods_array="${act->recommend_goods_id!}">
                                                        <thead>
                                                            <tr>
                                                                <th>商品名称</th>
                                                                <th>价格</th>
                                                                <th>库存</th>
                                                                <th>操作</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody class="tbody">
                                                            
                                                            <tr num="${result_info['goods']->prd_number!}">
                                                                <td>
                                                                    <div class="goods_info clearfix">
                                                                        <div class="goods_img"><img
                                                                                src="${result_info['goods']->goods_img!}" alt="" />
                                                                        </div>
                                                                        <div class="goods_name">
                                                                            ${result_info['goods']->goods_name!}
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                                <td>${result_info['goods']->shop_price!}</td>
                                                                <td>${result_info['goods']->goods_number!}</td>
                                                                <td><#if ($result_info['goods']->is_delete == 1)已删除<#elseif>($result_info['goods']->is_on_sale == 0)下架<#elseif>($result_info['goods']->goods_number==0)售罄<#else>上架</#if></td>
                                                                <td><a href="javascript:void(0)"
                                                                        goods_id="${result_info['goods']->goods_id!}"
                                                                        class="del">删除</a></td>
                                                            </tr>
                                                            
                                                        </tbody>
                                                    </table>
                                                    <#else>
                                                    <table class="goods_modal">
                                                        <thead>
                                                            <tr>
                                                                <th>商品名称</th>
                                                                <th>价格</th>
                                                                <th>库存</th>
                                                                <th>操作</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody class="tbody"></tbody>
                                                    </table>
                                                    </#if>
                                                </div>
                                            </div>
                                            <div class="score_box">
                                                <span><em>*</em>积分：</span>
                                                <input type="text" name="score" placeholder="请输入积分" value="${result_info['reward_info']['score']!}">
                                            </div>
                                            <div class="account_box">
                                                <span><em>*</em>余额：</span>
                                                <input type="text" name="account" placeholder="请输入金额" value="${result_info['reward_info']['account']!}">
                                            </div>
                                            <div class="other_box">
                                                <span><em>*</em>自定义：</span>
                                                <input type="text" name="other" placeholder="请输入自定义内容" value="${result_info['reward_info']['other']!}">
                                            </div>
                                            <div class="gift_num">
                                                <span><em>*</em>奖品份数：</span>
                                                <input type="text" name="reward_num" placeholder="" value="${result_info['reward_info']['reward_num']!}"> 份
                                                <p class='tips'>
                                                    奖品发完后则只显示测试结果内容不显示领取奖品按钮
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>领奖限制：</div>
                                    <div class="li_right">
                                        <label><input type="radio" name="reward_limit_type" value="0"
                                                <#if ($result_info['reward_limit_type'] == 0) checked </#if>>不设置</label>
                                        <label style="margin-left: 30px;"><input type="radio" name="reward_limit_type"
                                                value="1" <#if ($result_info['reward_limit_type'] == 1) checked </#if>>分享好友，领取奖品</label>
                                        <label style="margin-left: 30px;"><input type="radio" name="reward_limit_type"
                                                value="2" <#if ($result_info['reward_limit_type'] == 2) checked </#if>>填写信息，领取奖品</label>
                                        <div class="clearfix share_tips">
                                            <p class="tips">注：1、由于微信限制，无法获知用户是否已分享，此分享仅作为引导性提示</p>
                                            <p class="tips" style="margin-left:28px">2、会出现用户未分享也获得奖励的情况，请知悉</p>
                                        </div>
                                        <div class="clearfix condition_box">
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="1" <#if (in_array(1,$result_info['reward_limit_condition'])) checked </#if>>真实姓名</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="2" <#if (in_array(2,$result_info['reward_limit_condition'])) checked </#if>>手机号</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="3" <#if (in_array(3,$result_info['reward_limit_condition'])) checked </#if>>身份证号码</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="4" <#if (in_array(4,$result_info['reward_limit_condition'])) checked </#if>>性别</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="5" <#if (in_array(5,$result_info['reward_limit_condition'])) checked </#if>>生日</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="6" <#if (in_array(6,$result_info['reward_limit_condition'])) checked </#if>>婚姻状况</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="7" <#if (in_array(7,$result_info['reward_limit_condition'])) checked </#if>>教育程度</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="8" <#if (in_array(8,$result_info['reward_limit_condition'])) checked </#if>>所在行业</label></div>
                                            <div class="fl in_li"><label><input type="checkbox"
                                                        class="reward_limit_condition" name="reward_limit_condition[]"
                                                        value="9" <#if (in_array(9,$result_info['reward_limit_condition'])) checked </#if>>所在地</label></div>
                                        </div>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>结果页背景：</div>
                                    <div class="li_right">
                                        <label><input type="radio" name="bg_type" value="0" <#if ($result_info['bg_type'] == 0) checked </#if>>默认背景</label>
                                        <label style="margin-left:30px;"><input type="radio" name="bg_type"
                                                value="1" <#if ($result_info['bg_type'] == 1) checked </#if>>自定义</label>
                                        <div class="img_set bg_type">
                                            <input type="hidden" name="bg_img_path" value="${result_info['bg_img_path']!}">
                                            <div class="upload_img" >
                                                <img src="${result_info['bg_img_path']!}" alt="${result_info['bg_img_path']!}">
                                                <span class="choose_img">重新选择</span>
                                            </div>
                                            <input type="button" value="" class="add_image" style="display: block;">
                                            <span
                                                style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：750px
                                                * 1334px</span>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer" style="display: block;">
            <a class="save" href="javascript:void(0);">保存</a>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script>
    var assess_judge_type = "${assess_info->assess_judge_type ?? 0!}";
    $('.save').click(function() {
        if (assess_judge_type == 1) {
            if ($('[name="min_point"]').val() == '') {
                util.mobile_alert('请填写最小分值');
                $('[name="min_point"]').focus();
                return;
            }

            if ($('[name="max_point"]').val() == '') {
                util.mobile_alert('请填写最大分值');
                $('[name="min_point"]').focus();
                return;
            }

            if (parseInt($('[name="min_point"]').val()) >= parseInt($('[name="max_point"]').val())) {
                util.mobile_alert('最大分值不能小于最小分值');
                return;
            }
        }

        if ($('[name="result"]').val() == '') {
            util.mobile_alert('请填写测试结果');
            $('[name="result"]').focus();
            return;
        }

        if ($('[name="result_desc"]').val() == '') {
            util.mobile_alert('请填写测试结果描述');
            $('[name="result_desc"]').focus();
            return;
        }

        var reward_type = $('[name="reward_type"]:checked').val();
        if (reward_type > 0) {
            if (reward_type == 1 && $('[name="award_coupon"]').val() <= 0) {
                util.mobile_alert('请选择优惠券'); 
                return;
            }

            if (reward_type == 2 && !$('[name="recommend_goods_id"]').val()) {
                util.mobile_alert('请选择商品'); 
                return;
            }

            if (reward_type == 3 && !$('[name="score"]').val()) {
                util.mobile_alert('请填写积分'); 
                $('[name="score"]').focus();
                return;
            }

            if (reward_type == 4 && !$('[name="account"]').val()) {
                util.mobile_alert('请填写余额');
                $('[name="account"]').focus(); 
                return;
            }

            if (reward_type == 5 && !$('[name="other"]').val()) {
                util.mobile_alert('请填写自定义条件'); 
                $('[name="other"]').focus();
                return;
            }
            if (!$('[name="reward_num"]').val()) {
                util.mobile_alert('请填写奖品份数'); 
                $('[name="reward_num"]').focus();
                return;
            }
            if (reward_type == 1 && $('[name="reward_num"]').val() > $('.coupon_num').text()){
                util.mobile_alert('奖品份数不得超过优惠券数量'); 
                $('[name="reward_num"]').focus();
                return;
            }

        }

        if ($('[name="reward_limit_type"]:checked').val() == 2) {
            var v= $('.reward_limit_condition:checked').length;
            if (!$('.reward_limit_condition:checked').length) {
                util.mobile_alert('请选择领奖限制所需填写的信息'); 
                return;
            }
        }

        if ($('[name="bg_type"]:checked').val() == 1) {
            if (!$('[name="bg_img_path"]').val()) {
                util.mobile_alert('请选择自定义背景图'); 
                return;
            }
        }

        var assess_id = $('input[name="assess_id"]').val();
        var param = {};
        param = $("#form1").serializeArray();
        util.ajax_json('/admin/market/assess/result/save',function(d) {
            if (d.error == 0) {
                util.mobile_alert(d.message);
                setTimeout(function () {
                    location.href='/admin/market/assess/result/list?assess_id='+assess_id;
                }, 1000);
            }else{
                util.mobile_alert(d.message);
            }
        },param);
    });
    inputTest();
    setImg();
    function picker() {
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $('[name="reward_type"]').click(function () {
        inputTest();
    })
    $('[name="reward_limit_type"]').click(function () {
        inputTest();
    })
    $('[name="bg_type"]').click(function () {
        inputTest();
    })
    $('[name="result"]').keyup(function () {
        inputTest();
    })
    $('[name="result_desc"]').keyup(function () {
        inputTest();
    })

    $(".add_image,.choose_img").click(function () {
        var that = $(this);
        let width = '' , height = '';
        let inputName = $(this).parents('.img_set').children('input').eq(0).attr('name');
        if(inputName == "result_img_path"){
            width = 690;
            height = 440;
        } else if (inputName == "bg_img_path"){
            width = 750;
            height = 1334;
        }
        $.jImageManager({
            img_width: width,
            img_height: height,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                that.parents('.img_set').find('.upload_img').show()
                that.parents('.img_set').find('.upload_img img').attr('src', path);
                that.parents('.img_set').find('input[type="hidden"]').val(path);
                that.hide();
                inputTest()
            }
        });
        hasSaved = false;
    });

    $(document).on('mouseenter', '.upload_img', function () {
        $(this).find('span').show()
    });
    $(document).on('mouseleave', '.upload_img', function () {
        $(this).find('span').hide()
    });

    
    //选择商品
    $('.choose_goods').on('click', function () {
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            var checkedId1 = $('input[name="recommend_goods_id"]').val();
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['1085px', '430px']
                , content: '/admin/public/purchase/goods/list?is_spec_goods=1&is_single=1&record_select_value=' + checkedId1 //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success: function (layero, index) {
                    var goods = $('input[name="recommend_goods_id"]').val();
                    var body = layer.getChildFrame('body', index);
                    if (goods != '') {
                        if (isNaN(goods)) {
                            var goods_array = goods.split(',');
                            body.contents().find("tr").each(function () {
                                if ($.inArray($(this).attr("goods_id"), goods_array) > -1) {
                                    $(this).attr('data-back', 'false').addClass('goods_tr_choose');
                                }
                            });
                        }
                        else {
                            body.contents().find("tr").each(function () {
                                if ($(this).attr("goods_id") == goods) {
                                    $(this).attr('data-back', 'false').addClass('goods_tr_choose');
                                }
                            });
                        }
                    }
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var recommend_goods_id = $("input[name='recommend_goods_id']").val();
                    var iframe = layer.getChildFrame('body', index);
                    var goods = [];
                    var body = layer.getChildFrame('body', index);
                    var checkedId = iframe.find('#record_select_value').val();
                    $('input[name="recommend_goods_id"]').val(checkedId);
                    console.log(checkedId);
                    util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                        console.log(response);
                        var list = response.content;
                        var html = '';
                        for (var i in list) {
                            list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                            html += '<tr>' +
                                '        <td>' +
                                '            <div class="goods_img">' +
                                '                <img src="' + list[i].goods_img + '" />' +
                                '            </div>' +
                                '            <div class="goods_info clearfix" num="' + list[i].goods_number + '" prd_id="' + list[i].prd_id + '">' +
                                '                <div class="goods_name">' + list[i].goods_name + list[i].prd_desc + '</div>' +
                                '            </div>' + '<td>￥' + list[i].shop_price + '</td>' +
                                '<td>' + list[i].goods_number + '</td>' +
                                '<td><a href="##" item="' + list[i].prd_id + '" class="change_goods_del">删除</a></td>'
                            '        </td>';
                            html += '</tr>';
                        }
                        $('.goods_area').css('display', 'block');
                        $('.goods_modal').css('display', 'block');
                        $('.goods_modal .tbody').html(html);
                        $('.goods_table').css('display', 'block');
                        $('.goods_table .tbody').html(html);
                        check_goods_area_height()
                        layer.close(index);

                    }, { select_id: checkedId, is_spec_goods: 1 });
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    function check_goods_area_height() {
        let goods_modal = $('.goods_modal').outerHeight();
        let goods_table = $('.goods_table').outerHeight();
        if (goods_table > 300 || goods_modal > 300) {
            $('.goods_area').css({
                'height': '300px',
                'overflow-y': 'scroll',
            })
        } else {
            $('.goods_area').css({
                'height': 'auto',
                'overflow-y': 'auto',
            })
        }
    }
    function inputTest() {
        if ($('[name="reward_type"]:checked').val() == 1) {
            $('.gift_box > div').hide();
            $('.coupon_box,.gift_num').show();
            $('.preview_bottom').show().children('img').attr('src', '/image/admin/assess_coupon.png');
        } else if ($('[name="reward_type"]:checked').val() == 2) {
            $('.gift_box > div').hide();
            $('.goods_box,.gift_num').show();
        } else if ($('[name="reward_type"]:checked').val() == 3) {
            $('.gift_box > div').hide();
            $('.score_box,.gift_num').show();
            $('.preview_bottom').show().children('img').attr('src', '/image/admin/assess_score.png');
        } else if ($('[name="reward_type"]:checked').val() == 4) {
            $('.gift_box > div').hide();
            $('.account_box,.gift_num').show();
            $('.preview_bottom').show().children('img').attr('src', '/image/admin/assess_account.png');
        } else if ($('[name="reward_type"]:checked').val() == 5) {
            $('.gift_box > div').hide();
            $('.other_box,.gift_num').show();
            $('.preview_bottom').show().children('img').attr('src', '/image/admin/assess_custon.png');
        } else {
            $('.gift_box > div').hide();
            $('.preview_bottom').hide();
        }

        if ($('[name="reward_limit_type"]:checked').val() == 2) {
            $('.condition_box').show()
        } else {
            $('.condition_box').hide()
        }
        if ($('[name="reward_limit_type"]:checked').val() == 1) {
            $('.share_tips').show()
        } else {
            $('.share_tips').hide()
        }

        if ($('[name="bg_type"]:checked').val() == 1) {
            $('.bg_type').show()
        } else {
            $('.bg_type').hide()
        }

        if ($('[name="result"]').val() != '') {
            $('.preview_subject_title').text($('[name="result"]').val());
        } else {
            $('.preview_subject_title').text('测评结果');
        }

        if ($('[name="result_desc"]').val() != '') {
            $('.depict_content').text($('[name="result_desc"]').val());
        } else {
            $('.depict_content').text('结果描述');
        }

        if ($('[name="bg_type"]:checked').val() == 0) {
            $('.assess_preview').css('background', 'none');
        } else {
            if ($('[name="bg_img_path"]').val() != '')
                $('.assess_preview').css({
                    'background-image': 'url(' + $('[name="bg_img_path"]').val() + ')',
                    'background-size': '100% 100%',
                    'background-repeat': 'no-repeat'
                })
        }

        if ($('[name="result_img_path"]').val() != '') {
            $('.preview_pic > img').attr('src', $('[name="result_img_path"]').val()).show();
            $('.preview_pic > .no_pic_style').hide();
        }

        $('.coupon_num').text($('.award_coupon').children('option:selected').attr('surplus'));
        
    }

    $('.award_coupon').change(function () {
        $('.coupon_num').text($(this).children('option:selected').attr('surplus'));
    });

    function setImg() {
        $('.img_set').each(function() {
            if ($(this).find('input[type="hidden"]').val()) {
                $(this).find('.upload_img').show();
                $(this).find('.add_image').hide();
            }
        });
    }
</script>
<script type="text/javascript">
    getPowerInfo('main_config','assess','sub_4','测评',0);
</script>