var util = require("../../../../utils/util.js");
var base = require("../../../popup/base/base.js");
var coupon = require("../../../../common/service/coupon.js")
global.wxComponent({
  mixins: [base],
  properties: {
    coupon_list: Array
  },
  methods: {
    ...coupon,
    bindGetCoupon(e) {
      var d = this.eventData(e);
      d.form_id = e.detail.form_id;

      var _this = this;
      var coupon = this.data.coupon_list[d.coupon];
      coupon.disableds = true;
      this.$set();

      this.getCoupon(d, function(content) {
        coupon.can_fetch = 0;
        coupon.disableds = false;
        _this.$set();
        _this.$emit("get_coupon_ok", d.coupon);
      });
    }
  }
});
