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
        </style>
    </head>
    <body style="background:none;height: 320px;">
    <div id="set-goods">
        <#if ($is_tips)
        <div class="tips">
            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" alt="">
            <span>规则说明：同一个商品若同时参加多个营销活动在商品列表中优先显示规则为 秒杀 > 定金膨胀 > 砍价 > 多人拼团 > 限时降价</span>
        </div>
        </#if>
        <div class="goods_search">
            <form <#if ($type==1) action="/admin/frame/goods/select?type=1" <#else> action="/admin/frame/goods/select?is_check_single=${isCheckSingle!}" </#if> method="post" id="form1">
                {{csrf_field()!}
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
                                <#list ($sortList as $item)
                                    <option value="${item['sort_id']!}"
                                            <#if ($request['sort_id'] == $item['sort_id'])selected="selected"</#if>>${item['sort_name']!}(${item['goods_num']!})
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <div>
                            商品品牌：
                            <select name="brand_id" id="">
                                <option value="0">请选择商品品牌</option>
                                <#list ($brand_list as $item)
                                    <option value="${item['id']!}"
                                            <#if ($request['brand_id'] == $item['id'])selected="selected"</#if>>${item['brand_name']!}
                                    </option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="goods_fr">
                        商品价格范围：<input type="text" name="min_goods_price" value="${request['min_goods_price']!}"> 元至 <input type="text" name="max_goods_price" value="${request['max_goods_price']!}">
                    </div>
                </div>
                <div style="margin-bottom: 10px;">
                    <span>商品名称：<input type="text" name="goods_name" placeholder="请输入商品名称" value="${request['goods_name']!}" style="margin-left: 4px"></span>
                    <span>商品货号：<input type="text" name="goods_sn" placeholder="请输入商品货号" value="${request['goods_sn']!} " style="margin-left: 2px"></span>
                    <#if ($integral_mall ==1)
                        <span>上下架：</span>
                        <select name="is_on_sale">
                            <option value="-1">请选择上下架</option>
                            <option value="1" <#if  ($request['is_on_sale'] == 1) selected </#if>>上架</option>
                            <option value="0" <#if  (isset($request['is_on_sale']) && $request['is_on_sale'] == 0) selected </#if>>下架</option>
                        </select>
                    <#else>
                        商品标签：
                        <select name="goods_label_id" id="">
                            <option value="0">请选择商品标签</option>
                            <#list ($goodsTagList as $item)
                                <option value="${item->id!}" <#if ($request['goods_label_id'] == $item->id) selected </#if>>${item->name!}</option>
                            </#list>
                        </select>
                    </#if>
                </div>
                <div style="margin-bottom: 10px;">
                    <#if ($integral_mall ==1)
                    商品标签：
                    <select name="goods_label_id" id="">
                        <option value="0">请选择商品标签</option>
                        <#list ($goodsTagList as $item)
                            <option value="${item->id!}" <#if ($request['goods_label_id'] == $item->id) selected </#if>>${item->name!}</option>
                        </#list>
                    </select>
                    </#if>
                    <button class="btn_search choose">筛选</button>
                    <button class="reset_search choose">重置筛选条件</button>
                </div>
                <input type="hidden" id="page" name="page"/>
                <input type="hidden" name="integral_mall" value="${integral_mall!}"/>
                <input type="hidden" name="is_tips" value="${is_tips!}"/>

            </form>
        </div>
        <div class="goods_tb">
            <table width="100%">
                <thead>
                <tr>
                    <td width="25%">商品信息</td>
                    <td width="9%">商品货号</td>
                    <td width="4%">售价</td>
                    <td width="7%">库存</td>
                    <td width="8%">平台分类</td>
                    <td width="12%">商家分类</td>
                    <td width="17%">商品标签</td>
                    <td width="9%">品牌</td>
                    <td width="9%">商品状态</td>
                </tr>
                </thead>
                <tbody>
                <#if ($data_list->total_num > 0)
                <#list ($goods_items as $item)
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
                    <td>${item->brand_name!}</td>
                    <td>${item->is_on_sale == 1 ? '上架' : "下架"!}</td>
                </tr>
                </#list>
                    </#if>
                </tbody>
            </table>
        </div>
       <#if ($data_list->total_num > 0)
        <table width="100%" border="0" class="tb_paging">
            <tr>
                <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->per_page,'currentPage'=>$data_list->current_page,'count'=>$data_list->count,'total'=>$data_list->total_num,])!}
                    <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                    <a href="#"
                       onClick="return gopage(${data_list->current_page -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                    <a href="#"
                       onClick="return gopage(${data_list->current_page + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                    <a href="#"
                       onClick="return gopage(${data_list->last_page!});">{{ trans("admin/common.page.last_page")!}</a>
                    <input id="page" name="page" type="text" value="${data_list->current_page!}"
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
    <script>
        <#if  ($isCheckSingle)
            $('#set-goods').on('click','.goods_tb tbody tr', function(){
                var flag_back = $(this).attr('data-back');
                $('.goods_tb tbody tr.goods_tr_choose').each( function () {
                    $(this).removeClass('goods_tr_choose');
                    $(this).attr('data-back','true');
                })
                if (flag_back == 'true') {
                    $(this).addClass('goods_tr_choose');
                    $(this).attr('data-back', 'false');
                }
            });
        <#else>
            $('#set-goods').on('click','.goods_tb tbody tr', function(){
                var flag_back = $(this).attr('data-back');
                if(flag_back == 'true'){
                    $(this).addClass('goods_tr_choose');
                    $(this).attr('data-back','false');
                    flag_back = 'false';
                }else if(flag_back == 'false'){
                    $(this).removeClass('goods_tr_choose');
                    $(this).attr('data-back','true');
                    flag_back = 'true';
                }
            });
        </#if>
        function gopage(page) {
            var last_page = '${data_list->last_page!}';
            if(parseInt(page)>parseInt(last_page)){
                page = last_page;
            }
            $("#page").val(page);
            $("#form1").submit();
        }
        $('.reset_search').click(function () {
            $('select[name="cat_id"]').find('[value=0]').prop('selected', 'selected');
            $('select[name="sort_id"]').find('[value=0]').prop('selected', 'selected');
            $('select[name="is_on_sale"]').find('[value="-1"]').prop('selected', 'selected');
            $('input[name="min_goods_price"], input[name="max_goods_price"], input[name="goods_name"], input[name="goods_sn"]').val('');
            $('select[name="goods_label_id"]').find('[value=0]').prop('selected', 'selected');
            $('#form1').submit();
        })
    </script>
    </body>
</html>