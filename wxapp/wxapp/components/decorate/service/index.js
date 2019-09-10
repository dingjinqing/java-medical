var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  externalClasses: ['other-service'],
  properties: {
    is_phone_block: {
      type: Number,
      value: 0
    }
  },
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      // todo: newVal.is_phone_block 应在外部设置好
      // for (var j in content) {
      //   if (content[j].tmpl_name == 'phoneTmpl' && content[j].show_type == 1) {
      //     is_phone_block = 1;
      //   }
      // }
      // content[idx].is_phone_block = is_phone_block
    },

  }
});