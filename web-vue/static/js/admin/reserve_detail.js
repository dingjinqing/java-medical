//输入核销码
$('.btn_finish').on('click',function(){
    var store_id=$(this).attr('store_id');
    var order_id=$(this).attr('order_id');
    var order_sn=$(this).attr('order_sn');
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 2
            , title: ['核销码', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            // , area: ['450px','176px']
            , area: ['700px','300px']
            , content: '/admin/store/services/reserve/detail/check?store_id='+store_id+"&order_sn="+order_sn //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {

            }
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                if(iframe.contents().find("#check_number").val()=='') {
                   util.mobile_alert('请输入核销码');
                   return false;
                }
                var dis = 0;
                if(iframe.contents().find("input[name='verify_pay']:checked").val()==1){
                    if(parseFloat(iframe.contents().find("input[name='reduce']").val()) > parseFloat(iframe.contents().find("#card_id option:selected").attr("moneycount"))){
                        util.mobile_alert("剩余金额或次数不够");
                        return false;
                    }
                    // 3IeI2KYDJ4
                    dis = iframe.contents().find("#card_id option:selected").attr("moneycount");
                    if(iframe.contents().find("input[name='reduce']").val() == ''){
                        util.mobile_alert("请填写扣除金额或次数");
                        return false;
                    }
                    if(iframe.contents().find("input[name='reason1']").val() == ''){
                        util.mobile_alert("请填写扣除原因");
                        return false;
                    }
                }else if(iframe.contents().find("input[name='verify_pay']:checked").val() == 2){
                    if(iframe.contents().find("input[name='balance']").val() > $("input[name='balance']").attr("placeholder")){
                        util.mobile_alert("剩余金额或次数不够");
                        return false;
                    }
                    if(iframe.contents().find("input[name='balance']").val() == ''){
                        util.mobile_alert("请填写扣除金额");
                        return false;
                    }
                    if(iframe.contents().find("input[name='reason2']").val() == ''){
                        util.mobile_alert("请填写扣除原因");
                        return false;
                    }
                }
                var param = {
                    "verify_code":iframe.contents().find("#check_number").val(),
                    "verify_pay":iframe.contents().find("input[name='verify_pay']:checked").val(),
                    "card_id":iframe.contents().find("#card_id").val(),
                    "card_no":iframe.contents().find("#card_id option:selected").attr("card_no"),
                    "reduce":'-'+iframe.contents().find("input[name='reduce']").val(),
                    "balance":'-'+iframe.contents().find("input[name='balance']").val(),
                    "reason1":iframe.contents().find("input[name='reason1']").val(),
                    "reason2":iframe.contents().find("input[name='reason2']").val(),
                    "account":iframe.contents().find("input[name='balance']").attr("placeholder"),
                    "dis":dis,
                };
                param.store_id = store_id;
                param.order_sn = order_sn;
                param.order_id = order_id;
                if(param.reduce == '-'){
                    param.reduce =0;
                }
                if(param.balance == '-'){
                    param.balance =0;
                }
                if(param.reason1 == ''){
                    param.reason1='后台操作';
                }
                if(param.reason2 == ''){
                    param.reason2='后台操作';
                }
                if(param.account == '-'){
                    param.account=0;
                }
                util.ajax_json("/admin/store/services/reserve/detail/docheck", function (d) {
                    if (d && d.error == 0) {
                        layer.msg("操作成功");
                        location.reload();
                        // layer.contect();
                    } else if (d && d.error > 0) {
                        layer.msg(d.message);
                    }
                    console.log(d);
                }, param);
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});




