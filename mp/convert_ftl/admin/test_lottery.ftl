<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
    <title>抽奖测试</title>
</head>
<body>
    <table>
        <thead>
            <td>中奖等级</td>
            <td>奖品</td>
        </thead>
        <tbody>
{{--            {{print_r($win,true)!}--!}
            一等奖：概率：${win[1]['chance']!}%，剩余奖品数：${win[1]['remain_number']!}，中奖数：${award[1]!}，实际中奖率：{{bcdiv($award[1],500,2)!}；<br/>
            二等奖：概率：${win[2]['chance']!}%，剩余奖品数：${win[2]['remain_number']!}，中奖数：${award[2]!}，实际中奖率：{{bcdiv($award[2],500,2)!}；<br/>
            三等奖：概率：${win[3]['chance']!}%，剩余奖品数：${win[3]['remain_number']!}，中奖数：${award[3]!}，实际中奖率：{{bcdiv($award[3],500,2)!}；<br/>
            四等奖：概率：${win[4]['chance']!}%，剩余奖品数：${win[4]['remain_number']!}，中奖数：${award[4]!}，实际中奖率：{{bcdiv($award[4],500,2)!}；<br/>
            未中奖：${award[0]!}。
            <#list ($list as $value)
                <tr>
                    <td>
                        <#if ($value['lottery_grade'] == 1)一等奖
                        <#elseif>($value['lottery_grade'] == 2)二等奖
                        <#elseif>($value['lottery_grade'] == 3)三等奖
                        <#elseif>($value['lottery_grade'] == 4)四等奖
                        <#else>未中奖
                        </#if>
                    </td>
                    <td>${value['lottery_award']!}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</body>