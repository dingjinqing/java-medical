<#include "/admin/header.ftl">
    <link href="/css/admin/reserve_service_list.css?v=1.1.1" rel="stylesheet" type="text/css"/>
<style>
    .coupon_type{
        background: #fff;
        padding:10px 0 0;
    }
    .coupon_type ul{
        list-style:none;
        background: #f5f5f5;
        width: 97%;
        margin:0 auto;
        border:1px solid #f3f3f3;
    }
    .coupon_type ul:after{
        content: '';
        display: block;
        clear: both;
    }
    .coupon_type ul li{
        float: left;
        width: 100px;
        height: 40px;
        line-height: 40px;
        /*text-align: center;*/
        cursor: pointer;
    }
    .coupon_type ul li a{
        display: block;
        width: 100%;
        height:100%;
        color: black;
    }
    .coupon_type ul .actives{
        background: #fff;
    }

    .btn_searchinfo{
        border: 1px solid #5a8bff;
        background: #5a8bff;
        color: #fff;
        width: 85px;
        height: 30px;
        float: right;
    }
    .item-menu a{
        margin:0;
    }
</style>
<div class="goods-container">
    <div class="title">
        <span><a href="/admin/store/manage/list" style="color:  black;margin-left: 0px">门店列表（${store->store_name!}）</a> /</span>
        <span><a href="/admin/store/services/reserve/list?store_id=${store_id!}"  style="color:  #666;margin-left: 0px">服务管理</a></span>
        {{--<span> / ${store->store_name!}</span>--!}
        {{--<span>预约管理</span>/--!}
        {{--<span>分类管理</span>--!}
    </div>
    <div class="main-container">
    <div class="coupon_type">
        <ul>
            <li class="normal_type ">
                <a href="/admin/store/services/reserve/list?store_id=${store_id!}&top_index=6">预约管理</a>
            </li>
            <li class="fenlie_type actives">
                <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/service/list?store_id=${store_id!}&top_index=6" class="edition_type">服务管理</a>
            </li>
            <li class="fenlie_type">
                <a href="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" link="/admin/store/services/technician/list?store_id=${store_id!}&top_index=6" class="edition_tech" title="技师管理">${technician_title!}管理</a>
            </li>
            <li class="give_to_sb">
                <a href="/admin/store/services/comment/list?store_id=${store_id!}&top_index=6">评价管理</a>
            </li>
        </ul>
    </div>
    <form action="" method="post" id="form5">
    {{csrf_field()!}
        <input type="hidden" name="act" value="<#if ($input['act']!='')${input['act']!}<#else> add </#if>">
        <input type="hidden" name="cat_id" value="${input['cat_id']!}">
        <input type="hidden" name="store_id" value="${store_id!}">
        <div class="list-bottom" >
            <div class="clearfix top-banner" style="margin-left: 0;padding-top: 10px">
                <a href="/admin/store/services/service/list?store_id=${store_id!}&top_index=6"  class="create-good">服务列表</a>
                <a href="/admin/store/services/service/type?store_id=${store_id!}&top_index=6"  class="create-good actiove">分类管理</a>
                <a href="/admin/store/services/service/add?store_id=${store_id!}&top_index=6"  class="create-good" target="_blank">添加服务</a>
            </div>
            <div class="create_new clearfix">
                <input type="text" name="cat_name" placeholder="请输入分类名称" value="${input['cat_name']!}">
                <a href="javascript:onSubmit();">保存</a>
                <#if ($msg!='') ${msg!} </#if>

                <input type="text" class="btn_search_type" onkeydown="if (event.keyCode==13 || event.which==13) search(this.value);" name="keywords" placeholder="请输入分类查询">
                <button class="btn_searchinfo">查询</button>
            </div>
            <div class="type_table">
                <table width="100%">
                    <thead>
                    <tr>
                        <td width="150px">分类名</td>
                        <td width="300px">创建时间</td>
                        <td width="200px">操作</td>
                    </tr>
                    </thead>
                    <tbody>
                    <#list ($data_list as $item)
                        <tr class="cat-${item->cat_id!}">
                            <td width="150px" class="cat_name">${item->cat_name!}</td>
                            <td width="300px">${item->create_time!}</td>
                            <td width="200px">
                                <a href="javascript:edit(${item->cat_id!})" cat_name="${item->cat_name!}">编辑</a>
                                <a href="javascript:del_cat(${item->cat_id!})" cat_id="${item->cat_id!}" class="btn_delete">删除</a>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
            <div class="clearfix" style="padding: 0 10px;">
              <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </form>
    </div>
</div>
<script type="text/javascript">
    //分页
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#act").val("");
        $("#page").val(page);
        $("#form5").submit();
    }

    function  onSubmit(){
        var cname = $("input[name='cat_name']").val();
        if(cname != '' && cname != null && cname != undefined) {
            $("#page").val(1);
            $("#form5").submit();
        } else {
            util.mobile_alert("分类名称不能为空");
            return false;
        }
    }
    function edit(cat_id){
        var cat_name = $(".cat-"+cat_id).find(".cat_name").text();
        $('input[name="cat_name"]').val(cat_name);
        $('input[name="cat_id"]').val(cat_id);
        $('input[name="act"]').val('edit');
        $('input[name="cat_id"]').focus();

    }

    $(".btn_delete").click(function () {
        var _this = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该分类吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                $('input[name="act"]').val('delete');
                var cat_id = _this.attr('cat_id');
                $('input[name="cat_id"]').val(cat_id);
                $("#form5").submit();
                layer.close(index);
            });
        });
    })



    window.search = function(keywords){
        if(keywords==''){
            util.mobile_alert('请输入分类查询');
            return;
        }
        $("#page").val(1);
        $("#form5").submit();
    };
</script>
<#include "/admin/footer.ftl">
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
<script type="text/javascript">
    getPowerInfo('main_config','service','sub_5','服务管理',0);
</script>

