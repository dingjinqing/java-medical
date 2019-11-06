// components/item/swiper/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsImage:{
      type:Object,
      optionalTypes:[Number]
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    preview(e) {
      var d = this.eventData(e);
      let previewImage = this.data.goodsImage.map(item=>{
        let imgUrl = `${this.data.imageUrl}${item}`
        return imgUrl
      })
      wx.previewImage({
        current: d.src,
        urls: previewImage
      })
    }
  }
})
