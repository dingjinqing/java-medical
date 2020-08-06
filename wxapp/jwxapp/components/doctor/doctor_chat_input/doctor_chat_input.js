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
    showSendButton:false
  },

  lifetimes: {
    ready() {
      wx.onKeyboardHeightChange(res => {
        console.log(res.height)

           this.setData({
            bottom:res.height
           })

      })
    },

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
      this.triggerEvent('scrollBottom', {})
    },
    showMoreActions() {
      this.setData({
        moreActions: true,
        bottom:0
      })
    },
    // keybordDown(){
    //   this.setData({
    //     bottom:0
    //   })
    // },
    
    getFocus(e) {
      let that = this;
      that.hideMoreActions()
      this.setData({
        showSendButton:true
      })
      // that.triggerEvent('scrollBottom', {})
    },
    getBlur(){
      this.setData({
        showSendButton:false
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
      this.triggerEvent('scrollBottom', {})
    }
  }
})