var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");

global.wxComponent({
  mixins: [base, activity_goods],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      var _this = this;
      var m = this.data.m = newVal;
      console.log(m, '积分兑换++++++++++++++++++++++++++++')
      this.formatActivityBeginTime(m.integral_goods);
    },
    bindToInegral (e) {
      var d = this.eventData(e);
      console.log(d.tips)
      if (d.tips) {
        if (d.tips == "商品已删除") {
          util.showModal('提示', '商品已删除');
        } else {
          this.navigateToItem(d.goods_id);
        }
        return false;
      }
      util.jumpLink('/pages/item/item');
    },
  }
});