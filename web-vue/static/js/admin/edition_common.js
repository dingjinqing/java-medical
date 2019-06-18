//面包屑上的，优惠券和门店相关
$('.edition_type').click(function(){
    var _html = $(this).html();
    var mod;
    var url = $(this).attr("link");
    var data = {};
    $(this).attr('href','##');
    if(_html == '分裂优惠券'){
        mod = 'coupon_split';
        data = getPowerInfo('main_config',mod,'sub_4',_html);
    }else if(_html == '定向发券'){
        mod = 'coupon_grant';
        data = getPowerInfo('main_config',mod,'sub_4',_html);
    }else if(_html == '活动送券'){
        mod = 'coupon_gift';
        data = getPowerInfo('main_config',mod,'sub_4',_html);
    }else if(_html == '服务管理'){
        mod = 'service';
        data = getPowerInfo('main_config',mod,'sub_5',_html);
    }else if(_html == '技师管理'){
        mod = 'technician';
        data = getPowerInfo('main_config',mod,'sub_5',_html);
    }else{
        data.content = 1;
        data.message = '';
    }
    if(data.content == 1){
        $(this).attr('href',url);
    }
});
//技师管理
$('.edition_tech').click(function(){
    var _html = $(this).attr('title');
    var url = $(this).attr("link");
    $(this).attr('href','##');
    var mod = 'technician';
    var data = getPowerInfo('main_config',mod,'sub_5',_html);
    if(data.content == 1){
        $(this).attr('href',url);
    }
});
//数量
$('.goods_edition').click(function () {
    var _html = $(this).attr('data-title');
    var _name = $(this).attr('data-name');
    //编辑是1，新建是0
    var edit = $(this).attr('edit');
    var url = $(this).attr("link");
    var data = getPowerInfo('num_config',_name,'',_html,1,edit);
    $(this).attr('href','##');
    $(this).removeAttr('target');
    if(data.tip != 1){
        $(this).attr('href',url);
        $(this).attr('target','_blank');
    }
    console.log(data);
});