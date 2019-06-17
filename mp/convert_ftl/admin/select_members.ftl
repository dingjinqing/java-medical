<html style="height: 320px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <style>
        a{
            text-decoration: none;
        }
        input[type="checkbox"]:checked{
            width: 12px;
            height: 12px;
            background: url(/image/admin/square_yes.png) no-repeat;
            background-size: 100%;
        }
        input[type="checkbox"]{
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            width: 12px;
            height: 12px;
            background: url(/image/admin/square_no.png) no-repeat;
            background-size: 100%;
            position: relative;
            top: 1px;
            margin-right: 5px;
        }

    </style>
</head>
<body style="background:none;height: 320px;">
<#if ($empty == 0)
    <div>没有选中任何用户</div>
<#else>
<div id="set-goods">
    <div class="goods_search">
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <#if  ($head_flag == 1)
                <span>ID</span>
                <input type="text" name="id" value="${id!}"/>
                <span>昵称</span>
                <input type="text" name="username" value="${name!}">
                <span>手机号</span>
                <input type="text" name="mobile" value="${mobile!}">
            </#if>
            <input type="hidden" id="page" name="page" value="${data_list->currentPage()!}"/>

            <input type="hidden" name="verify_store_id" value="${request['verify_store_id']!}"/>
            <input type="hidden" id="user" name="user" value="${user_id!}"/>
            <input type="hidden" id="check_choice" name="check_choice" value="${check_choice!}"/>
            <input type="hidden" id="all_flag" name="all_flag" value="${all_flag!}"/>
            <#if  ( $head_flag)
                <button class="btn_search">搜索</button>
            </#if>
        </form>
    </div>
    <div class="goods_tb">
        <table width="100%">
            <thead>
            <tr>
                <td <#if ($flag=="service") hidden </#if>>
                    <label for="allAndNotAll">全选&nbsp;</label>
                    <input type="checkbox" id="allAndNotAll" name="choooose" checked />
                </td>
                <td>ID</td>
                <td>昵称</td>
                <td>手机号</td>
                <#if ($grade_card == 1)
                    <td>累计获得积分</td>
                    <td>累计消费金额</td>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#list ($data_list as $item)
                <tr data-back="true" user_id="${item->user_id!}" username="${item->username!}" mobile="${item->mobile!}">
                    <td <#if ($flag=="service") hidden </#if>><input type="checkbox" id="choose-vip" name="items"  checked value="${item->user_id!}"/></td>
                    <td>${item->user_id!}</td>
                    <td>${item->username!}</td>
                    <td>${item->mobile!}</td>
                    <#if ($grade_card == 1)
                        <td>${item->score_amount!}</td>
                        <td>${item->paid_amount!}</td>
                    </#if>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>

    <#if ($grade_card!=1 && $data_list -> count() != 0)
    <table width="" border="0" class="tb_paging">
        <input type="hidden" name="total" value="${data_list->total()!}"/>
        <tr>
            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                <a href="##"
                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                       size="5"
                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                <a href="#" style="width:46px;height: 30px;background: rgb(250, 250, 250);text-align: center" onClick="gopage($('#page').val())" >{{ trans("admin/common.page.jump_page")!}</a>
            </td>
        </tr>
    </table>
    <#else>
        <div style="width: 100%;background: #fff;">
            <div style="border: 1px solid #eee;">
                <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                    <img src="http://${image_domain!}/image/admin/no_data.png" alt="">
                </div>
                <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
            </div>
        </div>
    </#if>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
    var all_flag_arr = '${all_flag!}'.split(',');
    var check_choice = '${check_choice!}' == '' ? [] : '${check_choice!}'.split(',');

    var grade_card = "${grade_card!}";
    if(grade_card==1){
        $("#allAndNotAll").prop("checked",false);
    }
    {{--console.log('${all_flag!}');--!}
    {{--console.log('${check_choice!}');--!}
    // console.log(all_flag_arr);
    // console.log($.inArray('0', all_flag_arr));
    if($.inArray('0', all_flag_arr) == -1){
        $("#allAndNotAll").attr('checked',true);
        check_choice = '${user_all!}'.split(',');
        $("#check_choice").val(check_choice.join(','));
    }else{
        $("#allAndNotAll").attr('checked',false);
    }
    console.log($("#check_choice").val());
    if(all_flag_arr['${data_list->currentPage()!}'-1] == '1'){
        $(".goods_tb input[name='items']").attr('checked',true);
        $('input[name="items"]').each(function (index, item) {
            if($.inArray($(item).val(), check_choice) == -1){
                check_choice.push($(item).val());
            }
        })
    }else{
        $('input[name="items"]').each(function (index, item) {
            if ($.inArray($(item).val(), check_choice) > -1) {
                $(item).prop('checked', true);
            } else {
                $(item).prop('checked', false);
            }
        });

    }


    $("#allAndNotAll").click(function () {
        var isChecked = $("#allAndNotAll").prop("checked");
        if($(this).is(':checked')) {
            for (var i = 0;i<${data_list->count!};i++){
                all_flag_arr[i] = '1';
            }
            $(this).attr('src','/image/admin/square_yes.png');
            $('input[name="items"]').each(function (index, item) {
                if(!$(item).is(':checked')){
                    $(item).prop("checked",true);
                }
            });
            check_choice = '${user_all!}'.split(',');
            $("#check_choice").val(check_choice.join(','));
        }else{
            for (var i = 0;i<${data_list->count!};i++){
                all_flag_arr[i] = '0';
            }
            $(this).attr('src','/image/admin/square_no.png');
            $('input[name="items"]').each(function (index, item) {
                if($(item).is(':checked'))
                    $(item).prop("checked",false);
                    // console.log(check_choice);
            })
            check_choice = [];
            $("#check_choice").val('');
        }
    });

    $("#set-goods .goods_tb tbody tr").on('click','',function (e) {
        if( $(this).find('#choose-vip').is(":checked")){
            $(this).find('#choose-vip').prop("checked", false);
            for(var j=0;j<check_choice.length;j++){
                if(check_choice[j] == $(this).find('#choose-vip').val()){
                    check_choice.splice(j, 1);
                    $("#check_choice").val(check_choice.join(','));
                    console.log($("#check_choice").val());
                    break;
                }
            }
            $(this).find('#choose-vip').attr('src','/image/admin/square_no.png');
            $("#allAndNotAll").attr('src','/image/admin/square_no.png');
        }else{
            $(this).find('#choose-vip').prop("checked", true);
            check_choice.push($(this).find('#choose-vip').val());
            $("#check_choice").val(check_choice.join(','));
            console.log($("#check_choice").val());
            $(this).find('#choose-vip').attr('src','/image/admin/square_yes.png');
        }
        var curLength = '${data_list->total()!}';
        var checkedLength = $('#check_choice').val().split(',').length;
        if(curLength == checkedLength){
            all_flag_arr['${data_list->currentPage()!}'-1] = '1';
        }else{
            all_flag_arr['${data_list->currentPage()!}'-1] = '0';
        }
        if($.inArray('0', all_flag_arr) == -1){
            $("#allAndNotAll").prop("checked",true);
            $("#allAndNotAll").attr('src','/image/admin/square_yes.png');
        }else{
            $("#allAndNotAll").attr('checked',false);
            $("#allAndNotAll").attr('src','/image/admin/square_no.png');
        }
    });

    $(".goods_tb input[name='items']").click(function (e) {
        e.stopPropagation();
        // $(this).attr("checked", "checked");
        if($(this).is(':checked')){
            check_choice.push($(this).val());
            $("#check_choice").val(check_choice.join(','));
            console.log($("#check_choice").val());
            $(this).attr('src','/image/admin/square_yes.png');

        }else{
            // check_choice.splice($(this).index, 1);
            // console.log(check_choice);
            for(var j=0;j<check_choice.length;j++){
                if(check_choice[j] == $(this).val()){
                    check_choice.splice(j, 1);
                    $("#check_choice").val(check_choice.join(','));
                    console.log($("#check_choice").val());
                    break;
                }
            }
            $(this).attr('src','/image/admin/square_no.png');
            $("#allAndNotAll").attr('src','/image/admin/square_no.png');
        }
        var curLength = $(".goods_tb input[name='items']").length;
        var checkedLength = $(".goods_tb input[name='items']:checked").length;
        if(curLength == checkedLength){
            all_flag_arr['${data_list->currentPage()!}'-1] = '1';
        }else{
            all_flag_arr['${data_list->currentPage()!}'-1] = '0';
        }

        if($.inArray('0', all_flag_arr) == -1){
            $("#allAndNotAll").prop("checked",true);
            $("#allAndNotAll").attr('src','/image/admin/square_yes.png');
        }else{
            $("#allAndNotAll").attr('checked',false);

        }
    });

    {{--$("input[name='items']").click(function (e) {--!}
        {{--e.stopPropagation();--!}
        {{--$(this).attr("checked", "checked");--!}
        {{--var curLength = $(".goods_tb input[name='items']").length;--!}
        {{--var checkedLength = $(".goods_tb input[name='items']:checked").length;--!}
        {{--var allLength = '${data_list->total()!}';--!}
        {{--var checkedLength = $('#check_choice').val().split(',').length;--!}
        {{--if(curLength == checkedLength){--!}
        {{--all_flag_arr['${data_list->currentPage()!}'-1] = '1';--!}
        {{--}else{--!}
        {{--all_flag_arr['${data_list->currentPage()!}'-1] = '0';--!}
        {{--}--!}

        {{--if($.inArray('0', all_flag_arr) == -1){--!}
        {{--$("#allAndNotAll").prop("checked",true);--!}
        {{--$("#allAndNotAll").attr('src','/image/admin/square_yes.png');--!}
        {{--}else{--!}
        {{--$("#allAndNotAll").attr('checked',false);--!}
        {{--}--!}

        {{--if($(this).is(':checked')){--!}
        {{--check_choice.push($(this).val());--!}
        {{--$("#check_choice").val(check_choice.join(','));--!}
        {{--// console.log(check_choice);--!}
        {{--$(this).attr('src','/image/admin/square_yes.png');--!}

        {{--}else{--!}
        {{--// check_choice.splice($(this).index, 1);--!}
        {{--// console.log(check_choice);--!}
        {{--for(var j=0;j<check_choice.length;j++){--!}
        {{--if(check_choice[j] == $(this).val()){--!}
        {{--check_choice.splice(j, 1);--!}
        {{--$("#check_choice").val(check_choice.join(','));--!}
        {{--break;--!}
        {{--}--!}
        {{--}--!}
        {{--$(this).attr('src','/image/admin/square_no.png');--!}
        {{--$("#allAndNotAll").attr('src','/image/admin/square_no.png');--!}
        {{--}--!}
    {{--})--!}



    //     $('#set-goods').on('click','.goods_tb tbody tr',function(){
    //     var flag_back = $(this).attr('data-back');
    //     if(flag_back == 'true'){
    //         // $(this).addClass('goods_tr_choose');
    //         $(this).attr('data-back','false');
    //         $(this).find('#choose-vip').prop('checked','checked');
    //         flag_back = 'false';
    //     }else if(flag_back == 'false'){
    //         $(this).attr('data-back','true');
    //         $(this).find('#choose-vip').removeAttr('checked');
    //         flag_back = 'true';
    //     }
    // });
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#all_flag").val(all_flag_arr.join(','));
        $("#check_choice").val(check_choice.join(','));
        $("#page").val(page);
        $("#form1").submit();
    }

</script>
</#if>
</body>
</html>