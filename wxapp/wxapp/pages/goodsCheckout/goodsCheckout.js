// pages/goodsCheckout/goodsCheckout.js
var app = getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var post_type = '';
var shop_flag;
var score_pay_control = 0;
var account_pay_control = 0;
var card_account_pay_control = 0;
var checkbox_no = imageUrl + '/image/admin/select.png';
var goods_ida;
var order_real_name = 0;
var order_cid = 0;
var consignee_real_name = 0;
var consignee_cid = 0;
var custom = 0;
var custom_title = "";
var must_content = [];
var service_choose = 1;
var service_name = "";
var prompt_message = '';
var score_money_input;
var user_account_input;
var new_money_paid;
var member_card_input;
var member_card_balance;
var account_discount;
var score_discount;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    checkbox_no: imageUrl + '/image/admin/select.png',
    imageUrl: app.globalData.imageUrl,
    total_micro_second: -1,
    is_delete: 0,
    user_money: {
      score_ratio: 100
    },
    hasPurchase: 0,
    hasFullprice:0,
    hasPackage:0,
    cardMode: true,
    payMode: true,
    ruleMode: true,
    choose_card: {},
    timesMode: true,
    storeMode: true,
    insmode: true,
    score_pay_control: 0,
    account_pay_control: 0,
    pay_click_type: 0,
    card_account_pay_control: 0,
    pay_score: 0,
    pay_yue: 0,
    pay_card: 0,
    deliver_config: {},
    store_array: {},
    canClick: true,
    choose_store: {
      show_id: 0,
      show_time: 0,
      store_id: -1,
      store_name: '',
      address: '',
      dis: 0
    },
    choose_times: {
      date_id: 0,
      new_date_id: 0,
      time_id: 0,
      new_time_id: 0
    },
    order_real_name: 0,
    order_cid: 0,
    consignee_real_name: 0,
    consignee_cid: 0,
    custom: 0,
    custom_title: "",
    must_content: [],
    exchang_good: 0,
    service_choose: 1,
    service_name: "",
    score_pay_num: 100,
    prompt_message: "",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    console.log(options);
    if (!util.check_setting(options)) return;
    shop_flag = util.getCache('shop_flag');
    post_type = options.order_type;
    clearInterval(this.data.set_time_1);
    var choose_list = options.choose_list ? JSON.parse(options.choose_list) : {};
    must_content = [];
    goods_ida = choose_list.goods_id;
    if (post_type == 'cart') {
      this.data.create_order = {
        goods: choose_list,
        user_cart: 1,
        id_card: "",
        true_name: "",
        scan_store_id: choose_list.scan_store_id
      }
    } else if (post_type == 'give_gift') {
      this.data.create_order = {
        gift_id: options.gift_id
      }
    } else {
      this.data.create_order = {
        goods: [choose_list],
        record_id: options.record_id,
        pin_group_id: choose_list.pin_group_id,
        is_grouper_cheap: choose_list.is_grouper_cheap,
        integral_goods_id: choose_list.integral_goods_id,
        sk_id: choose_list.sk_id,
        lc_id: choose_list.lc_id,
        id_card: "",
        true_name: "",
        group_draw_id: choose_list.group_draw_id,
        group_id: choose_list.group_id,
        query_param: options.query_param != undefined ? JSON.parse(options.query_param) : [],
        card_no: options.card_no,
        launch_id: choose_list.launch_id
      }
    }
    this.data.old_create_order = this.data.create_order //用于刷新页面
    this.setData({
      shop_flag: shop_flag,
      exchang_good: options.exchang_good ? options.exchang_good : 0
    })
    this.loadPage(this.data.create_order);
  },
  jifen_fo: function(e) {
    this.setData({
      score_pay_control: 1,
      prompt_message: '',
      canClick: true,
    })
  },
  yue_fo: function(e) {
    this.setData({
      account_pay_control: 1,
      prompt_message: '',
      canClick: true,
    })
  },
  mem_fo: function(e) {
    this.setData({
      card_account_pay_control: 1,
      prompt_message: '',
      canClick: true,
    })
  },
  // 验证身份证和真实姓名
  checkIDcard: function(e) {
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请如实填写订单收货信息，务必保证收货人与收货人身份证保持一致，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      this.data.create_order.id_card = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 18) {
      util.showModal("提示", "身份证号长度不超过18位");
      this.data.create_order.id_card = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    if (!(re.test(e.detail.value))) {
      util.showModal("提示", "请输入正确的身份证号");
      this.data.create_order.id_card = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    this.data.create_order.id_card = e.detail.value.replace(/^\s+|\s+$/g, '');

  },
  checkName: function(e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请如实填写订单收货信息，务必保证收货人与收货人身份证保持一致，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      this.data.create_order.true_name = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    } else if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 40) {
      util.showModal("提示", "真实姓名长度不得多于40字");
      this.data.create_order.true_name = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    } else {
      this.data.create_order.true_name = e.detail.value.replace(/^\s+|\s+$/g, '');
    }
  },
  // 备注
  addMsg: function(e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 240 && shop_flag == 1) {
      util.showModal("提示", "备注长度不得超过240字");
      return false;
    }
    this.data.create_order.msg = e.detail.value;
  },
  loadPage: function(create_order_data) {
    var that = this;
    var order_data = JSON.stringify(create_order_data);
    util.api('/api/wxapp/order/toBuy', function(res) {
      //生成订单的相关信息
      if (res.error == 0) {
        var payment = res.content.payment;
        if (payment.length < 1 || res.content.disp == 0) {
          util.navigateTo({
            url: '/pages/index/index',
          });
          return false;
        }
        that.showPageData(that, res.content);
        that.setData({
          is_delete: 1,
        })
      } else {
        if (post_type == 'sec_kill') {
          if (res.content.unpaid_order_sn == '') {
            util.showModal('提示', res.content.message, function() {
              util.reLaunch({
                url: '/pages/item/item?goods_id=' + res.content.goods_id
              })
            }, false, '取消', '原价购买');
          } else {
            wx.showModal({
              title: '提示',
              content: res.content.message,
              cancelText: '原价购买',
              confirmText: '去支付',
              confirmColor: '#ff6666',
              success: function(response) {
                if (response.confirm) {
                  util.reLaunch({
                    url: '/pages/payment/payment?order_sn=' + res.content.unpaid_order_sn
                  })
                } else if (response.cancel) {
                  util.reLaunch({
                    url: '/pages/item/item?goods_id=' + res.content.goods_id
                  })
                }
              }
            })
          }
        } else if (post_type == 'cart') {
          res.message = res.message == '' ? '该商品不存在' : res.message;
          util.showModal('提示', res.message, function() {
            wx.navigateBack({
              url: '/pages/cart/cart'
            })
          }, false);
        } else if (post_type == "give_gift") {
          util.showModal('提示', res.message, function() {
            wx.navigateBack({})
          }, false);
        } else {
          res.message = res.message == '' ? '该商品不存在' : res.message;
          util.showModal('提示', res.message, function() {
            util.redirectTo({
              url: '/pages/index/index'
            })
          }, false);
        }
        return false;
      }
      //下单必填信息
      if (res.content.order_must_cfg[0]) {
        order_real_name = 0;
        order_cid = 0;
        consignee_real_name = 0;
        consignee_cid = 0;
        custom = 0;
        custom_title = 0;
        must_content = [];
        for (var must in res.content.order_must_cfg[0]) {
          if (res.content.order_must_cfg[0][must] == 'order_real_name') {
            order_real_name = 1;
            must_content.push(res.content.order_must_cfg[0][must]);
          }
          if (res.content.order_must_cfg[0][must] == 'order_cid') {
            order_cid = 1;
            must_content.push(res.content.order_must_cfg[0][must]);
          }
          if (res.content.order_must_cfg[0][must] == 'consignee_real_name') {
            consignee_real_name = 1;
            must_content.push(res.content.order_must_cfg[0][must]);
          }
          if (res.content.order_must_cfg[0][must] == 'consignee_cid') {
            consignee_cid = 1;
            must_content.push(res.content.order_must_cfg[0][must]);
          }
          if (res.content.order_must_cfg[0][must] == 'custom') {
            custom = 1;
            custom_title = res.content.order_must_cfg[1][0];
            must_content.push(res.content.order_must_cfg[0][must]);
          }
        }
      }
      that.setData({
        order_real_name: order_real_name,
        order_cid: order_cid,
        consignee_real_name: consignee_real_name,
        consignee_cid: consignee_cid,
        custom: custom,
        custom_title: custom_title,
        must_content: must_content,
      })
    }, {
      create_order: order_data,
      post_type: post_type
    }, '', true);
  },

  showPageData: function(that, postData) {
    //初始下单信息
    that.data.create_order.goods = that.data.create_order.goods ? that.data.create_order.goods : postData.goods;
    that.data.create_order.score_pay_num = postData.score_pay_num;
    that.data.create_order.group_id = postData.group_id;
    that.data.create_order.lc_id = postData.lc_id;
    that.data.create_order.original_price = postData.original_price.toFixed(2);
    that.data.create_order.member_card_reduce = postData.member_card_reduce.toFixed(2);
    that.data.create_order.promotion_reduce = postData.promotion_reduce.toFixed(2);
    that.data.create_order.grouper_cheap_reduce = postData.grouper_cheap_reduce.toFixed(2);
    that.data.create_order.discount = postData.discount.toFixed(2);
    that.data.create_order.package_discount = postData.package_discount.toFixed(2);
    that.data.create_order.pre_sale_discount = postData.pre_sale_discount.toFixed(2);
    that.data.create_order.bk_order_money = postData.bk_order_money.toFixed(2);
    that.data.create_order.pre_sale_money = (parseFloat(postData.money_paid) + parseFloat(postData.pre_sale_discount)).toFixed(2);
    that.data.create_order.pre_sale_money_paid = postData.money_paid.toFixed(2);
    that.data.create_order.money_paid = postData.money_paid.toFixed(2);
    that.data.create_order.shipping_fee = postData.shipping_fee.toFixed(2);
    that.data.create_order.card_no = postData.member_card_no;
    that.data.create_order.coupon_sn = postData.coupon_sn;
    that.data.create_order.store_id = postData.store_id
    that.data.create_order.member_card_balance = 0;
    that.data.create_order.account_discount = 0;
    that.data.create_order.cart_order_goods = postData.goods;
    that.data.create_order.order_pay_way = postData.order_pay_way;
    that.data.create_order.instead_pay = postData.instead_pay;
    that.data.create_order.instead_pay_num = postData.instead_pay != undefined ? postData.instead_pay.instead_pay_num : 0;
    that.data.create_order.service_terms = postData.service_terms;
    that.data.create_order.service_choose = postData.service_choose;
    that.data.create_order.service_name = postData.service_name;
    that.data.goods_type = postData.goods_type;
    that.data.instead_pay_show = false;
    that.data.money_after_discount = postData.money_after_discount;
    that.data.gift_store_goods = postData.gift_store_goods;
    that.data.pick_can_order = false;
    that.data.user_money.score_discount_ratio = postData.score_discount_ratio;
    that.data.score_max_discount = parseFloat(postData.score_max_discount).toFixed(2);

    if (post_type == 'integral') {
      var money_temp = 0;
      var score_temp = 0;
      for (var j in postData.goods) {
        money_temp += parseFloat((postData.goods[j].goods_price - postData.goods[j].goods_score / 100)).toFixed(2) * postData.goods[j].goods_number;
        score_temp += parseFloat((postData.goods[j].goods_score / 100)).toFixed(2) * postData.goods[j].goods_number;
      }
      that.data.create_order.money_paid = money_temp;
      that.data.create_order.money_temp = money_temp;
      that.data.create_order.score_discount = score_temp;
      that.data.create_order.new_score_integral = parseInt(that.data.create_order.score_discount * 100);
    } else {
      that.data.create_order.score_discount = 0;
    }
    if (post_type == 'pre_sale') {
      that.data.create_order.shipping_time = postData.shipping_time;
    }
    that.data.user_money.member_card_money = parseFloat(postData.member_card_money);
    that.data.user_money.score = postData.score;
    that.data.user_money.account = postData.account;

    //倒计时
    if (that.data.total_micro_second == -1) {
      that.data.total_micro_second = postData.cancel_time * 60;
      that.setData({
        clock: util.dateformat(that.data.total_micro_second),
      });
      that.setTimeInterval(that);
    }

    // 配送方式
    that.data.deliver_config.disp = postData.disp;
    if (postData.disp == 2) {
      that.data.deliver_config.kuaidi_view = "none";
      that.data.deliver_config.ziti_view = "block";
      that.data.deliver_config.border_2 = "1px solid " + that.data.comColor;
      that.data.deliver_config.color_2 = that.data.comColor;
    } else if (postData.disp == 4) {
      if (postData.store_id > 0) {
        this.data.deliver_config.kuaidi_view = "none";
        this.data.deliver_config.ziti_view = "block";
        this.data.deliver_config.border_1 = "1px solid #eee";
        this.data.deliver_config.color_1 = "#3535356";
        this.data.deliver_config.border_2 = "1px solid " + that.data.comColor;
        this.data.deliver_config.color_2 = that.data.comColor;
      } else {
        this.data.deliver_config.kuaidi_view = "block";
        this.data.deliver_config.ziti_view = "none";
        this.data.deliver_config.border_1 = "1px solid " + that.data.comColor;
        this.data.deliver_config.color_1 = that.data.comColor;
        this.data.deliver_config.border_2 = "1px solid #eee";
        this.data.deliver_config.color_2 = "#3535356";
      }
    } else if (postData.disp == 1) {
      this.data.deliver_config.kuaidi_view = "block";
      this.data.deliver_config.ziti_view = "none";
      this.data.deliver_config.border_1 = "1px solid " + that.data.comColor;
      this.data.deliver_config.color_1 = that.data.comColor;
      this.data.deliver_config.border_2 = "1px solid #eee";
      this.data.deliver_config.color_2 = "#3535356";
    }

    // 快递
    if (postData.address) that.data.create_order.address_id = postData.address.address_id;

    if (that.data.create_order.scan_store_id > 0 && postData.scan_store) {
      util.getUserLocation(function(location) {
        postData.scan_store.dis = app.getDistance(postData.scan_store.latitude, postData.scan_store.longitude, location.latitude, location.longitude);
        that.setData({
          scan_store: postData.scan_store,
        })
      })
    }
    // 可以门店自提
    if (postData.disp == 2 || postData.disp == 4) {
      var store_array = postData.store;
      var choose_yes = imageUrl + 'image/wxapp/selected.png';
      var choose_no = imageUrl + 'image/wxapp/icon_rectangle.png';
      for (var i in store_array) {
        store_array[i].choose_yes = choose_yes;
        store_array[i].choose_no = choose_no;
        store_array[i].store_radio = choose_no;
      }
      if (store_array.length > 0) {
        util.getUserLocation(function(location) {
          if (location) {
            var lat = 0;
            var lon = 0;
            var min = 10000;
            var dis = 0;
            var min_id = 0;
            for (var i = 0; i < store_array.length; i++) {
              lat = store_array[i].latitude;
              lon = store_array[i].longitude;
              dis = app.getDistance(lat, lon, location.latitude, location.longitude);
              if (lat > 0 && lon > 0) {
                store_array[i].dis = dis;
                if (that.data.create_order.store_id > 0) {
                  if (store_array[i].store_id == that.data.create_order.store_id) {
                    min_id = i;
                  }
                } else {
                  if (parseFloat(store_array[i].dis) < parseFloat(min)) {
                    min = parseFloat(store_array[i].dis);
                    min_id = i;
                  }
                }
              }
            }
            store_array[min_id].store_radio = choose_yes;
            if (that.data.create_order.store_id <= 0) {
              that.data.choose_store.index = min_id;
              that.data.choose_store.store_id = store_array[min_id].store_id;
              that.data.choose_store.store_name = store_array[min_id].store_name;
              that.data.choose_store.address = store_array[min_id].address;
              that.data.choose_store.dis = store_array[min_id].dis;
              if (postData.disp == 2) {
                that.data.create_order.store_id = store_array[min_id].store_id;
              }
            }
            console.log(store_array)
            that.setData({
              store_array: store_array,
              choose_store: that.data.choose_store
            })
          } else {
            if (that.data.create_order.store_id <= 0) {
              that.data.choose_store.index = 0;
              that.data.choose_store.store_id = store_array[0].store_id;
              that.data.choose_store.store_name = store_array[0].store_name;
              that.data.choose_store.address = store_array[0].address;
              that.data.choose_store.dis = store_array[0].dis;
            }
            that.setData({
              store_array: store_array,
              choose_store: that.data.choose_store
            })
          }
        })
      }
      // 自提时间
      if (postData.pickup_date.length > 0) {
        that.data.create_order.date = postData.pickup_date[that.data.choose_times.date_id].date;
        that.data.create_order.time = postData.pickup_date[that.data.choose_times.date_id].time[that.data.choose_times.time_id];
      }
    }
    if (postData.disp == 2 && that.data.create_order.store_id <= 0) {
      util.showModal('提示', '当前商品没有自提的门店', function() {
        util.jumpLink('pages/index/index');
      });
    }
    //判断支付方式
    var pay_type = {},
      pay_type_len = 0;
    if (!this.data.create_order.pay_code) this.data.create_order.pay_code = 'wxpay';

    for (var i in postData.payment) {
      if (postData.payment[i].pay_code == 'wxpay' && postData.payment[i].enabled == 1) {
        pay_type.wxpay = true;
        ++pay_type_len;
        this.data.pay_type_checked = 'wxpay';
      }
      if (postData.payment[i].pay_code == 'cod' && postData.payment[i].enabled == 1) {
        pay_type.cod = true;
        if (pay_type_len == 0) this.data.pay_type_checked = 'cod';
        ++pay_type_len;
      }
      if (postData.payment[i].pay_code == 'score' && postData.payment[i].enabled == 1) {
        pay_type.score = true;
      }
      if (postData.payment[i].pay_code == 'balance' && postData.payment[i].enabled == 1) {
        pay_type.balance = true;
      }
    }

    if (postData.instead_pay && parseInt(postData.instead_pay.status) == 1) {
      pay_type.instead_pay = true;
      if (pay_type_len == 0) this.data.pay_type_checked = 'instead_pay';
      ++pay_type_len;
    }
    // 商品
    for (var i in postData.goods) {
      if (post_type == 'integral') {
        postData.goods[i].money = parseFloat(postData.goods[i].goods_price - postData.goods[i].goods_score / 100).toFixed(2);
      }
      if ( postData.goods[i].purchase_price_id > 0 ) {
        that.setData({
          hasPurchase: 1
        })
      }
      if ( postData.goods[i].stra_id > 0 ){
        that.setData({
          hasFullprice: 1
        })
      }
      if(post_type == 'package_sale'){
        that.setData({
          hasPackage: 1
        })
      }
    }
    that.setData({
      pay_type: pay_type,
      pay_type_len: pay_type_len,
      pay_type_checked: this.data.pay_type_checked,
      instead_pay_show: this.data.instead_pay_show,
      goods: postData.goods,
      address: postData.address,
      deliver_config: that.data.deliver_config,
      choose_store: that.data.choose_store,
      date_info: postData.pickup_date, //自提时间
      can_shipping: postData.can_shipping
    })
    // 会员卡
    var card_array = postData.member_card_list;
    for (var i in card_array) {
      card_array[i].src_yes = imageUrl + 'image/wxapp/selected.png';
      card_array[i].src_no = imageUrl + 'image/wxapp/icon_rectangle.png';
      card_array[i].card_src = imageUrl + 'image/wxapp/icon_rectangle.png';
      card_array[i].shop_logo = postData.shop_logo;
      if (!postData.shop_logo) {
        card_array[i].shop_logo = imageUrl + 'image/wxapp/shop_logo_default.png';
      }
      if (card_array[i].bg_type == 1) {
        card_array[i].bg = "url('" + imageUrl + card_array[i].bg_img + "') no-repeat";
      } else {
        card_array[i].bg = card_array[i].bg_color;
      }
      if (i.toString() == postData.member_card_no.toString()) {
        card_array[i].card_src = 1;
        that.setData({
          card_choose_name: card_array[i].card_name
        })
      }
      if (card_array[i].score) {
        card_array[i].buy_score = JSON.parse(card_array[i].buy_score);
        if (card_arr[i].buy_score[0].value == 0) {
          var arrlen = card_array[i].buy_score.length;
          if (card_array[i].buy_score[arrlen - 1].each_money) {
            card_array[i].buy_score1 = card_array[i].buy_score.slice(1, arrlen - 1);
          } else {
            card_array[i].buy_score1 = card_array[i].buy_score.slice(1);
          }
        }
        if (card_array[i].buy_score[0].value == 1) {
          card_array[i].buy_score1 = card_array[i].buy_score[card_array[i].buy_score.length - 1];
        }
      }
      if (card_array[i].expire_time) {
        card_array[i].expire_time = card_array[i].expire_time.substring(0, 10);
      }
    }
    that.setData({
      card_array: card_array
    })

    // 优惠券
    if (postData.coupon_list) {
      var coupon_array = [];
      coupon_array.push({
        act_desc: '不使用优惠券'
      });
      var m = 0;
      var coupon_choose_index = 0;
      // var discount_index;
      for (var i in postData.coupon_list) {
        m++;
        coupon_array.push(postData.coupon_list[i]);
        if (i == postData.coupon_sn) {
          coupon_choose_index = m;
        }
      }
      that.setData({
        coupon_choose_index: coupon_choose_index,
        coupon_array: coupon_array
      })
    }

    // 积分抵扣
    var score_ratio = that.data.user_money.score_ratio;
    if (that.data.score_max_discount * score_ratio >= postData.score) {
      that.data.user_money.score_enable = parseInt(postData.score) - parseInt(postData.score) % score_ratio;
    } else if (parseInt(that.data.score_max_discount * score_ratio) >= score_ratio) {
      that.data.user_money.score_enable = parseInt(that.data.score_max_discount * score_ratio) - parseInt(that.data.score_max_discount * score_ratio) % score_ratio;
    } else {
      that.data.user_money.score_enable = 0;
    }
    that.setData({
      pay_score: 0,
      pay_yue: 0,
      pay_card: 0,
      score_pay_control: 0,
      account_pay_control: 0,
      pay_click_type: 0,
      post_type: post_type,
      user_money: that.data.user_money,
      create_order: that.data.create_order,
      member_card_input: '',
      score_money_input: that.data.user_money.score_enable,
      user_account_input: '',
      invoice_module: postData.invoice_switch == 0 ? true : false // 发票
    })

    // 默认支付填充
    if (postData.card_first == '1') {
      member_card_input = that.data.create_order.money_paid - that.data.user_money.member_card_money > 0 ? that.data.user_money.member_card_money : that.data.create_order.money_paid;
      new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
      member_card_balance = parseFloat(member_card_input).toFixed(2);
      that.setData({
        member_card_input: member_card_input > 0 ? member_card_input : '',
        pay_card: 1,
        'create_order.money_paid': new_money_paid,
        'create_order.member_card_balance': member_card_balance,
      })
    }
    if (postData.balance_first == '1' && that.data.user_money.account != null) {
      var count = 0;
      user_account_input = that.data.create_order.money_paid - that.data.user_money.account > 0 ? that.data.user_money.account : that.data.create_order.money_paid;
      if (parseFloat(user_account_input) == 0) {
        count++;
      }
      if (count == 0) {
        new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
        account_discount = parseFloat(user_account_input).toFixed(2);
        that.setData({
          user_account_input: user_account_input >= 0 ? user_account_input : '',
          pay_yue: 1,
          'create_order.money_paid': new_money_paid,
          'create_order.account_discount': account_discount,
        })
      }

    }
    if (postData.score_first == '1' && post_type != 'integral' && that.data.user_money.score_enable != NaN) {
      var score_en = +(parseFloat(that.data.user_money.score_enable / that.data.user_money.score_ratio).toFixed(2));
      var score_nu = +(parseFloat(that.data.create_order.score_pay_num / that.data.user_money.score_ratio).toFixed(2));
      var count = 0;
      if (score_en <= parseFloat(that.data.create_order.money_paid)) {
        score_money_input = score_en;
      } else if ((score_en > parseFloat(that.data.create_order.money_paid)) && (score_nu <= parseFloat(that.data.create_order.money_paid))) {
        score_money_input = that.getMultiple(that.data.create_order.money_paid);
      } else {
        count++;
      }
      if (count == 0) {
        new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(score_money_input)).toFixed(2);
        score_discount = score_money_input;
        that.setData({
          score_money_input: score_money_input >= 0 ? score_money_input : '',
          pay_score: 1,
          'create_order.money_paid': new_money_paid,
          'create_order.score_discount': score_discount,
        })
      }
    }
    that.check_store_goods();
  },
  check_store_goods: function() {
    this.data.not_pick_tip = '';
    if (this.data.create_order.store_id <= 0) {
      if (!this.data.can_shipping.all_goods) {
        for (var i in this.data.goods) {
          if (this.data.goods[i].is_gift == 1 && !this.data.can_shipping[this.data.goods[i].product_id]) {
            this.data.not_pick_tip += '"' + this.data.goods[i].goods_name + ':' + this.data.goods[i].goods_attr + '"';
          }
        }
      }
      return true;
    }

    var gift_store_goods = this.data.gift_store_goods[this.data.create_order.store_id] || [];
    for (var i in this.data.goods) {
      if (this.data.goods[i].is_gift == 0 || gift_store_goods.indexOf(this.data.goods[i].product_id) > -1) {
        this.data.goods[i].is_can_pick = 1;
      } else {
        this.data.goods[i].is_can_pick = -1;
        this.data.not_pick_tip += '"' + this.data.goods[i].goods_name + ':' + this.data.goods[i].goods_attr + '"';
      }
    }
    this.setData({
      not_pick_tip: this.data.not_pick_tip ? this.data.not_pick_tip : '',
      goods: this.data.goods
    })
  },
  check_score: function() {
    var score_ratio = this.data.user_money.score_ratio;
    if (this.data.score_max_discount * score_ratio >= this.data.user_money.score) {
      this.data.user_money.score_enable = parseInt(this.data.user_money.score) - parseInt(this.data.user_money.score) % score_ratio;
    } else if (parseInt(this.data.score_max_discount * score_ratio) >= score_ratio) {
      this.data.user_money.score_enable = parseInt(this.data.score_max_discount * score_ratio) - parseInt(this.data.score_max_discount * score_ratio) % score_ratio;
    } else {
      this.data.user_money.score_enable = 0;
    }
    var max_score_enable = parseInt(this.data.create_order.money_paid * score_ratio);
    if (!isNaN(parseInt(this.data.score_money_input))) max_score_enable += parseInt(this.data.score_money_input);
    if (this.data.user_money.score_enable > max_score_enable) {
      this.data.user_money.score_enable = max_score_enable;
    }
    this.setData({
      user_money: this.data.user_money
    })

  },
  // 快递
  show_kuaidi: function() {
    var create_order_data = JSON.parse(JSON.stringify(this.data.create_order));
    create_order_data.store_id = -1;
    this.loadPage(create_order_data);
  },
  // 自提
  show_ziti: function() {
    var create_order_data = JSON.parse(JSON.stringify(this.data.create_order));
    create_order_data.store_id = this.data.choose_store.store_id;
    this.loadPage(create_order_data);
  },
  // 地址
  addAddress: function() {
    var that = this;
    wx.chooseAddress({
      success: function(res) {
        console.log(res);
        var order_goods = JSON.stringify(that.data.goods);
        var create_order_data = JSON.parse(JSON.stringify(that.data.create_order));;
        util.api('/api/wxapp/address/choose', function(e) {
          // console.log(e);
          create_order_data.address_id = e.content.address_id;
          that.loadPage(create_order_data);
        }, {
          address: JSON.stringify(res),
          orderGoods: order_goods,
          post_type: post_type,
          create_order: JSON.stringify(that.data.create_order),
          goods_type: that.data.goods_type
        }, '', true);
      },
      fail: function() {
        wx.getSetting({
          success: function(res) {
            if (!res.authSetting['scope.address']) {
              util.showModal('是否打开设置页面', '需要获取您的位置信息，请到小程序的设置页面打开授权', function() {
                wx.openSetting({
                  success: function(res) {

                  }
                })
              })
            }
          }
        })
      }
    })
  },
  // 门店
  storeShow: function() {
    var old_id = this.data.choose_store.index || 0;
    this.data.store_array[old_id].store_radio = this.data.store_array[old_id].choose_yes;
    this.setData({
      store_array: this.data.store_array,
      storeMode: false
    })
  },
  storeCancel: function() {
    this.setData({
      storeMode: true
    })
  },
  storeConfirm: function() {
    this.data.create_order.store_id = this.data.choose_store.store_id;
    this.loadPage(this.data.create_order);
    this.setData({ storeMode: true })
  },
  chooseStore: function(e) {
    var that = this;
    var id = e.currentTarget.dataset.id;
    var old_id = that.data.choose_store.index;
    that.data.store_array[old_id].store_radio = that.data.store_array[old_id].choose_no;
    that.data.choose_store.index = id;
    that.data.choose_store.store_id = that.data.store_array[id].store_id;
    that.data.choose_store.store_name = that.data.store_array[id].store_name;
    that.data.choose_store.address = that.data.store_array[id].address;
    that.data.choose_store.dis = that.data.store_array[id].dis;
    that.data.store_array[id].store_radio = that.data.store_array[id].choose_yes;
    this.setData({
      store_array: that.data.store_array
    })
  },
  // 自提时间
  timeShow: function() {
    this.setData({
      timesMode: false,
      choose_times: this.data.choose_times
    })
  },
  timeHidden: function() {
    this.setData({
      timesMode: true
    })
  },
  dataClick: function(e) {
    var key = e.currentTarget.dataset.key;
    this.data.choose_times.new_date_id = key;
    this.setData({
      choose_times: this.data.choose_times
    })
  },
  selectedHour: function(e) {
    var key = e.currentTarget.dataset.time_id;
    this.data.choose_times.new_time_id = key;
    this.setData({
      choose_times: this.data.choose_times
    })
  },
  timeConfirm: function() {
    var choose_times = this.data.choose_times;
    if (choose_times.new_date_id) choose_times.date_id = choose_times.new_date_id;
    if (choose_times.new_time_id) choose_times.time_id = choose_times.new_time_id;
    this.data.create_order.date = this.data.date_info[choose_times.date_id].date;
    this.data.create_order.time = this.data.date_info[choose_times.date_id].time[choose_times.time_id];
    this.setData({
      timesMode: true,
      create_order: this.data.create_order
    })
  },
  // 支付方式
  radioChange: function(e) {
    if (e.detail.value == 'instead_pay') {
      this.data.create_order.order_pay_way = 2;
      this.data.instead_pay_show = true;

    } else {
      this.data.create_order.pay_code = e.detail.value;
      this.data.create_order.order_pay_way = 0;
      this.data.instead_pay_show = false;
    }
    this.data.pay_type_checked = e.detail.value;
    this.setData({
      instead_pay_show: this.data.instead_pay_show
    })
  },
  // 支付弹窗
  payClick: function(e) {
    var that = this;
    var type = e.currentTarget.dataset.type;
    if (type == 'score') {
      var score_en = +(parseFloat(that.data.user_money.score_enable / that.data.user_money.score_ratio).toFixed(2));
      var score_nu = +(parseFloat(that.data.create_order.score_pay_num / that.data.user_money.score_ratio).toFixed(2));
      if (score_en <= parseFloat(that.data.create_order.money_paid)) {
        score_money_input = score_en * 100;
      } else if ((score_en > parseFloat(that.data.create_order.money_paid)) && (score_nu <= parseFloat(that.data.create_order.money_paid))) {
        score_money_input = that.getMultiple(parseFloat(that.data.create_order.money_paid)) * 100;
      } else {
        score_money_input = 0;
      }
      that.setData({
        pay_click_type: 1,
        score_money_input: score_money_input,
      })
    } else if (type == 'yue') {
      that.setData({
        pay_click_type: 2,
      })
    } else {
      that.setData({
        pay_click_type: 3,
      })
    }
    that.setData({
      payMode: false,
      canClick: true,
      prompt_message: '',
    })
  },
  payCancel: function(e) {
    var that = this;
    that.setData({
      payMode: true
    })
  },
  // 会员卡
  cardClick: function(e) { //会员卡弹框显示
    this.setData({
      cardMode: false
    })
  },
  cardCancel: function(e) { //关闭会员卡弹框
    this.setData({
      cardMode: true
    })
  },
  checkCancelScore: function(e) {
    new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.score_discount);
    this.setData({
      'create_order.money_paid': new_money_paid,
      score_pay_control: 0,
      pay_score: 0,
      prompt_message: '',
      score_money_input: this.data.user_money.score_enable,
      'create_order.score_discount': 0,
    })
  },
  checkCancelYue: function(e) {
    new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.account_discount);
    this.setData({
      'create_order.money_paid': new_money_paid,
      account_pay_control: 0,
      pay_yue: 0,
      prompt_message: '',
      user_account_input: '',
      'create_order.account_discount': 0,
    })
  },
  checkCancelCard: function(e) {
    new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.member_card_balance);
    this.setData({
      'create_order.money_paid': new_money_paid,
      card_account_pay_control: 0,
      pay_card: 0,
      prompt_message: '',
      member_card_input: '',
      'create_order.member_card_balance': 0,
    })
  },
  chooseCard: function(e) { //选择会员卡
    var card_choose_name = e.currentTarget.dataset.name;
    var card_no = e.currentTarget.dataset.card_no;
    var id = e.currentTarget.dataset.id;
    var card_arr = this.data.card_array;
    for (var i in card_arr) {
      if (i == id) {
        if (card_arr[i].card_src == 1) {
          card_arr[i].card_src = card_arr[i].src_no;
          card_choose_name = false;
          card_no = 'no_use';
          this.data.create_order.only_show_card_list = true;
        } else {
          card_arr[i].card_src = 1;
          this.data.create_order.only_show_card_list = false;
        }
      } else {
        card_arr[i].card_src = card_arr[i].src_no;
      }
    }
    this.data.choose_card.card_choose_name = card_choose_name;
    this.data.choose_card.card_no = card_no;
    this.setData({
      card_array: card_arr
    })
  },
  payConfirm: function(e) {
    var that = this;
    var type = that.data.pay_click_type;
    var input;
    var prompt_message;
    if (type == 1) {
      that.score_money(e);
      input = that.data.score_money_input;
      prompt_message = that.data.prompt_message;
      if (prompt_message != '') return false;
      that.definePay();
      that.setData({
        pay_score: 1,
      })
    } else if (type == 2) {
      that.user_account(e);
      input = that.data.user_account_input;
      prompt_message = that.data.prompt_message;
      if (prompt_message != '') return false;
      that.definePay();
      that.setData({
        pay_yue: 1,
      })
    } else {
      that.member_card(e);
      input = that.data.member_card_input;
      prompt_message = that.data.prompt_message;
      if (prompt_message != '') return false;
      that.definePay();
      that.setData({
        pay_card: 1,
      })
    }
    that.setData({
      payMode: true,
    })
  },
  cardConfirm: function(e) {
    this.setData({
      cardMode: true,
      card_choose_name: this.data.choose_card.card_choose_name
    });
    var create_order_data = JSON.parse(JSON.stringify(this.data.create_order));
    var member_card_arr = Object.keys(this.data.card_array);
    if (member_card_arr.length == 1 && this.data.card_array[member_card_arr[0]].card_type == 1) {
      create_order_data.card_no = this.data.card_array[member_card_arr[0]].card_no;
    } else {
      create_order_data.card_no = this.data.choose_card.card_no;
    }
    this.checkCancelScore();
    this.checkCancelYue();
    this.checkCancelCard();
    this.loadPage(create_order_data);
  },
  member_card: function(e) {
    if (e.detail.value) {
      member_card_input = e.detail.value ? e.detail.value : 0;
    } else {
      if (this.data.member_card_input == '') {
        member_card_input = this.data.create_order.money_paid - this.data.user_money.member_card_money > 0 ? this.data.user_money.member_card_money : this.data.create_order.money_paid;
      } else {
        member_card_input = this.data.member_card_input;
      }
    }
    if (isNaN(member_card_input) || !isNaN(member_card_input) && member_card_input < 0) {
      var tishi1 = "请输入正确的余额";
      this.setData({
        member_card_input: '',
        prompt_message: tishi1,
      })
      return false;
    } else if (member_card_input == 0) {
      var tishi1 = "会员卡余额不可填写0";
      this.setData({
        member_card_input: '',
        prompt_message: tishi1,
      })
      return false;
    } else {
      var msg = '';
      if (parseInt(member_card_input) > parseInt(this.data.user_money.member_card_money)) {
        msg = "最多可以使用会员卡余额：" + this.data.user_money.member_card_money + "元";
      }
      new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
      if (new_money_paid < 0) {
        msg = '使用余额不得超过订单金额';
      }
      if (msg != '') {
        this.setData({
          prompt_message: msg,
          canClick: false,
        })
      }
      this.data.create_order.member_card_balance = parseFloat(member_card_input).toFixed(2);
      this.setData({
        member_card_input: member_card_input > 0 ? member_card_input : '',
        create_order: this.data.create_order,
        card_account_pay_control: 1,
      })
    }
  },
  // 优惠券
  bindPicChange: function(e) {
    var dis_index = e.detail.value;
    var create_order_data = JSON.parse(JSON.stringify(this.data.create_order));
    if (dis_index == 0) {
      create_order_data.coupon_sn = 'use_no';
    } else {
      create_order_data.coupon_sn = this.data.coupon_array[dis_index].coupon_sn;
    }
    this.loadPage(create_order_data);
  },
  // 积分
  score_money: function(e) {
    if (e.detail.value) {
      score_money_input = e.detail.value ? e.detail.value : 0;
    } else {
      score_money_input = this.data.score_money_input;
    }
    if (parseInt(this.data.user_money.score_enable) < this.data.create_order.score_pay_num) {
      var tishi1 = '当前积分最多使用' + this.data.user_money.score_enable + '无法达到系统要求，无法使用';
      this.setData({
        score_money_input: '',
        prompt_message: tishi1,
      })
      return false;
    }
    var msg = '';
    if (parseInt(score_money_input) > parseInt(this.data.user_money.score_enable)) {
      msg = "最多可以使用：" + parseInt(this.data.user_money.score_enable) + "积分";
    } else if (parseInt(score_money_input) < parseInt(this.data.create_order.score_pay_num)) {
      msg = '积分支付最小使用' + this.data.create_order.score_pay_num;
    } else {
      score_money_input = this.getMultiple(score_money_input);

    }

    if (score_money_input > 0) {
      var new_score_discount = parseFloat(score_money_input / this.data.user_money.score_ratio).toFixed(2);
    } else {
      var new_score_discount = 0;
    }

    new_money_paid = (parseFloat(this.data.create_order.money_paid) - new_score_discount).toFixed(2);
    if (new_money_paid < 0) {
      msg = '积分抵扣金额不得大于订单金额';
    }

    if (msg != '') {
      this.setData({
        prompt_message: msg,
        canClick: false,
      })
    }
    this.data.create_order.score_discount = parseFloat(score_money_input / this.data.user_money.score_ratio).toFixed(2);
    this.setData({
      create_order: this.data.create_order,
      score_pay_control: 1,
      score_money_input: (score_money_input <= 0) ? '' : score_money_input,
    })

  },
  definePay: function() {
    this.data.create_order.money_paid = new_money_paid > 0 ? new_money_paid : 0;
    this.setData({
      create_order: this.data.create_order,
    })
  },

  user_account: function(e) {
    if (e.detail.value) {
      user_account_input = e.detail.value ? e.detail.value : 0;
    } else {
      if (this.data.user_account_input == '') {
        user_account_input = this.data.create_order.money_paid - this.data.user_money.account > 0 ? this.data.user_money.account : this.data.create_order.money_paid;
      } else {
        user_account_input = this.data.user_account_input;
      }
    }
    if (isNaN(user_account_input) || !isNaN(user_account_input) && user_account_input < 0) {
      var tishi1 = "请输入正确的余额";
      this.setData({
        user_account_input: '',
        prompt_message: tishi1,
      })
      return false;
    } else if (user_account_input == 0) {
      var tishi1 = "余额需大于0";
      this.setData({
        user_account_input: '',
        prompt_message: tishi1,
      })
      return false;
    } else {
      var msg = '';
      if (parseInt(user_account_input) > parseInt(this.data.user_money.account)) {
        msg = "最多可以使用：" + this.data.user_money.account + "余额";
      }
      new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
      if (new_money_paid < 0) {
        msg = '使用余额不得超过订单金额';
      }
      if (msg != '') {
        this.setData({
          prompt_message: msg,
          canClick: false,
        })
      }
      this.data.create_order.account_discount = parseFloat(user_account_input).toFixed(2);
      this.setData({
        user_account_input: user_account_input <= 0 ? '' : user_account_input,
        create_order: this.data.create_order,
        account_pay_control: 1,
      });
      this.check_score();
    }
  },

  // 发票
  chooseInvoice: function() {
    var that = this;
    wx.chooseInvoiceTitle({
      success(res) {
        util.api('/api/wxapp/invoice/choose', function(e) {
          that.data.create_order.invoice_id = e.content;
          that.setData({
            invoice_title: res.title
          })
        }, {
          invoice_info: JSON.stringify(res)
        })
      },
      fail() {
        util.showModal('', "获取发票信息失败");
      }
    })
  },

  // 提交订单
  order_confirm: function(e) {
    var that = this;
    var create_order = that.data.create_order;
    create_order.open_id = util.getCache('openid');
    create_order.form_id = e.detail.formId;

    if (that.data.total_micro_second < 1) {
      util.showModal('提示', '订单结算时间已经截至，请重新下单', function() {
        wx.navigateBack({
          delta: 1,
          fail: function(e) {
            util.navigateTo({
              url: '/pages/index/index',
            })
          }
        })
      }, false);
      return false;
    }
    if (this.data.create_order.service_terms == 1) {
      if (this.data.create_order.service_choose == 0) {
        util.showModal('提示', '请确认并同意《' + that.data.create_order.service_name + '》', function() {}, false);
        return false;
      }
    }


    if (create_order.address_id <= 0 && create_order.scan_store_id <= 0) {
      if (create_order.store_id > 0) {
        var show_msg = '请选择提货人';
      } else {
        var show_msg = '请选择收货地址';
      }
      util.showModal('提示', show_msg);
      return false;
    }
    if (create_order.id_card == "" && shop_flag == 1) {
      util.showModal("提示", "请如实填写订单收货信息，务必保证收货人与收货人身份证保持一致，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      return false;
    }
    if (create_order.true_name == "" && shop_flag == 1) {
      util.showModal("提示", "请如实填写订单收货信息，务必保证收货人与收货人身份证保持一致，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      return false;
    }
    if (create_order.order_real_name == undefined && order_real_name == 1) {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      return false;
    }
    if (create_order.order_cid == undefined && order_cid == 1) {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      return false;
    }
    if (create_order.consignee_real_name == undefined && consignee_real_name == 1) {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      return false;
    }
    if (create_order.consignee_cid == undefined && consignee_cid == 1) {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      return false;
    }
    if (create_order.custom == undefined && custom == 1) {
      util.showModal("提示", "请如实填写" + custom_title + "。");
      return false;
    }
    if (e.target.dataset != undefined && e.target.dataset.instead_pay_num != undefined) {
      this.data.create_order.instead_pay_num = e.target.dataset.instead_pay_num;
      this.setData({
        insmode: true
      })
    }
    if (that.data.create_order.order_pay_way == 2 && that.data.create_order.instead_pay_num == 2) {
      this.setData({
        insmode: false
      })
      return false;
    }
    if (must_content.length > 0) {
      that.data.create_order.must_content = must_content;
    }
    if (that.data.pay_yue != 1) {
      that.setData({
        'create_order.account_discount': 0,
      })
    }
    if (that.data.pay_score != 1 && post_type != 'integral') {
      that.setData({
        'create_order.score_discount': 0,
      })
    }
    if (that.data.pay_card != 1) {
      that.setData({
        'create_order.member_card_balance': 0,
      })
    }
    var order_info = JSON.stringify(create_order);
    if (that.data.is_submit) return false;

    if (that.data.pick_can_order == false && that.data.not_pick_tip != '') {
      var order_tip;
      if (that.data.store_id > 0) {
        order_tip = "您订单中赠品" + that.data.not_pick_tip + "不支持在已选门店自提，请确认后下单"
      } else {
        order_tip = "您订单中赠品" + that.data.not_pick_tip + "不支持配送，请确认后下单"
      }
      util.showModal('提示', order_tip, function() {
        that.data.pick_can_order = true;
        that.order_confirm(e);
      }, true, '再看看', '继续下单');
      return false;
    }

    that.data.is_submit = true;

    util.api('/api/wxapp/order/toCheckout', function(res) {
      if (res.error == 0 || res.code == 0) {
        var order_sn = res.content.order_sn;
        if (that.data.create_order.order_pay_way == 2) {
          util.redirectTo({
            url: '/pages/insteadinfo/insteadinfo?order_sn=' + order_sn
          })
          return true;
        }
        var group_id = res.content.group_id != undefined ? res.content.group_id : 0;
        if (create_order.pay_code == 'wxpay' && typeof(res.content.timeStamp) != 'undefined') {
          wx.requestPayment({
            'timeStamp': res.content.timeStamp,
            'nonceStr': res.content.nonceStr,
            'package': res.content.package,
            'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
            'paySign': res.content.paySign,
            'success': function(res) {
              util.toast_success('支付成功');
              if (post_type == 'cart' && that.data.create_order.scan_store_id > 0) {
                util.redirectTo({
                  url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn + "&coupon=1",
                })
              } else if (post_type == 'group_draw') {
                util.redirectTo({
                  url: '/pages/pinlotteryinfo/pinlotteryinfo?group_draw_id=' + that.data.create_order.group_draw_id + "&goods_id=" + goods_ida + "&group_id=" + group_id,
                })
              } else if (post_type == 'give_gift') {
                util.jumpLink('/pages1/presentdetail/presentdetail?order_sn=' + order_sn, 'redirectTo');
              } else {
                util.redirectTo({
                  url: '/pages/payment/payment?order_sn=' + order_sn + "&coupon=1",
                })
              }
            },
            'fail': function(res) {
              util.redirectTo({
                url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn + "&coupon=1",
              })
            },
            'complete': function(res) {}
          });
        } else {
          if (post_type == 'cart' && that.data.create_order.scan_store_id > 0) {
            util.redirectTo({
              url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn + "&coupon=1",
            })
          } else if (post_type == 'group_draw') {
            util.redirectTo({
              url: '/pages/pinlotteryinfo/pinlotteryinfo?group_draw_id=' + that.data.create_order.group_draw_id + "&goods_id=" + goods_ida + "&group_id=" + group_id,
            })
          } else if (post_type == 'give_gift') {
            util.jumpLink('/pages1/presentdetail/presentdetail?order_sn=' + order_sn, 'redirectTo');
          } else {
            util.redirectTo({
              url: '/pages/payment/payment?order_sn=' + order_sn + "&coupon=1",
            })
          }
        }
      } else {
        util.showModal('提示', res.message, function() {
          that.loadPage(that.data.old_create_order);
        }, false);
      }
      that.data.is_submit = false;
    }, {
      order: order_info,
      post_type: post_type
    }, '', true);
  },
  // 倒计时
  setTimeInterval: function(that) {
    that.data.set_time_1 = setInterval(function() {
      if (that.data.total_micro_second > 0) {
        that.data.total_micro_second -= 1;
        that.setData({
          clock: util.dateformat(that.data.total_micro_second)
        });
      } else {
        clearInterval(that.data.set_time_1);
        that.setData({
          clock: ""
        });
      }
    }, 1000)
  },
  // 时间格式化输出，如25:19 86。每10ms都会调用一次
  dateformat: function(micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    // 分钟位 
    var hour = Math.floor(micro_second / 3600);
    var min = Math.floor((micro_second % 3600) / 60);
    if (hour < 10) hour = '0' + hour;
    if (min < 10) min = '0' + min;
    // 秒位
    var sec = second % 60;
    if (sec < 10) sec = '0' + sec;
    return hour + "时" + min + "分" + sec + "秒";
  },
  // 定金膨胀
  ruleCancel: function() {
    this.setData({
      ruleMode: true
    })
  },
  ruleShow: function() {
    this.setData({
      ruleMode: false
    })
  },
  preActionSheetChange: function() {
    this.setData({
      ruleMode: true
    })
  },
  close_cou: function() {
    this.setData({
      insmode: true
    })
  },
  // 下单信息的判断
  must_checkName1: function(e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      this.data.create_order.order_real_name = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    } else if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 40) {
      util.showModal("提示", "真实姓名长度不得多于40字");
      this.data.create_order.order_real_name = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    } else {
      this.data.create_order.order_real_name = e.detail.value.replace(/^\s+|\s+$/g, '');
    }
  },
  must_checkName2: function(e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      this.data.create_order.consignee_real_name = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    } else if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 40) {
      util.showModal("提示", "真实姓名长度不得多于40字");
      this.data.create_order.consignee_real_name = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    } else {
      this.data.create_order.consignee_real_name = e.detail.value.replace(/^\s+|\s+$/g, '');
    }
  },
  must_checkIDcard1: function(e) {
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      this.data.create_order.order_cid = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 18) {
      util.showModal("提示", "身份证号长度不超过18位");
      this.data.create_order.order_cid = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    if (!(re.test(e.detail.value))) {
      util.showModal("提示", "请输入正确的身份证号");
      this.data.create_order.order_cid = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    this.data.create_order.order_cid = e.detail.value.replace(/^\s+|\s+$/g, '');

  },
  must_checkIDcard2: function(e) {
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (e.detail.value.replace(/^\s+|\s+$/g, '') == "") {
      util.showModal("提示", "请如实填写订单收货信息，务必保证正确，如因为订单信息错误出现退单情况，造成的物流费用将由收货人自行承担。");
      this.data.create_order.consignee_cid = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 18) {
      util.showModal("提示", "身份证号长度不超过18位");
      this.data.create_order.consignee_cid = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    if (!(re.test(e.detail.value))) {
      util.showModal("提示", "请输入正确的身份证号");
      this.data.create_order.consignee_cid = e.detail.value.replace(/^\s+|\s+$/g, '');
      return false;
    }
    this.data.create_order.consignee_cid = e.detail.value.replace(/^\s+|\s+$/g, '');

  },
  must_custom: function(e) {
    if (e.detail.value.replace(/^\s+|\s+$/g, '').length > 50 && shop_flag == 1) {
      util.showModal("提示", "填写长度不得超过50字");
      this.data.create_order.custom_title = custom_title;
      this.data.create_order.custom = e.detail.value;
      return false;
    }
    this.data.create_order.custom_title = custom_title;
    this.data.create_order.custom = e.detail.value;
  },
  // 取整
  getMultiple: function (num) {
    if (parseFloat(num) < 100) {
      num = parseInt(num);
      return num;
    }
    return parseInt(num / 100) * 100
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var that = this;
    if (that.data.total_micro_second > 0) {
      clearInterval(that.data.set_time_1); //停止
      that.setData({
        clock: util.dateformat(that.data.total_micro_second),
      });
      that.setTimeInterval(that);
    } else {
      that.setData({
        clock: '',
      });
    }
  },
  toRule: function() {
    util.jumpToWeb('/wxapp/sercice/ServiceDocument');
  },
  checkService: function() {
    var that = this;
    var chooseFlag;
    if (that.data.create_order.service_choose == 1) {
      chooseFlag = 0
    } else {
      chooseFlag = 1
    }
    that.setData({
      'create_order.service_choose': chooseFlag
    })
  }
})