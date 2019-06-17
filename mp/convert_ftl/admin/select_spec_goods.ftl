<html style="height: 320px;">
<head>
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css" />
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css" />
    <link href="/css/admin/select_spec_goods.css?v=1.0.1" rel="stylesheet" type="text/css" />
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <style>
        a{
            text-decoration: none;
        }
        /*弹框样式优化*/
        .choose, .goods_fr{
            float: none;
        }
        .goods_search select{
            margin-right: 34px;
        }
        #form1 span{
            margin-right: 28px;
        }
        .reset_search.choose{
            margin-left: 10px;
        }
        #set-goods td {
            padding: 10px 8px;
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
        #set-goods{
            padding: 12px 16px;
        }
        .goods_spec{
            margin-bottom: 12px;
        }
        .goods_search{
            padding-bottom: 0px;
        }
        #set-goods td a, #set-goods td input,.goods_search input{
            border-radius: 2px;
        }
        .goods_fl div{
            float: left;
        }
        .goods_name{
            width: 160px;
            height: 35px !important;
            display: -webkit-box;
            display: -webkit-box;
            -webkit-line-clamp: 2;
           -webkit-box-orient: vertical;
            white-space: normal !important;
            word-break: break-all;
            padding-left:10px;
        }
        .goods_img img{
            width: 60px !important;
        }
        .tips{
            margin-bottom:12px;
        }
        .tips img{
            vertical-align: text-bottom;
        }
        .tips span{
            display: inline-block;
            margin-left: 5px;
            color: #888;
        }
    </style>
</head>
<body style="background:none;height: 320px;">
<div id="set-goods">
    <#if ($request['is_tips'])
    <div class="tips">
        <img src="http://${image_domain!}/image/admin/analysis_tishi.png" alt="">
        <span>规则说明：同一个商品若同时参加多个营销活动在商品列表中优先显示规则为 秒杀 > 定金膨胀 > 砍价 > 多人拼团 > 限时降价</span>
    </div>
    </#if>
    <div class="goods_search">
        <form action="/admin/public/purchase/goods/list" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="reduce_price" value="${request['reduce_price']!}"/>
            <input type="hidden" name="promotion" value="${request['promotion']!}"/>
            <input type="hidden" name="is_exc" value="${request['is_exc']!}"/>
            <input type="hidden" name="is_single" value="${request['is_single']!}"/>
            <input type="hidden" id="page" name="page" />
            <input type="hidden" name="is_spec_goods" value="${request['is_spec_goods']!}"/>
            <input type="hidden" name="is_cat" value="${request['is_cat']!}"/>
            <input type="hidden" name="is_sort" value="${request['is_sort']!}"/>
            <input type="hidden" name="is_brand" value="${request['is_brand']!}"/>
            <input type="hidden" name="brand_id" value="${request['brand_id']!}"/>
            <input type="hidden" name="is_label" value="${request['is_label']!}"/>
            <input type="hidden" name="all_id" value="${allID!}" disabled />
            <input type="hidden" name="page_all_id" value="${pageAllId!}" disabled />
            <input type="hidden" name="record_select_value" value="${request['record_select_value']!}" id="record_select_value"/>
            <input type="hidden" name="is_tips" value="${request['is_tips']!}"/>
            <input type="hidden" name="iframe_only_show" value="${request['iframe_only_show']!}"/>
            <#if ($request['is_spec_goods'] == 1)
                <input type="hidden" name="last_page" value="${data_list->lastPage()!}"/>
            <#else>
                <input type="hidden" name="last_page" value="${data_list1->last_page!}"/>
            </#if>
            <#if  (!$request['iframe_only_show'])
            <div class="goods_spec">
                <div class="goods_fl">
                    <div>
						平台分类：
                        <select name="cat_id" id="cat_id" <#if ($request['is_cat'] == 1) disabled="disabled" </#if>>
                            <option value="0">请选择平台分类</option>
                            <#list ($catList as $item)
                                <option value="${item['cat_id']!}" <#if ($request['cat_id'] == $item['cat_id']) selected </#if>>${item['cat_name']!}<#if ($request['is_spec_goods'] != 1) (${item['goods_num']!}) </#if></option>
                            </#list>
                        </select>
                    </div>
                    <div>
                        商家分类：
                        <select name="sort_id" id="sort_id" <#if ($request['is_sort'] == 1) disabled="disabled" </#if>>
                            <option value="0">请选择商家分类</option>
                            <#list ($sortList as $item)
                                <option value="${item['sort_id']!}"
                                        <#if ($request['sort_id'] == $item['sort_id'])selected="selected"</#if>>${item['sort_name']!}<#if ($request['is_spec_goods'] != 1) (${item['goods_num']!}) </#if></option>
                            </#list>
                        </select>
                    </div>
                    <div>
                        商品标签：
                        <select name="goods_label_id" id="goods_label_id" <#if ($request['is_label'] == 1) disabled="disabled" </#if>>
                            <option value="0">请选择商品标签</option>
                            <#list ($tag_list as $item)
                                <option value="${item->id!}" <#if ($request['goods_label_id'] == $item->id) selected </#if>>${item->name!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="goods_fr">
                    商品价格范围：<input type="text" name="min_goods_price" value="${request['min_goods_price']!}"/>
                    元至
                    <input type="text" name="max_goods_price" value="${request['max_goods_price']!}"/>
                </div>

            </div>
            <div class="goods_spec">
                <div class="goods_fl">
                    <div><span>商品名称：<input type="text" name="goods_name" placeholder="请输入商品名称" value="${request['goods_name']!}" style="margin-left: 4px"/></span></div>

                    <div><span>商品货号：<input type="text" name="goods_sn" placeholder="请输入商品货号" value="${request['goods_sn']!} " style="margin-left: 2px"/></span></div>
                    <div>
                        上下架：
                        <select name="is_on_sale" id="is_on_sale">
                            <option value="0">请选择上下架</option>
                            <option value="1" <#if ($request['is_on_sale'] == 1) selected </#if>>上架</option>
                            <option value="2" <#if ($request['is_on_sale'] == 2) selected </#if>>下架</option>
                        </select>
                    </div>
                    <div>
                        商品品牌：
                        <select name="brands" id="brands" >
                            <option value="0">请选择商品品牌</option>
                            <#list ($brand_list as $item)
                                <option value="${item['id']!}" <#if ($request['brands'] == $item['id']) selected </#if>>${item['brand_name']!}</option>
                            </#list>
                        </select>
                    </div>
                </div>
            </div>
            <div class="goods_spec">
                <div class="goods_fl" >
                    <div>
                        {{--商品状态：--!}
                        {{--<select name="is_on_sale" id="">--!}
                            {{--<option value="-1" selected>请选择商品状态</option>--!}
                            {{--<option value="1" <#if ($request['is_on_sale'] == 1) selected </#if>>在售</option>--!}
                            {{--<option value="0" <#if (!is_null($request['is_on_sale']) && $request['is_on_sale'] == 0) selected </#if>>下架</option>--!}
                        {{--</select>--!}
                        <button class="btn_search choose">筛选</button>
                        <button class="reset_search choose">重置筛选条件</button>
                    </div>
                </div>
            </div>
            </#if>
        </form>
    </div>
    <div class="goods_tb">
        <table width="100%">
            <thead>
            <tr>
                <#if ($is_single == 0)<td width="10%"><input type="checkbox" id="page_all_check" />全选本页</td></#if>
                    <#if ($is_single == 0) <td width="26%">商品信息</td><#else> <td width="28%">商品信息</td></#if>
                    <td width="8%">商品货号</td>
                    <td width="5%">售价</td>
                    <td width="6%">库存</td>
                    <td width="7%">平台分类</td>
                    <td width="10%">商家分类</td>
                    <#if ($is_single == 0)   <td width="12%">商品标签</td><#else>   <td width="14%">商品标签</td></#if>
                    <td width="9%">品牌</td>
                    <td width="10%">上下架</td>
            </tr>
            </thead>
            <tbody>
            <#if (is_array($data_list) && count($data_list) > 0 || is_object($data_list) && $data_list)
            <#list ($data_list as $item)
                <tr data-back='true' goods_id="${item->goods_id!}" <#if  ($request['is_spec_goods'] == 1) prd_id="${item->prd_id!}" </#if> <#if ($is_single == 1 && $request['record_select_value'] == $item->prd_id) class="goods_tr_choose" <#else> class="checked_goods" </#if>>
                    <#if ($is_single == 0)
                        <td style="border-right: none;">
                            <#if  ($request['is_spec_goods'] == 1)
                                <input type="checkbox" name="product_id[]" value="${item->prd_id!}" />
                                <#else>
                                <input type="checkbox" name="goods_id[]" value="${item->goods_id!}" />
                            </#if>
                        </td>
                    </#if>

                    <td style="border-left: none;" title="${item->goods_name_title!}">
                        <div class="goods_info clearfix">
                            <div class="goods_img"><img src="${item->goods_img!}!small" alt="" /></div>
                            <div class="info_goods">
                                <div class="goods_name" prd_num="${item->prd_num!}">
                                    {!! $item->goods_name !!}
                                </div>
                                <div class="goods_name" style="color: #999;font-size: 12px;height: 30px !important;">
                                    ${item->prd_desc!}
                                </div>
                            </div>
                        </div>
                    </td>
                    <td>${item->goods_sn!}</td>
                    <#if  ($request['is_spec_goods'] == 1)
                        <td>${item->prd_price!}</td>
                        <td>${item->prd_number!}</td>
                        <#else>
                        <td>${item->shop_price!}</td>
                        <td>${item->goods_number!}</td>
                    </#if>
                        <td>${item->cat_name!}</td>
                        <td>${item->sort_name!}</td>
                        <td>${item->tag!}</td>
                        <td>${item->brand_name!}</td>
                        <td><#if ($item->is_on_sale==1)上架<#else> 下架</#if></td>
                </tr>
            </#list>
                </#if>
            </tbody>
        </table>
    </div>
    <#if ( $request['is_spec_goods']  == 1 && count($data_list) > 0)
    <#if ($is_single==0)<div style="float: left;margin-top: 16px;margin-left: 15px"><input type="checkbox" id="all_check" />选择全部</div></#if>
    <table width="" border="0" class="tb_paging">
        <tr>
            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                <input id="page1" name="page1" type="text" value="${data_list->currentPage()!}"
                       size="5" style="height: 32px"
                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                <a href="#" style="width:46px;height: 30px;text-align: center" onClick="gopage($(this).prev().val())" >{{ trans("admin/common.page.jump_page")!}</a>
            </td>
        </tr>
    </table>
    <#elseif>( $request['is_spec_goods'] != 1 && $data_list1->total_num>0)
    <#if ($is_single==0)<div style="float: left;margin-top: 16px;margin-left: 15px"><input type="checkbox" id="all_check" />选择全部</div></#if>
    <table width="100%" border="0" class="tb_paging">
        <tr>
            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list1->per_page,'currentPage'=>$data_list1->current_page,'count'=>$data_list1->count,'total'=>$data_list1->total_num,])!}
                <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                <a href="#"
                   onClick="return gopage(${data_list1->current_page -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                <a href="#"
                   onClick="return gopage(${data_list1->current_page + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                <a href="#"
                   onClick="return gopage(${data_list1->last_page!});">{{ trans("admin/common.page.last_page")!}</a>
                <input id="page" name="page" type="text" value="${data_list1->current_page!}"
                       size="5"
                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')"  autocomplete="off">{{ trans("admin/common.page.page")!}
                <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($(this).prev().val())" >{{ trans("admin/common.page.jump_page")!}</a>
            </td>
        </tr>
    </table>
    <#else>
        <div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
        </div>
    </#if>
</div>
<script type="text/javascript" src="{{asset("js/admin/util.js")!}?v=1.02"></script>
<script>
    // 进来时填充数据
    var record_select_value = $('#record_select_value').val() == '' ? [] : $('#record_select_value').val().split(',');
    var all_ids = $('input[name="all_id"]').val().split(',') ? $('input[name="all_id"]').val().split(',') : [];
    var page_all_id = $('input[name="page_all_id"]').val().split(',') ? $('input[name="page_all_id"]').val().split(',') : [];
    var each_id = 'goods_id';
    if ('${request['is_spec_goods']!}' == 1) {
        each_id = 'product_id';
    }
    if('${is_single!}' == 0){
        $('input[name="'+each_id+'[]"]').each(function (index, item) {
            if ($(this).is(':disabled')) {
                $(item).prop('checked', false);
                $(item).parents('tr').removeClass('goods_tr_choose');
                return true;
            }
            if ($.inArray($(item).val(), record_select_value) > -1) {
                $(item).prop('checked', true);
                $(item).parents('tr').addClass('goods_tr_choose');
            } else {
                $(item).prop('checked', false);
                $(item).parents('tr').removeClass('goods_tr_choose');
            }
        });
        if (all_ids.length > 0 && util.isSubArray(all_ids,record_select_value)) {
            //触发全选
            $('#all_check').trigger('click');
        }
        if (page_all_id.length > 0 && util.isSubArray(page_all_id,record_select_value)) {
            //触发全选
            $('#page_all_check').trigger('click');
        }
    }
    function checked(_this) {
        var is_checked = $(_this).is(':checked');
        if (is_checked) {
            record_select_value.push($(_this).val());
            $(_this).parents('tr').addClass('goods_tr_choose');
        } else {
            var index = $.inArray($(_this).val(),record_select_value);
            record_select_value.splice(index, 1);
            $(_this).parents('tr').removeClass('goods_tr_choose');

        }
        $('#record_select_value').val(record_select_value.join(','));

        if (all_ids.length > 0 && util.isSubArray(all_ids,record_select_value)) {
            $('#all_check').prop('checked', true);
        } else {
            $('#all_check').prop('checked', false);
        }

        if (page_all_id.length > 0 && util.isSubArray(page_all_id,record_select_value)) {
            $('#page_all_check').prop('checked', true);
        } else {
            $('#page_all_check').prop('checked', false);
        }
    }
    <#if  (!$request['iframe_only_show'])
    //全选
    $('#all_check').click(function () {
        var is_checked = $(this).is(':checked');
        if (is_checked) {
            record_select_value = util.mergeArray(record_select_value,all_ids) ;
            $('input[name="'+each_id+'[]"]').parents('tr').addClass('goods_tr_choose');
        } else {
            for (var i = 0; i < all_ids.length; i++) {
                var index = $.inArray(all_ids[i],record_select_value);
                record_select_value.splice(index, 1);
            }
            $('input[name="'+each_id+'[]"]').parents('tr').removeClass('goods_tr_choose');
        }
        if (page_all_id.length > 0 && util.isSubArray(page_all_id,record_select_value)) {
            $('#page_all_check').prop('checked', true);
        } else {
            $('#page_all_check').prop('checked', false);
        }
        $('input[name="'+each_id+'[]"]').prop('checked', is_checked);
        $('#record_select_value').val(record_select_value.join(','));
    });
    //本页全选
    $('#page_all_check').click(function () {
        var is_checked = $(this).is(':checked');
        if (is_checked) {
            record_select_value = util.mergeArray(record_select_value,page_all_id) ;
            $('input[name="'+each_id+'[]"]').parents('tr').addClass('goods_tr_choose');
        } else {
            for (var i = 0; i < page_all_id.length; i++) {
                var index = $.inArray(page_all_id[i],record_select_value);
                record_select_value.splice(index, 1);
            }
            $('input[name="'+each_id+'[]"]').parents('tr').removeClass('goods_tr_choose');
        }
        if (all_ids.length > 0 && util.isSubArray(all_ids,record_select_value)) {
            $('#all_check').prop('checked', true);
        } else {
            $('#all_check').prop('checked', false);
        }
        $('input[name="'+each_id+'[]"]').prop('checked', is_checked);
        $('#record_select_value').val(record_select_value.join(','));
    });
    // 点击行时
    $('.checked_goods').on('click', function (e) {

        if(event.target.localName !== 'input') {
            $(this).find('input[type="checkbox"]').prop('checked', $(this).find('input[type="checkbox"]').is(':checked') ? false : true);
        }
        checked($(this).find('input[type="checkbox"]'));
    });
    $('.reset_search').click(function () {
        if('${request['is_cat']!}'==1){
            $("#cat_id").removeAttr("disabled");
        }else{
            $('select[name="cat_id"]').find('[value=0]').prop('selected', 'selected');
        }
        if('${request['is_sort']!}' == 1){
            $("#sort_id").removeAttr("disabled");
        }else{
            $('select[name="sort_id"]').find('[value=0]').prop('selected', 'selected');
        }
        if('${request['is_label']!}' == 1){
            $("#goods_label_id").removeAttr("disabled");
        }else{
            $('select[name="goods_label_id"]').find('[value=0]').prop('selected', 'selected');
        }
        $('input[name="min_goods_price"], input[name="max_goods_price"], input[name="goods_name"], input[name="goods_sn"]').val('');
        $("select[name='goods_label_id']").find('[value=0]').prop('selected','selected');
        $("select[name='is_on_sale']").find('[value=0]').prop('selected','selected');

        $('#form1').submit();
    })
    $('.btn_search').click(function () {

        if('${request['is_cat']!}'==1){
            $("#cat_id").removeAttr("disabled");
        }
        if('${request['is_sort']!}' == 1){
            $("#sort_id").removeAttr("disabled");
        }
        if('${request['is_label']!}' == 1){
            $("#goods_label_id").removeAttr("disabled");
        }
        $('#form1').submit();
    })
        <#else>
            $('#set-goods input[type="checkbox"]').attr('disabled', true);
            $('#set-goods').find('.goods_tr_choose').removeClass('goods_tr_choose');
    </#if>

    function gopage(page) {

        var last_page = $('input[name="last_page"]').val();
        if(parseInt(page)>parseInt(last_page)){
            page = last_page;
        }
        $("#page").val(page);
        if('${request['is_cat']!}'==1){
            $("#cat_id").removeAttr("disabled");
        }
        if('${request['is_sort']!}' == 1){
            $("#sort_id").removeAttr("disabled");
        }
        if('${request['is_label']!}' == 1){
            $("#goods_label_id").removeAttr("disabled");
        }
        $("#form1").submit();
    }

    <#if  ($is_single)
    $('#set-goods').on('click','.goods_tb tbody tr', function(){
        var flag_back = $(this).attr('data-back');
        $('.goods_tb tbody tr').each( function () {
            $(this).removeClass('goods_tr_choose');
            $(this).attr('data-back','true');
            $('#record_select_value').val('');
        })
        if (flag_back == 'true') {
            $(this).addClass('goods_tr_choose');
            $(this).attr('data-back', 'false');
            $('#record_select_value').val($(this).attr('prd_id'));
        }
    });
    </#if>

    function isSubArray(sub_array,array) {
        // if the other array is a false value, return
        if (!Array.isArray(sub_array) || !Array.isArray(array))
            return false;
        // compare lengths - can save a lot of time
        if (sub_array.length > array.length)
            return false;
        for (var i = 0, l = sub_array.length; i < l; i++) {
            // Check if we have nested arrays
            if ($.inArray(sub_array[i],array) === -1) {
                return false;
            }
        }
        return true;
    }
</script>
</body>
</html>