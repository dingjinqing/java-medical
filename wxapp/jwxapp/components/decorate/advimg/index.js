var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal)
      //pages/item/item?aid={{item.act_id}}&atp=3&gid={{item.goods_id}}
      if (newVal.image_type == '1') {
        newVal.each_width = (750 - parseFloat(newVal.image_space)) / 2;
        newVal.each_width = parseFloat(newVal.each_width).toFixed(0);
      }
    }
  }
});