var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    bindHotAreaTap: function (e) {
      var m = this.data.m;
      var clientX = e.touches[0].clientX;
      var clientY = e.touches[0].clientY;
      this.getRect('.hot-image-' + m.cur_idx).then(function (rect) {
        console.log(rect, clientX, clientY, m)
        var pX = clientX - rect.left;
        var pY = clientY - rect.top;
        var newX = pX * m.data.bg_img_width / rect.width;
        var newY = pY * m.data.bg_img_height / rect.height;
        var len = m.data.rectangles.length;
        for (var i = len - 1; i >= 0; i--) {
          var area = m.data.rectangles[i];
          console.log(newX, newY, area)
          if (
            newX >= area.x && newX < area.x + area.w &&
            newY >= area.y && newY < area.y + area.h
          ) {
            console.log(area.link_url)
            util.jumpLink(area.link_url);
          }
        }
      });
    }
  }
});