var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    show:{
      type:Boolean,
      value:false,
      observer(newVal){
        if(newVal === true){
          setTimeout(()=>{
            this.bindClose();
            this.data.closeEvent && this.data.closeEvent()
          }, this.data.duration)
        }
      }
    },
    icon:{
      type:String,
      value:'success'
    },
    title:{
      type:String,
      value:''
    },
    content:{
      type:String,
      value:''
    },
    duration:{
      type:Number,
      value:2000
    }
  },
  methods: {
    // toastComplete() {
    //   this.triggerEvent('callBackName');
    // },
    showToast({
      icon = 'success',
      title = '',
      content = '',
      duration = 2000,
      closeEvent = null
    }){
      if(icon){
        this.setData({
          icon:icon
        })
      }
      if(title){
        this.setData({
          title:title
        })
      }
      if(content){
        this.setData({
          content:content
        })
      }
      if(duration){
        this.setData({
          duration:duration
        })
      }
      this.data.closeEvent = closeEvent
      this.setData({
        show:true
      })
    }
  },
});