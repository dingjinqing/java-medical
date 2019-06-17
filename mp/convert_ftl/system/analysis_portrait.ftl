<#include ("system.header")
<script language="JavaScript" src="/js/echarts.min.js"></script>
<script language="JavaScript" src="/js/echarts.china.js"></script>
<script language="JavaScript" src="/js/jquery.searchableSelect.js"></script>
<link href="/css/system/jquery.searchableSelect.css" rel="stylesheet" type="text/css"/>
<style>
    #analysis {
        min-width: 1090px;
        margin-top: 10px;
    }
    #analysis .layui-fluid {

    }
    #analysis .demo-list .layui-card{
        padding-top: 50px;
        border-right: 1px solid #CCCCCC;
        text-align: center;
    }
    #analysis .layui-card .layui-card-header {
        font-size: 16px;
    }
    #analysis .layui-card-item {
        color: #9a9a9a;
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
    #analysis .layui-table tr th:first-child, #analysis .layui-table tr td:first-child {
        text-align: center;
    }
    .layui-progress-bar {
        background-color: #5a8bff;
    }
    .btn_choose{
        line-height: 25px;
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 65px;
        height: 30px;
        margin-left: 15px;
        border-radius: 2px;
    }

</style>

<div id="analysis">
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" style="height: 90px;">
                        <div>地区分布</div>
                        <select name="user_num" class="select_visit_trend action2" onchange="dataAnalysis.userActionSearch(2, $(this).val())">
                            <option value="1">活跃用户数</option>
                            <option value="2">新增用户数</option>
                        </select>
                        <select name="visit_trend" class="select_visit_trend  visit_trend" onchange="dataAnalysis.choose_date(this, 2)">
                            <option value="0">昨天</option>
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                        </select>

                        <input type="text" id="layui-date1" class="select_visit_trend" placeholder="" lay-key="7" style="display: none;">
                        <span class="layui-card-item" style="margin-left: 20px;">${startDate!}</span> -
                        <span class="layui-card-item">${endDate!}</span>
                        <input type="hidden" name="start_date"/>
                        <input type="hidden" name="end_date"/>
                        <select name="shop_id" class="select_visit_trend shop_select"  onchange="dataAnalysis.choose_date($('.visit_trend'), 2)">
                            <option value="0"  select="selected" >全部店铺</option>
                            <#list ($shop_list as $st)
                                <option value="${st['shop_id']!}" <#if ($request['shop_id']=$st->shop_id) selected </#if>>${st['nick_name']!}</option>
                            </#list>
                        </select>
                        <button class="btn_choose">查询</button>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list page_data3">
                        <div id="echart-portrait3" style="width: 50%; height: 500px; float: left;"></div>
                        <table class="layui-table" style="width: 49%; float: left; margin-top: 20px; margin-left: 5px;">
                            <thead>
                            <tr>
                                <th>省份</th>
                                <th>用户数</th>
                            </tr>
                            </thead>
                            <tbody id="portrait-province"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // data define
    // 用户画像数据
    var activeUser, newAddUser, activeUserSum, newAddUserSum;
    // 性别分布/年龄分布/地图/省/市/终端/机型/性别
    var sexData, ageData, mapData, provinceData, cityData, platformData, devicesData, sexAnotherData, userSum;
    var minProvince = maxProvince = 0;
    var proviceTr = `
        <tr>
            <td>@1</td>
            <td>
                <span style="width: 20%; float: left;">@2</span>
                <div class="layui-progress layui-progress-big" style="width: 75%; float: left;">
                    <div class="layui-progress-bar" lay-percent="@3%" lay-showPercent="true"></div>
                </div>
            </td>
        </tr>
    `;
    var cityTr = `
        <tr>
            <td>@1</td>
            <td>
                <span style="width: 20%; float: left;">@2</span>
                <div class="layui-progress layui-progress-big" style="width: 75%; float: left;">
                    <div class="layui-progress-bar" lay-percent="@3%" lay-showPercent="true"></div>
                </div>
            </td>
        </tr>
    `;
    var sexTr = `
        <tr>
            <td>@1</td>
            <td>@2</td>
            <td>@3%</td>
        </tr>
    `;
    //统计分析js层
    var dataAnalysis = {
        choose_date: function (obj, action = 1) {
            var val = $(obj).val();
            var shop_id = $('.shop_select').val();
            var day = 0;
            var startDate, endDate, startDate1, endDate2, month1, day1, month2, day2;

            if (val == 1) {
                day = 7;
            } else if(val == 2) {
                day = 30;
            } else {
                day = 1;
            }

            var curDate = new Date();
            curDate.setDate(curDate.getDate() - 1);
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

            dataAnalysis.getTrendData(val, action, parseInt($(obj).prev().val()), startDate1, endDate2,shop_id);
        },
        getTrendData: function (type = 0, action = 0, userAction = 1, startDate = '', endDate = '',shop_id = 0) {
            util.ajax_json('/system/ajax/analysis/ajax/userPortrait', function (response) {
                if (response.error == 0) {
                    if (action > 0) {
                        dataAnalysis.userActionSearch(action, userAction, response.content.active_user, response.content.new_add_user, response.content.active_user_sum, response.content.new_add_user_sum);
                        return true;
                    }
                    activeUser = response.content.active_user;
                    newAddUser = response.content.new_add_user;
                    activeUserSum = response.content.active_user_sum;
                    newAddUserSum = response.content.new_add_user_sum;

                    sexData = userAction == 1 ? JSON.stringify(activeUser.genders) : JSON.stringify(newAddUser.genders);
                    ageData = userAction == 1 ? activeUser.ages_first : newAddUser.ages_first;
                    mapData = userAction == 1 ? JSON.stringify(activeUser.province) : JSON.stringify(newAddUser.province);
                    platformData = userAction == 1 ? JSON.stringify(activeUser.platforms) : JSON.stringify(newAddUser.platforms);
                    devicesData = userAction == 1 ? JSON.stringify(activeUser.devices) : JSON.stringify(newAddUser.devices);
                    provinceData = userAction == 1 ? activeUser.province : newAddUser.province;
                    cityData = userAction == 1 ? activeUser.city : newAddUser.city;
                    sexAnotherData = userAction == 1 ? activeUser.genders : newAddUser.genders;
                    userSum = userAction == 1 ? activeUserSum : newAddUserSum;

                    var proviceHtml = '', cityHtml = '', sexHtml = '', proviceTrData, cityTrData, sexTrData;
                    for (var i in provinceData) {
                        if (parseInt(provinceData[i]['value']) == 0) continue;
                        var per = parseInt(userSum['province']) == 0 ? (parseInt(provinceData[i]['value']) > 0 ? '100' : '0') : (parseInt(provinceData[i]['value']) / parseInt(userSum['province']) * 100);
                        proviceTrData = proviceTr;
                        proviceTrData = proviceTrData.replace(/@1/, provinceData[i]['name']);
                        proviceTrData = proviceTrData.replace(/@2/, provinceData[i]['value']);
                        proviceTrData = proviceTrData.replace(/@3/, per.toFixed(2));
                        proviceHtml += proviceTrData;
                        if (minProvince > provinceData[i]['value']) {
                            minProvince = provinceData[i]['value'];
                        }
                        if (maxProvince < provinceData[i]['value']) {
                            maxProvince = provinceData[i]['value'];
                        }
                    }
                    var b = '';
                    if(proviceHtml == ''){
                        b += `<div class="no_data_style" style="width: 48%;border: 1px solid #eee;height: 100px;margin-bottom: 10px;margin-left: 15px;float:left;">
                                <div style="width: 30px;height: 33px; margin: 12px auto auto auto" >
                                   <img src="/image/admin/no_data.png" alt="">
                                </div>
                                  <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                           </div>`;
                        if($('.page_data3').find('.no_data_style').length <= 0){
                            $('.page_data3').append(b);
                        }
                    }
                    $('#portrait-province').html(proviceHtml);
                    $('.layui-card-header select[name="city"]').trigger('change');



                    loadProgressBar();

                    dataAnalysis.initEchartPortrait3();
                }
            }, { type: type, start_date : startDate, end_date : endDate ,shop_id:shop_id})
        },
        initEchartPortrait3: function () {
            // if (eval(mapData).length < 1) {
            //     $('#echart-portrait3').html('暂无相关数据');
            //     $('#echart-portrait3').css(
            //         {
            //             'line-height': '350px',
            //             'text-align': 'center',
            //         }
            //     );
            //     return;
            // }else{
            //
            // }
            var echartPortrait3 = echarts.init(document.getElementById('echart-portrait3'));
            var echartPortrait3Option = {
                /*title: {
                    text: '',
                    textStyle: {
                        fontSize: 30
                    },
                    x: 'center'
                },*/
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
                    text: [maxProvince, minProvince],
                    showLabel: true,
                    seriesIndex: [0],
                    min: minProvince,
                    max: maxProvince,
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
                    data: eval(mapData)
                }]
            };
            echartPortrait3.setOption(echartPortrait3Option);
        },

        // 解析省
        parsePortraitProvince: function (userAction) {
            var proviceTrData, proviceHtml = '';
            var provinceSum = userAction == 1 ? activeUserSum.province : newAddUserSum.province;
            var provinceSum = parseInt(provinceSum);
            for (var i in provinceData) {
                if (parseInt(provinceData[i]['value']) == 0) continue;
                var per = provinceSum == 0 ? (parseInt(provinceData[i]['value']) > 0 ? '100' : '0') : (parseInt(provinceData[i]['value']) / provinceSum * 100);
                proviceTrData = proviceTr;
                proviceTrData = proviceTrData.replace(/@1/, provinceData[i]['name']);
                proviceTrData = proviceTrData.replace(/@2/, provinceData[i]['value']);
                proviceTrData = proviceTrData.replace(/@3/, per.toFixed(2));
                proviceHtml += proviceTrData;
                if (minProvince > provinceData[i]['value']) {
                    minProvince = provinceData[i]['value'];
                }
                if (maxProvince < provinceData[i]['value']) {
                    maxProvince = provinceData[i]['value'];
                }
            }
            $('#portrait-province').html(proviceHtml);
            loadProgressBar();
            return true;
        },
        // 解析市
        parsePortraitCity: function (userAction, fun) {
            var cityTrData, cityHtml = '';
            var citySum = userAction == 1 ? activeUserSum.city : newAddUserSum.city;
            var citySum = parseInt(citySum);
            for (var i in cityData) {
                if (parseInt(cityData[i]['value']) == 0) continue;
                var per = citySum == 0 ? (parseInt(cityData[i]['value']) > 0 ? '100' : '0') : (parseInt(cityData[i]['value']) / citySum * 100);
                cityTrData = cityTr;
                cityTrData = cityTrData.replace(/@1/, cityData[i]['name']);
                cityTrData = cityTrData.replace(/@2/, cityData[i]['value']);
                cityTrData = cityTrData.replace(/@3/, per.toFixed(2));
                cityHtml += cityTrData;
            }
            $('#portrait-city').html(cityHtml);
            fun(true);
        },

        // 用户类型筛选
        userActionSearch: function (action = 1, userAction = 1, searchActiveUser = '', searchNewAddUser = '', searchActiveUserSum = '', searchNewAddUserSum = '') {
            console.log(arguments);
            if (action == 1) {
                if (searchActiveUser != '') {
                    activeUser.genders = searchActiveUser.genders;
                    activeUser.ages_first = searchActiveUser.ages_first;
                }
                if (searchNewAddUser != '') {
                    newAddUser.genders = searchNewAddUser.genders;
                    newAddUser.ages_first = searchNewAddUser.ages_first;
                }
                sexData = userAction == 1 ? JSON.stringify(activeUser.genders) : JSON.stringify(newAddUser.genders);
                ageData = userAction == 1 ? activeUser.ages_first : newAddUser.ages_first;
                dataAnalysis.initEchartPortrait1();
                dataAnalysis.initEchartPortrait2();
            } else if(action == 2) {
                if (searchActiveUser != '') {
                    activeUser.province = searchActiveUser.province;
                    activeUser.city = searchActiveUser.city;
                    activeUserSum.province = searchActiveUserSum.province;
                    activeUserSum.city = searchActiveUserSum.city;
                }
                if (searchNewAddUser != '') {
                    newAddUser.province = searchNewAddUser.province;
                    newAddUser.city = searchNewAddUser.city;
                    newAddUserSum.province = searchNewAddUserSum.province;
                    newAddUserSum.city = searchNewAddUserSum.city;
                }
                mapData = userAction == 1 ? JSON.stringify(activeUser.province) : JSON.stringify(newAddUser.province);
                provinceData = userAction == 1 ? activeUser.province : newAddUser.province;
                cityData = userAction == 1 ? activeUser.city : newAddUser.city;
                dataAnalysis.parsePortraitProvince(userAction);
                dataAnalysis.parsePortraitCity(userAction, function (status) {
                    $('.layui-card-header select[name="city"]').trigger('change');
                    dataAnalysis.initEchartPortrait3();
                });

            } else if(action == 3) {
                if (searchActiveUser != '') {
                    activeUser.platforms = searchActiveUser.platforms;
                    activeUser.devices = searchActiveUser.devices;
                }
                if (searchNewAddUser != '') {
                    newAddUser.platforms = searchNewAddUser.platforms;
                    newAddUser.devices = searchNewAddUser.devices;
                }
                platformData = userAction == 1 ? JSON.stringify(activeUser.platforms) : JSON.stringify(newAddUser.platforms);
                devicesData = userAction == 1 ? JSON.stringify(activeUser.devices) : JSON.stringify(newAddUser.devices);
                dataAnalysis.initEchartPortrait4();
                dataAnalysis.initEchartPortrait5();
            } else if(action == 4) {
                if (searchActiveUser != '') {
                    activeUser.genders = searchActiveUser.genders;
                    activeUserSum.genders = searchActiveUserSum.genders;
                }
                if (searchNewAddUser != '') {
                    newAddUser.genders = searchNewAddUser.genders;
                    newAddUserSum.genders = searchNewAddUserSum.genders;
                }
                sexAnotherData = userAction == 1 ? activeUser.genders : newAddUser.genders;
                dataAnalysis.parsePortraitSex(userAction);
            }
        },
       
    };

    $(function(){
        $("[name='shop_id']").searchableSelect();
        // $("[name='cat_id']").searchableSelect();
    });

    //echart 初始化
    dataAnalysis.getTrendData();

    // layui 进度条
    function loadProgressBar() {
        layui.use('element', function(){
            var element = layui.element;
            element.init();
        });
    }
    $('.btn_choose').click(function (e) {
        dataAnalysis.choose_date($('.visit_trend'), 2);
    })
</script>
<#include ("system.footer")
