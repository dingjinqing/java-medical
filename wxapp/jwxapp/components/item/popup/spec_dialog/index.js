const base = require('../../../popup/base/base.js')
const actPrdType = {
  1: {
    prdListName: 'groupBuyPrdMpVos',
    prdRealPrice: 'groupPrice',
    prdLinePrice: 'prdPrice',
    multiSkuAct: true
  },
  3: { prdRealPrice: 'bargainPrice', multiSkuAct: false },
  5: {
    prdListName: 'actProducts',
    prdRealPrice: 'secKillPrice',
    prdLinePrice: 'prdPrice',
    multiSkuAct: true
  },
  8: { prdRealPrice: 'payMoney', multiSkuAct: false },
  6: {
    prdListName: 'reducePricePrdMpVos',
    prdRealPrice: 'reducePrice',
    prdLinePrice: 'prdPrice',
    multiSkuAct: true
  },
  10: {
    prdListName: 'preSalePrdMpVos',
    prdRealPrice: 'preSalePrice',
    prdLinePrice: 'prdPrice',
    multiSkuAct: true
  },
  18: {
    prdListName: 'firstSpecialPrdMpVos',
    prdRealPrice: 'firsSpecialPrice',
    prdLinePrice: 'prdPrice',
    multiSkuAct: true
  },
  22: {
    prdListName: 'gradePrdMpVos',
    prdRealPrice: 'gradePrice',
    prdLinePrice: 'prdPrice',
    multiSkuAct: true
  },
  98: {
    prdListName: 'gradeReducePrdVos',
    prdRealPrice: 'activityPrice',
    prdLinePrice: 'prdPrice',
    multiSkuAct: true
  }
}
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    productsInfo: {
      type: Object,
      value: null,
      observer(val) {
        console.log(val)
        let productsInfo = this.data.productsInfo
          if(productsInfo.defaultPrd === true){
            let {limitBuyNum,limitMaxNum,activity} = productsInfo
            this.setData({
              checkedProduct: val.products[0]
            })
            if(activity && [1,5,10].includes(activity.activityType)){
              val.products[0].prdNumber = activity[actPrdType[activity.activityType]['prdListName']][0].stock
              if(activity.activityType === 1){
                console.log(1)
                limitBuyNum = activity.limitBuyNum
                limitMaxNum = activity.limitMaxNum
              }
              if([5,10].includes(activity.activityType)){
                limitMaxNum = activity.limitAmount
              }
            } else if (activity && activity.activityType === 3) {
              val.products[0].prdNumber = activity.stock
            }
            if(activity && [6,18,22].includes(activity.activityType) && activity.isLimit){
              limitMaxNum = activity.limitAmount
            }
            this.triggerEvent('productData', {
              goodsId: val.goodsId,
              ...val.products[0],
              limitBuyNum,
              limitMaxNum
            })
          } else {
            this.spec = this.data.productsInfo.products
            this.defaultSelectSpec()
            this.render()
          }
        // }
      }
    },
    triggerButton: {
      type: String,
      value: '',
      observer(val){
        this.render()
      }
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    specList: null,
    checkedProduct: null
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 默认选择的规格
    defaultSelectSpec() {
      var specs = this.getDefaultSpec()
      console.log(specs)
      if (specs) {
        specs = specs.split(';')
        for (var i in specs) {
          var s = specs[i].split(':')
          this.check(s[0], s[1])
        }
      }
    },
    getDefaultSpec() {
      this.refresh()
      for (var prd_specs in this.skuList) {
        if (this.skuList[prd_specs]['prdNumber'] > 0) {
          return prd_specs
        }
      }
      return false
    },
    // 渲染相关内容
    render() {
      this.refresh()
      this.getSelectPrd()
      this.setData({
        spec_list: this.specList
      })
      console.log(this.data.spec_list)
    },
    refresh() {
      this.initSpecList()
      var spec_list = this.specList
      for (var specName in spec_list) {
        var vals = spec_list[specName]
        for (var valName in vals) {
          var spec = {}
          spec[specName] = valName
          vals[valName].gary = this.isGary(spec)
          if (vals[valName].gary && vals[valName].isChecked) {
            vals[valName].isChecked = false
          }
        }
      }
      this.spec_list = spec_list
    },
    initSpecList() {
      if (this.inited) return
      this.skuList = {}
      for (let n = 0; n < this.spec.length; n++) {
        this.skuList[this.spec[n]['prdDesc']] = this.spec[n]
        if(this.data.productsInfo.activity && actPrdType[this.data.productsInfo.activity.activityType]['prdListName'])
        this.skuList[this.spec[n]['prdDesc']].actProduct = this.data.productsInfo.activity[actPrdType[this.data.productsInfo.activity.activityType]['prdListName']].find(item=>{return item.productId === this.spec[n].prdId})
      }
      this.specList = {}
      Object.keys(this.skuList)
        .map(item => item.split(';'))
        .map(item => {
          item.map(d => {
            let specItem = d.split(':')
            if (!this.specList[specItem[0]]) {
              this.specList[specItem[0]] = {
                [specItem[1]]: { isChecked: false, gary: false }
              }
            } else {
              if (Object.keys(this.specList[specItem[0]]).some(item => item === specItem[1])) return
              this.specList[specItem[0]] = {
                ...this.specList[specItem[0]],
                [specItem[1]]: { isChecked: false, gary: false }
              }
            }
          })
        })
      this.inited = true
    },
    bindClickSpec(e) {
      var d = this.eventData(e)
      this.check(d.key, d.index)
      this.render()
    },
    getSelectPrd() {
      this.select_prd = null
      var spec_list = this.specList
      if (Object.keys(this.select_specs).length == Object.keys(spec_list).length) {
        var prd_list = this.skuList
        var specs = this.select_specs
        console.log(prd_list)
        for (var prd_specs in prd_list) {
          var prd_specs_arr = prd_specs.split(';')
          var found = true
          for (var sid in specs) {
            if (prd_specs_arr.indexOf(sid + ':' + specs[sid]) == -1) {
              found = false
              break
            }
          }
          if (found) {
            this.select_prd = prd_list[prd_specs]
            break
          }
        }
      }
      if (this.select_prd) this.getPrdInfo()
    },
    getPrdInfo() {
      let select_prd = JSON.parse(JSON.stringify(this.select_prd))
      let { limitBuyNum, limitMaxNum,activity} = this.data.productsInfo
      if(activity && (!this.data.triggerButton || this.data.triggerButton === 'right') && activity.activityType !== 3 && activity.activityType !== 8){
        select_prd.prdRealPrice = select_prd['actProduct'][actPrdType[activity.activityType]['prdRealPrice']]
        select_prd.prdLinePrice = select_prd['actProduct'][actPrdType[activity.activityType]['prdLinePrice']]
        if(activity.activityType === 1){
          limitBuyNum = activity.limitBuyNum
          limitMaxNum = activity.limitMaxNum
        }
        if([5,10].includes(activity.activityType) || ([6,18,22].includes(activity.activityType) && activity.isLimit)){
          limitMaxNum = activity.limitAmount
        }
      } else if(activity && (!this.data.triggerButton || this.data.triggerButton === 'right') && (activity.activityType === 3 || activity.activityType === 8)){
        select_prd.prdRealPrice = activity[actPrdType[activity.activityType]['prdRealPrice']]
      }
      if(activity && (this.data.triggerButton === 'right' || !this.data.triggerButton) && [1,5,10].includes(activity.activityType)){
        select_prd.prdNumber = select_prd['actProduct']['stock']
      } else if (activity && (this.data.triggerButton === 'right' || !this.data.triggerButton) && activity.activityType === 3){
        select_prd.prdNumber = activity.stock
      } else {
        select_prd.prdNumber = select_prd['prdNumber']
      }
      this.setData({
        checkedProduct: select_prd
      })
      this.triggerEvent('productData', {
        goodsId: this.data.productsInfo.goodsId,
        ...select_prd,
        limitBuyNum,
        limitMaxNum
      })
    },
    check(specName, valName) {
      var spec_list = this.specList
      if (spec_list[specName][valName].gary) {
        console.error(`specName:${specName} valName:${valName} can not checked`)
        return
      }
      this.select_specs = this.select_specs || {}
      var old_val = this.select_specs[specName]
      if (old_val) {
        delete this.select_specs[specName]
        spec_list[specName][old_val].isChecked = false
      }
      spec_list[specName][valName].isChecked = old_val != valName
      if (old_val != valName) {
        this.select_specs[specName] = valName
      }
    },
    isGary(addSpec) {
      let min = this.getGoodsLimitMin()
      var specs = Object.assign({}, this.select_specs, addSpec)
      var prd_list = this.skuList
      for (var prd_specs in prd_list) {
        var prd_specs_arr = prd_specs.split(';')
        var found = true
        for (var sid in specs) {
          if (prd_specs_arr.indexOf(sid + ':' + specs[sid]) == -1) {
            found = false
            break
          }
        }
        if (found) {
          var stock = null
          if(this.data.productsInfo.activity && (this.data.triggerButton === 'right' || !this.data.triggerButton) && [1,5,10].includes(this.data.productsInfo.activity.activityType)){
            stock = prd_list[prd_specs]['actProduct']['stock']
          } else if (this.data.productsInfo.activity && (this.data.triggerButton === 'right' || !this.data.triggerButton) && this.data.productsInfo.activity.activityType === 3){
            stock = this.data.productsInfo.activity.stock
          } else {
            stock = prd_list[prd_specs]['prdNumber']
          }
          console.log(stock)
          if (stock >= min) return false
        }
      }
      return true
    },
    getGoodsLimitMin(){
      if((!this.data.triggerButton||this.data.triggerButton === 'right') && this.data.productsInfo.activity && this.data.productsInfo.activity.activityType === 1){
        return this.data.productsInfo.activity.limitBuyNum
      }
      return (this.data.productsInfo.limitBuyNum && this.data.productsInfo.limitBuyNum > 0) ? this.data.productsInfo.limitBuyNum : 1;
    },
    bindClose() {
      this.triggerEvent('close')
    }
  }
})
