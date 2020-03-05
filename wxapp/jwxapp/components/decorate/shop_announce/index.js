var base = require("../mixins/base.js");
var fix_top = require("../../common/fix_top.js");
global.wxComponent({
  mixins: [base, fix_top],
  data: {
    marqueeDistance: 10, // 偏移距离
    countTime: '',
    windowWidth: '',
    shopContentWidth: ''
  },
  pageLifetimes: {
    hide: function () {
      console.log('触发删除定时器+++++++++++++++++++++++')
      console.log(this._timers)
      this.clearTimers();
    }
  },
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      newVal.fixed = false;
      newVal.cur_idx = `c_${newVal.cur_idx}`
      var m = this.data.m = newVal;
      this.initAnimation(m.shop_text, m);
    },
    onShow () {
      this.clearTimers();
      this.initAnimation(this.data.m.shop_text, this.data.m);
    },
    initAnimation: function (shop_announce_Text, m) {
      var _this = this;
      _this.animation = wx.createAnimation({
        duration: 12000,
        timingFunction: 'linear'
      })
      console.log(m)
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
    onPageScroll (e) {
      var _this = this;
      var m = this.data.m;
      if (m.announce_position == '1') {
        this.getRect(`#${m.cur_idx}`).then(function (rect) {
          var top = _this.getFixeTop();
          if (!m.fixed && rect.top <= top) {
            m.fixed = true;
            m.fix_height = rect.height;
            m.top = top
            _this.startFixed(rect.height);
            _this.$set();
          } else {
            if (m.fixed && rect.top > top) {
              m.fixed = false;
              _this.stopFixed();
              _this.$set();
            }
          }
        })
      }
    }
  }
});