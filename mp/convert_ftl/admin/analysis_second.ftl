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
    #visit-retain-table tr {
        /*height: 75px;*/
    }
    #visit-page-table th {
        cursor: pointer;
    }
    #visit-page-table .asc-or-desc span {
        color: #5A8BFF;
        padding-left: 5px;
    }
</style>
<div class="title" >
    <span><a href="/admin/overview">概况</a> / </span>
    <span style="color: #666;">访问分析</span>
</div>
<div id="analysis">
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header">
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.triggerRequest(this, 1)">
                            <option value="1">打开次数</option>
                            <option value="2">访问次数</option>
                            <option value="3">访问人数</option>
                            <option value="4">新用户数</option>
                            <option value="5">人均停留时长</option>
                            <option value="6">次均停留时长</option>
                            <option value="7">平均访问深度</option>
                        </select>
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.choose_date(this)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        粒度
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.gradingRequest(this)" id="grading-select">
                            <option value="1">日</option>
                            <option value="7">周</option>
                            <option value="30">月</option>
                        </select>
                        <input type="text" id="layui-date1" class="select_visit_trend middle time" placeholder="" lay-key="1" style="display: none;">
                        <span class="layui-card-item" style="margin-left: 20px;">${startDate!}</span> -
                        <span class="layui-card-item">${endDate!}</span>
                        <input type="hidden" name="start_date" value=""/>
                        <input type="hidden" name="end_date" value=""/>
                        <span class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
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
                            <span class="float-layer-left">新用户数</span>
                            <span class="float-layer-right">首次访问小程序页面的用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">人均停留时长</span>
                            <span class="float-layer-right">平均每个用户停留在小程序页面的总时长，即小程序停留总时长/访问人数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">次均停留时长</span>
                            <span class="float-layer-right">平均每次打开小程序停留在小程序页面的总时长，即小程序停留总时长/打开次数。</span>
                        </div>
                        <div>
                            <span class="float-layer-left">平均访问深度</span>
                            <span class="float-layer-right">平均每次打开小程序访问的去重页面数，即每次访问去重页面数/打开次数。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div id="echart-visit-trend" style="width: 99%; height: 350px;"></div>
                        <div style="line-height: 350px; text-align: center; display: none;">暂无相关数据</div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" style="height: 84px;">
                        <div>访问分布</div>
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
                        <span class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">访问来源</span>
                            <span class="float-layer-right">小程序各个来源的打开次数，访问来源即用户打开小程序的具体场景，如二维码；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问时长</span>
                            <span class="float-layer-right">小程序各个访问时长区间的打开次数，访问时长即用户每次打开小程序到主动关闭或超时退出过程中停留的时长；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问深度</span>
                            <span class="float-layer-right">小程序各个访问深度区间的打开次数，访问深度即用户每次打开小程序到主动关闭或超时退出过程中访问的去重页面数。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div id="analysisEcharts1" style="width: 33%; height: 350px; float: left"></div>
                        <div style="width: 33%; line-height: 350px; text-align: center; display: none;">暂无相关数据</div>
                        <div id="analysisEcharts2" style="width: 33%; height: 350px; float: left"></div>
                        <div style="width: 33%; line-height: 350px; text-align: center; display: none;">暂无相关数据</div>
                        <div id="analysisEcharts3" style="width: 33%; height: 350px; float: left"></div>
                        <div style="width: 33%; line-height: 350px; text-align: center; display: none;">暂无相关数据</div>
                    </div>
                </div>
            </div>
            {{--<div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-row layui-col-space12 demo-list">
                        <table class="layui-table" style="width: 99%; margin-top: 20px; margin-left: 5px;">
                            <thead>
                                <tr>
                                    <th>带参数二维码</th>
                                    <th>打开次数</th>
                                    <th>占比</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td colspan="3">暂无相关记录</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>--!}
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" id="visit-retain">
                        <select name="visit_trend" class="select_visit_trend visit-retain-action" onchange="dataAnalysis.getVisitRetain()">
                            <option value="1">新增留存</option>
                            <option value="2">活跃留存</option>
                        </select>
                        <select name="visit_trend" class="select_visit_trend visit-retain" onchange="dataAnalysis.choose_date(this, 3)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        粒度
                        <select name="visit_trend" class="select_visit_trend visit-retain-grading" onchange="dataAnalysis.getVisitRetain()">
                            <option value="1">日</option>
                            <option value="2">周</option>
                            <option value="3">月</option>
                        </select>
                        <input type="text" id="layui-date3" class="select_visit_trend middle time" placeholder="" lay-key="3" style="display: none;">
                        <span class="layui-card-item" style="margin-left: 20px;">${startDate!}</span> -
                        <span class="layui-card-item">${endDate!}</span>
                        <input type="hidden" name="start_date" value=""/>
                        <input type="hidden" name="end_date" value=""/>
                        <span class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">新增留存</span>
                            <span class="float-layer-right">指定时间新增（即首次访问小程序）的用户，在之后的第N天（或周、月），再次访问小程序的用户数占比；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">活跃留存</span>
                            <span class="float-layer-right">指定时间活跃（即访问小程序）的用户，在之后的第N天（或周、月），再次访问小程序的用户数占比。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list" id="visit-retain-table">
                        <table class="layui-table" style="width: 96%; margin: 20px auto;">
                            <thead id="retain-thead">
                            </thead>
                            <tbody id="retain-tbody">
                            <tr>
                                <td>暂无相关数据</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" id="visit-page" style="height: 84px;">
                        <div>访问页面</div>
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.choose_date(this, 4)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        <input type="text" id="layui-date4" class="select_visit_trend middle time" placeholder="" lay-key="4" style="display: none;">
                        <span class="layui-card-item" style="margin-left: 20px;">${startDate!}</span> -
                        <span class="layui-card-item">${endDate!}</span>
                        <input type="hidden" name="start_date" value=""/>
                        <input type="hidden" name="end_date" value=""/>
                        <span class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">访问次数</span>
                            <span class="float-layer-right">访问该页面的总次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问人数</span>
                            <span class="float-layer-right">访问该页面的总用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">次均时长</span>
                            <span class="float-layer-right">用户平均每次访问该页面的停留时长，即该页面的总停留时长/访问次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">入口页次数</span>
                            <span class="float-layer-right">该页面作为入口页的访问次数，例如用户从页面A进入小程序，跳转到页面B，A为入口页，B不是；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">退出页次数</span>
                            <span class="float-layer-right">该页面作为退出页的访问次数，例如用户从页面A跳转到页面B，从页面B退出小程序，B为退出页，A不是；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">退出率</span>
                            <span class="float-layer-right">该页面作为退出页的访问次数占比，即退出页次数/访问次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享次数</span>
                            <span class="float-layer-right">分享该页面的总次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享人数</span>
                            <span class="float-layer-right">分享该页面的总人数，同一用户多次分享不重复计。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list" id="visit-page-table">
                        <table class="layui-table" style="width: 99%; margin-top: 20px; margin-left: 5px;">
                            <thead>
                                <tr>
                                    <th>页面路径</th>
                                    <th>页面名称</th>
                                    <th asc="2" action="1" class="asc-or-desc">访问次数<span></span></th>
                                    <th asc="1" action="2" class="asc-or-desc">访问人数<span></span></th>
                                    <th asc="1" action="3" class="asc-or-desc">次均时长<span></span></th>
                                    <th asc="1" action="4" class="asc-or-desc">入口页次数<span></span></th>
                                    <th asc="1" action="5" class="asc-or-desc">退出页次数<span></span></th>
                                    <th>退出率<span></span></th>
                                    <th asc="1" action="6" class="asc-or-desc">分享次数<span></span></th>
                                    <th asc="1" action="7" class="asc-or-desc">分享人数<span></span></th>
                                </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>暂无相关数据</td>
                            </tr>
                            </tbody>
                        </table>
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
    var visitSource = visitStayTime = visitDepth = [];
    var visitTrendAction = '打开次数';

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

            if (action == 1) {
                dataAnalysis.getTrendData(startDate1, endDate2);
            } else if (action == 2) {
                dataAnalysis.getVisitDisTribution(startDate1, endDate2);
            } else if (action == 3) {
                dataAnalysis.getVisitRetain(startDate1, endDate2);
            } else if (action == 4) {
                dataAnalysis.getVisitPage(startDate1, endDate2);
            }
        },
        getTrendData: function (startDate = '', endDate = '', grading = 0) {
            grading = grading == 0 ? $('#grading-select').val() : grading;
            util.ajax_json('/admin/ajax/survey/view/visit', function (response) {
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
            }, { start_date : startDate, end_date : endDate, action: $('select[name="visit_trend"]').val(), grading: grading})
        },
        // 访问分布
        getVisitDisTribution: function(startDate = '', endDate = '') {
            util.ajax_json('/admin/ajax/survey/source/distribution', function (response) {
                if (response.error == 0) {
                    visitSource = response.content.access_source_session_cnt;
                    visitStayTime = response.content.visit_staytime;
                    visitDepth = response.content.visit_depth;
                    if (visitSource.length < 1) {
                        $('#analysisEcharts1').next().show();
                        $('#analysisEcharts1').hide();
                        return;
                    } else {
                        $('#analysisEcharts1').next().hide();
                        $('#analysisEcharts1').show();
                    }
                    if (visitStayTime.length < 1) {
                        $('#analysisEcharts2').next().show();
                        $('#analysisEcharts2').hide();
                        return;
                    } else {
                        $('#analysisEcharts2').next().hide();
                        $('#analysisEcharts2').show();
                    }
                    if (visitDepth.length < 1) {
                        $('#analysisEcharts3').next().show();
                        $('#analysisEcharts3').hide();
                        return;
                    } else {
                        $('#analysisEcharts3').next().hide();
                        $('#analysisEcharts3').show();
                    }
                    dataAnalysis.initAnalysisEcharts1();
                    dataAnalysis.initAnalysisEcharts2();
                    dataAnalysis.initAnalysisEcharts3();
                }
            }, { start_date : startDate, end_date : endDate})
        },
        // 访问留存
        getVisitRetain: function(startDate = '', endDate = '', grading = 0, userAction = 0) {
            startDate = startDate == '' ? $('#visit-retain [name="start_date"]').val() : startDate;
            endDate = endDate == '' ? $('#visit-retain [name="end_date"]').val() : endDate;
            grading = grading == 0 ? $('.visit-retain-grading').val() : grading;
            userAction = userAction == 0 ? $('.visit-retain-action').val() : userAction;
            util.ajax_json('/admin/ajax/survey/view/retain', function (response) {
                if (response.error == 0) {
                    console.log(response.content);
                    var list = response.content.data;
                    if (list.length < 1) {
                        $('#retain-thead').html('');
                        $('#retain-tbody').html(`
                            <tr><td>暂无相关数据</td></tr>
                        `);
                        return;
                    }
                    var retainThead = retainTbody = '';

                    if (grading == 1) {
                        retainThead += `
                            <tr>
                                <th>时间</th>
                                <th>`+ (userAction == 1 ? '新增用户数' : '活跃用户数') +`</th>
                                <th>1天后</th>
                                <th>2天后</th>
                                <th>3天后</th>
                                <th>4天后</th>
                                <th>5天后</th>
                                <th>6天后</th>
                            </tr>
                        `;
                    } else if (grading == 2) {
                        retainThead += `
                            <tr>
                                <th>时间</th>
                                <th>`+ (userAction == 1 ? '新增用户数' : '活跃用户数') +`</th>
                                <th>1周后</th>
                                <th>2周后</th>
                                <th>3周后</th>
                            </tr>
                        `;
                    } else {
                        retainThead += `
                            <tr>
                                <th>时间</th>
                                <th>`+ (userAction == 1 ? '新增用户数' : '活跃用户数') +`</th>
                            </tr>
                        `;
                    }
                    for (var i in list) {
                        retainTbody += `
                                <tr>
                                    <td>`+list[i].ref_date+`</td>
                                    <td>`+list[i].list[0].value+`</td>
                            `;
                        if (grading == 1 || grading == 2) {
                            for (var j in list[i].list) {
                                if ($.inArray(parseInt(list[i].list[j].key), [0, 7, 14, 30]) > -1) {
                                    continue;
                                }
                                if (list[i].sum == 0) {
                                    retainTbody += '<td style="background-color: #d1efd1;"></td>';
                                } else if (parseInt(list[i].list[j].value) == 0){
                                    retainTbody += '<td style="background-color: #d1efd1;">0.00%</td>';
                                } else {
                                    retainTbody += '<td style="background-color: #76ce75;">' +(list[i].list[j].value / list[i].list[0].value * 100).toFixed(2)+ '%</td>';
                                }
                            }
                            retainTbody += `</tr>`;
                        }
                    }
                    $('#retain-thead').html(retainThead);
                    $('#retain-tbody').html(retainTbody);
                }
            }, { start_date : startDate, end_date : endDate, grading: grading, action: userAction})
        },
        initChartTread: function () {
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
                    right: '6%',
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
                    boundaryGap: true,
                    data: visitTrendDates
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: visitTrendAction,
                        type:'line',
                        data:visitTrendData,
                        itemStyle: {
                            normal: {
                                lineStyle: {
                                    width: 2,
                                    type: 'solid',  //'dotted'虚线 'solid'实线
                                    color: '#08B74F'   // #749dc9
                                }
                            }
                        }
                    }
                ]
            };
            echartVisitTrend.setOption(optionVisitTrend);
        },
        getVisitPage: function (startDate = '', endDate = '', action = 1, asc = 2) {
            startDate = startDate == '' ? $('#visit-page').find('[name="start_date"]').val() : startDate;
            endDate = endDate == '' ? $('#visit-page').find('[name="end_date"]').val() : endDate;
            util.ajax_json('/admin/ajax/analysis/basic/visitPage', function (response) {
                if (response.error == 0) {
                    var data = response.content.list;
                    var html = '';
                    var ascOrdesc = (asc == 1) ? 2 : 1;
                    var jiantou = (asc == 2) ? '↓' : '↑';
                    $('#visit-page-table .asc-or-desc[action="'+action+'"]').attr('asc', ascOrdesc);
                    $('#visit-page-table .asc-or-desc').find('span').text('');
                    $('#visit-page-table .asc-or-desc[action="'+action+'"]').find('span').text(jiantou);
                    for (var i in data) {
                        var percent = parseFloat(data[i].exitpage_pv) / parseFloat(data[i].page_visit_pv) * 100;
                        html += `<tr>`;
                        html += `<td>`+ parseInt(parseInt(i)+1) +`.`+data[i].page_path+`</td>`;
                        html += `<td>`+data[i].page_name+`</td>`;
                        html += `<td>`+data[i].page_visit_pv+`</td>`;
                        html += `<td>`+data[i].page_visit_uv+`</td>`;
                        html += `<td>`+data[i].page_staytime_pv.toFixed(2)+`</td>`;
                        html += `<td>`+data[i].entrypage_pv+`</td>`;
                        html += `<td>`+data[i].exitpage_pv+`</td>`;
                        if (parseFloat(data[i].page_visit_pv) == 0) {
                            html += `<td></td>`;
                        } else {
                            html += `<td>`+percent.toFixed(2)+`%</td>`;
                        }
                        html += `<td>`+data[i].page_share_pv+`</td>`;
                        html += `<td>`+data[i].page_share_uv+`</td>`;
                        html += '</tr>';
                    }
                    if(html == ''){
                        var c = '';
                        c += `<div class="no_data_style" style="width: 98%;border: 1px solid #eee;height: 100px;margin-bottom: 10px;margin-left: 15px;float:left;">
                                <div style="width: 30px;height: 33px; margin: 12px auto auto auto" >
                                   <img src="/image/admin/no_data.png" alt="">
                                </div>
                                  <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                           </div>`;
                        if($('#visit-page-table').find('.no_data_style').length <= 0){
                            $('#visit-page-table').append(c);
                        }
                    }

                    $('#visit-page').next().next().find('tbody').html(html);
                }
            }, { start_date : startDate, end_date : endDate, action : action, asc : asc})
        },
        initAnalysisEcharts1: function () {
            if (eval(visitSource).length < 1) {
                $('#analysisEcharts1').html('暂无相关数据');
                $('#analysisEcharts1').css(
                    {
                        'line-height': '350px',
                        'text-align': 'center',
                    }
                );
                return;
            }
            var analysisEcharts1 = echarts.init(document.getElementById('analysisEcharts1'));

            // 指定图表的配置项和数据
            analysisEcharts1Option = {
                title: {
                    text: '访问来源',
                    x: '50%',
                    y: 'bottom',
                    textAlign: 'center',
                    textStyle: {
                        color: "#666",
                        fontSize: "14",
                        fontWeight: "normal"
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                color: ['#3cb2ef', '#ff9f7f', '#ffdb5c', '#9fe6b8', '#67e0e3'],
                series: [
                    {
                        name:'打开次数',
                        type:'pie',
                        radius: ['50%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '30',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        itemStyle: {
                            normal: {
                                borderWidth: 1,
                                borderColor: '#fff',
                            }
                        },
                        data: eval(visitSource)
                    }
                ]
            };
            analysisEcharts1.setOption(analysisEcharts1Option);
        },
        initAnalysisEcharts2: function () {
            var analysisEcharts2 = echarts.init(document.getElementById('analysisEcharts2'));
            analysisEcharts2Option = {
                color: ['#3398DB'],
                title: {
                    text: '访问时长',
                    x: '50%',
                    y: 'bottom',
                    textAlign: 'center',
                    textStyle: {
                        color: "#666",
                        fontSize: "14",
                        fontWeight: "normal"
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: "{b} <br> 打开次数: {c}"
                },
                /*legend: {
                    data: [date]
                },*/
                grid: {
                    left: '4%',
                    right: '4%',
                    bottom: '10%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01],
                    min: 0,
                    max: parseInt(visitStayTime.max) + Math.ceil(parseInt(visitStayTime.max) / 5),
                    //interval: 20,
                    axisLabel: {
                        formatter: '{value}',
                        textStyle: {
                            //color: '#fff',
                            fontWeight: '80'
                        }
                    }
                },
                yAxis: {
                    type: 'category',
                    data: eval(visitStayTime.y_axis),
                    axisLabel: {
                        show: true,
                        interval: 0,
                        rotate: 0,
                        margin: 10,
                        inside: false,
                        textStyle: {
                            //color: '#fff',
                            fontWeight: '50'
                        }
                    }
                },
                series: [{
                    type: 'bar',
                    label: {
                        normal: {
                            show: true,
                            // formatter: '{c}',
                            formatter: function(v) {
                                var val = v.data;
                                if (val == 0) {
                                    return '';
                                }
                                return val;
                            },
                            position: 'right',
                            color: '#3276b1'
                        }
                    },
                    data: eval(visitStayTime.x_axis)
                }]
            };
            analysisEcharts2.setOption(analysisEcharts2Option);
        },
        initAnalysisEcharts3: function () {
            var analysisEcharts3 = echarts.init(document.getElementById('analysisEcharts3'));
            analysisEcharts3Option = {
                color: ['#3398DB'],
                title: {
                    text: '访问深度',
                    x: '50%',
                    y: 'bottom',
                    textAlign: 'center',
                    textStyle: {
                        color: "#666",
                        fontSize: "14",
                        fontWeight: "normal"
                    }
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    },
                    formatter: "{b} <br> 打开次数: {c}"
                },
                /*legend: {
                    data: [date]
                },*/
                grid: {
                    left: '4%',
                    right: '4%',
                    bottom: '10%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01],
                    min: 0,
                    max: parseInt(visitDepth.max) + Math.ceil(parseInt(visitDepth.max) / 5),
                    axisLabel: {
                        formatter: '{value}',
                        textStyle: {
                            //color: '#fff',
                            fontWeight: '80'
                        }
                    }
                },
                yAxis: {
                    type: 'category',
                    data: eval(visitDepth.y_axis),
                    axisLabel: {
                        show: true,
                        interval: 0,
                        rotate: 0,
                        margin: 10,
                        inside: false,
                        textStyle: {
                            //color: '#fff',
                            fontWeight: '50'
                        }
                    }
                },
                series: [{
                    type: 'bar',
                    label: {
                        normal: {
                            show: true,
                            // formatter: '{c}',
                            formatter: function(v) {
                                var val = v.data;
                                if (val == 0) {
                                    return '';
                                }
                                return val;
                            },
                            position: 'right',
                            color: '#3276b1'
                        }
                    },
                    data: eval(visitDepth.x_axis)
                }]
            };
            analysisEcharts3.setOption(analysisEcharts3Option);
        }
    };

    dataAnalysis.getTrendData();
    dataAnalysis.getVisitDisTribution();
    dataAnalysis.getVisitPage();
    dataAnalysis.getVisitRetain();
    //dataAnalysis.initAnalysisEcharts1();
    //dataAnalysis.initAnalysisEcharts2();
    //dataAnalysis.initAnalysisEcharts3();

    $('#visit-page-table .asc-or-desc').click(function () {
        dataAnalysis.getVisitPage('', '', $(this).attr('action'), $(this).attr('asc'));
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
                    console.log(date1);
                    console.log(date2);
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

                    var action = ele.attr('id').substr(-1, 1);
                    if (action == 1) {
                        dataAnalysis.getTrendData(startDate, endDate);
                    } else if (action == 2) {
                        dataAnalysis.getVisitDisTribution(startDate, endDate);
                    } else if (action == 3) {
                        dataAnalysis.getVisitRetain(startDate, endDate);
                    } else if (action == 4) {
                        dataAnalysis.getVisitPage(startDate, endDate);
                    }
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
    getPowerInfo('main_config','second_view','sub_0','访问分析',0);
</script>