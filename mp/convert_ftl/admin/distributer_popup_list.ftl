<html style="height:320px;">
    <head>
        <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css" />
        <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="/css/admin/distributer_popup_list.css?v=1.0.1">
        <script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
        <style>
            a{
                text-decoration: none;
            }
            .distributer_fl div{
                float: left;
                line-height: 30px;
            }
            .choose, .goods_fr{
            float: none;
            }
            .reset_search.choose{
                margin-left: 10px;
            }
            .btn_search{
                margin-left: 8px;
            }
            table{
                font-size:14px;
            }
            .choose, .goods_fr{
            float: none;
            }
            .goods_search select{
                margin-right: 34px;
            }
            #form1 span{
                margin-right: 28px;
            }
            .reset_search.choose{
                margin-left: 10px;
            }
            #add_distributer td {
                padding: 10px 8px;
            }
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
            #add_distributer{
                padding: 12px 16px;
            }
            .goods_spec{
                margin-bottom: 12px;
            }
            .goods_search{
                padding-bottom: 12px;
            }
            #add_distributer td a, #add_distributer td input,.goods_search input{
                border-radius: 2px;
            }
            .goods_fl div{
                float: left;
            }
            .goods_name{
                width: 160px;
            }
            .jump_page table tbody td{
                border: none;
            }
            .no_data_style{
                margin-top: 15px;
            }
        </style>
    </head>

    <body style="background:none;height:320px">
        <div id="add_distributer">

            <div class="distributer_search">
                <form action="" method="post" id="form1">
                {{csrf_field()!}
                <div class="distributer_spec">
                    <div class="distributer_fl">
                        <div>
                            分销员手机号：
                            <input type="text" placeholder="请填写手机号" name="mobile" value="${request['mobile']!}">
                        </div>
                        <div>
                            分销员昵称：
                            <input type="text" placeholder="请填写微信昵称" name="username" value="${request['username']!}">
                        </div>
                        <div>
                            真实姓名：
                            <input type="text" style="margin-left:15px" placeholder="请填写真实姓名" name="real_name" value="${request['real_name']!}">
                        </div>
                    </div>
                </div>
                <div class="distributer_spec">
                        <div class="distributer_fl">
                            <div>
                                分销员ID：　　
                                <input type="text" placeholder="请填写分销员ID" name="user_id" value="${request['user_id']!}">
                            </div>
                            <div>
                                分销员等级：
                                <select name="level_id" id="">
                                    <option value="-1">请选择等级</option>
                                    <#if ($distributor_level)
                                        <#list ($distributor_level as $level)
                                            <#if ($level->level_id <= $request['top_level_id'])
                                                <option <#if ($request['level_id'] == $level->level_id) selected </#if>
                                                value="${level->level_id!}">${level->level_name!}(${level->level_id!})</option>
                                            </#if>
                                        </#list>
                                    </#if>
                                </select>
                            </div>
                            <div>
                                分销员分组：
                                <select name="group_id">
                                    <option value="-1">请选择分组</option>
                                    <option value="0" <#if (isset($request['group_id']) && $request['group_id'] == 0) selected </#if>>未分组</option>
                                    <#if ($distributor_group)
                                        <#list ($distributor_group as $group)
                                            <option <#if ($request['group_id'] == $group->id) selected </#if>
                                            value="${group->id!}">${group->group_name!}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </div>
                            <div style="padding:10px 0">
                                <input type="hidden" id="page" name="page" />
                                <input type="hidden" name="top_level_id" value="${request['top_level_id']!}" />
                                <input type="hidden" name="all_id" value="${all_id!}" disabled />
                                <input type="hidden" name="record_select_value" value="${request['record_select_value']!}" id="record_select_value"/>
                                <input type="hidden" name="except_group_id" value="${request['$except_group_id']!}" />
                                <button class="btn_search choose">筛选</button>
                                <button class="reset_search choose">重置</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="goods_tb">
                <table width="100%">
                    <thead>
                        <tr>
                            <td width="50px"><input type="checkbox" id="all_check" />全选</td>
                            <td>分销员ID</td>
                            <td>分销员手机号</td>
                            <td>分销员昵称</td>
                            <td>真实姓名</td>
                            <td>下级用户数</td>
                            <td>累计获得佣金金额</td>
                            <td>分销员组</td>
                            <td>当前等级</td>
                        </tr>
                    </thead>
                    <tbody>
                        <#list ($data_list as $item)
                            <tr class="checked_distributers">
                                <td style="border-right:none;">
                                    <input type="checkbox" name="distributer_id[]" value="${item->user_id!}">
                                </td>
                                <td style="border-left: none;">
                                    ${item->user_id!}
                                </td>
                                <td>${item->mobile!}</td>
                                <td>${item->username!}</td>
                                <td>${item->real_name!}</td>
                                <td>${item->sublayer_number ?? 0!}</td>
                                <td>{{number_format($item->total_money,2)!}</td>
                                <td>
                                    <#if  ($item->invite_group > 0)
                                        ${distributor_group[$item->invite_group]->group_name!}
                                    <#else> 未分组
                                    </#if>
                                </td>
                                <td>${item->level_name!}<span>(${item->distributor_level!})</span></td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
                <div class="clearfix jump_page">
                    <#include "/admin/jump_page_admin.ftl">
                </div>
            </div>
            <!-- 分页 start -->
{{--            {{dd($distributor_level)!}--!}
            {{--<table width="" border="0" class="tb_paging">
                <tr>
                    <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                        <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                        <a href="##"
                        onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>
                        <a href="##"
                        onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                        <a href="##"
                        onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                        <input id="page1" name="page1" type="text" value="${data_list->currentPage()!}"
                            size="5" style="height: 32px"
                            onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                        <a href="#" style="width:46px;height: 30px;text-align: center" onClick="gopage($('#page1').val())" >{{ trans("admin/common.page.jump_page")!}</a>
                    </td>
                </tr>
            </table>--!}
            <!-- 分页 end -->

        </div> 
        <script>
            var record_select_value = $('#record_select_value').val() == '' ? [] : $('#record_select_value').val().split(',');
            // console.log(record_select_value)
            var each_id = 'distributer_id';
            $('input[name="'+each_id+'[]"]').each(function (index, item) {
                if ($(this).is(':disabled')) {
                    $(item).prop('checked', false);
                    return true;
                }
                if ($.inArray($(item).val(), record_select_value) > -1) {
                    $(item).prop('checked', true);
                    $(item).parents('tr').addClass('goods_tr_choose');
                } else {
                    $(item).prop('checked', false);
                    $(item).parents('tr').removeClass('goods_tr_choose');
                }
            });
            //全选
             $('#all_check').click(function () {
                var is_checked = $(this).is(':checked');
                if (is_checked) {
                    record_select_value = $('input[name="all_id"]').val().split(',');
                } else {
                    record_select_value = [];
                }
                $('input[name="distributer_id[]"]').prop('checked', is_checked);
                $('#record_select_value').val(record_select_value.join(','));
            });
            if (record_select_value.length > 0 && record_select_value.length == $('input[name="all_id"]').val().split(',').length) {
                //触发全选
                $('#all_check').trigger('click');
            }


            function checked(danxuan) {
                var is_checked = $(danxuan).is(':checked');
                if (is_checked) {
                    record_select_value.push($(danxuan).val());
                    $(danxuan).parents('tr').addClass('goods_tr_choose');
                } else {
                    var index = $.inArray($(danxuan).val(), record_select_value);
                    record_select_value.splice(index, 1);
                    $(danxuan).parents('tr').removeClass('goods_tr_choose');

                }
                $('#record_select_value').val(record_select_value.join(','));
                
                // console.log(record_select_value)
                // console.log($('input[name="all_id"]').val())
                // console.log($('input[name="all_id"]').val().split(',').length)
                if (record_select_value.length > 0 && record_select_value.length == $('input[name="all_id"]').val().split(',').length) {
                    $('#all_check').prop('checked', true);
                } else {
                    $('#all_check').prop('checked', false);
                }
            }



            // 点击行时
            $('.checked_distributers').on('click', function (e) {

            if(event.target.localName !== 'input') {
                $(this).find('input[type="checkbox"]').prop('checked', $(this).find('input[type="checkbox"]').is(':checked') ? false : true);
            }
            checked($(this).find('input[type="checkbox"]'));

            // if ($(this).attr('class') == "checked_goods goods_tr_choose") {
            //     $(this).find('input[type="checkbox"]').prop('checked', false);
            //     $(this).removeClass('goods_tr_choose');
            //     var index = $.inArray($(this).find('input[type="checkbox"]').val(),record_select_value);
            //     record_select_value.splice(index, 1);
            // } else {
            //     $(this).find('input[type="checkbox"]').prop('checked', true);
            //     $(this).addClass('goods_tr_choose');
            //     record_select_value.push($(this).find('input[type="checkbox"]').val());
            // }
            // $('#record_select_value').val(record_select_value.join(','));
            // if (record_select_value.length > 0 && record_select_value.length == $('input[name="all_id"]').val().split(',').length) {
            //     $('#all_check').prop('checked', true);
            // } else {
            //     $('#all_check').prop('checked', false);
            // }
            });
            $('.reset_search').click(function () {
                $('select[name="level_id"]').find('[value=-1]').prop('selected', 'selected');
                $('input[name="mobile"], input[name="username"], input[name="real_name"], input[name="user_id"]').val('');
                $('#form1').submit();
            })
            function gopage(page) {
                var last_page = '${data_list->lastPage()!}';
                if(parseInt(page)>parseInt(last_page)){
                    page = last_page;
                }
                $("#page").val(page);
                $("#form1").submit();
            }
        </script>  
    </body>
</html>