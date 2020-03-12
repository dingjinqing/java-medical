// components/decorate/form/index/index.js
var util = require("../../../../utils/util.js")

global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    page_content: {
      type: Object,
      value: {},
      observer(newVal, oldVal, changedPath) {
        console.log(newVal)
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

  }
})
