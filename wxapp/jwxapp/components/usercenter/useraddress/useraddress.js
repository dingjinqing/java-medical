// components/usercenter/useraddress/useraddress.js
import util from '../../../utils/util'

global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    data: {
      type: Array,
      value: [],
      observer(val){
        console.log(val)
      }
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
      let that = this
      wx.chooseAddress({
        success (op) {
          console.log('微dizhi:',op)
          util.api('/api/wxapp/address/wxadd', res => {
            console.log(res)
            if (res.error === 0) {
              that.triggerEvent('refreshlist')
            }
          }, {
            wxAddress:op
          })
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
    },

    // 设置默认地址
    changeDefaultRadio(e) {
      console.log(e)
      let that = this
      let id = Number(e.currentTarget.dataset.id)
      util.api('/api/wxapp/address/default', res => {
        if (res.error === 0) {
          that.triggerEvent('refreshlist')
        } else {
          util.toast_fail(res.message)
        }
      }, {
        addressId: id
      })
    },

    // 删除地址
    handleDeleteAddress (e) {
      let that = this
      console.log(e)
      let id = e.currentTarget.dataset.id
      if (id) {
        util.confirm('提示', '确认要删除该地址吗？', () => {
          util.api('/api/wxapp/address/remove', res => {
            if (res.error === 0) {
              console.log(res)
              util.toast_success('删除成功!')
              that.triggerEvent('refreshlist')
            } else {
              util.toast_fail(res.message)
            }
          }, {
            addressId: id
          })
        })
      }
    },
    // 编辑地址
    handleEditAddress (e) {
      let id = e.currentTarget.dataset.id
      wx.navigateTo({
        url: '/pages1/addressedit/addressedit?addressId='+id
      })
    }
  }
})
