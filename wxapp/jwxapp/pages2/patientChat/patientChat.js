// pages1/prescriptionsheet/prescriptionsheet.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var chatInput = null
const {
  orderSn,
  orderDetail
} = require('../../utils/i18n/pages/order.js');
const {
  theMaximumClaimLimit
} = require('../../utils/i18n/components/decorate/decorate.js');
const {
  all
} = require('../../utils/i18n/page1/search.js');

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    time: '2020-07-23 13:35:01',
    chatContent: [],
    system_info: '【系统提示】您向医生发起了在线咨询，医生会在24h内按候诊顺序依次接诊，若超过24h未接诊，将为您全额退款，请耐心等待。',
    system_img: false,
    firstLoad: true,
    status: 0,
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
    let {
      orderSn,
      first,
      sessionStatus
    } = options
    this.setData({
      orderSn,
      first,
      sessionStatus
    })
    this.setViewHeight()
  },
  getInputMessage(e) {
    let that = this
    let {
      detail: message
    } = e
    this.sendMessage({
      content: message
    }, 0)
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
    this.requestHistoryChat()
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    clearInterval(this.timer)
    clearInterval(this.statusTimer)
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    clearInterval(this.timer)
    clearInterval(this.statusTimer)
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
  sendImage({
    detail: {
      image,
      imgWidth,
      imgHeight
    }
  }) {
    this.sendMessage({
      content: image,
      imgWidth,
      imgHeight
    }, 1)
  },
  requsetMessage() {
    this.messageApi()
    this.timer = setInterval(this.messageApi, 5000)
  },
  requestStatus() {
    this.statusApi()
    this.statusTimer = setInterval(this.statusApi, 5000)
  },
  messageApi() {
    util.api('/api/wxapp/im/session/pull', res => {
      // console.log(res)
      let sessionStatus = this.data.sessionStatus
      if (res.error === 0 && res.content[0]) {
        let newChatContent = res.content.reduce((defaultValue, item) => {
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
      } else if (res.error === 140004 &&sessionStatus != 4 && sessionStatus != 6) {
        util.showModal('提示', '当前会话已结束', function () {
          util.redirectTo({
            url: 'pages2/doctorConsultation/doctorConsultation?tab=1'
          })
        });
      }
    }, {
      sessionId: this.data.sessionId,
      pullFromId: this.data.doctorId, //doctor_id
      selfId: util.getCache('user_id')
    }, '', false);
  },
  statusApi() {
    let sessionId = this.data.sessionId;
    let url = `/api/wxapp/im/session/status/${sessionId}`
    util.api(url, res => {
      // console.log(res)
      if (res.error === 0) {
        if (this.data.status != res.content) {
          let newChatContent = [];
          newChatContent.push({
            position: 1,
            status: res.content
          })
          this.setData({
            chatContent: [...this.data.chatContent, ...newChatContent],
            status: res.content
          })
        }
        if (res.content === 2 || res.content === 4 || res.content == 6) clearInterval(this.statusTimer)
      } else {
        clearInterval(this.statusTimer)
      }
    });
  },
  hideMoreActions() {
    chatInput.hideMoreActions()
  },
  sendMessage(message, type) {
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
          chatContent: chatContent
        })
        this.pageScrollBottom()
      }
    }, {
      sessionId: this.data.sessionId,
      fromId: util.getCache('user_id'), //user_id
      toId: this.data.doctorId, //doctor_id
      imSessionItem
    })
  },
   pageScrollBottom() {
    let that = this;
    that.getRectHeight()
  },

  startScroll(e) {
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

  handleShowPrescriptionDialog(e) {
    let {
      prescriptionCode
    } = e.currentTarget.dataset
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
  requestDetail() {
    let that = this;
    return new Promise((resolve, reject) => {
      util.api('/api/wxapp/inquiry/order/detail', res => {
        console.log(res)
        if (res.error === 0) {
          let con = res.content;
          let patient_message = {
            content: {
              name: con.patientName,
              sex: con.patientSex != 2 ? (con.patientSex == 0 ? '男' : '女') : '未知',
              age: con.patientAge,
              mess: con.descriptionDisease
            },
            system: false
          }
          that.setData({
            page_name: con.doctorName,
            doctorId: con.doctorId,
            time: con.createTime
          })
          if (that.data.first) {
            let imageUrl = JSON.parse(con.imageUrl);
            if (imageUrl != '') {
              patient_message.system = true
              that.sendMessage(patient_message, 3)
              imageUrl.forEach(function (val, index) {
                let img = {
                  content: val.imageUrl,
                  imgWidth: val.imageWidth,
                  imgHeight: val.imageHeight
                }
                if (index == imageUrl.length - 1) {
                  img.system = true;
                }
                that.sendMessage(img, 1)
              })
            } else {
              that.sendMessage(patient_message, 3)
            }
          }

        }
        resolve(res)
      }, {
        orderSn: that.data.orderSn,
      })
    })
  },
  requestSessionId() {
    let that = this;
    return new Promise((resolve, reject) => {
      util.api('/api/wxapp/im/session/get/orderSn',
        async res => {
          console.log(res)
          if (res.error === 0) {
            that.setData({
              sessionId: res.content
            })
            if (that.data.firstLoad) await that.requestDetail(orderSn)
            resolve(res)
          }
        }, {
          orderSn: this.data.orderSn,
        })
    })
  },
  async requestHistoryChat() {
    let resData = await this.requestSessionId()
    if (resData && this.data.firstLoad) await this.historyChatApi()
    this.requsetMessage()
    this.requestStatus()
    this.pageScrollBottom()
  },
  historyChatApi() {
    return new Promise((resolve, reject) => {
      util.api('/api/wxapp/im/session/render', res => {
        console.log(res)
        if (res.error === 0 && res.content.dataList.length) {
          let newChatContent = res.content.dataList.reduce((defaultValue, item) => {
            defaultValue.push({
              position: item.doctor ? 0 : 1,
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
        sessionId: this.data.sessionId,
        isDoctor: false,
        isFirstTime: this.data.firstLoad
      })
    })
  },
  viewImage(e) {
    let urls = [e.currentTarget.dataset.urls]
    wx.previewImage({
      urls
    })
  },
  setViewHeight() {
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
      scrollViewHeight: win_h - navigation_h - 55,
    });
  },
  getRectHeight() {
    let that = this
      wx.createSelectorQuery().select('#all_content').boundingClientRect().exec(function (reda) {
        that.setData({
          allHeight: reda[0].height,
          scrollHeight:reda[0].height
        })
      })
  },
  getScrollHeight(){
    let that = this
      wx.createSelectorQuery().select('#all_content').boundingClientRect().exec(function (reda) {
        that.setData({
          scrollHeight:reda[0].height
        })
      })
  }
})