var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    collect_info: {
      type: Object,
      value: {}
    }
  },
});