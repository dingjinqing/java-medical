const util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsData: {
      type: Object,
      value: null
    }
  },
  /**
   * 组件的初始数据
   */
  data: {},

  /**
   * 组件的方法列表
   */
  methods: {
    toItem(){
      util.jumpLink(`pages/item/item?goodsId=${this.data.goodsData.goodsId}`,'navigateTo')
    }
  }
});
