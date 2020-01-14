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
      // var d = this.eventData(e);
      // d.form_id = e.detail.form_id;

      // var _this = this;
      // var coupon = this.data.coupon_list[d.coupon];
      // coupon.disableds = true;
      // this.$set();

      // this.getCoupon(couponId, function(content) {
      //   coupon.canFetch = 0;
      //   coupon.disableds = false;
      //   _this.$set();
      //   _this.$emit("get_coupon_ok", couponId);
      // });

      var _this = this;
      var couponId = e.target.dataset.coupon_id
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
      }, {
          couponId: couponId
        });
    }
  }
});
