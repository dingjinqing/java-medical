var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal)
      var max_height_px = 0;
      var windowWidth = wx.getSystemInfoSync().windowWidth;
      for (var i = 0; i < newVal['img_items'].length; i++) {
        var item = newVal['img_items'][i];
        var h = parseFloat(item.src_height / item.src_width * windowWidth);
        if (!isNaN(h) && h > max_height_px)
          max_height_px = h;
      }
      newVal.swiperHeight = max_height_px == 0 ? 300 : (750 / windowWidth) * max_height_px; // 单位rpx
    }
  }
});