// pages1/prescriptionsheet/prescriptionsheet.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
const {
  orderSn,
  orderDetail
} = require('../../utils/i18n/pages/order.js');
const {
  theMaximumClaimLimit
} = require('../../utils/i18n/components/decorate/decorate.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    time: '2020-07-23 13:35:01',
    chatContent: [],
    system_info: '【系统提示】您向医生发起了在线咨询，医生会在24h内按候诊顺序依次接诊，若超过24h未接诊，将为您全额退款，请耐心等待。',
    system_img: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.hideShareMenu()
    let {orderSn} = options
    this.setData({
      orderSn
    })
    //get sessionId
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
  messageApi() {
    util.api('/api/wxapp/im/session/pull', res => {
      console.log(res)
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
      }
    }, {
      sessionId: this.data.sessionId,
      pullFromId: this.data.doctorId, //doctor_id
      selfId: util.getCache('user_id')
    }, '', false);
  },
  hideMoreActions() {
    let chatInput = this.selectComponent('#chatinput')
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
    wx.createSelectorQuery().select('.main-container').boundingClientRect(function (rect) {
      console.log(rect);
      wx.pageScrollTo({
        scrollTop: rect.height,
      });
    }).exec()
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
    return new Promise((resolve,reject) => {
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
            }
          }
          that.setData({
            page_name: con.doctorName,
            doctorId: con.doctorId
          })
          let imageUrl = JSON.parse(con.imageUrl);
          if (imageUrl != '') {
            that.setData({
              system_img: true
            })
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
        resolve(res)
      }, {
        orderSn: this.data.orderSn,
      })
    })
  },
  requestSessionId() {
    let that = this;
    return new Promise((resolve,reject)=>{
      util.api('/api/wxapp/im/session/get/orderSn',
      async res => {
        console.log(res)
        if (res.error === 0) {
          that.setData({
            sessionId: res.content
          })
         let resData = await that.requestDetail(orderSn)
         if (resData) resolve(res)
        }
      }, {
        orderSn: this.data.orderSn,
      })
    })
  },
  async requestHistoryChat(){
    let resData = await this.requestSessionId()
    if(resData) await this.historyChatApi()
    this.requsetMessage()
  },
  historyChatApi(){
    return new Promise((resolve,reject)=>{
      util.api('/api/wxapp/im/session/render',res=>{
        console.log(res)
        if(res.error === 0 && res.content.dataList.length){
          let newChatContent = res.content.dataList.reduce((defaultValue,item)=>{
            defaultValue.push({  
              position:item.doctor ? 0 : 1,
              messageInfo:{
                message:JSON.parse(item.message),
                type:item.type
              }
            })
            return defaultValue
          },[])
          this.setData({
            chatContent:[...newChatContent]
          })
        }
        resolve(res)
      },{
        sessionId:this.data.sessionId
      })
    }) 
  },
  viewImage(e){
    let urls = [e.currentTarget.dataset.urls]
    wx.previewImage({
      urls
    })
  }
})