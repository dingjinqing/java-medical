var util = require("../../../utils/util.js");
var base = require("../base/base.js")
var app = getApp();
var imageUrl = app.globalData.imageUrl;

global.wxComponent({
  mixins: [base],
  properties: {
    custom_info:Object,
    activity_id:Number
  },
  data: {
    imageUrl: imageUrl
  },
  methods: {
    jumpLink(e){
      var d = this.data;
      // util.api('/api/wxapp/lottery/notpop', function () { }, {
      //   activity_id: d.activity_id,
      //   is_forever: 1
      // });
      let link = e.currentTarget.dataset.link;
      if(link != ''){ 
        util.jumpLink(link, "navigateTo");
      }
    },
    bindClose(e) {
      var d = this.data;
      util.api('/api/wxapp/lottery/notpop', function () { }, {
        activity_id: d.activity_id,
        is_forever: 0
      });
      this.setData({
        show: false
      });
      this.$emit("close");
      this.$emit("resetShow")
    },
  }
});