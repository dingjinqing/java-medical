<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/gift_manage.css?v=1.0.5" type="text/css" />
<link rel="stylesheet" href="/css/admin/user_list.css">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css" />
<style type="text/css">
    .button-choose:hover{
        background-color: #447af9;
    }
</style>
<div style="min-width:1090px">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span style="color: #666;">发放明细</span>
    </div>
    <div class="main-container">
        <form action="/admin/market/activityreward/detail" method="post" id="form1">
            <input type="hidden" name="id" value="${request['id'] ?? 0!}"/>
            {{csrf_field()!}
        {{--<div class="normal_coupon">
            <div class="nav-role">
                <ul id="tab" class="nav-child-tabs">
                    <li>
                        <a href="" >全部特殊活动发券</a>
                    </li>
                    <li>
                        <a href="" >进行中</a>
                    </li>
                    <li>
                        <a href="" >未开始</a>
                    </li>
                    <li>
                        <a href="">已过期</a>
                    </li>
                    <li>
                        <a href="">已停用</a>
                    </li>
                    <li class="active">
                        <a href="">领取明细</a>
                    </li>
                </ul>
            </div>--!}

            <ul class="add-detail-ul">
                <li class="detail-info-li">
                    <div class="li-ff">
                        <span>手机号:</span>
                        <input type="text" name="mobile"  placeholder="请输入手机号" value="${request['mobile']!}"/>
                    </div>
                    <div class="li-ff">
                        <span>用户昵称：</span>
                        <input type="text" name="username"  value="${request['username']!}"/>
                    </div>
                    <div class="li-ff search-button" >
                        {{--<span>使用状态：</span>
                        <select name="" id="">
                            <option value="-1">全部</option>
                            <option value="0">打扰了</option>
                            <option value="1">打扰了</option>
                        </select>--!}
                        <button class="button-choose" type="submit" style="margin-top: 10px; line-height: 26px;">搜索</button>
                    </div>
                </li>
            </ul>
            <div class="return-goods-box">
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr>
                                    <th width="8%">用户ID</th>
                                    <th width="14%">用户昵称</th>
                                    <th width="14%">手机号码</th>
                                    <th width="14%">活动名称</th>
                                    <th width="20%">领取时间</th>
                                    <th width="30%">领取优惠券</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list  ($list as $item)
                                <tr>
                                    <td>${item->user_id!}</td>
                                    <td><a target="_blank" href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->username!}</a></td>
                                    <td>${item->mobile!}</td>
                                    <td>${item->name!}</td>
                                    <td>${item->receive_time!}</td>
                                    <td>
                                        <#list  ($item->couponArr as $coupon)
                                            <a href="/admin/market/coupon/edit?id=${coupon->id!}" target="_blank">${coupon->act_name!};</a>
                                        </#list>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                <div class="clearfix" style="margin-top: 20px;">
                  <#include "/admin/jump_page_admin.ftl">
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    // getPowerInfo('main_config','coupon_gift','sub_4','活动送券',0);
    getPowerInfo('main_config','activity_reward','sub_4','活动有礼',0);
</script>