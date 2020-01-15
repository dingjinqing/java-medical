var base = require("../../popup/base/base.js")
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
    showSelectedDialog:false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    showSelected(){
      this.setData({
        showSelectedDialog:true
      })
    },
    handleSearch(e){
      this.triggerEvent('search',e.detail.value)
    }
  }
})
