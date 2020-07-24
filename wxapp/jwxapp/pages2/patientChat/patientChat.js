// pages1/prescriptionsheet/prescriptionsheet.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    time: '2020-07-23 13:35:01',
    page_name: 'saoyang',
    chatContent:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.requsetMessage()
  },
  getInputMessage(e) {
    let that = this
    let {
      detail: message
    } = e
    console.log(message)
    that.sendMessage(message)
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
  sendMessage: function (message) {
    let that = this;
    if (message == '') return;
    let imSessionItem = {
      "type": 0,
      "message": ""
    }
    imSessionItem.message = message;
    util.api('/api/wxapp/im/session/send', res => {
      console.log(res)
      if (res.error === 0 ) {
        let chat = {}
        let chatContent = that.data.chatContent;
        chat.message = message;
        chat.type = 1;
        chatContent.push(chat)
        that.setData({
          chatContent:chatContent
        })
      }
    }, {
      departmentId: 12,
      patientId: 3, //患者
      fromId: 2, //小程序用户
      toId: 1, //to医生
      imSessionItem: imSessionItem
    }, '', false);
  },
  requsetMessage () {
    this.messageApi()
    this.timer = setInterval(this.messageApi,15000)
  },
  messageApi () {
    util.api('/api/wxapp/im/session/pull', res => {
      console.log(res)
      if (res.error === 0 && res.content.message) {
          let chat = {}
          let chatContent = that.data.chatContent;
          chat.message = res.content.message;
          chat.type = 0;
          chatContent.push(chat)
          that.setData({
            chatContent:chatContent
          })
      }
    }, {
      departmentId: 12,
      patientId: 3,
      pullFromId: 2,
      selfId: 1
    }, '', false);
  }
})