<htm>
    <head>
        <title>砍价测试</title>
        <script type="text/javascript" src="{{asset('js/jquery-1.11.1.min.js')!}"></script>
    </head>
    <body>
        <div>
            <form action="{{ url('/admin/official/bargain/test')!}" method="post" id="form_test">
                {{ csrf_field()!}
                商品价格：<input name="goods_price" type="number" value="${post['goods_price'] ?? 500!}">
                商品底价：<input name="expectation_price" type="number" value="${post['expectation_price']!}">
                人数：<input name="expectation_number" type="number" value="${post['expectation_number'] ?? 25!}">
                首次最低：<input name="bargain_min" type="number" value="${post['bargain_min']!}">%
                首次最高：<input name="bargain_max" type="number" value="${post['bargain_max']!}">%
                <button class="submit">提交</button>
            </form>
        </div>

        <table border="1px">

            <#if  (is_array($list))
                <#list ($list as $record_id => $record)
                    <#if  (is_array($record))
                        <#if  ($loop->first)
                            <tr>
                                <td></td>
                                <#list ($record as $index => $td)
                                    <td style="width: 30px">${index + 1!}</td>
                                </#list>
                            </tr>
                        </#if>
                        <tr>
                            <td>${record_id!}</td>
                            <#list ($record as $td)
                                <td>{{ isset($td['bargain_money']) ? number_format($td['bargain_money'],2) : ''!}</td>
                            </#list>
                        </tr>
                    </#if>
                </#list>
            </#if>
        </table>
    </body>
    <script type="text/javascript">
        $('.submit').click(function () {
            let goods_price = parseFloat($('[name="goods_price"]').val() || 0);
            let expectation_price = parseFloat($('[name="expectation_price"]').val() || 0);
            let expectation_number = parseInt($('[name="expectation_number"]').val() || 25);
            let bargain_min = parseFloat($('[name="bargain_min"]').val() || 0);
            let bargain_max = parseFloat($('[name="bargain_max"]').val() || 0);
            if (!(goods_price > 0) || isNaN(goods_price)) {
                alert('商品价格必须>0');
                return false;
            }
            if (!(expectation_price >= 0) || isNaN(expectation_price)) {
                alert('商品底价必须>0');
                return false;
            }
            if (!(expectation_number > 2) || isNaN(expectation_number)) {
                alert('人数必须>=3');
                return false;
            }
            if (isNaN(bargain_min) || !(bargain_min >= 0) || bargain_min > 50) {
                alert('首次最低必须>0');
                return false;
            }
            if (isNaN(bargain_max) || !(bargain_max >= 0) || bargain_max > 50) {
                alert('首次最低必须>0');
                return false;
            }
            $('#form_test').submit();
        })
    </script>
</htm>