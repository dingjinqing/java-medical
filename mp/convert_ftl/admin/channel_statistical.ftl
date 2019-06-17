<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/channel_statistical.css" type="text/css" />
<style>

    .search_select{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        height:30px;
        width: 70px;
        display: inline-block;
        vertical-align: top;
        line-height: 26px;
        padding-left: 20px;
        color: #ffffff;
    }

    .search_select:visited {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
    }
    .search_select:link {
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #FFF;
    }
    .search ul li{
        margin-top: 10px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>渠道页面分析 / </span><span> ${info->name!}数据统计</span>
</div>
<div class="main_container">
    <form action="/admin/market/channel/statistical" id="form1" method="post">
        {{ csrf_field()!}
        <input type="hidden" name="act">
        <div class="search">
            <ul class="clearfix">
                <li>
                    <span>时间：</span>
                    <select name="time_type" id="" class="time_type">
                        <option value="2" <#if ($request['time_type'] == 2 || !$request['time_type']) selected </#if>>最近7天</option>
                        <option value="3" <#if ($request['time_type'] == 3) selected </#if>>最近30天</option>
                        <option value="4" <#if ($request['time_type'] == 4) selected </#if>>自定义</option>
                    </select>
                </li>
                <li class="time" <#if ($request['time_type'] != 4)hidden </#if>>
                    <input style="width: 150px; height: 30px;" type="text" name="start_time" value="${request['start_time']!}" placeholder="请选择时间" id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off">
                    &nbsp;&nbsp;至&nbsp;&nbsp;
                    <input style="width: 150px; height: 30px;" type="text" name="end_time" value="${request['end_time']!}" placeholder="请选择时间" id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off">

                </li>
                <li>
                    <span>查询指标：</span>
                    <select name="type" id="" >
                        <option value="0" <#if ($request['type'] == 0) selected </#if>>访问次数</option>
                        <option value="1" <#if ($request['type'] == 1) selected </#if>>访问人数</option>
                    </select>
                </li>
                <li>
                    <span>访客类型：</span>
                    <select name="user_type" id="" >
                        <option value="0" >全部</option>
                        <option value="1" <#if ($request['user_type'] == 1) selected </#if>>老用户</option>
                        <option value="2" <#if ($request['user_type'] == 2) selected </#if>>新用户</option>
                    </select>
                </li>
                <li>
                    <span>查询页面：</span>
                    <select name="id" id="" >
                        <#list ($list as $k=>$v)
                        <option value="${k!}" <#if ($info->select_name == $v) selected </#if>>${v!}</option>
                            </#list>
                    </select>
                </li>
                <li >
                    <a class="search_select" >
                        筛选
                    </a>
                </li><li >
                    <a href="javascript:void(0)" style="color:#5a8bff;" class="export" hidden>
                        导出数据
                    </a>
                </li>
            </ul>
        </div>
    </form>
    <div class="echarts_content" id="echarts">

    </div>
    <div class="info_table">
        <table width="100%">
            <thead>
                <tr>
                    <th width="25%">页面名称</th>
                    <th>访问次数</th>
                    <th>访问人数</th>
                    <th>新用户访问次数</th>
                    <th>老用户访问次数</th>
                </tr>
            </thead>
            <tbody>
            <#list ($char['channel_name'] as $key=>$value)
                <tr>
                    <td>${value!}</td>
                    <td> <#if (array_sum($char['list_all_pv'][$key]) > 0){{array_sum($char['list_all_pv'][$key])!} <#else> 0 </#if></td>
                    <td> <#if (array_sum($char['list_all_uv'][$key]) > 0){{array_sum($char['list_all_uv'][$key])!} <#else> 0 </#if></td>
                    <td> <#if (array_sum($char['list_new_uv'][$key]) > 0){{array_sum($char['list_new_uv'][$key])!} <#else> 0 </#if></td>
                    <td> <#if (array_sum($char['list_old_uv'][$key]) > 0){{array_sum($char['list_old_uv'][$key])!} <#else> 0 </#if></td>
                </tr>
            </#list>
            </tbody>
        </table>
        {{--<div class="clearfix jump_page">
                <#include "/admin/jump_page_admin.ftl">
            </div>--!}
    </div>
</div>
<#include "/admin/footer.ftl">
<script language="JavaScript" src="/js/echarts.min.js"></script>
<script>
    $("input[name='act']").val();
    var date = @json($char['date']);
    var channel_name = @json($char['channel_name']);
    var data = @json($char['data']);
    var series = [];
    var name_title = [];
    for(var i in data){
        console.log(i);
        var obj_sub = {
            name:channel_name[i],
            type:'line',
            data: data[i]
        };
        name_title.push(channel_name[i]);
        series.push(obj_sub);
    }

    var dataAnalysis = {
        initEchart:function(){
            let echartEl = echarts.init(document.getElementById('echarts'),'light');
            echartOption = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    type:'scroll',
                    data: name_title,
                    backgroundColor:'#f5f5f5',
                    borderRadius: 5,
                    itemGap:30,
                    padding:10,
                    width:1000,
                    
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
                series:series
            }
            echartEl.setOption(echartOption);
        }
    }
    dataAnalysis.initEchart();
    $(".search_select").click(function () {
        if($(".time_type").val() == 4){
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
            }else{
                util.mobile_alert("请填写时间范围");
                return false;
            }
        }
        $("#form1").submit();
    })
    $('.time_type').change(function () {
        if($(this).val() == 4){
            $(".time").show();
        }else{
            $(".time").hide();
        }
    })
    $(".export").click(function () {
        $("input[name='act']").val("export");
        $("#form1").submit();
    })


</script>