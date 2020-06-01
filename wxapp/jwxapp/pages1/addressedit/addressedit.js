// pages1/addressedit/addressedit.js
import util from '../../utils/util'
var address_parse = require('../../utils/smartWeChat/js/address_parse')

global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    textarea: '',
    region: [],
    regionCode: [],
    formData: {
      consignee: '',
      mobile: '',
      address: '',
      districtCode: '',
      isDefault: false,
      zipcode: ''
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
    let textarea = '新疆阿克苏温宿县博孜墩柯尔克孜族乡吾斯塘博村一组306号 150-3569-6956 马云 (201011)'
    console.log(textarea)
    await this.getAddressData()
    let address =  await this.smart(textarea)
    console.log(address)
    let formData = {
      consignee: address.name,
      mobile: address.phone,
      address: address.address,
      districtCode: address.districtCode||'',
      isDefault: false
    }
    let region = [address.province, address.city, address.county]
    this.setData({formData:formData, region: region})
  },

  getWechatAdress () {
    wx.chooseAddress({
      success (op) {
        console.log('微dizhi:',op)
        util.api('/api/wxapp/address/wxadd', res => {
          console.log(res)
          if (res.error === 0) {
            util.redirectTo({
              url: '/pages/personalcenter/personalcenter'
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

  bindTextAreaBlur () {},

  /**
   * 选择地址
   */
  bindRegionChange (e) {
    console.log('picker发送选择改变，携带值为', e)
    this.setData({
      regionCode: e.detail.code,
      'formData.zipcode': e.detail.postcode,
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
              region: [content.postalName, content.cityName, content.districtName]
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
    if (formData.consignee === '') {
      wx.showToast({
        title: '请填写收件人!'
      })
      return false;
    } else if (formData.mobile === '') {
      wx.showToast({
        title: '请填写正确的联系电话！'
      })
      return false;
    } else if (formData.region.lenng <= 0) {
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
    // 处理地址
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