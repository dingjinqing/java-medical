// pages1/prescriptionsheet/prescriptionsheet.js
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
var util = require('../../utils/util.js');
const { theMaximumClaimLimit } = require('../../utils/i18n/components/decorate/decorate.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    array: ['请选择', '男', '女'],
    sex: 0,
    comm_img: [],
    name: '',
    age: '',
    orderAmount:0,
    descriptionDisease:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!options) return;
    let doctorId = options.doctor_id;
    let departmentId = options.depar_id;
    let orderAmount = options.price;
    this.requestDefault()
    this.setData({
      doctorId:doctorId,
      departmentId:departmentId,
      orderAmount:orderAmount
    })
  },
  bindSexChange: function (e) {
    this.setData({
      sex: e.detail.value
    })
  },
  //上传图片
  customUpImage: function (e) {
    var that = this;
    var comm_img = that.data.comm_img;
    util.uploadImage(1, function (res) {
      let data = JSON.parse(res.data);
      console.log(data)
      if (data.error == 0) {
        comm_img.push(data.content.imgUrl);
        that.setData({
          comm_img: comm_img,
          image: true,
        })
      }
    });
  },
  customDelImage: function (e) {
    var that = this;
    var imgindex = e.currentTarget.dataset.imgindex;
    var comm_img = that.data.comm_img;
    comm_img.splice(imgindex, 1);
    that.setData({
      comm_img: comm_img,
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

  },
  realName: function (e) {
    let name = e.detail.value;
    this.setData({
      name: name,
    })
  },
  realAge: function (e) {
    let age = e.detail.value;
    this.setData({
      age: age,
    })
  },
  bindCheckInput: function(e) {
    let descriptionDisease = e.detail.value;
    this.setData({
      descriptionDisease:descriptionDisease
    })
  },
  toOrder: function () {
    let that = this;
    let params = {
      doctorId:that.data.doctorId,
      departmentId:that.data.departmentId,
      patientId:that.data.patientId,
      descriptionDisease:that.data.departmentId,
      imagUrl:that.data.comm_img,
      orderAmount:that.data.orderAmount
    }
    util.api(
      '/api/wxapp/inquiry/order/pay',
      async res => {
        console.log(res)
          if (res.error === 0) {
            let {
              orderSn
            } = res.content
            console.log(orderSn)
            util.navigateTo({
              url: "/pages2/patientChat/patientChat?orderSn=" + orderSn
            })
            // if (this.data.choosePayType === 0 && res.content.webPayVo && paymentList.wxpay) {
            //   wx.requestPayment({
            //     timeStamp: res.content.webPayVo.timeStamp,
            //     nonceStr: res.content.webPayVo.nonceStr,
            //     package: res.content.webPayVo.package,
            //     signType: 'MD5',
            //     paySign: res.content.webPayVo.paySign,
            //     success: async res => {
            //       util.toast_success('支付成功')
            //       console.log(res)
            //     },
            //     fail: res => {
            //       console.log(res)
            //     },
            //     complete: res => {}
            //   })
            // }
          } else {
            // util.showModal('提示', res.message, function () {
            //   util.jumpLink('/pages/index/index', 'redirectTo')
            // })
          }
        },
        params
    )
  },

  requestDefault: function () {
    let that = this;
    util.api('/api/wxapp/user/patient/get/default', function (res) {
      console.log(res)
      if (res.error == 0 && res.content.id) {
        that.setData({
          patientId: res.content.id,
          name: res.content.name,
          age: res.content.age,
          sex: res.content.sex++
        })
      }
    }, {})
  },
})