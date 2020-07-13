var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")

global.wxComponent({
  mixins: [base],
  methods: {
    updatePage(){
      util.jumpLink('pages1/getprescription/getprescription')
    },
    handleShowDialog(e){
      let {caseHistoryId:id} = e.currentTarget.dataset
      util.api('/api/wxapp/medicine/history/detail',res=>{
        if(res.error === 0){
          this.setData({
            showCaseHistory:true,
            caseHistoryData:res.content
          })
        }
      },{
        id
      })
      
    }
  }
});