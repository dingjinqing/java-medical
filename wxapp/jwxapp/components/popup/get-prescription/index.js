var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    hospital_img: {
      type: String,
      value: ''
    },
    show:{
      type:Number,
      value:0,
      observer: function (newVal, oldVal) {
        let that = this;
        console.log(newVal + '12313123');
        // if (newVal == 1) {
        //   that.closeCollectMp();
        // }
      }
    }
  },
  methods:{
    preClose () {
      this.setData({
        show:false
      })
    },
    bindGetPre () {
      util.jumpLink('/pages1/getprescription/getprescription')
    }
  }
});