// pages/bargainlist/bargainlist.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var info_list = [];
var is_pink = 0;
var is_load = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    page:1,
    status:0,
    is_pink:0,
    is_load:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    order_request(that);
  },
  change_tab:function(e){
      var ctype = e.currentTarget.dataset.ctype;
      var that = this;
      that.data.page = 1;
      if(ctype == "on"){
        that.data.status = 0;
        order_request(that);
        that.setData({
          is_pink:0
        })
      }else if(ctype == 'success'){
        that.data.status = 1;
        order_request(that);
        that.setData({
          is_pink: 1
        })
      }else if(ctype== 'fail'){
        that.data.status = 2;
        order_request(that);
        that.setData({
          is_pink: 2
        })
      }
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    that.setData({
      is_load:1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    that.data.page = that.data.page + 1;
    util.api('/api/wxapp/bargain/list', function (res) {
      var disorder_info = res.content;
      var info_list = [];
      if (disorder_info.data.length > 0) {
        info_list = disorder_info.data;
      }
      for (var i = 0; i < disorder_info.data.length; i++) {
        disorder_info.data[i].end_time = disorder_info.data[i].end_time.substring(0, 10);
      }
      that.data.last_page = disorder_info.last_page;
      that.setData({
        disorder_info: disorder_info,
        info_list:that.data.info_list.concat(info_list),
        is_load:0
      });
    }, {   status: that.data.status, pageNo: that.data.page });
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  to_info:function(e){
    var record_id = e.currentTarget.dataset.record_id;
    var status = e.currentTarget.dataset.status;
    var is_ordered = e.currentTarget.dataset.is_ordered;
    var order_status = e.currentTarget.dataset.order_status;
    var order_sn = e.currentTarget.dataset.order_sn;
    if(status == 0){
      util.navigateTo({
        url: "/pages/bargaininfo/bargaininfo?record_id=" + record_id
      })
    } else if (status == 1 && is_ordered == 0){
      util.navigateTo({
        url: "/pages/goodsCheckout/goodsCheckout?order_type=bargain&record_id=" + record_id
      })
    } else if (status == 1 && is_ordered == 1 && order_status == 0){
      util.navigateTo({
        url: "/pages/orderinfo/orderinfo?order_sn=" + order_sn
      })
    } else if (status == 1 && is_ordered == 1 && order_status != 0) {
      util.navigateTo({
        url: "/pages/bargaininfo/bargaininfo?record_id=" + record_id
      })
    }

  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    order_request(this);
  },


})
function order_request(that) {
  util.api('/api/wxapp/bargain/list', function (res) {
    var disorder_info = res.content;
    var info_list = [];
    if (disorder_info.data.length>0){
      info_list = disorder_info.data;
    }
    for (var i = 0; i < disorder_info.data.length;i++){
      disorder_info.data[i].end_time = disorder_info.data[i].end_time.substring(0,10);
    }
    that.data.last_page = disorder_info.last_page;
    that.setData({
      disorder_info: disorder_info,
      info_list: info_list
    });
  }, {   status:that.data.status, pageNo: that.data.page });
}
