<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/presale_manage.css?v=1.1.1" type="text/css" />
<link rel="stylesheet" href="/css/admin/add_assess.css?v=1.0.0" type="text/css" />
<style>
    .preview_subject_title {
        padding-left: 20px;
        line-height: 56px;
    }

    a,
    a:hover,
    a:link {
        color: #5a8bff;
    }

    select {
        width: 170px;
        line-height: 30px;
        height: 30px;
        border: 1px solid #ccc;
    }

    .subject_box {
        background-color: #fff;
        margin-top: 15px;
        padding: 10px 0;
        border: 1px solid #ddd;
    }

    .assess_content {
        max-width: 1000px;
    }

    .btn_save a {
        color: #fff !important;
        border: 1px solid transparent;
    }

    .del {
        margin: 10px 10px 0 0;
    }

    .subject_title {
        line-height: 30px;
        display: flex;
        border-radius: 6px;
    }

    .subject_title>i {
        vertical-align: middle;
        float: left;
        width: 16px;
        color: #999;
    }

    .subject_title>span {
        vertical-align: middle;
        flex: 1;
        padding-left: 10px;
        color: #333;
    }

    .subject_title.check>span {
        color: #999;
    }

    .subject_title.check {
        background-color: #eee;
    }

    .subject_pic>img {
        height: 150px;
        width: 100%;
        background-color: #ddd;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .subject_pic,
    .preview_title_img {
        height: 150px;
        background-color: #ddd;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .preview_depict {
        padding-top: 10px;
        max-height: 300px;
        overflow-y: scroll;
        padding-right: 4px;
    }

    .preview_depict::-webkit-scrollbar {
        width: 8px;
        height: 8px;
        background: none;
    }

    .preview_depict::-webkit-scrollbar-thumb {
        border: 2px solid transparent;
        -webkit-border-radius: 8px;
        border-radius: 8px;
        background: #eee;
        -webkit-background-clip: content-box;
        background-clip: content-box;
    }

    .preview_subject_title {
        text-align: center;
        padding-left: 0;
        font-weight: 600;
    }

    .assess_content .left_content {
        height: auto;
    }

    .preview_title_img>img {
        width: 100%;
        height: 150px;
    }

    .preview_title {
        border-bottom: none;
    }

    .preview_top {
        overflow: hidden;
        position: unset;
        margin: auto;
        transform: none;
        margin-bottom: 80px;
    }
</style>
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
                    <a href="javascript:;">${title!}</a>
                </li>
            </ul>
        </div>
        <form action="" method="post" id="form1">
            {{csrf_field()!}
            <input type="hidden" name="act" value="${act!}">
            <input type="hidden" name="assess_id" value="${assess_id!}">
            <input type="hidden" name="topic_id" value="${topic_id ?? 0!}">
            <div class="assess_container">
                <div class="assess_content clearfix">
                    <div class="fl left_content">
                        <div class="fl_title">
                            <div>测评</div>
                        </div>
                        <div class="assess_preview">
                            <h4 class="preview_subject_title">${assess_info->act_name!}</h4>
                            <div class="preview_top">
                                <div class="preview_title">
                                    <p>题目名称</p>
                                    <div class="preview_title_img">

                                    </div>
                                </div>
                                <div class="preview_depict">
                                    <p class="subject_title">
                                        <i class="iconfont icondanxuan"></i><span>选项1</span>
                                    </p>
                                    <div class="subject_pic">
                                        <div class="no_pic_style">
                                            <img src="http://${image_domain!}/image/admin/assess_no_pic.png" alt="">
                                            <span>
                                                选项图片
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <#if ($assess_info->cover_style_type == 0)
                                <div class="preview_button" style="background-color: ${assess_info->cover_style->button_bg_color ?? '#cccccc'!};">
                                    <span style="color: ${assess_info->cover_style->button_text_color ?? '#ffffff'!};">下一题</span>
                                </div>
                            <#else>
                                <div class="preview_button" style="background-color: rgb(204, 204, 204); background-image: url('${assess_info->cover_style->next_button_img!}'); background-size: 100% 100%; background-repeat: no-repeat;">
                                    <span><#if (!$assess_info->cover_style->next_button_img)下一题 </#if> </span>
                                </div>
                            </#if>
                        </div>
                    </div>
                    <div class="right_content">
                        <div class="assess_set_info">
                            <div class="assess_set_title">设置题目</div>
                            <ul class="assess_set_ul">
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>题目格式：</div>
                                    <div class="li_right">
                                        <label><input type="radio" name="topic_type" value="0" <#if ($topic_info->topic_type == 0) checked </#if>>文本</label>
                                        <label style="margin-left: 30px"><input type="radio" name="topic_type"
                                                value="1" <#if ($topic_info->topic_type == 1) checked </#if>>图片</label>
                                        <label style="margin-left: 30px"><input type="radio" name="topic_type"
                                                value="2" <#if ($topic_info->topic_type == 2) checked </#if>>视频</label>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>题目：</div>
                                    <div class="li_right">
                                        <input type="text" name="topic_title" value="${topic_info->topic_title!}">
                                        　<span class="tips">最多可填写100字</span>
                                    </div>
                                </li>
                                <li class="clearfix subject_type_pic">
                                    <div class="li_left"><em>*</em>图片：</div>
                                    <div class="li_right img_set" style="margin-top:10px;">
                                        <input type="hidden" name="topic_type_path" value="${topic_info->topic_type_path!}">
                                        <div class="upload_img" style="display: none;">
                                            <img src="${topic_info->topic_type_path!}" alt="${topic_info->topic_type_path!}">
                                            <span class="choose_img">重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_image" style="display: block;">
                                        <span
                                            style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：690px
                                            * 440px</span>
                                    </div>
                                </li>
                                <li class="clearfix subject_type_video">
                                    <div class="li_left"><em>*</em>视频：</div>
                                    <div class="li_right video_set" style="margin-top:10px;">
                                        <input type="hidden" name="topic_type_path" value="">
                                        <div class="upload_img" style="display: none;">
                                            <img src="" alt="">
                                            <span class="choose_video">重新选择</span>
                                        </div>
                                        <input type="button" value="" class="add_video" style="display: block;">
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>题目背景：</div>
                                    <div class="li_right">
                                        <label><input type="radio" name="bg_img_type" value="0" <#if ($topic_info->bg_img_type == 0) checked </#if>>默认背景</label>
                                        <label style="margin-left:30px"><input type="radio" name="bg_img_type"
                                                value="1" <#if ($topic_info->bg_img_type == 1) checked </#if>>自定义</label>
                                        <div class="img_set subject_bg">
                                            <input type="hidden" name="bg_img_path" value="${topic_info->bg_img_path!}">
                                            <div class="upload_img" style="display: none;">
                                                <img src="${topic_info->bg_img_path!}" alt="${topic_info->bg_img_path!}">
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
                        <div class="assess_set_info">
                            <div class="assess_set_title" style="margin-bottom:0;">选项设置</div>
                            <ul class="assess_set_ul">
                                <li class="clearfix">
                                    <div class="li_left"><em>*</em>选项类型：</div>
                                    <div class="li_right">
                                        <label><input type="radio" name="option_type" value="0" <#if ($topic_info->option_type == 0) checked </#if> <#if ($assess_info->pub_flag == 1) disabled </#if>>单选</label>
                                        <label style="margin-left:30px;"><input type="radio" name="option_type"
                                                value="1" <#if ($topic_info->option_type == 1) checked </#if> <#if ($assess_info->pub_flag == 1) disabled </#if>>多选</label>
                                        <span class="tips fr">最多可添加10个选项</span>
                                        <#if ($assess_info->pub_flag == 1) 
                                        <input type="hidden" name="option_type" value="${topic_info->option_type ?? 0!}">
                                        </#if>
                                    </div>
                                </li>
                                <li class="clearfix ending">
                                    <div class="li_left"><em>*</em>跳题逻辑：</div>
                                    <div class="li_right">
                                        选择<select name="option_skip_type" class="skip_type" <#if ($assess_info->pub_flag == 1) disabled </#if>>
                                            <option value="1" <#if ($topic_info->option_skip_type == 1) selected </#if>>跳转到指定题目</option>
                                            <option value="2" <#if ($topic_info->option_skip_type == 2) selected </#if>>跳转到结果页,结束作答</option>
                                        </select>
                                        <#if ($assess_info->pub_flag == 1)
                                        <input type="hidden" name="option_skip_type" value="${topic_info->option_skip_type!}">
                                        </#if>
                                        <span>后跳转到：</span>
                                        <select name="option_skip_value" class="skip_value" <#if ($assess_info->pub_flag == 1) disabled </#if>>
                                            <#if ($topic_info->option_skip_type == 1 || !$topic_info->option_skip_type)
                                                <option value="0" <#if ($topic_info->option_skip_value == 0) selected </#if>>下一题</option>
                                                <#list ($topic_list as $topic)
                                                <option value="${topic->id!}" <#if ($topic_info->option_skip_value == $topic->id) selected </#if>>${topic->topic_title!}</option>
                                                </#list>
                                            <#else>
                                                <option value="0" <#if ($topic_info->option_skip_value == 0) selected </#if>>测评完成</option>
                                                <#if ($assess_info->assess_judge_type != 1)
                                                    <#list ($result_list as $result)
                                                    <option value="${result->id!}" <#if ($topic_info->option_skip_value == $result->id) selected </#if>>${result->result!}</option>
                                                    </#list>
                                                </#if>
                                            </#if>
                                        </select>
                                        <#if ($assess_info->pub_flag == 1)
                                        <input type="hidden" name="option_skip_value" value="${topic_info->option_skip_value!}">
                                        </#if>
                                    </div>
                                </li>
                                <li class="clearfix subject">
                                    <#if ($topic_info->option_content)
                                        <#list ($topic_info->option_content as $option)
                                        <div class="clearfix subject_box">
                                            <div class="li_left"><em>*</em>选项：</div>
                                            <div class="li_right">
                                                <input type="text" name="subject_name" value="${option['option_name']!}">
                                                
                                                <#if ($assess_info->pub_flag != 1)
                                                <a href="javascript:;" class="add_subject">添加选项</a>
                                                
                                                <a href="javascript:;" class="del fr"
                                                    style="display: inline; position: static;">
                                                    <img src="/image/admin/decorate_delete.png" alt="">
                                                </a>
                                                </#if>

                                                <p class="tips">最多可填写100字</p>
                                            </div>
                                            <div class="li_left">图片（选填）：</div>
                                            <div class="li_right img_set" style="margin-top:10px;">
                                                <input type="hidden" name="subject_img" value="${option['option_img']!}">
                                                <div class="upload_img" style="display: none;">
                                                    <img src="${option['option_img']!}" alt="">
                                                    <span class="choose_img">重新选择</span>
                                                </div>
                                                <input type="button" value="" class="add_image" style="display: block;">
                                                <span
                                                    style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：690px
                                                    * 440px</span>
                                            </div>

                                            <#if ($assess_info->assess_judge_type == 1)
                                            <div class="li_left"><em>*</em>选项分值：</div>
                                            <div class="li_right">
                                                <input type="text" name="option_point" value="${option['option_point']!}"> 分
                                            </div>
                                            </#if>

                                            <div class="li_left item_ending"><em>*</em>跳题逻辑：</div>
                                            <div class="li_right item_ending">
                                                选择<select name="skip_type" class="skip_type" <#if ($assess_info->pub_flag == 1) disabled </#if>>
                                                    <option value="1" <#if ($option['skip_type'] == 1) selected </#if>>跳转到指定题目</option>
                                                    <option value="2" <#if ($option['skip_type'] == 2) selected </#if>>跳转到结果页,结束作答</option>
                                                </select>
                                                <#if ($assess_info->pub_flag == 1)
                                                <input type="hidden" name="skip_type" value="${option['skip_type']!}">
                                                </#if>
                                                <span>后跳转到：</span>
                                                <select name="skip_value" class="skip_value" <#if ($assess_info->pub_flag == 1) disabled </#if>>
                                                    <#if ($option['skip_type'] == 1)
                                                        <option value="0" <#if ($option['skip_value'] == 0) selected </#if>>下一题</option>
                                                        <#list ($topic_list as $topic)
                                                        <option value="${topic->id!}" <#if ($option['skip_value'] == $topic->id) selected </#if>>${topic->topic_title!}</option>
                                                        </#list>
                                                    <#else>
                                                        <option value="0" <#if ($option['skip_value'] == 0) selected </#if>>测评完成</option>
                                                        <#if ($assess_info->assess_judge_type != 1)
                                                            <#list ($result_list as $result)
                                                            <option value="${result->id!}" <#if ($option['skip_value'] == $result->id) selected </#if>>${result->result!}</option>
                                                            </#list>
                                                        </#if>
                                                    </#if>
                                                </select>
                                                <#if ($assess_info->pub_flag == 1)
                                                <input type="hidden" name="skip_value" value="${option['skip_value']!}">
                                                </#if>
                                            </div>
                                        </div>
                                        </#list>
                                    <#else>
                                        <div class="clearfix subject_box">
                                            <div class="li_left"><em>*</em>选项：</div>
                                            <div class="li_right">
                                                <input type="text" name="subject_name">　<a href="javascript:;"
                                                    class="add_subject">添加选项</a>
                                                <a href="javascript:;" class="del fr"
                                                    style="display: inline; position: static;"><img
                                                        src="/image/admin/decorate_delete.png" alt=""></a>
                                                <p class="tips">最多可填写100字</p>
                                            </div>
                                            <div class="li_left">图片（选填）：</div>
                                            <div class="li_right img_set" style="margin-top:10px;">
                                                <input type="hidden" name="subject_img" value="">
                                                <div class="upload_img" style="display: none;">
                                                    <img src="" alt="">
                                                    <span class="choose_img">重新选择</span>
                                                </div>
                                                <input type="button" value="" class="add_image" style="display: block;">
                                                <span
                                                    style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：690px
                                                    * 440px</span>
                                            </div>

                                            <#if ($assess_info->assess_judge_type == 1)
                                            <div class="li_left"><em>*</em>选项分值：</div>
                                            <div class="li_right">
                                                <input type="text" name="option_point" value=""> 分
                                            </div>
                                            </#if>

                                            <div class="li_left item_ending"><em>*</em>跳题逻辑：</div>
                                            <div class="li_right item_ending">
                                                选择<select name="skip_type" class="skip_type">
                                                    <option value="1">跳转到指定题目</option>
                                                    <option value="2">跳转到结果页,结束作答</option>
                                                </select>
                                                <span>后跳转到：</span>
                                                <select name="skip_value" class="skip_value">
                                                    <option value="0">下一题</option>
                                                    <#list ($topic_list as $topic)
                                                    <option value="${topic->id!}">${topic->topic_title!}</option>
                                                    </#list>
                                                </select>
                                            </div>
                                        </div>
                                    </#if>
                                </li>
                                <li class="clearfix refresh" style="margin-top: 10px;color:#5a8bff">
                                    <a href="javascript:;" onclick="location.reload()">刷新</a> ｜
                                    <a href="{{url('/admin/market/assess/result/add?assess_id='.$assess_id)!}">添加结果</a> ｜
                                    <a href="{{url('/admin/market/assess/result/list?assess_id='.$assess_id)!}">管理结果</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <div class="btn_save fix_footer" style="display: block;">
            <a class="save" href="javascript:void(0);">保存</a>
        </div>
    </div>
</div>
<div class="template" hidden>
    <div class="clearfix subject_box">
        <div class="li_left"><em>*</em>选项：</div>
        <div class="li_right">
            <input type="text" name="subject_name">　<a href="javascript:;" class="add_subject">添加选项</a>
            <a href="javascript:;" class="del fr" style="display: inline; position: static;"><img
                    src="/image/admin/decorate_delete.png" alt=""></a>
            <p class="tips">最多可填写100字</p>
        </div>
        <div class="li_left">图片（选填）：</div>
        <div class="li_right img_set" style="margin-top:10px;">
            <input type="hidden" name="subject_img" value="">
            <div class="upload_img" style="display: none;">
                <img src="" alt="">
                <span class="choose_img">重新选择</span>
            </div>
            <input type="button" value="" class="add_image" style="display: block;">
            <span style="margin-top: 15px;display: inline-block;font-size: 14px;color: #999;">建议尺寸：690px
                * 440px</span>
        </div>
        <#if ($assess_info->assess_judge_type == 1)
        <div class="li_left"><em>*</em>选项分值：</div>
        <div class="li_right">
            <input type="text" name="option_point" value=""> 分
        </div>
        </#if>
        <div class="li_left item_ending"><em>*</em>跳题逻辑：</div>
        <div class="li_right item_ending">
            选择<select name="skip_type" class="skip_type">
                <option value="1">跳转到指定题目</option>
                <option value="2">跳转到结果页,结束作答</option>
            </select>
            <span>后跳转到：</span>
            <select name="skip_value" class="skip_value">
                <option value="0">下一题</option>
                <#list ($topic_list as $topic)
                <option value="${topic->id!}">${topic->topic_title!}</option>
                </#list>
            </select>
        </div>
    </div>
</div>

<!-- 题目 -->
<select name="skip_value" class="topic_list" hidden>
    <option value="0">下一题</option>
    <#list ($topic_list as $topic)
    <option value="${topic->id!}">${topic->topic_title!}</option>
    </#list>
</select>

<!-- 结果 -->
<select name="skip_value" class="result_list" hidden>
    <option value="0">测评完成</option>
    <#if ($assess_info->assess_judge_type != 1)
        <#list ($result_list as $result)
        <option value="${result->id!}">${result->result!}</option>
        </#list>
    </#if>
</select>

<#include "/admin/footer.ftl">
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.2"></script>
<script language="JavaScript" src="/js/admin/jVideoManager.js"></script>
<script>
    inputTest();
    setImg();
    if ($('input[name="act"]').val() == 'edit') {
        createSubject();
    }
    function picker() {
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }

    $('[name="topic_title"]').keyup(function () {
        inputTest();
    })
    $('[name="subject_name"]').keyup(function () {
        createSubject();
    })
    $('[name="topic_type"]').change(function () {
        inputTest();
    })
    $('[name="bg_img_type"]').change(function () {
        inputTest();
    })
    $('[name="option_type"]').click(function () {
        inputTest();
    })
    $('.assess_set_ul').on('click', '.add_subject', function () {
        if ($('.subject .subject_box').length < 10) {
            $('.subject .subject_box').eq($('.subject .subject_box').length - 1).after($('.template > .subject_box').clone(true));
        } else {
            util.mobile_alert('超出可添加数量');
        }
        $('.subject .subject_box:eq(0)').find('.del').hide();
        $('.subject .subject_box:eq(0)').siblings().find('.del').show();
        createSubject();
    })
    $('.assess_set_ul').on('click', '.del', function () {
        let that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除该选项吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            }, function (index) {
                that.parents('.subject_box').remove();
                createSubject();
                layer.close(index);
            });
        });
    })

    //复制跳题选项
    $('.assess_set_ul').on('change', '.skip_type', function () {
        var skip_type = $(this).val();
        var skip_value_obj = $(this).parent().find('.skip_value');
        skip_value_obj.children().remove(); 
        if (skip_type == 1) {
            skip_value_obj.append($('.topic_list').children().clone());
        }else{
            skip_value_obj.append($('.result_list').children().clone());
        }
    })

    $('.save').click(function () {
        if ($('[name="topic_title"]').val() == '') {
            util.mobile_alert('请输入题目')
            return false;
        }
        if ($('[name="topic_type"]:checked').val() == 1 && $('[name="topic_type_path"]').val() == '') {
            util.mobile_alert('请上传图片')
            return false;
        }
        if ($('[name="topic_type"]:checked').val() == 2 && $('[name="topic_type_path"]').val() == '') {
            util.mobile_alert('请上传视频')
            return false;
        }
        if ($('[name="bg_img_type"]:checked').val() == 1 && $('[name="bg_img_path"]').val() == '') {
            util.mobile_alert('请上传题目背景')
            return false;
        }

        let success = 1
        $('.subject .subject_box').each(function () {
            
            if ($(this).find('[name="subject_name"]').val() == '') {
                $(this).find('[name="subject_name"]').focus()
                util.mobile_alert('请输入选项')
                success = 0
                return false;
            }
            <#if ($assess_info->assess_judge_type == 1)
                if ($(this).find('[name="option_point"]').val() == '') {
                    $(this).find('[name="option_point"]').focus()
                    util.mobile_alert('请输入分值')
                    success = 0
                    return false;
                }
            </#if>
        })
        if(!success){
            return false;
        }

        var param = {};
        param.act = $('[name="act"]').val();
        param.topic_id = $('[name="topic_id"]').val();
        param.assess_id = $('[name="assess_id"]').val();
        param.topic_title = $('[name="topic_title"]').val();
        param.topic_type  = $('[name="topic_type"]:checked').val();
        var topic_type_path = '';
        if ($('[name="topic_type"]:checked').val() == 1) {
            topic_type_path = $('.subject_type_pic [name="topic_type_path"]').val();
        }else if ($('[name="topic_type"]:checked').val() == 2) {
            topic_type_path = $('.subject_type_video [name="topic_type_path"]').val();
        }
        param.topic_type_path = topic_type_path;
        param.bg_img_type = $('[name="bg_img_type"]:checked').val();
        param.bg_img_path = $('[name="bg_img_path"]').val();
        param.option_type = $('[name="option_type"]:checked').val();
        param.option_skip_type = $('[name="option_skip_type"]').val();
        param.option_skip_value = $('[name="option_skip_value"]').val();

        var options = [];
        $('.subject .subject_box').each(function () {
            var option = {};
            option.option_name  = $(this).find('[name="subject_name"]').val();
            option.option_img   = $(this).find('[name="subject_img"]').val();
            option.option_point = $(this).find('[name="option_point"]').val() ? $(this).find('[name="option_point"]').val() : 0;
            option.skip_type    = $(this).find('[name="skip_type"]').val();
            option.skip_value   = $(this).find('[name="skip_value"]').val();
            options.push(option);
        });
        param.option_content = options;
        util.ajax_json('/admin/market/assess/topic/save',function(d) {
            if (d.error == 0) {
                util.mobile_alert(d.message);
                setTimeout(function () {
                    location.href = '/admin/market/assess/topic/list?assess_id='+param.assess_id
                },1000)
            }else{
                util.mobile_alert(d.message);
            }
        },param);

    })




    function createSubject() {
        let subjectStr = '';
        $('.subject .subject_box').each(function (index) {
            let subject_name = $(this).find('[name="subject_name"]').val();
            let subject_img = $(this).find('[name="subject_img"]').val();
            subjectStr += `<p class="subject_title">
                                <i class="iconfont ${index == 0 ? 'icondanxuan' : 'icondanxuanbiankuang'}"></i><span>${subject_name ? subject_name : '选项'}</span>
                            </p>
                            ${subject_img ? `<div class="subject_pic">
                                            <img src='${subject_img}' alt="">
                                            </div>` : ''}`
        })
        $('.preview_depict').html(subjectStr)
        if ($('.preview_depict').find('img').length == 0) {
            $('.subject_title').eq(0).addClass('check');
        } else {
            $('.subject_title').eq(0).removeClass('check');
        }
        if ($('[name="option_type"]:checked').val() == 0) {
            $('.subject_box').find('.item_ending').show();
        } else {
            $('.subject_box').find('.item_ending').hide();
        }
    }


    $(".add_image,.choose_img").click(function () {
        var that = $(this);
        let width = '' , height = '';
        let inputName = $(this).parents('.img_set').children('input').eq(0).attr('name');
        if(inputName == "topic_type_path" || inputName == "subject_img"){
            width = 690;
            height = 440;
        } else if (inputName == "bg_img_path"){
            width = 750;
            height = 1334;
        }
        $.jImageManager({
            img_width: width,
            img_height: height,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                that.parents('.img_set').find('.upload_img').show()
                that.parents('.img_set').find('.upload_img img').attr('src', path);
                that.parents('.img_set').find('input[type="hidden"]').val(path);
                that.hide();
                createSubject();
                inputTest()
            }
        });
        hasSaved = false;
    });
    $(".add_video,.choose_video").click(function () {
        var that = $(this);
        $.jVideoManager({
            ok_cb: function (video) {
                video = video[0];
                console.log(video);
                $(that).parents('.video_set').find('[name="topic_type_path"]').val(video.url)
                $(that).parents('.video_set').find('[name="topic_type_path"]').attr('snapshot_url',video.snapshot_url)
                inputTest()
            }
        });
        hasSaved = false;
    });

    $(document).on('mouseenter', '.upload_img', function () {
        $(this).find('span').show()
    });
    $(document).on('mouseleave', '.upload_img', function () {
        $(this).find('span').hide()
    });


    function inputTest() {
        if ($('[name="topic_type"]:checked').val() == 0) {
            $('.subject_type_pic').hide()
            $('.subject_type_video').hide()
            $('.preview_title_img').hide()
        } else if ($('[name="topic_type"]:checked').val() == 1) {
            $('.subject_type_pic').show()
            $('.subject_type_video').hide()
            $('.preview_title_img').show()
            if ($('.subject_type_pic [name="topic_type_path"]').val() != '') {
                $('.preview_title_img').html($('<img></img>').attr('src', $('.subject_type_pic [name="topic_type_path"]').val()))
            } else {
                $('.preview_title_img').html(`<div class="no_pic_style">
                                            <img src="http://${image_domain!}/image/admin/assess_no_pic.png" alt="">
                                            <span>
                                                题目图片/视频
                                            </span>
                                        </div>`)
            }
        } else {
            $('.subject_type_pic').hide()
            $('.subject_type_video').show()
            $('.preview_title_img').show()
            if ($('.subject_type_video [name="topic_type_path"]').val() != '') {
                $('.preview_title_img').html($('<img></img>').attr('src', $('.subject_type_video [name="topic_type_path"]').attr('snapshot_url')))
            } else {
                $('.preview_title_img').html(`<div class="no_pic_style">
                                            <img src="http://${image_domain!}/image/admin/assess_no_pic.png" alt="">
                                            <span>
                                                题目图片/视频
                                            </span>
                                        </div>`)
            }
        }


        if ($('[name="bg_img_type"]:checked').val() == 0) {
            $('.subject_bg').hide();
        } else {
            $('.subject_bg').show();
        }

        if ($('[name="topic_title"]').val() != '') {
            $('.preview_title > p').text($('[name="topic_title"]').val());
        } else {
            $('.preview_title > p').text('题目名称');
        }

        $('.subject_box:eq(0)').find('.del').hide();
        $('.subject_box:eq(0)').siblings().find('.del').show();

        if ($('[name="option_type"]:checked').val() == 0) {
            $('.subject_box').find('.item_ending').show();
            $('.ending').css('border-bottom', 'none').hide();
            $('[name="option_type"]').parents('li').css('border-bottom', '1px dashed #ddd')
        } else {
            $('.subject_box').find('.item_ending').hide();
            $('.ending').css('border-bottom', '1px dashed #ddd').show();
            $('[name="option_type"]').parents('li').css('border-bottom', 'none')
        }

        if ($('[name="bg_img_type"]:checked').val() == 0) {
            $('.assess_preview').css('background', 'none');
        } else {
            if ($('[name="bg_img_path"]').val() != '')
                $('.assess_preview').css({
                    'background-image': 'url(' + $('[name="bg_img_path"]').val() + ')',
                    'background-size': '100% 100%',
                    'background-repeat': 'no-repeat'
                })
        }

    }

    function setImg() {
        $('.img_set').each(function() {
            if ($(this).find('input[type="hidden"]').val()) {
                $(this).find('.upload_img').show();
                $(this).find('.add_image').hide();
            }
        });
    }
</script>
<script type="text/javascript">
    getPowerInfo('main_config','assess','sub_4','测评',0);
</script>