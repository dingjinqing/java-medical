function exchange_some(title,id) {
    layer.open({
        type: 1
        ,title: [title,'text-align:center;padding: 0px;']
        ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
        ,area: '400px'
        ,content: $(id) //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        ,btn: ['确认','取消']
        ,btnAlign: 'r' //按钮居右
        ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
        ,yes: function(index, layero){ //确定按钮的回调

            if(id=='#set-province'){
                //余额充值
                var data={};
                data.free_id = $('#free_id').val();
                data.province_id = $(id).find('.province_id').val();
                data.key = 'province_id';
                if(data.province_id == 0){
                    util.mobile_alert('请选择省份');
                    return false;
                }
                util.ajax_json('/system/free/set/info',function(d){
                    if(d&&d.error==0){
                        var province_name = $('.province_id').find("option[value='"+data.province_id+"']").text();
                        $('.member_list_main').find(".member_tr[free_id='"+data.free_id+"']").find('.free_province span').text(province_name);
                        $('.member_list_main').find(".member_tr[free_id='"+data.free_id+"']").find('.free_province').attr('province_id',data.province_id);
                        util.mobile_alert(d.message);

                        layer.close(index);
                    }else{
                        util.mobile_alert(d.message);
                    }
                },data);
            }
            if(id=='#set-desc'){
                //积分充值
                var data={};
                data.free_id = $('#free_id').val();
                data.desc = $(id).find('.desc').val();
                data.key = 'desc';
                if(data.desc == '' ){
                    util.mobile_alert('请输入备注');
                    return false;
                }
                util.ajax_json('/system/free/set/info',function(d){
                    if(d&&d.error==0){
                        $('.member_list_main').find(".member_tr[free_id='"+data.free_id+"']").find('.free_desc span').text(data.desc);
                        util.mobile_alert(d.message);
                        layer.close(index);
                    }else{
                        util.mobile_alert(d.message);
                    }
                },data);
            }
            
        },btn2: function(index, layero){
            //按钮取消的回调


        }, end: function () {
            $(id).hide();
        }
    });
}
//修改积分
$('.member_list_main').on('click','.btn_province',function(){
    set_free_id($(this));
    var province_id = $(this).parent().attr('province_id');
    $('.province_id').find("option[value='"+province_id+"']").attr('selected','selected');
    exchange_some('修改省份','#set-province');
});
//修改备注
$('.member_list_main').on('click','.btn_desc',function(){
    set_free_id($(this));
    var desc = $(this).prev().text();
    $('#set-desc').find('.desc').val(desc);
    exchange_some('修改备注','#set-desc');
});

function set_free_id(obj) {
    var free_id = obj.parent().parent().attr('free_id');
    $('#free_id').val(free_id);
}

