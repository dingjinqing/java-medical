//全选全不选
$("#select-all").click(function () {
    var isChecked = $("#select-all").prop("checked");
    $(".list input[name='checkbox']").prop("checked", isChecked);
    if($(this).is(':checked')) {
        $(this).parents('.list-bottom').find('.list').find("input[name='checkbox']").each(function(){
            if(!$(this).is(':checked'))
                $(this).click();
        })
    }else{
        $(this).parents('.list-bottom').find('.list').find("input[name='checkbox']").each(function(){
            if($(this).is(':checked'))
                $(this).click();
        })
    }
});
$("#all_pass").click(function () {
    var cid = [];
    $('input[name="checkbox"]:checked').each(function () {
        cid.push($(this).attr('cid'));
    });
    if (cid.length == 0) {
        //util.alert('未选择任何商品');
        util.mobile_alert('未选择任何评价');
        return false;
    }
    else {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要通过么' + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="all"]').val(_this.attr('name'));
                $('input[name="cid"]').val(cid);
                $("#form1").submit();
                layer.close(index);
            });
        });

    }
})
$("#all_no").click(function () {
    var cid = [];
    $('input[name="checkbox"]:checked').each(function () {
        cid.push($(this).attr('cid'));
    });
    if (cid.length == 0) {
        //util.alert('未选择任何商品');
        util.mobile_alert('未选择任何评价');
        return false;
    }
    else {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要拒绝么' + '</div>', {
                title: false
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="all"]').val(_this.attr('name'));
                $('input[name="cid"]').val(cid);
                $("#form1").submit();
                layer.close(index);
            });
        });

    }
})
$("#all_delete").click(function () {
    var cid = [];
    $('input[name="checkbox"]:checked').each(function () {
        cid.push($(this).attr('cid'));
    });
    if (cid.length == 0) {
        //util.alert('未选择任何商品');
        util.mobile_alert('未选择任何评价');
        return false;
    }
    else {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="all"]').val(_this.attr('name'));
                $('input[name="cid"]').val(cid);
                $("#form1").submit();
                layer.close(index);
            });
        });

    }
})