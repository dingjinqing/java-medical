const decorate = require("../../../pages/common/decorate.js")
const util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsDescInfo:{
      type:Object,
      value:null,
      observer(val){
        this.setData({goodsDesc:util.filterRichText(val.goodsDesc)})
        if (val.goodsPageId) {
          decorate.requestDecoratePageData(val.goodsPageId, 0, this.processWindowData.bind(this));
          console.log(val.goodsDesc)
          
          this.setData({
            pageUp: val.isPageUp === 0 ? ['Page', 'Desc'] : ['Desc', 'Page'],
          })
        }
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    pageUp:['Desc']
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
