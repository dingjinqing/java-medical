var base = require("../base/base.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    memberCardList:Array,
    defaultMemberCardNo:String
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
    init(){
      console.log(this.data.defaultMemberCardNo)
     let cardList =  this.data.memberCardList.map(item=>{
        let cardItem = JSON.parse(JSON.stringify(item))
        cardItem.cardBgStyle = this.getCardBg(cardItem);
        cardItem.cardExpireTime = this.getCardExpireTime(cardItem)
        cardItem.isChecked = (this.data.defaultMemberCardNo && cardItem.cardNo === this.data.defaultMemberCardNo)
        return cardItem
     })
     this.setData({
       cardList
     })
    },
    getCardBg(cardItem) {
      console.log(cardItem);
      switch (cardItem.bgType) {
        case 0:
          return `background-color:${cardItem.bgColor};`;
        case 1:
          return `background:url('${this.data.imageUrl}${cardItem.bgImg}') no-repeat top left / 100% 100%;`;
      }
    },
    // 获取会员卡过期时间
    getCardExpireTime(cardItem) {
      if (cardItem.cardType === 2) return null
      if (cardItem.expireType === 2) return `永久有效`
      if (cardItem.expire === 1) return `此卡已过期，如需继续使用请联系商家`
      return `${cardItem.startDate} 至 ${cardItem.endDate}`
    },
    selectCard(e){
      let idx = e.currentTarget.dataset.index
      if (this.data.cardList[idx].isChecked === true){
        let target = `cardList[${idx}].isChecked`
        this.setData({
          [target]:false
        })
      } else {
        let oldTargetIdx = this.data.cardList.findIndex(item=>item.isChecked === true)
        if (oldTargetIdx !== -1){
          this.setData({
            [`cardList[${oldTargetIdx}].isChecked`]: false,
          })
        }
        this.setData({
          [`cardList[${idx}].isChecked`]: true
        })
      }
    },
    confirm(){
      let target = this.data.cardList.find(item=>item.isChecked === true)
      this.bindClose()
      if (!target) {this.triggerEvent('confirm', null); return}
      this.triggerEvent('confirm', target.cardNo)
    }
  },
  observers: {
    'show': function (val) {
      if (val) {
        this.init()
      }
    }
  }
});
