<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/pin_group.css?v=1.0.0" type="text/css" />
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
    .tb-decorate-list>thead>tr>th{
        background: #eef1f6;
        font-weight: normal;
        height: 38px;
    }
    .btn_adds{
        width: 100%;
        padding: 10px 23px;
    }
    .btn_adds a{
        padding: 0 10px;
        height:40px;
        line-height:40px;
        display: block;
        float: left;
    }
    .times_set{
       float: right;
        height:40px;
        display: flex;
        justify-content: center;

    }
    .ts_left{
        height: 40px;
        line-height: 40px;
    }
    .ts_left input[type='text']{
        width: 80px;
        height:30px;
    }
    .ts_left text{
        color: #999;
    }
    .btn_set_times{
        margin-left: 10px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">砍价</span>
        </div>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs clearfix">
                <li <#if (empty($nav))class="active"</#if>><a href="/admin/market/bargain/list">全部砍价活动</a></li>
                <li <#if ($nav == 1)class="active"</#if>><a href="/admin/market/bargain/list?nav=1">进行中</a></li>
                <li <#if ($nav == 2)class="active"</#if>><a href="/admin/market/bargain/list?nav=2">未开始</a></li>
                <li <#if ($nav == 3)class="active"</#if>><a href="/admin/market/bargain/list?nav=3">已过期</a></li>
                <li <#if ($nav == 4)class="active"</#if>><a href="/admin/market/bargain/list?nav=4">已停用</a></li>
            </ul>
        </div>
{{--        {{print_r($list)!}--!}
        <div class="btn_adds clearfix">
            <a href="/admin/market/bargain/add?nav=5" target="_blank">添加砍价活动</a>
            <div class="times_set">
                <div class="ts_left">砍价设置：每个被邀请的用户，单日可帮助砍价
                    <input type="text" name="daily_cut_times" value="${daily_cut_times!}" onkeyup="value=value.replace(/[^\d]/g,'')">
                    次
                    <text>设置为空时，不限制帮助砍价次数</text>
                </div>
                <a href="##" class="btn_set_times">保存设置</a>
            </div>
        </div>
    </div>
    <div class="main-container" style="padding-top: 0px">
        <form action="" method="post" id="form1" name="form1">
            {{ csrf_field()!}
            <div class="return-pin-group-box"  style="padding-top: 10px">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                                <tr class="get-list-th">
                                    <th style='width: 200px;'> 砍价活动名称 </th>
                                    <th>活动类型</th>
                                    <th style='width: 180px;'> 有效期 </th>
                                    <th > 活动状态 </th>
                                    <th style='width: 270px;'> 商品名称 </th>
                                    <th > 商品库存 </th>
                                    <th > 砍价库存 </th>
                                    <th > 成功数量 </th>
                                    <th > 发起砍价人数 </th>
                                    <th > 操作 </th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list ($list as $item)
                                    <tr>
                                        <td > ${item->bargain_name!} </td>
                                        <td>
                                            <#if  ($item->bargain_type == 1) 砍到任意金额结算
                                            <#else> 砍到指定金额结算
                                            </#if>
                                        </td>
                                        <td > ${item->start_time!}<br/>至<br/>${item->end_time!}</td>
                                        <td > ${item->status_name!}</td>
                                        <td > ${item->goods_name!} </td>
                                        <td > ${item->goods_number!} </td>
                                        <td > ${item->stock > $item->goods_number ? $item->goods_number : $item->stock!} </td>
                                        <td > ${item->success_number!} </td>
                                        <td > ${item->bargain_user_number!} </td>
                                        <td class="tb-decorate-a">
                                            <input hidden name="stop_bargain">
                                            <input hidden name="delete_bargain">
                                            <#if  (in_array($item->show_status, [0, 1]))
                                                <a href="/admin/market/bargain/add?id=${item->id!}&nav=5" target="_blank">
                                                    编辑
                                                </a><br/>
                                                <div class="erweima">
                                                    <a href="javascript:void(0);" class="hover_share" identity_id="${item->id!}" type="10">
                                                        分享
                                                    </a>
                                                </div><br/>
                                                <a href="javascript:void(0);" class="stop_bargain" item_id="${item->id!}">
                                                    停用
                                                </a><br/>
                                            <#elseif>(in_array($item->show_status, [2,3]))
                                                <#if (in_array($item->show_status, [3]))
                                                <a href="javascript:void(0);" class="restart_bargain" item_id="${item->id!}" end_time="${item->end_time!}">
                                                    启用
                                                </a><br/>
                                                </#if>
                                                <a href="javascript:void(0);" class="delete_bargain" item_id="${item->id!}">
                                                    删除
                                                </a><br/>
                                            </#if>
                                            <#if  (!in_array($item->show_status, [1]))
                                                <a href="/admin/orders/activity/order/list?top_index=4&sub_index=${sub_index!}&goods_type=3&act_id=${item->id!}">
                                                    查看砍价订单
                                                </a><br/>
                                                <a href="/admin/user/source/detail?top_index=4&sub_index=${sub_index!}&source=bargain&act_id=${item->id!}">
                                                    获取新用户明细
                                                </a><br/>
                                                <a href="/admin/market/bargain/record/list?id=${item->id!}">
                                                    查看发起砍价用户
                                                </a><br/>
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
    $(".stop_bargain").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要停用该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="stop_bargain"]').val(_this.attr("item_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    });
    $(".restart_bargain").click(function () {
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
                title: ['提示', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                var param = {
                    restart_bargain : _this.attr("item_id"),
                };
                console.log(param)
                util.ajax_json('/admin/market/bargain/list', function (res) {
                    // alert(JSON.stringify(res))
                    if (res.error == 0) {
                        util.mobile_alert("启用成功");
                        $("#form1").submit();
                    } else {
                        util.mobile_alert(res.message);
                    }
                }, param)
                layer.close(index);
            });
        });
        return;
    })
    $(".delete_bargain").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该活动吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="delete_bargain"]').val(_this.attr("item_id"));
                $("#form1").submit();
                layer.close(index);
            });
        });
    })

    $(".btn_set_times").click(function () {
        var daily_cut_times = $('[name="daily_cut_times"]').val();
        util.ajax_json('/admin/market/bargain/set/cut/times',function (res) {
            console.log(res);
            if(res.error == 0){
                util.mobile_alert('保存成功')
            }else{
                util.mobile_alert(res.message)
            }
        },{daily_cut_times:daily_cut_times})
    });
    $('.btn_copy').click(function(e){
        e.preventDefault();
        let prev = $(this).prev();
        prev[0].select();
        document.execCommand("Copy");
    })
    // var left =  $('.left-menu-content .item-menu:nth-child(2)');
    // left.find("img").attr("src","/image/admin/icon_left/bargain_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});

    function p(s) {
        return s < 10 ? '0' + s: s;
    }
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','bargain','sub_4','砍价',0);
</script>
