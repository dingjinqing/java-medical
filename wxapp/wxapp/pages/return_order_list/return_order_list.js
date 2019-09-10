// pages/return/returnorder.js
var util = require('../../utils/util.js');
var app = getApp();
var Url = app.globalData.baseUrl;
var order_sn;
var pingzheng = [];
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    main_url:app.globalData.baseUrl,
    page:1,
    imageUrl:app.globalData.imageUrl,
    pingzheng:[],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    order_sn=options.order_sn;
    var that =this;
    util.api('/api/wxapp/return/list',function(rest){
      if(rest.error==0){
        var return_order = rest.content;
        if (return_order.can_return_goods.length>0){
          var can_return = 1;
        }
        else{
          var can_return = 0;
        }
        if (return_order.return_order_list.length > 0) {
          var order_incompleted = 1;
        }
        else{
          var order_incompleted = 0;
        }
        for (var i in return_order.return_order_list) {
          //积分商品
          if (return_order.order.goods_type == 4) {
            for (var j in return_order.return_order_list[i].goods) {
              return_order.return_order_list[i].goods[j].goods_price = parseInt(return_order.return_order_list[i].goods[j].goods_price * 100);
            }
          }
          var can_shipping_fee = '0.00';
          if (return_order.order.order_status == 3) {
            return_order.return_order_list[i].can_return_free = return_order.return_order_list[i].can_return_free != undefined ? return_order.return_order_list[i].can_return_free : 0;
          }else{
            return_order.return_order_list[i].can_return_free = can_shipping_fee;
          }

        }
        // if (return_order.order.goods_type == 4) {
        //   //积分商品
        //   for (var i in return_order.return_order_list) {
        //     for (var j in return_order.return_order_list[i].goods) {
        //       return_order.return_order_list[i].goods[j].goods_price = parseInt(return_order.return_order_list[i].goods[j].goods_price * 100);
        //     }
        //   }
        // }
        that.setData({
          can_return:can_return,
          order_sn:return_order.order_sn,
          order:return_order.order,
          order_incompleted:order_incompleted,
          return_order_list:return_order.return_order_list,
          returnTypeName: return_order.return_type
        });
      }
    }, { order_sn: order_sn});

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
    wx.stopPullDownRefresh();
  },


  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },
  returnOrder:function(e){
  var order_sn = e.target.dataset.order_sn;
  var ret_id = e.target.dataset.ret_id;
    util.navigateTo({ url: '/pages/returnorder/returnorder?order_sn='+order_sn+'&ret_id='+ret_id })
  }
})
