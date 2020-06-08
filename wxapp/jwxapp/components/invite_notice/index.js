
const util = require('../../utils/util.js');
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    inviteId:{
      type:Number,
      value:null,
      observer(newVal){
        if(newVal) this.initData()
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    initData(){
      util.api('/api/wxapp/distribution/rebate/user/share',res=>{
        if(res.error === 0 && res.content) {
          this.setData({
            ...res.content,
            showInviteNotice:true
          })
        }
      },{
        userId:util.getCache('user_id'),
        inviteId:this.data.inviteId
      })
    },
    closeInviteNotice(){
      this.setData({
        showInviteNotice:false
      })
    }
  }
})
