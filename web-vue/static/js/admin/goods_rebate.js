//分销改价
// if($(".if_change_dis_price").is(":checked")){
//     $('.dis_price').css('display','block');
//     $('.set_dis_price>td').removeClass('buxianshi');
// }else{
//     $('.dis_price').css('display','none');
//     $('.set_dis_price>td').addClass('buxianshi');
// }
$(".if_change_dis_price").change(function () {
    if($(".if_change_dis_price").is(":checked")){
        changeRebatePrice()
        $('.dis_price').css('display','block');
        $('.set_dis_price>td').removeClass('buxianshi');
    }else{
        $('.dis_price').css('display','none');
        $('.set_dis_price>td').addClass('buxianshi');
    }
});
$(document).on('click','.rebate_min_price',function () {
    console.log('sss')
    setRebatePrice(2)
});
$(document).on('click','.rebate_max_price',function () {
    setRebatePrice(3)
});

function setRebatePrice(td_index) {
    if (td_index == 2 || td_index == 3) {
        var temp_price = 0;
        $('.dis_price table tr').each(function (i,item) {
            if (i == 0 || $(item).hasClass('last_spec')) return true;
            let tds = $(item).find('td');
            if (i == 1) {
                temp_price = tds.eq(td_index).find('input').val();
            } else {
                tds.eq(td_index).find('input').val(temp_price);
            }
            console.log(temp_price)
        })
    }
}

function changeRebatePrice() {
    var spec_table_tr = $('.spec-price table tr');
    var dis_table_tr = $('.dis_price table tr');

    var new_table = $('<table width="100%"></table>');
    if (spec_table_tr.length > 2) {
        var rebate_price = rebatePriceList();
        console.log(rebate_price)
        spec_table_tr.each(function (index, spec_tr) {
            if ($(spec_tr).hasClass('last_spec')) return true;
            if (index > 0) {
                let spec_tds = $(spec_tr).find('td');
                let spec_val_id = spec_tds.eq(1).find('input').attr('spec_val_id');
                let spec_name = spec_tds.eq(0).text();
                let spec_price = spec_tds.eq(1).find('input').val();
                console.log(spec_val_id)
                if (rebate_price[spec_val_id]) {
                    let old_tr = dis_table_tr.eq(rebate_price[spec_val_id].index);
                    old_tr.find('td').eq(0).text(spec_name);
                    old_tr.appendTo(new_table);
                } else {
                    $('<tr><td class="spec_item" spec_val_id="' + spec_val_id + '">' + spec_name + '</td>' +
                        '<td><input type="text" value="' + spec_price + '"/></td>' +
                        '<td><input type="text" /></td>' +
                        '<td><input type="text" /></td>' +
                        '</tr>').appendTo(new_table)
                }
            } else {
                dis_table_tr.eq(0).find('td').eq(0).css('display','block');
                dis_table_tr.eq(0).appendTo(new_table);
                return true;
            }
        });
        $('<tr class="last_spec"><td colspan="5" class="batche-set set_rebate_price"><span>批量设置：</span>' +
            '<a href="javascript:void(0)" class="rebate_min_price">最低售价</a>' +
            '<a href="javascript:void(0)" class="rebate_max_price">最高售价</a>' +
            '</td></tr>').appendTo(new_table);
    } else {
        dis_table_tr.eq(0).find('td').eq(0).css('display','none');
        dis_table_tr.eq(0).appendTo(new_table);
        let shop_price = $('[name="shop_price"]').val();
        $('<tr><td class="spec_item" spec_val_id="" style="display: none"></td>' +
            '<td><input type="text" value="' + shop_price + '"/></td>' +
            '<td><input type="text" /></td>' +
            '<td><input type="text" /></td>' +
            '</tr>').appendTo(new_table);
    }

    $('.dis_price').html(new_table);
}

function rebatePriceList(){
    var dataList = [];
    $('.dis_price table tr').each(function (i,item) {
        console.log(i)
        if (i == 0 || $(item).hasClass('last_spec')) return true;
        let tds = $(item).find('td');
        let spec_val_id = tds.eq(0).attr('spec_val_id');
        dataList[spec_val_id] = {
            index: i,
            spec_val_id: spec_val_id,
            spec_desc: tds.eq(0).text(),
            advise_price: parseFloat(tds.eq(1).find('input').val() || 0),
            min_price: parseFloat(tds.eq(2).find('input').val() || 0),
            max_price: parseFloat(tds.eq(3).find('input').val() || 0)
        };
        // console.log(dataList[spec_val_id])
    });
    return dataList;
}
