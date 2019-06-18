$(function () {
    /*全选和全不选*/
    $("#select-all").click(function () {
        var isChecked = $("#select-all").prop("checked");
        if($(this).is(':checked')) {
            $(this).prev().attr('src','/image/admin/square_yes.png');
            $(this).parents('.list-bottom').find('.list').find("input[type='checkbox']").each(function(){
                if(!$(this).is(':checked'))
                    $(this).click();
            })
        }else{
            $(this).prev().attr('src','/image/admin/square_no.png');
            $(this).parents('.list-bottom').find('.list').find("input[type='checkbox']").each(function(){
                if($(this).is(':checked'))
                    $(this).click();
            })
        }
    });

    $(".list input[type='checkbox']").click(function () {
        $(this).attr("checked", "checked");
        var allLength = $(".list input[type='checkbox']").length;
        var checkedLength = $(".list input[type='checkbox']:checked").length;
        if(allLength == checkedLength){
            $("#select-all").prop("checked",true);
            $("#select-all").prev().attr('src','/image/admin/square_yes.png');
        }else {
            $("#select-all").prop("checked",false);
        }
        if($(this).is(':checked')){

        }else{

            $("#select-all").prev().attr('src','/image/admin/square_no.png');
        }
    });
})