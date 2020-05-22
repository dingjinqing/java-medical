// pages1/brokeragerank/brokeragerank.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    all_net: 1, // 排名类型(1全网排名, 0当前等级排名)
    rank_info: [],
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
  // 全网排名
  to_allnet: function () {
    var that = this;
    that.setData({ all_net: 1 })
    rank_request(that);
  },
  // 当前等级排名
  to_now: function () {
    var that = this;
    that.setData({ all_net: 0 })
    rank_request(that);
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: "/pages2/brokeragerank/brokeragerank?user_id=" + util.getCache('user_id') + "&inviteId=" + util.getCache('user_id')
    }
  }
})
function rank_request(that) {
  // util.api('/api/wxapp/rebate/ranking', function (res) {
  //   var rank_info = res.content;
  //   if (rank_info.user_rebate.username.length > 4) {
  //     rank_info.user_rebate.username = rank_info.user_rebate.username.substr(0, 3) + "...";
  //   }
  //   if (rank_info.rebate_list && rank_info.rebate_list != "") {
  //     rank_info.rebate_list.forEach(item => {
  //       if (parseFloat(item.final_money) > 100000) {
  //         item.final_money = parseFloat(item.final_money).toFixed(0);
  //       }
  //     })
  //   }
  //   if (rank_info.user_ranking <= 7) {
  //     rank_info.rebates_lists = rank_info.rebate_list.slice(3, rank_info.rebate_list.length);
  //   }
  //   if (rank_info.rebate_top_three && rank_info.rebate_top_three != "") {
  //     rank_info.rebate_top_three.forEach(item => {
  //       if (parseFloat(item.final_money) > 100000) {
  //         item.final_money = parseFloat(item.final_money).toFixed(0);
  //       }
  //     })
  //   }
  //   that.setData({
  //     rank_info: rank_info
  //   });
  // }, { all_net: that.data.all_net });
}