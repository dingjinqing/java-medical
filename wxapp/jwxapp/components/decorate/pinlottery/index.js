var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");

global.wxComponent({
  mixins: [base, activity_goods],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.surplus_second -= m.elapse_secs;
      this.formatActivityBeginTime(m.bargain_goods);
      console.log(m, 'pinlottery++++++++++++++++++++++++')
      this.startActivityTimer('pinlottery', {
        0: m.surplus_second
      });
    },
    bindToGroupList (e) {
      var d = this.eventData(e);
      if (d.tips == "" || !d.tips) {
        util.jumpLink('/pages/pinlotterylist/pinlotterylist?group_draw_id=' + d.group_draw_id);
      } else {
        util.showModal("提示", d.tips);
        return false;
      }
    },
  }
});