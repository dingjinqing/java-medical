var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      newVal.is_play = 0;
    },
    bindPlayVideo(e) {
      var m = this.data.m;
      m.is_play = 1;
      this.$set();
    },
  }
});