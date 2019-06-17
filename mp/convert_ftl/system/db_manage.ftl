<#include ("system.header")
<link href="/css/admin/goods_list.css?v=1.0.6" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<style type="text/css">

    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    
    .order-info-li .fl2{
        width:90px;
        float: left;
        margin-top:5px;
    }
    .order-info-li .fl2 input{
        width:16px;
        float: left;
        margin-top:-5px;
        margin-left: 20px;
    }

    .sql_text{
    	width: 800px;
    	height: 300px;
    }
    
</style>
<ul id="tab" class="nav nav-tabs">
    <li class="active"><a href="#" data-toggle="tab" onclick="location.href = '/system/db/manage';">数据库更新</a></li>
    <li><a href="#" data-toggle="tab" onclick="location.href = '/system/db/manage/list';">数据库更新日志</a></li>
    <li ><a href="" data-toggle="tab" onclick="location.href ='/system/db/manage/sql/option';">数据查询</a></li>
</ul>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="order-info">
            <ul>
                <li class="order-info-li clearfix">
                    <div class="fl" >
                        <span>迭代版本号</span>
                        <input type="text" name="version" placeholder="请输入版本号">
                    </div>
                
                    <div class="fl">
                        <span>数据库类型</span>
                        <select name="db_type">
                        	<option value="">请选择数据库类型</option>
                        	<#list ($db_type_list as $db_type_key => $db_type_name)
                        	<option value="${db_type_key!}">${db_type_name!}</option>
                        	</#list>
                        </select>
                    </div>

                    <div class="fl hide_div_shop hide" >
                        <span>店铺ID</span>
                        <input type="text" name="shop_id" placeholder="请输入店铺ID（选填）" style="width: 150px;">
                    </div>

                    <button class="btn-choose">执行</button>
                </li>



                <li class="order-info-li clearfix error_msg hide">
                    <div class="fl" style="width: auto;">
                        <span>错误信息</span>
                        <span class="error_msg_detail" style="width: 80%;text-align: left"></span>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
	$("select[name='db_type']").change(function() {
		var type = $(this).val();
		if (type == 'shop') {
			$('.hide_div_shop').removeClass('hide');
		}else{
			$('.hide_div_shop').addClass('hide');
		}
	});

	$(".btn-choose").click(function() {
        var option_type = $("select[name='option_type']").val();
		var version = $("input[name='version']").val();
		var db_type = $("select[name='db_type']").val();
		if (!version) {
			util.mobile_alert("请填写版本号");
			return false;
		}

		if (!db_type) {
			util.mobile_alert("请选择数据库类型");
			return false;
		}

        var sql_command = $("textarea[name='sql_command']").val();
        if (!sql_command && option_type == 'sql') {
            util.mobile_alert("请输入sql语句");
            return false;
        }

		var shop_id = $("input[name='shop_id']").val();

		var param = {};
		param.version = version;
        param.option_type = option_type;
		param.db_type = db_type;
		param.shop_id = shop_id;
        param.sql_command = sql_command;
		util.ajax_json('/system/db/manage/option',function (d) {
			if (d.error == 0) {
				util.mobile_alert(d.message);
				setTimeout(function () {
                    location.href='/system/db/manage/list';
                }, 1000);
			}else{
				util.mobile_alert(d.message);
                if (d.content.code==1) {
                    $('.error_msg').removeClass('hide');
                    $('.error_msg_detail').text(d.content.msg)
                }
			}
		},param);
	});
</script>

<#include ("system.footer")