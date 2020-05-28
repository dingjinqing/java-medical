/**
 * 优惠券详情
 * user：常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();

var total_micro_second;
var set_time_out;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    couponSn: '',
    couponId: null, // 优惠券id
    act_info: {},
    input_vali: '', // 领取码
    detailType: 1, // 详情类型(个人中心详情: 0, 装修详情: 1)
    couponStatus: '0', // 优惠券状态(0未使用, 1已使用,2已过期)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    clearTimeout(set_time_out);
    var that = this;
    that.setData({
      couponSn: options.couponSn,
      couponId: Number(options.couponId),
      couponStatus: options.type
    })
    // 查看详情
    util.api("api/wxapp/coupon/detail", function (res) {
      if (res.error == 0) {
        that.initHandler(res, 0)
      } else {
        util.toast_fail(res.message);
        // setTimeout(function () {
        //   util.reLaunch({
        //     url: '/pages/index/index',
        //   })
        // }, 2000);
      }
    }, {
        couponSn: that.data.couponSn,
        couponId: that.data.couponId
      })
  },

  // 优化数据
  initHandler: function (res, type) {
    var _this = this;
    // 是否过期
    var time_now = util.formatTime(new Date);
    if (time_now > res.content.endTime) {
      res.content.isExpire = 1;
    } else if (time_now < res.content.endTime) {
      if (res.content.isUsed == 1) {
        res.content.isExpire = 1;
      } else {
        res.content.isExpire = 0;
      }
    }
    // 倒计时
    if (res.content.remainDays == 0) {
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
    this.setData({
      input_vali: e.detail.value
    })
  },

  //立即领取
  fetch_coupon: function (e) {
    // 领取码
    if (this.data.act_info.validationCode != '') {
      var vali = this.data.act_info.validationCode;
      if (this.data.input_vali != vali) {
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
                util.navigateTo({
                  url: '/pages/index/index',
                })
              }, 1000);
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
      }, { couponId: this.data.couponId })
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
              util.navigateTo({
                url: '/pages/index/index',
              })
            }, 1000);
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
    }, { couponId: this.data.couponId }, '', true)
  },

  // 立即使用
  to_search: function (e) {
    var actId = e.currentTarget.dataset.act_id;
    util.jumpLink(`/pages1/search/search${util.getUrlParams({
      pageFrom:20,
      outerPageParam:JSON.stringify({
        actId
      })
    })}`);
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

  //优惠券礼包页面
  toCoupackage:function(){
    if (this.data.act_info.accessMode == 2) {
      util.jumpLink('pages1/couponpackage/couponpackage?packId=' + this.data.act_info.accessId)
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    var couponSn = res.target.dataset.coupon_sn;
    var actId = res.target.dataset.act_id;
    util.api('/api/wxapp/divsionCoupon/share', function (res) {
      that.setData({
       'act_info.isShare': 1,
      })
    }, { couponSn: couponSn });
    return {
      title: '分享优惠券',
      path: '/pages/splitinfo/splitinfo?couponSn=' + couponSn + "&couponId=" + actId + "&inviteId=" + util.getCache('user_id'),
      imageUrl: this.data.imageUrl + '/image/wxapp/share_icon.jpg',
    }
  },

  // 领取已满员
  full_people: function (e) {
    var couponSn = e.target.dataset.coupon_sn;
    var actId = e.target.dataset.act_id;
    util.showModal("提示", '领取人数已满', function () {
      util.navigateTo({
        url: '/pages/splitinfo/splitinfo?couponSn=' + couponSn + "&couponId=" + actId + "&inviteId=" + util.getCache('user_id'),
      })
    }, true, '取消', '领取记录');
  },

  // 领取记录
  to_getRecord: function (e) {
    var couponSn = e.target.dataset.coupon_sn;
    var actId = e.target.dataset.act_id;
    util.navigateTo({
      url: '/pages/splitinfo/splitinfo?couponSn=' + couponSn + "&couponId=" + actId + "&inviteId=" + util.getCache('user_id'),
    })
  },

})