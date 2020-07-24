var base = require("../base/base.js");
var util = require("../../../utils/util.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    prescriptionData:Object
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
    viewRxSearch(){
      util.jumpLink(`pages/search/search${util.getUrlParams({
        pageFrom:102,
        outerPageParam:JSON.stringify({
          prescriptionCode:this.data.prescriptionData.prescriptionCode
        })
      })}`)
    },
    viewRepurchase(){
      util.jumpLink(`pages1/repurchaselist/repurchaselist${util.getUrlParams({
        prescriptionCode:this.data.prescriptionData.prescriptionCode
      })}`)
    }
  }
});
