var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    handleShowDialog(e){
      let {prescriptionNo} = e.currentTarget.dataset
      util.api('/api/wxapp/prescription/details',res=>{
        if(res.error === 0){
          this.setData({
            showPrescription:true,
            prescriptionData:res.content
          })
        }
      },{
        prescriptionNo
      })
      
    }
  }
});