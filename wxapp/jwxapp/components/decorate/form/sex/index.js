var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.range_arr = ['男', '女'];
      m.sex_dates = '请选择';
    },
    bindChange(e) {
      var m = this.data.m;
      m.sex_dates = m.range_arr[e.detail.value];
      m.module_value = m.range_arr[e.detail.value];
      this.$set();
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1 && !m.module_value) {
        m.error = '请选择' + m.form_title;
      }
    }
  }
});