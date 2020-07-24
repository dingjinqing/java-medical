// components/doctor/doctor_chat_input/doctor_chat_input.js
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    inputMessage:''
  },

  /**
   * 组件的方法列表
   */
  methods: {
    messageInput(e){
      this.setData({
        inputMessage:e.detail.value
      })
    },
    sendMessage(){
      if(!this.data.inputMessage) return
      this.triggerEvent('getInputMessage',this.data.inputMessage)
      this.setData({
        inputMessage:''
      })
    }
  }
})
