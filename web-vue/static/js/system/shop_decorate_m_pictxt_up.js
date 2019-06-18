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


$(document).on("click", ".up_image", function (e) {
	var p = $(this).parents(".choice");
	var pre = p.prev();
	if(pre.length > 0){
		pre.insertAfter(p);
	}
	$('.choice-content').each(function (index, item) {
		var xiugai = index + 1;
        if(index == 5) {
        	return false;
        }
        $(this).find('.choice_con').attr('name', "title"+xiugai);
        $(this).find('.choice_con').attr('id', "title"+xiugai);
        $(this).find('.choice_url').attr('name', "title_link"+xiugai);
        $(this).find('.choice_url').attr('id', "title_link"+xiugai);
        $(this).find('.sortable_btn').attr('class', "sortable_btn select_links" + xiugai);
	})
	$('.choice-image').each(function (index, item) {
		var xiugai = index + 1;
        if(index == 5) {
        	return false;
        }
        $(this).find('img').attr('class', "image"+xiugai);
	})
    checkimg5();
})
$(document).on("click", ".down_image", function (e) {
	var p = $(this).parents(".choice");
	var next = p.next();
	if(next.length > 0){
		next.insertBefore(p);
	}
	$('.choice-content').each(function (index, item) {
		var xiugai = index + 1;
        if(index == 5) {
        	return false;
        }
        $(this).find('.choice_con').attr('name', "title"+xiugai);
        $(this).find('.choice_con').attr('id', "title"+xiugai);
        $(this).find('.choice_url').attr('name', "title_link"+xiugai);
        $(this).find('.choice_url').attr('id', "title_link"+xiugai);
        $(this).find('.sortable_btn').attr('class', "sortable_btn select_links" + xiugai);
	})
	$('.choice-image').each(function (index, item) {
		var xiugai = index + 1;
        if(index == 5) {
        	return false;
        }
        $(this).find('img').attr('class', "image"+xiugai);
    })
    checkimg5()
})
function checkimg5(){
    let image_guide5 = $('#module_edit .ul-sortable').find('.image_guide5').clone(true);
    let del_row_item = $('#module_edit .ul-sortable').find('.del_row_item').clone(true);
    $('#module_edit .ul-sortable .choice').each(function(){
        $('#module_edit .ul-sortable').find('.image_guide5').remove()
        $('#module_edit .ul-sortable').find('.del_row_item').remove()
        if($(this).index() != 4){
            $(this).removeClass('image_guide_5')
        } else {
            console.log(image_guide5)
            console.log(del_row_item)
            $(this).addClass('image_guide_5')
            $(this).find('.control-group')[1].append(image_guide5[0]);
            $(this).find('.control-group')[1].append(del_row_item[0]);
        }
    })
    manager.change_show_module();
}