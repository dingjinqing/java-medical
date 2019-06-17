<#include ("system.header")
<link rel="stylesheet" href="/css/system/lottery_manage.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .lottery_table td{
        padding:15px 0;
    }
    .search_inputs{
        margin-bottom: 10px;
    }
    .lottery_table{
        padding-top:10px;
    }
    .paging_footer{
        margin-top: 0;
    }
    .search_inputs li div{
        margin-bottom: 10px;
    }
    .lottery_table table a{
        display: inline;
        cursor: pointer;
    }
</style>
<div class="main-container">
    {{--<div class="title">--!}
        {{--<div>--!}
            {{--<span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>--!}
            {{--<span style="color: #666;"><a href="/admin/market/integration/list">组团瓜分积分</a>/</span>--!}
            {{--<span style="color: #666;">${basicData->name!}-成团明细</span>--!}
        {{--</div>--!}
    {{--</div>--!}
    <form action="/system/market/integration/success?top_index=4&id=${request['id']!}" method="post" id="form1">
        {{csrf_field()!}
        {{--        {{print_r($data_list)!}--!}
        <input name="id" value="${basicData->id!}" hidden>
        <ul class="search_inputs">
            <li class="clearfix">
                <div>
                    <span >团ID</span>
                    <input name="group_id" type="text" value="${request['group_id']!}" placeholder="请输入团ID">
                </div>
                <div>
                    <span >成团状态</span>
                    <select name="status" id="">
                        <option value="0" selected>请选择</option>
                        <option value="1" <#if ($request['status']==1) selected </#if>>是</option>
                        <option value="2" <#if ($request['status']==2) selected </#if>>否</option>
                    </select>
                </div>
                <div>
                    <span>结束时间</span>
                    <span>
                      <input type="text" class="tb-text date-text" value="${request['start_time']!}" name="start_time"
                             id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                      <input type="text" class="tb-text date-text" value="${request['end_time']!}"  name="end_time"
                             id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                    </span>
                </div>
                <a href="##" class="btn_searchs">查询</a>
            </li>
        </ul>
        <div class="lottery_table"  <#if (!$list -> count()) style="min-height: 0px !important;" </#if>>
            <table width="100%">
                <thead>
                <td width="10%">团ID</td>
                <td width="8%">参团人数</td>
                <td>消耗积分</td>
                <td width="15%">开团时间</td>
                <td width="15%">结束时间</td>
                <td>团长昵称</td>
                <td width="15%">团长手机号</td>
                <td>成团状态</td>
                </thead>
                <tbody>
                <#list ($list as $item)
                <tr>
                    <td>${item->group_id!}</td>
                    <td><a class="a_click" pid="${item->inte_activity_id!}" gid="${item->group_id!}">${item->inte_user_sum!}</a></td>
                    <td>${item->use_integration!}</td>
                    <td>${item->start_time!}</td>
                    <td>${item->end_time!}</td>
                    <td>${item->grouper_name!}</td>
                    <td>${item->mobile!}</td>
                    <td>
                        <#if ($item->status==2)
                            拼团失败
                            <#else>
                                <#if ($item->inte_user_sum==$basicData->limit_amount)
                                    满员
                                    <#else>
                                    未满员
                                </#if>
                        </#if>
                    </td>
                </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <#if ($list ->count())
            <div class="paging_footer">
                <#include ("system.jump_page_system")
            </div>
        <#else>
            <div class="paging_footer" style="padding: 0 23px;height: 130px;">
                <#include ("system.jump_page_system")
            </div>
        </#if>
    </form>
</div>
{{--<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>--!}

<script type="text/javascript">

    $(".btn_searchs").click(function () {
        $("#page").val(1);
        if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
            util.mobile_alert('开始时间不能大于结束时间');
            return false;
        }
        $("#form1").submit();
    })
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $(document).on('click','.a_click',function () {
        var _this = $(this);
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['参团用户明细', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['850px','500px']
                , content: '/system/market/integration/group/detail?id='+ _this.attr('pid')+'&group_id='+  _this.attr('gid')//这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                    var iframeWin = window[layero.find('iframe')[0]['name']];
                }
                , yes: function (index, layero) { //保存按钮的回调
                    layer.close(index)
                }
            });
        });
    });

</script>
<#include ("system.footer")
<script type="text/javascript">
    // getPowerInfo('main_config','pin_integration','sub_4','组队瓜分积分',0);
</script>