var util = require('../../utils/util.js')
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var store_id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    code:'',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    if (!util.check_setting(options)) return;
    store_id = options.store_id;
    wx.hideShareMenu();
  },
  codeinfo:function(e){
    this.data.code = e.detail.value;
  },
  tocart:function(){
    var code = this.data.code;
    if(code.length === 0){
      util.toast_fail('请输入条码')
      return false
    }
    util.api('/api/wxapp/store/cart/add',function(res){
      if(res.error === 0){
        util.redirectTo({
          url:'/pages/cart/cart?store_id=' + store_id,
        })
      } else {
        util.showModal('提示', res.content);
      }
    },{ store_id:store_id,scan_code:code})
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
