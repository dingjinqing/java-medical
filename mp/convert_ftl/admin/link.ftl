<html>

<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css" />
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css" />
    <link href="/css/admin/link.css?v=1.2.2" rel="stylesheet" type="text/css" />
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <style type="text/css">
        .goods_info .goods_name span {
            border: 1px #f66 solid;
            padding: 0px 3px;
            color: #f66;
            font-size: 12px;
            border-radius: 2px;
        }

        .goods_tb thead td {
            background-color: #f5f5f5;
        }
    </style>
</head>

<body style="background:none;">
    <div class="clearfix">
        <div class="nav-role">
            <ul id="tab" class="">
                <li <#if ($nav==0)class="active" </#if>>
                    <a href="/admin/frame/decoration/link?nav=0&bottom=${bottom!}">常用链接</a>
                </li>
                <#if ($bottom!=1)
                <li <#if ($nav==1)class="active" </#if>>
                    <a href="/admin/frame/decoration/link?nav=1&bottom=${bottom!}">商品链接</a>
                </li>
                </#if>
                <li <#if ($nav==2)class="active" </#if>>
                    <a href="/admin/frame/decoration/link?nav=2&bottom=${bottom!}">自定义页面</a>
                </li>
                <li class="table">
                    <a style="cursor: default">营销活动</a>
                    <ul class="activity_list" <#if ($bottom==1) style="top:-70px" <#else> style="top:-105px" </#if>>
                        <li data-index="2">
                            <a>拼团抽奖</a>
                        </li>
                        <li data-index="1">
                            <a>瓜分积分</a>
                        </li>
                        <li data-index="6">
                            <a>好友助力</a>
                        </li>
                        <li data-index="4">
                            <a>加价购</a>
                        </li>
                        <li data-index="0">
                            <a>幸运抽奖</a>
                        </li>
                        <li data-index="3">
                            <a>满折满减</a>
                        </li>
                        <li data-index="5">
                            <a>一口价</a>
                        </li>
                        <li data-index="7">
                            <a>优惠券</a>
                        </li>
                        <li data-index="8">
                            <a>会员卡</a>
                        </li>
                        <li data-index="9">
                            <a>测评</a>
                        </li>
                        <li data-index="10">
                            <a>优惠礼包</a>
                        <li>
                    </ul>
                    <div class="three-circle">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </li>
                <li <#if ($nav==5 || $nav==7 || $nav==11 || $nav==12) class="active" </#if>>
                    <a style="cursor: default">商品分类</a>
                    <ul <#if ($bottom==1) style="top:-105px" <#else> style="top:-140px" </#if>>
                        <li <#if ($nav==5)class="active" </#if>>
                            <a href="/admin/frame/decoration/link?nav=5&bottom=${bottom!}">平台分类</a>
                        </li>
                        <li <#if ($nav==7)class="active" </#if>>
                            <a href="/admin/frame/decoration/link?nav=7&bottom=${bottom!}">商家分类</a>
                        </li>
                        <li <#if ($nav==11)class="active" </#if>>
                            <a href="/admin/frame/decoration/link?nav=11&bottom=${bottom!}">商品品牌</a>
                        </li>
                        <li <#if ($nav==12)class="active" </#if>>
                            <a href="/admin/frame/decoration/link?nav=12&bottom=${bottom!}">商品标签</a>
                        </li>
                    </ul>
                    <div class="three-circle">
                        <span></span>
                        <span></span>
                        <span></span>
                    </div>
                </li>
                <li <#if ($nav==3)class="active" </#if>>
                    <a href="/admin/frame/decoration/link?nav=3&bottom=${bottom!}">网页跳转</a>
                </li>
                <#if ($bottom != 2)
                <li <#if ($nav==4)class="active" </#if>>
                    <a href="/admin/frame/decoration/link?nav=4&bottom=${bottom!}">小程序跳转</a>
                </li>
                </#if>
                <li <#if ($nav==6)class="active" </#if>>
                    <a href="/admin/frame/decoration/link?nav=6&bottom=${bottom!}">表单页面</a>
                </li>
                <li <#if ($nav==8)class="active" </#if>>
                    <a href="/admin/frame/decoration/link?nav=8&bottom=${bottom!}">门店</a>
                </li>
            </ul>
        </div>
        <div class="nav_link">
            <#if ($nav == 0)
            {{--<div class="common-user-link">
                <span>链接名称</span>
                <input type="text" name="name" value="" />
                <span>链接地址</span>
                <input type="text" name="link" value="" />
                <button class="btn_search">添加</button>
            </div>--!}

            <div class="goods_tb">
                <table width="100%">
                    <thead>
                        <tr>
                            <td>名称</td>
                            <td>链接</td>
                            <td>操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr data-back="true">
                            <td>首页</td>
                            <td class="link">pages/index/index</td>
                            <td><a href="##" class="hover_share" identity_id="pages/index/index" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>门店列表页</td>
                            <td class="link">pages/storelist/storelist</td>
                            <td><a href="##" class="hover_share" identity_id="pages/storelist/storelist" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>购物车页</td>
                            <td class="link">pages/cart/cart</td>
                            <td><a href="##" class="hover_share" identity_id="pages/cart/cart" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>个人中心页</td>
                            <td class="link">pages/usercenter/usercenter</td>
                            <td><a href="##" class="hover_share" identity_id="pages/usercenter/usercenter" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>订单列表页</td>
                            <td class="link">pages/orderlist/orderlist</td>
                            <td><a href="##" class="hover_share" identity_id="pages/orderlist/orderlist" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>全部商品</td>
                            <td class="link">pages/searchs/search</td>
                            <td><a href="##" class="hover_share" identity_id="pages/searchs/search" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>商家分类</td>
                            <td class="link">pages/sort/sort</td>
                            <td><a href="##" class="hover_share" identity_id="pages/sort/sort" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>分销返利中心</td>
                            <td class="link">pages/distribution/distribution</td>
                            <td><a href="##" class="hover_share" identity_id="pages/distribution/distribution" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>授权手机号</td>
                            <td class="link">pages/auth/auth</td>
                            <td><a href="##" class="hover_share" identity_id="pages/auth/auth" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>积分商品列表</td>
                            <td class="link">pages/searchs/search?is_from=integral</td>
                            <td><a href="##" class="hover_share" identity_id="pages/searchs/search?is_from=integral" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>会员卡领取页（卡号+密码）</td>
                            <td class="link">pages/getcardpage/getcardpage?type=1</td>
                            <td><a href="##" class="hover_share" identity_id="pages/getcardpage/getcardpage?type=1" type="33">分享</a></td>
                        </tr>
                        <tr data-back="true">
                            <td>会员卡领取页（领取码）</td>
                            <td class="link">pages/getcardpage/getcardpage?type=2</td>
                            <td><a href="##" class="hover_share" identity_id="pages/getcardpage/getcardpage?type=2" type="33">分享</a></td>
                        </tr>
                        <#if  ($bottom == 1)
                        <tr data-back="true">
                            <td>客服</td>
                            <td class="link">pages/customer/customer</td>
                            <td><a href="##" class="hover_share" identity_id="pages/customer/customer" type="33">分享</a></td>
                        </tr>
                        </#if>
                    </tbody>
                </table>
            </div>
            <#elseif>($nav == 1)
            <div class="mp_bottom_goods" id="set-goods" style="padding-top: 0">
                <div class="goods_search">
                    <form action="/admin/frame/decoration/link?nav=1" method="post" id="form1">
                        {{csrf_field()!}
                        {{--<select name="cat_id" id="">--!}
                        {{--<option value="0">请选择分类</option>--!}
                        {{--<#list ($cat_list as $item)--!}
                        {{--<option value="${item['cat_id']!}" <#if ($cat_id == $item['cat_id']) selected
                        </#if>>${item['cat_name']!}</option>--!}
                        {{--</#list>--!}
                        {{--</select>--!}
                        <span style="font-size: 14px;margin-left: 10px">关键词</span>
                        <input type="text" name="keywords" value="${keywords!}"/>
                        <input type="hidden" id="gopage" name="page"/>
                        <button class="btn_search">搜索</button>
                    </form>
                </div>
                <div class="goods_tb">
                    <table width="100%">
                        <thead>
                            <tr>
                                <td>商品信息</td>
                                <td>商品货号</td>
                                <td>链接</td>
                            </tr>
                        </thead>
                        <tbody>
                            <#list ($data_list as $item)
                            <tr data-back="true" goods_id="${item->goods_id!}">
                                <td>
                                    <div class="goods_info clearfix">
                                        <div class="goods_img">
                                            <img src="${item->goods_img!}" alt="" />
                                        </div>
                                        <div class="goods_name">
                                            <#if  ($item->goods_type == 1)
                                            <span>拼团</span>
                                            <#elseif>($item->goods_type == 3)
                                            <span>砍价</span>
                                            <#elseif>($item->goods_type == 5)
                                            <span>秒杀</span>
                                            <#elseif>($item->is_card_exclusive == 1)
                                            <span>会员专享</span>
                                            </#if>
                                                {!! $item->goods_name !!}
                                        </div>
                                    </div>
                                </td>
                                <td>${item->goods_sn!}</td>
                                <#if  ($item->goods_type == 1)
                                <td class="link">
                                    pages/groupbuyitem/groupbuyitem?pin_group_id=${item->pin_group_id!}</td>
                                <#elseif>($item->goods_type == 3)
                                <td class="link">pages/bargainitem/bargainitem?bargain_id=${item->bargain_id!}</td>
                                <#elseif>($item->goods_type == 5)
                                <td class="link">pages/seckillitem/seckillitem?sk_id=${item->sk_id!}</td>
                                <#else>
                                <td class="link">pages/item/item?good_id=${item->goods_id!}</td>
                                </#if>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <#if ($es_flag == true)
                    <#if (count($data_list)>0)
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td style="text-align:right;">{{ trans("admin/common.page.page_info",['perPage'=>$data_list1->per_page,'currentPage'=>$data_list1->current_page,'count'=>$data_list1->count,'total'=>$data_list1->total_num,])!}
                                    <a href="#" style="background: rgb(250, 250, 250);" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                    <a href="#" style="background: rgb(250, 250, 250);"
                                       onClick="return gopage(${data_list1->current_page -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                    <a href="#" style="background: rgb(250, 250, 250);"
                                       onClick="return gopage(${data_list1->current_page + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                    <a href="#" style="background: rgb(250, 250, 250);"
                                       onClick="return gopage(${data_list1->last_page!});">{{ trans("admin/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text" value="${data_list1->current_page!}"
                                           size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')" autocomplete="off"
                                    >{{ trans("admin/common.page.page")!}
                                    <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                                </td>
                            </tr>
                        </table>
                    <#else>
                        <style>
                            .paging_footer{
                                padding: 0px 0px;
                            }
                            .paging_footer table td{
                                border: none;
                            }
                            .bottom-table td{
                                padding: 10px 0px !important;
                            }
                        </style>
                        <div class="no_data_style" style="width: 100%;border: 1px solid #eee;">
                            <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                            </div>
                            <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                        </div>
                    </#if>

                        <script>
                            function gopage(page) {
                                var last_page = '${data_list1 -> last_page!}';
                                if ($("input[name='act']")) {
                                    $("input[name='act']").val("");
                                }
                                if (parseInt(page) > parseInt(last_page)) {
                                    page = last_page;
                                }
                                $("#gopage").val(page);
                                $("#form1").submit();
                            }
                        </script>
                <#else>
                <#include "/admin/jump_page_admin.ftl">
                <script>
                    function gopage(page) {
                        var last_page = '${data_list -> lastPage()!}';
                        if ($("input[name='act']")) {
                            $("input[name='act']").val("");
                        }
                        if (parseInt(page) > parseInt(last_page)) {
                            page = last_page;
                        }
                        $("#gopage").val(page);
                        $("#form1").submit();
                    }
                </script>
            </div>
            </#if>
        <#elseif>($nav == 2)
            <div class="goods_tb">
                <div class="goods_search">
                    <form action="/admin/frame/decoration/link?nav=2" method="post" id="form1">
                        {{csrf_field()!}
                        <input type="hidden" name="bottom" value="${bottom!}">
                        <span style="font-size: 14px;margin-left: 10px">页面名称</span>
                        <input type="text" name="keywords" value="${keywords!}" />
                        <span style="font-size: 14px;margin-left: 10px">页面分类</span>
                        <select name="page_cat_id" id="">
                            <option value="0">请选择分类</option>
                            <#list ($page_cat_list as $k=>$name)
                            <option value="${k!}" <#if ($page_cat_id==$k) selected </#if>>${name!}</option>
                            </#list>
                        </select>
                        <button class="btn_search">搜索</button>
                    </form>
                </div>
                <table width="100%">
                    <thead>
                        <tr>
                            <td width="40%">名称</td>
                            <td>分类</td>
                            <td>链接</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#if (count($data_list) > 0)
                        <#list ($data_list as $item)
                        <#if ($item->page_type == 0)
                        <tr data-back="true">
                            <td>${item->page_name!}</td>
                            <td>${page_cat_list[$item->cat_id]!}</td>
                            <td class="link">pages/index/index?page=${item->page_id!}</td>
                        </tr>
                        </#if>
                        </#list>
                        </#if>
                    </tbody>
                </table>
                <#if (count($data_list) <= 0) <div class="no_data_style"
                    style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                    <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                        <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                    </div>
                    <p
                        style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                        暂无相关数据</p>
            </div>
            </#if>
        </div>
        <#elseif>($nav == 3)
        <div class="goods_tb goods_td">
            <div class="path_jump">
                链接名称：<input type="text" name="web_url_name" placeholder="请输入链接名称">
                跳转路径：<input type="text" name="web_url" placeholder="请输入要跳转的网页链接">
                <a href="##" class="jump_url_btn">保存</a>
            </div>
            <div style="margin-bottom: 10px; padding-left: 20px; font-size: 14px; color: red;">
                注意：由于微信限制，目前仅支持小程序关联的公众号文章链接
                {{--<span>网页跳转路径需登录<a href="https://mp.weixin.qq.com" style="margin-left: 0;">小程序管理后台</a>配置域名白名单</span>--!}
            </div>
            <table width="100%">
                <thead>
                    <tr>
                        <td>名称</td>
                        <td>链接</td>
                        <td>操作</td>
                    </tr>
                </thead>
                <tbody>
                    <tr data-back="true" style="display: none;" class="url_example">
                        <td class="link"></td>
                        <td><a href="##" class="delete_url">删除</a></td>
                    </tr>
                    <#if (count($data_list) > 0)
                    <#list ($data_list as $item)
                    <tr data-back="true">
                        <td>${item->title!}</td>
                        <td class="link">${item->link_path!}</td>
                        <td><a href="##" class="delete_url" item="${item->id!}">删除</a></td>
                    </tr>
                    </#list>
                    </#if>
                </tbody>
            </table>
            <#if (count($data_list) <= 0) <div class="no_data_style"
                style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                </div>
                <p
                    style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                    暂无相关数据</p>
        </div>
        </#if>
    </div>

    <#elseif>($nav == 4)
    <div class="goods_tb wxapp_jump">
        <div class="path_jumps">
            {{--<span>小程序名称：</span>--!}
            {{--<input type="text" class="link_name_in" name="appid" placeholder="小程序名称"><br>--!}
            {{--<span>跳转小程序appid：</span>--!}
            {{--<input type="text" class="appid_in" name="appid" placeholder="若不填写，则跳转小程序内对应页面" style="margin-top: 10px"><br>--!}
            <span>小程序名称：</span>　　
            <select name="appid_in" id="appid_in" class="miniprogram_select link_name">
                <#if ($jump_list)
                <option value="">本店铺小程序</option>
                <#list ($jump_list as $item)
                <option value="${item->app_id!}">${item->app_name!}</option>
                </#list>
                </#if>
            </select><br>
            <span>小程序页面名称：</span>
            <input type="text" class="path_name" name="wxapp_path_name" placeholder="请输入要跳转的小程序页面名称"
                style="margin-top: 10px"><br>
            <span>小程序页面地址：</span>
            <input type="text" class="path_in" name="wxapp_link" placeholder="请输入要跳转的小程序页面路径" style="margin-top: 10px">
            <a href="##" class="wxapp_save">保存</a>
        </div>
        <table width="100%">
            <thead>
                <tr>
                    <td width="20%">小程序名称</td>
                    <td width="20%">页面名称</td>
                    <td width="30%">路径</td>
                    <td width="15%">状态</td>
                    <td width="15%">操作</td>
                </tr>
            </thead>
            <tbody>
                <tr data-back="true" class="wxapp_example" style="display: none;">
                    <td class="link_name"></td>
                    <td class="path_name"></td>
                    <td class="show-link"></td>
                    <td hidden class="link hide"></td>
                    <td>
                        {{--<a href="##" class="update_link">编辑</a>--!}
                        <a href="##" class="delete_url">删除</a>
                    </td>
                </tr>
                <#if (count($data_list) > 0)
                <#list ($data_list as $item)
                <tr data-back="true">
                    <td class="link_name" width="20%">${item->title!}</td>
                    <td class="path_name" width="20%">${item->path_name!}</td>
                    <td class="show-link" width="30%">${item->link_path!}</td>
                    <td width="15%"><#if ($item->usable || $item->appid=='')可用 <#else> 不可用 </#if></td>
                    <td hidden class="link hide">${item->link_path_text!}</td>
                    <td width="20%">
                        {{--<a href="##"  class="update_link" item="${item->id!}">编辑</a>--!}
                        <a href="##" class="delete_url" item="${item->id!}">删除</a>
                    </td>
                </tr>
                </#list>
                </#if>
            </tbody>
        </table>
        <#if (count($data_list) <= 0) <div class="no_data_style"
            style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p
                style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                暂无相关数据</p>
    </div>
    </#if>
    </div>
    <#elseif>($nav == 5)
    <div class="goods_tb">
        <table width="100%">
            <thead>
                <tr>
                    <td>分类名称</td>
                    <td>链接</td>
                </tr>
            </thead>
            <tbody>
                <#if (count($data_list) > 0)
                <#list ($data_list as $item)
                <tr data-back="true" class="spread" cat_id="${item["cat_id"]!}">
                    <td class="name">${item['cat_name']!}
                        <#if ($item["child"])
                        <img class="cat_down" src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" alt=""
                            style="margin-left:10px">
                        </#if>
                    </td>
                    <td class="link">pages/searchs/search?cat_id=${item['cat_id']!}</td>
                </tr>
                <#if ($item["child"])
                <tr data-back="true" hidden class="${item["cat_id"]!}">
                    <td class="name">${item["child"]['cat_name']!}</td>
                    <td class="link">pages/searchs/search?cat_id=${item["child"]['cat_id']!}</td>
                </tr>
                <#if ($item["child"]["child"])
                <tr data-back="true" hidden class="${item["cat_id"]!}">
                    <td class="name">${item["child"]["child"]['cat_name']!}</td>
                    <td class="link">
                        pages/searchs/search?cat_id=${item["child"]["child"]['cat_id']!}</td>
                </tr>
                </#if>
                </#if>
                </#list>
                </#if>
            </tbody>
        </table>
        <#if (count($data_list) <= 0) <div class="no_data_style"
            style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p
                style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                暂无相关数据</p>
    </div>
    </#if>
    </div>
    <#elseif>($nav == 6)
    <div class="goods_tb">
        <table width="100%">
            <thead>
                <tr>
                    <td>名称</td>
                    <td>链接</td>
                </tr>
            </thead>
            <tbody>
                <#if (count($data_list) > 0)
                <#list ($data_list as $item)
                <#if ($item->page_type == 0)
                <tr data-back="true">
                    <td>${item->page_name!}</td>
                    <td class="link">pages/form/form?page_id=${item->page_id!}</td>
                </tr>
                </#if>
                </#list>
                </#if>
            </tbody>
        </table>
        <#if (count($data_list) <= 0) <div class="no_data_style"
            style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p
                style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                暂无相关数据</p>
    </div>
    </#if>
    </div>
    <#elseif>($nav == 7)
    <div class="goods_tb">
        <table width="100%">
            <thead>
                <tr>
                    <td width="40%">名称</td>
                    <td>链接</td>
                </tr>
            </thead>
            <tbody>
                <#if (count($data_list) > 0)
                <#list ($data_list as $item)
                <tr data-back="true" <#if ($item['parent_id'] !=0) hidden </#if> class="${item['parent_id']!}">
                    <td>${item['sort_name']!}<img class="sort_down" src="http://${image_domain!}/image/admin/shop_deco/icon_down.png" alt=""
                                style="margin-left:10px" <#if ($item['parent_id'] !=0 || $item['has_child']==0) hidden </#if>
                                sort_id="${item['sort_id']!}">
                    </td>
                    <td class="link">pages/searchs/search?sort_id=${item['sort_id']!}</td>
                </tr>
                </#list>
                </#if>
            </tbody>
        </table>
        <#if (count($data_list) <= 0) <div class="no_data_style"
            style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p
                style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                暂无相关数据</p>
    </div>
    </#if>
    </div>
    <#elseif>($nav == 8)
    <div class="goods_tb">
        <table width="100%">
            <thead>
                <tr>
                    <td style="width: 200px;">名称</td>
                    <td>营业状态</td>
                    <td>链接</td>
                </tr>
            </thead>
            <tbody>
                <#if (count($data_list) > 0)
                <#list ($data_list as $item)
                {{--                <#if ($item->business_state == 1)--!}
                <tr data-back="true">
                    <td style="width: 200px;">${item->store_name!} </td>
                    <td><#if ($item->business_state == 1)营业中<#else>歇业中</#if></td>
                    <td class="link">pages/storeinfo/storeinfo?id=${item->store_id!}</td>
                </tr>
                {{--</#if>--!}
                </#list>
                </#if>
            </tbody>
        </table>
        <#if (count($data_list) <= 0) <div class="no_data_style"
            style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p
                style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                暂无相关数据</p>
    </div>
    </#if>
    </div>
    <#elseif>($nav == 11)
    <div class="goods_tb">
        <div class="goods_search">
            <form action="/admin/frame/decoration/link?nav=11" method="post" id="form1">
                {{csrf_field()!}
                <input type="hidden" name="bottom" value="${bottom!}">
                <span style="font-size: 14px;margin-left: 10px">品牌名称</span>
                <input type="text" name="keywords" value="${keywords!}" />
                <button class="btn_search">搜索</button>
            </form>
        </div>
        <table width="100%">
            <thead>
                <tr>
                    <td width="40%">品牌信息</td>
                    {{--<td>分类</td>--!}
                    <td>链接</td>
                </tr>
            </thead>
            <tbody>
                <#if (count($data_list) > 0)
                <#list ($data_list as $item)
                <tr data-back="true">
                    <td>
                        <div class="goods_info clearfix">
                            <div class="goods_img">
                                <img src="${item->logo!}" alt="" />
                            </div>
                            <div class="goods_name">
                                ${item->brand_name!}
                            </div>
                        </div>
                    </td>
                    <td class="link">pages/searchs/search?brand_id=${item->id!}</td>
                </tr>

                </#list>
                </#if>
            </tbody>
        </table>
        <#if (count($data_list) <= 0) <div class="no_data_style"
            style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p
                style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                暂无相关数据</p>
    </div>
    </#if>
    </div>
    <#elseif>($nav == 12)
    <div class="goods_tb">
        <div class="goods_search">
            <form action="/admin/frame/decoration/link?nav=12" method="post" id="form1">
                {{csrf_field()!}
                <input type="hidden" name="bottom" value="${bottom!}">
                <span style="font-size: 14px;margin-left: 10px">标签名称</span>
                <input type="text" name="keywords" value="${keywords!}" />
                <button class="btn_search">搜索</button>
            </form>
        </div>
        <table width="100%">
            <thead>
                <tr>
                    <td width="40%">标签信息</td>
                    {{--<td>分类</td>--!}
                    <td>链接</td>
                </tr>
            </thead>
            <tbody>
                <#if (count($data_list) > 0)
                <#list ($data_list as $item)
                <tr data-back="true">
                    <td>
                        <div class="goods_info clearfix">
                            <div class="goods_name">
                                ${item->name!}
                            </div>
                        </div>
                    </td>
                    <td class="link">pages/searchs/search?label_id=${item->id!}</td>
                </tr>

                </#list>
                </#if>
            </tbody>
        </table>
        <#if (count($data_list) <= 0) <div class="no_data_style"
            style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
            <div style="width: 30px;height: 33px; margin: 25px auto auto auto">
                <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
            </div>
            <p
                style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">
                暂无相关数据</p>
    </div>
    </#if>
    </div>

    </#if>
    <div class="goods_tb  market_table  hide">
        <div class="market_title">营销活动/ <span></span></div>
        <table width="100%" class="activity_table">
            <thead>
                <tr>
                    <td style="width: 100px;">名称</td>
                    <td style="width: 155px;">有效期</td>
                    <td>链接</td>
                </tr>
            </thead>
            <tbody class="activity_info">
                <!-- <#if (count($data_list) > 0)
                    <#list ($data_list as $item)
                        <tr data-back="true">
                            <td style="width: 200px;">${item->lottery_name!} </td>
                            <td></td>
                            <td class="link">pages/lottery/lottery?lottery_id=${item->id!}</td>
                        </tr>
                    </#list>
                </#if> -->
            </tbody>
        </table>
    </div>
    </div>

    </div>
    <#include ('admin.share_common')
    <script src="/js/layui/layui.js" type="text/javascript"></script>
    <script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
    <script src="/js/artDialog/plugins/iframeTools.source.js"></script>
    <script language="JavaScript" src="/js/admin/util.js?v=1.0.9"></script>
    <script>
        var nav;
        var childrenHeight = $('.nav_link').innerHeight();
        $('.nav-role ul li ul').css('height',childrenHeight + 'px')
        $('body').on('click', '.goods_tb tbody tr', function (e) {
            if(e.target.className === 'hover_share'){
                return
            }
            var flag_back = $(this).attr('data-back');
            if (flag_back == 'true') {
                $(this).addClass('goods_tr_choose');
                $(this).siblings().removeClass('goods_tr_choose');
                $(this).siblings().attr('data-back', 'true');
                $(this).attr('data-back', 'false');
                flag_back = 'false';
                var cat_id = $(this).attr("cat_id");
                $("." + cat_id).removeAttr("hidden");
                $(this).find(".cat_down").attr("src",
                    "http://${image_domain!}/image/admin/shop_deco/icon_up.png");
            } else if (flag_back == 'false') {
                $(this).removeClass('goods_tr_choose');
                $(this).attr('data-back', 'true');
                var cat_id = $(this).attr("cat_id");
                $("." + cat_id).attr("hidden", "true");
                $(this).find(".cat_down").attr("src",
                    "http://${image_domain!}/image/admin/shop_deco/icon_down.png");
                flag_back = 'true';
            }
        });

        $("body").on("click", ".goods_td .jump_url_btn", function () {
            var data = {};
            data.title = $(".goods_td .path_jump [name='web_url_name']").val();
            data.link_path = $(".goods_td .path_jump [name='web_url']").val();
            data.link_action = 1;
            if (!data.title || !data.link_path) {
                util.mobile_alert('请填写名称或路径');
                return false;
            }
            util.ajax_json("/admin/manage/jump/addlink", function (response) {
                if (response && response.error == 0) {
                    util.mobile_alert('添加成功');
                    window.location.reload();
                    // $(".goods_td table tbody").append($(".goods_td table tbody .url_example").clone());
                    // $(".goods_td table tbody tr:last-of-type .link").html(link_path);
                    // $(".goods_td table tbody tr:last-of-type").removeAttr("style");
                    // $(".goods_td table tbody tr:last-of-type").removeAttr("class");
                    // $(".goods_td table tbody tr:last-of-type .delete_url").attr('item', response.content.id);
                    // $(".goods_td table tbody tr:last-of-type").click();
                    // $(".goods_td .path_jump input").val("");
                } else {
                    util.mobile_alert(response.message);
                }
            }, data);
        })

        $("body").on("click", ".goods_tb  .delete_url", function () {
            var $this = $(this);
            var bizData = {
                id: $this.attr('item')
            };
            util.ajax_json("/admin/manage/jump/dellink", function (response) {
                if (response && response.error == 0) {
                    $this.parent().parent().remove();
                } else {
                    util.mobile_alert(response.message);
                }
            }, bizData);
        })

        // 常用链接添加
        $("body").on("click", ".common-user-link button", function () {
            var data = {};
            data.name = $('.common-user-link [name="name"]').val();
            data.link = $('.common-user-link [name="link"]').val();
            if (!data.name || !data.link) {
                util.mobile_alert('请填写完整！');
                return false;
            }
            util.ajax_json("/admin/manage/link/addcommon", function (response) {
                if (response && response.error == 0) {
                    util.mobile_alert('创建成功');
                    window.location.reload();
                } else {
                    util.mobile_alert(response.message);
                }
            })
        });

        $("body").on("click", ".wxapp_jump .wxapp_save", function () {
            var link_name = $("#appid_in option:selected").text();
            var link_path = $(".wxapp_jump .path_in").val();
            var path_name = $(".wxapp_jump .path_name").val();
            var appid = $("#appid_in").val();
            if (path_name == "") {
                util.mobile_alert('请填写需要跳转的页面名称！');
                return false;
            }
            if (link_path == "") {
                util.mobile_alert('路径为空，默认跳转到首页。');
                //return false;
            }
            var bizData = {
                link_action: 2,
                title: link_name,
                path_name: path_name,
                appid: appid,
                link_path: link_path
            };
            util.ajax_json("/admin/manage/jump/addlink", function (response) {
                if (response && response.error == 0) {
                    var jsonData = {
                        appid: appid,
                        link_path: link_path
                    };
                    window.location.reload();
                    // $(".wxapp_jump table tbody").append($(".wxapp_jump table tbody .wxapp_example").clone());
                    // $(".wxapp_jump tbody tr:last-of-type .link_name").html(link_name);
                    // $(".wxapp_jump tbody tr:last-of-type .show-link").html(link_path);
                    // $(".wxapp_jump tbody tr:last-of-type .path_name").html(path_name);
                    // $(".wxapp_jump tbody tr:last-of-type .link.hide").html(JSON.stringify(jsonData));
                    // $(".wxapp_jump tbody tr:last-of-type").removeAttr("style");
                    // $(".wxapp_jump tbody tr:last-of-type").removeAttr("class");
                    // $(".wxapp_jump tbody tr:last-of-type .update_link").attr('item', response.content.id);
                    // $(".wxapp_jump tbody tr:last-of-type .delete_url").attr('item', response.content.id);
                    // $(".wxapp_jump tbody tr:last-of-type").click();
                    // $(".wxapp_jump .link_name_in").val("");
                    // $(".wxapp_jump .path_in").val("");
                    // $(".wxapp_jump .path_name").val('');
                } else {
                    util.mobile_alert(response.message);
                }
            }, bizData);
        })

        $("body").on("click", ".goods_tb  .update_link", function () {
            var link_id = $(this).attr('item');
            var edit_tr = $(this).parent().parent();
            var title = edit_tr.find('.link_name').text();
            var appid = edit_tr.find('.appid').text();
            var link_path = edit_tr.find('.show-link').text();

            var html_text = `
            <div id="link-update" class="path_jumps" style="line-height: 48px; font-size: 14px;">
                <input type="hidden" class="link_id" value="` + link_id + `"/>
                <span>小程序名称：</span>
                <input type="text" class="link_name_in" placeholder="小程序名称" value="` + title + `" style="line-height: 30px; border: 1px solid #eee; padding-left: 12px;" /><br>
                <span>跳转小程序appid：</span>
                <input type="text" class="appid_in" placeholder="若不填写，则跳转小程序内对应页面" value="` + appid + `" style="line-height: 30px; border: 1px solid #eee; padding-left: 12px;" /><br>
                <span>跳转小程序页面：</span>
                <input type="text" class="path_in" placeholder="请输入要跳转的小程序页面路径" value="` + link_path + `" style="line-height: 30px; border: 1px solid #eee; padding-left: 12px;" />
            </div>
        `;
            layer.open({
                type: 1,
                content: html_text,
                title: '编辑小程序跳转',
                area: ['500px', '255px'],
                btn: ['保存', '取消'],
                yes: function (index) {
                    var bizData = {
                        id: link_id,
                        title: $('#link-update .link_name_in').val(),
                        appid: $('#link-update .appid_in').val(),
                        link_path: $('#link-update .path_in').val(),
                    };
                    util.ajax_json("/admin/manage/jump/updatelink", function (response) {
                        if (response && response.error == 0) {
                            edit_tr.find('.link_name').text(bizData.title);
                            edit_tr.find('.appid').text(bizData.appid);
                            edit_tr.find('.show-link').text(bizData.link_path);
                            edit_tr.find('.link.hide').text(response.content.json_path);
                            layer.close(index);
                        } else {
                            util.mobile_alert(response.message);
                        }
                    }, bizData);
                },
                btn2: function () {
                    layer.close();
                }
            });

        })
        $(".sort_down").click(function () {
            if ($(this).attr("src") == "http://${image_domain!}/image/admin/shop_deco/icon_down.png") {
                $(this).attr('src', "http://${image_domain!}/image/admin/shop_deco/icon_up.png")
                $(this).parent().parent().parent().find("." + $(this).attr('sort_id')).show();
            } else {
                $(this).attr('src', "http://${image_domain!}/image/admin/shop_deco/icon_down.png")
                $(this).parent().parent().parent().find("." + $(this).attr('sort_id')).hide();
            }
        })
        $('.activity_list').on('click', 'li', function () {
            var name = $(this).find('a').text();
            $('.activity_list li').removeClass('active');
            $('.nav-role li').removeClass('active');
            $('.goods_tb').addClass('hide');
            $('.market_table').removeClass('hide');
            $('.market_title').find('span').text(name);
            $('.tb_paging').addClass('hide');
            $(this).addClass('active');
            $(this).parent().parent().addClass('active');
            let index = $(this).data('index');
            util.ajax_json('/admin/decoration/link/market', function (res) {
                if (res.error === 0) {
                    let actList = res.content.actList;
                    let linkStr = res.content.linkStr;
                    let nameStr = res.content.nameStr;
                    let pname = res.content.pname;
                    let activity_info = $('.activity_info');
                    if (actList.length != 0) {
                        let str = ''
                        for (let i = 0; i < actList.length; i++) {
                            str += `<tr data-back="true">
                                    <td style="width: 100px;">${actList[i][nameStr]}</td>
                                    <td style="width: 155px;text-align:left"> ${actList[i].due_time_type == 1 || actList[i].expire_type == 2 || actList[i].card_type == 2  ? '永久有效' :  (actList[i].expire_type == 1 || actList[i].validity ? '自领取开始' + (actList[i].validity || actList[i].receive_day)  + (actList[i].date_type == 1 ? '周' : (actList[i].date_type == 2 ? '月' : '天')) + '内有效' : ((actList[i].start_time || actList[i].add_time || actList[i].created) + '至' + actList[i].end_time))}</td>
                                    <td class="link">${linkStr}${actList[i][pname]}</td>
                                </tr>`
                        }
                        activity_info.html(str);
                        $('.activity_info').parent().next().remove();
                    } else {
                        $('.activity_info').html('');
                        $('.activity_info').parent().next().remove();
                        $('.activity_info').parent().after(`<div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                        <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                        </div>
                        <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                    </div>`);
                    }
                }
            }, {
                type: index
            })

        })
    </script>
</body>

</html>