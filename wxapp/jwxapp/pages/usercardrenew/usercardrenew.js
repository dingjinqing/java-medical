var util = require('../../utils/util.js');
var qrcode = require('../../utils/qrcode.js');
var barcode = require('../../utils/barcode.js');
var opt = {};
var user_account_input;
var member_card_input;
var new_money_paid;
var account_discount;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    cardMode: false,// 会员卡样式弹窗flag
    cardArray: [], // 会员卡样式弹窗渲染数据
    payMode: false, // 余额支付弹窗
    cardPayMode: false, // 会员卡余额支付弹窗
    pay_card: 0, // 会员卡支付flag
    pay_click_type: 2,
    user_money: {
      account: 100
    },
    cardChooseName: '',
    cardInfo: {
      cardType: 0,
      discount: 6, // 二维码弹窗折数
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
    }, // 续费卡详细信息
    success: 0, // 是否支付成功
    user_money: {},
    choose_card: {}, // 会员卡样式弹窗选中得会员卡数据
    create_order: {},
    memberCardsList: [],
    pay_yue: 0,
    pay_card: 0
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
    console.log(this.data.imageUrl, options)
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
    let that = this;
    let userId = util.getCache('user_id')
    console.log(JSON.parse(options.cardNo))
    util.api('/api/wxapp/card/renew', function (res) {
      console.log(res)
      let cardInfo = res.content;
      opt.card_id = cardInfo.cardId;
      opt.card_no = cardInfo.cardNo;
      opt.renew_num = cardInfo.renewNum;
      console.log(cardInfo)
      that.loadData(cardInfo, 0, that);
    }, {
      cardNo: JSON.parse(options.cardNo),
      userId: userId
    })
  },
  loadData (cardInfo, sgan, that) {
    //  模拟续费金额
    // cardInfo.renewNum = 50
    console.log(cardInfo)
    // 二维码处理
    if (sgan == 0) {
      var data = qrcode.createQrCodeImg(cardInfo.cardNo, {
        'size': 300
      });
      console.log(data)
      that.setData({
        img_code: data,
      })
    }
    let bg = null;
    // 背景处理
    console.log(that.data)
    let imageUrl = that.data.imageUrl
    cardInfo.bgImg = imageUrl + cardInfo.bgImg;
    if (cardInfo.bgType == 1) {
      bg = 'url(' + cardInfo.bgImg + ') no-repeat';
    } else {
      bg = cardInfo.bgColor;
    }
    // 处理过期时间/ 停用
    cardInfo.cardStopImg = this.getCardStopImage(cardInfo)
    // 店铺头像处理
    if (!that.data.bottom.logo) {
      cardInfo.shopAvatar = imageUrl + 'image/wxapp/shop_logo_default.png';
    } else {
      cardInfo.shopAvatar = that.data.bottom.logo;
    }
    cardInfo.renewNum = Number(cardInfo.renewNum).toFixed(2)
    that.setData({
      cardInfo: cardInfo,
      bg: bg,
      sgan: sgan
    })


    that.data.create_order.money_paid = cardInfo.renewNum; // 续费金额或积分
    that.data.create_order.account_discount = 0.00;
    that.data.create_order.member_card_balance = 0.00;
    opt.score_num = 0;
    if (cardInfo.renewType == 0) { // 现金处理
      that.data.user_money.account = cardInfo.account; // 用户余额
      // 会员卡
      let cardArray = cardInfo.memberCardList; // 用户可以用来进行续费支付的卡的集合
      let memberCards = []
      for (var i in cardArray) {
        // cardArray[i].src_yes = imageUrl + 'image/wxapp/selected.png';
        // cardArray[i].src_no = imageUrl + 'image/wxapp/icon_rectangle.png';
        // cardArray[i].card_src = imageUrl + 'image/wxapp/icon_rectangle.png';
        // 店铺头像
        if (!that.data.bottom.logo) {
          cardArray[i].avatar = imageUrl + 'image/wxapp/shop_logo_default.png';
        } else {
          cardArray[i].avatar = that.data.bottom.logo;
        }
        // 会员卡样式弹框 背景处理
        if (cardArray[i].bgType == 1) {
          let reg = /^http*/
          if (!reg.test(cardArray[i].bgImg)) {
            cardArray[i].bgImg = imageUrl + cardArray[i].bgImg;
          } else {
            cardArray[i].bgImg = cardArray[i].bgImg
          }
        }
        console.log(i, cardInfo.memberCardNo.toString())
        if (i.toString() == cardInfo.memberCardNo.toString()) {
          console.log('cf')
          that.data.user_money.member_card_money = parseFloat(cardArray[i].money); // 选择支付的卡的余额
          that.setData({
            cardChooseName: cardArray[i].cardName,
            memberCardNo: cardArray[i].cardNo
          })
        }
        memberCards.push(cardArray[i])
      }
      console.log(memberCards)
      that.setData({
        memberCardsList: memberCards,
        user_money: that.data.user_money,
        create_order: that.data.create_order
      })
      console.log(that.data.cardInfo)
      // 默认支付填充  
      // 如果优先用卡支付并且所选择支付的卡的有余额
      if (cardInfo.cardFirst == '1' && that.data.user_money.member_card_money != undefined) {
        // 卡付多少
        member_card_input = that.data.create_order.money_paid - that.data.user_money.member_card_money > 0 ? that.data.user_money.member_card_money : that.data.create_order.money_paid;
        // 扣除卡付的之后还需付多少
        new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
        // 会员卡所付数额保存两位小数
        member_card_balance = parseFloat(member_card_input).toFixed(2);
        console.log(new_money_paid, member_card_balance)
        that.setData({
          member_card_input: member_card_input > 0 ? member_card_input : '',
          pay_card: 1,
          'create_order.money_paid': new_money_paid,
          'create_order.member_card_balance': member_card_balance,
        })
      }
      console.log(cardInfo)
      // 如果优先用余额支付
      if (cardInfo.balanceFirst == '1') {
        var count = 0;
        user_account_input = that.data.create_order.money_paid - that.data.user_money.account > 0 ? that.data.user_money.account : that.data.create_order.money_paid;
        if (parseInt(user_account_input) == 0) {
          count++;
        }
        console.log(count)
        if (count == 0) { // 如果所需支付金额不为0
          // 计算扣除当前用户余额后还需支付的余额
          new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
          account_discount = parseFloat(user_account_input).toFixed(2);
          console.log(account_discount)
          that.setData({
            user_account_input: user_account_input >= 0 ? user_account_input : '',
            pay_yue: 1,
            'create_order.money_paid': new_money_paid,
            'create_order.account_discount': account_discount,
          })
        }
      }
    } else {  //  积分支付
      opt.score_num = cardInfo.renewNum;
    }

    console.log(this.data.create_order, this.data.user_money, this.data.pay_yue)
  },
  // 获取会员卡停用/删除状态图片
  getCardStopImage (cardItem) {
    if (cardItem.cardType === 2 && cardItem.flag === 2) {
      return `${this.data.imageUrl}image/wxapp/card_stop.png`
    }
    if (cardItem.expire === 1) {
      return `${this.data.imageUrl}image/wxapp/card_out_time.png`
    }
    return ``
  },
  modalinput () {
    console.log(this.data.cardInfo)
    let qrCode = []
    this.setData({
      qrCode,
      showQrcode: true
    })
  },
  // 选中会员卡样式弹窗确定事件
  getSelectCard (res) {
    console.log(res)
    if (res.detail) {
      let nowCheck = this.data.memberCardsList.find(item => item.cardNo == res.detail)
      this.data.choose_card.card_choose_name = nowCheck.cardName;
      this.data.choose_card.card_no = nowCheck.cardNo;
      console.log(this.data.memberCardsList)
      this.data.cardInfo.memberCardNo = res.detail
    } else {
      this.setData({
        cardChooseName: '',
        'cardInfo.memberCardNo': -1,
        memberCardNo: 0,
        choose_card: {}
      })
    }
    this.checkCancelYue();
    this.checkCancelCard();
    this.loadData(this.data.cardInfo, 0, this);
  },
  // 点击会员卡余额支付调起支付弹窗
  payClick (e) {
    console.log(this.data.create_order, this.data.user_money)
    var that = this;
    var type = e.currentTarget.dataset.type;
    if (type == 'yue') {
      that.setData({
        payMode: true
      })
    } else {
      that.setData({
        cardPayMode: true
      })
    }
  },
  checkCancelYue: function (e) { // 用户余额
    new_money_paid = (parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.account_discount)).toFixed(2);
    console.log(new_money_paid)
    this.setData({
      'create_order.money_paid': new_money_paid,
      account_pay_control: 0,
      pay_yue: 0,
      prompt_message: '',
      user_account_input: '',
      'create_order.account_discount': 0.00,
    })
  },
  checkCancelCard: function (e) { // 会员卡余额
    new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.member_card_balance);
    this.setData({
      'create_order.money_paid': new_money_paid,
      card_account_pay_control: 0,
      pay_card: 0,
      prompt_message: '',
      member_card_input: '',
      'create_order.member_card_balance': 0.00,
    })
  },
  // 余额支付弹窗确定事件
  getInputBalance (e) {
    console.log(e)
    if (e.detail) {
      user_account_input = e.detail ? e.detail : 0;
    } else {
      if (this.data.user_account_input == '') {
        user_account_input = this.data.create_order.money_paid - this.data.user_money.account > 0 ? this.data.user_money.account : this.data.create_order.money_paid;
      } else {
        user_account_input = this.data.user_account_input;
      }
    }
    new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);

    this.setData({
      user_account_input: user_account_input <= 0 ? '' : user_account_input,
      pay_yue: 1,
      'create_order.account_discount': parseFloat(user_account_input).toFixed(2),
      payMode: false
    })
    this.definePay();
  },
  // 会员卡余额支付弹窗确定事件
  getInputCardBalance (e) {
    console.log(e)
    if (e.detail) {
      member_card_input = e.detail ? e.detail : 0;
    } else {
      if (this.data.member_card_input == '') {
        member_card_input = this.data.create_order.money_paid - this.data.user_money.member_card_money > 0 ? this.data.user_money.member_card_money : this.data.create_order.money_paid;
      } else {
        member_card_input = this.data.member_card_input;
      }
    }
    console.log(this.data.create_order.money_paid)
    new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
    this.setData({
      member_card_input: member_card_input > 0 ? member_card_input : '',
      pay_card: 1,
      'create_order.member_card_balance': parseFloat(member_card_input).toFixed(2),
      cardPayMode: false
    })
    this.definePay();
  },
  // 会员卡
  cardClick (e) { //会员卡弹框显示
    this.setData({
      cardMode: true
    })
  },
  definePay () {
    console.log(new_money_paid)
    this.data.create_order.money_paid = new_money_paid > 0 ? new_money_paid : 0;
    this.setData({
      create_order: this.data.create_order
    })
  },
  //  提交订单
  OneClickBuy (e) {
    var that = this;

    let params = {
      cardId: opt.card_id,
      cardNo: opt.card_no,
      memberCardBalance: 0.00,
      memberCardNo: 0,
      moneyPaid: 0.00,
      renewNum: parseFloat(opt.renew_num).toFixed(2),
      scoreNum: Number(opt.score_num),
      useAccount: 0.00
    }
    console.log(that.data)
    // opt.openid = util.getCache('openid');
    // opt.form_id = e.detail.formId;
    if (that.data.cardInfo.renewType == 0) { // 现金支付
      params.moneyPaid = that.data.create_order.money_paid; // 续费金额
      params.useAccount = that.data.create_order.account_discount; // 余额支付
      params.memberCardBalance = that.data.create_order.member_card_balance == 0 ? '0.00' : that.data.create_order.member_card_balance; // 会员卡支付的价钱数额
      params.memberCardNo = that.data.memberCardNo;
      params.scoreNum = 0;
    } else {  // 积分支付
      params.moneyPaid = that.data.cardInfo.renewNum;
      params.useAccount = 0.00;
      params.memberCardBalance = 0.00;
      params.memberCardNo = 0;
    }
    console.log(opt, params);

    util.api('/api/wxapp/card/renew/checkout', function (res) {
      console.log(res)
      //   var order_sn = res.content.order_sn;
      if (res.error == 0) {
        console.log(typeof (res.content.webPayVo.timeStamp))
        if (typeof (res.content.webPayVo.timeStamp) != 'undefined') {
          console.log('wx**********************');
          wx.requestPayment({
            'timeStamp': res.content.webPayVo.timeStamp,
            'nonceStr': res.content.webPayVo.nonceStr,
            'package': res.content.webPayVo.package,
            'signType': typeof res.content.webPayVo.signType == "undefined" ? 'MD5' : res.content.webPayVo.signType,
            'paySign': res.content.webPayVo.paySign,
            'success': function (ret) {
              util.toast_success('支付成功');
              that.setData({
                'cardInfo.expireTime': res.content.expireTime,
                'cardInfo.money': res.content.money,
                success: 1
              })
            },
            'fail': function (res) {
              util.toast_fail('支付失败');
            },
            'complete': function (res) { }
          });
        } else {
          that.setData({
            'cardInfo.expireTime': res.content.expireTime,
            'cardInfo.money': res.content.money,
            success: 1
          })
        }

      } else {
        util.showModal('提示', res.message, function () {

        });
      }
    }, params)
  },
  to_index () {
    util.reLaunch({
      url: '/pages/index/index'
    })
  },
  to_last () {
    wx.navigateBack({
      delta: 1,
    });
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