var base = require("../../../../components/popup/base/base.js");
var filterBase = require("../filter_base/filter_base.js");
global.wxComponent({
  mixins: [base, filterBase],

  /**
   * 组件的属性列表
   */
  properties: {
    goodsBrands:{
      type:Array,
      value:null,
      observer(newVal){
        this.setData({
          brand_keys: newVal.reduce((defaultVal, val) => { return [ ...defaultVal, val.character ] },[])
        })
      }
    },
    selectedBrands:Array
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
    chooseBrand(e){
      let {brandId} = e.currentTarget.dataset
      this.triggerEvent('choose', {  brandId })
    },
    scrollToView(e) {
      console.log(e)
      let item_id = e.currentTarget.dataset.id == '#' ? 'order' : e.currentTarget.dataset.id;
      this.setData({
        item_id: item_id
      })
    }
  }
});
