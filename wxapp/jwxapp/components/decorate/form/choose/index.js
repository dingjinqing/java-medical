var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    // 单选 选项
    bindRadiosChange: function(e) {
      var m = this.data.m;
      m.module_value = e.detail.value;
      this.$set();
    },
    // 多选 选项
    bindCheckboxChange: function(e) {
      var m = this.data.m;
      m.module_value = e.detail.value;
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1 && (!m.module_value || m.module_value == '')) {
        m.error = '请选择' + m.form_title;
      }
    }
  }
});