<html style="height: 70px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <style>
        a{
            text-decoration: none;
        }
        #set-goods{
            margin-top: 20px;
            padding:10px 0;
            border-bottom: 1px solid #eee;
            padding-bottom: 27px;
        }
        #set-goods label{
            height:30px;
            line-height: 30px;
            margin-left: 60px;
            color: #333;
            font-size: 14px;
        }
        #set-goods input{
            height: 30px;
            line-height: 30px;
            padding-left:12px;
            border: 1px solid #eee;
        }
    </style>
</head>
<body style="background:none;height: 70px;">
<div id="set-goods">
    <label for="group_name">分组名称：</label>
    <input type="text" id="group_name">
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>
    $('#set-goods').on('click','.goods_tb tbody tr',function(){
        var flag_back = $(this).attr('data-back');
        if(flag_back == 'true'){
            $(this).addClass('goods_tr_choose');
            $(this).attr('data-back','false');
            flag_back = 'false';
        }else if(flag_back == 'false'){
            $(this).removeClass('goods_tr_choose');
            $(this).attr('data-back','true');
            flag_back = 'true';
        }
    });
</script>
</body>
</html>