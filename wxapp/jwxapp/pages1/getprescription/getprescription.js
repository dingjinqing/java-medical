// pages1/getprescription/getprescription.js
var util = require('../../utils/util.js');
var app = getApp()
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    real_name: '',
    mobile: '',
    card_id: '',
    mobileCheckCode: '',
    if_show_agree: 0,
    if_agree: 0,
    // 从咨询页面调过来
    list: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    if(options.list && options.list == 1){
      this.data.list = 1
    }
    let {
      identityCode,name,mobile
    } = options
    this.setData({
      card_id:identityCode,
      real_name:name,
      mobile:mobile
    })
  },
  realName(e) {
    this.data.real_name = e.detail.value;
  },
  mobileInput(e) {
    this.data.mobile = e.detail.value;
  },
  cardIdInput(e) {
    this.data.card_id = e.detail.value
  },
  verificationInput(e) {
    this.data.mobileCheckCode = e.detail.value
  },
  close_modal(e) {
    this.setData({
      if_show_agree: 0
    })
  },
  not_agree() {
    wx.navigateBack({})
  },
  agree() {
    this.setData({
      if_show_agree: 0,
      if_agree: 1
    })
  },
  getPrescription() {
    let prescription_info = {};
    prescription_info.name = this.data.real_name;
    prescription_info.mobile = this.data.mobile;
    prescription_info.identityCode = this.data.card_id;
    prescription_info.mobileCheckCode = this.data.mobileCheckCode;
    if (this.data.if_show_agree == 0 && this.data.if_agree == 0) {
      this.setData({
        if_show_agree: 1
      });
      return false;
    }
    if (!prescription_info.name) {
      util.showModal("提示", "请输入姓名！");
      return false;
    }
    if (!prescription_info.identityCode) {
      util.showModal("提示", "请输入身份证号！");
      return false;
    }
    var re = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    if (!(re.test(prescription_info.identityCode)) || prescription_info.identityCode.replace(/^\s+|\s+$/g, '').length != 18) {
      util.showModal("提示", "请输入正确的身份证号");
      return false;
    }
    if (!prescription_info.mobile) {
      util.showModal("提示", "请输入手机号！");
      return false;
    }
    if (!prescription_info.mobileCheckCode) {
      util.showModal("提示", "请输入验证码！");
      return false;
    }
    if (!/^1[3456789]\d{9}$/.test(prescription_info.mobile)) {
      util.showModal("提示", "请输入正确的手机号！");
      return false;
    }
    util.api('/api/wxapp/user/patient/get/info', res => {
      if (res.error == 0) {
        this.getPreInfo(prescription_info)
      } else if (res.error === 402001){
        util.showModal('提示', '验证码错误')
      } else {
        util.showModal('提示', '您暂无本医院就诊记录，请先添加就诊人', () => {
          util.jumpLink('/pages1/patientinfo/patientinfo?prescription_info=' + JSON.stringify(prescription_info) + '&list=' + this.data.list);
        }, true, '取消', '去添加')
      }
    }, {
      userId: util.getCache("user_id"),
      name: prescription_info.name,
      mobile: prescription_info.mobile,
      identityCode: prescription_info.identityCode,
      mobileCheckCode: prescription_info.mobileCheckCode
    })
  },
  addFamily() {
    util.jumpLink('/pages1/patientinfo/patientinfo');
  },
  getVerificationCode() {
    if (this.data.countDown) return false
    if (!this.data.card_id) {
      util.showModal("提示", "请输入身份证号！");
      return false;
    }
    if (!this.data.mobile) {
      util.showModal("提示", "请输入手机号！");
      return false;
    }
    if (!/^1[3456789]\d{9}$/.test(this.data.mobile)) {
      util.showModal("提示", "请输入正确的手机号！");
      return false;
    }
    util.api('/api/wxapp/user/patient/send/sms', res => {
      if (res.error === 0) {
        const TIME_COUNT = 60;
        this.setData({
          countDown: TIME_COUNT
        })
        if (!this.timer) {
          this.timer = setInterval(() => {
            if (this.data.countDown > 0 && this.data.countDown <= TIME_COUNT) {
              this.setData({
                countDown: --this.data.countDown
              })
            } else {
              clearInterval(this.timer)
              this.timer = null
            }
          }, 1000)
        }
      } else {
        util.showModal('提示',res.message)
      }
    }, {
      mobile: this.data.mobile,
      identityCode:this.data.card_id
    })
  },
  getPreInfo (prescription_info) {
    util.api('/api/wxapp/medicine/history/get/external/list', res => {
      if(res.error == 0) {
        util.toast_success('获取成功');
        if(this.data.list == 1){
          util.jumpLink('/pages2/doctorConsultation/doctorConsultation');
        }else{
          util.jumpLink('/pages1/familylist/familylist');
        }
      }
    },{
      patientName: prescription_info.name,
      mobile: prescription_info.mobile,
      identityCode: prescription_info.identityCode
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