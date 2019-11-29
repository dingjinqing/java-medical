// pages1/appointlist/appointlist.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    tabStatus: [
      { name: '全部预约', status: '', num: '' },
      { name: '待付款', status: 0, num: '' },
      { name: '待服务', status: 1, num: '' },
      { name: '已取消', status: 2, num: '' },
      { name: '已完成', status: 3, num: '' }
    ],
    activeType: 'all'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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