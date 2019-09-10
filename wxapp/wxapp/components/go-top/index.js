global.wxComponent({
  data: {
    top_display: 0
  },
  properties: {
    content:Object,
    isPictorialShow:Number,
  },
  methods: {
    onPageScroll(e) {
      if (!this._windowHeight) this._windowHeight = wx.getSystemInfoSync().windowHeight;
      var top_display = (e.scrollTop >= this._windowHeight);
      if (top_display != this.data.top_display) {
        this.setData({
          top_display: top_display
        });
      }
    },
    bindGoTop: function(e) {
      // 一键回到顶部
      if (wx.pageScrollTo) {
        wx.pageScrollTo({
          scrollTop: 0
        })
      } else {
        util.alert('当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。')
      }
    },
    go_share:function(){
      this.triggerEvent('go_share');
    },
  }
})