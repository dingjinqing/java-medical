var util = require("../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    brand_info: {
      type:Object,
      observer(newVal){
        console.log(newVal)
        this.setData({
          brand_keys: Object(newVal ? newVal : '')
        })
      }
    },
    is_reco:Number,
    right_height:Number,
    is_search: Number,
    show_rcommend_brand:Number,
    is_brand: Number,
    all_brand:Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    item_id:''
  },
  /**
   * 组件的方法列表
   */
  methods: {
    scrollToView(e){
      console.log(e)
      let item_id = e.currentTarget.dataset.id == '#' ? 'order' : e.currentTarget.dataset.id;
      this.setData({
        item_id:item_id
      })
    },
    to_search(e){
      let brand_id = e.currentTarget.dataset.brand_id
      util.jumpLink(`pages1/search/search?brandIds=${JSON.stringify([brand_id])}`,'navigateTo')
    },
    search_brand(e){
      var brand_id = e.currentTarget.dataset.brand_id
      this.triggerEvent('search_brand', {brand_id:brand_id});
    }
  }
})
