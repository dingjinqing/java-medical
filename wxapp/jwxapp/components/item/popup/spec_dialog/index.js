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
  6: {
    prdListName: 'reducePricePrdMpVos',
    prdRealPrice: 'reducePrice',
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
        if (val.defaultPrd === true) {
          // 活动规格限制
          let actLimit = {}
          if (val.activity) {
            actLimit.activityType = val.activity.activityType
            if (val.activity.limitMaxNum) {
              actLimit.limitMaxNum = val.activity.limitMaxNum
            }
            if (actPrdType[val.activity.activityType]['prdListName']) {
              actLimit.prdNumber =
                val.activity[actPrdType[val.activity.activityType]['prdListName']][0].stock
            }
            if (val.activity.limitBuyNum) {
              actLimit.limitBuyNum = val.activity.limitBuyNum
            }
          }
          this.setData({
            checkedProduct: val.products[0]
          })
          this.triggerEvent('productData', {
            goodsId: val.goodsId,
            ...val.products[0],
            limitBuyNum: val.limitBuyNum,
            limitMaxNum: val.limitMaxNum,
            ...actLimit
          })
        } else {
          if (val.activity) {
            if (actPrdType[val.activity.activityType].multiSkuAct) {
              let activityPrds = val.activity[
                actPrdType[val.activity.activityType]['prdListName']
              ].map(
                ({
                  productId: prdId,
                  stock: prdNumber,
                  [actPrdType[val.activity.activityType].prdRealPrice]: prdRealPrice,
                  [actPrdType[val.activity.activityType].prdLinePrice]: prdLinePrice,
                  isGradePrice = null
                }) => {
                  return { prdId, prdNumber, prdRealPrice, prdLinePrice, isGradePrice }
                }
              )
              this.setData({
                activityPrds
              })
            }
          }
          this.spec = val.products
          this.defaultSelectSpec()
          this.render()
        }
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
    // 获得价格
    getPrdInfo() {
      let select_prd = this.select_prd
      let { limitBuyNum, limitMaxNum } = this.data.productsInfo
      let actLimit = {}
      // if(this.data.productsInfo.activity){
      //   actLimit.activityType = this.data.productsInfo.activity.activityType
      //   if(this.data.productsInfo.activity.limitBuyNum){
      //     actLimit.limitMaxNum = this.data.productsInfo.activity.limitMaxNum
      //   }
      //   if(this.data.activityPrds){
      //     actLimit.prdNumber = this.data.activityPrds.find(item=>item.prdId === select_prd.prdId).prdNumber
      //   }
      //   if(this.data.productsInfo.activity.limitBuyNum){
      //     actLimit.limitBuyNum = this.data.productsInfo.activity.limitBuyNum
      //   }
      // }
      this.setData({
        checkedProduct: select_prd
      })
      this.triggerEvent('productData', {
        goodsId: this.data.productsInfo.goodsId,
        ...select_prd,
        limitBuyNum,
        limitMaxNum,
        ...actLimit
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
      var specs = Object.assign({}, this.select_specs, addSpec)
      console.log(specs)
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
          var stock = prd_list[prd_specs]['prdNumber']
          console.log(stock)
          if (stock > 0) return false
        }
      }
      return true
    },
    // // 格式化规格信息
    // formatSpec() {
    //   this.getCanCheckSpec()
    //   this.setData({
    //     specList: this.specList
    //   });
    //   // this.getCheckedProduct(specList);
    // },
    // getDefaultChecked(){
    //   this.getCanCheckSpec()
    //   for (var prd_specs in this.skuList) {
    //     if (this.skuList[prd_specs]['prdNumber'] > 0) {
    //       return prd_specs;
    //     }
    //   }
    //   return false
    // },
    // getCanCheckSpec(){
    //   this.initSpec()
    //   for (var specName in this.specList) {
    //     var vals = this.specList[specName];
    //     for (var valName in vals) {
    //       var spec = {};
    //       spec[specName] = valName;
    //       vals[valName].gary = this.gary(spec)
    //     }
    //   }
    // },
    // defaultSelectSpec(){
    //   let specs =  this.getDefaultChecked()
    //   console.log(specs)
    // },
    // getSelectPrd(){

    // },
    // gary(){
    //   console.log(spec)
    // },
    // initSpec(){

    // },
    // 切换规格按钮
    tapSpec(e) {
      let d = this.eventData(e)
      let pastIndex = this.data.specList[d.key].findIndex(item => item.isChecked === true)
      this.setData({
        [`specList.${d.key}[${pastIndex !== -1 ? pastIndex : 0}].isChecked`]: false,
        [`specList.${d.key}[${d.index}].isChecked`]: true
      })
      this.getCheckedProduct(this.data.specList)
    },
    // 获取选中组合后规格信息
    getCheckedProduct(specList) {
      let str = ''
      for (let i in specList) {
        str += `;${i}:${specList[i].filter(item => item.isChecked)[0].specName}`
      }
      str = str.substring(1)
      let productTarget = this.data.productsInfo.products.filter(item => item.prdDesc === str)[0]
      // if(this.data.productsInfo.activity && !actPrdType[this.data.productsInfo.activity.activityType].multiSkuAct){
      //   productTarget.prdLinePrice = productTarget.prdRealPrice
      //   productTarget.prdRealPrice = this.data.productsInfo.activity[actPrdType[this.data.productsInfo.activity.activityType].prdRealPrice]
      // }
      this.setData({
        checkedProduct: productTarget
      })
      let { limitBuyNum, limitMaxNum } = this.data.productsInfo
      let actLimit = {}
      // 活动规格的限购数量
      if (this.data.productsInfo.activity) {
        actLimit.activityType = this.data.productsInfo.activity.activityType
        if (this.data.productsInfo.activity.limitBuyNum) {
          actLimit.limitMaxNum = this.data.productsInfo.activity.limitMaxNum
        }
        if (this.data.activityPrds) {
          actLimit.prdNumber = this.data.activityPrds.find(
            item => item.prdId === productTarget.prdId
          ).prdNumber
        }
        if (this.data.productsInfo.activity.limitBuyNum) {
          actLimit.limitBuyNum = this.data.productsInfo.activity.limitBuyNum
        }
      }
      console.log({
        goodsId: this.data.productsInfo.goodsId,
        ...productTarget,
        limitBuyNum,
        limitMaxNum,
        ...actLimit
      })
      this.triggerEvent('productData', {
        goodsId: this.data.productsInfo.goodsId,
        ...productTarget,
        limitBuyNum,
        limitMaxNum,
        ...actLimit
      })
    },
    bindClose() {
      this.triggerEvent('close')
    }
  }
})
