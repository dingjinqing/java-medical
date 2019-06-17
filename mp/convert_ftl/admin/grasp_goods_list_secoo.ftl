<#include "/admin/header.ftl">
<link href="/css/admin/goods_list.css?v=1.2.2" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/sec_kill.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .share_td .share_span{
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 22px;
        left:-175px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }
    .share_td .share_span .share_sj{
        position: absolute;
        right: 90px;
        top: -7px;
    }
    .share_td.share_span span{
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }
    .share_td .share_span .code_imgs{
        display: block;
        margin:0 auto;
    }
    .share_td .share_span a{
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
        width: 100%;
        text-align: center;
        margin-left: 0;
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
    input[name='page']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging tr td,.tb_paging tr td a{
        color: #333;
        font-size: 14px;
    }
    .tb_paging{
        border: 0 !important;
    }
    .hover_share{
        display: inline-block;
        width: 29px;
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
    .create-good{
        border:1px solid #5a8bff;
    }
    .goods_fr input {
        height: 30px;
        width: 66px;
        text-align: center;
        border-radius: 3px;
        padding: 0px;
        margin: 0;
    }
    .create-good:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    .create-good:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #ffffff;
    }
    #search {
        margin-left: 5px;
        margin-bottom: 4px;
    }
    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
    }
    .goods_new{
        padding-left: 12px;
    }
    .goods_ck{
        margin-left: -14px;
    }
    .bottom-table{
        border-left: none;
    }
    .goods_fr{
        margin-left:10px;
    }
    .search-bl{
        width: 165px;
    }
    .primary {
    width: 135px;
    }
    .goods_fr input{
        border-radius: 3px;
        border: 1px solid #ccc;
        height:30px;
    }
    .create-good,.system_info{
        float:left;
        margin-right: 10px;
        margin-left:18px;
    }
    .create-good,.btn_searchinfo{
        border-radius:3px;
    }
    .system_info{
        margin:10px 0 0 0;
    }
    .list-center-sele {
        width: 150px;

        line-height: 50px;
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        margin-top: 10px;
        color: #333;
        font-size: 14px;
    }
    .sel_time{
        margin-left: 13px;
    }
   .nav-child-tabs{
       line-height:30px;
   }
   .list-bottom{
       padding:10px;
   }
</style>

    <div class="goods-container">
        <div class="title">
            <span>
                {{ trans("admin/shop.list-top.li_top_name")!}
            </span>/
            <span>
                第三方商品
            </span>
        </div>
        <div class="main-container">
            <form name="form" action="/admin/goods/manage/grasp/list?nav=${nav!}&top_index=2"  id="form1" method="post">
                <input type="hidden" name="del">
                <div class="list-center">
                        {{ csrf_field()!}
                        <input name="goods_id" type="hidden">
                        <input name="all" type="hidden">
                        <input name="nav" type="hidden" value="${nav!}">
                        <input type="hidden" id="check_choice" name="check_choice" value="${check_choice!}"/>
                        <input type="hidden" id="all_flag" name="all_flag" value="${all_flag!}"/>
                    <div>
                        {{--<select name="cat_id" class="list-center-sel">--!}
                            {{--<option value="0">请选择平台分类</option>--!}
                            {{--<#list ($cat_list as $item)--!}
                                {{--<option value="${item['cat_id']!}"--!}
                                        {{--<#if ($cat_id == $item['cat_id'])selected="selected"</#if>>${item['cat_name']!}</option>--!}
                            {{--</#list>--!}
                        {{--</select>--!}
                        <select name="sort_id" class="list-center-sel">
                            <option value="0">请选择商家分类</option>
                            <#list ($sort_list as $item)
                                <option value="${item['sort_id']!}"
                                        <#if ($sort_id == $item['sort_id'])selected="selected"</#if>>${item['sort_name']!}</option>
                            </#list>
                        </select>
                        <select name="brand_id" class="list-center-sel">
                            <option value="0">请选择品牌分类</option>
                            <#list ($brand_list as $item)
                                <option value="${item['id']!}"
                                        <#if ($brand_id == $item['id'])selected="selected"</#if>>${item['brand_name']!}</option>
                            </#list>
                        </select>
                        <select name="is_on_sale" class="list-center-sel">
                           <option value="-1"  >请选择销售状态</option>
                           <option value="1" <#if  ($is_on_sale ==1) selected </#if>>出售中</option>
                           <option value="0" <#if  ($is_on_sale ==0) selected </#if>>下架</option>
                        </select>
                       <span class="sel_time">更新时间
                        <input class="list-center-sele"  type="text" name="startDate" placeholder="请选择时间" id="startDate" value="${startDate!}"
                               onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>--
                        <input class="list-center-sele" type="text" name="endDate" placeholder="请选择时间" value="${endDate!}" id="endDate"
                               onclick="picker();"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/></span>
                       
                        {{--<span class="goods_fr">--!}
                            {{--价格：<input type="text" name="min_goods_price" value="${post['min_goods_price']!}"> 至 <input type="text" name="max_goods_price" value="${post['max_goods_price']!}">--!}
                        {{--</span> --!}
                        
                        <span class="search-bl">
                            <input type="text" name='keywords' value="${keywords!}" placeholder="{{ trans("admin/shop.list-center.search")!}"
                                   class="primary" >
                            <img src="http://${image_domain!}/image/admin/search.png" alt="" id="search">
                        </span> 

                        <button class="btn_searchinfo">查询</button>
                    </div>
                </div>

                <input type="hidden" name="sort_field" id="sort_field" value="${sort_field!}">
                <input type="hidden" name="sort_way" id="sort_way" value="${sort_way!}">
                <div class="list-bottom" style="padding-top:40px">
                <table align="center" class="list-bottom-show">
                    <thead style="border: 1px solid #eee;">
                        <tr id="list-bottom-top">
                            <th></th>
                            <th>商品图片</th>
                            <th>商品名称</th>
                            <th>品牌</th>
                            <th>分类</th>
                            <th>商家货号</th>
                            <th>
                                <a href="javascript:void(0);" onClick="return sort_f(2);">库存</a>
                                <span id="sort_symbo">${sort_symbo[2]!}</span>
                            </th>
                            <th>平台供货价</th>
                            <th>市场零售价</th>
                            <th>预计利润</th>
                            <th>实际分销价</th>
                            <th>实际利润</th>
                            {{--<th>--!}
                                {{--<a href="javascript:void(0);" onClick="return sort_f(1);">价格</a>--!}
                                {{--<span id="sort_symbo">${sort_symbo[1]!}</span>--!}
                            {{--</th>--!}
                            {{--<th>{{trans("admin/shop.list-bottom.price")!}</th>--!}

                            {{--<th>平台分类</th>--!}


                            {{--<th>{{trans("admin/shop.list-bottom.good_count")!}</th>--!}
                            {{--<th>{{trans("admin/shop.list-bottom.sale_sum")!}</th>--!}
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody id="" class="goods_list">
                    <#list ($data_list as $item)
                        <tr>
                            <td align="center" width="4%" style="border-right: none;position: relative;">
                                <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" />
                                <input type="checkbox" name="checkbox" goods_id="${item->goods_id!}" value="${item->goods_id!}" >
                            </td>
                            {{--<td align="left" class="goods-name" width="19%" style="border-left: none; position: relative;" title="${item->goods_name_title!}">--!}
                            <td align="left" class="goods-img" width="7%" style="border-left: none; position: relative;" title="商品图片">
                                <span>
                                    <img src="${item->goods_img!}!small" alt="" style="width:80px;height:80px">
                                </span>
                            </td>
                            <td align="left" class="goods-name" width="10%" style="border-left: none; position: relative;" title="${item->goods_name_title!}">
                                    <span class="list-name">
                                        <span style="margin-left: 5px;">
                                            <#if  ($item->is_on_sale == 0)
                                                <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">下架</span>
                                            </#if>
                                            {!! $item->goods_name !!}
                                        </span>
                                    </span>
                            </td>
                            <td align="center" width="8%">${item->brand_name!}</td>
                            <td align="center" width="7%">${item->sort_name!}</td>
                            <td align="center" width="9%">${item->goods_sn!}</td>
                            <td align="center" width="7%">
                                <span>
                                        ${item->goods_number!}
                                </span>
                            </td>
                            <td align="center" width="8%">${item->cost_price!}</td>
                            <td align="center" width="8%">${item->market_price!}</td>
                            <td align="center" width="8%">${item->expect_profit!}</td>
                            <td align="center" width="8%">${item->shop_price!}</td>

                            <td align="center" width="6%">${item->actual_profit!}</td>

                            {{--<td align="center" width="8%">${item->cat_name!}</td>--!}


                            <td align="center" width="53%" class="share_td">
                                <div>
                                    <span class="goods_new">
                                        <#if (!in_array($item->goods_id,$onsale_goods_ids))
                                        <a href="/admin/goods/manage/grasp/list?act=on_sale&goods_id=${item->goods_id!}&nav=${nav!}">发布</a>
                                        <#else>
                                            已发布
                                        </#if>
                                    </span>
                                    <span class="goods_new">
                                        <a href="/admin/goods/manage/grasp/info?goods_id=${item->goods_id!}&nav=${nav!}">查看</a>
                                    </span>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                    <#if (!$data_list->count)
                        <table style="width: 100%;border: 1px solid #eee;position: relative;margin-top: 16px;">
                            <tr>
                                <td style="width: 30px;height: 33px; margin: 18px auto auto auto;display: flex;justify-content: center;align-items: center;" >
                                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</td>
                            </tr>
                        </table>
                    <#else>
                    <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table" border="0">
                        <tr >
                            <td style="padding-left: 15px;">
                                <span style="position:relative;">&nbsp;&nbsp;
                                    <img src="http://${image_domain!}/image/admin/square_no.png" alt="" class="checkbox_prev" style="top: -1px;" />
                                    <input type="checkbox" id="select_all" />&nbsp;&nbsp;
                                    <label for="select_all">全选</label>
                                </span>
                                    <input name="on_sale" type="button" id="icon_ups" value="发布" class="down tb-btm-left">
                            </td>
                            <td style="text-align:right;">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="#" style="background: rgb(250, 250, 250);" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="#" style="background: rgb(250, 250, 250);"
                                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="#" style="background: rgb(250, 250, 250);"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="#" style="background: rgb(250, 250, 250);"
                                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')" autocomplete="off"
                                >{{ trans("admin/common.page.page")!}
                                <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                            </td>
                        </tr>
                    </table>
                    </#if>
                </table>
            </div>
            </form>
        </div>
    </div>
<script>
   
    var all_flag_arr = '${all_flag!}'.split(',');
    var check_choice = '${check_choice!}' == '' ? [] : '${check_choice!}'.split(',');
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';          //总页码数
    var goods_all = '${goods_all!}'.split(',');
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        $("#all_flag").val(all_flag_arr.join(','));
        $("#check_choice").val(check_choice.join(','));
        // var href = ;
        $("#page").val(page);
        // $('#form1').attr('action', href);
        $("#form1").submit();
    }
   if($.inArray('0', all_flag_arr) == -1){
        $("#select_all").prop('checked',true);
        $("#select_all").prev().attr('src','/image/admin/square_yes.png');
        check_choice = '${goods_all!}'.split(',');
        $("#check_choice").val(check_choice.join(','));
    }else{
        $("#select_all").prop('checked',false);
        $("#select_all").prev().attr('src','/image/admin/square_no.png');
    }
   
    if(all_flag_arr['${data_list->currentPage()!}'-1] == '1'){
        $(".goods_list input[name='checkbox']").prop('checked',true);
        $('.goods_list input[name="checkbox"]').each(function (index, v) {
             $(this).prev().attr('src','/image/admin/square_yes.png');
            if($.inArray($(v).val(), check_choice) == -1){
                check_choice.push($(v).val());
            }
        })
    }else{
        $('.goods_list input[name="checkbox"]').each(function (index, v) {
            if ($.inArray($(v).val(), check_choice) > -1) {
                $(v).prop('checked', true);
                 $(this).prev().attr('src','/image/admin/square_yes.png');
            } else {
                $(v).prop('checked', false);
            }
        });

    }

    $("#select_all").click(function () {
        var isChecked = $("#select_all").prop("checked");
        if($(this).is(':checked')) {
            for (var i = 0;i<page_all;i++){
                all_flag_arr[i] = '1';
            }
            $(this).prev().attr('src','/image/admin/square_yes.png');
            $(this).parents('.list-bottom').find('.goods_list').find("input[name='checkbox']").each(function(){
            if(!$(this).is(':checked'))
                $(this).click();   
            })
            check_choice =goods_all;
            $("#check_choice").val(check_choice.join(','));
        }else{
            for (var i = 0;i<page_all;i++){
                all_flag_arr[i] = '0';
            }
            $(this).prev().attr('src','/image/admin/square_no.png');
            $(this).parents('.list-bottom').find('.goods_list').find("input[name='checkbox']").each(function(){
             if($(this).is(':checked'))
                 $(this).click();
            })
            check_choice = [];
            $("#check_choice").val('');
        }
    });
    $(".goods_list input[name='checkbox']").click(function (e) {
        e.stopPropagation();
        // $(this).attr("checked", "checked");
        if($(this).is(':checked')){
            check_choice.push($(this).val());
            $("#check_choice").val(check_choice.join(','));
            console.log($("#check_choice").val());
            $(this).prev().attr('src','/image/admin/square_yes.png');

        }else{
            // check_choice.splice($(this).index, 1);
            // console.log(check_choice);
            for(var j=0;j<check_choice.length;j++){
                if(check_choice[j] == $(this).val()){
                    check_choice.splice(j, 1);
                    $("#check_choice").val(check_choice.join(','));
                    break;
                }
            }
            $(this).prev().attr('src','/image/admin/square_no.png');
            $("#select_all").prop('checked',false);
            $("#select_all").prev().attr('src','/image/admin/square_no.png');
        }
        var curLength = $(".goods_list input[name='checkbox']").length;
        var checkedLength = $(".goods_list input[name='checkbox']:checked").length;
        if(curLength == checkedLength){
            all_flag_arr['${data_list->currentPage()!}'-1] = '1';
        }else{
            all_flag_arr['${data_list->currentPage()!}'-1] = '0';
        }
        console.log(all_flag_arr);
        if($.inArray('0', all_flag_arr) == -1){
            $("#select_all").prop("checked",true);
            $("#select_all").prev().attr('src','/image/admin/square_yes.png');
        }else{
            $("#select_all").prop().attr('checked',false);
            $("#select_all").prev().attr('src','/image/admin/square_no.png');

        }
    });

    window.sort_f = function (field) {
        if ($("#sort_field").val() == field) {
            if ($("#sort_way").val() == 'desc') {
                $("#sort_way").val('asc');
            } else {
                $("#sort_way").val('desc');
            }
        } else {
            $("#sort_field").val(field);
            $("#sort_way").val('desc');
        }
        $("#form1").submit();
    };


    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    $("#search").click(function () {
       $("#form1").submit();
    });
    $("#icon_ups").click(function () {
        var goods_id = [];
        var isChecked = $("#select_all").prop("checked");
        if(isChecked){
          goods_id = goods_all;  
        }else{
           goods_id = check_choice;
        }
       
        console.log(goods_id);
        if (goods_id.length == 0) {
            //util.alert('未选择任何商品');
            util.mobile_alert('未选择任何商品');
            return false;
        } else {
            var _this = $(this);
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert('<div style="text-align: center;">' + '确认要发布吗？' + '</div>', {
                    title: ['提醒', 'text-align:center;padding: 0px;']
                    , area: '260px'
                    , closeBtn: 0
                    , btn: ['确定', '取消']
                },function(index){
                    $('input[name="all"]').val(_this.attr('name'));
                    $('input[name="goods_id"]').val(goods_id);
                    $("#form1").submit();
                    layer.close(index);
                });
            });

        }
    });

    $('[name="brand_id"]').change(function(){
        $("#form1").submit();
    });
    $('[name="is_on_sale"]').change(function(){
        $("#form1").submit();
    });
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/goods_list.js?v=1.1.3" type="text/javascript"></script>
<#include "/admin/footer.ftl">
