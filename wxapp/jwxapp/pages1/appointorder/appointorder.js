// pages1/appointorder/appointorder.js
const util = require('../../utils/util.js')
let app = getApp()
let imageUrl = app.globalData.imageUrl;

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    userId: '',
    userName: '',
    mobile: '',
    reserveInfo: {},

    imageUrl: imageUrl,
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    img_addr: imageUrl + '/image/wxapp/store_address.png',
    img_arrow: imageUrl + '/image/wxapp/backward_right.png',
    img_right: imageUrl + 'image/wxapp/right_into.png',
    img_success: imageUrl + '/image/wxapp/con_btn_success.png',
    img_iconsel: imageUrl + '/image/wxapp/selected.png',
    prd_img: imageUrl + "image/wxapp/address.png",
    img_store: imageUrl + "image/wxapp/address.png",
    img_person: imageUrl + "image/wxapp/appoint_man.png",
    img_cancel: imageUrl + "image/wxapp/cancel.png",
    img_phone: imageUrl + "image/wxapp/card_phone.png",
    img_time: imageUrl + "image/wxapp/appoint_time.png",
    img_man: imageUrl + "image/wxapp/tech_man.png",
    img_service: imageUrl + 'image/wxapp/icon_service.png',
    icon: app.globalData.imageUrl + 'image/wxapp/icon_rectangle.png',
    checkbox_no: imageUrl + '/image/admin/select.png',
    main_goods_notice: imageUrl + '/image/wxapp/main_goods_notice.png',

    // 接口请求回来的数据
    account: 0, // 余额
    shopAvatar: '', // 店铺logo
    serviceInfo: {}, // 接口返回的服务信息
    storeInfo: {}, // 接口返回的门店信息
    recentOrderInfo: {}, // 用户上一次预约填写的信息
    technicianTitle: '', //职称：技师分类
    paymentVoList: [], // 支持的支付方式
    cardList: [], // 会员卡列表
    cardFirst: false, // 默认选择会员卡支付开关
    balanceFirst: false, // 默认选择余额支付开关

    userCard: {}, // 选中的会员卡信息
    cardMode: false, // 会员卡弹窗
    payMode: false, // 余额支付弹窗
    member_card_input: '', // 支付弹窗-会员卡余额输入金额
    user_account_input: '', // 支付弹窗-余额输入金额
    pay_click_type: 0, // 因为积分，会员卡余额，余额支付公用一个弹窗，通过此字段区分支付方式： 1 积分，2 余额，3 会员卡
    prompt_message: "", // 校验填写余额信息
    canClick: true, // 是否可点击确认使用余额按钮
    pay_card: 0, // 会员卡余额有值
    pay_yue: 0, // 余额有值
    create_order: {
      account_discount: '', // 余额支付金额
      member_card_balance: '', // 会员卡余额支付金额
      money_paid: '' // 商品金额
    },
    add_message: '', // 备注
    params: {} // 提交的信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    wx.hideShareMenu();
    let reserveInfo = JSON.parse(options.data)
    let userId = util.getCache('user_id')
    let userName = util.getCache('nickName')
    let mobile = util.getCache('mobile')
    this.setData({
      userId: userId,
      userName: userName,
      mobile: mobile,
      reserveInfo: reserveInfo
    })
    this.initData()
  },
  initData () {
    let _this = this
    let params = {
      serviceId: this.data.reserveInfo.serviceId,
      userId: this.data.userId
    }
    util.api('/api/wxapp/store/service/confirmReservation', function (res) {
      console.log(res)
      if (res.error === 0) {
        let content = res.content
        content.service.serviceImg = JSON.parse(content.service.serviceImg)
        content.storePojo.storeImgs = JSON.parse(content.storePojo.storeImgs)
        // 会员卡列表
        let cardList = content.cardList.map(function (card) {
          card.card_src = _this.data.imageUrl + 'image/wxapp/icon_rectangle.png'
          if (!content.shopAvatar) {
            content.shopAvatar = _this.data.imageUrl + 'image/wxapp/shop_logo_default.png'
          } else {
            content.shopAvatar = _this.data.imageUrl + content.shopAvatar
          }
          if (card.bgType == 1) {
            card.bg = 'url(' + _this.data.imageUrl + card.bgImg + ') no-repeat'
          } else if (card.bgType == 0) {
            card.bg = card.bgColor
          }
          if (card.expireTime) {
            card.expireTime = card.expireTime.substring(0, 10)
          }
          return card
        })
        let userCard = {}
        if (cardList && cardList.length > 0) {
          userCard = cardList[0]
          userCard.card_src = 1
        }
        // 初始化应付金额
        _this.setData({
          serviceInfo: content.service,
          storeInfo: content.storePojo,
          recentOrderInfo: content.recentOrderInfo,
          technicianTitle: content.technicianTitle,
          paymentVoList: content.paymentVoList,
          cardList: cardList,
          userCard: cardList[0] || {}, // 默认选中第一张会员卡
          account: content.account,
          balanceFirst: content.balanceFirst,
          shopAvatar: content.shopAvatar,
          'create_order.money_paid': content.service.serviceSubsist,
          'create_order.account_discount': 0,
          'create_order.member_card_balance': 0,
          member_card_input: '',
          user_account_input: ''
        })

        _this.defaultInput(content)
      }
    }, params)
  },
  toArray (imgs) {
    let images = imgs.slice(1, imgs.length - 1).split(',')
    images = images.map(function (item) {
      return item.slice(1, item.length - 1)
    })
    return images
  },
  // 默认填充
  defaultInput (con) {
    let that = this
    // 会员卡余额支付 支付开关开启
    if (con.cardFirst == 1 && that.data.userCard.money) {
      let member_card_input = that.data.create_order.money_paid - that.data.userCard.money > 0 ?
        that.data.userCard.money : that.data.create_order.money_paid;
      let new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
      let member_card_balance = parseFloat(member_card_input).toFixed(2);
      that.setData({
        member_card_input: member_card_input > 0 ? member_card_input : '',
        pay_card: 1,
        'create_order.money_paid': new_money_paid,
        'create_order.member_card_balance': member_card_balance
      })
    }
    // 余额支付 支付开关开启
    if (con.balanceFirst == 1 && that.data.account) {
      let user_account_input = that.data.create_order.money_paid - that.data.account > 0 ?
        that.data.account : that.data.create_order.money_paid;
      let new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
      let account_discount = parseFloat(user_account_input).toFixed(2);
      that.setData({
        user_account_input: user_account_input >= 0 ? user_account_input : '',
        pay_yue: 1,
        'create_order.money_paid': new_money_paid,
        'create_order.account_discount': account_discount,
      })
    }
  },
  // 预约人
  nameInput (e) {
    let value = e.detail.value
    if (value != "") {
      this.setData({
        'params.subsciber': value,
        'recentOrderInfo.subscriber': value,
      })
    } else {
      util.showModal('提示', '请输入预约人姓名')
      return false
    }
  },
  cancelName () {
    this.setData({
      'recentOrderInfo.subscriber': "",
      'params.subsciber': ""
    })
  },
  // 手机号
  mobileInput (e) {
    let value = e.detail.value
    if (value != "") {
      this.setData({
        'params.mobile': value,
        'recentOrderInfo.mobile': value,
      })
    } else {
      util.showModal('提示', '请输入手机号')
      return false
    }
  },
  cancelMobile () {
    this.setData({
      'recentOrderInfo.mobile': "",
      'params.mobile': ""
    })
  },
  // 服务列表
  toStore (e) {
    let storeId = e.currentTarget.dataset.id
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + storeId,
    })
  },
  toService (e) {
    let serviceId = e.currentTarget.dataset.id
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id=' + serviceId,
    })
  },
  // 会员卡选择
  cardClick: function (e) { //会员卡弹框显示
    this.setData({
      cardMode: true
    })
  },
  cardCancel: function (e) { //关闭会员卡弹框
    this.setData({
      cardMode: false
    })
  },
  chooseCard (e) {
    let that = this
    let dataset = e.currentTarget.dataset
    let cardNo = dataset.card_no
    let id = dataset.id
    let cardList = this.data.cardList
    cardList.forEach(function (item, i) {
      if (item.cardNo === cardNo) {
        item.card_src = 1
        that.setData({
          userCard: item
        })
      } else {
        item.card_src = that.data.imageUrl + 'image/wxapp/icon_rectangle.png'
      }
    })
    that.setData({
      cardList: cardList
    })
  },
  cardConfirm (e) {
    this.setData({
      cardMode: false
    })
    this.checkCancelCard()
    this.checkCancelYue()
  },
  // 显示支付弹窗
  payClick (e) {
    let type = e.currentTarget.dataset.type
    let pay_click_type = 0
    if (type == 'score') { // 积分支付
      pay_click_type = 1
    } else if (type == 'yue') { // 余额支付
      pay_click_type = 2
    } else if (type == 'card') {
      // 会员卡余额支付
      pay_click_type = 3
    }
    this.setData({
      payMode: true,
      canClick: true,
      prompt_message: '',
      pay_click_type: pay_click_type
    })
  },
  payCancel () {
    this.setData({
      payMode: false
    })
  },
  payConfirm (e) {
    let type = this.data.pay_click_type
    let prompt_message = this.data.prompt_message
    if (prompt_message != '') return false
    if (type == 2) {
      this.user_account_blur(e)
      this.setData({
        pay_yue: 1
      })
    } else if (type == 3) {
      this.member_card_blur(e)
      this.setData({
        pay_card: 1
      })
    }
    this.setData({
      payMode: false
    })
  },
  /**
   *  会员卡余额支付
   */
  // 取消会员卡余额支付
  checkCancelCard (e) {
    let new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.member_card_balance);
    this.setData({
      'create_order.money_paid': new_money_paid,
      'create_order.member_card_balance': 0,
      pay_card: 0,
      prompt_message: '',
      member_card_input: ''
    })
  },
  mem_fo (e) {
    this.setData({
      prompt_message: '',
      canClick: true
    })
  },
  member_card_blur (e) {
    let value = e.detail.value
    let member_card_input = this.data.member_card_input
    if (value) {
      member_card_input = value ? value : 0
    } else {
      member_card_input = this.data.create_order.money_paid - this.data.userCard.money > 0 ?
        this.data.userCard.money : this.data.create_order.money_paid;
    }
    if (isNaN(member_card_input) || !isNaN(member_card_input) && member_card_input < 0) {
      this.setData({
        member_card_input: '',
        prompt_message: '请输入正确的金额'
      })
    }
    let msg = ''
    if (parseInt(member_card_input) > parseInt(this.data.account)) {
      msg = "最多可以使用会员卡余额：" + this.data.account + "元";
    }
    let new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(member_card_input)).toFixed(2);
    if (new_money_paid < 0) {
      msg = '使用金额不得超过订单金额'
    }
    if (msg != '') {
      this.setData({
        prompt_message: msg,
        canClick: false
      })
    }
    this.setData({
      member_card_input: member_card_input > 0 ? member_card_input : '',
      'create_order.member_card_balance': parseFloat(member_card_input).toFixed(2),
      'create_order.money_paid': new_money_paid
    })
  },
  /**
   * 余额支付
   */
  // 取消余额支付
  checkCancelYue (e) {
    let new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.account_discount);
    this.setData({
      'create_order.money_paid': new_money_paid,
      'create_order.account_discount': 0,
      pay_yue: 0,
      prompt_message: ''
    })
  },
  yue_fo (e) {
    this.setData({
      prompt_message: '',
      canClick: true
    })
  },
  user_account_blur (e) {
    let value = e.detail.value
    let user_account_input = this.data.user_account_input
    if (value) {
      user_account_input = value ? value : 0
    } else {
      if (user_account_input == "") {
        user_account_input = this.data.create_order.money_paid - this.data.account > 0 ?
          this.data.account : this.data.create_order.money_paid;
      }
    }
    if (isNaN(user_account_input) || !isNaN(user_account_input) && user_account_input < 0) {
      this.setData({
        user_account_input: '',
        prompt_message: '请输入正确的金额'
      })
      return false
    }
    let msg = ''
    if (parseInt(user_account_input) > parseInt(this.data.account)) {
      msg = "最多可以使用：" + this.data.account + "余额"
    }
    let new_money_paid = (parseFloat(this.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
    if (new_money_paid < 0) {
      msg = '使用余额不得超过订单金额'
    }
    if (msg != '') {
      this.setData({
        prompt_message: msg,
        canClick: false
      })
    }
    this.setData({
      user_account_input: user_account_input > 0 ? user_account_input : '',
      'create_order.account_discount': parseFloat(user_account_input).toFixed(2),
      'create_order.money_paid': new_money_paid
    })
  },

  // 备注
  msgInput (e) {
    let value = e.detail.value
    this.setData({
      addMessage: value
    })
  },
  // 订单计算
  OneClickBuy (e) {
    var that = this
    let params = {
      serviceId: this.data.reserveInfo.serviceId,
      userId: that.data.userId,
      storeId: that.data.storeInfo.storeId,
      technicianId: that.data.reserveInfo.tech_id,
      technicianName: that.data.reserveInfo.tech_name,
      subscriber: that.data.params.subsciber,
      mobile: that.data.params.mobile,
      addMessage: that.data.addMessage,
      serviceDate: that.data.reserveInfo.date,
      servicePeriod: that.data.reserveInfo.startTime + '-' + that.data.reserveInfo.endTime,
      useAccount: that.data.create_order.account_discount,
      memberCardNo: '',
      memberCardBalance: that.data.create_order.member_card_balance
    }
    if (!params.subscriber) {
      util.showModal('提示', '请输入预约人姓名')
      return false
    }
    if (!params.mobile) {
      util.showModal('提示', '请输入预约人手机号')
      return false
    }
    console.log(params)
    util.api('/api/wxapp/store/service/submitReservation', function (res) {
      if (res.error === 0) {
        console.log(res.content)
        if (typeof (res.content.timeStamp) != 'undefined') {

        } else {
          util.redirectTo({
            url: '/pages/appointinfo/appointinfo?order_sn=' + res.content.orderSn,
          })
        }
      } else if (res.error == 400002) {
        util.showModal('提示', res.content, function () {
          util.redirectTo({
            url: 'pages/appointment/appointment?service_id=' + params.serviceId,
          })
        });
      }
    }, params)
  },
  confirmServ (e) {
    let params = {
      serviceId: this.data.reserveInfo.serviceId,
      userId: that.data.userId,
      storeId: that.data.storeInfo.storeId,
      technicianId: that.data.reserveInfo.tech_id,
      technicianName: that.data.reserveInfo.tech_name,
      subscriber: that.data.params.subsciber,
      mobile: that.data.params.mobile,
      addMessage: that.data.addMessage,
      serviceDate: that.data.reserveInfo.date,
      servicePeriod: that.data.reserveInfo.startTime + '-' + that.data.reserveInfo.endTime
    }
    if (!params.subscriber) {
      util.showModal('提示', '请输入预约人姓名')
      return false
    }
    if (!params.mobile) {
      util.showModal('提示', '请输入预约人手机号')
      return false
    }
    console.log(params)
    util.api('/api/wxapp/store/service/submitReservation', function (res) {
      if (res.error === 0) {
        console.log(res.content)
        util.redirectTo({
          url: '/pages/appointinfo/appointinfo?order_sn=' + e.content.orderSn
        })
      } else if (res.error === 400002) {
        util.redirectTo({
          url: 'pages/appointment/appointment?service_id=' + params.serviceId,
        })
      }
    }, params)
  }
})