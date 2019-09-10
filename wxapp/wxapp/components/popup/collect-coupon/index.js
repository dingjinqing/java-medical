var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    collect_score: Number,
    collect_coupons: Object,
    show:{
      type:Number,
      value:0,
      observer: function (newVal, oldVal) {
        let _this = this;
        if (newVal == 1) {
          _this.closeCollectMp();
        }
      }
    }
  },
  methods:{
    closeCollectMp(){
      this.triggerEvent('closeCollectMp')
    }
  }
});