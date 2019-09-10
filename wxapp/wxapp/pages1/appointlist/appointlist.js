// APPPONTLIST.JS 2018.03.10
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
var prd_img;
var is_load = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    currentTabsIndex: 0,
    order_status: '',
    type: 'all',
    img_store: imageUrl + "image/wxapp/address.png",
    img_arrow: imageUrl + "image/wxapp/click_look.png",
    server_list: [],
    searchLoading: false,
    loadingComplete: false,
    page: 1,
    last_page:1,
    is_load:0,
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    server_request(that)
  },
  // 点击选项卡事件
  onTabsItemTap: function (e) {
    var that = this;
    var server_type = e.currentTarget.dataset.type;
    var order_status = e.currentTarget.dataset.status;
    that.setData({
      order_status: order_status,
      type: server_type
    })
    server_request(that)
  },
  toInfo:function(e){
    var order_sn = e.currentTarget.dataset.order_sn;
    util.navigateTo({
      url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn
    })
  },
  toStore:function(e){
    var id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/storeinfo/storeinfo?id=' + id
    })
  },
  toDetail: function (e) {
    var service_id = e.currentTarget.dataset.id;
    util.navigateTo({
      url: '/pages/appointment/appointment?service_id=' + service_id
    })
  },
  ser_comment:function(e){
    var order_sn = e.currentTarget.dataset.order_sn;
    var store_id = e.currentTarget.dataset.store_id;
    util.navigateTo({
      url: '../servicecomment/servicecomment?order_sn=' + order_sn + "&store_id=" + store_id
    })
  },
  //取消预约
  serverCancel:function(e){
    var mobile = e.currentTarget.dataset.mobile;
    util.showModal('提示', '请与商家联系后，由商家取消', function () {
      wx.makePhoneCall({
        phoneNumber: mobile
      })
    }, true, '取消', '直接联系');
  },
  toTrueCancel: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.showModal('提示', '是否取消该订单', function () {
      util.api('/api/wxapp/service/cancel', function (res) {
        if (res.error == 0) {
          util.toast_success('取消成功');
          util.navigateTo({ url: '/pages/appointlist/appointlist' });
        } else if (res.error == 400002) {
          util.toast_success('操作成功');
        }
      }, { order_sn: order_sn, open_id: open_id, form_id: form_id })
    },true);
  },
  //删除预约
  toDelete:function(e){
    var data = e.currentTarget.dataset;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.showModal("提示","是否删除该预约服务订单",function(){
      util.api('/api/wxapp/service/del',function(res){
        if (res.error == 0) {
          util.navigateTo({ url: '/pages/appointlist/appointlist' });
        }
      }, { order_sn: data.order_sn, order_id: data.order_id, open_id: open_id, form_id: form_id})
    },true);
  },
  //去支付
  toPay: function (e) {
    var order_sn = e.currentTarget.dataset.order_sn;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('/api/wxapp/service/pay', function (res) {
      if (res.error == 0) {
        if (typeof (res.content.timeStamp) != 'undefined') {
          wx.requestPayment({
            'timeStamp': res.content.timeStamp,
            'nonceStr': res.content.nonceStr,
            'package': res.content.package,
            'signType': typeof res.content.signType == "undefined" ? 'MD5' : res.content.signType,
            'paySign': res.content.paySign,
            'success': function (res) {
              util.toast_success('支付成功');
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'fail': function (res) {
              util.toast_fail('支付失败');
              util.navigateTo({
                url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
              })
            },
            'complete': function (res) {
            }
          });
        } else {
          util.toast_fail('支付失败');
          util.redirectTo({
            url: '/pages/appointinfo/appointinfo?order_sn=' + order_sn,
          })
        }
      } else if (e.error == 400002) {
        util.showModal('提示', e.content, function () {
          wx.navigateBack();
        });
      }else{
        util.showModal("提示",res.message,function(){
          wx.navigateBack({

          })
        });
        return false;
      }
    }, { order_sn: order_sn, openid: open_id,form_id: form_id })
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  onReachBottom:function(){
    var that = this;
    that.setData({
      is_load:1
    })
    if (that.data.page == that.data.last_page){
      that.setData({
        is_load:0
      })
      return;
    }
    var pageNo = that.data.page + 1;
    util.api('/api/wxapp/service/orderlist', function (rest) {
      var servL = rest.content;
      that.data.page = servL.current_page;
      var server_list_r = rest.content.data;
      var server_list = [];
      if (server_list_r.length > 0) {
        server_list = server_list_r;
        for (var i in server_list) {
          server_list[i].service_img = JSON.parse(server_list[i].service_img)[0];
          server_list[i].store_imgs = JSON.parse(server_list[i].store_imgs)[0];
        }
      }
      that.setData({
        server_list: that.data.server_list.concat(server_list),
        order_status: that.data.order_status,
        type: that.data.type,
        is_load:0
      });
    }, {   order_status: that.data.order_status, type:that.data.type, pageNo: pageNo})
  },
})
function server_request(that){
  util.api('/api/wxapp/service/orderlist', function (res) {
    var servL = res.content;
    var server_list_r = res.content.data;
    that.data.last_page = servL.last_page;
    var server_list = [];
    if (server_list_r.length > 0) {
      server_list = server_list_r;
      for(var i in server_list){
        server_list[i].service_img = JSON.parse(server_list[i].service_img)[0];
        server_list[i].store_imgs = JSON.parse(server_list[i].store_imgs)[0];
      }
    }
    that.setData({
      server_list: server_list,
      order_status: that.data.order_status,
      page_num: servL.last_page,
      curpage: servL.current_page,
      loadingComplete: false,
      searchLoading: false,
      type: that.data.type
    });
    if (servL.last_page > servL.current_page) {
      that.setData({
        loadingComplete: false,
        searchLoading: true,
      });
    }
  }, {   order_status: that.data.order_status, type: that.data.type });
  util.api('/api/wxapp/service/num', function (rest) {
    if (rest.error == 0) {
      that.setData({
        orderNum: rest.content
      });
    }
  }, {   })
}
