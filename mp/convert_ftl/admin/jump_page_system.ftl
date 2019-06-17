<style>
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
</style>
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
            <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);" onClick="gopage($('#page').val())" >{{ trans("system/common.page.jump_page")!}</a>
        </td>
    </tr>
</table>
<script>
    function gopage(page) {
        console.log(12);
        var last_page = '${data_list -> lastPage()!}';
        if(parseInt(page) > parseInt(last_page)) {
            page = last_page;
        }
        console.log(page);
        $("#page").val(page);
        $(".tb_paging").closest("form").submit();
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    console.log(page_home);
    var page_all = '${data_list->count!}';          //总页码数

</script>