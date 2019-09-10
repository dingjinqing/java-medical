// pages/insteadusers/insteadusers.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var order_sn;
var user_list = [];
var is_load = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    last_page: 1,
    server_list: [],
    is_load: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    mobile = util.getCache('mobile');
    var that = this;
    order_sn = options.order_sn;
    user_request(that);
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
    var that = this;
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    that.data.page = that.data.page + 1;
    util.api('/api/wxapp/insteadpay/userlist', function (rest) {
      var servL = rest.content;
      var server_list_r = rest.content.data;
      var user_list = [];
      if (server_list_r.length > 0) {
        user_list = server_list_r;
        for(var i in user_list){
          if(user_list[i].user_id == util.getCache("user_id")){
            user_list[i].is_own = 1
          }else{
            user_list[i].is_own = 0
          }
        }
      }
      that.setData({
        server_list: that.data.user_list.concat(user_list),
        is_load: 0
      });
    }, { pageNo: that.data.page,order_sn:order_sn });
  },

})
function user_request(that) {
  util.api('/api/wxapp/insteadpay/userlist', function (res) {
    var servL = res.content;
    that.data.last_page = servL.last_page;
    var server_list_r = res.content.data;
    var user_list = [];
    if (server_list_r.length > 0) {
      user_list = server_list_r;
      for (var i in user_list) {
        if (user_list[i].user_id == util.getCache("user_id")) {
          user_list[i].is_own = 1
        } else {
          user_list[i].is_own = 0
        }
      }
    }
    that.setData({
      user_list: user_list,
      page_num: servL.last_page,
      curpage: servL.current_page,
    });
  }, { pageNo: that.data.page,order_sn:order_sn });
}