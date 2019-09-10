var util = require("../../../utils/util.js");
var base = require("../base/base.js")

global.wxComponent({
  mixins: [base],
  properties: {
    lottery_id: Number,
    activity_id: Number,
  },
  methods: {
    bindToLottery(e) {
      var d = this.data;
      util.api('/api/wxapp/lottery/notpop', function() {}, {
        activity_id: d.activity_id
      });
      util.jumpLink('/pages/lottery/lottery?lottery_id=' + d.lottery_id + '&lottery_source=1&act_id=' + d.activity_id);
    }
  }
})