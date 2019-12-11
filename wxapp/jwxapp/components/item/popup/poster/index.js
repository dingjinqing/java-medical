var util = require("../../../../utils/util.js");
var base = require("../../../popup/base/base.js");
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    image:{
      type: Array,
      value:null
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
    saveImage(){
      util.base64ImageHandle(this.data.image,res=>{
        console.log(res)
      })
      this.bindClose()
    }
  }
})
