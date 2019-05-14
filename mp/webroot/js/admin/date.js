$(function(){
    var cells = document.getElementById('monitor').getElementsByTagName('div');
    var day1 = document.getElementById('curday');
    var day7 = document.getElementById('curday7');
    var this_week = document.getElementById('this_week');
    var clen = cells.length;
    var currentFirstDate;
    var myDate=new Date();
    today = myDate.getDate();//获取当前日期
    var formatDate = function(date){
        var year = date.getFullYear()+'-';
        var month = (date.getMonth()+1)+'-';
        var day = date.getDate();
        var week = '('+['星期一','星期二','星期三','星期四','星期五','星期六','星期天'][date.getDay()]+')';
        return day;
    };
    var addDate= function(date,n){
        date.setDate(date.getDate()+n);
        return date;
    };
    var setDate = function(date){
        var week = date.getDay()-1;
        var cell_txt = [0,0,0,0,0,0,0];
        var the_month;
        date = addDate(date,week*-1);
        currentFirstDate = new Date(date);
        for(var i = 0;i<clen;i++){
            //if(i=0){the_month = (date.getMonth()+1);}
            cells[i].innerHTML = formatDate(i==0 ? date : addDate(date,1));
            cell_txt[i] = cells[i];

            if(i==0)
            day1.innerHTML=date.getFullYear() +'-'+ (date.getMonth()+1) + '-' + cell_txt[0].innerHTML;
            day7.innerHTML=date.getFullYear() +'-'+ (date.getMonth()+1) + '-' + cell_txt[6].innerHTML;
        }
    };
    var setContent = function () {
        var this_td = $("#tech_schedule").find('td');
        this_td = this_td.slice(1,8);
        var param = {
            store_id:$("#store_id").val(),
            technician_id:$("#technician_id").val(),
            business_type:$("#business_type").val(),
            start_date:day1.innerHTML,
        }
        util.ajax_json("/admin/store/services/technician/schedule/list",function (d) {
            if(d && d.error == 0){
                $.each(this_td,function (k,v) {
                    var tech_schedule = d.content[k];
                    var td_html = '';
                    if(tech_schedule && tech_schedule.schedule_id > 0){
                        td_html += '<span class="sche_span">' + tech_schedule.schedule_name + '</span>';
                        td_html += '<div>' + tech_schedule.begin_time + '-' + tech_schedule.end_time + '</div>';
                    }else{
                        td_html += '<span class="sche_span">无排班</span>';
                        td_html += '<div></div>';
                    }
                    $(v).html(td_html);
                });
            } else if (d && d.error > 0) {
                layer.msg(d.message);
            }
        },param)

    };
    document.getElementById('last-week').onclick = function(){
        setDate(addDate(currentFirstDate,-7));
        setContent()
    };
    document.getElementById('next-week').onclick = function(){
        setDate(addDate(currentFirstDate,7));
        setContent()
    };
    this_week.onclick = function () {
        setDate(new Date());
        setContent()
    }
    setDate(new Date());
});
