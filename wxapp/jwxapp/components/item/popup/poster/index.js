var util = require("../../../../utils/util.js");
var base = require("../../../popup/base/base.js");
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    isMultiple:{
      type:Boolean,
      value:false
    },
    posterImage:String
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
    saveImage(){
      console.log(this.data.isMultiple)
      util.base64ImageHandle(this.data.posterImage,res=>{
        console.log(res)
      })
      if (this.data.isMultiple){

      }
      this.bindClose()
    }
  }
})
