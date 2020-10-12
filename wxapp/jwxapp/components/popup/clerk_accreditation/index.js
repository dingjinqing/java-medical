var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  data: {
    showCanvas: false,
    accountName:'',
    password:'',
    mobile:'',
    mobileCheckCode:'',
    isPharmacist:0
  },
  properties:{
    clerkImagePath:{
      type:Object,
      value:null,
      observer:(val)=>{
        console.log(val)
      }
    }
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
      // this.setData({
      //   showCanvas: true
      // })
      util.jumpLink('/pages3/clerkSign/clerkSign')
    },
    changeAccountName({detail:{value}}){
      this.setData({
        accountName:value
      })
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

      if(!this.data.clerkImagePath.imgPath && this.data.isPharmacist){
        util.showModal('提示','请签名添加图片')
        return
      }

      util.api('/api/wxapp/store/storeClerk/auth',result=>{
        console.log(result)
        if(result.error === 0){
          util.jumpLink('/pages3/clerkIndex/clerkIndex')
        } else {
          util.showModal('提示',result.message)
        }
      },{
        accountName:this.data.accountName,
        password:this.data.password,
        mobile:this.data.mobile,
        mobileCheckCode:this.data.mobileCheckCode,
        isPharmacist:this.data.isPharmacist,
        signature:this.data.clerkImagePath.imgPath
      })
    }
  }
});