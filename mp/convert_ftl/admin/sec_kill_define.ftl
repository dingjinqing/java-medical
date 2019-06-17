<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sec_kill.css?v=1.0.5" type="text/css" />
<link rel="stylesheet" href="/css/admin/common_share.css?v=1.2.1" type="text/css" />
<style type="text/css">
    body{
        padding-bottom: 40px;
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
    .sec_kill_footer button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .sec_kill_footer button:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .prompt{
        color: #999;
        margin: 8px 0 10px;
    }
    tr td{
        padding: 8px 0px;
    }
    #card_id{
        height: 30px;
        border: 1px solid #ccc;
        padding-left: 20px;
        width: 160px;
    }
    .card_select_box a {
        display: inline-block;
        color: #5a8bff;
        margin-left: 8px;
    }
    .card-info{
        padding: 10px 10px 0 10px;
        margin-top: 10px;
        background: #f5f5f5;
    }
    .card_span {
        position: relative;
        background-color: white;
        padding: 0 3px;
        height: 22px;
        line-height: 22px;
        text-align: center;
        display: inline-block;
        margin-left: 10px;
        border: 1px solid #ccc;
        margin-bottom:10px;
    }
    .card-delete {
        position: absolute;
        right: -10px;
        top: -7px;
        cursor: pointer;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span style="color: #666;">秒杀</span>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/seckill/list?nav=0">全部秒杀活动</a>
                </li>
                <li>
                    <a href="/admin/market/seckill/list?nav=1">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/seckill/list?nav=2">未开始</a>
                </li>
                <li>
                    <a href="/admin/market/seckill/list?nav=3">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/seckill/list?nav=4">已停用</a>
                </li>
                <li class="active">
                    <#if ($id)
                        <a href="/admin/market/seckill/add?top_index=4&id=${id!}">编辑秒杀活动</a>
                    <#else>
                        <a href="/admin/market/seckill/add?top_index=4">添加秒杀活动</a>
                    </#if>
                </li>
            </ul>
        </div>
        <div class="return-sec-kill-box" style="padding-top: 20px;">
            <form name="formData" id="form1" method="post" action="/admin/market/seckill/add">
                {{ csrf_field()!}
                <input type="hidden" name="id" value="${_GET['id']!}"/>
                <input type="hidden" name="is_can_edit" value="${is_can_edit!}"/>
                <table class="tb-sec-kill">
                    <tbody>
                    <tr style="height: 50px;">
                        <td style="width: 100px ;">
                            <span class="tb-full-left" ><strong>*</strong>活动名称：</span>
                        </td>
                        <td>
                            <input type="text" class="name" placeholder="请填写活动名称" name="name" value="${basicData->name!}" maxlength="20">
                            <span class="prompt">只作为商家记录使用，用户不会看到这个名称</span>
                        </td>

                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>活动商品：</span>
                        </td>
                        <td>
                            <#if (empty($basicData->goods_id))
                                <input type="button" value="+ 选择商品" id="sel-goods-btn" class="choose_goods" name="choose_goods">
                            </#if>
                            <input type="hidden" name="goods_id" value="${basicData->goods_id!}">
                            <span class="goods-namess" <#if  (empty($goodsInfo->goods_name)) style="display: none;" </#if>>${goodsInfo->goods_name!}</span>
                            <#if (empty($basicData->goods_id)  || $basicData->start_time>date("Y-m-d H:i:s"))
                                <span class="goods-btn-modify" <#if ($basicData->start_time<=date("Y-m-d H:i:s")) style="display: none;" </#if>>修改</span>
                            </#if>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>有效期：</span>
                        </td>
                        <td>
                                    <span>
                                        <#if (!empty($basicData->start_time) && $basicData->start_time <= date("Y-m-d H:i:s"))
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
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td style="width: 100px">
                            <span class="tb-full-left"><strong>*</strong>限购数量：</span>
                        </td>
                        {{--<td style="width: 100px">--!}
                            {{--<span class="tb-full-left">--!}
                                {{--<strong>*</strong>每个用户最多可购买秒杀商品数量：--!}
                            {{--</span>--!}
                        {{--</td>--!}
                        <td>
                            <input type="number" class="limit_amount" placeholder="" name="limit_amount" value="${basicData->limit_amount or 1!}"
                               <#if (!empty($basicData->limit_amount)  && $basicData->start_time <= date("Y-m-d H:i:s")) readonly </#if>; min="0"
                            />
                            <span class="prompt">单个用户对秒杀商品限购的数量，请填写阿拉伯数字，填写0表示不限制</span>
                        </td>
                    </tr>
                    <tr style="height: 50px;">
                        <td colspan="2">
                            
                            <span class="tb-full-left2" style="display: inline"><strong>*</strong>下单后&nbsp;&nbsp;&nbsp;&nbsp;<input type="number" class="limit_paytime" placeholder="" name="limit_paytime" value="${basicData->limit_paytime or 5!}"   <#if (!empty($basicData->limit_paytime)  && $basicData->start_time <= date("Y-m-d H:i:s")) readonly </#if>;  min="5">&nbsp;&nbsp;
                            分钟内未支付，则释放库存</span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <span class="prompt" style="margin-left: 30px">&nbsp;为确保买家能够顺利完成支付,请设置不小于5分钟的等待时间,因微信支付结果查询占用部分时间，造成订单状态变更不及时,故会存在极小部分订单自动退款给买家</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="tb-full-left1"><strong>*</strong>秒杀价格设置：</span>
                        </td>
                    </tr>
                    <tr style="text-align: center;" id="product-info">
                        <td colspan="2">
                            <table>
                                <tr>
                                    <th style="width: 300px">商品名称/规格</th>
                                    <th style="width: 180px">原价（元）</th>
                                    <th style="width: 180px">秒杀价（元）</th>
                                    <th style="width: 180px;">原库存</th>
                                    <th style="width: 180px">秒杀库存</th>
                                    <th style="width: 180px">剩余秒杀库存</th>
                                </tr>
                                <tr style="display: none;">
                                    <input type="hidden" name="product_id[]" value="" />
                                    <input type="hidden" name="original_price[]" value="" />
                                    <td class="goods_name" style="width: 180px">

                                    </td>
                                    <td class="original_price" style="width: 180px">

                                    </td>
                                    <td class="sec_kill_price" style="width: 180px">
                                        <input type="number"  name="sec_kill_price[]" value="" style="width:120px" class="skk"/>
                                    </td>
                                    <td class="original_stock" style="width: 180px">

                                    </td>
                                    <td class="total_stock" style="width: 180px">
                                        <input type="number" name="total_stock[]" value="" style="width:120px" id="s_stock"  onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
                                    </td>
                                    <td class="remain_stock" style="width: 180px">
                                        <input type="number" name="stock[]" id="stock" value="" readonly/>
                                    </td>
                                </tr>
                                <#list ($secGoodsInfo as $key => $item)
                                    <tr class="set_line_value">
                                        <input type="hidden" name="product_id[]" value="${item->product_id!}" />
                                        <input type="hidden" name="original_price[]" value="${originPrice[$item->product_id]!}" />
                                        <td class="goods_name">
                                            <#if (empty($goodsSpec[$key]->prd_desc))
                                                ${goodsInfo->goods_name!}
                                            <#else>
                                                ${goodsSpec[$key]->prd_desc!}
                                            </#if>
                                        </td>
                                        <td class="original_price">
                                            ${originPrice[$item->product_id]!}
                                        </td>
                                        <td class="sec_kill_price">
                                            <input type="number"  name="sec_kill_price[]" value="${item->sec_kill_price!}" <#if ($basicData->start_time < date("Y-m-d H:i:s")) readonly disabled </#if>/>
                                        </td>
                                        <td class="original_price">
                                            ${originStock[$item->product_id]!}
                                        </td>
                                        <td class=total_stock">
                                            <input type="number" name="total_stock[]" id="s_stock" value="${item->total_stock!}" <#if ($basicData->start_time <= date("Y-m-d H:i:s")) readonly disabled </#if>/>
                                        </td>
                                        <td class="remain_stock" >
                                            <input type="number" name="stock[]" id="stock" <#if  ($originStock[$item->product_id]<$item->stock) value="${originStock[$item->product_id]!}" <#else> value="${item->stock!}" </#if> readonly disabled/>
                                        </td>
                                    </tr>
                                </#list>
                            </table>
                        </td>
                    </tr>
                    <tr <#if (!empty($basicData->limit_amount)  && $basicData->start_time <= date("Y-m-d H:i:s")) hidden </#if>>
                        <td>
                            <span class="tb-full-left"><strong>&nbsp;</strong>批量设置：</span>
                        </td>
                        <td>
                            <span>
                                <a href="javascript:void(0)" class="set_line" onclick="util.set_line_value('sec_kill_price',1)" >秒杀价</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="javascript:void(0)" class="set_line" onclick="set_value()" >秒杀库存</a>
                            </span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <span class="tb-full-left1"><strong>*</strong>运费设置：</span>
                            <label><input type="radio" name="free_freight" value="1" <#if  (empty($basicData)|| $basicData->free_freight == 1 ) checked </#if>  <#if  (!empty($basicData) && $basicData->start_time <= date("Y-m-d H:i:s"))disabled="disabled" </#if>/> 免运费</label>
                            <label><input type="radio" name="free_freight"  value="0" <#if  ($basicData->free_freight == 0) checked </#if>  <#if  (!empty($basicData) && $basicData->start_time <= date("Y-m-d H:i:s"))disabled="disabled" </#if>/> 使用原商品运费模板</label>
                        </td>
                    </tr>
                    <tr class="card_goods">
                        <td style="vertical-align: top!important;">会员专享活动：</td>
                        <td style="padding-top:2px;"><input type="checkbox" name="is_card_exclusive" <#if ($basicData->card_id) checked </#if> style="padding:0;border:0;vertical-align: text-top;">
                            <span style="color:#000;line-height:30px;">用户持有会员卡才可以参与活动</span>
                            <input type="hidden" value="${goods_have_card_str!}" name="goods_have_card_str">
                            <input type="hidden" value="${goods_have_card_name!}" name="goods_have_card_name">
                            <div>
                                <div class="card_select_box">
                                    <select name="" id="card_id">
                                        <option value="0">请选择会员卡</option>
                                        <#list ($goods_cards as $gc)
                                            <option value="${gc->id!}">${gc->card_name!}</option>
                                        </#list>
                                    </select>
                                    <a href="javascript:void(0)" class="refresh-card">刷新</a>&nbsp;&nbsp;|
                                    <a href="/admin/user/member/create?top_index=5" target="_blank">新建会员卡 </a>&nbsp;&nbsp;|
                                    <a href="/admin/user/member/list?top_index=5" target="_blank">管理会员卡 </a>
                                </div>
                                <div class="card-info template-0" style="display: none">
                                    <div class="card-info-row">
                                        <span class="card-choose">已选：</span>
                                    </div>
                                </div>
                            </div> 
                        </td>
                    </tr>
                    <tr class="share_module" style="line-height: 33px;">
                        <td>店铺分享：</td>
                        <td>
                            <div class="fl" style="position: relative; ">
                                <input type="radio" name="share_action" value="1" <#if  (!$module_share || $module_share['share_action'] == 1) checked </#if>/> 默认样式
                                <a href="javascript:;" class="show_eg">查看示例
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/share/seckill_share.jpg"/>
                                    </div>
                                </a>
                                <a href="javascript:;" class="show_eg">下载海报
                                    <div class="hover_show">
                                        <img src="http://${image_domain!}/image/admin/share/seckill_pictorial.jpg"/>
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
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>

        </div>
        <div class="sec_kill_footer fix_footer">
            <button type="submit" onclick="if(secGroup.save() === false) return false;">保存</button>
        </div>
    </div>
</div>
<#include ('admin.preview_common')
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/common_share.js?v=1.1.1"></script>
<script>
    function set_value(){
        var value = $('.set_line_value').find(".total_stock").eq(0).find("input").val();
        $('.set_line_value').each(function (i,v) {
            $('.set_line_value').find(".total_stock").eq(i).find("input").val(value);
            $('.set_line_value').find('.remain_stock').eq(i).find('input').val(value)
        });
    }
    //失去焦点判断
    $('.limit_amount').blur(function(){
        var num = $('input[name = "limit_amount"]').val();
        if(!(/(^[1-9]\d*$)/.test(num)) && num != 0){
            util.mobile_alert('限购数量请输入正整数');
            return false;
        }
    });

    $(document).on("input propertychange blur",".skk",function () {
        if(!(/^\d+(\.\d{1,2})?$/ .test($(this).val())) && $(this).val() != 0){
            util.mobile_alert('秒杀价格请输入正数');
            $(this).val('');
        }
    })


    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    $(document).on("input propertychange blur","#s_stock",function () {
        if(!(/(^[1-9]\d*$)/.test($(this).val())) && $(this).val() != 0){
            util.mobile_alert('库存请输入正整数');
            $(this).val('');
        }
        // console.log($(this).parent().next().children());
        $(this).parent().next().children().val($(this).val());
    })
    {{--<#if  (!empty($basicData))--!}
        {{--$('input[name="is_default"]').click(function () {--!}
            {{--return false;--!}
        {{--});--!}
    {{--</#if>--!}
    //选择商品
    $('.choose_goods, .goods-btn-modify').on('click',function(){

        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center; padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px', '430px']
                , content: '/admin/frame/goods/select?is_check_single=1&is_need_onsale=0&is_tips=1' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var goods = $('.tb-sec-kill input[name="goods_id"]').val();
                    var body = layer.getChildFrame('body', index);
                    if(goods !='') {
                        if(isNaN(goods)) {
                            var goods_array = goods.split(',');
                            body.contents().find("tr").each(function(){
                                if($.inArray($(this).attr("goods_id"),goods_array)>-1){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                        else{
                            body.contents().find("tr").each(function(){
                                if($(this).attr("goods_id")==goods){
                                    $(this).attr('data-back','false').addClass('goods_tr_choose');
                                }
                            });
                        }
                    }
                }
                , yes: function (index, layero) {
                    //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    iframe.contents().find('tr[data-back="false"]').each(function(){
                        var goods_id = $(this).attr('goods_id');
                        var goods_name = $(this).find('.goods_name').text();
                        $(".tb-sec-kill input[name='goods_id']").val(goods_id);
                        $(".tb-sec-kill input[name='goods_id']").next().html(goods_name);
                        secGroup.getSpecGoodsInfo(goods_id, goods_name);
                        $('#sel-goods-btn').remove();
                        $('.goods-namess, .goods-btn-modify').show();
                        return true;
                    });
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    var secGroup = {
        //存储规格商品
        specProduct:{
            prd_id: [],
            prd_number: [],
            prd_price: []
        },
        //获得规格商品
        getSpecGoodsInfo: function (goods_id, goods_name) {
            util.ajax_json('/admin/market/seckill/list', function (response) {
                if (response.error == 0) {
                    secGroup.clearSpecProduct();
                    $('#product-info tr:gt(1)').remove();
                    var tr = '<tr class="set_line_value">' +$('#product-info tr:nth-of-type(2)').html()+ '</tr>';
                    $.each(response.content, function (index, item) {
                        $('#product-info table tbody').append(tr);
                        var last_tr = $('#product-info tr:nth-last-child(1)');
                        last_tr.find('.goods_name').text(item.prd_desc == '' ? goods_name : item.prd_desc);
                        last_tr.find('.original_price').text(item.prd_price);
                        last_tr.find('[name="product_id[]"]').val(item.prd_id);
                        last_tr.find('.original_stock').text(item.prd_number);
                        last_tr.find('[name="original_price[]"]').val(item.prd_price);
                        secGroup.specProduct.prd_id.push(item.prd_id);
                        secGroup.specProduct.prd_number.push(item.prd_number);
                        secGroup.specProduct.prd_price.push(item.prd_price);
                        last_tr.show();
                    });
                    console.log(secGroup.specProduct);
                } else {
                    util.mobile_alert(response.message);
                }
            }, {action: "get_goods_spec", goods_id: goods_id});
        },
        save: function (e) {
            //e.stopPropagation();
            if($('input[name="name"]').val() == '') {
                util.mobile_alert('请填写活动名称');
                return false;
            }
            if($('input[name="goods_id"]').val() == '') {
                util.mobile_alert('请选择商品');
                return false;
            }
            if($('input[name="start_time"]').val() == '' || $('input[name="end_time"]').val() == ''){
                util.mobile_alert('请填写有效期');
                return false;
            }
            if($('input[name="start_time"]').val() > $('input[name="end_time"]').val()){
                util.mobile_alert('开始时间不能大于结束时间');
                return false;
            }
            if($('input[name="limit_amount"]').val() == '') {
                util.mobile_alert('请填写秒杀限购数量');
                return false;
            }
            if($('input[name="limit_paytime"]').val() == '') {
                util.mobile_alert('请填写下单后保留时间');
                return false;
            }
            if(!(/(^[1-9]\d*$)/.test($('input[name = "limit_amount"]').val())) && $('input[name = "limit_amount"]').val() != 0 && ! /^\d+$/.test($('input[name = "limit_amount"]').val())){
                util.mobile_alert('限购数量请输入正整数');
                return false;
            }
            // if(parseInt($('input[name="limit_amount"]').val()) < 2) {
            //     util.mobile_alert('成团人数应大于2');
            //     return false;
            // }
            // if($('input[name="join_limit"]').val() == '' || parseInt($('input[name="join_limit"]').val()) < 0) {
            //     util.mobile_alert('请填写参团限制');
            //     return false;
            // }
            // if($('input[name="open_limit"]').val() == '' || parseInt($('input[name="open_limit"]').val()) < 0) {
            //     util.mobile_alert('请填写开团限制');
            //     return false;
            // }
            if($("input[name='is_card_exclusive']").is(":checked") && $('.card-info-row').find('.card_span').length <=0 ){
                util.mobile_alert('请选择专享会员卡');
                return false;
            }
            var is_can_edit = parseInt($('input[name="is_can_edit"]').val());
            if (is_can_edit) {
                var msg = '';
                $('#product-info input[name="sec_kill_price[]"]').each(function (index, item) {
                    if(index < 1) return true;
                    if($(item).val() == '' || parseFloat($(item).val()) < 0) {
                        msg = '秒杀价不能为空';
                        $(this).focus();
                        return false;
                    }
                    var prd_price = parseFloat(secGroup.specProduct.prd_price[index - 1]);
                    if(parseFloat($(item).val()) > prd_price) {
                        msg = '秒杀价不能大于原价' +prd_price;
                        $(this).focus();
                        return false;
                    }
                });
                var summ = 0;
                $(document).find('#product-info input[name="stock[]"]').each(function (index, item) {
                    if(index < 1) return true;
                    if (!($(item).val() == '' || parseInt($(item).val()) <= 0)) {
                        summ+=parseInt($(item).val());
                        var prd_number = parseInt(secGroup.specProduct.prd_number[index - 1])
                        if(parseInt($(item).val()) > prd_number) {
                            msg = '秒杀库存不能大于原库存' +prd_number;
                            $(this).focus();
                            return false;
                        }
                    }
                });
                console.log(summ);
                if(summ < 1) {
                    msg = '秒杀库存不能为空';
                    util.mobile_alert(msg);
                    $(this).focus();
                    return false;
                }

                if(msg != '') {
                    util.mobile_alert(msg);
                    return false;
                }
            }

            if (parseInt($('[name="share_action"]:checked').val()) == 2) {
                if ($('[name="share_doc"]').val() == '') {
                    util.mobile_alert('请选择文案');
                    return false;
                }
                if (parseInt($('[name="share_img_action"]:checked').val()) == 2 &&
                    $('[name="share_img"]').val() == '') {
                    util.mobile_alert('请上传图片');
                    return false;
                }
            }
            //提交
            util.ajax_json('/admin/market/seckill/add', function (response) {
                if (response.error == 0) {
                    hasSaved = true;
                    layer.ready(function () {
                        layer.msg('保存成功', {time: 2000},function () {
                            window.location = '/admin/market/seckill/list?nav=0';
                        });
                    });
                } else {
                    util.mobile_alert(response.message);
                }
            }, $('#form1').serialize());
            return false;
        },
        clearSpecProduct: function () {
            secGroup.specProduct.prd_id = [];
            secGroup.specProduct.prd_number = [];
            secGroup.specProduct.prd_price = [];
        }
    };
    var hasSaved = true;
    util.inputChange();
    util.checkboxChange();
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
    //编辑页初始化规格商品--提交校验数据合法性
    var goodsSpecArr = '${goodsSpec!}'.replace(/&quot;/g, '"');
    goodsSpecArr = JSON.parse(goodsSpecArr);
    $.each(goodsSpecArr, function (index, item) {
        secGroup.specProduct.prd_id.push(item.prd_id);
        secGroup.specProduct.prd_price.push(item.prd_price);
        secGroup.specProduct.prd_number.push(item.prd_number);
    });
    console.log(secGroup.specProduct);


    let card_arry = [];
    console.log($('input[name="goods_have_card_str"]').val());
    if ($('input[name="goods_have_card_str"]').val()){
        card_arry = $('input[name="goods_have_card_str"]').val().split(',');
        var card_name = $('input[name="goods_have_card_name"]').val().split(',');
        for (var i in card_arry){
            var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
            var span =' <span class="card_span">';
            var inner_html = span + '<span value="'+card_arry[i]+'">'+ card_name[i] + '</span>' + img + '</span>';
            $(".card-info-row").append(inner_html);
            $('#card_id').children("option[value='"+card_arry[i]+"']").remove();
            // if(!in_array(all_label[i].id,label_arry)){
            //     html+='<option value='+all_label[i].id+'>'+all_label[i].name+'</option>';
            // }
        }
        $('.card-info').show();
    }
    $('.card-info-row').on('click','.card-delete',function(){
        var op_name = $(this).parent().html();
        var opp_val = $(this).prev().attr("value");
    $(this).parent().remove();
    var op_html = '<option value="'+opp_val+'">' + op_name + '</option>';
    $('#card_id').append(op_html);
        card_arry.splice($.inArray(opp_val,card_arry),1);
        $('input[name="goods_have_card_str"]').val(card_arry.join(','));
        if(card_arry.length == 0){
            $('.card-info').hide();
        }
    });
    $('#card_id').change(function(){
        var card_name = $(this).children('option:selected').html();
        var card_val = $(this).children('option:selected').attr('value');
        if(card_arry.length == 0){
            $('.card-info').show();
        }
        card_arry.push(card_val);
        $('input[name="goods_have_card_str"]').val(card_arry.join(','));
        var img = ' <img src="/image/admin/icon_delete.png" alt="" class="card-delete"  />'
        var span =' <span class="card_span">';
        var inner_html = span + '<span value="'+card_val+'">'+ card_name + '</span>' + img + '</span>';
        $(this).parent().parent().find(".card-info-row").append(inner_html);
        $(this).children('option:selected').remove();
    })
    if($("input[name='is_card_exclusive']").is(":checked")){
        $('.card_select_box').css("display",'block');
        if($('.card-info-row').find('.card_span').length > 0){
            $('.card-info').css("display",'block');
        }
    }else{
        $('.card_select_box').css("display",'none');
        $('.card-info').css("display",'none');      
    }

    $("input[name='is_card_exclusive']").change(function () {
        if($("input[name='is_card_exclusive']").is(":checked")){
            $('.card_select_box').css("display",'block');
            if($('.card-info-row').find('.card_span').length > 0){
                $('.card-info').css("display",'block');
            }
        }else{
            $('.card_select_box').css("display",'none');
            $('.card-info').css("display",'none');         
        }
    })

    $('.refresh-card').click(function () {
        util.ajax_json('/admin/ajax/card/exclusive',function(d){
            if(d.error!=0){
                util.mobile_alert('刷新失败');
                return false;
            }
            else{
                var all_card = d.content.cards;
                var html = '<option value="0"  selected="selected" >请选择会员卡</option>';
                for (var i in all_card){
                    html+='<option value='+all_card[i].id+'>'+all_card[i].card_name+'</option>';
                }
                $('#card_id').html(html);
                for (var j in card_arry){
                    $('#card_id').children("option[value='"+card_arry[j]+"']").remove();
                }
                util.mobile_alert('刷新成功');
            }
        },{is_goods:0,is_not_time:1});
    });

</script>

<#include "/admin/footer.ftl">
<script>
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
    getPowerInfo('main_config','seckill_goods','sub_4','抽奖',0);
</script>