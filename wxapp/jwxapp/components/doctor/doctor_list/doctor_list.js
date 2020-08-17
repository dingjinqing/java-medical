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

    toDetail(e){
      let that = this;
      let doctor_id = e.currentTarget.dataset.doctor_id;
      util.navigateTo({
        url: "pages2/doctorIntroduce/doctorIntroduce?doctorId=" + doctor_id
      })
    },
   
  },

})