const decorate = require("../../../pages/common/decorate.js")
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsDescInfo:{
      type:Object,
      value:null
    }
  },

  /**
   * 组件的初始数据
   */
  data: {},
  lifetimes:{
    ready(){
      decorate.requestDecoratePageData(0, 0, this.processWindowData.bind(this));
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    // 自定义模板加载
    processWindowData: function (pageContent) {
      this.setData({
        pageContent: pageContent
      })
    },
  }
});
