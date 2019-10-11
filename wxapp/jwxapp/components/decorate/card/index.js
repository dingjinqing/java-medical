var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal)
      // 根据背景类型来判断是采用背景颜色还是背景图片
      if (newVal.bg_type == 0) {
        newVal.bg = newVal.bg_color = newVal.bg_color || '#e6cb96';
      }
      if (newVal.bg_type == 1) {
        newVal.bg = 'url(' + newVal.bg_img + ')';
      }
      console.log(newVal)
    },
    bindGetCard(e) {

    },
    viewCard(e) {

    }
  }
});