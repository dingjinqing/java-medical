var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    test:{
      moneyPaid:'微信支付',
      useCardBalance:'会员卡余额支付',
      useScore:'积分支付',
      useBalance:'余额支付'
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {orderSn,useInfo} = options
    this.setData({
      orderSn,
      useInfo:JSON.parse(useInfo)
    })
    this.payGiftRequest()
  },
  checkOrder(){
    util.jumpLink(`/pages/orderinfo/orderinfo?orderSn=${this.data.orderSn}`,'navigateTo')
  },
  goHome(){
    util.jumpLink(`/pages/index/index`,'redirectTo')
  },
  payGiftRequest(){
    util.api('/api/wxapp/payaward/prize/info',res=>{
      if(res.error === 0 && res.content){
        let {show} = res.content
        let awardInfo = this.getAwardInfo(res.content)
        this.setData({
          payAwardDialog:true,
          awardInfo
        })
      } else {

      }
    },{orderSn:this.data.orderSn})
  },
  getAwardInfo({currentAwardTimes:currentStep,payAwardSize:totalStep,payAwardPrize:awardInfo}){
    const needParams = {
      0:null,
      1:['couponView'],
      2:['couponView'],
      3:['lotteryName','lotteryId'],
      4:['accountNumber'],
      5:['product'],
      6:['scoreNumber'],
      7:['customImage','customLink'],
    }
    let stepInfo = {
      hasStep: (totalStep > 1),
      totalStep,
      currentStep
    }
    return {
      stepInfo,
      giftInfo:{
        giftType:awardInfo.giftType,
        awardInfo:{
          ...this.filterObj(awardInfo,needParams[awardInfo.giftType])
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