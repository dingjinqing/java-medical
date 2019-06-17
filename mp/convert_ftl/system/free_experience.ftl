<#include ("system.header")
<link href="/css/admin/goods_list.css?v=1.0.7" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<link rel="stylesheet" href="/css/admin/goods_edit.css?v=1.0.9" type="text/css" />
<link rel="stylesheet" href="/css/admin/question_feedback.css?v=1.0.6">
<link rel="stylesheet" href="/css/system/shop_pv.css" type="text/css" />
<style type="text/css">

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
    .order-info-li .fl2{
        width:90px;
        float: left;
        margin-top:5px;
    }
    .order-info-li .fl2 input{
        width:16px;
        float: left;
        margin-top:-5px;
        margin-left: 20px;
    }
    .member_list_main tbody td{
        height: 83px;
        padding:15px 0;
    }
    .member_list_main thead td{
        padding:15px 0;
    }
    .list-center-sel{
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        margin-top: 0px;
        color: #333;
        font-size: 14px;
        width: 150px;
        line-height: 50px;
        float: right;
        margin-right: 19px;
        margin-left: 0px;
    }
    td>a{
        background: #fff !important;
        color: #5a8bff;
        text-decoration: none;
    }
    td>a:hover{
        background: #fff !important;
        color: #5a8bff;
        text-decoration: underline;
    }
    td>a:active{
        background: #fff !important;
        color: #5a8bff;
        text-decoration: none;
    }

    .look_message{
        display: block;
        text-align: right;
    }
    .desc_input{
        width: 150px;
        line-height: 30px;
    }

    .exchange-num{
        padding: 10px 20px;
        border-bottom: 1px solid #eee;
        display: none;
    }
    .exchange-num div{
        line-height: 30px;
    }
    .exchange-num input{
        width: 100px;
        height: 30px;
        padding-left: 12px;
        margin: 0 10px;
        background-color: #fff;
        border: 1px solid #ccc;
    }

    .province_id{
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        margin-top: 0px;
        color: #333;
        font-size: 14px;
        width: 150px;
        line-height: 50px;
    }

</style>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                <input type="hidden" id="page" name="page" value="">
                <input type="hidden" name="shop_id" value="">
                <input type="hidden" name="fe_id" value="">
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>公司名称</span>
                            <input type="text" name="company" value="${data->company!}" placeholder='请输入公司名称' />
                        </div>
                        <div class="fl">
                            <span>联系人</span>
                            <input type="text" name="contact" value="${data->contact!}" placeholder='请输入联系人' />
                        </div>

                        <div class="fl" style="width: auto;">
                            <span>申请时间</span>
                            <input type="text" name="start_time" value="${data->start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>&nbsp;&nbsp;至&nbsp;
                            <input type="text" name="end_time" value="${data->end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                    </li>

                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>所在省份</span>
                            <select class="list-center-sel" name="province_id">
                                <option value="" >请选择省份</option>
                                <#list ($provinces as $pitem)
                                    <option value="${pitem->province_id!}"  <#if ($data->province_id == $pitem->province_id) selected="selected"</#if>>${pitem->name!}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="fl">
                            <span>店铺ID</span>
                            <input type="text" name="search_shop_id" value="${data->search_shop_id!}" placeholder='请输入店铺ID' />
                        </div>
                        <div class="fl">
                            <span>处理状态</span>
                            <select class="list-center-sel" name="is_deal">
                                <option value="" >请选择状态</option>
                                <option value="0" <#if ($data->is_deal === '0') selected="selected"</#if>>未处理</option>
                                <option value="1" <#if ($data->is_deal == 1) selected="selected"</#if>>已处理</option>
                            </select>
                        </div>
                        <button class="btn-choose">筛选</button>
                    </li>
                </ul>
            </form>
        </div>
        <div>
            <form action="" id="form2" method="">
                {{ csrf_field()!}

                <div class="member_list_main">
                    <table width="100%">
                        <thead>
                        <tr>
                            <td width="10%">联系人</td>
                            <td width="10%">电话</td>
                            <td width="10%">公司</td>
                            <td width="10%">来源</td>
                            <td width="10%">所在省份</td>
                            <td width="10%">留言</td>
                            <td width="8%">申请时间</td>
                            <td width="7%">开通店铺ID</td>
                            <td width="5%">处理状态</td>
                            <td width="15%">备注</td>
                            <td width="5%">操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list ($data_list as $item)
                            <tr class="member_tr" free_id="${item->fe_id!}">
                                <td>${item->contact!}</td>
                                <td>${item->mobile!}</td>
                                <td>${item->company!}</td>
                                <td>${item->source!}
                                    <#if ($item->user_id)<br/>
                                    <a href="/system/user/center?shop_id=${item->source!}&user_id=${item->user_id!}" target="_blank">user_id:${item->user_id!}</a>
                                    </#if>
                                </td>
                                <td province_id="${item->province_id!}" class="free_province">
                                    <span>${item->name!}</span> 
                                    <img src="http://${image_domain!}/image/admin/good_edit.png" grade="${item->grade!}" alt="" class="btn_province" />
                                </td>
                                <td>${item->content!}</td>
                                <td>${item->ask_time!}</td>
                                <td><#if  ($item->shop_id) ${item->shop_id!} <#else> 未开通 </#if></td>
                                <td><#if  ($item->is_deal)已处理 <#else> 未处理 </#if></td>
                                <td class="free_desc" fe_id="${item->fe_id!}">
                                    <span>${item->desc!}</span>
                                    <img src="http://${image_domain!}/image/admin/good_edit.png" grade="${item->grade!}" alt="" class="btn_desc" />
                                </td>
                                <td width="12%" class="${item->fe_id!}" id="${item->shop_id!}"><#if  ($item->is_deal)<a href="##" class="deal_button">编辑店铺</a><#else>  <a href="##" class="deal_button">分配店铺</a></#if> <br><a href="/system/free/message?fe_id=${item->fe_id!}" class="">查看详情</a></td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <div class="member_list_footer">
                    <table width="" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage() - 1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                <input id="page1" name="page1" type="text" value="${data_list->currentPage()!}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>

<input type="hidden" id="free_id" value="0" />
<div id="set-province" class="exchange-num">
    <div>
        <span>
            <select class="province_id">
                <option value="" >请选择省份</option>
                <#list ($provinces as $pitem)
                    <option value="${pitem->province_id!}"  <#if ($data->province_id == $pitem->province_id) selected="selected"</#if>>${pitem->name!}</option>
                </#list>
            </select>
        </span>
    </div>
</div>
<div id="set-desc" class="exchange-num">
    <div>
        <textarea name="desc" class="desc" placeholder="请输入备注" style="width: 300px;height: 100px;"></textarea>
    </div>
</div>
<#include ("system.footer")
<script type="text/javascript" src="/js/system/free_experience.js?v=1.0.0"></script>
<script>
    $.trim($(".wrapper").val());
    var wrapper_text_arr=[];
    $(".wrapper").each(function () {
        $.trim($(this).val());
        var tt=$(this).text();
        var tl=tt.length;
        var s=tt.toString();
        if(tl>'48'){
            var ss=s.substring(0,48)+"...";
        }
        $(this).text(ss);
        wrapper_text_arr.push(tl);
    })

    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    $(".deal_button").click(function () {
        var fe_id = $(this).parent().attr('class');
        var shop_id = $(this).parent().attr('id');

        // var layer_html = $("#layer_html").html();
        console.log(fe_id);
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({title: ['输入店铺ID，并确认','text-align:center;padding: 0px;'],
                type: 1,
                offset: 'auto',
                area: '300px',
                btn: ['确定'],
                btnAlign: 'r',
                content: '<div style="text-align: center;" >' +
                '<input id="shop"  value="'+shop_id+'" name="shop"  style="height: 30px; text-align:center;" placeholder="请输入店铺ID" />' +
                '</div>' ,
                success: function (layero, index) {

                }
                , yes:  function (index, layero) {
                    layer.close(index);
                    $("input[name='fe_id']").val(fe_id);
                    shop_id = $("#shop").val();
                    $("input[name='shop_id']").val(shop_id);
                    $("#form1").submit();
                    layer.msg('分配完毕！');
                }

            });
        });
    })


    var _val;
    $(".goods-number-img").click(function() {
        var _this = $(this);

        $(".goods-number-img").show();
        _this.hide();
        $(".change-input").attr("disabled", true);
        $(".change-input").removeClass('ipt-change');
        _this.prev().attr('disabled', false);
        _this.prev().addClass('ipt-change');
        _this.prev().focus();
    });

    $(".change-input").focus(function () {
        _val = $(this).val();
    });
    $(".change-input").blur(function (e) {
        var b_val = $(this).val();
        if(b_val == _val && b_val != ''){
            $('.ipt-change').next().show();
            $('.change-input').removeClass('ipt-change');
            $(".change-input").attr("disabled",true);
        }
    });
    $(".change-input").change(function(e){
        var data={};
        var el = $(this);
        data.fe_id = $(this).attr("fe_id");
        data.act =  $(this).attr("act");
        data.desc = $(this).val();
        util.ajax_json('/system/free/experience',function(d){
            if(d&&d.error==0){
                util.mobile_alert(d.content);
                el.removeClass('ipt-change');
                el.next().show();
                $(".change-input").attr("disabled",true);
            }
            else{
                util.mobile_alert(d.message);
            }
        },data);
    });


</script>