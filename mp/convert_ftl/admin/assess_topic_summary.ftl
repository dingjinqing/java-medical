<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css"/>
<style type="text/css">
    .tb_paging tr td{
        border: none;
    }
    .tb_paging{
        margin-top: 10px;
    }
    #page{
        width: 80px;
        padding-left: 0;
    }
    .echarts_container{
        background-color: #fff;
        text-align: center;
    }
    .echarts_content{
        width: 600px;
        margin: auto;
        height: 400px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span>测评</span> / <span style="color: #666;">题目列表</span> / <span style="color: #666;">选项统计</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="legendData" value="${legendData!}">
            <input type="hidden" name="seriesData" value="${seriesData!}">
            <div class="echarts_container">
                <div class="echarts_content"></div>
                <div class="content_table clearfix">
                    <table width="100%">
                        <thead>
                            <tr>
                                <td>编号</td>
                                <td>选项名称</td>
                                <td>参与人数</td>
                                <td width="10%">占比</td>
                            </tr>
                        </thead>
                        <tbody>
                            <#list ($data_list as $key => $item)
                            <tr>
                                <td>${key+1!}</td>
                                <td>${item['option_name']!}</td>
                                <td>${item['select_num']!}</td>
                                <td>${item['percentage']!}</td>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
    </div>
</div>
<#include "/admin/footer.ftl">
<script language="JavaScript" src="/js/echarts.min.js"></script>
<script language="JavaScript" src="/js/echarts.china.js"></script>
<script type="text/javascript">
var legendData = $('input[name="legendData"]').val();
var seriesData = $('input[name="seriesData"]').val();
legendData = JSON.parse(legendData);
seriesData = JSON.parse(seriesData);
var data = {
    legendData:legendData,
    seriesData:seriesData
};
var option = {
    tooltip : {
        trigger: 'item',
        formatter: `<div style='text-align:left'>
                    <p>{b}</p>
                    <p>占比：{d}% </p>
                    <p>参与人数：{c}</p>
                    </div>`,
        align:'L'
    },
    legend: {
        type: 'scroll',
        orient: 'vertical',
        right: 10,
        top: 20,
        bottom: 20,
        data: data.legendData
    },
    series : [
        {
            type: 'pie',
            radius : '55%',
            center: ['40%', '50%'],
            data: data.seriesData,
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
};
var myChart = echarts.init(document.querySelector('.echarts_content'),'light');
myChart.setOption(option);

</script>
<script type="text/javascript">
    getPowerInfo('main_config','assess','sub_4','测评',0);
</script>