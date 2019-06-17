<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/goods_edit.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/order_all.css" type="text/css"/>
<link rel="stylesheet" href="/css/admin/shop_decorate.css?v=1.0.0" type="text/css"/>
<style type="text/css">
    .tb-decorate-a {
        position: relative;
    }

    .tb-decorate-a .share_span {
        padding: 15px 12px;
        border: 1px solid #eee;
        background: #fff;
        font-size: 14px;
        position: absolute;
        top: 30px;
        left: 2px;
        width: 285px;
        text-align: center;
        z-index: 9999;
        display: none;
    }

    .tb-decorate-a .share_span .share_sj {
        position: absolute;
        right: 140px;
        top: -7px;
    }

    .tb-decorate-a .share_span span {
        color: #000;
        font-weight: bold;
        font-size: 14px;
        height: 30px;
        line-height: 30px;
    }

    .tb-decorate-a .share_span .code_imgs {
        display: block;
        margin: 0 auto;
    }

    .tb-decorate-a .share_span a {
        color: #999;
        font-size: 13px;
        display: inline-block;
        height: 30px;
        line-height: 30px;
    }

    .share_link {
        padding-top: 15px;
        width: 100%;
    }

    .share_link input {
        background: #f7f7f7;
        border: 1px solid #f2f2f2;
        height: 35px;
        width: 220px;
        padding-left: 8px;
        float: left;
        font-size: 13px;
        color: #666;
    }

    .share_link button {
        float: right;
        color: #5A8BFF;
        background: #fff;
        border: none;
        height: 35px;
        line-height: 35px;
    }

    .add-child-btn:hover {
        background-color: #447af9;
    }
    .tb-decorate-list{
        width: 95%;
    }
    .tb-decorate-list>tbody>tr>td{
        padding:15px 0;
    }
    .tb-decorate-list>thead>tr>th{
        border:none;
        background: #f5f5f5;
        padding:15px 0;
    }
    .tb_paging{
        float: none;
        width: 95%;
        margin-top: 20px;
    }

</style>
<div style="min-width: 1090px;">
    <form name="formData" action="/admin/market/form/feedback/list" id="form1" method="post">
        <div class="title">
            <span><a href="/admin/market/view?top_index=4" >营销管理</a> / </span>
            <span><a href="/admin/market/form/list?top_index=4">表单统计</a> / </span>
            <span style="color: #666;">反馈列表</span>
        </div>
        <div class="main-container">
            {{--<ul class="add-child-ul">--!}
                {{--<li>--!}
                    {{--<a href="#" class="add-child-btn">新建表单</a>--!}
                {{--</li>--!}
            {{--</ul>--!}
            {{--筛选--!}
            <ul class="check_input">
                <li class="clearfix" style="padding-top: 15px">
                    {{--<div>--!}
                        {{--<span>状态</span>--!}
                        {{--<select name="state" id="">--!}
                            {{--<option value="-1">全部</option>--!}
                            {{--<option <#if ($post_data['state'] == '0')selected </#if> value="0">未发布</option>--!}
                            {{--<option <#if ($post_data['state'] == '1')selected </#if> value="1">已发布</option>--!}
                            {{--<option <#if ($post_data['state'] == '2')selected </#if> value="2">已关闭</option>--!}
                        {{--</select>--!}
                    {{--</div>--!}
                    <div>
                        <span>提交时间：</span>
                        <input type="text" placeholder="" name="start_time" value="${request['start_time']!}"
                               onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off">
                        至
                        <input type="text" placeholder="" name="end_time" value="${request['end_time']!}"
                               onclick="picker();" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off">
                    </div>
                    <div>
                        <span>提交人昵称：</span>
                        <input name="nick_name" type="text" placeholder="请输入提交人昵称" value="${request['nick_name']!}">
                        <button class="btn_searchinfo">筛选</button>
                        <button class="export">导出表格</button>
                    </div>
                </li>
            </ul>
            <div class="return-goods-box" style="padding-top: 0">
                {{ csrf_field()!}
                <input type="hidden" name="act">
                <input type="hidden" name="page_id" value="${request['page_id']!}">
                <div class="goods-box-edit">
                    <div class="goods-edit-basic">
                        <table class="tb-decorate-list">
                            <thead>
                            <tr>
                                <th width="10%">提交用户昵称</th>
                                <th width="10%">提交用户电话</th>
                                <th width="10%">提交时间</th>
                                <th width="5%">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list ($page as $item)
                                    <tr>
                                    <td><a href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0">${item->nick_name!}</a></td>
                                    <td>${item->mobile!}</td>
                                    <td>${item->create_time!}</td>
                                    <td><a href="/admin/market/form/show/feedback?submit_id=${item->submit_id!}">查看操作数据</a></td>
                                    </tr>
                                </#list>
                            </tbody>
                        </table>
                        <#if (!$page->count())
                            <table style="width: 95%;border: 1px solid #eee;position: relative;margin-top: 12px;margin-left: 10px">
                                <tr>
                                    <td style="width: 30px;height: 33px; margin: 18px auto auto auto;display: flex;justify-content: center;align-items: center;" >
                                        <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                                    </td>
                                </tr>
                                <tr>
                                    <td style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</td>
                                </tr>
                            </table>
                        <#else>
                        <table border="0" style="border:none;width: 95%;margin-left: 9px;" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$page->perPage(),'currentPage'=>$page->currentPage(),'count'=>$page->count,'total'=>$page->total(),])!}
                                    <a href="#"
                                       onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${page->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${page->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                    <a href="#"
                                       onClick="return gopage(${page->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                    <input id="page" name="page" type="text" value="${page->currentPage()!}"
                                           size="5"
                                           onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                                    <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                                </td>
                            </tr>
                        </table>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">

    function gopage(page) {
        var last_page = '${page -> lastPage()!}';
        if(page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }


    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }

    $(".btn_searchinfo").click(function () {
        $("input[name='act']").val("");
        $("#form1").submit();
    })
    $(".export").click(function () {
        $("input[name='act']").val("export_csv");
        $("#form1").submit();
    })
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','form_decoration','sub_4','表单统计',0);
</script>