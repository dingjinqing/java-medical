<#include ("system.header")
<link rel="stylesheet" href="/css/system/shop_fight_group.css?v=1.0.2" type="text/css" />
<style>
    .zenticon-help-circle {
        width: 13px;
        height: 13px;
        vertical-align: middle;
    }
    .float-layer.common-width-height {
        width: 400px;
        height: 130px;
        top: 26px;
        left: -124px;
    }
    .float-layer {
        position: absolute;
        z-index: 9999;
        line-height: 30px;
        font-size: 14px;
        padding: 15px;
        border: 1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150,150,150,0.3);
        border-radius: 5px;
        background: #fff;
        display: none;
    }
    .float-layer .float-layer-left {
        width: 30%;
        float: left;
        display: inline-block;
        color: #999;
    }
    .float-layer .float-layer-right {
        width: 70%;
        display: inline-block;
        color: #353535;
    }
    .float-layer .float-layer-i{
        position: absolute;
        left: 118px;
        top: -12px;
        display: inline-block;
        width: 0px;
        height: 0px;
        border-width: 0px 12px 12px;
        border-style: dashed dashed solid;
        border-color: transparent transparent rgb(255, 255, 255);
    }
    .item-image{
        position: relative;
        display: inline-block;
    }
    .order-people > div, .two-title{
        margin-top: 20px;
        padding-left: 3%;
    }
    .activity-order.float-layer .float-layer-left{
        width: 40%;
    }
    .activity-order.float-layer .float-layer-right{
        width: 45%;
    }
    .activity-order{
        width: 347px !important;
    }
</style>

<div style="min-height: 1090px;">
	<div class="shop-container">
		<div class="order-info">
		    <form action="/system/statistics/shop/activity" method="post" id="form1">
                {{csrf_field()!}
	            <input type="hidden" name="type" value="${post_data['type']!}">
	            <input type="hidden" name="shop_id" value="${post_data['shop_id']!}">
				<input type="hidden" name="activity_type" value="${post_data['activity_type']!}">
	            <ul>
	                <li class="order-info-li clearfix">
	                    <div class="fl select-option">
	                    	<span>时间筛选</span>
	                    	<select name="type" class="select_visit_trend" id="">
	                    		<option value="1">查看1天</option>
	                    		<option <#if ($post_data['type'] == 7) selected </#if> value="7">最近7天</option>
	                    		<option <#if ($post_data['type'] == 30) selected </#if> value="30">最近30天</option>
	                    	</select>
	                    	<span class="layui-card-item start_date">${start_date!}</span>-<span class="layui-card-item end_date"> ${end_date!}</span>
	                    </div>
	                </li>
	            </ul>
	        </form>
		</div>
		<div class="many-people use_chart" style="display: none">
			<div class="order-people">
				<span class="tab-bar"></span>
				<span>活动使用统计</span>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                    <div class="float-layer common-width-height">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">进行中活动数</span>
                            <#if  ($post_data['activity_type'] == 1) 
                            <span class="float-layer-right">筛选时间内进行中的拼团活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">筛选时间内进行中的砍价活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">筛选时间内进行中的秒杀活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-right">筛选时间内进行中的表单活动数量</span>
                            </#if>
                        </div>
                        <div>
                            <span class="float-layer-left">已过期活动数</span>
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">筛选时间内过期的拼团活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">筛选时间内过期的砍价活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">筛选时间内过期的秒杀活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-right">筛选时间内过期的表单活动数量</span>
                            </#if>
                        </div>
                        <div>
                            <span class="float-layer-left">已关闭活动数</span>
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">截止到前一天已关闭的拼团活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">截止到前一天已关闭的砍价活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">截止到前一天已关闭的秒杀活动数量</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-right">截止到前一天已关闭的表单活动数量</span>
                            </#if>
                        </div>
                    </div>
                </span>
			</div>
			<div id="container-use" style="height: 400px; width: 100%;"></div>
		</div>
		<div class="many-people order_chart" style="display: none">
			<div class="order-people">
				<span class="tab-bar"></span>
                <#if ($post_data['activity_type'] == 7)
				<span>活动奖励统计</span>
                <#else>
				<span>活动订单统计</span>
                </#if>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer activity-order common-width-height" style="height: 95px;">
                        <div class="float-layer-i"></div>
                         <div>
                            <#if ($post_data['activity_type'] == 8)
                                 <span class="float-layer-left">活动总订单数</span>
                             <#elseif>($post_data['activity_type'] == 7)
                                 <span class="float-layer-left">活动总积分数</span>
                             <#else>
                                 <span class="float-layer-left">活动订单数</span>
                             </#if>
                             <#if  ($post_data['activity_type'] == 1)
                                 <span class="float-layer-right">拼团活动的订单数</span>
                             <#elseif> ($post_data['activity_type'] == 3)
                                 <span class="float-layer-right">砍价活动的订单数</span>
                             <#elseif> ($post_data['activity_type'] == 5)
                                 <span class="float-layer-right">秒杀活动的订单数</span>
                             <#elseif> ($post_data['activity_type'] == 6)
                                 <span class="float-layer-right">表单活动的订单数</span>
                             <#elseif> ($post_data['activity_type'] == 7)
                                 <span class="float-layer-right">活动设置的积分池总数</span>
                             <#elseif> ($post_data['activity_type'] == 8)
                                 <span class="float-layer-right">活动订单数</span>
                             </#if>
                        </div>
                         <div>
                             <#if ($post_data['activity_type'] == 8)
                                <span class="float-layer-left">奖品发放数量</span>
                            <#elseif>($post_data['activity_type'] == 7)
                                <span class="float-layer-left">已瓜分积分数</span>
                            <#else>
                                <span class="float-layer-left">活动订单总金额</span>
                            </#if>
                            <#if  ($post_data['activity_type'] == 1)
                                <span class="float-layer-right">拼团活动的订单总金额</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                                <span class="float-layer-right">砍价活动的订单总金额</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                                <span class="float-layer-right">秒杀活动的订单总金额</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                                <span class="float-layer-right">表单活动的订单总金额</span>
                            <#elseif> ($post_data['activity_type'] == 7)
                                <span class="float-layer-right">已瓜分的积分总数</span>
                            <#elseif> ($post_data['activity_type'] == 8)
                                <span class="float-layer-right">已发放奖品数量</span>
                            </#if>
                        </div>
                    </div>
                </span>
			</div>
			<div id="container-order" style="height: 400px; width: 100%;"></div>
		</div>
		<div class="many-people">
			<div class="order-people">
				<span class="tab-bar"></span>
				<span>活动用户统计</span>
                <div>
                    <span>访问用户</span>
                    <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer common-width-height" style="left: -100px; height: 120px;">
                        <div class="float-layer-i" style="left: 93px;"></div>
                        <div>
                            <span class="float-layer-left">访问用户数</span>
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">浏览拼团商品详情页的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">浏览砍价商品详情页的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">浏览秒杀商品详情页的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-right">浏览表单的用户数</span>
                            </#if>
                        </div>
                        <div>
                            <span class="float-layer-left">访问用户数占比</span>
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">浏览拼团商品详情页的用户数/进入小程序的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">浏览砍价商品详情页的用户数/进入小程序的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">浏览秒杀商品详情页的用户数/进入小程序的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-right">浏览表单的用户数/进入小程序的用户数</span>
                            </#if>
                        </div>
                    </div>
                </span>
                </div>
			</div>
			<!-- 访问用户 -->
			<div id="container-user" class="user_chart" style="height: 400px; width: 100%;display: none"></div>
            
            <#if  ($post_data['activity_type'] != 6)
            <div class="two-title">
                <#if  ($post_data['activity_type'] == 3)
                <span>砍价用户</span>
                <#elseif> ($post_data['activity_type'] == 5)
                <span>秒杀用户</span>
                <#elseif> ($post_data['activity_type'] == 1)
                <span>参团用户</span>
                <#elseif> ($post_data['activity_type'] == 7)
                <span>参团用户</span>
                <#elseif> ($post_data['activity_type'] == 8)
                <span>参团用户</span>
                <#elseif> ($post_data['activity_type'] == 9)
                <span>购买用户</span>
                </#if>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer common-width-height" style="left: -100px; height: 100px;">
                        <div class="float-layer-i" style="left: 93px;"></div>
                        <div>
                            <#if  ($post_data['activity_type'] == 3)
                            <span class="float-layer-left">砍价用户数</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-left">秒杀用户数</span>
                            <#elseif> ($post_data['activity_type'] == 1)
                            <span class="float-layer-left">参团用户数</span>
                            <#elseif> ($post_data['activity_type'] == 7)
                                <span>参团用户数</span>
                            <#elseif> ($post_data['activity_type'] == 8)
                                <span>参团用户数</span>
                            <#elseif> ($post_data['activity_type'] == 9)
                                <span>购买用户数</span>
                            </#if>

                            
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">参与拼团活动的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">参与砍价活动的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">参与秒杀活动的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 7)
                                    <span>参团用户数</span>
                                <#elseif> ($post_data['activity_type'] == 8)
                                    <span>参团用户数</span>
                                <#elseif> ($post_data['activity_type'] == 9)
                                    <span>购买用户数</span>
                            </#if>
                        </div>
                        <div>
                            <#if  ($post_data['activity_type'] == 3)
                            <span class="float-layer-left">砍价用户数占比</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-left">秒杀用户数占比</span>
                            <#elseif> ($post_data['activity_type'] == 1)
                            <span class="float-layer-left">参团用户数占比</span>
                            <#elseif> ($post_data['activity_type'] == 7)
                                <span>参团用户数占比</span>
                            <#elseif> ($post_data['activity_type'] == 8)
                                <span>参团用户数占比</span>
                            </#if>

                            
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">参与拼团活动用户数/访问拼团活动用户数</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">参与砍价活动用户数/访问砍价活动用户数</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">参与秒杀活动用户数/访问秒杀活动用户数</span>
                                <#elseif> ($post_data['activity_type'] == 7)
                                    <span>参与瓜分积分活动用户数/访问瓜分积分活动用户数</span>
                                <#elseif> ($post_data['activity_type'] == 8)
                                    <span>参与拼团抽奖活动用户数/访问拼团抽奖活动用户数</span>
                            </#if>
                        </div>
                    </div>
                </span>
            </div>
            </#if>

            <!-- 参团用户数 -->
			<div id="container-join" class="join_chart" style="height: 400px; width: 100%;display: none"></div>
            <#if ($post_data['activity_type'] != 9)
            <div class="two-title">
                <#if  ($post_data['activity_type'] == 1)
                <span>成团用户</span>
                <#elseif> ($post_data['activity_type'] == 3)
                <span>砍价成功用户</span>
                <#elseif> ($post_data['activity_type'] == 5)
                <span>秒杀成功用户</span>
                <#elseif> ($post_data['activity_type'] == 6)
                <span>提交用户</span>
                <#elseif> ($post_data['activity_type'] == 7)
                    <span>成团用户</span>
                <#elseif> ($post_data['activity_type'] == 8)
                    <span>成团用户</span>
                </#if>
                <span class="item-image">
                    <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                    <div class="float-layer common-width-height" style="left: -100px; height: 120px; width: 475px">
                        <div class="float-layer-i" style="left: 93px;"></div>
                        <div>
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-left">成团用户数</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-left">砍价成功用户数</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-left">秒杀成功用户数</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-left">提交用户数</span>
                            <#elseif> ($post_data['activity_type'] == 7)
                                <span>成团用户数</span>
                            <#elseif> ($post_data['activity_type'] == 8)
                                <span>成团用户数</span>
                            </#if>

                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">拼团成功的用户数</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">砍价成功用户数(包含退货退款用户数量)</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">秒杀成功用户数(包含退货退款用户数量)</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-right">表单提交用户数</span>
                                <#elseif> ($post_data['activity_type'] == 7)
                                    <span>拼团成功的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 8)
                                    <span>拼团成功的用户数</span>
                            </#if>
                        </div>
                        <div>
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-left">成团用户占比</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-left">砍价成功用户占比</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-left">秒杀成功用户占比</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-left">提交用户占比</span>
                            <#elseif> ($post_data['activity_type'] == 7)
                                <span>成团用户占比</span>
                            <#elseif> ($post_data['activity_type'] == 8)
                                <span>成团用户占比</span>
                            </#if>
                            
                            <#if  ($post_data['activity_type'] == 1)
                            <span class="float-layer-right">拼团成功用户数/参与拼团活动用户数</span>
                            <#elseif> ($post_data['activity_type'] == 3)
                            <span class="float-layer-right">砍价成功用户数/参与砍价活动用户数</span>
                            <#elseif> ($post_data['activity_type'] == 5)
                            <span class="float-layer-right">秒杀成功用户数/参与秒杀活动用户数</span>
                            <#elseif> ($post_data['activity_type'] == 6)
                            <span class="float-layer-right">提交用户数/访问表单用户数</span>
                                <#elseif> ($post_data['activity_type'] == 7)
                                    <span>拼团成功用户数/参与拼团活动用户数</span>
                                <#elseif> ($post_data['activity_type'] == 8)
                                    <span>拼团成功用户数/参与拼团活动用户数</span>
                            </#if>
                        </div>
                    </div>
                </span>
            </div>
           </#if>
			<!-- 成功用户数 -->
			<div id="container-success" class="success_chart" style="height: 400px; width: 100%;display: none"></div>

            <#if  ($post_data['activity_type'] != 5 && $post_data['activity_type'] != 9)
                <div class="two-title">
                    <span>分享用户</span>
                    <span class="item-image">
                        <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                        <div class="float-layer common-width-height" style="left: -100px; height: 95px;">
                            <div class="float-layer-i" style="left: 93px;"></div>
                            <div>
                                <span class="float-layer-left">分享用户数</span>
                                <#if  ($post_data['activity_type'] == 1)
                                <span class="float-layer-right">分享拼团活动的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 3)
                                <span class="float-layer-right">分享砍价活动的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 5)
                                <span class="float-layer-right">分享秒杀活动的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 6)
                                <span class="float-layer-right">分享表单的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 7)
                                <span class="float-layer-right">分享瓜积分的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 8)
                                <span class="float-layer-right">分享拼团抽奖的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 9)
                                <span class="float-layer-right">分享限时降价的用户数</span>
                                </#if>
                            </div>
                            <div>
                                <span class="float-layer-left">分享用户数占比</span>
                                <#if  ($post_data['activity_type'] == 1)
                                <span class="float-layer-right">分享用户数/访问拼团活动用户数</span>
                                <#elseif> ($post_data['activity_type'] == 3)
                                <span class="float-layer-right">分享用户数/访问砍价活动用户数</span>
                                <#elseif> ($post_data['activity_type'] == 5)
                                <span class="float-layer-right">分享用户数/访问秒杀活动用户数</span>
                                <#elseif> ($post_data['activity_type'] == 6)
                                <span class="float-layer-right">分享用户数/访问表单用户数</span>
                                <#elseif> ($post_data['activity_type'] == 7)
                                    <span class="float-layer-right">分享用户数/访问瓜积分的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 8)
                                    <span class="float-layer-right">分享用户数/访问拼团抽奖的用户数</span>
                                <#elseif> ($post_data['activity_type'] == 9)
                                    <span class="float-layer-right">分享用户数/访问限时降价的用户数</span>
                                </#if>
                            </div>
                        </div>
                    </span>
                </div>
            </#if>
           
            <!-- 分享用户数 -->
            <div id="container-share" class="share_chart" style="height: 400px; width: 100%;display: none"></div>
            <#if  ($post_data['activity_type'] != 3 && $post_data['activity_type'] != 9)
                <div class="two-title">
                    <span>拉新用户数</span>
                    <span class="item-image">
                        <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                        <div class="float-layer common-width-height" style="left: -100px; height: 60px; width:300px;">
                            <div class="float-layer-i" style="left: 93px;"></div>
                            <div>
                                <span class="float-layer-left" style="width: 36%;">拉新用户数</span>
                                <#if  ($post_data['activity_type'] == 1)
                                <span class="float-layer-right" style="width: 54%;">拼团活动带来的新用户</span>
                                <#elseif> ($post_data['activity_type'] == 3)
                                <span class="float-layer-right" style="width: 54%;">砍价活动带来的新用户</span>
                                <#elseif> ($post_data['activity_type'] == 5)
                                <span class="float-layer-right" style="width: 54%;">秒杀活动带来的新用户</span>
                                <#elseif> ($post_data['activity_type'] == 6)
                                <span class="float-layer-right" style="width: 54%;">表单活动带来的新用户</span>
                                </#if>
                            </div>
                        </div>
                    </span>
                </div>
            </#if>
            
            <!-- 砍价人次 -->
            <div id="container-bargain" class="bargain_user_chart" style="height: 400px; width: 100%;display: none"></div>
            <#if  ($post_data['activity_type'] == 3)
                <div class="two-title">
                    <span>拉新用户数</span>
                    <span class="item-image">
                        <img src="/image/admin/analysis_tishi.png" class="zenticon-help-circle">
                        <div class="float-layer common-width-height" style="left: -100px; height: 60px; width:300px;">
                            <div class="float-layer-i" style="left: 93px;"></div>
                            <div>
                                <span class="float-layer-left" style="width: 36%;">拉新用户数</span>
                                <#if  ($post_data['activity_type'] == 1)
                                <span class="float-layer-right" style="width: 54%;">拼团活动带来的新用户</span>
                                <#elseif> ($post_data['activity_type'] == 3)
                                <span class="float-layer-right" style="width: 54%;">砍价活动带来的新用户</span>
                                <#elseif> ($post_data['activity_type'] == 5)
                                <span class="float-layer-right" style="width: 54%;">秒杀活动带来的新用户</span>
                                <#elseif> ($post_data['activity_type'] == 6)
                                <span class="float-layer-right" style="width: 54%;">表单活动带来的新用户</span>
                                </#if>
                            </div>
                        </div>
                    </span>
                </div>
            </#if>
			<!-- 活动拉新用户数 -->
			<div id="container-new" class="new_user_chart" style="height: 400px; width: 100%;display: none"></div>
		</div>
	</div>
</div>
<script type="text/javascript" src="/js/echarts.min.js"></script>
<script>

    var chart_data = $.parseJSON(@json($chart_data));
    console.log(chart_data);
    show_charts(chart_data);
	function show_charts(chart_data) {
        if(chart_data.use_chart.show){
            $(".use_chart").css('display','block');
            var dom = document.getElementById("container-use");
            var myChart = echarts.init(dom);
            var option = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:chart_data.use_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:chart_data.use_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.use_chart.currently
                    },
                    {
                        name:chart_data.use_chart.legend[1],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#ff9f7f',
                                lineStyle:{
                                    color:'#ff9f7f'
                                }
                            }
                        },
                        data:chart_data.use_chart.expired
                    },
                    {
                        name:chart_data.use_chart.legend[2],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#3cb2ef',
                                lineStyle:{
                                    color:'#3cb2ef'
                                }
                            }
                        },
                        data:chart_data.use_chart.closed
                    }
                ]
            };
            myChart.setOption(option);
        }

        if(chart_data.order_chart.show){
            $(".order_chart").css('display','block');
            var dom1 = document.getElementById("container-order");
            var myChart1 = echarts.init(dom1);
            var option1 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:chart_data.order_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                yAxis: [
                    {
                        type: 'value'
                    },
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name:chart_data.order_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.order_chart.order_num
                    },
                    {
                        name:chart_data.order_chart.legend[1],
                        type:'line',
                        yAxisIndex: 1,
                        itemStyle : {
                            normal : {
                                color: '#ff9f7f',
                                lineStyle:{
                                    color:'#ff9f7f'
                                }
                            }
                        },
                        data:chart_data.order_chart.order_money
                    }
                ]
            };
            myChart1.setOption(option1);
        }

        if(chart_data.user_chart.show){
            $(".many-people .user_chart").css('display','block');
            var dom2 = document.getElementById("container-user");
            var myChart2 = echarts.init(dom2);
            var option2 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:chart_data.user_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                // yAxis: {
                //     type: 'value'
                // },
                yAxis: [
                    {
                        type: 'value'
                    },
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name:chart_data.user_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.user_chart.visited
                    },
                    {
                        name: chart_data.user_chart.legend[1],
                        type: 'line',
                        yAxisIndex: 1,
                        itemStyle: {
                            normal: {
                                color: '#ff9f7f',
                                lineStyle: {
                                    color: '#ff9f7f'
                                }
                            }
                        },
                        data: chart_data.user_chart.visited_ratio
                    }
                ]
            };
            myChart2.setOption(option2);
        }

        if(chart_data.join_chart.show){
            $(".many-people .join_chart").css('display','block');
            var dom3 = document.getElementById("container-join");
            var myChart3 = echarts.init(dom3);
            var option3 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:chart_data.join_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                // yAxis: {
                //     type: 'value'
                // },
                yAxis: [
                    {
                        type: 'value'
                    },
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name:chart_data.join_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.join_chart.join_user
                    },
                    {
                        name: chart_data.join_chart.legend[1],
                        type: 'line',
                        yAxisIndex: 1,
                        itemStyle: {
                            normal: {
                                color: '#ff9f7f',
                                lineStyle: {
                                    color: '#ff9f7f'
                                }
                            }
                        },
                        data: chart_data.join_chart.join_user_ratio
                    }
                ]
            };
            myChart3.setOption(option3);
        }

        if(chart_data.success_chart.show){
            $(".many-people .success_chart").css('display','block');
            var dom4 = document.getElementById("container-success");
            var myChart4 = echarts.init(dom4);
            var option4 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:chart_data.success_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                // yAxis: {
                //     type: 'value'
                // },
                yAxis: [
                    {
                        type: 'value'
                    },
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name:chart_data.success_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.success_chart.success_user
                    },
                    {
                        name: chart_data.success_chart.legend[1],
                        type: 'line',
                        yAxisIndex: 1,
                        itemStyle: {
                            normal: {
                                color: '#ff9f7f',
                                lineStyle: {
                                    color: '#ff9f7f'
                                }
                            }
                        },
                        data: chart_data.success_chart.success_user_ratio
                    }
                ]
            };
            myChart4.setOption(option4);
        }

        if(chart_data.share_chart.show){
            $(".many-people .share_chart").css('display','block');
            var dom5 = document.getElementById("container-share");
            var myChart5 = echarts.init(dom5);
            var option5 = {
                tooltip: {
                    trigger: 'axis'
                },
                // calculable : true,
                legend: {
                    data:chart_data.share_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                // yAxis: {
                //     type: 'value'
                // },
                yAxis: [
                    {
                        type: 'value'
                    },
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name:chart_data.share_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.share_chart.share_user
                    },
                    {
                        name: chart_data.share_chart.legend[1],
                        type: 'line',
                        yAxisIndex: 1,
                        itemStyle: {
                            normal: {
                                color: '#ff9f7f',
                                lineStyle: {
                                    color: '#ff9f7f'
                                }
                            }
                        },
                        data: chart_data.share_chart.share_user_ratio
                    }
                ]
            };
            myChart5.setOption(option5);
        }

        if(chart_data.bargain_user_chart.show){
            $(".many-people .bargain_user_chart").css('display','block');
            var dom7 = document.getElementById("container-bargain");
            var myChart7 = echarts.init(dom7);
            var option7 = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:chart_data.bargain_user_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:chart_data.bargain_user_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.bargain_user_chart.bargain_user_count
                    }
                ]
            };
            myChart7.setOption(option7);
        }

        if(chart_data.new_user_chart.show){
            $(".many-people .new_user_chart").css('display','block');
            var dom8 = document.getElementById("container-new");
            var myChart8 = echarts.init(dom8);
            var option8 = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:chart_data.new_user_chart.legend
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: chart_data.date_list
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name:chart_data.new_user_chart.legend[0],
                        type:'line',
                        itemStyle : {
                            normal : {
                                color: '#9fe6b8',
                                lineStyle:{
                                    color:'#9fe6b8'
                                }
                            }
                        },
                        data:chart_data.new_user_chart.new_user
                    }
                ]
            };
            myChart8.setOption(option8);
        }
    }

    $(".select_visit_trend").change(function () {
        console.log($("#form1").serialize())
        util.ajax_json('/system/statistics/shop/activity',function (res) {
            console.log(res);
            if(res.error == 0){
                $("[name='type']").val(res.content.post_data.type);
                $(".start_date").html(res.content.start_date);
                $(".end_date").html(res.content.end_date);
                show_charts(res.content.chart_data)
            }else{
                util.mobile_alert('加载失败');
                $(".select_visit_trend").val($("[name='type']").val());
            }
        },$("#form1").serialize())
    })

    $('.item-image').hover(function () {
        $(this).find('.float-layer').show();
    },function () {
        $(this).find('.float-layer').hide();
    });
</script>
<#include ("system.footer")