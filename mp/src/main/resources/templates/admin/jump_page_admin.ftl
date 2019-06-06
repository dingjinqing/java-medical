<#macro page_template data_list page image_domain >
<style type="text/css">
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    input[name='page']:focus {
        border: 1px solid #5a8bff;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging tr td,.tb_paging tr td a{
        color: #333;
        font-size: 14px;
    }
    .tb_paging{
        border: 0 !important;
    }
    .paging_footer table td{
        border: none !important;
    }
</style>

<#if ((data_list??) && (data_list?size > 0)) >
         <div class="tb_paging">
        <div class="left-div"><#nested></div>
        <div class="right-div">
        	${page.pageInfo}
            <a href="#" style="background: rgb(250, 250, 250);" onClick="return gopage(1);">第一页</a>
            <a href="#" style="background: rgb(250, 250, 250);"
               onClick="return gopage(${page.prePage});">上一页</a>
            <a href="#" style="background: rgb(250, 250, 250);"
               onClick="return gopage(${page.nextPage});">下一页</a>
            <a href="#" style="background: rgb(250, 250, 250);"
               onClick="return gopage(${page.lastPage});">最后一页</a>
            <input id="page" name="page" type="text" value="${page.currentPage}"
                   size="5"
                   onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')" autocomplete="off"
            >页
            <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >跳转</a>
		</div>
	</div>

<#else>
    <style>
        .paging_footer{
            padding: 0px 0px;
        }
        .paging_footer table td{
            border: none;
        }
        .bottom-table td{
            padding: 10px 0px !important;
        }
    </style>
    <div class="no_data_style" style="width: 100%;border: 1px solid #eee;">
        <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
            <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
        </div>
        <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
    </div>
</#if>
<#noparse>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
	function gopage(page) {
	    if($("input[name='act']")){
	        $("input[name='act']").val("");
	    }
	    $("#page").val(page);
	    $(".tb_paging").closest("form").submit();
	}
	$(".pagination").remove();

</script>
</#noparse>
</#macro>