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
    // this.requsetMessage()
  },
  getInputMessage(e) {
    let {
      detail: message
    } = e
    this.sendMessage({content:message},0)
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
  sendImage({detail:{image,imgWidth,imgHeight}}){
    this.sendMessage({content:image,imgWidth,imgHeight},1)
  },
  requsetMessage () {
    this.messageApi()
    this.timer = setInterval(this.messageApi,2000)
  },
  messageApi () {
    util.api('/api/wxapp/im/session/pull', res => {
      console.log(res)
      if (res.error === 0 && res.content[0]) {
        let chat = {}
        let chatContent = this.data.chatContent;
        chat.messageInfo = {
          message:JSON.parse(res.content[0].message),
          type:res.content[0].type
        };
        chat.type = 0;
        chatContent.push(chat)
        this.setData({
          chatContent:chatContent
        })
    }
    }, {
      departmentId: 12,
      patientId: 3,
      pullFromId: 2,
      selfId: 1
    }, '', false);
  },
  hideMoreActions(){
    let chatInput = this.selectComponent('#chatinput')
    chatInput.hideMoreActions()
  },
  sendMessage(message,type){
    if(!message.content) return
    let imSessionItem = {
      message:JSON.stringify(message),
      type
    }
    util.api('/api/wxapp/im/session/send',res => {
      console.log(res)
      if (res.error === 0 ) {
        let chat = {}
        let chatContent = this.data.chatContent;
        chat.messageInfo = {
          ...imSessionItem,
          message:JSON.parse(imSessionItem.message)
        };
        chat.type = 1;
        chatContent.push(chat)
        this.setData({
          chatContent:chatContent
        })
      }
    },{
      departmentId:12,
      patientId:3,
      fromId:1,
      toId:2,
      imSessionItem
    })
  }
})