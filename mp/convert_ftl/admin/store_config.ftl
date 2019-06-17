{{--<#include "/admin/header.ftl">--!}
<link rel="stylesheet" href="/css/admin/shop_setting.css?v=1.0.1" type="text/css" />
<style type="text/css">
    body,html{ background: #fff; }
</style>
<div id="set-shop">
    <form action="/admin/frame/shop/list" method="post" id="form1">
        {{csrf_field()!}
    <table  width="100%" id="store_list" cellpadding="0" cellspacing="0">
        <tr>
            <th>门店名称</th>
            <th style="width:26%;">门店地址</th>
            <th>负责人</th>
            <th>联系电话</th>
            <th>营业时间</th>
            <th>营业状态</th>
            <th>是否自提</th>
        </tr>
        <#if ($data_list -> count())
        <#list ($data_list as $item)
            <tr store_id="${item->store_id!}" auto="false">
                <td>${item->store_name!}</td>
                <td>${item->area!} ${item->address!}</td>
                <td>${item->manager!}</td>
                <td>${item->mobile!}</td>
                <td>${item->opening_time!} - ${item->close_time!} </td>
                <td class="business_state"><#if ($item->business_state) 营业 <#else> 关店 </#if></td>
                <td>
                    <div class="fl pay_fl" <#if ($item->auto_pick == 1)img_id="1" style="background: url('http://${image_domain!}/image/admin/on_1.png') left top / 100% 100% no-repeat;" <#else> img_id="0" </#if>>
                        <label>
                            <input type="checkbox" name="" aid="" />
                            <img src="http://${image_domain!}/image/admin/circle.png" class="draggable" <#if ($item->auto_pick == 1)style="right: 0px;" </#if>/>
                        </label>
                    </div>
                </td>
            </tr>
        </#list>
        </#if>
    </table>
    <#if ($data_list -> count())
    <table width="100%" height="50px" border="0" class="tb_paging">
        <tr>
            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                <input style="width:50px;" id="page" name="page" type="text" value="${data_list->currentPage()!}"
                       size="5"
                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
            </td>
        </tr>
    </table>
    <#else>
            <div class="no_data_style" style="width: 100%;border: 1px solid #eee;height: 100px;margin-top: 10px;">
                <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                </div>
                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
            </div>
    </#if>
    </form>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
    $(".draggable").click(function(){

        var img_id=$(this).parent().parent().attr("img_id");

        if(img_id==0 ){
            $(this).parent().parent().css("background","url(/image/admin/on_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","1");
            $(this).animate({right:"0px"});
            $(this).prev().attr("aid","1");
            $(this).prev().prop('checked',false);
            $(this).parent().parent().parent().parent().attr('auto','true');
        }
        else if(img_id==1){
            $(this).parent().parent().css("background","url(/image/admin/off_1.png) left top no-repeat").css("background-size","100% 100%").attr("img_id","0");
            $(this).animate({right:"20px"});
            $(this).prev().attr("aid","0");
            $(this).prev().prop('checked',true);
            $(this).parent().parent().parent().parent().attr('auto','true');
        }

    });
</script>
<script>

    function gopage(page) {
        // console.log($("#page").val(page));
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }
    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    }
    var page_home = '${data_list->currentPage()!}'; //当前页码数
    var page_all = '${data_list->count!}'            //总页码数
</script>
<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>
{{--<#include "/admin/footer.ftl">--!}