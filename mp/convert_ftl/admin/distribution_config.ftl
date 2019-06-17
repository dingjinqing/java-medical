<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.2" type="text/css" />
<style type="text/css">
    .third_cate{
        margin-left: 20px;
    }
    .config_detail ul li .distri_rank{
        float: left;
        height:50px;
        line-height: 50px;
        display: flex;
        align-items: center;
    }
    .fl{
        float: left;
    }
    .in_li {
        margin-right: 10px;
        width: 90px;
    }
    .activation_cfg{
        margin-left: 190px;
        margin-top: 7px;
        width: 800px;
        line-height: 30px;
        /*display: none;*/
    }
    input[type="checkbox"]{
        vertical-align: text-top;
    }
    /*.distribution_info{*/
        /*display: none;*/
    /*}*/
    .rule_link{
        color: red;
        margin-left: 10px;
        float: left;
    }
    .rule_link a{
        color: #5a8bff;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;">分销配置</span>
</div>
<form action="/admin/market/distribution/config" method="post" id="form1" style="padding-bottom: 100px">
    {{ csrf_field()!}
    <div class="reserve-container">
        <#include ('admin.distributio_title')
        <ul class="config_detail">
            <ul>
                <li class="detail_lis clearfix">
                    <div class="item_title">分销开关：</div>
                    <div class="distri_switch">
                        <div class="fl pay_fl"  img_id="${request['status'] == 1 ? 1 : 0!}"  <#if ($request['status']== 1)style='background: url("http://${image_domain!}/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if> id="status">
                            <label>
                                <input type="checkbox" name="status" aid="" <#if ($request['status'] == 1)checked </#if>/>
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($request['status'] == 1)style="right:0px;"</#if>/>
                            </label>
                        </div>
                    </div>
                    <span class="is_open"><#if ($request['status'] == 1)已开启 <#else> 已关闭</#if></span>
                </li>
                <li class="clearfix">
                    <div class="item_title" style="height:40px;"></div>
                    <div class="switch_tips">
                        开关默认关闭，开启开关，则用户可以申请为店铺分销员，分销员邀请用户注册产生订单，购买者邀请人可获得佣金奖励。关闭开关，手机端个人中心”分销中心“菜单隐藏，用户下单，邀请人不再产生佣金奖励，系统分销机制关闭，邀请不再记录邀请关系。
                    </div>
                </li>
                <li class="detail_lis clearfix">
                    <div class="item_title" >分销员审核：</div>
                    <div class="distri_judge">
                        <div class="fl pay_fl" img_id="${request['judge_status'] == 1 ? 1 : 0!}"  <#if ($request['judge_status']== 1)style='background: url("http://${image_domain!}/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if> >
                            <label>
                                <input type="checkbox" name="judge_status" <#if ($request['judge_status'] == 1)checked </#if>/>
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable"
                                     <#if ($request['judge_status'] == 1) style="right:0px;" </#if>/>
                            </label>
                        </div>
                    </div>
                    <span class="is_open"><#if ($request['judge_status'] == 1)已开启 <#else> 已关闭</#if></span>
                    <span style="color:#999;">（若开启审核，您需要配置推广文案内容）</span>
                    <#if  ($request['judge_status'] == 1) <a href="/admin/market/distribution/copywriting?top_index=4" style="color: #5a8bff;">推广文案配置</a> </#if>
                </li>
                <li class="clearfix">
                    <div class="item_title" style="height:25px;"></div>
                    <div class="switch_tips">
                        开启分销员审核功能后，普通用户申请成为分销员时需要经过商家审核。关闭则成为店铺分销员不需要申请审核，全部用户均默认为店铺分销员。
                    </div>
                </li>
                {{--{{dd($request)!}--!}
                <li class="clearfix">
                    <div class="distribution_info" <#if ($request['judge_status'] != 1) hidden </#if>>
                        <div class="item_title" style="height:25px;"></div>
                        <div style="margin-top: 5px;">
                            <input type="checkbox" name="activation" <#if ($request['activation'] == 1) checked </#if> style="vertical-align: text-top;">用户申请成为分销员时，需要提交个人信息
                        </div>

                        <div class="clearfix activation_cfg" <#if ($request['activation'] != 1) hidden </#if>>
                            {{--<input type="hidden" form="form1" name="activation_cfg" value="[]">--!}
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]"  value="real_name" <#if (in_array('real_name',$request['activation_cfg'] ?? array())) checked </#if> >真实姓名</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="mobile" <#if (in_array('mobile',$request['activation_cfg'] ?? array())) checked </#if> >手机号</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="cid" <#if (in_array('cid',$request['activation_cfg'] ?? array())) checked </#if> >身份证号码</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="sex" <#if (in_array('sex',$request['activation_cfg'] ?? array())) checked </#if> >性别</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="birthday" <#if (in_array('birthday',$request['activation_cfg'] ?? array())) checked </#if> >生日</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="marital_status" <#if (in_array('marital_status',$request['activation_cfg'] ?? array())) checked </#if> >婚姻状况</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="education" <#if (in_array('education',$request['activation_cfg'] ?? array())) checked </#if> >教育程度</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="industry_info" <#if (in_array('industry_info',$request['activation_cfg'] ?? array())) checked </#if> >所在行业</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="address" <#if (in_array('address',$request['activation_cfg'] ?? array())) checked </#if> >所在地</div>

                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="remarks" <#if (in_array('remarks',$request['activation_cfg'] ?? array())) checked </#if> >备注</div>
                            <div class="fl in_li"><input type="checkbox" class="activation_cfg" name="activation_cfg[]" value="upload_image" <#if (in_array('upload_image',$request['activation_cfg'] ?? array())) checked </#if> >图片上传</div>
                        </div>
                    </div>
                </li>
                <li class="detail_lis clearfix">
                    <div class="item_title" >分销员排名：</div>
                    <div class="distri_rank">
                        <div class="fl pay_fl" img_id="${request['rank_status'] == 1 ? 1 : 0!}"  <#if ($request['rank_status']== 1)style='background: url("http://${image_domain!}/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if> >
                            <label>
                                <input type="checkbox" name="rank_status" aid="" <#if ($request['rank_status'] == 1) checked </#if>/>
                                <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($request['rank_status'] == 1)style="right:0px;"</#if>/>
                            </label>
                        </div>
                    </div>
                    <span class="is_open"><#if ($request['rank_status'] == 1)已开启 <#else> 已关闭</#if></span>
                </li>
                <li class="clearfix">
                    <div class="item_title" style="height:25px;"></div>
                    <div class="switch_tips">
                        开关默认关闭，开启开关，且拥有返利数据的分销员数大于等于3位时分销员中心显示分销员佣金排名。关闭则不显示分销员佣金排名页面。
                    </div>
                </li>
                {{--<li class="detail_lis clearfix">--!}
                    {{--<div class="item_title">返利佣金比例：</div>--!}
                    {{--<div class="rebeat_percent">--!}
                        {{--<input type="text" onkeyup="value=value.replace(/[^\d.]/g,'')" name="scale" value="${request['scale']!}">%--!}
                    {{--</div>--!}
                {{--</li>--!}
                {{--<li class="clearfix">--!}
                    {{--<div class="item_title" style="height: 45px;"></div>--!}
                    {{--<div class="switch_tips">--!}
                    {{--所有的商品都按当前比例结算佣金，佣金值=商品实际支付金额*佣金比例，例如，分销商品价格100元，返利佣金比例20%，那么用户购买一件分销商品，邀请该用户分销员获得20元佣金。<br>--!}
                    {{--订单支付完成佣金返利到分销员分销中心的余额账户中，但是该佣金为待返利状态，订单完成后，佣金返利，分销员可以直接使用该佣金购物。限制小数点后一位数字。--!}
                    {{--</div>--!}
                {{--</li>--!}
                <li  class="detail_lis clearfix">
                    <div class="item_title">返利有效期：</div>

                    <div class="rebeat_vaild">
                        <input type="radio" name="vaild_type" value="0" <#if  ($request['vaild'] > 0) checked </#if>>
                        <input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="vaild" value="${request['vaild'] > 0 ? $request['vaild'] : ''!}">天
                        <input type="radio" name="vaild_type" value="1" <#if  (!($request['vaild'] > 0)) checked </#if> style="margin-left: 5%">永久
                    </div>

                </li>
                <li class="clearfix">
                    <div class="item_title" style="height:15px;"></div>
                    <div class="switch_tips">
                        用户被分销员邀请注册开始计算，在该天数限制内该用户购买分销商品给分销员计算佣金返利，一旦超过该天数，则不再给分销员佣金返利，默认为空，为空表示不限制。
                    </div>
                </li>
                <li  class="detail_lis clearfix">
                    <div class="item_title">分销员保护期：</div>
                    <div class="rebeat_vaild">
                        <input type="radio" name="protect_type" value="0" <#if  ($request['protect_date'] >= 0) checked </#if>>
                        <input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="protect_date" value="${request['protect_date'] >= 0 ? $request['protect_date'] : ''!}">天
                        <input type="radio" name="protect_type" value="1" <#if  (!($request['protect_date'] >= 0)) checked </#if> style="margin-left: 5%">永久
                    </div>
                </li>
                <li class="clearfix">
                    <div class="item_title" style="height:15px;"></div>
                    <div class="switch_tips">
                        在保护期内，分销员发展的客户不会变更绑定关系，保护期过后可通过分享链接重新绑定邀请关系。
                        超过保护期若未重新建立邀请关系，则原绑定关系仍然有效，可依据返利配置条件返利。
                    </div>
                </li>
                <li  class="detail_lis clearfix">
                    <div class="item_title">分销中心页面名称：</div>
                    <div class="center_vaild">
                        <input type="text" onkeyup="value=value.replace(/^[A-Za-z0-9]{19}|[\u4e00-\u9fa5]{10}/,'')" name="rebate_center_name" style="color: #333333" value="${request['rebate_center_name'] ?? "分销中心"!}">
                    </div>
                </li>
                <li class="clearfix whole_line">
                    <div class="item_title1">返利提现设置</div>
                    <ul>
                        <li class="clearfix" style="height: 50px;line-height: 50px">
                            <div class="item_title">返利提现开关：</div>
                            <div class="distri_fanli">
                                <div class="fl pay_fl"  img_id="${request['withdraw_status'] == 1 ? 1 : 0!}"  <#if ($request['withdraw_status']== 1)style='background: url("http://${image_domain!}/image/admin/on_1.png") left top / 100% 100% no-repeat;'</#if> id="withdraw_status">
                                    <label>
                                        <input type="checkbox" name="withdraw_status" aid="" <#if ($request['withdraw_status'] == 1)checked </#if>/>
                                        <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($request['withdraw_status'] == 1)style="right:0px;"</#if>/>
                                    </label>
                                </div>
                            </div>
                            <span class="is_open" style="float:left"><#if ($request['withdraw_status'] == 1)已开启 <#else> 已关闭</#if></span>
                            <div class="rule_link">注：开启提现功能开关前，请阅读 <a href="http://bbs.weipubao.cn/forum.php?mod=viewthread&tid=686&fromuid=1" target="_blank">《返利提现配置操作说明》</a></div>
                        </li>
                        <li class="clearfix">
                            <div class="item_title" style="height:40px;"></div>
                            <div class="switch_tips">
                                开关开启，分销员推广返利获得的佣金可提现到微信钱包，分销员在小程序发起返利申请，需后台审核通过才可提现到账 <br>
                                <div>
                                    <input type="radio" value="wx_mini" name="withdraw_source" <#if ($request['withdraw_source'] == 'wx_mini' || empty($request['withdraw_source'])) checked </#if>> <span style="color: #333333">小程序</span>
                                    <div>
                                        注意：使用返利提现功能，请确保小程序已开通微信支付，否则不可提现 &nbsp;<a href="/admin/config/trade" target="_blank" style="color: #5A8BFF">去配置</a>
                                    </div>
                                </div>
                                <div>
                                    <input type="radio" value="wx_open" name="withdraw_source" <#if ($request['withdraw_source'] == 'wx_open') checked </#if>> <span style="color: #333333">公众号</span>
                                    <div>
                                        注意：使用返利提现功能，请确保小程序已绑定认证服务号并配置相关支付信息，否则不可提现，未关注公众号的用户将会提现失败 &nbsp;<a href="/admin/public/service/auth/list" target="_blank" style="color: #5A8BFF">去配置</a>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li class="clearfix">
                        <div class="item_title">返利最小提现金额：</div>
                        <div class="rebeat_vaild">
                            <input type="text" onkeyup="value=value.replace(/[^\d]/g,'')" name="withdraw_cash" value="${request['withdraw_cash'] ?? 1!}">元
                        </div>
                        </li>
                        <li class="clearfix">
                            <div class="item_title" style="height:15px;"></div>
                            <div class="switch_tips">
                                分销员发起返利提现，单次申请最小提现金额。为防止分销员提现过于频繁，请设置单次最小提现金额。
                            </div>
                        </li>
                    </ul>
                </li>
                <li class="whole_line">
                    <div class="item_title1">分销中心推广海报背景图</div>
                    <div class="up_img clearfix">
                        <input type="hidden" name="bg_img" <#if ($request['bg_img']) value="${request['bg_img']!}" <#else> value="http://${image_domain!}/image/admin/dis_bg_1.jpg" </#if>>
                        <div class="ui_left">
                            {{--{{var_dump($request['bg_img'])!}--!}
                            <div class="have_bg clearfix" style="background-image:<#if ($request['bg_img']) url(${request['bg_img']!}) <#else>  url(http://${image_domain!}/image/admin/dis_bg_1.jpg) </#if>  ;background-size:100%,100%" >
                                <div class="img_tips" >
                                    <#if (!$request['bg_img'])
                                        <p>上传自定义的分销员推广图片</p>
                                        <p>建议尺寸640px*640px</p>
                                    </#if>
                                </div>
                                <div class="abso_area clearfix">
                                    <div class="aa_left"><img src="http://${image_domain!}/image/admin/user_touxiang.png" alt=""></div>
                                    <div class="aa_right">
                                        <div>昵称</div>
                                        <div><#if ($request['desc']) ${request['desc']!} <#else> 分享给你一个好物店铺，快来购物吧！！ </#if></div>
                                    </div>
                                </div>
                            </div>
                            <div class="bottom_area clearfix">
                                <#if ($qrcode)
                                    <img src="{{image_url($qrcode['qrcode_img'])!}" alt="" style="width: 75px;height: 75px;margin-right: 20px">
                                <#else>
                                    <span class="code_area">二维码区域</span>
                                </#if>
                                {{--<img src="${qrcode!}" alt="" class="img_tips">--!}
                                <img src="http://${image_domain!}/image/admin/usr_codes.png" alt="" class="img_tips">
                            </div>
                        </div>
                        <div class="ui_right">
                            <ul>
                                <li class="each_li clearfix">
                                    <div class="el_title">邀请文案：</div>
                                    <div style="float: left;"><input type="text" placeholder="请输入邀请文案" name="desc" value="${request['desc'] ?? '分享给你一个好物店铺快来购物吧！'!}"></div>
                                </li>
                                <li class="each_li clearfix">
                                    <div class="el_title">海报背景图：</div>
                                    <div class="up_detail ">
                                        <div class="ud_li clearfix">
                                            <span>默认背景选择：</span>
                                            <select name="m_bg" id="">
                                                <option value="http://${image_domain!}/image/admin/dis_bg_1.jpg" <#if ($request['bg_img'] == "http://{$image_domain}/image/admin/dis_bg_1.jpg" || !$request['bg_img']) selected </#if>>背景图1</option>
                                                <option value="http://${image_domain!}/image/admin/dis_bg_2.jpg" <#if ($request['bg_img'] == "http://{$image_domain}/image/admin/dis_bg_2.jpg") selected </#if>>背景图2</option>
                                                <option value="http://${image_domain!}/image/admin/dis_bg_3.jpg" <#if ($request['bg_img'] == "http://{$image_domain}/image/admin/dis_bg_3.jpg") selected </#if>>背景图3</option>
                                                <option value="http://${image_domain!}/image/admin/dis_bg_4.jpg" <#if ($request['bg_img'] == "http://{$image_domain}/image/admin/dis_bg_4.jpg") selected </#if>>背景图4</option>
                                            </select>
                                        </div>
                                        <div class="ud_li clearfix">
                                            <span>上传背景图片：</span>
                                            <div class="add_images">
                                                <img src="http://${image_domain!}/image/admin/add_img_bg.png" alt="" class="have_margin">
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="clearfix" style="padding: 0 10px">
                                    <div class="el_title"></div>
                                    <div style="float: left;color: #999;">图片尺寸640px*640px</div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>

            </ul>
        </div>
    </div>
</form>
<div class="pages_bottom">
    <a href="##" class="btn_to_save">保存</a>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script>

    $('.ud_li select').change(function () {
        var img_path1 = $(".ud_li select option:selected").val();
        $('input[name="bg_img"]').val(img_path1);
        $(".have_bg").css("background-image","url("+img_path1+")");
        $(".have_bg").css("background-size","100%,100%");
    });

</script>
<script type="text/javascript" src="{{asset('js/admin/distribution_config.js')!}?v=1.1.9"></script>
<script type="text/javascript">
    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
