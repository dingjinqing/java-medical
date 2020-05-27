// components/usercenter/useraddress/useraddress.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    comColor: String
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
    getWechatAdress () {
      wx.chooseAddress({
        success (res) {
          console.log(res)
        },
        fail (err) {
          console.log(err)
        }
      })
    }
  }
})
