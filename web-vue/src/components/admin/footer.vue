
<script language="JavaScript" src="/js/smartadmin/libs/jquery-ui-1.10.3.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/bootstrap/bootstrap.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/notification/SmartNotification.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/smartwidgets/jarvis.widget.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/sparkline/jquery.sparkline.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/jquery-validate/jquery.validate.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/masked-input/jquery.maskedinput.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/select2/select2.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/msie-fix/jquery.mb.browser.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/plugin/fastclick/fastclick.min.js"></script>
<script language="JavaScript" src="/js/smartadmin/app.config.js"></script>
<script language="JavaScript" src="/js/smartadmin/app.min.js"></script>
<script language="JavaScript" src="/js/ZeroClipboard/ZeroClipboard.js"></script>
<script type="text/javascript">
    $(".fix_footer").outerWidth($('.fix_every_footer').outerWidth());
    $(function () {
        setTimeout(function () {
            $(".fix_footer").show();
        }, 100);
    });
</script>

<script type="text/javascript">
    $('.system_info,.system_info_content').hover(function () {
        $('.system_info_content').show();
        $('.system_shadow').show();
    },function () {
        $('.system_info_content').hide();
        $('.system_shadow').hide();
    });
    //左侧栏的点击事件
    $('.item-menu a').click(function () {
        var _html = $(this).find('span').html();
        var sub = $(this).attr('sub');
        var mod;
        var url = $(this).attr("link");
        var data = {};
        $(this).attr('href','##');
        if(sub == "sub_0"){
            if(_html == '概况统计'){
                mod = 'basic_yesterday';
                data = getPowerInfo('main_config',mod,'sub_0',_html);
            }else if(_html == '用户画像'){
                mod = 'portrait_user';
                data = getPowerInfo('main_config',mod,'sub_0',_html);
            }else if(_html == '访问分析'){
                mod = 'second_view';
                data = getPowerInfo('main_config',mod,'sub_0',_html);
            }else if(_html == '来源分析'){
                mod = 'visit_source';
                data = getPowerInfo('main_config',mod,'sub_0',_html);
            }else if(_html == '交易统计'){
                mod = 'trades_summary';
                data = getPowerInfo('main_config',mod,'sub_0',_html);
            }else{
                data.content = 1;
                data.message = '';
            }
        }
        else if(sub == "sub_1"){
            if(_html == '商家分类管理'){
                mod = 'sort';
                data = getPowerInfo('main_config',mod,'sub_1',_html);
            }else{
                data.content = 1;
                data.message = '';
            }
        }
        else if(sub == "sub_3"){
            if(_html == '标签管理'){
                mod = 'tag';
                data = getPowerInfo('main_config',mod,'sub_3',_html);
            }else{
                data.content = 1;
                data.message = '';
            }
        }
        else if(sub == "sub_4"){
            if(_html == '多人拼团'){
                mod = 'pin_group';
                data = getPowerInfo('main_config',mod,'sub_4','拼团');
            }else if(_html == '砍价'){
                mod = 'bargain';
                data = getPowerInfo('main_config',mod,'sub_4','砍价');
            }else if(_html == '活动有礼'){
                mod = 'activity_reward';
                data = getPowerInfo('main_config',mod,'sub_4',_html);
            }else if(_html == '分销'){
                mod = 'distribution';
                data = getPowerInfo('main_config',mod,'sub_4',_html);
            }else if(_html == '支付有礼'){
                mod = 'pay_reward';
                data = getPowerInfo('main_config',mod,'sub_4',_html);
            }else if(_html == '幸运大抽奖'){
                mod = 'lottery';
                data = getPowerInfo('main_config',mod,'sub_4',_html);
            }else if(_html == '拼团抽奖'){
                mod = 'group_draw';
                data = getPowerInfo('main_config',mod,'sub_4',_html);
            }else if(_html == '瓜分积分'){
                mod = 'pin_integration';
                data = getPowerInfo('main_config',mod,'sub_4',_html);
            }else{
                data.content = 1;
                data.message = '';
            }
        }
        else if(sub == "sub_goods"){
            data = getPowerInfo('num_config','goods_num','','商品数量');
        }else if(sub == "sub_store"){
            data = getPowerInfo('num_config','store_num','','门店数量');
        }
        else{
            data.content = 1;
            data.message = '';
        }
        if(sub == 'sub_goods' || sub == "sub_store"){
            if(data.tip != 1){
                $(this).attr('href',url);
            }
        }else{
            if(data.content == 1){
                $(this).attr('href',url);
            }
        }


    });
    function getPowerInfo(config,mod,sub,name,flag,edit){
        /*
        *  参数说明：  config有两种，main_config -- 基础功能，num_config -- 数量
        *            mod 表示的时候该功能的value值，判断主要是哪个功能，唯一
        *            sub 只有模块功能才用到，表示的是该功能处于哪个模块，从sub_0 到 sub_5，数量 ；该参数填 '' 就好，不需要词参数
        *            name 表示该功能的名称，如，概况统计，商品数量等，需加引号，中文显示
        *            flag 与模块功能对应，flag为 0 是到新页面，用于页面内部；flag为其他值是弹框显示，用于点击事件的提示
        *            edit 与数量 对应，edit为 1 用于编辑；edit为其他值，用于新建
        * */

        var param = {
            config_name: config,
            mod_name : mod,
            sub_name: sub
        };
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
            }
        });
        var content = {};
        $.ajax({
            type: 'post',
            url: '/admin/version/judgment',
            data: param,
            dataType: 'json',
            async: false,
            success: function (d) {
                content.content = d.content;
                content.message = d.message;
                if(d.error == -2){
                    util.mobile_alert('该店铺暂无版本信息，部分功能将无法使用，请联系管理员！');
                    setTimeout(function () {
                        window.location.href = '/admin/account/shop/select';
                    },1000)
                }
                //模块  d.content ：-1表示该功能不可用 1表示可用
                if(d.content == -1){
                    if(config == 'main_config'){
                        if(flag == 0){
                            window.location.href = '/admin/authority/not?mod_name='+ name;
                        }else{
                            util.systemNotice(1,'',name);
                        }

                    }
                }
                //数量
                if(d.content.self){
                    var self = d.content.self;
                    self.num = parseFloat(self.num);
                    self.use = parseFloat(self.use);
                    if(edit == 1){//编辑
                        if(config == 'num_config' && self.num < self.use && self.num >= 0){
                            content.tip = 1;
                            modInfo(mod,self.num);
                        }
                    }else{//新建
                        if(config == 'num_config' && self.num <= self.use && self.num >= 0){
                            content.tip = 1;
                            modInfo(mod,self.num);
                        }
                    }

                }
            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
                $("#j_ajax_loading").fadeOut(500);
            }
        });
        return content;
    }
    function modInfo(mod,num){
        if(mod == 'goods_num'){
            var con = '商品数量达最大' + num + '个';
            util.systemNotice(2,con,'商品数量');
        }else if(mod == 'store_num'){
            var con = '门店数量达最大' + num + '个';
            util.systemNotice(2,con,'门店数量');
        }else if(mod == 'decorate_num'){
            var con = '装修页面数量达最大' + num + '个';
            util.systemNotice(2,con,'装修页面数量');
        }else if(mod == 'form_num'){
            var con = '表单数量达最大' + num + '个';
            util.systemNotice(2,con,'表单数量');
        }else if(mod == 'picture_num'){
            var con = '图片空间达最大' + num + 'M';
            util.systemNotice(2,con,'图片空间大小');
        }else if(mod == 'video_num'){
            var con = '视频空间达最大' + num + 'M';
            util.systemNotice(2,con,'视频空间大小');
        }
    }
    /**
     * 权限判断
     **/
    function getAuthorityDetail(type,path,pri_pass,role_pass,action,act_param){
        /*
        *  参数说明：  type 有三种 0是路由验证  1功能权限判断 2密码验证
        *            path 路由 type为1是用到
        *            pri_pass 功能名称 type为2时用到
        *            role_pass 密码 type为3是用到
        *            action 操作
        *            act_param 操作参数
        * */

        var param = {
            type: type,
            path : path,
            pri_pass: pri_pass,
            role_pass: role_pass
        };
        $.ajaxSetup({
            headers: {
                'X-CSRF-TOKEN': $('meta[name="csrf-token"]').attr('content')
            }
        });
        var content;
        $.ajax({
            type: 'post',
            url: '/admin/authority/judgment',
            data: param,
            dataType: 'json',
            async: false,
            success: function (d) {
                if(d.error != 0){
                    if(d.error == 1){
                        util.mobile_alert("操作错误");
                        content=1;
                    }else if(d.error == 2){
                        util.mobile_alert("没有此功能权限");
                        content=2;
                    }else if(d.error == 3){
                        content=3;
                        authorityPass(action,act_param);
                    }
                }else{
                    content=0;
                    do_action(action,act_param);
                }

            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
                $("#j_ajax_loading").fadeOut(500);
            }
        });
        return content;
    }
    /**
     * 功能权限密码弹框
     * @param action 操作名称
     * @param act_param 操作参数
     */
    function authorityPass (action,act_param) {//该弹框可关闭，只做一个提示作用
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var content;
            var $ = layui.jquery, layer = layui.layer;
            layer.closeAll();
            layer.open({
                type: 1
                , title: ['功能权限密码', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '360px'
                , content: "<div style='margin-left: 20px;' id='authority'>权限密码：<input style='height: 25px;' type='password' value='' name='role_pass' id='role_pass'></div>" //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , skin: 'demo-class'
                , async: false
                , yes: function (index, layero) { //保存按钮的回调
                    // authority_pass = $("#authority").find("#role_pass").val();
                    $.ajax({
                        type: 'post',
                        url: '/admin/authority/judgment',
                        data: {type:2,role_pass:$("#authority").find("#role_pass").val()},
                        dataType: 'json',
                        async: false,
                        success: function (d) {
                            if(d.error != 0){
                                if(d.error == 4){
                                    util.mobile_alert('密码错误');
                                    return false;
                                }
                            }else{
                                do_action(action,act_param);
                            }
                        },
                        error: function (XmlHttpRequest, textStatus, errorThrown) {
                            $("#j_ajax_loading").fadeOut(500);
                        }
                    });
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                },
            });

        });

    }

    /**
     * 权限密码之后的操作
     * @param action 操作名称
     * @param act_param 操作参数
     */
    function do_action(action,act_param) {
        if(action == 'user_export'){
            $('input[name="user_export"]').val("user_export");
            $("#form1").submit();
        }else if(action == 'return_no_pass'){
            util.ajax_json("/admin/orders/manage/return/not/pass", function (d) {
                if (d && d.error == 0) {
                    layer.msg("操作成功");
                    setTimeout(function () {
                        window.location.reload();
                    }, 1000);
                } else if (d && d.error > 0) {
                    layer.msg(d.message);
                }
            }, act_param);
        }else if(action == "return_agree_refund"){
            util.ajax_json("/admin/orders/manage/return/refund/agree", function (d) {
                if (d && d.error == 0) {
                    if (d.content.operate != undefined && d.content.operate == 99) {
                        refreshRefundResult(act_param.ret_id, 1);
                    } else {
                        layer.msg("操作成功");
                        setTimeout(function () {
                            window.location.reload();
                        }, 1000);
                    }
                } else if (d && d.error > 0) {
                    layer.msg(d.message);
                }
            }, act_param);
        }else if(action == "return_refuse_refund"){
            util.ajax_json("/admin/orders/manage/return/refund/refuse", function (d) {
                if (d && d.error == 0) {
                    layer.msg("操作成功");
                    setTimeout(function () {
                        window.location.reload();
                    }, 1000);
                } else if (d && d.error > 0) {
                    layer.msg(d.message);
                }
            }, act_param);
        }else if(action == 'return_pass'){
            util.ajax_json("/admin/orders/manage/return/pass", function (d) {
                if (d && d.error == 0) {
                    layer.msg("操作成功");
                    setTimeout(function () {
                        window.location.reload();
                    }, 1000);
                } else if (d && d.error > 0) {
                    layer.msg(d.message);
                }
            }, act_param);
        }else if(action == 'distribution_withdraw'){
            util.ajax_json('/admin/market/distribution/widthdraw/ajax', function (response) {
                if (response.error == 0) {
                    util.mobile_alert("操作成功");
                    location.reload();
                }else{
                    util.mobile_alert(response.message);
                }
            },act_param);
        }else if(action == 'scan_code_purchase'){
            util.ajax_json("/admin/orders/manage/verify",function (d) {
                if(d && d.error == 0){
                    util.mobile_alert("核销成功");
                    location.reload();
                }
            },act_param)
        }else if(action == 'card_order_return'){
            util.ajax_json('/admin/orders/card/return',function (res) {
                if (res.error == 0) {
                    util.mobile_alert('退款成功');
                    setTimeout(function () {
                    $("#form1").submit();
                    },1000)
                } else {
                    util.mobile_alert(res.message);
                }
            },act_param)
        }else if(action == 'batch_delete'){
            if(act_param.user_id == -1){
                $("input[name='batch_act']").val("batch_delete");
                $("#form1").submit();
            }else{
                util.ajax_json('/admin/user/manage/del',function(d){
                    if(d&&d.error>=0){
                        util.mobile_alert(d.content);
                        location.reload();
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                },act_param);
            }
        }else if(action == 'batch_tag'){
            if(act_param.user_id == -1){
                $("input[name='batch_act']").val("batch_tag");
                $("input[name='tag_id']").val(act_param.tag_id);
                $("#form1").submit();
            }else {
                util.ajax_json('/admin/user/tag/edit', function (d) {
                    if (d && d.error >= 0) {
                        if (act_param.batch_tag) {
                            $.each(act_param.user_id, function (index, value, array) {
                                $("#user_" + value).find('.btn_label').attr('user_tag', JSON.stringify(d.content[value]));
                            });
                        } else {
                            $("#user_" + act_param.user_id).find('.btn_label').attr('user_tag', JSON.stringify(act_param.user_tag));
                        }
                        util.mobile_alert(d.message);
                        location.reload();
                    }
                    else {
                        util.mobile_alert(d.message);
                    }
                }, act_param);
            }
        }else if(action == 'batch_card'){
            if(act_param.user_id == -1){
                $("input[name='batch_act']").val("batch_card");
                $("input[name='tag_id']").val(JSON.stringify(act_param.card_info));
                $("#form1").submit();
            }else {
                util.ajax_json('/admin/ajax/user/set/card',function(d){
                    if(d&&d.error==0){
                        layer.msg('保存成功', {time: 2000},function () {
                            location.reload();
                        });
                    }
                    else{
                        util.mobile_alert(d.message);
                    }
                },act_param);
            }
        }else if(action == 'batch_score'){
            if(act_param.user_id == -1){
                $("input[name='batch_act']").val("batch_score");
                $("input[name='tag_id']").val(act_param.score);
                $("#form1").submit();
            }else {
                util.ajax_json('/admin/user/manage/score/add', function (d) {
                    if (d && d.error == 0) {
                        // var score = parseInt(data.score_dis)+parseInt(data.score);
                        // $("#user_"+data.user_id).find('.ipt-integral').val(score);
                        util.mobile_alert(d.content);
                        location.reload();
                    }
                    else {
                        util.mobile_alert(d.message);
                    }
                }, act_param);
            }
        }else if(action == 'change_invite'){
            if(act_param.user_ids == -1){
                $("input[name='batch_act']").val("change_invite");
                $("input[name='tag_id']").val(act_param.invite_id);
                $("#form1").submit();
            }else {
                util.ajax_json('/admin/user/update/invite', function (res) {
                    if (res.error == 0) {
                        if (util.count(res.content) > 0) {
                            let str = '';
                            for (var i in res.content) {
                                str += res.content[i] + '<br/>'
                            }
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.alert('<div style="text-align: left">' + str + '</div>', {
                                    title: ['提醒', 'text-align:center;padding: 0px;']
                                    , area: ['320px', '240px']
                                    , btnAlign: 'c'
                                    , btn: ['确定']
                                }, function (index) {
                                    window.location.reload();
                                });
                            });
                        } else {
                            layer.msg('修改成功', {time: 500}, function () {
                                window.location.reload();
                            });
                        }
                    } else {
                        util.mobile_alert(res.message)
                    }
                }, act_param)
            }
        }else if(action == 'goods_export'){
            $("#export").val("export_csv");
            $("#form1").submit();
            $("#export").val('');
        } else if(action == 'batch_account'){
            util.ajax_json('/admin/user/manage/account/add',function(d){
                if(d&&d.error==0){
                    layer.msg('修改成功', {time: 500}, function () {
                        window.location.reload();
                    });
                }
                else{
                    util.mobile_alert(d.message);
                }
            },act_param);
        } else if(action == 'order_export'){
            $("#act").val("export_csv");
            $("#form1").submit();
            $("#act").val('');
        }
    }
    function refreshRefundResult(ret_id, times) {
        var ajaxRefreshRefundResult = function(ret_id, times) {
            console.log(arguments);
            util.ajax_json('/admin/orders/suborder/list', function (response) {
                if (response.error == 0) {
                    var list = response.content.list.data, html = '';
                    var table_template = '<table class="layui-table" lay-size="lg"><tbody><tr style="font-weight: bolder;"><th width="34%">用户</th><th width="30%">支付流水</th><th width="18%">退款金额</th><th width="18%">退款状态</th></tr>@@@<tbody></table>';
                    for (var i in list) {
                        html += '<tr><td>' + list[i].username + '</td><td>' +list[i].pay_sn+ '</td><td>' +list[i].refund_amount+ '</td>';
                        if (list[i].refund_amount > 0) {
                            html += '<td>退款完成</td>';
                        } else {
                            html += '<td><font color="red">退款失败</font></td>';
                        }
                        html += '</tr>';
                    }
                    $('#refresh-refund-result-body').html(table_template.replace('@@@', html));
                    if (parseInt(response.content.is_finish_refund) == 1 || times > 30) {
                        $('#refresh-refund-result-footer').show();
                        $('#refresh-refund-loading').hide();
                        util.mobile_alert('退款处理结束');
                        $('#layui-layer2').find('.layui-layer-btn0').show();
                    } else {
                        times++;
                        setTimeout(function () {
                            ajaxRefreshRefundResult(ret_id, times);
                        }, 1000)
                    }
                }
            }, {refund_detail: 1, ret_id: ret_id, page: 1, pagesize: 1000});
        };
        layer.open({
            type: 1,
            title: ['退款结果', 'text-align:center; padding: 0px;'],
            offset: 'auto',
            area: ['600px', '500px'],
            content: '<div style="padding: 10px;">' +
            '<div style="text-align: center; color: red;" id="refresh-refund-result-header"></div>' +
            '<div id="refresh-refund-result-body"></div>' +
            '<div id="refresh-refund-loading" style="text-align: center; position: absolute; bottom: 10px; left: 235px;"><img src="/image/admin/loadingv1.gif"/></div>' +
            '</div>',
            btn: ['关闭'],
            btnAlign: 'c',
            shade: [0.9, '#000'],
            anim: 1,
            success: function(index, layero) {
                $('#layui-layer2').find('.layui-layer-btn0').hide();
                ajaxRefreshRefundResult(ret_id, times);
            },
            cancel: function(index, layero) {
                return false;
            },
            end : function () {
                window.location.reload();
            }
        });
    }
</script>
<script src="/js/admin/edition_common.js?v=1.0.3" type="text/javascript"></script>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?168b8cbc117fcc08fdae905189d4feb9";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
</body>
</html>