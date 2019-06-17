<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/coupon_manage.css">
<link rel="stylesheet" href="/css/admin/store_manage.css?v=1.0.2">
<link rel="stylesheet" href="/css/admin/template_basic_config.css?v=1.0.6">
<style type="text/css">
    .tb-decorate-list>tbody>tr>td{
        height:50px;
        color: #666;
        font-size: 14px;
        position: relative;
    }
    .tb-decorate-list>thead>tr>th{
        text-align: left;
        font-size: 14px;
        color: #333;
        padding-left: 20px;
    }
    .save_bottom_footer{
        padding: 10px 0;
        background: #f8f8fa;
        border-top: 1px solid #f2f2f2;
        text-align: center;
        position: fixed;
        bottom: 0;
        width: 100%;
        margin-left: 10px;
    }
    .save{
        width: 90px;
        text-align: center;
        height: 30px;
        line-height: 22px;
        border: 1px solid #5A8BFF;
        background: #5A8BFF;
        color: #fff;
        margin: 0 auto;
        cursor: pointer;
    }
    .save:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .save:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span>基础配置/</span>
        <span style="color: #666;">消息模板配置</span>
    </div>
        <div class="main_container">
            <form action="/admin/config/message/template/basic" method="post" id="form1">
                {{csrf_field()!}
            <div class="list_content">
                <div class="list_table">
                    <div class="wx_template">
                        <span>微信消息模板</span>
                        <span>因微信平台限制，公众号消息最多可发送25类，小程序消息最多可发送25类，请谨慎选择</span>
                    </div>
                    <div class="nbd_wx hide">
                        <span>当前未绑定公众号，不能发送公众号信息</span>
                        <span><a href="#">绑定公众号&nbsp;></a></span>
                    </div>
                    <div class="bd_wx ">
                        <span>公众号消息已选<span class="wx_gzh_number">0</span>条,
                            小程序消息已选<span class="wx_xcx_number">0</span>条
                        </span>
                    </div>
                    <div class="goods-box-edit">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr class="click_sq1">
                                    <th width="15%">交易物流提醒</th>
                                    <th width="35%"></th>
                                    <th width="25%"></th>
                                    <th width="25%" style="text-align: right;padding-right: 20px;">
                                        <span class="notice1" style="color:#5A8BFF">收起</span>
                                        <img src="/image/admin/shop_deco/icon_up.png" alt="">
                                    </th>
                                </tr>
                                </thead>
                                <tbody class="tbody_sq1">
                                    <tr>
                                        <td class="group_name" style="border:none;">模板消息</td>
                                        <td style="border:none;">发送条件</td>
                                        <td style="border:none;">公众号消息</td>
                                        <td style="border:none;">小程序消息</td>
                                    </tr>
                                    <tr>
                                        <td>预约取消通知</td>
                                        <td>预约订单取消时立即发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh5" value="OPENTM207847150">
                                            <label for="gzh5"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/qx_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx1" value="AT0117">
                                            <label for="xcx1"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/qx_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>预约成功提醒</td>
                                        <td>服务预约成功时立即发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh1" value="OPENTM410419150">
                                            <label for="gzh1"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/cg_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx2" value="AT0104">
                                            <label for="xcx2"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/cg_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>预约到期提醒</td>
                                        <td>距预约服务开始前1小时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh2" value="OPENTM414204481">
                                            <label for="gzh2"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/yydq_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx3" value="AT1081">
                                            <label for="xcx3"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/yydq_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>账户余额变动提醒</td>
                                        <td>账户余额发生变动时立即发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh3" value="OPENTM402190178">
                                            <label for="gzh3"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/zhye_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx4" value="AT0157">
                                            <label for="xcx4"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/zhye_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>订单发货提醒</td>
                                        <td>订单发货时立即发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh4" value="OPENTM200565259">
                                            <label for="gzh4"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/ddfh_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx5" value="AT0007">
                                            <label for="xcx5"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/ddfh_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>订单未支付通知</td>
                                        <td>下单后未支付时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh6" value="TM00184">
                                            <label for="gzh6"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/ddwzf_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx6" value="AT1482">
                                            <label for="xcx6"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/ddwzf_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>订单支付成功通知</td>
                                        <td>订单支付成功时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh7" value="TM00015">
                                            <label for="gzh7"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/ddzfcg_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx7" value="AT0009">
                                            <label for="xcx7"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/ddzfcg_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>确认收货通知</td>
                                        <td>确认收货时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh8" value="OPENTM202314085">
                                            <label for="gzh8"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/qrsh_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx8" value="AT0241">
                                            <label for="xcx8"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/qrsh_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>退款失败通知</td>
                                        <td>退款失败时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh9" value="OPENTM412546294">
                                            <label for="gzh9"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/tksb_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx9" value="AT0329">
                                            <label for="xcx9"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/tksb_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>退款状态通知</td>
                                        <td>退款时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh10" value="TM00004">
                                            <label for="gzh10"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/tktz_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx10" value="AT0036">
                                            <label for="xcx10"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/tktz_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>门店自提到期提醒</td>
                                        <td>门店自提时间到期前半小时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh11" value="OPENTM414273954">
                                            <label for="gzh11"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/mdzt_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx11" value="AT1427">
                                            <label for="xcx11"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/mdzt_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>取货成功通知</td>
                                        <td>取货完成时发送</td>
                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送公众号消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>
                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh12" value="OPENTM412465579">
                                            <label for="gzh12"><span class="send_message">发送公众号消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/qhcg_gzh.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>

                                        <td class="hide">
                                            <input type="checkbox" class="check_gray" checked disabled="false">
                                            <span class="send_message">发送小程序消息</span>
                                            <span class="yl_gray">预览</span>
                                        </td>

                                        <td class="">
                                            <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx12" value="AT1146">
                                            <label for="xcx12"><span class="send_message">发送小程序消息</span></label>
                                            <span class="yl_blue">预览</span>
                                            <div class="contain_show">
                                                <div class="show_to">
                                                    <div class="sj_bcgb"></div>
                                                    <div class="sj_bcgw"></div>
                                                    <img src="/image/admin/template_message/qhcg_xcx.jpg" alt="">
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="goods-box-edit" style="margin-bottom: 20px">
                        <div class="goods-edit-basic">
                            <table class="tb-decorate-list">
                                <thead>
                                <tr class="click_sq2">
                                    <th width="15%">营销信息提醒</th>
                                    <th width="35%"></th>
                                    <th width="25%"></th>
                                    <th width="25%" style="text-align: right;padding-right: 20px;">
                                        <span class="notice2" style="color:#5A8BFF">收起</span>
                                        <img src="/image/admin/shop_deco/icon_up.png" alt="">
                                    </th>
                                </tr>
                                </thead>
                                <tbody class="tbody_sq2">
                                <tr>
                                    <td class="group_name" style="border:none;">模板消息</td>
                                    <td style="border:none;">发送条件</td>
                                    <td style="border:none;">公众号消息</td>
                                    <td style="border:none;">小程序消息</td>
                                </tr>
                                <tr>
                                    <td>会员卡余额变动提醒</td>
                                    <td>会员卡余额发生变动时立即发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh13" value="OPENTM402190178">
                                        <label for="gzh13"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_show">
                                            <div class="show_to">
                                                <div class="sj_bcgb"></div>
                                                <div class="sj_bcgw"></div>
                                                <img src="/image/admin/template_message/viptx_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx13" value="AT0157">
                                        <label for="xcx13"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_show">
                                            <div class="show_to">
                                                <div class="sj_bcgb"></div>
                                                <div class="sj_bcgw"></div>
                                                <img src="/image/admin/template_message/viptx_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>会员卡领取成功通知</td>
                                    <td>会员卡领取成功通知</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh14" value="OPENTM405766398">
                                        <label for="gzh14"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_show">
                                            <div class="show_to">
                                                <div class="sj_bcgb"></div>
                                                <div class="sj_bcgw"></div>
                                                <img src="/image/admin/template_message/vipcg_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx14" value="AT1198">
                                        <label for="xcx14"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_show">
                                            <div class="show_to">
                                                <div class="sj_bcgb"></div>
                                                <div class="sj_bcgw"></div>
                                                <img src="/image/admin/template_message/vipcg_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>限次卡扣减通知</td>
                                    <td>限次卡扣减使用次数后立即发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh15" value="OPENTM207664901">
                                        <label for="gzh15"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_show">
                                            <div class="show_to">
                                                <div class="sj_bcgb"></div>
                                                <div class="sj_bcgw"></div>
                                                <img src="/image/admin/template_message/xckj_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx15" value="AT0004">
                                        <label for="xcx15"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_show">
                                            <div class="show_to">
                                                <div class="sj_bcgb"></div>
                                                <div class="sj_bcgw"></div>
                                                <img src="/image/admin/template_message/xckj_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>卡券到期提醒</td>
                                    <td>会员卡/优惠券过期前一天发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh16" value="OPENTM400182201">
                                        <label for="gzh16"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kqdq_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx16" value="AT1199">
                                        <label for="xcx16"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kqdq_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>卡券领取成功通知</td>
                                    <td>领取优惠券后立即发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh17" value="OPENTM200772305">
                                        <label for="gzh17"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kqcg_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx17" value="AT1198">
                                        <label for="xcx17"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kqcg_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>拼团失败通知</td>
                                    <td>拼团时间截止后如未拼团成功则立即发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh18" value="OPENTM401113750">
                                        <label for="gzh18"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ptsb_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx18" value="AT0310">
                                        <label for="xcx18"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ptsb_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>拼团成功通知</td>
                                    <td>拼团成功时立即发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh19" value="OPENTM400932513">
                                        <label for="gzh19"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ptcg_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx19" value="AT0051">
                                        <label for="xcx19"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ptcg_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>自定义消息模板推送</td>
                                    <td>自定义消息模板到发送时间时立即发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh20" value="OPENTM207574677">
                                        <label for="gzh20"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ywcl_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx20" value="AT0159">
                                        <label for="xcx20"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ywcl_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>砍价成功提醒</td>
                                    <td>砍价成功时立即发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh21" value="OPENTM410292733">
                                        <label for="gzh21"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kjcg_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>

                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx21" value="AT1179">
                                        <label for="xcx21"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kjcg_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>砍价进度通知</td>
                                    <td>砍价结束前3小时如未砍价成功则发送</td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送公众号消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>
                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh22" value="OPENTM410292733">
                                        <label for="gzh22"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kjjd_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                    <td class="hide">
                                        <input type="checkbox" class="check_gray" checked disabled="false">
                                        <span class="send_message">发送小程序消息</span>
                                        <span class="yl_gray">预览</span>
                                    </td>

                                    <td class="">
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx22" value="AT1179">
                                        <label for="xcx22"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/kjjd_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>审核通过提醒</td>
                                    <td>审核通过提醒</td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh23" value="">
                                        <label for="gzh22"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/official_audit_success.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx23" value="AT1179">
                                        <label for="xcx22"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/mp_audit_success.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>审核不通过提醒</td>
                                    <td>审核不通过提醒</td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh24" value="OPENTM410292733">
                                        <label for="gzh22"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/official_audit_fail.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx24" value="AT1179">
                                        <label for="xcx22"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/mp_audit_fail.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>分销员等级升级提醒</td>
                                    <td>分销员等级升级提醒</td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh24" value="">
                                        <label for="gzh22"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/fxdj_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx24" value="AT1179">
                                        <label for="xcx22"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/fxdj_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>拼团抽奖结果通知</td>
                                    <td>拼团抽奖结果通知</td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_gzh[]" id="gzh25" value="">
                                        <label for="gzh22"><span class="send_message">发送公众号消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ptcj_gzh.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <input type="checkbox" class="check_blue" name="check_message_xcx[]" id="xcx25" value="AT1179">
                                        <label for="xcx22"><span class="send_message">发送小程序消息</span></label>
                                        <span class="yl_blue">预览</span>
                                        <div class="contain_shown">
                                            <div class="show_to">
                                                <div class="sj_bcgbn"></div>
                                                <div class="sj_bcgwn"></div>
                                                <img src="/image/admin/template_message/ptcj_xcx.jpg" alt="">
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
                <div class="save_bottom_footer fix_footer" >
                    <input class="save" type="submit" value="保存"/>
                </div>
            </form>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $('.save_bottom_footer').outerWidth($('.list_content').width());
  $('.yl_blue').mouseover(function () {
      $(this).parent().find('.contain_show').css('display','block');
      $(this).parent().find('.contain_show').mouseover(function () {
          $(this).parent().find('.contain_show').css('display','block');
      });
      $(this).parent().find('.contain_show').mouseleave(function () {
          $(this).parent().find('.contain_show').css('display','none');
      });
  });
  $('.yl_blue').mouseleave(function () {
      $(this).parent().find('.contain_show').css('display','none');
  });
    $('.yl_blue').mouseover(function () {
        $(this).parent().find('.contain_shown').css('display','block');
        $(this).parent().find('.contain_shown').mouseover(function () {
            $(this).parent().find('.contain_shown').css('display','block');
        });
        $(this).parent().find('.contain_shown').mouseleave(function () {
            $(this).parent().find('.contain_shown').css('display','none');
        });
    });
    $('.yl_blue').mouseleave(function () {
        $(this).parent().find('.contain_shown').css('display','none');
    });

  $('.click_sq1').click(function () {
     if($('.notice1').text()=='收起'){
         $('.tbody_sq1').hide();
         $('.notice1').text('展开');
         $('.notice1').next().attr('src','/image/admin/shop_deco/icon_down.png')
     }else{
         $('.tbody_sq1').show();
         $('.notice1').text('收起');
         $('.notice1').next().attr('src','/image/admin/shop_deco/icon_up.png')
     }
  });
  $('.click_sq2').click(function () {
      if($('.notice2').text()=='收起'){
          $('.tbody_sq2').hide();
          $('.notice2').text('展开');
          $('.notice2').next().attr('src','/image/admin/shop_deco/icon_down.png')
      }else{
          $('.tbody_sq2').show();
          $('.notice2').text('收起');
          $('.notice2').next().attr('src','/image/admin/shop_deco/icon_up.png')
      }
  });

  function choose_gzh(){
      let gzh=$('input[name="check_message_gzh[]"]');
      let count=0;
      for(let i=0;i<gzh.length;i++){
          if(gzh[i].checked){
              count++;
          }
      }
      return count;
  }
  function choose_xcx(){
      let xcx=$('input[name="check_message_xcx[]"]');
      let n=0;
      for(let i=0;i<xcx.length;i++){
          if(xcx[i].checked){
              n++;
          }
      }
      return n;
  }


  var gzh=$('input[name="check_message_gzh[]"]');
  var xcx=$('input[name="check_message_xcx[]"]');
  for( var j=0;j<gzh.length;j++){
      gzh[j].onclick=function(){
          $('.wx_gzh_number').text(choose_gzh());
      }
  }
  for(var i=0;i<xcx.length;i++){
      xcx[i].onclick=function(){
          $('.wx_xcx_number').text(choose_xcx());

          // if(tt > 20){
          //     util.mobile_alert("小程序消息不能超过20条！");
          // }
      }
  }

  $(".save").click(function () {
      var xcx_number =$('.wx_xcx_number').text();
      var gzh_number = $('.wx_gzh_number').text();
      if(xcx_number > 25){
          util.mobile_alert("小程序消息不能超过25条！");
          return false;
      }
      if(gzh_number > 25){
          util.mobile_alert("公众号消息不能超过25条！");
          return false;
      }
  });
    var template_config = @json($templateLibrary),
        gzh_number = 0,
        xcx_number = 0,
        default_template = @json($defaultLibrary);
    $('input[name="check_message_gzh[]"]').each(function (index, item) {
        $(item).val(default_template.B[index]);

        if (template_config != null && template_config.B != undefined && $.inArray(default_template.B[index], template_config.B) > -1) {
            $(item).prop('checked', true);
            gzh_number++;
        }

    });
    $('input[name="check_message_xcx[]"]').each(function (index, item) {
        $(item).val(default_template.A[index]);
        if (template_config != null && template_config.A != undefined && $.inArray(default_template.A[index], template_config.A) > -1) {
            $(item).prop('checked', true);
            xcx_number++;
        }
    });
    $('.wx_gzh_number').text(gzh_number);
    $('.wx_xcx_number').text(xcx_number);
</script>