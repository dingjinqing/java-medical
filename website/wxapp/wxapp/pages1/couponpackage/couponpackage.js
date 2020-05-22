// pages1/couponpackage/couponpackage.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var pack_info = [];
var pack_id;
var al_get_cou;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    pack_info:[],
    pack_id:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    if(options.pack_id){
      pack_id = options.pack_id
    }
    pack_request(that);
  },
  // 立即领取
  get_success:function(e){
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api("/api/wxapp/coupon/tobuy", function (res) {
        if(res.error == 0){
          if (pack_info.pack_info.access_mode == 2){
            util.showModal("提示", '领取成功', function () {
              that.onPullDownRefresh();
            })
          } else{
            util.jumpLink("/pages/cardCheckout/cardCheckout?card_id=" + pack_id + "&order_action=2");
          }
        }else{
          util.showModal("提示", res.message);
          return false;
        }
    }, { pack_id: pack_id, form_id: form_id, open_id: open_id})
  },
  // 使用商品
  to_search:function(e){
    var al_code = e.currentTarget.dataset.al_code;
    util.jumpLink("/pages/searchs/search?alias_code=" + al_code);
  },
  // 优惠券猎豹
  to_cou_list:function(){
    util.jumpLink("/pages/couponlist/couponlist")
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
    // 显示导航栏loading
    wx.showNavigationBarLoading();
    // 调用接口加载数据
    var that = this;
    pack_request(that);
    wx.hideNavigationBarLoading();
    // 当处理完数据刷新后，wx.stopPullDownRefresh可以停止当前页面的下拉刷新
    wx.stopPullDownRefresh();
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
function pack_request(that){
  util.api("/api/wxapp/coupon/pack",function(res){
    if(res.error == 0){
      pack_info = res.content;
      for(var i in pack_info.pack_list){
        if (pack_info.pack_list[i].act_code == "discount"){
          pack_info.pack_list[i].denomination = parseFloat(pack_info.pack_list[i].denomination).toFixed(0);
        }
        pack_info.pack_list[i].al_get_cou = parseFloat(pack_info.pack_list[i].total_amount) * pack_info.buy_count;
      }
      if (pack_info.pack_info.access_mode == 1){
        pack_info.pack_info.access_cost = parseFloat(pack_info.pack_info.access_cost).toFixed(0);
      }
      that.setData({
        pack_info:pack_info
      })
    }else{
      util.showModal("提示", res.message);
      return false;
    }
  },{pack_id:pack_id})
}