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
    serviceInfo: {},
    storeInfo: {},
    recentOrderInfo: {}, // 用户上一次预约填写的信息
    technicianTitle: '', //职称
    paymentVoList: [], // 支付方式
    cardList: [], // 会员卡列表
    userCard: {}, // 选中的会员卡信息
    cardFirst: false, // 是否选择会员卡支付
    account: 0, // 余额
    balanceFirst: false, // 是否选择余额支付
    shopAvatar: '', // 店铺logo

    card_choose_name: '', // 选择的会员卡
    cardMode: false, // 会员卡弹窗
    payMode: false, // 余额支付弹窗
    create_order: {
      account_discount: '',
      member_card_balance: '', // 会员卡余额支付金额
      money_paid: '' // 应付总额
    }, // 填写的支付金额
    add_message: '', // 备注
    pay_click_type: 0, // 支付方式
    account_pay_control: 0,
    card_account_pay_control: 0,
    canClick: true, // 为 false 时不可使用余额
    prompt_message: "", // 警告信息 
    member_card_input: '', // 余额支付弹窗-会员卡余额使用金额
    user_account_input: '', // 余额支付弹窗-余额使用金额
    pay_card: 0,
    pay_yue: 0,

    params: {} // 提交的信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
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

  initData() {
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
          if (content.shopAvatar) {
            content.shopAvatar = _this.data.imageUrl + 'image/wxapp/shop_logo_default.png'
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
          'create_order.member_card_balance': 0
        })

        _this.defaultInput(content)
      }
    }, params)
  },
  toArray(imgs) {
    let images = imgs.slice(1, imgs.length - 1).split(',')
    images = images.map(function (item) {
      return item.slice(1, item.length - 1)
    })
    return images
  },
  // 默认填充
  defaultInput(con) {
    let that = this
    // 会员卡余额支付 支付开关配置
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
    // 余额支付 支付开关配置
    if (con.balanceFirst == 1 && that.data.account != '') {
      let count = 0;
      let user_account_input = that.data.create_order.money_paid - that.data.account > 0 ? that.data.account : that.data.create_order.money_paid;
      if (parseInt(user_account_input) == 0) {
        count++
      }
      if (count == 0) {
        let new_money_paid = (parseFloat(that.data.create_order.money_paid) - parseFloat(user_account_input)).toFixed(2);
        let account_discount = parseFloat(user_account_input).toFixed(2);
        that.setData({
          user_account_input: user_account_input >= 0 ? user_account_input : '',
          pay_yue: 1,
          'create_order.money_paid': new_money_paid,
          'create_order.account_discount': account_discount,
        })
      }
    }
  },
  // 预约人
  nameInput(e) {
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
  cancelName() {
    this.setData({
      'recentOrderInfo.subscriber': "",
      'params.subsciber': ""
    })
  },
  // 手机号
  mobileInput(e) {
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
  cancelMobile() {
    this.setData({
      'recentOrderInfo.mobile': "",
      'params.mobile': ""
    })
  },
  // 服务列表
  toStore(e) {
    let storeId = e.currentTarget.dataset.id
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + storeId,
    })
  },
  toService(e) {
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
  // 会员卡支付
  payClick(e) {
    let _this = this
    let type = e.currentTarget.dataset.type
    if (type == 'score') {
      _this.setData({
        pay_click_type: 1
      })
    } else if (type == 'yue') {
      _this.setData({
        pay_click_type: 2
      })
    } else {
      _this.setData({
        pay_click_type: 3
      })
    }
    _this.setData({
      payMode: true,
      canClick: true,
      prompt_message: ''
    })
  },
  payCancel() {
    this.setData({
      payMode: false
    })
  },
  checkCancelYue(e) {
    // 当有余额支付时，点击
    let new_money_paid = parseFloat(this.data.create_order.money_paid) + parseFloat(this.data.create_order.account_discount);
    this.setData({
      'create_order.money_paid': new_money_paid,
      'create_order.account_discount': 0,
      account_pay_control: 0,
      pay_yue: 0,
      prompt_message: '',
      user_account_input: ''
    })
  },
  // 余额支付
  user_account(e) {
    let value = e.detail.value

  },
  payConfirm(e) {
    let type = this.data.pay_click_type
    if (type == 1) {
      this.score_money(e)

    }
  },
  // 备注
  // 订单计算

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

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})