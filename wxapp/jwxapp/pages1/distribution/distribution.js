// pages1/distribution/distribution.js
// pages/distribution/distribution.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var mobile = util.getCache('mobile');
var dis_info = [];
var img_save_url;
var is_second = 0;
var is_distributor, judge_status;
var page_id;
var decorate = require("../../pages/common/decorate.js")
// var spec_mixin = require("../../pages/goodscommon/spec.js")
global.wxPage({
  // mixins: [decorate, spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    is_second: 0,
    page_id: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    util.api('/api/wxapp/rebate/center', function (res) {
      if (res.error == 0) {
        dis_info = res.content;
        page_id = dis_info.fanli_cfg.rebate_page_id;
        that.page_id = page_id;
        if (page_id > 0) {
          that.requestDecoratePageData(page_id, 0, that.processWindowData.bind(that));
        }
        that.setData({
          page_id: page_id,
          rebate_center: 1,
          page_name: options.names
        })
        var marqueen_tex = [];
        img_save_url = imageUrl + dis_info.invite_image;
        for (var i = 0; i < dis_info.resent_rebate_list.length; i++) {
          if (dis_info.resent_rebate_list[i].finished_time != null) {
            dis_info.resent_rebate_list[i].finished_time = dis_info.resent_rebate_list[i].finished_time.substring(0, 10);
          }
          if (dis_info.resent_rebate_list[i].username.length > 4) {
            dis_info.resent_rebate_list[i].username = dis_info.resent_rebate_list[i].username.substring(0, 4) + "...";
          }
          dis_info.resent_rebate_list[i].fanli_money = parseFloat(dis_info.resent_rebate_list[i].fanli_money).toFixed(2);
        }
        dis_info.rebate_info.total_money = parseFloat(dis_info.rebate_info.total_money).toFixed(2);
        dis_info.rebate_info.wait_fanli_money = parseFloat(dis_info.rebate_info.wait_fanli_money).toFixed(2);
        for (var i in dis_info.rebate_top_three) {
          dis_info.rebate_top_three[i].total_money = parseFloat(dis_info.rebate_top_three[i].total_money).toFixed(2);
        }
        that.setData({
          dis_info: dis_info
        })
      } else {
        that.setData({
          rebate_center: 2,
          none_message: res.message,
          none_jump_page: res.content
        })
        // util.showModal('提示', res.message, function(){
        //   util.redirectTo({ url: res.content })
        // },false);
      }
    });
  },
  toRule: function () {
    util.api('/api/wxapp/rebate/config', function (res) {
      var rule_info = res.content;
      util.jumpToWeb('/wxapp/distribution/help', rule_info);
    }, {});
  },
  to_grade: function () {
    util.navigateTo({
      url: '/pages/distrigrade/distrigrade',
    })
  },
  a_tips: function () {
    util.showModal("说明", '已返利佣金自动提现到用户余额');
    return false;
  },
  toRank: function () {
    util.navigateTo({
      url: '/pages/brokeragerank/brokeragerank',
    })
  },
  toUser: function () {
    util.navigateTo({
      url: '/pages/inviteduser/inviteduser?user_id=' + util.getCache('user_id'),
    })
  },
  toOrder: function () {
    util.navigateTo({
      url: '/pages/distributionorder/distributionorder',
    })
  },
  to_item: function (e) {
    let good_id = e.currentTarget.dataset.goods_id;
    util.navigateTo({
      url: "/pages/item/item?gid=" + good_id
    })
  },
  to_search: function () {
    // util.navigateTo({
    //   url: "/pages/searchs/search?is_rebate=1"
    // })
  },
  toPromotion: function () {
    util.navigateTo({
      url: "/pages/distripromotion/distripromotion"
    })
  },
  bindRedirectTo: function (e) {
    util.redirectTo({
      url: e.currentTarget.dataset.page
    })
  },
  preview: function (e) {
    var nowImgUrl = e.target.dataset.src;
    var arr = [];
    arr[0] = nowImgUrl;
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    if (res.from == "button" && res.target.id == '2') {
      return {
        path: 'pages/distributionspread/distributionspread?invite_id=' + util.getCache('user_id'),
        title: dis_info.user_rebate.username + '邀请你免费赚钱啦，快来申请吧!',
        imageUrl: imageUrl + '/image/wxapp/share_dis.jpg',
      }
    } else {
      return {
        path: 'pages/index/index?invite_id=' + util.getCache('user_id'),
        title: dis_info.user_rebate.username + '分享给你一个好物店铺，快来查看吧!',
        imageUrl: imageUrl + dis_info.invite_image,
      }
    }

  },



  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    var that = this;
    if (page_id > 0) {
      this.requestDecoratePageData(page_id, 0, this.processWindowData.bind(this));
    }
    wx.stopPullDownRefresh();
  },
  // 自定义模板加载
  processWindowData: function (pageContent) {
    this.setData({
      pageContent: pageContent
    })
  },
})
