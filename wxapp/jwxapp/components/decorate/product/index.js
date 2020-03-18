var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var addCart = require("../../../pages/common/addCart")

global.wxComponent({
  mixins: [base,addCart],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal, '总数据')

    },
    bindMenuClick(e) {

    }
  }
});