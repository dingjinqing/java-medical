<#include "/system/header.ftl">

<div class="box panel panel-body">
    <table align="center" width="">
        <tr>
            <th rowspan="2"><img src="http://${image_domain}/image/system/info.png" width="50" height="50" border="0"/></th>
            <td><h3 style="font-weight:bolder;margin:0px;padding:0px;">${message!}</h3></td>
        </tr>
        <tr>
            <td id="navi_hint">
                <span id="hint_sec">30</span>秒后，自动返回上一页
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <ul style="margin:0px;padding:14px;border-top:1px solid #e4e4e4;">
                    <#if links?? >
                        <#list links as link >
                            <li style="list-style-type:lower-latin;">
                                <a href="{{ $link['href'] }"
                                   <#if link['target']?? >target="${link['target']}"</#if>>${link['text']}</a>
                            </li>
                        </#list>
                    <#else>
                        <li style="list-style-type:lower-latin;">
                            <a href="javascript:history.go(-1)"
                               onclick="javascript:history.go(-1);">返回</a>
                        </li>
                    </#if>
                </ul>

            </td>
        </tr>
    </table>
</div>


<#noparse>
<script language="JavaScript">

    var sec_count = 30;
    var def_navi = 'javascript:history.go(-1)';
    var timer_id = 0;
    onload = function () {
        if (def_navi == 'javascript:history.go(-1)' && window.history.length == 0) {
            $('#navi_hint').innerHTML = '';
            return;
        }
        timer_id = window.setInterval(navi_check, 1000);
    }

    function navi_check() {
        if (sec_count <= 0) {
            window.clearInterval();
            return;
        }
        sec_count--;
        var sec_hint = $('#hint_sec');
        sec_hint.html(sec_count);
        if (sec_count == 0) {
            window.clearInterval(timer_id);
            location.href = def_navi;
        }
    }
</script>
</#noparse>

<#include "/system/footer.ftl">