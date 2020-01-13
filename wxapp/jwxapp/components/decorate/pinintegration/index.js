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
      // util.api('/api/wxapp/pin/integration/start', function (res) {
      //   console.log(res)
      //   var group_id = '';
      //   if (res.content.can_pin.status == 0) {
      //     group_id = res.content.group_id;
      //   }
      //   if (res.content.can_pin.status > 0) {
      //     util.showModal('提示', res.content.can_pin.msg);
      //     return;
      //   }
      //   util.jumpLink('/pages/pinintegration/pinintegration?pinInte_id=' + act_id + '&group_id=' + group_id);
      // }, {
      //   pinInte_id: act_id
      // });
    }
  }
});
