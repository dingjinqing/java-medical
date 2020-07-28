// pages1/messagelist/messagelist.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_system: 1,
    is_order: 0,
    is_advisory: 0,
    // 系统公告列表
    system_notice: [],
    // 订单动态列表
    order_message: [],
    // 我的咨询列表
    advisory_list: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.requestList()
  },
  change_square (e) {
    var nav_index = e.currentTarget.dataset.nav_index;
    if (nav_index == 0) {
      this.setData({
        is_system: 1,
        is_order: 0,
        is_advisory: 0
      })
    } else if(nav_index == 1){
      this.setData({
        is_system: 0,
        is_order: 1,
        is_advisory: 0
      })
    } else{
      this.setData({
        is_system: 0,
        is_order: 0,
        is_advisory: 1
      })
    }
  },
  bindChangeStyle (e) {
    var this_index = e.currentTarget.dataset.index;
    if(this.data.system_notice[this_index].if_show_all == 0) {
      this.data.system_notice[this_index].if_show_all == 1
    } else{
      this.data.system_notice[this_index].if_show_all == 0
    }
    this.setData({
      system_notice: this.data.system_notice
    })
  },
  to_order (e) {
    util.jumpLink('/pages/orderinfo/orderinfo?orderSn=' + e.currentTarget.dataset.order)
  },
  delete_this (e) {
    let from = e.currentTarget.dataset.form;
    util.api('/api/wxapp/message/delete', res => {
      if(res.error == 0) {
        this.data.system_notice = [];
        this.data.order_message = [];
        this.data.advisory_list = [];
        this.requestList()
      }
    },{messageId: e.currentTarget.dataset.mes_id})
  },
  requestList () {
    util.api('/api/wxapp/message/list', res => {
      if (res.error === 0) {
        if(res.content != "") {
          this.data.system_notice = [];
          this.data.order_message = [];
          this.data.advisory_list = [];
          for (let i in res.content) {
            res.content[i].messageTime = res.content[i].messageTime.substr(0, 10);
            if(res.content[i].messageType == 0){
              this.data.system_notice.push(res.content[i])
            } else if(res.content[i].messageType == 1) {
              this.data.order_message.push(res.content[i])
            }else{
              this.data.advisory_list.push(res.content[i])
            }
          }
          if(this.data.system_notice != ""){
            for (let i in this.data.system_notice) {
              this.data.system_notice[i].if_show_all = 0;
              if(this.data.system_notice[i].messageContent.length > 26) {
                this.data.system_notice[i].messageContent1 = this.data.system_notice[i].messageContent.substr(0, 25) + "...";
              } else {
                this.data.system_notice[i].messageContent1 = this.data.system_notice[i].messageContent;
              }
            }
          }
        }
        this.setData({
          system_notice: this.data.system_notice,
          advisory_list: this.data.advisory_list,
          order_message: this.data.order_message
        })
      } else {
        util.showModal('提示', res.message)
        return false
      }
    },{ userId: util.getCache('user_id'),})
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