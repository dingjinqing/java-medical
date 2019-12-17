var util = require("../../../utils/util.js");
const actInfo = {
  5:{canCountDown:[3,0],3:'startTime',0:'endTime'} //拼团
}
const nameList = {
  1:'拼团',
  3:'砍价',
  5:'秒杀'
}
const actStatusNameList = {
  5:{1:'活动不存在',2:'活动已停用',5:'商品已抢光',4:'活动已结束',3:'距开始仅剩',0:'距结束仅剩',6:'超购买上限'} //拼团
}
const priceName = {5:{prdRealPrice:'secKillPrice',prdLinePrice:'prdPrice'}}
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    activity:{
      type:Object,
      value:null,
      observer(val){
        this.getCountDown(val)
        this.setData({
          actName:this.getActName(val),
          actStatusName:this.getActStatusName(val),
          prdRealPrice:this.getMin(val.actProducts.map(item => {let {[priceName[val.activityType].prdRealPrice]:prdRealPrice} = item; return prdRealPrice})),
          prdLinePrice:this.getMin(val.actProducts.map(item => {let {[priceName[val.activityType].prdLinePrice]:prdLinePrice} = item; return prdLinePrice}))
        })
      }
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
  },
  lifetimes: {
    detached() {
      clearTimeout(this.data.time);
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    getActName({activityType}){
      if(!activityType) return null
      return nameList[activityType]
    },
    getActStatusName({actState,activityType}){
      return actStatusNameList[activityType][actState] || null
    },
    getCountDown({activityType,actState,endTime,startTime}){
      if(!actInfo[activityType].canCountDown.includes(actState)) return
      let total_micro_second = Math.round((new Date(actInfo[activityType][actState] === 'startTime' ? startTime:endTime).getTime() - new Date().getTime()) / 1000)
      this.countdown(total_micro_second,actState,activityType)
    },
    // 获取最小值
    getMin(arr) {
      return Math.min(...arr);
    },
    countdown(total_micro_second,actState,activityType) {
      let clock =
        total_micro_second <= 0
          ? "已经截至"
          : util.dateformat(total_micro_second);
      this.setData({
        clock: clock
      });
      if(actInfo[activityType][actState] === 'endTime' && total_micro_second > 0){
        this.triggerEvent('actStatus',{canBuy:true})
      } else {
        this.triggerEvent('actStatus',{canBuy:false})
      }
      if (total_micro_second <= 0) return;
      this.setData({
        time: setTimeout(() => {
          total_micro_second -= 1;
          this.countdown(total_micro_second,actState,activityType);
        }, 1000)
      });
    }
  }
});
