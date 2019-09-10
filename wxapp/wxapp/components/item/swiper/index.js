global.wxComponent({

  properties: {
    gd: Object
  },

  methods: {
    bindPlayVideo(e) {
      this.setData({
        show_play: true
      });
    },
    bindPreviewUrls(e) {
      var d = this.eventData(e);
      wx.previewImage({
        current: d.src,
        urls: Object.values(this.data.gd.imgs),
      })
    }
  }
})