<div class="row_item m_card">
    <div class="no_use" style="display: none;">
        <img src="http://${image_domain!}/image/admin/no_drag_use.png" />该模块仅高级版和旗舰版可用
        <img class="del_img" src="http://${image_domain!}/image/admin/icon_delete.png" style="float: right;margin: 10px 10px 0 0;cursor:pointer;" />
    </div>
    <div class="card_module">
        <div class="card_back_module">
            <div class="card_type">普通卡</div>
            <div class="card_content clearfix">
                <div class="card_shop_icon">
                    <img <#if shop.shop_avatar??> src="http://${image_domain!}/image/admin/shop_def_y.png" <#else> src="${shop.shop_avatar!}" </#if> alt="">
                </div>
                <div class="card_content_right">
                    <div>会员卡</div>
                    <p>&times;&times;&times;&times;&times;&times;&times;&times;</p>
                </div>
                <div class="card_pay_fee">￥0.00元</div>
            </div>
            <div class="card_bottom">
                更多权益领取后查看<span>我要领卡</span>
            </div>
        </div>
    </div>
    <div class="item_module_title">
        <span>会员卡</span>
    </div>
    <div class="item_operation">
        <img class="up_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_up_use.png">
        <img class="down_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_down.png">
        <img class="del_img" src="http://${image_domain!}/image/admin/new_shop_beautify/add_close.png">
    </div>
    <div class="item_no_data">没有数据</div>
</div>

<div class="data_item d_m_card edit_card_modules">
    <h2>会员卡模块</h2>
    <div class="card_modules_right clearfix">
        <div class="card_right_left">会员卡:</div>
        <div class="card_add card_add_click">
            <img src="http://${image_domain!}/image/admin/shop_beautify/add_decorete.png" alt="">
            <p>添加会员卡</p>
        </div>
        <div class="card_right_content card_add_click" style="display: none;">
            <div class="card_back">
                <div>
                    <input type="hidden" card_type="" legal="" bg_type="" bg_color="" bg_img="" id="card_info" card_id="" />
                    <span class="card_name">会员卡名称</span>
                    <span class="card_state">使用中</span>
                    <span class="card_grade" style="display: none;float: right;margin-right: 20px;">v1</span>
                </div>
                <p>
                    有效期:2018.07.01-2018.09.01
                </p>
            </div>
        </div>
    </div>
    <div class="hidden_card_input">
        <input type="checkbox" name="hidden_card">用户领取后隐藏会员卡
    </div>
    <!-- <div style="margin-top:15px;">-->
        <!-- <input class="btn btn-primary queren " type="button" id="ok" name="ok" value="确定" style="margin-left: 80px;">-->
    <!-- </div>-->
</div>