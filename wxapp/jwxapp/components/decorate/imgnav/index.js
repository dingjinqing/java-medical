var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    bindJumpLink (e) {
      console.log(e.currentTarget.dataset)
    }
  }
});