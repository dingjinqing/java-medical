<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css" />
<style type="text/css">
    .tb_paging tr td {
        border: none;
    }

    .tb_paging {
        margin-top: 10px;
    }

    #page {
        width: 80px;
        padding-left: 0;
    }

    .search_info {
        padding: 10px 0;
    }

    .btn_excel {
        background: #5a8bff;
        color: #fff;
        border: 1px solid #5a8bff;
        margin-left: 15px;
    }
    .no_data_style{
        margin-top: 10px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">测评</span><span> / 反馈列表</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">

        <form action="/admin/market/assess/feedbacklist" method="post" id="form1">
            {{csrf_field()!}
            <div class="search_info">
                <ul class="clearfix">
                    <li class="clearfix">
                        <input name="nav" value="${options['nav']!}" hidden>
                        <input type="hidden" name="excel" value="" class="excel">

                        <div class="fl" style="width: 520px">
                            <span>提交时间：</span>
                            <input type="text" name="start_time" placeholder='选择日期'
                                value="${options['start_time']!}" onclick="picker();" autocomplete="off" />
                            至
                            <input type="text" name="end_time" placeholder='选择日期'
                                value="${options['end_time']!}" onclick="picker();" autocomplete="off" />
                        </div>
                        <div class="fl">
                            <span>提交人昵称：</span>
                            <input type="text" name="username" placeholder='请输入提交人昵称' value="${options['username']!}" >
                        </div>
                        <div class="fl btn_group">
                            <a href="##" class="btn_search">筛选</a>
                            <a href="##" class="btn_excel">导出表格</a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content_table clearfix">
                <table width="100%">
                    <thead>
                        <tr>
                            <td>提交人昵称</td>
                            <td>用户手机号</td>
                            <td>提交时间</td>
                            <td>测试结果</td>
                            <td>操作</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#list ($data_list as $item)
                        <tr>
                            <td>${item->username!}</td>
                            <td>${item->mobile!}</td>
                            <td>${item->in_time!}</td>
                            <td>${item->result!}</td>
                            <td><a href="/admin/market/assess/feedbackinfo?record_id=${item->id!}">查看反馈结果</a></td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <#include ('admin.jump_page_admin')
            </div>
        </form>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    $(".btn_search").click(function () {
        $("#form1").submit();
    });
    $('.btn_excel').click(function () {
        $('.excel').val('export');
        $('#form1').submit();
        $('.excel').val('');
    })
    function picker() {
        return WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' });
    }

</script>
<script type="text/javascript">
    getPowerInfo('main_config','assess','sub_4','测评',0);
</script>