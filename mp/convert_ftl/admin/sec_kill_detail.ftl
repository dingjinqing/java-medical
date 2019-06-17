<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sec_kill.css?v=1.0.1" type="text/css" />
<style>
    .tb-decorate-a{
        position: relative;
    }
    .tb-decorate-a .share_span{
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 48px;
        left:-90px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }
    .tb-decorate-a .a{
        width: 28px;
        height:30px;
        display: inline-block;
    }
    .tb-decorate-a .share_span .share_sj{
        position: absolute;
        right: 120px;
        top: -7px;
    }
    .tb-decorate-a .share_span span{
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }
    .tb-decorate-a .share_span .code_imgs{
        display: block;
        margin:0 auto;
    }
    .tb-decorate-a .share_span a{
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
    }
    .share_link{
        padding-top: 15px;
        width: 100%;
    }
    .share_link input{
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }
    .share_link button{
        float: right;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
    }
    .add-child-btn:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .add-child-btn:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .title div a.active {
        color: #5a8bff;
    }
    .limit_height{
       word-break: break-all;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div style="float: left;">
            <span><a href="/admin/market/view?top_index=4">{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
            <span> <a href="/admin/market/seckill/list?nav=1">秒杀</a> / </span><span><span class="ellipsis">${basicData->name!}</span> - </span>
            <span style="color: #666;">秒杀用户明细</span>
        </div>
        {{--<div style="float: right; margin-right: 10px;">--!}
            {{--<a href="/admin/market/seckill/detail?id=${request['sk_id']!}" <#if  ($request['is_grouper'] == 0) class="active" </#if>>参团用户列表</a> |--!}
            {{--<a href="/admin/market/seckill/detail?id=${request['sk_id']!}" <#if  ($request['is_grouper'] == 1) class="active" </#if>>团列表</a>--!}
        {{--</div>--!}
    </div>
    <div class="main-container">
        <form action="/admin/market/seckill/detail?id=${request['sk_id']!}" method="post" id="form1" name="form1">
            {{ csrf_field()!}
        <div class="box panel panel-body list-center-fee">
            <div class="form-inline shop-template-container">
                <ul class="clearfix">
                    <li class="sec-kill-info-li clearfix">
                        <div class="fl">
                            <span>
                                用户手机号
                            </span>
                            <input type="text" name="mobile" placeholder="手机号" value="${request['mobile']!}"/>
                        </div>
                        <div class="fl">
                            <span>
                                用户昵称
                            </span>
                            <input type="text" name="username" placeholder="昵称" value="${request['username']!}"/>
                        </div>
                        <div class="fl" style="width: 315px;">
                            <span>购买数量</span>
                            <input type="text" style="width: 80px" name="start"  placeholder="" value="${request['start']!}"/>
                            至
                            <input type="text" style="width: 80px" name="end" placeholder="" value="${request['end']!}"/>
                            {{--<select name="status" id="status">--!}
                                {{--<option value="-1">全部</option>--!}
                                {{--<option value="0" <#if  (isset($request['status']) && $request['status'] == 0) selected </#if>>成团中</option>--!}
                                {{--<option value="1" <#if  ($request['status'] == 1) selected </#if>>已成团</option>--!}
                                {{--<option value="2" <#if  ($request['status'] == 2) selected </#if>>未成团</option>--!}
                            {{--</select>--!}
                        </div>
                        <div class="fl">
                            <button type="submit"  class="sec-kill-search">搜索</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="return-sec-kill-box" style="padding-top: 10px;">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                                <tr class="get-list-th">
                                    <th width="12%"> 商品 </th>
                                    <th width="7%"> 用户ID </th>
                                    <th width="10%"> 用户昵称</th>
                                    <th width="11%"> 手机号码 </th>
                                    <th width="13%"> 订单号 </th>
                                    <th width="13%"> 购买时间 </th>
                                    <th width="13%"> 购买数量 </th>
                                </tr>
                            </thead>
                            <tbody>

                                <#list ($list as $item)
                                    <tr>
                                        <td class="limit_height"><span class="wrapper">${item->goods_name!}</span></td>
                                        <td>${item->user_id!}</td>
                                        <td>${item->username!}</td>
                                        <td>${item->mobile!}</td>
                                        <td>${item->order_sn!}</td>
                                        <td><#if (empty($item->pay_time))${item->add_time!}<#else> ${item->pay_time!}</#if></td>
                                        <td>${item->goods_amount!}</td>
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
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','seckill_goods','sub_4','抽奖',0);
    $(".wrapper").each(function () {
        $.trim($(this).val());
        var tt=$(this).text();
        var tl=tt.length;
        var s=tt.toString();
        if(tl>'40'){
            var ss=s.substring(0,40)+"...";
        }
        $(this).text(ss);
    })
</script>
