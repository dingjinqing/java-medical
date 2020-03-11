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
      debugger
      var act_id = e.currentTarget.dataset.act_id;
      console.log(act_id)
      util.jumpLink('/pages/pinintegration/pinintegration?pinInte_id=' + act_id);
      // 发起瓜分积分活动
      // util.api('/api/wxapp/pin/integration/start', function (res) {
      //   console.log(res)
      //   // var group_id = '';
      //   // if (res.content.canPin.status == 0) {
      //   //   group_id = res.content.group_id;
      //   // }
      //   // if (res.content.canPin.status > 0) {
      //   //   util.showModal('提示', res.content.canPin.msg);
      //   //   return;
      //   // }
      //   // util.jumpLink('/pages/pinintegration/pinintegration?pinInte_id=' + act_id + '&group_id=' + group_id);
      // }, {
      //   pinInteId: act_id
      // });
    }
  }
});
