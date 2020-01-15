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
      if(type === '19') return
      switch (type) {
        case '7':
          util.jumpLink('TODO')
          break;
        case '15':
          util.jumpLink('TODO')
          break;
        case '18':
          util.jumpLink('TODO')
          break;
        case '21':
          
          break;
      }
      util.jumpLink('TODO')
    }
  }
});
