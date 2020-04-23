// components/item/address_choose/index.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    chooseAddress(){
      wx.chooseAddress({
        success: (res) => {
          let {provinceName = '',cityName = '',countyName = '',detailInfo = ''} = res
          this.setData({
            addressText: `${provinceName}${cityName}${countyName}${detailInfo}`
          })
        },
        fail: () => {
          wx.getSetting({
            success: function(res) {
              if (!res.authSetting['scope.address']) {
                util.showModal('是否打开设置页面', '需要获取您的位置信息，请到小程序的设置页面打开授权', function() {
                  wx.openSetting({
                    success: function(res) {
                    }
                  })
                })
              }
            }
          })
        }
      })
    },
  }
})
