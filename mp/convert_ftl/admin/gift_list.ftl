<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css?v=1.0.0">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .change-input {
        width: 60px;
        border: none;
        background: #fff;
        text-align: center;
    }
    .tb-decorate-list>tbody>tr>td:first-of-type{
        text-align: center;
    }
    .ipt-change{
        border: 1px solid #E5E9F4;
        width: 60px;
        height: 26px;
    }
    .yxq{
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
        color: #5a8bff;
    }
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
    input[name='page']:focus {
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
    .search-bl{
        width: 228px;
        height: 30px;
        margin: 10px 10px;
        display: inline-block;
        border-radius: 3px;
        border: 1px solid #ccc;
        line-height: 30px;
    }
    .primary{
        width: 195px;
        height: 26px;
        background-color: #fff;
        border: none;
        color: #333;
        font-size: 14px;
        padding-left: 8px;
        outline:none;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        line-height: 30px;
    }
    .add-child-btn{
        right: 22px;
        left: auto;
        height: 30px;
        line-height: 30px !important;
        padding: 0px 10px;
    }
    .add-child-ul{
        height: 50px;
    }

</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">${title!}</span>
    </div>
    <div class="main-container">
        <div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li <#if ($request['nav'] == 0)class="active"</#if>>
                        <a href="/admin/market/gift/list?nav=0" >全部赠品活动</a>
                    </li>
                    <li <#if ($request['nav'] == 1)class="active"</#if>>
                        <a href="/admin/market/gift/list?nav=1">进行中</a>
                    </li>
                    <li <#if ($request['nav'] == 2)class="active"</#if>>
                        <a href="/admin/market/gift/list?nav=2" >未开始</a>
                    </li>
                    <li <#if ($request['nav'] == 3)class="active"</#if>>
                        <a href="/admin/market/gift/list?nav=3">已过期</a>
                    </li>
                    <li <#if ($request['nav'] == 4)class="active"</#if>>
                        <a href="/admin/market/gift/list?nav=4">已停用</a>
                    </li>
                </ul>
            </div>
            <form action="/admin/market/gift/list" method="post" id="form1">
                {{csrf_field()!}
                <ul class="add-child-ul">
                    <li>
                     <span style="position: absolute;left: 0px" >
                        <span style="padding-left: 30px">活动名称</span>
                        <span class="search-bl">
                            <input type="text" name='name' value="${request['name']!}" placeholder="请输入活动名称"
                                   class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span>
                        <button class="btn_searchinfo">查询</button>
                    </span>
                        <a href="/admin/market/gift/add" class="add-child-btn" target="_blank">添加赠品活动</a>
                    </li>
                </ul>
                <div class="return-goods-box">
                    <form action="/admin/market/gift/list" method="post" id="form1">
                        {{csrf_field()!}
                        <input name="del" type="hidden">
                        <input name="enable" type="hidden">
                        <input name="delete" type="hidden">
                        <input name="nav" type="hidden" value="${request['nav']!}">
                        <div class="goods-box-edit">
                            <div class="goods-edit-basic">
                                <table class="tb-decorate-list">
                                    <thead>
                                    <tr>
                                        <th width="13%">活动名称</th>
                                        <th width="13%">有效期</th>
                                        <th width="7%">优先级</th>
                                        <th width="10%">赠送次数</th>
                                        <th width="14%">活动状态</th>
                                        <th width="15%">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list ($list as $gift)
                                            <tr item="${gift->id!}">
                                                <td>
                                                    ${gift->name!}
                                                </td>
                                                <td class="yxq">
                                                    <span>${gift->start_time!}</span>
                                                    至
                                                    <span>${gift->end_time!}</span>
                                                </td>
                                                <td>
                                                    <input type="text" class="change-input" onkeyup="value=value.replace(/[^\d.]/g,'')" value="${gift->level!}" disabled />
                                                    <img src="/image/admin/good_edit.png" class="goods-number-img">
                                                </td>
                                                <td>${gift->gift_times!}</td>
                                                <td>
                                                    <#if  ($gift->nav == 1)
                                                        未开始
                                                        <#elseif> ($gift->nav == 2)
                                                        进行中
                                                        <#elseif> ($gift->nav == 3)
                                                        已过期
                                                        <#elseif> ($gift->nav == 4)
                                                        已停用
                                                    </#if>
                                                </td>
                                                <td class="tb-decorate-a">
                                                    <#if  (in_array($gift->nav, [1, 2]))
                                                        <a href="/admin/market/gift/add?id=${gift->id!}" target="_blank">编辑</a> -
                                                    </#if>

                                                    <#if  (in_array($gift->nav, [1, 2]))
                                                        <a href="javascript:void(0)" class="abort" act_id="${gift->id!}">停用</a>&nbsp;-&nbsp;
                                                    </#if>

                                                    <#if ($gift->is_enable == 1)
                                                        <a  href="javascript:void(0)" class="enable" act_id="${gift->id!}">启用</a>&nbsp;-&nbsp;
                                                    </#if>

                                                    <a href="/admin/market/gift/record?id=${gift->id!}" target="_blank">赠送明细</a>
                                                    <#if ($gift->nav != 2)
                                                        &nbsp;-&nbsp;<a href="#" act_id="${gift->id!}" class="del">删除</a>
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
                    </form>
                </div>
            </form>
        </div>
    </div>
</div>
<script>

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
                util.ajax_json(' /admin/ajax/gift/set', function(response){
                    if (response && response.error == 0){
                        util.mobile_alert('停用成功');
                        layer.close(index);
                        window.location = '/admin/market/gift/list?nav=${request['nav']!}';
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"set-disable", id:_this.attr('act_id')});
            });
        });
    });
    $(".enable").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要启用吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                util.ajax_json(' /admin/ajax/gift/set', function(response){
                    if (response && response.error == 0){
                        util.mobile_alert('启用成功');
                        layer.close(index);
                        window.location = '/admin/market/gift/list?nav=${request['nav']!}';
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"set-enable", id:_this.attr('act_id')});
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
                util.ajax_json(' /admin/ajax/gift/set', function(response){
                    if (response && response.error == 0){
                        util.mobile_alert('删除成功');
                        layer.close(index);
                        window.location = '/admin/market/gift/list?nav=${request['nav']!}';
                    }else{
                        util.mobile_alert(response.message);
                    }
                },{act:"del", id:_this.attr('act_id')});
            });
        });
    })
    function p(s) {
        return s < 10 ? '0' + s: s;
    }

    var level;
    $(".change-input").focus(function () {
        level = $(this).val();
    });
    $(".change-input").blur(function (e) {
        var new_level = $(this).val();
        if(Number(new_level) == Number(level) && new_level != ''){
            $('.ipt-change').next().show();
            $('.change-input').removeClass('ipt-change');
            $(".change-input").attr("disabled",true);
        }
    });
    $(".change-input").change(function(e){
        var gift_id = $(this).parent().parent().attr('item');
        var level = $(this).val();
        var that = $(this);
        if (level == '') {
            util.mobile_alert('优先级不能为空！');
            $(this).focus();
            return false;
        }
        util.ajax_json(' /admin/ajax/gift/set', function(response){
            if (response && response.error == 0){
                util.mobile_alert('设置成功');
                that.removeClass('ipt-change');
                that.next().show();
                $(".change-input").attr("disabled", true);
                window.location = '/admin/market/gift/list?nav=${request['nav']!}';
            }else{
                util.mobile_alert(response.message);
            }
        },{act:"set_level", id:gift_id, level: level});
    });
    $(document).on('click','.goods-number-img',function () {
        $(this).prev().addClass('ipt-change');
        $(this).prev().attr("disabled", false);
        $(this).prev().focus();
        $(this).hide();
    });
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','gift','sub_4','赠品策略',0);
</script>
