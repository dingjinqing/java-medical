var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal, 'coupon-------------')
      for (var j in newVal.coupon_arr) {
        newVal.coupon_arr[j].denomination = parseFloat(newVal.coupon_arr[j].denomination);
      }
      newVal.isCoupon = 0;
      console.log(this.data.comColor, this.data.borColor)
      // validation_code、is_exclusive、alias_code、use_score
    },

    bindFetchCoupon(e) {

    },
    bindGetCoupon(e) {
      var d = this.eventData(e);
      console.log(d)

    },
    bindCodeBlur(e) {

    },
    bindClose(e) {

    },
  }
});