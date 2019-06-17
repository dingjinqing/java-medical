<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/layui_change.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/analysis_user_summary.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/analysis_trades_summary.css" type="text/css" />
<style>
    .asc-or-desc {
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
</style>
<div id="analysis">
    <div class="title" >
            <span><a href="/admin/survey/overview?top_index=0">概况</a> / </span>
            <span style="color: #666;">交易统计 </span>
        </div>
    <div class="layui-fluid">
        <div class="layui-one">
            <div class="layui-row layui-col-md12 customer-trend">
                <div class="layui-card">
                    <div class="layui-col-md12 tendency">
                        <i class="left_bat"></i>
                        <span>地域分布</span>
                        <span class="item-image" style="display:inline-block">
                            <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                            <div class="float-layer" style="width:500px; height:280px; top:42px; left:-8px">
                                <div class="float-layer-i" style="left:74px; top: -12px"></div>
                                <div>
                                     <span class="float-layer-left">付款金额</span>
                                     <span class="float-layer-right">统计时间内，该地域访问用户的所有付款订单金额之和（拼团在成团时计入付款金额；货到付款在发货时计入付款金额，不剔除退款金额）</span>
                                </div>
                                <div>
                                     <span class="float-layer-left">付款人数</span>
                                     <span class="float-layer-right">统计时间内，该地域访问用户中下单并且付款成功的客户数，一人多次付款记为一人（不剔除退款订单）</span>
                                </div>
                                <div>
                                     <span class="float-layer-left">访客数</span>
                                     <span class="float-layer-right">统计时间内，该地域访问用户的所有访客数</span>
                                </div>
                                 <div>
                                     <span class="float-layer-left">访问-付款转化率</span>
                                     <span class="float-layer-right">统计时间内，该地域付款人数/该地域访客数</span>
                                </div>
                                 <div>
                                     <span class="float-layer-left">订单数</span>
                                     <span class="float-layer-right">统计时间内，该地区收货订单数 </span>
                                </div>
                            </div>
                        </span>
                    </div>
                    <div class="layui-col-md12">
                        <div class="layui-row">
                            <input type="text" class="layui-input" id="map-year" value="{{ date('Y-m')!}">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-two" style="margin-top: 10px; height: 570px;">
            <div class="layui-row layui-col-space12 demo-list">
                <div id="echart-analysis-map" style="width: 45%; height: 500px; float: left;"></div>
                <table class="layui-table main-table">
                    <thead>
                        <tr id="th-color">
                            <th>Top省份</th>
                            <th>付款金额</th>
                            <th>付款人数</th>
                            <th>访客数</th>
                            <th>访问-付款转化率</th>
                            <th>订单数</th>
                        </tr>
                    </thead>
                    <tbody id="map-year-table"></tbody>
                </table>
            </div>
        </div>
        <div class="layui-four">
            <div class="layui-row layui-col-md12 customer-trend">
                <div class="layui-card">
                    <div class="layui-col-md12 tendency">
                        <i class="left_bat"></i>
                        <span>标签成交分析</span>
                        <span class="item-image" style="display:inline-block">
                            <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                            <div class="float-layer" style="height: 400px; width: 500px;top: 39px;left: -9px; padding:10px">
                                <div class="float-layer-i" style="left:102px;"></div>
                                <div>
                                    <span class="float-layer-left">付款笔数</span>
                                    <span class="float-layer-right">统计时间内，该标签下客户的成功付款订单数，一个订单对应唯一一个订单号（拼团在成团时计入付款订单；货到付款在发货时计入付款订单，不剔除退款订单）
                                    </span>
                                </div>
                                <div>
                                    <span class="float-layer-left">付款金额（元）</span>
                                    <span class="float-layer-right">统计时间内，该标签下客户的所有付款订单金额之和（拼团在成团时计入付款金额；货到付款在发货时计入付款金额，不剔除退款金额）
                                    </span>
                                </div>
                                <div>
                                    <span class="float-layer-left">付款人数</span>
                                    <span class="float-layer-right">统计时间内，该标签下客户下单并且付款成功的客户数，一人多次付款记为一人（不剔除退款订单）</span>
                                </div>
                                <div>
                                    <span class="float-layer-left">付款商品件数</span>
                                    <span class="float-layer-right">统计时间内， 该标签下客户成功付款订单的商品件数之和（不剔除退款订单）</span>
                                </div>
                                <div>
                                    <span class="float-layer-left">有手机号客户数</span>
                                    <span class="float-layer-right">统计时间内， 该标签下下单并且付款成功的有手机号码的客户数（不剔除退款订单）</span>
                                </div>
                            </div>
                        </span>
                    </div>
                    <div class="layui-col-md12" style="margin-top: -12px;">
                        <div class="layui-row">
                            时间筛选
                            <select name="user-date" class="select_visit_trend select-tag" lay-verify="" onchange="analysis.choose_date(this)">
                                <option value="0">最近1天</option>
                                <option value="1">最近7天</option>
                                <option value="2">最近30天</option>
                            </select>
                            <span class="layui-card-item">${startDate!}</span> -
                            <span class="layui-card-item">${endDate!}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="goods-box-edit">
                <table class="tb-decorate-list" id="tag-list-table">
                    <thead>
                    <tr>
                        <th>标签</th>
                        <th asc="2" action="1" class="asc-or-desc checked">用户数<span></span></th>
                        <th asc="1" action="2" class="asc-or-desc">有手机号客户数<span></span></th>
                        <th asc="1" action="3" class="asc-or-desc">付款笔数<span></span></th>
                        <th asc="1" action="4" class="asc-or-desc">付款金额（元）<span></span></th>
                        <th asc="1" action="5" class="asc-or-desc">付款人数<span></span></th>
                        <th asc="1" action="6" class="asc-or-desc">付款商品件数<span></span></th>
                    </tr>
                    </thead>
                    <tbody id="analysis-tag-table"></tbody>
                </table>
                <div id="test1" style="text-align: right; margin-right: 12px;"></div>
            </div>
        </div>
    </div>
</div>


<script language="JavaScript" src="/js/echarts.min.js"></script>
<script language="JavaScript" src="/js/echarts.china.js"></script>
<script src="/js/layui/layui.js?v=1.0.8" type="text/javascript"></script>

<script>
    var mapData, mapDataMin, mapDataMax;
    var analysis = {
        initEchartMap: function () {
            var echartAnalysisMap = echarts.init(document.getElementById('echart-analysis-map'));
            var echartAnalysisMapOption = {
                tooltip: {
                    show: true,
                    formatter: function(params) {
                        if (params.name != '')  {
                            return params.name + '：' + params.value;
                        }
                    },
                },
                visualMap: {
                    type: 'continuous',
                    orient: 'horizontal',
                    text: [parseFloat(mapDataMax), parseFloat(mapDataMin)],
                    min: parseFloat(mapDataMin),
                    max: parseFloat(mapDataMax),
                    showLabel: true,
                    seriesIndex: [0],
                    inRange: {
                        color: ['#aad5ff', '#ccc', '#adcdef', '#71a8e3'],
                    },
                    itemHeight: '350',
                    textStyle: {
                        color: '#000'
                    },
                    bottom: 0,
                    x: 'center'
                },
                xAxis: {
                    type: 'value',
                    scale: true,
                    position: 'top',
                    splitNumber: 1,
                    boundaryGap: false,
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        margin: 2,
                        textStyle: {
                            color: '#aaa'
                        }
                    }
                },
                yAxis: {
                    type: 'category',
                    nameGap: 16,
                    axisLine: {
                        show: false,
                        lineStyle: {
                            color: '#ddd'
                        }
                    },
                    axisTick: {
                        show: false,
                        lineStyle: {
                            color: '#ddd'
                        }
                    },
                    axisLabel: {
                        interval: 0,
                        textStyle: {
                            color: '#999'
                        }
                    }
                },
                geo: {
                    //roam: true,
                    map: 'china',
                    left: 'center',
                    layoutSize: '80%',
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        emphasis: {
                            areaColor: '#fff464'
                        }
                    },
                    regions: [{
                        name: '南海诸岛',
                        value: 0,
                        itemStyle: {
                            normal: {
                                opacity: 0,
                                label: {
                                    show: false
                                }
                            }
                        }
                    }],
                },
                series: [{
                    name: 'mapSer',
                    type: 'map',
                    roam: false,
                    geoIndex: 0,
                    label: {
                        show: false,
                    },
                    data: mapData
                }]
            };
            echartAnalysisMap.setOption(echartAnalysisMapOption);
        },
        provinceTradesSummary: function (refDate) {
            refDate = arguments[0] == undefined ? $('#map-year').val() : refDate;
            util.ajax_json('/admin/survey/trades/summary', function (response) {
                if (response.error == 0) {
                    var list = response.content.list, html = '';
                    if (!list) {
                        $('#map-year-table').html('');
                        mapData = [];
                        mapDataMax = mapDataMin = 0;
                        analysis.initEchartMap();
                        util.mobile_alert('暂无相关数据');
                        var c = '';
                        c += `<div class="no_data_style" style="width: 550%;border: 1px solid #eee;height: 100px;margin-bottom: 10px;margin-top: 10px;float:left;">
                                <div style="width: 30px;height: 33px; margin: 12px auto auto auto" >
                                   <img src="/image/admin/no_data.png" alt="">
                                </div>
                                  <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                           </div>`;
                        if($('#map-year-table').find('.no_data_style').length <= 0){
                            $('#map-year-table').append(c);
                        }
                        return false;
                    }
                    for (var i in list) {
                        if (parseInt(list[i].uv) < parseInt(list[i].pay_user_num)) {
                            list[i].uv = list[i].pay_user_num + 1;
                            list[i].uv_pay_ratio = list[i].pay_user_num / list[i].uv * 100;
                        }
                        html += '<tr>' +
                            '<td>'+list[i].province+'</td>'+
                            '<td>'+list[i].pay_order_money.toFixed(2)+'</td>'+
                            '<td>'+list[i].pay_user_num+'</td>'+
                            '<td>'+list[i].uv+'</td>'+
                            '<td>'+list[i].uv_pay_ratio.toFixed(2)+'%</td>'+
                            '<td>'+list[i].order_num+'</td>'+
                            '</tr>';
                    }
                    mapData = response.content.map_data;
                    mapDataMax = response.content.max;
                    mapDataMin = response.content.min;
                    analysis.initEchartMap();
                    $('#map-year-table').html(html);
                } else {
                    util.mobile_alert(response.message);
                }
            }, {act:'province_trades_summary', ref_date:refDate})
        },
        tagTradesSummary: function (type, page, action, ascOrDesc) {
            type = arguments[0] == undefined ? 1 : type;
            page = arguments[1] == undefined ? 1 : page;
            action = arguments[2] == undefined ? $('#tag-list-table .checked').attr('action') : action;
            ascOrDesc = arguments[3] == undefined ? $('#tag-list-table .checked').attr('asc') : ascOrDesc;
            var jiantou = (ascOrDesc == 2) ? '↓' : '↑';
            util.ajax_json('/admin/survey/trades/summary', function (response) {
                if (response.error == 0) {
                    util.loadPage(response.content.total, response.content.current_page, function (res) {
                        analysis.tagTradesSummary(type, res.curr);
                    })
                    var list = response.content.data, html = '';
                    for (var i in list) {
                        html += '<tr>' +
                            '<td>'+list[i].tag+'</td>'+
                            '<td>'+list[i].has_user_num+'</td>'+
                            '<td>'+list[i].has_mobile_num+'</td>'+
                            '<td>'+list[i].pay_order_num+'</td>'+
                            '<td>'+parseFloat(list[i].pay_order_money).toFixed(2)+'</td>'+
                            '<td>'+list[i].pay_user_num+'</td>'+
                            '<td>'+list[i].pay_goods_number+'</td>'+
                        '</tr>';
                    }
                    if(html == ''){
                        var c = '';
                        c += `<div class="no_data_style" style="width: 98%;border: 1px solid #eee;height: 100px;margin-bottom: 10px;margin-left: 18px;margin-top: 10px;">
                                <div style="width: 30px;height: 33px; margin: 12px auto auto auto" >
                                   <img src="/image/admin/no_data.png" alt="">
                                </div>
                                  <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                           </div>`;
                        if($('#test1').parent().find('.no_data_style').length <= 0){
                            $('#test1').before(c);
                        }
                        $('#test1').hide();
                    }
                    $('#analysis-tag-table').html(html);
                    $('#tag-list-table .asc-or-desc').removeClass('checked');
                    $('#tag-list-table').find('[action="'+action+'"]').addClass('checked');
                    $('#tag-list-table').find('[action="'+action+'"]').attr('asc', ascOrDesc == 1 ? 2 : 1);
                    $('#tag-list-table .asc-or-desc').find('span').text('');
                    $('#tag-list-table .asc-or-desc[action="'+action+'"]').find('span').text(jiantou);
                } else {
                    util.mobile_alert(response.message);
                }
            }, {act:'tags_trades_summary', type:type, action: action, page:page, asc_or_desc:ascOrDesc})
        },
        choose_date: function (obj) {
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

            analysis.tagTradesSummary(day);
        },
    };
    analysis.provinceTradesSummary();
    analysis.tagTradesSummary();

    $('#tag-list-table .asc-or-desc').click(function () {
        var val = $('.select-tag').val();
        var type = 1;
        if (val == 1) type = 7;
        else if(val == 2) type = 30;
        analysis.tagTradesSummary(type, 1, $(this).attr('action'), $(this).attr('asc'));
    })

    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#map-year',
            type: 'month',
            done: function(value, date) {
                analysis.provinceTradesSummary(value);
            }
        });
    });

    $('.layui-card .item-image').hover(function () {
        $(this).find('.float-layer').show();
    },function () {
        $(this).find('.float-layer').hide();
    });
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','trades_summary','sub_0','交易统计',0);
</script>

