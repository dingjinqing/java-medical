// couponlist.js
var util = require('../../utils/util.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;
var vali;
var input_vali;
var code;
var arr;
var list;
var coupon_sn;
var is_expire ;
var goods_ids;
var form_id;
var open_id;
var disableds = false;
var total_micro_second;
var set_time_out;
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    disableds:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    code = options.code;
    goods_ids = options.goods_id;
    list = options.list;
    coupon_sn = options.coupon_sn;
    clearTimeout(set_time_out);
    var that = this;
    if (!list){
      util.api("/api/wxapp/coupon/info", function (res) {
        if(res.error == 0){
          if (res.content.is_delete == 1) {
            util.showModal('提示', '该优惠券已删除', function () {
              util.reLaunch({
                url: '/pages/index/index'
              })
            });
            return;
          }
          res.content.code = code;
          res.content.list = list;
          res.content.from = 1;
          that.setData({
            act_info: res.content,
          })
        }else{
          util.toast_fail('操作失败');
          util.reLaunch({
            url: '/pages/index/index',
          })
        }
      }, {   code: code });
    }else if(list){
      util.api("/api/wxapp/coupon/detail", function (res) {
        if(res.error == 0){
          res.content.coupon_sn = coupon_sn;
          var time_now = util.formatTime(new Date);
          if (time_now > res.content.end_time) {
            res.content.is_expire = 1;
          } else if (time_now < res.content.end_time) {
            res.content.is_expire = 0;
          }
          res.content.from = 0;
          res.content.list = list;
          if (res.content.remain_seconds_all > 0 && res.content.remain_days == 0) {
            total_micro_second = res.content.remain_seconds_all;
            if (total_micro_second > 0) {
              that.countdown(that);
            } else {
              that.setData({
                clock: ""
              });
            }
          }
          that.setData({
            act_info: res.content,
            list: list
          })
        }else{
          util.toast_fail('操作失败');
          util.reLaunch({
            url: '/pages/index/index',
          })
        }

      },{coupon_sn:coupon_sn})
    }

  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
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
  bindBlur: function(e) {
    input_vali = e.detail.value;
  },
  myCoupon: function() {
    util.navigateTo({
      url: '/pages/couponlist/couponlist',
    })
  },
  visitshop: function() {
    util.navigateTo({
      url: '/pages/index/index',
    })
  },
  to_search: function () {
    util.navigateTo({
      url: '/pages/searchs/search?coupon_sn=' + coupon_sn
    })
  },
  //优惠券礼包页面
  to_cou_package:function(){
    var that = this;
    if(that.data.act_info.access_mode == 2){
      util.jumpLink("/pages1/couponpackage/couponpackage?pack_id=" + that.data.act_info.access_id)
    }
  },
  //立即领取
  fetch_coupon: function(e) {
    vali = this.data.act_info.validation_code;
    form_id = e.detail.formId;
    open_id = util.getCache("openid");
    if(vali != null){
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
                if (goods_ids){
                  util.navigateTo({
                    url: '/pages/item/item?goods_id=' + goods_ids,
                  })
                }else{
                  wx.navigateBack({
                    url: '/pages/index/index',
                  })
                }
              }, 2000);
            });
          } else {
            util.toast_fail(res.message.msg);
          }
        }, {   code: code,form_id:form_id,open_id:open_id})
      }
    }else{
      var that = this;
      if (this.data.act_info.use_score == 1) {
        let showTitle = '是否使用' + this.data.act_info.score_number + '积分兑换此优惠券';
        util.showModal(showTitle,'', function () {
          that.getUserCoupon();
        }, true);
      } else {
        that.getUserCoupon();
      }
    }
  },
  onShareAppMessage: function (res) {
    util.api("/api/wxapp/share/record", function (d) {

    }, { activity_id: coupon_sn, activity_type: 8 });
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
    }, { code: code }, '', true)
  },
  top_more:function(e){
    var coupon_id = this.data.act_info.id;
    if (this.data.act_info.is_exclusive == 1 && this.data.act_info.card_list.length == 1 && this.data.act_info.card_list[0].card_type == 2) {
      util.showModal('提示', '您当前的会员等级不满足，仅拥有“' + this.data.act_info.card_list[0].card_name + '”等级卡用户可购买此商品。可在“个人中心”查看会员卡权益');
      return false;
    }
    if (this.data.act_info.card_list != 'undefined' && this.data.act_info.card_list.length == 1) {
      util.navigateTo({
        url: '/pages/usercardinfo/usercardinfo?card_list=1&card_id=' + this.data.act_info.card_list[0].id + '&code=' + code + '&coupon_id=' + coupon_id,
      })
    } else {
      util.navigateTo({
        url: '/pages/buycardlist/buycardlist?coupon_id=' + coupon_id + '&code=' + code,
      })
    }
  },

})
