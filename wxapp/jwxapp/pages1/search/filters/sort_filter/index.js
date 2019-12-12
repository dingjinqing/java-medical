var base = require("../../../../components/popup/base/base.js");
var filterBase = require("../filter_base/filter_base.js");
global.wxComponent({
  mixins: [base, filterBase],

  /**
   * 组件的属性列表
   */
  properties: {
    sorts:Array
  },

  /**
   * 组件的初始数据
   */
  data: {
    changeStatus:false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    toogleChange(){
      this.setData({ changeStatus: !this.data.changeStatus})
    }
  }
});
