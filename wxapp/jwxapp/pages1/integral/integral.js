// pages1/integral/integral.js
var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var is_sign = 0;
var list = [];
var user_center;
var page_id;
//var decorate = require("../../pages/common/decorate.js")
//var spec_mixin = require("../../pages/goodscommon/spec.js")
global.wxPage({
  //mixins: [decorate, spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_load: 0,
    is_sign: 0,
    page: 1,
    last_page: 1,
    bottom: false,
    list: [],
    page_id: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    if (!util.check_setting(options)) return;
    that.userRequest();
    get_score(that);
  },

  onPullDownRefresh: function () {
    var that = this;
    if (page_id > 0) {
      this.requestDecoratePageData(page_id, 0, this.processWindowData.bind(this));
    }
    wx.stopPullDownRefresh();
  },

  onReachBottom: function () {
    var that = this;
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      return;
    }
    that.data.page = that.data.page + 1;
    that.getlist();
  },
  toSign: function (e) {
    is_sign = 1;
    this.setData({
      is_sign: is_sign
    })
  },
  closeSign: function (e) {
    this.setData({
      is_sign: 0
    })
  },

  toWeb: function () {
    util.jumpToWeb('/wxapp/score/scoreDocument');
  },

  signScore: function (e) {
    this.setData({
      is_sign: 1
    })
    var score = user_center.sign_score.sign_data.receive_score;
    var that = this;
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api('api/wxapp/score/signAdd', function (res) {
      if (res.error == 0) {
        // util.toast_success('签到成功');
        // is_sign = 0;
        // that.setData({
        //   is_sign: is_sign
        // })
        that.userRequest();
        that.getlist();
      } else {
        // util.toast_fail(res.content);
      }
    }, {
        score: score,
        form_id: form_id,
        open_id: open_id
      })
  },

  lookRule: function (e) {
    var sign_rule = e.currentTarget.dataset.rule;
    util.jumpToWeb('/wxapp/sign/help', '&sign_rule=' + JSON.stringify(sign_rule))
  },

  userRequest: function (e) {
    var that = this;
    util.api('/api/wxapp/score/info', function (res) {
      if (res.content != null) {
        user_center = res.content;
        page_id = user_center.page_id;
        that.page_id = page_id;
        if (page_id > 0) {
          that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
        }
        that.setData({
          page_id: page_id,
          user_center: user_center,
        })
      }
    }, {})
  },

  getlist: function (e) {
    var that = this;
    util.api('/api/wxapp/score/list', function (res) {
      var listL = res.content.list;
      var expire = res.content.expire;
      if (listL.data.length > 0) {
        list = listL.data;
        for (var i = 0; i < list.length; i++) {
          list[i].score = parseInt(list[i].score);
        }
      }
      that.setData({
        list: that.data.list.concat(list),
        expire: expire
      })
    }, {
        pageNo: that.data.page
      });
  },
  // 自定义模板加载
  processWindowData: function (pageContent) {
    this.setData({
      pageContent: pageContent
    })
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

function get_score(that) {
  util.api('/api/wxapp/score/list', function (res) {
    var listL = res.content.list;
    that.data.last_page = listL.page.lastPage;
    var expire = res.content.expire;
    if (listL.dataList.length > 0) {
      list = listL.dataList;
      console.log("list--------------------------------------------------------")
      console.log(list)
      for (var i = 0; i < list.length; i++) {
        list[i].score = parseInt(list[i].score);
      }
    }

    that.setData({
      list: list,
      expire: expire
    })
  }, {

      pageNo: that.data.page
    });
}