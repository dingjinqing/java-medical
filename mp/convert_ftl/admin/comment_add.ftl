<#include "/admin/header.ftl">
<link href="/css/admin/goods_list.css?v=1.2.3" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="/css/admin/comment_add.css?v=1.0.7" type="text/css" />

<style type="text/css">
    .nav_list{
        line-height: 40px;
    }
</style>
<div class="goods-container">
    <div class="title">
        <span>{{ trans("admin/shop.list-top.li_top_name")!} </span>/
        <span>评价管理</span>
    </div>
    <div class="main-container">
        <form name="form" action="/admin/goods/comment/add/manage?nav=0&top_index=2"  id="form1" method="post">
            <div style="background-color: #fff">
                <ul id="tab" class="nav_list clearfix">
                    <li <#if ($nav==0)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/list?nav=0">评价记录</a>
                    </li>
                    <li <#if ($nav==1)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/list?nav=1">评价审核</a></li>

                    <#if ($addCommentSwitch == 1)
                        <li <#if ($nav==2)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/add/manage?nav=2">商品列表</a></li>
                    </#if>

                    <#if ($nav == 3)
                        <li <#if ($nav==3)class="active"</#if>><a href="#" data-toggle="tab" url="/admin/goods/comment/add/manage?nav=3">添加评价</a></li>
                    </#if>
                </ul>
                <script>
                    // tab切换
                    $("[data-toggle='tab']").click(function () {
                        var url = $(this).attr("url");
                        if (url != undefined) {
                            window.location.href = url;
                        }
                    });
                </script>
            </div>
            <div class="list-bottom" style="padding:10px">
                <table align="center" class="list-bottom-show">
                    <thead style="border: 1px solid #eee;">
                    <tr id="list-bottom-top">
                        <th width="17%">{{trans("admin/shop.list-bottom.name")!}</th>
                        <th width="10%">{{trans("admin/shop.list-bottom.good_sn")!}</th>
                        <th width="8%">商家分类</th>
                        <th width="8%">
                            {{trans("admin/shop.list-bottom.price")!}
                        </th>
                        <th width="7%">
                            {{trans("admin/shop.list-bottom.good_count")!}
                        </th>
                        <th width="7%">
                            {{trans("admin/shop.list-bottom.sale_sum")!}
                        </th>
                        <th width="7%">
                            访客数
                        </th>
                        <th width="7%">
                            浏览量
                        </th>
                        <th width="8%">
                            实际评价数
                        </th>
                        <th width="8%">
                            添加评价数
                        </th>
                    </tr>
                    </thead>
                    <tbody id="" class="list">
                        <tr>
                            <td align="left" class="goods-name"  style="border-left: none; position: relative;" title="${goods_info->goods_name_title!}">
                                <span >
                                    <span class="clearfix" style="margin-left: 5px;display:block;width:230px;position: relative;">
                                        <#if  ($goods_info->is_card_exclusive == 1)
                                            <span class="card_exclusive" style="bottom: 1px;">专享商品</span>
                                        </#if>

                                        <img src="${goods_info->goods_img!}!small" alt="" class="name-img">
                                    <span class="list-name">
                                        <#if  ($goods_info->goods_type == 1)
                                            <span style="border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px;display: inline-block">拼团</span>
                                        <#elseif>($goods_info->goods_type == 3)
                                            <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">砍价</span>
                                        <#elseif>($goods_info->goods_type == 5)
                                            <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">秒杀</span>
                                        <#elseif>($goods_info->goods_type == 7)
                                            <span style="display: inline-block;border: 1px red solid; padding: 0px 3px; color: red; border-radius: 2px;font-size: 12px">限时降价</span>
                                        </#if>
                                        <#if  ($goods_info->source == 0 && in_array($shop_flag,[1,2]))
                                            <span style="display: inline-block;border: 1px #ef8115 solid; padding: 0px 3px; color: #ef8115; border-radius: 2px;font-size: 12px">自营</span>
                                        </#if>
                                        {!! $goods_info->goods_name !!}
                                        </span>
                                    </span>
                                </span>
                            </td>
                            <td align="center" >${goods_info->goods_sn!}</td>
                            <td align="center" >${goods_info->sort_name!}</td>
                            <td align="center" >${goods_info->shop_price!}</td>
                            <td align="center" >${goods_info->goods_number!}</td>
                            <td align="center" >${goods_info->goods_sale_num!}</td>
                            <td align="center" >${goods_info->uv ?? 0!}</td>
                            <td align="center" >${goods_info->pv ?? 0!}</td>
                            <td align="center" >${goods_info->real_com_num!}</td>
                            <td align="center" >${goods_info->shop_com_num!}</td>
                        </tr>
                    </tbody>
                </table>

                <div class="promote_config">
                	<input type="hidden" name="goods_id" value="${goods_id!}">
	                <div class="each_line clearfix">
	                    <div class="el_left"><em>*</em>用户名：</div>
	                    <div class="el_right">
	                        <input type="text" placeholder="请填写用户名" name="bogus_username" value="">
	                    </div>
	                </div>
	                <div class="each_line clearfix">
	                    <div class="el_left"><em>*</em>头像：</div>
	                    <div class="el_right">
                            <ul class="share_img_area clearfix" >
                                <li class="add_user_avatar">
                                    <input type="hidden" name="bogus_user_avatar" value="">
                                    <img src="http://${image_domain!}/image/admin/add_img.png" class="avatar_add_img " default_img="http://${image_domain!}/image/admin/add_img.png">
                                    <!-- <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class=" user_avatar_delete" style="display: none;" > -->
                                </li>
                                <span class="promote_tip">建议尺寸：200*200像素</span>
                            </ul>
	                    </div>
	                </div>
	                <div class="each_line clearfix">
	                    <div class="el_left"><em>*</em>评价日期：</div>
	                    <div class="el_right">
	                        <input type="text" name="in_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value=""/>
	                    </div>
	                </div>

	                <div class="each_line clearfix">
	                    <div class="el_left"><em>*</em>评分：</div>
	                    <div class="el_right">
	                    	<div class="commstar">
	                    		<a href="javascript:" class="star1" _val="1">
									<img src="http://${image_domain!}/image/admin/collect_yes.png">
								</a>
								<a href="javascript:" class="star2" _val="2">
									<img src="http://${image_domain!}/image/admin/collect_yes.png">
								</a>
								<a href="javascript:" class="star3" _val="3">
									<img src="http://${image_domain!}/image/admin/collect_yes.png">
								</a>
								<a href="javascript:" class="star4" _val="4">
									<img src="http://${image_domain!}/image/admin/collect_yes.png">
								</a>
								<a href="javascript:" class="star5" _val="5">
									<img src="http://${image_domain!}/image/admin/collect_yes.png">
								</a>
	                    	</div>
							
                            <input type="hidden" name="commstar" value="5">
                            <div class="clr"></div>
	                    </div>
	                </div>

	                <div class="each_line clearfix">
	                    <div class="el_left"><em>*</em>心得：</div>
	                    <div class="el_right">
	                        <textarea name="comm_note" class="comm_note"></textarea>
	                    </div>
	                </div> 
	                <div class="each_line clearfix">
	                    <div class="el_left">晒单：</div>
	                    <div class="el_right">
                            <ul class="comm-item-img clearfix">
                                <li class="add_class">
                                    <input name="comm_img[]" type="hidden">
                                    <img src="http://${image_domain!}/image/admin/add_img.png" class="add_img" alt="" />
                                    <img src="http://${image_domain!}/image/admin/icon_delete.png" alt="" class="img-delete comm_img_delete" style="display: none;" />
                                    <img src="http://${image_domain!}/image/admin/master_diagram_left.png" alt="" class="master_diagram_left" style="display: none;">
                                    <img src="http://${image_domain!}/image/admin/master_ diagram_right.png" alt="" class="master_diagram_right" style="display: none;">
                                </li>
                                <div class="promote_tip">最多3张，建议尺寸：800*800像素</div>
                            </ul>
	                    </div>
	                </div>
	                	                
	           	    <div class="each_line clearfix">
	                    <div class="el_left">是否匿名：</div>
	                    <div class="el_right">
	                        <label for="need" class="anonymousflag">
	                            <input type="checkbox" name="anonymousflag" value="1">是
	                        </label>
	                        <span class="need_desc">勾选时将不显示用户头像</span>
	                    </div>
	                </div>
	            </div>
            </div>
            <div class="fix_footer">
                <a href="##" is_continue="0" class="btn_save btn_pub">发布</a>
                <a href="##" is_continue="1" class="btn_save btn_continue">继续添加</a>
            </div>
            
        </form>
    </div>
</div>

<script type="text/javascript" src="/js/admin/lang/zh-CN/image_common.js"></script>
<script language="JavaScript" src="/js/admin/jImageManager.js?v=1.0.0"></script>
<script src="/js/admin/comment_add.js?v=1.0.5" type="text/javascript"></script>
<script>
	$('.fix_footer').width($('.main-container').width());
    function picker(){
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }

    $(".commstar>a").mouseover(function () {
	    var val = $(this).attr("_val");
	    changeStar(val);
	});

	$(".commstar>a").mouseout(function () {
	    var val = $('[name="commstar"]').val();
	    changeStar(val);
	});

	$(".commstar>a").click(function () {
	    var val = $(this).attr("_val");
	    changeStar(val);
	    $('[name="commstar"]').val(val);
	});

	function changeStar(val) {
		$(".commstar>a").each(function () {
	        var v = $(this).attr("_val");
	        if (v <= val) {
	            $(this).children('img').attr('src','http://${image_domain!}/image/admin/collect_yes.png');
	        }else{
	            $(this).children('img').attr('src','http://${image_domain!}/image/admin/collect_no.png');
	        }
	    });
	}
</script>
<#include "/admin/footer.ftl">
