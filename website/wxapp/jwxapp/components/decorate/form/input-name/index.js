var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    bindCheckName: function(e) {
      var m = this.data.m;
      m.module_value = e.detail.value;
      this.check();
      if (m.error) {
        util.showModal('提示', m.error);
      }
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1 && (!m.module_value || m.module_value == "")) {
        m.error = '请填写' + m.form_title;
      }
    }
  }
});