// components/item/purchase_record/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsRecords: {
      type: Array,
      value: [],
      observer (val) {
        console.log(val)
        // 计算距离顶部的高度
        this.handleToCalculationTop()
      }
    }
  },
  data: {
    top_nav: 0
  },
  /**
   * 组件的方法列表
   */
  methods: {
    handleToCalculationTop () {
      var top_nav = 0
      if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
        top_nav = wx.getMenuButtonBoundingClientRect().bottom
      } else {
        wx.getSystemInfo({
          success: (res) => {
            top_nav = res.statusBarHeight * 3
          }
        })
      }
      that.setData({
        top_nav: top_nav
      })
    }
  }
})
