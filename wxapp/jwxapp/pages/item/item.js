// pages/item/item.js
var util = require("../../utils/util.js")
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imgUrls:[
      '../../images/arrow.png',
      '../../images/backward.png',
      '../../images/comment_up.png',
      '../../images/fail.png',
      '../../images/map_icon.png',
      '../../images/reply_up.png'
    ],
    goodsInfo:{}
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  backHome() {
    util.jumpLink('pages/index/index', 'navigateTo')
  },
  showCouponDialog() {
    this.setData({
      show_recommend_coupons:true
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  preview(e) {
    let nowImgUrl = e.target.dataset.src;
    let imgUrls = this.data.imgUrls
    let arr = [];
    for (let i in imgUrls) {
      arr.push(imgUrls[i]); //属性
    }
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
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