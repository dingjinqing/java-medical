var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal, newVal.bg_url)
      // var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
      // var re = new RegExp(strRegex);
      // console.log(re.test(newVal.bg_url) + newVal.bg_url)
      // if (re.test(newVal.bg_url)) {
      //   newVal.bg_url = newVal.bg_url;
      // } else {
      newVal.bg_url = newVal.imageUrl + newVal.bg_url;
      // }
    },

  }
});