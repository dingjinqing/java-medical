var base = require("../base/base.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    usePayInfo: Object,
    userScore:Number,
    scoreMax:Number,
    scoreMin:Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    scoreInPut:0,
    useMaxScore:0,
    canConfirm:true,
    msg:''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    bindClose() {
      this.setData({
        show: false
      })
      this.triggerEvent('close', 'score')
    },
    init(){
      let { moneyPaid } = this.data.usePayInfo
      moneyPaid = moneyPaid * 100;
      let scoreMax = this.data.scoreMax * 100
      let userScore = this.data.userScore
      let scoreMin = this.data.scoreMin
      let useMaxScore = moneyPaid > scoreMax ? (scoreMax > userScore ? userScore : scoreMax) : (moneyPaid > userScore ? userScore : moneyPaid);
      let msg = ''
      let canConfirm = true
      if(userScore === 0) {
        msg = this.$t("pages.checkout.integralTips[1]")
        canConfirm = false
      } else if (userScore < scoreMin){
        msg = this.$t("pages.checkout.integralTips[2]", { scoreMin: scoreMin})
        canConfirm = false
      } else if (useMaxScore === 0){
        msg = this.$t("pages.checkout.integralTips[3]")
        canConfirm = false
      }
      this.setData({ 
        useMaxScore,
        scoreInPut: useMaxScore,
        msg,
        canConfirm
      })
    },
    changeInput(e){
      let msg = ''
      let canConfirm = true
      let { value: scoreInPut } = e.detail
      scoreInPut = parseInt(scoreInPut)
      console.log(scoreInPut)
      if (isNaN(scoreInPut) || scoreInPut === '' || !isNaN(scoreInPut) && scoreInPut < 0) {
        msg = this.$t("pages.checkout.integralTips[4]")
        canConfirm = false
      } else if (scoreInPut === 0) {
        msg = this.$t("pages.checkout.integralTips[5]")
        canConfirm = false
      } else if (scoreInPut > this.data.userScore) {
        msg = this.$t("pages.checkout.integralTips[6]")
        canConfirm = false
      } else if (scoreInPut < this.data.scoreMin){
        msg = this.$t("pages.checkout.integralTips[2]", { scoreMin: this.data.scoreMin })
        canConfirm = false
      } else if (scoreInPut > this.data.useMaxScore){
        msg = this.$t("pages.checkout.integralTips[7]", { useMaxScore: this.data.useMaxScore})
        canConfirm = false
      }
      this.setData({
        scoreInPut,
        msg,
        canConfirm
      })
    },
    confirmData() {
      let intNum = parseInt(this.data.scoreInPut)
      this.triggerEvent('confirm', intNum)
      this.triggerEvent('close', 'score')
    }
  },
  observers: {
    'show': function (val) {
      if(val){
        this.init()
      }
    }
  }
});
