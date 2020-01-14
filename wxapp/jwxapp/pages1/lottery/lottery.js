// 幸运大抽奖：pages1/lottery/lottery.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var defaultGiftIcon = imageUrl + '/image/admin/icon_lottery/1.png';
global.wxPage({

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
    isCover: false, // 是否遮罩
    winIndex: 1, // 哪个选中（0~8）
    minturns: 2, // 最少转几圈(当为2时，最少转1圈)
    speed: 100, // 抽奖转动速度
    awardDialogVisible: false, // 奖品弹窗
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 初始化奖品，拿到奖品数据，加上谢谢参与
  },

  // 立即抽奖
  drawNow () {
    // 抽奖动画
    let startStep = this.data.winIndex;
    let endStep = parseInt(Math.random()*8+1);
    console.log(startStep, endStep);
    this.rolling(startStep, endStep)
  },

  /**
   * 抽奖动画
   * params(开始位置（0~8），结束位置（0~8))
   */
  rolling(startStep, endStep) {
    let that = this;
    // 从winIndex开始，总共要走多少步
    let totalStep = that.data.minturns *9 + endStep - startStep;
    // 已经走了多少步
    let hasRunStep = 0;
    that.setData({
      isCover: true
    });
    let timer = setInterval(function() {
      hasRunStep++;
      startStep++;
      if(startStep >= 9) {
        startStep = 0;
      }
      that.setData({
        winIndex: startStep
      })
      if (hasRunStep === totalStep) {
        // 动画停止
        clearInterval(timer)
        // 中奖弹窗
        that.hitTheJackpot()
      }
    }, that.data.speed)
  },

  // 中奖弹窗
  hitTheJackpot () {
    this.setData({
      awardDialogVisible: true
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