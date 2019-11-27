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
    }
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
      storeInfo.titleName = this._getDialogTitle(storeInfo.openType);
      this.setData({
        storeInfo: storeInfo
      });
    },
    _getDialogTitle(openType) {
      switch (openType) {
        case 2:
          return `选择同城配送门店`;
        default:
          return `选择自提门店`;
      }
    },
    _selectStore(e) {
      let nArr = this.data.storeInfo.data.map(item => {
        let nObj = JSON.parse(JSON.stringify(item));
        if (e.currentTarget.dataset.id === nObj.id) {
          nObj.checked = true;
        } else {
          nObj.checked = false;
        }
        return nObj;
      });
      this.setData({
        "storeInfo.data": nArr
      });
    },
    _triggerSubmit() {
      let targetData = this.data.storeInfo.data.find(
        item => item.checked === true
      );
      this.triggerEvent("submit", {
        openType: this.data.storeInfo.openType,
        ...targetData
      });
      this.bindClose();
    }
  }
});
