<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/participate_user_list.css" type="text/css" />
<style type="text/css">
	.anniu2{
		display: flex;
		justify-content: space-around;

	}
	.check_info_li .btn-search{
		line-height: 22px;
		margin: 0;
	}
	.anniu2 .reset-searach{
		line-height: 22px;
	}
	td	a{
		color:#5A8BFF;
	}
	select {
		height: 30px;
		line-height: 30px;
		padding-left: 12px;
		border: 1px solid #ccc;
		width: 150px;
	}
</style>
<div style="min-width: 1090px;">
	<div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span><a href="/admin/market/groupdraw/list?top_index=4">拼团抽奖</a> / </span>
        <span style="color: #666;">${name!} - 参与用户</span>
    </div>
    <div class="main-container">
		<form id="form1" action="" method="post" name="form1">
			{{csrf_field()!}
    	<div class="order_search_info">
    		<ul class="clearfix">
    			<li class="check_info_li clearfix">
    				<div class="fl">
    					<span>用户昵称</span>
    					<input type="text" value="${request['username']!}" name="username" placeholder="请输入用户昵称">
    				</div>
    				<div class="fl time_choose">
    					<span>参与时间</span>
    					<input type="text" name="start_time" placeholder="" value="${request['start_time']!}" onclick="picker();" autocomplete="off">
						至
						<input type="text" name="end_time" placeholder="" value="${request['end_time']!}" onclick="picker();" autocomplete="off">
    				</div>
    				<div class="fl">
    					<span>订单号</span>
    					<input type="text" value="${request['order_sn']!}" name="order_sn">
    				</div>
    			</li>
    			<li class="check_info_li clearfix">
    				<div class="fl">
    					<span>手机号</span>
    					<input type="text" value="${request['mobile']!}" name="mobile" placeholder="请输入手机号">
    				</div>
    				<div class="fl time_choose time_choose_number">
    					<span>邀请用户数</span>
    					<input type="text" name="min" value="${request['min']!}" class="user_nunber"> 至 <input type="text" name="max" value="${request['max']!}" class="user_nunber">
    				</div>
    				<div class="fl">
    					<span>团ID</span>
    					<input type="text" value="${request['group_id']!}" name="group_id">
    				</div>
    			</li>
				<li class="check_info_li clearfix">
					<div class="fl">
						<span>成团状态</span>
						<select name="is_finish" id="">
							<option value="0" selected>请选择</option>
							<option value="1" <#if ($request['is_finish'] == 1) selected </#if>>已成团</option>
							<option value="-1" <#if ($request['is_finish'] == -1) selected </#if>>未成团</option>
						</select>
					</div>
					<div class="fl">
						<span>是否团长</span>
						<select name="is_grouper" id="">
							<option value="0" selected>请选择</option>
							<option value="1" <#if ($request['is_grouper'] == 1) selected </#if>>是</option>
							<option value="-1" <#if ($request['is_grouper'] == -1) selected </#if>>否</option>
						</select>
					</div>
					<div class="fl clearfix anniu2" style="width: 200px;">
						<div type="button" class="btn-search">筛选</div>
						<div type="button" class="reset-searach">重置筛选</div>
					</div>
				</li>
    		</ul>
    	</div>
    	<div class="return-pin-group-box"  style="margin-top: 10px">
            <div class="goods-box-edit">
                <div class="goods-edit-basic">
                    <table class="tb-decorate-list">
                        <thead>
                            <tr class="get-list-th">
                                <th hidden> 用户ID </th>
                                <th > 昵称 </th>
                                <th > 手机号 </th>
                                <th > 参与时间 </th>
                                <th > 订单号 </th>
                                <th > 是否团长 </th>
                                <th > 团ID </th>
                                <th > 成团时间 </th>
                                <th > 抽奖码数量 </th>
                                <th> 邀请用户数 </th>
                            </tr>
                        </thead>
                        <tbody>
						<#list ($data_list as $item)
                            <tr>
                            	<td hidden>${item->user_id!}</td>
                            	<td><a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" target="_blank">${item->username!}</a></td>
                            	<td>${item->mobile!}</td>
                            	<td>${item->open_time!}</td>
                            	<td><a href="/admin/orders/manage/info?order_sn=${item->order_sn!}&top_index=3" target="_blank">${item->order_sn!}</a></td>
                            	<td>${item->is_grouper==1?'是':'否'!}</td>
                            	<td>${item->group_id!}</td>
								<td>${item->end_time!}</td>
								<td>${item->draw_num!}</td>
                            	<td>${item->invite_num!}</td>
                            </tr>
						</#list>
                        </tbody>
                    </table>
                </div>
            </div>
			<div class="paging_footer">
				<#include "/admin/jump_page_admin.ftl">
			</div>
        </div>
		</form>
    </div>
</div>

<#include "/admin/footer.ftl">

<script>
	function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    $('.reset-searach').click(function () {
		$(".order_search_info").find('input').each(function (i,v) {
			$(v).val("");
        })
        $('select[name="is_finish"]').find('[value=0]').prop('selected', 'selected');
        $('select[name="is_grouper"]').find('[value=0]').prop('selected', 'selected');
    })
	$(".btn-search").click(function () {
		$("#form1").submit();
    })
</script>
<script type="text/javascript">
    getPowerInfo('main_config','group_draw','sub_4','拼团抽奖',0);
</script>