/**
 * Created by 新国 on 2015/8/19.
 */

(function(){

    // window.gopage =  function(page){
    //     var last_page = '{{ $data_list -> lastPage() }}';
    //     if(parseInt(page) > parseInt(last_page)) {
    //         page = last_page;
    //     }
    //     $("#page").val(page);
    //     $("#form1").submit();
    // };

    $(".clone-template").click(function(){
        $("#act").val("clone");
        $("#deliver_template_id").val($(this).attr("deliver_template_id"));
        $("#form1").submit();
    });

    $(".del-template").click(function(){
        $("#act").val("del");
        $("#deliver_template_id").val($(this).attr("deliver_template_id"));
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("#form1").submit();
                layer.close(index);
            });
        });
       
    });

    $(".ship_is_free").change(function(){
        var ship_is_free = $(".ship_is_free").val();
        $(".shop-template-container span").addClass("hide");
        $(".shop-template-container span:eq("+ship_is_free+")").removeClass("hide");
    });

    $(".btn-save-shop-template").click(function(){
        var ship_is_free = $(".ship_is_free").val();
        var ship_fee = 10;
        var start_ship_order_gmv = 150;
        var no_satisfy_ship_fee = 10;
        var param = {};
        if(ship_is_free == "0"){
            ship_fee = parseFloat($(".ship_fee").val());
            if(isNaN(ship_fee) || !isNaN(ship_fee) && ship_fee <0){
                util.mobile_alert("运费必须大于等于0");
                $(".ship_fee").focus();
                return false;
            }
            start_ship_order_gmv = parseFloat($(".start_ship_order_gmv").val());
        }else{
            start_ship_order_gmv = parseFloat($(".start_ship_order_gmv").val());
            if(isNaN(start_ship_order_gmv) || !isNaN(start_ship_order_gmv) && start_ship_order_gmv <0){
                util.mobile_alert("运费必须大于等于0");
                $(".start_ship_order_gmv").focus();
                return false;
            }
            ship_fee = parseFloat($(".no_satisfy_ship_fee").val());
            if(isNaN(ship_fee) || !isNaN(ship_fee) && ship_fee <0){
                util.mobile_alert("运费必须大于等于0");
                $(".no_satisfy_ship_fee").focus();
                return false;
            }
        }
        param = {ship_is_free:ship_is_free,ship_fee:ship_fee,start_ship_order_gmv:start_ship_order_gmv};
        util.ajax_json("/admin/ajax/deliver/fee/template/op",function(d){
            if(d && d.error == 0){
                util.mobile_alert(d.content);
            }else if(d && d.error >0){
                util.mobile_alert(d.message);
            }
        },param);
    });
}());