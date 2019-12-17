/**
 * 优惠券详情
 * user：常乐
 */
let util = require("../../utils/util.js")
let config = require("../../utils/config.js")
var app = getApp();
var imageUrl = app.globalData.imageUrl
var couponSn;

var couponId;
var vali;
var input_vali;
var goods_ids;
var total_micro_second;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: "http://miniimg.cn/",
    act_info: {},
    detailType: 1, // 详情类型(个人中心性情: 0, 装修详情: 1)
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var _this = this;
    goods_ids = options.goods_id;
 
    if (options.couponSn) {
      couponSn = options.couponSn
      // 个人中心查看详情
      util.api("api/wxapp/coupon/detail",function(res){
        if(res.error == 0){
          _this.initHandler(res, 0)
        } else {
          util.toast_fail('操作失败');
          util.reLaunch({
            url: '/pages/index/index',
          })
        }
      },{couponSn:options.couponSn})
    } else {
      couponId = options.couponId
      // 装修界面查看详情
      util.api("api/wxapp/coupon/detail/byScore",function(res){
        if(res.error == 0){
          _this.initHandler(res, 1)
        } else {
          util.toast_fail('操作失败');
          util.reLaunch({
            url: '/pages/index/index',
          })
        }
      }, {couponId:options.couponId})
    }
  },

  // 优化数据
  initHandler: function(res, type) {
    var _this = this;
    // 是否过期
    var time_now = util.formatTime(new Date);
    if (time_now > res.content.endTime) {
      res.content.is_expire = 1;
    } else if (time_now < res.content.endTime) {
      res.content.is_expire = 0;
    }
    // 倒计时
    if (res.content.remain_seconds_all > 0 && res.content.remain_days == 0) {
      total_micro_second = res.content.remain_seconds_all;
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

  //立即领取
  fetch_coupon: function (e) {
    // 领取码
    vali = this.data.act_info.validationCode;
    // form_id = e.detail.formId;
    // open_id = util.getCache("openid");
    if (vali != null) {
      if (input_vali != vali) {
        util.toast_fail('领取码错误');
      } else {
        var that = this;
        that.setData({
          disableds: true
        })
        util.api("/api/wxapp/coupon/get", function (res) {
          if (res.error == 0) {
            util.toast_success('领取成功', function () {
              setTimeout(function () {
                if (goods_ids) {
                  util.navigateTo({
                    url: '/pages/item/item?goods_id=' + goods_ids,
                  })
                } else {
                  wx.navigateBack({
                    url: '/pages/index/index',
                  })
                }
              }, 2000);
            });
          } else {
            util.toast_fail(res.message.msg);
          }
        }, { 
          couponId: d.coupon_id,
        })
      }
    } else {
      var that = this;
      if (this.data.act_info.useScore == 1) {
        let showTitle = '是否使用' + this.data.act_info.scoreNumber + '积分兑换此优惠券';
        util.showModal(showTitle, '', function () {
          that.getUserCoupon();
        }, true);
      } else {
        that.getUserCoupon();
      }
    }
  },

  getUserCoupon: function () {
    util.api("/api/wxapp/coupon/get", function (res) {
      if (res.error == 0) {
        util.toast_success('领取成功', function () {
          setTimeout(function () {
            if (goods_ids) {
              util.navigateTo({
                url: '/pages/item/item?goods_id=' + goods_ids,
              })
            } else {
              util.navigateTo({
                url: '/pages/index/index',
              })
            }
          }, 2000);
        });
      } else {
        util.toast_fail(res.message.msg);
      }
    }, { couponId: couponId }, '', true)
  },

  // 立即使用
  to_search: function () {
    util.navigateTo({
      url: '/pages/searchs/search?coupon_sn=' + coupon_sn
    })
  },
})