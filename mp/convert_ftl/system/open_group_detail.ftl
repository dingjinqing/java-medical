<#include ("system.header")
<link rel="stylesheet" href="/css/system/open_group_detail.css" type="text/css" />
<style>
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
    select {
        height: 30px;
        line-height: 30px;
        padding-left: 12px;
        border: 1px solid #ccc;
        width: 150px;
    }
</style>
<div style="min-width: 1090px;">
	{{--<div class="title">--!}
        {{--<span><a href="/system/market/view?top_index=4">营销管理</a> / </span>--!}
        {{--<span><a href="/system/market/groupdraw/list?top_index=4">拼团抽奖</a> / </span>--!}
        {{--<span style="color: #666;">${name!} - 开团明细</span>--!}
    {{--</div>--!}
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
    					<span>开团时间</span>
    					<input type="text" name="start_time" value="${request['start_time']!}" placeholder="" onclick="picker();" autocomplete="off"> 至 <input type="text" name="end_time" value="${request['end_time']!}" placeholder="" onclick="picker();" autocomplete="off">
    				</div>
                    <div class="fl">
                        <span>团ID</span>
                        <input type="text" value="${request['group_id']!}" name="group_id">
                    </div>
    			</li>
    			<li class="check_info_li clearfix">
                    <div class="fl">
                        <span>手机号</span>
                        <input type="text" value="${request['mobile']!}" name="mobile" placeholder="请输入手机号">
                    </div>
    				<div class="fl">
                        <span>成团状态</span>
                        <select name="is_finish" id="">
                            <option value="0" selected>请选择</option>
                            <option value="1" <#if ($request['is_finish'] == 1) selected </#if>>已成团</option>
                            <option value="-1" <#if ($request['is_finish'] == -1) selected </#if>>未成团</option>
                        </select>
                    </div>
    				<div class="fl anniu2" style="margin-left: 25px;width: 200px;">
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
                                <th > 团ID </th>
                                <th > 参团人数 </th>
                                <th width="20%;"> 活动商品 </th>
                                <th > 开团时间 </th>
                                <th > 团长昵称 </th>
                                <th > 团长手机号 </th>
                                <th > 成团时间 </th>
                            </tr>
                        </thead>
                        <tbody>
						<#list ($data_list as $item)
                            <tr>
                            	<td>${item->group_id!}</td>
                            	<td><a href="/system/market/groupdraw/user?group_id=${item->group_id!}&top_index=4&id=${item->group_draw_id!}" target="_blank">${item->group_num!}</a></td>
                            	<td>
                                    <img src="${item->goods_img!}" alt="" class="name-img">
                                    <span class="list-name">
                                        <span style="margin-left: -50px;">${item->goods_name!}</span>
                                    </span>
                                </td>
                            	<td>${item->open_time!}</td>
                            	<td><a href="/system/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" target="_blank">${item->username!}</a></td>
                            	<td>${item->mobile!}</td>
                            	<td>${item->end_time!} </td>
                            </tr>
						</#list>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="paging_footer">
                <#include ("system.jump_page_system")
            </div>
        </div>
        </form>
    </div>
</div>

<#include ("system.footer")

<script>
	function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    $('.reset-searach').click(function () {
		$('.order_search_info').find("input").each(function (i,v) {
            $('.order_search_info').find("input").eq(i).val("");
        })
        $('select[name="is_finish"]').find('[value=0]').prop('selected', 'selected');
    })
    $(".btn-search").click(function () {
        $("#form1").submit();
    })
</script>
<script type="text/javascript">
    // getPowerInfo('main_config','group_draw','sub_4','拼团抽奖',0);
</script>