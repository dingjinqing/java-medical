// pages/lotterygift/lotterygift.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var type = 0;
var gift_info = [];
var gift_list = [];
var is_load = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page: 1,
    last_page: 1,
    type:0,
    gift_list:[],
    gift_info:[],
    is_load:0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    type = 0;
    gift_request(that);
    var margin_top_nav = 0
    if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
      margin_top_nav = wx.getMenuButtonBoundingClientRect().bottom
    } else {
      wx.getSystemInfo({
        success: (res) => {
          margin_top_nav = res.statusBarHeight * 3
        }
      })
    }
    that.setData({
      height: margin_top_nav,
    })
  },
  change_tabs:function(e){
    var that = this;
    type = e.currentTarget.dataset.type;
    that.data.type = e.currentTarget.dataset.type;
    that.data.page = 1;
    gift_request(that);
  },
  to_order:function(e){
    var order_sn = e.currentTarget.dataset.order_sn;
    util.jumpLink('../../pages/orderinfo/orderinfo?order_sn='+order_sn,"navigateTo");
  },
  // 立即领取赠品
  to_get_gift: function (e) {
    var params = {};
    params.goods_id = e.currentTarget.dataset.goods_id;
    params.goods_price = 0;
    params.user_id = util.getCache('user_id');
    params.goods_number = 1;
    params.prd_sn = e.currentTarget.dataset.prd_sn;
    params.prd_id = e.currentTarget.dataset.prd_id;
    params.product_id = e.currentTarget.dataset.prd_id;
    params.lc_id = e.currentTarget.dataset.lc_id;
    var order_types = 'lottery_present';
    if (e.currentTarget.dataset.sources == 1){
      params.launch_id = e.currentTarget.dataset.launch_id;
      order_types = 'friend_promote'
    }
    if (e.currentTarget.dataset.sources == 2){
      // params.launch_id = e.currentTarget.dataset.launch_id;
      order_types = 'assess_present'
    }
    var query_param = {};
    query_param.goods_id = e.currentTarget.dataset.goods_id;
    
    console.log(JSON.stringify(params));
    util.navigateTo({
      url: '../../pages/goodsCheckout/goodsCheckout?order_type=' + order_types+'&choose_list=' + JSON.stringify(params) + '&query_param=' + JSON.stringify(query_param),
    }) 
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
    util.api("/api/wxapp/lottery/gift", function (res) {
      if (res.error == 0) {
        gift_info = res.content;
        that.data.last_page = gift_info.last_page;
        console.log(typeof (gift_info.data));

        if (gift_info.data.length > 0) {
          gift_list = gift_info.data;
          for (let i in gift_list) {
            gift_list[i].source_word = "";
            if (gift_list[i].source_type == 0) {
              gift_list[i].source_word = "幸运大抽奖"
            } else if (gift_list[i].source_type == 1) {
              gift_list[i].source_word = "好友助力"
            } else if (gift_list[i].source_type == 2) {
              gift_list[i].source_word = "测评"
            } else {
              gift_list[i].source_word = ""
            }
          }
        }
        that.setData({
          gift_info: gift_info,
          gift_list: that.data.gift_list.concat(gift_list),
          type: type,
          is_load: 0
        })
      } else {
        util.showModal('提示', res.content)
      }
    }, { type: that.data.type, pageNo: that.data.page })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})
function gift_request(that){
  util.api('/api/wxapp/lottery/gift', function (res) {
    if (res.error == 0) {
      gift_info = res.content;
      that.data.last_page = gift_info.last_page;
      var full_goods_r = [];
      var gift_list = [];
      full_goods_r = gift_info.data;
      if (full_goods_r.length > 0) {
        gift_list = full_goods_r;
        for (let i in gift_list) {
          gift_list[i].source_word = "";
          if (gift_list[i].source_type == 0){
            gift_list[i].source_word = "幸运大抽奖"
          } else if (gift_list[i].source_type == 1){
            gift_list[i].source_word = "好友助力"
          } else if (gift_list[i].source_type == 2) {
            gift_list[i].source_word = "测评"
          } else {
            gift_list[i].source_word = ""
          }
        }
      }
      
      that.setData({
        gift_info: gift_info,
        gift_list: gift_list,
        type: type
      })
    } else {
      util.showModal("提示", res.message, function () {
        wx.navigateBack({

        })
      });
      return false;
    }
  }, { type: type, pageNo: that.data.page, page_rows: 5});
}
