// components/usercenter/useraddress/useraddress.js
import util from '../../../utils/util'
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    doctorList: {
      type: Object,
      value: '',
      observer: function (newVal) {
        console.log(newVal)
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  ready() {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoad() {
      console.log(this.data.doctorList)
    },
    toChat(e) {
      let doctor_id = e.currentTarget.dataset.doctor_id;
      let depar_id = e.currentTarget.dataset.depar_id;
      let price = e.currentTarget.dataset.price;
      let that = this;
      util.api('/api/wxapp/user/patient/get/default', function (res) {
        console.log(res)
        if (res.error == 0 && res.content.id) {
          let patientId = res.content.id;
          that.requestOrderStatus(depar_id, patientId, doctor_id, price)
        } else {
          util.navigateTo({
            url: "pages1/getprescription/getprescription?list=1"
          })
        }

      }, {})
    },
    requestOrderStatus(depar_id, patientId, doctor_id, price) {
      let that = this;
      util.api('/api/wxapp/inquiry/order/undone/get', function (res) {
        console.log(res)
        if (res.error == 0) {

        } else {
          util.navigateTo({
            url: "/pages2/doctorPatientMessage/doctorPatientMessage?doctor_id=" + doctor_id + "&depar_id=" + depar_id + "&price=" + price
          })
        }
      }, {
        userId: util.getCache('user_id'),
        departmentId: depar_id,
        patientId: patientId,
        doctorId: doctor_id
      })
    },
  },

})