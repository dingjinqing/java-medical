<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/brand_related.css?v=1.0.0" type="text/css" />
<div class="title">
    <span><a href="/admin/goods/manage/list?top_index=2">商品管理</a> / </span><span>品牌管理 / </span><span>添加品牌</span>
</div>
<div class="main_container">
    <form action="/admin/goods/brand/addBrand" method="POST" id="form1">
        {{ csrf_field()!}
        <input type="hidden" name="act">
        <input type="hidden" name="brand_id">
        <div class="brand_top" style="padding-top:20px;padding-bottom:20px;">
            <ul class="add_brand_config">
                <li class="clearfix">
                    <div class="brand_title">
                        <span class="must">*</span>
                        品牌名称：
                    </div>
                    <div class="config_box">
                        <input type="text" name="brand_name" value="${brand_info->brand_name!}">
                    </div>
                </li>
                <li class="clearfix">
                    <div class="brand_title">
                        <span class="must">*</span>
                        品牌Logo：
                    </div>
                    <div class="config_box">
                        <input type="hidden" name="brand_logo" value="${brand_info->logo!}">
                        <div class="logo_img" <#if ($brand_logo) style="display: block;" </#if>>
                            <img <#if ($brand_logo)src="${brand_logo!}" <#else> src="" </#if> alt="">
                            <span class="choose_img">重新选择</span>
                        </div>
                        <input type="button" value="" class="add_image" <#if ($brand_logo) style="display: none;"<#else> style="display: block;" </#if> >
                    </div>
                </li>
                <li class="clearfix">
                    <div class="brand_title">
                        品牌分类：
                    </div>
                    <div class="config_box">
                        <select name="classify_id" id="brand_classify_id">
                            <option value="0">请选择</option>
                            <#list ($classify_list as $item)
                                <option value="${item->classify_id!}"
                                        <#if ($classify_id == $item->classify_id)selected="selected"</#if>>${item->classify_name!}</option>
                            </#list>
                        </select>
                        <a href="javascript:void(0)" class="refresh-brand">刷新</a>&nbsp;|
                        <a  href="#" class="btn_addClassify" style="color:#5a8bff;">新建品牌分类 </a>&nbsp;|
                        <a href="/admin/goods/brand/classifylist?top_index=2" target="_blank">管理品牌分类 </a>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="brand_title">
                        品牌优先级：
                    </div>
                    <div class="config_box">
                        <input type="text" name="first" value="${brand_info->first!}">
                        <span class="tips" style="margin-left: 15px;">
                            请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前
                        </span>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="brand_title">
                        <span class="must">*</span>
                        设为推荐品牌：
                    </div>
                    <div class="config_box">
                        <input type="radio" name="is_rec" value="1" <#if ($is_recommend == 1) checked </#if>>是
                        <input type="radio" name="is_rec"  value="0" style="margin-left: 25px;" <#if ($is_recommend ==0) checked </#if>>否
                        <span class="tips" style="margin-left: 15px;">
                            设为推荐品牌的，将展示在小程序推荐品牌列表中
                        </span>
                    </div>
                </li>
                <li class="clearfix">
                    <div class="brand_title">
                        添加商品：
                    </div>
                    <div class="config_box">
                        <div class="choose_goods">
                            <img src="http://mpdevimg.weipubao.cn/image/admin/icon_jia.png" alt="">选择商品
                            <input type="hidden" name="goods_id" value="${goods_ids!}" >
                        </div>
                        <span style="margin-left:15px;color:#5a8bff;">已选择商品数量：<span class="goods_num"><#if ($goods_num)${goods_num!}<#else> 0 </#if></span></span>
                    </div>
                </li>
            </ul>
        </div>
    </form>

    
</div>
<div class="pages_bottom">
    <button class="btn_save" style="width: 90px;height: 30px;background: #5a8bff;color: #fff;margin: 20px 0 0 0;border: none;" brand_id = ${brand_info->id!}>保存</button>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script>
$('.refresh-brand').click(function () {
    util.ajax_json('/admin/goods/brand/addBrand',function(d){
        if(d.error!=0){
            util.mobile_alert('刷新失败');
            return false;
        }
        else{
            var classify_list = d.content.classify_list;
            var html = '<option value="0"  selected="selected" >请选择</option>';
            for (var i in classify_list){
                html+='<option value='+classify_list[i].classify_id+'>'+classify_list[i].classify_name+'</option>';
            }
            $('#brand_classify_id').html(html);
            util.mobile_alert('刷新成功');
        }
    },{is_classify:1});
});
//选择商品
$('.choose_goods').on('click',function(){
    layui.use('layer', function() { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer;
        var checkedId1 = $('input[name="goods_id"]').val();
        var goods = [];
        layer.open({
            type: 2
            , title: ['选择商品', 'text-align:center;padding: 0px;']
            , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            , area: ['945px','430px']
            , content: '/admin/public/purchase/goods/list?record_select_value='+checkedId1 //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            , btn: ['确定', '取消']
            , btnAlign: 'r' //按钮居右
            , shade: [0.3, '#000'] //显示遮罩透明度和颜色
            , yes: function (index, layero) { //保存按钮的回调
                var iframe = layer.getChildFrame('body', index);
                var body = layer.getChildFrame('body', index);
                var checkedId = iframe.find('#record_select_value').val(); 
                if (body.find('#record_select_value').val() == '') {
                    util.mobile_alert('请选择商品');
                    return false;
                }
                $('input[name="goods_id"]').val(checkedId);
                // if (body.find('#record_select_value').val().split(',').length > 20) {
                //     util.mobile_alert('最多选择20个商品');
                //     return false;
                // }
                $('.goods_num').text(checkedId.split(',').length)
                layer.close(index);
            }, btn2: function (index, layero) {
                //按钮取消的回调
            }
        });
    });
});

$('.btn_addClassify').click(function(){
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.alert(`<div class="add_class">
                     <p>品牌分类名称：<input type="text" name="add_class"></p>
                     <p>分类优先级：<input type="text" name="add_first" style="margin-left:14px"></p>
                     <p style="margin-left:98px;color:#999">请填写正整数，数值越大，优先级越高，在小程序前端展示位置越靠前</p>
                     </div>
                    `
            , {
                title: ['添加品牌分类', 'text-align:center;padding: 0px;']
                , area: ['400px','265px']
                , closeBtn: 0
                , btnAlign: 'c'
                , btn: ['确定', '取消']
            },function(index){
                let add_class = $('input[name="add_class"]').val()
                let add_first = $('input[name="add_first"]').val()
                util.ajax_json('/admin/goods/brand/classifylist',function (res) {
                    if(res.error == 0){
                        layer.msg('添加品牌分类成功', {time: 500},function () {
                           // window.location.reload();
                        });
                    }else{
                        util.mobile_alert(res.message)
                    }
                },{add_cla:add_class,add_first:add_first});
            });
    });
})
$(".add_image,.choose_img").click(function(){
    var that = $(this);
    $.jImageManager({
        img_width:100,
        img_height:100,
        ok_cb:function(img_arr){
            var path = img_arr[0].img_url;
            if (path == undefined) {
                path = img_arr[0].url;
            }
            $('.logo_img').show()
            $('.logo_img img').attr('src',path);
            $('input[name="brand_logo"]').val(path);
            that.hide();
        }
    });
});
$(document).on('mouseenter','.logo_img',function(){
    $('.logo_img').find('span').show()
});
$(document).on('mouseleave','.logo_img',function(){
    $('.logo_img').find('span').hide()
});
$('.btn_save').click(function(){
    if($('input[name="brand_name"]').val() == ''){
        util.mobile_alert('请填写品牌名称');
        return false;
    }
    if($('input[name="brand_logo"]').val() == ''){
        util.mobile_alert('请上传品牌logo');
        return false;
    }
   var brand_id = $(this).attr('brand_id');
    if(brand_id){
        $('input[name="act"]').val('update');
        $('input[name="brand_id"]').val(brand_id);
    }
    console.log(1);
    $.ajax({
        type: "post",
        url: "/admin/goods/brand/addBrand",
        data: $("#form1").serialize(),
        dataType: "json",

        success: function(data){
            $('#resText').empty();   //清空resText里面的所有内容
            console.log(data)
            if (data.error == 0){
                util.mobile_alert('保存成功');
                setTimeout(() => {
                    window.location.href = "/admin/goods/brand/list";
                }, 1000);
            }else{
                util.mobile_alert(data.message);
            }
        },
        error:function(data){
            util.mobile_alert(data.message);
        }
    });

})

</script>
