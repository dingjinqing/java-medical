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

<#if  (empty($data_list) && !empty($list))
    ${data_list =  $list!}
</#if>

<#if  (empty($data_list) && !empty($act_list))
    ${data_list =  $act_list!}
</#if>
<#if  (empty($data_list) && !empty($coupon_list))
    ${data_list =  $coupon_list!}
</#if>
<#if  (empty($data_list) && !empty($user_coupon_list))
    ${data_list =  $user_coupon_list!}
</#if>

<#if  (empty($data_list) && !empty($item->content->data_list))
    ${data_list =  $item->content->data_list!}
</#if>

<#if ($data_list->count())
<table width="100%" border="0" class="tb_paging">
    <tr>
        <td style="text-align:right;">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
            <a href="#" style="background: rgb(250, 250, 250);" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
            <a href="#" style="background: rgb(250, 250, 250);"
               onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
            <a href="#" style="background: rgb(250, 250, 250);"
               onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
            <a href="#" style="background: rgb(250, 250, 250);"
               onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
            <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                   size="5"
                   onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);"   onkeyup="value=value.replace(/[^\d.]/g,'')" autocomplete="off"
            >{{ trans("admin/common.page.page")!}
            <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
        </td>
    </tr>
</table>
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
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
    <#if  (!empty($data_list ))
    function gopage(page) {
        var last_page = '${data_list -> lastPage()!}';
        if($("input[name='act']")){
            $("input[name='act']").val("");
        }
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        $("#page").val(page);
        $(".tb_paging").closest("form").submit();
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}';          //总页码数

    </#if>
        // var pagination = document.getElementsByClassName('pagination');
        // pagination.parentNode.removeChild(pagination);

        $(".pagination").remove();

</script>