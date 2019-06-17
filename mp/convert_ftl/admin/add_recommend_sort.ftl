<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sort_manage.css?v=1.0.10" type="text/css" />
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">商家分类管理</span>
</div>
<div class="sort_container">
    <form action="" method="post" id="form1">
        {{csrf_field()!}
        <input type="hidden" name="type" value="1">
        <input type="hidden" name="parent_id" value="-1">
        <input type="hidden" name="children" >
        <input type="hidden" name="sort_id" value="${request->sort_id!}">
        <div class="sort_navlist">
            <ul class="sort_nav clearfix">
                <li class="sort_list" >
                    <a href="/admin/goods/sort/list?top_index=2&type=0">分类列表</a>
                </li>
                <li class="reco_sort_list" >
                    <a href="/admin/goods/sort/list?top_index=2&type=1">推荐分类</a>
                </li>
                <li class="add_sort " hidden >
                    <a href="/admin/goods/sort/add?top_index=2&type=0" >添加分类</a>
                </li>
                <li class="add_reco_sort sort_avtives" >
                    <a href="/admin/goods/sort/add?top_index=2&type=1"><#if ($request->sort_id) 编辑推荐分类 <#else> 添加推荐分类 </#if></a>
                </li>
            </ul>
            <div class="add_reco_content">
                <ul>
                    <li class="detail_lis clearfix yulan">
                        <div class="item_title"><span style="color: red;">*</span>分类名称：</div>
                        <input type="text" name="sort_name" class="reco_name" value="${request->sort_name!}">
                        <a href="javascript:;" class="show_eg" style="margin-left: 10px;margin-top: 4px;">查看示例
                            <div class="hover_show">
                                <img src="http://${image_domain!}/image/admin/new_preview_image/recommend_sort.jpg" alt="">
                            </div>
                        </a>
                    </li>
                    <li class="detail_lis clearfix sort_title_img">
                        <div class="item_title">分类优先级：</div>
                        <input type="text" name="first" class="reco_rank" onkeyup="value=value.replace(/[^\d]/g,'')" value="${request->first ?? 1!}">
                    </li>
                    <li class="detail_lis clearfix " style="margin-top: 8px">
                        <div class="item_title"></div>
                        <div class="tips_sort">仅可填写1到100间的整数数字越大前端排列顺序越靠前</div>
                    </li>
                    <li class="detail_lis clearfix sort_title_img">
                        <div class="item_title" style="line-height: inherit"><span style="color: red;">*</span>子类：</div>
                        <div class="child_content">
                            <#if ($children)
                                <#list ($children as $item)
                                    <div class="each_itema ">
                                        <ul>
                                            <input type="hidden" name="child_id" class="child_id" value="${item['sort_id']!}">
                                            <input type="hidden" name="child_parent" class="parent" value="${request->sort_id!}">
                                            <li class="each_itema_li clearfix">
                                                <div class="each_itma_title">子类名称：</div>
                                                <input type="text" class="chilie_sort_name" value="${item['sort_name']!}">
                                            </li>
                                            <li class="each_itema_li clearfix">
                                                <input type="hidden" name="sort_img" class="sort_img" value="${item['sort_img']!}">
                                                <div class="each_itma_title" style="line-height: inherit">图片：</div>
                                                <div class="add_icon_list clearfix">
                                                    <a href="##" class="select_sort_icon">修改</a>
                                                    <div class="sort_icon" style="background: url('${item['sort_img']!}');background-size:70px 70px ">
                                                        <span>更换图标</span>
                                                    </div>
                                                    <div class="size_limit">150*140</div>
                                                </div>
                                            </li>
                                            <li class="each_itema_li clearfix">
                                                <div class="each_itma_title">添加链接：</div>
                                                <div class="add_path_list clearfix">
                                                    <input type="text" class="path_input" name="img_link" value="${item['img_link']!}">
                                                    <a href="##" class="add_path">添加链接</a>
                                                </div>
                                            </li>
                                        </ul>
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_this">
                                    </div>
                                </#list>
                            <#else>
                                <div class="each_itema">
                                    <ul>
                                        <li class="each_itema_li clearfix">
                                            <input type="hidden" name="child_parent" class="parent" value="${request->sort_id!}">
                                            <div class="each_itma_title"><span style="color: red;">*</span>子类名称：</div>
                                            <input type="text" class="chilie_sort_name">
                                        </li>
                                        <li class="each_itema_li clearfix">
                                            <input type="hidden" name="sort_img" class="sort_img">
                                            <div class="each_itma_title" style="line-height: inherit"><span style="color: red;">*</span>图片：</div>
                                            <div class="add_icon_list clearfix">
                                                <a href="##" class="select_sort_icon">修改</a>
                                                <div class="sort_icon">
                                                    <span>更换图标</span>
                                                </div>
                                                <div class="size_limit">150*140</div>
                                            </div>
                                        </li>
                                        <li class="each_itema_li clearfix">
                                            <div class="each_itma_title">添加链接：</div>
                                            <div class="add_path_list clearfix">
                                                <input type="text" class="path_input" name="img_link">
                                                <a href="##" class="add_path">添加链接</a>
                                            </div>
                                        </li>
                                    </ul>
                                    <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_this">
                                </div>
                            </#if>
                            <a href="##" class="add_child_item">添加</a>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
    <#if  ($request->sort_id)
        <div class="pages_bottom">
            <a href="##" class="btn_to_update">保存</a>

        </div>
    <#else>
        <div class="pages_bottom">
            <a href="##" class="btn_to_save">保存</a>
        </div>
    </#if>
</div>
<div class="clone_item hide">
    <div class="each_itema ">
        <ul>
            <input type="hidden" name="child_parent" class="parent" value="${request->sort_id!}">
            <li class="each_itema_li clearfix">
                <div class="each_itma_title"><span style="color: red;">*</span>子类名称：</div>
                <input type="text" class="chilie_sort_name">
            </li>
            <li class="each_itema_li clearfix">
                <input type="hidden" name="sort_img" class="sort_img">
                <div class="each_itma_title" style="line-height: inherit"><span style="color: red;">*</span>图片：</div>
                <div class="add_icon_list clearfix">
                    <a href="##" class="select_sort_icon">修改</a>
                    <div class="sort_icon">
                        <span>更换图标</span>
                    </div>
                    <div class="size_limit">150*140</div>
                </div>
            </li>
            <li class="each_itema_li clearfix">
                <div class="each_itma_title">添加链接：</div>
                <div class="add_path_list clearfix">
                    <input type="text" class="path_input" name="img_link">
                    <a href="##" class="add_path">添加链接</a>
                </div>
            </li>
        </ul>
        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_this">
    </div>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript">
    var hasSaved = true;
    util.inputChange();
    util.selectChange();
    util.radioChange('service_type');
    $(".select_sort_icon").click(function () {
        var hasSaved = false;
    });
    $(".sort_icon").click(function () {
        var hasSaved = false;
    });
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
</script>
<script type="text/javascript" src="/js/admin/add_reco_sort.js?v=1.1.1"></script>
<script type="text/javascript">
    getPowerInfo('main_config','sort','sub_1','商家分类',0);
</script>