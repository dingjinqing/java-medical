<#include ('system.header')
<style type="text/css">
   *{
       padding: 0px;
       margin: 0px;
       box-sizing: border-box;
   }
   .message_container{
       background-color: white;
       padding: 10px;
       background: white;
   }
    .get_message{
        min-height: 500px;
        padding: 25px;
    }
    ul li{
        list-style: none;
        min-height: 45px;
        font-size: 13px;
    }
   .clearfix:after {
       content:"";
       display: block;
       clear:both;
   }
    .dl{
        float: left;
    }
    .get_message li .dl:nth-of-type(1){
        width: 120px;
        line-height: 30px;
        text-align: right;
    }
   .get_message li .dl:nth-of-type(2){
       width: auto;
       line-height: 30px;
       padding-left: 20px;
   }
    input[type="text"]{
        height: 30px;
    }
    .question_text{
        width: 230px;
        height: auto;
        min-height: 200px;
        margin-top: 5px;
        border: 1px solid #dcdcdc;
        word-break: break-all;
        word-wrap: break-word;
        line-height: 20px;
    }
    .dl-img{
        margin-top: 20px;
    }
    .dl-img-click{
        width: 180px;
        height: 160px;
        background: 100% 100%;
        float: left;
        margin-right: 10px;
    }
    .dl-img-click img{
        width: 100%;
        height: 100%;
    }
   /*预览部分*/
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
<div style="min-width: 1090px">
   <div class="message_container">
        <div class="get_message">
           <ul>
               <li class="clearfix">
                   <div class="dl">客户登录账号名称：</div>
                   <div class="dl">
                       <input type="text" value="${qf_message->user_name!}" disabled="disabled">
                   </div>
               </li>
               <li class="clearfix">
                   <div class="dl">登录账号手机号：</div>
                   <div class="dl">
                       <input type="text" value="${qf_message->account_mobile!}" disabled="disabled">
                   </div>
               </li>
               <li class="clearfix">
                   <div class="dl">填写手机号：</div>
                   <div class="dl">
                       <input type="text" value="${qf_message->mobile!}" disabled="disabled">
                   </div>
               </li>
               <li class="clearfix">
                   <div class="dl">反馈时间：</div>
                   <div class="dl">
                       <input type="text" value="${qf_message->create_time!}" disabled="disabled">
                   </div>
               </li>
               <li class="clearfix">
                   <div class="dl">类型：</div>
                   <div class="dl">
                       <input type="text" value="${qfcategory[$qf_message->category_id]!}" disabled="disabled">
                   </div>
               </li>
               <li class="clearfix">
                   <div class="dl">使用版本：</div>
                   <div class="dl">
                       <input type="text" value="${qf_message->version_name!}" disabled="disabled">
                   </div>
               </li>
               <li class="clearfix">
                   <div class="dl">问题：</div>
                   <div class="dl">
                       <div class="question_text">${qf_message->content!}</div>
                   </div>
               </li>
               <li class="clearfix dl-img">
                   <div class="dl">图片：</div>
                   <#if ($qf_message->qf_img)
                       <div class="dl">
                       <#list ($qf_message->img_list as $item)
                           <div class="dl-img-click">
                               <img src="${item!}" >
                           </div>
                       </#list>
                       </div>
                   <#else>
                       <div class="dl">
                           <p>无</p>
                       </div>
                   </#if>
               </li>
           </ul>
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
<#include ('system.footer')
<script>
    $('.carousel').carousel('pause');//禁止轮播 自动轮播
    $('.get_message').on('click','.dl-img-click',function(){
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

</script>