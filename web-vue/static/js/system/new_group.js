//选择商品
$('.choose_goods').click(function(){
  var checkedId = $('input[name="goods_id"]').val();
  layui.use('layer', function() { //独立版的layer无需执行这一句
      var $ = layui.jquery, layer = layui.layer;
      layer.open({
        type: 2
        , title: ['选择商品', 'text-align:center;padding: 0px;']
        , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
        , area: ['945px','450px']
        , content: '/admin/public/purchase/goods/list?record_select_value='+checkedId //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        , btn: ['确定', '取消']
        , btnAlign: 'r' //按钮居右
        , shade: [0.3, '#000'] //显示遮罩透明度和颜色
        ,success: function (layero, index) {

        }
        , yes: function (index, layero) { //保存按钮的回调
          var body = layer.getChildFrame('body', index);
          var checkedId = body.find('#record_select_value').val();
          $('input[name="goods_id"]').val(checkedId);
          if (body.find('#record_select_value').val() == '') {
              util.mobile_alert('请选择商品');
              return false;
          }
          if (body.find('#record_select_value').val().split(',').length > 20) {
              util.mobile_alert('最多选择20个商品');
              return false;
          }
          util.ajax_json('/admin/public/purchase/goods/list', function (response) {
            var list = response.content;
            var html = '';
            for (var i in list) {
              html += '<tr>' +
                  '        <td>' +
                  '            <div class="goods_img">' +
                  '                <img src="'+list[i].goods_img+'" />' +
              '            </div>' +
              '            <div class="goods_info clearfix">' +
              '                <div class="goods_name">'+list[i].goods_name+'</div>' +
              '            </div>' +'<td>￥'+list[i].shop_price+'</td>' +
                          '<td>'+list[i].goods_number+'</td>' +
                          '<td><a href="##" item="'+list[i].goods_id+'" class="del_goods">删除</a></td>'
              '        </td>';
              html += '</tr>';
              }
              $('#main_goods_list').html(html);                                                               
              $('.main_goods_table').css('display', 'block');
              layer.close(index);
          }, {select_id: body.find('#record_select_value').val()})
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
});

$(document).on('click', '.reduce_price_footer .save', function () {
  if($('input[name="name"]').val() == ''){
      util.mobile_alert('请填写名称');
      return false;
  }
  if($('input[name="start_time"]').val() == ''){
      util.mobile_alert('请填写开始时间');
      return false;
  }
  if($('input[name="end_time"]').val() == ''){
      util.mobile_alert('请填写结束时间');
      return false;
  }
  if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
      util.mobile_alert('开始时间不能大于结束时间');
      return false;
  }
  if($('input[name="min_join_num"]').val() == '') {
    util.mobile_alert('请填写开奖需要的最少参与人数');
    return false;
  }
  if($('input[name="pay_money"]').val() == '') {
    util.mobile_alert('请填写商品参与抽奖需支付金额');
    return false;
  }
  if($('input[name="join_limit"]').val() == '') {
    util.mobile_alert('参团限制不可为空');
    return false;
  }
  if($('input[name="open_limit"]').val() == '') {
    util.mobile_alert('开团限制不可为空');
    return false;
  }
  if($('input[name="limit_amount"]').val() == '') {
    util.mobile_alert('请填写最少成团人数');
    return false;
  }
  if($('input[name="to_num_show"]').val() == '') {
    util.mobile_alert('活动参与人数限制不能为空');
    return false;
  }
  if($('input[name="goods_id"]').val() == '') {
    util.mobile_alert('请选择商品');
    return false;
  } 
  layer.ready(function () {
      layer.msg('保存成功', {time: 2000},function () {
          $("#form1").submit();
      });
  });
});
$('#main_goods_list').on('click', '.del_goods', function () {
  var goods_id = $('input[name="goods_id"]').val();
  var goods_arr =  goods_id.split(',');
  var index = $.inArray($(this).attr('item'), goods_id.split(','))
  if (index > -1) {
      goods_arr.splice(index, 1);
  }
  $(this).parent().parent().remove();
  $('input[name="goods_id"]').val(goods_arr.join(','));
  if($('#main_goods_list tr').length < 1) {
    $('.main_goods_table').css('display', 'none');
  }
})

$('.view_examples').mouseover(function () {
  $('.show_img').css('display', 'block');
  $('.show_img').mouseover(function () {
    $('.show_img').css('display', 'block');
  })
  $('.show_img').mouseleave(function () {
    $('.show_img').css('display', 'none');
  })
})

$('.view_examples').mouseleave(function () {
  $('.show_img').css('display', 'none');
})