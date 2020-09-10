var base = require("../base/base.js");
var util = require("../../../utils/util.js");
var app = getApp()
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    patientMessage: {
      type: Object,
      value: null,
      observer(newVal) {
        if (newVal) this.init()
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    d_count: 0,
    f_count: 0
  },

  /**
   * 组件的方法列表
   */
  methods: {
    init() {
      if (this.data.patientMessage.diseaseHistoryList && this.data.patientMessage.familyDiseaseHistoryList) {
        let d_count = 0,f_count = 0;
        this.data.patientMessage.diseaseHistoryList.forEach(item => {
          if (item.checked == 1) d_count++;
        })
        this.data.patientMessage.familyDiseaseHistoryList.forEach(item => {
          if (item.checked == 1) f_count++;
        })
        this.setData({
          d_count: d_count,
          f_count: f_count
        })
      }
    }
  },

});