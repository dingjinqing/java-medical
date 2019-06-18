$(document).on("click", ".down_img1", function (e) {
    var toogle = $('.double_table .image1')[0].src;
    $('.double_table .image1').attr('src', $('.double_table .image2')[0].src);
    $('.double_table .image2').attr('src', toogle);
    var url = $('input[name="title_link1"]').val();
    $('input[name="title_link1"]').val($('input[name="title_link2"]').val());
    $('input[name="title_link2"]').val(url);
    e.preventDefault();
    manager.change_show_module();
});
$(document).on("click", ".up_img2", function (e) {
    var toogle = $('.double_table .image2')[0].src;
    $('.double_table .image2').attr('src', $('.double_table .image1')[0].src);
    $('.double_table .image1').attr('src', toogle);
    var url = $('input[name="title_link2"]').val();
    $('input[name="title_link2"]').val($('input[name="title_link1"]').val());
    $('input[name="title_link1"]').val(url);
    e.preventDefault();
    manager.change_show_module();
});
