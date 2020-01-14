// 幸运大抽奖：pages1/lottery/lottery.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var defaultGiftIcon = imageUrl + '/image/admin/icon_lottery/1.png';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    bgImg: imageUrl + '/image/wxapp/lo_bg4.jpg',
    rightIcon: imageUrl+ '/image/wxapp/go_cou_list.png',
    defaultGiftIcon: imageUrl + '/image/admin/icon_lottery/1.png',
    broadcastIcon: imageUrl + '/image/wxapp/lo_words.png',
    lotteryInfo: {},
    rawards: [
      { name: '四等奖', path: defaultGiftIcon },
      { name: '谢谢参与', path: defaultGiftIcon},
      { name: '三等奖', path: defaultGiftIcon},
      { name: '一等奖', path: defaultGiftIcon},
      { name: '二等奖', path: defaultGiftIcon},
      { name: '四等奖', path: defaultGiftIcon},
      { name: '三等奖', path: defaultGiftIcon},
      { name: '四等奖', path: defaultGiftIcon},
      { name: '二等奖', path: defaultGiftIcon}
    ],
    isCover: true,
    winIndex: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 初始化奖品，拿到奖品数据，加上谢谢参与
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