<html style="height: 320px;">
    <head>
        <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
        <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
        <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
        <style>
            a{
                text-decoration: none;
            }
            .goods_spec {
                margin-bottom: 10px;
                font-size: 14px;
                height: 30px;
            }
            .goods_fl{
                float: left;
            }
           .goods_fr input {
                height: 30px;
                width: 66px;
                text-align: center;
                border-radius: 3px;
                padding: 0px;
                margin: 0;
            }
            .reset_search {
                height: 30px;
                width: 100px;
                border: 1px solid #ccc;
                background: #f5f5f5;
                color: #666;
                margin-left: 8px;
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
                padding: 0 0 0 0;
            }
            #set-goods td a, #set-goods td input{
                border-radius: 2px;
            }
            .goods_fl div{
                float: left;
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
                font-size: 14px;
            }
            input[type="text"]{
                width:170px;
                height:30px;
                border:1px solid #bbb;
            }
            .channel_switch{
                margin: 15px 0;
            }
            .channel_search select{
                width: 140px;
                height:30px;
            }
            .channel_tb{
                margin-top: 10px;
            }
            .channel_tb table {
                border: 1px solid #eff1f5;
                border-collapse: collapse;
            }
            .channel_tb thead td {
                background: #faf9f8;
                text-align: center;
                color: #333;
            }
            .channel_tb tbody td {
                text-align: center;
                border: 1px solid #eff1f5;
                color: #666;
            }
        </style>
    </head>
    <body style="background:none;height: 320px;">
    <div id="set-goods">
        <form action="/admin/market/channel/add"  method="post" id="form1">
                {{csrf_field()!}
        <div class="channel_name">渠道页名称：<input type="text" placeholder="请输入渠道页名称" name="channel_name" style="border-radius: 3px;padding-left: 12px;" value="${request['channel_name']!}"> </div>
        <div class="channel_switch">
            渠道来源页面：<input type="radio" name="channel_switch" value="1" <#if ($request['channel_switch'] != 2) checked </#if> >自定义页面 <input type="radio" name="channel_switch" value="2" <#if ($request['channel_switch'] == 2) checked </#if> style="margin-left: 20px"> 商品详情页
        </div>
        <div class="channel_search">
            <div class="">
                页面名称：<input type="text" name="keywords1" value="${request['keywords1']!}" placeholder="请输入页面名称">
                页面分类：
                <select name="page_cat_id" id="">
                    <option value="0">请选择</option>
                    <#list ($page_cat_list as $k=>$cat)
                    <option value="${k!}" <#if ($request['page_cat_id'] == $k) selected </#if>>${cat!}</option>
                        </#list>
                </select>
                <button class="btn_search">筛选</button>
            </div>
        </div>
        <div class="channel_tb">
            <table width="100%">
                <thead>
                    <tr>
                        <th width="5%"></th>
                        <th>页面名称</th>
                        <th>创建时间</th>
                        <th>页面分类</th>
                    </tr>
                </thead>
                <tbody>
                <#list ($data1_list as $k=>$value)
                    <tr>
                        <td>
                            <input type="radio" name="page_check" page_id="${value->page_id!}" source_type="0" >
                        </td>
                        <td>${value->page_name!}</td>
                        <td>${value->create_time!}</td>
                        <td>${page_cat_list[$value->cat_id]!}</td>
                    </tr>
                    </#list>
                </tbody>
            </table>
                <#if (count($data1_list) <= 0)
                    <div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                        <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                        </div>
                        <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                    </div>
                </#if>
        </div>
        <div class="goods_search">
            <div class="goods_spec">
                <div class="goods_fl">
                    <div>
                        平台分类：
                        <select name="cat_id" id="">
                            <option value="0">请选择平台分类</option>
                            <#list ($cat_list as $item)
                                <option value="${item['cat_id']!}"
                                        <#if ($request['cat_id'] == $item['cat_id']) selected </#if>>${item['cat_name']!}(${item['goods_num']!})
                                </option>
                            </#list>
                        </select>
                    </div>
                    <div>
                        商家分类：
                        <select name="sort_id" id="">
                            <option value="0">请选择商家分类</option>
                            <#list ($sort_list as $item)
                                <option value="${item['sort_id']!}"
                                        <#if ($request['sort_id'] == $item['sort_id'])selected="selected"</#if>>${item['sort_name']!}(${item['goods_num']!})
                                </option>
                            </#list>
                        </select>
                    </div>
                    <div class="goods_fr">
                        商品价格范围：<input type="text" name="min_goods_price" value="${request['min_goods_price']!}" style="width: 80px;"> 元至
                        <input type="text" name="max_goods_price" value="${request['max_goods_price']!}" style="width: 80px;">
                    </div>
                </div>
            </div>
            <div class="goods_fl">
                <div>商品名称：<input type="text" name="goods_name" placeholder="请输入商品名称" value="${request['goods_name']!}" style="margin-left: 4px;width: 140px;"></div>
                <div>商品货号：<input type="text" name="goods_sn" placeholder="请输入商品货号" value="${request['goods_sn']!} " style="margin-left: 2px;width: 140px;"></div>
                <div>

                    商品标签：
                    <select name="goods_label_id" id="">
                        <option value="0">请选择商品标签</option>
                        <#list ($goodsTagList as $item)
                            <option value="${item->id!}" <#if ($request['goods_label_id'] == $item->id) selected </#if>>${item->name!}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div style="margin-bottom: 10px;">
                <button class="btn_search choose">筛选</button>
            </div>
        </div>
        <div class="goods_tb">
            <table width="100%">
                <thead>
                <tr>
                    <td width="28%">商品信息</td>
                    <td width="10%">商品货号</td>
                    <td width="5%">售价</td>
                    <td width="7%">库存</td>
                    <td width="9%">平台分类</td>
                    <td width="13%">商家分类</td>
                    <td width="18%">商品标签</td>
                    <td width="9%">商品状态</td>
                </tr>
                </thead>
                <tbody>
                    <#list ($data_list as $item)
                        <tr data-back="true" goods_id="${item->goods_id!}">
                            <td title="${item->goods_name_title!}">
                                <div class="goods_info clearfix">
                                    <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                    <div class="goods_name">
                                        {!! $item->goods_name !!}
                                    </div>
                                </div>
                            </td>
                            <td>${item->goods_sn!}</td>
                            <td>${item->shop_price!}</td>
                            <td>${item->goods_number!}</td>
                            <td>${item->cat_name!}</td>
                            <td>${item->sort_name!}</td>
                            <td>${item->tag!}</td>
                            <td>${item->is_on_sale == 1 ? '上架' : "下架"!}</td>
                        </tr>
                    </#list>
                </tbody>
            </table>
            <div class="clearfix jump_page">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>

    </form>
    <script>

        $('#set-goods').on('click','.goods_tb tbody tr', function(){
            var _this = $(this);
            var flag_back = $(this).attr('data-back');
            $('#set-goods').find(".goods_tb").find("tbody").find('tr').each(function (i,v) {
                $(v).removeClass("goods_tr_choose");
                $(v).attr("data-back",'true');
            })
            if(flag_back == 'true'){
                _this.addClass('goods_tr_choose');
                _this.attr('data-back','false');
                flag_back = 'false';
            }else if(flag_back == 'false'){
                _this.removeClass('goods_tr_choose');
                _this.attr('data-back','true');
                flag_back = 'true';
            }
        });
        function gopage(page) {
            var last_page = '${data_list->last_page!}';
            if(parseInt(page)>parseInt(last_page)){
                page = last_page;
            }
            $("#page").val(page);
            $("#form1").submit();
        }
        // $('.reset_search').click(function () {
        //     $('select[name="cat_id"]').find('[value=0]').prop('selected', 'selected');
        //     $('select[name="sort_id"]').find('[value=0]').prop('selected', 'selected');
        //     $('select[name="is_on_sale"]').find('[value="-1"]').prop('selected', 'selected');
        //     $('input[name="min_goods_price"], input[name="max_goods_price"], input[name="goods_name"], input[name="goods_sn"]').val('');
        //     $('select[name="goods_label_id"]').find('[value=0]').prop('selected', 'selected');
        //     $('#form1').submit();
        // })
        if($('input[name="channel_switch"]:checked').val() == 1){
            $('.channel_search').show()
            $('.channel_tb').show()
            $('.goods_search').hide()
            $('.goods_tb').hide()
        } else {
            $('.goods_search').show()
            $('.goods_tb').show()
            $('.channel_search').hide()
            $('.channel_tb').hide()
        }
        $('input[name="channel_switch"]').change(function(){
            if($(this).val() == 1){
                $('.channel_search').show()
                $('.channel_tb').show()
                $('.goods_search').hide()
                $('.goods_tb').hide()
            } else {
                $('.goods_search').show()
                $('.goods_tb').show()
                $('.channel_search').hide()
                $('.channel_tb').hide()
            }
        })
    </script>
    </div>
    </body>
</html>