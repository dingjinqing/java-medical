let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl("")
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let cardNo = options.card_no ? options.card_no : null
    this.requestCardInfo(cardNo)
  },
  requestCardInfo(cardNo){
    util.api('/api/card/detail',res => {
      let cardInfo = res.content
      cardInfo.cardExpireTime = this.getCardExpireTime(cardInfo);
      cardInfo.cardBgStyle = this.getCardBg(cardInfo);
      cardInfo.cardTypeName = this.getTypeName(cardInfo.cardType);
      cardInfo.buyScore = JSON.parse(cardInfo.buyScore)
      cardInfo.chargeMoney = JSON.parse(cardInfo.chargeMoney)
      cardInfo.storeList = cardInfo.storeList ? JSON.parse(cardInfo.storeList) : []
      this.setData({
        cardInfo:res.content
      })
    }, { cardNo: cardNo })
  },
  // 获取会员卡过期时间
  getCardExpireTime(cardItem) {
    if (cardItem.cardType === 2) return null
    if (cardItem.expireType === 2) return `永久有效`
    if (cardItem.expire === 1) return `此卡已过期，如需继续使用请联系商家`
    return `${cardItem.startDate} 至 ${cardItem.endDate}`
  },
  // 获取会员卡背景
  getCardBg(cardItem) {
    console.log(cardItem);
    switch (cardItem.bgType) {
      case 0:
        return `background-color:${cardItem.bgColor};`;
      case 1:
        return `background:url('${this.data.imageUrl}${cardItem.bgImg}') no-repeat top left / 100% 100%;`;
    }
  },
  // 获取卡类型
  getTypeName(cardType) {
    switch (cardType) {
      case 0:
        return '普通卡';
      case 1:
        return '限次卡';
      case 2:
        return '等级卡';
    }
  },
  // 获取会员卡领取状态
  getCardStatus(cardItem){

  },
  phoneCall: function (e) {
    wx.makePhoneCall({
      phoneNumber: this.data.cardInfo.mobile
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