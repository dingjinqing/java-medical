var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      m.address = [];
      m.module_value = {};
    },
    bindRegionChange(e) {
      var m = this.data.m;
      m.address = e.detail.value;
      m.module_value.area = m.address
      this.$set();
    },
    bindCheckAddress(e) {
      var m = this.data.m;
      m.module_value.detail = e.detail.value;
      if (m.confirm == 1 && m.with_detail == 1) {
        if (e.detail.value == "") {
          util.showModal('提示', '请填写详细地址');
          return false;
        }
      }
      this.$set();
    },
    check() {
      var m = this.data.m;
      m.error = null;
      if (m.confirm == 1 && !m.module_value.area) {
        m.error = '请选择' + m.form_title;
      } else if (m.confirm == 1 && m.with_detail == 1) {
        if (!m.module_value.detail || m.module_value.detail == '') {
          m.error = '请填写详细地址';
        }
      }
    }
  }
});