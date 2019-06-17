<#include "/admin/header.ftl">
<link href="/css/admin/goods_list.css?v=1.0.6" rel="stylesheet" type="text/css"/>
<style>
    a{
        text-decoration: none;
        color: black;
    }
    .float-layer {
        float: right;
        width: 450px;
        padding: 15px;
        position: absolute;
        left: 0px;
        top:60px;
        z-index: 1;
        border: 1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150,150,150,0.3);
        border-radius: 5px;
        background-color: #fff;
        line-height: 30px;
        display: none;
    }
    .float-layer .float-layer-left {
        width: 27%;
        float: left;
        display: inline-block;
        color: #999;
    }
    .float-layer .float-layer-right {
        width: 70%;
        display: inline-block;
        color: #353535;
    }
    .select_style{
        padding-top:0;
        padding-bottom:0;
        height:42px;
        margin-top:-7px;
    }
    .select_style span{
        font-size:14px;
        color:#999;
    }
    .layui-card .float-layer-i {
        position: absolute;
        left: 112px;
        top: -1px;
        margin-left: -12px;
        margin-top: -12px;
        display: inline-block;
        width: 0;
        height: 0;
        border-width: 12px;
        border-style: dashed;
        border-color: transparent;
        border-top-width: 0;
        border-bottom-color: #fff;
        border-bottom-style: solid;
    }
    #analysis {
        min-width: 1090px;
        margin-top: 10px;
    }
    #analysis .layui-card-header{
        padding:0px 15px;
        height:42px;
        border-bottom:none;
        font-size:16px;
        color:#333;
        margin-left:-15px;
    }
    #goods-summary-table tr td:not(:first-child) {
        text-align: center;
    }
    /* #analysis .layui-card-header span:first-child{
        padding: 0 7px;
        vertical-align: middle;
        border-left: 3px solid rgb(255,109,0);
    } */
    #analysis .layui-card-header .select_visit_trend {
        width: 160px;
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        color: #333;
        font-size: 14px;
        margin-right: 10px;
    }
    #analysis .layui-card{
        padding:0 15px 0 15px;
    }
    #analysis .demo-list .layui-card-item{
        padding:30px 15px 20px 15px;
    }
    
    #analysis .demo-list .layui-card-item p{
        width: 60px;
        padding: 0 15px;
        display: inline-block;
        color: #ccc;
        border-right: 1px dotted #ccc;
    }
    #analysis .demo-list .layui-card-item span.dotted{
        border-left: 1px dotted #ccc;
        height: 35px;
        display: inline-block;
        margin-left: 15px;
    }
    #analysis .demo-list .layui-card-item div{
        display: inline-block;
        margin-left: 20px;
        width:100px;
        position: relative;
    }
    #analysis .demo-list .layui-card-item div+div{
        margin-left: 200px;
    }
    .info_name{
        position: absolute;
        font-size: 12px;
        width: 300px;
        color: rgba(94, 91, 91);
        top: -20px;
    }
    .info_num{
        font-size: 20px;
        font-weight: 400;
    }
    .goods-name a:link{
        color: #333;
        text-decoration: none;
    }
    .goods-name a:visited{
        color: #333;
        text-decoration: none;
    }
    .goods-name a:hover{
        color: #777;
        text-decoration: none;
    }
    .goods-name a:active{
        color: #777;
        text-decoration: none;
    }
    .goods-name .left_shop_img{
        width: 32%;
        height: 100%;
        float: left;
        position: relative;
    }
    .goods-name .left_shop_img img{
        position: absolute;
        top: 0;
        bottom: 0;
        margin: auto;
    }
    .goods-name .right_shop_info{
        width: 68%;
        height: 100%;
        float: left;
        position: relative;
    }
    .goods-name .right_shop_info span{
        text-align: left;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp:2;
        -webkit-box-orient: vertical;
    }
    .goods-name .price{
        font-size: 12px;
        color: red;
        float: left;
        position: absolute;
        bottom: 0;
        left: 0;
    }
    thead>tr>th{
        background-color:#f5f5f5;
        font-size:14px;
        color:#666;
    }
    .title {
        margin-top: -9px;
    }
    .layui-fluid {
        margin-top:10px;
    }
    .clearfix:after{
        content:".";
        display:block;
        height:0;
        clear:both;
        visibility:hidden
    }
    .clearfix{
        overflow:hidden;
        _zoom:1;
    }
    #list-bottom-top .asc-or-desc {
        color: #749dc9;
        cursor: pointer;
    }
    .layui-laypage .layui-laypage-curr .layui-laypage-em{
        background: #5a8bff !important;
        color: #fff;
        border: 1px solid #5a8bff;
        text-decoration: none;
        padding: 0;
    }
    .layui-laypage a, .layui-laypage span{
        border: 1px solid #dedede;
        margin: 0 0px 5px 5px;
        background: rgb(250,250,250);
    }
    .layui-laypage a:hover{
        background: #fff !important;
        color: #5a8bff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }
    .layui-laypage button{
        background: rgb(250,250,250);
    }
    .layui-laypage button:hover{
        background: #fff !important;
        color: #5a8bff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }
    .layui-laypage button:focus{
        background: #5a8bff !important;
        color: #fff;
        border: 1px solid #5a8bff;
        text-decoration: none;
    }
    .btn-excel {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        line-height:25px;
    }
    .btn-excel:hover {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
        width: 85px;
        height: 30px;
    }
    .btn-excel:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
</style>

<div id="analysis">
    <div class="title">
        <div>
            <span><a href="/admin/survey/overview">概况</a> / </span>
            <span style="color: #666;">商品统计</span>
        </div>
    </div>
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card clearfix">
                    <div class="layui-card-header clearfix">
                        <span>商品概况</span>
                    </div>
                    <div class="layui-card-header clearfix select_style">
                        <select name="visit_trend" class="select_visit_trend" onchange="analysis.choose_date(this, 1)">
                            <option value="0">最近1天</option>
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                        </select>
                        <span class="layui-card-item">${startDate!}</span>-
                        <span class="layui-card-item">${endDate!}</span>
                        <select name="sort_id1" class="select_visit_trend select_goods_summary" onchange="analysis.choose_date(this, 1)">
                            <option value="0">请选择商家分类</option>
                            <#list ($sort_list as $sl)
                                <option value="${sl['sort_id']!}">${sl['sort_name']!}</option>
                            </#list>
                        </select>
                        <select name="brand_id1" class="select_visit_trend select_goods_summary" onchange="analysis.choose_date(this, 1)">
                            <option value="0">请选择商品品牌</option>
                            <#list ($brand_list as $bl)
                                <option value="${bl->id!}">${bl->brand_name!}</option>
                            </#list>
                        </select>
                        <select name="goods_label_id1" class="select_visit_trend select_goods_summary" onchange="analysis.choose_date(this, 1)">
                            <option value="0">请选择商品标签</option>
                            <#list ($goodsLabelList as $gl)
                                <option value="${gl->id!}">${gl->name!}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div class="layui-card-item" style="border-bottom:1px solid #ddd">
                            <p>商品分布</p>
                            <div>
                                <span class="info_name">在架商品数</span>
                                <span class="info_num" id="goods_id_number"></span>
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div class="layui-card-item"
                        style="border-bottom:1px solid #ddd">
                            <p>商品访问</p>
                            <div>
                                <span class="info_name">被访问商品数</span>
                                <span class="info_num" id="goods_id_visit"></span>
                            </div>
                            <div>
                                <span class="info_name">商品访客数 (商品UV)</span>
                                <span class="info_num" id="goods_user_visit"></span>
                            </div>
                            <div>
                                <span class="info_name">商品浏览量 (商品pv)</span>
                                <span class="info_num" id="goods_visit"></span>
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list" >
                        <div class="layui-card-item">
                            <p>商品转化</p>
                            <div>
                                <span class="info_name">加购人数</span>
                                <span class="info_num" id="cart_user_number"></span>
                            </div>
                            <div>
                                <span class="info_name">加购件数</span>
                                <span class="info_num" id="cart_goods_number"></span>
                            </div>
                            <div>
                                <span class="info_name">付款商品数</span>
                                <span class="info_num" id="paid_goods_number"></span>
                            </div>
                            <div>
                                <span class="info_name">商品访问付款转化率</span>
                                <span class="info_num" id="goods_ratio"></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12" style="margin-top:-4px;">
                <div class="layui-card clearfix">
                    <div class="layui-card-header clearfix">
                        <span>商品效果</span>
                        <i class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" width="14" height="14"/>
                        </i>
                        <form action="/admin/survey/goods/trades/summary" method="POST" id="form1">
                            {{ csrf_field()!}
                            <input type="hidden" name="act" value="">
                            <input type="hidden" name="type" value="">
                            <input type="hidden" name="action" value="">
                            <input type="hidden" name="asc_or_desc" value="">
                            <input type="hidden" name="sort_id" value="">
                            <input type="hidden" name="brand_id" value="">
                            <input type="hidden" name="goods_label_id" value="">
                            <input type="hidden" name="act_excel" value="">
                        </form>

                    </div>
                    <div class="layui-card-header clearfix select_style" style="margin-bottom:14px;">
                        <select name="visit_trend " class="select_visit_trend select_goods_summary choose_day" onchange="analysis.choose_date(this, 2)">
                            <option value="0">最近1天</option>
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                        </select>
                        <span class="layui-card-item"> ${startDate!}</span>-
                        <span class="layui-card-item"> ${endDate!}</span>
                        <select name="sort_id2" class="select_visit_trend select_goods_summary" onchange="analysis.choose_date(this, 2)">
                            <option value="0">请选择商家分类</option>
                            <#list ($sort_list as $sl)
                                <option value="${sl['sort_id']!}">${sl['sort_name']!}</option>
                            </#list>
                        </select>
                        <select name="brand_id2" class="select_visit_trend select_goods_summary" onchange="analysis.choose_date(this, 2)">
                            <option value="0">请选择商品品牌</option>
                            <#list ($brand_list as $bl)
                                <option value="${bl->id!}">${bl->brand_name!}</option>
                            </#list>
                        </select>
                        <select name="goods_label_id2" class="select_visit_trend select_goods_summary" onchange="analysis.choose_date(this, 2)">
                            <option value="0">请选择商品标签</option>
                            <#list ($goodsLabelList as $gl)
                                <option value="${gl->id!}">${gl->name!}</option>
                            </#list>
                        </select>

                            <button type="button" class="btn-excel" >导出商品</button>

                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">访客数</span>
                            <span class="float-layer-right">统计时间内，商品详情页被访问的去重人数，一个人在统计时间范围内访问多次只记为一个。</span>
                        </div>
                        <div>
                            <span class="float-layer-left">浏览量</span>
                            <span class="float-layer-right">统计时间内，商品详情页被访问的次数，一个人在统计时间内访问多次记为多次。</span>
                        </div>
                        <div>
                            <span class="float-layer-left">加购人数</span>
                            <span class="float-layer-right">统计时间内，添加该商品进入购物车的去重人数，一个人在统计时间内多次添加商品到购物车只记为一人。</span>
                        </div>
                        <div>
                            <span class="float-layer-left">付款人数</span>
                            <span class="float-layer-right">统计时间内，成功付款的客户数（拼团在成团时计入付款订单；货到付款在发货时计入付款订单，不剔除退款订单）</span>
                        </div>
                        <div>
                            <span class="float-layer-left">单品转化率</span>
                            <span class="float-layer-right">统计时间内，商品付款人数/商品访客数。</span>
                        </div>
                        <div>
                            <span class="float-layer-left">付款商品件数</span>
                            <span class="float-layer-right">统计时间内，成功付款订单的商品件数之和（拼团在成团时计入付款订单；货到付款在发货时计入付款订单，不剔除退款订单）</span>
                        </div>
                        <div>
                            <span class="float-layer-left">新成交客户数</span>
                            <span class="float-layer-right">没有购买，在筛选时间内首次在店铺付款的客户数量。</span>
                        </div>
                        <div>
                            <span class="float-layer-left">新成交客户数占比</span>
                            <span class="float-layer-right">新成交客户数/全部成交客户数</span>
                        </div>
                        <div>
                            <span class="float-layer-left">老成交客户数</span>
                            <span class="float-layer-right">购买过，在筛选时间内在店铺再次付款的客户数量</span>
                        </div>
                        <div>
                            <span class="float-layer-left">老成交客户数占比</span>
                            <span class="float-layer-right">老成交客户数/全部成交客户数</span>
                        </div>             
                    </div>
                    <div class="list-bottom">
                        <table align="center" class="list-bottom-show">
                            <thead>
                                <tr id="list-bottom-top">
                                    <th>商品信息</th>
                                    <th asc="2" action="1" class="asc-or-desc checked">访客数<span></span></th>
                                    <th asc="1" action="2" class="asc-or-desc">浏览量<span></span></th>
                                    <th asc="1" action="3" class="asc-or-desc">加购人数<span></span></th>
                                    <th asc="1" action="4" class="asc-or-desc">付款人数<span></span></th>
                                    <th asc="1" action="5" class="asc-or-desc">新成交客户数<span></span></th>
                                    <th asc="1" action="1" >新成交客户数占比</th>
                                    <th asc="1" action="6" class="asc-or-desc">老成交客户数<span></span></th>
                                    <th asc="1" action="1" >老成交客户数占比</th>
                                    <th>商品转化率</th>
                                    <th asc="1" action="7" class="asc-or-desc">付款商品件数<span></span></th>
                                    <th>推荐人数</th>
                                    <th>收藏人数</th>
                                </tr>
                            </thead>
                            <tbody id="goods-summary-table" class=""></tbody>
                            <table style="text-align: right;">
                                <tr>
                                    <td><div id="test1"></div></td>
                                </tr>
                            </table>

                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var laypage;
    layui.use('laypage', function(){
        laypage = layui.laypage;
    });
    var analysis = {
        choose_date: function (obj, action) {
            var val = $(obj).val();
            var day = 1;
            var startDate, endDate, month1, day1, month2, day2;

            if (val == 1) day = 7;
            else if(val == 2) day = 30;

            var curDate = new Date();
            var date = new Date();
            date.setDate(date.getDate() - day);

            month1 = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
            month2 = (curDate.getMonth() + 1) < 10 ? '0' + (curDate.getMonth() + 1) : (curDate.getMonth() + 1);

            day1 = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            day2 = curDate.getDate() < 10 ? '0' + curDate.getDate() : curDate.getDate();

            startDate = date.getFullYear() + "年" + month1 + "月" + day1 + "日";
            endDate = curDate.getFullYear() + "年" + month2 + "月" + day2 + "日";

            $(obj).parent().find('span.layui-card-item').eq(0).html(startDate);
            $(obj).parent().find('span.layui-card-item').eq(1).html(endDate);

            action == 1 ? analysis.goodsSummary(day) : analysis.goodsSingleSummary(day);
        },
        goodsSummary: function (type) {
            var sort_id = $('[name="sort_id1"] option:selected').val();
            var brand_id = $('[name="brand_id1"] option:selected').val();
            var goods_label_id = $('[name="goods_label_id1"] option:selected').val();
            type = arguments[0] == undefined ? 1 : type;
            util.ajax_json('/admin/survey/goods/trades/summary', function (response) {
                if (response.error == 0) {
                    if (!response.content) {
                        $('#goods_id_number, #goods_id_visit, #goods_user_visit, #goods_visit, #cart_user_number, #cart_goods_number, #paid_goods_number').html(0);
                        $('#goods_ratio').html('-');
                    } else {
                        var content = response.content;
                        $('#goods_id_number').html(response.content.goods_id_number);
                        $('#goods_id_visit').html(response.content.goods_id_visit);
                        $('#goods_user_visit').html(response.content.goods_user_visit);
                        $('#goods_visit').html(response.content.goods_visit);
                        $('#cart_user_number').html(response.content.cart_user_number);
                        $('#cart_goods_number').html(response.content.cart_goods_number);
                        $('#paid_goods_number').html(response.content.paid_goods_number);
                        var ratio = content.goods_user_visit == 0 ? 0 : (content.paid_user_number / content.goods_user_visit * 100);
                        $('#goods_ratio').html(ratio.toFixed(2)+'%');
                    }

                } else {
                    util.mobile_alert(response.message);
                }
            }, {act:'goods_summary', type:type, sort_id:sort_id, brand_id:brand_id, goods_label_id:goods_label_id})
        },
        goodsSingleSummary: function (type, page, action, ascOrDesc) {
            var sort_id = $('[name="sort_id2"] option:selected').val();
            // console.log(sort_id);
            var brand_id = $('[name="brand_id2"] option:selected').val();
            var goods_label_id = $('[name="goods_label_id2"] option:selected').val();
            type = arguments[0] == undefined ? 1 : type;
            page = arguments[1] == undefined ? 1 : page;
            action = arguments[2] == undefined ? $('#list-bottom-top .checked').attr('action') : action;
            ascOrDesc = arguments[3] == undefined ? $('#list-bottom-top .checked').attr('asc') : ascOrDesc;
            var jiantou = (ascOrDesc == 2) ? '↓' : '↑';
            util.ajax_json('/admin/survey/goods/trades/summary', function (response) {
                if (response.error == 0) {
                    util.loadPage(response.content.total, response.content.current_page, function (res) {
                        analysis.goodsSingleSummary(type, res.curr, action, ascOrDesc);
                    })
                    var list = response.content.data, html = '';
                    for (var i in list) {
                        var allUser,newUserRatio,oldUserRatio,goodsPayRatio;
                        allUser = list[i].new_user_number + list[i].old_user_number;
                        newUserRatio = allUser == 0 ? 0 : (list[i].new_user_number / allUser * 100);
                        oldUserRatio = allUser == 0 ? 0 : (list[i].old_user_number / allUser * 100);
                        goodsPayRatio = list[i].uv == 0 ? 0 : (list[i].paid_uv / list[i].uv * 100);
                        html += '<tr>'+
                                '<td align="left" class="goods-name" width="20%">' +
                                    '<a>' +
                                    '  <div class="left_shop_img">'+
                                    '     <img src="'+list[i].goods_img+'" alt="" class="name-img">' +
                                    '  </div>' +
                                    '  <div class="right_shop_info">' +
                                    '     <span title="'+list[i].goods_name+'">'+list[i].goods_name+'</span>' +
                                    '   <div class="price"><span>'+list[i].shop_price+'</span></div>' +
                                    '  </div>' +
                                    '</a>' +
                                '</td>' +
                            '<td>'+list[i].uv+'</td>' +
                            '<td>'+list[i].pv+'</td>' +
                            '<td>'+list[i].cart_uv+'</td>' +
                            '<td>'+list[i].paid_uv+'</td>' +
                            '<td>'+list[i].new_user_number+'</td>' +
                            '<td>'+newUserRatio+'%</td>' +
                            '<td>'+list[i].old_user_number+'</td>' +
                            '<td>'+oldUserRatio+'%</td>' +
                            '<td>'+goodsPayRatio.toFixed(2)+'%</td>' +
                            '<td>'+list[i].paid_goods_number+'</td>' +
                            '<td>'+list[i].recommend_user_num+'</td>' +
                            '<td>'+list[i].collect_uv+'</td>' +
                        '</tr>';
                    }
                    if(html == ''){
                        var c = '';
                        c += `<div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-bottom: 10px;float:left;">
                                <div style="width: 30px;height: 33px; margin: 12px auto auto auto" >
                                   <img src="/image/admin/no_data.png" alt="">
                                </div>
                                  <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                           </div>`;
                        if($('#test1').parent().find('.no_data_style').length <= 0){
                            $('#test1').before(c);
                        }
                        $('#test1').hide();
                    }else{
                        if($('#test1').parent().find('.no_data_style').length >= 0){
                            $('#test1').parent().find('.no_data_style').remove();
                        }
                        $('#test1').show();
                    }
                    $('#goods-summary-table').html(html);
                    $('#list-bottom-top .asc-or-desc').removeClass('checked');
                    $('#list-bottom-top').find('[action="'+action+'"]').addClass('checked');
                    $('#list-bottom-top').find('[action="'+action+'"]').attr('asc', ascOrDesc == 1 ? 2 : 1);
                    $('#list-bottom-top .asc-or-desc').find('span').text('');
                    $('#list-bottom-top .asc-or-desc[action="'+action+'"]').find('span').text(jiantou);
                } else {
                    util.mobile_alert(response.message);
                }
            }, {act:'goods_single_summary', type:type, page:page, action: action, asc_or_desc: ascOrDesc, sort_id:sort_id, brand_id:brand_id, goods_label_id:goods_label_id})
        },
    };
    $(document).ready(function (){
        analysis.goodsSummary();
        analysis.goodsSingleSummary();
    })
    $(".btn-excel").click(function () {
        var sort_id = $('[name="sort_id2"] option:selected').val();
        var action =2;
        // console.log(sort_id);
        var brand_id = $('[name="brand_id2"] option:selected').val();
        var goods_label_id = $('[name="goods_label_id2"] option:selected').val();
        var val = $('.choose_day').val();
        var type = 1;
        if (val == 1) type = 7;
        else if(val == 2) type = 30;
        var ascOrDesc = arguments[3] == undefined ? $('#list-bottom-top .checked').attr('asc') : ascOrDesc;
        $("input[name='act']").val('goods_single_summary');
        $("input[name='type']").val(type);
        $("input[name='action']").val(action);
        $("input[name='asc_or_desc']").val(ascOrDesc);
        $("input[name='sort_id']").val(sort_id);
        $("input[name='brand_id']").val(brand_id);
        $("input[name='act_excel']").val('excel');
        $("#form1").submit();
        // util.ajax_json('/admin/survey/goods/trades/summary', function (response) {
        //     if (response.error == 0) {
        //       alert('正在导出');
        //     } else {
        //         util.mobile_alert(response.message);
        //     }
        // }, {act:'goods_single_summary', type:type, action: action, asc_or_desc: ascOrDesc, sort_id:sort_id, brand_id:brand_id, goods_label_id:goods_label_id,act_excel:'excel'})
    }),


    $('#list-bottom-top .asc-or-desc').click(function () {
        var val = $('.select_goods_summary').val();
        var type = 1;
        if (val == 1) type = 7;
        else if(val == 2) type = 30;
        analysis.goodsSingleSummary(type, 1, $(this).attr('action'), $(this).attr('asc'));
    })

    $('.layui-card .item-image, .layui-card .float-layer').hover(function () {
        $(this).parent().parent().find('.float-layer').show();
    },function () {
        $(this).parent().parent().find('.float-layer').hide();
    })

</script>
<#include "/admin/footer.ftl">