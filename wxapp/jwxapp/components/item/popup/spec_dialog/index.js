const base = require("../../../popup/base/base.js")
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    productsInfo:{
      type:Object,
      value:null,
      observer(val){
        this.formatSpec(val.products)
      }
    }
  },
  lifetimes:{
    ready(){
      // let arr = [
      //   { prdDesc: "鞋码:40;颜色:红色;规格:abc"},
      //   { prdDesc: "鞋码:41;颜色:黄色;规格:abc"},
      //   { prdDesc: "鞋码:42;颜色:绿色;规格:abc"},
      //   { prdDesc: "鞋码:43;颜色:蓝色;规格:abc"},
      //   { prdDesc: "鞋码:44;颜色:青色;规格:abc"},
      //   { prdDesc: "鞋码:45;颜色:桔色;规格:abc"},
      //   { prdDesc: "鞋码:46;颜色:大红;规格:abc"},
      //   { prdDesc: "鞋码:47;颜色:大蓝;规格:abc"},
      //   { prdDesc: "鞋码:48;颜色:大青;规格:abc"},
      //   { prdDesc: "鞋码:49;颜色:大绿;规格:abc"},
      //   { prdDesc: "鞋码:50;颜色:红绿;规格:abc"},
      //   { prdDesc: "鞋码:10;颜色:橘黄;规格:abc"}
      // ]
      // this.formatSpec(arr)
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    specList:null,
    checkedProduct:null
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 格式化规格信息
    formatSpec(spec){
      let specList = {}
      let specAggregation = spec.map(item=>item.prdDesc.split(';'))
      specAggregation.map(item=>{
        item.map(d =>{
          let specItem =  d.split(':')
          if (!specList[specItem[0]]){
            specList[specItem[0]] = [{ specName: specItem[1],isChecked:true}]
          } else {
            specList[specItem[0]] = [...specList[specItem[0]], { specName: specItem[1], isChecked: false }] 
          }
        })
      })
      this.setData({
        specList: specList
      })
      this.getCheckedProduct(this.data.specList)
    },
    // 切换规格按钮
    tapSpec(e){
      let d = this.eventData(e)
      let pastIndex = this.data.specList[d.key].findIndex(item=>item.isChecked === true)
      this.setData({
        [`specList.${d.key}[${pastIndex !== -1 ? pastIndex : 0}].isChecked`]: false,
        [`specList.${d.key}[${d.index}].isChecked`]: true
      })
      this.getCheckedProduct(this.data.specList)
    },
    // 获取选中组合后规格信息
    getCheckedProduct(specList){
      let str = ''
      for (let i in specList){
        str += `;${i}:${specList[i].filter(item => item.isChecked)[0].specName}`
      }
      str = str.substring(1)
      let productTarget = this.data.productsInfo.products.filter(item=>item.prdDesc === str)[0]
      this.setData({
        checkedProduct: productTarget
      })
    }
  }
})
