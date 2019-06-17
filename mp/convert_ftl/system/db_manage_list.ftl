<#include ("system.header")
<link href="/css/admin/goods_list.css?v=1.0.6" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<style type="text/css">

    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    
    .order-info-li .fl2{
        width:90px;
        float: left;
        margin-top:5px;
    }
    .order-info-li .fl2 input{
        width:16px;
        float: left;
        margin-top:-5px;
        margin-left: 20px;
    }

    .sql_text{
        width: 600px;
        height: 300px;
    }
    
</style>
<ul id="tab" class="nav nav-tabs">
    <li><a href="#" data-toggle="tab" onclick="location.href = '/system/db/manage';">数据库更新</a></li>
    <li class="active"><a href="#" data-toggle="tab" onclick="location.href = '/system/db/manage/list';">数据库更新日志</a></li>
    <li ><a href="" data-toggle="tab" onclick="location.href ='/system/db/manage/sql/option';">数据查询</a></li>
</ul>
<div style="min-width: 1090px;">
    <div class="order-container">
        <form action="/system/db/manage/list" method="post" id="form1">
            <div class="order-info">
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl" style="width: auto;">
                            <span>迭代版本号</span>
                            <input type="text" name="version" placeholder="请输入版本号" value="${post_data['version']!}">
                        </div>
                        <div class="fl">
                            <span>数据库类型</span>
                            <select name="db_type">
                                <option value="">全部</option>
                                <#list ($db_type_list as $db_type_key => $db_type_name)
                                <option value="${db_type_key!}" <#if ($post_data['db_type'] == $db_type_key) selected </#if>>${db_type_name!}</option>
                                </#list>
                            </select>
                        </div>
                        <button class="btn-choose">查询</button>
                    </li>
                </ul>
            </div>
            <div class="box panel main-table">
                <div class="layer">
                    <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                        <thead>
                        <tr>
                            <th>版本号</th>
                            <th>更新类型</th>
                            <th>执行结果</th>
                            <th>总更新条数</th>
                            <th>成功更新条数</th>
                            <th>IP</th>
                            <th>更新时间</th>
                        </tr>
                        </thead>
                        <#list ($data_list as $item)
                            <tr style="text-align:center;" record_id="${item->record_id!}">
                                <td class="version">${item->version!}</td>
                                <td class="db_type" db_type="${item->db_type!}">${item->db_type_name!}</td>
                                <td class="option_rst" ><a href="/system/db/manage/detail?record_id=${item->record_id!}" style="color: #3276b1;">查看详情</a></td>
                                <td>${item->total_num!}</td>
                                <td>${item->success_num!}</td>
                                <td>${item->ip!}</td>
                                <td>${item->in_time!}</td>
                            </tr>
                        </#list>
                    </table>
                
                    {{ csrf_field()!}
                    <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                        <tr>
                            <td>
                            </td>
                            <td align="right">
                                <table width="100%" border="0" class="tb_paging">
                                    <tr>
                                        <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                            <a href="#"
                                               onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                            <a href="#"
                                               onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                            <a href="#"
                                               onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">
                                                {{ trans("system/common.page.next_page")!}</a>
                                            <a href="#"
                                               onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a>
                                            <input id="page" name="page" type="text"
                                                   value="${data_list->currentPage()!}" size="5"
                                                   onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="modal fade" id="modal_see" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">{{ trans("system/index.feedback_modal.close")!}</span></button>
                <h4 class="modal-title" id="myModalLabel">更新结果</h4>
            </div>
            <div class="modal-body">
                <form class="form needcheck">
                    <input type="hidden" name="log_id" value="0" style="border: 1px;">
                    <div class="row text-left log_detail">
                        
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">{{ trans("system/index.feedback_modal.close")!}</button>
                
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
    $("select[name='db_type']").change(function() {
        $("#form1").submit();
    });

    $(".btn-choose").click(function() {
        $("#form1").submit();
    });

    $(".see_detail").click(function () {
        var option_rst = $(this).attr('option_rst');
        option_rst = eval('('+option_rst+')');
        var html = '<table cellspacing="1" cellpadding="3"  class="table" id="main-table">';
        html += '<thead><tr>'
        html += '<th style="padding: 8px 4px;width: 34px;">序号</th>';
        html += '<th>内容</th>';
        html += '</tr></thead>';
        for (var i = 0; i < option_rst.length; i++) {
            html += '<tr>';
            html += '<td>'+(i+1)+'</td>';
            html += '<td>';
            if (option_rst[i] instanceof Array) {
                for (var j = 0; j < option_rst[i].length; j++) {
                    if (j < option_rst[i].length-1) {
                        html += '<p style="margin-bottom: 2px;border-bottom: 1px solid #ddd;">'+option_rst[i][j]+'</p>';
                    }else{
                        html += '<p style="margin-bottom: 2px;">'+option_rst[i][j]+'</p>';
                    }
                }
            }else{
                html += option_rst[i];
            }
            html +='</td></tr>';
        }
        html += '</table>';
        $(".log_detail").html(html);
    });
</script>

<#include ("system.footer")