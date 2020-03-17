var base = require("../../mixins/base.js");

global.wxComponent({
  mixins: [base],
  externalClasses: ['other-phone'],
  methods: {
    bindPhoneCall(e) {
      var mobiles = e.currentTarget.dataset.mobiles;
      wx.makePhoneCall({
        phoneNumber: mobiles
      })
    }
  }
});