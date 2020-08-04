var util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    source: {
      type: String,
      value: 'patient' // patient | doctor
    },
    hasInput: {
      type: Boolean,
      value: true
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    inputMessage: '',
    moreActions: false,
    bottom: 0,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    messageInput(e) {
      this.setData({
        inputMessage: e.detail.value
      })
    },
    sendMessage() {
      if (!this.data.inputMessage) return
      this.triggerEvent('getInputMessage', this.data.inputMessage)
      this.setData({
        inputMessage: '',
      })
    },
    showMoreActions() {
      this.setData({
        moreActions: true,
        bottom:0
      })
    },
    keybordDown(){
      this.setData({
        bottom:0
      })
    },
    getFocus(e) {
      let that = this;
      that.hideMoreActions()
      that.triggerEvent('scrollBottom', {})
    },
    keybord(e) {
      let that = this;
      let {
        height
      } = e.detail
      console.log(height)
      that.setData({
        bottom: height
      })
    },
    hideMoreActions() {
      this.setData({
        moreActions: false
      })
    },
    sendImage() {
      util.uploadImage(1, (res) => {
        let data = JSON.parse(res.data);
        if (data.error == 0) {
          console.log(data)
          this.triggerEvent('sendImage', {
            image: data.content.imgUrl,
            imgWidth: data.content.imgWidth,
            imgHeight: data.content.imgHeight
          })
          this.hideMoreActions()
        }
      });
    }
  }
})