<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/shop_style.css?v=1.0.7" type="text/css" />
<style>
    .pin_group_footer div {
        width: 70px;
        height: 30px;
        border: none;
        background: #5A8BFF;
        color: #fff;
        margin: auto;
        padding-top: 5px;
        cursor: pointer;
    }
    .pin_group_footer div:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }

</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div style="float: left;">
            <span><a>小程序管理</a> / </span>
            <span> <a style="color: #666;">店铺风格</a></span>
        </div>
    </div>
    <div class="main-container ">
        <div class="style_container fix_every_footer">
            <div class="color_title">
                <div style="padding-left: 20px;">店铺配色方案：</div>
                <div class="shop-color <#if ($shop_style[0] == 1 || $shop_style[0] == 0) color_select </#if>" data="#ff6666,#fee7e7" num="1">
                    <span>配色1</span>
                    <span class="ps-color" style="background: #ff6666" value="#ff6666"></span>
                    <span class="ps-color" style="background: #fee7e7" value="#ffd1d1"></span>
                </div>
                <div class="shop-color <#if ($shop_style[0] == 2) color_select </#if>" data="#e53e24,#f2ad3c"  num="2">
                    <span>配色2</span>
                    <span class="ps-color" style="background: #e53e24" value="#e53e24"></span>
                    <span class="ps-color" style="background: #f2ad3c" value="#f2ad3c"></span>
                </div>
                <div class="shop-color <#if ($shop_style[0] == 3) color_select </#if>" data="#7e56c5,#333333"  num="3">
                    <span>配色3</span>
                    <span class="ps-color" style="background: #7e56c5" value="#7e56c5"></span>
                    <span class="ps-color" style="background: #333333" value="#333333"></span>
                </div>
                <div class="shop-color <#if ($shop_style[0] == 4) color_select </#if>" data="#09bb07,#333333"  num="4">
                    <span>配色4</span>
                    <span class="ps-color" style="background: #09bb07" value="#09bb07"></span>
                    <span class="ps-color" style="background: #333333" value="#333333"></span>
                </div>
                <div class="shop-color <#if ($shop_style[0] == 5) color_select </#if>" data="#4a90e2,#dbe9f9"  num="5">
                    <span>配色5</span>
                    <span class="ps-color" style="background: #4a90e2" value="#4a90e2"></span>
                    <span class="ps-color" style="background: #dbe9f9" value="#b7d3f3"></span>
                </div>
                <div class="shop-color <#if ($shop_style[0] == 6) color_select </#if>" data="#feb609,#333333"  num="6">
                    <span>配色6</span>
                    <span class="ps-color" style="background: #feb609" value="#feb609"></span>
                    <span class="ps-color" style="background: #333333" value="#333333"></span>
                </div>
                <div class="shop-color <#if ($shop_style[0] == -1) color_select </#if>" flag="true"  num="-1">
                    <span>自定义</span>
                    <input type="color" class="choose-color ps-color" <#if ($shop_style[0] == -1) value="{{explode(',',$shop_style[1])[0] ?? '#ffffff'!}" <#else> value="#ffffff" </#if>>
                    <input type="color" class="choose-color ps-color" <#if ($shop_style[0] == -1) value="{{explode(',',$shop_style[1])[1] ?? '#ffffff'!}" <#else> value="#ffffff" </#if>>
                </div>
            </div>
            <div class="color_content">
               <div style="padding-left: 22px">当前配色方案示例：</div>
               <div class="shop-img">
                    <div class="my-font icon-m2 set-color1"></div>
                    <div class=" m1-price set-color1 "><span class="set-color1">￥</span>1399</div>
                    <div class=" m1-coupon1 set-color1 set-color6 set-color7 ">满200减10</div>
                    <div class=" m1-coupon2 set-color1 set-color6 set-color7 ">满300减20</div>
                    <div class="m1-add  set-color2">加入购物车</div>
                    <div class="m1-buy set-color5">立即购买</div>
               </div>
                <div class="shop-img">
                    <div class="m2-price set-color1">￥960.00</div>
                    <div class="m2-ys ys1 set-color1 set-color3">黑色</div>
                    <div class="m2-ys ys2 set-color1 set-color3">s</div>
                    <div class="m2-add add1 set-color2">加入购物车</div>
                    <div class="m2-add add2 set-color5">立即购买</div>
                </div>
                <div class="shop-img">
                    <div class="m3-time set-color1">0天0时29分35</div>
                    <div class="m3-kd set-color1 set-color4">快递</div>
                    <div class="m3-price set-color1">￥98.00</div>
                    <div class="my-font icon-m1 set-color1"></div>
                    <div class="m3-minus set-color1">减10元</div>
                    <div class="m3-total set-color1">应付总额：0元</div>
                    <div class="m3-add set-color5 ">提交订单</div>
                </div>
            </div>
        </div>
        <div class="pin_group_footer fix_footer" style="width: 1179px; display: block;">
            <div class="save">保存</div>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    //初始化
    if("${shop_style[0]!}" != ''){
        if($(".color_select").attr('flag') != 'true'){
            var color1 = $(".color_select").find(".ps-color").eq(0).attr('value');
            var color2 = $(".color_select").find(".ps-color").eq(1).attr('value');
        }else{
            var color1 = $(".color_select").find('.choose-color').eq(0).val();
            var color2 = $(".color_select").find('.choose-color').eq(1).val();
        }
        /* color1 主色
           color2 辅色
        */
        var color3 = colorRgb(color1,0.2);//优惠券背景渐变色
        var color4 = colorRgb(color1,0.4);//优惠券边框颜色
        $(document).find('.set-color1').css('color',color1);
        $(document).find('.set-color2').css('background',color2);
        $(document).find('.set-color5').css('background',color1);
        $(document).find('.set-color3').css('border','1px solid'+ color1);
        $(document).find('.set-color4').css('border-bottom','1px solid'+ color1);
        $(document).find('.set-color6').css('background',color3);
        $(document).find('.set-color7').css('border','1px solid '+ color4);
    }

    $(".save").click(function () {
        var data = '';
        if($('.color_select').attr("flag") == "true"){
            data = $('.color_select').find(".choose-color").eq(0).val()+","+$('.color_select').find(".choose-color").eq(1).val();
        }else {
            data = $('.color_select').attr("data");
        }
        var shop_style=[];
        shop_style.push($('.color_select').attr("num"));
        shop_style.push(data);
        util.ajax_json('/admin/manage/decorate/style',function(response){
            if (response.error == 0) {
                util.mobile_alert('保存成功');
                location.reload();
            } else {
                util.mobile_alert(response.message);
            }
        }, {shop_style:shop_style});
    })

 $(".shop-color").click(function () {
     $(".shop-color").removeClass("color_select");
     $(this).addClass("color_select");
     if($(this).attr('flag') != 'true'){
         var color1 = $(this).find(".ps-color").eq(0).attr('value');
         var color2 = $(this).find(".ps-color").eq(1).attr('value');
     }else{
         var color1 = $(this).find('.choose-color').eq(0).val();
         var color2 = $(this).find('.choose-color').eq(1).val();
     }
     /* color1 主色
        color2 辅色
     */
     var color3 = colorRgb(color1,0.2);//优惠券背景渐变色
     var color4 = colorRgb(color1,0.4);//优惠券边框颜色
     $(document).find('.set-color1').css('color',color1);
     $(document).find('.set-color2').css('background',color2);
     $(document).find('.set-color5').css('background',color1);
     $(document).find('.set-color3').css('border','1px solid'+ color1);
     $(document).find('.set-color4').css('border-bottom','1px solid'+ color1);
     $(document).find('.set-color6').css('background',color3);
     $(document).find('.set-color7').css('border','1px solid '+ color4);
 })
$(".choose-color").change(function () {
    var color1 = $(this).parent().find('.choose-color').eq(0).val();
    var color2 =  $(this).parent().find('.choose-color').eq(1).val();
    var color3 = colorRgb(color1,0.2);//优惠券背景渐变色
    var color4 = colorRgb(color1,0.4);//优惠券边框颜色
    $(document).find('.set-color1').css('color',color1);
    $(document).find('.set-color2').css('background',color2);
    $(document).find('.set-color5').css('background',color1);
    $(document).find('.set-color3').css('border','1px solid'+ color1);
    $(document).find('.set-color4').css('border-bottom','1px solid'+ color1);
    $(document).find('.set-color6').css('background',color3);
    $(document).find('.set-color7').css('border','1px solid ' + color4);
})

 function colorRgb(str, opacity) {
     var sColor = str.toLowerCase();
     if (sColor) {
         if (sColor.length === 4) {
             var sColorNew = "#";
             for (var i = 1; i < 4; i += 1) {
                 sColorNew += sColor.slice(i, i + 1).concat(sColor.slice(i, i + 1));
             }
             sColor = sColorNew;
         }
         //处理六位的颜色值
         var sColorChange = [];
         for (var i = 1; i < 7; i += 2) {
             sColorChange.push(parseInt("0x" + sColor.slice(i, i + 2)));
         }
         return "rgba(" + sColorChange.join(",") + "," + opacity + ")";
     } else {
         return sColor;
     }
 };
</script>
