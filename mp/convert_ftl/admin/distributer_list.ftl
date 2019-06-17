<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.4" type="text/css" />
<style>
    .info_table .tb_paging{
        float: right;
    }
    .info_table .tb_paging td{
        padding: 0;
        border: none;
        text-align: right;
    }
    .info_table .tb_paging input{
        height: 30px;
        border: 1px solid #dedede;
        text-align: center;
        margin: 0 8px;
    }
    .info_table .tb_paging tr td a {
        display: inline-block;
        border: 1px solid #dedede;
        padding: 0px 8px;
        height: 30px;
        line-height: 30px;
        margin-left: 5px;
    }
    .info_table .tb_paging tr td a:first-child {
        margin-left: 15px;
    }
    .tips{
        margin-bottom:10px;
    }
    .group_select_box{
        text-align:center;
        margin:20px 0 15px;
    }
    .group_select_box select{
        width:130px;
        height:35px;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 /</span><span style="color: #666;">${title!}</span>
</div>
<div class="reserve-container">
    <form action="/admin/market/distribution/distributer/list?top_index=4" id="form1" method="post">
    <div class="pages_nav clearfix">
        <#if ($request['all_users'] != 1)
            <#include ("admin.distributio_title")
        </#if>
    </div>
        {{ csrf_field()!}
        <input type="hidden" name="act">
    <div class="search_reason">
        <ul>
            <li class="clearfix">
                <div class="monile_num re_li">
                    <span>手机号</span>
                    <input type="text" placeholder="请填写手机号" name="mobile" value="${request['mobile']!}">
                </div>
                <div class="wx_name re_li">
                    <span>微信昵称</span>
                    <input type="text" placeholder="请填写微信昵称" name="username" value="${request['username']!}">
                </div>
                <div class="wx_name re_li">
                    <span>真实姓名</span>
                    <input type="text" placeholder="请填写真实姓名" name="real_name" value="${request['real_name']!}">
                </div>
            <li class="clearfix">
                <div class="invite_num re_li">
                    <span>被邀请用户手机号</span>
                    <input type="text" placeholder="请填写被邀请用户手机号" name="fanli_mobile" value="${request['fanli_mobile']!}">
                </div>
                <div class="login_time re_li">
                    <span>注册时间</span>
                    <input type="text" onclick="picker()" name="start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['start_time']!}" autocomplete="off">
                    &nbsp;&nbsp;至&nbsp;&nbsp;
                    <input type="text" onclick="picker()" name="end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['end_time']!}" autocomplete="off">
                </div>

            </li>
            <li class="clearfix">
                <div class="invite_name re_li">
                    <span>被邀请用户昵称</span>
                    <input type="text" placeholder="请填写被邀请用户昵称" name="fanli_username" value="${request['fanli_username']!}">
                </div>
                <div class="invite_name re_li">

                    <span>分销员等级</span>
                    <select name="level_id" id="">
                        <option value="-1">请选择等级</option>
                        <#if ($distributor_level)
                            <#list ($distributor_level as $level)
                                <option <#if ($request['level_id'] == $level->level_id) selected </#if>
                                    value="${level->level_id!}">${level->level_name!}(
                                        <#if ($level->level_id == 2)二级
                                        <#elseif>($level->level_id == 3)三级
                                        <#elseif>($level->level_id == 4)四级
                                        <#elseif>($level->level_id == 5)五级
                                        <#else> 一级
                                        </#if>
                                    )</option>
                            </#list>
                        </#if>
                    </select>
                </div>
                <div class="invite_name re_li">

                    <span>分销员分组</span>
                    <select name="group_id" id="">
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
                <button type="button" class="btn_exel fr" style="vertical-align: top;margin-right:85px;margin-bottom:20px">导出</button>
                <button type="button" class="btn_seach fr">筛选</button>
            </li>
        </ul>
    </div>
{{--        {{print_r($request)!}--!}
        <input type="hidden" name="sort_field" id="sort_field" value="${sort_field!}">
        <input type="hidden" name="sort_way" id="sort_way" value="${sort_way!}">
        <input type="hidden" name="all_users" value="${request['all_users']!}">
    <div class="info_table" style="padding-bottom: 50px">
        <p class="tips"><span style="color:red;">注：</span>未开启分销员审核时，列表只展示有下级用户的分销员</p>
        <table width="100%" style="margin-bottom: 20px">
            <thead>
                <tr>
                    <th width="13%">分销员昵称</th>
                    <th width="7%">分销员手机号</th>
                    <th width="10%">注册时间</th>
                    <th width="7%">真实姓名</th>
                    <th width="10%">分销员分组</th>
                    <th width="10%">分销员等级</th>
                    <th width="5%">
                        <a href="javascript:void(0);" onClick="return sort_f(1);">下级用户数</a>
                        <span id="sort_symbo">${sort_symbo[1]!}</span>
                    </th>
                    <th width="5%">
                        <a href="javascript:void(0);">间接邀请用户数</a>
                    </th>
                    <th width="10%">
                        <a href="javascript:void(0);" onClick="return sort_f(3);">累积获得佣金金额</a>
                        <span id="sort_symbo">${sort_symbo[3]!}</span>
                    </th>
                    <th width="10%">
                        <a href="javascript:void(0);" onClick="return sort_f(2);">待返利佣金金额</a>
                        <span id="sort_symbo">${sort_symbo[2]!}</span>
                    </th>
                    <th width="20%">操作</th>
                </tr>
            </thead>
            <tbody>
                <#list ($data_list as $item)
                    <tr>
                        <td><a target="_blank" href="/admin/user/manage/center?user_id=${item->user_id!}&top_index=5&sub_index=0" style="color: #0E70CA"  target="_blank">${item->username!}</a></td>
                        <td>${item->mobile!}</td>
                        <td>${item->create_time!}</td>
                        <td>${item->real_name!}</td>
                        <td>
                            <span>
                                <#if  ($item->invite_group > 0)
                                    ${distributor_group[$item->invite_group]->group_name!}
                                <#else> 未分组
                                </#if>
                            </span>
                            <a href="javascript:;" class="edit_group" style="color: #5a8bff;" group_id="${item->invite_group!}" user_id="${item->user_id!}">编辑</a></td>
                        <td>${item->level_name!}</td>
                        <td><a target="_blank"   href="/admin/market/distribution/distributer/invited/list?top_index=4&fanli_user_id=${item->user_id!}" style="color: #5a8bff;">${item->sublayer_number ?? 0!}</a></td>
                        <td><a target="_blank" href="/admin/market/distribution/distributer/indirect/invited/list?top_index=4&rebate_user_id=${item->user_id!}
" style="color: #5a8bff;">${item->next_number ?? 0!}</a></td>
                        <td>{{number_format($item->rebate_money,2)!}</td>
                        <td>{{number_format($item->wait,2)!}</td>
                        <td class="caozuo">
                            <input hidden name="delete_distributor" />
                             <a target="_blank"  href="/admin/market/distribution/distributer/invited/list?top_index=4&fanli_user_id=${item->user_id!}" class="btn_invited">查看已邀请用户</a>
                             <a target="_blank"  href="/admin/market/distribution/brokerage/detail?top_index=4&fanli_user_id=${item->user_id!}" class="btn_redetail" style="margin-bottom: 10px">查看返利佣金明细</a>
                            <#if ($fanli_cfg['judge_status'] == 1)
                                <a user_id="${item->user_id!}" class="btn_del_distributor">清除</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
  <#include "/admin/jump_page_admin.ftl">
    </div>
    </form>
</div>
<div style="display:none;" class="group_select_box">
    <span>选择分组：</span>
    <select name="group_select">
        {{--<option value="-1">请选择分组</option>--!}
        <option value="0">未分组</option>
        <#if ($distributor_group)
            <#list ($distributor_group as $group)
                <option value="${group->id!}">${group->group_name!}</option>
            </#list>
        </#if>
    </select>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    // function picker() {
    //     return WdatePicker({dateFmt: 'yyyy-MM-dd'});
    // }
    $('.btn_seach').click(function () {
        $("#page").val(1);
        $("input[name='act']").val("");
        $("#form1").submit();
    })
    $(".btn_exel").click(function () {
        $("input[name='act']").val("export_csv");
        $("#form1").submit();
    })
    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }
    $(".btn_del_distributor").click(function () {
        var user_id = $(this).attr("user_id")
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '清除分销员，则该分销员被清除分销员资格，被清除的用户可重新申请成为分销员，确定清除吗？' + '</div>', {
                title: ['清除分销员','text-align:center;padding: 0px;']
                , area: '350px'
                , closeBtn: 0
                , btn: ['确定清除', '取消']
            },function(index){
                $("input[name='delete_distributor']").val(user_id);
                $("#form1").submit();
                layer.close(index);
            });
        });
    })
    window.sort_f = function (field) {
        if ($("#sort_field").val() == field) {
            if ($("#sort_way").val() == 'desc') {
                $("#sort_way").val('asc');
            } else {
                $("#sort_way").val('desc');
            }
        } else {
            $("#sort_field").val(field);
            $("#sort_way").val('desc');
        }
        $("#form1").submit();
    };

    $('.edit_group').click(function(){
        var _this = $(this);
        layui.use('layer', function(){
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type:1,
                title: ['设置分销员分组', 'text-align:center;padding: 0px;'],
                offset: 'auto',
                area: '300px',
                content: $('.group_select_box'),
                btn: ['确定', '取消'],
                btnAlign: 'r' ,
                shade: [0.3, '#000'],
                success: function() {
                    var group_id = _this.attr('group_id');
                    $('.group_select_box select').val(group_id)
                },
                yes:function(index){
                    var select_group_id = $('.group_select_box select').val();
                    if (!(select_group_id >= 0)) {
                        util.mobile_alert('请选择分销员分组')
                        return false;
                    }
                    var select_group_name = $('.group_select_box select').find("option:selected").text();
                    var user_id = _this.attr('user_id');
                    util.ajax_json('/admin/market/distributor/group/update',function(res){
                        if(res.error == 0){
                            _this.parent().find('span').html(select_group_name);
                            util.mobile_alert('编辑分组成功')
                        } else {
                            util.mobile_alert(res.message)
                        }
                    },{action:'update_group_user',group_id:select_group_id,group_user_ids:user_id});
                    layer.close(index);
                    $('.group_select_box').hide()
                },
                btn2:function(index,layero){
                    layer.close(index);
                    $('.group_select_box').hide()
                }
            })
        })
    })
    // var left =  $('.left-menu-content .item-menu:nth-child(7)');
    // left.find("img").attr("src","/image/admin/icon_left/img_distribution_h.png");
    // left.find("a").css("background","#2E3144");
    // left.find("span").css({"color":"white","opacity":"1"});
</script>
<script type="text/javascript">
    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
































