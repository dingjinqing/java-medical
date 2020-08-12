var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    if_focus: false,
    star: [{
        show: true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      }
    ],
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

  },
  choose_focus(){
    this.setData({
      if_focus:!this.data.if_focus
    })
  },
  choose_star(e) {
    let idx = e.currentTarget.dataset.idx;
    let star = this.data.star;
    star.forEach((item, index) => {
      if (index <= idx) {
        item.show = true
      } else {
        item.show = false
      }
    })
    this.setData({
      star: star
    })
  }

})