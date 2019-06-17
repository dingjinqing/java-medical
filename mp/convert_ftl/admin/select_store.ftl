<link rel="stylesheet" href="/css/admin/shop_setting.css?v=1.0.1" type="text/css" />
<style type="text/css">
    body,html{ background: #fff; }
    .goods_tr_choose {
        background: #eee;
    }
    table {
        border-collapse: collapse;
        border-spacing: 0;
    }
</style>
<div id="set-shop">
    <form action="" method="post" id="form1">
        {{csrf_field()!}
        <table id="store_list" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <th><input type="checkbox" name="store_id_all" <#if  (!empty($storeArr) && count($data_list) == count($storeArr))) checked </#if>/></th>
                <th width="150px">门店名称</th>
                <th width="150px">门店地址</th>
                <th width="70px">负责人</th>
                <th>联系电话</th>
                <th>营业时间</th>
                <th width="60px" style="padding: 8px 0;">营业状态</th>
            </tr>
            <#if ($data_list)
                <#list ($data_list as $item)
                    <tr store_id="${item->store_id!}" auto="false" class="choose_store">
                        <td><input type="checkbox" name="store_id[]" value="${item->store_id!}" <#if  (in_array($item->store_id, $storeArr)) checked </#if>/></td>
                        <td>${item->store_name!}</td>
                        <td>${item->area!} ${item->address!}</td>
                        <td>${item->manager!}</td>
                        <td>${item->mobile!}</td>
                        <td><#if ($item->business_type == 1) 每天 <#elseif>($item->business_type == 0)${item->opening_time!} - ${item->close_time!} </#if></td>
                        <td class="business_state"><#if ($item->business_state) 营业 <#else> 关店 </#if></td>
                    </tr>
                </#list>
            </#if>
        </table>
        <#if (!$data_list)
            <div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                </div>
                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
            </div>
        </#if>


        {{--<table width="100%" height="50px" border="0" class="tb_paging">--!}
            {{--<tr>--!}
                {{--<td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}--!}
                    {{--<a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>--!}
                    {{--<a href="##"--!}
                       {{--onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>--!}
                    {{--<a href="##"--!}
                       {{--onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>--!}
                    {{--<a href="##"--!}
                       {{--onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>--!}
                    {{--<input style="width:50px;" id="page" name="page" type="text" value="${data_list->currentPage()!}"--!}
                           {{--size="5"--!}
                           {{--onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}--!}
                {{--</td>--!}
            {{--</tr>--!}
        {{--</table>--!}
    </form>
</div>

{{--<script>--!}

    {{--function gopage(page) {--!}
        {{--// console.log($("#page").val(page));--!}
        {{--var last_page = '${data_list->lastPage()!}';--!}
        {{--if(page>last_page){--!}
            {{--page = last_page;--!}
        {{--}--!}
        {{--$("#page").val(page);--!}
        {{--$("#form1").submit();--!}
    {{--}--!}
    {{--function picker() {--!}
        {{--return WdatePicker({dateFmt: 'yyyy-MM-dd'});--!}
    {{--}--!}
    {{--var page_home = '${data_list->currentPage()!}'; //当前页码数--!}
    {{--var page_all = '${data_list->count!}'            //总页码数--!}
{{--</script>--!}
{{--<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>--!}
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
    var data_count = '{{ count(object2array($data_list))!}';
    $('#set-shop').on('click','.choose_store',function(){
        var flag_back = $(this).attr('auto');
        if(flag_back == 'false'){
            $(this).addClass('goods_tr_choose');
            $(this).attr('auto','true');
            flag_back = 'true';
            $(this).find('input[name="store_id[]"]').prop('checked', true);
            if (parseInt($('input[name="store_id[]"]:checked').length) == parseInt(data_count)) {
                $('input[name="store_id_all"]').prop('checked', true);
            }
        }else if(flag_back == 'true'){
            $(this).removeClass('goods_tr_choose');
            $(this).attr('auto','false');
            flag_back = 'false';
            $(this).find('input[name="store_id[]"]').prop('checked', false);
            $('input[name="store_id_all"]').prop('checked', false);
        }
    });
    $('input[name="store_id_all"]').click(function () {
        var is_checked = $(this).is(':checked');
        $('input[name="store_id[]"]').prop('checked', is_checked);
        if (is_checked) {
            $('input[name="store_id[]"]').parent().parent().attr('auto', true);
            $('input[name="store_id[]"]').parent().parent().addClass('goods_tr_choose');
        } else {
            $('input[name="store_id[]"]').parent().parent().attr('auto', false);
            $('input[name="store_id[]"]').parent().parent().removeClass('goods_tr_choose');
        }
    })
</script>