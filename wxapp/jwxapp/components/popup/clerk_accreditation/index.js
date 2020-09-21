var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  data: {
    isPharmacist: true,
    showCanvas: false,
    accountName:'',
    password:'',
    mobile:'',
    mobileCheckCode:'',
    isPharmacist:0
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
      util.api('/api/wxapp/store/storeClerk/send/check/code', res => {
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
    },
    changeAccountName({detail:{value}}){
      this.data.accountName = value
    },
    changePassword({detail:{value}}){
      this.data.password = value
    },
    changeMobile({detail:{value}}){
      this.data.mobile = value
    },
    changeMobileCheckCode({detail:{value}}){
      this.data.mobileCheckCode = value
    },
    toggleIsPharmacist(){
      this.setData({
        isPharmacist:this.data.isPharmacist ? 0 : 1
      })
    },
    confirm(){
      if(!this.data.accountName){
        util.showModal('提示','请输入门店账户')
        return
      }
      if(!this.data.password){
        util.showModal('提示','请输入账户密码')
        return
      }
      if(!this.data.mobile){
        util.showModal('提示','请输入手机号')
        return
      }
      if (!/^1[3456789]\d{9}$/.test(this.data.mobile)) {
        util.showModal("提示", "请输入正确的手机号！");
        return false;
      }
      if(!this.data.mobileCheckCode){
        util.showModal('提示','请输入验证码')
        return
      }
    }
  }
});