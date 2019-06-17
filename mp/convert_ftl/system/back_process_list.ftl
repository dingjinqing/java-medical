<#include ("system.header")

<form action="/system/back/process/list" name="form1" id="form1" method="post">
    <input type="hidden" name="act" id="act" value="">
    <input type="hidden" name="page" value="">
    <input type="hidden" name="rec_id" id="rec_id" value="">
    {{ csrf_field()!}
    <div class="box panel ">
        <div class="panel-body form-inline">
            执行状态： <select name="state" class="form-control">
                <option value="-1">-- 请选择 --</option>
                <option value="0" <#if ($state==0) selected </#if>>初始</option>
                <option value="1" <#if ($state==1) selected </#if>>执行中</option>
                <option value="2" <#if ($state==2) selected </#if>>执行完成</option>
                <option value="3" <#if ($state==3) selected </#if>>执行失败</option>
                <option value="4" <#if ($state==4) selected </#if>>执行停止</option>
            </select>
        </div>
    </div>
    <div class="box panel main-table">
        <div class="layer">
            <table cellspacing='1' cellpadding='3' width="100%" class="table" id="main-table" style="word-wrap: break-word; word-break: break-all;">
                <thead>
                <tr>
                    <th>任务名称</th>
                    <th width="20%">参数</th>
                    <th>状态</th>
                    <th>失败原因</th>
                    <th width="200px">进度</th>
                    <th>进度信息</th>
                    <th>备注</th>
                    <th>创建时间</th>
                    <th>结束时间</th>
                    <th width="50px">操作</th>
                </tr>
                </thead>

                <#list ($data_list as $item)
                    <tr style="text-align:center;" rec_id="${item->rec_id!}" state="${item->state!}">
                        <td>${item->job_name!}</td>
                        <td><#if (!empty($item->parameters)) @json($item->parameters) </#if></td>
                        <td>${item->state_name!}</td>
                        <td>${item->fail_reason!}</td>
                        <td>
                            <div class="progress" title="${item->progress!}%" style="margin-bottom: 0">
                                <div class="progress-bar progress-bar-success" role="progressbar"
                                     aria-valuenow="${item->progress!}"
                                     aria-valuemin="0" aria-valuemax="100"
                                     style="width: ${item->progress!}%;min-width: 1%;"
                                     >
                                    ${item->progress!}%
                                </div>
                            </div>
                        </td>
                        <td>${item->progress_info!}</td>
                        <td>${item->memo!}</td>
                        <td>${item->created!}</td>
                        <td>${item->end_time!}</td>
                        <td>
                            <#if (in_array($item->state,[0,1]))
                                <a href="javascript:void(0);" class="stop-item"
                                   onclick="stop('${item->rec_id!}')">强行终止</a>&nbsp;&nbsp;
                            </#if>
                        </td>
                    </tr>
                </#list>
            </table>
            <table cellspacing='1' cellpadding='3' width="100%" class="bottom-table">
                <tr>
                    <td>
                    </td>
                    <td align="right">
                        <table width="100%" border="0">
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
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>

</form>


<script>
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if (page > last_page) {
            page = last_page;
        }
        $("#page").val(page);
        $("#form1").submit();
    }


    function stop(id) {
        if (confirm("确定强行停止此任务吗？")) {
            $("#act").val('stop');
            $("#rec_id").val(id);
            $("#form1").submit();
        }
    }

    function refresh_progress() {
        var timer = setInterval(function () {
            var tr_els = $("tr[rec_id][state='1']");
            if (tr_els.length == 0) {
                clearInterval(timer);
            } else {
                var rec_ids = [];
                tr_els.each(function () {
                    rec_ids.push($(this).attr("rec_id"));
                });
                var param = {rec_ids: rec_ids.join(",")};
                util.show_loading = false;
                util.ajax_json("/system/back/process/progress", function (d) {
                    if (d && d.error == 0) {
                        for (var j in d.content) {
                            fill_tr_data(d.content[j])
                        }
                    }
                }, param)
            }
        }, 1000);
    }

    function fill_tr_data(data) {
        var el = $("tr[rec_id='" + data.rec_id + "']");
        el.attr("state", data.state);
        el.find("td:nth-child(3)").text(data.state_name);
        el.find("td:nth-child(4)").text(data.fail_reason);
        var progress_bar = el.find(".progress-bar");
        progress_bar.text(data.progress + "%");
        progress_bar.attr("aria-valuenow", data.progress);
        progress_bar.parent().attr("title", data.progress + "%");
        progress_bar.css("width", data.progress + "%");
        el.find("td:nth-child(6)").text(data.progress_info);
        el.find("td:nth-child(7)").text(data.memo ? data.memo : "");
        el.find("td:nth-child(9)").text(data.end_time);
        if (data.state == 2 || data.state == 3 || data.state == 4) {
            el.find(".stop-item").hide();
        }
    }

    refresh_progress();

    $('[name="state"]').change(function () {
        $("#page").val(1);
        $("#form1").submit();
    });
    // $("#main-table").FixedHead({tableLayout: "fixed"});
</script>

<#include ("system.footer")
