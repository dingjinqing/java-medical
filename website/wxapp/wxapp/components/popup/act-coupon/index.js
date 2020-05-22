var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    send_coupon: {
      type: Object,
      value: {}
    }
  },

  methods: {
    bindGoCouponList(e) {
      util.jumpLink('/pages/couponlist/couponlist');
    }
  }
});