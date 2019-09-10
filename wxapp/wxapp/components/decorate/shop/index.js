var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
      var re = new RegExp(strRegex);
      if (re.test(newVal.bg_url)) {
        newVal.bg_url = newVal.bg_url;
      } else {
        newVal.bg_url = newVal.imageUrl + 'image/admin/shop_beautify/beau3.png';
      }
    },

  }
});