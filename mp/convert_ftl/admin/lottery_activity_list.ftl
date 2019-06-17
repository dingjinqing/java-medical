<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/lottery_activity_list.css" type="text/css" />

<div style="min-width: 1090px;">
	<div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理 / </a></span>
        <span><a href="/admin/new.market?top_index=4">拼团抽奖 / </a></span>
        <span style="color: #666;">活动名称-活动订单</span>
    </div>
    <div class="main-container">
    	<div class="order-info">
    		<ul class="clearfix">
    			<li class="order-info-li clearfix">
    				<div class="fl">
                        <span>商品名称</span>
                        <input type="text" name="goods_name" placeholder="商品名称" value="">
                    </div>
                    <div class="fl">
                        <span>订单号</span>
                        <input type="text" name="order_sn" placeholder="订单号" value="">
                    </div>
                    <div class="fl">
                    	<span>订单状态</span>
                    	<select name="" id="">
                    		<option value="-1">全部订单</option>
                    		<option value="1">待付款</option>
                    	</select>
                    </div>
    			</li>
    			<li class="order-info-li clearfix">
    				<div class="fl">
                        <span>收货人姓名</span>
                        <input type="text" name="goods_name" placeholder="收货人姓名" value="">
                    </div>
                    <div class="fl">
                        <span>收货人手机号</span>
                        <input type="text" name="order_sn" placeholder="收货人手机号" value="">
                    </div>
    			</li>
    			<div class="fl receive_address kuaidi" style="display: block;">
    				<span>收货地址</span>
    				<select name="province_code" id="province_code">
    					<option value="">请选择省</option>
    				</select>
                    <select name="city_code" id="city_code">
    					<option value="">请选择市</option>
    				</select>
    				<select name="district_code" id="district_code">
    					<option value="">请选择县</option>
    				</select>
    			</div>
    			<li class="order-info-li clearfix btn_zu">
                    <button type="button" class="btn-choose">筛选</button>
                    <button type="button" class="btn-excel" style="margin-left: 12px;">导出表格</button>
                </li>
    		</ul>
    	</div>
    	<div class="return-pin-group-box"  style="margin-top: 10px">
            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <table class="tb-decorate-list">
                        <thead>
                            <tr class="get-list-th">
                                <th > 订单号 </th>
                                <th width="20%;"> 活动商品 </th>
                                <th > 是否成团 </th>
                                <th > 收货人信息 </th>
                                <th > 是否中奖 </th>
                                <th > 下单时间 </th>
                                <th> 抽奖码数量 </th>
                                <th> 订单状态 </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                            	<td><a href="">P201808091101405008</a></td>
                            	<td>
                                    <img src="http://mpdevimg.weipubao.cn/upload/4748160/image/20181016/crop_ZWBFBwrKm8LsaZ7X.jpeg" alt="" class="name-img">
                                    <span class="list-name">
                                        <span style="margin-left: -50px;">测试商品g01</span>
                                    </span>
                                </td>
                            	<td>是</td>
                            	<td>
                                    <div><a href="#">奔跑的小猪</a></div>
                                    <div><a href="#">18866668888</a></div>
                            	</td>
                            	<td>是</td>
                            	<td>2018-09-18 21:38:36 </td>
                            	<td>12</td>
                            	<td>已发货</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


<#include "/admin/footer.ftl">