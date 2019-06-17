<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/integral_manage.css?v=1.0.7" type="text/css" />
<link rel="stylesheet" href="/css/admin/common_share.css?v=1.2.1" type="text/css" />
<style>
    .goods-btn-modify {
        margin-left: 10px;
        color: #5A8BFF;
        cursor: pointer;
    }
    .pin_group_footer button{
        line-height: 24px;
    }
    .pin_group_footer button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #fff;
    }
    .pin_group_footer button:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
        color: #fff;
    }
    .config_box .detail_li .goods_price_box input{
        width:60px;
    }
</style>
<div class="title" >
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
    <span style="color: #666;">
        积分兑换
        {{--<#if  (empty($basicData))--!}
        {{--新建积分兑换商品--!}
            {{--<#else>--!}
            {{--查看积分兑换商品--!}
        {{--</#if>--!}
    </span>
</div>
<div class="main_container">
    <div class="list_nav">
        <ul class="clearfix">
            <li <#if  (empty($request['nav'])) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=0">全部积分兑换活动</a>
            </li>
            <li <#if  ($request['nav'] == 1) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=1">进行中</a>
            </li>
            <li <#if  ($request['nav'] == 2) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=2">未开始</a>
            </li>
            <li <#if  ($request['nav'] == 3) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=3">已过期</a>
            </li>
            <li <#if  ($request['nav'] == 4) class="actives" </#if>>
                <a href="/admin/market/integral/convert/list?nav=4">已停用</a>
            </li>
            <li <#if  ($request['nav'] == 5) class="actives" </#if>>
                <a href="/admin/market/integral/goods/add?nav=5"><#if (empty($basicData))添加<#else>查看</#if>积分兑换活动</a>
            </li>
        </ul>
    </div>
    <form action="/admin/market/integral/goods/add" method="post" id="form1" style="padding-bottom: 100px">
        {{ csrf_field()!}
        <input type="hidden" name="id" value="${basicData->id!}">
        <div class="config_box">
            <ul>
                <li class="clearfix detail_li">
                    <div class="item_title"><span class="strong">*</span>活动名称:</div>
                    <div class="right_item">
                        <input type="text" name="name" id="act_name" class="act_name" value="${basicData->name!}">
                        <span class="switch_tips">只作为商家记录使用，用户不会看到这个名称</span>
                    </div>
                </li>
                <li class="clearfix detail_li">
                    <div class="item_title"><span class="strong">*</span>有效期：</div>
                    <div class="right_item">
                        <input type="text" onclick="picker()" class="kaishi" name="start_time" style="margin-right: 10px" value="${basicData->start_time!}" autocomplete="off">
                        至
                        <input type="text" onclick="picker()" class="jeishu" name="end_time" style="margin-left: 10px" value="${basicData->end_time!}" autocomplete="off">
                    </div>
                </li>
                <li class="clearfix detail_li">
                    <div class="item_title"><span class="strong">*</span>单个用户最多可兑换数量：</div>
                    <div class="right_item">
                        <input type="text" class="most_nums"  onkeyup="value=value.replace(/[^\d]/g,'')" name="max_exchange_num" value="${basicData->max_exchange_num ?? 1!}">
                        <span class="switch_tips">填0则不限制</span>
                    </div>
                </li>
                <li class="clearfix detail_li">
                    <div class="item_title"><span class="strong">*</span>添加商品：</div>
                    <div class="right_item" id="choose-goods">
                        <#if (empty($basicData->goods_id))
                            <input type="button" value="+ 选择商品" id="sel-goods-btn" class="choose_goods" name="choose_goods">
                        </#if>
                        <input type="hidden" name="goods_id" value="${basicData->goods_id!}">
                        <span class="goods-namess" <#if  (empty($goods->goods_name)) style="display: none;" </#if>>${goods->goods_name!}</span>
                        <#if (empty($basicData->goods_id) || $basicData->start_time>date("Y-m-d H:i:s"))
                            <span class="goods-btn-modify" <#if (empty($basicData)) style="display: none;" </#if>>修改</span>
                        </#if>
                    </div>

                </li>
                <li class="clearfix detail_li">
                    <div class="item_title"><span class="strong">*</span>积分兑换设置：</div>
                </li>
                <li class="clearfix detail_li" style="height: auto;">
                    <div>
                        <table width="100%">
                            <thead>
                                <tr>
                                    <td width="20%">商品名称/规格</td>
                                    <td width="20%">原价（元）</td>
                                    <td width="20%">商品兑换价格</td>
                                    <td width="20%">原库存</td>
                                    <td width="20%">兑换商品库存</td>
                                </tr>
                            </thead>
                            <tbody id="product-info">
                                <tr style="display: none;">
                                    <td width="20%"></td>
                                    <td width="20%" class="goods_price"></td>
                                    <td width="20%" class="goods_price_box">
                                        <input type="text" name="money[]" onkeyup="value=value.replace(/[^\d.]/g,'')">元 +
                                        <input type="text" name="score[]" class="product-info-template">积分
                                    </td>
                                    <td width="20%"></td>
                                    <td width="20%" class="stock">
                                        <input type="text" name="stock[]" class="product-info-template">
                                    </td>
                                    <input type="hidden" name="product_id[]" value="" class="product-info-template"/>
                                </tr>
                                <#list  ($product as $key => $item)
                                    <tr class="set_line_value">
                                        <td width="20%">
                                            <#if (empty($specProduct[$key]->prd_desc))
                                                ${goods->goods_name!}
                                            <#else>
                                                ${specProduct[$key]->prd_desc!}
                                            </#if>
                                        </td>
                                        <td width="20%" class="goods_price">
                                            ${specProduct[$key]->prd_price!}
                                        </td>
                                        <td width="20%" class="goods_price_box">
                                            <input type="text" name="money[]" value="${item->money!}" onkeyup="value=value.replace(/[^\d.]/g,'')">元 +
                                            <input type="text" name="score[]" value="${item->score!}">积分
                                        </td>
                                        <td width="20%">${specProduct[$key]->prd_number!}</td>
                                        <td width="20%" class="stock">
                                            <input type="text" name="stock[]" <#if ($specProduct[$key]->prd_number < $item->stock) value="${specProduct[$key]->prd_number!}" <#else> value="${item->stock!}" </#if>>
                                        </td>
                                        <input type="hidden" name="product_id[]" value="${item->product_id!}"/>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li class="clearfix detail_li" <#if (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s")) hidden </#if>>
                    <div class="item_title">批量设置：</div>
                    <div class="right_title"><span>
                                <a href="javascript:void(0)" class="set_line" onclick="util.set_line_value('goods_price_box',2)" >商品兑换价格</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="set_line" onclick="util.set_line_value('stock',1)" >商品兑换库存</a>
                            </span></div>
                </li>
                <li class="clearfix detail_li share_module" style="height: auto;">
                    <div class="item_title"><span class="strong">*</span>店铺分享：</div>
                    <div class="fl" style="position: relative; ">
                        <input type="radio" name="share_action" value="1" <#if  (!$module_share || $module_share['share_action'] == 1) checked </#if>/> 默认样式
                        <a href="javascript:;" class="show_eg">查看示例
                            <div class="hover_show" style="top: -31px;">
                                <img src="http://${image_domain!}/image/admin/share/integral_share_new.jpg"/>
                            </div>
                        </a>
                        <a href="javascript:;" class="show_eg">下载海报
                            <div class="hover_show" style="top: -31px;">
                                <img src="http://${image_domain!}/image/admin/share/integral_pictorial.jpg"/>
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
                            <div class="module_share_image" style="margin-left: 60px;">
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
                </li>
            </ul>
        </div>

    </form>
    <div class="pin_group_footer fix_footer">
        <button type="submit">保存</button>
    </div>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script src="/js/admin/add_integral_goods.js?v=1.0.8" type="text/javascript"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/common_share.js?v=1.1.1"></script>

<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false});
    }
    var msg = "{{ session('message')!}";
    if (msg != '') {
        util.mobile_alert(msg);
    }
    <#if  (!empty($basicData) && $basicData->start_time<=date("Y-m-d H:i:s"))
        $('input[type="text"]').prop('disabled', true);
        $('button[type="submit"]').hide();
    </#if>

    var hasSaved = true;
    util.inputChange();
    util.selectChange();
    $(".choose_goods").click(function () {
        var hasSaved = false;
    });
    $(".goods-btn-modify").click(function () {
        var hasSaved = false;
    });
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗';
        }
    };
    var act_name = $('.act_name').get(0);
    var flag = false;
    act_name.addEventListener('compositionstart', function(){
        console.log(1);
        flag = true;
 6  });
    act_name.addEventListener('compositionend',function(){
        console.log(2)
        flag = false
        limitLength(this.value,'40','','act_name')
    });
    act_name.addEventListener('input',function(){
        console.log(3)
        limitLength(this.value,'40','','act_name')
    });
    function limitLength(value, byteLength, title, attribute) { 
        if(!flag){
            var newvalue = value.replace(/[^\x00-\xff]/g, "**");               
            var length = newvalue.length;
            if (length * 1 <=byteLength * 1){ 
                    return; 
            } 
            var limitDate = newvalue.substr(0, byteLength); 
            var count = 0; 
            var limitvalue = ""; 
            for (var i = 0; i < limitDate.length; i++) { 
                    var flat = limitDate.substr(i, 1); 
                    if (flat == "*") { 
                        count++; 
                    } 
            } 
            var size = 0; 
            var istar = newvalue.substr(byteLength * 1 - 1, 1);
        
            if (count % 2 == 0) { 
                    size = count / 2 + (byteLength * 1 - count); 
                    limitvalue = value.substr(0, size); 
            } else { 
                    size = (count - 1) / 2 + (byteLength * 1 - count); 
                    limitvalue = value.substr(0, size); 
            }
            document.getElementById(attribute).value = limitvalue; 
            return; 
        }         
    }
    getPowerInfo('main_config','integral_goods','sub_4','积分商品',0);

</script>