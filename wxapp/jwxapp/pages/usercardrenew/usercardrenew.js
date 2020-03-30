var util = require('../../utils/util.js');
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    cardMode: true,// 会员卡样式弹窗flag
    cardArray: [], // 会员卡样式弹窗渲染数据
    cardChooseName: '修改样式',
    choose_card: {},
    cardInfo: {
      cardType: 0,
      shopAvatar: util.getCache('avatarUrl'),
      cardName: '自动审核',
      expireType: 0,
      expireTime: '2020-6-17',
      chargeMoney: 1,
      money: 10,
      renewType: 0,
      renewNum: 100,
      shouldRenewDate: 100,
      renew_type: 0,
      renew_num: 100,
      renew_type: 0
    } // 续费卡详细信息
  },
  /**
   * 生命周期函数--监听页面加载
   * 
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.setData({
      page_name: '会员卡续费'
    })
    console.log(this.data, options)
    if (this.data.linColor) {
      let linColor = this.data.linColor.slice(0, this.data.linColor.lastIndexOf(',')) + ',0.3)';
      console.log(linColor)
      this.setData({
        bgColor: linColor
      })
    }
    let winWidth = wx.getSystemInfoSync().windowWidth;
    let viewHeight = winWidth * 0.94 / 1.8;
    this.setData({
      viewHeight: viewHeight,
    })
    // 模拟修改会员卡弹窗样式
    let cardArr = [
      {
        src_yes: this.data.imageUrl + 'image/wxapp/selected.png',
        src_no: this.data.imageUrl + 'image/wxapp/icon_rectangle.png',
        card_src: this.data.imageUrl + 'image/wxapp/icon_rectangle.png',
        // card_src: 1,
        shop_logo: this.data.imageUrl + 'image/wxapp/shop_logo_default.png',
        bg: '#0000AC',
        card_name: '腾飞测试1',
        card_type: 0,
        expire_time: null,
        card_no: 1
      },
      {
        src_yes: this.data.imageUrl + 'image/wxapp/selected.png',
        src_no: this.data.imageUrl + 'image/wxapp/icon_rectangle.png',
        card_src: this.data.imageUrl + 'image/wxapp/icon_rectangle.png',
        // card_src: 1,
        shop_logo: this.data.imageUrl + 'image/wxapp/shop_logo_default.png',
        bg: '#0081FF',
        card_name: '腾飞测试2',
        card_type: 0,
        expire_time: null,
        card_no: 1
      },
      {
        src_yes: this.data.imageUrl + 'image/wxapp/selected.png',
        src_no: this.data.imageUrl + 'image/wxapp/icon_rectangle.png',
        card_src: this.data.imageUrl + 'image/wxapp/icon_rectangle.png',
        // card_src: 1,
        shop_logo: this.data.imageUrl + 'image/wxapp/shop_logo_default.png',
        bg: '#0081FF',
        card_name: '腾飞测试3',
        card_type: 0,
        expire_time: null,
        card_no: 1
      }
    ]
    this.setData({
      card_array: cardArr
    })
    // var that = this;
    // util.api('/api/card/renew/info', function(res) {
    //   card_info = res.content;
    //   opt.card_id = card_info.card_id;
    //   opt.card_no = card_info.card_no;
    //   opt.renew_num = card_info.renew_num;
    //   console.log(card_info)
    //   that.loadData(card_info, 0, that);
    // }, {
    //   card_no: card_no
    // })
  },
  loadData: function (card_info, sgan, that) {
    // var bg;
    // var data_type_name;
    // if (card_info.date_type == 0) {
    //   data_type_name = "日";
    // } else if (card_info.date_type == 1) {
    //   data_type_name = "周";
    // } else {
    //   data_type_name = "月";
    // }
    // card_info.new_bg_img = imageUrl + card_info.new_bg_img;
    // if (card_info.bg_type == 1) {
    //   bg = 'url(' + card_info.new_bg_img + ') no-repeat';
    // } else {
    //   bg = card_info.bg_color;
    // }
    // if (sgan == 0) {
    //   var data = qrcode.createQrCodeImg(card_no, {
    //     'size': 300
    //   });
    //   that.setData({
    //     img_code: data,
    //   })
    // }
    // if (card_info.shop_avatar == null) {
    //   card_info.shop_avatar = imageUrl + 'image/wxapp/shop_logo_default.png';
    // }
    // that.setData({
    //   data_type_name: data_type_name,
    //   card_info: card_info,
    //   bg: bg,
    //   sgan: sgan
    // })
    // that.data.create_order.money_paid = card_info.renew_num;
    // that.data.create_order.account_discount = 0;
    // that.data.create_order.member_card_balance = 0;
    // opt.score_num = 0;
    // if (card_info.renew_type == 0) {
    //   that.data.user_money.account = card_info.account;
    //   // 会员卡
    //   var card_array = card_info.memeber_card_list;
    //   for (var i in card_array) {
    //     card_array[i].src_yes = imageUrl + 'image/wxapp/selected.png';
    //     card_array[i].src_no = imageUrl + 'image/wxapp/icon_rectangle.png';
    //     card_array[i].card_src = imageUrl + 'image/wxapp/icon_rectangle.png';
    //     card_array[i].shop_logo = card_info.shop_logo;
    //     if (!card_info.shop_logo) {
    //       card_array[i].shop_logo = imageUrl + 'image/wxapp/shop_logo_default.png';
    //     }
    //     if (card_array[i].bg_type == 1) {
    //       card_array[i].bg = "url('" + imageUrl + card_array[i].bg_img + "') no-repeat";
    //     } else {
    //       card_array[i].bg = card_array[i].bg_color;
    //     }
    //     if (i.toString() == card_info.member_card_no.toString()) {
    //       card_array[i].card_src = 1;
    //       that.data.user_money.member_card_money = parseFloat(card_array[i].money);
    //       that.setData({
    //         card_choose_name: card_array[i].card_name,
    //         member_card_no: card_array[i].card_no
    //       })
    //     }
    //     if (card_array[i].expire_time) {
    //       card_array[i].expire_time = card_array[i].expire_time.substring(0, 10);
    //     }
    //   }
    //   that.setData({
    //     card_array: card_array,
    //     user_money: that.data.user_money,
    //     create_order: that.data.create_order,
    //     member_card_input: '',
    //     user_account_input: '',
    //   })
    //   // 默认支付填充
    //   if (card_info.card_first == '1' && that.data.user_money.member_card_money != undefined) {
    //     member_card_input = that.data.create_order.money_paid - that.data.user_money.member_card_money > 0 ? that.data.user_money.member_card_money : that.data.create_order.money_paid;
    //     new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
    //     member_card_balance = parseFloat(member_card_input).toFixed(2);
    //     that.setData({
    //       member_card_input: member_card_input > 0 ? member_card_input : '',
    //       pay_card: 1,
    //       'create_order.money_paid': new_money_paid,
    //       'create_order.member_card_balance': member_card_balance,
    //     })
    //   }
    //   if (card_info.balance_first == '1') {
    //     var count = 0;
    //     user_account_input = that.data.create_order.money_paid - that.data.user_money.account > 0 ? that.data.user_money.account : that.data.create_order.money_paid;
    //     if (parseInt(user_account_input) == 0) {
    //       count++;
    //     }
    //     if (count == 0) {
    //       new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
    //       account_discount = parseFloat(user_account_input).toFixed(2);
    //       that.setData({
    //         user_account_input: user_account_input >= 0 ? user_account_input : '',
    //         pay_yue: 1,
    //         'create_order.money_paid': new_money_paid,
    //         'create_order.account_discount': account_discount,
    //       })
    //     }
    //   }
    // } else {
    //   opt.score_num = card_info.renew_num;
    // }


  },
  onPullDownRefresh: function () {
    // wx.stopPullDownRefresh();
  },

  modalinput: function () {
    // barcode.barcode('barcode', card_no, 580, 150);
    // this.setData({
    //   showModal: true
    // })
  },

  cardConfirm: function (e) {
    this.setData({
      cardMode: true
    });
    // var member_card_arr = Object.keys(this.data.card_array);
    // if (member_card_arr.length == 1 && this.data.card_array[member_card_arr[0]].card_type == 1) {
    //   card_info.member_card_no = this.data.card_array[member_card_arr[0]].card_no;
    // } else {
    //   card_info.member_card_no = this.data.choose_card.card_no;
    // }
    // this.checkCancelYue();
    // this.checkCancelCard();
    // this.loadData(card_info, 0, this);
  },
  chooseCard: function (e) { //选择会员卡
    console.log(e)
    let card_choose_name = e.currentTarget.dataset.name;
    let renew_card_no = e.currentTarget.dataset.card_no;
    let id = e.currentTarget.dataset.id;  // 当前选中的卡下标
    let card_arr = this.data.card_array;
    card_arr.forEach((item, index) => {
      if (index == id) {
        card_arr[index].card_src = 1;
      } else {
        card_arr[index].card_src = card_arr[index].src_no;
      }
    })
    this.data.choose_card.card_choose_name = card_choose_name;
    this.data.choose_card.card_no = renew_card_no;
    console.log(card_arr)
    this.setData({
      card_array: card_arr
    })
  },
  // 支付弹窗
  payClick: function (e) {
    // var that = this;
    // var type = e.currentTarget.dataset.type;
    // if (type == 'score') {
    //   that.setData({
    //     pay_click_type: 1,
    //   })
    // } else if (type == 'yue') {
    //   that.setData({
    //     pay_click_type: 2,
    //   })
    // } else {
    //   that.setData({
    //     pay_click_type: 3,
    //   })
    // }
    // that.setData({
    //   payMode: false,
    //   canClick: true,
    //   prompt_message: '',
    // })
  },
  payCancel: function (e) {
    // var that = this;
    // that.setData({
    //   payMode: true
    // })
  },

  checkCancelYue: function (e) {
    // new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.account_discount);
    // this.setData({
    //   'create_order.money_paid': new_money_paid,
    //   account_pay_control: 0,
    //   pay_yue: 0,
    //   prompt_message: '',
    //   user_account_input: '',
    //   'create_order.account_discount': 0,
    // })
  },
  checkCancelCard: function (e) {
    // new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.member_card_balance);
    // this.setData({
    //   'create_order.money_paid': new_money_paid,
    //   card_account_pay_control: 0,
    //   pay_card: 0,
    //   prompt_message: '',
    //   member_card_input: '',
    //   'create_order.member_card_balance': 0,
    // })
  },
  payConfirm: function (e) {
    // var that = this;
    // var type = that.data.pay_click_type;
    // var input;
    // var prompt_message;
    // if (type == 1) {
    //   that.score_money(e);
    //   input = that.data.score_money_input;
    //   prompt_message = that.data.prompt_message;
    //   if (prompt_message != '') return false;
    //   that.definePay();
    //   that.setData({
    //     pay_score: 1,
    //   })
    // } else if (type == 2) {
    //   that.user_account(e);
    //   input = that.data.user_account_input;
    //   prompt_message = that.data.prompt_message;
    //   if (prompt_message != '') return false;
    //   that.definePay();
    //   that.setData({
    //     pay_yue: 1,
    //   })
    // } else {
    //   that.member_card(e);
    //   input = that.data.member_card_input;
    //   prompt_message = that.data.prompt_message;
    //   if (prompt_message != '') return false;
    //   that.definePay();
    //   that.setData({
    //     pay_card: 1,
    //   })
    // }
    // that.setData({
    //   payMode: true,
    // })
  },
  yue_fo: function (e) {
    // this.setData({
    //   account_pay_control: 1,
    //   prompt_message: '',
    //   canClick: true,
    // })
  },
  mem_fo: function (e) {
    // this.setData({
    //   card_account_pay_control: 1,
    //   prompt_message: '',
    //   canClick: true,
    // })
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
    // if (e.detail.value) {
    //   member_card_input = e.detail.value ? e.detail.value : 0;
    // } else {
    //   if (this.data.member_card_input == '') {
    //     member_card_input = this.data.create_order.money_paid - this.data.user_money.member_card_money > 0 ? this.data.user_money.member_card_money : this.data.create_order.money_paid;
    //   } else {
    //     member_card_input = this.data.member_card_input;
    //   }
    // }
    // if (isNaN(member_card_input) || !isNaN(member_card_input) && member_card_input < 0) {
    //   var tishi1 = "请输入正确的余额";
    //   this.setData({
    //     member_card_input: '',
    //     prompt_message: tishi1,
    //   })
    //   return false;
    // } else if (member_card_input == 0) {
    //   var tishi1 = "会员卡余额不可填写0";
    //   this.setData({
    //     member_card_input: '',
    //     prompt_message: tishi1,
    //   })
    //   return false;
    // } else {
    //   var msg = '';
    //   if (parseInt(member_card_input) > parseInt(this.data.user_money.member_card_money)) {
    //     msg = "最多可以使用会员卡余额：" + this.data.user_money.member_card_money + "元";
    //   }
    //   new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
    //   if (new_money_paid < 0) {
    //     msg = '使用余额不得超过订单金额';
    //   }
    //   if (msg != '') {
    //     this.setData({
    //       prompt_message: msg,
    //       canClick: false,
    //     })
    //   }
    //   this.data.create_order.member_card_balance = parseFloat(member_card_input).toFixed(2);
    //   this.setData({
    //     member_card_input: member_card_input > 0 ? member_card_input : '',
    //     create_order: this.data.create_order,
    //     card_account_pay_control: 1,
    //   })
    // }
  },
  user_account: function (e) {
    // if (e.detail.value) {
    //   user_account_input = e.detail.value ? e.detail.value : 0;
    // } else {
    //   if (this.data.user_account_input == '') {
    //     user_account_input = this.data.create_order.money_paid - this.data.user_money.account > 0 ? this.data.user_money.account : this.data.create_order.money_paid;
    //   } else {
    //     user_account_input = this.data.user_account_input;
    //   }
    // }
    // if (isNaN(user_account_input) || !isNaN(user_account_input) && user_account_input < 0) {
    //   var tishi1 = "请输入正确的余额";
    //   this.setData({
    //     user_account_input: '',
    //     prompt_message: tishi1,
    //   })
    //   return false;
    // } else if (user_account_input == 0) {
    //   var tishi1 = "余额需大于0";
    //   this.setData({
    //     user_account_input: '',
    //     prompt_message: tishi1,
    //   })
    //   return false;
    // } else {
    //   var msg = '';
    //   if (parseInt(user_account_input) > parseInt(this.data.user_money.account)) {
    //     msg = "最多可以使用：" + this.data.user_money.account + "余额";
    //   }
    //   new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
    //   if (new_money_paid < 0) {
    //     msg = '使用余额不得超过订单金额';
    //   }
    //   if (msg != '') {
    //     this.setData({
    //       prompt_message: msg,
    //       canClick: false,
    //     })
    //   }
    //   this.data.create_order.account_discount = parseFloat(user_account_input).toFixed(2);
    //   this.setData({
    //     user_account_input: user_account_input <= 0 ? '' : user_account_input,
    //     create_order: this.data.create_order,
    //     account_pay_control: 1,
    //   });
    // }
  },
  definePay: function () {
    // this.data.create_order.money_paid = new_money_paid > 0 ? new_money_paid : 0;
    // this.setData({
    //   create_order: this.data.create_order,
    // })
  },
  //  提交订单
  OneClickBuy: function (e) {

  },

  to_index: function () {
    //  util.reLaunch({
    //    url: '/pages/index/index'
    //  })
  },
  to_last: function () {
    //  wx.navigateBack({
    //    delta: 1,
    //  });
  },
  /**
   * 隐藏模态对话框
   */
  hideModal: function () {
    // this.setData({
    //   showModal: false
    // });
  },
  // 展示二维码
  showQrCode () {
    let qrCode = [this.data.cardInfo.qrCode]
    this.setData({
      qrCode,
      showQrcode: true
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  }
})