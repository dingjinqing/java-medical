<#include "/admin/header.ftl">
<script language="JavaScript" src="/js/echarts.min.js"></script>
<style>
    #analysis {
        min-width: 1090px;
        margin-top: 10px;
    }
    .nav-role {
        width: 100%;
        height: 55px;
        background-color: #fff;
        margin: 0 auto;
        font-size: 14px;
        box-sizing: border-box;
    }
    .nav-child-tabs>li.active>a {
        background: #fff !important;

        box-shadow: 0 2px 0 #5A8BFF;

        padding-bottom: 13px;
        box-sizing: border-box;
    }
    .nav-child-tabs {
        width: 97%;
        height: 50px;
        margin-left: 23px;
        padding-left: 0;
        line-height: 52px;
        border-bottom: 1px solid #eee;
        list-style: none;
    }
    .layui-col-space15{
     margin: 0px;
    }
    .layui-col-space15>*{
        padding: 0px !important;
    }

    ul.nav-child-tabs>li {
        float: left;
        margin-right: 15px;
        padding-right: 15px;
    }

    ul.nav-child-tabs>li>a, ul.nav-child-tabs>li.active>a {
        color: #333;
        text-decoration: none;
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
        height: 84px;
        padding-left: 22px;
    }
    #analysis .layui-card .layui-card-header .item-image {
        float: right;
        margin-top: 12px;
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
        padding: 20px 60px 30px;
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
    .mingxi{
        color: #5a8bff !important;
        font-size: 14px;
    }
</style>

<div id="analysis">
    <div class="title">
        <div>
            <span><a href="/admin/overview?top_index=0">概况</a> / </span>
            <span style="color: #666;">资产管理</span>
        </div>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if ($asset_content==0) class="active" </#if>>
                    <a href="/admin/survey/asset/manage?nav=0">现金资产管理</a>
                </li>
                <li <#if ($asset_content==1) class="active" </#if>>
                    <a href="/admin/survey/asset/manage?nav=1">积分资产管理</a>
                </li>
            </ul>
        </div>
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12 core_index">
                <div class="layui-card clearfix">
                    <div class="layui-card-header">
                        <span>营收概况及趋势</span>
                        <span ><a href="/admin/survey/asset/detail?asset_content=${asset_content!}" class="mingxi">查看明细</a></span>
                        <div>
                            <select name="visit_trend" class="select_visit_trend core_index_type" >
                                <option value="1">昨日</option>
                                <option value="7">最近7天</option>
                                <option value="30">最近30天</option>
                            </select>
                            <span class="layui-card-item">${startDate!}</span> -
                            <span class="layui-card-item"> ${endDate!}</span>

                        </div>
                    </div>
                    <div class="layui-row">
                        <div class="layui-col-md12">
                            <ul class="clearfix mode_bottom" mode="mode_bottom">
                                <li style="width:33.3333%" name="${asset_names[0]!}" class="active">
                                {{--<li style="width:33.3333%" name="income_real_money" class="active">--!}
                                    <div style="font-size:12px;">
                                        净收入
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
                                <li style="width:33.3333%" name="${asset_names[1]!}" class="active">
                                    <div style="font-size:12px;">
                                        总收入
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
                                <li style="width:33.3333%" name="${asset_names[2]!}" class="active">
                                    <div style="font-size:12px;">
                                        总支出
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
        this.echartsVisitTrend2 = echarts.init(document.getElementById('echart-payinfo-second'));
        this.legend = [];
        this.date = [];
        this.data = [];
        this.series = [];
        this.colors = ['#3388ff','#2fae44','#ff4444'];
        this.yesterday = [];
        this.today = [];
        this.init();
    }

    //echarts对象初始化
    echartsdata.prototype.init = function(){
        this.firstRender()
        this.bindEvent()
    }
    //首屏加载echarts对象
    echartsdata.prototype.firstRender = function() {
        var that = this;
        var mode = [];
        $(".mode_bottom").find(".active").each(function (j,v) {
            mode.push($(".mode_bottom").find(".active").eq(j).attr("name"));
        });
        var type = $(".core_index").find("select").val();
        if(mode.length<1){
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
            var request = {
                mode:mode,
                type:type,
                asset_content:'${asset_content!}'
            }
            util.ajax_json('/admin/survey/asset/manage',function(data){
                if(data.error != 0){
                    $('#echart-payinfo-second').html("暂无相关数据");
                    $('#echart-payinfo-second').css(
                        {
                            'line-height': '350px',
                            'text-align': 'center',
                        }
                    );
                }else{
                    $("#echart-payinfo-second").removeAttr('_echarts_instance_');
                    that.echartsVisitTrend = echarts.init(document.getElementById('echart-payinfo-second'));
                    console.log(data);
                    var content = data.content;
                    $("span.layui-card-item").eq(0).html(content.start_date);
                    $("span.layui-card-item").eq(1).html(content.end_date);
                    // that.legend = [];
                    // var content = data.content;
                    // for(var j in content.constant){
                    //     that.legend.push(content.constant[j]);
                    // }
                    that.date = [];
                    that.data = [];
                    var userPlotDates = content.userPlotDates
                    var userPlotList = content.userPlotList;
                    var userPlotlegends = content.userPlotlegends;
                    console.log(userPlotDates.length);
                    if(userPlotDates.length<1){
                        $('#echart-payinfo-second').html("暂无相关数据");
                        $('#echart-payinfo-second').css(
                            {
                                'line-height': '350px',
                                'text-align': 'center',
                            }
                        );
                    }else{
                        that.series = [];
                        for(var i in userPlotList){
                            var obj_sub = {
                                name:userPlotList[i]['name'],
                                type: 'line',
                                tooltip: {
                                    trigger: 'axis'
                                },
                                yAxisIndex: 0,
                                smooth: false,
                                data: userPlotList[i]['data']
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
                                data: userPlotlegends
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
                                    inverse: false,
                                    axisLine: {
                                        onZero: false,
                                        lineStyle: {
                                            color: that.colors[0],
                                            width: 2
                                        }
                                    },
                                    data: userPlotDates
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
                    }


                    var compareData = content.assetCompare;
                    //填充
                    $(".mode_bottom li").each(function (i,v) {
                        // console.log($(".mode_bottom li").eq(i).attr("name"));
                        if(compareData[$(v).attr("name")]['data'] === null){
                            $(v).find("div").eq(1).text('--');
                        }else{
							if(content.asset_content==0){
                                $(v).find("div").eq(1).text(numFormat(compareData[$(v).attr("name")]['data'])+'元');
                            }else{
                                $(v).find("div").eq(1).text(numFormat(compareData[$(v).attr("name")]['data'])+'积分');
                            }
                        }


                        if(type == 1){
                            if(compareData[$(v).attr("name")]['per']=='-' || !compareData[$(v).attr("name")]['per']){
                                $(v).find("div").eq(2).find("span i").eq(1).html('--');
                                $(v).find("div").eq(2).find("span i").eq(0).html("");
                            }else{
                                var per = compareData[$(v).attr("name")]['per'].split("%");
                                $(v).find("div").eq(2).find("span i").eq(1).html(Math.abs(per[0])+'%');
                                console.log(per);
                                if(per != undefined && per[0] >= 0){
                                    $(v).find("div").eq(2).find("span i").eq(0).html("↑").removeClass("down").addClass("top");
                                    $(v).find("div").eq(2).find("span i").eq(1).removeClass("down").addClass("top");
                                }else if(per != undefined && per[0] < 0){
                                    $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                    $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                                }
                            }
                            // if(content.percent[$(v).attr("name")][0] >= 0){
                            //     $(v).find("div").eq(2).find("span i").eq(0).html("↑")
                            // }else{
                            //     $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                            //     $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                            // }
                            if(compareData[$(v).attr("name")]['per1']=='-' || !compareData[$(v).attr("name")]['per1']){
                                $(v).find("div").eq(3).find("span i").eq(1).html('--');
                                $(v).find("div").eq(3).find("span i").eq(0).html("");
                            }else{
                                $(v).find("div").eq(3).find("span i").eq(1).html(compareData[$(v).attr("name")]['per1']);
                                var per = compareData[$(v).attr("name")]['per1'].split("%");
                                console.log(per);
                                if(per != undefined && per[0] >= 0){
                                    $(v).find("div").eq(3).find("span i").eq(0).html("↑")
                                }else if(per != undefined && per[0] < 0){
                                    $(v).find("div").eq(3).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                    $(v).find("div").eq(3).find("span i").eq(1).removeClass("top").addClass("down");
                                }
                            }
                            // $(v).find("div").eq(3).find("span i").eq(1).html(content.percent[$(v).attr("name")][1]+"%");
                            // if(content.percent[$(v).attr("name")][1] >= 0){
                            //     //down
                            //     $(v).find("div").eq(3).find("span i").eq(0).html("↑").removeClass("down").addClass("top");
                            //     $(v).find("div").eq(3).find("span i").eq(1).removeClass("down").addClass("top");
                            // }else{
                            //     $(v).find("div").eq(3).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                            //     $(v).find("div").eq(3).find("span i").eq(1).removeClass("top").addClass("down");
                            // }
                            $(v).find("div").eq(2).find("span").eq(0).html("较前一日");
                            $(v).find("div").eq(2).show();
                            $(v).find("div").eq(3).show();
                        }else{
                            if(compareData[$(v).attr("name")]['per']=='-' || !compareData[$(v).attr("name")]['per']){
                                $(v).find("div").eq(2).find("span i").eq(1).html('--');
                                $(v).find("div").eq(2).find("span i").eq(0).html("");
                            }else{
                                var per = compareData[$(v).attr("name")]['per'].split("%");
                                $(v).find("div").eq(2).find("span i").eq(1).html(Math.abs(per[0])+'%');
                                console.log(per);
                                if(per != undefined && per[0] >= 0){
                                    $(v).find("div").eq(2).find("span i").eq(0).html("↑").removeClass("down").addClass("top");
                                    $(v).find("div").eq(2).find("span i").eq(1).removeClass("down").addClass("top");
                                }else if(per != undefined && per[0] < 0){
                                    $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                                    $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                                }
                            }
                            // $(v).find("div").eq(2).find("span i").eq(1).html(content.percent[$(v).attr("name")][0]+"%");
                            // if(content.percent[$(v).attr("name")][0] >= 0){
                            //     $(v).find("div").eq(2).find("span i").eq(0).html("↑").removeClass("down").addClass("top")
                            //     $(v).find("div").eq(2).find("span i").eq(1).removeClass("down").addClass("top")
                            // }else{
                            //     $(v).find("div").eq(2).find("span i").eq(0).html("↓").removeClass("top").addClass("down");
                            //     $(v).find("div").eq(2).find("span i").eq(1).removeClass("top").addClass("down");
                            // }
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
            },request);

        }
    }

    //绑定事件
    echartsdata.prototype.bindEvent = function(){
        var that = this;
        $('.core_index ul li').click(function(){
            this.echartsVisitTrend = echarts.init(document.getElementById('echart-payinfo-second'));
            this.colors = ['#3388ff','#2fae44','#ff4444','#ff6600'];
            this.yesterday = [];
            this.today = [];
            that.firstRender();
        })
        $(".core_index_type").change(function(){
            this.echartsVisitTrend = echarts.init(document.getElementById('echart-payinfo-second'));
            this.colors = ['#3388ff','#2fae44','#ff4444','#ff6600'];
            this.yesterday = [];
            this.today = [];
            that.firstRender();
            // that.echartsVisitTrend.setOption(that.optionVisitTrend,true)
            // that.echartsVisitTrend2.setOption(that.optionVisitTrend,true)
        })

        //页面大小改变重新渲染echarts图表大小
        // $(window).resize(function(){
        //     that.echartsVisitTrend.resize();
        //     that.echartsPayMoney.resize();
        // });
    }
    //实例化echarts设置对象
    new echartsdata();

    $('.layui-card-header > i').hover(function(){
            $(this).parent().parent().find('.float-layer').show();
        },function(){
            $(this).parent().parent().find('.float-layer').hide();
        }
    )
    function numFormat(num){
		var res=num.toString().replace(/\d+/, function(n){ // 先提取整数部分
			return n.replace(/(\d)(?=(\d{3})+$)/g,function($1){
				return $1+",";
			});
		})
        return res;
    }

</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','basic_yesterday','sub_0','概况统计',0);
</script>