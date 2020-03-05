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
      value:null,
      observer(val){
        console.log(val)
      }
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
      this.data.image.forEach(item=>{
        util.base64ImageHandle(item,res=>{
          console.log(res)
        })
      })
      this.bindClose()
    }
  }
})
