<html style="height: 320px;">
<head>
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css"/>
    <link rel="stylesheet" href="/css/admin/layui_change.css?v=1.0.0" type="text/css"/>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css" />
    <link href="/css/admin/card_select.css?v=1.1.5" rel="stylesheet" type="text/css" />
    <style>
        a{
            text-decoration: none;
        }
    </style>
</head>
<body style="background:none;height: 320px;margin:0">
<div class="card-select">
    <form action="/admin/frame/card/select" id="form1">
        <div class="card_left">
            <label for="card_common">
                <input type="radio" name="card_type" id="card_common" value="0" <#if (empty($request["card_type"]) || $request["card_type"]==0) checked </#if> class="card_type"/>
                普通会员卡
            </label>
            <label for="card_limit">
                <input type="radio" name="card_type" id="card_limit" value="1" <#if ($request["card_type"]==1) checked </#if> class="card_type"/>
                限次会员卡
            </label>
            <label for="card_grade">
                <input type="radio" name="card_type" id="card_grade" value="2" <#if ($request["card_type"]==2) checked </#if> class="card_type"/>
                等级会员卡
            </label>
        </div>
        <div class="card_right">
            <div class="card_right_top">
                <input type="text" placeholder="请输入会员卡名称" name="card_name" value="${request["card_name"]!}">
                <span class="search" style="cursor:pointer;">
                    <img src="http://${image_domain!}/image/admin/shop_beautify/search.png" alt="" width="12px" style="vertical-align: initial;">
                    搜索
                </span>
                <img src="http://${image_domain!}/image/admin/shop_beautify/refresh.png" alt="" class="refresh" />
            </div>
            <div class="card_right_center">
                <#if (count($card_list)==0)
                <div class="empty_card">
                    <div class="empty_card_content" style="text-align: center;">
                        <img src="http://${image_domain!}/image/admin/empty_card.png" alt="">
                        <p>暂无会员卡，快去添加吧</p>
                        <a href="/admin/user/member/create?card_type=${request["card_type"]?$request["card_type"]:0!}" target="_blank">
                            <span>添加会员卡</span>
                        </a>
                    </div>
                </div>
                <#else>
                <ul class="clearfix">
                    <#list ($card_list as $card)
                    <li class="card_list" <#if ($card->bg_type==0)style="background:${card->bg_color!}"<#elseif>($card->bg_type==1)style="background:url(http://${image_domain!}/${card->bg_img!}) no-repeat;-webkit-background-size: cover;background-size: cover;"</#if>>
                        <img src="http://${image_domain!}/image/admin/shop_beautify/checked_card.png" class="card_checked" />
                        <input type="hidden" card_type="${card->card_type!}" legal="${card->legal!}" bg_type="${card->bg_type!}"
                               bg_color="${card->bg_color!}" bg_img="${card->bg_img!}" card_id="${card->id!}" is_pay="${card->is_pay!}"
                               pay_type="${card->pay_type!}" pay_fee="${card->pay_fee!}"  class="card_info" />
                        <div>
                            <span class="card_name">${card->card_name!}</span>
                            <#if ($card->card_type != 2)
                                <span class="card_state">
                                    {{--<#if ($card->receive_day || (!$card->receive_day && $card->start_time>date("Y-m-d",time())))--!}
                                        {{--未开始<#else>使用中</#if>--!}
                                    使用中
                                </span>
                            <#else>
                                <span class="card_grade" style="float: right;padding-right: 10px">
                                    ${card->grade!}
                                </span>
                            </#if>
                        </div>
                        <p class="receive_day" <#if ($card->card_type == 2) style="display: none" </#if>>
                            <#if (!$card->receive_day && $card->expire_type == 0)
                            有效期:{{substr($card->start_time,0,10)!}-{{substr($card->end_time,0,10)!}
                            <#elseif>($card->receive_day && $card->expire_type == 1)
                            领取之日起 ${card->receive_day!}
                                <#if ($card->date_type == 0)日
                                <#elseif>($card->date_type == 1)周
                                <#elseif>($card->date_type == 2)月
                                </#if>
                            内有效
                            <#else>
                            有效期:永久有效
                            </#if>
                        </p>
                    </li>
                    </#list>
                    <li class="to_add_card">
                        <a href="/admin/user/member/create?card_type=${request["card_type"]?$request["card_type"]:0!}" target="_blank">
                            <img src="http://${image_domain!}/image/admin/add_card.png" alt="" width="25px" />
                            <p>添加会员卡</p>
                        </a>
                    </li>
                </ul>
                </#if>
            </div>
        </div>
    </form>
</div>

<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script language="JavaScript" src="/js/admin/util.js?v=1.0.1"></script>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script>
    $(".card_type").on("click",function(){
        if($(this).val() == 1){
            var param = {
                config_name: 'main_config',
                mod_name : 'count_card',
                sub_name: 'sub_3'
            };
           util.ajax_json('/admin/version/judgment',function (d) {
               //console.log(d);
               if(d.content == -1){
                   $('#card_common').prop('checked',true);
                   util.systemNotice(1,'','限次会员卡');
               }else{
                   $("#form1").submit();
               }
           },param);
        }else if($(this).val() == 2){
            var param = {
                config_name: 'main_config',
                mod_name : 'grade_card',
                sub_name: 'sub_3'
            };
            util.ajax_json('/admin/version/judgment',function (d) {
                //console.log(d);
                if(d.content == -1){
                    $('#card_common').prop('checked',true);
                    util.systemNotice(1,'','等级会员卡');
                }else{
                    $("#form1").submit();
                }
            },param);
        }else{
            $("#form1").submit();
        }
    });
    $(".refresh").on("click",function () {
        location.reload();
    });
    $(".search").on("click",function () {
        $("#form1").submit();
    });
    if($('input[name="card_type"]:checked').val()==2){
        if($('.card_list').length==9){
            $('.to_add_card').hide();
        }
    }
</script>
</body>
</html>