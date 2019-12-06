var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    close_img_url: String,
    back_style: {
      type: String,
      value: ""
    },

    hide_close: Boolean,
    horizon: {
      type: String,
      value: "init" // left center right fill, 默认值为init表示center
    },
    vertical: {
      type: String,
      value: "center", // top center bottom fill
      observer (newVal, oldVal, changedPath) {
        if (this.data.horizon == 'init' && (newVal == 'top' || newVal == 'bottom')) {
          this.setData({
            horizon: "fill"
          });
        }
      }
    },
    close_pos: {
      type: String,
      value: "outer", // outer inner mask-corner outer-bottom
    }
  },
  data: {
    margin_top_nav: 0
  },
  ready () {
    if (!this.data.close_img_url) {
      var img = this.data.close_pos == 'inner' ? "close_icon" : "split_btn1";
      this.data.close_img_url = this.data.imageUrl + "image/wxapp/" + img + ".png";
      this.setData({
        close_img_url: this.data.close_img_url
      });
    }
    var height = 0
    if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
      height = wx.getMenuButtonBoundingClientRect().bottom
    } else {
      wx.getSystemInfo({
        success: (res) => {
          height = res.statusBarHeight * 3
        }
      })
    }
    this.setData({
      margin_top_nav: height
    })
  }
})