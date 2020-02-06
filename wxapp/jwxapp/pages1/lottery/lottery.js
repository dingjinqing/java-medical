// 幸运大抽奖：pages1/lottery/lottery.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var thanksGiftIcon = imageUrl + '/image/admin/icon_lottery/thank.png';
var firstGiftIcon = imageUrl + '/image/admin/icon_lottery/1.png';
var secondGiftIcon = imageUrl + '/image/admin/icon_lottery/2.png';
var thirdGiftIcon = imageUrl + '/image/admin/icon_lottery/3.png';
var fourthGiftIcon = imageUrl + '/image/admin/icon_lottery/4.png';
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
    userInfo: {},
    rawards: [
      { name: '四等奖', path: fourthGiftIcon },
      { name: '谢谢参与', path: thanksGiftIcon},
      { name: '三等奖', path: thirdGiftIcon},
      { name: '一等奖', path: firstGiftIcon},
      { name: '二等奖', path: secondGiftIcon},
      { name: '四等奖', path: fourthGiftIcon},
      { name: '三等奖', path: thirdGiftIcon},
      { name: '四等奖', path: fourthGiftIcon},
      { name: '二等奖', path: secondGiftIcon}
    ],
    isCover: false, // 是否遮罩
    winIndex: 1, // 哪个选中（0~8）
    minturns: 2, // 最少转几圈(当为2时，最少转1圈)
    speed: 100, // 抽奖转动速度
    awardDialogVisible: false, // 奖品弹窗
    lotteryId: '', 
    lotteryType: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 初始化奖品，拿到奖品数据，加上谢谢参与
    let that = this
    let lotteryId = options.lotteryId
    that.setData({
      lotteryId: lotteryId
    })
    // 可以获取到进入小程序的场景值，例如从分享卡片进来的
    let res = wx.getEnterOptionsSync()
    console.log(options)
    console.log(res)
    util.api('/api/wxapp/lottery/get', function(res) {
      console.log(res)
      if (res.error === 0) {
        let content = res.content
        that.initRawards(content.lotteryInfo.prizeList)
        that.setData({
          lotteryInfo: content.lotteryInfo,
          userInfo: content.lotteryUserTimeInfo
        })
      }
    }, {id: lotteryId})
  },

  initRawards (rawards) {
    let lotteryRawards = []
    for(let i = 0; i<9; i++) {
      switch(i) {
        case 0:
        case 5:
        case 7:
          lotteryRawards.push({
            name: rawards[3].iconImgs,
            path: imageUrl + rawards[3].iconImgsImage
          })
          break
        case 1:
          lotteryRawards.push({
            name: '谢谢参与',
            path: thanksGiftIcon
          })
          break
        case 2:
        case 6:
          lotteryRawards.push({
            name: rawards[2].iconImgs,
            path: imageUrl + rawards[2].iconImgsImage
          })
          break
        case 3:
          lotteryRawards.push({
            name: rawards[0].iconImgs,
            path: imageUrl + rawards[0].iconImgsImage
          })
          break
        case 4:
        case 8:
          lotteryRawards.push({
            name: rawards[1].iconImgs,
            path: imageUrl + rawards[1].iconImgsImage
          })
          break
      }
    }
    console.log(lotteryRawards)
    this.setData({
      rawards: lotteryRawards
    })
  },

  // 立即抽奖
  drawNow () {
    // 抽奖动画
    let that = this
    let startStep = this.data.winIndex;
    let endStep = parseInt(Math.random()*8+1);
    console.log(startStep, endStep);
    this.rolling(startStep, endStep)
    util.api('/api/wxapp/lottery/join', function(res) {
      if (res.error === 0) {
        console.log(res.content)
      }
    },{
      lotteryId: Number(that.data.lotteryId),
      lotteryType: that.data.lotteryType
    })
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
  onShow: function (res) {
    console.log(res)
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

  /**
   * 监听用户某些操作触发的事件，用于统计用途，目前仅支持转发事件
   */
  onUserOpStatistic: function (e) {
    if (e.op == 'share') {
      console.log(e)
    }
  }
})