var base = require("../mixins/base.js");
global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      newVal['height'] = newVal['blank_height'] * 2 + "rpx";
    },
  }
});