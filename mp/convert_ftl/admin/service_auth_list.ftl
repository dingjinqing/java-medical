<#include "/admin/toggle_header.ftl">
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/layui_change.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/account_user.css?v=1.0.0">
<link rel="stylesheet" href="/css/admin/shop_setting.css?v=1.2" type="text/css" />

<style type="text/css">
    .add-child-btn{
        display: inline-block;
        text-align: center;
        margin: 0 8px 0 50px;
    }
    tbody tr {
        text-align: center;
    }
    .question_bg{
        margin-top: -5px;
        margin-left: -6px;
    }
    .question_fa{
        position: relative;
    }
    .contain_quertion{
        display: none;
        width: 330px;
        height: 220px;
        position: absolute;
        top: 0px;
        right: -320px;
        z-index: 2;
    }
    .question_div{
        border: 1px solid #ccc;
        margin-left: 20px;
        background-color: white;
        z-index: 2;
        padding: 10px;
    }
    .question_div>p{
        font-size: 12px;
        line-height: 30px;
        text-indent: 2em;
    }
    .question_div>p>a{
        color: #5a8bff;
        text-decoration: none;
    }
    .question_div>p>a:hover{
        color: #5a8bff;
        text-decoration: underline;
    }
    .question_div>p>a:active{
        color: #5a8bff;
    }
</style>
<div style="padding: 10px;">
    <ul class="add-child-ul">
        <li class="question_fa">
            <a href="/wechat/official/account/authorization" link="/wechat/official/account/authorization" target="_blank" class="add-child-btn">添加授权</a>
            注意：仅可以授权认证服务号.授权完成认证服务号，关注服务号的用户在接收小程序消息通知时优先通过小程序接收。
            <img src="/image/admin/analysis_tishi.png" class="question_bg">
            <div class="contain_quertion">
            <div class="question_div">
                <p> 由于微信平台相关要求，公众号授权完成后，
                    需要在店铺内小程序管理-小程序授权绑定和该店铺小程序同主体的公众号，绑定后，使用该绑定公众号登录微信公众平台，
                    在公众平台小程序小程序管理页面添加关联小程序，
                    关联该小程序后，关注公众号的用户在接收小程序消息通知时即可通过公众号接收。
                </p>
                <p>
                    小程序接收消息限制较大，详情查看<a href="https://developers.weixin.qq.com/miniprogram/dev/api/notice.html#%E4%B8%8B%E5%8F%91%E6%9D%A1%E4%BB%B6%E8%AF%B4%E6%98%8E">模板消息下发条件说明</a>，此功能方便商家针对用户进行小程序消息通知。
                </p>
            </div>
            </div>
        </li>
    </ul>

    <form action="" name="form1" id="form1" method="post" class="add-account-form">
        <input type="hidden" name="act" id="act" value="">
        <input type="hidden" name="account_id" id="account_id" value="">
        {{ csrf_field()!}
        <div class="box panel panel-default main-table" id="main-table">
            <table id="list_tbl" cellspacing='1' cellpadding='3' width="90%" class="table">
                <thead>
                <tr id="first_tr">
                    <th>服务号名称</th>
                    <th>主体名称</th>
                    <th>公众微信号</th>
                    <th>授权时间</th>
                    <th>已绑定店铺</th>
                    <th>授权状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody class="account_tb">
                    <#list  ($list as $item)
                        <tr>
                            <td>${item->nick_name!}</td>
                            <td>${item->principal_name!}</td>
                            <td>${item->alias!}</td>
                            <td>${item->last_auth_time!}</td>
                            <td>
                                <#list  ($item->bind_mps as $shop)
                                    ${shop->nick_name!} <br />
                                </#list>
                            </td>
                            <td>
                                <#if  ($item->is_auth_ok == 1)
                                    已授权
                                    <#else>
                                    已取消
                                </#if>
                            </td>
                            <td>
                                <a href="/admin/public/service/auth/detail?app_id=${item->app_id!}">查看</a>
                                <a href="##" class="withdraw" app_id="${item->app_id!}" pay_mch_id="${item->pay_mch_id!}"  pay_key="${item->pay_key!}"  pay_cert_content="${item->pay_cert_content!}"  pay_key_content="${item->pay_key_content!}">提现配置</a>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
            {{--<table width="100%" border="0" class="tb_paging">--!}
                {{--<tr>--!}
                    {{--<td align="right">{{ trans("admin/common.page.page_info",['perPage'=>$data_list->perPage(),'currentPage'=>$data_list->currentPage(),'count'=>$data_list->count,'total'=>$data_list->total(),])!}--!}
                        {{--<a href="#" onClick="return gopage(1);">{{ trans("admin/common.page.first_page")!}</a>--!}
                        {{--<a href="#"--!}
                           {{--onClick="return gopage(${data_list->currentPage() -1!});">{{ trans("admin/common.page.pre_page")!}</a>--!}
                        {{--<a href="#"--!}
                           {{--onClick="return gopage(${data_list->currentPage() + 1!});">{{ trans("admin/common.page.next_page")!}</a>--!}
                        {{--<a href="#"--!}
                           {{--onClick="return gopage(${data_list->lastPage()!});">{{ trans("admin/common.page.last_page")!}</a>--!}
                        {{--<input id="page" name="page" type="text" value="${data_list->currentPage()!}"--!}
                               {{--size="5"--!}
                               {{--onKeyDown="if (event.keyCode==13 || event.which==13) gopage(this.value);">{{ trans("admin/common.page.page")!}--!}
                    {{--</td>--!}
                {{--</tr>--!}
            {{--</table>--!}
        </div>
    </form>
</div>
<div id="set-wx-pay" style="display: none">
    <table width="100%">
        <tr>
            <td>商户号:</td>
            <td><input type="text" name="pay_mch_id" value=""/></td>
        </tr>
        <tr>
            <td>支付秘钥:</td>
            <td><input type="text" name="pay_key" value="" /></td>
        </tr>
        <tr>
            <td>支付证书:</td>
            <td class="wx-file">
                <textarea name="pay_cert_content" style="width:240px;height:100px" ></textarea>
            </td>
        </tr>
        <tr>
            <td>支付私钥:</td>
            <td class="wx-file">
                <textarea name="pay_key_content" style="width:240px;height:100px"></textarea>
            </td>
        </tr>
    </table>
</div>

<script>
    {{--function gopage(page) {--!}
        {{--var last_page = '${data_list -> lastPage()!}';--!}
        {{--if(page>last_page) {--!}
            {{--page = last_page;--!}
        {{--}--!}
        {{--$("#page").val(page);--!}
        {{--$("#form1").submit();--!}
    {{--}--!}
   $(".question_bg").mouseover(function () {
        $(".contain_quertion").css("display","block");
        $(".contain_quertion").mouseover(function () {
            $(".contain_quertion").css("display","block");
        });
        $(".contain_quertion").mouseleave(function () {
            $(".contain_quertion").css("display","none");
        })
    });
   $(".question_bg").mouseleave(function () {
       $(".contain_quertion").css("display","none");
   });

    function remove_account(id) {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">' + '确认要删除吗？' + '</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                $("#act").val('del');
                $("#account_id").val(id);
                $("#form1").submit();
                layer.close(index);
            });
        });
    }

    $(document).ready(function () {
        $(".formtable td").mouseover(function () {
            $(".formtable tr").removeClass("hover_tr");
            $(this).parent().addClass("hover_tr");
        });
    });

    $('.withdraw').click(function(){
        //初始化
        var _this = $(this);
        $('#set-wx-pay').find('input[name="pay_mch_id"]').val(_this.attr('pay_mch_id'));
        $('#set-wx-pay').find('input[name="pay_key"]').val(_this.attr('pay_key'));
        $('#set-wx-pay').find('textarea[name="pay_cert_content"]').val(_this.attr('pay_cert_content'));
        $('#set-wx-pay').find('textarea[name="pay_key_content"]').val(_this.attr('pay_key_content'));
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer;
            layer.open({
                type: 1
                , title: ['提现配置', 'text-align:center;padding: 0px;']
                , offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                , area: '680px'
                , content: $('#set-wx-pay') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                , btn: ['确定', '取消']
                , btnAlign: 'r' //按钮居右
                , shade: [0.3, '#000'] //显示遮罩透明度和颜色
                , yes: function (index, layero) { //确定按钮的回调
                    var data={
                        app_id : _this.attr("app_id"),
                        pay_mch_id : $('#set-wx-pay').find('input[name="pay_mch_id"]').val(),
                        pay_key : $('#set-wx-pay').find('input[name="pay_key"]').val(),
                        pay_cert_content : $('#set-wx-pay').find('textarea[name="pay_cert_content"]').val(),
                        pay_key_content : $('#set-wx-pay').find('textarea[name="pay_key_content"]').val()
                    };
                    util.ajax_json('/admin/public/service/auth/list',function(d){
                        if(d&&d.error==0){
                            _this.attr("pay_mch_id", data.pay_mch_id);
                            _this.attr("pay_key", data.pay_key);
                            _this.attr("pay_cert_content", data.pay_cert_content);
                            _this.attr("pay_key_content", data.pay_key_content);
                            util.mobile_alert(d.message);
                            layer.close(index);
                        }
                        else{
                            util.mobile_alert(d.message);
                        }
                    },data);
                }, btn2: function (index, layero) {
                    //按钮取消的回调
                }
            });
        });
    });
    {{--var page_home = '${data_list->currentPage()!}'; //当前页码数--!}
    {{--var page_all = '${data_list->count!}';       //总页码数--!}
</script>
{{--<script src="/js/admin/page.js?v=1.0.0" type="text/javascript"></script>--!}

<#include "/admin/footer.ftl">
{{--<script type="text/javascript">--!}
    {{--$('.add-child-btn').click(function () {--!}
        {{--var url = $(this).attr("link");--!}
        {{--var data = getPowerInfo('main_config','authorization','sub_2','微信公众号授权');--!}
        {{--$(this).attr('href','##');--!}
        {{--$(this).removeAttr('target');--!}
        {{--if(data.content == 1){--!}
            {{--$(this).attr('href',url);--!}
            {{--$(this).attr('target','_blank');--!}
        {{--}--!}
    {{--});--!}
{{--</script>--!}
