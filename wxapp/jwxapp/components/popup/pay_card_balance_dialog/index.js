var base = require("../base/base.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    usePayInfo:Object,
    userCardBalance:Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    cardBalanceInput:0,
    canConfirm:true,
    msg:''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    bindClose(){
      this.setData({
        show:false
      })
      this.triggerEvent('close','cardBalance')
    },
    init(){
      let { moneyPaid } = this.data.usePayInfo
      let userCardBalance = this.data.userCardBalance
      let maxCanUse = moneyPaid > userCardBalance ? userCardBalance : moneyPaid
      this.setData({
        maxCanUse,
        cardBalanceInput: maxCanUse,
        canConfirm : maxCanUse > 0 ? true : false,
        msg: maxCanUse > 0 ? '' : this.$t("pages.checkout.balanceTips[1]", { target: this.$t("pages.checkout.membershipCard")})
      })
    },
    changeInput(e){
      let msg = ''
      let canConfirm = true
      let { value: cardBalanceInput} = e.detail
      let floatNum = parseFloat(cardBalanceInput).toFixed(3)
      floatNum = parseFloat(floatNum.substring(0, floatNum.length - 1))
      if (isNaN(floatNum) || floatNum === '' || !isNaN(floatNum) && floatNum < 0){
        msg = this.$t("pages.checkout.balanceTips[2]", { target: this.$t("pages.checkout.membershipCard") })
        canConfirm = false
      } else if (floatNum === 0){
        msg = this.$t("pages.checkout.balanceTips[1]", { target: this.$t("pages.checkout.membershipCard") })
        canConfirm = false
      } else if (floatNum > this.data.maxCanUse){
        msg = this.$t("pages.checkout.balanceTips[3]")
        canConfirm = false
      }
      this.setData({
        cardBalanceInput,
        msg,
        canConfirm
      })
    },
    confirmData(){
      let floatNum = parseFloat(this.data.cardBalanceInput).toFixed(3)
      floatNum = parseFloat(floatNum.substring(0, floatNum.length - 1))
      this.triggerEvent('confirm', floatNum )
      this.triggerEvent('close', 'cardBalance')
    }
  },
  observers:{
    'show':function(val){
      if(val){
        this.init()
      }
    }
  }
});
