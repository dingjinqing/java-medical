// STORELIST.JS 2018.03.06
var app = new getApp();
var util = require('../../utils/util.js');
var imageUrl = app.globalData.imageUrl;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    list: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    this.setData({
      scan_stores: options.scan_stores ? options.scan_stores : 0
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
    var that = this;
    that.listRequest(that);
  },
  listRequest:function(that){
    util.getUserLocation(function (location){
      util.api('/api/wxapp/store/list', function (res) {
        that.setData({
          list: res.content
        });
      }, { scan_stores: that.data.scan_stores, location: JSON.stringify(location) });
    },true)
  },
  jumpUrl:function(e){
    var id = e.currentTarget.dataset.id;
    var state = e.currentTarget.dataset.state;
    if(state == 1){
      if (this.data.scan_stores) {
        util.navigateTo({
          url: '/pages/scancode/scancode?store_id=' + id
        })
      } else {
        util.navigateTo({
          url: '/pages/storeinfo/storeinfo?id=' + id
        })
      }
    }else{
      util.showModal('提示', '该门店未营业');
    }
  },
  onIndex: function (e) {
    util.navigateTo({ url: '/pages/index/index' })
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
    that.listRequest(that);
    // 隐藏导航栏loading
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
    return {
      path: 'pages/storelist/storelist?invite_id=' + util.getCache('user_id'),
    }
  }
})
