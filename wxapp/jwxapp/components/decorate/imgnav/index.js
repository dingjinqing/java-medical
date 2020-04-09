var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
global.wxComponent({
  mixins: [base],
  methods: {
    bindJumpLink (e) {
      console.log(e.currentTarget.dataset)
      var d = this.eventData(e);
      console.log(d)
      util.jumpLink(d.url);
    }
  }
});