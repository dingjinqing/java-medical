<#include ("system.header")
<#include ("system.features_nav")
<script language="JavaScript" src="/js/jquery.searchableSelect.js"></script>
<link rel="stylesheet" href="/css/system/shop_activity_data.css?v=1.0.4" type="text/css">
<link href="/css/system/jquery.searchableSelect.css" rel="stylesheet" type="text/css"/>
<style>

	#analysis .layui-card-header .select_visit_trend {
		width: 160px;
		height: 30px;
		border-radius: 3px;
		border: 1px solid #ccc;
		color: #333;
		font-size: 14px;
		margin-right: 10px;
	}
	#analysis .layui-table tr th:first-child, #analysis .layui-table tr td:first-child {
		text-align: center;
	}


	.nothing{
		width: 100%;
		height:400px;
		margin: auto;
		padding: auto;
		font-size: 20px;
		text-align: center;
		line-height: 200px;
	}
	td a{
		color: #5A8BFF;
	}
	.activity{
		padding-bottom: 40px;
	}
	.main-order-table.common-width .tr-border td{
		width: 6%;
	}
</style>
<div style="min-height: 1090px;">
	<div class="shop-container">
		<div class="order-info">
		    <form action="" method="post" id="form1">
				{{csrf_field()!}
	            <input type="hidden" name="page" value="1">
	            <ul>
	                <li class="order-info-li clearfix">
	                    <div class="fl one">
							<span style="width: 70px;">小程序名称</span>
							<select name="shop_id" class="select_visit_trend shop_select"  >
								<option value="0"  select="selected" >全部店铺</option>
								<#list ($shop_list as $st)
									<option value="${st['shop_id']!}" <#if ($request['shop_id']==$st['shop_id']) selected </#if>>${st['nick_name']!}</option>
								</#list>
							</select>

							{{--<select name="shop_id" class="select_visit_trend shop_select">--!}
								{{--<option value="0"  <#if (!$request['shop_id']) selected </#if>  disabled>请选择</option>--!}
								{{--<#list ($shop_list as $st)--!}
									{{--<option value="${st['shop_id']!}" <#if ($request['shop_id']==$st->shop_id) selected </#if>>${st['nick_name']!}</option>--!}
								{{--</#list>--!}
							{{--</select>--!}
	                        {{--<input type="text" name="shop_name" value="${request['shop_name']!}" placeholder='店铺名称' />--!}
	                    </div>
	                    <div class="fl one" style="width: 245px">
	                        <span>店铺id</span>
	                        <input type="text" name="shop_ids" value="${request['shop_ids']!}" placeholder='店铺ID' />
	                    </div>
	                    <div class="fl one" style="width: 130px;">
	                       <button class="search-button">查询店铺活动数据</button>
	                    </div>
	                    <div class="fl one select-option">
	                    	<select name="type" class="select_visit_trend" id="">
	                    		<option value="1" selected>查看1天</option>
	                    		<option value="7" <#if ($request['type']==7) selected </#if>>最近7天</option>
	                    		<option value="30" <#if ($request['type']==30) selected </#if>>最近30天</option>
	                    	</select>
	                    	<span class="layui-card-item" style="width: auto">${date[0]!} - ${date[1]!}</span>
	                    </div>
	                </li>
	            </ul>
	        </form>
		</div>
		<div class="activity" <#if ($data_list) data="1" <#else> data="0" </#if>>
			{{--{{dd($data_list)!}--!}
			<#if (!$data_list)
				<div class="nothing">
					请核对输入正确的店铺名称或店铺ID！
				</div>
			<#else>
				<#list ($data_list as $key => $value)
					<#if ($key == 1)
					<div class="many-people">
						<div class="order-people">
							<span class="tab-bar"></span>
							<span>多人拼团</span>
						</div>
						<table class="main-order-table common-width">
							<tbody>
								<tr class="tr-border">
									<td>进行中活动数</td>
									<td>已过期活动数</td>
									<td>已关闭活动数</td>
									<td>活动订单数</td>
									<td>活动订单总金额</td>
									<td>访问用户数</td>
									<td>访问用户占比</td>
									<td>参团用户数</td>
									<td>成团用户数</td>
									<td>成团用户占比</td>
									<td>分享用户数</td>
									<td>分享用户数占比</td>
									<td>活动拉新用户数</td>
									<td>操作</td>
								</tr>
								<tr class="tr-no-border">
									<td>${value->currently!}</td>
									<td>${value->expired!}</td>
									<td>${value->closed!}</td>
									<td>${value->order_num!}</td>
									<td>${value->order_money!}</td>
									<td>${value->visited_user!}</td>
									<td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
									<td>${value->join_user!}</td>
									<td>${value->success_user!}</td>
									<td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
									<td>${value->share_user!}</td>
									<td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
									<td>${value->new_user!}</td>
									<td>
										<a href="/system/order/list?goods_type=1&shop_id=${value->shop_id!}">查看订单</a>
										<br>
										<a href="/system/pin/group/list?shop_id=${value->shop_id!}">活动列表</a>
										<br>
										<a href="/system/statistics/shop/activity?activity_type=1&shop_id=${value->shop_id!}">活动数据</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<#elseif>($key == 3)
						<div class="many-people">
								<div class="order-people">
									<span class="tab-bar"></span>
									<span>砍价</span>
								</div>
								<table class="main-order-table common-width">
									<tbody>
									<tr class="tr-border">
										<td>进行中活动数</td>
										<td>已过期活动数</td>
										<td>已关闭活动数</td>
										<td>活动订单数</td>
										<td>访问用户数</td>
										<td>访问用户占比</td>
										<td>砍价用户数</td>
										<td>砍价用户数占比</td>
										<td>砍价成功用户数</td>
										<td>砍价成功用户占比</td>
										<td>砍价人次</td>
										<td>分享用户数</td>
										<td>分享用户数占比</td>
										<td>活动拉新用户数</td>
										<td>操作</td>
									</tr>
									<tr class="tr-no-border">
										<td>${value->currently!}</td>
										<td>${value->expired!}</td>
										<td>${value->closed!}</td>
										<td>${value->order_num!}</td>
										<td>${value->visited_user!}</td>
										<td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
										<td>${value->join_user!}</td>
										<td>${value->visited_user == 0 ? '--' : number_format($value->join_user/$value->visited_user*100,2).'%'!}</td>
										<td>${value->success_user!}</td>
										<td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
										<td>${value->bargain_user_count!}</td>
										<td>${value->share_user!}</td>
										<td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
										<td>${value->new_user!}</td>
										<td>
											<a href="/system/order/list?goods_type=3&shop_id=${value->shop_id!}">查看订单</a>
											<br>
											<a href="/system/bargain/list?shop_id=${value->shop_id!}">活动列表</a>
											<br>
											<a href="/system/statistics/shop/activity?activity_type=3&shop_id=${value->shop_id!}">活动数据</a>
										</td>
									</tr>
									</tbody>
								</table>
							</div>
					<#elseif>($key == 5)
						<div class="many-people">
							<div class="order-people">
								<span class="tab-bar"></span>
								<span>秒杀</span>
							</div>
							<table class="main-order-table common-width">
								<tbody>
								<tr class="tr-border">
									<td>进行中活动数</td>
									<td>已过期活动数</td>
									<td>已关闭活动数</td>
									<td>活动订单数</td>
									<td>活动订单总金额</td>
									<td>访问用户数</td>
									<td>访问用户占比</td>
									<td>参与秒杀用户数</td>
									<td>参与秒杀用户数占比</td>
									<td>秒杀支付用户数</td>
									<td>秒杀支付用户占比</td>
									<td>活动拉新用户数</td>
									<td>操作</td>
								</tr>
								<tr class="tr-no-border">
									<td>${value->currently!}</td>
									<td>${value->expired!}</td>
									<td>${value->closed!}</td>
									<td>${value->order_num!}</td>
									<td>${value->order_money!}</td>
									<td>${value->visited_user!}</td>
									<td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
									<td>${value->join_user!}</td>
									<td>${value->visited_user == 0 ? '--' : number_format($value->join_user/$value->visited_user*100,2).'%'!}</td>
									<td>${value->success_user!}</td>
									<td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
									<td>${value->new_user!}</td>
									<td>
										<a href="/system/order/list?goods_type=5&shop_id=${value->shop_id!}">查看订单</a>
										<br>
										<a href="/system/sec/kill/list?shop_id=${value->shop_id!}">活动列表</a>
										<br>
										<a href="/system/statistics/shop/activity?activity_type=5&shop_id=${value->shop_id!}">活动数据</a>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					<#elseif>($key == 6)
						<div class="many-people">
							<div class="order-people">
								<span class="tab-bar"></span>
								<span>表单</span>
							</div>
							<table class="main-order-table">
								<tbody>
								<tr class="tr-border">
									<td>进行中表单数</td>
									<td>已过期表单数</td>
									<td>已关闭表单数</td>
									<td>访问用户数</td>
									<td>访问用户占比</td>
									<td>分享用户数</td>
									<td>分享用户数占比</td>
									<td>提交表单用户数</td>
									<td>提交表单用户数占比</td>
									<td>拉取新用户数</td>
									<td>操作</td>
								</tr>
								<tr class="tr-no-border">
									<td>${value->currently!}</td>
									<td>${value->expired!}</td>
									<td>${value->closed!}</td>
									<td>${value->visited_user!}</td>
									<td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
									<td>${value->share_user!}</td>
									<td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
									<td>${value->success_user!}</td>
									<td>${value->visited_user == 0 ? '--' : number_format($value->success_user/$value->visited_user*100,2).'%'!}</td>
									<td>${value->new_user!}</td>
									<td>
										<a href="#" hidden>查看订单</a>
										{{--<br>--!}
										<a href="/system/form/decoration/list?shop_id=${value->shop_id!}">活动列表</a>
										<br>
										<a href="/system/statistics/shop/activity?activity_type=6&shop_id=${value->shop_id!}">活动数据</a>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					<#elseif>($key == 7)
						<div class="many-people">
							<div class="order-people">
								<span class="tab-bar"></span>
								<span>组团瓜分积分</span>
							</div>
							<table class="main-order-table common-width">
								<tbody>
								<tr class="tr-border">
									<td>进行中活动数</td>
									<td>已过期活动数</td>
									<td>已关闭活动数</td>
									<td>活动总积分数</td>
									<td>已瓜分积分数</td>
									<td>访问用户数</td>
									<td>访问用户占比</td>
									<td>参团用户数</td>
									<td>成团用户数</td>
									<td>成团用户占比</td>
									<td>分享用户数</td>
									<td>分享用户数占比</td>
									<td>活动拉新用户数</td>
									<td>操作</td>
								</tr>
								<tr class="tr-no-border">
									<td>${value->currently!}</td>
									<td>${value->expired!}</td>
									<td>${value->closed!}</td>
									<td>${value->order_num!}</td>
									<td>${value->order_money!}</td>
									<td>${value->visited_user!}</td>
									<td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
									<td>${value->join_user!}</td>
									<td>${value->success_user!}</td>
									<td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
									<td>${value->share_user!}</td>
									<td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
									<td>${value->new_user!}</td>
									<td>
										<a href="/system/market/integration/list?shop_id=${value->shop_id!}">活动列表</a>
										<br>
										<a href="/system/statistics/shop/activity?activity_type=7&shop_id=${value->shop_id!}">活动数据</a>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					<#elseif>($key == 8)
						<div class="many-people">
							<div class="order-people">
								<span class="tab-bar"></span>
								<span>拼团抽奖</span>
							</div>
							<table class="main-order-table common-width">
								<tbody>
								<tr class="tr-border">
									<td>进行中活动数</td>
									<td>已过期活动数</td>
									<td>已关闭活动数</td>
									<td>活动订单数</td>
									<td>奖品发放数量</td>
									<td>访问用户数</td>
									<td>访问用户占比</td>
									<td>参团用户数</td>
									<td>成团用户数</td>
									<td>成团用户占比</td>
									<td>分享用户数</td>
									<td>分享用户数占比</td>
									<td>活动拉新用户数</td>
									<td>操作</td>
								</tr>
								<tr class="tr-no-border">
									<td>${value->currently!}</td>
									<td>${value->expired!}</td>
									<td>${value->closed!}</td>
									<td>${value->order_num!}</td>
									<td>${value->order_money!}</td>
									<td>${value->visited_user!}</td>
									<td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
									<td>${value->join_user!}</td>
									<td>${value->success_user!}</td>
									<td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>
									<td>${value->share_user!}</td>
									<td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>
									<td>${value->new_user!}</td>
									<td>
										<a href="/system/order/list?goods_type=8&shop_id=${value->shop_id!}">查看订单</a>
										<br>
										<a href="/system/market/groupdraw/list?shop_id=${value->shop_id!}">活动列表</a>
										<br>
										<a href="/system/statistics/shop/activity?activity_type=8&shop_id=${value->shop_id!}">活动数据</a>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					<#elseif>($key == 9)
						<div class="many-people">
							<div class="order-people">
								<span class="tab-bar"></span>
								<span>限时降价</span>
							</div>
							<table class="main-order-table common-width">
								<tbody>
								<tr class="tr-border">
									<td>进行中活动数</td>
									<td>已过期活动数</td>
									<td>已关闭活动数</td>
									<td>活动订单数</td>
									<td>活动订单总金额</td>
									<td>访问用户数</td>
									<td>访问用户占比</td>
									{{--<td>参团用户数</td>--!}
									<td>下单用户数</td>
									{{--<td>成团用户占比</td>--!}
									{{--<td>分享用户数</td>--!}
									{{--<td>分享用户数占比</td>--!}
									{{--<td>活动拉新用户数</td>--!}
									<td>操作</td>
								</tr>
								<tr class="tr-no-border">
									<td>${value->currently!}</td>
									<td>${value->expired!}</td>
									<td>${value->closed!}</td>
									<td>${value->order_num!}</td>
									<td>${value->order_money!}</td>
									<td>${value->visited_user!}</td>
									<td>${value->visited == 0 ? '--' : number_format($value->visited_user/$value->visited*100,2).'%'!}</td>
									{{--<td>${value->join_user!}</td>--!}
									<td>${value->success_user!}</td>
{{--									<td>${value->join_user == 0 ? '--' : number_format($value->success_user/$value->join_user*100,2).'%'!}</td>--!}
									{{--<td>${value->share_user!}</td>--!}
									{{--<td>${value->visited_user == 0 ? '--' : number_format($value->share_user/$value->visited_user*100,2).'%'!}</td>--!}
									{{--<td>${value->new_user!}</td>--!}
									<td>
										<a href="/system/order/list?goods_type=6&shop_id=${value->shop_id!}">查看订单</a>
										<br>
										<a href="/system/market/reduce/list?shop_id=${value->shop_id!}">活动列表</a>
										<br>
										<a href="/system/statistics/shop/activity?activity_type=9&shop_id=${value->shop_id!}">活动数据</a>
									</td>
								</tr>
								</tbody>
							</table>
						</div>
					</#if>
				</#list>
			{{--<div class="many-people">--!}
				{{--<div class="order-people">--!}
					{{--<span class="tab-bar"></span>--!}
					{{--<span>活动有礼</span>--!}
				{{--</div>--!}
				{{--<table class="main-order-table">--!}
					{{--<tbody>--!}
						{{--<tr class="tr-border">--!}
							{{--<td>进行中活动数</td>--!}
							{{--<td>已过期活动数</td>--!}
							{{--<td>已关闭活动数</td>--!}
							{{--<td>活动送券活动数量</td>--!}
							{{--<td>幸运大抽奖活动数量</td>--!}
							{{--<td>参与活动用户数</td>--!}
							{{--<td>活动拉新用户数</td>--!}
						{{--</tr>--!}
						{{--<tr class="tr-no-border">--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>23</td>--!}
							{{--<td>￥20</td>--!}
							{{--<td>10</td>--!}
							{{--<td>5.2%</td>--!}
						{{--</tr>--!}
					{{--</tbody>--!}
				{{--</table>--!}
			{{--</div>--!}
			{{--<div class="many-people">--!}
				{{--<div class="order-people">--!}
					{{--<span class="tab-bar"></span>--!}
					{{--<span>支付有利</span>--!}
				{{--</div>--!}
				{{--<table class="main-order-table">--!}
					{{--<tbody>--!}
						{{--<tr class="tr-border">--!}
							{{--<td>进行中活动数</td>--!}
							{{--<td>已过期活动数</td>--!}
							{{--<td>已关闭活动数</td>--!}
							{{--<td>活动订单数</td>--!}
							{{--<td>活动订单总金额</td>--!}
							{{--<td>分裂优惠券活动数量</td>--!}
							{{--<td>幸运大抽奖活动数量</td>--!}
							{{--<td>参与活动用户数</td>--!}
							{{--<td>活动拉新用户数</td>--!}
						{{--</tr>--!}
						{{--<tr class="tr-no-border">--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>23</td>--!}
							{{--<td>￥20</td>--!}
							{{--<td>10</td>--!}
							{{--<td>5.2%</td>--!}
							{{--<td>5</td>--!}
							{{--<td>20%</td>--!}
						{{--</tr>--!}
					{{--</tbody>--!}
				{{--</table>--!}
			{{--</div>--!}
			{{--<div class="many-people">--!}
				{{--<div class="order-people">--!}
					{{--<span class="tab-bar"></span>--!}
					{{--<span>优惠券</span>--!}
				{{--</div>--!}
				{{--<table class="main-order-table">--!}
					{{--<tbody>--!}
						{{--<tr class="tr-border">--!}
							{{--<td>进行中优惠券数</td>--!}
							{{--<td>已过期优惠券数</td>--!}
							{{--<td>已关闭优惠券数</td>--!}
							{{--<td>使用优惠券订单数</td>--!}
							{{--<td>用券订单总金额</td>--!}
							{{--<td>领券用户数</td>--!}
							{{--<td>领券用户占比</td>--!}
							{{--<td>用券用户数</td>--!}
							{{--<td>用券用户数占比</td>--!}
							{{--<td>砍价成功用户占比</td>--!}
							{{--<td>券使用率</td>--!}
						{{--</tr>--!}
						{{--<tr class="tr-no-border">--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>23</td>--!}
							{{--<td>￥20</td>--!}
							{{--<td>10</td>--!}
							{{--<td>5.2%</td>--!}
							{{--<td>25</td>--!}
							{{--<td>5</td>--!}
							{{--<td>20%</td>--!}
							{{--<td>20%</td>--!}
						{{--</tr>--!}
					{{--</tbody>--!}
				{{--</table>--!}
			{{--</div>--!}
			{{--<div class="many-people">--!}
				{{--<div class="order-people">--!}
					{{--<span class="tab-bar"></span>--!}
					{{--<span>定向发券</span>--!}
				{{--</div>--!}
				{{--<table class="main-order-table">--!}
					{{--<tbody>--!}
						{{--<tr class="tr-border">--!}
							{{--<td>进行中优惠券数</td>--!}
							{{--<td>已过期优惠券数</td>--!}
							{{--<td>已关闭优惠券数</td>--!}
							{{--<td>使用优惠券订单数</td>--!}
							{{--<td>用券订单总金额</td>--!}
							{{--<td>领券用户数</td>--!}
							{{--<td>领券用户占比</td>--!}
							{{--<td>用券用户数</td>--!}
							{{--<td>用券用户数占比</td>--!}
							{{--<td>砍价成功用户占比</td>--!}
							{{--<td>券使用率</td>--!}
						{{--</tr>--!}
						{{--<tr class="tr-no-border">--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>23</td>--!}
							{{--<td>￥20</td>--!}
							{{--<td>10</td>--!}
							{{--<td>5.2%</td>--!}
							{{--<td>25</td>--!}
							{{--<td>5</td>--!}
							{{--<td>20%</td>--!}
							{{--<td>20%</td>--!}
						{{--</tr>--!}
					{{--</tbody>--!}
				{{--</table>--!}
			{{--</div>--!}
			{{--<div class="many-people">--!}
				{{--<div class="order-people">--!}
					{{--<span class="tab-bar"></span>--!}
					{{--<span>积分兑换</span>--!}
				{{--</div>--!}
				{{--<table class="main-order-table">--!}
					{{--<tbody>--!}
						{{--<tr class="tr-border">--!}
							{{--<td>进行中优惠券数</td>--!}
							{{--<td>已过期优惠券数</td>--!}
							{{--<td>已关闭优惠券数</td>--!}
							{{--<td>使用优惠券订单数</td>--!}
							{{--<td>用券订单总金额</td>--!}
							{{--<td>领券用户数</td>--!}
							{{--<td>领券用户占比</td>--!}
							{{--<td>用券用户数</td>--!}
							{{--<td>用券用户数占比</td>--!}
							{{--<td>砍价成功用户占比</td>--!}
							{{--<td>券使用率</td>--!}
						{{--</tr>--!}
						{{--<tr class="tr-no-border">--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>23</td>--!}
							{{--<td>￥20</td>--!}
							{{--<td>10</td>--!}
							{{--<td>5.2%</td>--!}
							{{--<td>25</td>--!}
							{{--<td>5</td>--!}
							{{--<td>20%</td>--!}
							{{--<td>20%</td>--!}
						{{--</tr>--!}
					{{--</tbody>--!}
				{{--</table>--!}
			{{--</div>--!}
			{{--<div class="many-people">--!}
				{{--<div class="order-people">--!}
					{{--<span class="tab-bar"></span>--!}
					{{--<span>幸运大抽奖</span>--!}
				{{--</div>--!}
				{{--<table class="main-order-table">--!}
					{{--<tbody>--!}
						{{--<tr class="tr-border">--!}
							{{--<td>进行中优惠券数</td>--!}
							{{--<td>已过期优惠券数</td>--!}
							{{--<td>已关闭优惠券数</td>--!}
							{{--<td>使用优惠券订单数</td>--!}
							{{--<td>用券订单总金额</td>--!}
							{{--<td>领券用户数</td>--!}
							{{--<td>领券用户占比</td>--!}
							{{--<td>用券用户数</td>--!}
							{{--<td>用券用户数占比</td>--!}
							{{--<td>砍价成功用户占比</td>--!}
							{{--<td>券使用率</td>--!}
						{{--</tr>--!}
						{{--<tr class="tr-no-border">--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>23</td>--!}
							{{--<td>￥20</td>--!}
							{{--<td>10</td>--!}
							{{--<td>5.2%</td>--!}
							{{--<td>25</td>--!}
							{{--<td>5</td>--!}
							{{--<td>20%</td>--!}
							{{--<td>20%</td>--!}
						{{--</tr>--!}
					{{--</tbody>--!}
				{{--</table>--!}
			{{--</div>--!}
			{{--<div class="many-people">--!}
				{{--<div class="order-people">--!}
					{{--<span class="tab-bar"></span>--!}
					{{--<span>消息推送</span>--!}
				{{--</div>--!}
				{{--<table class="main-order-table">--!}
					{{--<tbody>--!}
						{{--<tr class="tr-border">--!}
							{{--<td>进行中优惠券数</td>--!}
							{{--<td>已过期优惠券数</td>--!}
							{{--<td>已关闭优惠券数</td>--!}
							{{--<td>使用优惠券订单数</td>--!}
							{{--<td>用券订单总金额</td>--!}
							{{--<td>领券用户数</td>--!}
							{{--<td>领券用户占比</td>--!}
							{{--<td>用券用户数</td>--!}
							{{--<td>用券用户数占比</td>--!}
							{{--<td>砍价成功用户占比</td>--!}
							{{--<td>券使用率</td>--!}
						{{--</tr>--!}
						{{--<tr class="tr-no-border">--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>20</td>--!}
							{{--<td>23</td>--!}
							{{--<td>￥20</td>--!}
							{{--<td>10</td>--!}
							{{--<td>5.2%</td>--!}
							{{--<td>25</td>--!}
							{{--<td>5</td>--!}
							{{--<td>20%</td>--!}
							{{--<td>20%</td>--!}
						{{--</tr>--!}
					{{--</tbody>--!}
				{{--</table>--!}
			{{--</div>--!}
			</#if>
		</div>
	</div>
</div>

<#include ("system.footer")
<script>
    $(function(){
        $("[name='shop_id']").searchableSelect();
        // $("[name='cat_id']").searchableSelect();
    });
	$(".select_visit_trend").change(function () {
		$("#form1").submit();
    })
</script>