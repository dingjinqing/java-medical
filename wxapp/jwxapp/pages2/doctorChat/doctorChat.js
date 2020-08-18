// pages1/prescriptionsheet/prescriptionsheet.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var chatInput = null
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    // time: '2020-07-23 13:35:01',
    page_name: '',
    prescriptionMessage: null,
    chatContent: [],
    targetUserInfo: {},
    source: null,
    historyPageParams: {
      currentPage: 1,
      pageRows: 20
    },
    firstLoad: true,
    scrollTop: 0,
    arrive_bottom: true,
    allHeight: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    chatInput = this.selectComponent('#chatinput')
    wx.hideShareMenu()
    let { targetUserInfo, source = null } = options
    this.setData({
      targetUserInfo: JSON.parse(targetUserInfo),
      page_name: JSON.parse(targetUserInfo).patientName,
      source
    })
    this.setViewHeight()
  },
  getInputMessage (e) {
    let {
      detail: message
    } = e
    this.sendMessage({ content: message }, 0)
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
    if (this.data.prescriptionMessage) this.sendMessage({ content: this.data.prescriptionMessage }, 2)
    this.requestHistoryChat()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    clearInterval(this.timer)
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    clearInterval(this.timer)
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
  sendImage ({ detail: { image, imgWidth, imgHeight } }) {
    this.sendMessage({ content: image, imgWidth, imgHeight }, 1)
  },
  requsetMessage () {
    this.messageApi()
    if (this.data.targetUserInfo.sessionStatus === 2 || this.data.targetUserInfo.sessionStatus === 5 || this.data.targetUserInfo.sessionStatus === 4) this.timer = setInterval(this.messageApi, 5000)
  },
  messageApi () {
    util.api('/api/wxapp/im/session/pull', res => {
      console.log(res)
      if (res.error === 0 && res.content.messages) {
        let newChatContent = res.content.messages.reduce((defaultValue, item) => {
          defaultValue.push({
            position: 0,
            messageInfo: {
              message: JSON.parse(item.message),
              type: item.type
            }
          })
          return defaultValue
        }, [])
        this.setData({
          chatContent: [...this.data.chatContent, ...newChatContent]
        })
        if (this.data.arrive_bottom == true) {
          this.pageScrollBottom()
        }
        this.getScrollHeight()
      }
    }, {
      sessionId: this.data.targetUserInfo.id,
      pullFromId: this.data.targetUserInfo.userId,
      selfId: util.getCache('doctor_id') || util.getCache('bottom').doctor_id
    }, '', false);
  },
  hideMoreActions () {
    chatInput.hideMoreActions()

  },
  sendMessage (message, type) {

    if (!message.content) return
    let imSessionItem = {
      message: JSON.stringify(message),
      type
    }
    util.api('/api/wxapp/im/session/send', res => {
      console.log(res)
      if (res.error === 0) {
        let chat = {}
        let chatContent = this.data.chatContent;
        chat.messageInfo = {
          ...imSessionItem,
          message: JSON.parse(imSessionItem.message)
        };
        chat.position = 1;
        chatContent.push(chat)
        this.setData({
          chatContent: chatContent,
          prescriptionMessage: null
        })
        this.pageScrollBottom()
      }
    }, {
      sessionId: this.data.targetUserInfo.id,
      fromId: util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
      toId: this.data.targetUserInfo.userId,
      imSessionItem
    })
  },
  createPrescription () {

    util.jumpLink(`pages2/prescribe/prescribe${util.getUrlParams({
      patientId: this.data.targetUserInfo.patientId,
      userId: this.data.targetUserInfo.userId
    })}`)
  },
  chatEnd () {

    util.showModal('提示', '确定要结束本次问诊吗？', () => {
      util.api('/api/wxapp/inquiry/order/status/update', res => {
        if (res.error === 0) {
          clearInterval(this.timer)
          this.setData({
            'targetUserInfo.sessionStatus': 4
          })
          if (this.data.source === 'inquiryList') {
            let pageList = getCurrentPages();
            let prevPage = pageList[pageList.length - 2];
            let targetIndex = prevPage.data.dataList[this.data.targetUserInfo.parentIndex].findIndex(item => item.id === this.data.targetUserInfo.id)
            prevPage.setData({
              [`dataList[${this.data.targetUserInfo.parentIndex}][${targetIndex}].sessionStatus`]: 4
            })
            wx.navigateBack()
          }
        }
      }, {
        orderSn: this.data.targetUserInfo.orderSn,
        orderStatus: 3,
        sessionId: this.data.targetUserInfo.id
      })
    }, true, '再想想', '确认结束')
  },
  chatContinue () {

    util.showModal('提示', '确定要继续问诊吗？', () => {
      util.api('/api/wxapp/inquiry/order/status/update', res => {
        if (res.error === 0) {
          this.setData({
            'targetUserInfo.sessionStatus': 5
          })
          this.requsetMessage()
          if (this.data.source === 'inquiryList') {
            let pageList = getCurrentPages();
            let prevPage = pageList[pageList.length - 2];
            let targetIndex = prevPage.data.dataList[this.data.targetUserInfo.parentIndex].findIndex(item => item.id === this.data.targetUserInfo.id)
            prevPage.setData({
              [`dataList[${this.data.targetUserInfo.parentIndex}][${targetIndex}].sessionStatus`]: 5
            })
          }
        }
      }, {
        orderSn: this.data.targetUserInfo.orderSn,
        orderStatus: 2,
        sessionId: this.data.targetUserInfo.id
      })
    }, true, '再想想', '继续')
  },
  pageScrollBottom () {
    let that = this;
    that.getRectHeight()
  },

  startScroll (e) {
    let that = this;
    let scrollHeight = e.detail.scrollTop + that.data.scrollViewHeight - 10; //20是margin的大概差值
    let allHeight = that.data.scrollHeight;
    let arrive_bottom = that.data.arrive_bottom;
    console.log(scrollHeight, allHeight)
    if (scrollHeight >= allHeight) {
      if (arrive_bottom == true) return
      that.setData({
        arrive_bottom: true
      })
    } else {
      if (arrive_bottom == false) return
      that.setData({
        arrive_bottom: false
      })
    }
  },

  handleShowPrescriptionDialog (e) {
    let { prescriptionCode } = e.currentTarget.dataset
    util.api('/api/wxapp/prescription/details', res => {
      if (res.error === 0) {
        this.setData({
          showPrescription: true,
          prescriptionData: res.content
        })
      }
    }, {
      prescriptionCode
    })
  },
  async requestHistoryChat () {
    if (this.data.firstLoad) await this.historyChatApi()
    this.requsetMessage()
    this.pageScrollBottom()
  },
  historyChatApi () {
    return new Promise((resolve, reject) => {
      util.api('/api/wxapp/im/session/render', res => {
        console.log(res)
        if (res.error === 0 && res.content.dataList.length) {
          let newChatContent = res.content.dataList.reduce((defaultValue, item) => {
            defaultValue.push({
              position: item.doctor ? 1 : 0,
              messageInfo: {
                message: JSON.parse(item.message),
                type: item.type
              }
            })
            return defaultValue
          }, [])
          this.setData({
            chatContent: [...newChatContent],
            firstLoad: false
          })
        }
        resolve(res)
      }, {
        sessionId: this.data.targetUserInfo.id,
        isDoctor: true,
        isFirstTime: this.data.firstLoad
      })
    })
  },
  viewImage (e) {

    let urls = [e.currentTarget.dataset.urls]
    wx.previewImage({
      urls
    })
  },
  setViewHeight () {
    let win_h = wx.getSystemInfoSync().windowHeight;
    let navigation_h;
    if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
      navigation_h = wx.getMenuButtonBoundingClientRect().bottom + 8
    } else {
      wx.getSystemInfo({
        success: (res) => {
          navigation_h = res.statusBarHeight * 3 + 8
        }
      })
    }
    this.setData({
      scrollViewHeight: win_h - navigation_h - this.getBottomHeight(),
    });
  },
  getBottomHeight(){
    if(this.data.targetUserInfo.sessionStatus === 6) return 0;
    return 110
  },
  getRectHeight () {
    let that = this
    wx.createSelectorQuery().select('#all_content').boundingClientRect().exec(function (reda) {
      that.setData({
        allHeight: reda[0].height,
        scrollHeight: reda[0].height
      })
    })
  },
  getScrollHeight () {
    let that = this
    wx.createSelectorQuery().select('#all_content').boundingClientRect().exec(function (reda) {
      that.setData({
        scrollHeight: reda[0].height
      })
    })
  }
})