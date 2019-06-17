<#include "/admin/header.ftl">
<link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/template_config.css?v=1.0.4" type="text/css" />
<style>
    a{
        text-decoration: none;
    }
    .search-list {
        line-height: 50px;
    }
    .search-list span {
        width: 50px;
        display: inline-block;
    }
    .btn_search:hover{color: #fff;text-decoration:none;}
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span><a href="/admin/market/message/template/list?top_index=4">消息推送</a> / </span><span style="color: #666;">添加消息模板</span>
</div>
<div class="main-container fix_every_footer">
    <form action="/admin/market/message/template/add" method="post" id="form1">
        {{ csrf_field()!}
        <div class="template_info"  >
            {{--<ul class="custom_title clearfix">--!}
                {{--<li><a href="##">添加模板消息消息模板</a></li>--!}
            {{--</ul>--!}
            <div class="template_content" style="margin-top: 0;padding-top: 15px;">
                <div class="prompt">
                    <img src="http://${image_domain!}/image/admin/notice_img.png" />
                    <span>每人每天最多发送一条自定义模板消息，请勿发送骚扰信息，违者将受到微信官方相应处罚，严重者将被封禁小程序</span>
                </div>
                <div class="clearfix">
                    <div class="template_left">
                        <div class="left_title"></div>
                        <div class="left_info">
                            <div class="left_template">
                                <div class="left_temlpate_top">
                                    <img src="http://${image_domain!}/image/admin/shop_logo_default.png" width="20px" style="margin-top: -3px;" />
                                    <span>此处为小程序的名称</span>
                                    <span class="fr">...</span>
                                </div>
                                <div class="left_template_center">
                                    <div class="left_tem_type">商家活动通知</div>
                                    <div class="left_tem_time">2018年6月11日</div>
                                    <div class="left_tem_prompt">业务标题</div>
                                    <div class="left_tem_title">业务标题</div>
                                    <div class="left_tem_content clearfix">
                                        <div class="fl">业务内容</div>
                                        <div class="fr">&times;&times;&times;</div>
                                    </div>
                                </div>
                                <div class="left_bottom">进入小程序查看</div>
                                <div class="left_bottom">拒收通知</div>
                            </div>
                        </div>
                    </div>
                    <div class="template_right">
                        <ul class="template_right_ul">
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em><span>消息名称：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <input type="text" class="template_name" maxlength="20" onblur="limiWord(this,20)" style="width: 190px;" name="name" />
                                        <div>
                                            <span class="news_num">0</span>/<span class="news_all">20</span>
                                        </div>
                                    </div>
                                    <div class="right_error"></div>
                                    <div class="right_prompt">只作为商家记录使用，用户不会看到这个名称</div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em><span>消息类型：</span>
                                </div>
                                <div class="tem_right" style="margin-top: 6px;">
                                    {{-- <div class="right_ipt"> --!}
                                        <select name="action" id="" style="display: none;">
                                            {{-- <option value="1">业务处理通知</option> --!}
                                            <option value="2">商家活动通知</option>
                                            {{-- <option value="3">活动加入成功提醒</option> --!}
                                        </select>
                                        商家活动通知
                                {{--   </div> --!}
                               </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em><span>业务标题：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="right_ipt">
                                        <input type="text" maxlength="7" onblur="limiWord(this,7)" name="title" />
                                        <div>
                                            <span class="news_num">0</span>/<span class="news_all">7</span>
                                        </div>
                                    </div>
                                    <div class="right_error"></div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em><span>业务内容：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="choose_template">
                                        <div class="tem_right_content">选择模板</div>
                                        <div class="right_ipt right_textarea">
                                            <textarea placeholder="请输入小程序推送内容" maxlength="50" onblur="limiWord(this,50)" name="content"></textarea>
                                            <div>
                                                <span class="news_num">0</span>/<span class="news_all">50</span>
                                            </div>
                                            <span class="add_template">添加为模板</span>
                                        </div>
                                    </div>
                                    <div class="right_error"></div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em><span>进入小程序查看：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="tem_right_link" style="display: inline-block;padding: 0 5px">选择链接</div>
                                    <input type="hidden" name="page_link" />
                                    <span></span>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em><span>参与活动人群：</span>
                                </div>
                                <div class="tem_right" style="margin-top: 6px;" id="join-person">
                                    <div class="tips">以下筛选条件为“或”关系</div>
                                    <div>
                                        <input type="checkbox" name="cart_box" value="1"><span>加购人群</span><span class="tips" style="margin-left: 30px;">30天内在本店内有加入购物车行为，但没有支付的用户</span>
                                    </div>
                                    <div>
                                        <input type="checkbox" name="goods_box" value="1"><span>指定购买商品人群</span> <span class="tips" style="margin-left: 30px;">最多可选择3件商品</span>
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
                                        <input type="hidden" name="card_list">
                                        <select name="" id="" class="card_id">
                                            <option value="-1">请选择会员卡</option>
                                            <#list  ($cardList as $item)
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
                                        <input type="hidden" name="tag_list">
                                        <select name="" id="" class="tag_id">
                                            <option value="-1">请选择会员标签</option>
                                            <#list  ($tagList as $tag)
                                                <option value="${tag->tag_id!}">${tag->tag_name!}</option>
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
                                        <input type="hidden" name="send_member">
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
                                            <option value="7">指定时间内有登陆记录</option>
                                        </select>
                                        <div class="choose_content">
                                            
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="fl">
                                    <em>*</em><span>发送时间：</span>
                                </div>
                                <div class="tem_right">
                                    <div class="tem_right_send" style="margin: 0;">
                                        <label for="now_send">
                                            <input id="now_send" value="1" type="radio" name="send_action" checked />
                                            立即发送
                                        </label>
                                        <div class="right_prompt">
                                            <input type="hidden" name="user_id"/>
                                            预计送达人数：<span class="user_count">0</span> <span class="look_people">查看</span>
                                        </div>
                                    </div>
                                    <div class="tem_right_send">
                                        <label for="continue_send">
                                            <input id="continue_send" value="2" type="radio" name="send_action"  />
                                            持续发送
                                        </label>
                                        <div>
                                            <input type="text" placeholder="请选择日期" id="startDate" onclick="picker()" name="start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}', dateFmt:'yyyy-MM-dd HH:mm:ss'})" autocomplete="off" />
                                            至
                                            <input type="text" placeholder="请选择日期" id="endDate" onclick="picker()" name="end_time" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}', dateFmt:'yyyy-MM-dd HH:mm:ss'})" autocomplete="off" />
                                        </div>
                                        <div class="right_prompt">所有可送达的用户均会第一时间收到一次此消息</div>
                                    </div>
                                    <div class="tem_right_send">
                                        <label for="timing_send">
                                            <input id="timing_send" value="3" type="radio" name="send_action"  />
                                            定时发送
                                            <input type="text" placeholder="请选择日期" onclick="picker()" name="task_start_time" onfocus="WdatePicker({minDate: '{{ date("Y-m-d H:i:s", strtotime(date("y-m-d H:i:s") ."+ 5 min"))!}', dateFmt: 'yyyy-MM-dd HH:mm:ss'})" autocomplete="off"/>
                                        </label>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="template_footer fix_footer">
                <div class="save">保存并发送</div>
            </div>
        </div>
    </form>
</div>

<div class="card_choose hide">
    <div>
        <span>持有</span>
        <select name="card_list[]" id="">
            <option value="">请选择</option>
            <#list  ($cardList as $item)
                <option value="${item->id!}">${item->card_name!}</option>
            </#list>
        </select>
        <span>会员卡</span>
        <img src="http://${image_domain!}/image/admin/sign_del.png" class="reduce_card" />
    </div>
</div>
<div class="label_choose hide">
    <div>
        <span>属于</span>
        <select name="card_list[]" id="">
            <option value="">请选择</option>
            <#list  ($tagList as $item)
                <option value="${item->tag_id!}">${item->tag_name!}</option>
            </#list>
        </select>
        <span>会员标签</span>
        <img src="http://${image_domain!}/image/admin/sign_del.png" class="reduce_label" />
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
            <div style="color: #666; margin-bottom: 10px; padding-left: 30px;">注：因微信小程序官方限制，本日部分用户可接收的消息数量已达上限，故接收不到本次消息推送</div>
            <div class="search-list">
                <span>id</span>
                <input type="text" name="id" value=""/>
                <span>昵称</span>
                <input type="text" name="username" value="">
                <span>手机号</span>
                <input type="text" name="mobile" value="">
            </div>
            <div class="search-list">
                <span style="width: 110px">是否关注公众号</span>
                <select name="is_focusing_official">
                    <option value="0">是否关注公众号</option>
                    <option value="1">是</option>
                    <option value="2">否</option>
                </select>
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
            <div class="no_data_style" style="width: 100%;border: 1px solid #eee;margin-top:10px;">
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
        <span>指定时间内有登陆记录：</span><input type="text" name="point_start_time"  id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"><span style="display:block;margin:10px 0 10px 230px;">至</span><input type="text" name="point_end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" style="margin-left:155px" autocomplete="off"> <a href="javascript:;" class="del"><img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""></a>
    </div>
</div>
<script type="text/javascript">
    var hasSaved = true;
</script>
<script src="/js/admin/template_config.js?v=1.1.4" type="text/javascript"></script>
<#include ("admin.common_user")
<#include "/admin/footer.ftl">
<script type="text/javascript">
    //版本控制
    getPowerInfo('main_config','message_template','sub_4','消息推送',0);
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
    util.inputChange();
    util.selectChange();
    util.radioChange('send_action');
    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    // window.onbeforeunload = function(event) {
    //     if(hasSaved == false){
    //         event.returnValue = "确认要离开吗？";
    //     }
    // };
    var msg = '{{ session("message")!}';
    if (msg != '') {
        util.mobile_alert(msg);
    }
    function getUserMember(cb) {
        var param = getSearchParam();
        util.ajax_json('/admin/frame/user/getUserMember',function (d) {
            if (d.error == 0) {
                $('input[name="user_id"]').val(d.content);
                if (d.content)  {
                    $('.user_count').html(d.content.split(',').length);
                } else {
                    $('.user_count').html(0);
                }
            } else {
                console.log(response);
            }
        },param);
    }
    $('#join-person input').change(function () {
        getUserMember();
        $('input[name="user_id"]').val('');
    });
    // $('#join-person select').change(function () {
    //     getUserMember();
    //     $('input[name="user_id"]').val('');
    // });

    $('.clone input').blur(function () {
        if ($('input[name="custom_box"]:checked').val() == 1) {
            getUserMember();
        }
    });

    // 以下处理弹框js
    var record_select_value = [];
    //全选
    $('#set-goods input[name="check_all"]').click(function () {
        var is_checked = $(this).is(':checked');
        if (is_checked) {
            record_select_value = $('#user').val().split(',');
        } else {
            record_select_value = [];
        }
        $('input[name="user_id[]"]:not(:disabled)').prop('checked', is_checked);
        $('#record_select_value').val(record_select_value.join(','));
    });

    if (record_select_value.length > 0 && record_select_value.length == $('#user').val().split(',').length) {
        //触发全选
        $('#set-goods input[name="check_all"]').trigger('click');
    }

    //单选
    function checkOne(item) {
        record_select_value = $('#record_select_value').val() != '' ? $('#record_select_value').val().split(',') : [];
        var is_checked = $(item).is(':checked');
        if (is_checked) {
            record_select_value.push($(item).val());
        } else {
            var index = $.inArray($(item).val(), record_select_value);
            record_select_value.splice(index, 1);
        }
        if (record_select_value.length > 0 && record_select_value.length == $('#user').val().split(',').length) {
            $('#set-goods input[name="check_all"]').prop('checked', true);
        } else {
            $('#set-goods input[name="check_all"]').prop('checked', false);
        }
        $('#record_select_value').val(record_select_value.join(','));
    }
    $('.btn_search').click(function () {
        // $('#record_select_value').val('');
        var paramJson = getSearchParam();
        paramJson.is_click_search = 1;
        paramJson.page = 1;
        paramJson.id = $('input[name="id"]').val();
        paramJson.username = $('input[name="username"]').val();
        paramJson.mobile = $('input[name="mobile"]').val();
        paramJson.is_focusing_official = $('select[name="is_focusing_official"]').val();
        loadUserList(paramJson);
    })

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