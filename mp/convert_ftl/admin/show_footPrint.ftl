<table border="1px" style="border-collapse: collapse;
    border-spacing: 0;">
    <tr>
    <th>id</th>
    <th>goods_id</th>
    <th>user_id</th>
    <th>update_time</th>
    </tr>
    <#list ($data_list as $item)
        <tr>
        <td>${item->id!}</td>
        <td>${item->goods_id!}</td>
        <td>${item->user_id!}</td>
        <td>${item->update_time!}</td>
        </tr>
    </#list>
</table>