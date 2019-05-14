function picker() {
    return WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
}

$(function () {
    $('.btn-choose').click(function () {
        $('#form1').submit();
    })
});