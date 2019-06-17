<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/brand_related.css?v=1.0.1" type="text/css" />
<div class="title">
    <span><a href="/admin/goods/manage/list?top_index=2">商品管理</a> / </span><span>品牌管理</span>
</div>
<div class="main_container">
    <form action="/admin/goods/brand/showbrandcfg" method="POST" id="form1">
        {{ csrf_field()!}
        <div class="brand_top">
            <ul class="switch_box clearfix">
                <li ><a href="/admin/goods/brand/list?top_index=2">全部品牌</a></li>
                <li><a href="/admin/goods/brand/classifylist?top_index=2">品牌分类</a></li>
                <li class="active"><a href="/admin/goods/brand/showbrandcfg?top_index=2">品牌展示设置</a></li>
            </ul>

            <div class="show_config_box">
                <div>
                    将全部品牌展示在商品分类页：
                    <input type="checkbox" class="switch" id="checkbox1" name="show_all_brand" <#if ($show_all_brand == 1) checked </#if>>
                    <label for="checkbox1" class="switch"></label>
                    <span class="on_off" style="color: #000">已关闭</span>
                    <span class="tips">开启后，将在商品分类页展示全部品牌列表</span>
                    <a href="javascript:;" class="show_eg">查看示例
                        <div class="hover_show">
                            <img src="http://${image_domain!}/image/admin/brand_show2.jpg" alt="">
                        </div>
                    </a>
                </div>

                <div>
                    推荐品牌：
                    <input type="checkbox" class="switch" id="checkbox2" name="show_rcommend_brand"  <#if ($show_rcommend_brand !=0) checked </#if>>
                    <label for="checkbox2" class="switch"></label>
                    <span class="on_off" style="color: #000">已关闭</span>
                    <span class="tips">开启后，将在商品分类页展示推荐品牌列表</span>
                </div>
                <div class="recommend_config" <#if ($show_rcommend_brand ==0 )style="display: none"</#if>>
                    <div class="clearfix"><span>推荐标题：</span> <input type="text" name="recom_title" value="${show_title ?? '推荐品牌'!}"></div>
                    <div class="clearfix">
                        <span>展示样式：</span> 
                        <div>
                            <div>
                                <input type="radio" name="show_rcom_brand" value="2" <#if ($show_rcommend_brand ==2) checked </#if>>按品牌展示
                                <a href="javascript:;" class="show_eg">
                                    查看示例
                                    <div class="hover_show bottom">
                                        <img src="http://${image_domain!}/image/admin/brand_show1.jpg" alt="">
                                    </div>
                                </a>
                                
                            </div>
                            <div>
                                <input type="radio" name="show_rcom_brand" value="3" <#if ($show_rcommend_brand ==3) checked </#if>>按品牌分类展示
                                <a href="javascript:;" class="show_eg">
                                    查看示例
                                    <div class="hover_show bottom">
                                        <img src="http://${image_domain!}/image/admin/brand_show3.jpg" alt="">
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <button class="btn-save" style="width:90px;height:30px;background: #5a8bff;color: #fff; margin: 0 0 0 70px;border: none;">保存</button>
                </div>
            </div>

        </div>
    </form>

</div>
<#include ('admin.preview_common')
<#include "/admin/footer.ftl">
<script>
    $('.btn-save').click(function(){
        if($('input[name="show_rcommend_brand"]').is(':checked')){
            if($('input[name="show_rcom_brand"]:checked').length < 1){
                util.mobile_alert('请选择展示样式');
                return false;
            }
        }
        $('#form1').submit();
    })
$('input.switch').change(function(){
        let $this = $(this);
        if($this.is(':checked')){
            $this.parent().find('.on_off').text('已开启')
        } else {
            $this.parent().find('.on_off').text('已关闭')
        }
    })
$('input.switch').each(function(){
    let $this = $(this);
    if($this.is(':checked')){
        $this.parent().find('.on_off').text('已开启')
    } else {
        $this.parent().find('.on_off').text('已关闭')
    }
})
    $('input[name="show_rcommend_brand"]').click(function () {
        let $this = $(this);
        if($this.is(':checked')){
            $('.recommend_config').show();

        }else {
            $('.recommend_config').hide();
        }
    })
</script>