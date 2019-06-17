<#include "/admin/header.ftl">
<link rel="stylesheet" href="/css/admin/layui/css/layui.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/layui_change.css" type="text/css" />
<link rel="stylesheet" href="/css/admin/analysis_user_summary.css?v=1.0.3" type="text/css" />
{{--<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">--!}
<link rel="stylesheet" href="/css/element.css">
<style>
    .el-input__inner{
        border: 1px solid #ccc;
        height: 32px;
        line-height: 32px;
        width: 180px;
    }
    .el-input__prefix{
        top: -2px;
    }
    .el-input__suffix{
        left: 95px;
        top: -3px;
    }
</style>
<div id="analysis">
    <div class="title" >
        <span><a href="/admin/survey/overview?top_index=0">概况</a> / </span>
        <span style="color: #666;">
            用户统计
            {{--<#if  (empty($basicData))--!}
            {{--新建积分兑换商品--!}
            {{--<#else>--!}
            {{--查看积分兑换商品--!}
            {{--</#if>--!}
        </span>
    </div>
    <div class="layui-fluid">
        <div class="layui-one" style="overflow:hidden">
            {{--客户概况及趋势--!}
            <div class="layui-row layui-col-md12 customer-trend">
                <div class="layui-card">
                    <div class="layui-col-md12 tendency">
                        <i class="left_bat"></i>
                        <span>客户概况及趋势</span>
                        {{--<span class="item-image" style="display:inline-block">--!}
                        {{--<img src="image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">--!}
                        {{--<div class="float-layer">--!}
                        {{--<div class="float-layer-i"></div>--!}
                        {{--<div>--!}
                        {{--<span class="float-layer-left">访客数</span>--!}
                        {{--<span class="float-layer-right">筛选时间内，店铺所有页面被访问的去重人数，一个人在筛选时间范围内访问多次只记为一个</span>--!}
                        {{--</div>--!}
                        {{--<div>--!}
                        {{--<span class="float-layer-left">累积会员数</span>--!}
                        {{--<span class="float-layer-right">截至到筛选时间的最后一天，店铺的会员累积人数</span>--!}
                        {{--</div>--!}
                        {{--<div>--!}
                        {{--<span class="float-layer-left">成交客户数</span>--!}
                        {{--<span class="float-layer-right">筛选时间内，在店铺中付款成功的去重客户数，一个人在筛选时间范围内付款多次只记为一个</span>--!}
                        {{--</div>--!}
                        {{--</div>--!}
                        {{--</span>--!}
                    </div>
                    <div class="layui-col-md12" style="margin-top:-12px">
                        <div class="layui-row">
                            <select name="user-date" class="select_visit_trend" lay-verify="" onchange="dataAnalysis.choose_date(this)">
                                <option value="1">最近1天</option>
                                <option value="7">最近7天</option>
                                <option value="30">最近30天</option>
                            </select>
                            <span class="layui-card-item">${startDate!}</span> -
                            <span class="layui-card-item"> ${endDate!}</span>
                            <input type="hidden" name="start_date" value="${start_Date!}"/>
                            <input type="hidden" name="end_date" value="${end_Date!}"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-md8 customer-num uc_area1" style="margin-left:111px; width: 80%">
                <div class="layui-card">
                    <div class="layui-col-md4 border-right">
                        <div>
                            <span>访客数</span>
                            <span class="item-image" style="display:inline-block">
                                <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                <div class="float-layer">
                                    <div class="float-layer-i" style="left:81px; top:-11px"></div>
                                    <div>筛选时间内，店铺所有页面被访问的去重人数，一个人在筛选时间范围内访问多次只记为一个</div>
                                </div>
                            </span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="layui-green">
                            <span >较前一日</span>
                            <span class="trend">⬆</span>
                            <span class="percent">-</span>
                        </div>
                    </div>
                    <div class="layui-col-md4 border-right">
                        <div>
                            <span>累积用户数</span>
                            <span class="item-image" style="display:inline-block">
                                <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                <div class="float-layer">
                                    <div class="float-layer-i" style="left: 108px"></div>
                                    <div>截至到筛选时间的最后一天，店铺的会员累积人数</div>
                                </div>
                            </span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="layui-red">
                            <span>较前一日</span>
                            <span class="trend">⬇</span>
                            <span class="percent">-</span>
                        </div>
                    </div>
                    <div class="layui-col-md4" style="padding: 26px 0 29px 40px;">
                        <div>
                            <span>成交用户数</span>
                            <span class="item-image" style="display:inline-block">
                                <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                <div class="float-layer">
                                    <div class="float-layer-i" style="left: 108px"></div>
                                    <div>筛选时间内，在店铺中付款成功的去重客户数，一个人在筛选时间范围内付款多次只记为一个</div>
                                </div>
                            </span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="layui-green">
                            <span>较前一日</span>
                            <span class="trend">⬇</span>
                            <span class="percent">-</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-md12 demo-list">
                <div id="echarts-user-trend" style="height:400px; background: white"></div>
                <div style="line-height: 400px; text-align: center; display: none;">暂无相关数据</div>
            </div>
        </div>
        <div class="layui-two" style="overflow:hidden;">
            {{--用户活跃--!}
            <div class="layui-row layui-col-md12 customer-trend">
                <div class="layui-card">
                    <div class="layui-col-md12 tendency">
                        用户活跃
                    </div>
                    <div class="layui-col-md12" style="margin-top:-12px">
                        <div class="layui-row">
                            <select name="user-date" class="select_visit_trend" lay-verify="" onchange="dataAnalysis.choose_date(this,2)">
                                <option value="1">最近1天</option>
                                <option value="7">最近7天</option>
                                <option value="30">最近30天</option>
                            </select>
                            <span class="layui-card-item">${startDate!}</span> -
                            <span class="layui-card-item"> ${endDate!}</span>
                            <input type="hidden" name="start_date" value=""/>
                            <input type="hidden" name="end_date" value=""/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-md12 customer-num uc_area2" style="width: 80%;margin-left: 111px;">
                <div class="layui-card">
                    <div class="layui-col-md3 border-right padding-bottom">
                        <div>
                            <span>访问会员数</span>
                            <span class="item-image" style="display:inline-block">
                                <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                <div class="float-layer common-width-height">
                                    <div class="float-layer-i"></div>
                                    <div>
                                         <span class="float-layer-left">访问会员数</span>
                                         <span class="float-layer-right">筛选时间内，访问过店铺的用户数量,一人多次访问记为一人</span>
                                    </div>
                                    <div>
                                         <span class="float-layer-left">访问会员数占比</span>
                                         <span class="float-layer-right">筛选时间内，访问用户数 / 累积用户数</span>
                                    </div>
                                </div>
                            </span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="ratio-gray">
                            <span>占比</span>
                            <span class="percent">0%</span>
                        </div>
                    </div>
                    <div class="layui-col-md3 border-right padding-bottom">
                        <div>
                            <span>领券会员数</span>
                            <span class="item-image" style="display:inline-block">
                                <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                <div class="float-layer common-width-height">
                                    <div class="float-layer-i"></div>
                                    <div>
                                         <span class="float-layer-left">领券会员数</span>
                                         <span class="float-layer-right">筛选时间内，领取了优惠券的用户数，一人多次领券记为一人</span>
                                    </div>
                                    <div>
                                         <span class="float-layer-left">领券会员数占比</span>
                                         <span class="float-layer-right"> 筛选时间内，领券用户数/ 访问用户数</span>
                                    </div>
                                </div>
                            </span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="ratio-gray">
                            <span>占比</span>
                            <span class="percent">0%</span>
                        </div>
                    </div>
                    <div class="layui-col-md3 border-right padding-bottom">
                        <div>
                            <span>加购会员数</span>
                            <span class="item-image" style="display:inline-block">
                                <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                <div class="float-layer common-width-height">
                                    <div class="float-layer-i"></div>
                                    <div>
                                         <span class="float-layer-left">加购会员数</span>
                                         <span class="float-layer-right">筛选时间内，将商品添加购物车的用户数，一人多次添加购物车记为一人</span>
                                    </div>
                                    <div>
                                         <span class="float-layer-left">加购会员数占比</span>
                                         <span class="float-layer-right">筛选时间内，加购用户数/ 访问用户数</span>
                                    </div>
                                </div>
                            </span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="ratio-gray">
                            <span>占比</span>
                            <span class="percent">0%</span>
                        </div>
                    </div>
                    <div class="layui-col-md3" style="padding: 26px 0 33px 40px;">
                        <div>
                            <span>成交会员数</span>
                            <span class="item-image" style="display:inline-block">
                                <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                <div class="float-layer" style="width:500px; height:130px; top:49px; left: -141px;">
                                    <div class="float-layer-i" style="left: 249px; top: -11px"></div>
                                    <div>
                                         <span class="float-layer-left">成交会员数</span>
                                         <span class="float-layer-right">筛选时间内，付款成功的用户数，一人多次付款成功记为一人</span>
                                    </div>
                                    <div>
                                         <span class="float-layer-left">成交会员数占比</span>
                                         <span class="float-layer-right">筛选时间内，成交用户数/ 访问用户数</span>
                                    </div>
                                </div>
                            </span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="ratio-gray">
                            <span>占比</span>
                            <span class="percent">0%</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-md12 demo-list">
                <div id="echarts-act-trend" style="height:400px; background: white"></div>
                <div style="line-height: 400px; text-align: center; display: none;">暂无相关数据</div>
            </div>
        </div>
        <div class="layui-three">
            <div class="layui-row layui-col-md12 customer-trend">
                <div class="layui-card">
                    <div class="layui-col-md12 tendency">
                        <i class="left_bat"></i>
                        <span>会员统计</span>
                        <span class="item-image" style="display:inline-block">
                            <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                            <div class="float-layer" style="width:400px; height: 300px; top: 39px;left: -11px;">
                                <div class="float-layer-i"></div>
                                <div>
                                     <span class="float-layer-left">累积会员数</span>
                                     <span class="float-layer-right">截至到筛选时间的最后一天，店铺的会员累积人数</span>
                                </div>
                                <div>
                                     <span class="float-layer-left">新增会员数</span>
                                     <span class="float-layer-right">筛选时间内，通过领取会员卡，新成为会员的客户数量</span>
                                </div>
                                <div>
                                     <span class="float-layer-left">升级会员数</span>
                                     <span class="float-layer-right">筛选时间内，通过会员规则升级的会员数量，一人多次升级记为一人</span>
                                </div>
                                 <div>
                                     <span class="float-layer-left">储值会员数</span>
                                     <span class="float-layer-right">筛选时间内，进行储值的会员数量，一人多次储值记为一人</span>
                                </div>
                            </div>
                        </span>
                    </div>
                    <div class="layui-col-md12" style="margin-top: -12px">
                        <div class="layui-row">
                            <select name="user-date" class="select_visit_trend" lay-verify="" onchange="dataAnalysis.choose_date(this,3)">
                                <option value="1">最近1天</option>
                                <option value="7">最近7天</option>
                                <option value="30">最近30天</option>
                            </select>
                            <span class="layui-card-item">${startDate!}</span> -
                            <span class="layui-card-item"> ${endDate!}</span>
                            <input type="hidden" name="start_date" value=""/>
                            <input type="hidden" name="end_date" value=""/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-md12 customer-num uc_area3" style="width: 81%; margin-left:111px">
                <div class="layui-card">
                    <div class="layui-col-md3 border-right">
                        <div>
                            <span>累积会员数</span>
                        </div>
                        <div class="zero-weight">5</div>
                        <div class="layui-red">
                            <span>较前一日</span>
                            <span class="trend">⬆</span>
                            <span class="percent">0%</span>
                        </div>
                    </div>
                    <div class="layui-col-md3 border-right">
                        <div>
                            <span>新增会员数</span>
                            <span></span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="layui-green">
                            <span>较前一日</span>
                            <span class="trend">⬆</span>
                            <span class="percent">-</span>
                        </div>
                    </div>
                    <div class="layui-col-md3 border-right">
                        <div>
                            <span>升级会员数</span>
                            <span></span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="layui-green">
                            <span>较前一日</span>
                            <span class="trend">⬇</span>
                            <span class="percent">-</span>
                        </div>
                    </div>
                    <div class="layui-col-md3" style="padding: 26px 0 29px 40px;">
                        <div>
                            <span>储值会员数</span>
                            <span></span>
                        </div>
                        <div class="zero-weight">0</div>
                        <div class="layui-green">
                            <span>较前一日</span>
                            <span class="trend">⬇</span>
                            <span class="percent">-</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-four" style="height: 325px; background: white">
            <div class="layui-row layui-col-md12 customer-trend">
                <div class="layui-card">
                    <div class="layui-col-md12 tendency">
                        <i class="left_bat"></i>
                        <span>成交用户分析</span>
                    </div>
                    <div class="layui-col-md12" style="margin-top: -12px">
                        <div class="layui-row">
                            <select name="user-date" class="select_visit_trend" lay-verify="" onchange="dataAnalysis.choose_date(this,4)">
                                <option value="1">最近1天</option>
                                <option value="7">最近7天</option>
                                <option value="30">最近30天</option>
                            </select>
                            <span class="layui-card-item">${startDate!}</span> -
                            <span class="layui-card-item"> ${endDate!}</span>
                            <input type="hidden" name="start_date" value="" class="changeTrads1"/>
                            <input type="hidden" name="end_date" value="" class="changeTrads2"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-md12 uc_area4">
                <div class="layui-card">
                    <table class="layui-table layui-main-table">
                        <colgroup>
                            <col width="200">
                            <col width="200">
                            <col width="200">
                            <col width="200">
                            <col width="200">
                            <col>
                        </colgroup>
                        <thead>
                        <tr>
                            <th>客户类型</th>
                            <th>
                                <span>客户数</span>
                                <span class="item-image" style="display:inline-block">
                                        <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                        <div class="float-layer" style="width:400px; height: 215px; top: 39px;left: -11px;">
                                            <div class="float-layer-i"></div>
                                            <div>
                                                 <span class="float-layer-left">客户数</span>
                                                 <span class="float-layer-right">筛选时间内，付款成功的客户数，一人多次付款成功记为一人</span>
                                            </div>
                                            <div>
                                                 <span class="float-layer-left">新成交客户数</span>
                                                 <span class="float-layer-right">没有购买，在筛选时间内首次在店铺付款的客户数量</span>
                                            </div>
                                            <div>
                                                 <span class="float-layer-left">老成交客户数</span>
                                                 <span class="float-layer-right">购买过，在筛选时间内在店铺再次付款的客户数量</span>
                                            </div>
                                        </div>
                                    </span>
                            </th>
                            <th>
                                <span>客户数占比</span>
                                <span class="item-image" style="display:inline-block">
                                        <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                        <div class="float-layer" style="height: 115px; top: 39px;left: -11px;width: 500px;padding: 10px;">
                                            <div class="float-layer-i" style="left: 93px;"></div>
                                            <div>
                                                 <span class="float-layer-left">全部成交客户占比</span>
                                                 <span class="float-layer-right">筛选时间成交客户数 / 累积所有客户数</span>
                                            </div>
                                            <div>
                                                 <span class="float-layer-left">新成交客户占比</span>
                                                 <span class="float-layer-right">筛选时间新成交客户数 / 全部成交客户数</span>
                                            </div>
                                            <div>
                                                 <span class="float-layer-left">老成交客户占比</span>
                                                 <span class="float-layer-right">筛选时间老成交客户数 / 全部成交客户数</span>
                                            </div>
                                        </div>
                                    </span>
                            </th>
                            <th>
                                <span>客单价</span>
                                <span class="item-image" style="display:inline-block">
                                        <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                        <div class="float-layer" style="width:400px; height: 65px; top: 39px;left: -11px;">
                                            <div class="float-layer-i"></div>
                                            <div>
                                                 <span class="float-layer-left">客单价</span>
                                                 <span class="float-layer-right">筛选时间内，付款金额 / 付款人数</span>
                                            </div>
                                        </div>
                                    </span>
                            </th>
                            <th>付款金额</th>
                            <th>
                                <span>访问-付款转化率</span>
                                <span class="item-image" style="display:inline-block">
                                        <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                                        <div class="float-layer" style="width:700px; height: 122px; top: 39px;left: -525px;">
                                            <div class="float-layer-i" style="left: 640px;"></div>
                                            <div>
                                                 <span class="float-layer-left">全部成交客户-访问-付款转化率</span>
                                                 <span class="float-layer-right">筛选时间内，全部成交客户数/店铺访客数</span>
                                            </div>
                                            <div>
                                                 <span class="float-layer-left">新成交客户-访问-付款转化率</span>
                                                 <span class="float-layer-right">筛选时间内，新成交客户数/店铺访客数中无购买记录的访客数</span>
                                            </div>
                                            <div>
                                                 <span class="float-layer-left">老成交客户-访问-付款转化率</span>
                                                 <span class="float-layer-right">筛选时间内，老成交客户数/店铺访客数中购买过的访客数</span>
                                            </div>
                                        </div>
                                    </span>
                            </th>
                        </tr>
                        </thead>
                        <tbody class="four_body">
                        <tr>
                            <td>全部成交客户</td>
                            <td>
                                <span>-</span>
                                <span class="layui-red">⬆-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-red">⬆-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-red">⬆-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-red">⬆-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-red">⬆-</span>
                            </td>
                        </tr>
                        <tr>
                            <td>新成交客户</td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                        </tr>
                        <tr>
                            <td>老成交客户</td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                            <td>
                                <span>-</span>
                                <span class="layui-green">⬇-</span>
                            </td>
                        </tr>
                        <tr class="choose_four">
                            <td>趋势指标选择</td>
                            <input type="hidden" name="sex"  class="tradesValue" value="order_user_data"/>
                            <td colspan="5">
                                <span>
                                     <input type="radio" name="sex" checked value="order_user_data" title="客户数" onclick="dataAnalysis.changeTrads('order_user_data')">客户数
                                </span>
                                <span>
                                     <input type="radio" name="sex" value="per_money" title="客单价" onclick="dataAnalysis.changeTrads('per_money')">客单价
                                </span>
                                <span>
                                    <input type="radio" name="sex" value="paid_money" title="付款金额" onclick="dataAnalysis.changeTrads('paid_money')">付款金额
                                </span>
                                <span>
                                    <input type="radio" name="sex" value="tran_rate" title="访问付款转化率" onclick="dataAnalysis.changeTrads('tran_rate')">访问付款转化率
                                </span>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="layui-five" style="overflow: hidden;">
            <div class="layui-row layui-col-md12 demo-list" style="padding-top: 16px;">
                <div id="echarts-order-trend" style="height:400px; background: white"></div>
                <div style="line-height: 400px; text-align: center; display: none;">暂无相关数据</div>
            </div>
        </div>
        <div class="layui-six" style="overflow: hidden">
            <div class="layui-row layui-col-md12 customer-trend" style="position: relative">
                <div class="layui-card">
                    <div class="layui-col-md12 tendency">
                        <span>客户复购趋势</span>
                        <span class="item-image" style="display:inline-block">
                            <img src="/image/admin/analysis_tishi.png" alt="" class="zenticon-help-circle">
                            <div class="float-layer" style="width:400px; height: 300px; top: 39px;left: -11px;">
                                <div class="float-layer-i" style="left: 104px;"></div>
                                <div>
                                     <span class="float-layer-left">累积会员数</span>
                                     <span class="float-layer-right">截至到筛选时间的最后一天，店铺的会员累积人数</span>
                                </div>
                                <div>
                                     <span class="float-layer-left">新增会员数</span>
                                     <span class="float-layer-right">筛选时间内，通过领取会员卡，新成为会员的客户数量</span>
                                </div>
                                <div>
                                     <span class="float-layer-left">升级会员数</span>
                                     <span class="float-layer-right">筛选时间内，通过会员规则升级的会员数量，一人多次升级记为一人</span>
                                </div>
                                 <div>
                                     <span class="float-layer-left">储值会员数</span>
                                     <span class="float-layer-right">筛选时间内，进行储值的会员数量，一人多次储值记为一人</span>
                                </div>
                            </div>
                        </span>
                    </div>
                    <div class="layui-col-md3" style="margin-top: -12px">
                        <select name="user-date" class="select_visit_trend">
                            <option value="0">自然周</option>
                        </select>
                        {{--<input type="text" class="layui-input" id="test2">--!}
                    </div>
                    <div id="app" class="layui-col-md3" style="position: absolute;margin-top: 30px;margin-left: 200px">
                        <div class="container">
                            <div class="block">
                                <span class="demonstration">周</span>
                                <el-date-picker v-model="value3" type="week" format="yyyy年 第 WW 周" @change="dateChange" placeholder="选择周" :picker-options="pickerOptions1">
                                </el-date-picker>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-row layui-col-md12 demo-list" style="padding-top: 0;">
                <div id="echarts-rebuy-trend" style="height: 400px; background: white; padding-top: 20px;"></div>
                <div style="line-height: 400px; text-align: center; display: none;">暂无相关数据</div>
            </div>
        </div>
    </div>
</div>

<#include "/admin/footer.ftl">

<script language="JavaScript" src="/js/echarts.min.js"></script>
<script src="/js/layui/layui.js?v=1.0.7" type="text/javascript"></script>
{{--<script src="https://unpkg.com/vue/dist/vue.js"></script>--!}
<script src="/js/vue.js"></script>
<!-- import JavaScript -->
<script src="/js/element.js"></script>
{{--<script src="https://unpkg.com/element-ui/lib/index.js"></script>--!}

<script>
    //统计分析js层
    var dateNow = new Date();
    var Main = {
        data() {
            return {
                value3: dateNow,
                pickerOptions1: {
                    firstDayOfWeek: 1
                },
            };
        },
        methods: {
            dateChange(val) {
                console.log(val);
                this.value3 = val;
                month = (val.getMonth() + 1) < 10 ? '0' + (val.getMonth() + 1) : (val.getMonth() + 1);
                day = val.getDate() < 10 ? '0' + val.getDate() : val.getDate();
                endDate = val.getFullYear().toString() + month.toString() + day.toString();
                dataAnalysis.getUserTrendPlot(7,5,'',endDate,'');
            }
        }

    };
    var Ctor = Vue.extend(Main)
    new Ctor().$mount('#app')
    var dataAnalysis = {
        choose_date: function (obj, action = 1) {
            var val = $(obj).val();
            // var day = 0;
            var startDate1, endDate1, startDate2, endDate2, month1, day1, month2, day2;
            var curDate = new Date();
            var date = new Date();
            date.setDate(date.getDate() -val);

            month1 = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
            month2 = (curDate.getMonth() + 1) < 10 ? '0' + (curDate.getMonth() + 1) : (curDate.getMonth() + 1);

            day1 = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            day2 = curDate.getDate() < 10 ? '0' + curDate.getDate() : curDate.getDate();

            startDate1 = date.getFullYear() + "年" + month1 + "月" + day1 + "日";
            endDate1 = curDate.getFullYear() + "年" + month2 + "月" + day2 + "日";

            startDate2 = date.getFullYear().toString() + month1.toString() + day1.toString();
            endDate2 = curDate.getFullYear().toString() + month2.toString() + day2.toString();

            $(obj).parent().find('input[name="start_date"]').val(startDate2);
            $(obj).parent().find('input[name="end_date"]').val(endDate2);

            $(obj).parent().find('span.layui-card-item').eq(0).html(startDate1);
            $(obj).parent().find('span.layui-card-item').eq(1).html(endDate1);

            if(action !=3){
                // console.log("1"+val);
                if(action == 4){
                    var tradesType1 = $('.tradesValue').val();
                    dataAnalysis.getUserTrendPlot(val,action,startDate2,endDate2,tradesType1);
                }else{
                    dataAnalysis.getUserTrendPlot(val,action,startDate2,endDate2,'');
                }


            }
            if (action != 5){
                dataAnalysis.getCompareData(val,action);

            }

            // action == 1 ? dataAnalysis.getTrendData(startDate1, endDate2) : dataAnalysis.getVisitPage(startDate1, endDate2);
        },
        getUserTrendPlot: function (type,action,startDate,endDate,tradesType='') {
            util.ajax_json('/admin/survey/user/summary', function (response) {
                console.log(response);
                if (response.error == 0) {
                    var echarts_area = '';
                    if(response.content.action == 1){
                        echarts_area = 'echarts-user-trend';
                    }else if(response.content.action == 2){
                        echarts_area = 'echarts-act-trend';
                    }else if(response.content.action == 4){
                        echarts_area = 'echarts-order-trend';
                    }else if(response.content.action == 5){
                        echarts_area = 'echarts-rebuy-trend';
                    }
                    if (response.content.userPlotDates.length < 1) {

                        console.log(response.content.userPlotDates);
                        console.log(12);
                        $('#'+echarts_area).next().show();
                        $('#'+echarts_area).hide();
                        return;
                    } else {
                        $('#'+echarts_area).next().hide();
                        $('#'+echarts_area).show();
                        var userSummaryData = response.content.userPlotList;
                        var userPlotDates = response.content.userPlotDates;
                        var userSummaryDataList = [];
                        var userPlotlegends = response.content.userPlotlegends;
                        console.log(userSummaryData);
                        $.each(userSummaryData,function (i,item) {
                            var dataTemp = {
                                name: userSummaryData[i]['name'],
                                type:'line',
                                data:userSummaryData[i]['data'],
                                itemStyle : {
                                    normal : {
                                        color: '#E26EB4',
                                        lineStyle:{
                                            color:'#E26EB4'
                                        }
                                    }
                                },
                                itemStyle: {
                                    normal: {
                                        color: userSummaryData[i]['color'],
                                        lineStyle: {
                                            width: 2,
                                            type: 'solid',  //'dotted'虚线 'solid'实线
                                            color: userSummaryData[i]['color']
                                        }
                                    }
                                }
                            };
                            userSummaryDataList.push(dataTemp);
                        });
                        dataAnalysis.initChart(action,userSummaryDataList,userPlotDates,userPlotlegends,echarts_area);
                    }
                }
            }, { type:type, action: action, startDate:startDate, endDate:endDate, tradesType:tradesType})
        },
        initChart: function (action,userSummaryDataList,userPlotDates,userPlotlegends,echarts_area) {

            var echartUserTrend = echarts.init(document.getElementById(echarts_area));

            // 指定图表的配置项和数据
            var optionUserTrend = {
                /*title: {
                    text: 'btc'
                },*/
                legend: {
                    data:userPlotlegends
                },
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                /*toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },*/
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: userPlotDates
                },
                yAxis: {
                    type: 'value'
                },
                series: userSummaryDataList
            };
            echartUserTrend.setOption(optionUserTrend);
        },
        getCompareData: function (type = 1, action = 1) {
            util.ajax_json('/admin/ajax/user/summary/compare', function (response) {
                if (response.error == 0) {
                    var data = response.content.userCompareList;
                    var type = response.content.type;
                    var action = response.content.action;
                    console.log(data);
                    console.log(type);
                    var uc_area = 'uc_area'+action;
                    var j=0;
                    if(action == 4){
                        var html ='';
                        for(var i in data){
                            if(j == 0){
                                var htmltr = '<tr><td>全部成交客户</td>';
                            }else if(j==1){
                                var htmltr = '<tr><td>新成交客户</td>';
                            }else if(j==2){
                                var htmltr = '<tr><td>老成交客户</td>';
                            }
                            var std = '';
                            for(var k in data[i]){
                                std = std + '<td><span>'+data[i][k].dim_data+'</span>';
                                if(data[i][k].dim_per=='-' || !data[i][k].dim_per){
                                    std = std+'<span class="gray">--</span></td>'
                                }else{
                                    var per = data[i][k].dim_per.split("%");
                                    console.log(per);
                                    if(per != undefined && per[0] >= 0){
                                        std = std + '<span class="layui-red">'+'↑'+Math.abs(per[0])+'%'+'</span></td>';
                                    }else if(per != undefined && per[0] < 0){
                                        std = std + '<span class="layui-green">'+'↓'+Math.abs(per[0])+'%'+'</span></td>';
                                    }
                                }
                            }
                            htmltr = htmltr + std + '</tr>'
                            html = html + htmltr;
                            j++;
                        }
                        var el = $(".choose_four").clone();
                        $(".four_body").html(html);
                        el.appendTo($(".four_body"));
                    }else if(action == 2){
                        for (var i in data){
                            $('.'+uc_area).find('.zero-weight').eq(j).html(data[i].dim_data);

                            $('.'+uc_area).find('.percent').eq(j).html(data[i].dim_per);
                            j++;
                        }
                    }else{
                        for (var i in data){
                            var per = '';
                            $('.'+uc_area).find('.zero-weight').eq(j).html(data[i].dim_data);
                            if(data[i].dim_per != '-' && data[i].dim_per){
                                per = data[i].dim_per.split("%");
                                $('.'+uc_area).find('.percent').eq(j).html(Math.abs(per[0])+"%");
                            }else{
                                $('.'+uc_area).find('.percent').eq(j).html(data[i].dim_per);
                                $('.'+uc_area).find('.percent').eq(j).parent().attr('class','gray');
                                $('.'+uc_area).find('.percent').eq(j).parent().find('.trend').html('-');
                            }

                            if(per != undefined && per[0] >= 0){
                                $('.'+uc_area).find('.percent').eq(j).parent().attr('class','layui-red');
                                $('.'+uc_area).find('.percent').eq(j).parent().find('.trend').html('↑');
                            }else if(per != undefined && per[0] < 0){
                                $('.'+uc_area).find('.percent').eq(j).parent().attr('class','layui-green');
                                $('.'+uc_area).find('.percent').eq(j).parent().find('.trend').html('↓');
                            }
                            if(type == 7){
                                $('.'+uc_area).find('.percent').eq(j).prev().prev().html('较前一周');
                            }else if(type == 30){
                                $('.'+uc_area).find('.percent').eq(j).prev().prev().html('较前一月');
                            }else if(type == 1){
                                $('.'+uc_area).find('.percent').eq(j).prev().prev().html('较前一日');
                            }

                            j++;
                        }
                    }

                }
            }, { type : type, action : action})
        },
        changeTrads:function (tradesType = '') {
            var startDate = $('.changeTrads1').val();
            var endDate = $('.changeTrads2').val();
            $('.tradesValue').attr('value',tradesType);
            dataAnalysis.getUserTrendPlot(1,4,startDate,endDate,tradesType);
        }
    };
    dataAnalysis.getUserTrendPlot(1,1);
    dataAnalysis.getUserTrendPlot(1,2);

    dataAnalysis.getUserTrendPlot(1,4,'','','order_user_data');
    dataAnalysis.getUserTrendPlot(7,5);
    dataAnalysis.getCompareData(1,1);
    dataAnalysis.getCompareData(1,2);
    dataAnalysis.getCompareData(1,3);
    dataAnalysis.getCompareData(1,4);
    // var myecharts = echarts.init(document.getElementById('echarts-main'));
    // var options = {
    //     tooltip : {
    //         trigger: 'axis'
    //     },
    //     legend: {
    //         data:['新客户成交数','老客户成交数']
    //     },
    //     xAxis : [
    //         {
    //             type : 'category',
    //             boundaryGap : false,
    //             data : ['2018-08-25','2018-08-26','2018-08-27','2018-08-28','2018-08-29','2018-08-30','2018-08-31','2018-09-01']
    //         }
    //     ],
    //     yAxis : [
    //         {
    //             type : 'value'
    //         }
    //     ],
    //     series : [
    //         {
    //             name:'新客户成交数',
    //             type:'line',
    //             itemStyle : {
    //                 normal : {
    //                     color: '#E26EB4',
    //                     lineStyle:{
    //                         color:'#E26EB4'
    //                     }
    //                 }
    //             },
    //             data:[220, 182, 191, 234, 290, 330, 310]
    //         },
    //         {
    //             name:'老客户成交数',
    //             type:'line',
    //             itemStyle : {
    //                 normal : {
    //                     color: '#FF9F7F',
    //                     lineStyle:{
    //                         color:'#FF9F7F'
    //                     }
    //                 }
    //             },
    //             data:[120, 132, 101, 134, 90, 230, 210]
    //         }
    //     ]
    // };
    //
    // myecharts.setOption(options);
    //
    // var myuser = echarts.init(document.getElementById('user-main'));
    // myuser.setOption(options);
    //
    // var optionold = echarts.init(document.getElementById('echarts-main-new'));
    // optionold.setOption(options);
    //
    // var echartsold = echarts.init(document.getElementById('echarts-main-old'));
    // echartsold.setOption(options);

    $('.layui-card .item-image').hover(function () {
        $(this).find('.float-layer').show();
    },function () {
        $(this).find('.float-layer').hide();
    });

    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test2' //指定元素
        });
    });


</script>