// pages/distrigrade/distrigrade.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var grade_info = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl:app.globalData.imageUrl,
    grade_info:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    util.api('/api/wxapp/distributor/level/detail', function (res) {
      if(res.error == 0){
        grade_info = res.content;
        grade_info.username = util.getCache("nickName");
        grade_info.useravatar = util.getCache("avatarUrl");
        if (grade_info.is_has_up_level == true){
          var level_info = grade_info.next_level;
          if (level_info.invite_number != "" && level_info.invite_number > 0) {
            grade_info.person_width = 640 * grade_info.sublayer_number / level_info.invite_number;
            grade_info.person_bili = grade_info.sublayer_number / level_info.invite_number;
            grade_info.person_bili = parseFloat(grade_info.person_bili * 100).toFixed(2);
          }
          // 推广金
          if (level_info.total_distribution_money != "" && level_info.total_distribution_money > 0) {
            grade_info.tui_money = 640 * grade_info.user_fanli_money / level_info.total_distribution_money;
            grade_info.tui_bili = grade_info.user_fanli_money / level_info.total_distribution_money;
            grade_info.tui_bili = parseFloat(grade_info.tui_bili * 100).toFixed(2);
            grade_info.user_fanli_money = parseFloat(grade_info.user_fanli_money).toFixed(2);
            grade_info.next_level.total_distribution_money = parseFloat(grade_info.next_level.total_distribution_money).toFixed(2);
          }
          // 推广金与消费总额
          if (level_info.total_buy_money != "" && level_info.total_buy_money > 0) {
            grade_info.sum_money = 640 * grade_info.user_order_money / level_info.total_buy_money;
            grade_info.sum_bili = grade_info.user_order_money / level_info.total_buy_money;
            grade_info.sum_bili = parseFloat(grade_info.sum_bili * 100).toFixed(2);
            grade_info.user_order_money = parseFloat(grade_info.user_order_money).toFixed(2);
            grade_info.next_level.total_buy_money = parseFloat(grade_info.next_level.total_buy_money).toFixed(2);
          }
        }
        that.setData({
          grade_info: grade_info
        })
      }else{
        util.showModal("提示",res.message);
        return false;
      }
    },{})
  },
  to_record:function(){
    util.navigateTo({
      url: '/pages/distrirecord/distrirecord',
    })
  },
})
