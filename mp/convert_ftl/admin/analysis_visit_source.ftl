<#include "/admin/header.ftl">
<script language="JavaScript" src="/js/echarts.min.js"></script>
<style>
    #analysis {
        min-width: 1090px;
        margin-top: 10px;
    }
    #analysis .layui-fluid {

    }
    .layui-col-space12{
        padding: 0 15px;
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
    #visit-source-frame {
        padding-left: 20px;
        display: flex;
        flex-wrap: wrap;
    }
    #visit-source-frame span {
        padding: 7px 15px;
        margin-right: 10px;
        border: 1px solid #5A8BFF;
        border-radius: 30px;
        cursor: pointer;
        margin-bottom: 10px;
    }
    #visit-source-frame span.cancle {
        color: #9a9a9a;
        border: 1px solid #e7e7eb;
    }
</style>
<div class="title" >
    <span><a href="/admin/overview">概况</a> / </span>
    <span style="color: #666;">来源分析</span>
</div>
<div id="analysis">
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" style="height: 100px;" id="analysis-access-source">
                        <div>整体来源分析</div>
                        {{--<select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.triggerRequest(this, 1)">
                            <option value="1">打开次数</option>
                            <option value="2">访问人数</option>
                        </select>--!}
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.choose_date(this, 1)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        <input type="text" id="layui-date1" class="select_visit_trend middle time" placeholder="" lay-key="1" style="display: none;">
                        <span class="layui-card-item" style="margin-left: 20px;">${startDate!}</span> -
                        <span class="layui-card-item">${endDate!}</span>
                        <input type="hidden" name="start_date" value=""/>
                        <input type="hidden" name="end_date" value=""/>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div id="visit-source-frame"></div>
                        <div id="echart-visit-source1" style="width: 100%; height: 350px;"></div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" id="analysis-visit-source">
                        {{--<select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.triggerRequest(this, 1)">
                            <option value="1">打开次数</option>
                            <option value="2">访问人数</option>
                        </select>--!}
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.getAccessSource()" id="access-source-define">
                            <#list  ($sourceDefine as $key => $item)
                                <option value="${key!}">${item!}</option>
                            </#list>
                        </select>
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.choose_date(this, 2)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        <input type="text" id="layui-date2" class="select_visit_trend middle time" placeholder="" lay-key="2" style="display: none;">
                        <span class="layui-card-item" style="margin-left: 20px;">${startDate!}</span> -
                        <span class="layui-card-item">${endDate!}</span>
                        <input type="hidden" name="start_date" value=""/>
                        <input type="hidden" name="end_date" value=""/>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div id="echart-visit-source2" style="width: 100%; height: 350px;"></div>
                        <div style="width: 100%; line-height: 350px; text-align: center; display: none;">暂无相关数据</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // param define
    var visitSource;
    var accessSource;
    //统计分析js层
    var dataAnalysis = {
        triggerRequest: function(obj, action = 1) {
            visitTrendAction = $(obj).find(':selected').text();
            var startDate = $(obj).parent().find('input[name="start_date"]').val();
            var endDate = $(obj).parent().find('input[name="end_date"]').val();
            action == 1 ? dataAnalysis.getTrendData(startDate, endDate, $('#grading-select').val()) : dataAnalysis.getVisitPage(startDate, endDate);
        },
        gradingRequest: function(obj) {
            var startDate = $(obj).parent().find('input[name="start_date"]').val();
            var endDate = $(obj).parent().find('input[name="end_date"]').val();
            dataAnalysis.getTrendData(startDate, endDate, $('#grading-select').val());
        },
        choose_date: function (obj, action = 1) {
            var val = $(obj).val();
            var day = 0;
            var startDate, endDate, startDate1, endDate2, month1, day1, month2, day2;
            var eleName = 'layui-date'+action;

            if (val == 3) {
                $('#'+eleName).show();
                return;
            }

            $('#'+eleName).hide();

            if (val == 1) {
                day = 7;
            } else if(val == 2) {
                day = 30;
            }

            var curDate = new Date();
            var date = new Date();
            date.setDate(date.getDate() - day);

            month1 = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
            month2 = (curDate.getMonth() + 1) < 10 ? '0' + (curDate.getMonth() + 1) : (curDate.getMonth() + 1);

            day1 = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            day2 = curDate.getDate() < 10 ? '0' + curDate.getDate() : curDate.getDate();

            startDate = date.getFullYear() + "年" + month1 + "月" + day1 + "日";
            endDate = curDate.getFullYear() + "年" + month2 + "月" + day2 + "日";

            startDate1 = date.getFullYear().toString() + month1.toString() + day1.toString();
            endDate2 = curDate.getFullYear().toString() + month2.toString() + day2.toString();

            $(obj).parent().find('input[name="start_date"]').val(startDate1);
            $(obj).parent().find('input[name="end_date"]').val(endDate2);

            $(obj).parent().find('span.layui-card-item').eq(0).html(startDate);
            $(obj).parent().find('span.layui-card-item').eq(1).html(endDate);

            action == 1 ? dataAnalysis.getTrendData(startDate1, endDate2) : dataAnalysis.getAccessSource(startDate1, endDate2);
        },
        getTrendData: function (startDate = '', endDate = '', cancleBtn = '') {
            startDate == '' ? $('#analysis-access-source').find('input[name="start_date"]').val() : startDate;
            endDate == '' ? $('#analysis-access-source').find('input[name="end_date"]').val() : endDate;
            util.ajax_json('/admin/ajax/survey/source/distribution', function (response) {
                if (response.error == 0) {
                    if (response.content.access_source_session_cnt.length < 1) {
                        $('#echart-visit-source1').html('暂无相关数据');
                        $('#echart-visit-source1').css(
                            {
                                'line-height': '350px',
                                'text-align': 'center'
                            }
                        )
                        return;
                    }
                    visitSource = response.content.visit_source;
                    var btnData = response.content.access_source_session_cnt;
                    var html = '', cancle_class = 1;
                    for (var i in btnData) {
                        cancle_class = parseInt(btnData[i].is_show) == 1 ? '' : 'cancle';
                        html += '<span class="' +cancle_class+ '" item="' +btnData[i].key+ '">' +btnData[i].name+ '</span>';
                    }
                    $('#visit-source-frame').html(html);
                    dataAnalysis.initEchartVisitSource1();
                }
            }, { start_date : startDate, end_date : endDate, cancle_btn : cancleBtn})
        },
        getAccessSource: function (startDate = '', endDate = '', action = 0) {
            startDate = startDate == '' ? $('#analysis-visit-source').find('input[name="start_date"]').val() : startDate;
            endDate = endDate == '' ? $('#analysis-visit-source').find('input[name="end_date"]').val() : endDate;
            action = action == 0 ? $('#analysis-visit-source #access-source-define').val() : action;
            util.ajax_json('/admin/ajax/survey/source/access', function (response) {
                if (response.error == 0) {
                    if (response.content.x_axis.length < 1) {
                        $('#echart-visit-source2').next().show();
                        $('#echart-visit-source2').hide();
                        return;
                    } else {
                        $('#echart-visit-source2').next().hide();
                        $('#echart-visit-source2').show();
                    }
                    accessSource = response.content;
                    dataAnalysis.initEchartVisitSource2();
                }
            }, { start_date : startDate, end_date : endDate, action: action})
        },
        initEchartVisitSource1: function() {
            var echartVisitSource1 = echarts.init(document.getElementById('echart-visit-source1'));
            echartVisitSource1Option = {
                title: {
                    text: '来源分析',
                    x: '50%',
                    y: '90%',
                    textAlign: 'center',
                    textStyle: {
                        color: "#666",
                        fontSize: "14",
                        fontWeight: "normal"
                    }
                },
                grid: {
                    bottom: '5%',
                    containLabel: true
                },
                tooltip: {
                    trigger: 'axis',
                    /*axisPointer: {
                        type: 'shadow'
                    },*/
                    formatter: "{b} <br> 打开次数: {c}"
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        interval: 0
                    },
                    data: visitSource.x_axis,
                    axisLabel: {
                        interval: 0,
                        formatter:function(value,index) {
                            var str = '', j = 0;
                            for (var i = 0; i < value.length; i++) {
                                str += value[i];
                                if (j == 3) {
                                    str += '\n\n';
                                    j = 0;
                                } else {
                                    ++j;
                                }
                            }
                            return str;
                        }
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: visitSource.y_axis,
                    type: 'bar',
                    itemStyle: {
                        color : '#5a8bff'
                    }
                }]
            };
            echartVisitSource1.setOption(echartVisitSource1Option);
        },
        initEchartVisitSource2: function () {
            var echartVisitSource2 = echarts.init(document.getElementById('echart-visit-source2'));

            // 指定图表的配置项和数据
            var echartVisitSource2Option = {
                /*title: {
                    text: 'btc'
                },*/
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                /*toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },*/
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: accessSource.x_axis
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '打开次数',
                        type:'line',
                        data:accessSource.y_axis,
                        itemStyle: {
                            normal: {
                                lineStyle: {
                                    width: 2,
                                    type: 'solid',  //'dotted'虚线 'solid'实线
                                    color: '#08B74F'
                                }
                            }
                        }
                    }
                ]
            };
            echartVisitSource2.setOption(echartVisitSource2Option);
        }
    };

    dataAnalysis.getTrendData();
    dataAnalysis.getAccessSource();

    $('#visit-source-frame').on('click', 'span', function () {

        if ($(this).hasClass('cancle')) {
            $(this).removeClass('cancle');
        } else {
            $(this).addClass('cancle');
        }
        var cancleBtn = [];
        $('#visit-source-frame').find('.cancle').each(function () {
            cancleBtn.push($(this).attr('item'));
        })
        cancleBtn = cancleBtn.join(',');
        dataAnalysis.getTrendData('', '', cancleBtn);
    })
    // layui 日期
    layui.use('laydate', function(){
        $('.time').each(function(){
            var ele = $(this);
            var laydate = layui.laydate;
            laydate.render({
                elem: this,
                //position: 'fixed',
                format: 'yyyy年MM月dd日',
                range: true,
                done: function(value, date1, date2){
                    if (value == '') return;
                    //监听日期被切换
                    date1.month = date1.month < 10 ? '0' + date1.month : date1.month;
                    date1.date = date1.date < 10 ? '0' + date1.date : date1.date;
                    date2.month = date2.month < 10 ? '0' + date2.month : date2.month;
                    date2.date = date2.date < 10 ? '0' + date2.date : date2.date;

                    ele.parent().find('span').eq(0).html(value.split('-')[0]);
                    ele.parent().find('span').eq(1).html(value.split('-')[1]);

                    var startDate = date1.year.toString() + date1.month.toString() + date1.date.toString();
                    var endDate = date2.year.toString() + date2.month.toString() + date2.date.toString();

                    ele.parent().find('input[name="start_date"]').val(startDate);
                    ele.parent().find('input[name="end_date"]').val(endDate);

                    ele.attr('id').substr(-1, 1) == 1 ? dataAnalysis.getTrendData(startDate, endDate) : dataAnalysis.getAccessSource(startDate, endDate);
                }
            });
        })
    });

    $('.layui-card .item-image, .layui-card .float-layer').hover(function () {
        $(this).parent().parent().find('.float-layer').show();
    },function () {
        $(this).parent().parent().find('.float-layer').hide();
    })
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','visit_source','sub_0','来源分析',0);
</script>