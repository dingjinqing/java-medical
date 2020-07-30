// pages1/prescriptionsheet/prescriptionsheet.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
const {
  orderSn
} = require('../../utils/i18n/pages/order.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    time: '2020-07-23 13:35:01',
    page_name: 'saoyang',
    chatContent: [],
    system_info: '【系统提示】您向医生发起了在线咨询，医生会在24h内按候诊顺序依次接诊，若超过24h未接诊，将为您全额退款，请耐心等待。'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.orderSn) this.requestDetail(options.orderSn)
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
    this.requsetMessage()
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
        let chat = {}
        let chatContent = this.data.chatContent;
        chat.messageInfo = {
          message: JSON.parse(res.content[0].message),
          type: res.content[0].type
        };
        chat.position = 0;
        chatContent.push(chat)
        this.setData({
          chatContent: chatContent
        })
      }
    }, {
      departmentId: 12,
      patientId: 137,
      pullFromId: 1, //doctor_id
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
      departmentId: 12,
      patientId: 137,
      fromId: util.getCache('user_id'), //user_id
      toId: 1, //doctor_id
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
  handleShowPrescriptionDialog(e){
    let {prescriptionCode} = e.currentTarget.dataset
    util.api('/api/wxapp/prescription/details',res=>{
      if(res.error === 0){
        this.setData({
          showPrescription:true,
          prescriptionData:res.content
        })
      }
    },{
      prescriptionCode
    })
  },
  requestDetail(orderSn) {
    let that = this;
    util.api('/api/wxapp/inquiry/order/detail', res => {
      console.log(res)
      if (res.error === 0) {
        let {
          con
        } = res.content
        let patient_message = {
          content: {
            name: con.patientName,
            sex: con.sex != 2 ? (con.sex == 0 ? '男' : '女') : '未知',
            age: con.age,
            mess: con.descriptionDisease
          }
        }
        that.sendMessage(patient_message, 3)
        let imageUrl = con.imageUrl;
        imageUrl.forEach(function (val) {
          let img = {
            content: {
              img: val
            }
          }
          that.sendMessage(img, 1)
        })
      }
    }, {
      orderSn: orderSn,
    })
  }
})