const util = require("../../utils/util")
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requestWithDrawInfo()
  },
  requestWithDrawInfo(){
    util.api('/api/wxapp/doctor/rebate/total',res=>{
      if(res.error === 0){
        let {totalMoney,accruingWithDrawCash,waitWithdrawCash,withdrawCashMax,withdrawCashMix} = res.content
        this.setData({
          totalMoney:totalMoney || '0.00',
          accruingWithDrawCash:accruingWithDrawCash || '0.00',
          waitWithdrawCash:waitWithdrawCash || '0.00',
          withdrawCashMin:withdrawCashMix || '0.00',
          withdrawCashMax:withdrawCashMax || '0.00'
        })
      }
    })
  },
  viewDetail(){
    util.jumpLink('pages2/withdrawInfo/withdrawInfo')
  },
  withDrawInfo(){
    util.jumpLink('pages2/doctorWithdraw/doctorWithdraw')
  },
  viewRecord(){
    util.jumpLink(`pages2/doctorWithdrawRecord/doctorWithdrawRecord${util.getUrlParams({
      accruingWithDrawCash:this.data.accruingWithDrawCash
    })}`)
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