//选择奖励类型
$("select[name='reward_type']").change(function() {
	$("#form1").submit();
});

$(".btn_search").click(function () {
	$("#form1").submit();
});

//删除
$(".del_btn").click(function() {
    var obj = $(this);
	var act_id = obj.parent().parent().attr('act_id');
	var param = {};
	param.act_id = act_id;
	param.act = 'del';
	util.ajax_json('/admin/market/promote/save',function(d) {
		if (d.error == 0) {
			util.mobile_alert(d.content);
            location.reload();
		}else{
		    util.mobile_alert(d.message);
        }
	},param); 
});

//停用
$(".block_btn").click(function() {
    var obj = $(this);
    var act_id = obj.parent().parent().attr('act_id');
    var param = {};
    param.act_id = act_id;
    param.act = 'block';
    util.ajax_json('/admin/market/promote/save',function(d) {
        if (d.error == 0) {
            util.mobile_alert(d.content);
            obj.addClass('hide');
            obj.parent().find('.edit').addClass('hide');
            obj.parent().find('.share_common').addClass('hide');
            obj.parent().find('.del_btn').removeClass('hide');
            obj.parent().parent().find('.act_status_name').text('已停用');
        }else{
            util.mobile_alert(d.message);
        }
    },param);
});