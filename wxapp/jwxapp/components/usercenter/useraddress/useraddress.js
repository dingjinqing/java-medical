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
    orderOptions: null,
    prevPage: null
  },

  ready () {
    
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoad (op) {
      let that = this
      let {chooseCurrentTarget = null,deliverType = null} = op
      this.setData({
        chooseCurrentTarget,
        deliverType
      })
      if (op.select) {
        this.setData({
          select: op.select
        })
        wx.getStorage({
          key: 'orderOptions',
          success (res) {
            if (res) {
              if (res.data.addressId) {
                delete res.data.addressId
              }
              that.setData({
                orderOptions: res.data
              })
            }
          }
        })
      }
      this.initData()
    },
     /**
     * 请求收货地址
     */
    initData () {
      let that = this
      let addressList = []
      util.api('/api/wxapp/address/list', res => {
        if (res.error === 0) {
          console.log(res.content)
          // 默认选中一个
          if (that.data.select) {
            let list = res.content.addressList || []
            let len = list.length
            for(let i = 0; i< len; i++) {
              let item = list[i]
              if (that.data.select && that.data.select != -1) {
                if (item.addressId == that.data.select) {
                  item.select = true
                  break
                }
              } else {
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
          }
          if(this.data.deliverType === '1'){
            addressList = [{type:'chooseCurrentTarget'},...res.content.addressList]
          } else {
            addressList = [...res.content.addressList]
          }
          that.setData({
            addressList: addressList.length && addressList || []
          })
        }
      })
    },

    // 微信导入后新增到数据库
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
    
    // 新增收货地址
    newAdressHandle () {
      wx.navigateTo({
        url: '/pages1/addressedit/addressedit?select='+this.data.select,
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
        url: '/pages1/addressedit/addressedit?addressId='+id+'&select='+this.data.select
      })
    },

    // 选中地址
    catchSelectAddress (e) {
      console.log(e)
      if (this.data.select) {
        let id = e.currentTarget.dataset.id
        let addressList = this.data.addressList
        let chooseCurrentTarget = id === 'chooseCurrentTarget' ? 1 : 0
        if(!chooseCurrentTarget){
          addressList.forEach(item => {
            if (item.addressId === id) {
              item.select = true
            } else {
              item.select = ''
            }
          })
        }
        this.setData({
          addressList: addressList
        })
        let pages = getCurrentPages()
        let sourcePage = this.getPrevPage(pages)
        let prevPage = sourcePage.page
        console.log(prevPage)
        if (prevPage && (prevPage.route === 'pages/item/item' || prevPage.route === 'pages/checkout/checkout')) {
          if (prevPage.route === 'pages/checkout/checkout') {
            if(chooseCurrentTarget == 1){
              prevPage.setData({
                chooseCurrentTarget
              })
            } else {
              prevPage.setData ({
                addressId: id,
                'params.addressId': id,
                chooseCurrentTarget
              })
            }
          } else {
            prevPage.setData({
              addressId: id
            })
          }
          wx.navigateBack({
            delta: sourcePage.index
          })
          return false
        }
        console.log(this.data.orderOptions)
        let opts = this.stringOpts(this.data.orderOptions)
        console.log(opts, id)
        util.navigateTo('/pages/checkout/checkout?addressId='+id+'&'+opts)
      }
    },

    // 获取
    getPrevPage (pages) {
      let len = pages.length
      let count = 0
      for(let i = len-1; i>=0; i--) {
        let page = pages[i]
        if (page.route === 'pages/item/item' || page.route === 'pages/checkout/checkout') {
          return {
            page: page,
            index: count
          }
        }
        count++
      }
      return {}
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
  },
  // pageLifetimes: {
  //   show () {
  //     this.initData()
  //   }
  // }
})
