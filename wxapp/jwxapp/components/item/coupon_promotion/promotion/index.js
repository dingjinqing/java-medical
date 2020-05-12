const base = require("../../../popup/base/base.js");
const util = require('../../../../utils/util.js');
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    promotion:Array
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
    goPromotion(e){
      let {id,type} = e.currentTarget.dataset
      if(type === '19' || type === '18') return
      const linkData = {
        7: {url:'/pages/maingoodslist/maingoodslist',params:{identity_id:id}},
        15:{url:'pages1/fullship/fullship',params:{ruleId:id}},
        21:{url:'/pages/fullprice/fullprice',params:{strategy_id:id}}
      }
      console.log(`${linkData[type].url}${this.getUrlParams(linkData[type].params)}`)
      util.jumpLink(`${linkData[type].url}${this.getUrlParams(linkData[type].params)}`,'navigateTo')
    },
    //整合参数
    getUrlParams (obj) {
      return Object.keys(obj).reduce((UrlStr, item, index) => {
        if (index !== 0) UrlStr += `&`
        return UrlStr += `${item}=${obj[item]}`
      }, '?')
    },
  }
});
