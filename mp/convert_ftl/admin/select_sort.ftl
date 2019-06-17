<html>
<head>
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="/css/admin/select_sort.css?v=1.0.2" type="text/css" />

</head>
<body>
<div id="set_sort_category">
        <#if (!$sort_list || count($sort_list) == 0)
            <div class="no_category">
                <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无分类</p>
                </div>    
            </div>
        <#else>
            <ul>
                <#if ($sort_list)
                    <#list ($sort_list as $item)
                        <li class="sort_li">
                            <div class="first_sort_cate">
                                <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" sort_id="${item->sort_id!}"/>
                                <span>
                            <input type="checkbox" name="sort_id[]" value="${item->sort_id!}" id="sort_${item->sort_id!}"  class="first_box"    <#if ($item->checked) checked </#if>/>
                            <label for="sort_${item->sort_id!}" sort_name = "${item->sort_name!}"sort_num="${item->sort_goods_num!}">${item->sort_name!}(${item->sort_goods_num!})</label>
                        </span>
                            </div>
                            <#if ($item->child)
                                <div class="second_sort_cate clearfix">
                                    <#list ($item->child as $sub_item)
                                    <div>
                                        <span>
                                            <input type="checkbox" name="sort_id[]" value="${sub_item->sort_id!}" id="cat_${sub_item->sort_id!}" class="second_box" <#if ($sub_item->checked) checked </#if>/>
                                            <label for="sort_${sub_item->sort_id!}" sort_num="${sub_item->sort_goods_num!}" sort_name = "${sub_item->sort_name!}">${sub_item->sort_name!}(${sub_item->sort_goods_num!})</label>
                                        </span>
                                    </div>
                                    </#list>
                                </div>
                            </#if>
                        </li>
                    </#list>
                </#if>
            </ul>
        </#if>
    </div>
    <script>

        $('#set_sort_category .cate_open').each(function (i,v) {
            var sort_id = $(v).attr('sort_id');
            var sort_ids = '${sort_ids!}'.split(',');
            if('${open_show!}'==1 && $.inArray(sort_id,sort_ids) == 0){
                $(this).parent().next().show();
                $(this).attr('src','/image/admin/cate_jian.png');
                $(this).attr('data-flag','false');
                flag_open = 'false';
            }
        })
           //一级分类的全选
        $('#set_sort_category').on('click','.first_sort_cate input[type="checkbox"]',function () {
            if($(this).is(':checked')){
                $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",true);
            }else{
                $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",false);
            }
        });
        //二级分类的选中
            $('#set_sort_category').on('click','.second_sort_cate .second_box',function () {
                var allLengthS = $(this).parent().parent().parent().find('.second_box').length;
                var checkedLengthS = $(this).parent().parent().parent().find('.second_box:checked').length;
                /* 二级分类全部选中，一级选中 */
                // if(allLengthS === checkedLengthS){
                //     $(this).parents('.second_sort_cate').prev().find('input[type="checkbox"]').prop("checked",true);
                // } else {
                //     $(this).parents('.second_sort_cate').prev().find('input[type="checkbox"]').prop("checked",false);
                // }
                if(allLengthS !== checkedLengthS){
                    $(this).parents('.second_sort_cate').prev().find('input[type="checkbox"]').prop("checked",false);
                }
            });
            //点击展开二级和三级分类
            $('#set_sort_category').on('click','.cate_open',function(){
                var flag_open = $(this).attr('data-flag');
                if(flag_open == 'true'){
                    $(this).parent().next().show();
                    $(this).attr('src','/image/admin/cate_jian.png');
                    $(this).attr('data-flag','false');
                    flag_open = 'false';
                }else if(flag_open == 'false'){
                    $(this).parent().next().hide();
                    $(this).attr('src','/image/admin/cate_jia.png');
                    $(this).attr('data-flag','true');
                    flag_open = 'true';
                }
            });

    </script>
</body>
</html>