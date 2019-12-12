const base = require("../../../popup/base/base.js");
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
        if (val.defaultPrd === true){
          this.triggerEvent("productData", {
            goodsId: val.goodsId,
            ...val.products[0],
            limitBuyNum:val.limitBuyNum,
            limitMaxNum:val.limitMaxNum
          });
        } else {
          this.formatSpec(val.products);
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
    // 格式化规格信息
    formatSpec(spec) {
      let specList = {};
      let specAggregation = spec.map(item => item.prdDesc.split(";"));
      specAggregation.map(item => {
        item.map(d => {
          let specItem = d.split(":");
          if (!specList[specItem[0]]) {
            specList[specItem[0]] = [
              { specName: specItem[1], isChecked: true }
            ];
          } else {
            if (specList[specItem[0]].some(item => item.specName === specItem[1])) return;
            specList[specItem[0]] = [
              ...specList[specItem[0]],
              { specName: specItem[1], isChecked: false }
            ];
          }
        });
      });
      this.setData({
        specList: specList
      });
      this.getCheckedProduct(this.data.specList);
    },
    // 切换规格按钮
    tapSpec(e) {
      let d = this.eventData(e);
      let pastIndex = this.data.specList[d.key].findIndex(
        item => item.isChecked === true
      );
      this.setData({
        [`specList.${d.key}[${
          pastIndex !== -1 ? pastIndex : 0
        }].isChecked`]: false,
        [`specList.${d.key}[${d.index}].isChecked`]: true
      });
      this.getCheckedProduct(this.data.specList);
    },
    // 获取选中组合后规格信息
    getCheckedProduct(specList) {
      let str = "";
      for (let i in specList) {
        str += `;${i}:${
          specList[i].filter(item => item.isChecked)[0].specName
        }`;
      }
      str = str.substring(1);
      let productTarget = this.data.productsInfo.products.filter(
        item => item.prdDesc === str
      )[0];
      this.setData({
        checkedProduct: productTarget
      });
      let { limitBuyNum, limitMaxNum } = this.data.productsInfo;
      console.log(productTarget)
      this.triggerEvent("productData", {
        goodsId: this.data.productsInfo.goodsId,
        ...productTarget,
        limitBuyNum,
        limitMaxNum
      });
    },
    bindClose(){
      this.triggerEvent('close')
    }
  }
});
