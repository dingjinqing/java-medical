var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.select = '请选择';
      var select_arr = [];
      for (var i in m.selects) {
        select_arr.push(m.selects[i]);
      }
      m.select_arr = select_arr;
    },
    bindSelectChange: function(e) {
      var m = this.data.m;
      m.select = m.select_arr[e.detail.value];
      m.module_value = m.select_arr[e.detail.value];
      this.$set();
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1 && (!m.module_value || m.module_value == "")) {
        m.error = '请选择' + m.form_title;
      }
    }
  }
});