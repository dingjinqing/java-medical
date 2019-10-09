var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    //  当传入的模块数据改变时触发
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal)
      if (newVal.search_font == 0) {
        newVal.height = '80rpx'
      } else if (newVal.search_font == 2) {
        newVal.height = '56rpx'
      } else {
        newVal.height = '68rpx'
      }
    },
    bindSearchConfirm(e) {
      // var search_text = e.detail.value;
      // util.jumpLink('/pages/newsearch/newsearch');
    },
    toSort(e) {
      // util.jumpLink('/pages/sort/sort');
    }
  },
});