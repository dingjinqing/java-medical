// pages/distributionspread/distributionspread.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var dis_content = [];
var status = null; // 分销状态
var is_block = 0;
var is_bind_mobile;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_block: 0,
    is_authorize: 0,
    has_apply: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    is_block = 0;
    dis_request(that);
  },
  // 申请成为分销员
  apply_get: function (e) {
    util.navigateTo({
      url: '/pages/memberinfo/memberinfo?distribution=1'
    })
    // var form_id = e.detail.formId;
    // var open_id = util.getCache("openid");
    // var that = this;
    // if (is_bind_mobile == 1 && mobile == "") {
    //   util.checkSession(function () {
    //     that.setData({
    //       is_block: is_block = 1
    //     })
    //   })
    //   return false;
    // }
    // if (that.data.has_apply) return false;
    // that.data.has_apply = true;
    // if (that.data.dis_content.activation == 1) {
    //   util.navigateTo({
    //     url: '/pages/memberinfo/memberinfo?distribution=1'
    //   })
    // } else {
    //   util.api('/api/wxapp/distributor/apply', function (res) {
    //     if (res.error == 0) {
    //       dis_request(that);
    //     } else {
    //       dis_request(that);
    //     }
    //     that.data.has_apply = false;
    //   }, {
    //       form_id: form_id,
    //       open_id: open_id
    //     });
    // }
  },

  // 进入分销中心
  to_distribution: function () {
    util.navigateTo({
      url: '/pages/distribution/distribution',
    })
  },

  bindGetPhoneNumberOk: function (e) {
    mobile = e.detail.phoneNumber;
    this.setData({
      is_authorize: 1,
      is_block: 0
    })
  },
  close_authorize: function () {
    this.setData({
      is_authorize: 0
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
      dis_content = res.content;
      // is_bind_mobile = res.content.is_bind_mobile;
      that.setData({
        dis_content: dis_content,
        is_block: is_block
      })
      that.setData({
        page_name: dis_content.title
      })
      if (dis_content.document != null) {
        that.setData({
          dis_desc: util.filterRichText(dis_content.document)
        });
      }
    }
  }, {});
  // 查看最新的审核状态
  util.api('/api/wxapp/distribution/distributor/apply/detail', function (res) {
    if (res.error === 0 && res.content) {
      console.log(res.content.status)
      that.setData({
        status: res.content.status,
      })
    }
  }, {});
}
