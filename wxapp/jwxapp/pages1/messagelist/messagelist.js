// pages1/messagelist/messagelist.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_all: 1,
    is_system: 0,
    is_order: 0,
    is_advisory: 0,
    // 全部列表
    all_notice: [],
    // 系统公告列表
    system_notice: [],
    // 订单动态列表
    order_message: [],
    // 我的咨询列表
    advisory_list: [],
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
        is_advisory: 0,
        is_all: 0
      })
    } else if(nav_index == 1){
      this.setData({
        is_system: 0,
        is_order: 1,
        is_advisory: 0,
        is_all: 0
      })
    } else if(nav_index == 2){
      this.setData({
        is_system: 0,
        is_order: 0,
        is_advisory: 1,
        is_all: 0
      })
    } else{
      this.setData({
        is_system: 0,
        is_order: 0,
        is_advisory: 0,
        is_all: 1
      })
    }
    this.changeReadNum (nav_index, 0)
  },
  show_mes (e) {
    var that = this;
    util.showModal('消息详情', e.currentTarget.dataset.index, function(){
      that.changeReadNum (e.currentTarget.dataset.mes_type,e.currentTarget.dataset.mes_id)
    },false);
  },
  to_order (e) {
    this.changeReadNum (e.currentTarget.dataset.mes_type,e.currentTarget.dataset.mes_id)
    util.jumpLink('/pages/orderinfo/orderinfo?orderSn=' + e.currentTarget.dataset.order)
  },
  delete_this (e) {
    util.showModal('提示', '确定要删除此消息吗？', () => {
      util.api('/api/wxapp/message/delete', res => {
        if(res.error == 0) {
          util.toast_success('删除成功')
          this.data.system_notice = [];
          this.data.order_message = [];
          this.data.advisory_list = [];
          this.requestList()
          this.changeReadNum (e.currentTarget.dataset.mes_type,e.currentTarget.dataset.mes_id)
        }else {
          util.showModal('提示', res.message)
          return false
        }
      },{messageId: e.currentTarget.dataset.mes_id})
    }, true, '取消', '删除')
    
  },
  requestList () {
    util.api('/api/wxapp/message/list', res => {
      if (res.error === 0) {
        if(res.content != "") {
          this.data.all_notice = [];
          this.data.system_notice = [];
          this.data.order_message = [];
          this.data.advisory_list = [];
          for (let i in res.content.userMessages) {
            res.content.userMessages[i].bindName = '';
            res.content.userMessages[i].messageTime = res.content.userMessages[i].messageTime.substr(0, 10);
            if(res.content.userMessages[i].messageType == 0){
              this.data.system_notice.push(res.content.userMessages[i])
              res.content.userMessages[i].bindName = 'show_mes'
            } else if(res.content.userMessages[i].messageType == 1) {
              this.data.order_message.push(res.content.userMessages[i])
              res.content.userMessages[i].bindName = 'to_order'
            }else{
              this.data.advisory_list.push(res.content.userMessages[i])
              res.content.userMessages[i].bindName = 'to_query'
            }
            this.data.all_notice.push(res.content.userMessages[i])
          }
        }
        this.setData({
          system_notice: this.data.system_notice,
          advisory_list: this.data.advisory_list,
          order_message: this.data.order_message,
          all_notice: this.data.all_notice,
          system_num: res.content.announcementMessageCount,
          order_num: res.content.orderMessageCount,
          query_num: res.content.imSessionMessageCount,
        })
      } else {
        util.showModal('提示', res.message)
        return false
      }
    },{ userId: util.getCache('user_id'),})
  },
  changeReadNum (mesType,mesId) {
    util.api('/api/wxapp/message/change', res => {
      if(res.error == 0){
        this.setData({
          system_num: res.content.announcementCount,
          order_num: res.content.orderCount,
          query_num: res.content.chatCount,
        })
      }
    },{
      messageType: mesType,
      messageId: mesId
    })
  },
  setReadStatus (e) {
    this.changeReadNum (e.currentTarget.dataset.mes_type,e.currentTarget.dataset.mes_id)
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