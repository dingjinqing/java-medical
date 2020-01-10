var util = require("../../../utils/util.js");
var base = require("../base/base.js")

global.wxComponent({
  mixins: [base],
  data: {
    net_type: 'unkonwn',
    videoplay: false
  },
  properties: {
    show: {
      type: Boolean,
      value: false,
      observer (newVal, oldVal, changedPath) {
        console.log(newVal)
        this.detectNetworkType();
      }
    },
    video_url: {
      type: String,
      value: "",
    },
    video_size: {
      type: Number,
      value: 0,
    },
    poster: String,
    title: String,
  },
  methods: {
    detectNetworkType () {
      var _this = this;
      wx.getNetworkType({
        success (res) {
          console.log(res)
          _this.setData({
            net_type: res.networkType
          });
        }
      })
    },
    bindVideoPlay: function (e) {
      this.setData({
        videoplay: true
      });
    },
    bindVideoPause: function (e) {
      this.setData({
        videoplay: false
      });
    },
  }
})