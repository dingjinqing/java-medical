var util = require("../../../utils/util.js");
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
    inputMessage:'',
    moreActions:false
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
    },
    showMoreActions(){
      this.setData({
        moreActions:true
      })
    },
    getFocus(e){
      this.hideMoreActions()
    },
    hideMoreActions(){
      this.setData({
        moreActions:false
      })
    },
    sendImage(){
      util.uploadImage(1, (res) => {
        let data = JSON.parse(res.data);
        if (data.error == 0) {
          this.triggerEvent('sendImage',{image:data.content.imgUrl})
          this.hideMoreActions()
        }
      });
    }
  }
})
