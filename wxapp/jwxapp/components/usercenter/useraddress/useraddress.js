// components/usercenter/useraddress/useraddress.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    data: {
      type: Array,
      value: []
    },
    comColor: String,
    options: Object
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 观察者
   */
  observers: {
    data: function (val) {
      console.log(val)
    }
  },

  ready () {
    console.log(this.data.data)
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
    },
    // 微信导入后新增到数据库
    newAdressHandle () {
      wx.navigateTo({
        url: '/pages1/addressedit/addressedit',
      })
    }
  }
})
