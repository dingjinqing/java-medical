<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/instead_pay_config.css?v=1.1.6" type="text/css"/>
<div style="min-width: 1090px">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">好友代付</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <form action="/admin/market/insteadpay/config" method="post" id="form1">
            {{csrf_field()!}
            <div class="all_content">
                <div class="instead_title">
                    <div class="it_left">
                        <div class="bold_one">好友代付</div>
                        <div>单人代付：用户邀请一个好友代付全部款项。</div>
                        <div>多人代付：用户可将代付信息发到微信群或多个微信好友，请TA们帮忙代付。</div>
                        <div>温馨提示：代付订单将在下单后24小时内有效，逾期后未完成支付，所有款项将自动退回到付款人账户。</div>
                    </div>
                    <div class="it_right">
                        <div class="it_switch" img_id="0">
                            <label for="">
                                <input type="checkbox" name="status" />
                                <img class="draggable" src="http://${image_domain!}/image/admin/circle.png" alt="">
                            </label>
                        </div>
                        <span class="is_open">
                            <#if  ($insteadPay['status'] == 1)
                                已开启
                            <#else>
                                已关闭
                            </#if>
                        </span>
                    </div>
                </div>
                <div class="all_config_item">
                    {{--单人部分--!}
                    <div class="single_check">
                        <input type="checkbox" name="action[]" value="1" <#if  (in_array(1, $action ?? [])) checked </#if> />单人代付
                    </div>
                    <div class="single_set clearfix">
                        <div class="single_tit">发起人默认留言：</div>
                        <input type="text" name="order_user_message[1]" value="${order_user_message[1] ??  '相中了一件商品，帮人家付一下呗！'!}" />
                    </div>
                    <div class="single_set clearfix">
                        <div class="single_tit">代付人默认留言：</div>
                        <input type="text" name="instead_pay_message[1]" value="${instead_pay_message[1] ?? '帮你付款啦！'!}" />
                    </div>
                    {{--多人部分--!}
                    <div class="single_check">
                        <input type="checkbox" name="action[]" value="2" <#if  (in_array(2, $action ?? [])) checked </#if>/>多人代付
                    </div>
                    <div class="single_set clearfix">
                        <div class="single_tit">发起人默认留言：</div>
                        <input type="text" name="order_user_message[2]" value="${order_user_message[2] ?? '相中了一件商品，帮人家付一下呗！'!}" />
                    </div>
                    <div class="single_set clearfix">
                        <div class="single_tit">代付人默认留言：</div>
                        <input type="text" name="instead_pay_message[2]" value="${instead_pay_message[2] ?? '帮你付款啦！'!}" />
                    </div>
                    <div class="single_set clearfix">
                        <div class="single_tit">代付金额比例：</div>
                        <a href="javascript:;" class="show_eg" style="height: 30px;line-height: 30px">查看示例
                            <div class="hover_show" style="top: -40px">
                                <img src="http://${image_domain!}/image/admin/new_preview_image/friend_help.jpg" alt="">
                            </div>
                        </a>
                    </div>
                    <div class="single_set clearfix">
                        <div class="single_tit"></div>
                        <div class="each_set">
                            <table>
                                <thead>
                                    <tr>
                                        <td>选项描述</td>
                                        <td>
                                            <div class="show_more">
                                                代付金额比例<img src="http://${image_domain!}/image/admin/analysis_tishi.png" alt="" class="view_more">
                                                <div class="more_content">
                                                    <div class="little_square"></div>
                                                    <div>好友可选择商家设定的代付金额比例支付。</div>
                                                    <div>例：订单金额为100元，用户选择多人代付，分享给好友A，好友A可选择代付金额比例10%，即10元；代付金额比例30%，即30元；代付订单剩余金额，即100元。好友A也可以手动填写代付金额帮助好友付款。</div>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <div><input type="text" maxlength="5" name="pay_ratio_text[]" value="${pay_ratio_text[0] ?? '意思意思'!}"></div>
                                        </td>
                                        <td>
                                            <div><input type="text" placeholder="10" name="pay_ratio_number[]" value="${pay_ratio_number[0] ?? 10!}"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div><input type="text" maxlength="5" name="pay_ratio_text[]" value="${pay_ratio_text[1] ?? '情比金坚'!}"></div>
                                        </td>
                                        <td>
                                            <div><input type="text" placeholder="30" name="pay_ratio_number[]" value="${pay_ratio_number[1] ?? 30!}"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div><input type="text" maxlength="5" name="pay_ratio_text[]" value="${pay_ratio_text[2] ?? '一掷千金'!}"></div>
                                        </td>
                                        <td>
                                            <div>订单剩余金额</div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer">
            <a class="save_instead_pay" href="javascript:void(0);">保存</a>
        </div>
    </div>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script src="/js/admin/instead_pay_config.js?v=1.0.2" type="text/javascript"></script>
<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    var status = '${status!}';
    if (parseInt(status) == 1) {
        $(".draggable").trigger('click');
    } else {
        $('.all_config_item').css("display","none");
    }
</script>
<script type="text/javascript">
    getPowerInfo('main_config','insteadpay','sub_4','好友代付',0);
</script>