var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");

global.wxComponent({
  mixins: [base, activity_goods],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.surplus_second -= m.elapse_secs;
      // this.formatActivityBeginTime(m.bargain_goods);
      console.log(m, 'pinlottery++++++++++++++++++++++++')
      this.startActivityTimer('pinlottery', {
        0: m.surplus_second
      });
      // 处理活动状态提示 tips
      this.handleToState(m)
    },
    handleToState (m) {
      let tips = ''
      switch (m.state) {
        case 1:
          tips = '活动不存在'
          break
        case 2:
          tips = '活动已停用'
          break
        case 3:
          tips = '活动未开始'
          break
        case 4:
          tips = '活动已过期'
          break
      }
      console.log(tips)
      this.setData({
        'm.tip': tips
      })
    },
    // 拼团抽奖列表
    bindToGroupList (e) {
      var d = this.eventData(e);
      if (d.tips == "" || !d.tips) {
        util.jumpLink('/pages/pinlotterylist/pinlotterylist?group_draw_id=' + d.group_draw_id);
      } else {
        util.showModal(this.$t("components.decorate.tips"), d.tips);
        return false;
      }
    },
  }
});