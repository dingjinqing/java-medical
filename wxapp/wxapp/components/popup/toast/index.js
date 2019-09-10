var util = require("../../../utils/util.js");
var base = require("../base/base.js")
global.wxComponent({
  mixins: [base],
  properties: {
    show:{
      type:Boolean,
      value:false,
      observer:function(newVal,oldVal){
        let _this = this;
        if(newVal == true){
          console.log(_this.data)
          setTimeout(()=>{
            _this.bindClose();
            _this.toastComplete();
          }, _this.data.toastInfo.duration)
        }
      }
    },
    toastInfo:{
      type:Object,
      value: {
        icon : "success",
        title : "",
        duration : 2000,
        content : ""
      }
    },
  },
  methods: {
    toastComplete() {
      this.triggerEvent('callBackName');
    },
  },
});