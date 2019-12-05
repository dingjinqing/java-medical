var base = require("../../../components/popup/base/base.js");
var filterBase = require("./filter_base/filter_base.js");
global.wxComponent({
  mixins: [base, filterBase],

  /**
   * 组件的属性列表
   */
  properties: {
  },

  /**
   * 组件的初始数据
   */
  data: {
    showSortDialog:false,
    showBrandDialog:false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    showSort(){
      this.setData({ showSortDialog:true })
    },
    showBrand(){
      this.setData({ showBrandDialog:true })
    }
  }
});
