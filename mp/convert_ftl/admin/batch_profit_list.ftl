<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sec_kill.css?v=1.0.1" type="text/css" />
<style>
    .change-input{
        width:60px;
        border: none;
        outline: none;
        position: relative;
        top: 2px;
        text-align: center;
        background-color: #fff;
    }
    .ipt-change{
        border: 1px solid #E5E9F4;
        width: 60px;
        height: 26px;
    }
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
    .file_box{
        display: inline-block;
        border: 1px solid #ddd;
        width: 250px;
        height: 30px;
        background-color: #fff;
        vertical-align: middle;
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
    .goods_profit_popup{
        padding: 0 15px;
    }
    .import_popup_title{
        margin-top: 15px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
            <span>
                {{ trans("admin/shop.list-top.li_top_name")!}
            </span>/
        <span>
                批量毛利
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
                <li>
                    <a href="/admin/market/reduce/price/list">批量降价</a>
                </li>
                <li class="active">
                    <a href="/admin/market/reduce/profit/list">批量加价率</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="main-container">
        <form name="form1" action="/admin/market/reduce/profit/list"  id="form1" method="post" >
            {{ csrf_field()!}
            <div class="btn_adds">
                {{--<a id="add_profit" target="_blank">导入批量毛利</a>--!}

                <input type="hidden" name="act" id="act" value="">
                <span>活动时间：</span>
                <input type="text" class="tb-text date-text" value="${request['act_start_time']!}" name="act_start_time"
                       id="startTime" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                <input type="text" class="tb-text date-text" value="${request['act_end_time']!}"  name="act_end_time"
                       id="endTime" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                <input type="text" name='act_name' value="${request['act_name']!}" placeholder="请搜索活动"
                       class="primary" >
                <select name="act_status" class="list-center-sel">
                    <option value="0">请选择活动状态</option>
                    <option value="2" <#if ($request['act_status'] == 2)selected="selected"</#if>>未开始</option>
                    <option value="1" <#if ($request['act_status'] == 1)selected="selected"</#if>>进行中</option>
                    <option value="3" <#if ($request['act_status'] == 3)selected="selected"</#if>>已过期</option>
                    <option value="4" <#if ($request['act_status'] == 4)selected="selected"</#if>>已停用</option>
                </select>
                <select name="brand_id" class="list-center-sel">
                    <option value="0">请选择商品品牌</option>
                    <#list ($brand_list as $item)
                        <option value="${item->id!}" <#if ($request['brand_id'] == $item->id)selected="selected"</#if>>${item->brand_name!}</option>
                    </#list>
                </select>
                <select name="sort_id" class="list-center-sel">
                    <option value="0">请选择商家分类</option>
                    <#list ($sort_list as $item)
                        <option value="${item->sort_id!}" <#if ($request['sort_id'] == $item->sort_id)selected="selected"</#if>>${item->sort_name!}</option>
                    </#list>
                </select>
                {{--<select name="status" class="list-center-sel">--!}
                    {{--<option value="0">请选择使用状态</option>--!}
                    {{--<option value="2" <#if ($request['status'] == 2)selected="selected"</#if>>使用中</option>--!}
                    {{--<option value="1" <#if ($request['status'] == 1)selected="selected"</#if>>已停用</option>--!}
                {{--</select>--!}
            </div>
            <div class="btn_adds" style="padding-top:0;">
                
                    <a href="/admin/market/reduce/add?type=1&top_index=4" target="_blank" style="margin-right:10px;">
                            添加限时降价活动
                    </a>
                    <a id="export_csv" target="_blank">导出批量毛利活动</a>
                    <button class="btn_searchinfo" style="margin-left:10px;line-height:16px">查询</button>
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
                                <th width="10%"> 商家分类 </th>
                                <th width="10%"> 商品品牌 </th>
                                <th width="10%"> 商品毛利 </th>
                                <th width="20%"> 活动名称 </th>
                                <th width="20%"> 有效期 </th>
                                <th width="10%"> 活动状态 </th>
                                <th width="20%"> 添加时间 </th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list ($list as $item)
                                <tr>
                                    <td style="text-align: center"> ${item->sort_name!}</td>
                                    <td> ${item->brand_name!}</td>
                                    <#if ($item->end_time >= now())
                                    <td> <span>
                                    <input type="text" value="${item->profit_per!}" onkeyup="value=value.replace(/[^\d.]/g,'')" name="profit_per" bs_id="${item->id!}"  class="change-input" disabled onkeyup="value=value.replace(/[^\d.]/g,'')" />
                                                %<img src="http://${image_domain!}/image/admin/good_edit.png" grade="${item->grade!}" alt="" class="goods-profit-img" />
                                </span></td>
                                    <#else>
                                    <td>${item->profit_per!}%</td>
                                    </#if>
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
<div class="goods_profit_popup" hidden>
    <form action="" method="POST" id="form2">
        {{csrf_field()!}
        <p class="import_popup_title">第一步：模板下载</p>
        <div class="card_box">
            <div>
                <span>下载模板：</span>
                <a href="{{ main_url('doc/batch_profit.xlsx')!}" style="color: #5a8bff;">批量毛利导入文件模板</a>
            </div>
        </div>
        <p class="import_popup_title">第二步：数据导入</p>
        <div class="file_upload">
            <div>
                <span style="vertical-align: middle">上传文件：</span>
                <div class="file_box clearfix">
                    <span class="fl filetext"></span>
                    <span class="fr" onclick="document.getElementById('file').click()">浏览...</span>
                </div>
                <input type="file" name="goods_profit" id="file" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[0].innerText=this.value">
            </div>
            <div class="file_upload_explain">
                <p>导入规则：文件当前仅支持excel格式。</p>
            </div>
        </div>
    </form>
</div>
<script>
    $('.abort').click(function () {
        var action = $(this).attr('action');
        if (action == 'or_enable') {
            var param = {
                id: $(this).attr('bs_id'),
                action: action,
                status: $(this).attr('status')
            };
            if ($(this).attr('status') == 0) {
                var lay_message = '确认要停用吗？';
            }else{
                var lay_message = '确认要启用吗？';
            }
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + lay_message + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                }, function (index) {
                    util.ajax_json('/admin/goods/manage/profit/list', function (response) {
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

        }
        util.ajax_json('/admin/market/seckill/list', function (response) {
            if (response.error == 0) {
                $("#form1").submit();
            } else {
                util.mobile_alert(response.message);
            }
        }, param)
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
    $("#add_profit").click(function(){
        console.log(11);
        layui.use('layer', function() {
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['商品导入', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['500px','auto']
                , content: $('.goods_profit_popup')
                , btn: ['导入']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    var formData = new FormData($('.goods_profit_popup'));
                    formData.append('goods_profit', $('#file')[0].files[0]);
                    $.ajaxSetup({
                        contentType : false,
                        processData : false
                    });
                    util.ajax_json('/admin/goods/manage/profit/add', function (response) {
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
                    $('.goods_profit_popup').hide()
                    layer.close(index);
                }
                ,cancel:function(){
                    $('.goods_profit_popup').hide()
                }
            });
        });
    })

    $('[name="brand_id"]').change(function(){
        $("#form1").submit();
    });
    $('[name="sort_id"]').change(function(){
        $("#form1").submit();
    });
    $('[name="act_status"]').change(function(){
        $("#form1").submit();
    });
    $('#export_csv').click(function(){
        $('#act').val('export');
        $('#form1').submit();
        $('#act').val('');
    })
    var _val;
    $(".change-input").focus(function () {
        _val = $(this).val();
    });
    $(".change-input").blur(function (e) {
        var b_val = $(this).val();
        if(Number(b_val) == Number(_val) && b_val != ''){
            //console.log(111);
            $('.ipt-change').next().show();
            $('.change-input').removeClass('ipt-change');
            $(".change-input").attr("disabled",true);
        }
    });
    $(".change-input").change(function(e){
        var data={};
        var el = $(this);
        data.bs_id = $(this).attr("bs_id");
        data.profit_per = $(this).val();
        if($(this).attr('name') == 'profit_per'){
            if($(this).val() == ''){
                util.mobile_alert('请输入分销毛利');
                $(this).focus();
                return;
            }
        }
        util.ajax_json('/admin/goods/manage/update/profit',function(d){
            if(d&&d.error==0){
                util.mobile_alert(d.content);
                el.removeClass('ipt-change');
                el.next().show();
                $(".change-input").attr("disabled",true);
            }
            else{
                util.mobile_alert('折后价大于售价：'+ d.content.error);
            }
        },data);
    });
    $(".goods-profit-img").click(function() {
        var _this = $(this);
        $(".goods-profit-img").show();
        _this.hide();
        $(".change-input").attr("disabled", true);
        $(".change-input").removeClass('ipt-change');
        _this.prev().attr('disabled', false);
        _this.prev().addClass('ipt-change');
        _this.prev().focus();

    });
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','seckill_goods','sub_4','抽奖',0);
</script>
