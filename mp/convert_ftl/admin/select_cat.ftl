<html>
<head>
    <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
    <style>
    *{
        margin: 0;
        padding: 0;
    }
    img{
        vertical-align: middle;
    }
    body {
        line-height: 24px;
        font: 14px Helvetica Neue,Helvetica,PingFang SC,\5FAE\8F6F\96C5\9ED1,Tahoma,Arial,sans-serif;
    }
    ul,li{
        list-style:none;
        margin:0;
        padding:0;
    }
    label {
        display: inline-block;
        max-width: 100%;
        margin-bottom: 5px;
    }
    #set-category{
        display: block;
        width: 100%;
        height: 100%;
        padding: 0 12px;
        box-sizing: border-box;
    }
    #set-category li{
        padding: 10px;
    }
    #set-category li:nth-of-type(even){
        background: #f3f3f3;
    }
    #set-category li .third_cate span{
        display: inline-block;
        width: 140px;
        -ms-text-overflow: ellipsis;
        text-overflow: ellipsis;
        overflow: hidden;
        white-space:nowrap;
    }
    #set-category input[type='checkbox']{
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        width: 12px;
        height: 12px;
        background: url("/image/admin/square_no.png") no-repeat;
        position: relative;
        top: 1px;
        margin-right: 3px;
        margin-left: 5px;
        border: none;
    }
    #set-category input[type='checkbox']:checked{
        background: url("/image/admin/square_yes.png") no-repeat;
    }
    #set-category li:nth-of-type(even) {
        background: #f3f3f3;
    }
    .second_cate {
        margin-left: 35px;
        display: none;
        color: #666;
    }
    .third_cate{
        margin-left: 20px;
    }
    </style>
</head>
<body>
<div id="set-category">
    <#if (!$cat_list)
        <div class="no_category">
            <div>
                <img src="http://${image_domain!}/image/admin/no_category.png" alt="">
                <p>暂无分类</p>
            </div>
        </div>
    <#else>
        <ul>
            <#if ($cat_list)
                <#list ($cat_list as $item)
                    <li class="cate_li">
                        <div class="first_cate">
                            <img src="http://${image_domain!}/image/admin/cate_jia.png" alt="" class="cate_open" data-flag="true" cat_id="${item->cat_id!}" />
                            <span>
                            <input type="checkbox" name="cat_id[]" value="${item->cat_id!}" id="cat_${item->cat_id!}" <#if ($item->checked) checked </#if>/>
                            <label goods_num="${item->goods_num!}" for="cat_${item->cat_id!}">${item->cat_name!}(${item->goods_num!})</label>
                        </span>
                        </div>
                        <#if ($item->child)
                            <div class="second_cate">
                                <#list ($item->child as $sub_item)
                                    <div>
                                <span>
                                    <input type="checkbox" name="cat_id[]" value="${sub_item->cat_id!}" id="cat_${sub_item->cat_id!}" class="second_box" <#if ($sub_item->checked) checked </#if> />
                                    <label goods_num="${sub_item->goods_num!}" for="cat_${sub_item->cat_id!}">${sub_item->cat_name!}(${sub_item->goods_num!})</label>
                                </span>
                                        <#if ($sub_item->child)
                                            <div class="third_cate">
                                                <#list ($sub_item->child as $th_item)
                                                    <span>
                                                        <input type="checkbox" name="cat_id[]" value="${th_item->cat_id!}" id="cat_${th_item->cat_id!}"  <#if ($th_item->checked) checked </#if>/>
                                                        <label goods_num="${th_item->goods_num!}" for="cat_${th_item->cat_id!}">${th_item->cat_name!}(${th_item->goods_num!})</label>
                                                    </span>
                                                </#list>
                                            </div>
                                        </#if>
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
        $('#set-category .cate_open').each(function (i,v) {
            var cat_id = $(v).attr('cat_id');
            var cat_ids = '${cat_ids!}'.split(',');
            if('${open_show!}'==1 && $.inArray(cat_id,cat_ids) == 0){
                $(this).parent().next().show();
                $(this).attr('src','/image/admin/cate_jian.png');
                $(this).attr('data-flag','false');
                flag_open = 'false';
            }
        })

        //一级分类的全选
        $('#set-category').on('click','.first_cate input[type="checkbox"]',function () {
            if($(this).is(':checked')){
                $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",true);
            }else{
                $(this).parent().parent().next().find('input[type="checkbox"]').prop("checked",false);
            }
        });
        //二级分类的选中
        $('#set-category').on('click','.second_cate .second_box',function () {
            var allLengthS = $(this).parent().parent().parent().find('.second_box').length;
            var checkedLengthS = $(this).parent().parent().parent().find('.second_box:checked').length;
            // if(allLengthS == checkedLengthS){
            //     // $(this).parent().parent().parent().prev().find('input[type="checkbox"]').prop('checked',true);
            //     if(checkedLengthS>0){
            //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',true);
            //     }else{
            //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',false);

            //     }
            // }else{
            //     if(checkedLengthS>0){
            //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',true);
            //     }else{
            //         $(this).parent().next().find('input[type="checkbox"]').prop('checked',false);
            //     }
            // }
            /* 二级分类点击，下面所有三级分类被选中 */
            $(this).parent().next().find('input[type="checkbox"]').prop("checked",$(this).prop("checked"));
            /* 二级分类全部选中，一级选中 */
            // if(allLengthS === checkedLengthS){
            //     $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
            // } else {
            //     $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
            // }
        });
        //三级分类的选中
        $('#set-category').on('click','.third_cate input[type="checkbox"]',function () {
            $(this).attr("checked", "checked");
            // var allLength = $(this).parent().parent().find('input[type="checkbox"]').length;
            // var checkedLength = $(this).parent().parent().find('input[type="checkbox"]:checked').length;
            // if(allLength === checkedLength){
            //     $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",true);
            // } else {
            //     $(this).parent().parent().prev().children('input[type="checkbox"]').prop("checked",false);
            // }
            // var secondLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]').length;
            // var secondCheckedLength = $(this).parents('.second_cate').children().children('span').children('input[type="checkbox"]:checked').length;
            /* 三级全部选中 判断二级是否全部选中 */
            // if(secondLength === secondCheckedLength){
            //     $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",true);
            // } else {
            //     $(this).parents('.second_cate').prev().find('input[type="checkbox"]').prop("checked",false);
            // }
        });
        //点击展开二级和三级分类
        $('#set-category').on('click','.cate_open',function(){
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