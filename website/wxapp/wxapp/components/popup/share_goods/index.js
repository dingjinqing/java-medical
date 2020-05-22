var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    
  },
  methods:{
    
    shareMessage(){
      this.triggerEvent('share_message');
      this.setData({
        show: false
      })
    },
    download_img() {
      this.triggerEvent('download_img');
      this.setData({
        show: false
      })
    },
  }
});