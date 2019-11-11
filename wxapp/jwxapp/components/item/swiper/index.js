// components/item/swiper/index.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsMedia:{
      type:Object,
      optionalTypes:[Number]
    }
  },
  data:{
    showVideo:false
  },
  /**
   * 组件的方法列表
   */
  methods: {
    preview(e) {
      var d = this.eventData(e);
      wx.previewImage({
        current: d.src,
        urls: this.data.goodsMedia.goodsImgs
      })
    },
    playVideo(){
      this.setData({
        showVideo: true
      })
    }
  }
})
