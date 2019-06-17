<style>
    .btn_add {
        width: 120px;
        height: 30px;
        line-height: 30px;
        text-align: center;
        color: #5A8BFF;
        border: 1px solid #ccc;
        background: #fff;
        cursor: pointer;
        margin: 10px 0;
        display: inline-block;
    }

    .goods_area {
        padding-right: 10px;
        max-width: 528px;
    }

    .goods_area table {
        margin-bottom: 0;
        border: 1px solid #ddd;
        margin-top: 0;
        background: #fff;
        max-width: 600px;
    }

    .goods_area table thead tr {
        background: #f8f8f8;
        font-weight: bold;
        color: #333;
        text-align: center;
    }

    .goods_area table thead tr td {
        padding: 10px 5px;
    }

    .goods_area table tbody td {
        border: 1px solid #ddd;
        text-align: center;
    }

    .goods_area table td {
        padding: 10px;
    }

    .goods_img img {
        width: 40px;
    }

    .goods_img {
        width: 40px;
        height: 40px;
        margin-right: 5px;
        float: left;
    }

    .goods_name {
        line-height: 20px;
        text-align: left;
    }

    .sort_area table {
        margin-bottom: 10px;
        border: 1px solid #ddd;
        margin-top: 10px;
        background: #fff;
    }

    .cat_table,
    .sort_table {
        display: none;
    }

    .sort_table tr th,
    .cat_table tr th {
        text-align: center;
    }

    .sort_table th,
    .cat_table th {
        background-color: #f8f8f8;
    }

    .cat_modal tr:last-child div,
    .sort_table tr:last-child div,
    .cat_table tr:last-child div {
        padding-left: 20px;
        line-height: 50px;
        font-size: 14px;
        border-bottom: 1px solid #eee;
        color: #333;
    }

    .cat_modal tr:last-child div span,
    .sort_table tr:last-child div span,
    .cat_table tr:last-child div span {
        padding: 4px 10px;
        border-radius: 20px;
        font-size: 14px;
        color: #666;
    }

    .first_cat {
        border: 1px solid #b9d2ff;
        background-color: #f0f5ff;
    }

    .second_cat {
        border: 1px solid #ffe2b8;
        background-color: #fffaf2;
    }

    .third_cat {
        border: 1px solid #ffc0cc;
        background-color: #fff6f8;
    }

    .cat_modal tr:last-child div span+span,
    .sort_table tr:last-child div span+span,
    .cat_table tr:last-child div span+span {
        margin-left: 15px;
    }

    .cat_modal tr:last-child ul.cat_ul,
    .sort_table tr:last-child ul.cat_ul,
    .cat_table tr:last-child ul.cat_ul {
        width: 100%;
        padding-bottom: 16px;
    }

    .cat_modal tr:last-child ul,
    .sort_table tr:last-child ul,
    .cat_table tr:last-child ul {
        padding-left: 20px;
    }

    .cat_modal tr:last-child ul li,
    .sort_table tr:last-child ul li,
    .cat_table tr:last-child ul li {
        float: left;
        padding: 4px 10px;
        border-radius: 20px;
        line-height: 20px;
        margin: 16px 10px 0 0;
        color: #666;
    }

    .cat_modal tr:first-child span,
    .sort_table tr:first-child span,
    .cat_table tr:first-child span {
        margin-left: 74px;
        font-size: 15px;
        color: #333;
    }
</style>
<div class="choose_content">
    <#if (in_array('add_goods',$config))
    <div class="box_list">
        <div class="btn_add" data-type="add_goods" area_data>
            <img src="http://img.mini.cn/image/admin/icon_jia.png" alt="">
            选择商品
        </div>
        <span class="area_show"></span>
        <div class="goods_area">
            <#if ($goodsLabel->gl_goods_id)
            <table class="goods_table" width="100%" goods_array="${goodsLabel->goods_array!}">
                <thead>
                    <tr>
                        <td width="50%">商品名称</td>
                        <td width="15%">价格</td>
                        <td width="15%">库存</td>
                        <td width="20%">操作</td>
                    </tr>
                </thead>
                <tbody class="label_tbody">
                    <#list ($goodsLabel->goods_array as $item)
                    <tr>
                        <td width="50%">
                            <div class="goods_info clearfix">
                                <div class="goods_img"><img src="${item->goods_img!}" alt="" /></div>
                                <div class="goods_name">
                                    ${item->goods_name!}
                                </div>
                            </div>
                        </td>
                        <td width="15%">${item->shop_price!}</td>
                        <td width="15%">${item->goods_number!}</td>
                        {{--<td><#if ($item->is_delete == 1)已删除<#elseif>($item->is_on_sale == 0)下架<#elseif>($item->goods_number==0)售罄<#else>上架</#if></td>--!}
                        <td width="20%"><a href="javascript:void(0)" goods_id="${item->goods_id!}" class="del">删除</a>
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>

            <#else>
            <table class="goods_table" width="100%" style="display: none">
                <thead>
                    <tr>
                        <td width="50%">商品名称</td>
                        <td width="15%">价格</td>
                        <td width="15%">库存</td>
                        <td width="20%">操作</td>
                    </tr>
                </thead>
                <tbody class="label_tbody"></tbody>
            </table>
            </#if>
        </div>
    </div>
    </#if>
    <#if (in_array('add_cate',$config))
    <div class="box_list">
        <div class="btn_add" data-type="add_cate" area_data>
            <img src="http://img.mini.cn/image/admin/icon_jia.png" alt="">
            选择平台分类
        </div>
        <span class="area_show"></span>
        <div class="sort_area">
            <table class="cat_table" width="528px" cat_array="${strategy->recommend_cat_id!}">
                <tr>
                    <th width="100%" style="border-bottom: 1px solid #ddd;"><span>平台分类</span>
                        <div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px"
                                class="edit" data-type="add_cate">编辑</a><a href="javascript:;" class="del_cat">删除</a></div>
                    </th>
                    <!-- <th width="30%" >操作</th> -->
                </tr>
            </table>
        </div>
    </div>
    </#if>
    <#if (in_array('add_sort',$config))
    <div class="box_list">
        <div class="btn_add" data-type="add_sort" area_data>
            <img src="http://img.mini.cn/image/admin/icon_jia.png" alt="">
            选择商家分类
        </div>
        <span class="area_show"></span>
        <div class="sort_area">
            <table class="sort_table" width="528px" sort_array="${strategy->recommend_sort_id!}">
                <tr>
                    <th width="100%" style="border-bottom: 1px solid #ddd;"><span>商家分类</span>
                        <div class="fr" style="margin-right: 6px;"><a href="javascript:;" style="margin-right:10px"
                            class="edit" data-type="add_sort">编辑</a><a href="javascript:;" class="del_sort_cat">删除</a></div>
                    </th>
                    <!-- <th width="30%" >操作</th> -->
                </tr>
            </table>
        </div>
    </div>
    </#if>
    <#if (in_array('add_label',$config))
    <div class="box_list">
        <div class="btn_add" data-type="add_label" area_data>
            <img src="http://img.mini.cn/image/admin/icon_jia.png" alt="">
            选择商品标签
        </div>
        <span class="area_show"></span>
        <div></div>
    </div>
    </#if>
    <#if (in_array('add_brand',$config))
    <div class="box_list">
        <div class="btn_add" data-type="add_brand" area_data>
            <img src="http://img.mini.cn/image/admin/icon_jia.png" alt="">
            选择商品品牌
        </div>
        <span class="area_show"></span>
        <div></div>
    </div>
    </#if>
</div>

<script>
    <#if  ($type == 1)
        $('.choose_content .btn_add').each(function () {
            if ($(this).attr('area_data') != '') {
                let type = new Map([
                    ['add_goods', ['已选择商品：', '件商品']],
                    ['add_cate', ['已选择分类：', '个分类']],
                    ['add_sort', ['已选择分类：', '个分类']],
                    ['add_label', ['已选择标签：', '个标签']],
                    ['add_brand', ['已选择品牌：', '个品牌']],
                ])
                let action = type.get($(this).data('type'))
                let area_length = util.count(($(this).attr('area_data') || '').split(','));
                $(this).parent().find('.area_show').html(action[0] + area_length + action[1])
            } else {
                $(this).parent().find('.area_show').html('');
            }
        })
    </#if>
    $('.choose_content .btn_add,.choose_content .edit').click(function () {
        let type = new Map([
            ['add_goods', ['选择商品', '/admin/public/purchase/goods/list?record_select_value=', '#record_select_value', '945px']],
            ['add_cate', ['选择平台分类', '/admin/frame/goods/cat/select?&cat_ids=', 'input[name="cat_id[]"]:checked', '580px']],
            ['add_sort', ['选择商家分类', '/admin/frame/goods/sort/select?&sort_ids=', 'input[name="sort_id[]"]:checked', '580px']],
            ['add_label', ['选择商品标签', '/admin/frame/goods/label/select?select_ids=', '#select_ids', '750px']],
            ['add_brand', ['选择商品品牌', '/admin/frame/goods/brand/select?select_ids=', '#select_ids', '750px']],
        ])
        let action = type.get($(this).data('type'))
        let goods_area_data = $(this).parents('.box_list').find('.btn_add').attr('area_data')
        let _this = $(this);
        console.log(action)
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery,
                layer = layui.layer;
            layer.open({
                type: 2,
                title: [action[0], 'text-align:center;padding: 0px;'],
                offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                ,
                area: [action[3], '440px'],
                content: action[1] + goods_area_data //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                ,
                btn: ['确定', '取消'],
                btnAlign: 'r' //按钮居右
                ,
                shade: [0.3, '#000'] //显示遮罩透明度和颜色
                ,
                yes: function (index, layero) { //保存按钮的回调
                    <#if  ($type == 1)
                        if (_this.data('type') == 'add_cate' || _this.data('type') == 'add_sort') {
                            var sort_array = [];
                            var iframe = layer.getChildFrame('body', index);
                            iframe.find(action[2]).each(function () {
                                sort_array.push($(this).val());
                            });
                            if (util.count(sort_array) > 0) {
                                _this.parent().find(".area_show").html('已选择分类：' + sort_array.length + '个分类')
                            } else {
                                _this.parent().find(".area_show").html('')
                            }
                            _this.parents('.box_list').find('.btn_add').attr('area_data', sort_array.join(','));
                        } else {
                            var iframe = layer.getChildFrame('body', index);
                            var select_ids = iframe.find(action[2]).val();
                            if (select_ids) {
                                _this.parent().find(".area_show").html(
                                    _this.data('type') == 'add_label' ? '已选择标签：' + select_ids.split(',').length + '个标签' : (_this.data('type') == 'add_brand' ? '已选择品牌：' + select_ids.split(',').length + '个品牌' : '已选择商品：' + select_ids.split(',').length + '件商品')
                                )
                            } else {
                                _this.parent().find(".area_show").html('')
                            }
                            _this.parents('.box_list').find('.btn_add').attr('area_data', select_ids);
                        }
                    </#if>
                    <#if  ($type == 2)
                        if (_this.data('type') == 'add_goods') {
                            var iframe = layer.getChildFrame('body', index);
                            var checkedId = iframe.find(action[2]).val();
                            util.ajax_json('/admin/public/purchase/goods/list', function (response) {
                                var list = response.content;
                                var html = '';
                                for (var i in list) {
                                    list[i].prd_desc = list[i].prd_desc == undefined ? '' : list[i].prd_desc;
                                    html += '<tr>' +
                                        '        <td>' +
                                        '            <div class="goods_img">' +
                                        '                <img src="' + list[i].goods_img + '" />' +
                                        '            </div>' +
                                        '            <div class="goods_info">' +
                                        '                <div class="goods_name">' + list[i].goods_name + '</div>' +
                                        '                <div class="goods_spec">' + list[i].prd_desc + '</div>' +
                                        '            </div>' + '<td>￥' + list[i].shop_price + '</td>' +
                                        '<td>' + list[i].goods_number + '</td>' +
                                        '<td><a href="##" goods_id=' + list[i].goods_id + ' class="del">删除</a></td>'
                                    '        </td>';
                                    html += '</tr>';
                                }
                                $('.label_tbody').html(html);
                                check_goods_area_height()
                                $('.goods_table').show();
                                _this.parents('.box_list').find('.btn_add').attr('area_data', checkedId)
                            }, { select_id: checkedId });
                        } else if (_this.data('type') == 'add_cate') {
                            var iframe = layer.getChildFrame('body', index);
                            var cat_array = [];
                            $(".cat_table tr:gt(0)").remove();
                            var ul = $('<ul>');
                            ul.addClass('cat_ul clearfix');
                            var html = `<div>
                                        示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span><span class="third_cat">三级分类</span>
                                    </div>`
                            iframe.find(action[2]).each(function () {
                                cat_array.push($(this).val());
                            });
                            iframe.find('.cate_li').each(function () {
                                var firstCheck = $(this).find('.first_cate').find('input[type="checkbox"]');
                                var secondCheck = $(this).find('.second_cate').children().children('span').find('input[type="checkbox"]');
                                var thirdCheck = $(this).find('.third_cate').children().find('input[type="checkbox"]');
                                thirdCheck.each(function () {
                                    if ($(this).parent().parent().prev().find('input[type="checkbox"]').is(':checked')) {
                                        return;
                                    } else if ($(this).is(':checked')) {
                                        var el = $('<li class="third_cat">');
                                        el.text($(this).next().text());
                                        ul.append(el)
                                        return;
                                    }
                                })
                                if (firstCheck.is(':checked') === true) {
                                    var el = $('<li class="first_cat">');
                                    el.text(firstCheck.next().text());
                                    ul.append(el)
                                    return;
                                } else if (firstCheck.is(':checked') === false) {
                                    secondCheck.each(function () {
                                        if ($(this).is(':checked')) {
                                            var el = $('<li class="second_cat">');
                                            el.text($(this).next().text());
                                            ul.append(el)
                                            return;
                                        }
                                    })
                                }
                            });
                            $('.cat_table tr:first-child').after($('<tr>').append(html).append(ul));
                            if ($('.cat_table ul li').length === 0) {
                                $('.cat_table').hide();
                            } else {
                                $('.cat_table').show();
                            }
                            _this.parents('.box_list').find('.btn_add').attr('area_data', cat_array)
                        } else if (_this.data('type') == 'add_sort') {
                            var iframe = layer.getChildFrame('body', index);
                            var cat_array = [];
                            $(".sort_table tr:gt(0)").remove();
                            var ul = $('<ul>');
                            ul.addClass('cat_ul clearfix');
                            var html = `<div>
                                        示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span>
                                    </div>`
                            iframe.find(action[2]).each(function () {
                                cat_array.push($(this).val());
                            });
                            iframe.find('.sort_li').each(function () {
                                var firstCheck = $(this).find('.first_sort_cate').find('input[type="checkbox"]');
                                var secondCheck = $(this).find('.second_sort_cate').children().children('span').find('input[type="checkbox"]');
                                if (firstCheck.is(':checked') === true) {
                                    var el = $('<li class="first_cat">');
                                    el.text(firstCheck.next().text());
                                    ul.append(el)
                                    return;
                                } else if (firstCheck.is(':checked') === false) {
                                    secondCheck.each(function () {
                                        if ($(this).is(':checked')) {
                                            var el = $('<li class="second_cat">');
                                            el.text($(this).next().text());
                                            ul.append(el)
                                            return;
                                        }
                                    })
                                }
                            });
                            $('.sort_table tr:first-child').after($('<tr>').append(html).append(ul));
                            if ($('.sort_table ul li').length === 0) {
                                $('.sort_table').hide();
                            } else {
                                $('.sort_table').show();
                            }
                            _this.parents('.box_list').find('.btn_add').attr('area_data', cat_array)
                        }
                    </#if>
                    layer.close(index);
                },
                btn2: function (index) {
                    //按钮取消的回调
                }
            });
        });
    })

    <#if  ($type == 2)
    function check_goods_area_height() {
        let goods_table = $('.goods_table').outerHeight();
        if (goods_table > 300) {
            $('.goods_area').css({
                'height': '300px',
                'overflow-y': 'scroll'
            })
            $('.choose_goods').css({
                'margin-bottom': '10px'
            })
        } else {
            $('.goods_area').css({
                'height': 'auto',
                'overflow-y': 'auto'
            })
        }
        if (goods_table < 50) {
            $('.choose_goods').css({
                'margin-bottom': '0'
            })
        }
    }

    $("body").on('click', '.del', function () {
        var del_goods_id = $(this).attr('goods_id');
        console.log(del_goods_id);
        var goods = $('[data-type="add_goods"]').attr('area_data');
        if (isNaN(goods)) {
            var goods_array = goods.split(',');
            for (var i = 0; i < goods_array.length; i++) {
                if (goods_array[i] == del_goods_id) {
                    goods_array.splice(i, 1);
                    break;
                }
            }
            $('[data-type="add_goods"]').attr('area_data', goods_array.join(','));
        }
        else {
            $('[data-type="add_goods"]').attr('area_data', '')
        }
        $(this).parent().parent().remove();
        if ($('.goods_table tr').length == 1) {
            $('.goods_table').hide();
        }
        check_goods_area_height()
    });
    $("body").on('click','.del_cat',function(){
        $(this).parents('.box_list').find('.btn_add').attr('area_data','')
        $('.cat_table').find('tr').eq(1).remove();
        $('.cat_table').hide();
    })
    $("body").on('click','.del_sort_cat',function(){
        $(this).parents('.box_list').find('.btn_add').attr('area_data','')
        $('.sort_table').find('tr').eq(1).remove();
        $('.sort_table').hide();
    })
    check_goods_area_height()
    function onSortload(type){
        if(type == 'sort'){
           var element = $('[data-type="add_sort"]').attr('area_data')
           var link = '/admin/frame/goods/sort/select'
           var element2 = '.sort_table'
        } else {
            var element = $('[data-type="add_cate"]').attr('area_data')
           var link = '/admin/frame/goods/cat/select'
           var element2 = '.cat_table'
        }
        var sort_id = element;
        var ul = $('<ul>');
            ul.addClass('cat_ul clearfix');
            var html= `<div>
                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span>
                       </div>`
        util.ajax_json(link, function(response){
            let sort_list = response.content.sort_list;
            console.log(sort_list)
            sort_list.forEach(element => {
                if(element.checked && element.level === 0){
                    var el = $('<li class="first_cat">');
                    el.text(element.sort_name + '('+ element.sort_goods_num +')');
                    ul.append(el)
                    return false;
                } else {
                    element.child.forEach(elem => {
                        if(elem.checked && elem.level === 1){
                            var el = $('<li class="second_cat">');
                            el.text(elem.sort_name + '('+ elem.sort_goods_num +')');
                            ul.append(el)
                            return false;
                        }
                    })
                }
            });
        },{type:1,sort_ids:sort_id});
        if(sort_id){
            $(element2 + ' tr:first-child').after($('<tr>').append(html).append(ul));
            $(element2).show()
        }else{
            $(element2).hide()
        }
    }
    onSortload();
    onSortload('sort');
    </#if>
</script>