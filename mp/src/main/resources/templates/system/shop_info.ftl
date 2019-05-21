@include("system.header")
<link rel="stylesheet" href="/css/system/shop_list.css" type="text/css"/>
<ul id="tab" class="nav nav-tabs">

    <li @if($nav_type==0)class="active"@endif>
        <a href="#" data-toggle="tab" onclick="location.href = '/system/shop/list';">店铺列表</a></li>

    @if($nav_type==2)
        <li class="active"><a href="#" data-toggle="tab" url="#">店铺添加</a></li>
    @endif
    @if($nav_type==3)
        <li class="active"><a href="#" data-toggle="tab" url="#">店铺编辑</a></li>
    @endif
</ul>

<form action="{{ $act_url }}" name="form1" id="form1" method="post"  >
    <input type="hidden" name="act" id="act" value="{{ $act }}">
    <input type="hidden" name="shop_id" id="shop_id" value="{{ $shop->shop_id }}">
    {{ csrf_field() }}
    <div class="box panel">
        <div class="panel-body">
            <table class="tab_body show table">
                <tr>
                    <td>
                        所属账号
                    </td>
                    <td>
                        {{ $shop_account->user_name }}
                    </td>
                </tr>
                <tr>
                    <td>
                        <span style="color: red">*</span>手机号
                    </td>
                    <td>
                        <input type="text" name="pre_mobile" value="{{$shop->mobile}}" hidden>
                        <input type="text" name="mobile" value="{{ $shop->mobile }}">
                        <span id="check_mobile"></span>
                    </td>
                </tr>
                <tr>
                    <td>
                        店铺类型
                    </td>
                    <td>
                        <select name="shop_type">
                            {{--<option value="0" @if($shop->shop_type==0) selected @endif>通用版</option>--}}
                            {{--<option value="1" @if($shop->shop_type==1) selected @endif>试用版</option>--}}
                            <option value="" selected >请选择店铺类型</option>
                            @foreach($version_list as $item)
                            <option value="{{$item->level}}" @if($shop->shop_type==$item->level) selected @endif>{{$item->version_name}}</option>
                            @endforeach
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        到期时间
                    </td>
                    <td>
                        <input type="text" name="expire_time" value="{{ $shop->expire_time }}" onClick="picker();" autocomplete="off">
                    </td>
                </tr>
                <tr>
                    <td>
                        接收短信手机号
                    </td>
                    <td>
                        <input type="text" name="receive_mobile" value="{{ $shop->receive_mobile }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <span style="color: red">*</span>店铺名称
                    </td>
                    <td>
                        <input type="text" name="shop_name" value="{{ $shop->shop_name }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        店铺客服电话
                    </td>
                    <td>
                        <input type="text" name="shop_phone" value="{{ $shop->shop_phone }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        店铺公告
                    </td>
                    <td>
                        <textarea name="shop_notice" cols="19" rows="4">{{ $shop->shop_notice }}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        店铺微信
                    </td>
                    <td>
                        <input type="text" name="shop_wx" value="{{ $shop->shop_wx }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        店铺邮箱
                    </td>
                    <td>
                        <input type="text" name="shop_email" value="{{ $shop->shop_email }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        店铺禁用
                    </td>
                    <td>
                        <input type="checkbox" name="is_enabled"  @if($shop->is_enabled == 1) checked @endif style="vertical-align: middle;margin-top: -1px">禁用
                    </td>
                </tr>

                <tr>
                    <td>
                        店铺标记
                    </td>
                    <td>
                        <select name="shop_flag">
                            <option value="" selected >请选择店铺标记</option>
                            <option value="1" @if($shop->shop_flag==1) selected @endif>欧派</option>
                            <option value="2" @if($shop->shop_flag==2) selected @endif>寺库</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        隐藏底部
                    </td>
                    <td>
                        <input type="checkbox" name="hid_bottom"  @if($shop->hid_bottom == 1) checked @endif style="vertical-align: middle;margin-top: -1px">隐藏
                    </td>
                </tr>
                <tr>
                    <td>
                        欧派店铺标识
                    </td>
                    <td>
                        <input type="text" name="member_key" value="{{ $shop->member_key }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        欧派大屏租户名称
                    </td>
                    <td>
                        <input type="text" name="tenancy_name" value="{{ $shop->tenancy_name }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        欧派大屏用户名
                    </td>
                    <td>
                        <input type="text" name="user_name" value="{{ $shop->user_name }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        欧派大屏密码
                    </td>
                    <td>
                        <input type="text" name="password" value="{{ $shop->password }}">
                    </td>
                </tr>
                <tr>
                    <td>
                        店铺客服QQ
                    </td>
                    <td>
                        <input type="text" name="shop_qq" value="{{ $shop->shop_qq }}">
                    </td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input type="button" value="保存" class="primary" onclick="return on_submit();">&nbsp;&nbsp;&nbsp;
                        @if(!$shop->shop_id)
                            <span class="text-danger">
                                添加新店铺，会创建此店铺的数据库。添加店铺只可以禁用，不能删除！，谨慎添加。
                            </span>
                        @endif
                    </td>
                </tr>

            </table>

        </div>
    </div>
</form>

<script>
    // $('input[name="mobile"]').change(function () {
    //         on_submit();
    //     }
    // );
    function on_submit() {
        if($('input[name="shop_name"]').val() == ''){
            art.dialog.tips('店铺名称不能为空');
            $('input[name="shop_name"]').focus();
            return false;
        }
        if($('input[name="mobile"]').val() == ''){
            art.dialog.tips('手机号不能为空');
            $('input[name="mobile"]').focus();
            return false;
        }
        if($("select").val() == ""){
            art.dialog.tips('店铺类型不能未空');
            $('select').focus();
            return false;
        }
        // 写入验证代码
        var param = {};
        var pre_mobile = $('input[name="pre_mobile"]').val();
        // console.log(pre_mobile);
        param.mobile = $('input[name="mobile"]').val();
        if (pre_mobile != $('input[name="mobile"]').val()){
            util.ajax_json("/system/shop/check/mobile", function (d) {
                if (d && d.error == 0) {
                    $("#check_mobile").text(d.content);
                    // art.dialog.tips(d.content);
                    setTimeout(function () {
                        $("#form1").submit();
                    }, 500);
                    return true;
                } else {
                    $("#check_mobile").text(d.content);
                    //art.dialog.tips(d.content);
                    //$('input[name="mobile"]').focus();
                    if(confirm(d.content+"，确定继续创建此店铺吗？")){
                        setTimeout(function () {
                            $("#form1").submit();
                        }, 500);
                        return true;
                    }
                    return false;
                }
            }, param);
        }else{
            $("#form1").submit();
        }
    }
    function picker(){
        return WdatePicker({dateFmt:'yyyy-MM-dd',autoUpdateOnChanged:false});
    }
</script>


@include("system.footer")
