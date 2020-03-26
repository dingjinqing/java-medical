/**
 * 优惠券详情
 * user：常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();
// var imageUrl = app.globalData.imageUrl
var couponSn;

var couponId;
var vali;
var input_vali;
var goods_ids;
var total_micro_second;
var set_time_out;
var scene;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: "http://miniimg.cn/",
    act_info: {},
    detailType: 1, // 详情类型(个人中心详情: 0, 装修详情: 1)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    clearTimeout(set_time_out);
    var _this = this;
    goods_ids = options.goods_id;
    scene = options.scene;

    if (options.couponSn || options.id) {
      couponSn = options.couponSn
      couponId = Number(options.id)
      // 个人中心查看详情
      util.api("api/wxapp/coupon/detail", function (res) {
        if (res.error == 0) {
          _this.initHandler(res, 0)
        } else {
          util.toast_fail('操作失败');
          setTimeout(function () {
            util.reLaunch({
              url: '/pages/index/index',
            })
          }, 2000);
        }
      }, { couponSn, couponId, scene: scene })
    }
  },

  // 优化数据
  initHandler: function (res, type) {
    var _this = this;
    // 是否过期
    var time_now = util.formatTime(new Date);
    if (time_now > res.content.endTime) {
      res.content.is_expire = 1;
    } else if (time_now < res.content.endTime) {
      if (res.content.isUsed == 1) {
        res.content.is_expire = 1;
      } else {
        res.content.is_expire = 0;
      }
    }
    // 倒计时
    if (res.content.remainDays == 0) {
      // total_micro_second = res.content.remain_seconds_all;
      total_micro_second = res.content.remainHours * 3600 + res.content.remainMinutes * 60 + res.content.remainSeconds;
      if (total_micro_second > 0) {
        _this.countdown(_this);
      } else {
        _this.setData({
          clock: ""
        });
      }
    }
    _this.setData({
      act_info: res.content,
      detailType: type
    })
  },

  // 倒计时
  countdown: function (that) {
    that.setData({
      clock: util.dateformat(total_micro_second)
    });
    if (total_micro_second <= 0) {
      that.setData({
        clock: ""
      });
      // timeout则跳出递归
      return;
    }
    set_time_out = setTimeout(function () {
      // 放在最后--
      total_micro_second -= 1;
      that.countdown(that);
    }, 1000)
  },

  // 领取码
  bindBlur: function (e) {
    input_vali = e.detail.value;
  },

  //立即领取
  fetch_coupon: function (e) {
    // 领取码
    if (this.data.act_info.validationCode != '') {
      vali = this.data.act_info.validationCode;
      if (input_vali != vali) {
        util.toast_fail('领取码错误');
        return
      }
    }
    // 直接领取 / 积分兑换
    if (this.data.act_info.useScore == 0) {
      util.api("/api/wxapp/coupon/get", function (res) {
        if (res.error == 0) {
          if (res.content == 0) {
            util.toast_success('领取成功', function () {
              setTimeout(function () {
                if (goods_ids) {
                  util.navigateTo({
                    url: '/pages/item/item?gid=' + goods_ids,
                  })
                } else {
                  wx.navigateBack({
                    url: '/pages/index/index',
                  })
                }
              }, 2000);
            });
          } else if (res.content == 1) {
            util.toast_fail('优惠券不存在');
          } else if (res.content == 2) {
            util.toast_fail('优惠券已过期');
          } else if (res.content == 3) {
            util.toast_fail('优惠券已停用');
          } else if (res.content == 4) {
            util.toast_fail('优惠券库存为0');
          } else if (res.content == 5) {
            util.toast_fail('可用积分不足');
          } else if (res.content == 6) {
            util.toast_fail('积分更新失败');
          } else if (res.content == 7) {
            util.toast_fail('领取次数达上限');
          }
        } else {
          util.toast_fail('领取失败');
        }
      }, { couponId })
    } else if (this.data.act_info.useScore == 1) {
      var that = this
      let showTitle = '是否使用' + this.data.act_info.scoreNumber + '积分兑换此优惠券';
      util.showModal(showTitle, '', function () {
        that.getUserCoupon();
      }, true, "取消", "确定");
    }
  },

  getUserCoupon: function () {
    util.api("/api/wxapp/coupon/get", function (res) {
      if (res.error == 0) {
        if (res.content == 0) {
          util.toast_success('领取成功', function () {
            setTimeout(function () {
              if (goods_ids) {
                util.navigateTo({
                  url: '/pages/item/item?gid=' + goods_ids,
                })
              } else {
                util.navigateTo({
                  url: '/pages/index/index',
                })
              }
            }, 2000);
          });
        } else if (res.content == 1) {
          util.toast_fail('优惠券不存在');
        } else if (res.content == 2) {
          util.toast_fail('优惠券已过期');
        } else if (res.content == 3) {
          util.toast_fail('优惠券已停用');
        } else if (res.content == 4) {
          util.toast_fail('优惠券库存为0');
        } else if (res.content == 5) {
          util.toast_fail('可用积分不足');
        } else if (res.content == 6) {
          util.toast_fail('积分更新失败');
        } else if (res.content == 7) {
          util.toast_fail('领取次数达上限');
        }
      } else {
        util.toast_fail('领取失败');
      }
    }, { couponId }, '', true)
  },

  // 立即使用
  to_search: function (e) {
    var coupon_sn = e.currentTarget.dataset.coupon_sn;
    util.jumpLink('/pages1/search/search?couponSn=' + coupon_sn);
  },

  // 我的优惠券
  myCoupon: function () {
    util.navigateTo({
      url: '/pages/coupon/coupon',
    })
  },

  // 进店看看
  visitshop: function () {
    util.navigateTo({
      url: '/pages/index/index',
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    var that = this;
    // util.api('/api/wxapp/divsionCoupon/share', function (res) {
    //   that.setData({
    //     'act_info.is_share': 1,
    //   })
    // }, { couponSn: that.data.coupon_sn });
    if (res.from === 'button') { }
    return {
      title: '分享优惠券',
      path: '/pages/splitinfo/splitinfo?user=' + user + "&coupon_sn=" + coupon_sn + "&coupon_id=" + coupon_id + "&invite_id=" + util.getCache('user_id'),
      imageUrl: imageUrl + 'image/wxapp/share_icon.jpg',
    }
  },

  // 领取记录
  to_getRecord: function () {
    util.navigateTo({
      // url: '/pages/splitinfo/splitinfo?user=' + user + "&coupon_sn=" + coupon_sn + "&coupon_id=" + coupon_id,
      url: '/pages/splitinfo/splitinfo',
    })
  },

})