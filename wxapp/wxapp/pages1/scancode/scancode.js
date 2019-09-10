var util = require('../../utils/util.js')
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile;
var info
var id;
var all_serv = {};
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    if (!util.check_setting(options)) return;
    id = options.store_id;
    this.loadPage(this);
    wx.hideShareMenu();
  },
  /* 扫码 */
  scangoods:function(){
    wx.scanCode({
      onlyFromCamera:true,
      scanType: ['barCode'],
      success:res => {
        util.api('/api/wxapp/store/cart/add', function (reponse) {
          if (reponse.error == 0) {
            util.navigateTo({
              url: '/pages/cart/cart?store_id=' + id,
            })
          } else {
            util.showModal('提示', reponse.content);
          }
        }, {   store_id: id, scan_code: res.result })
       },
      fail: res => {
        util.showModal('提示', '扫码失败');
       },
      complete: res => { }
    })
  },
  /* 手动输入条形码 */
  gomanualcode:function(){
    var store_id = id;
    util.navigateTo({
      url:"/pages/manualbarcode/manualbarcode?store_id=" + store_id,
      success:res => {},
      fail:res => {},
      complete:res => {}
    })
  },
  /* 出场码页 */
  goappearancecode:function(){
    var store_id = id;
    util.navigateTo({
      url:"/pages/appearancecode/appearancecode?store_id=" + store_id,
      success:res => {},
      fail:res => {},
      complete:res => {}
    })
  },
  /* 帮助页 */
  goHelp:function(){
    var mobile = info.mobile;
    var manager = info.manager;
    util.jumpToWeb('/wxapp/scancode/help','&mobile=' + mobile + '&manager=' + manager)
  },
  /* 门店列表页 */
  goStoreList:function(){
    util.redirectTo({
      url:"/pages/storelist/storelist?scan_stores=1"
    })
  },
  /* 购物车 */
  gocart:function(){
    var store_id = id;
    util.navigateTo({
      url:'/pages/cart/cart?store_id=' + store_id
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
    this.loadPage(this);
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

  },

  loadPage: function(that){
    util.api('/api/wxapp/store/info', function (res) {
      if (res.content.is_delete == 1) {
        util.showModal('提示', '该门店不存在', function () {
          util.reLaunch({
            url: '/pages/index/index'
          })
        });
        return;
      }
      mobile = res.content.mobile;
      info = res.content;
      if (res.content.content != null) {
		  that.setData({
			  content: util.filterRichText(res.content.content)
		  });
      }
      if (info.list.length >= 0) {
        all_serv.cat_id = 0;
        all_serv.cat_name = '全部分类';
        all_serv.services = info.all_service;
        info.list.unshift(all_serv);
        for (var i in info.list) {
          if (info.list[i].services != null && info.list[i].services.length > 0) {
            for (var j in info.list[i].services) {
              info.list[i].services[j].service_img = JSON.parse(info.list[i].services[j].service_img)[0];
            }
          }
        }
      }
      that.setData({
        info: info
      });
    }, { id: id  })
  }
})
