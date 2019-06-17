<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sec_kill.css?v=1.0.1" type="text/css" />
<style>
    .tb-decorate-a .erweima{
        position: relative;
        display: inline-block;
    }
    .tb-decorate-a .erweima a{
        color: #5a8bff;
    }
    .tb-decorate-a .share_span{
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 20px;
        left:-175px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }
    .tb-decorate-a .a{
        width: 28px;
        height:30px;
        display: inline-block;
    }
    .tb-decorate-a .share_span .share_sj{
        position: absolute;
        right: 90px;
        top: -7px;
    }
    .tb-decorate-a .share_span span{
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }
    .tb-decorate-a .share_span .code_imgs{
        display: block;
        margin:0 auto;
    }
    .tb-decorate-a .share_span a{
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
    }
    .share_link{
        padding-top: 15px;
        width: 100%;
    }
    .share_link input{
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }
    .share_link button{
        float: right;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
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
    .vip_exclusive{
        display: inline-block;
        border: 1px #c5a050 solid;
        padding: 0px 3px;
        color: #c5a050;
        border-radius: 2px;
        font-size: 12px;
    }
    .import_popup_title{
        margin-bottom: 14px;
    }
    .card_box > div:last-of-type{
        margin-top: 10px;
    }
    .card_box input[type="text"]{
        width: 140px;
        line-height: 30px;
        border: 1px solid #ddd;
    }
    .file_box{
        display: inline-block;
        border: 1px solid #ddd;
        width: 250px;
        height: 30px;
        background-color: #fff;
        vertical-align: middle;
        margin-top: 10px;
    }
    .file_box span.fl{
        width: 168px;
        line-height: 28px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .file_box span.fr{
        text-align: center;
        line-height: 28px;
        width: 80px;
        background-color: #f5f5f5;
        cursor: pointer;
        border-left: 1px solid #ddd;
    }
    .file_upload_explain{
        margin-top: 16px;
        color: #666;
    }
    .file_upload_explain > p+p{
        padding-left:70px;
    }
    input[type="file"]{
        width: 0;
        opacity: 0;
        height: 0;
    }
    .btn_adds input[type="text"],.btn_adds select{
        width:140px;
        line-height:30px;
        height:30px;
        vertical-align: middle;
    }
    .btn_adds .btn_searchinfo{
        padding: 10px;
        background-color: #5a8bff;
        border: 1px solid #5A8BFF;
        color: #fff;
        font-size: 14px;
    }
    .primary,.btn_searchinfo,.list-center-sel{
        margin-left: 30px;
    }
    .goods_price_popup{
        padding: 0 15px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
            <span>
                {{ trans("admin/shop.list-top.li_top_name")!}
            </span>/
        <span>
                批量改价
        </span>
    </div>
    <div class="main-container">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/reduce/list?nav=0&top_index=4" >全部限时降价活动</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=1&top_index=4">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=2&top_index=4" >未开始</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=3&top_index=4">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=4&top_index=4">已停用</a>
                </li>
                <li class="active">
                    <a href="/admin/market/reduce/price/list">批量降价</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/profit/list">批量加价率</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="main-container" style="padding-top: 0px">
        <form name="form1" action="/admin/market/reduce/price/list"  id="form1" method="post" >
            {{ csrf_field()!}
            <div class="btn_adds">
                
                {{--<a  id="add_price" target="_blank">添加改价活动</a>--!}
                    <input type="hidden" name="act" id="act" value="">
                    <span>活动时间：</span>
                    <input type="text" class="tb-text date-text" value="${request['act_start_time']!}" name="act_start_time"
                           id="startTime" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                    <input type="text" class="tb-text date-text" value="${request['act_end_time']!}"  name="act_end_time"
                           id="endTime" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                    <input type="text" name='act_name' value="${request['act_name']!}" placeholder="请搜索活动"
                           class="primary" >
                    <input type="text" name='prd_sn' value="${request['prd_sn']!}" placeholder="请搜索商品编码"
                           class="primary" >
                    <select name="act_status" class="list-center-sel">
                        <option value="0">请选择活动状态</option>
                        <option value="2" <#if ($request['act_status'] == 2)selected="selected"</#if>>未开始</option>
                        <option value="1" <#if ($request['act_status'] == 1)selected="selected"</#if>>进行中</option>
                        <option value="3" <#if ($request['act_status'] == 3)selected="selected"</#if>>已过期</option>
                        <option value="4" <#if ($request['act_status'] == 4)selected="selected"</#if>>已停用</option>
                    </select>
                    <button class="btn_searchinfo" style="margin-left:10px;line-height: 8px;">查询</button>
            </div>
            <div class="btn_adds" style="padding-top:0;">
                <a href="/admin/market/reduce/add?type=1&top_index=4" target="_blank" style="margin-right:10px;">
                    添加限时降价活动
                </a>
                <a  id="export_csv"  class="btn_exp" target="_blank">导出改价活动</a>
            </div>
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
                                <th width="20%"> 商品编码 </th>
                                <th width="10%"> 商品价格 </th>
                                <th width="20%"> 活动名称 </th>
                                <th width="20%"> 有效期 </th>
                                <th width="10%"> 活动状态 </th>
                                <th width="20%"> 添加时间 </th>
                                {{--<th width="20%"> 操作 </th>--!}
                            </tr>
                            </thead>
                            <tbody>
                            <#list ($list as $item)
                                <tr>
                                    <td style="text-align: center"> ${item->prd_sn!}</td>
                                    <td> ${item->price!}</td>
                                    <td> ${item->name!}</td>
                                    <td>${item->start_time!} <br/>至<br/> ${item->end_time!}</td>
                                    <td>
                                        <#if ($item->act_status == 1)
                                            进行中
                                        <#elseif>($item->act_status == 2)
                                            未开始
                                        <#elseif>($item->act_status == 3)
                                            已过期
                                        <#elseif>($item->act_status == 4)
                                            已停用
                                        </#if>
                                    </td>
                                    <td> ${item->add_time!}</td>
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
<div class="goods_price_popup" hidden>
    <form action="" method="POST" id="form2">
        {{csrf_field()!}
        {{--<p class="import_popup_title">第一步：模板下载</p>--!}
        <div class="card_box">
            <div>
                <span>活动名称：</span>
                <input type="text" class="name" placeholder="请填写活动名称" name="name" value="${basicData->name!}" maxlength="20">
                <span class="prompt">只作为商家记录使用，用户不会看到这个名称</span>
                {{--<a href="{{ main_url('doc/goods.xlsx')!}" style="color: #5a8bff;">价格导入文件模板</a>--!}
            </div>
        </div>
        <div class="card_box">
            <div>
                <span>活动时间：</span>
                <input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time"
                       id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                <input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time"
                       id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                {{--<a href="{{ main_url('doc/goods.xlsx')!}" style="color: #5a8bff;">价格导入文件模板</a>--!}
            </div>
        </div>
        {{--<p class="import_popup_title">第二步：数据导入</p>--!}
        <div class="file_upload">
            <div>
                <span style="vertical-align: middle">上传文件：</span>
                <div class="file_box clearfix">
                    <span class="fl filetext"></span>
                    <span class="fr" onclick="document.getElementById('file').click()">浏览...</span>

                </div>
                <a href="{{ main_url('doc/batch_price.xlsx')!}" style="color: #5a8bff;">价格导入文件模板</a>
                <input type="file" name="goods_price" id="file" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[0].innerText=this.value">
            </div>
            <div class="file_upload_explain">
                <p>导入规则：文件当前仅支持excel格式。</p>
            </div>
        </div>
    </form>
</div>
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
                    util.ajax_json('/admin/market/seckill/list', function (response) {
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
        util.ajax_json('/admin/market/seckill/list', function (response) {
            if (response.error == 0) {
                $("#form1").submit();
            } else {
                util.mobile_alert(response.message);
            }
        }, param)
        if(action == 'delete') {
            var param = {
                id: $(this).attr('pin_id'),
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
                    util.ajax_json('/admin/market/seckill/list', function (response) {
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
    $(".hover_share").hover(function(){
        var sk_id = $(this).attr("item");
        var that = $(this);
        util.ajax_json('/admin/market/seckill/getqrcode', function(response){
            if(response && response.error==0){
                that.parent().find('.code_imgs').attr("src", response.content.qrcode_img);
                that.parent().find('.down_imgs').attr("href", response.content.qrcode_img);
                that.parent().find("#fe_text").val(response.content.type_url);
                $('.share_span').hide();
                that.parent().find('.share_span').show();
            }else{
                util.mobile_alert(response.message);
            }
        },{sk_id:sk_id});
    },function(){
        $(this).parent().find('.share_span').hide();
    });
    $('.share_span').hover(function () {
        $(this).show();
    },function () {
        $(this).hide();
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
    $("#add_price").click(function(){
        console.log(11);
        layui.use('layer', function() {
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['批量价格导入', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['570px','auto']
                , content: $('.goods_price_popup')
                , btn: ['导入']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    var formData = new FormData();
                    formData.append('goods_price', $('#file')[0].files[0]);
                    formData.append('name', $('input[name="name"]').val());
                    formData.append('start_time', $('input[name="start_time"]').val());
                    formData.append('end_time', $('input[name="end_time"]').val());
                    $.ajaxSetup({
                        contentType : false,
                        processData : false
                    });
                    util.ajax_json('/admin/goods/manage/price/add', function (response) {
                        if (response.error == 0) {
                            util.mobile_alert(response.content);
                            setTimeout(function () {
                                layer.close(index);
                                location.reload();
                            }, 2000);

                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, formData)
                    $('.goods_price_popup').hide()
                    layer.close(index);
                }
                ,cancel:function(){
                    $('.goods_price_popup').hide()
                }
            });
        });
    })
    $(".btn_exp").click(function () {
        $("#act").val("export");
        $("#form1").submit();
        $("#act").val('');
    })
    $('[name="act_status"]').change(function(){
        $("#form1").submit();
    });
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','seckill_goods','sub_4','抽奖',0);
</script>
