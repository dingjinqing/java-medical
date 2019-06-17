<div class="nav-goods">
    <ul id="tab" class="nav-fee-tabs">

        <li <#if ($nav_type==0)class="deliver_active"</#if>>
            <a href="#" data-toggle="tab" url="/admin/goods/deliver/template/list">
                {{ trans("admin/deliver_fee.fee_top.top_name")!}
            </a>
        </li>
        <li <#if ($nav_type==1)class="deliver_active"</#if>>
            <a href="#" data-toggle="tab" url="/admin/goods/deliver/template/weight/list">
                {{ trans("admin/deliver_fee.fee_top.weight_fee_name")!}
            </a>
        </li>
        <li <#if ($nav_type==2)class="deliver_active"</#if>>
            <a href="#" data-toggle="tab" url="/admin/goods/deliver/template/add">
                {{ trans("admin/deliver_fee.fee_top.add_fee_name")!}
            </a>
        </li>
        <li <#if ($nav_type==4)class="deliver_active"</#if>>
            <a href="#" data-toggle="tab" url="/admin/goods/deliver/template/weight/add">
                {{ trans("admin/deliver_fee.fee_top.add_weight_fee_name")!}
            </a>
        </li>
        <#if ($nav_type==3)
            <li class="deliver_active"><a href="#" data-toggle="tab" url="#">运费模板编辑</a></li>
        </#if>
        <#if ($nav_type==5)
            <li class="deliver_active"><a href="#" data-toggle="tab" url="#">编辑重量运费模板 </a></li>
        </#if>
    </ul>
</div>
