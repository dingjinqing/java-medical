<style type="text/css">
    .absolute-left-menu{
        display: none;
    }
    html{
        background-color: white;
    }
</style>
<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/version_notice.css?v=1.0.11">
<div class="version_notice_body">
    <div class="version_notice_container">
        <div class="version_notice_header">
            <div class="header_left">
                <div class="version_sq" apply_type="0"><span>申请升级</span></div>
                <div class="version_sq" apply_type="1"><span>申请续费</span></div>
                <div class="version_download"><span>点击下载查看</span>
                    <a href="/doc/店+小程序版本功能表.pdf" download="">店+小程序版本功能表.pdf</a>
                </div>
            </div>
        </div>
        <div class="version_notice_content">
             {{--<div class="version_number">--!}
                 {{--<div class="version_image vimg-first">--!}
                      {{--<div class="vimg-header">--!}
                          {{--<div class="vimg-header-title vimg-first-title"><span>体验版</span></div>--!}
                      {{--</div>--!}
                      {{--<div class="vimg-text">--!}
                          {{--<span class="version_money">免费</span>--!}
                          {{--<span class="version_date">/30天</span>--!}
                      {{--</div>--!}
                 {{--</div>--!}
                 {{--<div class="version_number_content">--!}
                       {{--<div class="content_messsage">--!}
                           {{--<div class="banben_message">--!}
                               {{--<p><b>版本描述</b></p>--!}
                               {{--<ul>--!}
                                   {{--<li>适用于对平台进行试用体验</li>--!}
                                   {{--<li>试用有效期为30天</li>--!}
                               {{--</ul>--!}
                           {{--</div>--!}
                           {{--<div class="function_message">--!}
                               {{--<p><b>功能描述</b></p>--!}
                               {{--<ul>--!}
                                   {{--<li>小程序电商版全部功能体验</li>--!}
                                   {{--<li>小程序门店全部功能体验</li>--!}
                                   {{--<li>会员标签</li>--!}
                                   {{--<li>折扣会员卡，充值会员卡，限次会员卡</li>--!}
                                   {{--<li>丰富多样的装修模块</li>--!}
                                   {{--<li>基础营销活动免费体验</li>--!}
                                   {{--<li>小程序概况统计数据一览</li>--!}
                               {{--</ul>--!}
                           {{--</div>--!}
                       {{--</div>--!}
                 {{--</div>--!}
             {{--</div>--!}
             <div class="version_number">
                 <div class="version_image vimg-second">
                     <div class="vimg-header">
                         <div class="vimg-header-title vimg-second-title"><span>基础版</span></div>
                     </div>
                     <div class="vimg-text">
                         <span class="version_date">￥</span>
                         <span class="version_money">3800</span>
                         <span class="version_date">/1年期</span>
                     </div>
                 </div>
                 <div class="version_number_content">
                     <div class="content_messsage">
                         <div class="banben_message">
                             <p><b>版本描述</b></p>
                             <ul>
                                 <li>适用于大多数企业进行官网展示、门店小程序、
                                 电商商城小程序平台搭建的需要</li>
                                 <li>享受小程序基础应用功能</li>
                             </ul>
                         </div>
                         <div class="function_message">
                             <p><b>功能描述</b></p>
                             <ul>
                                 <li>电商版小程序全部功能开放，商品限额500个</li>
                                 <li>支持小程序门店展示、服务预约、门店自提功能</li>
                                 <li>支持折扣会员卡功能</li>
                                 <li>大量装修模块免费开放使用，砍价、视频、会员卡模块不支持使用</li>
                                 <li>支持商品优惠券功能</li>
                                 <li>小程序概况统计数据一览</li>
                             </ul>
                         </div>
                     </div>
                 </div>
             </div>
             <div class="version_number">
                 <div class="version_image vimg-third">
                     <div class="vimg-header">
                         <div class="vimg-header-title vimg-third-title"><span>高级版</span></div>
                     </div>
                     <div class="vimg-text">
                         <span class="version_date">￥</span>
                         <span class="version_money">5800</span>
                         <span class="version_date">/1年期</span>
                     </div>
                 </div>
                 <div class="version_number_content">
                     <div class="content_messsage">
                         <div class="banben_message">
                             <p><b>版本描述</b></p>
                             <ul>
                                 <li>适用于官网展示、电商商城、预约到店、
                                 营销互动、多门店等小程序平台搭建需要</li>
                             </ul>
                         </div>
                         <div class="function_message">
                             <p><b>功能描述</b></p>
                             <ul>
                                 <li style="color:#ff4343;font-weight: 600">包含基础版所有功能</li>
                                 <li>商品数量无限量</li>
                                 <li>支持门店买单和技术预约</li>
                                 <li>支持会员标签，充值和限次会员卡</li>
                                 <li>支持会员卡、视频装修模块</li>
                                 <li>除分销和砍价功能外，所有营销活动均支持使用</li>
                                 <li>用户画像、访问分析、来源分析均可实时查询</li>
                             </ul>
                         </div>
                     </div>
                 </div>
             </div>
             <div class="version_number">
                 <div class="version_tj">
                     <span><img src="/image/admin/version_recommend.png"></span>
                 <div class="version_image vimg-four">
                     <div class="vimg-header">
                         <div class="vimg-header-title vimg-four-title"><span>旗舰版</span></div>
                     </div>
                     <div class="vimg-text vimg-four-text">
                         <span class="version_date">￥</span>
                         <span class="version_money">9800</span>
                         <span class="version_date">/1年期</span>
                     </div>
                 </div>
                 </div>
                 <div class="version_number_content four-content">
                     <div class="content_messsage">
                         <div class="banben_message ">
                             <p><b>版本描述</b></p>
                             <ul>
                                 <li>全功能版本，提供超大容量数据存储空间</li>
                                 <li>一对一专家咨询服务</li>
                             </ul>
                         </div>
                         <div class="function_message ">
                             <p><b>功能描述</b></p>
                             <ul>
                                 <li style="color:#ff4343;font-weight: 600">包含高级版全部功能</li>
                                 <li>全量装修模块</li>
                                 <li>海量营销功能</li>
                                 <li>更全面数据查询功能</li>
                             </ul>
                         </div>
                     </div>
                 </div>
             </div>
        </div>
    </div>
</div>
<script>
    $(".version_sq").click(function () {
        var apply_mod = "${apply_mod!}";
        var _this = $(this);
        var param = {
            "apply_type":_this.attr("apply_type"),
            "apply_mod":apply_mod
        };
        util.ajax_json("/admin/version/charge/renew", function (response) {
            console.log(response);
            if(response.error > 0){
                alert_info(1);
            }else{
                if(_this.attr('apply_type') == 0){
                    alert_info(2);
                }else{
                    alert_info(3);
                }
            }
        }, param);
    });
    var win_hei = window.screen.availHeight;
    var top_m = (win_hei - 200)/2;
    function alert_info(num) {
        var content;
        var content1 = '<div>已将信息反馈给您的专属产品顾问，我们将尽快与您联系！！！</div><div>联系客服：400-010-1039</div>';
        var content2 = '<div>今天您已经提交过升级申请了，产品顾问将在3个工作日内与您取得联系！！！</div><div>联系客服：400-010-1039</div>';
        var content3 = '<div>今天您已经提交过续费申请了，产品顾问将在3个工作日内与您取得联系！！！</div><div>联系客服：400-010-1039</div>';
        if(num == 1){
            content = content1;
        }
        if(num == 2){
            content = content2;
        }
        if(num == 3){
            content = content3;
        }
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['系统通知', 'text-align:center;padding: 0px;']
                , offset: top_m + 'px' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '360px'
                , content: content //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['好的']
                , btnAlign: 'c' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , skin: 'demo-class'
                , yes: function (index, layero) { //保存按钮的回调
                    var iframe = layer.getChildFrame('body', index);
                    layer.close(index);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    }
</script>





