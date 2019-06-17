<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/sort_manage.css?v=1.0.1" type="text/css" />
<style type="text/css">
    .change_div{
        width: 100%;
        position: absolute;
        bottom:0;
        background: rgba(0,0,0,0.5);
        color: #fff;
        padding:5px 0;
        font-size: 12px;
        text-align: center;
        /*display: none;*/
    }
    .choose_imfdd .title_mgadsa{
        width: 230px;
    }
    .title_mgadsa img{
        max-width: 100%;
        max-height:100%;
    }
</style>
<div class="title">
    <span>商品管理 / </span><span style="color: #666;">商家分类管理  </span>
</div>
<div class="sort_container">
    <form action="" method="post" id="form1">
        {{csrf_field()!}
        <input type="hidden" name="sort_img" value="${request->sort_img!}">
        <input type="hidden" name="type" value="0">
        <input type="hidden" name="sort_id" value="${request->sort_id!}">
        <div class="sort_navlist">
            <ul class="sort_nav clearfix">
                <li class="sort_list" >
                    <a href="/admin/goods/sort/list?top_index=2&type=0">分类列表</a>
                </li>
                <li class="reco_sort_list" >
                    <a href="/admin/goods/sort/list?top_index=2&type=1">推荐分类</a>
                </li>
                <li class="add_sort sort_avtives" >
                    <a href="/admin/goods/sort/add?top_index=2&type=0"><#if ($request->sort_id) 编辑分类 <#else> 添加分类 </#if></a>
                </li>
                <li class="add_reco_sort" hidden>
                    <a href="/admin/goods/sort/add?top_index=2&type=1" >添加推荐分类</a>
                </li>
            </ul>
            <div class="add_sort_content">
                <ul>
                    <li class="detail_lis clearfix">
                        <div style="margin-bottom: 15px">
                            <input <#if ($request->parent_id == 0 && !is_null($request->parent_id)) disabled </#if> type="radio" name="sort_radio" <#if ($request->parent_id == 0)checked="checked" class="checkbox_actives" </#if> id="all_store" value="0" onclick="radio_choose(this)" />
                            <label for="all_store">添加一级分类</label>
                            <input style="margin-left: 30px"  <#if ($request->parent_id == 0 && !is_null($request->parent_id)) disabled </#if>  style="z-index: 2" type="radio" name="sort_radio" <#if ($request->parent_id!=0)checked="checked" class="checkbox_actives" </#if> id="part_store" value="1" onclick="radio_choose(this)" />
                            <label style="z-index: 2" for="part_store">添加二级分类</label>
                        </div>
                    </li>
                   <li class="detail_lis clearfix yulan" style="display: none">
                       <div class="item_title"><span style="color: red;">*</span>一级分类：</div>
                       <select name="parent_id" id="" class="sort_grade" <#if ($request->parent_id == 0 && !is_null($request->parent_id)) disabled </#if>>
                           {{--<option value="0" <#if ($request->parent_id == 0) selected </#if>>不选择</option>--!}
                           <option value=""  selected >请选择</option>
                           <#list ($sort_list as $item)
                           <option value="${item->sort_id!}" <#if ($request->parent_id == $item->sort_id) selected </#if>>${item->sort_name!}</option>
                           </#list>
                       </select>
                       <a href="javascript:;" class="show_eg" style="margin-left: 10px;margin-top: 4px;">查看示例
                           <div class="hover_show">
                               <img src="http://${image_domain!}/image/admin/new_preview_image/sort.jpg" alt="">
                           </div>
                       </a>
                   </li>
                    <li class="detail_lis clearfix " style="display: none;">
                        <div class="item_title"></div>
                        <div class="tips_sort">分类默认最多只能创建二级分类</div>
                    </li>
                    <li class="detail_lis clearfix sort_title_img">
                        <div class="item_title"><span style="color: red;">*</span>分类名称：</div>
                        <input type="text" class="sort_name" name="sort_name" value="${request->sort_name!}">
                    </li>
                    <li class="detail_lis clearfix sort_title_img">
                        <div class="item_title"><span style="color: red;"></span>分类优先级：</div>
                        <input type="text" name="first" class="sort_rank" onkeyup="value=value.replace(/[^\d]/g,'')" value="${request->first ?? 1!}">
                    </li>
                    <li class="detail_lis clearfix">
                        <div class="item_title"></div>
                        <div class="tips_sort">可填写1到100间的整数，数字越大前端排列顺序越靠前。优先级重复，则按照分类添加时间排序，添加越早越靠前排列</div>
                    </li>
                    <li class="detail_lis clearfix sort_title_img choose_imfdd">
                        <div class="item_title" style="line-height: inherit"><span style="color: red;"></span>分类头图：</div>
                        <div class="title_mgadsa">
                            <img <#if ($request->sort_img) src="${request->sort_img!}" <#else> src="http://${image_domain!}/image/admin/add_simple.png" </#if> alt="" class="sele_img">
                            <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="del_this">
                            <div class="change_div">更换图片</div>
                        </div>
                    </li>
                    <li class="detail_lis clearfix choose_imfdd">
                        <div class="item_title"></div>
                        <div class="tips_sort">显示在分类页顶部，不填写则不显示，建议尺寸510*200</div>
                    </li>
                    <li class="detail_lis clearfix choose_imfdd">
                        <div class="item_title"></div>
                        <div class="add_path_list clearfix">
                            <div>头图链接：</div>
                            <input type="text" class="path_input" name="img_link" value="${request->img_link!}" />
                            <a href="##" class="add_path">添加链接</a>
                        </div>
                    </li>
                    <li class="detail_lis clearfix sort_title_img fenleideicon">
                        <div class="item_title" style="line-height: inherit"><span style="color: red;">*</span>分类图标：</div>
                        <div class="add_icon_list clearfix">
                            <a href="##" class="select_sort_icon">修改</a>
                            <div class="sort_icon" style="background: url(/image/admin/sort_moren.png) no-repeat;background-size: 50px 50px;background-position: center">
                                <span>更换图标</span>
                            </div>
                            <div class="size_limit">150*140</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </form>
    <#if  ($request)
        <div class="pages_bottom">
            <a href="##" class="btn_to_update">保存</a>

        </div>
    <#else>
        <div class="pages_bottom">
            <a href="##" class="btn_to_save">保存</a>
        </div>
    </#if>
</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="/js/admin/add_sort.js?v=1.1.2"></script>
<script type="text/javascript">

    var img = '${request->sort_img!}';
    if(img!=""){
        $(".sort_icon").css("background",'url('+img+')');
    }
    $(".sort_icon").css("background-size",'70px 70px');
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
    $('input[name="sort_radio"]').click(function () {
        if($(this).val() == 1){
            $(".yulan").show();
        }else{
            $(".yulan").hide();
        }
    })
</script>
<script type="text/javascript">
    getPowerInfo('main_config','sort','sub_1','商家分类',0);
</script>
