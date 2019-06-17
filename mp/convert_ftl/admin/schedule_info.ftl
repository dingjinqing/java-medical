<link rel="stylesheet" href="/css/admin/schedule_list.css?v=1.0.2" type="text/css" />
<div id="add-schedule2">
    {{--<div class="add_sche_head">--!}
        {{--<span class="add_title">技师</span>--!}
        {{--<select name="" id="add_tech2" class="add_select">--!}
            {{--<option value="">请选择分类</option>--!}
            {{--<option value="河图">河图</option>--!}
            {{--<option value="张三">张三</option>--!}
            {{--<option value="李四">李四</option>--!}
        {{--</select>--!}
    {{--</div>--!}
    <div class="add_sche_con">
        {{--<p>排班规则(按周循环，修改后从明天开始生效)</p>--!}
        <div class="schedule_week">
            <#list ($technician_schedule_list as $key => $technician_schedule)
            <span>
                <span class="add_title">${key!}：</span>
                <select name="" id="" class="add_select add_select1">
                    <option value="无排班" schedule_id="0" type="0">无排班</option>
                    <#list ($schedule_list as $schedule)
                        <option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" <#if ($technician_schedule->schedule_id == $schedule->schedule_id)selected </#if> type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>
                    </#list>
                </select>
            </span>
            </#list>
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
                {{--<select name="" id="" class="add_select add_select1">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span>--!}
                {{--<span class="add_title">周三：</span>--!}
                {{--<select name="" id="" class="add_select add_select1">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span>--!}
                {{--<span class="add_title">周四：</span>--!}
                {{--<select name="" id="" class="add_select add_select1">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span style="margin: 15px 15px 0;">--!}
                {{--<span class="add_title">周五：</span>--!}
                {{--<select name="" id="" class="add_select add_select1">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span>--!}
                {{--<span class="add_title">周六：</span>--!}
                {{--<select name="" id="" class="add_select add_select1">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
            {{--<span>--!}
                {{--<span class="add_title">周日：</span>--!}
                {{--<select name="" id="" class="add_select add_select1">--!}
                    {{--<option value="无排班" schedule_id="0" type="0">无排班</option>--!}
                    {{--<#list ($schedule_list as $schedule)--!}
                        {{--<option value="${schedule->schedule_name!}" schedule_id="${schedule->schedule_id!}" data-time="10:00-19:30" type="1">${schedule->schedule_name!} (${schedule->begin_time!}-${schedule->end_time!})</option>--!}
                    {{--</#list>--!}
                {{--</select>--!}
            {{--</span>--!}
        {{--</div>--!}
    </div>
</div>