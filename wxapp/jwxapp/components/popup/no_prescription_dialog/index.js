var base = require("../base/base.js");
var util = require("../../../utils/util.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
   
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
    viewPrescriptionSheet(){
      util.jumpLink('pages1/prescriptionsheet/prescriptionsheet')
    }
  }
});
