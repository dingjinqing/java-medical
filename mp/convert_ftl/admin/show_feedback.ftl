<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/show_feedback.css?v=1.0.0" type="text/css" />
<style type="text/css">
    .checkbox_prev{
        -webkit-appearance: none;
        -moz-appearance: none;
        appearance: none;
        width: 14px;
        height: 14px;
        background: url(/image/admin/check_no.png) no-repeat;
        background-size: 100%;
        margin-left: 20px;
        position: relative;
        top: 1px;
        margin-right: 4px;
    }
    .checkbox_prev:checked{
        background: url(/image/admin/check_yes.png) no-repeat;
        background-size: 100%;
    }
    .comm_back{
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        left: 0;
        background: rgba(0,0,0,0.7);
        display: none;
    }
    .reserve-list-table td{
        text-align: left;
    }
    .close_comm{
        position: fixed;
        top: 10px;
        right: 20px;
        display: none;
        z-index: 301;
        cursor: pointer;
    }
    #myCarousel img{
        margin: 0 auto;
    }
    .comm_all{
        width: 100%;
        /*position: fixed;*/
        position: absolute;
        /*top: %;*/
        left: 0;
        z-index: 300;
    }
</style>
<div class="title">
    <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
    <span><a href="/admin/market/form/list?top_index=4">表单统计</a> / </span>
    <span><a href="/admin/form/feedback/list?page_id=${page->page_id!}&top_index=4">反馈列表</a> / </span>
    <span style="color: #666;"><a href="">反馈详情</a></span>
    {{--<span> / ${store->store_name!}</span>--!}
</div>
<form action="" method="post" id="form1">
    <div class="reserve-container">
        <div class="reserve-list">
            <ul class="reserve-list-nav clearfix">
                <li id="all"  class="nav-active"><a href="#">
                        <span class="wait_re">反馈详情 </span>
                    </a></li>
                <li id="pending_pay" class="nav-active" hidden><a href="#">
                        <span class="wait_re">数据统计 </span></a>
                </li>
            </ul>
            <div class="reserve-list-table">
                <table width="80%" border="1" style="border-color:#eee;text-align: center">
                    <#list ($page as $item)
                    <tr>
                        <td width="40%">${item["form_title"]!}</td>
                        <td width="60%" class="comm_message">
                            <#if (empty($item['selects']))
                                <#if ($item['module_name'] == 'm_imgs')
                                    <#if ($item['module_value'])
                                        <#list ($item['module_value'] as $img)
                                            <img src="${img!}" alt="" style="display: inline-block;" width="65px" height="65px" class="click_comm">
                                        </#list>
                                    </#if>
                                <#else>
                                    ${item['module_value']!}
                                </#if>
                            <#elseif>($item['module_name'] == 'm_sex')
                                <input type="radio" value="0" <#if ( $item['module_value'] == '男') checked </#if> class="checkbox_prev" disabled>男
                                &nbsp;&nbsp;
                                <input type="radio" value="1" <#if ( $item['module_value'] == '女') checked </#if> class="checkbox_prev" disabled>女
                            <#elseif>($item['module_name'] == 'm_slide')
                                <#list ($item["selects"] as $k=>$select)
                                    <span style="font-weight: bolder">·</span><span <#if ($item['module_value'] == $select) style="color: #0E70CA" </#if>>${select!}</span>
                                    <br>
                                </#list>
                            <#else>
                                <#if ($item['module_value'])
                                    <#list ($item["selects"] as $k=>$select)
                                        <span style="font-weight: bolder">·</span><span <#if (in_array($k,$item['module_value'])) style="color: #0E70CA" </#if>>${select!}</span>
                                        <br>
                                    </#list>
                                <#else>
                                    <#list ($item["selects"] as $k=>$select)
                                        <span style="font-weight: bolder">·</span><span>${select!}</span>
                                        <br>
                                    </#list>
                                </#if>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                </table>
            </div>
            <div class="clearfix">

            </div>
        </div>
    </div>
</form>
<div class="comm_back"></div>
<img src="http://${image_domain!}/image/wxapp/close_icon.png" class="close_comm" />
<div class="comm_all">
    <div id="myCarousel" class="carousel slide">
        <div class="carousel-inner" >
        </div>
        <a class="carousel-control left" href="#myCarousel" data-slide="prev"></a>
        <a class="carousel-control right" href="#myCarousel" data-slide="next"></a>
    </div>
</div>
<script type="text/javascript">
    //点击查看评价原图
    // $('.carousel').carousel('pause');//禁止轮播 自动轮播
    $('.comm_message').on('click','.click_comm',function(){
        $('.comm_back').show();
        $('.close_comm').show();
        $('.comm_all').css('padding-bottom','30px');
        var height =  document.body.offsetHeight * 0.5;
        $('.comm_all').css('top',height);
        var img_src = [];
        var img_index = $(this).index();
        $.each($(this).parent().find('img'),function(){  //获取当前评论的图片路径
            img_src.push($(this).attr('src'));
        });
        var item_html = '';
        $.each(img_src,function(k,v){   //将路径赋给轮播
            if(img_index == k){
                item_html += '<div class="item active">';
                item_html += '<img src="' + v + '" alt="' + k + ' slide">';
                item_html += '</div>';
            }else{
                item_html += '<div class="item">';
                item_html += '<img src="' + v + '" alt="' + k + ' slide">';
                item_html += '</div>';
            }
        });
        $('.carousel-inner').append(item_html);
        $('.comm_back,.close_comm,.carousel-inner').click(function(){
            $('.comm_back').hide();
            $('.close_comm').hide();
            $('.comm_all').css('padding-bottom','0px');
            item_html = '';
            $('.carousel-inner').html('');
        });
    });

</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','form_decoration','sub_4','表单统计',0);
</script>
