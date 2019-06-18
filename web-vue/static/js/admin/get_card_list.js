function exchange_some(title,id) {
    layer.open({
        type: 1
        ,title: [title,'text-align:center;padding: 0px;']
        ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
        ,area: '600px'
        ,content: $(id) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        ,btn: ['确认','取消']
        ,btnAlign: 'r' //按钮居右
        ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
        ,yes: function(index, layero){ //确定按钮的回调
            if(id=='#set-money'){
                //余额充值

                var data={};
                if($(id).find('.amount').val()<0){
                    if(parseInt($(id).find('.money_dis').val())<Math.abs(parseInt($(id).find('.amount').val()))){
                        util.mobile_alert("余额不够");
                        return false;
                    }
                }
                data.money_dis = $(id).find('.money_dis').val();
                data.reduce = $(id).find('.amount').val();
                data.user_id = $(id).find('.user_id').val();
                data.card_id = $(id).find('.card_id').val();
                data.card_no = $(id).find('.card_no').val();
                data.message = $(id).find('.remark').val();
                data.card_type = $(id).find('.card_type').val();
                util.ajax_json('/admin/ajax/user/card/consume',function(d){
                    if(d&&d.error==0){
                        util.mobile_alert(d.content);
                        layer.close(index);
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                    location.reload();
                },data);
            }
            if(id=='#set-integral'){
                //次数充值
                var data={};
                if($(id).find('.score').val()<0){
                    if(parseInt($(id).find('.score_dis').val())<Math.abs(parseInt(($(id).find('.score').val())))){
                        util.mobile_alert("次数不够");
                        return false;
                    }
                }

                data.count_dis = $(id).find('.score_dis').val();
                data.reduce = $(id).find('.score').val();
                data.user_id = $(id).find('.user_id').val();
                data.card_id = $(id).find('.card_id').val();
                data.card_no = $(id).find('.card_no').val();
                data.message = $(id).find('.remark').val();
                data.card_type = $(id).find('.card_type').val();
                util.ajax_json('/admin/ajax/user/card/consume',function(d){
                    if(d&&d.error==0){
                        util.mobile_alert(d.content);
                        layer.close(index);
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                    location.reload();
                },data);
            }
            if(id=='#set-exchang'){
                //兑换次数充值
                var data={};
                if($(id).find('.score').val()<0){
                    if(parseInt($(id).find('.score_dis').val())<Math.abs(parseInt(($(id).find('.score').val())))){
                        util.mobile_alert("次数不够");
                        return false;
                    }
                }

                data.count_dis = $(id).find('.score_dis').val();
                data.reduce = $(id).find('.score').val();
                data.user_id = $(id).find('.user_id').val();
                data.card_id = $(id).find('.card_id').val();
                data.card_no = $(id).find('.card_no').val();
                data.message = $(id).find('.remark').val();
                data.card_type = $(id).find('.card_type').val();
                data.type = 1;
                util.ajax_json('/admin/ajax/user/card/consume',function(d){
                    if(d&&d.error==0){
                        util.mobile_alert(d.content);
                        layer.close(index);
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                    location.reload();
                },data);
            }
            //layer.close(index);   //开启则可以关闭弹框
        },btn2: function(index, layero){
            //按钮取消的回调


        }
    });
}
//修改积分
$('.member_list_main').on('click','.btn_integral',function(){
    if($.trim($(this).parent().prev().prev().html())=='未激活'){
        util.mobile_alert('该卡未激活');
        return false;
    }
    exchange_some('修改次数','#set-integral');
    var ipt_val = $(this).parent().parent().find('.ipt-integral').val();
    if(!ipt_val) ipt_val=0;
    $('#set-integral').find('.score_dis').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.user_id').val();
    $('#set-integral').find('.user_id').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.card_id').val();
    $('#set-integral').find('.card_id').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.card_no').val();
    $('#set-integral').find('.card_no').val(ipt_val);
});
//修改余额
$('.member_list_main').on('click','.btn_money',function(){
    if($.trim($(this).parent().prev().html())=='未激活'){
        util.mobile_alert('该卡未激活');
        return false;
    }
    exchange_some('修改余额','#set-money');
    var ipt_val = $(this).parent().parent().find('.ipt-money').val();
    if(!ipt_val) ipt_val="0.00";
    $('#set-money').find('.money_dis').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.user_id').val();
    $('#set-money').find('.user_id').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.card_id').val();
    $('#set-money').find('.card_id').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.card_no').val();
    $('#set-money').find('.card_no').val(ipt_val);
});
//修改兑换商品次数
$('.member_list_main').on('click','.btn_exchang',function(){
    if($.trim($(this).parent().prev().prev().prev().html())=='未激活'){
        util.mobile_alert('该卡未激活');
        return false;
    }
    exchange_some('修改兑换次数','#set-exchang');
    var ipt_val = $(this).parent().parent().find('.ipt-exchang').val();
    if(!ipt_val) ipt_val=0;
    $('#set-exchang').find('.score_dis').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.user_id').val();
    $('#set-exchang').find('.user_id').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.card_id').val();
    $('#set-exchang').find('.card_id').val(ipt_val);
    var ipt_val = $(this).parent().parent().find('.card_no').val();
    $('#set-exchang').find('.card_no').val(ipt_val);
});
