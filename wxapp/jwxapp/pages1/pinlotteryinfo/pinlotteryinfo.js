// pages1/pinlotteryinfo/pinlotteryinfo.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var group_draw_id;
var goods_id;
var group_id;
var group_info = [];
var user_arr = [];
var total_micro_second = 0;
var set_time_out;
var prd_id;
var order_sn;
var show_user_modal;
var share_img;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    total_micro_second: 0,
    group_info: [],
    show_user_modal: 0,
    nickName: util.getCache('nickName'),
    click_num: false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    group_draw_id = options.group_draw_id;
    goods_id = options.goods_id;
    group_id = options.group_id;
    var that = this;
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
    if (user_avatar == "" || user_name == "") {
      that.setData({
        show_user_modal: 1
      })
    }
    that.data.is_show_modal = 0;
    clearTimeout(set_time_out);

    util.api('/api/wxapp/groupdraw/info', function (res) {
      if (res.error == 0) {
        group_info = res.content;
        if (res.content) {
          util.api('/api/wxapp/user_goods/record', function (res1) {

          }, { goods_id: goods_id, active_id: group_draw_id, active_type: 8, type: 1 })
        }
        if (group_info.user_group_info) {
          order_sn = group_info.user_group_info.order_sn;
        }

        // 判断更多抽奖活动的个数
        if (group_info.draw_goods != '' && group_info.draw_goods) {
          if (group_info.draw_goods.length > 6) {
            group_info.draw_goods = group_info.draw_goods.slice(0, 6);
          }
        }
        // 倒计时
        if (group_info.surplus_second) {
          total_micro_second = group_info.surplus_second;
          if (total_micro_second > 0) {
            that.countdown(that);
          }
        }
        if (group_info.group_join_detail.user_list.length > 0) {
          user_arr = group_info.group_join_detail.user_list.slice(1);
        }
        that.setData({
          group_info: group_info,
          user_arr: user_arr
        })
      } else {
        util.showModal("提示", res.message, function () {
          util.reLaunch({
            url: '/pages/index/index',
          })
        });
        return false;
      }
    }, { group_draw_id: group_draw_id, goods_id: goods_id, group_id: group_id })
    util.api('/api/wxapp/groupdraw/shareimg', function (res) {
      share_img = res.content;
    }, { goods_id: goods_id, group_draw_id: group_draw_id, group_id: group_id });
  },
  to_lists: function () {
    if (group_info.group_draw.status == 2) {
      util.reLaunch({
        url: '/pages/index/index',
      })
    } else {
      util.navigateTo({
        url: '/pages/pinlotterylist/pinlotterylist?group_draw_id=' + group_draw_id,
      })
    }
  },
  // 去详情页
  to_group: function (e) {
    var goods_id = e.currentTarget.dataset.goods_id;
    var group_draw_id = e.currentTarget.dataset.draw_id;
    util.navigateTo({
      url: '/pages/pinlotteryitem/pinlotteryitem?goods_id=' + goods_id + "&group_draw_id=" + group_draw_id,
    })
  },
  // 去参团
  to_join: function () {
    var choose_list = {};
    choose_list['goods_id'] = goods_id;
    choose_list['group_draw_id'] = group_draw_id;
    choose_list['group_id'] = group_id;
    choose_list['user_id'] = util.getCache("user_id");
    choose_list['product_id'] = group_info.group_draw.product_id;
    choose_list['goods_number'] = 1;
    util.navigateTo({
      url: "/pages/goodsCheckout/goodsCheckout?order_type=group_draw&choose_list=" + JSON.stringify(choose_list),
    })
  },
  // 去开团
  to_open: function () {
    var choose_list = {};
    choose_list['goods_id'] = goods_id;
    choose_list['group_draw_id'] = group_draw_id;
    choose_list['user_id'] = util.getCache("user_id");
    choose_list['product_id'] = group_info.group_draw.product_id;
    choose_list['goods_number'] = 1;
    util.navigateTo({
      url: "/pages/goodsCheckout/goodsCheckout?order_type=group_draw&choose_list=" + JSON.stringify(choose_list),
    })
  },
  close_this: function () {
    this.data.is_show_modal = 0;
    this.setData({
      is_show_modal: 0
    })
  },
  // 倒计时
  countdown: function (that) {
    that.setData({
      clock: that.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: "已经截止"
      });
      return;
    }
    set_time_out = setTimeout(function () {
      total_micro_second -= 1;
      that.countdown(that);
    }
      , 1000)
  },
  // 时间格式化输出，如3:25:19 86。每10ms都会调用一次
  dateformat: function (micro_second) {
    // 秒数
    var second = Math.floor(micro_second);
    //天数位
    var date = Math.floor(second / 86400);
    // 小时位
    var hr = Math.floor((second - date * 24 * 3600) / 3600);
    if (hr < 10) { hr = "0" + hr }
    // 分钟位
    var min = Math.floor((second - hr * 60 * 60 - date * 24 * 3600) / 60);
    if (min < 10) { min = "0" + min }
    // 秒位
    var sec = second % 60;
    if (sec < 10) { sec = "0" + sec }
    var arrs = [];
    if (date == 0) {
      return hr + ':' + min + ":" + sec;
    } else {
      return date + '天' + hr + ':' + min + ":" + sec;
    }
  },
  getUserInfo: function (e) {
    this.setData({
      show_user_modal: 0
    });
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
          }
        })
      }
      that.setData({
        nickName: user_name,
      })
    }
    if (e.currentTarget.dataset.join == 1) {
      that.to_join(e);
    }
    if (e.currentTarget.dataset.open == 1) {
      that.to_open(e);
    }
    that.setData({
      click_num: true,
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
  to_orderinfo: function () {
    if (group_info.group_draw.status == 2) {
      util.reLaunch({
        url: '/pages/index/index',
      })
    } else {
      util.navigateTo({
        url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn,
      })
    }
  },
  to_orderinfos: function () {
    util.navigateTo({
      url: '/pages/orderinfo/orderinfo?order_sn=' + order_sn,
    })
  },
  // 去规则页
  to_rule: function () {
    util.jumpToWeb('/wxapp/pinlottery/help');
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    var that = this;
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: group_draw_id, activity_type: 8 });
    return {
      title: "快来参与" + group_info.group_draw.pay_money + "元拼团大抽奖吧",
      imageUrl: imageUrl + share_img,
      path: '/pages/pinlotteryinfo/pinlotteryinfo?group_draw_id=' + group_draw_id + "&goods_id=" + goods_id + "&group_id=" + group_id + '&invite_id=' + util.getCache('user_id'),
    }
  },
  to_coupon_list: function () {
    util.jumpLink('pages/couponlist/couponlist');
  }
})
