// components/popup/lottery-award-dialog/index.js
var util = require("../../../utils/util.js");
var base = require('../base/base.js');
var app = getApp()
var imageUrl = app.globalData.imageUrl;
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    prizeInfo: Object,
    btnstatus: Number,
    score: Number,
    lotteryId: Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    show: false,
    imageUrl: imageUrl,
    title: '中奖啦!',
    lotteryInfo: {}
  },

  observers: {
    prizeInfo: function(info) {
      if (info !== null) {
        this.setData({
          title: info.prizeText,
          lotteryInfo: info
        })
      }
    }
  },

  /**
   * 组件的方法列表
   */
  methods: {
    goToOrder () {
      let info = this.data.lotteryInfo
      let goodsList = [{
        goodsId: 94,
        prdRealPrice: 0,
        goodsNum: 1,
        prdId: 7563,
        isCart: 0
      }]
      util.navigateTo({
        url: "/pages/checkout/checkout?activityType=24&activityId=" + Number(info.prizeId) + "&goodsList=" + JSON.stringify(goodsList)
      })
    },
    goLotteryList () {
      util.navigateTo({
        url: '/pages1/lotteryrule/lotteryrule?lotteryId=' + this.data.lotteryId
      })
    }
  },
  show: function() {
    console.log(this)
  }
})
