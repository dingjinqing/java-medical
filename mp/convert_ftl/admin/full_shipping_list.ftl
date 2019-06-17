<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/full_shipping.css?v=1.0.1" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">满包邮活动</span>
        </div>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs clearfix">
                <li <#if ($request['nav'] == 0) class="active" </#if>><a href="/admin/market/ship/list?nav=0">全部满包邮活动</a></li>
                <li <#if ($request['nav'] == 1) class="active" </#if>><a href="/admin/market/ship/list?nav=1">进行中</a></li>
                <li <#if ($request['nav'] == 2) class="active" </#if>><a href="/admin/market/ship/list?nav=2">未开始</a></li>
                <li <#if ($request['nav'] == 3) class="active" </#if>><a href="/admin/market/ship/list?nav=3">已过期</a></li>
                <li <#if ($request['nav'] == 4) class="active" </#if>><a href="/admin/market/ship/list?nav=4">已停用</a></li>
            </ul>
        </div>
        <div class="btn_adds clearfix">
            <a href="/admin/market/ship/create?top_index=4" target="_blank">添加满包邮活动</a>
        </div>
    </div>
    <div class="main-container">
        <div class="ship_list">
            <form action="/admin/market/ship/list" method="post" id="form1">
                {{csrf_field()!}
                <table width="100%">
                    <thead>
                    <tr class="ship_first">
                        <td width="13%">活动名称</td>
                        <td width="14%">创建时间</td>
                        <td width="10%">活动商品</td>
                        <td width="20%">包邮规则</td>
                        <td width="15%">有效期</td>
                        <td>优先级</td>
                        <td>活动状态</td>
                        <td>操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list ($data_list as $k=>$item)
                        <tr>
                            <td >${item->name!}</td>
                            <td >${item->add_time!}</td>
                            <td ><#if ($item->type == 0)全部<#else>部分</#if>商品</td>
                            <td ><#list ($item->rule as $key=>$val)
                                    <#if ($key<=1)
                                        <#if ($val->con_type == 0)
                                            满${val->money!}元包邮；
                                        <#elseif>($val->con_type == 1)
                                            满${val->num!}件包邮；
                                        <#else>
                                            满${val->money!}元包邮或满${val->num!}件包邮；
                                        </#if>
                                        <#if ($key==0)<br>
                                        </#if>
                                    </#if>
                                </#list>
                                <#if ($item->rule_count>2)
                                等共${item->rule_count!}条
                                    </#if>
                            </td>
                            <td >
                                <span><#if ($item->expire_type == 0) ${item->start_time!}<br/>至<br/>${item->end_time!}<#else> 永久有效</#if></span>
                                {{--<span> 永久有效</span>--!}
                            </td>
                            <td >${item->level!}</td>
                            <td class="act_status_name"><#if ($item->status==1)进行中 <#elseif>($item->status==2)未开始<#elseif>($item->status==3)已过期<#elseif>($item->status==4)已停用</#if></td>
                            <td class="some_edit" id="${item->id!}">
                                <#if (in_array($item->status,[1,2]))<a href="/admin/market/ship/create?id=${item->id!}" class="edit" >编辑 </a>
                                    <a href="ajavscript:void(0)" class="hover_share" identity_id="${item->rule[0]->id!}" type="32">分享</a>
                                    <a href="ajavscript:void(0)" class="block_btn" type="1"> 停用</a>
                                </#if>
                                <#if (in_array($item->status,[3,4]))
                                    <a href="ajavscript:void(0)" class="del_btn"> 删除 </a>
                                </#if>
                                <#if (in_array($item->status,[4]))
                                    <a href="ajavscript:void(0)" class="block_btn" type="0"  >启用</a>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </form>

            <div class="paging_footer" style="margin-top: 12px;">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </div>
</div>
<#include ('admin.share_common')
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script>
    $(".block_btn").click(function () {
        var _this = $(this);
        var text='确认要启用吗？';
        if(_this.attr('type') == 1){
            text='确认要停用吗？';
        }
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + text + '</div>', {
                title: ['提示', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/ship/list', function (res) {
                    if (res.error == 0) {
                        if(_this.attr('type') == 1) {
                            util.mobile_alert("停用成功");
                            location.reload();
                        }else{
                            util.mobile_alert("启用成功");
                            location.reload();
                        }
                    } else {
                        util.mobile_alert(res.message);
                    }
                }, {status:_this.attr('type'),id:_this.parent().attr("id")})
                layer.close(index);
            });
        });
        return;
    })
    $(".del_btn").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                util.ajax_json('/admin/market/ship/list', function (res) {
                    if (res.error == 0) {
                            util.mobile_alert("删除成功");
                            _this.parent().parent().remove();
                    } else {
                        util.mobile_alert(res.message);
                    }
                }, {del_flag:1,id:_this.parent().attr("id")})
                layer.close(index);
            });
        });
        return;
    })
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','free_ship','sub_4','满包邮',0);
</script>

