<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_import_list.css?v=1.0.0" type="text/css"/>
<style>
    table{
        margin-top: 20px;
        border: 1px solid #eee;
    }
    thead{
        background-color: #f5f5f5;
    }
    th,td{
        text-align: center;
        padding: 8px 10px;
    }
    .user_import_table td{
        border: 1px solid #eee;
    }
    .activation_container,.user_import_popup{
        display: none;
        padding: 16px;
    }
    .explain{
        padding: 16px;
        background-color: #f5f5f5;
    }
    .explain p{
        color: #666;
    }
    .explain p+p{
        color: #666;
        padding-left: 42px;
    }
    .activation_explain{
        margin-top: 16px;
    }
    .activation_explain span{
        margin-right: 10px;
    }
    .activation_explain textarea{
        vertical-align: text-top;
        overflow:hidden;
        width: calc(100% - 70px);
        height: 70px;
        margin-bottom: 6px;
        resize: none;
        border-color: #dbdcde;
    }
    .explain_info{
        padding-left: 80px;
    }
    .explain_info > span:first-of-type{
        position: relative;
        color: #5a8bff;
        cursor: pointer;
    }
    .explain_info > span:last-of-type{
        color: #999;
    }
    .activation_award{
        margin-top: 20px;
    }
    .activation_award span.fl{
        margin-right: 10px;
        margin-top: 5px;
    }
    .award_box{
        display: inline-block;
        width: calc(100% - 70px);
        overflow:hidden;
    }
    input[type="checkbox"]{
        vertical-align: text-top;
    }
    input[type="text"]{
        border-radius: 5px;
        height: 30px;
        width: 100px;
        border: 1px solid #eee;
        margin-right: 5px;
    }
    .coupon_box{
        padding-left: 70px;
        margin-top: 10px;
    }
    .card_add {
        background: #fff;
        border: 1px solid #e4e4e4;
        text-align: center;
        padding: 13px 0;
        cursor: pointer;
        float: none;
        width: 100px;
        height: 90px;
    }
    .card_add img {
        margin-top: 10px;
    }
    .card_add p{
        color: #999;
        font-size: 12px;
        margin: 8px 0 0 0;
    }
    .coupon_div {
        display: none;
    }
    .hide {
        display: none!important;
    }
    .coupon_list {
        float: left;
        width: 40%;
        border: 1px solid #fbb;
        -webkit-border-radius: 110px;
        -moz-border-radius: 10px;
        border-radius: 10px;
        text-align: center;
        overflow: hidden;
    }
    .coupon_del {
        position: absolute;
        top: -5px;
        right: -5px;
        cursor: pointer;
    }
    .coupon_list_top {
        margin-top: 10px;
        color: #f66;
        font-size: 14px;
    }
    .coupon_list_top span {
        font-size: 20px;
        font-weight: bold;
        display: inline-block;
    }
    .coupon_list_center {
        height: 40px;
        color: #f66;
        font-size: 12px;
    }
    .coupon_center_number {
        color: #fbb;
    }
    .coupon_list_bottom {
        font-size: 12px;
        background: #f66 url(/image/admin/coupon_border.png) repeat-x top;
        -webkit-background-size: 12px;
        background-size: 12px;
        height: 24px;
        line-height: 30px;
        color: #fff;
    }
    .coupon_div .coupon_list {
        width: 100px;
        margin-right: 20px;
        background: white;
        margin-bottom: 10px;
        position: relative;
        overflow: inherit;
    }
    .card_add{
        display:inline-block;
        vertical-align: top;
        height: 98px;
        margin-bottom: 10px;
    }
    .coupon_div{
        display:block;
        width: 600px;
        font-size: 0;
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
    }
    .tem_right{
        float: left;
        min-width: 245px;
    }
    .coupon_content{
        background-color:#f5f5f5;
        margin-left:16px;
        padding:16px;
        margin-top:10px;
        border-radius:6px;
    }
    .import_popup_title{
        margin-bottom: 14px;
    }
    .card_box,.file_upload{
        background-color: #f5f5f5;
        border-radius: 6px;
        margin-bottom: 14px;
        padding: 10px 16px 16px;
    }
    .choose_card > div{
        line-height: 40px;
    }
    .choose_card > div+div{
        padding-left: 144px;
    }
    .choose_card > div:last-of-type{
        line-height: 24px;
        color: #999;
    }
    .choose_card select{
        height: 30px;
        width: 140px;
        padding-left: 8px;
        color: #666;
        border: 1px solid #ddd;
    }
    .card_box > div:last-of-type{
        margin-top: 10px;
    }
    .file_box{
        display: inline-block;
        border: 1px solid #ddd;
        width: 250px;
        height: 30px;
        background-color: #fff;
        vertical-align: middle;
    }
    .file_box span.fl{
        width: 168px;
        line-height: 28px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .file_box span.fr{
        text-align: center;
        line-height: 28px;
        width: 80px;
        background-color: #f5f5f5;
        cursor: pointer;
        border-left: 1px solid #ddd;
    }
    .file_upload_explain{
        margin-top: 16px;
        color: #666;
    }
    .file_upload_explain > p+p{
        padding-left:70px;
    }
    input[type="file"]{
        width: 0;
        opacity: 0;
        height: 0;
    }
    .no_data .no_data_style{
        margin-top:10px;
    }
    .user_import_activate{
        position: absolute;
        top: -168px;
        left: 58px;
        z-index: 2;
        display: none;
    }

    .order-info input{
        border-radius: 0px;
        width: 150px;
        height: 30px;
        padding-left: 12px;
        border: 1px solid #ccc;
    }
    .order-info-li{
        padding: 8px 0;
    }
    .order-info-li .fl{
        width: 280px;
    }
    .order-info-li .fl span{
        display: inline-block;
        width: 80px;
        line-height: 30px;
        text-align: right;
        margin-right: 25px;
        color: #333;
        vertical-align: top;
    }
    .btn-choose{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        margin-left: 15px;
    }
    a:active,a:hover,a:focus{
        text-decoration: none;
        color: #333;
    }
    .hover_show:before{
        top: 175px !important;
    }
    .TempContainer{
        z-index: 19891017 !important;
    }
</style>
<div class="title">
    <span>会员管理 / </span><span style="color: #666;">${title!}</span>
</div>
<div class="main_container">
    <div class="user_import_container">
        <div class="user_title clearfix">
            <div class="fl">说明：会员导入适用于会员迁移的场景，系统会导入增量会员并更新未激活会员的信息，不会更新已激活会员的信息</div>
            <div class="fr">
                <span class="activation">设置激活通知</span>
                <span class="user_import">会员导入</span>
            </div>
        </div>
        <div class="order-info">
            <form action="" method="post" id="form_choose">
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>批次号</span>
                            <input type="text" name="id" value="${id!}" placeholder='请输入批次号' />
                        </div>
                        <div class="fl" style="width: auto;">
                            <span>{{ trans("admin/user_list.operate_time")!}</span>
                            <input  style="width: 150px; height: 30px;" type="text" name="start_time" value="${start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" />
                            &nbsp;&nbsp;{{ trans("admin/user_list.zhi")!}&nbsp;&nbsp;
                            <input  style="width: 150px; height: 30px;" type="text" name="end_time" value="${end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off" />
                        </div>
                        <!-- <div class="fl">
                            <span>会员卡</span>
                            <input type="text" name="invite_user" value="${data->invite_user!}" placeholder='会员卡名称' />
                        </div> -->
                        <a class="btn-choose" style="display: inline-block;line-height: 30px;padding-left: 28px;">{{ trans("admin/user_list.choose")!}</a>
                    </li>
                </ul>
            </form>
        </div>
        <table class="user_import_table" width="100%;">
            <thead>
                <tr>
                    <th>批次号</th>
                    <th>操作时间</th>
                    <th>会员卡</th>
                    <th>成功数量</th>
                    <th>失败数量</th>
                    <th>激活数量</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            <#list  ($list as $item)
                <tr>
                    <td>${item->id!}</td>
                    <td>${item->add_time!}</td>
                    <td>
                        <#if  ($item->card_id)
                            <#list  (explode(',', $item->card_id) as $cardId)
                                <a href="/admin/user/member/edit?id=${cardId!}&card_type=${cardType[$cardId] ?? 0!}&top_index=5">${cardName[$cardId] ?? ''!}</a> <br/>
                            </#list>
                        </#if>
                    </td>
                    <td>${item->success_num!}</td>
                    <td>${item->total_num - $item->success_num!}</td>
                    <td><#if  ($item->activate_num > 0) <a href="/admin/user/manage/list?top_index=5&batch_id=${item->id!}">${item->activate_num!}</a><#else> 0 </#if></td>
                    <td>
                        <#if  ($item->total_num - $item->success_num > 0)
                        <a href="/admin/user/import/export?batch_id=${item->id!}">下载失败数据</a>
                        <br>
                        </#if>
                        <#if  ($item->activate_num > 0)
                        <a href="/admin/user/import/exportActivate?batch_id=${item->id!}">下载激活数据</a>
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="clearfix no_data">
            <#include "/admin/jump_page_admin.ftl">
        </div>
    </div>
</div>

<#include "/admin/footer.ftl">
<div class="activation_container">
    <form action="" method="POST" id="form1">
        {{csrf_field()!}
        <div class="explain">
            <p>说明：1、会员导入系统后，需要在移动端授权手机号以对应导入的会员信息。</p>
            <p>2、为提升会员的激活率，可设置一定的授权奖励。</p>
            <p>3、设置完成后请到“基础配置”-“个人中心配置”中开启“会员激活开关”。</p>
        </div>
        <div class="activation_explain clearfix">
            <span class="fl">激活说明:</span>
            <textarea class="fl" name="explain" cols="30" rows="10" value="" maxlength="300">${notice_info['explain'] ? $notice_info['explain'] : '感谢您一直以来对本店铺的关注，您之前使用的全部服务已经迁移至本小程序中，为方便您找回此前的会员权益，请同意授权您的手机号，完成会员激活。'!}</textarea>
            <a href="javascript:;" class="show_eg" style="margin-left: 70px;">查看示例
                <div class="hover_show" style="top: -170px">
                    <img src="http://${image_domain!}/image/admin/new_preview_image/user_vip.jpg" alt="">
                </div>
            </a>
        </div>
        <div class="activation_award">
            <span class="fl">激活奖励:</span>
            <div class="award_box">
                <input type="checkbox" name="score_status" <#if  ($notice_info['score'] > 0) checked </#if> > <span>积分</span>
                <input type="text" name="score" value="${notice_info['score'] > 0 ? $notice_info['score'] : ''!}">积分
            </div>
            <div class="coupon_box">
                <input type="checkbox" name="coupon_status" <#if  (!empty($notice_info['mrking_voucher_id'])) checked </#if> >优惠券
                <div class="coupon_content clearfix">
                    <input type="hidden" name="coupon_ids" value="${notice_info['mrking_voucher_id']!}">
                    <div class="tem_right">
                        <div class="coupon_div clearfix" coupon_json="" <#if ($notice_info['coupon_list']) style="display: block" </#if>>
                            <#list ($notice_info['coupon_list'] ?? [] as $k => $cou)
                                <div class="coupon_list">
                                    <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del">
                                    <input type="hidden" coupon_id="${cou->id!}" act_code="${cou->act_code!}" denomination="${cou->denomination!}" class="coupon_info">
                                    <div class="coupon_list_top">
                                        <#if ($cou->act_code == 'voucher')
                                            ¥<span>${cou->denomination!}</span>
                                        <#elseif>($cou->act_code == 'discount')
                                            打<span>${cou->denomination!}</span>折
                                        </#if>
                                    </div>
                                    <div class="coupon_list_center">
                                        <#if ($cou->use_consume_restrict == 0)
                                            <div class="coupon_center_limit">不限制</div>
                                        <#else>
                                            <div class="coupon_center_limit">满${cou->least_consume!}使用</div>
                                        </#if>
                                        <div class="coupon_center_number">剩余<span>${cou->surplus!}</span>张</div>
                                    </div>
                                    <div class="coupon_list_bottom">
                                        领取
                                    </div>
                                </div>
                            </#list>
                            <div class="card_add card_add_click">
                            <img src="http://mpdevimg.weipubao.cn/image/admin/shop_beautify/add_decorete.png" alt="">
                            <p>添加优惠券</p>
                            </div>  
                        </div>
                        <p style="color:#bbb">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

<div class="coupon_list_clone hide">
    <div class="coupon_list">
        <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del">
        <input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info">
        <div class="coupon_list_top">
            ¥<span>××</span>
        </div>
        <div class="coupon_list_center">
            <div class="coupon_center_limit">满××使用</div>
            <div class="coupon_center_number">剩余<span>××</span>张</div>
        </div>
        <div class="coupon_list_bottom">
            领取
        </div>
    </div>
</div>

<div class="user_import_popup">
    <form action="" method="POST" id="form2">
        {{csrf_field()!}
        <p class="import_popup_title">第一步：模板下载</p>
        <div class="card_box">
            <div class="choose_card">
                <input type="hidden" name="card_id">
                <div>
                    <span>发放会员卡（可选项）</span>
                    <span>普通会员卡</span>
                    <select name="" class="card">
                        <option value="0">请选择会员卡</option>
                        <#list ($card_list as $item)
                            <#if ($item->card_type == 0)
                                <option value="${item->id!}" card_id="${item->id!}" card_type="${item->card_type!}" class="option">${item->card_name!}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
                <div>
                    <span>限次会员卡</span>
                    <select name="" class="card">
                        <option value="0">请选择会员卡</option>
                        <#list ($card_list as $item)
                            <#if ($item->card_type == 1)
                                <option value="${item->id!}" card_id="${item->id!}" card_type="${item->card_type!}" class="option">${item->card_name!}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
                <div>
                    <span>等级会员卡</span>
                    <select name="" class="card">
                        <option value="0">请选择会员卡</option>
                        <#list ($card_list as $item)
                            <#if ($item->card_type == 2)
                                <option value="${item->id!}" card_id="${item->id!}" card_type="${item->card_type!}" class="option">${item->card_name!}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
                <div>
                    <span>导入成功后，将自动给被导入的用户发放会员卡</span>
                </div>
            </div>
            <div>
                <span>下载模板：</span>
                <a href="{{ main_url('doc/用户导入模板.xlsx')!}">会员导入文件模板</a>
            </div>
        </div>
        <p class="import_popup_title">第二步：数据导入</p>
        <div class="file_upload">
            <div>
                <span style="vertical-align: middle">上传文件：</span>
                <div class="file_box clearfix">
                    <span class="fl filetext"></span>
                    <span class="fr" onclick="document.getElementById('file').click()">浏览...</span>
                </div>
                <input type="file" name="user_import" id="file" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[0].innerText=this.value">
            </div>
            <div class="file_upload_explain">
                <p>导入规则：1、文件当前仅支持excel格式。</p>
                <p>2、导入以手机号为唯一标识，系统中已有手机号无法导入，请严格按照模板文件格式填写用户信息。</p>
            </div>
        </div>
    </form>
</div>
<#include ('admin.preview_common')
<script>
    $(".btn-choose").click(function () {
        $("#form_choose").submit();
    })
    $('.activation').click(function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['设置激活通知', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['750px','600px']
                , content: $('.activation_container') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['保存']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index) {
                    if ($('[name="explain"]').val() == '') {
                        util.mobile_alert('请设置激活说明');
                        return false;
                    }
                    if(!$('[name="score_status"]').is(':checked') && !$('[name="coupon_status"]').is(':checked')){
                        util.mobile_alert('请至少选择一种奖励方式');
                        return false;
                    }
                    if($('[name="score_status"]').is(':checked') && ($('[name="score"]').val() == '' ||
                        isNaN(parseInt($('[name="score"]').val())) || parseInt($('[name="score"]').val()) <= 0)) {
                        util.mobile_alert('请输入正确的奖励积分');
                        return false;
                    }
                    if($('[name="coupon_status"]').is(':checked') && $('[name="coupon_ids"]').val() == ''){
                        util.mobile_alert('请选择奖励优惠券');
                        return false;
                    }
                    if(!$('[name="score_status"]').is(':checked')){
                        $('[name="score"]').val('');
                    }
                    if(!$('[name="coupon_status"]').is(':checked')){
                        $('[name="coupon_ids"]').val('');
                    }
                    let data = $('#form1').serialize();
                    util.ajax_json('/admin/user/import/setnotice',function(response){
                        if (response.error == 0) {
                            layer.msg('设置激活通知成功',{time:2000});
                            setTimeout(() => {
                                layer.close(index);
                                location.reload();
                            }, 2000);
                        } else {
                            util.mobile_alert(util.message);
                        }
                    }, data);
                    $('.activation_container').hide();
                    layer.close(index);
                }
                ,cancel:function(){
                    $('.activation_container').hide();
                }
            });
        });
    })
    
    $('.coupon_div').on('click','.coupon_del',function () {
        var coupon_ids = $("input[name='coupon_ids']").val().split(',');
        for(var i in coupon_ids){
            if(coupon_ids[i] == $(this).next().attr('coupon_id')){
                coupon_ids.splice(i,1);
            }
        }
        $('input[name="coupon_ids"]').val(coupon_ids.join(','));
        $(this).parent().remove();
        if(coupon_ids.length<5){
            $('.card_add').show();
            $(".changetext").text("最多可以添加5张优惠券，已过期和已停用的优惠券不能添加");
        }
        hasSaved = false;
    });
    
    $(".coupon_div").on('click','.card_add_click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择优惠券', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['420px','420px']
                , content: '/admin/frame/coupon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var iframe = layer.getChildFrame('body', index);
                    if($('.coupon_div').find('.coupon_list').size() > 0){
                        $('.coupon_div').find('.coupon_list').each(function () {
                            var _this = $(this);
                            iframe.find('.coupon_list').each(function () {
                                if($(this).find('.coupon_info').attr('coupon_id') == _this.find('.coupon_info').attr('coupon_id')){
                                    $(this).addClass('card_list_active');
                                }
                            });
                        });
                    }
                    iframe.find('.coupon_list').click(function () {
                        if($(this).hasClass('card_list_active')){
                            $(this).removeClass('card_list_active');
                        }else{
                            $(this).addClass('card_list_active');
                        }
                    });
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var list_active = iframe.find('.card_list_active');
                    if($(list_active).size() == 0){
                        util.mobile_alert('请选择优惠券');
                        return;
                    }
                    if($(list_active).size() > 5){
                        util.mobile_alert('最多只能选择5张优惠券哦~');
                        return;
                    }
                    // var coupon_arr = [];
                    var coupon_ids = [];
                    var card_add = $('.card_add').clone();
                    $('.coupon_div').html('');
                    // if($("input[name='coupon_ids']").val()){
                    //     coupon_ids = $("input[name='coupon_ids']").val().split(',');
                    // }
                    $(list_active).each(function (i) {
                        var coupon_clone = $('.coupon_list_clone').find('.coupon_list').clone();
                        coupon_clone.find('.coupon_info').attr('act_code',$(this).find('.coupon_info').attr('act_code'));
                        coupon_clone.find('.coupon_info').attr('denomination',$(this).find('.coupon_info').attr('denomination'));
                        coupon_clone.find('.coupon_info').attr('coupon_id', $(this).find('.coupon_info').attr('coupon_id'));
                        if($(this).find('.coupon_info').attr('act_code') == "discount"){
                            coupon_clone.find('.coupon_list_top').html('<span>' + $(this).find('.coupon_info').attr('denomination') + '</span>折');
                        }
                        if($(this).find('.coupon_info').attr('act_code') == "voucher"){
                            coupon_clone.find('.coupon_list_top').html('￥<span>' + $(this).find('.coupon_info').attr('denomination') + '</span>');
                        }
                        coupon_clone.find('.coupon_center_limit').text($(this).find('.coupon_center_limit').text().replace(/\s+/g,""));
                        coupon_clone.find('.coupon_center_number').text($(this).find('.coupon_center_number').text().replace(/\s+/g,""));
                        $('.coupon_div').show();
                        $('.coupon_div').prepend(coupon_clone).append(card_add);
                        coupon_ids.push($(this).find('.coupon_info').attr('coupon_id'));
                        $('input[name="coupon_ids"]').val(coupon_ids);
                    });
                    if($('.coupon_div').find('.coupon_list').length==5){
                        $(".card_add_click").hide();
                    }else{
                        $(".card_add_click").show();
                    }
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    
    $(".user_import").click(function(){
        layui.use('layer', function() {
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['会员导入', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['714px','auto']
                , content: $('.user_import_popup')
                , btn: ['导入']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    var card_id = []
                    $('.card').each(function(){
                        if($(this).val() != 0){
                            card_id.push($(this).val());
                        }
                    })
                    $('[name="card_id"]').val(card_id);
                    var formData = new FormData();
                    formData.append('user_import', $('#file')[0].files[0]);
                    formData.append('card_id', $('[name="card_id"]').val());

                    $.ajaxSetup({
                        contentType : false,
                        processData : false
                    });
                    util.ajax_json('/admin/user/import/insert', function (response) {
                        if (response.error == 0) {
                            util.mobile_alert('导入成功');
                            layer.close(index);
                            location.reload();
                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, formData)
                    $('.user_import_popup').hide()
                    layer.close(index);
                }
                ,cancel:function(){
                    $('.user_import_popup').hide()
                }
            });
        });
    })

    if($('.coupon_div').find('.coupon_list').length >4){
        $('.card_add').hide();
    }
    $('.explain_info').find('span:eq(0)').on('mouseenter',function(){
        $(this).find('.user_import_activate').show()
    }).on('mouseleave',function(){
        $(this).find('.user_import_activate').hide()
    })
</script>