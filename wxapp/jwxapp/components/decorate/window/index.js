var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'window+++++++++++++++++++++++++++++++')
      var window_block = [];
      var tmpl_width = 750;
      if (!newVal.table_size || newVal.table_size == undefined) {
        newVal.table_size.cols = 4;
        newVal.table_size.rows = 4;
      }
      var min_block_size = tmpl_width / newVal.table_size.cols;
      newVal.all_height = min_block_size * newVal.table_size.rows;
      var windowData = newVal['data'];
      for (var w_idx in windowData) {
        if (windowData[w_idx].rows > 0 && windowData[w_idx].cols > 0) {
          var temp = windowData[w_idx];
          temp.width = min_block_size * windowData[w_idx].cols + "rpx";
          temp.height = min_block_size * windowData[w_idx].rows + "rpx";
          temp.top = (windowData[w_idx].x - 1) * min_block_size + "rpx";
          temp.left = (windowData[w_idx].y - 1) * min_block_size + "rpx";
          window_block.push(temp);
        }
      }
      newVal['data'] = window_block;
    },
  }
});