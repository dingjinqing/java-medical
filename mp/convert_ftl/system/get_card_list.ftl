<#include ("system.header")
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.51" type="text/css" />
<link rel="stylesheet" href="/css/system/user_list.css" type="text/css" />
<style>
    .order-info-li .fl{  width:25%; }
    .btn-choose:hover, .btn-choose:focus    {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none;
    }
    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    td .ipt-money:focus ,td .ipt-integral:focus{
        border: none !important;
        box-shadow: none !important;
        -webkit-box-shadow: none !important;
        -moz-box-shadow: none !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>会员管理 / </span>
        <span style="color: #666;">会员卡领取明细</span>
    </div>
    <div class="order-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                <input type="hidden"  name="shop_id"  value="${data->shop_id!}" />
                <input type="hidden"  name="page"  value="1" />
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>手机号码</span>
                            <input type="text" name="mobile" value="${data->mobile!}" placeholder="输入手机号码" />
                        </div>
                        <div class="fl">
                            <span>昵称</span>
                            <input type="text" name="username" value="${data->username!}" placeholder="输入昵称" />
                        </div>
                        <div class="fl" style="width:auto">
                            <span>领取时间</span>
                            <input type="text" name="start_time" value="${data->start_time!}" placeholder="" onclick="picker();"  autocomplete="off"/>
                            至
                            <input type="text" name="end_time" value="${data->end_time!}" onclick="picker();" placeholder=""  autocomplete="off"/>
                        </div>


                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>会员卡</span>
                            <select name="user_card" id="user_card">
                                <option value="">全部</option>
                                <#list ($card_list as $item)
                                    <option value="${item->id!}" <#if ($item->id==$data->user_card) selected </#if>>${item->card_name!}</option>
                                </#list>
                            </select>

                        </div>
                        <div class="fl">
                            <span>会员卡类型</span>
                            <select name="card_type" id="user_card">
                                <option value="-1">全部</option>
                                <option value="0" <#if (!is_null($data->card_type) && $data->card_type == 0) selected </#if>>普通卡</option>
                                <option value="1" <#if ($data->card_type == 1) selected </#if>>限次卡</option>
                            </select>
                        </div>
                        <button class="btn-choose">筛选</button>
                    </li>
                </ul>
            </form>
        </div>
        <div>
            <form action="" id="form2" method="">
                {{ csrf_field()!}
                <input type="hidden" class="user_id" name="user_id"  value="${user_id!}" />
                <input type="hidden" class="shop_id" name="shop_id"  value="${data->shop_id!}" />
                <input type="hidden" name="mobile" value="${data->mobile!}"/>
                <input type="hidden" name="username" value="${data->username!}"/>
                <input type="hidden" name="start_time" value="${data->start_time!}"/>
                <input type="hidden" name="end_time" value="${data->end_time!}"/>
                <div class="member_list_main">
                    <table width="100%">
                        <thead>
                            <tr>
                                <td>领取时间</td>
                                <td>会员卡号</td>
                                <td>会员</td>
                                <td>会员卡 | 类型</td>
                                <td>状态</td>
                                <td>余额（元）</td>
                                <td>次数（次）</td>
                                <td>操作</td>
                            </tr>
                        </thead>
                        <tbody>
                        <#if ($data_list)
                            <#list ($data_list as $item)
                            <tr id="user_${item->user_id!}">
                                <input type="hidden" class="user_id" value="${item->user_id!}" />
                                <input type="hidden" class="card_id" value="${item->card_id!}" />
                                <input type="hidden" class="card_no" value="${item->card_no!}" />
                                <td>${item->add_time!}</td>
                                <td>${item->card_no!}</td>
                                <td><a href="/system/user/card/list?shop_id=${data->shop_id!}&user_id=${item->user_id!}" >${item->username!}</a></td>
                                <td>
                                    ${item->card_name!}<br/>
                                    <#if ($item->card_type==0) 普通卡
                                    <#elseif>($item->card_type==1) 限次卡
                                    <#elseif>($item->card_type==2) 规则卡
                                    </#if>
                                </td>
                                <td>
                                    <#if ($item->flag==0)
                                        <#if ($item->expire_time<date("Y-m-d H:i:s",time()))
                                            已过期
                                        <#else>
                                            <#if ($item->activation == 1 && empty($item->activation_time))
                                                未激活
                                            <#else>
                                                正在使用
                                            </#if>
                                        </#if>
                                    <#elseif>($item->flag==1)
                                        已删除
                                    </#if>
                                </td>
                                <td>
                                    <#if ($item->card_type==0 && ($item->send_money || json_decode($item->charge_money)[0]->value != -1))
                                    <input type="text" value="${item->money!}" class="ipt-money" readonly="readonly" />
                                        <#if ($item->flag==0 && $item->expire_time>date("Y-m-d H:i:s",time()))
                                            <img src="http://${image_domain!}/image/admin/add_some.png" alt="" class="btn_money" />
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if ($item->card_type==1)
                                    <input type="text" value="${item->surplus!}" class="ipt-integral" readonly="readonly" />
                                        <#if ($item->flag==0 && $item->expire_time>date("Y-m-d H:i:s",time()))
                                            <img src="http://${image_domain!}/image/admin/add_some.png" alt="" class="btn_integral" />
                                        </#if>
                                    </#if>
                                </td>
                                <td>
                                    <#if ($item->card_type==0)
                                    <a href="/system/user/charge/list?shop_id=${data->shop_id!}&card_type=0&card_no=${item->card_no!}" style="color: #0E70CA">充值明细</a>
                                    -
                                    <a href="/system/user/consume/list?shop_id=${data->shop_id!}&card_type=0&card_no=${item->card_no!}" style="color: #0E70CA">消费明细</a>
                                    <#elseif>($item->card_type==1)
                                    <a href="/system/user/charge/list?shop_id=${data->shop_id!}&card_type=1&card_no=${item->card_no!}" style="color: #0E70CA">充值明细</a>
                                    -
                                    <a href="/system/user/consume/list?shop_id=${data->shop_id!}&card_type=1&card_no=${item->card_no!}" style="color: #0E70CA">消费明细</a>
                                    </#if>
                                </td>
                            </tr>
                            </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>
                <div class="member_list_footer">
                    <table width="" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="#"
                                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>
<div id="set-money" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="card_id"/>
    <input type="hidden" class="card_no"/>
    <input type="hidden" class="card_type" value="0"/>
    <div class="exchange_old">
        <span>当前余额:</span>
        <input type="text" class="money_dis" disabled />
    </div>
    <div style="margin-bottom: 10px;">
        <span>增加金额:</span>
        <input type="text" class="amount" value=""  onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
        <span style="color:red">（*当余额为正时，增加余额；余额为负时，减少余额*）</span>
    </div>
    <div>
        <span>增加备注:</span>
        <input type="text" class="remark" value="" size="200" style="width: 450px;"/>
    </div>
</div>
<div id="set-integral" class="exchange-num">
    <input type="hidden" class="user_id"/>
    <input type="hidden" class="card_id"/>
    <input type="hidden" class="card_no"/>
    <input type="hidden" class="card_type" value="1"/>
    <div class="exchange_old">
        <span>当前次数:</span>
        <input type="text" class="score_dis" disabled />
    </div>
    <div style="margin-bottom: 10px;">
        <span>增加次数:</span>
        <input type="text" class="score" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
        <span style="color:red">（*当次数为正时，增加次数；次数为负时，减少次数*）</span>
    </div>
    <div>
        <span>增加备注:</span>
        <input type="text" class="remark" value="" size="200" style="width: 450px;"/>
    </div>
</div>


<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form2").submit();
    }
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}'            //总页码数
</script>

<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script src="/js/admin/get_card_list.js?v=1.0.3" type="text/javascript"></script>

<#include ("system.footer")