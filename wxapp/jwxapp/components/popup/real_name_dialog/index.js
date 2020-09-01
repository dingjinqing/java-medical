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
    realName:''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    changeInput(e){
      let {value:realName} = e.detail
      this.setData({
        realName
      })
    },
    comfirmName(){
      this.triggerEvent('getRealName',{realName:this.data.realName})
    }
  }
});
