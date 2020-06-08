// pages1/brokeragerank/brokeragerank.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var rank_info = [];
var all_net = 1;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    all_net: 1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    rank_request(that)

  },
  to_allnet: function () {
    var that = this;
    that.setData({
      all_net: 1,
    })
    rank_request(that);
  },
  to_now: function () {
    var that = this;
    that.setData({
      all_net: 0,
    })
    rank_request(that);
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: "/pages2/brokeragerank/brokeragerank?user_id=" + util.getCache('user_id') + "&invite_id=" + util.getCache('user_id')
    }
  }
})
function rank_request(that) {
  // util.api('/api/wxapp/rebate/ranking', function (res) {
  //   rank_info = res.content;
  //   if (rank_info.user_rebate.username.length > 4) {
  //     rank_info.user_rebate.username = rank_info.user_rebate.username.substr(0, 3) + "...";
  //   }
  //   if (rank_info.rebate_list && rank_info.rebate_list != "") {
  //     for (var i in rank_info.rebate_list) {
  //       if (parseFloat(rank_info.rebate_list[i].final_money) > 100000) {
  //         rank_info.rebate_list[i].final_money = parseFloat(rank_info.rebate_list[i].final_money).toFixed(0);
  //       }
  //     }
  //   }
  //   if (rank_info.user_ranking <= 7) {
  //     rank_info.rebates_lists = rank_info.rebate_list.slice(3, rank_info.rebate_list.length);

  //   }
  //   if (rank_info.rebate_top_three && rank_info.rebate_top_three != "") {
  //     for (var i = 0; i < rank_info.rebate_top_three.length; i++) {
  //       if (parseFloat(rank_info.rebate_top_three[i].final_money) > 100000) {
  //         rank_info.rebate_top_three[i].final_money = parseFloat(rank_info.rebate_top_three[i].final_money).toFixed(0);
  //       }
  //     }
  //   }
  //   that.setData({
  //     rank_info: rank_info
  //   });

  // }, { all_net: that.data.all_net });
}