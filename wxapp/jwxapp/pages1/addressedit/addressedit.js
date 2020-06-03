// pages1/addressedit/addressedit.js
import util from '../../utils/util'
var address_parse = require('../../utils/smartWeChat/js/address_parse')

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    addressId: '',
    textarea: '',
    region: [],
    regionCode: [],
    formData: {
      consignee: '',
      mobile: '',
      address: '',
      districtCode: '',
      isDefault: false,
      zipCode: ''
    },
    autoSwitch: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (option) {
    let that = this
    if (option && option.addressId) {
      this.setData({
        addressId:option.addressId
      })
      util.api('/api/wxapp/address/get', res => {
        if (res.error === 0) {
          let content = res.content
          content.zipCode = content.zipcode
          that.setData({
            region: [content.provinceName, content.cityName, content.districtName],
            regionCode: [content.provinceCode, content.cityCode, content.districtCode],
            formData: Object.assign({}, content)
          })
        }
      }, {addressId: option.addressId})
    }
    // 初始化自动识别swicth
    wx.getStorage({
      key: 'autoSwitch',
      success: res => {
        console.log(res)
        const autoSwitch = res.data
        if (autoSwitch === false||autoSwitch === true) {
          that.setData({
            autoSwitch: autoSwitch
          })
        }
      }
    })
    
  },

  handleAutoSwicth (e) {
    console.log(e)
    let value = e.detail.value
    this.setData({
      autoSwitch: value
    })
    wx.setStorage({
      data: value,
      key: 'autoSwitch'
    })
  },

  smart (val){
    return address_parse.method(val || '')
  },
  getAddressData (){//手动重新挂载数据
    address_parse.getData()
  },
  /**
   * 自动识别快递信息
   */
  async handleAutoRecognition () {
    let textarea = this.data.textarea
    await this.getAddressData()
    let address =  await this.smart(textarea)
    console.log(address)
    let formData = {
      consignee: address.name,
      mobile: address.phone,
      address: address.address,
      districtCode: address.districtCode||'',
      zipCode: address.zipCode,
      isDefault: false
    }
    let region = [address.province, address.city, address.county]
    this.setData({formData:formData, region: region})
  },

  /**
   * 从微信获取地址
   */
  getWechatAdress () {
    wx.chooseAddress({
      success (op) {
        console.log('微dizhi:',op)
        util.api('/api/wxapp/address/wxadd', res => {
          console.log(res)
          if (res.error === 0) {
            util.redirectTo({
              url: '/pages/personalcenter/personalcenter?tabIndex=2'
            })
          } else {
            util.showModal('提醒', res.message)
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

  /**
   * 自动识别收货信息填写
   */
  bindTextAreaInput (e) {
    console.log(e)
    let value = e.detail.value
    this.setData({
      textarea: value
    })
  },

  /**
   * 选择地址
   */
  bindRegionChange (e) {
    console.log('picker发送选择改变，携带值为', e)
    this.setData({
      regionCode: e.detail.code,
      'formData.zipCode': e.detail.postcode,
      region: e.detail.value
    })
  },

  /**
   * 定位
   */
  handleRecognition () {
    let that = this
    wx.getLocation({
      altitude: 'altitude',
      success (res) {
        console.log('location:', res)
        util.api('/api/wxapp/address/getLocation',  op=> {
          console.log(op)
          if (op.error === 0) {
            let content = op.content
            that.setData({
              region: [content.postalName, content.cityName, content.districtName],
              regionCode: [content.postalId, content.cityId, content.districtId]
            })
          } else {
            util.showModal('提醒', '定位失败')
          }
        }, {
          lat: res.latitude,
          lng: res.longitude
        })
      }
    })
  },

  /**
   * 编辑表单
   */
  bindChangeHandle (e) {
    console.log(e)
  },

  /**
   * 设置默认
   */
  handleDefault (e) {
    console.log(e)
    let value = e.detail.value
  },

  /**
   * 保存
   */
  formSubmit (e) {
    console.log(e)
    // 校验用户填写
    let formData = e.detail.value
    this.setData({
      formData: formData
    })
    if (formData.consignee === '') {
      wx.showToast({
        title: '请填写收件人!'
      })
      return false;
    } else if (formData.mobile === '' || !(/^[\d-]{7,11}$/.test(formData.mobile))) {
      wx.showToast({
        title: '请填写正确的联系电话！'
      })
      return false;
    } else if (this.data.region.lenng <= 0) {
      wx.showToast({
        title: '请选择所在地区！',
      })
      return false
    } else if (formData.address === '') {
      wx.showToast({
        title: '请填写街道信息！',
      })
      return false
    }
    if (!formData.isDefault) {
      this.setData({
        'formData.isDefault': 0
      })
    } else {
      this.setData({
        'formData.isDefault': 1
      })
    }
    // 处理地址
    this.processAddress()
    console.log(this.data.formData)
    if (!this.data.addressId) {
      // 新增
      util.api('/api/wxapp/address/add', res => {
        if (res.error === 0) {
          console.log(res)
          util.toast_success('新增成功!')
          util.navigateTo('/pages/personalcenter/personalcenter?tabIndex=2')
        } else {
          util.fail_toast(res.message)
        }
      }, this.data.formData)
    } else {
      // 编辑
      this.setData({
        'formData.addressId': this.data.addressId
      })
      formData.addressId = this.data.addressId
      util.api('/api/wxapp/address/update', res => {
        if (res.error === 0) {
          console.log(res)
          util.toast_success('更新成功!')
          util.navigateTo('/pages/personalcenter/personalcenter?tabIndex=2')
        } else {
          util.fail_toast(res.message)
        }
      }, this.data.formData)
    }
  },

  processAddress () {
    let region = this.data.region
    let regionCode = this.data.regionCode
    let formData = this.data.formData
    if (region.length > 0) {
      formData.provinceName = region[0]
      formData.cityName = region[1]
      formData.districtName = region[2]
    }
    if (regionCode.length > 0) {
      formData.provinceCode = regionCode[0]
      formData.cityCode = regionCode[1]
      formData.districtCode = regionCode[2]
    }
    console.log(formData)
    this.setData({
      formData: formData
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})