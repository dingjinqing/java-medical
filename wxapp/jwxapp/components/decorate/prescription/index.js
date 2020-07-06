var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    handleShowDialog(){
      console.log(111)
      this.setData({
        showPrescription:true
      })
    }
  }
});