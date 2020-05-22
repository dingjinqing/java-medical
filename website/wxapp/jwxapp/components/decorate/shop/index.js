var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal, newVal.bg_url)

      newVal.bg_url = newVal.imageUrl + newVal.bg_url;
      newVal.shop_bg_path = newVal.imageUrl + newVal.shop_bg_path;
    },

  }
});