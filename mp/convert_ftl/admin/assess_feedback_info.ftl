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

    .btn_execl {
        background: #5a8bff;
        color: #fff;
        border: 1px solid #5a8bff;
        margin-left: 15px;
    }

    .content_table {
        margin-top: 0;
    }

    .user_table_info tbody>tr:first-child>td {
        border-top: none;
    }

    .user_info thead span+span {
        margin-left: 40px;
    }

    .user_info {
        margin-bottom: 15px;
    }

    .table_top {
        text-align: center;
        padding: 10px 0;
        border: 1px solid #eee;
        border-bottom: none;
        background-color: #f5f5f5;
    }

    .content_table thead td {
        background-color: #fff !important;
    }

    .content_table {
        padding: 12px;
    }
</style>
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span>测评</span> / <span>反馈列表</span> / <span style="color: #666;">反馈详情</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">

        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <div class="content_table clearfix">
                <div class="user_info">
                    <h3 class="table_top">
                        用户信息
                    </h3>
                    <table width="100%">
                        <thead>
                            <tr>
                                <td width="100%" style="text-align: left">
                                    <span>ID:${record_info['id']!}</span>
                                    <span>昵称：${record_info['username']!}</span>
                                    <span>提交时间:${record_info['in_time']!}</span>
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td style="padding: 0;border: none">
                                    <table width="100%" class="user_table_info">
                                        <tbody>
                                            <tr>
                                                <td>真实姓名</td>
                                                <td>${user_info['real_name']!}</td>
                                                <td>手机号</td>
                                                <td>${record_info['mobile']!}</td>
                                                <td>身份证号</td>
                                                <td>${user_info['cid']!}</td>
                                            </tr>
                                            <tr>
                                                <td>性别</td>
                                                <td>${user_info['sex_name']!}</td>
                                                <td>生日</td>
                                                <td>${user_info['birthday']!}</td>
                                                <td>婚姻状况</td>
                                                <td>${user_info['marital_status_name']!}</td>
                                            </tr>
                                            <tr>
                                                <td>教育程度</td>
                                                <td>${user_info['education_name']!}</td>
                                                <td>所在行业</td>
                                                <td>${user_info['industry_info_name']!}</td>
                                                <td>所在地</td>
                                                <td>${user_info['address']!}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div style="margin-bottom: 15px;">
                    <h3 class="table_top">
                        测评结果
                    </h3>
                    <table width="100%">
                        <thead>
                            <tr>
                                <td>编号</td>
                                <td>测试结果</td>
                                <td>测试奖励</td>
                                <td>${record_info['judge_title']!}</td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${record_info['result_id']!}</td>
                                <td>${record_info['result']!}</td>
                                <td>${record_info['reward_name']!}</td>
                                <td> <span style="color: red">${record_info['judge_name']!}</span> </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <div>
                    <h3 class="table_top">
                        题目列表
                    </h3>
                    <table width="100%">
                        <thead>
                            <tr>
                                <td>编号</td>
                                <td>题目名称</td>
                                <td>选项</td>
                                <#if ($record_info['assess_judge_type'] == 1)
                                <td>得分</td>
                                </#if>
                            </tr>
                        </thead>
                        <tbody>
                            <#list ($topic_record_list as $key => $topic_record)
                            <tr>
                                <td>${key+1!}</td>
                                <td>${topic_record->topic_title!}</td>
                                <td>${topic_record->option_name!}</td>
                                <#if ($record_info['assess_judge_type'] == 1)
                                <td><span style="color: red">${topic_record->topic_point!}</span></td>
                                </#if>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
    </div>
</div>
<#include "/admin/footer.ftl">

<script>
    /*getPowerInfo('main_config','pre_sale','sub_4','定金膨胀',0);*/
</script>