<#include ("system.header")
<#include ("system.features_nav")
<link rel="stylesheet" href="/css/system/use_features.css?v=1.0.0" type="text/css">

<div style="min-width: 1090px;">
    <div class="order-container">
	    <div class="order-info">
	        <form action="/system/statistics/view" method="post" id="form1">
				{{csrf_field()!}
	            {{--<input type="hidden" name="page" value="1">--!}
	            {{--<input type="hidden" class="shop_id" name="shop_id"  value="0" />--!}
	            <ul>
	                <li class="order-info-li clearfix order-common">
	                    <div class="">
	                       共有店铺<span>${shop_type['v4']+$shop_type['v3']+$shop_type['v2']+$shop_type['v1']!}</span>个，其中<span>${shop_type['v4']!}</span>个旗舰版，<span>${shop_type['v3']!}</span>个高级版，<span>${shop_type['v2']!}</span>个基础版，<span>${shop_type['v1']!}</span>个体验版
	                    </div>
	                </li>
	                <li class="order-info-li clearfix">
	                    <div class="fl" style="width: auto;">
							<span>筛选时间</span>
							<select name="type" class="select_visit_trend" id="">
								<option value="1" selected>查看1天</option>
								<option value="7" <#if ($type==7) selected </#if>>最近7天</option>
								<option value="30" <#if ($type==30) selected </#if>>最近30天</option>
							</select>
							<span class="layui-card-item" style="width: auto;">${date[0]!} - ${date[1]!}</span>
						</div>
	                    <button class="btn-choose">筛选</button>
	                    {{--<button class="btn-choose-new">重置</button>--!}
	                </li>
	            </ul>
	        </form>
	    </div>
	    <div>
	        <form action="" id="form2" method="">
	            <input type="hidden" class="shop_id" name="shop_id"  value="1" />
	            <div class="member_list_main">
	                <table width="100%">
	                    <thead>
	                        <tr>
	                            <td width="8%">功能名称</td>
	                            <td width="10%">有权限店铺数量</td>
	                            <td width="10%">已使用店铺数量</td>
	                            <td width="10%">未使用店铺数量</td>
	                            <td width="8%">使用率</td>
	                            <td width="10%">正在使用的店铺数量</td>
	                            <td width="10%">正在进行的活动数量</td>
	                            <td width="10%">操作</td>
	                        </tr>
	                    </thead>
	                    <tbody>
							<#list ($data_list as $value)
	                        <tr>
	                        	<td><a href="/system/statistics/activity?activity_type=${value->activity_type!}" class="btn_opera" target="_blank">${activity[$value->activity_type]!}</a></td>
	                        	<td>${value->shop_num!}</td>
	                        	<td>${value->use!}</td>
	                        	<td>${value->nouse!}</td>
	                        	<td>${value->shop_num != 0 ? number_format($value->use/$value->shop_num*100,2) : '--'!}%</td>
	                        	<td>${value->ingoing!}</td>
	                        	<td>${value->num!}</td>
	                        	<td><a href="/system/statistics/activity?activity_type=${value->activity_type!}" class="btn_opera">查看详情</a></td>
	                        </tr>
							</#list>
	                    </tbody>
	                </table>
	            </div>
	            <div class="member_list_footer">
	            </div>
	        </form>
	    </div>
    </div>
</div>


<div id="set-delete" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="is_delete"/>
    <div class="exchange_old">
        <span>提示:&nbsp;</span>禁止登录后会员将不能登录了，确定禁止登录吗？
    </div>
</div>
<div id="all-delete" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="is_delete"/>
    <div class="exchange_old">
        <span>提示:&nbsp;</span>禁止登录后会员将不能登录了，确定禁止登录吗？
    </div>
</div>




<#include ("system.footer")
<script>


// 筛选
	$('.btn-choose').click(function () {
		$("#form1").submit();
    })
	$(".select_visit_trend").change(function () {
        $("#form1").submit();
    })

</script>