<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/reduce_price.css?v=1.1.8" type="text/css" />
<link rel="stylesheet" href="/css/admin/common_share.css?v=1.2.1" type="text/css" />
<style type="text/css">
    .goods-btn-modify{
        display: inline !important;
    }
    body{
        padding-bottom: 40px;
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
        margin-top: 10px;
        margin-bottom: 5px;
        float: none;
    }
    .goods-namess {
        padding: 8px 2px;
        width: auto;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        background: #f4f4f4;
    }
    .goods-btn-modify {
        padding: 8px 2px;
        display: inline-block;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        color: #5A8BFF;
        cursor: pointer;
    }
    #product-info table tr td{
        border: 1px solid #eee;
    }
    .prompt {
        color: red;
        margin-left: 20px;
    }
    input[readonly] {
        background: #EBEBE4;
    }
    .reduce_price_footer button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .reduce_price button:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .reduce_price_footer button:active, .reduce_price_footer button:visited{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    .reduce_content input[type="text"]:focus{
       border:1px solid #447af9;
    }
    .prompt{
        color: #999;
        margin: 8px 0 -2px;
    }
    tr td{
        padding: 8px 0px;
    }
    .goods_area table tbody td{
       font-size: 16px;
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
    #batch_price,#batch_profit{
        display:block;
        width: 120px;
        height: 30px;
        background-color: #fff;
        opacity: 1;
        color: #5a8bff;
        border: 1px solid #ddd;
        cursor: pointer;
        text-align:center;
        line-height:30px;
        margin-top:10px;
    }
    .goods_price_popup,.goods_profit_popup{
        padding:20px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">限时降价</span>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/reduce/list?nav=0&top_index=4">全部限时降价活动</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=1&top_index=4">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=2&top_index=4">未开始</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=3&top_index=4">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/list?nav=4&top_index=4">已停用</a>
                </li>
                <#if ($shop_flag == 2)
                <li>
                    <a href="/admin/market/reduce/price/list">批量降价</a>
                </li>
                <li>
                    <a href="/admin/market/reduce/profit/list">批量加价率</a>
                </li>
                </#if>
                <li class="active">
                    <#if ($id)
                        <a href="/admin/market/reduce/list?top_index=4&id=${id!}">编辑限时降价活动</a>
                    <#else>
                        <a href="/admin/market/reduce/list?top_index=4">添加限时降价活动</a>
                    </#if>
                </li>
            </ul>
        </div>
        <div class="return-reduce-price-box" style="padding-top: 20px;">
            <form name="formData" id="form1" method="post" action="/admin/market/reduce/add?top_index=4">
                {{ csrf_field()!}
                <input type="hidden" name="id" value="${_GET['id']!}"/>
                <input type="hidden" name="goods_id" value="${basicData->goods_id!}"/>
                <input type="hidden" name="goods" value="${basicData->goods!}"/>
                <input type="hidden" name="period_action" value="${basicData->period_action!}"/>
                <input type="hidden" name="point_time" value="${basicData->point_time!}"/>
                <input type="hidden" name="extend_time" value="${basicData->extend_time!}"/>
                <input type="hidden" name="batch_discount" value="${basicData->batch_discount!}"/>
                <input type="hidden" name="batch_reduce" value="${basicData->batch_reduce!}"/>
                <input type="hidden" name="batch_final_price" value="${basicData->batch_final_price!}"/>
                <input type="hidden" name="is_batch_integer" value="${basicData->is_batch_integer!}"/>
                <input type="hidden" name="add_type" value="${basicData->add_type!}"/>
                <input type="hidden" name="prd_price" value=""/>
                <input type="hidden" name="bs_profit" value=""/>
                <input type="hidden" name="shop_flag" value="${shop_flag!}"/>
                <table class="tb-reduce-price">
                    <tbody>
                    <tr style="height: 50px;">
                        <td style="width: 100px ;">
                            <span class="tb-full-left" style="margin-top: -24px"><strong>*</strong>活动名称：</span>
                        </td>
                        <td>
                            <input type="text" class="name" placeholder="请填写活动名称" name="name" value="${basicData->name!}" maxlength="20">
                            <p class="prompt" style="margin-top: 8px">只作为商家记录使用，用户不会看到这个名称</p>
                        </td>

                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>有效期：</span>
                        </td>
                        <td>
                                    <span>
                                        <#if (!empty($basicData->start_time) && $edit==0)
                                            <input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time"
                                                   id="startDate" readonly> 至
                                            <input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time"
                                                   id="endDate" readonly>
                                        <#else>
                                            <input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time"
                                                   id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
                                            <input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time"
                                                   id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
                                        </#if>
                                    </span>
                            {{--<#if ($basicData) class="loop" </#if>--!}
                            <input type="checkbox" class="loop" id="zhouqi"  <#if ($basicData->period_action) checked </#if> name="zhouqi" <#if ($edit==0 && $basicData->period_action) disabled </#if>>
                            <input type="checkbox" class="loop_bak" id="zhouqi"  <#if ($basicData->period_action) checked </#if> name="zhouqi" <#if ($edit==0 && $basicData->period_action) disabled </#if>><span>按周期重复</span>
                            <span class="loop_text" hidden><span>${span!}</span><a class="xg">修改</a></span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left" style="margin-top: -27px"><strong>*</strong>限购数量：</span>
                        </td>
                        <td>
                            <input type="number" class="limit_amount" placeholder="" name="limit_amount" value="${basicData->limit_amount or 1!}"
                                   <#if (!empty($basicData->limit_amount)  && $edit == 0) readonly </#if>; min="0"
                            />
                            <p class="prompt">限制单个用户在单次活动中购买单个降价商品的数量,请填写阿拉伯数字,填写0表示不限制</p>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px;vertical-align: top !important;" >
                            <span class="tb-full-left" style="margin-top: 5px;"><strong>*</strong>活动商品：</span>
                        </td>
                        <td>
                            <#if (empty($basicData->goods_id))
                                <input type="button" value="+ 选择商品" id="sel-goods-btn" class="choose_goods" name="choose_goods">
                            </#if>
                            <input type="hidden" name="goods_id" value="${basicData->goods_id!}">
                            {{--<span class="goods-namess" <#if  (empty($goodsInfo->goods_name)) style="display: none;" </#if>>${goodsInfo->goods_name!}</span>--!}
                            <#if (!empty($basicData->goods_id)  || $edit == 1)
                                <#if (!$edit == 0 )
                                    <span class="goods-btn-modify" >修改</span>
                                </#if>
                            </#if>
                            <span class="prompt">最多选择100个商品</span>
                                <#if ($shop_flag == 2 && !($request['id'] > 0))
                            <div><a  id="batch_price" target="_blank">批量降价</a></div>
                            <div><a  id="batch_profit" target="_blank">批量加价率</a></div>
                                    </#if>
                        </td>
                    </tr>
                    </tbody>
                </table>

               <div class="reduce_content <#if (!$basicData->goods_id) hide </#if>">
                   <#if ($basicData->goods_id)
                       <div class="reduce_content edit_content">
                           <#if ($edit == 1)
                           <div class="set-reduce clearfix" >
                               <span class="tb-full-left"><strong>*</strong>设置折扣：</span>
                               <label class="reduce_label">
                                   <input type="radio" name="reduce_discount" value="">
                                   批量打&nbsp;<input type="text" class="reduce_text" id="prd_discount"  onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" name="batch_discount" value="${basicData->batch_discount!}">&nbsp;折
                               </label>
                               <label class="reduce_label">
                                   <input type="radio" name="reduce_discount">
                                   批量减价&nbsp;<input type="text" class="reduce_text" id="prd_reduce" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" name="batch_reduce" value="${basicData->batch_reduce!}">&nbsp;元
                               </label>
                               <label class="reduce_label">
                                   <input type="radio" name="reduce_discount">
                                   批量折后价&nbsp;<input type="text" class="reduce_text" id="prd_after"onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" name="batch_final_price" value="${basicData->batch_final_price!}">&nbsp;元
                               </label>
                               <span class="reduce_choose">确定</span>
                               <span class="reduce_cancel">取消</span>
                               <span class="price_choose_int">批量价格取整</span>
                               <span class="price_choose_del">批量删除</span>
                           </div>
                           </#if>
                           <div class="main_goods_table">
                               <table class="tb_goods">
                                   <thead>
                                   <tr class="change_tr_first">
                                       <td><input type="checkbox" id="choose_all"></td>
                                       <td width="25%">商品名称</td>
                                       <td>原价</td>
                                       <td>库存</td>
                                       <td>折扣</td>
                                       <td>减价</td>
                                       <td>折后价</td>
                                       <td <#if ($edit == 0) hidden </#if>>操作</td>
                                   </tr>
                                   </thead>
                                   <tbody class="reduce_tbody">
                                   <#if  (!empty($basicData->goods))
                                       <#list  ($basicData->goods as $item)
                                           <tr class="${item->goods_id!}">
                                               <td><input type="checkbox" class="choose_single"></td>
                                               <td>
                                                   <div class="goods_img">
                                                       <img src="${item->goods_img!}" />
                                                   </div>
                                                   <div class="goods_info">
                                                       <div class="goods_name">${item->goods_name!}</div>
                                                   </div>
                                               </td>
                                               <td class="original_price">${item->shop_price!}</td>
                                               <td>${item->goods_number!}</td>
                                               <td><input type="text" class="price_zk" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" value="${item->discount!}">折</td>
                                               <td><input type="text" class="price_jj" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" value="${item->reduce_price!}">元</td>
                                               <td class="price_zhj">
                                                   <div class="price_red">
                                                   </div>
                                                   <input type="text" class="price_zh" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" value="${item->goods_price!}">元
                                                   <div class="price_blue" goods_id="${item->goods_id!}" <#if ($item->prd_num <= 0) hidden </#if>>
                                                       <span>${item->prd_num!}</span>
                                                       个规格降价
                                                   </div>
                                               </td>
                                               <td <#if ($edit == 0) hidden </#if>><a item="${item->goods_id!}" class="change_goods_del">删除</a></td>
                                               <#if ($item->prd_list)
                                                   <#list ($item->prd_list as $prd_list)
                                                       <input class="spec_value" type="hidden" name="${prd_list->product_id!}" prd_id="${prd_list->product_id!}" value="${prd_list->prd_price!}" goods_id="${prd_list->goods_id!}" prd_price="${prd_list->price!}">
                                                   </#list>
                                               </#if>
                                           </tr>
                                       </#list>
                                   </#if>
                                   </tbody>
                               </table>
                           </div>
                       </div>
                   <#else>
                       <div class="reduce_content add_content" style="display: none">
                           <div class="set-reduce clearfix" >
                               <span class="tb-full-left"><strong>*</strong>设置折扣：</span>
                               <label class="reduce_label">
                                   <input type="radio" name="reduce_discount">
                                   批量打&nbsp;<input type="text" class="reduce_text" id="prd_discount"  onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">&nbsp;折
                               </label>
                               <label class="reduce_label">
                                   <input type="radio" name="reduce_discount">
                                   批量减价&nbsp;<input type="text" class="reduce_text" id="prd_reduce" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">&nbsp;元
                               </label>
                               <label class="reduce_label">
                                   <input type="radio" name="reduce_discount">
                                   批量折后价&nbsp;<input type="text" class="reduce_text" id="prd_after"onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">&nbsp;元
                               </label>
                               <span class="reduce_choose">确定</span>
                               <span class="reduce_cancel">取消</span>
                               <span class="price_choose_int">批量价格取整</span>
                               <span class="price_choose_del">批量删除</span>
                           </div>
                           <div class="main_goods_table">
                               <table class="tb_goods" >
                                   <thead>
                                   <tr class="change_tr_first">
                                       <td><input type="checkbox" id="choose_all"></td>
                                       <td width="25%">商品名称</td>
                                       <td>原价</td>
                                       <td>库存</td>
                                       <td>折扣</td>
                                       <td>减价</td>
                                       <td>折后价</td>
                                       <td>操作</td>
                                   </tr>
                                   </thead>
                                   <tbody class="reduce_tbody">
                                   </tbody>
                               </table>
                           </div>
                       </div>
                   </#if>
               </div>
                <div class="clearfix share_module" style="margin-top: 10px;">
                    <div class="fl">
                        <span class="tb-full-left"><strong>*</strong>店铺分享：</span>
                    </div>

                    <div class="fl">
                        <input type="radio" name="share_action" value="1" <#if  (!$module_share || $module_share['share_action'] == 1) checked </#if>/> 默认样式
                        <a href="javascript:;" class="show_eg">查看示例
                            <div class="hover_show">
                                <img src="http://${image_domain!}/image/admin/share/reduce_share.jpg"/>
                            </div>
                        </a>
                        <a href="javascript:;" class="show_eg">下载海报
                            <div class="hover_show">
                                <img src="http://${image_domain!}/image/admin/share/reduce_pictorial.jpg"/>
                            </div>
                        </a>
                        <div>
                            <input type="radio" name="share_action" value="2" <#if  ($module_share['share_action'] == 2) checked </#if>/> 自定义样式
                        </div>

                        <div style="padding-left: 22px;">
                            <span>文案：</span><input type="text" name="share_doc" value="${module_share['share_doc'] ?: $shop->shop_name!}" style="margin-left: 18px;"/>
                        </div>
                        <div style="padding-left: 22px;">
                            <span>分享图：</span>
                            <input type="radio" name="share_img_action" value="1" <#if  (!$module_share || $module_share['share_img_action'] == 1) checked </#if> /> 活动商品信息图
                            <p style="padding-left: 60px;">
                                <input type="radio" name="share_img_action" value="2" <#if  ($module_share['share_img_action'] == 2) checked </#if>/>自定义图片
                            </p>
                            <div class="module_share_image">
                                <input type="hidden" name="share_img" value="${module_share['share_img']!}">
                                <div class="choose_img" <#if  ($module_share['share_img']) style="display: block;" <#else> style="display: none;" </#if>>
                                    <img src="${module_share['share_img']!}"/>
                                    <span>重新选择</span>
                                </div>
                                <input type="button" value="" class="add_image" <#if  ($module_share['share_img']) style="display: none;" <#else> style="display: inline-block;" </#if>>
                                <span style="float: left; margin-top: 25px; margin-left: 20px;">建议尺寸: 800*800像素</span>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

        </div>
        <div class="reduce_price_footer fix_footer">
            <button type="submit" class="save">保存</button>
        </div>
    </div>
</div>

<div class="goods_price_popup" hidden>
    <form action="" method="POST" id="form2">
        {{csrf_field()!}
        <p class="import_popup_title">第一步：模板下载</p>
        <div class="card_box">
            <div>
                <span>下载模板：</span>
                <a href="{{ main_url('doc/batch_price.xlsx')!}" style="color: #5a8bff;">批量降价导入文件模板</a>
            </div>
        </div>
        <p class="import_popup_title">第二步：数据导入</p>
        <div class="file_upload">
            <div>
                <span style="vertical-align: middle">上传文件：</span>
                <div class="file_box clearfix">
                    <span class="fl filetext"></span>
                    <span class="fr" onclick="document.getElementById('file1').click()">浏览...</span>
                </div>
                <input type="file" name="goods_price" id="file1" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[0].innerText=this.value">
            </div>
            <div class="file_upload_explain">
                <p>导入规则：文件当前仅支持excel格式。</p>
            </div>
        </div>
    </form>
</div>

<div class="goods_profit_popup" hidden>
    <form action="" method="POST" id="form3">
        {{csrf_field()!}
        <p class="import_popup_title">第一步：模板下载</p>
        <div class="card_box">
            <div>
                <span>下载模板：</span>
                <a href="{{ main_url('doc/batch_profit.xlsx')!}" style="color: #5a8bff;">批量加价率导入文件模板</a>
            </div>
        </div>
        <p class="import_popup_title">第二步：数据导入</p>
        <div class="file_upload">
            <div>
                <span style="vertical-align: middle">上传文件：</span>
                <div class="file_box clearfix">
                    <span class="fl filetext"></span>
                    <span class="fr" onclick="document.getElementById('file2').click()">浏览...</span>
                </div>
                <input type="file" name="goods_profit" id="file2" accept=".xlsx,.xls" onchange="document.getElementsByClassName('filetext')[1].innerText=this.value">
            </div>
            <div class="file_upload_explain">
                <p>导入规则：文件当前仅支持excel格式。</p>
            </div>
        </div>
    </form>
</div>

<div class="content_time" style="display: none">
   <div class="label_day">
       <input type="radio" name="redu_time" id="redu_time1" value="1">
       <label for="redu_time1">每天</label>
       <input type="text" name="startTime1" id="startTime1" onclick="picker2();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime1\')}',minDate:'00:00', dateFmt:'HH:mm'})" autocomplete="off">
       至&nbsp;
       <input type="text" name="endTime1" id="endTime1" onclick="picker2();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime1\')}', maxDate:'23:59',dateFmt:'HH:mm'})" autocomplete="off">
   </div>
   <div class="label_month">
       <input type="radio" name="redu_time" id="redu_time2" value="2">
       <label for="redu_time2">每月</label>
       <select name="month" id="" class="red_select">
           <option value="">1</option>
       </select>
       日&nbsp;
       <input type="text" name="startTime2" id="startTime2" onclick="picker2();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime2\')}',minDate:'00:00', dateFmt:'HH:mm'})" autocomplete="off">
       至&nbsp;
       <input type="text" name="endTime2" id="endTime2" onclick="picker2();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime2\')}',maxDate:'23:59',  dateFmt:'HH:mm'})" autocomplete="off">

   </div>
   <div class="label_year">
       <input type="radio" name="redu_time" id="redu_time3" value="3">
       <label for="redu_time3">每周</label>
       <span class="red_week" week="1">周一</span>
       <span class="red_week" week="2">周二</span>
       <span class="red_week" week="3">周三</span>
       <span class="red_week" week="4">周四</span>
       <span class="red_week" week="5">周五</span>
       <span class="red_week" week="6">周六</span>
       <span class="red_week" week="0">周日</span>
       <input type="text" name="startTime3" id="startTime3" onclick="picker2();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime3\')}',minDate:'00:00', dateFmt:'HH:mm'})" autocomplete="off">
       至&nbsp;
       <input type="text" name="endTime3" id="endTime3" onclick="picker2();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime3\')}',maxDate:'23:59',  dateFmt:'HH:mm'})" autocomplete="off">
   </div>
</div>
<#include ('admin.preview_common')
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/common_share.js?v=1.1.1"></script>

<script>
    hasSaved = true;
    var edit = '${edit!}';
    if(edit == 1){
        // $("input[name='zhouqi']").removeClass('loop');
        // $("input[name='zhouqi']").addClass('loop_bak');
        $(".loop").hide();
        $(".loop").attr("id",'');
        $(".loop_text").show();
        $(".loop_text").attr("id",'zhouqi');
        if('${span!}' != ''){
            $(".loop_text").prop("checked",true);
        }
    }else if(edit == '0'){
        $(".xg").hide();
        $('input').each(function(){
            var name =$(this).attr("name");
                $(this).prop('readonly','readonly');
                if(name != 'zhouqi'){
                    $(this).css("background","#eee")
                }else{
                    $(this).prop('disabled','disabled');
                }
            $(".loop_text").addClass("hide");
        });
        $(".loop").hide();
        $(".loop").attr("id",'');
        $(".loop_text").show();
        if('${span!}' != ''){
            $(".loop_text").prop("checked",true);
            $(".loop_text").removeClass('hide');
        }
    }else if(edit === ''){
        $(".loop_bak").hide()
        $(".loop").attr("id",'');
    }
    if($("input[name='period_action']").val() != ''){
        var point_time = $("input[name='point_time']").val().split('@');
        var extend_time = $("input[name='extend_time']").val().split('@');
        var period_action = $("input[name='period_action']").val().split('@');
        $("input[name='redu_time']").each(function (i,v) {
            if($(v).val() == $("input[name='period_action']").val()){
                $(v).prop("checked",true);
            }
            if($(v).val() == period_action){
                $('input[name="startTime1"]').val(point_time[0]);
                $('input[name="endTime1"]').val(point_time[1]);
            }else if($(v).val() == period_action){
                $('select[name="month"]').find("option").each(function (j) {
                    if($('select[name="month"]').find("option").eq(j).val()==extend_time){
                        $('select[name="month"]').find("option").eq(j).attr("selected",'selected');
                    }
                })
                $('input[name="startTime2"]').val(point_time[0]);
                $('input[name="endTime2"]').val(point_time[1]);
            }else if($(v).val() == period_action){
                $('.red_week').each(function (j) {
                    if($('.red_week').eq(j).attr('week') == extend_time){
                        $('.red_week').eq(j).attr('flag',0);
                    }
                })
                $('input[name="startTime3"]').val(point_time[0]);
                $('input[name="endTime3"]').val(point_time[1]);
            }
        })
    }
    $(document).on('click','input.loop ,.xg',function () {
        var _this = $(this);

        layui.use('layer', function () { //独立版的layer无需执行这一句
            layer.open({
                type: 1
                , title: ['设置活动重复周期', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['600px']
                , content:$('.content_time') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , success: function (layero, index) {

                }
                , yes: function (index, layero) { //保存按钮的回调
                    var type = $(".content_time").find("input[type='radio']:checked").val();
                    $("input[name='period_action']").val(type);
                    if(type == 1){
                        if($('input[name="startTime1"]').val() == '' || $('input[name="endTime1"]').val() == ''){
                            util.mobile_alert('请填写有效周期');
                            return false;
                        }
                        $("input[name='point_time']").val($("input[name='startTime1']").val()+"@"+$("input[name='endTime1']").val());
                        var text ="每日"+$("input[name='startTime1']").val()+"至"+$("input[name='endTime1']").val();
                        $(".loop_text span").text(text);
                        $('.loop_text').show();
                        $(".loop_text").removeClass('hide');
                        $('.loop_bak').show();
                        $(".loop_bak").attr("id",'zhouqi');
                        $(".loop_bak").prop("checked",true);
                        $('.loop').hide();
                        $(".loop").attr("id",'');
                    }else if(type == 2){
                        if($('input[name="startTime2"]').val() == '' || $('input[name="endTime2"]').val() == ''){
                            util.mobile_alert('请填写有效周期');
                            return false;
                        }
                        $("input[name='point_time']").val($("input[name='startTime2']").val()+"@"+$("input[name='endTime2']").val());
                        $("input[name='extend_time']").val($('select[name="month"] option:selected').text());
                        var text ="每月"+$('select[name="month"] option:selected').text()+"日"+$("input[name='startTime2']").val()+"至"+$("input[name='endTime2']").val();
                        $(".loop_text span").text(text);
                        $('.loop_text').show();
                        $(".loop_text").removeClass('hide');
                        $('.loop_bak').show();
                        $(".loop_bak").attr("id",'zhouqi');
                        $(".loop_bak").prop("checked",true);
                        $('.loop').hide();
                        $(".loop").attr("id",'');
                    }else if(type == 3){
                        if($('input[name="startTime3"]').val() == '' || $('input[name="endTime3"]').val() == ''){
                            util.mobile_alert('请填写有效周期');
                            return false;
                        }
                        var weeks = "";
                        var week = "";
                        var bz = 0;
                        $(".label_year").find(".red_week").each(function (i) {
                            if($(".label_year").find(".red_week").eq(i).attr('flag')==0 && $(".label_year").find(".red_week").eq(0).attr('flag')!='undefined'){
                                weeks = weeks + $(".label_year").find(".red_week").eq(i).attr('week') + '@';
                                week = week +$(".label_year").find(".red_week").eq(i).text()+' ';
                                bz ++;
                            }
                        })
                        if(bz < 1){
                            util.mobile_alert('请选择每周重复日期');
                            return false;
                        }
                        $("input[name='extend_time']").val(weeks);
                        $("input[name='point_time']").val($("input[name='startTime3']").val()+"@"+$("input[name='endTime3']").val());
                        var text = "每"+week+$("input[name='startTime3']").val()+"至"+$("input[name='endTime3']").val();
                        $(".loop_text span").text(text);
                        $('.loop_text').show();
                        $(".loop_text").removeClass('hide');
                        $('.loop_bak').show();
                        $(".loop_bak").attr("id",'zhouqi');
                        $(".loop_bak").prop("checked",true);
                        $('.loop').hide();
                        $(".loop").attr("id",'');
                    }
                    _this.prop('checked',true);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                    _this.prop('checked',false);
                },cancel:function (index, layero) {
                  _this.prop('checked',false);
                }
            });
        });
    })
    $("input.loop_bak").click(function () {
        var _this = $(this);
        if (_this.prop("checked") == false) {
            $('.loop').show();
            $(".loop").attr("id",'zhouqi');
            $('.loop_bak').hide();
            $(".loop_bak").attr("id",'');
            $('.loop_text').hide();
            $('.loop_text').addClass('hide');
            $(".loop").prop("checked",false);
        }
        // return false;
    })
    for(var i=2;i<32;i++){
        var option =`<option value="">${i}</option>`;
        $(".red_select").append(option);
    }

    //设置周期

    $(".label_day label,.label_day input[type='radio']").click(function () {
        if(typeof($(this).parent().find("input[type='text']").attr("disabled")) != "undefined"){
            $(this).parent().find("input[type='text']").attr("disabled",false);
        }
        $(this).parent().siblings().find("input[type='text'],.red_select").attr("disabled",true);
        $(".red_select").css("background","#ebebe4");
        $(".red_week").css({"color":"#666","border":"1px solid #ccc","background":"#ebebe4"});
        $(this).parent().find("input[type='radio']").prop("checked",true);
        $(document).off("click",'.red_week');
    })
    $(".label_day input[type='text']").click(function () {
        if(typeof($(this).parent().find("input[type='text']").attr("disabled")) != "undefined"){
            $(this).parent().find("input[type='text']").attr("disabled",false);
        }
        $(this).parent().siblings().find("input[type='text'],.red_select").attr("disabled",true);
        $(".red_select").css("background","#ebebe4");
        $(".red_week").css({"color":"#666","border":"1px solid #ccc","background":"#ebebe4"});
        $(this).parent().find("input[type='radio']").prop("checked",true);
    });
    $(".label_month input[type='text'],.label_month select,.label_month input[type='radio'],.label_month label").click(function () {
        if(typeof($(this).parent().find("input[type='text']").attr("disabled")) != "undefined"){
            $(this).parent().find("input[type='text'],select").attr("disabled",false);
            $(".red_select").css("background","#fff");
        }
        $(this).parent().siblings().find("input[type='text']").attr("disabled",true);
        $(".red_week").css({"color":"#666","border":"1px solid #ccc","background":"#ebebe4"});
        $(this).parent().find("input[type='radio']").prop("checked",true);
        $(document).off("click",'.red_week');
    });
   $(".label_year input[type='radio'],.label_year label").click(function () {
       $(".red_week").css({"color":"#333","border":"1px solid #ccc","background":"#fff"});
       if(typeof($(this).parent().find("input[type='text']").attr("disabled")) != "undefined"){
           $(this).parent().find("input[type='text']").attr("disabled",false);
       }
       $(this).parent().siblings().find("input[type='text'],select").attr("disabled",true);
       $(".red_select").css("background","#ebebe4");
       $(".red_week").removeAttr("flag");
       $(document).off("click",'.red_week').on('click','.red_week',function (e) {
           if(typeof($(this).parent().find("input[type='text']").attr("disabled")) != "undefined"){
               $(this).parent().find("input[type='text']").attr("disabled",false);
           }
           $(this).parent().siblings().find("input[type='text'],select").attr("disabled",true);
           $(".red_select").css("background","#ebebe4");
           if(typeof ($(this).attr("flag")) == 'undefined'){
               $(this).css({"color":"#5A8BFF","border":"1px solid #5A8BFF"});
               $(this).attr("flag","0");
           }else{
               $(this).css({"color":"#333","border":"1px solid #ccc"});
               $(this).removeAttr("flag");
           }
           $(this).parent().find("input[type='radio']").prop("checked",true);
           e.stopPropagation();
       });

   })



    //失去焦点判断
    $('.limit_amount').blur(function(){
        var num = $('input[name = "limit_amount"]').val();
        if(!(/(^[1-9]\d*$)/.test(num)) && num != 0){
            util.mobile_alert('限购数量请输入正整数');
            return false;
        }
    });


    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                realTimeFmt:"HH:mm:ss",
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    };
    function picker2(){
        hasSaved = false;
        return WdatePicker(
            {
                realTimeFmt:"HH:mm:ss",
                dateFmt: 'HH:mm',
                autoUpdateOnChanged: false
            }
        );
    };
    $("#batch_price").click(function(){
        // console.log(11);
        layui.use('layer', function() {
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['批量降价', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['500px','auto']
                , content: $('.goods_price_popup')
                , btn: ['导入']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    $(".reduce_content").removeClass("hide");
                    $('input[name="add_type"]').val(1);
                    var formData = new FormData();
                    formData.append('goods_price', $('#file1')[0].files[0]);
                    $.ajaxSetup({
                        contentType : false,
                        processData : false,
                    });
                    util.ajax_json('/admin/reduce/import/price', function (response) {

                        if (response.error == 0) {
                            var data = response.content;
                            var checkedId = data.goodsIds;//goodsIds的字符串
                            // var checkedId = '956,955';//goodsIds的字符串
                            var prdPrices = data.prdPrices;//prd的价格对
                            console.log(JSON.stringify(prdPrices));
                            $('input[name="prd_price"]').val(JSON.stringify(prdPrices));
                            var old_goods = $('input[name="goods_id"]').val().split(",");
                            formData.append('select_id', checkedId);
                            formData.append('select_goods_id', checkedId);
                            formData.append('add_type', 1);
                            formData.append('prd_price', JSON.stringify(prdPrices));
                            console.log(checkedId);
                            util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                                var list = response.content;
                                console.log(list);
                                var html = '';
                                for (var i in list) {
                                    if($.inArray(list[i].goods_id.toString(),old_goods) == -1){
                                        // list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                                        html += `<tr class="${ list[i].goods_id }">
                                           <td><input type="checkbox" class="choose_single"></td>
                                           <td>
                                               <div class="goods_img">
                                                   <img src="${ list[i].goods_img }" />
                                               </div>
                                               <div class="goods_info">
                                                   <div class="goods_name">${ list[i].goods_name }</div>
                                               </div>
                                           </td>
                                           <td class="original_price">${ list[i].shop_price}</td>
                                           <td>${list[i].goods_number }</td>
                                           <td><input type="text" class="price_zk" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">折</td>
                                           <td><input type="text" class="price_jj" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">元</td>
                                           <td class="price_zhj">
                                               <div class="price_red" >
                                               </div>
                                               <input type="text" class="price_zh" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" style="font-size: 14px">元
                                               <div class="price_blue" goods_id="${ list[i].goods_id }" prd_num="${ list[i].prd_num }">
                                                   <span>${ list[i].prd_num }</span>
                                                   个规格降价
                                               </div>
                                           </td>
                                           <td><a item="${ list[i].goods_id }" class="change_goods_del">删除</a></td>
                                       </tr>`;
                                    }
                                }
                                if($("input[name='id']").val() == ""){
                                    $(".add_content").css("display","block");
                                    // $(".edit_content").css("display",'none');
                                }
                                $(".reduce_tbody").append(html);
                                if(old_goods[0]!=""){
                                    for(var j in old_goods){
                                        if(!in_array(old_goods[j],checkedId.split(","))){
                                            $(".reduce_tbody").find('.'+ old_goods[j] ).remove();
                                        }
                                    }
                                }
                                $('input[name="goods_id"]').val(checkedId);
                                util.ajax_json("/admin/reduce/select",function (res) {
                                    if(res.error == 0){
                                        var list = res.content.data;
                                        $(".reduce_tbody").find("tr").each(function (j,k) {
                                            var html_prd = '';
                                            for (var m in list) {
                                                if($.inArray(list[m].goods_id.toString(),old_goods) == -1){
                                                    if ($(".reduce_tbody").find("tr").eq(j).attr("class") == list[m].goods_id) {
                                                        var list_prd = list[m].prd_list;
                                                        for (var n in list_prd) {
                                                            html_prd += `<input type="hidden" name="${ list_prd[n].prd_id }" prd_id="${ list_prd[n].prd_id }" value="${ list_prd[n].prd_price }" goods_id="${ list_prd[n].goods_id }" class="spec_value" prd_price="${ list_prd[n].price }">`;
                                                        }
                                                        if(list[m].prd_num == 0 || list[m].prd_num == 1){
                                                            $(".reduce_tbody").find("tr").eq(j).find('.price_zk').val(list[m].discount);
                                                            $(".reduce_tbody").find("tr").eq(j).find('.price_jj').val(list[m].reduce);
                                                            $(".reduce_tbody").find("tr").eq(j).find('.price_zh').val(list[m].price);
                                                        }
                                                    }
                                                }
                                            }
                                            $(".reduce_tbody").find("tr").eq(j).append(html_prd);
                                        })
                                    }else{
                                        util.mobile_alert('折后价大于原价：'+res.content.prd_sn);
                                    }
                                },formData)
                                if ($("#choose_all").prop('checked')) {
                                    $(".choose_single").prop('checked', 'checked');
                                } else {
                                    $(".choose_single").prop('checked', false);
                                }
                                $(".price_blue").each(function (q,w) {
                                    if($(".price_blue").eq(q).attr("prd_num")<1){
                                        $(".price_blue").eq(q).hide();
                                    }
                                })
                                layer.close(index);
                            },formData)
                            // util.mobile_alert(response.content);
                            // setTimeout(function () {
                            //     layer.close(index);
                            //     location.reload();
                            // }, 2000);

                        } else {
                            var error = response.content;
                            var msg = '';
                            if(error.repeat_sns !=''){
                                msg += '重复的规格：'+error.repeat_sns;
                            }
                            if(error.un_exsit !=''){
                                msg += '不存在的规格：'+error.un_exsit;
                            }
                            util.mobile_alert(msg);
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
    });
    $("#batch_profit").click(function(){
        // console.log(11);
        layui.use('layer', function() {
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['批量加价率', 'text-align:center;padding: 0px;']
                , offset: 'auto'
                , area: ['500px','auto']
                , content: $('.goods_profit_popup')
                , btn: ['导入']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,yes: function (index) {
                    $(".reduce_content").removeClass("hide");
                    $('input[name="add_type"]').val(2);
                    var formData = new FormData();
                    formData.append('goods_profit', $('#file2')[0].files[0]);
                    $.ajaxSetup({
                        contentType : false,
                        processData : false
                    });
                    util.ajax_json('/admin/reduce/import/profit', function (response) {

                        if (response.error == 0) {
                            var data = response.content;
                            var checkedId = data.goodsIds;//goodsIds的字符串
                            console.log(checkedId);
                            var prdPrices = data.goods_profit;//prd的价格对
                            var bs_profit = data.bs_profit;//prd的价格对
                            formData.append('select_id', checkedId);
                            formData.append('select_goods_id', checkedId);
                            formData.append('add_type', 2);
                            formData.append('batch_profit', JSON.stringify(prdPrices));
                            $('input[name="bs_profit"]').val(JSON.stringify(bs_profit));
                            var old_goods = $('input[name="goods_id"]').val().split(",");
                            util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                                var list = response.content;
                                console.log(list);
                                var html = '';
                                for (var i in list) {
                                    if($.inArray(list[i].goods_id.toString(),old_goods) == -1){
                                        // list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                                        html += `<tr class="${ list[i].goods_id }">
                                           <td><input type="checkbox" class="choose_single"></td>
                                           <td>
                                               <div class="goods_img">
                                                   <img src="${ list[i].goods_img }" />
                                               </div>
                                               <div class="goods_info">
                                                   <div class="goods_name">${ list[i].goods_name }</div>
                                               </div>
                                           </td>
                                           <td class="original_price">${ list[i].shop_price}</td>
                                           <td>${list[i].goods_number }</td>
                                           <td><input type="text" class="price_zk" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">折</td>
                                           <td><input type="text" class="price_jj" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">元</td>
                                           <td class="price_zhj">
                                               <div class="price_red" >
                                               </div>
                                               <input type="text" class="price_zh" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" style="font-size: 14px">元
                                               <div class="price_blue" goods_id="${ list[i].goods_id }" prd_num="${ list[i].prd_num }">
                                                   <span>${ list[i].prd_num }</span>
                                                   个规格降价
                                               </div>
                                           </td>
                                           <td><a item="${ list[i].goods_id }" class="change_goods_del">删除</a></td>
                                       </tr>`;
                                    }
                                }
                                if($("input[name='id']").val() == ""){
                                    $(".add_content").css("display","block");
                                    // $(".edit_content").css("display",'none');
                                }
                                $(".reduce_tbody").append(html);
                                if(old_goods[0]!=""){
                                    for(var j in old_goods){
                                        if(!in_array(old_goods[j],checkedId.split(","))){
                                            $(".reduce_tbody").find('.'+ old_goods[j] ).remove();
                                        }
                                    }
                                }
                                $('input[name="goods_id"]').val(checkedId);
                                util.ajax_json("/admin/reduce/select",function (res) {
                                    if(res.error == 0){
                                        var list = res.content.data;
                                        $(".reduce_tbody").find("tr").each(function (j,k) {
                                            var html_prd = '';
                                            for (var m in list) {
                                                if($.inArray(list[m].goods_id.toString(),old_goods) == -1){
                                                    if ($(".reduce_tbody").find("tr").eq(j).attr("class") == list[m].goods_id) {
                                                        var list_prd = list[m].prd_list;
                                                        for (var n in list_prd) {
                                                            html_prd += `<input type="hidden" name="${ list_prd[n].prd_id }" prd_id="${ list_prd[n].prd_id }" value="${ list_prd[n].prd_price }" goods_id="${ list_prd[n].goods_id }" class="spec_value" prd_price="${ list_prd[n].price }">`;
                                                        }
                                                        if(list[m].prd_num == 0 || list[m].prd_num == 1){
                                                            $(".reduce_tbody").find("tr").eq(j).find('.price_zk').val(list[m].discount);
                                                            $(".reduce_tbody").find("tr").eq(j).find('.price_jj').val(list[m].reduce);
                                                            $(".reduce_tbody").find("tr").eq(j).find('.price_zh').val(list[m].price);
                                                        }
                                                    }
                                                }
                                            }
                                            $(".reduce_tbody").find("tr").eq(j).append(html_prd);
                                        })
                                    }else{
                                        util.mobile_alert('折后价大于原价：'+res.content.prd_sn);
                                    }
                                },formData)
                                if ($("#choose_all").prop('checked')) {
                                    $(".choose_single").prop('checked', 'checked');
                                } else {
                                    $(".choose_single").prop('checked', false);
                                }
                                $(".price_blue").each(function (q,w) {
                                    if($(".price_blue").eq(q).attr("prd_num")<1){
                                        $(".price_blue").eq(q).hide();
                                    }
                                })
                                layer.close(index);
                            },formData)
                            // util.mobile_alert(response.content);
                            // setTimeout(function () {
                            //     layer.close(index);
                            //     location.reload();
                            // }, 2000);

                        } else {
                            var error = response.content.error;
                            var msg = '';
                            if(error.brand_repeat !=''){
                                msg += '重复的品牌:'+error.brand_repeat;
                            }
                            if(error.sort_repeat !=''){
                                msg += '重复的分类:'+error.sort_repeat;
                            }
                            if(error.not_exist_brand !=''){
                                msg += '不存在的品牌:'+error.not_exist_brand;
                            }
                            if(error.not_exist_sort !=''){
                                msg += '不存在的分类:'+error.not_exist_sort;
                            }
                            if(error.goods_repeat !=''){
                                msg += '重复的商品:'+error.goods_repeat;
                            }
                            util.mobile_alert(msg);
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
    });
    $("#save_test").click(function(){
        $("#form1").submit();
    })

    {{--<#if  (!empty($basicData))--!}
    {{--$('input[name="is_default"]').click(function () {--!}
    {{--return false;--!}
    {{--});--!}
    {{--</#if>--!}

</script>

<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/reduce_price.js?v=1.2.7"></script>
<script>
    //getPowerInfo('main_config','reduce_price','sub_4','限时降价',0);
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
</script>