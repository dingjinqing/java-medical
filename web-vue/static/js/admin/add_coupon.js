
    function onCateload(){
        console.log(cat_id)
        if(cat_id){
            $(".cat_table tr:gt(0)").remove();
            var ul = $('<ul>');
            ul.addClass('cat_ul clearfix');
            var html= `<div>
                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span><span class="third_cat">三级分类</span>
                        </div>`
            $('.cate_li').each(function(){
                var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                thirdCheck.each(function(){
                    if($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')){
                        return;
                    } else if($(this).is(':checked')) {
                        var el = $('<li class="third_cat">');
                            el.text($(this).next().text());
                            ul.append(el)
                            return;
                    }
                })
                if(firstCheck.is(':checked') === true){
                    var el = $('<li class="first_cat">');
                    el.text(firstCheck.next().text());
                    ul.append(el)
                    return;
                } else if (firstCheck.is(':checked') === false) {
                    secondCheck.each(function(){
                        if($(this).is(':checked')){
                            var el = $('<li class="second_cat">');
                            el.text($(this).next().text());
                            ul.append(el)
                            return;
                        } 
                    })
                }
            })
            $('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
            $('.cat_table').show()
        }else{
            $('.cat_table').hide()
        }
    }
    onCateload();
//选择分类
$('.add_cate,.edit_cls').on('click',function(){
    var count = $(this).attr("cat_count");
    if(count==0){
        util.mobile_alert('无可选分类');
    }
    else {
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['添加平台分类', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['580px','540px']
                , content: $('#set-category') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //保存按钮的回调
                    var cat_array = [];
                    $(".cat_table tr:gt(0)").remove();
                        var ul = $('<ul>');
                        ul.addClass('cat_ul clearfix');
                        var html= `<div style="margin:0;">
                                        示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span><span class="third_cat">三级分类</span>
                                    </div>`
                        $('input[name="cat_id[]"]:checked').each(function () {
                            cat_array.push($(this).val());
                        });
                        $('.cate_li').each(function(){
                            var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                            var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                            var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                            thirdCheck.each(function(){
                                if($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')){
                                    return;
                                } else if($(this).is(':checked')) {
                                    var el = $('<li class="third_cat">');
                                        el.text($(this).next().text());
                                        ul.append(el)
                                        return;
                                }
                            })
                            if(firstCheck.is(':checked') === true){
                                var el = $('<li class="first_cat">');
                                el.text(firstCheck.next().text());
                                ul.append(el)
                                return;
                            } else if (firstCheck.is(':checked') === false) {
                                secondCheck.each(function(){
                                    if($(this).is(':checked')){
                                        var el = $('<li class="second_cat">');
                                        el.text($(this).next().text());
                                        ul.append(el)
                                        return;
                                    } 
                                })
                            }
                        });
                        $('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
                        if ($('.cat_table ul li').length === 0) {
                            $('.cat_table').hide();
                        } else {
                            $('.cat_table').show();
                        }
                    $("input[name='recommend_cat_id']").val(cat_array);
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调


                }
            });
        });
    }
});
//一级分类的全选
$('#set-category').on('click','.first_cate input[type="checkbox"]',function () {
    if($(this).is(':checked')){
        $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",true);
    }else{
        $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",false);
    }
});
//二级分类的选中
$('#set-category').on('click','.second_cate .second_box',function () {
    var allLengthS = $(this).parent().parent().parent().find('.second_box').length;
    var checkedLengthS = $(this).parent().parent().parent().find('.second_box:checked').length;
    /* if(allLengthS == checkedLengthS){
        // $(this).parent().parent().parent().prev().find('input[type="checkbox"]').prop('checked',true);
        if(checkedLengthS>0){
            $(this).parent().next().find('input[type="checkbox"]').prop('checked',true);
        }else{
            $(this).parent().next().find('input[type="checkbox"]').prop('checked',false);

        }
    }else{
        if(checkedLengthS>0){
            $(this).parent().next().find('input[type="checkbox"]').prop('checked',true);
        }else{
            $(this).parent().next().find('input[type="checkbox"]').prop('checked',false);
        }
    } */
    /* 二级分类点击，下面所有三级分类被选中 */
    $(this).parent().next().find('input[type="checkbox"]').prop("checked",$(this).prop("checked"));
    /* 二级分类全部选中，一级选中 */
    if(allLengthS === checkedLengthS){
        $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
    } else {
        $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
    }
});
//三级分类的选中
$('#set-category').on('click','.third_cate input[type="checkbox"]',function () {
    $(this).attr("checked", "checked");
    var allLength = $(this).parent().parent().find('input[type="checkbox"]').length;
    var checkedLength = $(this).parent().parent().find('input[type="checkbox"]:checked').length;
    if(allLength === checkedLength){
        $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",true);
    } else {
        $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",false);
    }
    var secondLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]').length;
    var secondCheckedLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]:checked').length;
    /* 三级全部选中 判断二级是否全部选中 */
    if(secondLength === secondCheckedLength){
        $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
    } else {
        $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
    }
});
//点击展开二级和三级分类
$('#set-category').on('click','.cate_open',function(){
    var flag_open = $(this).attr('data-flag');
    if(flag_open == 'true'){
        $(this).parent().next().show();
        $(this).attr('src','/image/admin/cate_jian.png');
        $(this).attr('data-flag','false');
        flag_open = 'false';
    }else if(flag_open == 'false'){
        $(this).parent().next().hide();
        $(this).attr('src','/image/admin/cate_jia.png');
        $(this).attr('data-flag','true');
        flag_open = 'true';
    }
});


//选择商品
$('.choose_goods').on('click',function(){
    var currentTime = (new Date()).valueOf();
    var startTime = $('input[name="start_time"]').val();
    startTime = startTime.replace(/-/g, '/');
    var time = new Date(startTime);
    time = time.getTime();
    var endTime = $('input[name="end_time"]').val();
    endTime = endTime.replace(/-/g, '/');
    var time1 = new Date(endTime);
    time1 = time1.getTime();
    if(time < currentTime && disabled) {
        $('.choose_goods').css('background', '#ebebe4');
        return false;
    }
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        var checkedId1 = $('input[name="recommend_goods_id"]').val();
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','430px']
            , content: '/admin/public/purchase/goods/list?record_select_value='+checkedId1 //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            ,success: function (layero, index) {
                    var goods = $('input[name="recommend_goods_id"]').val();
                    var body = layer.getChildFrame('body', index);
                    if(goods !='') {
                        if(isNaN(goods)) {
                            var goods_array = goods.split(',');
                            body.contents().find("tr").each(function(){
                                if($.inArray($(this).attr("goods_id"),goods_array)>-1){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                        else{
                            body.contents().find("tr").each(function(){
                                if($(this).attr("goods_id")==goods){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                    }
                }
            , yes: function (index, layero) { //保存按钮的回调
                var recommend_goods_id = $("input[name='recommend_goods_id']").val();
                var iframe = layer.getChildFrame('body', index);
                var goods=[];
                var body = layer.getChildFrame('body', index);
                var checkedId = iframe.find('#record_select_value').val();
                $('input[name="recommend_goods_id"]').val(checkedId);
                util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                      console.log(response);
                      var list = response.content;
                      var html = '';
                      for (var i in list) {
                        list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                        html += '<tr>' +
                            '        <td>' +
                            '            <div class="goods_img">' +
                            '                <img src="'+list[i].goods_img+'" />' +
                        '            </div>' +
                        '            <div class="goods_info clearfix">' +
                        '                <div class="goods_name">'+list[i].goods_name+'</div>' +
                        '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                                    '<td>'+list[i].goods_number+'</td>' +
                                    '<td><a href="##" item="'+list[i].prd_id+'" class="change_goods_del">删除</a></td>'
                        '        </td>';
                        html += '</tr>';
                      }
                      $('.goods_modal').css('display','block');
                      $('.goods_modal .tbody').html(html);
                      $('.goods_table').css('display','block');
                      $('.goods_table .tbody').html(html);
                      check_goods_area_height()
                      layer.close(index);

                },{select_id: checkedId});

                // if(goods_id){
                //     $(".goods_table tr:gt(0)").remove();
                //     iframe.contents().find('tr[data-back="false"]').each(function(){
                //         var el = $('.goods_modal_clone').find('tr').clone();
                //         el.removeClass('hide');
                //         goods.push($(this).attr('goods_id'));
                //         el.find('td').eq(0).html($(this).find('td').eq(0).html());
                //         el.find('td').eq(1).text($(this).find('td').eq(2).text());
                //         el.find('td').eq(2).text($(this).find('td').eq(3).text());
                //         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
                //         $('.goods_table tr:first-child').after(el);
                //     });
                //     $('.goods_table').show();
                // }else{
                //     $(".goods_modal tr:gt(0)").remove();
                //     iframe.contents().find('tr[data-back="false"]').each(function(){
                //         var el = $('.goods_modal_clone').find('tr').clone();
                //         el.removeClass('hide');
                //         goods.push($(this).attr('goods_id'));
                //         el.find('td').eq(0).html($(this).find('td').eq(0).html());
                //         el.find('td').eq(1).text($(this).find('td').eq(2).text());
                //         el.find('td').eq(2).text($(this).find('td').eq(3).text());
                //         el.find('.del').attr('goods_id',$(this).attr('goods_id'));
                //         $('.goods_modal tr:first-child').after(el);
                //     });
                //     $('.goods_modal').show();
                // }

                // $("input[name='recommend_goods_id']").val(goods);
                hasSaved = false;
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});

$(document).on('click', '.change_goods_del', function () {
    var good_id = $('input[name="recommend_goods_id"]').val();
    var goodIds = good_id.split(',');
    var index = $.inArray($(this).attr('item'), goodIds);
    if (index) {
        goodIds.splice(index, 1);
    }
    $('input[name="recommend_goods_id"]').val(goodIds.join(','));
    $(this).parent().parent().parent().find('tr').length < 1 ? $('.goods_modal').css('display','none') : $(this).parent().parent().remove();
    if($('.goods_table tbody').find('tr').length < 1){
    $('.goods_table').css('display','none')
    }
    if($('.goods_modal tbody').find('tr').length < 1){
    $('.goods_modal').css('display','none')
    }
    check_goods_area_height()
});

function picker(){
    return WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
}
$('.save').click(function(){
    if(type == 1){
        /*分裂优惠券开始*/
        if($('input[name="activity_names"]').val() == ''){
            util.mobile_alert('请填写活动名称');
            return false;
        }
        if($('input[name="act_start_time"]').val() == ''){
            util.mobile_alert('请填写活动开始时间');
            return false;
        }
        if($('input[name="act_end_time"]').val() == ''){
            util.mobile_alert('请填写活动结束时间');
            return false;
        }
        if($('input[name="act_start_time"]').val()>$('input[name="act_end_time"]').val() ){
            util.mobile_alert('结束时间不能早于开始时间');
            return false;
        }
        if($("select[name='cou_limit']").val() == "0"){
            util.mobile_alert('请填写单次下单可分享优惠券数量');
            return false;
        }
        if($('input[name="least_money"]').val() == ''){
            util.mobile_alert('请填写触发活动金额');
            return false;
        }

        var a1 = $("input[name='act_end_time']").val();
        if($("#radio_goods_one").is(":checked") && $("input[name='end_time']").val() != ""){
            var a2 = $("input[name='end_time']").val();
        }
        if(a1>a2){
            util.mobile_alert('活动结束时间应早于优惠券到期时间！');
            return false;
        }
        if($('input[name="act_start_time"]').val() < act_end_time){
            util.mobile_alert('该时间段存在进行中的活动');
            return false;
        }
        /*分裂优惠券结束*/
    }

    if($('input[name="act_name"]').val() == ''){
        util.mobile_alert('请填写优惠券名称');
        return false;
    }
    if($('input[name="act_code"]:checked').val() == 'voucher'){
        if($('input[name="denomination"]:eq(0)').val() == ''){
            util.mobile_alert('请填写面值');
            return false;
        }
        else{
            $('input[name="denomination"]:eq(1)').prop('disabled','disabled');
        }
    }
    else{
        if($('input[name="denomination"]:eq(1)').val() == ''){
            util.mobile_alert('请填写面值');
            return false;
        }
        else{
            $('input[name="denomination"]:eq(0)').prop('disabled');
        }
    }
    if($('input[name="use_consume_restrict"]:checked').val() == '1' && $('input[name="least_consume"]').val() ==''){
        util.mobile_alert('请填写使用门槛');
        return false;
    }
    if ($('[name="use_score"]:checked').val() == 1 && !($('[name="score_number"]').val() > 0)) {
        util.mobile_alert('请填写兑换积分数');
        return false;
    }
    if($('input[name="radio_date"]:checked').val() == '1'){
        if($('input[name="start_time"]').val() == ''){
            util.mobile_alert('请填写优惠券开始时间');
            return false;
        }
        else if($('input[name="end_time"]').val() == ''){
            util.mobile_alert('请填写优惠券结束时间');
            return false;
        }
        else if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
            util.mobile_alert('开始时间不能大于结束时间');
            return false;
        }
        else{
            $('input[name="validity"]').val('');
        }
    }
    else{
        if($('input[name="validity"]').val() == ''){
            util.mobile_alert('请填写有效时间');
            return false;
        }
        else{
            $('input[name="start_time"]').val('');
            $('input[name="end_time"]').val('');
        }
    }
    if($('input[name="total_amount"]').val() == ''){
        util.mobile_alert('请填写发放总量');
        return false;
    }
    if(Number($('input[name="total_amount"]').attr("amount"))>0 && Number($('input[name="total_amount"]').val()) < Number($('input[name="total_amount"]').attr("amount"))){
        util.mobile_alert('发放总量不得小于已领取和已发放数量之和');
        return false;
    }
    if($('input[name="radio_goods"]:checked').val() == '1'){
        if($('input[name="goods_id"]').val() == ''){
            util.mobile_alert('请选择商品');
            return false;
        }
    }else{
        $('input[name="recommend_goods_id"]').val("");
        $('input[name="recommend_cat_id"]').val("");
        $('input[name="sort_ids"]').val("");
    }
    if($("input[name='is_card_exclusive']").is(":checked") && $('.card-info-row').find('.card_span').length <=0 ){
        util.mobile_alert('请选择专享会员卡');
        return false;
    }
    hasSaved = true;
    $('input').each(function(){
        if( $(this).attr("type")=='radio'){
            $(this).removeAttr('disabled');
        }
    });
    // return;
    layer.ready(function () {
        layer.msg('保存成功', {time: 2000},function () {
            $("#form1").submit();
        });
    });
});
util.inputChange();
util.selectChange();
util.radioChange('suit_goods');
util.radioChange('radio_goods');
util.radioChange('act_code');
util.radioChange('use_consume_restrict');
util.radioChange('radio_date');
window.onbeforeunload = function(event) {
    if(hasSaved == false){
        return '确认要离开吗';
    }
};














