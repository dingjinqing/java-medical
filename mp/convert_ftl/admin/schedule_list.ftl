<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/schedule_list.css?v=1.0.3" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
{{--        <span><a href="/admin/store/manage/list" style="color:  black">门店列表（${store->store_name!}）</a> /</span>--!}
        <span><a href="/admin/store/services/technician/list?store_id=${store_id!}"  style="color:  #666">${technician_title!}管理</a>/</span>
        <span style="color: #666;">排班配置</span>
        {{--<span>预约管理 / </span>--!}
        {{--<span style="color: #666;">排班配置</span>--!}
    </div>
    <div class="main-container">
        <form action="" id="form1" method="post">
            <div class="schedule">
                <div class="schedule_head">
                    {{--<span class="btn_add">添加排班</span>--!}
                    <span class="btn_set">设置班次</span>
                    门店营业时间为：<#if ($data_time["business_type"])每天<#else>工作日</#if> ${data_time["work_time"]!}。
                </div>
                <input type="text" id="store_id" hidden value="${store_id!}">
                <input type="text" id="technician_id" hidden value="${technician_info->id!}">
                <input type="text" id="business_type" hidden value="${data_time["business_type"]!}">
                <div class="schedule_table">
                    <div class="schedule_table_head">
                        <div class="st_head_week clearfix">
                            <img src="/image/admin/sche_prev.png" class="sche_prev" id="last-week" />
                            <span class="st_head_week_span">
                                <span id="curday"></span>
                                -
                                <span id="curday7"></span>
                            </span>
                            <img src="/image/admin/sche_next.png" class="sche_next" id="next-week" />
                        </div>
                        <a href="##" class="this_week" id="this_week">本周</a>
                    </div>
                    <div class="schedule_table_con">
                        <table width="100%" class="schedule_tech_table">
                            <tr id="monitor">
                                <th>${technician_title!}</th>
                                <th>周一
                                    <div>02-26</div>
                                </th>
                                <th>周二
                                    <div>02-27</div>
                                </th>
                                <th>周三
                                    <div>02-28</div>
                                </th>
                                <th>周四
                                    <div>03-01</div>
                                </th>
                                <th>周五
                                    <div>03-02</div>
                                </th>
                                <th>周六
                                    <div>03-03</div>
                                </th>
                                <th>周日
                                    <div>03-04</div>
                                </th>
                                <th>操作</th>
                            </tr>
{{--                            {{print_r($technician_schedule_list)!}--!}
                            <tr id="tech_schedule">
                                <td><span class="tech_name" id="${technician_info->id!}">${technician_info->technician_name!}</span></td>
                                <#list ($technician_schedule_list as $item)
                                    <#if ($item && $item->schedule_id > 0)
                                        <td><span class="sche_span">${item->schedule_name!}</span><div>${item->begin_time!}-${item->end_time!}</div></td>
                                    <#else>
                                        <td><span class="sche_span">无排班</span><div></div></td>
                                    </#if>
                                </#list>
                                <#if (!$data_time["business_type"])
                                    <td><span class="sche_span">无排班</span><div></div></td>
                                    <td><span class="sche_span">无排班</span><div></div></td>
                                </#if>
                                <td><a href="##" class="operate_sche">编辑</a></td>
                            </tr>
                        </table>
                        {{--<table width="100%" border="0" class="tb_paging">--!}
                            {{--<tr>--!}
                                {{--<td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}--!}
                                    {{--<a href="#" onclick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>--!}
                                    {{--<a href="#"--!}
                                       {{--onclick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>--!}
                                    {{--<a href="#"--!}
                                       {{--onclick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>--!}
                                    {{--<a href="#"--!}
                                       {{--onclick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>--!}
                                    {{--<input id="page" name="page" type="text" value="${data_list->currentPage()!}"--!}
                                           {{--size="5"--!}
                                           {{--onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}--!}
                                {{--</td>--!}
                            {{--</tr>--!}
                        {{--</table>--!}
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
{{--<div id="add-schedule">--!}
    {{--<div class="add_sche_head">--!}
        {{--<span class="add_title">${technician_title!}</span>--!}
        {{--<select name="" id="add_tech" class="add_select">--!}
            {{--<option value="">请选择分类</option>--!}
            {{--<#list ($technician_list as $technician)--!}
                {{--<option value="${technician->id!}">${technician->technician_name!}</option>--!}
            {{--</#list>--!}
        {{--</select>--!}
    {{--</div>--!}
    {{--<div class="add_sche_con">--!}
        {{--<p>排班规则(按周循环，修改后从明天开始生效)</p>--!}
        {{--<div class="schedule_week">--!}
            {{--<span>--!}
                {{--<span class="add_title">周一：</span>--!}
                {{--<select name="" id="" class="add_select add_select1">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span style="margin: 15px 15px 0;">--!}
                {{--<span class="add_title">周二：</span>--!}
                {{--<select name="" id="" class="add_select">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span>--!}
                {{--<span class="add_title">周三：</span>--!}
                {{--<select name="" id="" class="add_select">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span>--!}
                {{--<span class="add_title">周四：</span>--!}
                {{--<select name="" id="" class="add_select">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span style="margin: 15px 15px 0;">--!}
                {{--<span class="add_title">周五：</span>--!}
                {{--<select name="" id="" class="add_select">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<#if ($data_time["business_type"])--!}
            {{--<span>--!}
                {{--<span class="add_title">周六：</span>--!}
                {{--<select name="" id=""  class="add_select">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span>--!}
                {{--<span class="add_title">周日：</span>--!}
                {{--<select name="" id="" class="add_select">--!}
                    {{--<option value="无排班"  schedule_id="0"  type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--</#if>--!}
        {{--</div>--!}
    {{--</div>--!}
{{--</div>--!}
<div class="shift_manage" id="shift-manage">
    <div class="add_sche_con">
        <p>店铺营业时间为：<#if ($data_time["business_type"])每天<#else>工作日</#if> ${data_time["work_time"]!}</p>
        <div>
            <input type="text" placeholder="请输入班次名称" class="ipt_shifts" />
            <select name="" id="" class="add_select sel_begin">
                <option value="">请选择开始时间</option>
                <#if (is_array($data_time["begin_time_list"]))
                    <#list ($data_time["begin_time_list"] as $begin_time)
                        <option value="${begin_time!}">${begin_time!}</option>
                    </#list>
                </#if>
            </select>
            <select name="end_time" id="" class="add_select sel_end">
                <option value="">请选择结束时间</option>
                <#if (is_array($data_time["end_time_list"]))
                    <#list ($data_time["end_time_list"] as $end_time)
                        <option value="${end_time!}">${end_time!}</option>
                    </#list>
                </#if>
            </select>
            <span class="sm_btn_add">添加</span>
        </div>
    </div>
    <div class="shifts_list">
        <#list ($schedule_list as $schedule)
        <div class="shifts_list_li">
            <input name="schedule_id" type="text" hidden value="${schedule->schedule_id!}" />
            <input name="schedule_name" type="text" value="${schedule->schedule_name!}" />
            <select name="begin_time" class="add_select add_begin">
                <option value="${schedule->begin_time!}">${schedule->begin_time!}</option>
                <#if (is_array($data_time["begin_time_list"]))
                    <#list ($data_time["begin_time_list"] as $begin_time)
                        <option value="${begin_time!}">${begin_time!}</option>
                    </#list>
                </#if>
            </select>
            <select name="end_time" class="add_select add_end">
                <option value="${schedule->end_time!}">${schedule->end_time!}</option>
                <#if (is_array($data_time["end_time_list"]))
                    <#list ($data_time["end_time_list"] as $end_time)
                        <option value="${end_time!}">${end_time!}</option>
                    </#list>
                </#if>
            </select>
            <span class="sm_btn_save">保存</span>
            <span class="sm_btn_del">删除</span>
        </div>
        </#list>
    </div>
</div>
<div style="display: none">
    <div class="shift_clone shifts_list_li">
        <input name="schedule_id" type="text" hidden value="${schedule->schedule_id!}" />
        <input name="schedule_name" type="text" value="${schedule->schedule_name!}" />
        <select name="begin_time" class="add_select add_begin">
            <option value="${schedule->begin_time!}">${schedule->begin_time!}</option>
            <#if (is_array($data_time["begin_time_list"]))
                <#list ($data_time["begin_time_list"] as $begin_time)
                    <option value="${begin_time!}">${begin_time!}</option>
                </#list>
            </#if>
        </select>
        <select name="end_time" class="add_select add_end">
            <option value="${schedule->end_time!}">${schedule->end_time!}</option>
            <#if (is_array($data_time["end_time_list"]))
                <#list ($data_time["end_time_list"] as $end_time)
                    <option value="${end_time!}">${end_time!}</option>
                </#list>
            </#if>
        </select>
        <span class="sm_btn_save">保存</span>
        <span class="sm_btn_del">删除</span>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/shedule_list.js?v=1.0.1"></script>
<script type="text/javascript" src="/js/admin/date.js?v=1.0.1"></script>
{{--<script>--!}
    {{--function gopage(page) {--!}
        {{--var last_page = '${data_list->lastPage()!}';--!}
        {{--if(page>last_page){--!}
            {{--page = last_page;--!}
        {{--}--!}
        {{--$("#page").val(page);--!}
        {{--$("#form1").submit();--!}
    {{--}--!}
    {{--var page_home = '${data_list->currentPage()!}'; //当前页码数--!}
    {{--var page_all = '${data_list->count!}'            //总页码数--!}
{{--</script>--!}
{{--<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>--!}
