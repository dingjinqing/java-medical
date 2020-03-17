var base = require("../../mixins/base.js");
var util = require("../../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    bindCheckInput: function(e) {
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

      if (m.confirm == 1 && (!m.module_value || m.module_value == '')) {
        m.error = '请输入' + m.form_title + '内容，最少' + m.least_number + '个字，最多填写' + m.most_number + '个字';
      } 
      if (m.module_value) {
        if (m.module_value.length < m.least_number) {
          m.error = '最少填' + m.least_number + '个字';
        }
        if (m.module_value.length > m.most_number) {
          m.error = '最多填' + m.most_number + '个字';
        }
      }
    }
  }
});