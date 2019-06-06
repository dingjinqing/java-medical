<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/add_coupon.css?v=1.0.15" type="text/css" />
<link rel="stylesheet" href="/css/admin/mp_bottom.css?v=1.0.4" type="text/css" />
<style type="text/css">
    input[name="page[]"]{
        width: 220px;
    }
    body{
        padding-bottom: 40px;
    }
    .mp_footer button:hover{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
    .mp_footer button:focus{
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
</style>

<div style="min-width: 1090px;">
    <div class="title" style="margin: 0;">
        <span>小程序管理/ </span>
        <span style="color: #666;">底部导航配置</span>
    </div>
    <div class="main-container">

            <div class="mp_bottom fix_every_footer">
                <div class="add_coupon add_coupon_content clearfix">
                    <div class="fl content_left">
                        <div class="fl_title">底部导航配置</div>
                        <ul class="nav_show">
                            <#if ($img_list)
                                <#list ($img_list as $k=>$item)
                                    <li <#if ($k==0)class="nav_show_active"</#if>>
                                        <img src="${item['hover']!}" alt="" class="nav_red" />
                                        <img src="${item['normal']!}" alt="" class="nav_gray" />
                                        <p>${item['text']!}</p>
                                    </li>
                                </#list>
                            <#else>
                            <li class="nav_show_active">
                                <img src="/image/admin/icon_mps/icon_yes_2.png" alt="" class="nav_red" />
                                <img src="/image/admin/icon_mps/icon_no_2.png" alt="" class="nav_gray" />
                                <p>首页</p>
                            </li>
                            <li>
                                <img src="/image/admin/icon_mps/icon_yes_1.png" alt="" class="nav_red" />
                                <img src="/image/admin/icon_mps/icon_no_1.png" alt="" class="nav_gray" />
                                <p>门店</p>
                            </li>
                            <li>
                                <img src="/image/admin/icon_mps/icon_yes_3.png" alt="" class="nav_red" />
                                <img src="/image/admin/icon_mps/icon_no_3.png" alt="" class="nav_gray" />
                                <p>购物车</p>
                            </li>
                            <li>
                                <img src="/image/admin/icon_mps/icon_yes_4.png" alt="" class="nav_red" />
                                <img src="/image/admin/icon_mps/icon_no_4.png" alt="" class="nav_gray" />
                                <p>我的</p>
                            </li>
                            </#if>
                                <li class="hide li_modal">
                                    <img src="/image/admin/icon_mps/icon_yes_4.png" alt="" class="nav_red" />
                                    <img src="/image/admin/icon_mps/icon_no_4.png" alt="" class="nav_gray" />
                                    <p></p>
                                </li>
                        </ul>
                    </div>
                    <div class="content_right">
                        <form action="/admin/manage/bottom/cfg" method="post" id="form1">
                            <input type="hidden" name="info">
                            {{csrf_field()!}
                        </form>
                        {{--<div class="coupon_info">--!}
                            {{--<form action="/admin/manage/bottom/cfg" method="post" id="form1">--!}
                                {{--{{csrf_field()!}--!}
                                {{--<input type="hidden" name="info">--!}
                                {{--<div class="coupon_info_title">底部导航配置</div>--!}
                                {{--<div class="mp_use">--!}
                                    {{--<p>将导航应用在以下页面:</p>--!}
                                    {{--<label for="use_one">--!}
                                        {{--<input type="checkbox" name="page[]" id="use_one" value="0"  <#if (($page && in_array(0,$page)) || (!$page)) checked </#if>/>--!}
                                        {{--店铺主页--!}
                                    {{--</label>--!}
                                    {{--<label for="use_two">--!}
                                        {{--<input type="checkbox" name="page[]" id="use_two" value="1" <#if ($page && in_array(1,$page)) checked </#if>/>--!}
                                        {{--自定义页面--!}
                                    {{--</label>--!}
                                    {{--<label for="use_three">--!}
                                        {{--<input type="checkbox" name="page[]" id="use_three" value="2" <#if ($page &&in_array(2,$page)) checked </#if>/>--!}
                                        {{--商品搜索--!}
                                    {{--</label>--!}
                                {{--</div>--!}
                            {{--</form>--!}
                        {{--</div>--!}
                        <div class="coupon_info coupon_rule">
                            <div class="coupon_info_title">
                                导航配置: <span style="color: #999;">最少需要使用三个导航设置，最多五个。图标大小80*80。</span>
                            </div>
                            <div>
                                <div class="mp_nav">
                                    <div class="mp_nav_list">
                                        <span>导航文字：</span>
                                        <input type="text" name="title[]" value="${img_list[0]['text'] or '首页'!}" />
                                    </div>
                                    <div class="mp_nav_list mp_nav_icon">
                                        <span>图片：<a href="##" class="mp_change">修改</a></span>
                                        <div class="nav_icon">
                                            <img src="${img_list[0]['hover'] or '/image/admin/icon_mps/icon_yes_2.png'!}" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input type="hidden" name="hover[]" value="${img_list[0]['hover'] or '/image/admin/icon_mps/icon_yes_2.png'!}">
                                            <p>点击状态</p>
                                        </div>
                                        <div class="nav_icon">
                                            <img src="${img_list[0]['normal'] or '/image/admin/icon_mps/icon_no_2.png'!}" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input type="hidden" name="normal[]" value="${img_list[0]['normal'] or '/image/admin/icon_mps/icon_no_2.png'!}">
                                            <p>未点击状态</p>
                                        </div>
                                    </div>
                                    <div class="mp_nav_list">
                                        <span>添加链接：</span>
                                        <input type="text" name="page[]" value="${img_list[0]['page']!}" data-appid="${img_list[0]['appid']!}" />
                                        <button class="btn_link" val="1">选择链接</button>
                                    </div>
                                </div>
                                <div class="mp_nav">
                                    <div class="mp_nav_list">
                                        <span>导航文字：</span>
                                        <input type="text" name="title[]" value="${img_list[1]['text'] or '门店'!}" />
                                    </div>
                                    <div class="mp_nav_list mp_nav_icon">
                                        <span>图片：<a href="##" class="mp_change">修改</a></span>
                                        <div class="nav_icon">
                                            <img src="${img_list[1]['hover'] or '/image/admin/icon_mps/icon_yes_1.png'!}" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input type="hidden" name="hover[]" value="${img_list[0]['hover'] or '/image/admin/icon_mps/icon_yes_1.png'!}">
                                            <p>点击状态</p>
                                        </div>
                                        <div class="nav_icon">
                                            <img src="${img_list[1]['normal'] or '/image/admin/icon_mps/icon_no_1.png'!}" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input type="hidden" name="normal[]" value="${img_list[1]['normal'] or '/image/admin/icon_mps/icon_no_1.png'!}">
                                            <p>未点击状态</p>
                                        </div>
                                    </div>
                                    <div class="mp_nav_list">
                                        <span>添加链接：</span>
                                        <input type="text" name="page[]" value="${img_list[1]['page']!}" data-appid="${img_list[0]['appid']!}" />
                                        <button class="btn_link">选择链接</button>
                                    </div>
                                </div>
                                <div class="mp_nav">
                                    <div class="mp_nav_list">
                                        <span>导航文字：</span>
                                        <input type="text" name="title[]" value="${img_list[2]['text'] or '购物车'!}" />
                                    </div>
                                    <div class="mp_nav_list mp_nav_icon">
                                        <span>图片：<a href="##" class="mp_change">修改</a></span>
                                        <div class="nav_icon">
                                            <img src="${img_list[2]['hover'] or '/image/admin/icon_mps/icon_yes_3.png'!}" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input name="hover[]" type="hidden" value="${img_list[2]['hover'] or '/image/admin/icon_mps/icon_yes_3.png'!}">
                                            <p>点击状态</p>
                                        </div>
                                        <div class="nav_icon">
                                            <img src="${img_list[2]['normal'] or '/image/admin/icon_mps/icon_no_3.png'!}" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input name="normal[]" type="hidden" value="${img_list[2]['normal'] or '/image/admin/icon_mps/icon_no_3.png'!}">
                                            <p>未点击状态</p>
                                        </div>
                                    </div>
                                    <div class="mp_nav_list">
                                        <span>添加链接：</span>
                                        <input type="text" name="page[]" value="${img_list[2]['page']!}" data-appid="${img_list[2]['appid']!}" />
                                        <button class="btn_link">选择链接</button>
                                    </div>
                                </div>
                                <#if ($img_list)
                                    <#list ($img_list as $k=>$item)
                                        <#if ($k>2)
                                    <div class="mp_nav mp_nav_more">
                                        <img src="/image/admin/icon_delete.png" alt="" class="delete-nav" />
                                        <div class="mp_nav_list">
                                            <span>导航文字：</span>
                                            <input type="text" name="title[]" value="${item['text']!}" />
                                        </div>
                                        <div class="mp_nav_list mp_nav_icon">
                                            <span>图片：<a href="##" class="mp_change">修改</a></span>
                                            <div class="nav_icon">
                                                <img src="${item['hover']!}" alt="" />
                                                <a href="javascript:void(0)" class="add_img">更换图标</a>
                                                <input name="hover[]" type="hidden" value="${item['hover']!}">
                                                <p>点击状态</p>
                                            </div>
                                            <div class="nav_icon">
                                                <img src="${item['normal']!}" alt="" />
                                                <a href="javascript:void(0)" class="add_img">更换图标</a>
                                                <input name="normal[]" type="hidden" value="${item['normal']!}">
                                                <p>未点击状态</p>
                                            </div>
                                        </div>
                                        <div class="mp_nav_list">
                                            <span>添加链接：</span>
                                            <input type="text" name="page[]" value="${item['page']!}" data-appid="${item['appid']!}" />
                                            <button class="btn_link">选择链接</button>
                                        </div>
                                    </div>
                                            </#if>
                                    </#list>
                                <#else>
                                <div class="mp_nav mp_nav_more">
                                    <img src="/image/admin/icon_delete.png" alt="" class="delete-nav" />
                                    <div class="mp_nav_list">
                                        <span>导航文字：</span>
                                        <input type="text" name="title[]" value="我的" />
                                    </div>
                                    <div class="mp_nav_list mp_nav_icon">
                                        <span>图片：<a href="##" class="mp_change">修改</a></span>
                                        <div class="nav_icon">
                                            <img src="/image/admin/icon_mps/icon_yes_4.png" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input name="hover[]" type="hidden" value="/image/admin/icon_mps/icon_yes_4.png">
                                            <p>点击状态</p>
                                        </div>
                                        <div class="nav_icon">
                                            <img src="/image/admin/icon_mps/icon_no_4.png" alt="" />
                                            <a href="javascript:void(0)" class="add_img">更换图标</a>
                                            <input name="normal[]" type="hidden" value="/image/admin/icon_mps/icon_no_4.png">
                                            <p>未点击状态</p>
                                        </div>
                                    </div>
                                    <div class="mp_nav_list">
                                        <span>添加链接：</span>
                                        <input type="text" name="page[]" value="${item['page']!}" data-appid="" />
                                        <button class="btn_link">选择链接</button>
                                    </div>
                                </div>
                                </#if>
                                <#if ((!$img_list) || count($img_list)<5)<button class="add_nav" onclick="return false;">添加菜单</button></#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="mp_footer fix_footer">
                <button class="mp_save" onclick="submit();">保存</button>
                {{--<button class="mp_back">返回</button>--!}
            </div>
    </div>
</div>

<div class="add_modal hide">
    <div class="mp_nav mp_nav_more">
        <img src="/image/admin/icon_delete.png" alt="" class="delete-nav" />
        <div class="mp_nav_list">
            <span>导航文字：</span>
            <input type="text" name="title[]" name="text" value="标题"/>
        </div>
        <div class="mp_nav_list mp_nav_icon">
            <span>图片：<a href="##" class="mp_change">修改</a></span>
            <div class="nav_icon">
                <img src="/image/admin/shop_beautify/add_decorete.png" alt="" />
                <a href="javascript:void(0)" class="add_img">更换图标</a>
                <input type="hidden" name="hover[]">
                <p>点击状态</p>
            </div>
            <div class="nav_icon">
                <img src="/image/admin/shop_beautify/add_decorete.png" alt="" />
                <a href="javascript:void(0)" class="add_img">更换图标</a>
                <input type="hidden" name="normal[]">
                <p>未点击状态</p>
            </div>
        </div>
        <div class="mp_nav_list">
            <span>添加链接：</span>
            <input type="text" name="page[]" value="${item['page']!}" data-appid="" />
            <button class="btn_link">选择链接</button>
        </div>
    </div>
</div>
<div id="change-icon">
    <ul class="clearfix">
        <li class="mp_list">
            <span>首页</span>
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_yes_2.png" class="hover1" />
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_no_2.png" class="normal1" />
        </li>
        <li class="mp_list">
            <span>门店</span>
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_yes_1.png" class="hover1" />
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_no_1.png" class="normal1" />
        </li>
        <li class="mp_list">
            <span>购物车</span>
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_yes_3.png" class="hover1" />
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_no_3.png" class="normal1" />
        </li>
        <li class="mp_list" style="margin-right: 0;">
            <span>我的</span>
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_yes_4.png" class="hover1" />
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_no_4.png" class="normal1" />
        </li>
        <li class="mp_list">
            <span>分类</span>
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_yes_5.png" class="hover1" />
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_no_5.png" class="normal1" />
        </li>
        <li class="mp_list">
            <span>活动</span>
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_yes_6.png" class="hover1" />
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_no_6.png" class="normal1" />
        </li>
        <li class="mp_list">
            <span>订单</span>
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_yes_7.png" class="hover1" />
            <img src="http://${image_domain!}/image/admin/icon_mps/icon_no_7.png" class="normal1" />
        </li>
    </ul>
</div>
<script>
    function submit(){
        var info=[];
        var obj={};
        var flag=0;
//        if($('input[name="page[]"]:checked').length==0){
//            util.mobile_alert('请选择应用导航的页面');
//            return false;
//        }
        $('.coupon_info .mp_nav').each(function(){
            obj={};
            obj.text = $(this).find('[name="title[]"]').val();
            obj.btn = 0;
            if(obj.text=='')
            {
                flag++;
                util.mobile_alert('导航文字不能为空');
                return false;
            }
            obj.normal = $(this).find('[name="normal[]"]').val();
            obj.hover = $(this).find('[name="hover[]"]').val();
            if(obj.hover == ''){
                flag++;
                util.mobile_alert('请添加点击状态的图片');
                return false;
            }
            if(obj.normal == ''){
                flag++;
                util.mobile_alert('请添加未点击状态的图片');
                return false;
            }
            if($(this).find('[name="page[]"]').attr('data-appid') != ""){
                obj.appid = $(this).find('[name="page[]"]').attr('data-appid');
            }
            obj.page = $(this).find('[name="page[]"]').val();
            if(obj.page == ''){
                flag++;
                util.mobile_alert('请选择要跳转的链接');
                return false;
            }
            if(obj.page == "pages/customer/customer"){
                obj.btn = 1;
            }
            info.push(obj);
        });
        if(flag>0){
            return false;
        }
        $('input[name="info"]').val(JSON.stringify(info));
        util.mobile_alert('保存成功');
        $("#form1").submit();
    }
</script>
<script type="text/javascript" src="/js/admin/mp_bottom.js?v=1.1.4"></script>
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<#include "/admin/footer.ftl">
