// pages1/distrigrade/distrigrade.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    gradeInfo: [] // 详情
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    util.api('/api/wxapp/distributor/level/detail', function (res) {
      if (res.error == 0) {
        var gradeInfo = res.content;
        if (gradeInfo.isHigtLevel == 0) {
          // 邀请人数
          if (gradeInfo.toNextNumber != "" && gradeInfo.toNextNumber > 0) {
            gradeInfo.personWidth = 640 * gradeInfo.nextNumber / gradeInfo.toNextNumber;
            gradeInfo.personBili = gradeInfo.nextNumber / gradeInfo.toNextNumber;
            gradeInfo.personBili = parseFloat(gradeInfo.personBili * 100).toFixed(2);
          }
          // 推广金
          if (gradeInfo.toRebateMoney != "" && gradeInfo.toRebateMoney > 0) {
            gradeInfo.tuiWidth = 640 * gradeInfo.rebateMoney / gradeInfo.toRebateMoney;
            gradeInfo.tuiBili = gradeInfo.rebateMoney / gradeInfo.toRebateMoney;
            gradeInfo.tuiBili = parseFloat(gradeInfo.tuiBili * 100).toFixed(2);
            gradeInfo.rebateMoney = parseFloat(gradeInfo.rebateMoney).toFixed(2);
            gradeInfo.toRebateMoney = parseFloat(gradeInfo.toRebateMoney).toFixed(2);
          }
          // 推广金与消费总额
          if (gradeInfo.toRebateAndPayMoney != "" && gradeInfo.toRebateAndPayMoney > 0) {
            gradeInfo.sumWidth = 640 * gradeInfo.rebateAndPayMoney / gradeInfo.toRebateAndPayMoney;
            gradeInfo.sumBili = gradeInfo.rebateAndPayMoney / gradeInfo.toRebateAndPayMoney;
            gradeInfo.sumBili = parseFloat(gradeInfo.sumBili * 100).toFixed(2);
            gradeInfo.rebateAndPayMoney = parseFloat(gradeInfo.rebateAndPayMoney).toFixed(2);
            gradeInfo.toRebateAndPayMoney = parseFloat(gradeInfo.toRebateAndPayMoney).toFixed(2);
          }
        }
        that.setData({
          gradeInfo: gradeInfo
        })
      } else {
        util.showModal("提示", res.message);
        return false;
      }
    }, {
      userId: util.getCache('user_id')
    })
  },
  // 去升降记录页面
  to_record: function () {
    util.navigateTo({
      url: '/pages/distrirecord/distrirecord',
    })
  },
})
