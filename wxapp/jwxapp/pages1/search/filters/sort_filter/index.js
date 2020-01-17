var base = require("../../../../components/popup/base/base.js");
var filterBase = require("../filter_base/filter_base.js");
global.wxComponent({
  mixins: [base, filterBase],

  /**
   * 组件的属性列表
   */
  properties: {
    sorts:Array,
    selectedSort:Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    showParentId:null
  },

  /**
   * 组件的方法列表
   */
  methods: {
    toogleChange(e){
      let {sortId} = e.currentTarget.dataset,showParentId = this.data.showParentId
      if(sortId === showParentId){
        showParentId = null
      } else {
        showParentId = sortId
      }
      this.setData({
        showParentId
      })
    },
    chooseSort(e){
      let {sortId} = e.currentTarget.dataset
      this.triggerEvent('choose', { sortId })
      this.bindClose()
    }
  }
});
