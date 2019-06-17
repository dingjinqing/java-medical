<#include "/admin/header.ftl">
<link href="/css/admin/member_card_audit.css?v=1.0.0" rel="stylesheet" type="text/css"/>
<div class="title">
    <span>会员管理 / </span><a style="color: #666;" href="/admin/user/member/list">会员卡</a><span style="color: #666;"> / </span><span>${card_info->card_name!}</span> - <span style="color: #666;">激活审核</span>
</div>
<div class="card_audit_container clearfix" >
    <form method="post" id="form1" action="/admin/user/member/examine?card_id=${response['card_id']!}">
        {{csrf_field()!}
        <input hidden name="nav" value="${response['nav']!}"/>
        <div class="card_info">
            <ul>
                <li class="clearfix">
                    <div><span>真实姓名</span><input type="text" name="real_name" value="${response['real_name']!}"></div>
                    <div><span>手机号</span><input type="tel" name="mobile" value="${response['mobile']!}"></div>
                    <div><span>申请时间</span><input type="text" name="start_time" value="${response['start_time']!}" onclick="picker();" autocomplete="off">至<input type="text" name="end_time" value="${response['end_time']!}" onclick="picker();" autocomplete="off"></div>
                    <button class="search">查询</button>
                </li>
            </ul>
        </div>
        <div class="member_list_info clearfix">
                <ul class="member_nav clearfix">
                    <li <#if ($response['nav'] == 1) class="activesa" </#if> onclick="selectStatus(1)">待审核</li>
                    <li <#if ($response['nav'] == 2) class="activesa" </#if> onclick="selectStatus(2)">审核通过</li>
                    <li <#if ($response['nav'] == 3) class="activesa" </#if> onclick="selectStatus(3)">未通过</li>
                </ul>
            <#list ($data_list as $data)
                <div class="member_info">
                    <div class="member_title">
                        <span>ID：<strong>${data->id!}</strong></span>
                        <span>昵称：<strong>${data->username!}</strong></span>
                        <span>手机号：<strong>${data->mobile!}</strong></span>
                        <span>申请时间：<strong>${data->add_time!}</strong></span>
                        <span class="operate">操作</span>
                    </div>
                    <div class="member_content clearfix">
                        <ul class="clearfix">
                            <#if ($card_info->activation_cfg && in_array('real_name',$card_info->activation_cfg))
                                <li>真实姓名：<strong>${data->real_name!}</strong></li>
                            </#if>
                            <#if ($card_info->activation_cfg && in_array('cid',$card_info->activation_cfg))
                            <li>身份证号码：<strong>${data->cid!}</strong></li>
                            </#if>
                            <#if ($card_info->activation_cfg && in_array('sex',$card_info->activation_cfg))
                            <li>性别：<strong><#if ($data->sex == 'f') 女 <#elseif>($data->sex == 'm') 男 </#if></strong></li>
                            </#if>
                            <#if ($card_info->activation_cfg && in_array('birthday',$card_info->activation_cfg))
                            <li>生日：<strong>${data->birthday_year!}-${data->birthday_month!}-${data->birthday_day!}</strong></li>
                            </#if>
                            <#if ($card_info->activation_cfg && in_array('marital_status',$card_info->activation_cfg))
                            <li>婚姻状况：<strong><#if ($data->marital_status == 1) 未婚 <#elseif>($data->marital_status == 2)已婚 <#elseif>($data->marital_status == 3) 保密 </#if></strong></li>
                            </#if>
                            <#if ($card_info->activation_cfg && in_array('education',$card_info->activation_cfg))
                            <li>教育程度：<strong>${education[$data->education]!}</strong></li>
                            </#if>
                            <#if ($card_info->activation_cfg && in_array('industry_info',$card_info->activation_cfg))
                            <li>所在行业：<strong>${industry_info[$data->industry_info]!}</strong></li>
                            </#if>
                            <#if ($card_info->activation_cfg && in_array('adress',$card_info->activation_cfg))
                            <li>所在地：<strong>${data->province!} ${data->city!} ${data->district!}</strong></li>
                            </#if>
                        </ul>
                        <div class="operate_box clearfix">
                            <div class="content clearfix">
                                <#if ($data->status == 1)
                                <div class="pass" id="${data->id!}" card_no="${data->card_no!}">通过</div>
                                <div class="fail" id="${data->id!}">不通过</div>
                                <#elseif>($data->status == 2)
                                <span class="audit_pass">审核通过</span>
                                <#elseif>($data->status == 3)
                                <span class="not_pass">未通过</span>
                                <span class="details" refuse_desc = "${data->refuse_desc!}" style="cursor: pointer">查看详情</span>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
            <#include "/admin/jump_page_admin.ftl">
        </div>
    </form>
</div>

<script>
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    function selectStatus(status) {
        $("#page").val(1);
        $("input[name='nav']").val(status);
        $("#form1").submit();
    }
    $(".fail").click(function () {
        var _this = $(this);
        layer.open({
            type: 1,
            title: ["审核不通过原因", "text-align:center;padding: 0px;"],
            content: '<div class="text_warn"><img src="/image/admin/notice_img.png"><span>&nbsp;&nbsp;原因提交后不可修改，请谨慎提交</span></div>' +
            '<div class="qm_text"><textarea name="reason" id="reason" style="width: 350px; height: 200px; border: none;" onkeyup="calculateBytes(this)"></textarea></div>' +
                        '<div style="position: absolute; bottom: 25px; right: 30px;"><span class="news_num">0</span>/<span class="news_all">150</span></div>',
            area: ['375px','375px'],
            btn: ['确定', '取消'],
            yes: function (index) {
                if($("#reason").val() == ''){
                    util.mobile_alert('请添加不通过原因');
                    return false;
                }
                util.ajax_json("/admin/user/member/examine",function (res) {
                    if(res && res.error == 0){
                        util.mobile_alert("拒绝成功");
                        location.reload();
                    }else{
                        util.mobile_alert("拒绝失败");
                    }
                },{act:'refuse',id:_this.attr("id"),refuse_desc:$("#reason").val()})
                layer.close(index);
            }
        });
    });
    $(".pass").click(function () {
        var _this = $(this);
        layer.open({
            type: 1,
            title: ["提示", "text-align:center;padding: 0px;"],
            content: '<div style="padding: 10px;text-align: center;">确认要通过审核吗？</div>',
            area: ['300px','138px'],
            btn: ['确定', '取消'],
            yes: function (index) {
                util.ajax_json("/admin/user/member/examine",function (res) {
                    if(res && res.error == 0){
                        util.mobile_alert("审核成功");
                        location.reload();
                    }else{
                        util.mobile_alert("审核失败");
                    }
                },{act:'pass',id:_this.attr("id"),card_no:_this.attr("card_no")})
                layer.close(index);
            }
        });
    })
    $(".details").click(function () {
        var refuse_desc = $(this).attr("refuse_desc");
        layer.open({
            type: 1,
            title: ["提示", "text-align:center;padding: 0px;"],
            content: '<div class="text_warn"><img src="/image/admin/notice_img.png"><span>&nbsp;&nbsp;原因提交后不可修改，请谨慎提交</span></div>' +
            '<div class="qm_text"><textarea style="width: 350px; height: 200px; border: none;" onkeyup="calculateBytes(this)">'+ refuse_desc +'</textarea></div>',
            area: ['375px','375px'],
            btn: [ '取消'],
        });
    })
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
</script>
<#include "/admin/footer.ftl">