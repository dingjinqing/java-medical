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
    chatContent:[],
    system_info:'【系统提示】您向医生发起了在线咨询，医生会在24h内按候诊顺序依次接诊，若超过24h未接诊，将为您全额退款，请耐心等待。'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  getInputMessage(e) {
    let that = this
    let {
      detail: message
    } = e
    this.sendMessage(message,0)
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
  sendImage({detail:{image}}){
    this.sendMessage(image,1)
  },
  requsetMessage () {
    this.messageApi()
    this.timer = setInterval(this.messageApi,5000)
  },
  messageApi () {
    util.api('/api/wxapp/im/session/pull', res => {
      console.log(res)
      if (res.error === 0 && res.content[0]) {
          let chat = {}
          let chatContent = this.data.chatContent;
          chat.message = res.content[0].message;
          chat.type = 0;
          chatContent.push(chat)
          this.setData({
            chatContent:chatContent
          })
      }
    }, {
      departmentId: 12,
      patientId: 3,
      pullFromId: 1,
      selfId: 2
    }, '', false);
  },
  hideMoreActions(){
    let chatInput = this.selectComponent('#chatinput')
    chatInput.hideMoreActions()
  },
  sendMessage(message,type){
    if(!message) return
    let imSessionItem = {
      message,
      type
    }
    util.api('/api/wxapp/im/session/send',res => {
      console.log(res)
      if (res.error === 0 ) {
        let chat = {}
        let chatContent = this.data.chatContent;
        chat.messageInfo = imSessionItem;
        chat.type = 1;
        chatContent.push(chat)
        this.setData({
          chatContent:chatContent
        })
      }
    },{
      departmentId:12,
      patientId:3,
      fromId:2,
      toId:1,
      imSessionItem
    })
  },
})