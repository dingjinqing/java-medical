<html style="height: 70px;">
<head>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <style>
        a{
            text-decoration: none;
        }
        #set-goods{
            padding:10px 56px;
            border-bottom: 1px solid #eee;
        }
        #set-goods:after{
            content: '';
            display: block;
            clear: both;
        }
        #set-goods label{
            height:30px;
            line-height: 30px;
            margin-left: 60px;
            color: #333;
            font-size: 14px;
            float: left;
        }
        #set-goods textarea{
            padding-left:12px;
            float: left;
            border: 1px solid #eee;
            height:100px;
            font-family: "微软雅黑";
        }
    </style>
</head>
<body style="background:none;height: 70px;">
<div id="set-goods">
    {{--<label for="group_name">添加备注：</label>--!}
    <textarea name="admin_message" id="" cols="30" rows="10"></textarea>
</div>
<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
</body>
</html>