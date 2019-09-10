// APPOINTMENT.JS 2018.03.07
const util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var date_info; 
var opt = {};
var name;
var mobile;
var is_delete = 0;
var store_id;
var checkbox_no = imageUrl + '/image/admin/select.png';
var user_account_input;
var new_money_paid;
var member_card_input;
var member_card_balance;
var account_pay_control = 0;
var card_account_pay_control = 0;
var account_discount;
global.wxPage({
  data: {
    id: 0,
    key: 0,
    keytwo: 0,
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    img_addr: imageUrl + '/image/wxapp/store_address.png',
    img_arrow: imageUrl + '/image/wxapp/backward_right.png',
    img_success: imageUrl + '/image/wxapp/con_btn_success.png',
    img_iconsel: imageUrl + '/image/wxapp/selected.png',
    prd_img: imageUrl + "image/wxapp/address.png",
    img_store: imageUrl + "image/wxapp/address.png",
    img_person: imageUrl + "image/wxapp/appoint_man.png",
    img_cancel: imageUrl + "image/wxapp/cancel.png",
    img_phone: imageUrl + "image/wxapp/card_phone.png",
    img_time: imageUrl + "image/wxapp/appoint_time.png",
    img_man: imageUrl + "image/wxapp/tech_man.png",
    index: "1",
    img_service: imageUrl + 'image/wxapp/icon_service.png',
    icon: app.globalData.imageUrl + 'image/wxapp/icon_rectangle.png',
    is_delete: 0,
    user_money:{},
    choose_card: {},
    card_no:1,
    create_order:{},
    imageUrl: app.globalData.imageUrl,
    cardMode: true,
    payMode: true,
    canClick: true,
    account_pay_control: 0,
    pay_click_type: 0,
    card_account_pay_control: 0,
    pay_score: 0,
    pay_yue: 0,
    pay_card: 0,
    prompt_message: "",
    checkbox_no: imageUrl + '/image/admin/select.png'
  },
  onLoad: function(option) {
    if (!util.check_setting(option)) return;
    opt = JSON.parse(option.date);
    opt.subscriber = "";
    opt.mobile = "";
    opt.user_id = util.getCache('user_id');
    store_id = opt.store_id;
    this.loadPage(this.data.card_no);
  },
  loadPage: function (card_no){
    var that = this;
    util.api('/api/wxapp/service/orderInfo', function (res) {
      var postData = res.content;
      if (postData.store.is_delete == 1 || postData.serviceinfo.is_delete == 1) {
        is_delete = 1;
        util.showModal('提示', '该服务已删除', function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        that.setData({
          is_delete: is_delete,
        })
        return;
      }
      opt.store_id = postData.store.store_id;
      postData.serviceinfo.service_img = JSON.parse(postData.serviceinfo.service_img)[0];
      postData.store.store_imgs = JSON.parse(postData.store.store_imgs)[0];

      that.data.create_order.money_paid = postData.serviceinfo.service_subsist;
      that.data.create_order.account_discount = 0;
      that.data.create_order.member_card_balance = 0;
      if (postData.userifo) {
        that.setData({
          name: postData.userifo.subscriber,
          mobile: postData.userifo.mobile
        })
        opt.subscriber = postData.userifo.subscriber;
        opt.mobile = postData.userifo.mobile;
      }
      if (opt.technician_name) {
        that.setData({
          tech_name: opt.technician_name
        })
      }
  
      that.data.user_money.account = postData.account;
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
          that.data.user_money.member_card_money = parseFloat(card_array[i].money);
          that.setData({
            card_choose_name: card_array[i].card_name,
            member_card_no: card_array[i].card_no
          })
        }
        if (card_array[i].expire_time) {
          card_array[i].expire_time = card_array[i].expire_time.substring(0, 10);
        }
      }
      that.setData({
        date: opt.day,
        time: opt.hour,
        store: postData.store,
        service: postData.serviceinfo,
        card_array: card_array,
        user_money: that.data.user_money,
        create_order:that.data.create_order,
        member_card_input: '',
        user_account_input: '',
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
        if (parseInt(user_account_input) == 0) {
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
    }, {
        service_id: opt.service_id,
        store_id: store_id,
        card_no: card_no,      
      })
  },

  cardConfirm: function (e) {
    this.setData({
      cardMode: true,
    });
    var card_no = {};
    var member_card_arr = Object.keys(this.data.card_array);
    if (member_card_arr.length == 1 && this.data.card_array[member_card_arr[0]].card_type == 1) {
      card_no.card_no = this.data.card_array[member_card_arr[0]].card_no;
    } else {
      card_no.card_no = this.data.choose_card.card_no;
    }
    this.checkCancelYue();
    this.checkCancelCard();
    this.loadPage(card_no.card_no);
  },
  chooseCard: function (e) { //选择会员卡
    var card_choose_name = e.currentTarget.dataset.name;
    var card_no = e.currentTarget.dataset.card_no;
    var id = e.currentTarget.dataset.id;
    var card_arr = this.data.card_array;
    for (var i in card_arr) {
      if (i == id) {
        card_arr[i].card_src = 1;
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
  // 支付弹窗
  payClick: function (e) {
    var that = this;
    var type = e.currentTarget.dataset.type;
    if (type == 'score') {
      that.setData({
        pay_click_type: 1,
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
  payCancel: function (e) {
    var that = this;
    that.setData({
      payMode: true
    })
  },

  checkCancelYue: function (e) {
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
  checkCancelCard: function (e) {
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
  payConfirm: function (e) {
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
  yue_fo: function (e) {
    this.setData({
      account_pay_control: 1,
      prompt_message: '',
      canClick: true,
    })
  },
  mem_fo: function (e) {
    this.setData({
      card_account_pay_control: 1,
      prompt_message: '',
      canClick: true,
    })
  },
  onPullDownRefresh: function() {
    wx.stopPullDownRefresh();
  },
  mobileInput: function(e) {
    opt.mobile = e.detail.value;
    if (/^1[3456789]\d{9}$/.test(e.detail.value)) {
      this.setData({
        mobile: opt.mobile
      })
    } else {
      util.showModal('提示', "请输入正确的手机号！");
      return false;
    }

  },
  msgInput: function(e) {
    opt.add_message = e.detail.value;
    this.setData({
      add_message: opt.add_message
    })
  },
  nameInput: function(e) {
    opt.subscriber = e.detail.value;
    if (e.detail.value != "") {
      this.setData({
        name: opt.subscriber
      })
    } else {
      util.showModal('提示', "请输入预约人姓名！");
      return false;
    }
  },
  //编辑input
  cancelName: function() {
    opt.subscriber = "";
    this.setData({
      name: ""
    })
  },
  cancelMobile: function() {
    opt.mobile = "";
    this.setData({
      mobile: ""
    })
  },
  // 会员卡
  cardClick: function (e) { //会员卡弹框显示
    this.setData({
      cardMode: false
    })
  },
  cardCancel: function (e) { //关闭会员卡弹框
    this.setData({
      cardMode: true
    })
  },
  member_card: function (e) {
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
  user_account: function (e) {
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
    }
  },
  definePay: function () {
    this.data.create_order.money_paid = new_money_paid > 0 ? new_money_paid : 0;
    this.setData({
      create_order: this.data.create_order,
    })
  },
  //  提交订单
  OneClickBuy: function(e) {
    var that = this;
    if (!opt.subscriber || opt.subscriber == "") {
      util.showModal('提示', '请输入预约人姓名', function() {});
      return false;
    }
    if (!opt.mobile || opt.mobile == "") {
      util.showModal('提示', '请输入预约人手机号', function() {});
      return false;
    }
    opt.openid = util.getCache('openid');
    opt.form_id = e.detail.formId;
    opt.service_date = opt.day;
    opt.service_period = opt.hour;
    opt.money_paid = that.data.create_order.money_paid;
    opt.use_account = that.data.create_order.account_discount;
    opt.member_card_balance = that.data.create_order.member_card_balance;
    opt.member_card_no = that.data.member_card_no; 
    console.log(opt);
    util.api('/api/wxapp/service/ordersubmit', function(res) {
      var order_sn = res.content.order_sn;
      if (res.error == 0) {
        if (typeof(res.content.timeStamp) != 'undefined') {
          wx.requestPayment({
            'timeStamp': res.content.timeStamp,
            'nonceStr': res.content.nonceStr,
            'package': res.content.package,
            'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
            'paySign': res.content.paySign,
            'success': function(res) {
              util.toast_success('支付成功');
              util.redirectTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'fail': function(res) {
              util.toast_fail('支付失败');
              util.redirectTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'complete': function(res) {}
          });
        } else {
          util.redirectTo({
            url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
          })
        }
      } else if (res.error == 400002) {
        util.showModal('提示', res.content, function() {
          util.redirectTo({
            url: 'pages/appointment/appointment?service_id=' + opt.service_id,
          })
        });
      }
    }, opt)
  },
  confirmServ: function(e) {
    opt.service_date = opt.day;
    opt.service_period = opt.hour;
    opt.form_id = e.detail.formId;
    var that = this;
    opt.money_paid = that.data.create_order.money_paid;
    opt.use_account = that.data.create_order.account_discount;
    opt.member_card_balance = that.data.create_order.member_card_balance;
    opt.member_card_no = that.data.member_card_no; 
    if (opt.subscriber == "" || !opt.subscriber) {
      util.showModal('提示', "请输入姓名！");
      return false;
    }
    if (!/^1[3456789]\d{9}$/.test(opt.mobile)) {
      util.showModal('提示', "请输入正确的手机号！");
      return false;
    }
    util.api('/api/wxapp/service/ordersubmit', function(e) {
      if (e.error == 0) {
        util.redirectTo({
          url: '/pages/appointinfo/appointinfo?order_sn=' + e.content.order_sn,
        })
      } else if (e.error == 400002) {
        util.showModal('提示', e.content, function() {
          util.redirectTo({
            url: 'pages/appointment/appointment?service_id=' + opt.service_id,
          })
        });
      }
    }, opt)
  },
  onShow: function(e) {
    var that = this;
    var icon = '';
    that.setData({
      icon: app.globalData.imageUrl + 'image/wxapp/icon_rectangle.png',
    })
  },
  //服务跳转
  toService: function(e) {
    var service_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id=' + service_id,
    })
  },
  //门店跳转
  toStore: function(e) {
    var store_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + store_id,
    })
  }
})