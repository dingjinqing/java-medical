<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css" />
<link rel="stylesheet" href="/css/admin/add_assess.css?v=1.0.0" type="text/css" />
<div style="min-width: 1090px;">
    <div class="title">
        <div>
            <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
            <span style="color: #666;">${title!}</span>
        </div>
    </div>
    <div class="main-container fix_every_footer">
        <div class="nav-role">
            <ul id="tab" class="nav-child-tabs">
                <li>
                    <a href="/admin/market/assess/list?nav=0">全部测评</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=1">进行中</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=2">未开始</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=3">已过期</a>
                </li>
                <li>
                    <a href="/admin/market/assess/list?nav=4">已停用</a>
                </li>
                <li class="active">
                    <a href="javascript:;">添加评测</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="act" value="${act ?? 'add'!}">
            <input type="hidden" name="assess_id" value="${assess_id ?? 0!}">
            <div class="assess_container">
                <div class="assess_content clearfix">
                    <div class="fl left_content">
                        <div class="fl_title">
                            <div>测评</div>
                        </div>
                        <div class="assess_preview">
                            <div class="preview_top">
                                <div class="bg_1"></div>
                                <div class="bg_2"></div>
                                <div class="preview_title"></div>
                                <div class="preview_pic">
                                    <div class="no_pic_style">
                                        <img src="http://${image_domain!}/image/admin/assess_no_pic.png" alt="">
                                        <span>
                                            测试封面
                                        </span>
                                    </div>
                                    <img src="" alt="" style="display: none;">
                                </div>
                                <div class="preview_depict">
                                    <p class="preview_depict_title">测试简介：</p>
                                    <div class="depict_content">

                                    </div>
                                </div>
                            </div>
                            <div class="preview_button">
                                <span>开始测评</span>
                            </div>
                        </div>
                    </div>
                    <div class="right_content">
                        <div class="assess_set_info">
                            <div class="assess_set_title">活动配置</div>
                            <ul class="assess_set_ul">
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>活动时间：</div>
                                    <div class="li_right">
                                        <p><label><input type="radio" name="due_time_type" <#if ($info->due_time_type == 0) checked </#if> value="0"><span>固定时间</span></label> <label style="margin-left:15px;"><input type="radio" name="due_time_type" <#if ($info->due_time_type == 1) checked </#if> value="1"><span>永久有效</span></label></p>
                                        <p>
                                        <input type="text" name="start_time" onfocus="picker()" placeholder="开始时间" autocomplete="off" value="${info->start_time!}"> 至
                                        <input type="text" name="end_time" onfocus="picker()" placeholder="结束时间"  autocomplete="off" value="${info->end_time!}">
                                        </p>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>单用户参与次数：</div>
                                    <div class="li_right">
                                        <label>
                                            <input type="radio" name="part_times_type" value="0" <#if ($info->part_times_type == 0) checked </#if>>不限制
                                        </label>
                                        <label style="margin-left: 30px;">
                                            <input type="radio" name="part_times_type" value="1" <#if ($info->part_times_type == 1) checked </#if>>限制次数
                                        </label>
                                        <div class="partake_set">
                                            <p>每天最多参与：<input type="text" name="part_times_day" class="small_input" value="${info->part_times_day ?? 1!}"
                                                    onkeyup="value=value.replace(/[^\d]/g,'')">　次</p>
                                            <p>累计最多参与：<input type="text" name="part_times_total" class="small_input" value="${info->part_times_total ?? 1!}"
                                                    onkeyup="value=value.replace(/[^\d]/g,'')">　次</p>
                                            <p class="tips">默认为1，0表示不限制，累计次数必须大于每天参与次数</p>
                                        </div>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>活动总反馈数量：</div>
                                    <div class="li_right">
                                        达 <input type="text" name="feedback_total" class="small_input" value="${info->feedback_total ?? 0!}"
                                            onkeyup="value=value.replace(/[^\d]/g,'')"> 次后不可提交
                                        <p class="tips">默认为0，0表示不限制</p>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>结果判断条件：</div>
                                    <div class="li_right" style="overflow: unset">
                                        <p>
                                            <label>
                                                <input type="radio" name="assess_judge_type" value="0" <#if ($info->assess_judge_type == 0) checked </#if> <#if ($act == 'edit') disabled </#if>>
                                                <span style="margin:0 4px">根据选项判断</span>
                                            </label>
                                            <span class="tips">用户选择指定的选项后，跳转到指定结果页面</span>
                                        </p>
                                        <p>
                                            <label>
                                                <input type="radio" name="assess_judge_type" value="1" <#if ($info->assess_judge_type == 1) checked </#if> <#if ($act == 'edit') disabled </#if>>
                                                <span style="margin:0 4px">根据得分判断</span>
                                            </label>
                                            <span class="tips">用户答题得分在指定分数区间时，跳转到指定结果页面</span>
                                        </p>
                                        <p class="tips" style="margin-left: -29px;">注：(1)创建题目及结果之后切换条件，原条件下创建的题目及结果不会删除
                                        </p>
                                        <p class="tips">(2)切换条件后需要在该条件下重新创建题目及结果</p>
                                        <#if ($act == 'edit')
                                        <input type="hidden" name="assess_judge_type" value="${info->assess_judge_type!}">
                                        </#if>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="assess_set_info">
                            <div class="assess_set_title">选项设置</div>
                            <ul class="assess_set_ul">
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>测试名称：</div>
                                    <div class="li_right">
                                        <input type="text" name="act_name" id="act_name" value="${info->act_name!}"> <span
                                            class="tips">最多可设置10个字</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>封面样式：</div>
                                    <div class="li_right">
                                        <label>
                                            <input type="radio" name="cover_style_type" value="0" <#if ($info->cover_style_type == 0) checked </#if>> 默认样式
                                        </label>
                                        <label style="margin-left: 30px;">
                                            <input type="radio" name="cover_style_type" value="1" <#if ($info->cover_style_type == 1) checked </#if>>自定义样式
                                        </label>
                                    </div>
                                </li>
                                <li class="clearfix assess_cover_style">
                                    <div class="li_left"><em>*</em>测试封面：</div>
                                    <div class="li_right img_set" style="margin-top: 10px;">
                                        <input type="hidden" name="cover_img" value="${info->cover_style->cover_img ?? ''!}">
                                        <div class="upload_img" style="display: none;">
                                            <img src="${info->cover_style->cover_img!}" alt="">
                                            <span class="choose_img">重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_image" style="display: block;">
                                        <span
                                            style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：690px
                                            * 440px</span>
                                    </div>
                                </li>
                                <li class="clearfix assess_page_style">
                                    <div class="li_left"><em>*</em>测试页面：</div>
                                    <div class="li_right img_set" style="margin-top: 10px;">
                                        <input type="hidden" name="page_img" value="${info->cover_style->page_img ?? ''!}">
                                        <div class="upload_img" style="display: none;">
                                            <img src="${info->cover_style->page_img!}" alt="">
                                            <span class="choose_img">重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_image" style="display: block;">
                                        <span
                                            style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：750px
                                            * 1334px</span>
                                    </div>
                                </li>
                                <li class="clearfix assess_button_style">
                                    <div class="li_left"><em>*</em>开始测试按钮：</div>
                                    <div class="li_right img_set" style="margin-top: 10px;">
                                        <input type="hidden" name="start_button_img" value="${info->cover_style->start_button_img ?? ''!}">
                                        <div class="upload_img" style="display: none;">
                                            <img src="${info->cover_style->start_button_img!}" alt="">
                                            <span class="choose_img">重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_image" style="display: block;">
                                        <span
                                            style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：650px
                                            * 86px</span>
                                    </div>
                                </li>
                                <li class="clearfix assess_next_button_style">
                                    <div class="li_left"><em>*</em>下一题按钮：</div>
                                    <div class="li_right img_set" style="margin-top: 10px;">
                                        <input type="hidden" name="next_button_img" value="${info->cover_style->next_button_img ?? ''!}">
                                        <div class="upload_img" style="display: none;">
                                            <img src="${info->cover_style->next_button_img!}" alt="">
                                            <span class="choose_img">重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_image" style="display: block;">
                                        <span
                                            style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：650px
                                            * 86px</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>测试简介：</div>
                                    <div class="li_right" style="margin-top: 10px;">
                                        <textarea name="assess_desc" cols="54" rows="8" maxlength="300"
                                            style="line-height: 1;resize: none;border-color: #ccc;">${info->cover_style->assess_desc!}</textarea>
                                        <p class="tips">最多可填写300字</p>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>按钮样式：</div>
                                    <div class="li_right">
                                        <p>文字内容　
                                            <input type="text" name="button_name" id="button_name" value="${info->cover_style->button_name!}">
                                            <span class="tips">最多可填写5字</span>
                                        </p>
                                        <p>文字颜色　
                                            <input type="color" name="button_text_color" value="${info->cover_style->button_text_color ?? '#ffffff'!}"
                                                default_color="${info->cover_style->button_text_color ?? '#ffffff'!}">　
                                            <a href="javascript:;" class="reset_color">重置</a>
                                        </p>
                                        <p>文字背景　
                                            <input type="color" name="button_bg_color" value="${info->cover_style->button_bg_color ?? '#cccccc'!}" default_color="${info->cover_style->button_bg_color ?? '#cccccc'!}">　
                                            <a href="javascript:;" class="reset_color">重置</a>
                                        </p>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>封面背景：</div>
                                    <div class="li_right">
                                        <p>
                                            <label>
                                                <input type="radio" name="assess_cover_bg" value="0" <#if ($info->cover_style->assess_cover_bg == 0)) checked </#if>>默认背景
                                            </label>
                                            <label style="margin-left: 30px;">
                                                <input type="radio" name="assess_cover_bg" value="1" <#if ($info->cover_style->assess_cover_bg == 1)) checked </#if>>自定义
                                            </label>
                                        </p>
                                        <div class="cover_bg_set img_set">
                                            <input type="hidden" name="cover_bg_img" value="${info->cover_style->cover_bg_img ?? ''!}">
                                            <div class="upload_img" style="display: none;">
                                                <img src="${info->cover_style->cover_bg_img!}" alt="">
                                                <span class="choose_img">重新选择</span>
                                            </div>
                                            <input type="button" value="" class="add_image" style="display: block;">
                                            <span
                                                style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：750px
                                                * 1334px</span>
                                        </div>

                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer" style="width: 1199px; display: block;">
            <a class="save" href="javascript:void(0);">下一步</a>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/add_assess.js?v=1.0.0.7"></script>
<script>
function picker(){
    hasSaved = false;
    return WdatePicker(
        {
            dateFmt: 'yyyy-MM-dd HH:mm:ss',
            autoUpdateOnChanged: false
        }
    );
}

let act_name = $('#act_name').get(0);
act_name.addEventListener('compositionstart', function(){
    flag = true;
});
act_name.addEventListener('compositionend',function(){
    flag = false
    limitLength(this.value,'20','','act_name')
});
act_name.addEventListener('input',function(){
    limitLength(this.value,'20','','act_name')
});
let button_name = $('#button_name').get(0);
button_name.addEventListener('compositionstart', function(){
    flag = true;
});
button_name.addEventListener('compositionend',function(){
    flag = false
    limitLength(this.value,'10','','button_name')
});
button_name.addEventListener('input',function(){
    limitLength(this.value,'10','','button_name')
});
function limitLength(value, byteLength, title, attribute) { 
    if(!flag){
        var newvalue = value.replace(/[^\x00-\xff]/g, "**");               
        var length = newvalue.length;
        if (length * 1 <=byteLength * 1){ 
                return; 
        } 
        var limitDate = newvalue.substr(0, byteLength); 
        var count = 0; 
        var limitvalue = ""; 
        for (var i = 0; i < limitDate.length; i++) { 
                var flat = limitDate.substr(i, 1); 
                if (flat == "*") { 
                    count++; 
                } 
        } 
        var size = 0; 
        var istar = newvalue.substr(byteLength * 1 - 1, 1);
    
        if (count % 2 == 0) { 
                size = count / 2 + (byteLength * 1 - count); 
                limitvalue = value.substr(0, size); 
        } else { 
                size = (count - 1) / 2 + (byteLength * 1 - count); 
                limitvalue = value.substr(0, size); 
        }
        document.getElementById(attribute).value = limitvalue; 
        return; 
    }         
}
</script>
<script type="text/javascript">
    getPowerInfo('main_config','assess','sub_4','测评',0);
</script>