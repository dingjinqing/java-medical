var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var fix_top = require("../../common/fix_top.js");
global.wxComponent({
  mixins: [base, fix_top],
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
      newVal.cur_idx = `c_${newVal.cur_idx}`
      newVal.fixed = false;
    },
    bindSearchConfirm(e) {
      util.jumpLink('/pages/newsearch/newsearch');
    },
    toSort(e) {
      util.jumpLink('/pages/sort/sort');
    },
    onPageScroll(e) {
      var _this = this;
      var m = this.data.m;
      if (m.search_position == '1') {
        this.getRect("#" + m.cur_idx).then(function (rect) {
          var top = _this.getFixeTop();
          if (!m.fixed && rect.top <= top) {
            m.fixed = true;
            m.fix_height = rect.height;
            m.top = top;
            _this.startFixed(rect.height);
            _this.$set();
          } else {
            if (m.fixed && rect.top > top) {
              m.fixed = false;
              _this.stopFixed();
              _this.$set();
            }
          }
        });
      }
    }
  },
});