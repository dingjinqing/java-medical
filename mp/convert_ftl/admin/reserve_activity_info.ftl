<html>
<head>
    <link href="/css/admin/reserve_info.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/common.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <link href="/css/admin/goods_list.css?v=1.1.2" rel="stylesheet" type="text/css"/>
    <meta name="csrf-token" content="{{ csrf_token()!}">
    <style>
        textarea {
            font-family: "微软雅黑";
        }
        .choose_member{
            position: absolute;
            top:10px;left: 165px
        }
        .edit_member{
            position: absolute;
            top:10px;left: 385px
        }
    </style>
</head>
<body style="background: #fff">
<form action="/admin/ajax/reserve/addservice" method="post" id="form3">
    {{ csrf_field()!}
    <input type="text" hidden name="store_id" value="${store_id!}">
    <input type="text" hidden name="user_id">
    <div id="add_activity">
        <div class="reserve-name">
            <label for="reserve_names"><span>*</span>预约人：</label>
            <input type="text" placeholder="请填写预约人姓名" id="reserve_names" name="reserve_names" disabled hidden>
            <div class="btn_add_members choose_member" id="add_members"><a href="##">选择会员</a></div>
        </div>
        <div class="mobiles">
            <label for="contact_mobile"><span>*</span>手机号：</label>
            <input type="text" placeholder="请填写预约人手机号" id="contact_mobile" name="contact_mobile">
        </div>
        <div class="reserve_arrive_time">
            <label for="reserve_arrive_time"><span>*</span>预约到店时间：</label>
            <input type="text" placeholder="选择时间"  onclick="return picker();" id="reserve_arrive_time" name="reserve_arrive_time">
        </div>
        <div class="reserve_detail clearfix">
            <label><span>*</span>预约服务：</label>
            <div class="rd_table">
                <select name="service_names" id="service_names">
                    <option value="0">请选择预约服务</option>
                    <#list ($service_list as $item)
                        <option value="${item->id!}">${item->service_name!}</option>
                    </#list>
                </select>
            </div>
        </div>
        <div class="reserve_detail clearfix">
            <label><span>*</span>预约${technician_title!}：</label>
            <div class="rd_table">
                <select name="technician_names" id="technician_names">
                    <option value="0">请选择预约${technician_title!}</option>
                </select>
            </div>
        </div>
        <div class="act_remark clearfix">
            <label>备注：</label>
            <textarea name="add_message" id="" cols="30" rows="10" placeholder="备注不超过200个字"></textarea>
        </div>
    </div>
</form>

</body>
</html>


<script language="JavaScript" src="/js/jquery-1.11.1.min.js"></script>
<script language="JavaScript" src="/js/jquery.json.js"></script>
<script language="JavaScript" src="/js/admin/util.js?v=1.0.4"></script>
<script language="JavaScript" src="/js/date/WdatePicker.js"></script>
<script src="/js/layui/layui.js" type="text/javascript"></script>
<script language="JavaScript" src="/js/artDialog/jquery.artDialog.js?skin=opera"></script>
<script language="JavaScript" src="/js/artDialog/plugins/iframeTools.source.js"></script>
<script language="JavaScript" src="/js/admin/lang/{{ config("app.locale")!}/util.js"></script>

<script language="JavaScript" src="/js/admin/table.js?v=1.0.4"></script>
<script language="JavaScript" src="/js/admin/reserve_activity_info.js?v=1.0.4"></script>
<script type="text/javascript">

    function picker() {
        return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
    }
    $("#service_names").on("change",function(){
        var _this = $(this);
        var data = {};
        data.store_id = $("input[name='store_id']").val();
        data.service_id = _this.val();
        util.ajax_json('/admin/ajax/reserve/technician',function(d){
            if(d&&d.error==0){
                var str="<option value='0'>不指定${technician_title!}</option>";
                var content = JSON.parse(d.content);
                for(var i=0;i<content.length;i++){
                    var li = content[i];
                    console.log(li);
                    str += "<option value='"+li["id"]+"'>"+li["technician_name"]+"</option>";
                }
                $("#technician_names").html(str);
            }
            else{
                util.mobile_alert(d.message);
            }
        },data);
    })
</script>