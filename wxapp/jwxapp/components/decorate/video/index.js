var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'video++++++++++++++++++++++')
      newVal.video_img = newVal.video_img.substr(1)
      newVal.is_play = 0;
    },
    bindPlayVideo (e) {
      var m = this.data.m;
      m.is_play = 1;
      this.$set();
    },
  }
});