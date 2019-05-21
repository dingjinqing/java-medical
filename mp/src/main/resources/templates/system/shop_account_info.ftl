<#include "/system/header.ftl">

<link rel="stylesheet" href="/css/system/shop_list.css?v=1.0.0">
<link rel="stylesheet" href="/css/system/table_list.css?v=1.0.0">
<style>

</style>
<ul id="tab" class="nav nav-tabs">

    <li <#if (nav_type==0)>class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/account/list">商家账号列表</a>
    </li>
    <li <#if (nav_type==2)>class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/account/add">商家账号添加</a>
    </li>
    <#if (nav_type==3)>
        <li class="active"><a href="#" data-toggle="tab" url="#">商家账号编辑</a></li>
    </#if>
</ul>
<#noparse>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>
</#noparse>
<form enctype="multipart/form-data" action="${act_url}" name="form1" id="form1" method="post"
      onsubmit="return on_submit();">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="sys_id" id="sys_id" value="${shop_account.sys_id!}">
    <div class="box panel">
        <div class="panel-body">
            <table class="tab_body show table">
                <tr>
                    <td>
                        用户名
                    </td>
                    <td>
                        <input type="text" name="user_name" value="${shop_account.user_name!}">
                    </td>
                </tr>
                <tr>
                    <td>
                        密码
                    </td>
                    <td>
                        <input type="password" name="password" value="">
                        <#if ("${shop_account.sys_id!}" != "")>
                            <span class="text-warning">密码为空，则不修改原密码</span>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td>
                        昵称
                    </td>
                    <td>
                        <input type="text" name="account_name" value="${shop_account.account_name!}">
                    </td>
                </tr>
                <tr>
                    <td>
                        审核状态
                    </td>
                    <td>
                        <select name="state">
                            <option value="1" <#if ("${shop_account.state!}" =="1")> selected </#if>>申请中</option>
                            <option value="2" <#if ("${shop_account.state!}" =="2")> selected </#if>>审核通过</option>
                            <option value="3" <#if ("${shop_account.state!}" =="3")> selected </#if>>审核不通过</option>
                            <option value="4" <#if ("${shop_account.state!}" =="4")> selected </#if>>已禁用</option>
                        </select>
                    </td>
                </tr>
                <tr hidden>
                    <td>
                        店铺等级
                    </td>
                    <td>
                        <select name="shop_grade">
                            <option value="1" <#if ("${shop_account.shop_grade!}" =="1")> selected </#if>>普通店</option>
                            <option value="2" <#if ("${shop_account.shop_grade!}" =="2")> selected </#if>>专营店</option>
                            <option value="3" <#if ("${shop_account.shop_grade!}" =="3")> selected </#if>>精品店</option>
                            <option value="4" <#if ("${shop_account.shop_grade!}" =="4")> selected </#if>>旗舰店</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        最大SKU数量
                    </td>
                    <td>
                        <input type="text" name="max_sku_num" value="${shop_account.max_sku_num!10000}">
                    </td>
                </tr>
                <tr>
                    <td>
                        最大店铺数量
                    </td>
                    <td>
                        <input type="text" name="max_shop_num" value="${shop_account.max_shop_num!100}">
                    </td>
                </tr>
                <tr>
                    <td>
                        首次续费时间
                    </td>
                    <td>
                        <input type="text" name="buy_time" value="${shop_account.buy_time!}" onClick="picker();" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td>
                        到期时间
                    </td>
                    <td>
                        <input type="text" name="end_time" value="${shop_account.end_time!}" onClick="picker();" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td>
                        手机号
                    </td>
                    <td>
                        <input type="text" name="mobile" value="${shop_account.mobile!}">
                    </td>
                </tr>
                <tr>
                    <td>
                        公司名称
                    </td>
                    <td>
                        <input type="text" name="company" value="${shop_account.company!}" size="50">
                    </td>
                </tr>
                <tr>
                    <td>
                        销售员
                    </td>
                    <td>
                        <input type="text" name="salesperson" value="${shop_account.salesperson!}">
                    </td>
                </tr>
                <tr>
                    <td>地理位置</td>
                    <td>
                        <select name="province_code" id="province_code">
                            <#list province as item>
                                <option value="${item.province_id}" <#if ("${shop_account.province_code!}" == "${item.province_id}")> selected </#if>>${item.name}</option>
                            </#list>
                        </select>
                        <select name="city_code" id="city_code">
                            <#list city as item>
                                <option value="${item.city_id}" <#if ("${shop_account.city_code!}" == "${item.city_id}")> selected </#if>>${item.name}</option>
                            </#list>
                        </select>
                        <select name="district_code" id="district_code">
                            <#list district as item>
                                <option value="${item.district_id}" <#if ("${shop_account.district_code!}" == "${item.district_id}")> selected </#if>>${item.name}</option>
                            </#list>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        详细地址
                    </td>
                    <td>
                        <input type="text" name="address" value="${shop_account.address!}">
                    </td>
                </tr>
                <tr>
                    <td>
                        初始销量
                    </td>
                    <td>
                        <input type="checkbox" class="switch" id="checkbox1" value="1" name="base_sale" <#if ("${shop_account.base_sale!0}" != "0")> checked </#if> >
                        <label for="checkbox1" class="switch"></label>
                        <span style="color: rgb(0, 0, 0);">已关闭</span>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input type="submit" value="保存" class="primary">&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

            </table>

        </div>
    </div>
</form>

<#noparse>
<script>
    function on_submit() {
        // 写入验证代码
        if($('[name="user_name"]').val()==""){
            art.tips("用户名不能为空");
            $('[name="user_name"]').focus();
            return false;
        }

        if($('[name="sys_id"]').val()=="" && $('[name="password"]').val()==""){
            art.tips("密码不能为空");
            $('[name="password"]').focus();
            return false;
        }
        var num = parseInt($('[name="max_sku_num"]').val());
        if(isNaN(num) || num<0){
            art.tips("最大SKU数量必须大于0的数字");
            $('[name="max_sku_num"]').focus();
            return false;
        }

        num = parseInt($('[name="max_shop_num"]').val());
        if(isNaN(num) || num<0){
            art.tips("最大店铺数量必须大于0的数字");
            $('[name="max_shop_num"]').focus();
            return false;
        }

        if($('[name="buy_time"]').val()==""){
            art.tips("首次续费时间不能为空");
            $('[name="buy_time"]').focus();
            return false;
        }

        if($('[name="end_time"]').val()==""){
            art.tips("到期时间不能为空");
            $('[name="end_time"]').focus();
            return false;
        }
        /*验证正确格式的手机号*/
        var re=/^[1][3,4,5,6,7,8,9][0-9]{9}$/;
        if($(":text[name='mobile']").val() != ""){
            if(!re.test($(":text[name='mobile']").val())){
                art.dialog.tips("请输入有效的手机号");
                return false;
            }
        }

        return true;
    }
    function picker(){
        return WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
    }
    /*省市区的联动*/
    $("select[name='province_code']").change(function(){
        var pid= $(this).val();
        var data = {};
        data.province_id = pid;
        util.ajax_json('/system/user/address',function(d){
            if(d && d.error == 0){
                var html = '';
                var first_city_id = d.content.city[0].city_id;
                $.each(d.content.city,function(i,e){
                    html +='<option value="'+e.city_id+'" >'+e.name+'</option>';
                });
                $("select[name='city_code']").empty().append(html);
                html='';
                $.each(d.content.district,function(i,e){
                    html +='<option value="'+e.district_id+'" >'+e.name+'</option>';
                });
                $("select[name='district_code']").empty().append(html);
            }
        },data)
    });
    $("select[name='city_code']").change(function(){
        var cid= $(this).val();
        var data = {};
        data.city_id = cid;
        util.ajax_json('/system/user/address',function(d){
            if(d && d.error == 0){
                var html = '';
                $.each(d.content,function(i,e){
                    html +='<option value="'+e.district_id+'" >'+e.name+'</option>';
                });
                $("select[name='district_code']").empty().append(html);
            }
        },data)
    });
    $('input.switch').change(function(){
        let $this = $(this);
        if($this.is(':checked')){
            $this.parent('td').find('span').text('已开启')
        } else {
            $this.parent('td').find('span').text('已关闭')
        }
    })
    $('input.switch').each(function(){
        let $this = $(this);
        if($this.is(':checked')){
            $this.parent('td').find('span').text('已开启')
        } else {
            $this.parent('td').find('span').text('已关闭')
        }
    })
</script>

</#noparse>
<#include "/system/footer.ftl">
