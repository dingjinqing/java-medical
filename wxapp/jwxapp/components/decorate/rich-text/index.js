var base = require("../mixins/base.js");
var util = require("../../../utils/util.js");
global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, "rrrr++++++++++++++++++++++++++")
      newVal.rich_text = util.filterRichText(newVal.rich_text);
    },
  }
});