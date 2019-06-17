<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/fighting_group_draw.css?v=1.0.1" type="text/css" />
<style>
    td a{
        color: #5A8BFF;
        display: block;
    }
    .tb-decorate-list tbody tr>td>a{
        text-decoration: none;
        color: #5A8BFF;
    }
    a, a:active, a:hover, a:focus {
        text-decoration: none;
    }
    .tb-decorate-list .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-list .erweima a{
        color:#5A8BFF;
    }
</style>
<div style="min-width: 1090px;">
	<div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span style="color: #666;">拼团抽奖</span>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if (empty($nav))class="active"</#if>>
                    <a href="/admin/market/groupdraw/list?top_index=4">全部拼团抽奖活动</a>
                </li>
                <li <#if ($nav == 1)class="active"</#if>>
                    <a href="/admin/market/groupdraw/list?nav=1&top_index=4" >进行中</a>
                </li>
                <li <#if ($nav == 2)class="active"</#if>>
                    <a href="/admin/market/groupdraw/list?nav=2&top_index=4" >未开始</a>
                </li>
                <li <#if ($nav == 3)class="active"</#if>>
                    <a href="/admin/market/groupdraw/list?nav=3&top_index=4" >已过期</a>
                </li>
                <li <#if ($nav == 4)class="active"</#if>>
                    <a href="/admin/market/groupdraw/list?nav=4&top_index=4">已停用</a>
                </li>
            </ul>
        </div>
        <form name="formData" action="/admin/market/groupdraw/list?nav=${request['nav']!}" id="form1" method="post">
            {{ csrf_field()!}
            <div class="order-serarch-info" style="padding-bottom: 0px;">
                <ul class="clearfix">
                    <li class="check_info_li clearfix">
                        <div class="fl">
                            <span>活动名称</span>
                            <input type="text" value="${request['name']!}" name="name" placeholder="请输入活动名称">
                        </div>
                        <div class="fl time_choose">
                            <span>活动有效期</span>
                            <input type="text" name="start_time" value="${request['start_time']!}" onclick="picker();" autocomplete="off">
                            至
                            <input type="text" name="end_time" value="${request['end_time']!}" onclick="picker();" autocomplete="off">
                        </div>
                        <div class="fl">
                            <button type="submit" class="btn-search">筛选</button>
                        </div>
                    </li>
                </ul>
            </div>
            <ul class="add-child-ul">
                <li>
                    <a class="add-child-btn" href="/admin/market/groupdraw/add?top_index=4&nav=5" target="_blank">添加拼团抽奖活动</a>
                </li>
            </ul>
            <div class="return-goods-box" style="margin-top: 10px">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                                <tr>
                                    <th width="10%">活动名称</th>
                                    <th width="12%">有效期</th>
                                    <th width="5%">状态</th>
                                    <th width="5%">商品数</th>
                                    <th width="10%">最少成团人数</th>
                                    <th width="10%">开奖所需最少人数</th>
                                    <th width="7%">参与人数</th>
                                    <th width="7%">成团人数</th>
                                    <th width="5%">开团数</th>
                                    <th width="7%">中奖用户</th>
                                    <th width="10%">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list ($data_list as $item)
                                    <tr>
                                        <td>${item->name!}</td>
                                        <td>${item->start_time!} <br/>至<br/> ${item->end_time!}</td>
                                        <td>
                                            <#if ($item->status==0)
                                                已停用
                                            <#elseif>($item->end_time < date("Y-m-d H:i:s",time()))
                                                已过期

                                            <#elseif>($item->start_time < date("Y-m-d H:i:s",time()))
                                                进行中
                                            <#elseif>($item->start_time > date("Y-m-d H:i:s",time()))
                                                未开始
                                            </#if>
                                        </td>
                                        <td>{{count(explode(',', $item->goods_id))!}</td>
                                        <td>${item->limit_amount!}</td>
                                        <td>${item->min_join_num!}</td>
                                        <td>${item->join_num!}</td>
                                        <td>${item->success_user_num!}</td>
                                        <td>${item->open_group_num!}</td>
                                        <td>${item->draw_user_num!}</td>
                                        <td item="${item->id!}">
                                            <#if  (in_array($nav, [1, 2]))
                                                <a href="/admin/market/groupdraw/add?top_index=4&id=${item->id!}">编辑</a>
                                                <div class="erweima">
                                                    <a href="##" class="hover_share" identity_id="pages/pinlotterylist/pinlotterylist?group_draw_id=${item->id!}" type="33">分享</a>
                                                    {{--分享弹出二维码--!}
                                                </div><br/>
                                            </#if>
                                            <#if  (!in_array($item->nav, [3, 4]) && $item->status == 1)
                                                    <a href="javascript:void(0)" class="abort"> 停用 </a>
                                            </#if>
                                            <#if  ($item->is_enable == 1)
                                                <a href="#" class="enable"> 启用 </a>
                                            </#if>
                                            <#if ($item->start_time < date("Y-m-d H:i:s",time()))
                                                <a href="/admin/orders/activity/order/list?top_index=${top_index!}&sub_index=${sub_index!}&act_id=${item->id!}&goods_type=8"  target="_blank">查看活动订单</a>
                                                <a href="/admin/market/groupdraw/user?top_index=4&id=${item->id!}"  target="_blank">查看参与用户</a>
                                                <a href="/admin/user/source/detail?top_index=${top_index!}&sub_index=${sub_index!}&act_id=${item->id!}&source=group_draw"  target="_blank">获取新用户明细</a>
                                                <a href="/admin/market/groupdraw/detail?top_index=4&id=${item->id!}"  target="_blank">开团明细</a>
                                            </#if>
                                            <#if  (in_array($item->nav, [3, 4]))
                                                <a href="#" class="del"> 删除 </a>
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

<#include "/admin/footer.ftl">
<#include ('admin.share_common')
<script>
	 function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }

     $('.abort').click(function(){
         var _this = $(this);
         var id = _this.parent().attr('item');
         layui.use('layer', function () {
             var layer = layui.layer;
             layer.alert('<div style="text-align: center;">' + '确认要停用吗？' + '</div>', {
                 title: ['提醒', 'text-align:center;padding: 0px;']
                 , area: '260px'
                 , closeBtn: 0
                 , btn: ['确定', '取消']
             },function(index){
                 util.ajax_json("/admin/market/groupdraw/list",function (res) {
                     if(res.error == 0){
                         util.mobile_alert("停用成功");
                         location.reload();
                     }
                 },{id: id,act: 'disable'})

             });
         });
     });
     $(".enable").click(function () {
         var _this = $(this);
         var id = _this.parent().attr('item');
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
                 title: ['提示', 'text-align:center;padding: 0px;']
                 , area: '260px'
                 , closeBtn: 0
                 , btn: ['确定', '取消']
             }, function (index) {
                 var param = {
                     id : id,
                     act:'enable'
                 };
                 util.ajax_json('/admin/market/groupdraw/list', function (res) {
                     if (res.error == 0) {
                         util.mobile_alert("启用成功");
                         location.reload();
                     } else {
                         util.mobile_alert(res.message);
                     }
                 }, param)
                 layer.close(index);
             });
         });
         return;
     })
     $(".del").click(function () {
         var _this = $(this);
         var id = _this.parent().attr('item');
         layui.use('layer', function () {
             var layer = layui.layer;
             layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                 title: ['提醒', 'text-align:center;padding: 0px;']
                 , area: '260px'
                 , closeBtn: 0
                 , btn: ['确定', '取消']
             }, function (index) {
                 $('input[name="delete"]').val(_this.attr("reduce_id"));
                 // $("#form1").submit();
                 var param = {
                     id : id,
                     act:'del'
                 };
                 util.ajax_json('/admin/market/groupdraw/list', function (res) {
                     if (res.error == 0) {
                         util.mobile_alert("删除成功");
                         location.reload();
                     } else {
                         util.mobile_alert(res.message);
                     }
                 }, param)
                 layer.close(index);
             });
         });
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
<script type="text/javascript">
    getPowerInfo('main_config','group_draw','sub_4','拼团抽奖',0);
</script>
