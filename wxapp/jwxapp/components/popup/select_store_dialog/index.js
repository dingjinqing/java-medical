var base = require("../base/base.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    storeData: {
      type: Object,
      value: null,
      observer(newVal) {
        if (newVal) this._initData(newVal);
      }
    },
    selectedStore:Number
  },

  /**
   * 组件的初始数据
   */
  data: {},

  /**
   * 组件的方法列表
   */
  methods: {
    _initData(data) {
      let storeInfo = JSON.parse(JSON.stringify(data));
      let newArray = Object.keys(storeInfo).sort((a,b)=>{return a - b}).map(item=>{
        console.log(item)
        storeInfo[item].distance = item
        return storeInfo[item]
      })
      console.log(newArray)
      this.setData({
        storeInfo: newArray
      });
    },
    _selectStore(e) {
      let {idx} = e.currentTarget.dataset
      this.setData({
        selectedStore : this.data.storeInfo[idx].storeId
      })
    },
    _triggerSubmit() {
      let targetData = this.data.storeInfo.find(
        item => item.storeId === this.data.selectedStore
      );
      this.triggerEvent("confirm", {
        ...targetData
      });
      this.bindClose();
    }
  }
});
