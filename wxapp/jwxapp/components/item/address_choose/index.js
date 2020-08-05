const util = require('../../../utils/util.js');
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    addressInfo:{
      type:Object,
      value:null,
      observer(){
        this.initAddress()
      }
    },
    goodsId:Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    addressId: -1
  },

  /**
   * 组件的方法列表
   */
  methods: {
    initAddress(){
      let {addressId = -1, provinceName = '',cityName = '',districtName = '',address = ''} = this.data.addressInfo
      console.log(provinceName, cityName, districtName, address)
      this.setData({
        addressText: `${provinceName || ''}${cityName || ''}${districtName || ''}${address || ''}`,
        addressId: addressId
      })
    },
    chooseAddress(){
      util.navigateTo('/components/usercenter/useraddress/useraddress?select='+this.data.addressId)
      // wx.chooseAddress({
      //   success: (res) => {
      //     let {provinceName = '',cityName = '',countyName = '',detailInfo = ''} = res
      //     this.setData({
      //       addressText: `${provinceName}${cityName}${countyName}${detailInfo}`
      //     })
      //     util.api(
      //       '/api/wxapp/address/choose',
      //       res => {
      //         console.log(res)
      //         if (res.error === 0) {
      //           this.triggerEvent('addressChange',{...res.content})
      //         }
      //       },
      //       { wxAddress: { ...res }, goodsId:this.data.goodsId }
      //     )
      //   },
      //   fail: () => {
      //     wx.getSetting({
      //       success: function(res) {
      //         if (!res.authSetting['scope.address']) {
      //           util.showModal('是否打开设置页面', '需要获取您的位置信息，请到小程序的设置页面打开授权', function() {
      //             wx.openSetting({
      //               success: function(res) {
      //               }
      //             })
      //           })
      //         }
      //       }
      //     })
      //   }
      // })
    },
  }
})
