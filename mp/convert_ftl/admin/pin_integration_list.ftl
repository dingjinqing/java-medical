<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sec_kill.css?v=1.0.0" type="text/css" />
<style>
    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
       color:#5A8BFF;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .return-sec-kill-box{
        padding-top: 10px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
            <span style="color: #666;">组团瓜分积分</span>
        </div>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if ($request['nav'] == 0) class="active" </#if>>
                    <a href="/admin/market/integration/list?nav=0">全部瓜分积分活动</a>
                </li>
                <li <#if ($request['nav'] == 1) class="active" </#if>>
                    <a href="/admin/market/integration/list?nav=1">进行中</a>
                </li>
                <li <#if ($request['nav'] == 2) class="active" </#if>>
                    <a href="/admin/market/integration/list?nav=2">未开始</a>
                </li>
                <li <#if ($request['nav'] == 3) class="active" </#if>>
                    <a href="/admin/market/integration/list?nav=3">已过期</a>
                </li>
                <li <#if ($request['nav'] == 4) class="active" </#if>>
                    <a href="/admin/market/integration/list?nav=4">已停用</a>
                </li>
            </ul>
        </div>
        <div class="btn_adds">
            <a href="/admin/market/integration/add" target="_blank">添加瓜分积分活动</a>
        </div>
    </div>

    <div class="main-container" style="padding-top: 0px">
        <form action="/admin/market/integration/list?nav=${request['nav']!}" method="post" id="form1" name="form1">
            {{ csrf_field()!}
            {{--<div class="box panel panel-body list-center-fee">
                <div class="form-inline shop-template-container">
                    <button type="button" class="coupon-search">搜索</button>
                </div>
            </div>--!}
            <div class="return-sec-kill-box">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr class="get-list-th">
                                <th width="12%"> 活动名称 </th>
                                <th width="12%"> 活动内容 </th>
                                <th width="12%"> 活动积分总量</th>
                                <th width="15%"> 有效期 </th>
                                <th > 活动状态 </th>
                                <th > 参与人数</th>
                                <th > 团数量 </th>
                                <th > 消耗积分 </th>
                                <th > 操作 </th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list ($list as $item)
                                <tr>
                                    <td>${item->name!}</td>
                                    <td> ${item->limit_amount!}人瓜分${item->inte_group!}</td>
                                    <td>${item->inte_total!}积分<br/>剩余:${item->inte_remain!}积分</td>
                                    <td>${item->start_time!}<br/>至<br/> ${item->end_time!}</td>
                                    <td>
                                        <#if ($item->pin_integration_status == 1)
                                            进行中
                                        <#elseif>($item->pin_integration_status == 2)
                                            未开始
                                        <#elseif>($item->pin_integration_status == 3)
                                            已过期
                                        <#elseif>($item->pin_integration_status == 4)
                                            已停用
                                        </#if>
                                    </td>
                                    <td>${inteUserSum[$item->id] ?? 0!}</td>
                                    <td>${inteGroupSum[$item->id] ?? 0!}</td>
                                    <td>${item->inte_total-$item->inte_remain!}</td>
                                    <td class="tb-decorate-a">
                                        <#if  (in_array($item->pin_integration_status, [1, 2]))
                                            <a href="/admin/market/integration/add?id=${item->id!}" target="_blank">
                                                编辑
                                            </a><br/>
                                            <div class="erweima">
                                                <a href="##" class="hover_share" identity_id="${item->id!}" type="22">分享</a>
                                                {{--分享弹出二维码--!}
                                            </div><br/>
                                        </#if>
                                        {{-- <#if  (in_array($item->pin_group_status, [3, 4]))
                                            <a href="javascript:void(0);" class="abort" action="del" item="${item->id!}">
                                                删除
                                            </a><br/>
                                        </#if>
                                        --!}
                                        <#if (in_array($item->pin_integration_status, [1, 2, 4]))
                                            <a href="javascript:void(0);" class="abort" action="or_enable" status="<#if  ($item->status == 0) 1 <#else> 0 </#if> " item="${item->id!}" end_time="${item->end_time!}">
                                                <#if  (in_array($item->pin_integration_status, [1, 2])) 停用 <#else> 启用 </#if>
                                            </a><br/>
                                        </#if>
                                        <#if  ($item->sec_kill_status != 2)
                                            <a href="/admin/market/integration/detail?id=${item->id!}">
                                              参团明细
                                            </a><br/>
                                            <a href="/admin/market/integration/success?id=${item->id!}">
                                              成团明细
                                            </a><br/>
                                        </#if>

                                        <#if ($item->pin_integration_status == 4 || $item->pin_integration_status == 3)
                                            <a href="#" class="abort" action="delete" id="${item->id!}">删除</a>
                                        </#if>
                                    </td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="paging_footer">
                    <#include "/admin/jump_page_admin.ftl">
                </div>
            </div>
        </form>
    </div>
</div>
<#include ('admin.share_common')
<script>
    $('.return-sec-kill-box .tb-decorate-a .abort').click(function () {
        var action = $(this).attr('action');
        if (action == 'or_enable') {
            var param = {
                id: $(this).attr('item'),
                action: action,
                status: $(this).attr('status')
            };
            if ($(this).attr('status') == 0) {
                var lay_message = '确认要停用吗？';
            }else{
                var lay_message = '确认要启用吗？';
            }
            if($(this).attr('status') == 1){
                var _this = $(this);
                var myDate = new Date();
                //获取当前年
                var year=myDate.getFullYear();
                //获取当前月
                var month=myDate.getMonth()+1;
                //获取当前日
                var date=myDate.getDate();
                var h=myDate.getHours();       //获取当前小时数(0-23)
                var m=myDate.getMinutes();     //获取当前分钟数(0-59)
                var s=myDate.getSeconds();
                var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
                if(_this.attr("end_time")<= now){
                    util.mobile_alert("该活动已过期，不可启用");
                    return false;
                }
            }
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + lay_message + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    util.ajax_json('/admin/market/integration/list', function (response) {
                        if (response.error == 0) {
                            $("#form1").submit();
                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, param)
                    layer.close(index);
                });
            });
            return;

        } else if(action == 'del') {
            var param = {
                id: $(this).attr('item'),
                action: action
            };
        }
        util.ajax_json('/admin/market/integration/list', function (response) {
            if (response.error == 0) {
                $("#form1").submit();
            } else {
                util.mobile_alert(response.message);
            }
        }, param)
        if(action == 'delete') {
            var param = {
                id: $(this).attr('id'),
                action: action,
            };
            var _this = $(this);
            console.log(param);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    util.ajax_json('/admin/market/integration/list', function (response) {
                        if (response.error == 0) {
                            $("#form1").submit();
                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, param)
                    layer.close(index);
                });
            });
        }
    })
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','pin_integration','sub_4','组队瓜分积分',0);
</script>
