<html >
<head>
    <link href="/css/system/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/system/reduce_price.css?v=1.1.0" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="/css/system/goods_label.css?v=1.0.9" type="text/css" />
    <style>
        a{
            text-decoration: none;
        }
        .goods_area table {
          width: 590px;
            border: 1px solid #eee !important;
        }
        .goods_area table thead tr td{
            font-weight: 400 !important;
            font-size: 16px;
        }
        .goods_area table tbody td{
            border: 1px solid #eee !important;
            word-break: break-all;
        }

        input[type="text"]{
            width: 80px;
            height: 30px;
            padding-left: 0px;
            text-align: center;
        }
        thead tr td{
            border: 0;
        }
        .goods_table td{
            border: none;
        }
        input[type="text"]:focus{
            border:1px solid #447af9;
        }
    </style>
</head>
<body style="background:none;">
<div class="goods_area" style="display: block">
    <table class="goods_table" width="100%" >
        <thead style="border:0 !important;cellpadding:0 ;cellspacing:0">
        <tr>
            <td width="50%">规格名称</td>
            <td>原价</td>
            <td>折后价</td>
        </tr>
        </thead>
        <tbody class="label_tbody">
        <#list ($data_list as $item)
            <tr>
                <td width="50%">${item->prd_desc!}</td>
                <td class="prd_price">${item->prd_price!}</td>
                <td><input type="text" class="price" value="${item->reduce_price!}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script>

</script>
</body>
</html>