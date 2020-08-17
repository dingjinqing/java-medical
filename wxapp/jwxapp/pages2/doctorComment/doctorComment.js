var util = require('../../utils/util.js')
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    isAttention: false,
    star: [{
        show: true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      }
    ],
    isAnonymou:false,
    params: {
      commNote: '',
      imSessionId: null,
      orderSn: null,
      patientId: null,
      doctorId: null,
      stars: 5,
      isAnonymou: 0,
    },
    con:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {
      orderSn,
      patientId,
      doctorId,
      imSessionId
    } = options
    this.setData({
      'params.orderSn': orderSn,
      'params.patientId': patientId,
      'params.doctorId': doctorId,
      'params.imSessionId': imSessionId,
      doctorId:doctorId
    })
    this.getDoctor()
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
  choose_focus() {
    this.setData({
      isAttention: !this.data.isAttention
    })
    this.updateFocus()
  },
  choose_star(e) {
    let idx = e.currentTarget.dataset.idx;
    let star = this.data.star,
      count = 0;
    star.forEach((item, index) => {
      if (index <= idx) {
        item.show = true
        count++
      } else {
        item.show = false
      }
    })
    console.log(count)
    this.setData({
      star: star,
      'params.stars': count
    })
  },
  bindCheckInput: function (e) {
    let commNote = e.detail.value;
    this.setData({
      'params.commNote': commNote
    })
  },
  radioChange: function () {
    this.setData({
      isAnonymou: !this.data.isAnonymou
    })
    let isAnonymou = 0
    if(this.data.isAnonymou == true) isAnonymou = 1
    this.setData({
      'params.isAnonymou':isAnonymou
    })
  },
  submit() {
    util.api('/api/wxapp/patient/doctor/comment/add', function (res) {
      console.log(res)
      if (res.error == 0) {
        util.redirectTo({
          url: 'pages2/doctorConsultation/doctorConsultation?tab=1'
        })
      }
    }, {
      ...this.data.params
    })
  },
  updateFocus() {
    let focus = this.data.isAttention;
    let status;
    if (focus == true) {
      status = 1
    } else {
      status = 0
    }
    util.api('/api/wxapp/user/doctor/attention/update', function (res) {
      console.log(res)
      if (res.error == 0) {
        if(status == 1){
          util.toast_success('已关注');
        }else{
          util.toast_success('取消关注');
        }
     
      }
    }, {
      doctorId: this.data.doctorId,
      userId: util.getCache('user_id'),
      status: status
    })
  },
  getDoctor(){
    let that = this;
    util.api('/api/wxapp/consultation/doctor/info', function (res) {
      console.log(res)
      if (res.error == 0) {
        that.setData({
            con:res.content,
            isAttention:res.content.isAttention
          })
      }
    }, {
      doctorId: that.data.doctorId,
      userId: util.getCache('user_id'),
    })
  }

})