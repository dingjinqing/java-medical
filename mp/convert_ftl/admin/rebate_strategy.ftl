<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.3" type="text/css" />
<style type="text/css">
    .third_cate{
        margin-left: 20px;
    }
    .goods_info{
        width: 180px;
    }
    .float-layer,
    .float-layer_cost {
        padding: 15px;
        position: absolute;
        left: -220px;
        top: -80px;
        z-index:3;
        border:1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150, 150, 150, 0.5);
        border-radius: 5px;
        background-color: #fff;
        line-height: 30px;
        display: block;
        font-size: 12px;
    }
    .float-layer_cost{
        left: 0;
        top: -120px;
    }
    .float-layer .float-layer-text,
    .float-layer_cost .float-layer-text{
        font:inherit;
        margin: 0;
        padding: 0;
        line-height:1.5;
        vertical-align: baseline;
        white-space: nowrap;
        font-size: 0px;
    }
    .float-layer .float-layer-text p,
    .float-layer_cost .float-layer-text p{
        font-size: 12px;
        text-align: left;
    }
    .float-layer .float-layer-i,
    .float-layer_cost .float-layer-i{
        position: absolute;
        bottom: -7px;
        left: 50%;
        transform: rotate(-45deg);
        width: 0;
        height: 0;
        border-width: 7px;
        border-style: dashed;
        border-color: #fff;
        box-shadow: -2px 2px 3px rgba(150, 150, 150, 0.5);
        z-index: 1;
    }
    .float-layer_cost .float-layer-i{
        left: 47px;
    }
    .distributer_table .tbody div{
        display: inline-block;
        vertical-align: middle;
    }
    .distributer_table .td_r_div{
        text-align: left;
    }
    .td_r_div span{
        line-height: 20px;
    }
    .td_r_div span.red{
        color: #ff6666;
    }
    .goods_table, .cat_table, .goods_modal, .cat_modal, .sort_table {
    border: 1px solid #ddd;
    margin-bottom: 10px;
    margin-top: 0px;
    }
    /*.goods_table, .goods_modal{
        border: none;
    }
    .goods_table thead{
        border: 1px solid #ddd;
    }*/
    .sort_table tr:first-child a,
    .cat_modal tr:first-child a,
    .cat_table tr:first-child a{
        color: #5A8BFF;
        font-weight: normal;
    }
    .sort_table tr:first-child span,
    .cat_modal tr:first-child span,
    .cat_table tr:first-child span{
        margin-left: 74px;
    }
    .sort_table tr:last-child ul,
    .cat_modal tr:last-child ul,
    .cat_table tr:last-child ul{
        padding-left: 20px;
    }
    .sort_table tr:last-child div,
    .cat_modal tr:last-child div,
    .cat_table tr:last-child div{
        padding-left: 20px;
        line-height: 50px;
        font-size: 14px;
        border-bottom: 1px solid #eee;
        color: #333;
    }
    .sort_table tr:last-child div span,
    .cat_modal tr:last-child div span,
    .cat_table tr:last-child div span{
        padding:4px 10px;
        border-radius: 20px;
        font-size: 14px;
        color: #666;
    }
    .sort_table tr:last-child div span+span,
    .cat_modal tr:last-child div span+span,
    .cat_table tr:last-child div span+span{
        margin-left: 15px;
    }
    .sort_table tr:last-child ul.cat_ul,
    .cat_modal tr:last-child ul.cat_ul,
    .cat_table tr:last-child ul.cat_ul{
        width: 100%;
        padding-bottom: 16px;
    }
    .sort_table tr:last-child ul li,
    .cat_modal tr:last-child ul li,
    .cat_table tr:last-child ul li{
        float: left;
        padding:4px 10px;
        border-radius: 20px;
        line-height:20px;
        margin: 16px 10px 0 0;
        color: #666;
    }
    .sort_table tr:last-child ul li:last-child,
    .cat_modal tr:last-child ul li:last-child,
    .cat_table tr:last-child ul li:last-child{
        margin-right: 0px;
    }
    .first_cat{
        border: 1px solid #b9d2ff;
        background-color: #f0f5ff;
    }
    .second_cat{
        border: 1px solid #ffe2b8;
        background-color: #fffaf2;
    }
    .third_cat{
        border: 1px solid #ffc0cc;
        background-color: #fff6f8;
    }
    .cat_table{
        display: none;
    }
    .cost_price{
        position: relative;
    }
    .no_category{
    display: flex;
    justify-content:center;
    align-items:center;
    width: 100%;
    height: 100%;
    }
    .no_category p{
        text-align: center;
        color:#999;
        margin: 14px 0;
    }
    .whole_line .goods_table,.whole_line .goods_modal {
    margin-left: 0;
    }
    .quanxian_info{
        float: left;
        height:45px;
        line-height: 50px;
    }
    .quanxian_info input[type='checkbox']{
        top:1px;
        margin-right:5px
    }
    .quanxian_info text{
        margin-left: 10px;
        color: #999;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span style="color: #666;">分销 / </span><span style="color: #666;">返利策略配置</span>
</div>
<form  method="post" id="form2">
    {{ csrf_field()!}
    <div class="reserve-container">
        <div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>
        <div class="rebate_ul">
            <ul class="clearfix">
                <li><a href="/admin/market/distribution/strategy/list?top_index=4">全部策略</a></li>
                <li><a href="/admin/market/distribution/strategy/list?top_index=4&nav=1">进行中</a></li>
                <li><a href="/admin/market/distribution/strategy/list?top_index=4&nav=2">未开始</a></li>
                <li><a href="/admin/market/distribution/strategy/list?top_index=4&nav=3">已过期</a></li>
                <li><a href="/admin/market/distribution/strategy/list?top_index=4&nav=4">已停用</a></li>
                <li class="rebate_active"><a href="##">${title!}</a></li>
            </ul>
        </div>
        <div class="rebate_detail">
            <ul>
                <li class="detail_lis clearfix">
                    <input hidden name="id" value="${strategy->id!}">
                    <div class="item_title"><em>*</em> 返利策略名称：</div>
                    <div class="distri_switch">
                        <input type="text" name="strategy_name" value="${strategy->strategy_name!}" placeholder="请输入返利策略的名称" />
                    </div>
                </li>
                <li class="detail_lis clearfix">
                    <div class="item_title" ><em>*</em> 返利策略优先级：</div>
                    <div class="distri_judge">
                        <input type="text" name="strategy_level" value="${strategy->strategy_level!}" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="请输入返利策略优先级" />
                    </div>
                    <span style="color:#999;"></span>
                </li>
                <li class="clearfix" style="display: block;">
                    <div class="item_title" style="height: 28px;"></div>
                    <div class="switch_tips" style="font-size:14px">
                        当一个商品被添加到多个策略时，执行优先级最高的，可填写1到100间的整数。允许优先级重复，若重复则返利商品执行最新创建的返利策略。
                    </div>
                </li>
                <li class="detail_lis clearfix" style="display: block;">
                    <div class="item_title" ><em>*</em> 有效期：</div>
                    <div class="distri_rank">
                        <input type="text" name="start_time" id="startDate" value="${strategy->start_time!}" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" />
                        至&nbsp;
                        <input type="text" name="end_time" id="endDate" value="${strategy->end_time!}" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" />
                    </div>
                </li>
                <li class="detail_lis clearfix" style="display:block">
                    <div class="item_title"><em>*</em>分销员自购返利：</div>
                    <div class="Purchase_switch">
                        <input type="radio" name="self_purchase" value="1" <#if ($strategy->self_purchase == 1) checked </#if>>开启
                        <input type="radio" name="self_purchase" value="0" <#if ($strategy->self_purchase == 0) checked </#if>>关闭
                        <span class="switch_tips" style="font-size:14px;float:none;margin-left:10px;">
                        开启后，分销员购买商品也会获得返利，返利比例为分销员当前等级的直接邀请返利比例。
                        </span>
                    </div>
                </li>
                <li class="clearfix" style="display: block;">
                    <div class="item_title" style="height: 28px;"></div>
                    <div class="switch_tips" style="font-size:14px">
                        注：当自购返利开关开启，若下单人是分销员，则该下单人的间接邀请人不会获得返利，其直接邀请人可获得返利，返利比例为直接邀请人所在等级的间接邀请返利比例
                    </div>
                </li>
                <li class="detail_lis clearfix cost_price" style="display:block">
                    <div class="item_title" class="">
                        <img src="http://${image_domain!}/image/admin/system_icon.png" alt="" class="cost_price_image">
                        <div class="float-layer_cost" style="display: none;">
                            <div class="float-layer-i"></div>
                            <div class="float-layer-text">
                                <p>成本价保护：</p>
                                <p>当单件商品实付金额-成本价大于0时，按分销比例分配差额</p>
                                <p>当单件商品实付金额-成本价小于等于0时，返利为0</p>
                                <p>注：</p>
                                <p>未设置成本价的商品无效</p>
                            </div>
                        </div>
                        成本价保护：
                    </div>
                    <div class="Purchase_switch">
                        <input type="radio" name="cost_protection" value="1" <#if ($strategy->cost_protection == 1) checked </#if>>开启
                        <input type="radio" name="cost_protection" value="0" <#if ($strategy->cost_protection == 0) checked </#if>>关闭
                    </div>
                </li>
                <li class="clearfix" style="display: block;">
                    <div class="item_title"><em>*</em> 返利佣金比例：</div>
                    <div class="rebeat_percent_list">
                        <!-- <input type="text" name="fanli_ratio" value="${strategy->fanli_ratio!}" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')" placeholder="请输入返利比例" />% -->
                        <table class="distributer_table" width="900px">
                            <thead>
                                <tr>
                                    <th width="10%">等级</th>
                                    <th width="30%">等级名称</th>
                                    <th width="65%">
                                        返利佣金比例
                                        <i class="item-image"><img src="http://${image_domain!}/image/admin/analysis_tishi.png" alt="">
                                            <div class="float-layer" style="display: none;">
                                                <div class="float-layer-i"></div>
                                                <div class="float-layer-text">
                                                    <p>
                                                        直接邀请返利比例：分销员成功推广后获取的佣金
                                                    </p>
                                                    <p>
                                                        间接邀请返利比例：B是A发展的分销员，B成功推广后，A可获得邀请奖励佣金
                                                    </p>
                                                </div>
                                            </div>
                                        </i>
                                    </th>
                                </tr>
                            </thead>
                            <tbody class="tbody">
                                <tr>
                                    <td>一级</td>
                                    <td class="grade"><input type="text" value="${distributor_level[1]->level_name!}(已启用)" disabled></td>
                                    <td>
                                        <div style="width:50%">
                                            <p>直接邀请返利比例<input type="text" name="fanli_ratio" value="${strategy->fanli_ratio ?? 0!}" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')" />%</p>
                                            <!-- 间接邀请返利比例 -->
                                            <p>间接邀请返利比例<input  type="text" name="rebate_ratio" value="${strategy->rebate_ratio ?? 0!}" class="fanli_input" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')">%</p>
                                        </div>
                                        <div style="width:45%" class="td_r_div">
                                            <span></span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>二级</td>
                                    <td class="grade"><input type="text" value="${distributor_level[2]->level_name!}<#if ($distributor_level[2]->level_status == 1)(已启用)</#if>" disabled></td>
                                    <td>
                                        <div style="width:50%">
                                            <p>直接邀请返利比例<input type="text" name="fanli_ratio_2" class="fanli_input" value="${strategy->fanli_ratio_2!}" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')" />%</p>
                                            <p>间接邀请返利比例<input  type="text" name="rebate_ratio_2" value="${strategy->rebate_ratio_2!}" class="fanli_input" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')">%</p>
                                        </div>
                                        <div style="width:45%" class="td_r_div">
                                            <span></span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>三级</td>
                                    <td class="grade"><input type="text" value="${distributor_level[3]->level_name!}<#if ($distributor_level[3]->level_status == 1)(已启用)</#if>" disabled></td>
                                    <td>
                                        <div style="width:50%">
                                            <p>直接邀请返利比例<input type="text" name="fanli_ratio_3" class="fanli_input" value="${strategy->fanli_ratio_3!}" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')" />%</p>
                                            <p>间接邀请返利比例<input  type="text" name="rebate_ratio_3" value="${strategy->rebate_ratio_3!}" class="fanli_input" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')">%</p>
                                        </div>
                                        <div style="width:45%" class="td_r_div">
                                            <span></span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>四级</td>
                                    <td class="grade"><input type="text" value="${distributor_level[4]->level_name!}<#if ($distributor_level[4]->level_status == 1)(已启用)</#if>" disabled></td>
                                    <td>
                                        <div style="width:50%">
                                            <p>直接邀请返利比例<input type="text" name="fanli_ratio_4" class="fanli_input" value="${strategy->fanli_ratio_4!}" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')" />%</p>
                                            <p>间接邀请返利比例<input  type="text" name="rebate_ratio_4" value="${strategy->rebate_ratio_4!}" class="fanli_input" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')">%</p>
                                        </div>
                                        <div style="width:45%" class="td_r_div">
                                            <span></span>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>五级</td>
                                    <td class="grade"><input type="text" value="${distributor_level[5]->level_name!}<#if ($distributor_level[5]->level_status == 1)(已启用)</#if>" disabled></td>
                                    <td>
                                        <div style="width:50%">
                                            <p>直接邀请返利比例<input type="text" name="fanli_ratio_5" class="fanli_input" value="${strategy->fanli_ratio_5!}" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')" />%</p>
                                            <p>间接邀请返利比例<input  type="text" name="rebate_ratio_5" value="${strategy->rebate_ratio_5!}" class="fanli_input" onkeyup="value=value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3');value=value.replace(/[^\d.]/g,'')">%</p>
                                        </div>
                                        <div style="width:45%" class="td_r_div">
                                            <span></span>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </li>
                <li class="clearfix" style="display: block;">
                    <div class="item_title" style="height: 45px;"></div>
                    <div class="switch_tips" style="font-size:14px">
                        该策略配置商品都按当前比例结算佣金，佣金值=商品实际支付金额*佣金比例，例如，分销商品价格100元，返利佣金比例20%，那么用户购买一件分销商品，邀请该用户分销员获得20元佣金。
                    </div>
                    <div class="switch_tips" style="font-size:14px">
                        订单支付完成佣金返利到分销员分销中心的余额账户中，但是该佣金为待返利状态，订单完成后，佣金返利，分销员可以直接使用该佣金购物。限制小数点后一位数字。
                    </div>
                </li>
                <li class="clearfix">
                    <div class="item_title" style="height: 45px;">分销员权限：</div>
                    <div class="quanxian_info">
                        <input type="checkbox" name="send_coupon" <#if  ($strategy->send_coupon == 1) checked </#if> class="">推广赠送优惠券
                        <text>允许分销员分销商品时赠送优惠券</text>
                    </div>
                </li>
                <li class="whole_line" style="display: block;">
                    <div class="item_title1">分销商品</div>
                    <div class="up_img clearfix changge_goods">
                        <input  type="hidden" name="recommend_goods_id" value="${strategy->recommend_goods_id!}">
                        <input  type="hidden" name="recommend_cat_id" value="${strategy->recommend_cat_id!}">
                        <input  type="hidden" name="sort_ids" value="${strategy->recommend_sort_id!}">
                        <input type="radio" name="recommend_type" id="alls" value="0" <#if ($strategy->recommend_type == 0) checked </#if>>  全部商品
                        <input type="radio" name="recommend_type" id="somes" value="1" <#if ($strategy->recommend_type == 1) checked </#if>>  指定商品
                    </div>
                    <div class="choosed_info clearfix">
                        <div class="choose_goods">
                            <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />选择商品
                        </div>
                    </div>
                    <div class="goods_area">
                    <#if ($strategy->recommend_goods_id)
                        <table class="goods_table" goods_array="${strategy->recommend_goods_id!}">
                            <thead>
                                <tr>
                                <th width="50%">商品名称</th>
                                <th width="15%">价格</th>
                                <th width="15%">库存</th>
                                <th width="20%">操作</th>
                            </tr>
                            </thead>
                            <tbody class="tbody">
                            <#list ($strategy->goods_array as $item)
                                <tr>
                                    <td width="50%">
                                        <div class="goods_info clearfix">
                                            <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                            <div class="goods_name">
                                                ${item->goods_name!}
                                            </div>
                                        </div>
                                    </td>
                                    <td width="15%">${item->shop_price!}</td>
                                    <td width="15%">${item->goods_number!}</td>
                                    {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                    <td width="20%"><a href="javascript:void(0)" goods_id="${item->goods_id!}" class="del">删除</a></td>
                                </tr>
                            </#list>
                            </tbody>
                        </table>

                    <#else>
                        <table class="goods_modal" width="380px">
                            <thead>
                                <tr style="width:100%;">
                                    <th width="50%">商品名称</th>
                                    <th width="15%">价格</th>
                                    <th width="15%">库存</th>
                                    <th width="20%">操作</th>
                                </tr>
                            </thead>
                            <tbody class="tbody"></tbody>
                        </table>
                    </#if>
                    </div>
                    
                    <div class="choosed_info clearfix" style="margin-bottom:10px;">
                        <div class="add_sort">
                            <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="">
                            添加商家分类
                        </div>
                    </div>
                    <table class="sort_table" width="380px" sort_array="${strategy->recommend_sort_id!}">
                        <tr>
                            <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_sort_cls">编辑</a><a href="javascript:;" class="del_sort_cat">删除</a></div></th>
                            <!-- <th width="30%" >操作</th> -->
                        </tr>
                    </table>
                    <div class="choosed_info clearfix" style="margin-bottom:10px;">
                        <div class="add_cate">
                            <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />添加平台分类
                        </div>
                    </div>
                   
                        <table class="cat_table" width="380px" cat_array="${strategy->recommend_cat_id!}">
                            <tr>
                                <th width="100%"><span>平台分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px" class="edit_cls">编辑</a><a href="javascript:;" class="del_cat">删除</a></div></th>
                                <!-- <th width="30%" >操作</th> -->
                            </tr>
                        </table>
                    <!-- <#if ($strategy->recommend_cat_id)
                    <#else>
                        <table class="cat_modal" width="500px">
                            <tr>
                                <th width="100%"><span>商品分类</span><div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px">编辑</a><a href="javascript:;">删除</a></div></th>
                            </tr>
                        </table>
                    </#if> -->
                </li>
            </ul>
        </div>
    </div>
    <table  class="goods_modal_clone hide">
        <tr>
            <td></td>
            <td></td>
            <td>上架</td>
            <td><a href="javascript:void(0)"  class="del">删除</a></td>
        </tr>
    </table>
    <!-- <table class="cat_modal_clone hide">
        <ul>
            <li></li>
        </ul>
    </table> -->
</form>
<div id="set-category">
        <#if (!$cat_list)
            <div class="no_category">
                <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无分类</p>
                </div>    
            </div>
        <#else>
        <ul>
            <#if ($cat_list)
                <#list ($cat_list as $item)
                    <li class="cate_li">
                        <div class="first_cate">
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" />
                            <span>
                            <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                            <label for="cat_${item->cat_id!}" goods_num="${item->goods_num!}">${item->cat_name!}(${item->goods_num!})</label>
                        </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                                <span>
                                    <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                    <label for="cat_${sub_item->cat_id!}" goods_num="${sub_item->goods_num!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                                </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                                <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                                <label for="cat_${th_item->cat_id!}" goods_num="${th_item->goods_num!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                            </span>
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </#list>
                            </div>
                        </#if>
                    </li>
                </#list>
            </#if>
        </ul>
        </#if>
</div>
<div class="pages_bottom">
    <a href="##" class="btn_strategy_save">保存</a>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    var hasSaved = true;
</script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="{{asset('js/admin/select_sort.js')!}?v=1.0.2"></script>
<script type="text/javascript">
    var cat_id = "${strategy->recommend_cat_id!}";
    var goods_id = '${strategy->recommend_goods_id!}';

    // var cat_list = "{{json_encode($cat_list)!}";
    // var arrayCat = JSON.parse(cat_list.replace(/&quot;/g,'"'))
    // console.log(arrayCat);

    $("body").on('click','.del',function(){
        var del_goods_id = $(this).attr('goods_id');
        var goods = $('input[name="recommend_goods_id"]').val();
        if(isNaN(goods)) {
            var goods_array = goods.split(',');
            for (var i = 0; i < goods_array.length; i++) {
                if (goods_array[i] == del_goods_id) {
                    goods_array.splice(i, 1);
                    break;
                }
            }
            $('input[name="recommend_goods_id"]').val(goods_array.join());
        }
        else{
            $('input[name="recommend_goods_id"]').val('');
        }
        $(this).parent().parent().remove();
        if (goods_id) {
            if($('.goods_table tr').length == 1){
                $('.goods_table').hide();
            }
        } else {
            if($('.goods_modal tr').length == 1){
                $('.goods_modal').hide();
            }
        }
        check_goods_area_height()
    });
    // $("body").on('click','.del_cat',function(){
    //     var del_cat_id = $(this).attr('cat_id');
    //     var cat = $('input[name="recommend_cat_id"]').val();
    //     if(isNaN(cat)) {
    //         var cat_array = cat.split(',');
    //         for (var i = 0; i < cat_array.length; i++) {
    //             if (cat_array[i] == del_cat_id) {
    //                 cat_array.splice(i, 1);
    //                 break;
    //             }
    //         }
    //         console.log(cat_array);
    //         $('input[name="recommend_cat_id"]').val(cat_array.join());
    //     }
    //     else{
    //         $('input[name="recommend_cat_id"]').val('');
    //     }
    //     $(this).parent().parent().remove();
    //     $("#cat_"+del_cat_id).prop('checked',false);
    //     if (cat_id) {
    //         if($('.cat_table tr').length == 1){
    //             $('.cat_table').hide();
    //         }
    //     }else{
    //         if ($('.cat_modal tr').length == 1) {
    //             $('.cat_modal').hide();
    //         }
    //     }

    // });
    $('[name^="fanli_ratio"],[name^="rebate_ratio"]').on('keyup',function(){
        var inputParent = $(this).parent().parent();
        var fanli_ratio = inputParent.find('[name^="fanli_ratio"]').val() ? inputParent.find('[name^="fanli_ratio"]').val() : 0;
        var rebate_ratio = inputParent.find('[name^="rebate_ratio"]').val() ? inputParent.find('[name^="rebate_ratio"]').val() : 0;
        var span = inputParent.next().children('span');
        if(parseFloat(fanli_ratio) > parseFloat(rebate_ratio)){
            span.text('当前等级分销员可获返利金额为下单商品金额的'+rebate_ratio+'%-'+fanli_ratio+'%')
        } else {
            span.text('当前等级分销员可获返利金额为下单商品金额的'+fanli_ratio+'%-'+rebate_ratio+'%')
        }
        if(fanli_ratio >= 100 || rebate_ratio >= 100){
            span.addClass('red')
        } else {
            span.removeClass('red')
        }
    })
    $("body").on('click','.del_cat',function(){
        $('input[name="recommend_cat_id"]').val('');
        $('.cat_table').find('tr').eq(1).remove();
        $('#set-category ul input[type="checkbox"]:checked').each(function(){
            $(this).prop('checked',false);
        })     
        $('.cat_table').hide();
    })

    $('[name="strategy_level"]').blur(function () {
        if($(this).val() > 100 || $(this).val() <= 0){
            util.mobile_alert('优先级只能填写1-100间的整数');
            $(this).val( '' );
            $(this).focus();
        }
    });
    $('[name^="fanli_ratio"]').blur(function () {
        if($(this).val() == ''){
            util.mobile_alert('请填写返利比例');
            $(this).val( '' );
            $(this).focus();
        }
        if(parseFloat($(this).val()) >= 50 || parseFloat($(this).val()) <= 0){
            util.mobile_alert('返利比例必须大于0%小于50%');
            $(this).val( '' );
            $(this).focus();
        }
    });
    $('[name^="rebate_ratio"]').blur(function () {
        if($(this).val() == ''){
            util.mobile_alert('请填写返利比例');
            $(this).val( '' );
            $(this).focus();
        }
        if(parseFloat($(this).val()) >= 50 || parseFloat($(this).val()) <= 0){
            util.mobile_alert('返利比例必须大于0%小于50%');
            $(this).val( '' );
            $(this).focus();
        }
    });

    //获取当前时间  格式为 ： 2018-08-30 10:25:18
    function CurentTime() {
        var now = new Date();

        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();            //日

        var hh = now.getHours();            //时
        var mm = now.getMinutes();          //分
        var ss = now.getSeconds();           //秒

        var clock = year + "-";

        if(month < 10)
            clock += "0";

        clock += month + "-";

        if(day < 10)
            clock += "0";

        clock += day + " ";

        if(hh < 10)
            clock += "0";

        clock += hh + ":";
        if (mm < 10) clock += '0';
        clock += mm + ":";

        if (ss < 10) clock += '0';
        clock += ss;
        return(clock);
    }

    // console.log(CurentTime());
    $('#endDate').blur(function () {
       // console.log($(this).val());
       if($(this).val() && $(this).val() <= CurentTime()){
           util.mobile_alert('结束时间不能小于当前当前时间');
           $(this).val('');
       }
    });

    $('.btn_strategy_save').click(function () {
        if($('[name="strategy_name"]').val() == ''){
            util.mobile_alert('请填写返利策略的名称');
            $('[name="strategy_name"]').focus();
            return;
        }else{
            if($('[name="strategy_name"]').val().length > 10){
                util.mobile_alert('名称最多只能填写10个字');
                $('[name="strategy_name"]').val( $('[name="strategy_name"]').val().substring(0,10) );
                return;
            }
        }
        if($('[name="strategy_level"]').val() == ''){
            util.mobile_alert('请填写返利策略优先级');
            $('[name="strategy_level"]').focus();
            return;
        }else{
            if($('[name="strategy_level"]').val() > 100 || $('[name="strategy_level"]').val() <= 0){
                util.mobile_alert('优先级只能填写1-100间的整数');
                $('[name="strategy_level"]').val( '' );
                $('[name="strategy_level"]').focus();
                return;
            }
        }
        if($('#startDate').val() == ''){
            util.mobile_alert('请填写有效期');
            $('#startDate').focus();
            return;
        }
        if($('#endDate').val() == ''){
            util.mobile_alert('请填写有效期');
            $('#endDate').focus();
            return;
        }
        if ($('#startDate').val() > $('#endDate').val()) {
            util.mobile_alert('结束时间应大于开始时间接近');
            $('#endDate').focus();
            return;
        }
        if($('[name="fanli_ratio"]').val() == ''){
            util.mobile_alert('请填写返利比例');
            $('[name="fanli_ratio"]').focus();
            return;
        }else{
            if(parseFloat($('[name="fanli_ratio"]').val()) >= 50 || parseFloat($('[name="fanli_ratio"]').val()) <= 0){
                util.mobile_alert('返利比例必须大于0%小于50%');
                $('[name="fanli_ratio"]').val( '' );
                $('[name="fanli_ratio"]').focus();
                return;
            }
        }
        if($("#alls").is(":checked")){
            $("input[name='recommend_goods_id']").val('');
            $("input[name='recommend_cat_id']").val('');
        }
        // console.log($("#form2").serialize());
        util.ajax_json('/admin/market/distribution/strategy/save',function (response) {
            console.log(response)
            if (response.error == 0) {
                hasSaved = true;
                layer.ready(function () {
                    layer.msg('保存成功', {time: 1000},function () {
                        window.location = '/admin/market/distribution/strategy/list?top_index=4';
                    });
                });
            } else {
                util.mobile_alert('保存失败');
            }
        },$("#form2").serialize());
    });

    var disabled = "${strategy->strategy_name!}";
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    util.inputChange();
    util.radioChange('goods_all');
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
    $('.item-image').mouseenter(function(){
        $(this).find('.float-layer').show()
        console.log(1)
    }).mouseleave(function(){
        $(this).find('.float-layer').hide()
    })
    $('.cost_price_image').mouseenter(function(){
        $(this).next('.float-layer_cost').show()
        console.log(1)
    }).mouseleave(function(){
        $(this).next('.float-layer_cost').hide()
    })
    function check_goods_area_height(){
        let goods_modal = $('.goods_modal').outerHeight();
        let goods_table = $('.goods_table').outerHeight();
        if( goods_table > 300 || goods_modal > 300){
            $('.goods_area').css({
                'height': '300px',
                'overflow-y': 'scroll',
            })
        } else {
            $('.goods_area').css({
                'height': 'auto',
                'overflow-y': 'auto',
            })
        }
    }
    function onShow(){
        var inputParents = $('[name^="fanli_ratio"]').parent().parent();
        inputParents.each(function(){
            let fanli_ratio = $(this).find('[name^="fanli_ratio"]').val();
            let rebate_ratio = $(this).find('[name^="rebate_ratio"]').val();
            let span = $(this).next().children('span')
            if(rebate_ratio && fanli_ratio){
                if(parseFloat(fanli_ratio) > parseFloat(rebate_ratio)){
                    span.text('当前等级分销员可获返利金额为下单商品金额的'+rebate_ratio+'%-'+fanli_ratio+'%')
                } else {
                    span.text('当前等级分销员可获返利金额为下单商品金额的'+fanli_ratio+'%-'+rebate_ratio+'%')
                }
                if(fanli_ratio >= 100 || rebate_ratio >= 100){
                    span.addClass('red')
                } else {
                    span.removeClass('red')
                }
            }
        });
    }
    onShow()
</script>
<script type="text/javascript" src="{{asset('js/admin/distribution_config.js')!}?v=1.3.3"></script>
<script type="text/javascript">
    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
