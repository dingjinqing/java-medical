var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    updatePage(){
      util.jumpLink('pages1/getprescription/getprescription')
    }
  }
});