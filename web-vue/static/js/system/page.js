var btn_home = $(".tb_paging a:nth-of-type(1)");
var btn_prev = $('.tb_paging a:nth-of-type(2)');
var btn_next = $('.tb_paging a:nth-of-type(3)');
var btn_last = $('.tb_paging a:nth-of-type(4)');
if(page_home == 1){
    btn_home.removeAttr('onclick');
    btn_home.css({'cursor':'default','background':'#fafafa'});
    btn_prev.css({'cursor':'default','background':'#fafafa'});
    btn_prev.removeAttr('onclick');
}
if(page_all == page_home){
    btn_last.removeAttr('onclick');
    btn_last.css({'cursor':'default','background':'#fafafa'});
    btn_next.css({'cursor':'default','background':'#fafafa'});
    btn_next.removeAttr('onclick');
}