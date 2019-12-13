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
      // var d = this.eventData(e);
      // d.form_id = e.detail.form_id;

      var _this = this;
      // var coupon = this.data.coupon_list[d.coupon];
      // coupon.disableds = true;
      // this.$set();

      // this.getCoupon(d, function(content) {
      //   coupon.can_fetch = 0;
      //   coupon.disableds = false;
      //   _this.$set();
      //   _this.$emit("get_coupon_ok", d.coupon);
      // });

      console.log(e.target.dataset)
      var id = e.target.dataset.id
      util.api('api/wxapp/coupon/get', function (res) {
        wx.hideLoading();
        if (res.error == 0) {
          if (res.content == 0) {
            util.showModal('提示', '您已领取的个数已达到可领取限制');
          } else {
            util.toast_success('领取成功')
            _this.data.coupon_list.forEach((item, index) => {
              if (item.id === id) {
                item.canFetch = false
              }
            })
            _this.$set();
          }
        } else {
          util.toast_fail(res.message.msg);
        }
      }, {
          cuponId: id
        });
    }
  }
});
