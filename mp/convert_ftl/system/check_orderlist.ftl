<#include ("system.header")
<link rel="stylesheet" href="/css/admin/order_all.css?v=1.0.27" type="text/css"/>
<link rel="stylesheet" href="/css/admin/check_order.css?v=1.0.0" type="text/css"/>
<!-- <link rel="stylesheet" href="/css/admin/common.css?v=3.3.11"> -->
<link rel="stylesheet" href="/css/system/shop_pv.css" type="text/css" />
<link rel="stylesheet" href="/css/system/article_list.css" type="text/css" />
<style type="text/css">
    td{
        padding: 8px 10px;
    }
</style>


<div class="title">
    <span>订单管理 / </span><span style="color: #666;">买单订单</span>
</div>
<form action="" method="post" id="form1">
    
<div class="order-container">
    <div class="order_serarch_info">
        <ul class="clearfix">
            <li class="check_info_li clearfix">
                <div class="fl">
                    <span>订单号</span>
                    <input type="text" value="" name="order_sn" placeholder="请输入订单号">
                </div>
                <div class="fl time_choose">
                    <span>支付时间</span>
                    <input type="text" name="pay_time_start" placeholder="支付时间" value="" onclick="picker();" autocomplete="off">
                    至
                    <input type="text" name="pay_time_end" placeholder="支付时间" value="" onclick="picker();" autocomplete="off">
                </div>
            </li>
            <li class="check_info_li clearfix">
                <div class="fl">
                    <span>买单人</span>
                    <input type="text" value="" name="payer_name" placeholder="请输入买单人昵称">
                </div>
                <div class="fl">
                    <span>门店</span>
                    <select name="store_id" id="">
                        <option value="0">请选择</option>
                    
                            <option value="" ></option>
                    </select>
                </div>
                <div class="fl" style="margin-left: 48px;">
                    <button type="button" class="btn_search">筛选</button>
                    {{--<button type="button" class="btn_excel">导出表格</button> --!}
                </div>
            </li>
        </ul>
    </div>
    {{--买单总金额--!}
    <div class='money_amount'>
        <img src="" alt="">
        买单收银总金额 <span> 元</span>
    </div>
    <div class="check_list">
        <ul class="cl_banner clearfix">
            <li >
                <a href="/admin/order/check/list">全部</a>
            </li>
            <li >
                <a href="/admin/order/check/list?star_flag=1">追星订单</a>
            </li>
        </ul>
        <div class="checklist_table">
            <table width="100%">
                <thead>
                    <tr>
                        <td>买单门店</td>
                        <td>买单人</td>
                        <td>买单时间</td>
                        <td>买单收银金额</td>
                    </tr>
                    <tr class="jiange">
                        <td colspan="4"></td>
                    </tr>
                </thead>
                <tbody>
             
                    <tr class="check-tb-head">
                        <td colspan="4">
                            <span class="span1"> 支付单号：1234567890123</span>
                            <span class="span2">支付方式：微信支付</span>
                            <span class="fr" order_sn="">
                                <span class="btn-star-flag"
                                      title="切换星标"></span>
                                <a href="javascript:void(0);" class="add_text" order_sn=""
                                   seller_remark="">添加备注</a>
                                <a href="/system/order/check/info?order_sn=" class="click_to_detail" target="_blank">查看详情</a>
                            </span>
                        </td>
                    </tr>
                    {{--这里的tr循环订单下的多个买单--!}
                    <tr class="check_tb_body">
                        <td>牡丹园门店</td>
                        <td>人生若只如初见🙊</td>
                        <td>2018-08-09 14:01:25</td>
                        <td>0.00</td>
                    </tr>
                    {{--第二个循环结束--!}

                </tbody>
                <tbody style="border: none;">
                <tr>
                    <td colspan="8"></td>
                </tr>
                </tbody>
                {{--第一个循环结束--!}
            </table>
        </div>
        <div class="clearfix">
            <table width="100%" border="0" class="tb_paging" style="font-size: inherit;">
                <tr>
                    <td align="right">
                        <a href="#" onClick="return gopage(1);">首页</a>
                        <a href="#"
                           onClick="return gopage(1);">上一页</a>
                        <a href="#"
                           onClick="return gopage(1);">下一页</a>
                        <a href="#"
                           onClick="return gopage(1);">末页</a>
                        <input id="page" name="page" type="text" value=""
                               size="5"
                               onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
</form>

<div id="set-text">
    <textarea name="seller_remark" id="seller_remark"></textarea>
</div>

<script src="/js/admin/region.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/check_order.js?v=1.0.4" type="text/javascript"></script>
<script type="text/javascript">
    //分页
    
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<#include ("system.footer")