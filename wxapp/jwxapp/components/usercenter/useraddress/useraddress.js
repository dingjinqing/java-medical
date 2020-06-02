// components/usercenter/useraddress/useraddress.js
import util from '../../../utils/util'

global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    comColor: String,
    options: Object
  },

  /**
   * 组件的初始数据
   */
  data: {
    addressList: [],
    select: '',
    orderOptions: null
  },

  ready () {
    this.initData()
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoad (op) {
      let that = this
      console.log(op)
      if (op.select) {
        this.setData({
          select: op.select
        })
        wx.getStorage({
          key: 'orderOptions',
          success (res) {
            if (res) {
              that.setData({
                orderOptions: res.data
              })
            }
          }
        })
      }
    },
     /**
     * 请求收货地址
     */
    initData () {
      let that = this
      util.api('/api/wxapp/address/list', res => {
        if (res.error === 0) {
          console.log(res.content)
          // 默认选中一个
          if (that.data.select) {
            let list = res.content.addressList
            let len = list.length
            for(let i = 0; i< len; i++) {
              let item = list[i]
              if (item.isDefault) {
                item.select = true
                break
              } else {
                if (i >= len-1) {
                  item.select = true
                }
                break
              }
            }
          }
          that.setData({
            addressList: res.content.addressList || []
          })
        }
      })
    },
    getWechatAdress () {
      let that = this
      wx.chooseAddress({
        success (op) {
          console.log('微dizhi:',op)
          util.api('/api/wxapp/address/wxadd', res => {
            console.log(res)
            if (res.error === 0) {
              // that.triggerEvent('refreshlist')
              that.initData()
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
          // that.triggerEvent('refreshlist')
          that.initData()
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
              // that.triggerEvent('refreshlist')
              that.initData()
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
    },

    // 选中地址
    catchSelectAddress (e) {
      console.log(e)
      if (this.data.select) {
        let id = e.currentTarget.dataset.id
        this.data.addressList.map(item => {
          if (item.addressId === id) {
            item.select = true
          } else {
            item.select = ''
          }
        })
        this.setData({
          addressList: this.data.addressList
        })
        console.log(this.data.orderOptions)
        let opts = this.stringOpts(this.data.orderOptions)
        console.log(opts)
        // util.navigateTo('/pages/checkout/checkout?addressId='+id+'&'+this.data.orderOptions)
      }
    },

    stringOpts (opts) {
      let str = ''
      for (const key in opts) {
        const op = opts[key];
        str += key+'='+ op +'&'
      }
      str = str.slice(0, str.length-1)
      return str
    }
  }
})
