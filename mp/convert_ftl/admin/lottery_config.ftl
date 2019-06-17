<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/add_coupon.css?v=1.0.8" type="text/css" />
<link rel="stylesheet" href="/css/admin/lottery_manage.css?v=1.0.7" type="text/css" />

<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span style="color: #666;">幸运大抽奖</span>
</div>
<div class="main-container fix_every_footer">
    <form method="post" id="form1">
        {{csrf_field()!}
        <div class="list_nav">
            <ul class="nav_lists clearfix">
                <li><a href="/admin/market/lottery/list?top_index=4">所有抽奖</a></li>
                <li><a href="/admin/market/lottery/list?top_index=4&nav=1">进行中</a></li>
                <li><a href="/admin/market/lottery/list?top_index=4&nav=2">未开始</a></li>
                <li><a href="/admin/market/lottery/list?top_index=4&nav=3">已过期</a></li>
                <li><a href="/admin/market/lottery/list?top_index=4&nav=4">已停用</a></li>
                <li class="actives"><a href="##">${title!}</a></li>
            </ul>
        </div>
        <div class="add_lottery">
        <div class="add_lottery_content clearfix">
            <div class="lo_left" style="width: 347px">
                <div class="lf_title">
                    <div>幸运大转盘</div>
                </div>
                <div class="lotteryde">
                    <div class="lo_top">
                        <div class="lo_area">
                            {{--<img src="http://${image_domain!}/image/admin/icon_lottery/lo_coin1.png" alt="" class="lo_conin1">--!}
                            {{--<img src="http://${image_domain!}/image/admin/icon_lottery/lo_coin2.png" alt="" class="lo_conin2">--!}
                            {{--<img src="http://${image_domain!}/image/admin/icon_lottery/lo_coin3.png" alt="" class="lo_conin3">--!}
                            <div class="lo_bg ">
                                <ul class=" clearfix">
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/1.png" alt="">
                                        <div>四等奖</div>
                                    </li>
                                    <li class="lo_item">
                                        <img <#if ($lottery->no_award_image) src="${lottery->no_award_image!}" <#else>
                                        src="http://${image_domain!}/image/admin/icon_lottery/thank.png" </#if> alt="">
                                        <div><#if ($lottery->no_award_icon) ${lottery->no_award_icon!} <#else> 谢谢参与 </#if></div>
                                    </li>
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/2.png" alt="">
                                        <div>三等奖</div>
                                    </li>
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/3.png" alt="">
                                        <div>一等奖</div>
                                    </li>
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/4.png" alt="">
                                        <div>二等奖</div>
                                    </li>
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/1.png" alt="">
                                        <div>四等奖</div>
                                    </li>
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/2.png" alt="">
                                        <div>三等奖</div>
                                    </li>
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/1.png" alt="">
                                        <div>四等奖</div>
                                    </li>
                                    <li class="lo_item">
                                        <img src="http://${image_domain!}/image/admin/icon_lottery/4.png" alt="">
                                        <div>二等奖</div>
                                    </li>
                                </ul>
                            </div>
                            <div class="lo_linbo">
                                <img src="http://${image_domain!}/image/admin/icon_lottery/lo_words.png" alt="">
                                <div>张三获得<span>一等奖50积分</span></div>
                            </div>
                        </div>
                    </div>
                    <div class="btn_lott">
                        <div class="btn_lott2">立即抽奖</div>
                    </div>
                    <div class="lo_rule">
                        <div class="lo_rule_tile">
                            <img src="http://${image_domain!}/image/admin/icon_lottery/lo_rule_l.png" alt="">
                            <span>活动规则</span>
                            <img src="http://${image_domain!}/image/admin/icon_lottery/lo_rule_r.png" alt="">
                        </div>
                        <div class="rule_content">
                            <div>活动有效期：</div>
                            <div class="huoodng_time">
                                <span class="start">${lottery->start_time!}</span>
                                至
                                <span class="end">${lottery->end_time!}</span>
                            </div>
                            <div>活动说明：</div>
                            <div class="huodong_tips">{!! '<p>'.str_replace(array("\r\n", "\r", "\n"),'</p><br/><p>',$lottery->lottery_explain).'</p>' !!}</div>
                        </div>
                    </div>
                </div>

            </div>

            <div class="lo_right">
                 <div class="activity_info">
                    <div class="activity_info_title">活动信息</div>
                     <ul>
                         <input hidden name="id" value="${lottery->id!}" />
                         <li class="clearfix ai_li">
                             <span><em>*</em>活动名称：</span>
                             <input name="lottery_name" type="text" placeholder="最多支持10个字" value="${lottery->lottery_name!}" maxlength="10" class="act_mame">
                         </li>
                         <li class="clearfix ai_li">
                             <span><em>*</em>活动有效期：</span>
                             <div class="choose_times">
                                 <div>
                                     <span>生效时间：</span>
                                     <input name="start_time" value="${lottery->start_time!}" type="text" class="start_time" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false})" autocomplete="off">
                                 </div>
                                 <div>
                                     <span>过期时间：</span>
                                     <input name="end_time" value="${lottery->end_time!}" type="text" class="end_time" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false})" autocomplete="off">
                                 </div>
                             </div>
                         </li>
                         <li class="clearfix ai_li">
                             <span><em>*</em>活动说明：</span>
                             <textarea name="lottery_explain" class="activity_tips" id="" cols="30" rows="10">${lottery->lottery_explain!}</textarea>
                         </li>
                     </ul>
                 </div>
                 <div class="activity_rule">
                     <div class="activity_info_title">规则设置</div>
                     <ul>
                         <li class="ar_li clearfix">
                             <span>免费抽奖：</span>
                             <input name="free_chances" value="${lottery->free_chances!}" type="text" placeholder="为空则表示不限制" onkeyup="value=value.replace(/[^\d]/g,'')">
                             <p class="inline_tips">用户可免费抽奖几次</p>
                         </li>
                         <li class="ar_li clearfix">
                             <span>分享抽奖：</span>
                             <div class="share_lottery">
                                 <div class="share_radio">
                                     <input type="radio" name="can_share" value="1" <#if ($lottery->can_share == 1) checked </#if>> 允许
                                     <input type="radio" name="can_share" value="0" <#if ($lottery->can_share != 1) checked </#if> style="margin-left: 8px"> 不允许
                                 </div>
                                 <p class="normal_tips">用户无免费抽奖机会时可通过分享给好友获得抽奖机会</p>
                                 <div class="each_times">
                                    分享最多获得<input name="share_chances" value="${lottery->share_chances!}" type="text" class="comme_wid" placeholder="为空表示不限制" onkeyup="value=value.replace(/[^\d]/g,'')">次抽奖机会
                                 </div>
                             </div>
                         </li>
                         <li class="ar_li clearfix">
                             <span>付费抽奖：</span>
                             <div class="pay_lottery">
                                 <div class="pay_radio">
                                     <input type="radio" name="can_use_score" value="1" <#if ($lottery->can_use_score == 1) checked </#if>> 允许
                                     <input type="radio" name="can_use_score" value="0" <#if ($lottery->can_use_score != 1) checked </#if> style="margin-left: 8px"> 不允许
                                 </div>
                                 <p class="normal_tips">用户无法通过分享获取抽奖机会时可通过消耗积分获得抽奖机会</p>
                                 <div class="each_times">
                                     每次抽奖消耗积分：
                                     <input name="score_per_chance" value="${lottery->score_per_chance!}" type="text"
                                            style="margin-right: 0;width: 140px;" placeholder="为空时不消耗积分" class="comme_wid" onkeyup="value=value.replace(/[^\d]/g,'')">
                                     <p class="inline_tips">用户每次参与需要消耗积分数</p>
                                 </div>
                                 <div class="money_times" style="margin-top: 10px">
                                     付费最多获取<input name="score_chances" value="${lottery->score_chances!}" type="text"
                                                  placeholder="为空表示不限制" class="comme_wid" onkeyup="value=value.replace(/[^\d]/g,'')">次抽奖机会
                                 </div>
                             </div>
                         </li>
                         <li class="ar_li clearfix">
                             <span>未中奖赠送积分：</span>
                             <div class="dive_score">
                                 <input name="no_award_score"  onkeyup="value=value.replace(/[^\d]/g,'')"  value="${lottery->no_award_score!}" type="text" placeholder="为空表示不赠送积分" class="comme_wid" style="margin-left: 0;width: 185px">
                                 <p class="inline_tips">仅送给未中奖的用户</p>
                             </div>
                         </li>
                         <li class="ar_li clearfix">
                             <span></span>
                             <div class="up_imahe">
                                 <div class="up_icons clearfix">
                                     <input name="no_award_image" hidden value="${lottery->no_award_image!}">
                                     <img src="<#if ($lottery->no_award_image) ${lottery->no_award_image!} <#else> http://${image_domain!}/image/admin/icon_lottery/thank.png </#if>" alt="" class="icon_imgs">
                                     <div class="up_right">
                                         <div class="btn_up_img clearfix">
                                             <span class="choose_icon">修改</span>
                                             <a href="##" class="add_no_award">上传未中奖图标</a>
                                             <a href="##" class="clear_no_award">清空</a>
                                         </div>
                                         <p class="size_rule">仅支持jpg/png/尺寸80*80 不超过1M</p>
                                     </div>
                                 </div>
                                 <div class="icon_detail">
                                     icon描述：<input name="no_award_icon" value="${lottery->no_award_icon!}" type="text" placeholder="最多可填写4个字" maxlength="4">
                                 </div>
                             </div>
                         </li>
                         {{--<li class="ar_li clearfix">--!}
                             {{--<span>活动说明：</span>--!}
                             {{--<textarea name="" class="activity_tips" id="" cols="30" rows="10" placeholder="这次没有中奖，抱歉啦！"></textarea>--!}
                         {{--</li>--!}
                     </ul>
                 </div>
                <div class="lottery_setting">
                    <div class="activity_info_title">抽奖设置</div>
                    <input hidden name="first_award" value="${lottery->first_award!}">
                    <input hidden name="second_award" value="${lottery->second_award!}">
                    <input hidden name="third_award" value="${lottery->third_award!}">
                    <input hidden name="fourth_award" value="${lottery->fourth_award!}">
                    <ul id="lottery_module">
                        <li class="lc_li clearfix rule_tips">
                            不同等级分别设置不同的奖项、每个奖项的份数和中奖概率，四个等级中奖概率之和小于等于100%。
                        </li>
                        <li class="lc_li clearfix rule_tips">
                            例如：一等奖1份，中奖概率为2%；二等奖2份，中奖概率为3%；三等奖3份，中奖概率为4%；四等奖4份，中奖概率为5%。则用户A抽奖时，中奖概率为（2%+3%+4%+5%）=14%。
                        </li>
                        <li class="lc_li clearfix">
                            <div class="grade_setting">
                                <ul class="grade_nav clearfix">
                                    <li class="award_level first_award checked_tab" onclick="select_award_level(this)" level="first_award">一等奖</li>
                                    <li class="award_level second_award" onclick="select_award_level(this)" level="second_award">二等奖</li>
                                    <li class="award_level third_award" onclick="select_award_level(this)" level="third_award">三等奖</li>
                                    <li class="award_level fourth_award" onclick="select_award_level(this)" level="fourth_award">四等奖</li>
                                </ul>
                                <ul class="grade_detail">
                                    <li class="gd_li clearfix">
                                        <span>中奖概率：</span>
                                        <input class="chance" type="text" placeholder="0-100" onkeyup="value=value.replace(/[^\d.]/g,'')"> %
                                    </li>
                                    <li class="gd_li clearfix">
                                        <span>选择奖品：</span>
                                        <div class="type_radio">
                                            <input type="radio" name="present_type" id="scores" value="0">积分
                                            <input type="radio" name="present_type" value="1" id="yues">用户余额
                                            <input type="radio" name="present_type" value="2" id="coupons">优惠券
                                            <input type="radio" name="present_type" value="4" id="gifts">赠品
                                            <input type="radio" name="present_type" value="3" id="owns">自定义
                                        </div>
                                    </li>
                                    <li class="gd_li clearfix coupon_type">
                                        <span></span>
                                        <ul class="coupon_set">
                                            {{--积分部分--!}
                                            <li class="clearfix score_set four_one">
                                                <span><em>*</em>赠送积分：</span>
                                                <input class="award_score" type="text" placeholder="请填写积分数"  onkeyup="value=value.replace(/[^\d]/g,'')">
                                            </li>
                                            {{--余额部分--!}
                                            <li class="clearfix yue_set four_one">
                                                <span><em>*</em>赠送余额：</span>
                                                <input class="award_account" type="text" placeholder="请填写余额数"  onkeyup="value=value.replace(/[^\d]/g,'')">
                                            </li>
                                            {{--优惠券部分--!}
                                            <li class="clearfix coupon_set four_one">
                                                <span><em>*</em>优惠券：</span>
                                                <select class="award_coupon">
                                                    <option value="" num="0">未选择</option>
                                                    <#if ($coupon_list)
                                                        <#list ($coupon_list as $value)
                                                            <option value="${value->id!}" num="${value->surplus!}">${value->act_name!}</option>
                                                        </#list>
                                                    </#if>
                                                </select>
                                                <a href="javascript:void(0)" class="btn_fresh">刷新</a>
                                                <a href="/admin/market/coupon/add" target="_blank" class="btn_build">新建</a>
                                            </li>
                                            <li class="clearfix coupon_set four_one" style="margin: 0;">
                                                <input hidden class="coupon_num" value="0"/>
                                                <p class="tip_grade">优惠券可用库存<a class="show_coupon_num">0</a>份</p>
                                            </li>
                                            {{--赠品部分--!}
                                            <li class="clearfix gift_set four_one">
                                                <div class="clearfix">
                                                    <span><em>*</em>赠送赠品：</span>
                                                    <div class="choose_goods" >
                                                        <img src="http://${image_domain!}/image/admin/icon_jia.png" alt="" />选择商品
                                                        <input type="hidden" name="recommend_goods_id" value="${coupon->recommend_goods_id!}">
                                                    </div>
                                                    <div style="margin-top:40px;">
                                                        <span style="width: 80px;">赠品有效期:</span>
                                                        <div class="choose_good">
                                                            <input class="keep_days" type="text" placeholder="默认保留7天" style="width: 100px;"  onkeyup="value=value.replace(/[^\d]/g,'')"> 天
                                                        </div>
                                                        <div style="color: #999;padding-left:80px;margin-top:3px;">中奖用户需在有效期内领取，过期后将无法领取</div>
                                                    </div>
                                                </div>

                                                <div class="goods_area" style="margin:0;padding-right:5px">
                                                    <#if ($coupon->recommend_goods_id)
                                                        <table class="goods_table" goods_array="${act->recommend_goods_id!}">
                                                            <thead>
                                                            <tr>
                                                                <th>商品名称</th>
                                                                <th>价格</th>
                                                                <th>库存</th>
                                                                <th>操作</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody class="tbody">
                                                            <#list ($coupon->goods_info as $item)
                                                                <tr num="${item->prd_number!}">
                                                                    <td>
                                                                        <div class="goods_info clearfix">
                                                                            <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                                                            <div class="goods_name">
                                                                                ${item->goods_name!}
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                    <td>${item->shop_price!}</td>
                                                                    <td>${item->goods_number!}</td>
                                                                    {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                                                                    <td><a href="javascript:void(0)" goods_id="${item->goods_id!}" class="del">删除</a></td>
                                                                </tr>
                                                            </#list>
                                                            </tbody>
                                                        </table>

                                                    <#else>

                                                        <table class="goods_modal">
                                                            <thead>
                                                            <tr>
                                                                <th>商品名称</th>
                                                                <th>价格</th>
                                                                <th>库存</th>
                                                                <th>操作</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody class="tbody"></tbody>
                                                        </table>
                                                    </#if>
                                                </div>
                                            </li>
                                            {{--自定义部分--!}
                                            <li class="clearfix own_set four_one">
                                                <span>自定义：</span>
                                                <input class="award_other" type="text" placeholder="请填写自定义文字" maxlength="50" style="width: 150px;">
                                            </li>
                                            <li class="clearfix">
                                                <span>奖品份数：</span>
                                                <input class="present_number" type="text" placeholder="请填写份数"  onkeyup="value=value.replace(/[^\d]/g,'')"> 份
                                            </li>
                                            <li class="clearfix">
                                                <span></span>
                                                <div class="num_tips">
                                                    <p class="tip_grade">奖品份数限制中奖人数，中奖人数达到奖品份数，则后续抽奖用户不再中奖</p>
                                                    <p class="tip_grade">份数为空则不设此奖项，即该奖项中奖概率为0</p>
                                                </div>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </li>
                        <li class="lc_li clearfix">
                            <div class="grade_area">
                                <div class="up_gr_icons clearfix">
                                    <img src="http://${image_domain!}/image/admin/icon_lottery/1.png" alt="" class="icon_gr_imgs">
                                    <div class="up_gr_right">
                                        <div class="btn_up_img clearfix">
                                            <span class="choose_gr_icon">修改</span>
                                            <a href="##" class="add_award">上传中奖图标</a>
                                            <a href="##" class="clear_award">清空</a>
                                        </div>
                                        <p class="size_rule">仅支持jpg/png/尺寸80*80 不超过1M</p>
                                    </div>
                                </div>
                                <div class="icon_gr_detail">
                                    icon描述：<input class="icon_text" type="text" placeholder="最多可填写4个字" maxlength="4">
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
        <div class="coupon_footer fix_footer">
            <a href="##" class="btn_save_cfg">保存</a>
        </div>
    </form>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/admin/lottery_config.js?v=1.1.6" type="text/javascript"></script>
<script type="text/javascript">
    getPowerInfo('main_config','lottery','sub_4','抽奖',0);

    $(".fix_footer").outerWidth($('.fix_every_footer').width());
    var hasSaved = true;
    util.inputChange();
    util.selectChange();
    util.radioChange('share_primit');
    util.radioChange('pay_primit');
    util.radioChange('present_type');
    $(".choose_icon").click(function () {
        hasSaved == false
    })
    $(".up_gr_icons .btn_up_img a").click(function () {
        hasSaved == false
    })
    $(".btn_fresh").click(function () {
        hasSaved == false
    })
    $(".btn_build").click(function () {
        hasSaved == false
    })
    $(".btn_fresh").click(function () {
        hasSaved == false
    })
    $(".up_icons .btn_up_img a").click(function () {
        hasSaved == false
    })
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗';
        }
    };
</script>