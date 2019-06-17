<#include ("system.header")
<link rel="stylesheet" href="/css/system/shop_fight_group.css?v=1.0.2" type="text/css" />
<style>
    .zenticon-help-circle {
        width: 13px;
        height: 13px;
        vertical-align: middle;
    }
    .float-layer.common-width-height {
        width: 400px;
        height: 130px;
        top: 26px;
        left: -124px;
    }
    .float-layer {
        position: absolute;
        z-index: 9999;
        line-height: 30px;
        font-size: 14px;
        padding: 15px;
        border: 1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150,150,150,0.3);
        border-radius: 5px;
        background: #fff;
        display: none;
    }
    .float-layer .float-layer-left {
        width: 30%;
        float: left;
        display: inline-block;
        color: #999;
    }
    .float-layer .float-layer-right {
        width: 70%;
        display: inline-block;
        color: #353535;
    }
    .float-layer .float-layer-i{
        position: absolute;
        left: 118px;
        top: -12px;
        display: inline-block;
        width: 0px;
        height: 0px;
        border-width: 0px 12px 12px;
        border-style: dashed dashed solid;
        border-color: transparent transparent rgb(255, 255, 255);
    }
    .item-image{
        position: relative;
        display: inline-block;
    }
    .order-people > div, .two-title{
        margin-top: 20px;
        padding-left: 3%;
    }
    .activity-order.float-layer .float-layer-left{
        width: 40%;
    }
    .activity-order.float-layer .float-layer-right{
        width: 45%;
    }
    .activity-order{
        width: 347px !important;
    }
    .select-option1 input{
        width: 140px;
        height: 32px;
        border-radius: 3px;
        border: 1px solid #ccc;
        color: #333;
        font-size: 14px;
        margin-right: 10px;
    }
    .btn-choose{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        margin-left: 15px;
        display: inline-block;
        padding-left: 27px;
        padding-top: 3px;
        cursor: pointer;
    }
</style>

<div style="min-height: 1090px;">
    <div class="shop-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                {{csrf_field()!}
                <input type="hidden" name="type" value="${post_data['type']!}">
                <input type="hidden" name="shop_id" value="${post_data['shop_id']!}">
                <input type="hidden" name="activity_type" value="${post_data['activity_type']!}">
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl select-option1" style="margin-left: 20px">
                            <span>时间筛选</span>
                            {{--<select name="type" class="select_visit_trend" id="">--!}
                                {{--<option value="1">查看1天</option>--!}
                                {{--<option <#if ($post_data['type'] == 7) selected </#if> value="7">最近7天</option>--!}
                                {{--<option <#if ($post_data['type'] == 30) selected </#if> value="30">最近30天</option>--!}
                            {{--</select>--!}
                            <input type="text" name="start_time"  id="actstartDate" onfocus="picker()" autocomplete="off" value="${request['start_time']!}"/> 至
                            <input type="text" name="end_time" id="actendDate" value="${request['end_time']!}"onfocus="picker()" autocomplete="off"/>
                            <div class="btn-choose">搜索</div>
                            {{--<span class="layui-card-item start_date">${start_date!}</span>-<span class="layui-card-item end_date"> ${end_date!}</span>--!}
                        </div>
                    </li>
                </ul>
            </form>
        </div>
        <div class="many-people use_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>订单数量</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                    <div class="float-layer common-width-height">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">订单数量</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-use" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>用户数量</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">用户数量</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-order" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_goods_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>商品数量</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">商品数量</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-goods" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_money_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>支付金额</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">支付金额</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-money" style="height: 400px; width: 100%;"></div>
        </div>

        <div class="many-people order_yue_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>用户余额</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">用户余额</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-yue" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_card_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>卡余额</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">卡余额</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-card" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_score_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>积分金额</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">积分金额</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-score" style="height: 400px; width: 100%;"></div>
        </div>

    </div>
</div>
<script type="text/javascript" src="/js/echarts.min.js"></script>
<script>
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd ',
                autoUpdateOnChanged: false
            }
        );
    }
    var chart_data = @json($data);
    console.log(chart_data);
    show_charts(chart_data);
    function show_charts(chart_data) {
        if(chart_data.use_chart){
            $(".use_chart").css('display','block');
            var dom = document.getElementById("container-use");
            var myChart = echarts.init(dom);
            var option = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['订单汇总','微信支付','货到付款','余额支付','积分支付']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单汇总',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:chart_data.use_chart.total_money
                    },{
                        name:'微信支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:chart_data.use_chart.wxpay
                    },{
                        name:'货到付款',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:chart_data.use_chart.cod
                    },{
                        name:'余额支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.use_chart.balance
                    },{
                        name:'积分支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:chart_data.use_chart.score
                    },
                ]
            };
            myChart.setOption(option);
        }

        if(chart_data.order_chart){
            $(".order_chart").css('display','block');
            var dom1 = document.getElementById("container-order");
            var myChart1 = echarts.init(dom1);
            var option1 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['订单汇总','微信支付','货到付款','余额支付','积分支付']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单汇总',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:chart_data.order_chart.total_money
                    },{
                        name:'微信支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:chart_data.order_chart.wxpay
                    },{
                        name:'货到付款',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:chart_data.order_chart.cod
                    },{
                        name:'余额支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.order_chart.balance
                    },{
                        name:'积分支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:chart_data.order_chart.score
                    },
                ]
            };
            myChart1.setOption(option1);
        }

        if(chart_data.order_goods_chart){
            $(".order_goods_chart").css('display','block');
            var dom2 = document.getElementById("container-goods");
            var myChart2 = echarts.init(dom2);
            var option2 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['订单汇总','微信支付','货到付款','余额支付','积分支付']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单汇总',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:chart_data.order_goods_chart.total_money
                    },{
                        name:'微信支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:chart_data.order_goods_chart.wxpay
                    },{
                        name:'货到付款',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:chart_data.order_goods_chart.cod
                    },{
                        name:'余额支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.order_goods_chart.balance
                    },{
                        name:'积分支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:chart_data.order_goods_chart.score
                    },
                ]
            };
            myChart2.setOption(option2);
        }

        if(chart_data.order_money_chart){
            $(".order_money_chart").css('display','block');
            var dom3 = document.getElementById("container-money");
            var myChart3 = echarts.init(dom3);
            var option3 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['订单汇总','微信支付','货到付款','余额支付','积分支付']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单汇总',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:chart_data.order_money_chart.total_money
                    },{
                        name:'微信支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:chart_data.order_money_chart.wxpay
                    },{
                        name:'货到付款',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:chart_data.order_money_chart.cod
                    },{
                        name:'余额支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.order_money_chart.balance
                    },{
                        name:'积分支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:chart_data.order_money_chart.score
                    },
                ]
            };
            myChart3.setOption(option3);
        }

        if(chart_data.order_yue_chart){
            $(".order_yue_chart").css('display','block');
            var dom4 = document.getElementById("container-yue");
            var myChart4 = echarts.init(dom4);
            var option4 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['订单汇总','微信支付','货到付款','余额支付','积分支付']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单汇总',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:chart_data.order_yue_chart.total_money
                    },{
                        name:'微信支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:chart_data.order_yue_chart.wxpay
                    },{
                        name:'货到付款',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:chart_data.order_yue_chart.cod
                    },{
                        name:'余额支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.order_yue_chart.balance
                    },{
                        name:'积分支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:chart_data.order_yue_chart.score
                    },
                ]
            };
            myChart4.setOption(option4);
        }

        if(chart_data.order_card_chart){
            $(".order_card_chart").css('display','block');
            var dom5 = document.getElementById("container-card");
            var myChart5 = echarts.init(dom5);
            var option5 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['订单汇总','微信支付','货到付款','余额支付','积分支付']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单汇总',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:chart_data.order_card_chart.total_money
                    },{
                        name:'微信支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:chart_data.order_card_chart.wxpay
                    },{
                        name:'货到付款',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:chart_data.order_card_chart.cod
                    },{
                        name:'余额支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.order_card_chart.balance
                    },{
                        name:'积分支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:chart_data.order_card_chart.score
                    },
                ]
            };
            myChart5.setOption(option5);
        }

        if(chart_data.order_score_chart){
            $(".order_score_chart").css('display','block');
            var dom6 = document.getElementById("container-score");
            var myChart6 = echarts.init(dom6);
            var option6 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['订单汇总','微信支付','货到付款','余额支付','积分支付']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单汇总',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:chart_data.order_score_chart.total_money
                    },{
                        name:'微信支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:chart_data.order_score_chart.wxpay
                    },{
                        name:'货到付款',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:chart_data.order_score_chart.cod
                    },{
                        name:'余额支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.order_score_chart.balance
                    },{
                        name:'积分支付',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:chart_data.order_score_chart.score
                    },
                ]
            };
            myChart6.setOption(option6);
        }

    }

    $(".btn-choose ").click(function () {
        if($("input[name='start_time']").val() || $("input[name='end_time']").val()){
            if($("input[name='start_time']").val() && $("input[name='end_time']").val()){
                var t1=$("input[name='start_time']").val();
                var start_time = new Date(t1.replace(/-/g, "/"));//replace方法将-转为/
                var t2=$("input[name='end_time']").val();
                var end_time = new Date(t2.replace(/-/g, "/"));//replace方法将-转为/
                if((end_time.getTime()-start_time.getTime())>3600*24*1000*30*1){
                    util.mobile_alert("时间间隔超过一个月，请重新填写");
                    return false;
                }
            }
        }
        $("#form1").submit();
    })

    $('.item-image').hover(function () {
        $(this).find('.float-layer').show();
    },function () {
        $(this).find('.float-layer').hide();
    });
</script>
<#include ("system.footer")