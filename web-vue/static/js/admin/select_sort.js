
$(function(){
    //选择分类
    $('.add_sort,.edit_sort_cls').on('click',function(){
        var count = $(this).attr("cat_count");
        if(count==0){
            util.mobile_alert('无可选分类');
        }
        else {
            layui.use('layer', function () { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer;
                var checkedId1 = $('input[name="sort_ids"]').val()
                console.log(checkedId1)
                layer.open({
                    type: 2
                    , title: ['添加商家分类', 'text-align:center;padding: 0px;']
                    , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    , area: ['580px','540px']
                    , content: '/admin/frame/goods/sort/select?type=3&sort_ids='+checkedId1 //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    , btn: ['确定', '取消']
                    , btnAlign: 'r' //按钮居右
                    , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    , success:function(layero,index){

                    }
                    , yes: function (index, layero) { //保存按钮的回调
                        var cat_array = [];
                        var iframe = layer.getChildFrame('body', index);
                        var body = layer.getChildFrame('body', index);
                        var checkedId = iframe.find('#record_select_value').val();
                        $(".sort_table tr:gt(0)").remove();
                        var ul = $('<ul>');
                        ul.addClass('cat_ul clearfix');
                        var html= `<div>
                                        示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span>
                                    </div>`
                        iframe.find('input[name="sort_id[]"]:checked').each(function () {
                            cat_array.push($(this).val());
                        });
                        iframe.find('.sort_li').each(function(){
                            var firstCheck = $(this).find('.first_sort_cate').find('input[type="checkbox"]');
                            var secondCheck = $(this).find('.second_sort_cate').children().children('span').find('input[type="checkbox"]');
                            if(firstCheck.is(':checked') === true){
                                var el = $('<li class="first_cat">');
                                el.text(firstCheck.next().text());
                                ul.append(el)
                                return;
                            } else if (firstCheck.is(':checked') === false) {
                                secondCheck.each(function(){
                                    if($(this).is(':checked')){
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
                        $("input[name='sort_ids']").val(cat_array);

                        layer.close(index);
                    }, btn2: function (index, layero) {
                        //按钮取消的回调


                    }
                });
            });
        }
    });
    $("body").on('click','.del_sort_cat',function(){
        $('input[name="sort_ids"]').val('');
        $('.sort_table').find('tr').eq(1).remove();
        $('.sort_table').hide();
    })
    function onSortload(){
        var sort_id = $('input[name="sort_ids"]').val();
        console.log(sort_id)
        var ul = $('<ul>');
            ul.addClass('cat_ul clearfix');
            var html= `<div>
                            示例：<span class="first_cat">一级分类</span><span class="second_cat">二级分类</span>
                       </div>`
        util.ajax_json('/admin/frame/goods/sort/select', function(response){
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
            $('.sort_table tr:first-child').after($('<tr>').append(html).append(ul));
            $('.sort_table').show()
        }else{
            $('.sort_table').hide()
        }
    }
    onSortload();
})
