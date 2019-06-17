<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <link href="${favicon or ""!}" rel="shortcut icon"/>
    <link href="/css/smartadmin/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/font-awesome.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-production.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/smartadmin/smartadmin-skins.min.css" media="screen" rel="stylesheet" type="text/css">
    <link href="/css/system/base.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/system/common.css?v=0.1.1" rel="stylesheet" type="text/css"/>
    <link href="/css/system/menu.css?v=1.0.4" rel="stylesheet" type="text/css">

    <title>${global_title!}</title>
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <script language="JavaScript" src="/js/smartadmin/libs/jquery-ui-1.10.3.min.js"></script>
    <script language="JavaScript" src="/js/system/index.js?v=1.0.4"></script>
    <style type="text/css">
        #main{
            width: 100% !important;
            /*min-width: 100% !important;*/
        }

    </style>
</head>
<body>
<div class="modal fade" id="contact_feedback" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">{{ trans("system/index.feedback_modal.close")!}</span></button>
                <h4 class="modal-title" id="myModalLabel">{{ trans("system/index.feedback_modal.title")!}</h4>
            </div>
            <div class="modal-body">
                <form class="form needcheck" action="?c=feedback" method="post" id="form_footer_global_feedback">
                    <div class="row text-center">
                        <div class="form-group name feedback_content">
                            <label class="sr-only" for="name">{{ trans("system/index.feedback_modal.name")!}</label>
                            <input id="contact_feedback_name" type="text" class="form-control data_check"
                                   required="required" maxlength="64"
                                   placeholder="{{ trans("system/index.feedback_modal.name")!}:">
                        </div><!--//form-group-->
                        <div class="form-group email feedback_content">
                            <label class="sr-only" for="tel">{{ trans("system/index.feedback_modal.tel")!}</label>
                            <input id="contact_feedback_phone" type="tel" class="form-control data_check" maxlength="32"
                                   required="required"
                                   placeholder="{{ trans("system/index.feedback_modal.tel")!}:">
                        </div><!--//form-group-->
                        <div class="form-group message feedback_content">
                            <label class="sr-only"
                                   for="message">{{ trans("system/index.feedback_modal.message")!}</label>
                            <textarea id="contact_feedback_message" class="form-control data_check" rows="6"
                                      required="required"
                                      placeholder="{{ trans("system/index.feedback_modal.message")!}:"></textarea>
                        </div><!--//form-group-->
                    </div><!--//row-->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">{{ trans("system/index.feedback_modal.close")!}</button>
                <button type="button" id="feedback_submit"
                        class="btn btn-primary">{{ trans("system/index.feedback_modal.submit")!}</button>
            </div>
        </div>
    </div>
</div>
<header id="header" style="color: white;">
    <div id="logo-group">
            <span id="logo">
                <img src="${logo->img_path or "/image/system/b2c_logo.png"!}"/>
            </span>
    </div>

    <span class="sign" hidden value="{$sign}"></span>

    <div class="clearfix" style="margin-left:130px;">
        <#list ($menu_list as $no => $item)
            <#if ($item['check'] || $role_id==0)
                <div class="pull-left first" bid="${no+1!}"  link="${item['link'] or "#"!}">
                    <img src="${item['img_url']!}">

                    <div>${item['name']!}</div>
            </div>
            <span style="display:none;">${no+2!}</span>
            </#if>
        </#list>
    </div>
    <!-- pulled right: nav area -->
    <div class="account">
        <!-- logout button -->
        <div class="btn-header transparent pull-right">
            <span> <label>${user['user_name']!}</label><img src="http://${image_domain!}/image/system/first_5.png"></span>
        </div>
        <img src="http://${image_domain!}/image/system/menu_top.png" class="menu_top" id="menu_top" style="display:none;">
    </div>
    <div class="log-menu" style="display:none;">
        <a href="/system/passwordSet">{{ trans("system/index.memu.passwdset")!}</a>
        <#if  (!$user['is_sub_account'])
            <a href="/system/account/user/list" target="main">{{ trans("system/index.memu.child_account_admin")!}</a>
        </#if>
        <a href="/system/logout" title="{{ trans("system/index.memu.logout")!}"
           id="logout">{{ trans("system/index.memu.logout")!}</a>
    </div>
</header>

<div style="height:100%; min-height:375px;">
    <div class="left-menu">
        <#list ($menu_list as $num => $item1)
            <div class="left-menu-content" cid="${num+1!}">
                <#list ($item1['sub'] as $item)
                    <#if (isset($item['check']) || $role_id == 0)
                        <dl class="item-menu"
                            style="display:${item['display'] or "inherit"!}">
                            <a href="javascript:void(0);" link="${item['link'] or "#"!}"
                               <#if (isset($item['link'])) target="${item['target'] or "main"!}" </#if>
                               zid="0">
                                <img src="${item['img_url'] or ""!}" class="on" cid="0">
                                <img src="${item['img_url_h'] or ""!}" cid="1" fid="0">
                                <span class="menu-item-parent">${item['name'] or "no name"!}</span>
                            </a>
                            <#if  (isset($item['sub']))
                                <div class="sub-menu" style="display:none">
                                    <#list  ($item['sub']  as $sub_item)
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

                <div class="copy-right">
                    <hr>
                    <span>{{ trans("system/index.footer.copyright")!}</span>
                    <hr>
                </div>
            </div>
            <span style="display:none;">${num+1!}</span>
        </#list>

        <div style="margin-top:30px;">
           <!--  <div class="out_head">
                <b class="con_icon"></b>
                <span class="con_content">{{ trans("system/index.footer.contact_us")!}</span>
            </div> -->
            <div class="global_contact" id="global_contact">
                <div class="con_head" id="global_contact_header">
                    <b class="con_icon"></b>
                    <span class="con_content">{{ trans("system/index.footer.contact_us")!}</span>
                </div>
                <div class="con_body" id="global_contact_body">
                    <div class="cons_qrcode"></div>
                    <div class="cons_mobile">{{ trans("system/index.footer.contact_us")!}</div>
                    <div class="cons_QQ">
                        <a href="http://wpa.qq.com/msgrd?v=3&uin=2895665430&site=qq&menu=yes"
                           class="cons_qq_btn" rel="nofollow"
                           target="_blank">{{ trans("system/index.footer.contact_qq")!}</a></div>
                    <div class="cons_feedback" data-toggle="modal"
                         data-target="#contact_feedback">{{ trans("system/index.footer.feedback")!}</div>
                </div>
            </div>
        </div>

    </div>


    <div class="right-menu" cid="${cid ?? 0!}">
        <iframe src="${start_main_url or ""!}" name="main" id="main"
                style="overflow:visible;min-width:1196px;" frameborder="0" width="100%" height="94%" scrolling="auto"
                onload="window.parent"/>
    </div>


</div>
</body>

</html>
