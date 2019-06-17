<ul id="tab" class="nav nav-tabs">
    <li <#if ($nav_type==0)class="active"</#if>>
        <a href="#" data-toggle="tab"
           onclick="location.href = '/system/mp/version';">小程序模板版本管理</a>
    </li>

    <li <#if ($nav_type==1) class="active" </#if>>
        <a href="#" data-toggle="tab" url="#"
           onclick="location.href = '/system/mp/operate/log/list';">小程序版本操作日志</a>
    </li>

    <li <#if ($nav_type==2) class="active" </#if>>
        <a href="#" data-toggle="tab" url="#"
           onclick="location.href = '/system/mp/auth/list';">小程序授权列表</a>
    </li>

    <li <#if ($nav_type==3) class="active" </#if>>
        <a href="#" data-toggle="tab" url="#"
           onclick="location.href = '/system/mp/version/stat';">小程序版本统计</a>
    </li>

    <#if ($nav_type==4)
        <li class="active"><a href="#" data-toggle="tab" url="#">小程序授权信息</a></li>
    </#if>
</ul>
