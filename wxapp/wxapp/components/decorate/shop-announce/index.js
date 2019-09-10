var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  pageLifetimes: {
    show() { 
      console.log("pageLifetimes show");
    },
  },
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      var m = this.data.m = newVal;
      this.initAnimation(m.shop_text, m);
    },
    onShow() {
      this.clearTimers();
      this.initAnimation(this.data.m.shop_text, this.data.m);
    },
    initAnimation: function (shop_announce_Text, m) {
      var _this = this;
      _this.animation = wx.createAnimation({
        duration: 12000,
        timingFunction: 'linear'
      })
      var announcen_len = (shop_announce_Text.length * 13) + 120;
      m.width = announcen_len;
      m.animationData = m.animationData || {};
      if (announcen_len > 480) {
        _this.animation.translate(0, 0).step({
          duration: 0
        });
        m.animationData = _this.animation.export();
        _this.$set();
        this.createTimer("timeout", "name", function () {
          _this.animation.translate(-Number(announcen_len), 0).step();
          m.animationData = _this.animation.export();
          _this.$set();
          this.createTimer("interval", "name1", function () {
            _this.animation.translate(310, 0).step({
              duration: 0
            });
            m.animationData = _this.animation.export();
            _this.$set();
            _this.animation.translate(-Number(announcen_len), 0).step();
            m.animationData = _this.animation.export();
            _this.$set();
          }.bind(_this), 12000);
        }.bind(_this), 4000)
      }
    },
  }
});