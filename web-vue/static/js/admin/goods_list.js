//20180131
$('[name="cat_id"]').change(function(){
    $("#form1").submit();
});
$('[name="sort_id"]').change(function(){
    $("#form1").submit();
});
$('[name="goods_label_id"]').change(function(){
    $("#form1").submit();
});
$('[name="activity_type"]').change(function(){
    $("#form1").submit();
});

$('[name="source"]').change(function(){
    $("#form1").submit();
});

$("#select-all").click(function () {
    var isChecked = $("#select-all").prop("checked");
    //$(".list input[name='checkbox']").prop("checked", isChecked);
    if($(this).is(':checked')) {
        $(this).prev().attr('src','/image/admin/square_yes.png');
        $(this).parents('.list-bottom').find('.list').find("input[name='checkbox']").each(function(){
            if(!$(this).is(':checked'))
                $(this).click();
        })
    }else{
        $(this).prev().attr('src','/image/admin/square_no.png');
        $(this).parents('.list-bottom').find('.list').find("input[name='checkbox']").each(function(){
            if($(this).is(':checked'))
                $(this).click();
        })
    }
});
$(".list input[name='checkbox']").click(function () {
    $(this).attr("checked", "checked");
    var allLength = $(".list input[name='checkbox']").length;
    var checkedLength = $(".list input[name='checkbox']:checked").length;
    if(allLength == checkedLength){
        $("#select-all").prop("checked",true);
        $("#select-all").prev().attr('src','/image/admin/square_yes.png');
    }else {
        $("#select-all").prop("checked",false);
    }
    if($(this).is(':checked')){
        $(this).prev().attr('src','/image/admin/square_yes.png');

    }else{
        $(this).prev().attr('src','/image/admin/square_no.png');
        $("#select-all").prev().attr('src','/image/admin/square_no.png');
    }
});


//点击图片编辑时修改前面input的值
$(".goods-number-img").click(function() {
    var _this = $(this);
    var data = getPowerInfo('num_config','goods_num','','商品数量',1,1);
    if(data.tip != 1) {
        if ($(this).attr("grade") == 1) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '此商品为会员价商品，确认要修改吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    $(".goods-number-img").show();
                    _this.hide();
                    $(".change-input").attr("disabled", true);
                    $(".change-input").removeClass('ipt-change');
                    _this.prev().attr('disabled', false);
                    _this.prev().addClass('ipt-change');
                    _this.prev().focus();
                    layer.close(index);
                });
            });
        }else {
            $(".goods-number-img").show();
            _this.hide();
            $(".change-input").attr("disabled", true);
            $(".change-input").removeClass('ipt-change');
            _this.prev().attr('disabled', false);
            _this.prev().addClass('ipt-change');
            _this.prev().focus();
        }
    }
});

function deliver_open(goods_id){
    // console.log(goods_id);
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        //触发事件
        layer.open({
            type: 1
            ,title: ['设置运费模板','text-align:center;padding: 0px;']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: '650px'
            ,id: 'layerDemoD' //防止重复弹出
            ,content: $('#set-config-temp') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['保存','取消']
            ,btnAlign: 'r' //按钮居右
            ,shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,yes: function(index, layero){ //保存按钮的回调
                var data={};
                data.goods_id = goods_id;
                data.deliver_template_id = $('[name="deliver_template_id"]').val();

                util.ajax_json('/admin/goods/manage/update',function(d){
                    if(d&&d.error==0){
                        util.mobile_alert(d.content);
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                },data);
                layer.close(index);
                //return false 开启该代码可禁止点击该按钮关闭
            },btn2: function(index, layero){
                //按钮取消的回调

                //return false 开启该代码可禁止点击该按钮关闭
            }
        });
    });
}

//商品标签
var label_arry = [];
 var goods_all_labels_id = $('input[name="goods_all_labels_id_str"]').val().split(',');
var goods_all_labels_name = $('input[name="goods_all_labels_name_str"]').val().split(',');
// console.log(all_label);
$('.label-info-row').on('click','.label-delete',function(){
    var op_name = $(this).parent().html();
    var opp_val = $(this).prev().attr("value");
    $(this).parent().remove();
    var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
    $('#label_id').append(op_html);
    label_arry.splice($.inArray(opp_val,label_arry),1);
    $('input[name="goods_have_label_str"]').val(label_arry.join(','));
    if(label_arry.length == 0){
        $('.label-choose').hide();
    }
});

$("#label_id").change(function(){
    var label_name = $(this).children('option:selected').html();
    var op_val = $(this).children('option:selected').attr('value');
    console.log(label_arry.length);
    if(label_arry.length == 0){
        $('.label-choose').show();
    }
    label_arry.push(op_val);
    $('input[name="goods_have_label_str"]').val(label_arry.join(','));
    var img = ' <img src="/image/admin/icon_delete.png" alt="" class="label-delete"  />'
    var span =' <span class="label_span">';
    var inner_html = span + '<span value="'+op_val+'">'+ label_name + '</span>' + img + '</span>';
    $(this).parent().parent().next().show();
    $(this).parent().parent().next().find(".label-info-row").append(inner_html);
    $(this).children('option:selected').remove();
});
$('.label_infoo').on('click',function(){
    var goods_have_label_str = $(this).attr('goods_have_label_str');
    var goods_have_label_name = $(this).attr('goods_have_label_name');
    var goods_id = $(this).attr('goods_id');
    // var status = $.parseJSON($(this).attr('status'));
    var _this = $(this);
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        layer.open({
            type: 1
            , title: ['选择应用位置', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: '500px'
            , content: $('#set-label') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , success: function (index, layero) {
                var layer_frame = $('#set-label');
                var html = '<option value="0"  selected="selected" >请选择商品标签</option>';
                $(".label-info-row").children().remove();
                $('.label-choose').hide();
                // console.log(all_label);
                for (var i in goods_all_labels_id){
                    html+='<option value='+goods_all_labels_id[i]+'>'+goods_all_labels_name[i]+'</option>';
                }
                $('.label_option').html(html);
                // console.log(goods_have_label_str);
                $('input[name="goods_have_label_str"]').val(goods_have_label_str);
                // console.log(goods_have_label_str + goods_have_label_name);
                if (goods_have_label_str!=''){
                    label_arry = goods_have_label_str.split(',');
                    var label_name = goods_have_label_name.split(',');
                    for (var i in label_arry){
                        var img = ' <img src="/image/admin/icon_delete.png" alt="" class="label-delete"  />'
                        var span =' <span class="label_span">';
                        var inner_html = span + '<span value="'+label_arry[i]+'">'+ label_name[i] + '</span>' + img + '</span>';
                        $(".label-info-row").append(inner_html);
                        $('#label_id').children("option[value='"+label_arry[i]+"']").remove();
                        // if(!in_array(all_label[i].id,label_arry)){
                        //     html+='<option value='+all_label[i].id+'>'+all_label[i].name+'</option>';
                        // }
                    }
                    $('.label-choose').show();
                }else{
                    label_arry = [];
                }
            }
            , yes: function (index, layero) { //保存按钮的回调
                label_arry = [];
                goods_have_label_str = $('input[name="goods_have_label_str"]').val();
                // console.log(goods_have_label_str);
                util.ajax_json('/admin/ajax/gl/list/update',function (res) {
                    console.log(res)
                    if (res.error == 0) {
                        _this.attr('goods_have_label_str',goods_have_label_str);
                        _this.attr('goods_have_label_name',res.content.goods_have_label_name);
                        var span_name = res.content.goods_have_label_name.split(',');
                        _this.prev().children().remove();
                        if(span_name.length>4){
                            span_name.length =4;
                        }
                        for(var i=0;i<span_name.length;i++){
                            if(span_name[i] != ''){
                                var span = '<span>'+ span_name[i]+'</span>';
                                _this.prev().append(span);
                            }

                        }
                        util.mobile_alert('修改成功');
                        layer.close(index);
                    } else {
                        util.mobile_alert(res.message);
                    }
                },{goods_id:goods_id,goods_have_label_str:goods_have_label_str});
                // layer.close(index);
            }, btn2: function (index, layero) {
                //取消按钮的回调
            }
        });
    });
});





