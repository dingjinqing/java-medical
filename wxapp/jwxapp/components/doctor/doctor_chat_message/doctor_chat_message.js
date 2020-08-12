import util from '../../../utils/util'
var app = new getApp();
var imageUrl = app.globalData.imageUrl;
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    chatContent: Object,
    systemInfo: String,
    systemImg: Boolean,
    idx: Number,
    status: {
      type: Number,
      observer(newVal) {
        console.log(newVal)
      }
    },
    isDoctor: {
      type: Boolean,
      value: false
    }
  },
  options: {
    multipleSlots: true
  },
  lifetimes: {
    ready() {
      let that = this;
      that.initAvatar()
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    userAvatar: util.getCache('avatarUrl')
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onLoad(op) {
      let that = this

    },
    initAvatar() {
      let avatar = ''
      if (this.data.isDoctor) {
        if (this.data.chatContent.position === 0) avatar = this.data.imageUrl + 'image/wxapp/user_default_icon.png'
        if (this.data.chatContent.position === 1) avatar = this.data.imageUrl + 'image/wxapp/doctor_default_icon.png'
      } else {
        if (this.data.chatContent.position === 0) avatar = this.data.imageUrl + 'image/wxapp/doctor_default_icon.png'
        if (this.data.chatContent.position === 1) avatar = this.data.imageUrl + 'image/wxapp/user_default_icon.png'
      }
      this.setData({
        avatar
      })
    }
  },
})