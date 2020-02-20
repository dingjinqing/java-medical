var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal, newVal.bg_url)
      newVal.bg_url = 'http://jmpdevimg.weipubao.cn' + newVal.bg_url;
      newVal.shop_bg_path = 'http://jmpdevimg.weipubao.cn' + newVal.shop_bg_path;
    },

  }
});