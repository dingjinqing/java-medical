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
                <input type="hidden" name="type" value="${request['type']!}">
                <input type="hidden" name="shop_id" value="${request['shop_id']!}">
                <input type="hidden" name="activity_type" value="${request['activity_type']!}">
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl select-option1" style="margin-left: 20px">
                            <span>颗粒度</span>
                            <select name="type" class="select_visit_trend" id="">
                            <option value="1" selected>天</option>
                            <option <#if ($request['type'] == 7) selected </#if> value="7">周</option>
                            <option <#if ($request['type'] == 30) selected </#if> value="30">月</option>
                            <option <#if ($request['type'] == 3) selected </#if> value="3">季</option>
                            </select>
                            <input type="text" name="start_time"  id="actstartDate" onfocus="picker()" autocomplete="off" value="${request['start_time']!}"/> 至
                            <input type="text" name="end_time" id="actendDate" value="${request['end_time']!}"onfocus="picker()" autocomplete="off"/>
                            <div class="btn-choose">搜索</div>
                        </div>
                    </li>
                </ul>
            </form>
        </div>
        <div class="many-people use_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>用户数分析</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                    <div class="float-layer common-width-height">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">用户数量</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-use" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>订单数量</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">订单数量</span>
                        </div>
                    </div>
                </span>
            </div>
            <div id="container-order" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_goods_chart" style="display: none">
            <div class="order-people">
                <span class="tab-bar"></span>
                <span>支付金额</span>
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
                <span>商品数量</span>
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
                <span>商品数量</span>
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

        <div class="many-people order_yue_chart1" style="display: none">
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
            <div id="container-yue1" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_card_chart1" style="display: none">
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
            <div id="container-card1" style="height: 400px; width: 100%;"></div>
        </div>
        <div class="many-people order_score_chart1" style="display: none">
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
            <div id="container-score1" style="height: 400px; width: 100%;"></div>
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
    var data = @json($data);
    var date = @json($date);
    console.log(data);
    show_charts(data);
    console.log(data.new_user);
    function show_charts(data) {
        if(data.new_user){
            $(".use_chart").css('display','block');
            var dom = document.getElementById("container-use");
            var myChart = echarts.init(dom);
            var option = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['注册用户','下单用户','微信下单用户','货到付款下单用户','余额下单用户','积分下单用户']
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
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'注册用户',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:data.new_user
                    },{
                        name:'下单用户',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:data.order_user_num
                    },{
                        name:'微信下单用户',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:data.wx_user_num
                    },{
                        name:'货到付款下单用户',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:data.cod_user_num
                    },{
                        name:'余额下单用户',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:data.balance_user_num
                    },{
                        name:'积分下单用户',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0445f5',
                                lineStyle:{
                                    color:'#0445f5'
                                }
                            }
                        },
                        data:data.score_user_num
                    },
                ]
            };
            myChart.setOption(option);
        }

        if(data.order_num){
            $(".order_chart").css('display','block');
            var dom1 = document.getElementById("container-order");
            var myChart1 = echarts.init(dom1);
            var option1 = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:['订单数','微信支付订单','货到付款订单','余额支付订单','积分支付订单']
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
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单数',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#eb757b',
                                lineStyle:{
                                    color:'#eb757b'
                                }
                            }
                        },
                        data:data.order_num
                    },{
                        name:'微信支付订单',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:data.wx_order_num
                    },{
                        name:'货到付款订单',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:data.cod_order_num
                    },{
                        name:'余额支付订单',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:data.balance_order_num
                    },{
                        name:'积分支付订单',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:data.score_order_num
                    },
                ]
            };
            myChart1.setOption(option1);
        }

        if(data.wx_money){
            $(".order_goods_chart").css('display','block');
            var dom2 = document.getElementById("container-goods");
            var myChart2 = echarts.init(dom2);
            var option2 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['微信支付金额','会员卡支付金额','余额支付金额','积分支付金额']
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
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'微信支付金额',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:data.wx_money
                    },{
                        name:'会员卡支付金额',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:data.card_money
                    },{
                        name:'余额支付金额',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:data.balance_money
                    },{
                        name:'积分支付金额',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:data.score_money
                    },
                ]
            };
            myChart2.setOption(option2);
        }

        if(data.order_goods_num){
            $(".order_money_chart").css('display','block');
            var dom3 = document.getElementById("container-money");
            var myChart3 = echarts.init(dom3);
            var option3 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['订单商品','微信支付商品','货到付款商品','余额支付商品','积分支付商品']
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
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:'订单商品',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:data.order_goods_num
                    },{
                        name:'微信支付商品',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ffb57a',
                                lineStyle:{
                                    color:'#ffb57a'
                                }
                            }
                        },
                        data:data.wx_goods_num
                    },{
                        name:'货到付款商品',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#baa3de',
                                lineStyle:{
                                    color:'#baa3de'
                                }
                            }
                        },
                        data:data.cod_goods_num
                    },{
                        name:'余额支付商品',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:data.balance_goods_num
                    },{
                        name:'积分支付商品',
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#0ab3f1',
                                lineStyle:{
                                    color:'#0ab3f1'
                                }
                            }
                        },
                        data:data.score_goods_num
                    },
                ]
            };
            myChart3.setOption(option3);
        }



        if(data.wx_order_balance){
            $(".order_yue_chart1").css('display','block');
            var dom4 = document.getElementById("container-yue1");
            var myChart4 = echarts.init(dom4);
            var option4 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['微信支付','货到付款','余额支付','积分支付']
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
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
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
                        data:data.wx_order_balance
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
                        data:data.cod_order_balance
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
                        data:data.cod_order_balance
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
                        data:data.score_order_balance
                    },
                ]
            };
            myChart4.setOption(option4);
        }

        if(data.wx_order_card){
            $(".order_card_chart1").css('display','block');
            var dom5 = document.getElementById("container-card1");
            var myChart5 = echarts.init(dom5);
            var option5 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['微信支付','货到付款','余额支付','积分支付']
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
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
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
                        data:data.wx_order_card
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
                        data:data.cod_order_card
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
                        data:data.balance_order_card
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
                        data:data.score_order_card
                    },
                ]
            };
            myChart5.setOption(option5);
        }

        if(data.wx_order_score){
            $(".order_score_chart1").css('display','block');
            var dom6 = document.getElementById("container-score1");
            var myChart6 = echarts.init(dom6);
            var option6 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:['微信支付','货到付款','余额支付','积分支付']
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
                    data: date
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
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
                        data:data.wx_order_score
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
                        data:data.cod_order_score
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
                        data:data.balance_order_score
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
                        data:data.score_order_score
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
                if((end_time.getTime()-start_time.getTime())>3600*24*1000*183){
                    util.mobile_alert("时间间隔超过半年，请重新填写");
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