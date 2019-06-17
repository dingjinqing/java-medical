<#include "/admin/header.ftl">
<script language="JavaScript" src="/js/echarts.min.js"></script>
<style>
    #analysis {
        min-width: 1090px;
        margin-top: 10px;
    }
    #analysis .layui-fluid {

    }
    #analysis .demo-list .layui-card{
        margin: 20px 0;
        border-right: 1px solid #e7e7eb;
        text-align: center;
        -webkit-box-shadow: none;
        -moz-box-shadow: none ;
        box-shadow: none;
    }
    #analysis .layui-card .layui-card-header {
        font-size: 16px;
        border: none;
        color: #333;
    }
    #analysis .layui-card .layui-card-header .item-image {
        float: right;
        margin-top: 12px;
    }
    .float-layer {
        float: right;
        width: 420px;
        padding: 15px;
        position: absolute;
        right: 8px;
        z-index: 9999;
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
        width: 25%;
        float: left;
        display: inline-block;
        color: #999;
    }
    .float-layer .float-layer-right {
        width: 70%;
        display: inline-block;
        color: #353535;
    }
    .l-float .float-layer {
        float: right;
        width: 420px;
        padding: 15px;
        position: absolute;
        left: 8px;
        z-index: 2;
        border: 1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150,150,150,0.3);
        border-radius: 5px;
        background-color: #fff;
        line-height: 30px;
        display: none;
    }
    .l-float .float-layer .float-layer-left {
        width: 25%;
        float: left;
        display: inline-block;
        color: #999;
    }
    .l-float .float-layer .float-layer-right {
        width: 70%;
        display: inline-block;
        color: #353535;
    }
    .l-float .float-layer .float-layer-i {
        position: absolute;
        left: 88px;
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
    .layui-card .float-layer-i {
        position: absolute;
        right: 10px;
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
    #analysis .layui-card .item-color-red {
        color: #e15f63;
    }
    #analysis .layui-card .item-color-blue {
        color: #5a8bff;
    }
    #analysis .layui-card-item {
        color: #999;
        font-size: 14px;
    }
    #analysis .layui-card-num {
        padding-top: 5px;
        font-size: 24px;
    }
    #analysis .layui-card-header .select_visit_trend {
        width: 160px;
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        color: #333;
        font-size: 14px;
        margin-right: 10px;
    }
    #analysis .layui-card-header .select_visit_trend.middle {
        width: 235px;
        padding-left: 6px;
    }
    #analysis .layui-table tr th:not(:first-child), #analysis .layui-table tr td:not(:first-child){
        text-align: center;
    }

    /* #analysis .data_overview .layui-card-header > span:first-child{
        padding: 0 7px;
        vertical-align: middle;
        border-left: 3px solid rgb(255,109,0);
    } */
    .data_overview .data_pv_info li{
        float: left;
        box-sizing: border-box;
        border:1px solid #ccc;
        padding:20px 30px 20px 80px;
        background-color:#fff;
        position: relative;
    }
    .data_overview .data_pv_info li:nth-child(2){
        margin-left: -1px;
    }
    .data_overview .data_pv_info li:nth-child(3){
        margin-top: -1px;
    }
    .data_overview .data_pv_info li:nth-child(4){
        margin:-1px 0 0 -1px;
    }
    .data_overview .data_pv_info li::after{
        content: " ";
        position: absolute;
        display: none;
        right: 0;
        bottom: 0;
        width: 16px;
        height: 16px;
        background-size: 16px 16px;
        background-image: url("/image/admin/basic_choice.png")
    }
    .data_overview .data_pv_info li.active{
        border-color:#0a87ff;
        z-index: 1;
    }
    .data_overview .data_pv_info li.active::after{
        display: block;
    }
    .data_overview .data_pv_info li div{
        margin: 15px;
    }
    .data_overview .data_pv_info li div:nth-child(1){
        font-size: 14px;
        color: black;
        margin-bottom: 5px;
    }
    .data_overview .data_pv_info li div:nth-child(2){
        font-size: 20px;
        margin:0 30px 0 15px;
    }
    .data_overview .data_pv_info li div:nth-child(3){
        margin-top: 5px;
        font-size: 12px;
        color: #9c9993;
    }
    /* #analysis .core_index .layui-card-header > span:first-child{
        padding: 0 7px;
        vertical-align: middle;
        border-left: 3px solid rgb(255,109,0);
    } */
    .layui-card-header input[type="text"]{
        width: 150px;
        height: 30px;
        border-radius: 2px;
        border: 1px solid #9c9993;
        padding-left: 10px;
        font-size: 14px;
    }
    .core_index .layui-card-header div{
        margin-right: 50px;
    }
    .core_index ul{
        padding: 50px 60px 30px;
    }
    .core_index ul li{
        float: left;
        box-sizing: border-box;
        width: 25%;
        padding: 26px 0 26px 40px;
        position: relative;
        border:1px solid #eee;
        margin:0 0 0 -1px;
        z-index: 0;
    }
    .core_index ul li:hover{
        cursor: pointer;
    }
    .core_index ul li div:nth-child(3){
        color: #9c9993;
    }
    .core_index ul li div:nth-child(4){
        color: #9c9993;
    }
    .core_index ul li div{
        margin-bottom: 5px;
    }
    .core_index ul li::after{
        content: " ";
        position: absolute;
        display: none;
        right: 0;
        bottom: 0;
        width: 16px;
        height: 16px;
        background-size: 16px 16px;
        background-image: url("/image/admin/basic_choice.png")
    }
    .core_index ul li.active{
        border:1px solid #0a87ff;
        z-index: 1;
    }
    .core_index ul li.active::after{
        display: block;
    }
    .core_index ul li i{
        font-style: normal;
    }
    .core_index ul li i.top {
        color: #ff0000;
    }
    .core_index ul li i.down{
        color: #2fae44;
    }
    .title{
        margin-top:-9px;
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
</style>

<div id="analysis">
    <div class="title">
        <div>
            <span><a href="/admin/survey/overview?top_index=0">概况</a> / </span>
            <span style="color: #666;">实时概况</span>
        </div>
    </div>
    <div class="layui-fluid" style="margin-top:10px">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12 data_overview">
                <div class="layui-card l-float">
                    <div class="layui-card-header">
                        <span>实时概况</span>
                        <i>
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" width="14" height="14" alt="" style="vertical-align: inherit;">
                        </i>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">付款金额(元)：</span>
                            <span class="float-layer-right">统计时间内，所有付款订单金额之和</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访客数：</span>
                            <span class="float-layer-right">0点截至当前时间，页面被访问的去重人数，一个人在统计时间范围内访问多次只记为一个</span>
                        </div>
                        <div>
                            <span class="float-layer-left">浏览量：</span>
                            <span class="float-layer-right">0点截至当前时间，页面被访问的次数，一个人在统计时间内访问多次记为多次</span>
                        </div>
                        <div>
                            <span class="float-layer-left">付款订单数：</span>
                            <span class="float-layer-right">0点截至当前时间，成功付款的订单数，一个订单对应唯一一个订单号（拼团在成团时计入付款订单；货到付款在发货时计入付款订单）</span>
                        </div>
                        <div>
                            <span class="float-layer-left">付款人数：</span>
                            <span class="float-layer-right">0点截至当前时间，下单并且付款成功的客户数，一人多次付款记为一人</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12">
                        <div class="layui-col-md6 layui-col-ms6 layui-col-xs6">
                            <div style="margin-left:33px;margin-bottom: 5px;font-size: 14px" id="real_time_profile">付款金额</div>
                            <div style="margin-left:33px;font-size: 20px" id="pay_money">0</div>
                            <div id="echart-pay-money" style="width:100%;height: 250px;"></div>
                        </div>
                        <div class="layui-col-md6 layui-col-ms6 layui-col-xs6" style="padding-right:20px;">
                            <ul class="data_pv_info clearfix">
                                <li style="width:50%" data-index="1">
                                    <div><span>访客数</span></div>
                                    <div><span class="num_today" name="uv">0</span></div>
                                    <div><span>昨日全天：</span><span class="num_yesterday" name="uv">0</span></div>
                                </li>
                                <li style="width:50%" data-index="2">
                                    <div><span>浏览量</span></div>
                                    <div><span class="num_today" name="pv">0</span></div>
                                    <div><span>昨日全天：</span><span class="num_yesterday" name="pv">0</span></div>
                                </li>
                                <li style="width:50%" data-index="3">
                                    <div><span>付款订单数</span></div>
                                    <div><span class="num_today" name="pay_order_num">0</span></div>
                                    <div><span>昨日全天：</span><span class="num_yesterday" name="pay_order_num">0</span></div>
                                </li>
                                <li style="width:50%" data-index="4">
                                    <div><span>付款人数</span></div>
                                    <div><span class="num_today" name="pay_user_num">0</span></div>
                                    <div><span>昨日全天：</span><span class="num_yesterday" name="pay_user_num">0</span></div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12 core_index">
                <div class="layui-card clearfix">
                    <div class="layui-card-header">
                        <span>核心指标</span>
                        <div style="float:right">
                            <span>时间筛选:</span>
                            <select name="visit_trend" class="select_visit_trend core_index_type" style="margin-left:15px;">
                                <option value="1">昨日</option>
                                <option value="7">最近7天</option>
                                <option value="30">最近30天</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-md12">
                            <ul class="clearfix mode_top" mode="mode_top">
                                <li class="active" name="pay_order_money">
                                    <div style="font-size:12px;" class="data-title" data-index="0">
                                        付款金额
                                    </div>
                                    <div style="font-size:26px">

                                    </div>

                                    <div style="font-size:12px;">
                                        <span>较前一日</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较上一周</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>

                                </li>
                                <li name="uv_pay_ratio">
                                    <div style="font-size:12px;" class="data-title" data-index="1">
                                        访问-付款转化率
                                    </div>
                                    <div style="font-size:26px">
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较前一日</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较上一周</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>
                                </li>
                                <li name="pct">
                                    <div style="font-size:12px;" class="data-title" data-index="2">
                                        客单价
                                    </div>
                                    <div style="font-size:26px">
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较前一日</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较上一周</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>
                                </li>
                                <li name="pay_order_num">
                                    <div style="font-size:12px;" class="data-title" data-index="3">
                                        付款订单数
                                    </div>
                                    <div style="font-size:26px">
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较前一日</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较上一周</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px">%</i></span>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="layui-row" id="echart-payinfo-first" style="width:100%;height: 350px">
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-md12">
                            <ul class="clearfix mode_bottom" mode="mode_bottom">
                                <li style="width:33.3333%" name="pay_user_num" class="active">
                                    <div style="font-size:12px;">
                                        付款人数
                                    </div>
                                    <div style="font-size:26px">
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较前一日</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px"></i></span>
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较上一周</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px"></i></span>
                                    </div>
                                </li>
                                <li style="width:33.3333%" name="uv">
                                    <div style="font-size:12px;">
                                        访客数
                                    </div>
                                    <div style="font-size:26px">
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较前一日</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px"></i></span>
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较上一周</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px"></i></span>
                                    </div>
                                </li>
                                <li style="width:33.3333%" name="pv">
                                    <div style="font-size:12px;">
                                        浏览量
                                    </div>
                                    <div style="font-size:26px">
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较前一日</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px"></i></span>
                                    </div>
                                    <div style="font-size:12px;">
                                        <span>较上一周</span> <span><i class="top">↑</i><i class="top" style="margin-left:3px"></i></span>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="layui-row" id="echart-payinfo-second" style="width:100%;height: 350px"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $('.core_index ul li').click(function(){
        $(this).toggleClass('active')
    })
    var echartsdata = function() {
        this.echartsVisitTrend = echarts.init(document.getElementById('echart-payinfo-first'));
        this.echartsVisitTrend2 = echarts.init(document.getElementById('echart-payinfo-second'));
        this.echartsPayMoney = echarts.init(document.getElementById('echart-pay-money'))
        this.legend = [];
        this.date = [];
        this.data = [];
        this.series = [];
        this.colors = ['#3388ff','#2fae44','#ff4444','#ff6600'];
        this.legend2 = [];
        this.date2 = [];
        this.data2 = [];
        this.series2 = [];
        this.colors2 = ['#3388ff','#2fae44','#ff4444','#ff6600'];
        this.payMoneyData = []
        this.yesterday = [];
        this.today = [];
        this.init();
    }

    //echarts对象初始化
    echartsdata.prototype.init = function(){
        this.firstRender()
        this.payMoney()
        this.bindEvent()
    }
    //首屏加载echarts对象
    echartsdata.prototype.firstRender = function() {
        var that = this;
        var mode1 = [];
        var mode2 = [];
        $(".mode_top").find(".active").each(function (i,v) {
            mode1.push($(".mode_top").find(".active").eq(i).attr("name"));
        });
        $(".mode_bottom").find(".active").each(function (j,v) {
            mode2.push($(".mode_bottom").find(".active").eq(j).attr("name"));
        });
        var type = $(".core_index").find("select").val();
        if(mode1.length<1){
            $('#echart-payinfo-first').html("暂无相关数据");
            $('#echart-payinfo-first').css(
                {
                    'line-height': '350px',
                    'text-align': 'center',

                }
            );
            return
        }else{
            var request1 = {
                mode:mode1,
                type:type
            }
            util.ajax_json('/admin/ajax/analysis/core/indicators',function(data){
                if(data.error != 0){
                    $('#echart-payinfo-first').text("暂无相关数据");
                    $('#echart-payinfo-first').css(
                        {
                            'line-height': '350px',
                            'text-align': 'center',
                        }
                    );
                }else{
                    $("#echart-payinfo-first").removeAttr('_echarts_instance_');
                    that.echartsVisitTrend = echarts.init(document.getElementById('echart-payinfo-first'));
                    console.log(data);
                    that.legend = [];
                    var content = data.content;
                    for(var j in content.constant){
                        that.legend.push(content.constant[j]);
                    }
                    that.date = [];
                    that.data = [];
                    that.date = content.curve.date
                    that.data = content.curve.data;
                    that.series = [];
                    for(var i in that.data){
                        var obj_sub = {
                            name:content['constant'][i],
                            type: 'line',
                            tooltip: {
                                trigger: 'axis'
                            },
                            yAxisIndex: 0,
                            smooth: false,
                            data: that.data[i]
                        };
                        that.series.push(obj_sub);
                    }
                    // that.obj = {
                    //     name: that.data[0].title,
                    //     type: 'line',
                    //     tooltip: {
                    //         trigger: 'axis'
                    //     },
                    //     yAxisIndex: 0,
                    //     smooth: true,
                    //     data: that.data[0].data
                    // }
                    // that.series.push(that.obj);
                    that.optionVisitTrend = {
                        color: that.colors,
                        // title: {
                        //     subtext: '最多显示四项指标',
                        //     left: '7%',
                        //     top: '-3%',
                        // },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross'
                            },
                            backgroundColor: 'rgb(255,255,255)',
                            borderWidth: '2',
                            borderColor: 'gray',
                            textStyle: {
                                color: 'black'
                            },
                            // formatter: function (data) {
                            //     var str = data[0].axisValue + '<br/>'
                            //     data.forEach((item, i) = > {
                            //         if(item.seriesName === '访问-付款转化率'
                            // )
                            //     {
                            //         str += item.marker + item.seriesName + ':' + item.value + '%' + '<br/>'
                            //     }
                            // else
                            //     {
                            //         str += item.marker + item.seriesName + ':' + item.value + '<br/>'
                            //     }
                            // })
                            //     return str;
                            // }
                        },
                        legend: {
                            data: that.legend
                        },
                        xAxis: [
                            {
                                type: 'category',
                                axisTick: {
                                    alignWithLabel: true,
                                    lineStyle: {
                                        type: 'dotted'
                                    }
                                },
                                inverse: true,
                                axisLine: {
                                    onZero: false,
                                    lineStyle: {
                                        color: that.colors[0],
                                        width: 2
                                    }
                                },
                                data: that.date
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                // splitLine: {show: false},//去除网格线
                                // splitArea : {show : true},//保留网格区域
                                position: 'left',
                                splitNumber: 5,
                                axisLine: {
                                    lineStyle: {
                                        color: that.colors[0]
                                    },
                                    show: false
                                },
                                axisLabel: {
                                    formatter: '{value}'
                                },
                                axisPointer: {
                                    show: false
                                }
                            },
                            {
                                type: 'value',
                                // splitLine: {show: false},//去除网格线
                                // splitArea : {show : true},//保留网格区域
                                splitNumber: 5,
                                axisLine: {
                                    lineStyle: {
                                        color: that.colors[1]
                                    },
                                    show: false
                                },
                                axisLabel: {
                                    formatter: '{value}%'
                                },
                                axisPointer: {
                                    show: false
                                }
                            }
                        ],
                        series :eval(that.series)
                    }
                    that.echartsVisitTrend.setOption(that.optionVisitTrend)
                    //填充
                    $(".mode_top li").each(function (i,v) {
                        $(v).find("div").eq(1).text(content.data[0][$(v).attr("name")]);
                        if(type == 1){
                            $(v).find("div").eq(2).find("span i").eq(1).html(content.percent[$(v).attr("name")][0]+"%");
                            if(content.percent[$(v).attr("name")][0] >= 0){
                                $(v).find("div").eq(2).find("span i").eq(0).html("↑")
                            }else{
                                $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                            }
                            $(v).find("div").eq(3).find("span i").eq(1).html(content.percent[$(v).attr("name")][1]+"%");
                            if(content.percent[$(v).attr("name")][1] >= 0){
                                $(v).find("div").eq(3).find("span i").eq(0).html("↑")
                            }else{
                                $(v).find("div").eq(3).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                $(v).find("div").eq(3).find("span i").eq(1).removeClass("top").addClass("down");
                            }
                            $(v).find("div").eq(2).show();
                            $(v).find("div").eq(3).show();
                            $(v).find("div").eq(2).find("span").eq(0).html("较前一日");
                        }else{
                            $(v).find("div").eq(2).find("span i").eq(1).html(content.percent[$(v).attr("name")][0]+"%");
                            if(content.percent[$(v).attr("name")][0] >= 0){
                                $(v).find("div").eq(2).find("span i").eq(0).html("↑").removeClass("down").addClass("top");
                                $(v).find("div").eq(2).find("span i").eq(1).removeClass("down").addClass("top");
                            }else{
                                $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                            }
                            if(type == 7){
                                $(v).find("div").eq(2).find("span").eq(0).html("较前七日");
                            }else if(type == 30){
                                $(v).find("div").eq(2).find("span").eq(0).html("较前三十日");
                            }
                            $(v).find("div").eq(2).show();
                            $(v).find("div").eq(3).hide();
                        }
                    })
                }
            },request1);

        }
        if(mode2.length<1){
            $('#echart-payinfo-second').html("暂无相关数据");
            $('#echart-payinfo-second').css(
                {
                    'line-height': '350px',
                    'text-align': 'center',
                }
            );
            return;
        }else{
            this.echartsVisitTrend2 = echarts.init(document.getElementById('echart-payinfo-second'));
            var request2 = {
                mode:mode2,
                type:type
            }
            util.ajax_json('/admin/ajax/analysis/core/indicators',function(data2){
                if(data2.error != 0){
                    $('#echart-payinfo-second').html("暂无相关数据");
                    $('#echart-payinfo-second').css(
                        {
                            'line-height': '350px',
                            'text-align': 'center',
                        }
                    );
                }else{
                    $("#echart-payinfo-second").removeAttr('_echarts_instance_');
                    that.echartsVisitTrend2 = echarts.init(document.getElementById('echart-payinfo-second'));
                    console.log(data2);
                    that.legend2 = [];
                    var content = data2.content;
                    for(var j in content.constant){
                        that.legend2.push(content.constant[j]);
                    }
                    that.date2 = [];
                    that.data2 = [];
                    that.date2 = content.curve.date
                    that.data2 = content.curve.data;
                    that.series2 = [];
                    for(var i in that.data2){
                        var obj_sub = {
                            name:content['constant'][i],
                            type: 'line',
                            tooltip: {
                                trigger: 'axis'
                            },
                            yAxisIndex: 0,
                            smooth: false,
                            data: that.data2[i]
                        };
                        that.series2.push(obj_sub);
                    }
                    // that.obj = {
                    //     name: that.data[0].title,
                    //     type: 'line',
                    //     tooltip: {
                    //         trigger: 'axis'
                    //     },
                    //     yAxisIndex: 0,
                    //     smooth: true,
                    //     data: that.data[0].data
                    // }
                    // that.series.push(that.obj);
                    that.optionVisitTrend = {
                        color: that.colors,
                        // title: {
                        //     subtext: '最多显示四项指标',
                        //     left: '7%',
                        //     top: '-3%',
                        // },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross'
                            },
                            backgroundColor: 'rgb(255,255,255)',
                            borderWidth: '2',
                            borderColor: 'gray',
                            textStyle: {
                                color: 'black'
                            },
                            // formatter: function (data) {
                            //     var str = data[0].axisValue + '<br/>'
                            //     data.forEach((item, i) = > {
                            //         if(item.seriesName === '访问-付款转化率'
                            // )
                            //     {
                            //         str += item.marker + item.seriesName + ':' + item.value + '%' + '<br/>'
                            //     }
                            // else
                            //     {
                            //         str += item.marker + item.seriesName + ':' + item.value + '<br/>'
                            //     }
                            // })
                            //     return str;
                            // }
                        },
                        legend: {
                            data: that.legend2
                        },
                        xAxis: [
                            {
                                type: 'category',
                                axisTick: {
                                    alignWithLabel: true,
                                    lineStyle: {
                                        type: 'dotted'
                                    }
                                },
                                inverse: true,
                                axisLine: {
                                    onZero: false,
                                    lineStyle: {
                                        color: that.colors2[0],
                                        width: 2
                                    }
                                },
                                data: that.date2
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value',
                                // splitLine: {show: false},//去除网格线
                                // splitArea : {show : true},//保留网格区域
                                position: 'left',
                                splitNumber: 5,
                                axisLine: {
                                    lineStyle: {
                                        color: that.colors[0]
                                    },
                                    show: false
                                },
                                axisLabel: {
                                    formatter: '{value}'
                                },
                                axisPointer: {
                                    show: false
                                }
                            },
                            {
                                type: 'value',
                                // splitLine: {show: false},//去除网格线
                                // splitArea : {show : true},//保留网格区域
                                splitNumber: 5,
                                axisLine: {
                                    lineStyle: {
                                        color: that.colors[1]
                                    },
                                    show: false
                                },
                                axisLabel: {
                                    formatter: '{value}%'
                                },
                                axisPointer: {
                                    show: false
                                }
                            }
                        ],
                        series :eval(that.series2)
                    }
                    that.echartsVisitTrend2.setOption(that.optionVisitTrend)
                    //填充
                    $(".mode_bottom li").each(function (i,v) {
                        $(v).find("div").eq(1).text(content.data[0][$(v).attr("name")]);
                        if(type == 1){
                            $(v).find("div").eq(2).find("span i").eq(1).html(content.percent[$(v).attr("name")][0]+"%");
                            if(content.percent[$(v).attr("name")][0] >= 0){
                                $(v).find("div").eq(2).find("span i").eq(0).html("↑")
                            }else{
                                $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                            }
                            $(v).find("div").eq(3).find("span i").eq(1).html(content.percent[$(v).attr("name")][1]+"%");
                            if(content.percent[$(v).attr("name")][1] >= 0){
                                //down
                                $(v).find("div").eq(3).find("span i").eq(0).html("↑").removeClass("down").addClass("top");
                                $(v).find("div").eq(3).find("span i").eq(1).removeClass("down").addClass("top");
                            }else{
                                $(v).find("div").eq(3).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                $(v).find("div").eq(3).find("span i").eq(1).removeClass("top").addClass("down");
                            }
                            $(v).find("div").eq(2).show();
                            $(v).find("div").eq(3).show();
                            $(v).find("div").eq(2).find("span").eq(0).html("较前一日");
                        }else{
                            $(v).find("div").eq(2).find("span i").eq(1).html(content.percent[$(v).attr("name")][0]+"%");
                            if(content.percent[$(v).attr("name")][0] >= 0){
                                $(v).find("div").eq(2).find("span i").eq(0).html("↑").removeClass("down").addClass("top")
                                $(v).find("div").eq(2).find("span i").eq(1).removeClass("down").addClass("top")
                            }else{
                                $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                            }
                            if(type == 7){
                                $(v).find("div").eq(2).find("span").eq(0).html("较前七日");
                            }else if(type == 30){
                                $(v).find("div").eq(2).find("span").eq(0).html("较前三十日");
                            }
                            $(v).find("div").eq(2).show();
                            $(v).find("div").eq(3).hide();
                        }
                    })
                }
            },request2);

        }
    }

    echartsdata.prototype.payMoney = function(){
        var that = this;
        util.ajax_json('/admin/ajax/analysis/general/situation',function(result){
            $('#real_time_profile').text('付款金额');
            // $('#real_time_profile').text(result[0].title)
            console.log(result);
            result = result.content;
            that.payMoneyData = result.date;
            that.yesterday = result.data['yesterday'];
            that.today = result.data['today'];
            let colors = ['#3388ff','#ff6600']
            that.optionPayMoney = {
                color:colors,
                title:{
                    subtext:'昨日全天',
                    left:'5%',
                    top:'-3%',
                },
                tooltip:{
                    trigger:'axis',
                    axisPointer:{
                        type:'cross'
                    },
                    backgroundColor:'rgb(255,255,255)',
                    borderWidth:'2',
                    borderColor:'gray',
                    textStyle:{
                        color:'black'
                    },
                    formatter:function(data){
                        console.log(data)
                        if (data.length === 1) {
                            return('00:00 ~' + data[0].axisValue.padStart(2,'0') + ':59<br/>'
                            + data[0].marker + data[0].seriesName + ':' + data[0].value
                            )
                        } else if( data.length === 2) {
                            return('00:00 ~' + data[0].axisValue.padStart(2,'0') + ':59<br/>'
                            + data[0].marker + data[0].seriesName + ':' + data[0].value + '<br/>'
                            + data[1].marker + data[1].seriesName + ':' + data[1].value
                            )
                        }
                    }
                },
                legend: {
                    data:['今日','昨日']
                },
                xAxis:[
                    {
                        type:'category',
                        axisTick:{
                            alignWithLabel:true,
                            lineStyle:{
                                type:'dotted'
                            }
                        },
                        axisLine: {
                            onZero: false,
                            lineStyle:{
                                color:colors[0],
                                width:2,
                                type:'solid'
                            }
                        },
                        data:[
                            '0',
                            '1',
                            '2',
                            '3',
                            '4',
                            '5',
                            '6',
                            '7',
                            '8',
                            '9',
                            '10',
                            '11',
                            '12',
                            '13',
                            '14',
                            '15',
                            '16',
                            '17',
                            '18',
                            '19',
                            '20',
                            '21',
                            '22',
                            '23'
                        ]
                    }
                ],
                yAxis:[
                    {
                        type:'value',
                        position:'left',
                        axisLine:{
                            lineStyle:{
                                color:colors[0]
                            },
                            show:false
                        },
                        axisLabel: {
                            formatter: '{value}'
                        },
                        axisPointer:{
                            show:false
                        }
                    },
                ],
                series:[
                    {
                        name:'今日',
                        type:'line',
                        tooltip:{
                            trigger:'axis'
                        },
                        yAxisIndex:0,
                        smooth:false,
                        data:that.today
                    },
                    {
                        name:'昨日',
                        type:'line',
                        tooltip:{
                            trigger:'axis'
                        },
                        yAxisIndex:0,
                        smooth:false,
                        data:that.yesterday
                    },
                ]
            }
            that.echartsPayMoney.setOption(that.optionPayMoney,true)
            //填充
            $(".data_pv_info .num_today").each(function (i,v) {
                $(v).text(result.data.num_today[$(v).attr("name")]);
            })
            $(".data_pv_info .num_yesterday").each(function (i,v) {
                $(v).text(result.data.num_yesterday[$(v).attr("name")]);
            })
            $('#pay_money').html(result.data.today[23]);
        })

    }

    //绑定事件
    echartsdata.prototype.bindEvent = function(){
        var that = this
        $('.core_index ul li').click(function(){
            this.echartsVisitTrend = echarts.init(document.getElementById('echart-payinfo-first'));
            this.echartsVisitTrend2 = echarts.init(document.getElementById('echart-payinfo-second'));
            this.echartsPayMoney = echarts.init(document.getElementById('echart-pay-money'))
            this.colors = ['#3388ff','#2fae44','#ff4444','#ff6600'];
            this.colors2 = ['#3388ff','#2fae44','#ff4444','#ff6600'];
            this.payMoneyData = []
            this.yesterday = [];
            this.today = [];
            that.firstRender();
            // if($(this).hasClass('active') && $(this).children('div.data-title').data('index') === $(this).index()){
            //     that.legend.push(that.data[$(this).index()].title)
            //     let obj = JSON.parse(JSON.stringify(that.obj))
            //     obj.name = that.data[$(this).index()].title
            //     obj.data = that.data[$(this).index()].data
            //     obj.yAxisIndex = ($(this).index() === 1) ? 1 : 0
            //     that.series.push(obj)
            // }
            // else if (!($(this).hasClass('active') || !($(this).children('div.data-title').data('index') === $(this).index()))){
            //     var element = $(this).children('div.data-title')
            //     console.log(element.text().trim())
            //     that.series.forEach((item,index) => {
            //         if(item.name === element.text().trim()){
            //             console.log(index)
            //             that.legend.splice(index,1)
            //             that.series.splice(index,1)
            //
            //         }
            //     })
            // }
            // that.echartsVisitTrend.setOption(that.optionVisitTrend,true)
            // that.echartsVisitTrend2.setOption(that.optionVisitTrend,true)
        })
        $(".core_index_type").change(function(){
            this.echartsVisitTrend = echarts.init(document.getElementById('echart-payinfo-first'));
            this.echartsVisitTrend2 = echarts.init(document.getElementById('echart-payinfo-second'));
            this.echartsPayMoney = echarts.init(document.getElementById('echart-pay-money'))
            this.colors = ['#3388ff','#2fae44','#ff4444','#ff6600'];
            this.colors2 = ['#3388ff','#2fae44','#ff4444','#ff6600'];
            this.payMoneyData = []
            this.yesterday = [];
            this.today = [];
            that.firstRender();
            // that.echartsVisitTrend.setOption(that.optionVisitTrend,true)
            // that.echartsVisitTrend2.setOption(that.optionVisitTrend,true)
        })
        $('.data_pv_info li').click(function(){
            if($(this).hasClass('active')){
                $('#real_time_profile').text(that.payMoneyData[$(this).data('index')].title)
                that.yesterday = JSON.parse(JSON.stringify(that.payMoneyData[$(this).data('index')].yesterday));
                that.today = JSON.parse(JSON.stringify(that.payMoneyData[$(this).data('index')].today));
                that.echartsPayMoney.clear()
                that.optionPayMoney.series[0].data = that.today
                that.optionPayMoney.series[1].data = that.yesterday
                that.echartsPayMoney.setOption(that.optionPayMoney)
            }
        })
        //页面大小改变重新渲染echarts图表大小
        $(window).resize(function(){
            that.echartsVisitTrend.resize();
            that.echartsPayMoney.resize();
        });
    }
    //实例化echarts设置对象
    new echartsdata();

    $('.layui-card-header > i').hover(function(){
        $(this).parent().parent().find('.float-layer').show();
    },function(){
        $(this).parent().parent().find('.float-layer').hide();
    }
    )
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','basic_yesterday','sub_0','概况统计',0);
</script>