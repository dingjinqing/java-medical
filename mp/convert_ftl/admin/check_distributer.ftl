<#include "/admin/header.ftl">

<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.8" type="text/css" />
<style type="text/css">
    .search_res ul .re_li{
        width: 275px;
    }

    .search_res ul .re_li span{
        width: 65px;
        margin-right: 10px;
    }
    .btn_found{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        height: 30px;
        line-height: 25px;
        width: 70px;
    }
    .btn_found:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .btn_found:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .qm_text {
        width: 480px;
        height: 310px;
        background-color: white;
        border: 1px solid #ddd;
        margin-left: 10px;
        word-wrap: break-word;
        word-break: break-all;
        margin-top: 12px;
    }
    .text_warn {
        height: 30px;
        line-height: 30px;
        color: rgb(102, 102, 102);
        font-size: 12px;
        background-color: rgb(255, 247, 235);
        padding: 0px 20px;
        border-width: 1px;
        border-style: solid;
        border-color: rgb(255, 213, 163);
        border-image: initial;
        width: 96%;
        margin: 10px 0 0 10px;
    }
    .audit_fail_reason {
        text-align: left !important;
        line-height: 20px;
        overflow: hidden;
        word-wrap: break-word;
        word-break: break-all;
    }
    .audit_fail_reason .zk_sq {
        display: inline-block;
        cursor: pointer;
        color:#5a8bff;
    }
    thead td:first-of-type{
        text-align: left;
    }
    thead td:first-of-type span+span{
        margin-left: 30px;
    }
    .examine span{
        display: block;
        border: 1px solid #ddd;
        margin: 0 20%;
        cursor: pointer;
        padding: 2px 0;
    }
    .examine span:first-of-type{
        background-color: #edf3fe;
        color: #5a8bff;
        border: 1px solid #cbd8fd;
    }
    .examine span:last-of-type{
        background-color: #f5f5f5;
        color: #666;
        border: 1px solid #eee;
    }
    .examine span+span{
        margin-top: 15px;
    }
    .examine_info{
        display: block;
        width: 100%;
    }
    .examine_info li{
        text-align: left;
        margin-right: 30px;
        line-height: 30px;
    }
    table+table{
        margin-top: 15px;
    }
    .check_list > table > tbody > tr > td:first-of-type{
        padding: 0;
    }
    .distributer_info  tbody > tr:first-of-type > td{
        border-top: none;
    }
    .distributer_info  tbody > tr:last-of-type > td{
        border-bottom: none;
    }
    .distributer_info  tbody > tr > td:first-of-type{
        border-left: none;
    }
    .distributer_info  tbody > tr > td:last-of-type{
        border-right: none;
    }
    .distributer_info  tbody > tr:nth-last-child(1) td:last-of-type{
        text-align: left;
    }
    .distributer_info  tbody > tr:nth-last-child(2) td:last-of-type{
        text-align: left;
    }
    .check_nav li{
        cursor: pointer;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;">分销员审核</span>
</div>
<div class="reserve-container">

        {{--<input type="hidden" name="act">--!}
        <div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>
    <form action="/admin/market/distribution/distributer/check?top_index=4" id="form1" method="post">
        {{csrf_field()!}
        <div class="search_reason search_res">
        <ul>
            <li class="clearfix">
                <div class="re_li">
                    <span>手机号</span>
                    <input type="text" name="mobile" value="${post_data['mobile']!}" placeholder="请输入手机号码">
                </div>
                <div class="re_li">
                    <span>昵称</span>
                    <input type="text" name="username" value="${post_data['username']!}" placeholder="请输入用户昵称">
                </div>
                <div class="re_li" style="width: 480px;">
                    <span>申请时间</span>
                    <input type="text" name="start_time" value="${post_data['start_time']!}" onclick="picker();" >
                    至
                    <input type="text" name="end_time" value="${post_data['end_time']!}" onclick="picker();" >
                </div>
                <button type="button" class="btn_found">查询</button>
            </li>
        </ul>
    </div>
        <div class="check_info">
            <ul class="check_nav clearfix">
                <li <#if ($post_data['status'] == 0) class="activesa"</#if> onclick="selectStatus(0)">待审核分销员</li>
                <li <#if ($post_data['status'] == 1) class="activesa"</#if> onclick="selectStatus(1)">审核通过</li>
                <li <#if ($post_data['status'] == 2) class="activesa"</#if> onclick="selectStatus(2)">未通过</li>
                <input hidden name="status" value="${post_data['status']!}"/>
            </ul>
            <div class="check_list">
                {{--<!-- <table width="100%">--!}
                    {{--<thead>--!}
                        {{--<tr>--!}
                            {{--<td width="15%">手机号</td>--!}
                            {{--<td width="15%">昵称</td>--!}
                            {{--<td width="15%">真实姓名</td>--!}
                            {{--<td>申请时间</td>--!}
                            {{--<#if ($post_data['status'] > 0)--!}
                                {{--<td>审核时间</td>--!}
                            {{--</#if>--!}
                            {{--<#if  ($post_data['status'] == 2)--!}
                                {{--<td width="15%">未通过原因</td>--!}
                            {{--</#if>--!}
                            {{--<td>审核状态</td>--!}
                            {{--<#if ($post_data['status'] == 0)--!}
                                {{--<td width="20%">操作</td>--!}
                            {{--</#if>--!}
                        {{--</tr>--!}
                    {{--</thead>--!}
                    {{--<tbody>--!}
                    {{--<#if ($data_list)--!}
                        {{--<#list ($data_list as $item)--!}
                            {{--<tr>--!}

                                {{--<td>${item->mobile!}</td>--!}
                                {{--<td><a target="_blank"  href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #0E70CA">${item->username!}</a></td>--!}
                                {{--<td>${item->real_name!}</td>--!}
                                {{--<td>${item->add_time!}</td>--!}
                                {{--<#if ($item->status > 0)--!}
                                    {{--<td>${item->update_time!}</td>--!}
                                {{--</#if>--!}
                                {{--<#if  ($item->status == 2)--!}
                                    {{--<td class="audit_fail_reason">--!}
                                        {{--<span class="msg">${item->msg!}</span>--!}
                                        {{--<span class="zk_sq hide">展开<img src="/image/admin/mp_de_collapse.png" class="zk_img"></span>--!}
                                    {{--</td>--!}
                                {{--</#if>--!}
                                {{--<td>--!}
                                    {{--<#if ($item->status == 0)待审核--!}
                                    {{--<#elseif>($item->status == 1)已通过--!}
                                    {{--<#else>未通过--!}
                                    {{--</#if>--!}
                                {{--</td>--!}
                                {{--<#if ($item->status == 0)--!}
                                {{--<td>--!}
                                    {{--<input hidden name="pass_apply">--!}
                                    {{--<input hidden name="refuse_apply">--!}
                                    {{--<input hidden name="msg">--!}
                                    {{--<a href="##" id="${item->id!}" class="btn_pass">通过</a>--!}
                                    {{--<a href="##" id="${item->id!}" class="btn_refuse">不通过</a>--!}
                                {{--</td>--!}
                                {{--</#if>--!}
                            {{--</tr>--!}
                        {{--</#list>--!}
                    {{--</#if>--!}
                    {{--</tbody>--!}
                {{--</table> -->--!}
                <#if ($data_list)
                <#list ($data_list as $item)
                <table width="100%">
                    <thead>
                        <tr>
                            <td width="<#if ($item->status == 2) 60% <#else> 70% </#if>">
                                <#if ($item->user_id)
                                    <span>ID:${item->user_id!}</span>
                                </#if>
                                <#if ($item->username)
                                    <span>昵称：${item->username!}</span>
                                </#if>
                                <#if ($item->mobile)
                                    <span>手机号:${item->mobile!}</span>
                                </#if>
                                <#if ($item->add_time)
                                    <span>申请时间:${item->add_time!}</span>
                                </#if>
                            </td>
                            <td width="15%">
                                <#if ($item->status == 0)审核状态
                                <#else>审核时间
                                </#if>
                            </td>
                            <td width="<#if ($item->status == 2) 10% <#else> 15% </#if>">
                                <#if ($item->status == 0)操作
                                <#else>审核状态
                                </#if>
                            </td>
                            <#if ($item->status == 2)
                                <td width="15%">未通过原因</td>
                            </#if>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                {{--<!-- <#if  (is_array($item->config_fields) && count($item->config_fields) > 0)--!}
                                    {{--<ul class="examine_info">--!}
                                        {{--<#if  (in_array('real_name',$item->config_fields))--!}
                                            {{--<li class="fl">真实姓名：${item->activation_fields['real_name']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('mobile',$item->config_fields))--!}
                                            {{--<li class="fl">手机号码：${item->activation_fields['mobile']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('cid',$item->config_fields))--!}
                                            {{--<li class="fl">身份证号码：${item->activation_fields['cid']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('address',$item->config_fields))--!}
                                            {{--<li class="fl">所在地：${item->activation_fields['address']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('sex',$item->config_fields))--!}
                                            {{--<li class="fl">性别：${item->activation_fields['sex'] == 'm' ? '男性' : '女性'!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('birthday',$item->config_fields))--!}
                                            {{--<li class="fl">生日：${item->activation_fields['birthday_year']!}-${item->activation_fields['birthday_month']!}-${item->activation_fields['birthday_day']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('marital_status',$item->config_fields))--!}
                                            {{--<li class="fl">婚姻状况：${item->activation_fields['marital_status']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('education',$item->config_fields))--!}
                                            {{--<li class="fl">教育程度：${item->activation_fields['education']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('industry_info',$item->config_fields))--!}
                                            {{--<li class="fl">所在行业：${item->activation_fields['industry_info']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('remarks',$item->config_fields))--!}
                                            {{--<li class="fl">备注：${item->activation_fields['remarks']!}</li>--!}
                                        {{--</#if>--!}
                                        {{--<#if  (in_array('upload_image',$item->config_fields))--!}
                                            {{--<li class="fl"><a href="${item->activation_fields['upload_image']!}" target="_blank" style="color:#5a8bff;">查看图片</a></li>--!}
                                        {{--</#if>--!}
                                    {{--</ul>--!}
                                {{--<#else> 无需提交个人信息--!}
                                {{--</#if> -->--!}
                                <#if  (is_array($item->config_fields) && count($item->config_fields) > 0)
                                <table width="100%" class="distributer_info">
                                    <tbody>
                                        <tr>
                                            <td>真实姓名</td>
                                            <td>
                                                <#if  (in_array('real_name',$item->config_fields))
                                                ${item->activation_fields['real_name']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                            <td>手机号</td>
                                            <td>
                                                <#if  (in_array('mobile',$item->config_fields))
                                                ${item->activation_fields['mobile']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                            <td>身份证号</td>
                                            <td>
                                                <#if  (in_array('cid',$item->config_fields))
                                                ${item->activation_fields['cid']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>性别</td>
                                            <td>
                                                <#if  (in_array('sex',$item->config_fields))
                                                ${item->activation_fields['sex'] == 'm' ? '男性' : '女性'!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                            <td>生日</td>
                                            <td>
                                                <#if  (in_array('birthday',$item->config_fields))
                                                ${item->activation_fields['birthday_year']!}-${item->activation_fields['birthday_month']!}-${item->activation_fields['birthday_day']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                            <td>婚姻状况</td>
                                            <td>
                                                <#if  (in_array('marital_status',$item->config_fields))
                                                ${item->activation_fields['marital_status']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>教育程度</td>
                                            <td>
                                                <#if  (in_array('education',$item->config_fields))
                                                ${item->activation_fields['education']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                            <td>所在行业</td>
                                            <td>
                                                <#if  (in_array('industry_info',$item->config_fields))
                                                ${item->activation_fields['industry_info']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                            <td>所在地</td>
                                            <td>
                                                <#if  (in_array('address',$item->config_fields))
                                                ${item->activation_fields['address']!}
                                                <#else>
                                                无
                                                </#if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>备注</td>
                                            <td colspan="5">
                                                <#if  (in_array('remarks',$item->config_fields))
                                                ${item->activation_fields['remarks']!}
                                                </#if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>图片</td>
                                            <td colspan="5">
                                                <#if  (in_array('upload_image',$item->config_fields))
                                                    <#if  ($item->activation_fields['upload_image'])
                                                        <a href="${item->activation_fields['upload_image']!}" target="_blank" style="color:#5a8bff;">查看图片</a>
                                                    </#if>
                                                </#if>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                                <#else> 无需提交个人信息
                                </#if>
                            </td>
                            <td>
                                <#if ($item->status == 0)待审核
                                <#else>${item->update_time!}
                                </#if>
                            </td>
                            <td>
                                <#if ($item->status == 0)
                                    <div class="examine">
                                        <input hidden name="pass_apply">
                                        <input hidden name="refuse_apply">
                                        <input hidden name="msg">
                                        <span id="${item->id!}" class="btn_pass">通过</span>
                                        <span id="${item->id!}" class="btn_refuse">不通过</span>
                                    </div>
                                <#elseif>($item->status == 1)已通过
                                <#else>未通过
                                </#if>
                            </td>
                            <#if ($item->status == 2)
                                <td class="audit_fail_reason">
                                    <span class="msg">${item->msg!}</span>
                                    <span class="zk_sq hide">展开<img src="/image/admin/mp_de_collapse.png" class="zk_img"></span>
                                </td>
                            </#if>
                        </tr>
                    </tbody>
                </table>
                </#list>
                </#if>
            </div>
            <div class="member_list_footer clearfix" style="margin-top: 15px">
              <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </form>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    // var left =  $('.left-menu-content .item-menu:nth-child(7)');
    // left.find("img").attr("src","/image/admin/icon_left/img_distribution_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

    function selectStatus(status) {
        $("#page").val(1);
        $("input[name='status']").val(status);
        $("#form1").submit();
    }
    $(".btn_found").click(function () {
        $("#page").val(1);
        $("#form1").submit();
    })
    $(".btn_pass").click(function () {
        $("input[name='pass_apply']").val($(this).attr('id'));
        $("#form1").submit();
    })
    $(".btn_refuse").click(function () {
        var id = $(this).attr('id');
        layer.open({
            type: 1,
            title: ["审核不通过原因", "text-align:center;padding: 0px;"],
            content: '<div class="text_warn"><img src="/image/admin/notice_img.png"><span>&nbsp;&nbsp;原因提交后不可修改，请谨慎提交</span></div>' +
            '<div class="qm_text"><textarea name="reason" id="reason" style="width: 478px; height: 308px; border: none;" onkeyup="calculateBytes(this)"></textarea></div>' +
                        '<div style="position: absolute; bottom: 50px; right: 20px;"><span class="news_num">0</span>/<span class="news_all">150</span></div>',
            area: ['500px','500px'],
            btn: ['确定', '取消'],
            yes: function (index) {
                if($("#reason").val() == ''){
                    util.mobile_alert('请添加不通过原因');
                    return false;
                }

                $("input[name='refuse_apply']").val(id);
                $("input[name='msg']").val($("#reason").val());
                $("#form1").submit();
                layer.close(index);
            }
        });
    });
    function calculateBytes(obj) {
        var str = $(obj).val();
        var bytesCount = str.length;
        /*for (var i = 0; i < str.length; i++){
            var c = str.charAt(i);
            if (/[\u0000-\u00ff]/.test(c)){
                bytesCount += 1;
            }else{
                bytesCount += 3;
            }
        }*/
        if (bytesCount > 150) {
            $(obj).val(str.substring(0, 150));
        } else {
            $('.news_num').text(bytesCount);
        }
    }
    $('.audit_fail_reason').each(function () {
        var  _this = $(this);
        var need_height = parseInt($(this).css('line-height')) * 2;
        var fontsize=parseInt($(this).css('font-size'));
        var rel_width = $(this).width();
        var lin_num = Math.floor(rel_width / fontsize);
        var need_len = lin_num * 2;
        var rel_height = $(this).height();
        if(rel_height > need_height){
            var text = $(this).find('.msg').text();
            $(this).height(need_height);
            $(this).find('.msg').text($(this).find('.msg').text().substring(0, need_len - 1));
            $(this).find('.msg').text($(this).find('.msg').text()+'…');

            $(this).find('.zk_sq').removeClass('hide');
            $(this).find('.zk_sq').click(function () {
                var zksq = _this.find('.zk_sq');
                if (zksq.text() == '展开') {
                    _this.find('.msg').text(text);
                    zksq.html("收起" + '<img src="/image/admin/mp_de_expand.png">');
                } else {
                    _this.find('.msg').text(text.substring(0, need_len - 1));
                    _this.find('.msg').text(_this.find('.msg').text()+'…');
                    zksq.html("展开" + '<img src="/image/admin/mp_de_collapse.png">');
                }
            });
        }
    })
</script>