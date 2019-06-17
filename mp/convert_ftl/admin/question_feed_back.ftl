<style type="text/css">
    .absolute-left-menu{
        display: none;
    }
</style>
<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/question_feedback.css?v=1.0.9">
<link rel="stylesheet" href="/css/admin/goods_edit.css?v=1.0.9" type="text/css" />
<style type="text/css">
    .goods-item-img li{
        background-color: white;
    }
</style>
<div class="question_container">
    <div class="question_content">
        <form action="/admin/question/feedback" method="post" id="form1" >
         {{ csrf_field()!}
        <div class="question_content_container">
             <div class="question_header">
                 <span>问题反馈</span>
             </div>
             <div class="question_body clearfix">
                 <div class="question_message clearfix">
                     <div class="qm"><span>*</span>&nbsp;类型</div>
                     <div class="question_label">
                         <input type="hidden" name="category_id" id="category" value="">
                         <input type="hidden" name="cate" id="" value="${cate!}">
                         <div class="ql_style">
                             <span>产品建议</span>
                             <p hidden>1</p>
                             <img src="/image/admin/question_gou.png" class="ql_style_img">
                         </div>
                         <div class="ql_style">
                             <span>网页异常</span>
                             <p hidden>2</p>
                             <img src="/image/admin/question_gou.png" class="ql_style_img">
                         </div>
                         <div class="ql_style">
                             <span>功能使用咨询</span>
                             <p hidden>3</p>
                             <img src="/image/admin/question_gou.png" class="ql_style_img">
                         </div>
                         <div class="ql_style">
                             <span>其他</span>
                             <p hidden>4</p>
                             <img src="/image/admin/question_gou.png" class="ql_style_img">
                         </div>
                     </div>
                 </div>
                 <div class="question_message clearfix">
                     <div class="qm"><span>*</span>&nbsp;问题</div>
                     <div class="qm_text">
                     <textarea name="content" id="inputtext" placeholder="请描述您遇到的问题"></textarea>
                         <span ><span class="change_number">0</span>/200</span>
                     </div>
                     <div>
                     <ul class="goods-item-img clearfix">
                         {{--<li>--!}
                             {{--<input name="goods_img[]" type="hidden" value="${item!}">--!}
                             {{--<img src="${item!}" class='' alt="" />--!}
                             {{--<img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" />--!}
                         {{--</li>--!}
                         <li>
                             <input name="qf_img[]" type="hidden">
                             <img src="http://${image_domain!}/image/admin/add_img_bg.png" class="add_img" alt="" />
                             <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete good_img_delete" style="display: none;" />
                         </li>
                     </ul>
                     </div>
                 </div>
                 <div class="question_message clearfix">
                     <div class="qm">联系电话:</div>
                     <div class="qm_phone">
                         <input type="text"  id="qm_phonenum" name="mobile" placeholder="请填写您的手机号" style="padding-left: 12px">
                     </div>
                     <span class="qm_phone_text">为方便我们尽快把结果反馈给您，请留下您的联系方式</span>
                 </div>
                 <div class="question_message clearfix">
                       <input type="submit" value="提交" class="click_submit" >
                 </div>
             </div>
        </div>
        </form>
    </div>
</div>

{{--<#include "/admin/footer.ftl">--!}
<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script type="text/javascript">
    var i=0;
    $('.goods-item-img').on('click','.good_img_delete',function(){
        $(this).parent().remove();
        hasSaved = false;
    });
    $('.add_img').click(function() {
        var img_number = $(".good_img_delete").length;
        console.log(img_number);
        if(img_number >=6 ){
            util.mobile_alert('最多上传5张图！');
            return false;
        }
        var el = $(this).parent().clone();
        var obj = $(this).parent();
        var w = 800;
        var h = 800;
        $.jImageManager({
            img_width: w,
            img_height: h,
            ok_cb: function (img_arr) {
                var path = img_arr[0].img_url;
                if (path == undefined) {
                    path = img_arr[0].url;
                }
                el.find("img").eq(0).attr("src", path);
                el.addClass("new");
                el.find("input").attr("value", path);
                hasSaved = false;
                el.removeClass('add_class');
                el.find("img").eq(1).show();
                obj.before(el);
            }
        });
    });


    var count=0;
    $('.ql_style').click(function () {
        count++;
        if(count%2!==0){
            $(this).find('img').css('display','block');
            $(this).css('border','1px solid #5a8bff');
        }else{
            $(this).find('img').css('display','none');
            $(this).css('border','1px solid #ddd');
        };
        if($(this).find('img').css('display','block')&& $(this).css('border','1px solid #5a8bff')){
            $(this).addClass('addd');
            $("#category").val($(this).children("p").eq(0).text());
            $(this).siblings().find('img').css('display','none');
            $(this).siblings().css('border','1px solid #ddd');
            $(this).siblings().removeClass('addd');
        };
    });

    $("#inputtext").on("input propertychange", function() {
        var $this = $(this),
            _val = $.trim($this.val()),
            count = "";
        if (_val.length > 200) {
            $this.val(_val.substring(0, 200));
        }
        count = $.trim($this.val()).length;
        $(".change_number").text(count);
    });

    $('.click_submit').on('click',function () {
        if(!$('.ql_style').hasClass('addd')){
            util.mobile_alert('没有选择问题类型！');
            return false;
        };
        if($('#qm_phonenum').val().length>0 && $('#qm_phonenum').val().length!=11){
            util.mobile_alert('手机号格式有误！');
            return false;
        }
        if($('#inputtext').val().length<20){
            util.mobile_alert('问题描述最少输入20字！');
            return false;
        };
        $("#form1").submit();
    })

</script>