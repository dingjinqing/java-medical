// pages/insteadinfo/insteadinfo.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var ins_info = [];
var manage_msg = '';
var if_own;
var order_sn;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    ins_info:[],
    manage_msg:'',
    if_own: 0,
    order_sn:'',
    click_num: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    mobile = util.getCache('mobile');
    var that = this;
    order_sn = options.order_sn;
    ins_request(that, options);
    var user_name = util.getCache('nickName');
    var user_avatar = util.getCache('avatarUrl');
    if (!user_name || user_name == '用户' + parseInt(util.getCache('user_id') + 10000)
      || user_name == util.getCache('openid') || !user_avatar
      || user_avatar.indexOf('image/admin/head_icon.png') > -1) {
      that.setData({
        getsq: false,
      })
    } else {
      that.setData({
        getsq: true,
      })
    }
  },
  getUserInfo: function (e) {
    var that = this;
    var canIUse = wx.canIUse('button.open-type.getUserInfo');
    if (e.detail.userInfo) {
      if (canIUse) {
        var user_avatar = e.detail.userInfo.avatarUrl;
        var user_name = e.detail.userInfo.nickName;
        util.setCache("nickName", user_name);
        util.setCache("avatarUrl", user_avatar);
        util.api('/api/wxapp/account/updateUser', function (res) {
        }, {

            username: user_name,
            user_avatar: user_avatar
          });
        that.setData({
          click_num: true,
        })
      } else {
        wx.getUserInfo({
          success: res => {
            var user_avatar = e.detail.userInfo.avatarUrl;
            var user_name = e.detail.userInfo.nickName;
            util.setCache("nickName", user_name);
            util.setCache("avatarUrl", user_avatar);
            util.api('/api/wxapp/account/updateUser', function (res) {
            }, {

                username: user_name,
                user_avatar: user_avatar
              });
            that.setData({
              click_num: true,
            })
          }
        })

      }
    }

  },
  //自己付点
  own_pay_some:function(e){
    var form_id = e.detail.formId;
    var open_id = util.getCache('openid');
    util.api("/api/wxapp/common/saveformid", function (res) {

    }, { form_id: form_id, open_id: open_id });
    util.navigateTo({
      url: '/pages/insteadcheckout/insteadcheckout?order_sn=' + order_sn,
    })
  },
  // woyexaingmai
  to_item:function(e){
    var form_id = e.detail.formId;
    var open_id = util.getCache('openid');
    util.api("/api/wxapp/common/saveformid", function (res) {

    }, { form_id: form_id, open_id: open_id });
    util.navigateTo({
      url: '/pages/item/item?good_id=' + ins_info.order.order_goods[0].goods_id,
    })
  },
  viewmore:function(){
    util.navigateTo({
      url: '/pages/insteadusers/insteadusers?order_sn=' + order_sn,
    })
  },
  orderInfo: function (e) {
    // var order_sn = e.target.dataset.order_sn;
    util.navigateTo({ url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn })
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
    return {
      title: "相中了一件商品，帮人家付一下呗！",
      path: 'pages/insteadinfo/insteadinfo?order_sn=' + order_sn + '&message=' + manage_msg
        + '&invite_id=' + util.getCache('user_id'),
      imageUrl: ins_info.order.order_goods[0].goods_img,
    }
  },
  set_message: function (e) {
    manage_msg = e.detail.value ? e.detail.value : manage_msg;
  }
})
function ins_request(that, options){
  util.api('/api/wxapp/insteadpay/detail', function (res) {
    if (res.error == 0) {
      ins_info = res.content;
      manage_msg = ins_info.message;
      ins_info.pro_width = (650 * ins_info.has_pay_money) / (parseFloat(ins_info.wait_pay_money) + parseFloat(ins_info.has_pay_money));
      ins_info.pro_width = parseFloat(ins_info.pro_width).toFixed(2);
      if (ins_info.order.user_id == util.getCache("user_id")){
        if_own = 0;
      }else{
        if_own = 1;
        manage_msg = options.message ? options.message : manage_msg;
      }
      for (var i in ins_info.user_list.data){
        if (ins_info.user_list.data[i].user_id == util.getCache("user_id")){
          ins_info.user_list.data[i].is_own = 1
        }else{
          ins_info.user_list.data[i].is_own = 0
        }
      }
      that.setData({
        ins_info: ins_info,
        manage_msg: manage_msg,
        if_own: if_own
      })
    } else {
      util.showMadal("提示", res.message);
      return false;
    }
  }, { order_sn: order_sn })
}
