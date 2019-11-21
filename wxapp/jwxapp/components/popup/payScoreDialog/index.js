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
      let msg = ''
      let canConfirm = true
      if(userScore === 0) {
        msg = `可用积分为0无法支付`
        canConfirm = false
      } else if (userScore < scoreMin){
        msg = `积分支付最小使用${scoreMin}`
        canConfirm = false
      } 
      this.setData({ 
        useMaxScore: moneyPaid > scoreMax ? (scoreMax > userScore ? userScore : scoreMax) : (moneyPaid > userScore ? userScore : moneyPaid ),
        scoreInPut: moneyPaid > scoreMax ? (scoreMax > userScore ? userScore : scoreMax) : (moneyPaid > userScore ? userScore : moneyPaid),
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
        msg = '请输入正确的积分'
        canConfirm = false
      } else if (scoreInPut === 0) {
        msg = '积分需大于0'
        canConfirm = false
      } else if (scoreInPut > this.data.userScore) {
        msg = `超出已有积分数量`
        canConfirm = false
      } else if (scoreInPut < this.data.scoreMin){
        msg = `积分支付最小使用${this.data.scoreMin}`
        canConfirm = false
      } else if (scoreInPut > this.data.useMaxScore){
        msg = `最多可以使用：${this.data.useMaxScore}积分`
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
