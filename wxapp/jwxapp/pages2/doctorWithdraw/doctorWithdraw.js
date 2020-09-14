const util = require("../../utils/util")

// pages2/doctorWithdraw/doctorWithdraw.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    withdrawCash:0,
    showRealNameDialog:false,
    realName:''
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
        let {totalMoney,accruingWithDrawCash,waitWithDrawCash,withdrawCashMix,withdrawCashMax,doctorId} = res.content
        this.setData({
          totalMoney:totalMoney || '0.00',
          accruingWithDrawCash:accruingWithDrawCash || '0.00',
          waitWithDrawCash:waitWithDrawCash || '0.00',
          withdrawCashMin:withdrawCashMix || '0.00',
          withdrawCashMax:withdrawCashMax || '0.00',
          doctorId
        })
      }
    })
  },
  takeAllMoney(){
    console.log(this.data.totalMoney)
    this.setData({
      withdrawCash:parseFloat(this.data.totalMoney) 
    })
  },
  changeInput(e){
    let {value:withdrawCash} = e.detail
      this.setData({
        withdrawCash
      })
  },
  takeMoney(){
    if(isNaN(this.data.withdrawCash) || this.data.withdrawCash === 0){
      util.showModal('提示','请输入金额后再试')
      return false
    }
    if (!(/^\d+(\.\d{1,2})?$/.test(this.data.withdrawCash))) {
      util.showModal("提示", "提现金额不能超过2位小数或格式不正确");
      return false;
    }
    if(parseFloat(this.data.withdrawCash) < parseFloat(this.data.withdrawCashMin)){
      util.showModal('提示','提现金额不得小于单次最小提现金额')
      return false
    }
    if(parseFloat(this.data.withdrawCash) > parseFloat(this.data.totalMoney)){
      util.showModal('提示','提现金额不得大于可提现余额')
      return false
    }
    if(this.data.realName === ''){
      this.setData({
        showRealNameDialog:true
      })
      return false
    }
  },
  getRealName(e){
    this.data.realName = e.detail.realName
    util.showModal('提示','确认提现吗？',()=>{
      this.setData({
        showRealNameDialog:false
      })
      util.api('/api/wxapp/doctor/withdraw/apply',res=>{
        console.log(res)
        if(res.error === 0){   
          wx.showModal({
            title: '提示',
            content: '提现申请已提交，请等待管理员审核',
            cancelText: "确定",
            cancelColor: "#333333",
            confirmText: "回到首页",
            confirmColor: "#ff6666",
            success(res) {
              if (res.confirm) {
                util.reLaunch({
                  url: '/pages/index/index',
                })
              } else if (res.cancel) {
                wx.navigateBack({
                  delta: 2
                })
              }
            }
          })
        } else {
          util.showModal("提示", res.message);
          return false;
        }
      },{
        doctorId:this.data.doctorId,
        withdrawCash:this.data.withdrawCash,
        realName:this.data.realName
      })
    },true,'取消','确认提现')
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