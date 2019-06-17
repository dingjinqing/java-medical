<#include ("system.header")
<link href="/css/system/merchant_platform.css" rel="stylesheet" type="text/css"/>

<ul id="tab" class="nav nav-tabs">
    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="">文件导入</a>
    </li>
    <li <#if ($nav_type==2)class="active"</#if>><a href="#" data-toggle="tab" url="">ERP商品导入</a>
    </li>
</ul>

<div class="main-panel">
	<div class="panel-top">
		导入商品CSV文件：
		<button class="choose-file">选择文件</button>
		未选择任何文件
		<span><a href="javascript:void(0)">样例文件下载</a></span>
	</div>
    <div class="panel-bottom">
    	<button class="goods-introduction">商品导入</button>
    </div>
</div>

<div class="member_list_main">
	<table width="100%">
	    <thead>
	        <tr>
	            <td>ID</td>
	            <td>操作来源</td>
	            <td>类型</td>
	            <td>状态描述</td>
	            <td>开始时间</td>
	            <td>完成时间</td>
	            <td>操作</td>
	        </tr>
	    </thead>
	    <tbody>
	        <tr>
	        	<td>1752</td>
	        	<td>平台</td>
	        	<td>商品批量导入</td>
	        	<td>导入完成,共一件商品,共一个规格</td>
	        	<td>2018-10-30 11:58:29</td>
	        	<td>2018-11-10 11:58:29</td>
	        	<td><a href="javascript:void(0)">查看失败列表</a></td>
	        </tr>
	    </tbody>
	</table>
</div>

<div class="member_list_footer">
    <table width="" border="0" class="tb_paging">
        <tbody>
        	<tr>
	            <td align="right">当前页面1/1，总记录17条
	                <a href="##" style="cursor: default; background: rgb(250, 250, 250);">首页</a>
	                <a href="##" style="cursor: default; background: rgb(250, 250, 250);">上一页</a>
	                <a href="##" style="cursor: default; background: rgb(250, 250, 250);">下一页</a>
	                <a href="##" style="cursor: default; background: rgb(250, 250, 250);">末页</a>
	                <input id="page" name="page" type="text" value="1" size="5" onkeydown="if (event.keyCode==13 || event.which==13) gopage(this.value);">页
	            </td>
	        </tr>
        </tbody>
    </table>
</div>

<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>













<#include ("system.footer")