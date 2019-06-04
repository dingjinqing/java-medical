<div class="modal fade" id="contact_feedback" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">关闭</span></button>
                <h4 class="modal-title" id="myModalLabel">请填写您的意见和建议，提交后我们会尽快与您联系</h4>
            </div>
            <div class="modal-body">
                <form class="form needcheck" action="?c=feedback" method="post" id="form_footer_global_feedback">
                    <div class="row text-center">
                        <div class="form-group name feedback_content">
                            <label class="sr-only" for="name">姓名</label>
                            <input id="contact_feedback_name" type="text" class="form-control data_check"
                                   required="required" maxlength="64"
                                   placeholder="姓名:">
                        </div><!--//form-group-->
                        <div class="form-group email feedback_content">
                            <label class="sr-only" for="tel">电话</label>
                            <input id="contact_feedback_phone" type="tel" class="form-control data_check" maxlength="32"
                                   required="required"
                                   placeholder="电话:">
                        </div><!--//form-group-->
                        <div class="form-group message feedback_content">
                            <label class="sr-only"
                                   for="message">留言</label>
                            <textarea id="contact_feedback_message" class="form-control data_check" rows="6"
                                      required="required"
                                      placeholder="留言:"></textarea>
                        </div><!--//form-group-->
                    </div><!--//row-->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">关闭</button>
                <button type="button" id="feedback_submit"
                        class="btn btn-primary">提交</button>
            </div>
        </div>
    </div>
</div>

<div class="absolute-top-header" >
    <header id="header" style="color: white;">
        <div id="logo-group">
            <span id="logo">
                <img src="${logo.img_path!"http://${image_domain}/image/admin/official/bottom_logo.png"}"
                     style="height:49px;margin-top:13px"/>
            </span>
        </div>

        <div class="clearfix">
            <#list menuList  as item>
                <a href="${item['linkUrl']!}" style="color: white">
                    <div class="pull-left first" link="${item['linkUrl']!"#"}">
                        <div>${item['name']!}</div>
                    </div>
                </a>
            </#list>
        </div>
        <!-- pulled right: nav area -->
        <div class="account">
            <!-- logout button -->
            <div class="btn-header transparent pull-right">
            <span>
                <#if  "${login_user['is_sub_account']}" == "1"><span>
                    <img <#if ("${login_user['shop_avatar']!}" != "")> src="${login_user['shop_avatar']!}" <#else> src="http://${image_domain!}/image/admin/head_icon.png" </#if> 
                       class="head-img" style="border-radius:100%;"/>
                </span>
                </#if>
                <label>
                    ${login_user['account_name']!login_user['user_name']!}
                </label>
                <img src="http://${image_domain!}/image/admin/img1.png" class="jiantou-top">
            </span>
            </div>
            <img src="http://${image_domain!}/image/admin/active_top.png" class="menu_top" id="menu_top"
                 style="display:none;">
        </div>
        <div class="log-menu" style="display:none;">
            <#if  ("${login_user['is_sub_account']!}" =="1")>
                <a href="/admin/account/manage">账户设置</a>
            <#else>
                <a href="/admin/subPasswordModify">修改密码</a>
            </#if>

            <#if   ("${login_user['is_sub_account']!}" !="1")>
                <a href="/admin/config/role/list">子账号权限管理</a>
            </#if>
            <a href="/admin/public/service/auth/list">授权公众号</a>
            <a href="/admin/account/shop/select" title="选择店铺"
               id="logout">选择店铺</a>
            <a href="/admin/logout" title="退出"
               id="logout">退出</a>
        </div>
    </header>
</div>

<div class="absolute-left-menu">
    <div class="left-menu-back">
        <div class="left-menu">
                <div class="left-menu-content">
                    <#list  leftMenuList as  item>
                        <#if item['check']  ||  user_role_id == 0>
                            <#if !item['hide'] && !(item['name'] == '第三方商品' && !is_grasp_shop)
                             && !(item['enName'] == 'goods_import' && isShowGoodsImport == 0)
                             && !(item['enName'] == 'action_record' && login_user['is_sub_account']=="1")>
                                <dl class="item-menu" sid="${item?index}" style="display:${item['display']!"inherit"}">
                                    <a href="${item['linkUrl']!}" link="${item['linkUrl']!}" zid="0" sub="${item['linkUrl']!}">
                                        <#if  (item['recommendPic'] != "")>
                                            <img src="${item['recommendPic']!}" class="on show_tj" cid="0" >
                                            <img src="${item['recommendPic']!}" cid="1" fid="0" class="show_tj" >
                                        <#else>
                                        <img src="${item['imageUrl']!}" class="on" cid="0">
                                        <img src="${item['imageHoverUrl']!}" cid="1" fid="0">
                                        </#if>
                                        <span class="menu-item-parent">${item['name']!"no name"}</span>
                                    </a>
                                    <#if  (item['subMenu']??)>
                                        <div class="sub-menu" style="display:none">
                                            <#list item['subMenu']  as sub_item>
                                                <#if  (sub_item['check'] || user_role_id == 0 )>
                                                    <dl hidden style="display:block;">
                                                        <a href="${sub_item['linkUrl']!}">${sub_item['name']!}</a>
                                                    </dl>
                                                </#if>
                                            </#list>
                                        </div>
                                    </#if>
                                </dl>
                            </#if>
                        </#if>
                    </#list>


                </div>

            <div style="margin-top:30px;">
                <div class="global_contact" id="global_contact">
                    <div class="con_head" id="global_contact_header">
                        <b class="con_icon"></b>
                        <span class="con_content">联系我们</span>
                    </div>
                    <div class="con_body" id="global_contact_body">
                        <div class="cons_qrcode"></div>
                        <div class="cons_mobile">联系我们</div>
                        <div class="cons_QQ">
                            <a href="http://wpa.qq.com/msgrd?v=3&uin=2895665430&site=qq&menu=yes"
                               class="cons_qq_btn" rel="nofollow"
                               target="_blank">企业QQ咨询</a></div>
                        <div class="cons_feedback" data-toggle="modal"
                             data-target="#contact_feedback">意见反馈</div>
                    </div>
                </div>
            </div>
                <div class="left_suspension">
                    <div class="suspension">
                        <div style="width: 100%;border-right: 1px solid #8c929e">
                            <img src="/image/admin/left_menu_phone.png">
                        </div>
                        <div class="suspension_message sm1" style="width: 143px">
                            <img src="/image/admin/left_menu_jt.png">
                            <span style="display: inline-block;width: 100%;text-align: left;height: 30px;word-break: break-all;">客服电话：400-010-1039</span>
                        </div>
                    </div>
                    <div class="suspension suspension_click1">
                        <div style="width: 100%;border-right: 1px solid #8c929e">
                        <img src="/image/admin/left_menu_zx.png">
                        </div>
                        <div class="suspension_message">
                            <img src="/image/admin/left_menu_jt.png">
                            <span><a href="tencent://message/?uin=3003715029&Site=&Menu=yes">在线咨询</a></span>
                        </div>
                    </div>
                    <div class="suspension suspension_click2">
                        <img src="/image/admin/left_menu_question.png">
                        <div class="suspension_message">
                            <img src="/image/admin/left_menu_jt.png">
                            <span><a href="/admin/question/feedback">问题反馈</a></span>
                        </div>
                    </div>
                </div>
        </div>
    </div>

</div>

<script>
  var default_localurl='${top_index!0}';
  var sub_index = parseInt("${sub_index!0}");
  var top_index = parseInt("${top_index!0}");
</script>
<#noparse>

<script type="text/javascript">
    var localurl=parseInt(window.location.href.split('top_index=')[1]);
    if(!localurl){
        var localurl=default_localurl;
    }
    //本地url
    $(".first").each(function(){
        if($(this).attr("bid")-1==localurl){
            $(".first").css("background-color","");
            $(this).css("background-color","#437AF9");
        }
    });
    $(".item-menu").hover(function () {
            $(this).children("a").addClass("active").children("img[cid=1]").addClass("on");
            $(this).children("a").children("img[cid=0]").removeClass("on");
            $(this).children(".sub-menu").show();
            var distance = $(this).position().top;
            var height_div = $(this).children(".sub-menu").height();
            if (distance >= 45) {
                if (height_div == 45) {
                    $(this).children(".sub-menu").css("top", distance + "px");
                }
                else if (height_div == 45 * 2) {
                    $(this).children(".sub-menu").css("top", distance - 22 + "px");
                }
                else {
                    $(this).children(".sub-menu").css("top", distance - 45 + "px");
                }
            }

        }, function () {
            $(this).children(".sub-menu").hide();
            $(this).children("a[zid=0]").removeClass("active");
            $(this).children("a").children("img[cid=0]").addClass("on");
            $(this).children("a").children("img[fid=0]").removeClass("on");
            $("img[fid=1]").prev().removeClass("on");
        }
    );
    //二级菜单（左边栏）
    $(".left-menu-content .item-menu ").each(function(){
        var sid = $(this).attr("sid");
        // console.log(light_ahref[0]);
        if(sid == sub_index&&!(top_index==4&& sub_index==0)){
            $(this).find("img").eq(0).attr("class","");
            $(this).find("img").eq(1).attr("class","on");
            $(this).find("a").attr("class","active");
            $(this).find("span").css({"color":"white","opacity":"1"});
            $(this).hover(function(){
                $(this).find("img").eq(0).attr("class","");
                $(this).find("img").eq(1).attr("class","on");
                $(this).find("a").attr("class","active");
            },function(){
                $(this).find("img").eq(0).attr("class","");
                $(this).find("img").eq(1).attr("class","on");
                $(this).find("a").attr("class","active");
            })
        };

        // $(this).mouseleave(function () {
        //     $(this).find("img").eq(0).attr("class","");
        //     $(this).find("img").eq(1).attr("class","on");
        //     $(this).find("a").attr("class","active");
        // })
    });
    $(".suspension").mouseover(function () {
        $(this).find(".suspension_message").css("display","block");
        console.log(1);
    });
    $(".suspension").mouseleave(function () {
        $(this).find(".suspension_message").css("display","none");
    });
    
    $(".suspension_click1").click(function () {
        window.location.href="tencent://message/?uin=3003715029&Site=&Menu=yes";
    });
    $(".suspension_click2").click(function () {
        window.location.href="/admin/question/feedback";
    });
</script>
</#noparse>
