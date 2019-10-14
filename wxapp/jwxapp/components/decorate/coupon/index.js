var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal.coupon_arr)
      for (var j in newVal.coupon_arr) {
        newVal.coupon_arr[j].denomination = parseFloat(newVal.coupon_arr[j].denomination);
      }
      newVal.isCoupon = 0;
      console.log(this.data.comColor, this.data.borColor)
    },

    bindFetchCoupon(e) {

    },
    bindGetCoupon(e) {

    },
    bindCodeBlur(e) {

    },
    bindClose(e) {

    },
  }
});