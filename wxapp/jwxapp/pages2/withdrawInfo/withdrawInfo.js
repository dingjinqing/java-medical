const util = require("../../utils/util")
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    title_bgColor:'#26C4BC',
    activeStyle1:'border-radius:16rpx 16rpx 0 0;background-color:#fff',
    activeStyle2:'border-radius:0 16rpx 0 16rpx;background-color:#f5f5f5',
    activeType:'1',
    targetStatus:'1'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  changeType(e){
    let {type} = e.currentTarget.dataset
    this.data.activeType = type
    if(type === '1'){
      this.setData({
        activeStyle1:'border-radius:16rpx 16rpx 0 0;background-color:#fff',
        activeStyle2:'border-radius:0 16rpx 0 16rpx;background-color:#f5f5f5'
      })
    } else if (type === '2') {
      this.setData({
        activeStyle1:'border-radius:16rpx 0 16rpx 0;background-color:#f5f5f5',
        activeStyle2:'border-radius:16rpx 16rpx 0 0;background-color:#fff'
      })
    }
  },
  toggleStatus(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      targetStatus:type
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