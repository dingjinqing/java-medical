var base = require("../../../components/popup/base/base.js");
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
    showSortDialog:false,
    showBrandDialog:false
  },

  /**
   * 组件的方法列表
   */
  methods: {
    showSort(){
      this.setData({ showSortDialog:true })
    },
    showBrand(){
      this.setData({ showBrandDialog:true })
    }
  },
  observers:{
    'show':function(val){
      let animation = wx.createAnimation({
        duration: 600,
        timingFunction: 'ease'
      })
      if(val === true){
        animation.translateX(0).step()
      } else {
        animation.translateX('600rpx').step()
      }
      this.setData({
        animation: animation.export()
      })
    }
  }
});
