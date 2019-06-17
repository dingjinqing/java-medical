<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/full_cut.css?v=1.0.0" type="text/css" />
<link rel="stylesheet" href="/css/admin/user_list.css">
<style type="text/css">
    .add-child-btn:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .add-child-btn:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
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
    .vip_exclusive{
        display: inline-block;
        border: 1px #c5a050 solid;
        padding: 0px 3px;
        color: #c5a050;
        border-radius: 2px;
        font-size: 12px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">{{ trans("admin/market_manage.full_cut_title")!}</span>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if ($nav_type==0)class="active"</#if>>
                    <a href="/admin/market/fullcut/list?nav=0&top_index=4">全部满折满减活动</a>
                </li>
                <li <#if ($nav_type==1)class="active"</#if>>
                    <a href="/admin/market/fullcut/list?nav=1&top_index=4" >进行中</a>
                </li>
                <li <#if ($nav_type==2)class="active"</#if>>
                    <a href="/admin/market/fullcut/list?nav=2&top_index=4" >未开始</a>
                </li>
                <li <#if ($nav_type==3)class="active"</#if>>
                    <a href="/admin/market/fullcut/list?nav=3&top_index=4" >已过期</a>
                </li>
                <li <#if ($nav_type==4)class="active"</#if>>
                    <a href="/admin/market/fullcut/list?nav=4&top_index=4">已停用</a>
                </li>
            </ul>
        </div>

        {{--<script>--!}
            {{--// tab切换--!}
            {{--$("[data-toggle='tab']").click(function () {--!}
                {{--var url_arr = ['/admin/market/fullcut/list', '/admin/full/cut/list', '', '', ''];--!}
                {{--var idx = $(this).parent().index();--!}
                {{--if (url_arr[idx] != undefined) {--!}
                    {{--window.location.href = url_arr[idx];--!}
                {{--}--!}
            {{--});--!}
        {{--</script>--!}
        <ul class="add-child-ul" style="height: 54px">
            <li>
                <span has_goods="${has_goods!}" class="add-child-btn" >{{ trans('admin/market_manage.full_cut_list.add_full_act')!}</span>
            </li>
        </ul>
    </div>
    <div class="main-container" style="padding-top: 0px">
        <div class="return-goods-box" style="padding-top: 10px">
            <form name="formData"  action="/admin/market/fullcut/list?nav=${nav_type!}"   id="form1" method="post" >
                {{ csrf_field()!}
                <input type="hidden" name="del"/>
                <input type="hidden" name="delete"/>
                <input type="hidden" name="start"/>
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr>
                                <th width="18%">{{ trans('admin/market_manage.full_cut_list.act_name')!}</th>
                                <th width="11%">{{ trans('admin/market_manage.full_cut_list.act_style')!}</th>
                                <th width="20%">{{ trans('admin/market_manage.full_cut_list.pre_rule')!}</th>
                                <th width="15%">{{ trans('admin/market_manage.full_cut_list.date_limit')!}</th>
                                <th width="17%">{{ trans('admin/market_manage.full_cut_list.status')!}</th>
                                <th width="18%">{{ trans('admin/market_manage.full_cut_list.operation')!}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if ($act_list)
                                <#list ($act_list as $item)
                                    <tr>
                                        <td>
                                            <#if ($item->card_id)
                                            <span class="vip_exclusive">会员专享
                                            </span>
                                            </#if>
                                            ${item->act_name!}
                                        </td>
                                        <td>
                                            <#if  ($item->type == 1)
                                                每满减
                                            <#elseif> ($item->type == 2)
                                                阶梯满减
                                            <#elseif> ($item->type ==3)
                                                满折
                                            <#elseif> ($item->type ==4)
                                                第X件打折
                                            <#else>
                                                满赠
                                            </#if>
                                        </td>
                                        <td>
                                            <#list ($item->rule as $row)
                                                <#if ($item->type == 2)
                                                <p>
                                                    <#if ($row->full_money>0)
                                                        满${row->full_money!}元减${row->reduce_money!}
                                                    <#else>
                                                        满${row->amount!}件减${row->reduce_money!}
                                                    </#if>
                                                </p>
                                                <#elseif>($item->type == 1)
                                                    <p>
                                                        <#if ($row->full_money>0)
                                                            每满${row->full_money!}元减${row->reduce_money!}
                                                        <#else>
                                                            每满${row->amount!}件减${row->reduce_money!}
                                                        </#if>
                                                    </p>
                                                <#elseif> ($item->type == 4)
                                                    <p>
                                                        第${row->amount!}件打${row->discount!}折
                                                    </p>
                                                <#else>
                                                    <p>
                                                        <#if ($row->full_money>0)
                                                            满${row->full_money!}元打${row->discount!}折
                                                        <#else>
                                                            满${row->amount!}件打${row->discount!}折
                                                        </#if>
                                                    </p>
                                                </#if>
                                            </#list>
                                        </td>
                                        <td>${item->start_time!}<br/>至<br/>${item->end_time!}</td>
                                        <td>
                                            <#if  ($item->del_flag == 1)已停用
                                            <#else>
                                                <#if (date('Y-m-d H:i:s') > $item->end_time)已结束
                                                <#elseif> (date('Y-m-d H:i:s') < $item->start_time)未开始
                                                <#else>使用中</#if>
                                            </#if>
                                        </td>
                                        <td class="tb-decorate-a">
                                            <#if (date('Y-m-d H:i:s') < $item->end_time && $item->del_flag == 0)<a href="/admin/market/fullcut/edit?top_index=4&id=${item->id!}" target="_blank">{{ trans('admin/market_manage.full_cut_list.edit')!}</a>&nbsp;-&nbsp;
                                            <a href="javascript:void(0)" class="abort" act_id="${item->id!}">{{ trans('admin/market_manage.full_cut_list.stop_use')!}</a></#if>
                                            <#if ($item->del_flag == 1)<a href="javascript:void(0)" class="restart" act_id="${item->id!}" end_time="${item->end_time!}">{{ trans('admin/market_manage.full_cut_list.start_use')!}</a> - </#if>
                                            <#if (date('Y-m-d H:i:s') > $item->end_time || $item->del_flag == 1)
                                                <a href="#" class="del" id="${item->id!}">删除</a>
                                            </#if>
                                        </td>
                                    </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="paging_footer">
                <#include "/admin/jump_page_admin.ftl">
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $(".add-child-btn").click(function(){
        if($(this).attr('has_goods') == 0){
            util.mobile_alert('您已设置了全部商品参与的满减满折活动，无法添加新的活动');
        }
        else{
            window.open('/admin/market/fullcut/create?top_index=4');
        }
    });
    $('.abort').click(function(){
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $('input[name="del"]').val(_this.attr('act_id'));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $('.restart').click(function(){
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
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json('/admin/market/fullcut/list', function (response) {
                    if (response.error == 0) {
                       util.mobile_alert("启用成功");
                       location.reload();
                    }else{
                        util.mobile_alert("活动时间有冲突启用失败");
                    }
                }, {start:_this.attr("act_id")})
                // $('input[name="start"]').val(_this.attr("act_id"));
                // $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".del").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="delete"]').val(_this.attr("id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','full_cut','sub_4','满折满减',0);
</script>