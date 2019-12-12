
const base = require("../../../components/popup/base/base.js");
const filterBase = require("./filter_base/filter_base.js");
const util = require('../../../utils/util.js');
global.wxComponent({
  mixins: [base, filterBase],

  /**
   * 组件的属性列表
   */
  properties: {
  },
  lifetimes:{
    attached(){
      util.api('/api/wxapp/goods/search/init',res=>{
        if(res.error === 0){
          let { sorts, goodsBrands, activityTypes, goodsLabels } = res.content
          this.setData({
            sorts,
            goodsBrands,
            activityTypes,
            goodsLabels,
            formatGoodsBrands: this.getFormatBrand(goodsBrands)
          })
        }
      })
    }
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
    },
    getFormatBrand(goodsBrands){
      let arr = []
      Object.values(goodsBrands).forEach(item=>{
        arr = [...b,...item]
      })
      return arr
    }
  }
});
