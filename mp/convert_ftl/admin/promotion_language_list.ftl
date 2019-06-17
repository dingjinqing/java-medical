<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/distribution.css?v=1.0.8" type="text/css" />
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span><span>分销 / </span><span style="color: #666;">${title!}</span>
</div>
<style>
    .btn_exel{
        width: 85px;
        height: 30px;
        border: 1px solid #ccc;
        background: #f5f5f5;
        color: #666;
        margin-left: 15px;
        display: inline-block;
        text-align: center;
    }
</style>
<form action="/admin/market/distribution/promotionLanguage/list" method="post" id="form1" style="padding-bottom: 100px">
    {{ csrf_field()!}
    <input type="hidden" name="act">
    <div class="reserve-container">
        <div class="pages_nav clearfix">
            <#include ("admin.distributio_title")
        </div>
        <div class="promotion_filter">
            <ul>
                
                <li class="clearfix">
                    <div class="login_time re_li">
                        <span>创建时间</span>
                        <input type="text" onclick="picker()" name="in_start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['in_start_time']!}" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type="text" onclick="picker()" name="in_end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['in_end_time']!}" autocomplete="off">
                    </div>
                    <div class="login_time re_li">
                        <span>最后修改时间</span>
                        <input type="text" onclick="picker()" name="up_start_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['up_start_time']!}" autocomplete="off">
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type="text" onclick="picker()" name="up_end_time" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" value="${request['up_end_time']!}" autocomplete="off">
                    </div>
                    <div class="re_li">
                        <span>推广语内容</span>
                        <input type="text" name="promotion_language" value="${request['promotion_language']!}" placeholder="请输入推广语">
                    </div>
                    <a href="##" class="btn_filter">筛选</a>
                </li>
                <li class="clearfix">
                    <a href="##" class="btn_add">添加推广语</a>
                </li>
            </ul>
        </div>
        <div class="promotion_table" style="margin-bottom: 0px;min-height: 380px">
            <table width="100%">
                <thead>
                <tr>
                    <td width="10%">推广语标题</td>
                    <td >推广语内容</td>
                    <td width="15%">创建时间</td>
                    <td width="15%">更新时间</td>
                    <td width="10%">状态</td>
                    <td width="15%">操作</td>
                </tr>
                </thead>
                <tbody>
                <#list ($data_list as $item)
                    <tr lan_id="${item->id!}">
                        <td>${item->title!}</td>
                        <td style="text-align: left;">${item->promotion_language!}</td>
                        <td width="15%">${item->in_time!}</td>
                        <td width="15%">${item->up_time!}</td>
                        <td width="10%"><#if ($item->is_block == 1)已停用 <#else> 已启用 </#if></td>
                        <td width="15%">
                            <a href="#" class="btn_edit">编辑</a>
                            <a href="#" class="btn_block" is_block="${item->is_block!}"><#if ($item->is_block == 1)启用<#else>停用</#if></a>
                            <a href="#" class="btn_del">删除</a>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="paging_footer" style="margin-top: 12px">
                <#include "/admin/jump_page_admin.ftl">
            </div>
        </div>
    </div>
</form>
<div id="set-language" class="exchange-num" style="margin: 15px;display: none;">
    <input type="hidden" class="user_id"/>
    <div class="exchange_old">
        <span>标题：<input type="text" name="title" maxlength="10"></span>
        <br>
        <span>请输入分销员推广语，将帮助分销员朋友圈推广:</span>
    </div>
    <div class="text_div">
        <textarea maxlength="200" placeholder="请输入" class="textarea"></textarea>
        <span class="font_num"><span class="number">0</span>/200</span>
    </div>
    
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript" src="{{asset('js/admin/promotion_language_list.js')!}?v=1.1.11"></script>
<script type="text/javascript">
    function picker() {
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }
    
    $(".btn_filter").click(function () {
        $("#form1").submit();
    })
    

    getPowerInfo('main_config','distribution','sub_4','分销',0);
</script>
