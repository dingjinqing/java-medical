<#include "/admin/header.ftl">
<script language="JavaScript" src="/js/echarts.min.js"></script>
<style>
    #analysis {
        min-width: 1090px;
        margin-top: 10px;
    }
    #analysis .layui-fluid {

    }
    #analysis .demo-list .layui-card{
        margin: 20px 0;
        border-right: 1px solid #e7e7eb;
        text-align: center;
        -webkit-box-shadow: none;
        -moz-box-shadow: none ;
        box-shadow: none;
    }
    #analysis .layui-card .layui-card-header {
        font-size: 16px;
        border: none;
        color: #333;
    }
    #analysis .layui-card .layui-card-header .item-image {
        float: right;
        margin-top: 12px;
    }
    .float-layer {
        float: right;
        width: 420px;
        padding: 15px;
        position: absolute;
        right: 8px;
        z-index: 9999;
        border: 1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150,150,150,0.3);
        border-radius: 5px;
        background-color: #fff;
        line-height: 30px;
        display: none;
    }
    .float-layer .float-layer-left {
        width: 25%;
        float: left;
        display: inline-block;
        color: #999;
    }
    .float-layer .float-layer-right {
        width: 70%;
        display: inline-block;
        color: #353535;
    }
    .l-float .float-layer {
        float: right;
        width: 420px;
        padding: 15px;
        position: absolute;
        left: 8px;
        z-index: 2;
        border: 1px solid #fff;
        word-wrap: break-word;
        word-break: break-all;
        box-shadow: 0 0 20px rgba(150,150,150,0.3);
        border-radius: 5px;
        background-color: #fff;
        line-height: 30px;
        display: none;
    }
    .l-float .float-layer .float-layer-left {
        width: 25%;
        float: left;
        display: inline-block;
        color: #999;
    }
    .l-float .float-layer .float-layer-right {
        width: 70%;
        display: inline-block;
        color: #353535;
    }
    .l-float .float-layer .float-layer-i {
        position: absolute;
        left: 88px;
        top: -1px;
        margin-left: -12px;
        margin-top: -12px;
        display: inline-block;
        width: 0;
        height: 0;
        border-width: 12px;
        border-style: dashed;
        border-color: transparent;
        border-top-width: 0;
        border-bottom-color: #fff;
        border-bottom-style: solid;
    }
    .layui-card .float-layer-i {
        position: absolute;
        right: 10px;
        top: -1px;
        margin-left: -12px;
        margin-top: -12px;
        display: inline-block;
        width: 0;
        height: 0;
        border-width: 12px;
        border-style: dashed;
        border-color: transparent;
        border-top-width: 0;
        border-bottom-color: #fff;
        border-bottom-style: solid;
    }
    #analysis .layui-card .item-color-red {
        color: #e15f63;
    }
    #analysis .layui-card .item-color-blue {
        color: #5a8bff;
    }
    #analysis .layui-card-item {
        color: #999;
        font-size: 14px;
    }
    #analysis .layui-card-num {
        padding-top: 5px;
        font-size: 24px;
    }
    #analysis .layui-card-header .select_visit_trend {
        width: 160px;
        height: 30px;
        border-radius: 3px;
        border: 1px solid #ccc;
        color: #333;
        font-size: 14px;
        margin-right: 10px;
    }
    #analysis .layui-card-header .select_visit_trend.middle {
        width: 235px;
        padding-left: 6px;
    }
    #analysis .layui-table tr th:not(:first-child), #analysis .layui-table tr td:not(:first-child){
        text-align: center;
    }

    .data_overview .data_pv_info li{
        float: left;
        box-sizing: border-box;
        border:1px solid #ccc;
        padding:20px 30px 20px 80px;
        background-color:#fff;
        position: relative;
    }
    .data_overview .data_pv_info li:nth-child(2){
        margin-left: -1px;
    }
    .data_overview .data_pv_info li:nth-child(3){
        margin-top: -1px;
    }
    .data_overview .data_pv_info li:nth-child(4){
        margin:-1px 0 0 -1px;
    }
    .data_overview .data_pv_info li::after{
        content: " ";
        position: absolute;
        display: none;
        right: 0;
        bottom: 0;
        width: 16px;
        height: 16px;
        background-size: 16px 16px;
        background-image: url("/image/admin/basic_choice.png")
    }
    .data_overview .data_pv_info li.active{
        border-color:#0a87ff;
        z-index: 1;
    }
    .data_overview .data_pv_info li.active::after{
        display: block;
    }
    .data_overview .data_pv_info li div{
        margin: 15px;
    }
    .data_overview .data_pv_info li div:nth-child(1){
        font-size: 14px;
        color: black;
        margin-bottom: 5px;
    }
    .data_overview .data_pv_info li div:nth-child(2){
        font-size: 20px;
        margin:0 30px 0 15px;
    }
    .data_overview .data_pv_info li div:nth-child(3){
        margin-top: 5px;
        font-size: 12px;
        color: #9c9993;
    }
    .layui-card-header input[type="text"]{
        width: 150px;
        height: 30px;
        border-radius: 2px;
        border: 1px solid #9c9993;
        padding-left: 10px;
        font-size: 14px;
    }
    .core_index .layui-card-header div{
        margin-right: 50px;
    }
    .core_index ul{
        padding: 50px 60px 30px;
    }
    .core_index ul li{
        float: left;
        box-sizing: border-box;
        width: 25%;
        padding: 26px 0 26px 40px;
        position: relative;
        border:1px solid #eee;
        margin:0 0 0 -1px;
        z-index: 0;
    }
    .core_index ul li:hover{
        cursor: pointer;
    }
    .core_index ul li div:nth-child(3){
        color: #9c9993;
    }
    .core_index ul li div:nth-child(4){
        color: #9c9993;
    }
    .core_index ul li div{
        margin-bottom: 5px;
    }
    .core_index ul li::after{
        content: " ";
        position: absolute;
        display: none;
        right: 0;
        bottom: 0;
        width: 16px;
        height: 16px;
        background-size: 16px 16px;
        background-image: url("/image/admin/basic_choice.png")
    }
    .core_index ul li.active{
        border:1px solid #0a87ff;
        z-index: 1;
    }
    .core_index ul li.active::after{
        display: block;
    }
    .core_index ul li i{
        font-style: normal;
    }
    .core_index ul li i.top {
        color: #ff0000;
    }
    .core_index ul li i.down{
        color: #2fae44;
    }
    .title{
        margin-top:-9px;
    }
    .clearfix:after{
        content:".";
        display:block;
        height:0;
        clear:both;
        visibility:hidden
    }
    .clearfix{
        overflow:hidden;
        _zoom:1;
    }
</style>

<div id="analysis">
    <div class="title">
        <div>
            <span><a href="/admin/survey/overview?top_index=0">概况</a> / </span>
            <span style="color: #666;">概况统计</span>
        </div>
    </div>
    <div class="layui-fluid" style="margin-top:10px">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header">
                        <span>昨日概况</span>
                        <span class="item-image">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">打开次数</span>
                            <span class="float-layer-right">昨日打开小程序总次数，用户从打开小程序到主动关闭小程序或超时退出计为一次；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问次数</span>
                            <span class="float-layer-right">计为多次访问；昨日访问小程序内所有页面总次数，多个页面之间跳转、同一页面的重复访问计为多次访问；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问人数</span>
                            <span class="float-layer-right">昨日访问小程序内所有页面的总用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">新访问用户数</span>
                            <span class="float-layer-right">首次访问小程序页面的用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享次数</span>
                            <span class="float-layer-right">昨日分享小程序的总次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享人数</span>
                            <span class="float-layer-right">昨日分享小程序的总人数，同一用户多次分享不重复计。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list" style="padding-bottom: 20px;">
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <!-- 填充内容 -->
                            <div class="layui-card">
                                <div class="layui-card-item">打开次数</div>
                                <div class="layui-card-num">${basicData->session_cnt ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['session_cnt'][0] === '@@')
                                        -
                                        <#else>
                                            <span <#if  ($percent['session_cnt'][0] < 0)
                                                  class="item-color-red"
                                                  <#else>
                                                  class="item-color-blue"
                                                    </#if>
                                            >
                                                ${percent['session_cnt'][0]!}%
                                            </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['session_cnt'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['session_cnt'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['session_cnt'][1]!}%
                                    </span>
                                    </#if>

                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['session_cnt'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['session_cnt'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                            ${percent['session_cnt'][2]!}%
                                        </span>
                                    </#if>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <div class="layui-card">
                                <div class="layui-card-item">访问次数 / 人数 </div>
                                <div class="layui-card-num">${basicData->visit_pv ?? 0!} / ${basicData->visit_uv ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['visit_pv'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_pv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_pv'][0]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['visit_uv'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv'][0]!}%
                                        </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['visit_pv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_pv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_pv'][1]!}%
                                        </span>
                                    </#if>
                                    /
                                    <#if  ($percent['visit_uv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv'][1]!}%
                                        </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['visit_pv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_pv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                    ${percent['visit_pv'][2]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['visit_uv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv'][2]!}%
                                        </span>
                                    </#if>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <div class="layui-card">
                                <div class="layui-card-item">新访问用户数</div>
                                <div class="layui-card-num">${basicData->visit_uv_new ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['visit_uv_new'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv_new'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv_new'][0]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['visit_uv_new'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv_new'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv_new'][1]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['visit_uv_new'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['visit_uv_new'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['visit_uv_new'][2]!}%
                                    </span>
                                    </#if>

                                </div>
                            </div>
                        </div>
                        <div class="layui-col-sm4 layui-col-md3 layui-col-lg3">
                            <div class="layui-card">
                                <div class="layui-card-item">分享次数 / 人数</div>
                                <div class="layui-card-num">${basicShare->share_pv ?? 0!} / ${basicShare->share_uv ?? 0!}</div>
                                <div>
                                    <span class="layui-card-item">日</span>
                                    <#if  ($percent['share_pv'][0] === '@@')
                                        -
                                        <#else>
                                        <span <#if  ($percent['share_pv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                            ${percent['share_pv'][0]!}%
                                        </span>
                                    </#if>
                                     /
                                    <#if  ($percent['share_uv'][0] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_uv'][0] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_uv'][0]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">周</span>
                                    <#if  ($percent['share_pv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_pv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_pv'][1]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['share_uv'][1] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_uv'][1] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_uv'][1]!}%
                                    </span>
                                    </#if>
                                </div>
                                <div>
                                    <span class="layui-card-item">月</span>
                                    <#if  ($percent['share_pv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_pv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_pv'][2]!}%
                                    </span>
                                    </#if>
                                    /
                                    <#if  ($percent['share_uv'][2] === '@@')
                                        -
                                    <#else>
                                        <span <#if  ($percent['share_uv'][2] < 0)
                                              class="item-color-red"
                                              <#else>
                                              class="item-color-blue"
                                                </#if>>
                                        ${percent['share_uv'][2]!}%
                                    </span>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" style="height:47px;line-height:60px;">
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.triggerRequest(this, 1)" id="trigger1">
                            <option value="1">累计访问用户数</option>
                            <option value="2">打开次数</option>
                            <option value="3">访问次数</option>
                            <option value="4">访问人数</option>
                            <option value="5">新访问用户数</option>
                            <option value="8">分享次数</option>
                            <option value="9">分享人数</option>
                            <option value="6">人均停留时长</option>
                            <option value="7">次均停留时长</option>
                        </select>
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.choose_date(this)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        <input type="text" id="layui-date1" class="select_visit_trend middle time" placeholder="" lay-key="1" style="display: none;">
                        <span class="layui-card-item" >${start_date!}</span> -
                        <span class="layui-card-item">${end_date!}</span>
                        <input type="hidden" name="start_date" value=""/>
                        <input type="hidden" name="end_date" value=""/>
                        <span class="item-image" style="margin-top:21px;">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">累计访问用户数</span>
                            <span class="float-layer-right">历史累计访问小程序的用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">新访问用户数</span>
                            <span class="float-layer-right">首次访问小程序页面的用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">打开次数</span>
                            <span class="float-layer-right">打开小程序总次数，用户从打开小程序到主动关闭小程序或超时退出计为一次；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问次数</span>
                            <span class="float-layer-right">访问小程序内所有页面总次数，多个页面之间跳转、同一页面的重复访问计为多次访问；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">访问人数</span>
                            <span class="float-layer-right">访问小程序内所有页面的总用户数，同一用户多次访问不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享次数</span>
                            <span class="float-layer-right">分享小程序的总次数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">分享人数</span>
                            <span class="float-layer-right">分享小程序的总人数，同一用户多次分享不重复计；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">人均停留时长</span>
                            <span class="float-layer-right">平均每个用户停留在小程序页面的总时长，即小程序停留总时长/访问人数；</span>
                        </div>
                        <div>
                            <span class="float-layer-left">次均停留时长</span>
                            <span class="float-layer-right">平均每次打开小程序停留在小程序页面的总时长，即小程序停留总时长/打开次数。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list">
                        <div id="echart-visit-trend" style="width: 100%; height: 350px;"></div>
                        <div style="line-height: 350px; text-align: center; display: none;">暂无相关数据</div>
                    </div>
                </div>
            </div>
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header" style="height:47px;line-height:60px;">
                        <select name="visit_trend" class="select_visit_trend" onchange="dataAnalysis.choose_date(this, 2)">
                            <option value="1">最近7天</option>
                            <option value="2">最近30天</option>
                            <option value="3">自定义</option>
                        </select>
                        <input type="text" id="layui-date2" class="select_visit_trend middle time" placeholder="" lay-key="7" style="display: none;">
                        <span class="layui-card-item" >${start_date!}</span> -
                        <span class="layui-card-item">${end_date!}</span>
                        <input type="hidden" name="start_date"/>
                        <input type="hidden" name="end_date"/>
                        <span class="item-image" style="margin-top:21px;">
                            <img src="http://${image_domain!}/image/admin/analysis_tishi.png" />
                        </span>
                    </div>
                    <div class="float-layer">
                        <div class="float-layer-i"></div>
                        <div>
                            <span class="float-layer-left">受访页</span>
                            <span class="float-layer-right">用户进入小程序访问的所有页面，例如用户从页面A进入小程序，跳转到页面B，A,B均为受访页。</span>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space12 demo-list page_data" style="padding:0 15px 5px 15px">
                        <table class="layui-table" style="width: 99%; margin-top: 20px; margin-left: 5px;">
                            <thead>
                                <tr>
                                    <th>页面路径</th>
                                    <th>页面名称</th>
                                    <th>访问次数</th>
                                    <th>占比</th>
                                </tr>
                            </thead>
                            <tbody id="visit-page" style="color:#333;">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // param define
    var visitTrendDates = [];
    var visitTrendData = [];
    var visitTrendAction = '累计访问用户数';

    //统计分析js层
    var dataAnalysis = {
        triggerRequest: function(obj, action = 1) {
            visitTrendAction = $(obj).find(':selected').text();
            var startDate = $(obj).parent().find('input[name="start_date"]').val();
            var endDate = $(obj).parent().find('input[name="end_date"]').val();
            action == 1 ? dataAnalysis.getTrendData(startDate, endDate) : dataAnalysis.getVisitPage(startDate, endDate);
        },
        choose_date: function (obj, action = 1) {
            var val = $(obj).val();
            var day = 0;
            var startDate, endDate, startDate1, endDate2, month1, day1, month2, day2;
            var eleName = 'layui-date'+action;

            if (val == 3) {
                $('#'+eleName).show();
                return;
            }

            $('#'+eleName).hide();

            if (val == 1) {
                day = 7;
            } else if(val == 2) {
                day = 30;
            }

            var curDate = new Date();
            var date = new Date();
            date.setDate(date.getDate() - day);

            month1 = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
            month2 = (curDate.getMonth() + 1) < 10 ? '0' + (curDate.getMonth() + 1) : (curDate.getMonth() + 1);

            day1 = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            day2 = curDate.getDate() < 10 ? '0' + curDate.getDate() : curDate.getDate();

            startDate = date.getFullYear() + "年" + month1 + "月" + day1 + "日";
            endDate = curDate.getFullYear() + "年" + month2 + "月" + day2 + "日";

            startDate1 = date.getFullYear().toString() + month1.toString() + day1.toString();
            endDate2 = curDate.getFullYear().toString() + month2.toString() + day2.toString();

            $(obj).parent().find('input[name="start_date"]').val(startDate1);
            $(obj).parent().find('input[name="end_date"]').val(endDate2);

            $(obj).parent().find('span.layui-card-item').eq(0).html(startDate);
            $(obj).parent().find('span.layui-card-item').eq(1).html(endDate);

            action == 1 ? dataAnalysis.getTrendData(startDate1, endDate2) : dataAnalysis.getVisitPage(startDate1, endDate2);
        },
        getTrendData: function (startDate = '', endDate = '') {
            util.ajax_json('/admin/ajax/survey/analysis/basic/visit', function (response) {
                if (response.error == 0) {
                    if (response.content.date.length < 1) {
                        $('#echart-visit-trend').next().show();
                        $('#echart-visit-trend').hide();
                        return;
                    } else {
                        $('#echart-visit-trend').next().hide();
                        $('#echart-visit-trend').show();
                    }
                    visitTrendDates = response.content.date;
                    visitTrendData = response.content.list;
                    dataAnalysis.initChartTread();
                }
            }, { start_date : startDate, end_date : endDate, action: $('#trigger1').val()})
        },
        initChartTread: function () {
            var echartVisitTrend = echarts.init(document.getElementById('echart-visit-trend'));

            // 指定图表的配置项和数据
            var optionVisitTrend = {
                /*title: {
                    text: 'btc'
                },*/
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
                    data: visitTrendDates
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: visitTrendAction,
                        type:'line',
                        data:visitTrendData,
                        itemStyle: {
                            normal: {
                                lineStyle: {
                                    width: 2,
                                    type: 'solid',  //'dotted'虚线 'solid'实线
                                    color: '#08B74F'
                                }
                            }
                        }
                    }
                ]
            };
            echartVisitTrend.setOption(optionVisitTrend);
        },
        getVisitPage: function (startDate = '', endDate = '') {
            util.ajax_json('/admin/ajax/analysis/basic/visitPage', function (response) {
                if (response.error == 0) {
                    var data = response.content.list;
                    var sum = response.content.sum ? response.content.sum : 0;

                    var html = '';
                    var b = '';
                    for (var i in data) {
                        var percent = parseFloat(data[i].page_visit_pv) / sum * 100;
                        html += `<tr>`;
                        html += `<td>`+ parseInt(parseInt(i)+1) +`.`+data[i].page_path+`</td>`;
                        html += `<td>`+data[i].page_name+`</td>`;
                        html += `<td>`+data[i].page_visit_pv+`</td>`;
                        html += `<td>`+percent.toFixed(2)+`%</td>`;
                        html += '</tr>';
                    }
                    if(html == ''){
                      b += `<div class="no_data_style" style="width: 99%;border: 1px solid #eee;height: 100px;margin-top: 10px;margin-left: 5px">
                                <div style="width: 30px;height: 33px; margin: 25px auto auto auto" >
                                   <img src="/image/admin/no_data.png" alt="">
                                </div>
                                  <p style="font-size: 12px;color:#666;margin: 8px auto 18px auto;width: 100px;height: 30px;line-height: 30px;text-align: center">暂无相关数据</p>
                           </div>`;
                        if($('.page_data').find('.no_data_style').length <= 0){
                            $('.page_data').append(b);
                        }
                    }
                    $('#visit-page').html(html);
                }
            }, { start_date : startDate, end_date : endDate})
        }
    };

    dataAnalysis.getTrendData();
    dataAnalysis.getVisitPage();

    // layui 日期
    /*var ele;
    $('#layui-date1, #layui-date2').focus(function () {
        ele = $(this);
    })*/
    layui.use('laydate', function(){
        $('.time').each(function(){
            var ele = $(this);
            var laydate = layui.laydate;
            laydate.render({
                elem: this,
                //position: 'fixed',
                format: 'yyyy年MM月dd日',
                range: true,
                done: function(value, date1, date2){
                    if (value == '') return;
                    //监听日期被切换
                    date1.month = date1.month < 10 ? '0' + date1.month : date1.month;
                    date1.date = date1.date < 10 ? '0' + date1.date : date1.date;
                    date2.month = date2.month < 10 ? '0' + date2.month : date2.month;
                    date2.date = date2.date < 10 ? '0' + date2.date : date2.date;

                    ele.parent().find('span').eq(0).html(value.split('-')[0]);
                    ele.parent().find('span').eq(1).html(value.split('-')[1]);

                    var startDate = date1.year.toString() + date1.month.toString() + date1.date.toString();
                    var endDate = date2.year.toString() + date2.month.toString() + date2.date.toString();

                    ele.parent().find('input[name="start_date"]').val(startDate);
                    ele.parent().find('input[name="end_date"]').val(endDate);

                    ele.attr('id').substr(-1, 1) == 1 ? dataAnalysis.getTrendData(startDate, endDate) : dataAnalysis.getVisitPage(startDate, endDate);
                }
            });
        })
    });
    /*layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#layui-date1, #layui-date2',
            format: 'yyyy年MM月dd日',
            range: true,
            done: function(value, date1, date2){
                console.log(value);
                //监听日期被切换
                date1.month = date1.month < 10 ? '0' + date1.month : date1.month;
                date1.date = date1.date < 10 ? '0' + date1.date : date1.date;
                date2.month = date2.month < 10 ? '0' + date2.month : date2.month;
                date2.date = date2.day < 10 ? '0' + date2.date : date2.date;

                ele.parent().find('span').eq(0).html(value.split('-')[0]);
                ele.parent().find('span').eq(1).html(value.split('-')[1]);

                var startDate = date1.year.toString() + date1.month.toString() + date1.date.toString();
                var endDate = date2.year.toString() + date2.month.toString() + date2.date.toString();

                ele.parent().find('input[name="start_date"]').val(startDate);
                ele.parent().find('input[name="end_date"]').val(endDate);

                ele.attr('id').substr(-1, 1) == 1 ? dataAnalysis.getTrendData(startDate, endDate) : dataAnalysis.getVisitPage(startDate, endDate);
            }
        });
    });*/
    $('.layui-card .item-image, .layui-card .float-layer').hover(function () {
        $(this).parent().parent().find('.float-layer').show();
    },function () {
        $(this).parent().parent().find('.float-layer').hide();
    })
    $('.layui-card-header > i').hover(function(){
        $(this).parent().parent().find('.float-layer').show();
    },function(){
        $(this).parent().parent().find('.float-layer').hide();
    }
    )

    $('.core_index ul li').click(function(){
        $(this).toggleClass('active')
    })
    // $('.data_pv_info li').click(function(){
    //     $('.data_pv_info li').removeClass('active')
    //     $(this).addClass('active')
    // })




    // //实例化echarts设置对象
    // new echartsdata();
</script>
<#include "/admin/footer.ftl">
<script type="text/javascript">
    getPowerInfo('main_config','basic_yesterday','sub_0','概况统计',0);
</script>