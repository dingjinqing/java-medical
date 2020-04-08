// pages/distributionspread/distributionspread.js
var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    mobile: util.getCache('mobile'),
    dis_content: [], 
    status: null, // 分销状态
    is_block: 0, // 绑定手机号弹窗
    is_authorize: 0, // 已授权弹窗
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    dis_request(that);
  },

  // 进入分销中心
  to_distribution: function () {
    util.navigateTo({
      url: '/pages/distribution/distribution',
    })
  },

  // 申请成为分销员
  apply_get: function (e) {
    var that = this;
    if (that.data.mobile == "") {
      util.checkSession(function () {
        that.setData({
          is_block: 1
        })
      })
      return false;
    }
    util.navigateTo({
      url: '/pages/memberinfo/memberinfo?distribution=1'
    })
  },

  // 授权手机号回调
  bindGetPhoneNumberOk: function (e) {
    this.setData({
      mobile: e.detail.phoneNumber,
      is_block: 0,
      is_authorize: 1
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: "/pages/distributionspread/distributionspread",
    }
  }
})
function dis_request(that) {
  // 获取分销推广文案
  util.api('/api/wxapp/distribution/document', function (res) {
    if (res.error == 0) {
      that.setData({
        dis_content: res.content,
        page_name: res.content.title
      })
      if (res.content.document != null) {
        that.setData({
          dis_desc: util.filterRichText(res.content.document)
        });
      }
      
    }
  }, {});
  // 查看最新的审核状态
  util.api('/api/wxapp/distribution/distributor/apply/detail', function (res) {
    if (res.error === 0) {
      that.setData({
        status: res.content == null ? null : res.content.status,
      })
    }
  });
}