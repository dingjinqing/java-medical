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
    noAwardDialogVisible: false, // 未中奖弹窗
    lotteryId: '', 
    lotteryType: '', // 抽奖类型（抽奖次数来源） 1免费,2分享,3积分
    lotterySource: 3, // 活动来源 1开屏有礼,2支付有礼,3分享,4评价有礼,5分享有礼
    btnstatus: 1, // 按钮状态 1立即抽奖，2去分享，3消耗积分抽奖，4抽奖次数用光啦
    prizeInfo: null // 奖品信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 初始化奖品，拿到奖品数据，加上谢谢参与
    let that = this
    let lotteryId = options.lotteryId
    let lotterySource = options.lotterySource || 3
    this.setData({
      lotteryId: lotteryId,
      lotterySource: lotterySource
    })
    // 可以获取到进入小程序的场景值，例如从分享卡片进来的
    let res = wx.getEnterOptionsSync()
    console.log('场景值：', res)
    this.lotteryRequest()
  },

  // 请求抽奖信息
  lotteryRequest() {
    let that = this
    util.api('/api/wxapp/lottery/get', function (res) {
      console.log(res)
      if (res.error === 0) {
        let content = res.content
        that.initRawards(content.lotteryInfo.prizeList)
        that.initRaffleButton(content.lotteryUserTimeInfo)
        that.setData({
          lotteryInfo: content.lotteryInfo,
          userInfo: content.lotteryUserTimeInfo
        })
      } else {
        util.showModal('提示', res.message, function () {
          wx.navigateBack({})
        })
      }
    }, { id: that.data.lotteryId })
  },

  // 初始化奖品
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

  /**
   * 初始化抽奖按钮
   * 1.免费抽奖
   * 2.分享抽奖
   * 3.积分抽奖
   * 4.不能抽奖
   */
  initRaffleButton(userInfo) {
    let raffleInfo = userInfo
    let btnstatus = 4
    let canFreeTime = raffleInfo.freeTime - raffleInfo.usedFreeTime
    let canShareTime = 0
    if (raffleInfo.shareTime < raffleInfo.shareMaximum) {
      canShareTime = raffleInfo.shareTime - raffleInfo.usedShareTime
    } else {
      canShareTime = raffleInfo.shareMaximum - raffleInfo.usedShareTime
    }
    console.log('canFreeTime:', canFreeTime, 'canShareTime', canShareTime)
    if (canFreeTime + canShareTime > 0) {
      btnstatus = 1
    } else if (raffleInfo.shareMaximum > raffleInfo.shareTime && canShareTime === 0) {
      btnstatus = 2
    } else if (raffleInfo.scoreMaximum > raffleInfo.usedScoreTime) {
      btnstatus = 3
    } else {
      btnstatus = 4
    }
    this.setData({
      btnstatus: btnstatus
    })
  },

  // 立即抽奖
  drawNow () {
    let that = this
    util.api('/api/wxapp/lottery/join', function (res) {
      if (res.error === 0 && res.content.flag) {
        console.log(res.content)
        let content = res.content
        // 是否抽奖成功
        if (content.flag) {
          // 抽奖动画
          let startStep = that.data.winIndex;
          let endStep = parseInt(Math.random() * 8 + 1);
          console.log(startStep, endStep);
          switch (content.lotteryGrade) {
            case 1:
              endStep = 3
              break
            case 2:
              let steps2 = [4, 8]
              let random2 = parseInt(Math.random() * 2)
              endStep = steps2[random2]
              break
            case 3:
              let steps3 = [2, 6]
              let random3 = parseInt(Math.random() * 2)
              endStep = steps3[random3]
              break
            case 4:
              let steps4 = [0, 5, 7]
              let random4 = parseInt(Math.random() * 3)
              endStep = steps4[random4]
            default:
              endStep = 1
          }
          that.rolling(startStep, endStep, content)
        } else {
          that.$message.error(content.msg)
        }
      } else {
        that.$message.error(res.message)
      }
    }, {
        lotteryId: Number(that.data.lotteryId),
        lotteryType: that.data.lotteryType,
        lotterySource: that.data.lotterySource
      }
    )
    
  },

  /**
   * 抽奖动画
   * params(开始位置（0~8），结束位置（0~8), 奖品信息)
   */
  rolling(startStep, endStep, prizeInfo) {
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
        // 弹出奖励
        if (prizeInfo.flag && prizeInfo.lotteryGrade > 0) {
          that.hitTheJackpot(prizeInfo)
        } else {
          that.noDraw()
        }
      }
    }, that.data.speed)
  },

  // 未中奖弹窗
  noDraw() {
    this.setData({
      noAwardDialogVisible: true
    })
  },

  // 中奖弹窗
  hitTheJackpot () {
    this.setData({
      awardDialogVisible: true
    })
  },

  // 抽奖记录
  toList () {
    util.navigateTo({
      url: '/pages1/lotteryrule/lotteryrule?lotteryId=' + this.data.lotteryId,
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
    let that = this
    let username = util.getCache('nickName')
    if (username === "" || username === null) {
      username = "神秘的小伙伴"
    }
    util.api('/api/wxapp/lottery/share', function(res) {
      if (res.error === 0) {
        that.lotteryRequest()
      }
    }, { lotteryId: that.data.lotteryId })
    return {
      path: '/pages1/lottery/lottery?lotteryId='+ that.data.lotteryId,
      title: username + '邀你免费拿大奖，限时免费立即参加吧！！！',
      imageUrl: imageUrl + '/image/wxapp/share_lott1.jpg'
    }
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