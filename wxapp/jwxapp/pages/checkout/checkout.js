var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    balanceStatus: 0,//使用余额状态 
    scoreStatus: 0,//使用积分状态
    cardBalanceStatus: 0,//使用会员卡余额状态
    couponArray: null,//优惠券列表
    defaultCouponIndex: null,//默认选择优惠券
    payType: [0, 1, 2],
    choosePayTypeIndex: 0,//所选支付方式
    showCardBalanceDialog: false, //是否显示会员卡余额弹框
    showBalanceDialog: false, //是否显示余额弹框
    showScoreDialog: false, //是否显示积分弹框
    showCardDialog: false, //是否显示选择会员卡弹框
    showStoreDialog: false, //是否显示选择门店弹框
    params: {
      action: 10,
      activityType: null, // 指定本次结算所参加的唯一营销活动类型
      activityId: null, // 指定本次结算所参加的唯一营销活动类型 ID
      groupId: null, // 拼团参团id
      addressId: null, // 地址id
      goods: null, // 商品列表
      deliverType: 0, // 配送方式
      storeId: null, // 门店id
      memberCardNo: 0, //0: 默认选第一张；null：不选；其他：卡号
      couponSn: 0, //0: 默认选第一张；null：不选；其他：优惠卷号
      scoreDiscount: null, // 积分抵扣金额
      balance: null, //余额抵扣金额
      cardBalance: null, //会员卡抵扣金额
      orderPayWay: null,//支付方式
      isCart: 0
    },
    usePayInfo: {
      moneyPaid: 0,//订单可支付的金额
      useCardBalance: 0,//已使用的会员卡余额
      useBalance: 0,//已使用的余额
      useScore: 0//已使用的积分
    },
    orderInfo: {},
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let goods = [];
    let { goodsList, activityType, activityId } = options
    JSON.parse(goodsList).forEach(item => {
      let { goodsId, prdRealPrice: goodsPrice, goodsNum: goodsNumber, prdId: productId, isCart = 0 } = item
      goods.push({ goodsId, goodsPrice, goodsNumber, productId, isCart })
    })
    this.setData({
      'params.goods': goods,
      'params.isCart': goods[0].isCart, //购物车来源|商品详情
      'params.activityType': activityType,
      'params.activityId': activityId
    })
    if (options.groupid) {
      this.setData({
        'params.groupId': options.groupid
      })
    }
    this.requestOrder()
  },
  requestOrder () {
    util.api('/api/wxapp/order', res => {
      if (res.error === 0) {
        let orderInfo = res.content
        this.setData({
          orderInfo
        })
        this.getCouponData(orderInfo)
        this.defaultInput(orderInfo)
      }
    }, { ...this.data.params })
  },
  // 选择地址
  addAddress () {
    wx.chooseAddress({
      success: (res) => {
        util.api('/api/wxapp/address/choose', res => {
          console.log(res)
          if (res.error === 0) {
            this.setData({
              'params.addressId': res.content.addressId
            })
            console.log()
            this.requestOrder()
          }
        }, { wxAddress: { ...res } })
      },
      fail: function () {
        wx.getSetting({
          success: function (res) {
            if (!res.authSetting['scope.address']) {
              util.showModal('是否打开设置页面', '需要获取您的位置信息，请到小程序的设置页面打开授权', function () {
                wx.openSetting({
                  success: function (res) {
                  }
                })
              })
            }
          }
        })
      }
    })
  },
  // 默认填充
  defaultInput (orderInfo) {
    let { isBalancePay, isCardPay, isScorePay, moneyPaid, memberCardMoney, userAccount, scorePayNum, userScore, scoreMaxDiscount, paymentList } = orderInfo
    if (isCardPay === 1 && memberCardMoney > 0) {
      let useCardBalance = moneyPaid - memberCardMoney > 0 ? memberCardMoney : moneyPaid
      moneyPaid -= useCardBalance
      this.setData({
        'usePayInfo.useCardBalance': useCardBalance,
        cardBalanceStatus: useCardBalance > 0 ? 1 : 0,
      })
    } else {
      this.setData({
        'usePayInfo.useCardBalance': 0,
        cardBalanceStatus: 0
      })
    }
    if (paymentList.balance && isBalancePay === 1 && userAccount > 0) {
      let useBalance = moneyPaid - userAccount > 0 ? userAccount : moneyPaid
      moneyPaid -= useBalance
      this.setData({
        'usePayInfo.useBalance': useBalance,
        balanceStatus: useBalance > 0 ? 1 : 0,
      })
    } else {
      this.setData({
        'usePayInfo.useBalance': 0,
        balanceStatus: 0,
      })
    }
    if (paymentList.score && isScorePay === 1 && userScore > scorePayNum && userScore > 0) {
      let useScore = moneyPaid * 100 > scoreMaxDiscount * 100 ? (scoreMaxDiscount * 100 > userScore ? userScore : scoreMaxDiscount * 100) : (moneyPaid * 100 > userScore ? userScore : moneyPaid * 100)
      moneyPaid -= useScore
      this.setData({
        'usePayInfo.useScore': useScore,
        scoreStatus: useScore > 0 ? 1 : 0
      })
    } else {
      this.setData({
        'usePayInfo.useScore': 0,
        scoreStatus: 0
      })
    }
    this.getPayMoney()
  },
  // 会员卡余额支付事件
  cardBalanceTap () {
    if (this.data.cardBalanceStatus) {
      this.setData({
        cardBalanceStatus: 0,
        showCardBalanceDialog: false,
        'usePayInfo.useCardBalance': 0
      })
    } else {
      this.setData({
        showCardBalanceDialog: true
      })
    }
    this.getPayMoney()
  },
  // 余额支付事件
  balanceTap () {
    if (this.data.balanceStatus) {
      this.setData({
        balanceStatus: 0,
        showBalanceDialog: false,
        'usePayInfo.useBalance': 0
      })
    } else {
      this.setData({
        showBalanceDialog: true
      })
    }
    this.getPayMoney()
  },
  // 积分支付事件
  scoreTap () {
    if (this.data.scoreStatus) {
      this.setData({
        scoreStatus: 0,
        showScoreDialog: false,
        'usePayInfo.useScore': 0
      })
    } else {
      this.setData({
        showScoreDialog: true
      })
    }
    this.getPayMoney()
  },
  // 获得输入的余额数
  getInputBalance (data) {
    this.setData({
      'usePayInfo.useBalance': data.detail,
      balanceStatus: 1
    })
    this.getPayMoney()
  },
  //获取输入的积分数
  getInputScore (data) {
    this.setData({
      'usePayInfo.useScore': data.detail,
      scoreStatus: 1
    })
    this.getPayMoney()
  },
  // 获取输入的会员卡余额数
  getInputCardBalance (data) {
    this.setData({
      'usePayInfo.useCardBalance': data.detail,
      cardBalanceStatus: 1
    })
    this.getPayMoney()
  },
  // 获取优惠券数据
  getCouponData (orderInfo) {
    let { coupons, defaultCoupon } = orderInfo
    if (coupons === null || coupons.length === 0) {
      this.setData({
        couponArray: null,
        defaultCouponIndex: null
      })
      return
    }
    let couponArray = [{ couponSn: null, actName: '不使用优惠券' }, ...coupons]
    let defaultCouponIndex = defaultCoupon && couponArray.findIndex(item => item.couponSn === defaultCoupon.couponSn) || 0
    this.setData({
      couponArray,
      defaultCouponIndex
    })
  },

  // 变更优惠券
  couponChange (e) {
    let { couponSn } = this.data.couponArray[parseInt(e.detail.value)]
    this.setData({
      'params.couponSn': couponSn,
      defaultCouponIndex: parseInt(e.detail.value)
    })
    this.requestOrder()
  },
  // 选择会员卡事件
  selectCardTap () {
    this.setData({
      showCardDialog: true
    })
  },
  // 得到选择后的会员卡
  getSelectCard (data) {
    this.setData({
      'params.memberCardNo': data.detail
    })
    this.requestOrder()
  },
  // 变更支付类型
  changePayType (e) {
    this.setData({
      choosePayTypeIndex: e.currentTarget.dataset.index
    })
  },
  // 变更配送方式
  selectShippingMethod (e) {
    this.setData({
      'params.deliverType': e.currentTarget.dataset.index
    })
    this.requestOrder()
  },
  // 选择门店
  selectStore () {
    if (this.data.chooseShippingIndex === 0) return;
    let storeDialogData = {};
    storeDialogData.openType = this.data.chooseShippingIndex;
    storeDialogData.data = [
      { id: 1, storeName: '伊泰大厦', address: '潘家园', distance: '5.46' },
      { id: 2, storeName: '回龙观大厦', address: '西直门', distance: '0.15' },
      { id: 3, storeName: '西二旗大厦', address: '西二旗', distance: '15' },
      { id: 4, storeName: '霍营大厦', address: '蜂鸟社区', distance: '123' },
    ];
    this.setData({
      showStoreDialog: true,
      storeDialogData: storeDialogData
    })
  },
  //获取应付总金额
  getPayMoney () {
    let moneyPaid = this.data.orderInfo.moneyPaid, useScore = this.data.usePayInfo.useScore, useBalance = this.data.usePayInfo.useBalance, useCardBalance = this.data.usePayInfo.useCardBalance
    let floatNum = parseFloat(moneyPaid - useCardBalance - useBalance - useScore / 100).toFixed(3)
    floatNum = parseFloat(floatNum.substring(0, floatNum.length - 1))
    this.setData({
      'usePayInfo.moneyPaid': floatNum
    })
  },
  // 获取门店改变
  getSelectStore (info) {
    let { id: storeId, openType } = info.detail;
    console.log(storeId, openType)
    switch (openType) {
      case 2:

        break;
      default:

        break;
    }
  },
  // 关闭弹窗
  closeDialog (target) {
    switch (target.detail) {
      case 'balance':
        this.setData({
          showBalanceDialog: false
        })
        break;
      case 'score':
        this.setData({
          showScoreDialog: false
        })
        break;
      default:
        this.setData({
          showCardBalanceDialog: false
        })
        break;
    }
  },
  // 提交订单
  confirmOrder () {
    let { orderGoods: goods, orderAmount, paymentList, activityType, activityId } = this.data.orderInfo || {}
    let { useBalance: balance, useCardBalance: cardBalance, useScore: scoreDiscount } = this.data.usePayInfo
    let addressId = this.data.orderInfo.address && this.data.orderInfo.address.addressId || null
    let couponSn = this.data.orderInfo.defaultCoupon && this.data.orderInfo.defaultCoupon.couponSn || null
    let memberCardNo = this.data.orderInfo.defaultMemberCard && this.data.orderInfo.defaultMemberCard.cardNo || null
    if (!addressId) {
      wx.showToast({
        title: '请选择地址',
        icon: 'none'
      })
      return
    }
    goods = goods.filter((item) => {
     return item.isGift !== 1
    })
    let params = {
      goods,
      action: 10,
      orderAmount,
      addressId,
      balance,
      cardBalance,
      scoreDiscount,
      deliverType: this.data.params.deliverType,
      orderPayWay: this.data.choosePayTypeIndex,
      couponSn,
      memberCardNo,
      activityType,
      activityId,
      groupId: this.data.params.groupId
    }
    util.api('/api/wxapp/order/submit', res => {
      if (res.error === 0) {
        let { orderSn } = res.content
        if (this.data.choosePayTypeIndex === 0 && res.content.webPayVo && paymentList.wxpay) {
          wx.requestPayment({
            'timeStamp': res.content.webPayVo.timeStamp,
            'nonceStr': res.content.webPayVo.nonceStr,
            'package': res.content.webPayVo.package,
            'signType': 'MD5',
            'paySign': res.content.webPayVo.paySign,
            'success': (res) => {
              util.toast_success('支付成功');
              util.jumpLink(`pages1/payment/payment${this.getUrlParams({ orderSn, useInfo: JSON.stringify({ ...this.data.usePayInfo }) })}`, 'redirectTo')
            },
            'fail': (res) => {
              console.log(res)
              util.jumpLink(`/pages/orderinfo/orderinfo?orderSn=${orderSn}`, 'redirectTo')
            },
            'complete': (res) => { }
          });
        } else {
          util.jumpLink(`pages1/payment/payment${this.getUrlParams({ orderSn, useInfo: JSON.stringify({ ...this.data.usePayInfo }) })}`, 'redirectTo')
        }
      } else {
        util.showModal('提示', res.message, function () {
          util.jumpLink('/pages/index/index', 'redirectTo')
        });
      }
    }, params)
  },
  //整合参数
  getUrlParams (obj) {
    return Object.keys(obj).reduce((UrlStr, item, index) => {
      if (index !== 0) UrlStr += `&`
      return UrlStr += `${item}=${obj[item]}`
    }, '?')
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

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  }
})