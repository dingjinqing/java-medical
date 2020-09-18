var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  data: {
    mobile: '',
    isDoctor: true,
    showCanvas: false
  },
  lifetimes: {},
  methods: {
    getVerificationCode() {
      if (this.data.countDown) return false
      if (!this.data.mobile) {
        util.showModal("提示", "请输入手机号！");
        return false;
      }
      if (!/^1[3456789]\d{9}$/.test(this.data.mobile)) {
        util.showModal("提示", "请输入正确的手机号！");
        return false;
      }
      util.api('/api/wxapp/user/patient/send/sms', res => {
        if (res.error === 0) {
          const TIME_COUNT = 60;
          this.setData({
            countDown: TIME_COUNT
          })
          if (!this.timer) {
            this.timer = setInterval(() => {
              if (this.data.countDown > 0 && this.data.countDown <= TIME_COUNT) {
                this.setData({
                  countDown: --this.data.countDown
                })
              } else {
                clearInterval(this.timer)
                this.timer = null
              }
            }, 1000)
          }
        } else {
          util.showModal('提示', res.message)
        }
      }, {
        mobile: this.data.mobile
      })
    },
    showCanvasContent() {
      this.setData({
        showCanvas: true
      })
    }
  }
});