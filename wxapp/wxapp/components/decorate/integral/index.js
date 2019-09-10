var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");

global.wxComponent({
  mixins: [base, activity_goods],
  methods: {

    onPropChange(newVal, oldVal, changedPath) {
      var _this = this;
      var m = this.data.m = newVal;
      this.formatActivityBeginTime(m.integral_goods);
    },
    bindToInegral(e) {
      var d = this.eventData(e);
      if (d.tips != "") {
        if (d.tips == "商品已删除") {
          util.showModal('提示', '商品已删除');
        } else {
          this.navigateToItem(d.goods_id);
        }
        return false;
      }
      util.jumpLink('/pages/integralitem/integralitem?integral_goods_id=' + d.in_goods_id);
    },
  }
});