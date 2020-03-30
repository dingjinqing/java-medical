var util = require("../../../../utils/util.js");
var base = require("../../../popup/base/base.js");
// var coupon = require("../../../../common/service/coupon.js")
global.wxComponent({
  mixins: [base],
  properties: {
    coupon_list: Array
  },
  methods: {
    // ...coupon,
    bindGetCoupon(e) {
      var d = this.eventData(e);
      var _this = this;

      if (d.use_score === true) {
        // 积分领取
        util.jumpLink('/pages/receiveCoupon/receiveCoupon?couponId=' + d.coupon_id);
      } else {
        if (d.vali !== '') {
          // 领取页面
          util.jumpLink('/pages/getCoupon/getCoupon?id=' + d.coupon_id);
        } else {
          // 直接领取
          util.api('api/wxapp/coupon/get', function (res) {
            wx.hideLoading();
            if (res.error == 0) {
              if (res.content == 0) {
                util.toast_success('领取成功')
                _this.data.coupon_list.forEach((item, index) => {
                  if (item.id === couponId) {
                    item.canFetch = false
                  }
                })
                _this.$set();
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
          }, { couponId: d.coupon_id });
        }
      }
    }
  }
});
