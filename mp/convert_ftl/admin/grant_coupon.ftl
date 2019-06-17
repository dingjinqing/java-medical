<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/grant_coupon.css?v=1.0.3">
<div style="min-width: 1090px;">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4" >{{ trans("admin/market_manage.market_manage_title")!}</a> / </span>
        <span>{{ trans("admin/market_manage.coupon_grant_title")!} / </span>
        <span style="color: #666;">{{ trans("admin/market_manage.grant_coupon_title")!}</span>
    </div>

    <div class="main-container fix_every_footer">
        <form action="/admin/market/grant" method="post" id="form1">
            {{csrf_field()!}
            <div class="grant_detail">
                <ul class="content">
                    <li class="clearfix">
                        <span class="act_title"><span class="must">*</span>活动名称：</span>
                        <div class="act_right">
                            <input type="text" placeholder="请输入活动名称" name="act_name">
                        </div>
                    </li>
                    <li class="clearfix" id="join-person">
                        <span class="act_title"><span class="must">*</span>选择人群：</span>
                        <div class="act_right">
                            <p class="tips">以下筛选条件为“或”的关系</p>
                            <div style="margin-top: 15px;">
                                <input type="checkbox" name="cart_box" value="1"><span>加购人群</span><span class="tips" style="margin-left: 30px;">30天内在本店内有加入购物车行为，但没有支付的用户</span>
                            </div>
                            <div>
                                <input type="checkbox" name="goods_box" value="1"><span>购买指定商品人群</span> <span class="tips" style="margin-left: 30px;">最多可选择3件商品</span>
                                <div class="goods_list_box">
                                    <input type="hidden" name="goods_ids">
                                    <span>选择商品</span>
                                    <ul class="goods_list clearfix" style="margin:8px 0 0 80px;">
                                        <li class="add_goods">
                                            <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div>
                                <input type="checkbox" name="card_box" value="1">持有 
                                <input type="hidden" name="card_id" class="join-person">
                                <select name="" id="" class="card_id">
                                    <option value="-1">请选择会员卡</option>
                                    <#list ($card as $item)
                                        <option value="${item->id!}">${item->card_name!}</option>
                                    </#list>
                                </select>
                                <span>会员卡人群</span>
                                <div class="card-info">
                                    <div class="card-info-row">
                                        <span class="card-choose">已选：</span>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <input type="checkbox" name="tag_box" value="1">属于
                                <input type="hidden" name="tag_id">
                                <select name="" id="" class="label_id">
                                    <option value="-1">请选择会员标签</option>
                                    <#list ($tag as $item)
                                        <option value="${item->tag_id!}">${item->tag_name!}</option>
                                    </#list>
                                </select>
                                <span>标签人群</span>
                                <div class="label-info">
                                    <div class="label-info-row">
                                        <span class="label-choose">已选：</span>
                                    </div>
                                </div>
                            </div>
                            <div>
                                <input type="checkbox" name="member_box" value="1">
                                <input type="hidden" name="send_member" class="join-person">
                                <span>选择指定的会员</span>
                                <a href="javsscript:;" id="open_common_user">+ 添加会员</a>
                                已选择会员<span class="member_num common_member_num">0</span>人
                            </div>
                            <div>
                                <input type="checkbox" name="custom_box" value="1"><span>自定义</span>
                                <select name="" id="choose_condition">
                                    <option value="0">请选择</option>
                                    <option value="1">N天内有交易记录</option>
                                    <option value="2">N天内没有交易记录</option>
                                    <option value="3">累计购买次数大于N次</option>
                                    <option value="4">累计购买次数小于N次</option>
                                    <option value="5">购买商品均价大于N元</option>
                                    <option value="6">购买商品均价小于N元</option>
                                    <option value="7">指定时间内有登录记录</option>
                                </select>
                                <div class="choose_content">
                                    
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="clearfix">
                        <span class="act_title"><span class="must">*</span>选择优惠券：</span>
                        <div class="act_right">
                            <div class="coupon_box clearfix">
                                <input type="hidden" name="coupon_ids" value="${data_list['coupon_ids']!}">
                                <div class="tem_right">
                                    <div class="coupon_div clearfix" coupon_json="" <#if ($data_list) style="display: block" </#if>>
                                        <#if ($data_list['coupons'])
                                            <#list ($data_list['coupons'] as $k=>$cou)
                                                <div class="coupon_list">
                                                    <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del" <#if ($edit==1 && $data_list->act_start_time<= date("Y-m-d H:i:s")) style="display: none" </#if>>
                                                    <input type="hidden" coupon_id="${cou->id!}" act_code="${cou->act_code!}" denomination="${cou->denomination!}" class="coupon_info">
                                                    <div class="coupon_list_top">
                                                        <#if ($cou->act_code == 'voucher')
                                                            ¥<span>${cou->denomination!}</span>
                                                        <#elseif>($cou->act_code == 'discount')
                                                            打<span>${cou->denomination!}</span>折
                                                        </#if>
                                                    </div>
                                                    <div class="coupon_list_center">
                                                        <#if ($cou->use_consume_restrict == 0)
                                                            <div class="coupon_center_limit">不限制</div>
                                                        <#else>
                                                            <div class="coupon_center_limit">满${cou->least_consume!}使用</div>
                                                        </#if>
                                                        <div class="coupon_center_number">剩余<span>${cou->surplus!}</span>张</div>
                                                    </div>
                                                    <div class="coupon_list_bottom">
                                                        领取
                                                    </div>
                                                </div>
                                            </#list>
                                        </#if>
                                        <div class="card_add card_add_click">
                                        <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                                        <p>添加优惠券</p>
                                        </div>  
                                    </div>
                                    <p style="color:#bbb">最多添加5张优惠券，已过期和已停用的优惠券不能添加</p>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="clearfix send_time">
                        <span class="act_title"><span class="must">*</span>发送时间：</span>
                        <div class="act_right">
                            <div>
                                <input type="radio" name="send_action" value="1"><span>立即发送</span>

                            </div>
                            <div>
                                <input type="radio" name="send_action" value="2"><span>定时发送</span><input type="text" name="start_time" style="margin-left: 15px" id="sendDate" onclick="picker();" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false"  autocomplete="off">
                            </div>
                        </div>
                    </li>
                    <input type="hidden" name="user_id"/>
                    {{--<li>
                        <div class="expect_member_num">
                            预计发放用户数：<span class="user_count">0</span>人  <a href="#"class="look_people">查看</a>
                        </div>
                    </li>--!}
                </ul>
            </div>
            <div class="btn_grant fix_footer">
                <a href="##">确认发放</a>
            </div>
        </form>
    </div>
    <div class="coupon_list_clone hide">
        <div class="coupon_list">
            <img src="http://${image_domain!}/image/admin/sign_del.png" class="coupon_del">
            <input type="hidden" coupon_id="" act_code="" denomination="" class="coupon_info">
            <div class="coupon_list_top">
                ¥<span>××</span>
            </div>
            <div class="coupon_list_center">
                <div class="coupon_center_limit">满××使用</div>
                <div class="coupon_center_number">剩余<span>××</span>张</div>
            </div>
            <div class="coupon_list_bottom">
                领取
            </div>
        </div>
    </div>
    {{--持有会员卡点击加号重复的部分--!}
    <div class="card_add_lines_parent hide">
        <div class="card_add_lines">
            持有
            <select name="card" id="">
                <option value="0">请选择</option>
                <#list ($card as $item)
                    <option value="${item->id!}">${item->card_name!}</option>
                </#list>
            </select>
            会员卡
            <a href="##" class="del_lines"><img src="http://${image_domain!}/image/admin/sign_del.png" alt="" class=""></a>
        </div>
    </div>
    {{--所属标签重复的部分--!}
    <div class="labelling_add_lines_parent hide">
        <div class="labelling_add_lines">
            属于
            <select name="tag" id="">
                <option value="0">请选择</option>
                <#list ($tag as $item)
                    <option value="${item->tag_id!}">${item->tag_name!}</option>
                </#list>
            </select>
            会员标签
            <a href="##" class="del_lines"><img src="/image/admin/sign_del.png" alt="" class=""></a>
        </div>
    </div>
</div>
<div class="clone">
    <div class="clearfix" value="1">
        <span>N天内有交易记录：</span><input type="text" name="have_pay"><span>天内</span> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
    <div class="clearfix" value="2">
        <span>N天内没有交易记录：</span><input type="text" name="no_pay"><span>天内</span> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
    <div class="clearfix" value="3">
        <span>累计购买次数大于N次：</span><input type="text" name="min_count"><span>次</span> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
    <div class="clearfix" value="4">
        <span>累计购买次数小于N次：</span><input type="text" name="max_count"><span>次</span> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
    <div class="clearfix" value="5">
        <span>购买商品均价大于N元：</span><input type="text" name="min_ave_price"><span>元</span> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
    <div class="clearfix" value="6">
        <span>购买商品均价小于N元：</span><input type="text" name="max_ave_price"><span>元</span> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
    <div class="clearfix time" value="7">
        <span>指定时间内有登陆记录：</span><input type="text" name="point_start_time"  id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off">　至　<input type="text" name="point_end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off"> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
</div>

<div id="set-goods" class="hide">
    <form action="" method="post" id="form1">
        <div class="goods_search">
            {{csrf_field()!}
            <input type="hidden" id="page" name="page"/>
            <input type="hidden" id="user" value="" disabled/>
            <input type="hidden" name="is_click_search" value="0"/>
            <input type="hidden" id="record_select_value" />
            <div class="search-list">
                <span>id</span>
                <input type="text" name="id" value=""/>
                <span>昵称</span>
                <input type="text" name="username" value="">
                <span>手机号</span>
                <input type="text" name="mobile" value="">
                <a class="btn_search" style="display: inline-block; text-align: center; line-height: 30px; cursor: pointer;">搜索</a>
            </div>
        </div>
        <div class="goods_tb">
            <table width="100%">
                <thead>
                <tr>
                    <td><input type="checkbox" name="check_all" /></td>
                    <td>id</td>
                    <td>昵称</td>
                    <td>手机号</td>
                    <td>可接收信息数量</td>
                    <td>是否关注公众号</td>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div class="no_data_style" style="width: 100%;border: 1px solid #eee;">
                <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                </div>
                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
            </div>
            <div id="pageshow" style="text-align: right;"></div>
        </div>
    </form>
</div>
<div id="template_table">
    <table width="100%">
        <tbody>
            <tr data-back="true">
                <td><input type="checkbox" name="user_id[]" value="" onclick="checkOne(this)" /></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </tbody>
    </table>
</div>
<#include ("admin.common_user")
<#include "/admin/footer.ftl">
<script src="{{asset('js/admin/grant_coupon.js')!}?v=1.1.11"></script>
<script>
    $('.goods_list').on('click','.add_goods',function(){
        var record_select_value = $('input[name="goods_ids"]').val();
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 2
                , title: ['选择商品', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: ['945px','450px']
                , content: '/admin/public/purchase/goods/list?is_spec_goods=0&record_select_value='+record_select_value //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,success: function (layero, index) {
    
                }
                , yes: function (index, layero) { //保存按钮的回调
                    var body = layer.getChildFrame('body', index);
                    var checkedId = body.find('#record_select_value').val();
                    if (body.find('#record_select_value').val() == '') {
                        util.mobile_alert('请选择商品');
                        return false;
                    }
                    if (body.find('#record_select_value').val().split(',').length > 3) {
                        util.mobile_alert('最多选择3个商品');
                        return false;
                    }
                    $('input[name="goods_ids"]').val(body.find('#record_select_value').val());
                    if ($('input[name="goods_box"]:checked').val()==1) getUserMember();
                    util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                        console.log(response.content);
                        var list = response.content;
                        var html = '';
                        for (var i in list) {
                            html+=`<li>
                                        <img src="${list[i].goods_img}" class="goods_img" alt="">
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" goods_id="${list[i].goods_id}" style="display: inline-block;">
                                    </li>`;
                        }
                        $('.goods_list').html(
                            `<li class="add_goods">
                                        <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
                            </li>`)
                        $('.add_goods').before(html);
                        
                        if(list.length >= 3){
                            $('.add_goods').hide();
                        }
                        if(list.length < 3){
                            $('.add_goods').show();
                        }
                        layer.close(index);
                    }, {is_spec_goods: 0,  select_id: body.find('#record_select_value').val()})
    
                }, btn2: function (index, layero) {
                    //按钮取消的回调
    
                }
            });
        });
    });
</script>