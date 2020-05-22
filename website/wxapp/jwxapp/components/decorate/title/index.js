var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'title++++++++++++++++++++++++++++++++++')
      if (newVal['tit_center'] == 1) {
        newVal['pos'] = 'left';
      } else {
        newVal['pos'] = 'center';
      }
    },
  }
});