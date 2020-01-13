var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      for (var j in newVal.coupon_arr) {
        newVal.coupon_arr[j].denomination = parseFloat(newVal.coupon_arr[j].denomination);
        Object.assign(newVal.coupon_arr[j], newVal[j])
      }
      newVal.isCoupon = 0;
      console.log(newVal, '+++++++++++++++++++')

    },

    bindFetchCoupon (e) {
      var d = this._form_data;
      var _this = this;
      var m = this.data.m;
      if (this._input_vali != d.vali) {
        util.toast_fail('领取码错误');
      } else {
        util.api("/api/wxapp/coupon/get", function (res) {

          if (res.error == 0) {
            if (res.content == 0) {
              util.toast_success('领取成功', function () {
                m.isCoupon = 0;
                m['coupon_arr'][d.coupon_key].status = -1;
                _this.$set();
              });
            } else {
              if (res.content == 1) {
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
              util.toast_fail(res.content);
              m.isCoupon = 0;
              m['coupon_arr'][d.coupon_key].status = -1;
              _this.$set();
            }

            // if (res.content == 0) {
            //   util.showModal('提示', '您已领取的个数已达到可领取限制');
            //   m.isCoupon = 0;
            //   m['coupon_arr'][d.coupon_key].status = -1;
            //   _this.$set();
            // } else {
            //   util.toast_success('领取成功', function () {
            //     m.isCoupon = 0;
            //     m['coupon_arr'][d.coupon_key].status = -1;
            //     _this.$set();
            //   });
            // }
          } else {
            util.toast_fail('领取失败');
          }
        }, {
          code: d.code,
          form_id: d.form_id,
          open_id: d.open_id
        })
      }
    },

    bindGetCoupon (e) {
      var d = this.eventData(e);
      var m = this.data.m;
      var _this = this;

      if (d.use_score === 1 || d.exclusive != '') {
        // 积分兑换
        util.jumpLink('/pages/getCoupon/getCoupon?couponId=' + d.coupon_id);
      } else {
        // 直接领取
        wx.showLoading({
          title: '领取中···',
        })
        util.api('/api/wxapp/coupon/get', function (res) {
          wx.hideLoading();
          if (res.error == 0) {
            if (res.content == 0) {
              util.toast_success('领取成功', function () {
                m['coupon_arr'][d.key].status = -1;
                _this.$set();
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
        }, {
          couponId: d.coupon_id
        });
      }
    },

    bindCodeBlur (e) {
      this._input_code = e.detail.value;
    },
    bindClose (e) {
      this.data.m.isCoupon = 0;
      this.$set();
    },
  }
});