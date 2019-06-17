<div class="modal fade" id="contact_feedback" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">{{ trans("admin/index.feedback_modal.close")!}</span></button>
                <h4 class="modal-title" id="myModalLabel">{{ trans("admin/index.feedback_modal.title")!}</h4>
            </div>
            <div class="modal-body">
                <form class="form needcheck" action="?c=feedback" method="post" id="form_footer_global_feedback">
                    <div class="row text-center">
                        <div class="form-group name feedback_content">
                            <label class="sr-only" for="name">{{ trans("admin/index.feedback_modal.name")!}</label>
                            <input id="contact_feedback_name" type="text" class="form-control data_check"
                                   required="required" maxlength="64"
                                   placeholder="{{ trans("admin/index.feedback_modal.name")!}:">
                        </div><!--//form-group-->
                        <div class="form-group email feedback_content">
                            <label class="sr-only" for="tel">{{ trans("admin/index.feedback_modal.tel")!}</label>
                            <input id="contact_feedback_phone" type="tel" class="form-control data_check" maxlength="32"
                                   required="required"
                                   placeholder="{{ trans("admin/index.feedback_modal.tel")!}:">
                        </div><!--//form-group-->
                        <div class="form-group message feedback_content">
                            <label class="sr-only"
                                   for="message">{{ trans("admin/index.feedback_modal.message")!}</label>
                            <textarea id="contact_feedback_message" class="form-control data_check" rows="6"
                                      required="required"
                                      placeholder="{{ trans("admin/index.feedback_modal.message")!}:"></textarea>
                        </div><!--//form-group-->
                    </div><!--//row-->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">{{ trans("admin/index.feedback_modal.close")!}</button>
                <button type="button" id="feedback_submit"
                        class="btn btn-primary">{{ trans("admin/index.feedback_modal.submit")!}</button>
            </div>
        </div>
    </div>
</div>

<div class="absolute-top-header" >
    <header id="header" style="color: white;">
        <div id="logo-group">
            <span id="logo">
                <img src="${logo->img_path or "http://$image_domain/image/admin/official/bottom_logo.png"!}"
                     style="height:49px;margin-top:13px"/>
            </span>
        </div>

        <span class="sign" hidden value="${sign!}"></span>

        <div class="clearfix">
            <#list ((array)$first_menu as $no => $item)
                <a href="{{ add_url_param($item['link'],"top_index=".$no)!}" style="color: white">
                    <div class="pull-left first" bid="${no+1!}" link="${item['link'] or "#"!}">
                        <div>${item['name']!}</div>
                    </div>
                </a>
                <span style="display:none;">${no+2!}</span>
            </#list>
        </div>
        <!-- pulled right: nav area -->
        <div class="account">
            <!-- logout button -->
            <div class="btn-header transparent pull-right">
            <span>
                <#if  (!$login_user['is_sub_account'])<span>
                    <img <#if ($login_user['shop_avatar'] != '') src="${login_user['shop_avatar']!}"
                         <#else> src="http://${image_domain!}/image/admin/head_icon.png" </#if> class="head-img"
                         style="border-radius:100%;"/>
                </span>
                </#if>
                <label>
                    ${login_user['account_name'] or $login_user['user_name']!}
                </label>
                <img src="http://${image_domain!}/image/admin/img1.png" class="jiantou-top">
            </span>
            </div>
            <img src="http://${image_domain!}/image/admin/active_top.png" class="menu_top" id="menu_top"
                 style="display:none;">
        </div>
        <div class="log-menu" style="display:none;">
            <#if  (!$login_user['is_sub_account'])
                <a href="/admin/account/manage">{{ trans("admin/index.memu.accountset")!}</a>
            <#else>
                <a href="/admin/subPasswordModify">{{ trans("admin/index.memu.passwdset")!}</a>
            </#if>

            <#if  (!$login_user['is_sub_account'])
                <a href="/admin/config/role/list"
                >{{ trans("admin/index.memu.child_account_admin_role")!}</a>
            </#if>
            <a href="/admin/public/service/auth/list">授权公众号</a>
            <a href="/admin/account/shop/select" title="{{ trans("admin/index.memu.select_shop")!}"
               id="logout">{{ trans("admin/index.memu.select_shop")!}</a>
            <a href="/admin/logout" title="{{ trans("admin/index.memu.logout")!}"
               id="logout">{{ trans("admin/index.memu.logout")!}</a>
        </div>
    </header>
</div>

<div class="absolute-left-menu">
    <div class="left-menu-back">
        <div class="left-menu">
            <#list ((array)$menu_list as $num => $item1)
                <div class="left-menu-content <#if ($num != $top_index) hide </#if>" cid="${num+1!}">
                    <#list ((array)$item1['sub'] as $s=>$item)
                        <#if (isset($item['check']) || $user_role_id == 0)
                            <#if ($item['hid']!=1 && !($item['name'] == '第三方商品' && !$is_grasp_shop)
                             && !($item['en_name'] == 'goods_import' && $isShowGoodsImport == 0)
                             && !($item['en_name'] == 'action_record' && $login_user['is_sub_account']))
                                <dl class="item-menu" sid="${s+1!}" style="display:${item['display'] or "inherit"!}">
                                    <a href="{{ add_url_param($item['link'],"top_index=".$num)!}"
                                       link="{{ add_url_param($item['link'],"top_index=".$num)!}"
                                       zid="0" sub="${item['sun'] or ""!}">
                                        <#if  ($item['sunn']== 'recomment' && $item['sun'] == "sub_4")
                                            <img src="${item['show_tj'] or ""!}" class="on show_tj" cid="0" >
                                            <img src="${item['show_tj'] or ""!}" cid="1" fid="0" class="show_tj" >
                                        <#else>
                                        <img src="${item['img_url'] or ""!}" class="on" cid="0">
                                        <img src="${item['img_url_h'] or ""!}" cid="1" fid="0">
                                        </#if>
                                        <span class="menu-item-parent">${item['name'] or "no name"!}</span>
                                    </a>
                                    <#if  (isset($item['sub']))
                                        <div class="sub-menu" style="display:none">
                                            <#list  ((array)$item['sub']  as $sub_item)
                                                <#if  (isset($sub_item['check']) || $user_role_id == 0 )
                                                    <dl hidden style="display:block;">
                                                        <a href="{{ add_url_param($sub_item['link'],"top_index=".$num)!}">${sub_item['name']!}</a>
                                                    </dl>
                                                </#if>
                                            </#list>
                                        </div>
                                    </#if>
                                </dl>
                            </#if>
                        </#if>
                    </#list>

                    {{--<div class="copy-right">--!}
                    {{--<hr>--!}
                    {{--<span>{{ trans("admin/index.footer.copyright")!}</span>--!}
                    {{--<hr>--!}
                    {{--</div>--!}
                </div>
                <span style="display:none;">${num+1!}</span>
            </#list>

            <div style="margin-top:30px;">
                {{--<div class="out_head">--!}
                {{--<b class="con_icon"></b>--!}
                {{--<span class="con_content">{{ trans("admin/index.footer.contact_us")!}</span>--!}
                {{--</div>--!}
                <div class="global_contact" id="global_contact">
                    <div class="con_head" id="global_contact_header">
                        <b class="con_icon"></b>
                        <span class="con_content">{{ trans("admin/index.footer.contact_us")!}</span>
                    </div>
                    <div class="con_body" id="global_contact_body">
                        <div class="cons_qrcode"></div>
                        <div class="cons_mobile">{{ trans("admin/index.footer.contact_us")!}</div>
                        <div class="cons_QQ">
                            <a href="http://wpa.qq.com/msgrd?v=3&uin=2895665430&site=qq&menu=yes"
                               class="cons_qq_btn" rel="nofollow"
                               target="_blank">{{ trans("admin/index.footer.contact_qq")!}</a></div>
                        <div class="cons_feedback" data-toggle="modal"
                             data-target="#contact_feedback">{{ trans("admin/index.footer.feedback")!}</div>
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
<script type="text/javascript">
    var localurl=parseInt(window.location.href.split('top_index=')[1]);
    if(!localurl){
        var localurl='${top_index!}';
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
        if(sid == '${sub_index+1!}'&&!('${top_index!}'==4&&'${sub_index!}'==0)){
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
