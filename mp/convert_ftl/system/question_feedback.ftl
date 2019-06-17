<#include ("system.header")
<link href="/css/admin/goods_list.css?v=1.0.6" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/user_list.css?v=1.0.5" type="text/css" />
<style type="text/css">

    input[type='text']:focus {
        border: 1px solid #5a8bff !important;
        box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -webkit-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
        -moz-box-shadow: 0 0 5px rgba(90,139,255, 1) !important;
    }
    .tb_paging td a:hover{
        background: #fff !important;
        color: #5a8bff;
        border:1px solid #5a8bff;
        text-decoration: none;
    }
    .tb_paging td a:focus{
        background: #5a8bff !important;
        color: #fff;
        border:1px solid #5a8bff;
        text-decoration:none;
    }
    .order-info-li .fl2{
        width:90px;
        float: left;
        margin-top:5px;
    }
    .order-info-li .fl2 input{
        width:16px;
        float: left;
        margin-top:-5px;
        margin-left: 20px;
    }
    .member_list_main tbody td{
        height: 83px;
        padding:15px 0;
    }
    .member_list_main thead td{
        padding:15px 0;
    }
    .list-center-sel{
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        margin-top: 0px;
        color: #333;
        font-size: 14px;
        width: 150px;
        line-height: 50px;
        float: right;
        margin-right: 19px;
        margin-left: 0px;
    }
    td>a{
        background: #fff !important;
        color: #5a8bff;
        text-decoration: none;
    }
    td>a:hover{
        background: #fff !important;
        color: #5a8bff;
        text-decoration: underline;
    }
    td>a:active{
        background: #fff !important;
        color: #5a8bff;
        text-decoration: none;
    }

    .look_message{
        display: block;
        text-align: right;
    }

    .ctrl-img{
        position: relative;
    }
    .ctrl-img img{
        width: 50px;
        height: 48px;
        border-radius: 2px;
        border: 1px solid #ccc;
        margin: 2px 5px 0 0;
    }
    /*预览*/
    .comm_back{
        width: 100%;
        height: 100%;
        position: fixed;
        top: 0;
        left: 0;
        background: rgba(0,0,0,0.7);
        display: none;
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
        position: absolute;
        top: 100px;
        left: 0;
        z-index: 300;
    }
</style>
<div style="min-width: 1090px;">
    <div class="order-container">
        <div class="order-info">
            <form action="" method="post" id="form1">
                <input type="hidden" name="page" value="1">
                {{ csrf_field()!}
                <ul>
                    <li class="order-info-li clearfix">
                        <div class="fl">
                            <span>反馈用户</span>
                            <input type="text" name="user_name" value="${data->user_name!}" placeholder='反馈用户名称' />
                        </div>
                        <div class="fl">
                            <span>问题类型</span>
                            <select  class="list-center-sel" name="category_id">
                                <option value="" >请选择反馈类型</option>
                                <option value="1" <#if ($data->category_id == 1) selected="selected" </#if>>产品建议</option>
                                <option value="2" <#if ($data->category_id == 2) selected="selected" </#if>>网页异常</option>
                                <option value="3" <#if ($data->category_id == 3) selected="selected" </#if>>功能使用咨询</option>
                                <option value="4" <#if ($data->category_id == 4) selected="selected" </#if>>其他</option>
                            </select>
                        </div>
                    </li>
                    <li class="order-info-li clearfix">
                        <div class="fl" style="width: auto;">
                            <span>反馈时间</span>
                            <input type="text" name="start_time" value="${data->start_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="startDate" onclick="picker();"
                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off"/>&nbsp;&nbsp;至&nbsp;
                            <input type="text" name="end_time" value="${data->end_time!}" placeholder='{{ trans("admin/user_list.choose_time")!}' id="endDate" onclick="picker();"
                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" autocomplete="off"/>
                        </div>
                        <button class="btn-choose">筛选</button>
                    </li>
                </ul>
            </form>
        </div>
        <div>
            <form action="" id="form2" method="">
                {{ csrf_field()!}
                <input type="hidden" name="user_name"   value="${data->user_name!}">
                <input type="hidden" name="category_id"  value="${data->category_id!}">
                <input type="hidden" name="start_time" value="${data->start_time!}">
                <input type="hidden" name="end_time" value="${data->end_time!}">
                <div class="member_list_main">
                    <table width="100%">
                        <thead>
                        <tr>
                            <td width="8%">客户登录账号</td>
                            <td width="10%">登录账号手机号</td>
                            <td width="10%">填写手机号</td>
                            <td width="14%">反馈时间</td>
                            <td width="6%">使用版本</td>
                            <td width="8%">问题类型</td>
                            <td width="16%">问题内容</td>
                            <td width="10%" >图片</td>
                            <td width="9%">已看状态</td>
                            <td width="9%">详情查看</td>
                        </tr>
                        </thead>
                        <tbody>
                            <#list ($data_list as $item)
                                <tr id="" >
                                    <td width="8%">${item->user_name!}</td>
                                    <td width="10%">${item->account_mobile!}</td>
                                    <td width="10%">${item->mobile!}</td>
                                    <td width="14%">${item->create_time!}</td>
                                    <td width="6%">${item->version_name!}</td>
                                    <td width="8%">
                                        ${qfcategory[$item->category_id]!}
                                    </td>
                                    <td width="16%" class="look_text">
                                        <span class="wrapper">${item->content!}</span>

                                    </td>
                                    <td width="10%" class="ctrl-img">
                                        <#if ($item->qf_img)<img src="${item->qf_img!}" class="click_img"/><#else> 无 </#if>
                                    </td>
                                    <#if ($item->is_look == 1)
                                        <td width="9%">已查看</td>
                                        <#else>
                                        <td width="9%" style="color: red">未查看</td>
                                    </#if>
                                    <td width="9%" >
                                        <a href="/system/question/message?question_feedback_id=${item->question_feedback_id!}" >查看详情</a>
                                    </td>

                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <div class="member_list_footer">
                    <table width="" border="0" class="tb_paging">
                        <tr>
                            <td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}
                                <a href="##" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage() - 1!});">{{ trans("admin/common.page.pre_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>
                                <a href="##"
                                   onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>
                                <input id="page" name="page" type="text" value="${data_list->currentPage()!}"
                                       size="5"
                                       onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}
                            </td>
                        </tr>
                    </table>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="comm_back"></div>
<img src="http://${image_domain!}/image/wxapp/close_icon.png" class="close_comm" />
<div class="comm_all">
    <div id="myCarousel" class="carousel slide">
        <div class="carousel-inner">
        </div>
        <a class="carousel-control left" href="#myCarousel" data-slide="prev"></a>
        <a class="carousel-control right" href="#myCarousel" data-slide="next"></a>
    </div>
</div>
<#include ("system.footer")
<script>
    $('.carousel').carousel('pause');//禁止轮播 自动轮播
    $('.ctrl-img').on('click','.click_img',function(){
        $('.comm_back').show();
        $('.close_comm').show();
        $('.comm_all').css('padding-bottom','30px');
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
    $.trim($(".wrapper").val());
    var wrapper_text_arr=[];
    $(".wrapper").each(function () {
        $.trim($(this).val());
          var tt=$(this).text();
          var tl=tt.length;
          var s=tt.toString();
          if(tl>'48'){
              var ss=s.substring(0,48)+"...";
          }
          $(this).text(ss);
          wrapper_text_arr.push(tl);
    })
    function gopage(page) {
        var last_page = '${data_list->lastPage()!}';
        if(page>last_page){
            page = last_page;
        }
        $("#page").val(page);
        $("#form2").submit();
    }


</script>