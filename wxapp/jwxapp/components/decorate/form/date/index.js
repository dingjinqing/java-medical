var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.date = '请选择';
    },
    bindDateChange: function(e) {
      var m = this.data.m;
      m.date = m.module_value = e.detail.value;
      this.$set();
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1) {
        if (!m.module_value || m.module_value == '') {
          m.error = '请选择' + m.form_title;
        }
      }
    }
  }
});