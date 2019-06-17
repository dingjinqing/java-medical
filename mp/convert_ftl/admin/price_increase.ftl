<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/price_increase.css?v=1.0.1" type="text/css" />
<style type="text/css">
    a:link,a:focus,a:hover,a:active{
        text-decoration: none;
    }
</style>
<div style="min-width: 1090px">
    <div class="title">
        <span><a href="/admin/market/view?top_index=4">营销管理</a> / </span>
        <span style="color: #666;">加价购</span>
    </div>
    <div class="main-container">
        <div class="content">
            <div class="head">
                <ul class="clearfix">
                    <li>
                        <a href="/admin/market/purchase/list?nav=0">全部加价购</a>
                    </li>
                    <li><a href="/admin/market/purchase/list?nav=1">进行中</a></li>
                    <li><a href="/admin/market/purchase/list?nav=2">未开始</a></li>
                    <li><a href="/admin/market/purchase/list?nav=3">已过期</a></li>
                    <li><a href="/admin/market/purchase/list?nav=4">已停用</a></li>
                    <li class="actives"><a href="##"><#if  (!empty($config))编辑<#else>添加</#if>加价购活动</a></li>
                </ul>
            </div>
            <div class="price_increase">
                <div class="increase_title">
                    <div class="set_rule">设置活动规则</div>
                    <div class="set_main_goods">设置主商品</div>
                    <div class="set_change_goods">设置换购商品</div>
                </div>
                <div class="increase_content">
                    <form action="/admin/market/purchase/add" id="form1" method="post">
                        {{csrf_field()!}
                        <input type="hidden" name="id" value="${config->id!}"/>
                        <div class="con_set_rule">
                            <ul>
                                <li class="clearfix">
                                    <div class="fl">
                                        <em>*</em>
                                        活动名称：
                                    </div>
                                    <div class="fr_l">
                                        <input type="text" name="name" value="${config->name!}"/>
                                        <span>只作为商家记录使用，用户不会看到这个名称</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="fl">
                                        <em>*</em>
                                        活动优先级：
                                    </div>
                                    <div class="fr_l">
                                        <input type="text" name="level" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${config->level!}"/>
                                        <span>用于区分不同加价购活动的优先级，请填写正整数，数值越大优先级越高</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="fl">
                                        <em>*</em>
                                        活动时间：
                                    </div>
                                    <div class="fr_l">
                                        <input type="text" name="start_time" id="startDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,maxDate:'#F{$dp.$D(\'endDate\')}'})" autocomplete="off" value="${config->start_time!}"/>
                                        至&nbsp;&nbsp;
                                        <input type="text" name="end_time" id="endDate" onclick="picker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoUpdateOnChanged:false,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'2037-12-31 23:59:59'})" autocomplete="off" value="${config->end_time!}"/>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="fl">
                                        每单最多换购
                                    </div>
                                    <div class="fr_l">
                                        <input type="text" name="max_change_purchase" class="ipt_small" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${config->max_change_purchase ?? 0!}"/>件
                                        <span>每单最多换购商品数，填写0表示不限制</span>
                                    </div>
                                </li>
                                <li class="clearfix">
                                    <div class="fl">
                                        <em>*</em>
                                        活动规则：
                                    </div>
                                    <div class="fr_l">
                                        <span>最多可设置3档换购规则，需满足金额依次递增</span>
                                    </div>
                                </li>
                                <#if  (empty($config))
                                    <li class="clearfix rule_list rule_list1">
                                        <div class="fl">
                                            <em>*</em>
                                            换购规则<span>1</span>：
                                        </div>
                                        <div class="fr_l">
                                            主商品购满
                                            <input type="text" class="ipt_small" name="full_price[]" />
                                            元可加
                                            <input type="text" class="ipt_small" name="purchase_price[]" />
                                            元换购
                                            <a href="##" class="add_rule">+ 添加规则</a>
                                        </div>
                                    </li>
                                </#if>
                                <#list  ($rule as $item)
                                <li class="clearfix rule_list rule_list${loop->iteration!}">
                                    <div class="fl">
                                        <em>*</em>
                                        换购规则<span>${loop->iteration!}</span>：
                                    </div>
                                    <div class="fr_l">
                                        主商品购满
                                        <input type="text" class="ipt_small" name="full_price[]" value="${item-> full_price!}"/>
                                        元可加
                                        <input type="text" class="ipt_small" name="purchase_price[]" value="${item-> purchase_price!}"/>
                                        元换购
                                        <#if  (count(object2array($rule)) <= 2 && $loop->last)
                                        <a href="##" class="add_rule">+ 添加规则</a>
                                        </#if>
                                        <#if  (!$loop->first)
                                        <a href="##" class="del_rule">删除</a>
                                        </#if>
                                    </div>
                                </li>
                                </#list>
                            </ul>
                        </div>
                        <div class="con_set_main" style="display: none;">
                            <div class="add_change_goods" item="1" val="1">添加主商品</div>
                            <input type="hidden" name="goods_id" value="${config->goods_id!}"/>
                            <div class="main_goods_table">
                                <table class="tb_goods">
                                    <thead>
                                        <tr class="change_tr_first">
                                            <td width="40%">商品名称</td>
                                            <td>商品原价</td>
                                            <td>商品库存</td>
                                            <#if  (!$config || !($config->status == 0 || time() > strtotime($config->start_time)))
                                            <td>操作</td>
                                            </#if>
                                        </tr>
                                    </thead>
                                    <tbody id="main_goods_list">
                                        <#list  ($goods as $item)
                                            <tr>
                                                <td>
                                                    <div class="goods_img">
                                                        <img src="${item->goods_img!}" />
                                                    </div>
                                                    <div class="goods_info">
                                                        <div class="goods_name">${item->goods_name!}</div>
                                                        <div class="goods_spec">${item->goods_spec!}</div>
                                                    </div>
                                                </td>
                                                <td>${item->shop_price!}</td>
                                                <td>${item->goods_number!}</td>
                                                <td><a href="##" item="${item->goods_id!}" class="goods_del">删除</a></td>
                                            </tr>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="con_set_change" style="display: none">
                            <div>
                                <ul class="change_head clearfix">
                                    <li class="change_active change_rule" val="1">规则1</li>
                                    <li class="change_rule" val="2">规则2</li>
                                    <li class="change_rule" val="3">规则3</li>
                                    <input type="hidden" name="product_id[1]" value="${rule[0]->product_id ?? ''!}"/>
                                    <input type="hidden" name="product_id[2]" value="${rule[1]->product_id ?? ''!}"/>
                                    <input type="hidden" name="product_id[3]" value="${rule[2]->product_id ?? ''!}"/>
                                </ul>
                                <div class="change_content">
                                    <div class="change_con_goods change_con_goods1">
                                        <p class="prompt"><img src="/image/admin/notice_img.png">换购规则1：购满<span class="span1">99.00</span>元，加价<span class="span2">19.90</span>元换购以下商品</p>
                                        <div class="add_change_goods" item="2">添加换购商品</div>
                                        <table class="tb_goods">
                                            <thead>
                                            <tr class="change_tr_first">
                                                <td width="40%">商品名称</td>
                                                <td>商品原价</td>
                                                <td>商品库存</td>
                                                <#if  (!$config || !($config->status == 0 || time() > strtotime($config->start_time)))
                                                <td>操作</td>
                                                </#if>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <#if  (!empty($ruleProduct[0]))
                                            <#list  ($ruleProduct[0] as $item)
                                                <tr>
                                                    <td>
                                                        <div class="goods_img">
                                                            <img src="${item->goods_img!}" />
                                                        </div>
                                                        <div class="goods_info">
                                                            <div class="goods_name">${item->goods_name!}</div>
                                                            <div class="goods_spec">${item->goods_spec!}</div>
                                                        </div>
                                                    </td>
                                                    <td>${item->prd_price!}</td>
                                                    <td>${item->prd_number!}</td>
                                                    <td><a href="##" item="${item->prd_id!}" class="change_goods_del">删除</a></td>
                                                </tr>
                                            </#list>
                                            </#if>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="change_con_goods change_con_goods2" style="display: none">
                                        <p class="prompt"><img src="/image/admin/notice_img.png">换购规则2：购满<span class="span1">99.00</span>元，加价<span class="span2">19.90</span>元换购以下商品</p>
                                        <div class="add_change_goods" val="2">添加换购商品</div>
                                        <table class="tb_goods">
                                            <thead>
                                            <tr class="change_tr_first">
                                                <td width="40%">商品名称</td>
                                                <td>商品原价</td>
                                                <td>商品库存</td>
                                                <#if  (!$config || !($config->status == 0 || time() > strtotime($config->start_time)))
                                                <td>操作</td>
                                                </#if>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <#if  (!empty($ruleProduct[1]))
                                                <#list  ($ruleProduct[1] as $item)
                                                    <tr>
                                                        <td>
                                                            <div class="goods_img">
                                                                <img src="${item->goods_img!}" />
                                                            </div>
                                                            <div class="goods_info">
                                                                <div class="goods_name">${item->goods_name!}</div>
                                                                <div class="goods_spec">${item->goods_spec!}</div>
                                                            </div>
                                                        </td>
                                                        <td>${item->prd_price!}</td>
                                                        <td>${item->prd_number!}</td>
                                                        <td><a href="##" item="${item->prd_id!}" class="change_goods_del">删除</a></td>
                                                    </tr>
                                                </#list>
                                            </#if>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="change_con_goods change_con_goods3" style="display: none;">
                                        <p class="prompt"><img src="/image/admin/notice_img.png">换购规则3：购满<span class="span1">99.00</span>元，加价<span class="span2">19.90</span>元换购以下商品</p>
                                        <div class="add_change_goods" val="3">添加换购商品</div>
                                        <table class="tb_goods">
                                            <thead>
                                            <tr class="change_tr_first">
                                                <td width="40%">商品名称</td>
                                                <td>商品原价</td>
                                                <td>商品库存</td>
                                                <#if  (!$config || !($config->status == 0 || time() > strtotime($config->start_time)))
                                                <td>操作</td>
                                                </#if>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <#if  (!empty($ruleProduct[2]))
                                                <#list  ($ruleProduct[2] as $item)
                                                    <tr>
                                                        <td>
                                                            <div class="goods_img">
                                                                <img src="${item->goods_img!}" />
                                                            </div>
                                                            <div class="goods_info">
                                                                <div class="goods_name">${item->goods_name!}</div>
                                                                <div class="goods_spec">${item->goods_spec!}</div>
                                                            </div>
                                                        </td>
                                                        <td>${item->prd_price!}</td>
                                                        <td>${item->prd_number!}</td>
                                                        <td><a href="##" item="${item->prd_id!}" class="change_goods_del">删除</a></td>
                                                    </tr>
                                                </#list>
                                            </#if>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="footer">
                <a href="##" class="set1 next1" style="margin-right: 10px;">下一步</a>
                <a href="##" class="set2 prev1" style="margin-right: 10px;">上一步</a>
                <a href="##" class="set2 next2" style="margin-right: 10px;">下一步</a>
                <a href="##" class="set3 prev2" style="margin-right: 10px;">上一步</a>
                <a href="##" class="set3 save">保存</a>
            </div>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">
<ul class="hide rule_clone">
    <li class="clearfix rule_list">
        <div class="fl">
            <em>*</em>
            换购规则<span>1</span>：
        </div>
        <div class="fr_l">
            主商品购满
            <input type="text" class="ipt_small" name="full_price[]" />
            元可加
            <input type="text" class="ipt_small" name="purchase_price[]" />
            元换购
            <a href="##" class="add_rule">+ 添加规则</a>
            <a href="##" class="del_rule">删除</a>
        </div>
    </li>
</ul>
<script type="text/javascript">
    var hasSaved = false;
    var is_show_save = false;
</script>
<script src="/js/admin/price_increase.js?v=1.3.1" type="text/javascript"></script>
<script type="text/javascript">
    $('.footer').width($('.content').width());

    function picker(){
        hasSaved = false;
        return WdatePicker(
            {
                dateFmt: 'yyyy-MM-dd HH:mm:ss',
                autoUpdateOnChanged: false
            }
        );
    }
    util.inputChange();
    window.onbeforeunload = function(event) {
        if(hasSaved == false){
            return '确认要离开吗？';
        }
    };
    var msg = '{{ session('message')!}';
    if (msg) {
        util.mobile_alert(msg);
    }
    <#if  ($config)
        <#if  ($config->status == 0 || strtotime($config->end_time) < time())
            $('input').prop('disabled', 'disabled');
            $('.add_change_goods').hide();
            $('.change_goods_del, .goods_del').parent().remove();
            $('.add_rule, .del_rule').remove();
        </#if>
        <#if  ($config->status == 1 && strtotime($config->start_time) < time() &&
            strtotime($config->end_time) > time())
            $('input').prop('disabled', 'disabled');
            $('.add_change_goods').hide();
            $('.add_rule, .del_rule').remove();
            $('.change_goods_del, .goods_del').parent().remove();
            $('input[name="name"], input[name="level"], input[type="hidden"]').removeAttrs('disabled');
        </#if>
        is_show_save = true;
        $('.save').css('display', 'inline-block');
    </#if>
        getPowerInfo('main_config','purchase_price','sub_4','加价购',0);
</script>