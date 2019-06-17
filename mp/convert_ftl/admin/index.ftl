<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <link href="${favicon or "/favicon.ico"!}" rel="shortcut icon"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-production.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-skins.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/admin/base.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/common.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/menu.css?v=1.0.1" rel="stylesheet" type="text/css">

    <title>${global_title!}</title>
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <script language="JavaScript" src="/js/smartadmin/libs/jquery-ui-1.10.3.min.js"></script>
    <script language="JavaScript" src="/js/admin/index.js?v=1.0.4"></script>
</head>
<body>
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
<header id="header" style="color: white;">
    <div id="logo-group">
            <span id="logo">
                <img src="${logo->img_path or "http://$image_domain/image/admin/official/bottom_logo.png"!}"
                     style="height:49px;margin-top:13px"/>
            </span>
    </div>

    <span class="sign" hidden value="${sign!}"></span>

    <div class="clearfix">
        <#list ($first_menu as $no => $item)
            <div class="pull-left first" bid="${no+1!}" link="${item['link'] or "#"!}">
                <div>${item['name']!}</div>
            </div>
            <span style="display:none;">${no+2!}</span>
        </#list>
    </div>
    <!-- pulled right: nav area -->
    <div class="account">
        <!-- logout button -->
        <div class="btn-header transparent pull-right">
            <span>
                <#if  (!$user['is_sub_account'])<span>
                    <img <#if ($user['shop_avatar'] != '') src="${user['shop_avatar']!}"
                         <#else> src="http://${image_domain!}/image/admin/head_icon.png" </#if> class="head-img"
                         style="border-radius:100%;"/>
                </span>
                </#if>
                <label>
                    ${user['account_name'] or $user['user_name']!}
                </label>
                <img src="http://${image_domain!}/image/admin/img1.png" class="jiantou-top">
            </span>
        </div>
        <img src="http://${image_domain!}/image/admin/active_top.png" class="menu_top" id="menu_top"
             style="display:none;">
    </div>
    <div class="log-menu" style="display:none;">
        <#if  (!$user['is_sub_account'])
            <a href="/admin/account/manage">{{ trans("admin/index.memu.accountset")!}</a>
        <#else>
            <a href="/admin/subPasswordModify">{{ trans("admin/index.memu.passwdset")!}</a>
        </#if>

        <#if  (!$user['is_sub_account'])
            <a href="/admin/config/role/list"
               target="main">{{ trans("admin/index.memu.child_account_admin_role")!}</a>
        </#if>
        <a href="/admin/account/shop/select" title="{{ trans("admin/index.memu.select_shop")!}"
           id="logout">{{ trans("admin/index.memu.select_shop")!}</a>
        <a href="/admin/logout" title="{{ trans("admin/index.memu.logout")!}"
           id="logout">{{ trans("admin/index.memu.logout")!}</a>
    </div>
</header>

<div class="left-menu-back">
    <div class="left-menu">
        <#list ($menu_list as $num => $item1)
            <div class="left-menu-content" cid="${num+1!}">
                <#list ((array)$item1['sub'] as $item)
                    <#if (isset($item['check']) || $role_id == 0)
                        <dl class="item-menu"
                            style="display:${item['display'] or "inherit"!}">
                            <a href="javascript:void(0);" link="${item['link'] or "#"!}"
                               <#if (isset($item['link'])) target="main" </#if>
                               zid="0">
                                <img src="${item['img_url'] or ""!}" class="on" cid="0">
                                <img src="${item['img_url_h'] or ""!}" cid="1" fid="0">
                                <span class="menu-item-parent">${item['name'] or "no name"!}</span>
                            </a>
                            <#if  (isset($item['sub']))
                                <div class="sub-menu" style="display:none">
                                    <#list  ((array)$item['sub']  as $sub_item)
                                        <#if  (isset($sub_item['check']) || $role_id == 0 )
                                            <dl hidden style="display:block;">
                                                <a href="javascript:void(0);" link="${sub_item['link'] or ""!}"
                                                   target="${sub_item['target'] or "main"!}">${sub_item['name']!}</a>
                                            </dl>
                                        </#if>
                                    </#list>
                                </div>
                            </#if>
                        </dl>
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

    </div>


    <div class="right-menu" cid="${cid!}">
        <iframe src="${start_main_url or ""!}" name="main" id="main"
                style="overflow:visible;min-width:1196px;" frameborder="0" width="100%" height="94%" scrolling="auto"
        />
    </div>


</div>
</body>

</html>
