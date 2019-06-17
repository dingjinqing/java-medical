<#include ("system.header")
<link href="/css/system/table_list.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<style type="text/css">
	.table>thead>tr>th{
        width: 7%;
    }
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
    	width: 600px;
    	height: 150px;
    }
    
</style>
<ul id="tab" class="nav nav-tabs">
    <li><a href="#" data-toggle="tab" onclick="location.href = '/system/db/manage';">数据库更新</a></li>
    <li><a href="#" data-toggle="tab" onclick="location.href = '/system/db/manage/list';">数据库更新日志</a></li>
    <li class="active"><a href="" data-toggle="tab" onclick="location.href ='/system/db/manage/sql/option';">数据查询</a></li>
</ul>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="order-info">
            {{ csrf_field()!}
            <input type="hidden" name="record_id" value="0">
            <input type="hidden" name="act" value="add">
            <ul>
                <li class="order-info-li clearfix">
                    <div class="fl">
                        <span>数据库类型</span>
                        <select name="db_type">
                        	<option value="">请选择数据库类型</option>
                        	<#list ($db_type_list as $db_type_key => $db_type_name)
                        	<option value="${db_type_key!}">${db_type_name!}</option>
                        	</#list>
                        </select>
                    </div>

                    <div class="fl hide_div_shop hide" style="width: auto;">
                        <span>店铺ID</span>
                        <input type="text" name="shop_id" placeholder="请输入店铺ID">
                    </div>

                    <button class="btn-choose">执行</button>

                </li>
                <li class="order-info-li clearfix">
                    <div class="fl" style="width: auto;">
                        <span>SQL语句</span>
                        <textarea name="sql_command" class="sql_text" placeholder="请输入sql语句"></textarea>
                    </div>
                </li>
                <li class="order-info-li clearfix">
                    <div class="fl hide error_msg" style="width: 100%;">
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
		var db_type     = $("select[name='db_type']").val();
		if (!db_type) {
			util.mobile_alert("请选择数据库类型");
			return false;
		}

        var shop_id     = $("input[name='shop_id']").val();
        if (db_type == 'shop' && !shop_id) {
            util.mobile_alert("请输入店铺ID");
            return false;
        }

		var sql_command = $("textarea[name='sql_command']").val();
		if (!sql_command) {
			util.mobile_alert("请输入sql语句");
			return false;
		}

        $(".main-table").remove();
        $('.error_msg').addClass('hide')
        param = {};
        param.db_type = db_type;
        param.sql_command = sql_command;
        param.shop_id = shop_id;
        util.ajax_json('/system/db/sql/search',function (d) {
            if (d.error == 0) {
                var html = '<div class="box panel main-table">';
                html += '<div class="layer">';
                html += '<table cellspacing="1" cellpadding="3"  class="table" id="main-table">'
                html += '<thead><tr>';
                var head = d.content.head;
                for (var i = 0; i < head.length; i++) {
                    html += '<th>'+head[i]+'</th>';
                }
                html += '</tr></thead>'; 

                var body = d.content.body;

                for (var i = 0; i < body.length; i++) {
                    html += '<tr style="text-align:center;">';
                    for (var j in body[i]) {
                        html += '<td>'+body[i][j]+'</td>';
                    }
                    html += '</tr>';
                }
                html += '</table></div></div>';
                $(".order-container").append(html);
                util.mobile_alert(d.message);
            }else{
                util.mobile_alert(d.message);
                if (d.content.code == 1) {
                    $('.error_msg').removeClass('hide')
                    $('.error_msg_detail').text(d.content.msg);
                }
            }
        },param);
		
		// $("#form1").submit();
	});

	$('.btn-edit').click(function(argument) {
		$("input[name='act']").val('eidt');
		var parant = $(this).parent().parent();
		var record_id = parant.attr('record_id');
		var version = parant.find('.version').text();
		var db_type = parant.find('.db_type').attr('db_type');
		var sql_command = parant.find('.sql_command').attr('sql');
		var shop_id = parant.attr('shop_id');
		$("input[name='record_id']").val(record_id);
		$("input[name='version']").val(version);
		$("input[name='shop_id']").val(shop_id);
		$("textarea[name='sql_command']").val(sql_command);
		$("select[name='db_type']").fint("option[value='"+db_type+"']").prop("selected", true);
	});
</script>

<#include ("system.footer")