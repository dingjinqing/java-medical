<html style="height: 70px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <style>
        a{
            text-decoration: none;
        }
        #set-goods{
            padding:20px 0;
            border-bottom: 1px solid #ccc;
            height: 80%;
        }
        #set-goods label{
            height:30px;
            line-height: 30px;
            margin-left: 60px;
            color: #333;
            font-size: 14px;
        }
        #set-goods input[type='text']{
            height:30px;
            padding-left:12px;
            border: 1px solid #ccc;
            width: 150px;
        }
        .verify_pay label{
            cursor: pointer;
        }
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
        select {
            line-height: 30px;
            padding-left: 12px;
            border: 1px solid #ccc;
            outline: none;
            background-color: #fff;
            width: 150px;
            height: 30px;
        }
    </style>
</head>
<body style="background:none;height: 200px;">
<div id="set-goods">
    <label for="group_name">请输入核销码：</label>
    <input type="text" id="check_number">
    <br>
    <label for="group_name">请选择核销方式：</label>
    <div class="verify_pay">
        <label for="store_pay">
            <input type="radio" name="verify_pay" value="0" class="checkbox_prev" id="store_pay" checked />
            门店买单
        </label>
        <br />
        <label for="card">
            <input type="radio" name="verify_pay" value="1" class="checkbox_prev" id="card" />
            会员卡
        </label>&nbsp;&nbsp;&nbsp;
        <div style="display:inline-block;">
        <select name="card_id" id="card_id"  hidden="true">
            <#if (!$common_card && !$count_card)
                <option value="" disabled selected>没有会员卡可选</option>
            <#else>
                <#if ($common_card)
                    <option disabled selected>请选择普通会员卡</option>
                    <#list ($common_card as $common)
                        <option value="${common->card_id!}" moneycount="${common->money!}" card_no="${common->card_no!}">${common->card_name!}</option>
                    </#list>
                </#if>
                <#if ($count_card)
                    <option disabled <#if (!$common_card) selected </#if>>请选择限次会员卡</option>
                    {{--{{print_r($count_card)!}--!}
                    <#list ($count_card as $count)
                        <option value="${count->card_id!}" moneycount="${count->surplus!}" card_no="${count->card_no!}">${count->card_name!}</option>
                    </#list>
                </#if>
            </#if>
        </select>
        <input type="text" name="reduce" placeholder="请输入金额或次数" hidden="true" />
        <input type="text" name="reason1" placeholder="请输入扣除原因" hidden="true" />
        </div>
        <br />
        <label for="balance">
            <input type="radio" name="verify_pay" value="2" class="checkbox_prev" id="balance" />
            账户余额
        </label>
        <input type="text" name="balance" placeholder="${account!}"  hidden="true" />
        <input type="text" name="reason2" placeholder="请输入扣除原因" hidden="true" />
    </div>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
    $("#store_pay").on("click",function () {
        $("input[name='balance']").attr("hidden",'true');
        $("input[name='reason2']").attr("hidden",'true');
        $("select[name='card_id']").attr("hidden",'true');
        $("input[name='reduce']").attr("hidden","true");
        $("input[name='reason1']").attr("hidden","true");
    })
    $("#card").on("click",function(){
       if($(this).is(":checked")){
           $("select[name='card_id']").removeAttr("hidden");
           $("input[name='reduce']").removeAttr("hidden");
           $("input[name='reason1']").removeAttr("hidden");
           $("input[name='balance']").attr("hidden",'true');
           $("input[name='reason2']").attr("hidden",'true');
       }
    })
    // var moneycount = "";
    $("#card_id").on("change",function(){
        // console.log($("#card_id option:selected").attr("moneycount"));
        var moneycount = $("#card_id option:selected").attr("moneycount");
        $("input[name='reduce']").attr("placeholder",moneycount);
    })
    $("#balance").on("click",function(){
        if($(this).is(":checked")){
            $("select[name='card_id']").attr("hidden",'true');
            $("input[name='reduce']").attr("hidden","true");
            $("input[name='reason1']").attr("hidden","true");
            $("input[name='balance']").removeAttr("hidden");
            $("input[name='reason2']").removeAttr("hidden");
        }
    })
</script>
</body>
</html>