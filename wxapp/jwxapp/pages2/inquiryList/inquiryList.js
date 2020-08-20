var util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    targetStatus:'1',
    filterParams:{
      sessionStatus:[1],
      doctorId:util.getCache('doctor_id') || util.getCache('bottom').doctor_id,
      userId:null
    },
    pageParams:{
      currentPage:1,
      pageRows:20
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {type} = options
    if(type === 'wait'){
        this.setData({
          targetStatus:'1',
          'filterParams.sessionStatus':[1],
        })
    }
    if(type === 'my'){
      this.setData({
        targetStatus:'2',
        'filterParams.sessionStatus':[2,4,5,6],
      })
    }
    this.requestSessionList()
  },
  requestSessionList(){
    util.api('/api/wxapp/im/session/page/list',res=>{
      console.log(res)
      if(res.error === 0){
        if(this.data.pageParams.currentPage === 1){
          this.setData({
            dataList:[[...res.content.dataList]]
          })
        } else {
          this.setData({
            ['dataList[' + (parseInt(this.data.pageParams.currentPage) - 1) + ']']:res.content.dataList
          })
        }
        this.setData({
          pageParams:res.content.page
        })
      }
    },{
      ...this.data.filterParams,
      ...this.data.pageParams
    })
  },
  toggleStatus(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      targetStatus:type,
      'pageParams.currentPage':1,
      'filterParams.sessionStatus':type === '1' ? [1] : [2,4,5,6]
    })
    this.requestSessionList()
  },
  accept(e){
    let {parentIndex,sessionId} = e.currentTarget.dataset
    let targetIndex = this.data.dataList[parentIndex].findIndex(item=>item.id === sessionId)
    let target = this.data.dataList[parentIndex][targetIndex]
    let changeOrderStatus = {
      1:2, //待接诊-正在接诊状态,
      4:2  //会话结束-正在接诊状态
    }
    util.api('/api/wxapp/inquiry/order/status/update',res=>{
      if(res.error === 0){
        if(target.sessionStatus === 1){
          let targetList = this.data.dataList[parentIndex]
          targetList.splice(targetList.findIndex(item=>item.id === sessionId),1)
          this.setData({
            [`dataList[${parentIndex}]`]:targetList
          })
        } else {
          this.setData({
            [`dataList[${parentIndex}][${targetIndex}].sessionStatus`]:5
          })
        }
        util.jumpLink(`pages2/doctorChat/doctorChat${util.getUrlParams({
          targetUserInfo:JSON.stringify({...{...target,sessionStatus:target.sessionStatus === 1 ? 2 : 5},parentIndex}),
          source:'inquiryList'
        })}`)
      }
    },{
      orderSn:target.orderSn,
      orderStatus:changeOrderStatus[target.sessionStatus],
      sessionId
    })
  },
  returnOrder(e){
    util.showModal('提示','确认取消此次问诊么？',()=>{
      let {parentIndex,sessionId} = e.currentTarget.dataset
      let targetIndex = this.data.dataList[parentIndex].findIndex(item=>item.id === sessionId)
      let target = this.data.dataList[parentIndex][targetIndex]
      util.api('/api/wxapp/inquiry/order/refund',res=>{
        console.log(res)
        if(res.error === 0){
          let targetList = this.data.dataList[parentIndex]
            targetList.splice(targetList.findIndex(item=>item.id === sessionId),1)
            this.setData({
              [`dataList[${parentIndex}]`]:targetList
            })
        }
      },{
        orderSn:target.orderSn
      })
    },true,'取消','确认')
  },
  viewChat(e){
    let {parentIndex,sessionId} = e.currentTarget.dataset
    let targetIndex = this.data.dataList[parentIndex].findIndex(item=>item.id === sessionId)
    let target = this.data.dataList[parentIndex][targetIndex]
    if(![2,4,5,6].includes(target.sessionStatus)) return
    util.jumpLink(`pages2/doctorChat/doctorChat${util.getUrlParams({
      targetUserInfo:JSON.stringify({...target,parentIndex}),
      source:'inquiryList'
    })}`)
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    this.setData({
      'pageParams.currentPage': 1
    })
    this.requestSessionList()
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    ) {
      return;
    }
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestSessionList();
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})