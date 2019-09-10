// pages/distribution/distribution.js
var util = require('../../utils/util.js')
var app = getApp()
var promotionList = [];
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_second:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    promotonRequest(that);
  },

  //设置or取消默认
  toDefault:function (e) {
    var that = this;
    var lan_id = e.currentTarget.dataset.lanid;
    var is_default = e.currentTarget.dataset.isdefault;
    util.api("/api/wxapp/rebate/promotionLanguage/setDef", function (res) {
      if (res.error == 0) {
        // if (is_default == 0) {
        //   that.text('默认');
        // }else{
        //   that.text('设为默认');
        // }
        util.toast_success('设置成功');
        setTimeout(function () {
          promotonRequest(that);
        }, 1000);
      }else {
        util.toast_fail('设置失败');
        return false
      }
    }, { lan_id:lan_id,is_default:is_default });
  },

  //复制到粘贴板
  toCopy:function (e) {
    var that = this;
    var content = e.currentTarget.dataset.content;
    wx.setClipboardData({
      data: content,
      success: function (res) {
        util.toast_success('复制成功');
      }
    });
  },
  
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
})

function promotonRequest(that) {
  util.api('/api/wxapp/rebate/promotionLanguage/list', function (res) {
    if (res.error == 0) {
      promotionList = res.content;
      that.setData({
        promotionList: promotionList
      })
    } else {
      that.setData({
        none_message: res.message,
        none_jump_page: res.content
      })
    }
  });
}
