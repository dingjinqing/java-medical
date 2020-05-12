var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'pinintegration+++++++++++++++++++++')
      newVal.moude_img1 = "url(" + newVal.module_img + ")";
      newVal.moude_img2 = "url(" + newVal.imageUrl + "/image/wxapp/pin_back.png)";
    },
    bindToPinIntegration (e) {
      var act_id = e.currentTarget.dataset.act_id;
      console.log(act_id)
      util.jumpLink('/pages1/pinintegration/pinintegration?pid=' + act_id);
    }
  }
});
