<#include ("system.header")
<script src="/js/layui/layui.js" type="text/javascript"></script>
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
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
</style>

<div id="analysis">
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header">
                        <span>昨日概况 (${data->nick_name!})</span>
                        <span class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">打开次数</span>
                            <span class="float-layer-right">昨日打开小程序总次数，用户从打开小程序到主动关闭小程序或超时退出计为一次；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问次数</span>
                            <span class="float-layer-right">计为多次访问；昨日访问小程序内所有页面总次数，多个页面之间跳转、同一页面的重复访问计为多次访问；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问人数</span>
                            <span class="float-layer-right">昨日访问小程序内所有页面的总用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">新访问用户数</span>
                            <span class="float-layer-right">首次访问小程序页面的用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享次数</span>
                            <span class="float-layer-right">昨日分享小程序的总次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享人数</span>
                            <span class="float-layer-right">昨日分享小程序的总人数，同一用户多次分享不重复计。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list" style="padding-bottom: 20px;">
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <!-- 填充内容 -->
                            <div class="layui-card">
                                <div class="layui-card-item">打开次数</div>
                                <div class="layui-card-num">${basicData->session_cnt ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['session_cnt'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['session_cnt'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>
                                        >
                                                ${percent['session_cnt'][0]!}%
                                            </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['session_cnt'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['session_cnt'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['session_cnt'][1]!}%
                                    </span>
                                    </#if>

                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['session_cnt'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['session_cnt'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                            ${percent['session_cnt'][2]!}%
                                        </span>
                                    </#if>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <div class="layui-card">
                                <div class="layui-card-item">访问次数 / 人数 </div>
                                <div class="layui-card-num">${basicData->visit_pv ?? 0!} / ${basicData->visit_uv ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['visit_pv'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_pv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_pv'][0]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['visit_uv'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv'][0]!}%
                                        </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['visit_pv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_pv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_pv'][1]!}%
                                        </span>
                                    </#if>
                                    /
                                    <#if  ($percent['visit_uv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv'][1]!}%
                                        </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['visit_pv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_pv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                    ${percent['visit_pv'][2]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['visit_uv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv'][2]!}%
                                        </span>
                                    </#if>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <div class="layui-card">
                                <div class="layui-card-item">新访问用户数</div>
                                <div class="layui-card-num">${basicData->visit_uv_new ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['visit_uv_new'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv_new'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv_new'][0]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['visit_uv_new'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv_new'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv_new'][1]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['visit_uv_new'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv_new'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv_new'][2]!}%
                                    </span>
                                    </#if>

                                </div>
                            </div>
                        </div>
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <div class="layui-card">
                                <div class="layui-card-item">分享次数 / 人数</div>
                                <div class="layui-card-num">${basicShare->share_pv ?? 0!} / ${basicShare->share_uv ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['share_pv'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_pv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                            ${percent['share_pv'][0]!}%
                                        </span>
                                    </#if>
                                    /
                                    <#if  ($percent['share_uv'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_uv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_uv'][0]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['share_pv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_pv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_pv'][1]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['share_uv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_uv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_uv'][1]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['share_pv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_pv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_pv'][2]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['share_uv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_uv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_uv'][2]!}%
                                    </span>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header">
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.triggerRequest(this, 1)">
                            <option value="1">累计访问用户数</option>
                            <option value="2">打开次数</option>
                            <option value="3">访问次数</option>
                            <option value="4">访问人数</option>
                            <option value="5">新访问用户数</option>
                            <option value="8">分享次数</option>
                            <option value="9">分享人数</option>
                            <option value="6">人均停留时长</option>
                            <option value="7">次均停留时长</option>
                        </select>
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.choose_date(this)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        <input type="text" id="layui-date1" class="select_visit_trend middle time" placeholder="" lay-key="1" style="display: none;">
                        <span class="layui-card-item" style="margin-left: 20px;">${start_date!}</span> -
                        <span class="layui-card-item">${end_date!}</span>
                        <input type="hidden" name="start_date" value=""/>
                        <input type="hidden" name="end_date" value=""/>
                        <span class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">累计访问用户数</span>
                            <span class="float-layer-right">历史累计访问小程序的用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">新访问用户数</span>
                            <span class="float-layer-right">首次访问小程序页面的用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">打开次数</span>
                            <span class="float-layer-right">打开小程序总次数，用户从打开小程序到主动关闭小程序或超时退出计为一次；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问次数</span>
                            <span class="float-layer-right">访问小程序内所有页面总次数，多个页面之间跳转、同一页面的重复访问计为多次访问；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问人数</span>
                            <span class="float-layer-right">访问小程序内所有页面的总用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享次数</span>
                            <span class="float-layer-right">分享小程序的总次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享人数</span>
                            <span class="float-layer-right">分享小程序的总人数，同一用户多次分享不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">人均停留时长</span>
                            <span class="float-layer-right">平均每个用户停留在小程序页面的总时长，即小程序停留总时长/访问人数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">次均停留时长</span>
                            <span class="float-layer-right">平均每次打开小程序停留在小程序页面的总时长，即小程序停留总时长/打开次数。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div id="echart-visit-trend" style="width: 100%; height: 350px;"></div>
                        <div style="line-height: 350px; text-align: center; display: none;">暂无相关数据</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // param define
    var visitTrendDates = [];
    var visitTrendData = [];
    var visitTrendAction = '累计访问用户数';

    //统计分析js层
    var dataAnalysis = {
        triggerRequest: function(obj, action = 1) {
            visitTrendAction = $(obj).find(':selected').text();
            var startDate = $(obj).parent().find('input[name="start_date"]').val();
            var endDate = $(obj).parent().find('input[name="end_date"]').val();
            dataAnalysis.getTrendData(startDate, endDate);
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

            dataAnalysis.getTrendData(startDate1, endDate2);
        },
        getTrendData: function (startDate = '', endDate = '') {
            util.ajax_json('/system/analysis/basic/visit', function (response) {
                if (response.error == 0) {
                    if (response.content.date.length < 1) {
                        $('#echart-visit-trend').next().show();
                        $('#echart-visit-trend').hide();
                        return;
                    } else {
                        $('#echart-visit-trend').next().hide();
                        $('#echart-visit-trend').show();
                    }
                    visitTrendDates = response.content.date;
                    visitTrendData = response.content.list;
                    dataAnalysis.initChartTread();
                }
            }, { start_date : startDate, end_date : endDate, action: $('select[name="visit_trend"]').val(), shop_id : '${data->shop_id!}' })
        },
        initChartTread: function () {
            console.log(visitTrendData);
            var echartVisitTrend = echarts.init(document.getElementById('echart-visit-trend'));

            // 指定图表的配置项和数据
            var optionVisitTrend = {
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
                    data: visitTrendDates
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: visitTrendAction,
                        type:'line',
                        data: visitTrendData,
                        itemStyle: {
                            normal: {
                                lineStyle: {
                                    width: 2,
                                    type: 'solid'  //'dotted'虚线 'solid'实线
                                }
                            }
                        }
                    }
                ]
            };
            echartVisitTrend.setOption(optionVisitTrend);
        }
    };

    dataAnalysis.getTrendData();

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
                    date2.date = date2.day < 10 ? '0' + date2.date : date2.date;

                    ele.parent().find('span').eq(0).html(value.split('-')[0]);
                    ele.parent().find('span').eq(1).html(value.split('-')[1]);

                    var startDate = date1.year.toString() + date1.month.toString() + date1.date.toString();
                    var endDate = date2.year.toString() + date2.month.toString() + date2.date.toString();

                    ele.parent().find('input[name="start_date"]').val(startDate);
                    ele.parent().find('input[name="end_date"]').val(endDate);

                    dataAnalysis.getTrendData(startDate, endDate);
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
<#include ("system.footer")