// pages1/formsuccess/formsuccess.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var submitId;
var successInfo = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    successInfo:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    submitId = options.submitId;
    util.api("/api/wxapp/form/success",function(res){
      if(res.error == 0){
        successInfo = res.content;
        for (var i in successInfo.couponList){
          if (successInfo.couponList[i].startTime && successInfo.couponList[i].endTime) {
            successInfo.couponList[i].startTime = successInfo.couponList[i].startTime.substring(0,10);
            successInfo.couponList[i].endTime = successInfo.couponList[i].endTime.substring(0, 10);
          }
        }
        successInfo.formInfo = JSON.parse(successInfo.formCfg)
        that.setData({
          successInfo: successInfo
        })
      }else{
        util.showModal("提示",res.content);
        return false;
      }
    }, { id: submitId})
  },

  to_index:function(e){
    // var form_id = e.detail.formId;
    // var open_id = util.getCache("openid");
    // util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id});
    util.reLaunch({
      url: '/pages/index/index',
    })
  },
  to_integral:function(){
    util.navigateTo({
      url: '/pages1/integral/integral',
    })
  },
  to_coupon:function(){
    util.navigateTo({
      url: '/pages/coupon/coupon',
    })
  },
  to_anylink: function(e){
    // let form_id = e.detail.formId;
    // let open_id = util.getCache("openid");
    // util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id });
    util.jumpLink(successInfo.formInfo.custom_link_path);
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
    return {
      path: this.route + '?invite_id=' + util.getCache('user_id')
    }
  }
})