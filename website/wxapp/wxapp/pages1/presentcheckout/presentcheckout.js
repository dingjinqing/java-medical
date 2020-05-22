// pages1/presentcheckout/presentcheckout.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var gift_info = [];
var order_sn;
var address = [];
var total_micro_second;
var set_time_out;
var address_id;
// is_from_detail=1时来自详情页
var is_from_detail = 0;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    gift_info:[],
    is_from_detail:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    order_sn = options.order_sn;
    is_from_detail = 0;
    if (options.is_from_detail && options.is_from_detail == 1){
      is_from_detail = options.is_from_detail;
      this.setData({
        is_from_detail: is_from_detail
      })
    }
    var that = this;
    gift_request(that);
    clearTimeout(set_time_out);
  },
  // 倒计时
  countdown: function (that) {
    that.setData({
      clock: util.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      // timeout则跳出递归
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }, 1000)
  },
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    // 分钟位
    var min = Math.floor(second / 60);
    if (min < 10) min = '0' + min;
    // 秒位
    var sec = second % 60;
    if (sec < 10) sec = '0' + sec;
    return min + "分" + sec + "秒";
  },
  // 选择地址
  addAddress: function () {
    var that = this;
    wx.chooseAddress({
      success: function (res) {
        let order_goods = JSON.stringify(gift_info.order_goods);
        util.api('/api/wxapp/address/choose', function (e) {
          if(e.error == 0){
            address_id = e.content.address_id;
          }else{
            util.showModal('提示', e.message);
            return false
          }
        }, { address: JSON.stringify(res), orderGoods: order_goods})
        that.setData({
          address:res
        })
      },
      fail: function () {
        wx.getSetting({
          success: function (res) {
            if (!res.authSetting['scope.address']) {
              util.showModal('是否打开设置页面', '需要获取您的位置信息，请到小程序的设置页面打开授权', function () {
                wx.openSetting({
                  success: function (res) {

                  }
                })
              })
            }
          }
        })
      }
    })
  },
  // 提交订单
  toCheck:function(e){
    let that = this;
    let order_goods = JSON.stringify(gift_info.order_goods);
    let form_id = e.detail.formId;
    let open_id = util.getCache("openid");
    util.api('/api/wxapp/givegift/tocheckout', function (res) {
      if(res.error == 0){
        util.jumpLink('pages1/presentlist/presentlist?is_get=1');
      }else{
        util.showModal('提示', res.message);
        return false
      }
    }, { address_id: address_id, order_sn: order_sn, order_goods: order_goods, form_id: form_id, open_id: open_id })
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
function gift_request(that) {
  util.api('/api/wxapp/givegift/order', function (res) {
    if (res.error == 0) {
      gift_info = res.content;
      address = gift_info.address;
      address_id = address.address_id;
      if (gift_info.surplus_second) {
        total_micro_second = gift_info.surplus_second;
        if (total_micro_second > 0) {
          that.countdown(that);
        }  else {
          that.setData({
            clock: "已经截止"
          });
        }
      }
      that.setData({
        gift_info: gift_info,
        address: address
      })
    } else {
      util.showModal('提示', res.message,function(){
        util.jumpLink("/pages/index/index",'redirectTo')
      },false)
      return false
    }
  }, { order_sn: order_sn, is_from_detail: is_from_detail })
}