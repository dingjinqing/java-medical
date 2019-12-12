
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
    showBrandDialog:false,
    minPrice:null,
    maxPrice:null,
    selectedSort: null,
    selectedBrands: [],
    selectedLabels:[],
    selectedActTypes:[]
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
      if (!goodsBrands) return null
      let arr = []
      Object.values(goodsBrands).forEach(item=>{
        arr = [...b,...item]
      })
      return arr
    },
    reset(){
      this.setData({
        minPrice: null,
        maxPrice: null,
        selectedSort: null,
        selectedBrands: [],
        selectedLabels: [],
        selectedActTypes: []
      })
    },
    changeInput(e){
      let target = {
        'min':'minPrice',
        'max':'maxPrice'
      }
      this.setData({
        [target[e.currentTarget.dataset.target]]: e.detail.value
      })
    },
    // 选择分类
    chooseSort(e){
      let { sortId: selectedSort } = e.currentTarget.dataset
      this.setData({
        selectedSort
      })
    },
    // 选择品牌
    chooseBrands(e){
      let {brandId} = e.currentTarget.dataset
      let selectedBrands = this.data.selectedBrands
      let idx = selectedBrands.indexOf(brandId)
      if (idx === -1) {
        selectedBrands.push(brandId)
      } else {
        selectedBrands.splice(idx,1)
      }
      this.setData({ selectedBrands })
    },
    // 选择标签
    chooseLabels(e){
      let { labelId } = e.currentTarget.dataset
      let selectedLabels = this.data.selectedLabels
      let idx = selectedLabels.indexOf(brandId)
      if (idx === -1) {
        selectedLabels.push(brandId)
      } else {
        selectedLabels.splice(idx, 1)
      }
      this.setData({ selectedLabels })
    },
    // 选择活动
    // 确定
    confirm(){
      let selectedSort = this.data.selectedSort
      let selectedBrands = this.data.selectedBrands
      let selectedLabels = this.data.selectedLabels
      let selectedActTypes = this.data.selectedActTypes
      let minPrice = parseFloat(this.data.minPrice)
      let maxPrice = parseFloat(this.data.maxPrice)
      this.triggerEvent('confirm', { selectedSort, selectedBrands, selectedLabels, selectedActTypes, minPrice, maxPrice} )
      this.bindClose()
    }
  }
});
