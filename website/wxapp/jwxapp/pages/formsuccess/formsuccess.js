// pages/formsuccess/formsuccess.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var submit_id;
var success_info = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    success_info: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    submit_id = options.submit_id;
    util.api("/api/wxapp/form/success", function (res) {
      if (res.error == 0) {
        success_info = res.content;
        for (var i in success_info.send_coupon_list) {
          success_info.send_coupon_list[i].start_time = success_info.send_coupon_list[i].start_time.substring(0, 10);
          success_info.send_coupon_list[i].end_time = success_info.send_coupon_list[i].end_time.substring(0, 10);
        }
        that.setData({
          success_info: success_info
        })
      } else {
        util.showModal("提示", res.content);
        return false;
      }
    }, { submit_id: submit_id })
  },
  to_index: function (e) {
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id });
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  to_integral: function () {
    util.navigateTo({
      url: '/pages/integral/integral',
    })
  },
  to_coupon: function () {
    util.navigateTo({
      url: '/pages/couponlist/couponlist',
    })
  },
  to_anylink: function (e) {
    let form_id = e.detail.formId;
    let open_id = util.getCache("openid");
    util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id });
    util.jumpLink(success_info.form_data.form_cfg.custom_link_path);
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

})
