<html style="height: 320px;">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
</head>
<body style="background:none;height: 450px;font-size: 14px">
     <div id="batch_set_goods" class="clearfix">
         <ul class="bsg_left">
             <li class="each_module each_module_act"><a href="##">上架时间</a></li>
             <li class="each_module"><a href="##">运费模板</a></li>
             <li class="each_module"><a href="##">限购数量</a></li>
             <li class="each_module"><a href="##">商品价格</a></li>
             <li class="each_module"><a href="##">会员专属</a></li>
             <li class="each_module"><a href="##">商品详情</a></li>
         </ul>
         <div class="bsg_right">
            <div class="br_title">
                已选：<a href="##">20</a>件商品
                <span>最多可批量设置20件商品</span>
            </div>
             {{--上下架--!}
             <div class="br_content if_sale clearfix">
                 <div class="bc_left">上下架：</div>
                 <div class="bc_right">
                     <div class="each_label">
                         <label for="in_sale">
                             <input type="radio" name="if_sale" value="0" checked id="in_sale"> 立即上架售卖
                         </label>
                     </div>
                     <div class="each_label">
                         <label for="aftet_sale">
                             <input type="radio" name="if_sale" value="1"  id="aftet_sale"> 自定义上架售卖
                         </label>
                         <label class="choose-time" for="sale_time"  onclick="picker();">选择上架售卖时间</label>
                     </div>
                     <div class="each_label">
                         <label for="no_sale">
                             <input type="radio" name="if_sale" value="2"  id="no_sale"> 暂不售卖，放入仓库
                         </label>
                     </div>
                 </div>
             </div>
             {{--运费模板--!}
             <div class="br_content shipping_fee clearfix">
                 <div class="bc_left">运费模板：</div>
                 <div class="bc_right">
                     <div class="set_model">
                         <select name="" id="" style="width: 155px">
                             <option value="0">店铺默认运费模板</option>
                         </select>

                         <a href="##">刷新</a> |
                         <a href="##">新建模板</a> |
                         <a href="##">管理模板</a>
                     </div>
                     <div class="set_content">
                         <div>店铺默认运费模板：10元</div>
                         <a href="##">查看详情</a>
                     </div>
                 </div>
             </div>
             {{--限购数量--!}
             <div class="br_content limit_buy_num clearfix">
                 <div class="buy_max clearfix">
                     <div class="bm_left">最小限购数量：</div>
                     <div class="bm_right">
                         <input type="text" value="" onkeyup="value=value.replace(/[^\d.]/g,'')">
                         <span class="con_tips">0或不填表示不限制购买数量</span>
                     </div>
                 </div>
                 <div class="buy_max clearfix">
                     <div class="bm_left">最大限购数量：</div>
                     <div class="bm_right">
                         <input type="text" value="" onkeyup="value=value.replace(/[^\d.]/g,'')">
                         <span class="con_tips">0或不填表示不限制购买数量</span>
                     </div>
                 </div>
             </div>
             {{--商品价格--!}
             <div class="br_content goods_money clearfix">
                 <div class="gm_set">
                     批量设置：
                     <a href="##">改价金额</a>
                     <a href="##">折扣</a>
                     <a href="##">涨/降价</a>
                     <span class="con_tips">填写“负值”即减价</span>
                 </div>
                 <div class="set_table">
                     <table width="100%">
                         <thead>
                         <tr>
                             <td width="30%">商品规格</td>
                             <td width="10%">原价</td>
                             <td width="20%">折扣</td>
                             <td width="20%">涨/降价</td>
                             <td width="20%">改价金额</td>
                         </tr>
                         </thead>
                         <tbody>
                         <tr>
                             <td width="30%">
                                 <div class="goods_infos">
                                     <div class="spec_img"><img src="http://${image_domain!}/image/wxapp/img1.jpg" alt=""></div>
                                 </div>
                                 <div class="goods_message">商品名称+商品规格</div>
                             </td>
                             <td width="10%">100.00</td>
                             <td width="20%">
                                 <input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')">折
                             </td>
                             <td width="20%">
                                 <input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')">元
                             </td>
                             <td width="20%">
                                 <input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')">元
                                 <span>价格不得小于0</span>
                             </td>
                         </tr>
                         </tbody>
                     </table>
                 </div>
             </div>
             {{--会员专属--!}
             <div class="br_content just_for_member clearfix">
                 <div class="bc_left" style="width:110px">会员专享商品：</div>
                 <div class="bc_right">
                     <div class="br_top">
                         <input type="checkbox" > 用户持有会员卡才可以购买此商品
                     </div>
                     <div class="set_model">
                         <select name="" id="">
                             <option value="0">请选择会员卡</option>
                         </select>

                         <a href="##">刷新</a> |
                         <a href="##">新建会员卡</a> |
                         <a href="##">管理会员卡</a>
                     </div>
                 </div>
             </div>
             {{--商品详情--!}
             <div class="br_content goods_detail_desc clearfix">
                 <div class="each_gd_line clearfix">
                     <div class="egl_left">模板位置：</div>
                     <div class="egl_right">
                         <label for="auto_top">
                             <input type="radio" value="0" checked id="auto_top"> 自定义内容在上
                         </label>
                         <label for="detail_top">
                             <input type="radio" value="1" id="detail_top"> 商品详情在上
                         </label>
                     </div>
                 </div>
                 <div class="each_gd_line clearfix">
                     <div class="egl_left">自定义内容：</div>
                     <div class="egl_right">
                         <div>设置商品详情页显示的自定义内容</div>
                         <div class="page_info">
                             <div class="page_name">页面名称</div>
                             <img src="http://${image_domain!}/image/admin/dele_service.png" alt="">
                             <div class="btnchoose_page">选择模板</div>
                             <a href="##" class="btn_add_page">添加模板</a>
                         </div>
                     </div>
                 </div>
             </div>
         </div>
     </div>
</body>
</html>
<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    $('.bsg_left li').each(function () {
        var ind = $(this).index();
        $('.bsg_left li').eq(ind).click(function () {
            $('.bsg_left li').removeClass('each_module_act');
            $(this).addClass("each_module_act");
            $(".bsg_right .br_content").css("display","none");
            $(".bsg_right .br_content").eq(ind).css("display","block");
        })
    })


</script>