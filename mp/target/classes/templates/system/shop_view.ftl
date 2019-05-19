<#include "/system/header.ftl">

<link rel="stylesheet" href="/css/admin/overview.css?v=1.0.2" type="text/css" />
<style type="text/css">
    .title_share{
        width: 220px;
    }
    .title{
        margin-top: -15px;
        height: 30px;
        line-height: 29px;
    }
    .ov_head {
        display: initial;
    }
    .ov_head_title {
        font-size: 20px;
        font-weight: bold;
        text-align: left;
        margin-left: -18px;
    }
    .container{
        height: 350px;
        width: 60%;
        float: left;
    }
    .ov_head_con12 {
        display: flex;
        padding: 43px 0;
        padding: 10px;
        float: left;
        max-width: 40%;
        width: 40%;
        table-layout: fixed;
    }
    .ov_head_con12 table tr td{
        padding:10px;
        word-wrap: break-word;
        word-break: break-all;
        width:14.3%;
    }
    .ov_head_con12 table{
        width:100%;
    }
    .search{
        display: inline-block;
        font-weight: normal;
        font-size: 14px;
        padding-right: 5px;
        background: #fff;
        margin-right: 5px;
        height: 41px;
        /*text-align: center;*/
        width:100%;
        padding-left: 20px;
    }
    .search input,.search select{
        height: 30px;
        line-height: 30px;
        width: 130px;
    }
    .search ul li{
        display: inline-block;
        width: 270px;
    }
    .search_btn{
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        cursor: pointer;
        border: 1px solid #ccc;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        border-radius: 2px;
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
        width: 58px;
        display: inline-block;
    }
</style>
<div class="title">

    <span>${role.role_name!}  ${user['user_name']}</span>
    <span class="title_type"><#if (user['role_id'] == 1)> 子账号<#else> 管理员 </#if></span>
    <div class="title_share">
         $date }
    </div>
</div>

<div class="main-container">
    <div class="ov_head">
        <div style="padding-right: 5px;background: #fff;margin-right: 5px;<#if (shop_id)> display:none; </#if>">
            <div>
                <div class="ov_head_title">账户统计</div>
                <div class="ov_head_con1">
                    <div>
                        <a href="/system/shop/account/list">
                            <p><#if (account_number??)> ${account_number} <#else> 0 </#if></p>
                            <span>账户总数</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/shop/list">
                            <p><#if (shop_data['shop_num']??)> ${shop_data['shop_num']} <#else> 0 </#if></p>
                            <span>店铺总数</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/shop/list?is_use=1&is_enabled=0">
                            <p><#if (shop_data['effective_num']??)> ${shop_data['effective_num']} <#else> 0 </#if></p>
                            <span>有效店铺</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/shop/list?is_use=1">
                            <p><#if (shop_data['use_num']??)> ${shop_data['use_num']} <#else> 0 </#if></p>
                            <span>使用中</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/shop/account/list">
                            <p><#if (end_account_number??)> ${end_account_number} <#else> 0 </#if></p>
                            <span>将过期</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/shop/list?is_enabled=1">
                            <p><#if (shop_data['enabled_num']??)> ${shop_data['enabled_num']} <#else> 0 </#if></p>
                            <span>已禁用</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/mp/auth/list?is_auth_ok=1">
                            <p><#if (shop_auth_number??)> ${shop_auth_number} <#else> 0 </#if></p>
                            <span>授权成功</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/mp/auth/list?is_auth_ok=0">
                            <p><#if (no_shop_auth_number??) > ${no_shop_auth_number} <#else> 0 </#if></p>
                            <span>未授权成功</span>
                        </a>
                    </div>
                    <div>
                        <a href="/system/mp/auth/list?open_pay=1">
                            <p><#if (shop_pay_number??)> ${shop_pay_number} <#else> 0 </#if></p>
                            <span>开通支付</span>
                        </a>
                    </div>
                    <div style="border: none;">
                        <a href="/system/mp/auth/list?open_pay=0">
                            <p>${no_shop_pay_number!0}</p>
                            <span>未开通支付</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <#if (nick_name) >
            <div class="ov_head">
                <div style="padding-right: 5px;background: #fff;margin-right: 5px;font-size:  20px;font-weight: 800;height: 41px;font-style: unset;text-align: center;">
                        ${nick_name}
                </div>
            </div>
        </#if>
        <form action="/system/shop/shop_view" method="post" id="form1">
            <input type="hidden" name="shop_id" value="${shop_id}">
        <div class="search">
            <ul>

                <li style="width: 350px;">
                    时间：<input type="text" name="start_time" value="${request['start_time']!}" onfocus="picker()" autocomplete="off">
                    -
                    <input type="text" name="end_time" value="${request['end_time']!}" onfocus="picker()" autocomplete="off">
                </li>
                <li>
                    <div class="search_btn">搜索</div>
                </li>
            </ul>
        </div>

        <div style="padding-right: 5px;background: #fff;margin-right: 5px">
            <div class="clearfix">
                <div class="ov_head_title">用户统计 用户总数：$user_number}

                </div>

                <div class="container" id="container"></div>
                <div class="ov_head_con12">
                    <table border="2">
                        <tr style="text-align:center;">
                            <#if (request['type'] == 1 || request['type'] == '')>
                            <td>一</td>
                            <td>二</td>
                            <td>三</td>
                            <td>四</td>
                            <td>五</td>
                            <td>六</td>
                            <td>日</td>
                            </#if>
                        </tr>
                        <#list users as item>
                            <#if (request['type'] == 1 || request['type'] == '')>
                                <#if (item?index%7==0) >
                                    <tr style="text-align:center;">
                                </#if>
                                    <td style="font-size:16px;">$item}
                                        <br/><span style="font-size: 12px;color:  gray;">$key}</span></td>
                                <#if (item?index%7==6) >
                                    </tr>
                                </#if>
                            <#elseif (request['type'] == 30) >
                                <td style="font-size:16px;">${item}
                                <br/><span style="font-size: 12px;color:  gray;">$key}</span></td>
                            </#if>
                        </#list>
                    </table>
                </div>
            </div>
        </div>

        <div style="padding-right: 5px;background: #fff;margin-right: 5px">
            <div class="clearfix">
                <div class="ov_head_title">订单统计 订单总数：${order_number}

                </div>

                <div class="container" id="container_order" ></div>
                <div class="ov_head_con12">
                    <table border="2">
                        <tr style="text-align:center;">
                                <td>一</td>
                                <td>二</td>
                                <td>三</td>
                                <td>四</td>
                                <td>五</td>
                                <td>六</td>
                                <td>日</td>
                        </tr>
                        <#list orders as item >
                                <#if (item?index%7==0) >
                                    <tr style="text-align:center;">
                                        </#if>
                                        <td style="font-size:16px;">$item}
                                            <br/><span style="font-size: 12px;color:  gray;">$key}</span></td>
                                        <#if (item?index%7==6) >
                                    </tr>
                                </#if>

                        </#list>
                    </table>
                </div>
            </div>
        </div>

        <div style="padding-right: 5px;background: #fff;margin-right: 5px">
            <div class="clearfix">
                <div class="ov_head_title">订单支付统计 微信：$total_money['money']},余额：$total_money['account_money']},卡余额：$total_money['card_money']},积分：$total_money['score_money']}
                    --<div class="search">--}
                        --颗粒度：<input type="text">--}
                        --时间：<input type="text">-<input type="text">--}
                        --<div class="search_btn">搜索</div>--}
                    --</div>--}
                </div>

                <div class="container" id="container_order_money"></div>
                <div class="ov_head_con12">
                    <table border="2">
                        <tr style="text-align:center;">
                                <td>一</td>
                                <td>二</td>
                                <td>三</td>
                                <td>四</td>
                                <td>五</td>
                                <td>六</td>
                                <td>日</td>
                        </tr>
                        <#list order_money as item >
                                <#if (item?index%7==0) >
                                    <tr style="text-align:center;">
                                        </#if>
                                        <td style="font-size:16px;">${item}
                                            <br/><span style="font-size: 12px;color:  gray;">$key}</span></td>
                                        <#if (item?index%7==6) >
                                    </tr>
                                </#if>

                        </#list>

                    </table>
                </div>
            </div>
        </div>
        </form>
    </div>
</div>
<script language="JavaScript" src="/js/echarts.min.js"></script>
<script>
var orders = ${orders2_json};
var users =${users2_json};
var order_money = ${order_money2_json};
var money = ${order_money3_json};
</script>
<#noparse>
<script type="text/javascript">
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd',
                autoUpdateOnChanged: false
            }
        );
    }
    //用户图表
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    var data = [];
    var data2 = [];
    var i = 0;
    $.each(users ,function (index, value){
            data[i] = index;
            data2[i] = value;
            i++;
    } );
    option = null;
    option = {
        title: {
            text: '注册用户变化趋势',
            subtext: '不是虚构'
        },
        xAxis: {
            type: 'category',
            data: data
        },
        yAxis: {
            type: 'value'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['注册量']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name:'新增用户',
                type:'line',
                data:data2,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                },
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                }
            }
        ]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

    //订单数量图表
    var dom = document.getElementById("container_order");
    var orderChart = echarts.init(dom);
    
    var data = [];
    var data2 = [];
    var i = 0;
    $.each(orders ,function (index, value){
            data[i] = index;
            data2[i] = value;
            i++;
    } );
    option = null;
    option = {
        title: {
            text: '订单变化趋势',
            subtext: '不是虚构'
        },
        xAxis: {
            type: 'category',
            data: data
        },
        yAxis: {
            type: 'value'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['订单量']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name:'支付订单',
                type:'line',
                data:data2,
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            }
        ]
    };

    if (option && typeof option === "object") {
        orderChart.setOption(option, true);
    }
    //订单金额图表
    var dom = document.getElementById("container_order_money");
    var moneyChart = echarts.init(dom);
    
    var data = [];
    var data2 = money;
    var wx_money = [];
    var account_money = [];
    var card_money = [];
    var score_money = [];
    var i = 0;
    $.each(order_money ,function (index, value){
        data[i] = index;
        i++;
    } );

    var aj = 0;
    $.each(data2.wx_money ,function (index, value) {
        wx_money[aj] = value;
        aj++;
    });
    var bj = 0;
    $.each(data2.account_money ,function (index, value) {
        account_money[bj] = value;
        bj++;
    });
    var cj = 0;
    $.each(data2.card_money ,function (index, value) {
        card_money[cj] = value;
        cj++;
    });
    var dj = 0;
    $.each(data2.score_money ,function (index, value) {
        score_money[dj] = value;
        dj++;
    });

    option = null;
    option = {
        title: {
            text: '支付金额变化趋势',
            subtext: '不是虚构'
        },
        xAxis: {
            type: 'category',
            data: data
        },
        yAxis: {
            type: 'value'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data:['支付金额']
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        series: [
            {
                name:'微信',
                type:'line',
                data:wx_money,
                areaStyle: {normal: {},
                markPoint: {
                    data: [
                        {type: 'max', name: '最大值'},
                        {type: 'min', name: '最小值'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均值'}
                    ]
                }
            },
            {
                name:'账户余额',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {},
                data:account_money
            },
            {
                name:'卡余额',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {},
                data:card_money
            },
            {
                name:'积分支付',
                type:'line',
                stack: '总量',
                areaStyle: {normal: {},
                data:score_money
            }
        ]
    };

    if (option && typeof option === "object") {
        moneyChart.setOption(option, true);
    }
    $(".search_btn").click(function () {
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
</script>

</#noparse>

<#include "/system/footer.ftl">
