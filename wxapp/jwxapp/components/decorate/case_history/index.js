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

    },
    viewCaseHistoryList(){
      util.handleBuriedPoint('home_page_syn_my_medical', 'pages/index/index', [{
        key: '点击'
      }])
      util.jumpLink('pages1/medicalrecordlist/medicalrecordlist')
    }
  }
});
