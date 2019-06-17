<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/service_config.css?v=1.0.1">
<style type="text/css">
    .save_btn{
        background: #578dfa;
        border: none;
        color: #fff;
        padding:5px 25px;
    }
    .save_btn:hover{
        background-color: #447af9;
        border-color: #447af9;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span>{{ trans("admin/store_manage.store_manage")!} / </span>
        <span style="color: #666;">{{ trans("admin/store_manage.service_config.title")!}</span>
    </div>
    <div class="main-container">
        {{--评论配置--!}
        <div class="service_comments">
            <div class="service_btn">门店买单</div>
            <div class="comment_model">
                <label for="no_buy">
                    <input type="radio" name="store_buy" mod_name="门店买单" id="no_buy" value="1" style="margin-left: 0" <#if ($store_buy == 0) checked </#if>>
                    <span>关闭</span>
                </label>
                <label for="buy">
                    <input type="radio" name="store_buy" mod_name="服务管理" id="buy" value="2" <#if ($store_buy == 1) checked </#if>>
                    <span>启用</span>
                </label>
            </div>
        </div>
        {{--评论配置--!}
        <div class="service_comments">
            <div class="service_btn">服务评论配置</div>
            <div class="comment_model">
                <label for="no_review">
                    <input type="radio" name="aduit_state" mod_name="服务管理" id="no_review" value="0" style="margin-left: 0" <#if ($comment == 0) checked </#if>>
                    <span>不用审核</span>
                </label>
                <label for="last_review">
                    <input type="radio" name="aduit_state" mod_name="服务管理" id="last_review" value="1" <#if ($comment == 1) checked </#if>>
                    <span>先发后审</span>
                </label>
                <label for="first_review">
                    <input type="radio" name="aduit_state" mod_name="服务管理" id="first_review" value="2" <#if ($comment == 2) checked </#if>>
                    <span>先审后发</span>
                </label>
            </div>
        </div>
        {{--评价结束--!}
        {{--技师名称设置--!}
        <div class="change_techname">
            <div class="change_btn">职称配置</div>
            <div class="changename_model">
                <form action="" method="post" id="form2">
                    <input type="text" value="${tech_names!}"  placeholder="" name="tech_names" mod_name="技师管理">
                </form>
            </div>
        </div>
        <div class="scancode">
            <div class="scancode_btn">扫码购</div>
            <div class="scancode_model">
                <button>选择门店</button>
                <span class="choice" choice_ids="${store_scan_ids!}">已选择:<strong><#if ($store_scan_ids) {{count(explode(',',$store_scan_ids))!} <#else> 0 </#if></strong>家</span>
                <span class="scancode_tips">选择开启“扫码购”功能的门店</span>
            </div>
        </div>
        <div class="btn_model">
            <button class="save_btn">保存</button>
        </div>
    </div>
</div>

<#include "/admin/footer.ftl">
<script>
    //版本控制
    var store_pay = '${version['store_pay']!}';
    var technician = '${version['technician']!}';
    var service = '${version['service']!}';
    if(store_pay == -1){
        $("input[name='store_buy']").attr("disabled",'true');
        $("input[name='store_buy']").parent("label").addClass("version_disabled");
    }
    if(technician == -1){
        $("input[name='tech_names']").attr("disabled",'true');
        $("input[name='tech_names']").parent().addClass("version_disabled");
    }
    if(service == -1){
        $("input[name='aduit_state']").attr("disabled",'true');
        $("input[name='aduit_state']").parent("label").addClass("version_disabled");
    }
    $(".version_disabled").click(function () {
        util.systemNotice(1,'',$(this).attr("mod_name"));
    })
    $('.top_banner li .service_btn').click(function () {
        $('.top_banner li a').attr("class","");
        $(this).attr("class","active");
        $(".comment_model").show();
        $(".changename_model").hide();
    });

    $('.top_banner li .change_btn').click(function () {
        $('.top_banner li a').attr("class","");
        $(this).attr("class","active");
        $(".comment_model").hide();
        $(".changename_model").show();
    });
    $('.scancode_model button').click(function(){
        var choice_ids = $('.choice').attr('choice_ids').split(',');
        layui.use('layer',function(){
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type:2,
                title:['选择开启“扫码购”功能的门店','text-align:center;padding:0px'],
                offset:'auto',
                area:['900px','450px'],
                content:'/admin/frame/store/select?is_from_scan=1&store_id='+$('.choice').attr('choice_ids'),
                btn:['确定','取消'],
                btnAlign:'r',
                shade:[0.3,'#000'],
                success:function (layero, index) {
                    var body = layer.getChildFrame('body',index);
                    body.contents().find(".choose_store").each(function(){
                        if ($.inArray($(this).attr('store_id'),choice_ids) > -1) {
                            $(this).addClass('goods_tr_choose');
                            $(this).attr('auto','true');
                        }
                    });
                },
                yes:function(index,layero){
                    var body = layer.getChildFrame('body',index);
                    var ids = [];
                    body.contents().find(".choose_store.goods_tr_choose").each(function(){
                            ids.push($(this).attr('store_id'));
                    });
                    $('.choice').attr('choice_ids',ids.join(','));
                    $('.choice strong').text(ids.length);
                    layer.close(index);
                },
                btn2:function(index,layero){
                    layer.close(index);
                }
            })
        })
    })

    // $(".click_to_change").click(function () {
    //     $(".changename_model input").removeAttr("readonly");
    //     $(".changename_model input").focus();
    // })

//     $(".changename_model input").blur(function () {
//        if($(this).val() == ""){
//            util.mobile_alert('请填写要修改的名称！');
//            return false;
//        }
//         $(this).attr("readonly","readonly");
//     })

    $(".comment_model a").click(function () {
        $("#form1").submit();
    });
    $(".save_btn").click(function(){
        var data = {};
        data.service_comment =$('input[name="aduit_state"]:checked').val();
        data.store_buy =$('input[name="store_buy"]:checked').val();
        data.tech_names =$('input[name="tech_names"]').val();
        data.choice_ids =$('.choice').attr('choice_ids');
        // console.log(data); return false;
        util.ajax_json('/admin/ajax/servicecomment/cfg',function(d){
            if(d&&d.error ==0){
                layer.ready(function () {
                    layer.msg(d.content, {time: 2000},function () {
                        window.location.reload();
                    });
                });
            }
            else{
                util.mobile_alert(d.message);
                // window.location.reload();
            }
        },data);
    });
</script>