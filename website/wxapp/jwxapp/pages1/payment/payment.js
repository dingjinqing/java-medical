var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    goodsType: 1
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {
      orderSn = null, useInfo, goodsType = 1, cardNo = null
    } = options
    this.setData({
      test: {
        moneyPaid: this.$t("pages.order.payCode.wxpay"),
        useCardBalance: this.$t("pages.order.payCode.cardBalance"),
        useScore: this.$t("pages.order.payCode.score"),
        useBalance: this.$t("pages.order.payCode.balance")
      },
      orderSn,
      useInfo: JSON.parse(useInfo),
      goodsType: +goodsType,
      hasUseInfo: this.getUseInfo(JSON.parse(useInfo)),
      cardNo
    })
    this.setData({
      buttonName: this.getButtonData(+goodsType)
    })
    this.selectComponent('#recommend').requestData()
    this.payGiftRequest()
  },
  checkOrder() {
    let goodsType = this.data.goodsType
    switch (goodsType) {
      case 2:
        util.jumpLink(`pages/coupon/coupon`, 'navigateTo')
        break;
      case 3:
        util.jumpLink(`pages/cardinfo/cardinfo?cardNo=${this.data.cardNo}`, 'navigateTo')
        break;
      default:
        util.jumpLink(`/pages/orderinfo/orderinfo?orderSn=${this.data.orderSn}`, 'navigateTo')
        break;
    }
  },
  goHome() {
    util.jumpLink(`/pages/index/index`, 'redirectTo')
  },
  payGiftRequest() {
    if (!this.data.orderSn) return
    util.api('/api/wxapp/payaward/prize/info', res => {
      if (res.error === 0 && res.content) {
        let awardInfo = this.getAwardInfo(res.content)
        this.setData({
          payAwardDialog: true,
          awardInfo
        })
      } else {

      }
    }, {
      orderSn: this.data.orderSn
    })
  },
  getUseInfo(useInfo) {
    if(Object.values(useInfo).some(item=>item > 0)) return true
    return false
  },
  getAwardInfo({
    currentAwardTimes: currentStep,
    payAwardSize: totalStep,
    payAwardPrize: awardInfo,
    message
  }) {
    const needParams = {
      0: [null],
      1: ['couponView'],
      2: ['couponView'],
      3: ['lotteryId'],
      4: ['account'],
      5: ['product', 'productId', 'keepDays'],
      6: ['scoreNumber'],
      7: ['customImage', 'customLink'],
    }
    let stepInfo = {
      hasStep: (totalStep > 1),
      totalStep,
      currentStep
    }
    return {
      stepInfo,
      message,
      giftInfo: {
        giftType: awardInfo.giftType,
        awardInfo: {
          ...this.filterObj(awardInfo, needParams[awardInfo.giftType])
        }
      }
    }
  },
  // 过滤需要的参数
  filterObj(obj, arr) {
    if (typeof obj !== "object" || !Array.isArray(arr)) {
      throw new Error("参数格式不正确");
    }
    const result = {};
    Object.keys(obj)
      .filter(key => arr.includes(key))
      .forEach(key => {
        result[key] = obj[key];
      });
    return result;
  },
  getButtonData(goodsType) {
    if(goodsType === 3 && !this.data.cardNo) return null
    switch (goodsType) {
      case 1:
        return this.$t("pages.order.viewOrder")
      case 2:
        return this.$t("pages.order.viewCouponList")
      case 3:
        return this.$t("pages.order.viewCardInfo")
    }
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.selectComponent('#recommend').requestData()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})