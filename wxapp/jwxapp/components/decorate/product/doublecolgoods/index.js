var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'double')
      // 处理活动
      this.handleToGoodsActivities(newVal.goodsListData)

    }
  }
});