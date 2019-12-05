var filterBase = {
  properties: {
    navHeight: Number
  },

  observers: {
    'show': function (val) {
      let animation = wx.createAnimation({
        duration: 600,
        timingFunction: 'ease'
      })
      if (val === true) {
        animation.translateX(0).step()
      } else {
        animation.translateX('600rpx').step()
      }
      this.setData({
        animation: animation.export()
      })
    }
  }
};
module.exports = filterBase;