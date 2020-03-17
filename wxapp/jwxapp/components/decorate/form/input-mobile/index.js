var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    //检查手机号
    bindCheckMobile: function(e) {
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
        m.error = '请选择' + m.form_title;
      }
      if (m.module_value != "" && m.module_value != undefined) {
        if (!(/^1[3456789]\d{9}$/.test(m.module_value))) {
          m.error = '请输入正确的' + m.form_title;
        }
      }
    }
  }
});