// pages/userqrcode/userqrcode.js
var app = getApp()
var util = require('../../utils/util.js');
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
var posterBase64 = '';
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    myQrCode: '',
    userName: '',
    userAvatar: '',
    imageUrl: app.globalData.imageUrl,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    util.api('/api/wxapp/user/qrcode', function (res) {
      if (res.error == 0) {
        _this.setData({
          isOk: true,
          userQrCode: res.content
        })
      }else{
        _this.setData({
          isOk: false
        })
        wx.showToast({
          title: res.message,
          icon: 'none',
          duration: 3000
        })
        
      }
    })
    wx.hideShareMenu();
  },

  bindSaveIamge(e) {
    if (this.data.userQrCode) {
      util.base64ImageHandle(this.data.userQrCode, function (res) {
        util.toast_success('保存成功');
      });
    } else {
      util.toast_fail('正在生成中...')
    }
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
    var that = this;
    var Name = util.getCache('nickName');
    var Avatar = util.getCache('avatarUrl');
    var QrCode = util.getCache('myQrCode');
    return {
      path: 'pages/userqrcode/userqrcode?userName=' + Name + '&userAvatar=' + Avatar + '&myQrCode=' + QrCode + '&invite_id=' + util.getCache('user_id'),
    }
  }

})