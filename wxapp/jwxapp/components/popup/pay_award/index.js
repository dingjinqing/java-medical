var base = require("../base/base.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    awardInfo:{
      type:Object,
      value:null,
      observer(val){
        console.log(val)
        this.setStep(val)
        this.getStyle(val.giftInfo)
        this.setData({
          giftInfo:val.giftInfo,
          hasGift:this.hasGift(val.giftInfo),
        })
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 设置步骤条
    setStep({stepInfo}){
      if(!stepInfo || !stepInfo.hasStep) return
      let stepList = [],{currentStep} = stepInfo
      for(let i = 1;i<=stepInfo.totalStep;i++){
        stepList.push(i)
      }
      console.log(currentStep)
      this.setData({
        stepInfo:{
          currentStep,
          stepList
        }
      })
    },
    hasGift(giftInfo){
      const params = new Map([
        [0,null],
        [1,'couponView'],
        [2,'couponView'],
        [3,'lotteryId'],
        [4,'account'],
        [5,'product'],
        [6,'scoreNumber'],
        [7,'customImage']
      ])
      return giftInfo.awardInfo[params.get(giftInfo.giftType)] != null
    },
    getStyle(giftInfo){
      let DialogStyle = 'background:#f66 url('+this.data.imageUrl+'image/wxapp/pay_award_coupon_top_bg.png) no-repeat left -16rpx / 100%;',DialogClass = ''
      if([4,5,6].includes(giftInfo.giftType) && this.hasGift(giftInfo)){
        DialogClass =  'no-coupon'
        DialogStyle = 'background:#FFDFBB;'
      } else if (giftInfo.giftType === 2 && this.hasGift(giftInfo)){
        DialogClass = 'split'
        DialogStyle = 'background:#fb645e;border-radius:0'
      }
      this.setData({
        DialogClass,
        DialogStyle
      })
    }
  }
});
