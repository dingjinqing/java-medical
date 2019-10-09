var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      if (newVal.bg_type == 0) {
        newVal.bg = newVal.bg_color = newVal.bg_color || '#e6cb96';
      }
      if (newVal.bg_type == 1) {
        newVal.bg = 'url(' + newVal.imageUrl + newVal.bg_img + '!big)';
      }
      console.log(newVal)
    },
    bindGetCard(e) {

    },
    viewCard(e) {

    }
  }
});