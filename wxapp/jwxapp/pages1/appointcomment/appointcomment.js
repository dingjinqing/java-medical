// pages1/appointcomment/appointcomment.js
var util = require('../../utils/util.js');
var app = getApp()
var imageUrl = app.globalData.imageUrl;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    serviceId: '',
    activeStyle: '',
    serviceType: 0, // 0 全部 1好评 2中评 3差评
    comment: [],
    numbers: [0,0,0,0],
    ratio: [0,0,0],
    nick_avatar: imageUrl + ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    if (!util.check_setting(options)) return;
    this.setData({
      activeStyle: 'border-bottom:2rpx solid ' + this.data.comColor + ';color:' + this.data.comColor + ';',
      serviceId: options.serviceId
    })
    this.initCommentData()
  },

  initCommentData () {
    let that = this;
    util.api('/api/wxapp/store/service/allComment',function(res){
      if (res.error === 0) {
        let content = res.content
        let comment = content.comment
        if (comment && comment.length>0) {
          comment.forEach(item => {
            if(item.commImg) {
              item.commImg = JSON.parse(item.commImg)
            }
          })
        }
        that.setData({
          comment:comment,
          numbers:content.numbers,
          ratio:content.ratio
        })
      }
    }, {serviceId: this.data.serviceId, type: this.data.serviceType})
  },

  change_com (e) {
    console.log(e)
    let type = Number(e.currentTarget.dataset.type)
    this.setData({
      serviceType: type
    })
    this.initCommentData()
  },

  clickComment(e) {
    console.log(e)
    let src = e.currentTarget.dataset.src
    let list = e.currentTarget.dataset.all
    wx.previewImage({
      current: src,
      urls: list
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