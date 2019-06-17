<#include ("system.header")
<link rel="stylesheet" href="/css/system/new_group.css?v=1.0.5" type="text/css" />
<style>
	.hover_show:before{
		top: 253px !important;
	}
</style>
<div style="min-width: 1090px;">
	{{--<div class="title">--!}
        {{--<span><a href="/system/market/view?top_index=4">营销管理</a> / </span>--!}
        {{--<span><a href="/system/market/groupdraw/list?top_index=4">拼团抽奖</a></span>--!}
    {{--</div>--!}
	<div class="main-container">
		<div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li <#if (empty($nav))class="active"</#if>>
                    <a href="/system/market/groupdraw/list">全部拼团抽奖活动</a>
                </li>
                <li <#if ($nav == 1)class="active"</#if>>
                    <a href="/system/market/groupdraw/list?nav=1" >进行中</a>
                </li>
                <li <#if ($nav == 2)class="active"</#if>>
                    <a href="/system/market/groupdraw/list?nav=2" >未开始</a>
                </li>
                <li <#if ($nav == 3)class="active"</#if>> 
                    <a href="/system/market/groupdraw/list?nav=3" >已过期</a>
                </li>
                <li <#if ($nav == 4)class="active"</#if>>
                    <a href="/system/market/groupdraw/list?nav=4">已停用</a>
                </li>
				<li <#if ($nav == 5)class="active"</#if>>
					<a href="/system/market/groupdraw/add?nav=4"><#if (empty($basicData->id))添加拼团抽奖活动<#else>编辑拼团抽奖活动</#if></a>
				</li>
            </ul>
        </div>
		<div class="return-pin-group-box" style="padding-top: 20px;">
            <form name="formData" id="form1" method="post" action="/system/market/groupdraw/add" style="margin-bottom: 80px;">
				{{csrf_field()!}
				<input type="hidden" name="id" value="${_GET['id']!}"/>
                <table class="tb-pin-group">
                    <tbody>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>活动名称：</span>
	                        </td>
	                        <td>
	                            <input type="text" class="limit_amount" placeholder="请输入活动名称" name="name" maxlength="20" value="${basicData->name!}">
	                        </td>
	                    </tr>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>活动有效期：</span>
	                        </td>
	                        <td>
								<input type="text" class="tb-text date-text" value="${basicData->start_time!}" name="start_time" value="${basicData->start_time!}"
									   id="startDate" onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"> 至
								<input type="text" class="tb-text date-text" value="${basicData->end_time!}"  name="end_time" value="${basicData->end_time!}"
									   id="endDate" onclick="picker();" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off">
	                            <span style="color: #999;">活动结束即开奖
	                            </span>
	                        </td>
	                    </tr>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>奖池最少人数：</span>
	                        </td>
	                        <td>
	                            <input type="number" name="min_join_num" class="limit_amount" onkeyup="value=value.replace(/[^\d]/g,'')"  value="{{empty($basicData->min_join_num) ? 100 : $basicData->min_join_num!}"/>
	                            <span style="color: #999;">每个奖池开奖所需的最少参与人数,少于这个人数,则所有参与用户都不中奖,每个商品对应一个奖池</span>
	                        </td>
	                    </tr>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>商品金额：</span>
	                        </td>
	                        <td>
	                            <input type="text" name="pay_money" class="limit_amount" oninput="this.value=this.value.replace(/[^\d.]/g,'')"  value="{{empty($basicData->pay_money) ? 1 : $basicData->pay_money!}"/> 元
	                            <span style="color: #999;">下单参与拼团抽奖,商品需要支付的金额</span>
	                        </td>
	                    </tr>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>最大参团数量：</span>
	                        </td>
	                        <td>
	                            <input type="number" class="limit_amount" onkeyup="this.value=this.value.replace(/\D/g,'')" placeholder="" name="join_limit" value="{{empty($basicData->join_limit) ? 5 : $basicData->join_limit!}" min="0">
	                            <span style="color: #999;">活动时间内,每个用户可以参与抽奖团的最大数量</span>
	                        </td>
	                    </tr>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>最大开团数量：</span>
	                        </td>
	                        <td>
	                            <input type="number" class="limit_amount" placeholder="" name="open_limit" onkeyup="this.value=this.value.replace(/\D/g,'')" value="{{empty($basicData->open_limit) ? 1 : $basicData->open_limit!}" min="0">
	                            <span style="color: #999;">活动时间内,每个用户可以开启抽奖团的最大数量</span>
	                        </td>
	                    </tr>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>最少成团人数：</span>
	                        </td>
	                        <td>
	                            <input type="number" class="limit_amount" placeholder="" name="limit_amount" onkeyup="this.value=this.value.replace(/\D/g,'')" value="{{empty($basicData->limit_amount) ? 5 : $basicData->limit_amount!}" min="0">
	                            <span style="color: #999;">每个抽奖团的最少成团人数,成团后,该团内所有参与用户可参与抽奖</span>
	                        </td>
	                    </tr>
                        <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>最小展示人数：</span>
	                        </td>
	                        <td style="position: relative;">
	                            <input type="number" class="limit_amount" onkeyup="value=value.replace(/[^\d]/g,'')" value="{{empty($basicData->to_num_show) ? 100 : $basicData->to_num_show!}"  placeholder="" name="to_num_show" min="0" >
	                            <span style="color: #999;">活动时间内,参与用户数达到设置的数量时,小程序前端展示活动参与人数</span>
								<a href="javascript:;" class="show_eg">查看示例
									<div class="hover_show" style="top: -250px">
										<img src="http://${image_domain!}/image/system/new_preview_image/pin_lottery.jpg" alt="">
									</div>
								</a>
	                        </td>
	                    </tr>
						<#if (!($basicData->id && $basicData->start_time <= date("Y-m-d H:i:s") && !$basicData->reward_coupon_id))
						<tr style="height: 50px;">
							<td style="width: 100px">
								<span class="tb-full-left" style="    margin-bottom: 17px;"><strong>&nbsp;</strong>鼓励奖：</span>
							</td>
							<td>
								<div class="coupon_content">
									<input type="hidden" name="reward_coupon_id" value="${basicData->reward_coupon_id!}">
									<div class="tem_right">
										<p style="color: #999; padding-bottom: 10px;">买家拼团失败后给予一定奖励，可提升买家复购</p>
										<div class="coupon_div clearfix" coupon_json="" <#if ($basicData->coupon_list) style="display: block" </#if>>
											<#list ($basicData->coupon_list ?? [] as $k => $cou)
												<div class="coupon_list">
													<#if (!($basicData->id && $basicData->start_time <= date("Y-m-d H:i:s")))
														<img src="http://${image_domain!}/image/system/sign_del.png" class="coupon_del">
													</#if>
													<input type="hidden" coupon_id="${cou->id!}" act_code="${cou->act_code!}" denomination="${cou->denomination!}" class="coupon_info">
													<div class="coupon_list_top">
														<#if ($cou->act_code == 'voucher')
															¥<span>${cou->denomination!}</span>
														<#elseif>($cou->act_code == 'discount')
															打<span>${cou->denomination!}</span>折
														</#if>
													</div>
													<div class="coupon_list_center">
														<#if ($cou->use_consume_restrict == 0)
															<div class="coupon_center_limit">不限制</div>
														<#else>
															<div class="coupon_center_limit">满${cou->least_consume!}使用</div>
														</#if>
														<div class="coupon_center_number">剩余<span>${cou->surplus!}</span>张</div>
													</div>
													<div class="coupon_list_bottom">
														领取
													</div>
												</div>
											</#list>
											<#if (!($basicData->id && $basicData->start_time <= date("Y-m-d H:i:s")))
												<#if  (!$bargain->id || count(explode(',', $bargain->reward_coupon_id)) < 5))
												<div class="card_add card_add_click">
													<img src="http://${image_domain!}/image/system/shop_beautify/add_decorete.png" alt="">
													<p>添加优惠券</p>
												</div>
												</#if>
											</#if>
										</div>
										<p style="color:#999999;">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
									</div>
								</div>
							</td>
						</tr>
						</#if>
	                    <tr style="height: 50px;">
	                        <td style="width: 100px">
	                            <span class="tb-full-left"><strong>*</strong>活动商品：</span>
	                        </td>
	                        <td>
	                           <input type="button" value="+ 选择商品" id="sel-goods-btn" class="choose_goods" name="choose_goods">
	                           <span style="color: #999;">最多添加20个商品</span>
	                        </td>
	                    </tr>
                    </tbody>
                </table>
                <input type="hidden" name="goods_id" value="${basicData->goods_id!}">
                <div class="main_goods_table">
                    <table class="tb_goods">
                        <thead>
                            <tr class="change_tr_first">
                                <td width="40%">商品名称</td>
                                <td>商品原价</td>
                                <td>商品库存</td>
								<#if  (!$basicData || ($basicData->status == 1 && strtotime($basicData->end_time) < time()))
								<td>操作</td>
								</#if>
                            </tr>
                        </thead>
                        <tbody id="main_goods_list">
							<#list  ($goodsList as $goods)
								<tr>
									<td>
										<div class="goods_img">
											<img src="${goods->goods_img!}">
										</div>
										<div class="goods_info clearfix">
											<div class="goods_name">${goods->goods_name!}</div>
										</div>
									</td>
									<td>${goods->shop_price!}</td>
									<td>${goods->goods_number!}</td>
									<td>
										<a href="##" item="${goods->goods_id!}" class="del_goods">删除</a>
									</td>
								</tr>
							</#list>
						</tbody>
                    </table>
                </div>
            </form>
        </div>
        <div class="reduce_price_footer fix_footer">
            <button onclick="return false;" class="save">保存</button>
        </div>
	</div>
</div>
<div class="coupon_list_clone hide">
	<div class="coupon_list">
		<img src="http://${image_domain!}/image/system/sign_del.png" class="coupon_del">
		<input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info">
		<div class="coupon_list_top">
			¥<span>××</span>
		</div>
		<div class="coupon_list_center">
			<div class="coupon_center_limit">满××使用</div>
			<div class="coupon_center_number">剩余<span>××</span>张</div>
		</div>
		<div class="coupon_list_bottom">
			领取
		</div>
	</div>
</div>
<#include ('system.preview_common')
<#include ("system.footer")

<script>
	function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
</script>
<script src="/js/system/new_group.js?v=1.1.5"></script>
<script type="text/javascript">
	<#if  ($basicData->id)
	    $('.main_goods_table').css('display', 'block');
		<#if  ($basicData->status == 0 || strtotime($basicData->end_time) < time())
		$('input').prop('disabled', 'disabled');
		$('.save, input[name="choose_goods"]').remove();
		$('.del_goods').parent().remove();
		</#if>
		<#if  ($basicData->status == 1 && strtotime($basicData->start_time) < time() &&
			strtotime($basicData->end_time) > time())
		$('input').prop('disabled', 'disabled');
		$('input[name="name"], input[type="hidden"]').removeAttrs('disabled');
		$('input[name="choose_goods"]').remove();
    	$('.del_goods').parent().remove();
		</#if>
	</#if>
</script>
<script type="text/javascript">
    // getPowerInfo('main_config','group_draw','sub_4','拼团抽奖',0);

    $(".coupon_div").on('click','.card_add_click',function(){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择优惠券', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['550px','500px']
                , content: '/system/frame/coupon/select' //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
                    var iframe = layer.getChildFrame('body', index);
                    if($('.coupon_div').find('.coupon_list').size() > 0){
                        $('.coupon_div').find('.coupon_list').each(function () {
                            var _this = $(this);
                            iframe.find('.coupon_list').each(function () {
                                if($(this).find('.coupon_info').attr('coupon_id') == _this.find('.coupon_info').attr('coupon_id')){
                                    $(this).addClass('card_list_active');
                                }
                            });
                        });
                    }
                    iframe.find('.coupon_list').click(function () {
                        if($(this).hasClass('card_list_active')){
                            $(this).removeClass('card_list_active');
                        }else{
                            $(this).addClass('card_list_active');
                        }
                    });
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    var list_active = iframe.find('.card_list_active');
                    if($(list_active).size() == 0){
                        util.mobile_alert('请选择优惠券');
                        return;
                    }
                    if($(list_active).size() > 5){
                        util.mobile_alert('最多只能选择5张优惠券哦~');
                        return;
                    }
                    var coupon_ids = [];
                    var card_add = $('.card_add').clone();
                    $('.coupon_div').html('');
                    $(list_active).each(function (i) {
                        var coupon_clone = $('.coupon_list_clone').find('.coupon_list').clone();
                        coupon_clone.find('.coupon_info').attr('act_code',$(this).find('.coupon_info').attr('act_code'));
                        coupon_clone.find('.coupon_info').attr('denomination',$(this).find('.coupon_info').attr('denomination'));
                        coupon_clone.find('.coupon_info').attr('coupon_id', $(this).find('.coupon_info').attr('coupon_id'));
                        if($(this).find('.coupon_info').attr('act_code') == "discount"){
                            coupon_clone.find('.coupon_list_top').html('<span>' + $(this).find('.coupon_info').attr('denomination') + '</span>折');
                        }
                        if($(this).find('.coupon_info').attr('act_code') == "voucher"){
                            coupon_clone.find('.coupon_list_top').html('￥<span>' + $(this).find('.coupon_info').attr('denomination') + '</span>');
                        }
                        coupon_clone.find('.coupon_center_limit').text($(this).find('.coupon_center_limit').text().replace(/\s+/g,""));
                        coupon_clone.find('.coupon_center_number').text($(this).find('.coupon_center_number').text().replace(/\s+/g,""));
                        $('.coupon_div').show();
                        $('.coupon_div').prepend(coupon_clone).append(card_add);
                        coupon_ids.push($(this).find('.coupon_info').attr('coupon_id'));
                        $('input[name="reward_coupon_id"]').val(coupon_ids);
                    });
                    if($('.coupon_div').find('.coupon_list').length==5){
                        $(".card_add_click").hide();
                    }else{
                        $(".card_add_click").show();
                    }
                    hasSaved = false;
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });

    $('.coupon_div').on('click','.coupon_del',function () {
        var coupon_ids = $("input[name='reward_coupon_id']").val().split(',');
        var index = $.inArray($(this).next().attr('coupon_id'), coupon_ids);
        if (index <= -1) return false;
        coupon_ids.splice(index,1);
        $('input[name="reward_coupon_id"]').val(coupon_ids.join(','));
        $(this).parent().remove();
        if(coupon_ids.length < 5){
            $('.card_add').show();
        }
        hasSaved = false;
    });
	<#if  ($basicData->id && count(explode(',', $basicData->reward_coupon_id)) >= 5)
    	$('.card_add').hide();
	</#if>
</script>
