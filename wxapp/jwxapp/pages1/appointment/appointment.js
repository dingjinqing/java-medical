// pages1/appointment/appointment.js
let util = require('../../utils/util')
let app = getApp()
let imageUrl = app.globalData.imageUrl;

global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    serviceId: '',
    imageUrl: imageUrl,
    img_cart: imageUrl + '/image/wxapp/cart_icon.png',
    img_store: imageUrl + 'image/wxapp/sto_logo1.png',
    img_close: imageUrl + '/image/wxapp/close_icon.png',
    img_addr: imageUrl + '/image/wxapp/store_address.png',
    img_time: imageUrl + '/image/wxapp/store_time.png',
    img_charge: imageUrl + '/image/wxapp/icon_notice.png',
    img_arrow: imageUrl + '/image/wxapp/backward_right.png',
    img_sercer: imageUrl + '/image/wxapp/server_icon.png',
    img_success: imageUrl + '/image/wxapp/con_btn_success.png',
    img_iconsel: imageUrl + '/image/wxapp/selected.png',
    img_service: imageUrl + 'image/wxapp/icon_service.png',
    square_no: imageUrl + 'image/wxapp/icon_rectangle.png',

    storeInfo: {}, // 门店信息
    serviceInfo: {}, // 门店服务信息
    reservationInfoList: [], // 门店服务可预约时间详情
    reserveInfo: {}, // 预约信息
    commentInfo: [], // 评论

    timeMode: false, // 预约时间弹窗
    show_id: 0, // 预约日期id
    show_time: 0, // 预约时间id

    techMode: false, // 可预约技师弹窗
    technicianFlag: 1, // 服务是否有技师
    technicianPojoList: [], // 技师列表
    tech_id: '',
    tech_name: '', // 技师名称

    technicianTitle: '', // 职称

    mobile: ''
  },

  /**
   * 
   */
  showMap() {
    let address = this.data.storeInfo.address;
    let latt = parseFloat(this.data.storeInfo.latitude);
    let lonn = parseFloat(this.data.storeInfo.longitude);
    wx.openLocation({
      latitude: latt,
      longitude: lonn,
      scale: 28,
      name: address
    })
  },

  toBack() {
    util.showModal('提示', '找不到该服务', function () {
      wx.navigateBack()
    })
  },

  /**
   * 评价
   */
  click_to_detail() { },
  clickComment() { },

  bindGetPhoneNumberOk(e) {
    this.setData({
      mobile: e.detail.phoneNumber
    })
  },

  /**
   * 选择预约时间
   */
  timeShow() {
    if (this.data.reservationInfoList && this.data.reservationInfoList.length > 0) {
      this.setData({
        timeMode: true
      })
      if (this.data.show_id === 0) {
        this.setData({
          'reserveInfo.date': this.data.reservationInfoList[0].reservationDate
        })
      }
      if (this.data.show_time === 0) {
        this.setData({
          'reserveInfo.startTime': this.data.reservationInfoList[0].reservationTimeList[0].startTime,
          'reserveInfo.endTime': this.data.reservationInfoList[0].reservationTimeList[0].endTime
        })
      }
    } else {
      util.showModal('提示', '暂无时间可选择！');
    }
  },
  timeClose() {
    this.setData({
      timeMode: false
    })
  },
  selectedDay(e) {
    let key = e.currentTarget.dataset.key
    let date = e.currentTarget.dataset.date
    this.setData({
      show_id: key,
      'reserveInfo.date': date
    })
  },
  selectedHour(e) {
    let key = e.currentTarget.dataset.key
    let startTime = e.currentTarget.dataset.start_time
    let endTime = e.currentTarget.dataset.end_time
    this.setData({
      show_time: key,
      'reserveInfo.startTime': startTime,
      'reserveInfo.endTime': endTime
    })
  },
  timeConfirm() {
    this.setData({
      timeMode: false
    })
    if (this.data.reservationInfoList[this.data.show_id].reservationTimeList[this.data.show_time].technicianFlag == 1) {
      this.setData({
        technicianFlag: 1,
        technicianPojoList: this.data.reservationInfoList[this.data.show_id].reservationTimeList[this.data.show_time].technicianPojoList
      })
    }
    if (this.data.serviceInfo.serviceType == 1) {
      this.techShow()
    }
  },

  /**
   * 技师，选择技师
   */
  techShow() {
    if (this.data.technicianPojoList) {
      this.setData({
        techMode: true
      })
    } else {
      util.showModal('提示', '暂无技师可选择');
    }
  },
  techClose() {
    this.setData({
      techMode: false
    })
  },
  chooseTach(e) {
    let key = e.currentTarget.dataset.index
    let id = e.currentTarget.dataset.id
    let name = e.currentTarget.dataset.name
    this.setData({
      tech_id: id,
      tech_name: name
    })
  },
  techConfirm() {
    this.setData({
      techMode: false,
      'reserveInfo.tech_name': this.data.tech_name,
      'reserveInfo.tech_id': this.data.tech_id
    })
  },

  /**
   * 立即预约
   */
  reservationNow() {
    console.log(this.data.reserveInfo)
    let reserveInfo = this.data.reserveInfo
    if (!reserveInfo || !reserveInfo.date || !reserveInfo.startTime) {
      this.timeShow()
      return false
    }
    if (this.data.serviceInfo.serviceType === 1 && !reserveInfo.tech_name) {
      this.techShow()
      return false
    }
    this.setData({
      'reserveInfo.serviceId': this.data.serviceId
    })
    // 校验是否选择预约时间
    util.navigateTo({
      url: '/pages/appointorder/appointorder?data=' + JSON.stringify(this.data.reserveInfo),
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let that = this
    let serviceId = options.service_id
    if (!serviceId) {
      that.toBack()
      return false
    }
    that.setData({
      serviceId: serviceId
    })
    util.api('/api/wxapp/store/service/reservation', function (res) {
      if (res.error === 0) {
        console.log(res.content)
        let serviceInfo = res.content.serviceInfo
        let storeInfo = res.content.storeInfo
        let reservationInfoList = res.content.reservationInfoList
        if (storeInfo.delFlag == 1 || serviceInfo.delFlag == 1) {
          util.showModal('提示', '该服务已删除', function () {
            util.reLaunch({
              url: '/pages/index/index'
            })
          })
          return;
        }
        if (res.content.businessState == 0) {
          // util.showModal('提示', '该店铺未营业，随便逛逛', function () {
          //   util.reLaunch({
          //     url: '/pages/bottom/bottom'
          //   })
          // })
          // return;
        }
        // 格式化
        if (serviceInfo.serviceImg) {
          serviceInfo.serviceImg = JSON.parse(serviceInfo.serviceImg)
        } else {
          serviceInfo.serviceImg = ['']
        }
        if (reservationInfoList) {
          reservationInfoList = reservationInfoList
          // 格式化预约时间数据和技师数据
          reservationInfoList.forEach(function (info) {
            info.reservationTimeList.forEach(function (time) {
              if (time.startTime) {
                time.startTime = time.startTime.toString().slice(0, 5)
                time.endTime = time.endTime.toString().slice(0, 5)
              }
            })
          })
        }
        that.setData({
          reservationInfoList: reservationInfoList,
          storeInfo: storeInfo,
          serviceInfo: serviceInfo,
          technicianTitle: res.content.technicianTitle
        })
      } else {
        that.toBack()
      }
    }, { serviceId: serviceId })
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