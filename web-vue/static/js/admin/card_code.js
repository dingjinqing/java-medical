$(function(){
    $('.code_set_box,.key_set_box').on('click','.increase',function(){
        if($(this).parent().parent().hasClass('code_set_box')){
            var index = $('.code_set_box p').eq($('.code_set_box p').length-1).attr('code_index');
        } else {
            var index = $('.key_set_box p').eq($('.key_set_box p').length-1).attr('key_index');
        }
        var p_clone = $(this).parent().clone();
        p_clone.find('span:eq(0)').text('批次' + ++index);
        p_clone.find('input').val('');
        p_clone.find('.remove_batch').removeClass('hide');
        p_clone.find('[name="batch_name"]').prop('disabled', false);
        if($(this).parent().parent().hasClass('code_set_box')){
            p_clone.attr('code_index', index);
            p_clone.appendTo('.code_set_box');
        } else {
            p_clone.attr('key_index', index);
            p_clone.appendTo('.key_set_box');
        }
    })
    $('.code_set_box,.key_set_box').on('click','.upload',function(){
        var _this = $(this), postData = new FormData();
        postData.append('batch_name', _this.parent().find('[name="batch_name"]').val());
        postData.append('receive_action', _this.attr('receive-action'));
        if (!postData.get('batch_name')) {
            util.mobile_alert('请填写批次名称');
            return false;
        }
        var content = postData.get('receive_action') == 1 ? $('.code_popup') : $('.key_popup'),
            title = postData.get('receive_action') == 1 ? '领取码' : '卡号+密码',
            batch_id = _this.parent().find('[name="batch_id[]"]').val();

        layer.open({
            type: 1
            ,title: [title,'text-align:center;padding: 0']
            ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
            ,area: ['auto','330px']
            ,id: 'layerDemo' //防止重复弹出
            ,content: content //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            ,btn: ['保存','取消']
            ,btnAlign: 'r' //按钮居右
            ,shade: [0.3, '#000'], //显示遮罩透明度和颜色
            success: function (layero, index) {
                var action =  (card_batch == undefined || card_batch[batch_id] == undefined) ? 1 : card_batch[batch_id].action;
                if (postData.get('receive_action') == 1) {
                    $('.code_popup [name="action"][value="'+action+'"]').prop('checked', true);
                    $('.code_popup [name="action"][value="'+action+'"]').trigger('change');
                    if (card_batch != undefined && card_batch[batch_id] != undefined) {
                        $('.code_popup [name="code_size"]').val(card_batch[batch_id].code_size).attr('disabled', true);
                        $('.code_popup [name="number"]').val(card_batch[batch_id].number).attr('disabled', true);
                        $('.code_popup [name="action"][value="'+action+'"]').attr('disabled', true);
                        $('.code_popup [name="code_prefix"]').val(card_batch[batch_id].code_prefix).attr('disabled', true);
                        $('.code_popup .export').attr('href', '/admin/ajax/card/exportbatch?batch_id='+batch_id+'&receive_action='+postData.get('receive_action'));
                        $('.code_popup .export').show();
                    } else {
                        $('.code_popup input').not('[name="action"]').val('').attr('disabled', false);
                        $('.code_popup [name="action"]').attr('disabled', false);
                        $('.code_popup .export').attr('href', '');
                        $('.code_popup .export').hide();
                    }
                } else {
                    $('.key_popup  [name="action"][value="'+action+'"]').prop('checked', true);
                    $('.key_popup [name="action"][value="'+action+'"]').trigger('change');
                    if (card_batch != undefined && card_batch[batch_id] != undefined) {
                        $('.key_popup [name="card_size"]').val(card_batch[batch_id].card_size).attr('disabled', true);
                        $('.key_popup [name="card_pwd_size"]').val(card_batch[batch_id].card_pwd_size).attr('disabled', true);
                        $('.key_popup [name="number"]').val(card_batch[batch_id].number).attr('disabled', true);
                        $('.key_popup  [name="action"][value="'+action+'"]').attr('disabled', true);
                        $('.key_popup [name="card_prefix"]').val(card_batch[batch_id].card_prefix).attr('disabled', true);
                        $('.key_popup .export').attr('href', '/admin/ajax/card/exportbatch?batch_id='+batch_id+'&receive_action='+postData.get('receive_action'));
                        $('.key_popup .export').show();
                    } else {
                        $('.key_popup input').not('[name="action"]').val('').attr('disabled', false);
                        $('.key_popup [name="action"]').attr('disabled', false);
                        $('.key_popup .export').attr('href', '');
                        $('.key_popup .export').hide();
                    }
                }

            },
            yes: function(index, layero){
                var action = content.find('[name="action"]:checked').val();
                if (batch_id && action != 2) {
                    layer.close(index);
                    return false;
                }
                postData.append('batch_id', batch_id);
                postData.append('action', action);
                if (!postData.get('action')) {
                    util.mobile_alert('请选择制卡方式');
                    return false;
                }
                if (postData.get('receive_action') == 1) {
                    if (postData.get('action') == 1) {
                        if($('.code_popup .create_code [name="code_size"]').val() < 6 || $('.code_popup .create_code [name="code_size"]').val() > 12){
                            util.mobile_alert('请输入正确的领取码位数')
                            return false;
                        }
                        postData.append('code_prefix',$('.code_popup .create_code [name="code_prefix"]').val())
                        postData.append('code_size', $('.code_popup .create_code [name="code_size"]').val());
                        postData.append('number', $('.code_popup .create_code [name="number"]').val());
                    } else {
                        postData.append('card_import', $('#file1')[0].files[0]);
                    }
                } else {
                    if (postData.get('action') == 1) {
                        if($('.key_popup  .create_key [name="card_size"]').val() < 6 || $('.key_popup  .create_key [name="card_size"]').val() > 12){
                            util.mobile_alert('请输入正确的卡号位数')
                            return false;
                        }
                        postData.append('card_prefix',$('.key_popup .create_key [name="card_prefix"]').val())
                        postData.append('card_size', $('.key_popup  .create_key [name="card_size"]').val());
                        postData.append('card_pwd_size', $('.key_popup  .create_key [name="card_pwd_size"]').val());
                        postData.append('number', $('.key_popup  .create_key [name="number"]').val());
                    } else {
                        postData.append('card_import', $('#file2')[0].files[0]);
                    }
                }
                $.ajaxSetup({
                    contentType : false,
                    processData : false
                });
                util.ajax_json('/admin/ajax/card/generatecode', function (response) {
                    if (response.error == 0) {
                        var batch_id = response.content.batch_info.id;
                        _this.parent().find('[name="batch_id[]"]').eq(0).val(batch_id);
                        card_batch[batch_id] = response.content.batch_info;
                        _this.parent().find('[name="batch_name"]').prop('disabled', true);
                        get_import_record(batch_id,_this.parent().find('[name="batch_name"]').val(),postData.get('receive_action'))
                        layer.close(index);
                    } else {
                        util.mobile_alert(response.message);
                    }
                }, postData)
            },
            btn2: function(index, layero){
                //按钮取消的回调
            }
        });
        
    });
    $('.code_set_box,.key_set_box').on('click', '.remove_batch', function () {
        if($(this).parents('.code_set_box').length != 0){
            var box_length = $(this).parents('.code_set_box').find('p').length
        } else {
            var box_length = $(this).parents('.key_set_box').find('p').length
        }
        if(box_length < 2){
            layer.msg("最少保留一个批次");
            return false;
        }
        var _that = $(this);
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.alert('<div style="text-align: center;">确认要废除该批次吗？</div>', {
                title: ['提醒', 'text-align:center;padding: 0px;']
                , area: '260px'
                , closeBtn: 0
                , btn: ['确定', '取消']
            },function(index){
                var batch_id = _that.parent().find('[name="batch_id[]"]').val();
                if (card_batch != undefined && card_batch[batch_id] != undefined) {
                    delete card_batch[batch_id];
                }
                _that.parent().remove();
                layer.close(index);
            });
        });  
    })
    $('.key_set_box,.code_set_box').on('click','.import_record',function(){
        let batch_id = $(this).parent().find('[name="batch_id[]"]').val();
        let record_batch = $(this).parent().find('[name="batch_name"]').val();
        let receive_action = $(this).attr('receive-action');
        get_import_record(batch_id, record_batch, receive_action)
    })
    $('input[name="action"]').change(function(){
        if ($(this).parent().parent().hasClass('code_popup')) {
            console.log($(this).val());
            if ($(this).val() == 1) {
                $('.code_popup .upload_code').hide();
                $('.code_popup .create_code').show();
            } else {
                $('.code_popup .upload_code').show();
                $('.code_popup .create_code').hide();
            }
        } else {
            if ($(this).val() == 1) {
                $('.key_popup .upload_key').hide();
                $('.key_popup .create_key').show();
            } else {
                $('.key_popup .upload_key').show();
                $('.key_popup .create_key').hide();
            }
        }
    })
    if($('input[name="receive_action"]:checked').val() != 1){
        $('.key_set_box').show();
    } else {
        $('.code_set_box').show();
    }
    $('input[name="receive_action"]').change(function(){
        if($('input[name="receive_action"]:checked').val() != 1){
            $('.key_set_box').show();
            $('.code_set_box').hide();   
        } else {
            $('.key_set_box').hide();
            $('.code_set_box').show();
        }
    })
    function get_import_record(batch_id,record_batch,receive_action){
        var postData = new FormData();
        postData.append('batch_id', batch_id);
        $.ajaxSetup({
            contentType : false,
            processData : false
        });
        util.ajax_json('/admin/ajax/cardcode/record', function (response) {
            let mock = response.content.list;
            let tbody_content = '';
            let record_total = 0;
            if(mock.length == 0){
                util.mobile_alert('暂无记录')
                return false;
            }
            mock.forEach((el,index) => {
                tbody_content += `<tr>
                                <td>${index + 1}</td>
                                <td>${el.add_time}</td>
        <td style="color:#5a8bff">${el.success_num ? el.success_num : 0} ${el.success_num > 0 ? `<a href="/admin/ajax/card/batchgroup?batch_id=${batch_id}&group_id=${el.group_id}&receive_action=${receive_action}&is_success=1" target="_blank" class="import_download success"></a>` : ''}</td>
                                <td style="color:red">${el.fail_num ? el.fail_num : 0} ${el.fail_num > 0 ? `<a href="/admin/ajax/card/batchgroup?batch_id=${batch_id}&group_id=${el.group_id}&receive_action=${receive_action}&is_success=0" target="_blank" class="import_download fail">` : ''}</td>
                            </tr>`;
                record_total += el.success_num;
            });
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.alert(`<div class="record_box">
                        <p class="clearfix">
                            <span class="fl">批次：<span class="record_batch">${record_batch}</span></span>
                            <span class="fr">合计：<span class="record_total">${record_total}</span>/10000</span>
                        </p>
                        <div class="info_table"><table width="100%;">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>操作时间</th>
                                        <th>成功数量</th>
                                        <th>失败数量</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    ${tbody_content}
                                </tbody>
                            </table>
                        </div>
                    </div>`, {
                    title: ['导入记录', 'text-align:center;padding: 0px;']
                    , area: ['600px','auto']
                    , btn: ['确定']
                    , btnAlign : 'c'
                    , shade: [0.3, '#000']
                },function(index){

                    layer.close(index);
                });
            });
        }, postData)

    }
})