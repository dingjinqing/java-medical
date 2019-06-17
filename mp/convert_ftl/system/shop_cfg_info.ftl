<#include ("system.header")

<ul id="tab" class="nav nav-tabs">

    <li <#if ($nav_type==0)class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/cfg/list">配置信息列表</a>
    </li>
    <li <#if ($nav_type==2)class="active"</#if>><a href="#" data-toggle="tab" url="/system/shop/cfg/add">配置信息添加</a>
    </li>
    <#if ($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="#">配置信息编辑</a></li>
    </#if>
</ul>
<script>
    // tab切换
    $("[data-toggle='tab']").click(function () {
        var url = $(this).attr("url");
        if (url != undefined) {
            window.location.href = url;
        }
    });
</script>

<form enctype="multipart/form-data" action="${act_url!}" name="form1" id="form1" method="post"
      onsubmit="return on_submit();">
    <input type="hidden" name="act" id="act" value="${act!}">
    <input type="hidden" name="" id="" value="${shop_cfg->!}">
    {{ csrf_field()!}
    <div class="box panel">
        <div class="panel-body">
            <table class="tab_body show table">
                

                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input type="submit" value="保存" class="primary">&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>

            </table>

        </div>
    </div>
</form>

<script>
    function on_submit() {
        // 写入验证代码
        return true;
    }

</script>


<#include ("system.footer")
