var base = require("../base/base.js");
var util = require("../../../utils/util.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
   dialogType:{
     type:Number,
     value:1
   },
   patientInfo:{
     type:Object,
     value:null
   },
   source:{
     type:String,
     value:''
   }
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
      util.jumpLink(`pages1/prescriptionsheet/prescriptionsheet${util.getUrlParams({
        patientInfo:JSON.stringify(this.data.patientInfo)
      })}`)
      this.bindClose()
    },
    addPatient(){
      util.jumpLink(`pages1/familylist/familylist?source=${this.data.source}`)
      this.bindClose()
    }
  }
});
