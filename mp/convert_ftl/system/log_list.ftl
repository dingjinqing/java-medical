<#include ("system.header")
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<link rel="stylesheet" href="/css/system/table_list.css" type="text/css"/>
<style type="text/css">
    .renew label{
        margin-left: 4%;
    }
    .renew input[type='text']{
        width: 55%;
    }
    .renew input[type='date']{
        width: 55%;
    }

    .form-control{
        width: 160px;
        display: inline-block;
        margin-left: 14px;
    }
    .panel-body  input{
        width: 160px;
        display: inline-block;
        margin-left: 20px;
        height: 32px;
        padding: 6px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        color: #555;
        background-color: #fff;
        background-image: none;
        border: 1px solid #ccc;
    }

    .panel-body select option:first-child {
        color: black;
    }
    .btn_search{
        cursor: pointer;
        color: #fff;
        background-color: #86a7cb;
        border-color: #86a7cb;
        margin-bottom: 0;
        font-weight: 400;
        text-align: center;
        white-space: nowrap;
        padding: 3px 12px;
        font-size: 13px;
        line-height: 1.42857143;
        border-radius: 2px;
        width: 60px;
        margin-left: 10px;
    }
    .fl{
    	margin-right: 10px;
    }

    a{
    	color: #86a7cb;
    }

    .feedback_content span{
        word-break: break-all;
    }

    .feedback_content label{
        text-align: right;
    }


</style>
<ul id="tab" class="nav nav-tabs">

    <li><a href="#" data-toggle="tab" onclick="location.href = '/system/operation/list';">操作日志</a></li>
    
    <li class="active"><a href="#" data-toggle="tab"onclick="location.href = '/system/log/list';">错误日志</a></li>
   
    {{--<#if ($nav_type==3)--!}
    {{--<li class="active"><a href="#" data-toggle="tab" url="#">店铺编辑</a></li>--!}
    {{--</#if>--!}
</ul>

<form action="/system/log/list" name="form1" id="form1" method="post">
    <input type="hidden" name="page" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body">
        	<div class="fl">
                <span>店铺ID：</span>
                <input type="text" name="shop_id" placeholder="请输入店铺ID" value="${param['shop_id']!}">
            </div>
            <div class="fl">
                <span>店铺名称：</span>
                <input type="text" name="shop_name" placeholder="请输入店铺名称" value="${param['shop_name']!}">
            </div>
            <div class="fl">
                <span>错误等级：</span>
                <select name="error_level">
                	<option value="-1">全部</option>
                	<#list ($error_level as $ek=>$ev)
                	<option <#if ($param['error_level'] == $ek && isset($param['error_level'])) selected </#if> value="${ek!}">${ev!}</option>
                	</#list>
                </select>
            </div>
            <div class="fl">
                <span>来源：</span>
                <select name="error_source">
                	<#list ($source_list as $k=>$v)
                	<option <#if ($param['error_source'] == $k) selected </#if> value="${k!}">${v!}</option>
                	</#list>
                </select>
            </div>
                    
            <button  class="btn_search" type="button">{{ trans("system/common.operation.search")!}</button>
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table">
                <thead>
                <tr>
                    <th width="5%">店铺ID</th>
                    <th width="7%">店铺名称</th>
                    <th width="7%">错误等级</th>
                    <th width="7%">错误来源</th>
                    <th width="7%">处理状态</th>
                    <th width="7%">处理人</th>
                    <th width="7%">处理时间</th>
                    <th width="7%">动作名称</th>
                    <th width="7%">ip</th>
                    <th width="7%">操作</th>
                </tr>
                </thead>
                <#list ($data_list as $item)
                    <tr style="text-align:center;" log_id="${item->log_id!}" remark="${item->remark!}" >
                        <td width="5%">${item->shop_id!}</td>
                        <td width="7%" class="shop_name">${item->shop_name!}</td>
                        <td width="7%">${item->error_level_name!}</td>
                        <td width="7%">${item->error_source_name!}</td>
                        <td width="7%">${item->deal_status_name!}</td>
                        <td width="7%">${item->deal_person!}</td>
                        <td width="7%">${item->deal_time!}</td>
                        <td width="7%">${item->action_name!}</td>
                        <td width="7%">${item->ip!}</td>
                        <td width="7%"><a href="#" data-toggle="modal" data-target="#modal_submit" class="deal_error" deal_person="${item->deal_person!}">处理</a>&nbsp;|&nbsp;<a href="#" data-toggle="modal" data-target="#modal_see" error_msg="${item->error_msg!}" error_content="${item->error_content!}" request_content="${item->request_content!}" response_content="${item->response_content!}" class="see_detail" error_code="${item->error_code!}">查看详情</a></td>
                    </tr>

                </#list>
                
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0" class="tb_paging">
                            <tr>
                                <td align="right">{{ trans("system/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                    <a href="##" onClick="return gopage(1);">{{ trans("system/common.page.first_page")!}</a>
                                    <a href="##" onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("system/common.page.pre_page")!}</a>
                                    <a href="##" onClick="return gopage(${data_list->currentPage() + ($data_list->lastPage() > $data_list->currentPage() ? 1: 0)!});">{{ trans("system/common.page.next_page")!}</a>
                                    <a href="##" onClick="return gopage(${data_list->lastPage()!});">{{ trans("system/common.page.last_page")!}</a><input id="page" name="page" type="text" value="${data_list->currentPage()!}" size="5" onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("system/common.page.page")!}
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
<div class="modal fade" id="modal_see" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">{{ trans("system/index.feedback_modal.close")!}</span></button>
                <h4 class="modal-title" id="myModalLabel">错误详情</h4>
            </div>
            <div class="modal-body">
                <form class="form needcheck" action="" method="" id="form_footer_global_feedback">
                    <div class="row text-left">
                    	<div class="form-group name feedback_content">
                            <label>错误码：</label>
                            <span class="error_code">无</span>
                        </div><!--//form-group-->
                    	<div class="form-group name feedback_content">
                            <label>错误说明：</label>
                            <span class="error_msg">无</span>
                        </div><!--//form-group-->
                        <div class="form-group name feedback_content">
                            <label>错误内容：</label>
                            <span class="error_content">无</span>
                        </div><!--//form-group-->
                        <div class="form-group email feedback_content">
                            <label>请求内容：</label>
                            <span class="request_content">无</span>
                        </div><!--//form-group-->
                        <div class="form-group message feedback_content">
                            <label>响应内容：</label>
                            <span class="response_content">无</span>
                        </div><!--//form-group-->
                        <div class="form-group message feedback_content">
                            <label>备注：</label>
                            <span class="remark">无</span>
                        </div><!--//form-group-->
                    </div><!--//row-->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">{{ trans("system/index.feedback_modal.close")!}</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="modal_submit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                            class="sr-only">{{ trans("system/index.feedback_modal.close")!}</span></button>
                <h4 class="modal-title" id="myModalLabel">处理错误</h4>
            </div>
            <div class="modal-body">
                <form class="form needcheck">
                	<input type="hidden" name="log_id" value="0">
                    <div class="row text-left">
                        <div class="form-group name feedback_content">
                            <label>处理人：</label>
                            <input class="deal_person" style="height: 25px;" type="text" name="deal_person" placeholder="请填写处理人姓名">
                        </div><!--//form-group-->
                        <div class="form-group message feedback_content">
                            <label style="vertical-align: top;">备注：</label>
                            <textarea class="form-control" style="width: 400px;" name="remark" rows="6" placeholder="填写备注"></textarea>
                        </div><!--//form-group-->
                    </div><!--//row-->
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default"
                        data-dismiss="modal">{{ trans("system/index.feedback_modal.close")!}</button>
                <button type="button" class="btn btn-primary deal_submit">{{ trans("system/index.feedback_modal.submit")!}</button>
            </div>
        </div>
    </div>
</div>
<script>
    function gopage(page) {
    var last_page = '${data_list->lastPage()!}';
    if(page>last_page){
        page = last_page;
    }
        $("#page").val(page);
        $("#form1").submit();
    }
    $("#main-table").FixedHead({tableLayout: "fixed"});

    $("#shop_id").val($(".btn_renew").attr('shop_id'));
    $("#re_sys_id").val($(".btn_renew").attr('sys_id'));

    $(".btn_search").click(function () {
        $('#form1').submit();
    })
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';            //总页码数
    
    $(".see_detail").click(function() {
    	$(".error_code").text('无');
    	$(".error_msg").text('无');
    	$(".error_content").text('无');
    	$(".request_content").text('无');
    	$(".response_content").text('无');
    	$(".remark").text('无');

    	var error_code = $(this).attr('error_code');
    	if (error_code) $(".error_code").text(error_code);
    	var error_msg = $(this).attr('error_msg');
    	if (error_msg) $(".error_msg").text(error_msg);
    	var error_content = $(this).attr('error_content');
    	if (error_content) $(".error_content").text(error_content);
    	var request_content = $(this).attr('request_content');
    	if (request_content) $(".request_content").text(request_content);
    	var response_content = $(this).attr('response_content');
    	if (response_content) $(".response_content").text(response_content);

    	var remark = $(this).parent().parent().attr('remark');
    	if (remark) $(".remark").text(remark);
    });

    $(".deal_error").click(function() {
    	var log_id = $(this).parent().parent().attr('log_id');
    	var deal_person = $(this).attr('deal_person');
    	var remark = $(this).parent().parent().attr('remark');
    	$("input[name='log_id']").val(log_id);
    	$("input[name='deal_person']").val(deal_person);
    	$("textarea[name='remark']").val(remark);
    });

    $("select[name='error_level']").change(function () {
        $("#form1").submit();
    });

    $("select[name='error_source']").change(function () {
        $("#form1").submit();
    });

    //处理错误
    $(".deal_submit").click(function () {
    	var deal_submit = $(this);
    	var deal_person = $("input[name='deal_person']").val();
    	if (!deal_person) {
    		util.mobile_alert('请填写处理人员');
    	}
    	var remark = $("textarea[name='remark']").val();
    	param = {};
    	param.deal_person = deal_person;
    	param.remark      = remark;
    	param.log_id = $("input[name='log_id']").val();
    	util.ajax_json('/system/log/deal/error',function (d) {
    		if (d.error == 0) {
    			util.mobile_alert('操作成功');
    			setTimeout(function () {
                    $("#modal_submit").hide();
                    location.reload();
                }, 1000);
    		}else{
    			util.mobile_alert('操作失败');
    		}
    	},param);
    });
</script>
<#include ("system.footer")
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>