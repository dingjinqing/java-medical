<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css?v=1.2.5" type="text/css" />
<link rel="stylesheet" href="/css/admin/score_cfg.css?v=1.0.6" type="text/css" />
<link href="/css/admin/goods_decorate.css?v=1.1.9" rel="stylesheet">
<style type="text/css">
    .save:hover {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }

    .save:focus {
        background-color: #447af9 !important;
        border-color: #447af9 !important;
        text-decoration: none
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <span>会员管理 / </span>
        <span style="color: #666;">积分管理</span>
    </div>
    <div class="main-container fix_every_footer">
        <form action ="/admin/user/score/add" id ='form1' method="post">
                {{ csrf_field()!}
            <input type="hidden" id="page_id" name="page_id" value="${page->page_id!}">
            <input name="nav" type="hidden" value="${nav!}">
            <div class="list_nav">
                <ul class="clearfix">
                    <li <#if  ($nav==0) class="actives" </#if>>
                        <a href="/admin/user/score/cfg?top_index=5">积分规则设置</a>
                    </li>
                    <li <#if  ($nav==1) class="actives" </#if>>
                        <a href="/admin/user/score/add?top_index=5&nav=1">前端展示设置</a>
                    </li>
                </ul>
            </div>
            <div class="content_container">
                <div class="middle">
                    <div class="top">
                        <div class="text_warn">
                            <img src="http://${image_domain!}/image/admin/notice_img.png">
                            <span>请在此设置小程序前端“我的积分”页面展示内容</span>
                        </div>
                    </div>
                    <div class="bottom">
                        <div class="left">
                            <img src="http://${image_domain!}/image/admin/score_qd.png">
                            <div class="show_area area_two  <#if (!$page->page_id) hide </#if>">
                                <div>已选择模板:<a style="color: #5a8bff;padding-left:5px;"  href='http://${image_domain!}/admin/manage/decorate/page?id=${page->page_id!}' target="_blank">${page->page_name!}</a>
                                </div>
                            </div>

                            <div class="show_area area_one <#if ($page->page_id) hide </#if>">
                                <h1>自定义内容区域</h1>
                                <p>可在右侧选择商品页模板</p>
                                <p>未添加内容时,不显示此模块内容</p>
                            </div>
                            <div class="score_get">
                                积分收支明细
                            </div>
                            <div class="score_set">
                                <div>连续签到2天，获得1积分</div>
                                <div>2019-04-29 12:00:00</div>
                                <div>+1</div>
                            </div>
                        </div>
                        <div class="right">
                            <div class="score_r">
                                <div class="r_top">
                                    <span>积分说明</span>
                                    <span>用于前端展示，向用户说明店铺积分相关规则</span>
                                </div>
                                <div class="r_bottom">
                                    <div class="add_des">编写积分说明</div>
                                </div>
                            </div>
                            <div class="score_r">
                                <div class="r_top">
                                    <span>自定义内容</span>
                                    <span>用于向用户推荐积分相关活动商品</span>
                                </div>
                                <div class="r_bottom">

                                    <#if ($page->page_id)
                                    <span class="template-show" style="display: block">
                                        <span class="template_name">${page->page_name!}</span>
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""
                                            class="img-delete1">
                                    </span>
                                    <#else>
                                    <span class="template-show">
                                        <span class="template_name"></span>
                                        <img src="http://${image_domain!}/image/admin/icon_delete.png" alt=""
                                            class="img-delete1">
                                    </span>
                                    </#if>

                                    <span class="choose_template">选择模板</span>
                                    <a class="refresh_temp add_goods">刷新</a>
                                    <a class="add_goods" href="/admin/manage/decorate/page" target="_blank">添加模板</a>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer">
                <button type="submit">保存</button>
           </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<div id="choose_page" style="display: none">
    <div class="goods-box-edit">
        <div class="goods-edit-basic goods_tem">
            <div class="goods_search">
                <form action="">
                    <span style="font-size: 14px;margin-left: 10px">页面名称</span>
                    <input type="text" name="pageKeywords" value="${pageKeywords!}" placeholder="请输入页面名称" />
                    <span style="font-size: 14px;margin-left: 10px">页面分类</span>
                    <select name="page_cat_id" id="page_cat_id">
                        <option value="0">请选择分类</option>
                        <#list ($page_cat_list as $k=>$name)
                        <option value="${k!}">${name!}</option>
                        </#list>
                    </select>
                    <span class="search_template">搜索</span>
                </form>
            </div>
            <table class="tb-decorate-list choose_c">
                <thead>
                    <tr>
                        <th width="25%">{{ trans("admin/shop_decorate.shop_decorate_list.page_name")!}</th>
                        <th width="30%">{{ trans("admin/shop_decorate.shop_decorate_list.create_time")!}</th>
                        <th width="20%">{{ trans("admin/shop_decorate.shop_decorate_list.is_index")!}</th>
                    </tr>
                </thead>
                <tbody class="temp_list">
                    <#list ($pageList as $item)
                    <tr data-back="true" page_id="${item->page_id!}" page_type="${item->page_type!}"
                        page_tpl_type="${item->page_tpl_type!}" page_content="${item->page_content!}">
                        <td>
                            <span class="tem_name">${item->page_name!}</span>
                        </td>
                        <td>${item->create_time!}</td>
                        <td><#if ($item->page_type)是<#else>否</#if></td>
                    </tr>
                    </#list>
                </tbody>
            </table>
            <#if (count($pageList)<=0) <div class="no_data_style"
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
</div>
</div>
<script>
    $(".fix_footer").outerWidth($('.fix_every_footer').width());
    $('.choose_template').on('click', function () {
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery,
                layer = layui.layer;
            layer.open({
                type: 1,
                title: ['选择模板', 'text-align:center; padding: 0px;'],
                offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    ,
                area: ['745px', '430px'],
                content: $('#choose_page') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    ,
                btn: ['确定', '取消'],
                btnAlign: 'r' //按钮居右
                    ,
                shade: [0.3, '#000'] //显示遮罩透明度和颜色
                    ,
                success: function (layero, index) {
                    $('.tb-decorate-list tbody tr.goods_tr_choose').each(function () {
                        $(this).removeClass('goods_tr_choose');
                        $(this).attr('data-back', 'true');
                    })
                    $(".goods_tem").show();
                },
                yes: function (index, layero) {
                    var count = 0;
                    $('.tb-decorate-list tbody tr').each(function () {
                        if ($(this).attr("data-back") == 'false') {
                            count++;
                        }
                    })
                    if (count == 0) {
                        util.mobile_alert("请选择模板!");
                        return false;
                    }
                    var tem_name = $('tr[data-back="false"]').find(".tem_name").text();
                    var page_id = $('tr[data-back="false"]').attr("page_id");
                    var link = '/admin/manage/decorate/page?id=' + page_id
                    $('#page_id').val(page_id);
                    $(".template_name").text(tem_name);
                    $(".template-show").show();
                    $('.area_one').addClass('hide');
                    $('.area_two').removeClass('hide');
                    $('.area_two a').text(tem_name);
                    $('.area_two a').attr('href',link);
                    layer.close(index);
                },
                btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    $(".img-delete1").click(function () {
        $(this).parent().hide();
        $('.area_one').removeClass('hide');
        $('.area_two').addClass('hide');
        $('#page_id').val(0);
    })
    $(".btn_save").click(function(){
        layer.ready(function () {
            layer.msg('保存成功', {time: 2000},function () {
                $("#form1").submit();
            });
        });
    })
    $('#choose_page').on('click', '.tb-decorate-list tbody tr', function () {
        var flag_back = $(this).attr('data-back');
        $('.tb-decorate-list tbody tr.goods_tr_choose').each(function () {
            $(this).removeClass('goods_tr_choose');
            $(this).attr('data-back', 'true');
        })
        if (flag_back == 'true') {
            $(this).addClass('goods_tr_choose');
            $(this).attr('data-back', 'false');
        }
    });
    $('.refresh_temp').click(function () {
        $('input[name="pageKeywords"]').val('');
        $('#page_cat_id').val(0);
        searchTemplate('',0,'刷新');
    });
    $('.search_template').click(function () {
        var keywords = $('[name="pageKeywords"]').val();
        var page_cat_id = $('#page_cat_id option:selected').val();
        searchTemplate(keywords,page_cat_id,'搜索');
    });
    function searchTemplate(keywords, page_cat_id, op_name) {
        util.ajax_json('/admin/ajax/template/refresh', function (d) {
            if (d.error) {
                util.mobile_alert(op_name + '失败');
                return false;
            } else {
                var all_page = d.content.page_list;
                var page_cat = d.content.page_cat_list;
                var html = '';
                var html1 = `<option value="0">请选择分类</option>`;
                console.log(all_page.length);
                if (all_page.length > 0) {
                    for (var i in all_page) {
                        html += `<tr data-back="true" page_id="${all_page[i].page_id}"  page_type="${all_page[i].page_type}" page_tpl_type="${all_page[i].page_tpl_type}" page_content='${all_page[i].page_content}'>
                        <td>
                        <span class="tem_name">${all_page[i].page_name}</span>
                        </td>
                        <td>${all_page[i].create_time}</td>
                        <td>${all_page[i].page_type ? '是' : '否'}</td>
                        </tr>`;
                    }
                    $('.temp_list').html(html);
                    if ($('.goods_tem').find('.no_data_style').length >= 0) {
                        $('.goods_tem').find('.no_data_style').remove();
                    }
                } else {
                    var html2 = `<div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                        <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                        </div>
                        <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                    </div>`;
                    $('.temp_list').html('');
                    if ($('.goods_tem').find('.no_data_style').length <= 0) {
                        $('.goods_tem').append(html2);
                    }
                }
                for (var i in page_cat) {
                    html1 +=
                        `<option value="${i}" ${i == page_cat_id ? 'selected' : ''}>${page_cat[i]}</option>`;
                }
                $('#page_cat_id').html(html1);
                util.mobile_alert(op_name + '成功');
            }
        }, {
            keywords: keywords,
            page_cat_id: page_cat_id
        });
    }
    
    $('.add_des').click(function () {
        $(window).attr('location',"/admin/user/score/copywriting");
    })
</script>