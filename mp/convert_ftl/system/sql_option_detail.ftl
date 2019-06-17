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
    <li><a href="#" data-toggle="tab" onclick="location.href = '/system/db/manage/list';">数据库更新日志</a></li>
    <li ><a href="" data-toggle="tab" onclick="location.href ='/system/db/manage/sql/option';">数据查询</a></li>
    <li class="active"><a href="" data-toggle="tab">更新日志详情</a></li>
</ul>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="box panel main-table">
            <div class="layer">
                <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                    <thead>
                    <tr>
                        <th width="10%">数据库名称</th>
                        <th>SQL语句</th>
                        <th width="10%">执行结果</th>
                        <th width="10%">错误原因</th>
                    </tr>
                    </thead>
                    <#list ($data_list as $item)
                        <tr style="text-align:center;" record_id="${item->id!}">
                            <td>${item->dbName!}</td>
                            <td>${item->sql!}</td>
                            <td><#if ($item->result == 1) SUCCESS <#else> FAILED </#if></td>
                            <td><#if (!empty($item->msg)) ${item->msg!} <#else> 无 </#if></td>
                        </tr>
                    </#list>
                </table>
            </div>
        </div>
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
<#include ("system.footer")