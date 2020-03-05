const util = require("../../utils/util.js");
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl(""),
    chooseType:null,
    isAnonymous:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      options
    })
    this.requestOrder()
  },
  requestOrder(){
    let {orderId,orderSn} = this.data.options
    util.api('api/wxapp/order/pay/instead',res=>{
      if(res.error === 0 && res.content){
        this.setData({
          pageInfo:res.content,
          userPayNum:res.content.moneyPaid,
          manageMsg:res.content.message,
          nickName:util.getCache('nickName'),
          payList:this.setPayList(res.content)
        })
      }
    },{
      orderId,
      orderSn,
      action:12
    })
  },
  setPayList(pageInfo){
    let payList = []
    if(pageInfo.isSelf === 0 && pageInfo.threeStages && pageInfo.threeStages.length > 0){
      pageInfo.threeStages.forEach((item,index)=>{
        payList.push({key:index,name:item[1],value:item[0]})
      })
    }
    return payList
  },
  changeHide(e){
    this.setData({
      isAnonymous:!this.data.isAnonymous
    })
  },
  changeMsg(e){
    this.setData({
      manageMsg:e.detail.value
    })
  },
  changeNickName(e){
    this.setData({
      nickName:e.detail.value
    })
  },
  changePayNum(e){
    this.setData({
      userPayNum:e.detail.value
    })
  },
  radioChange(e){
    let value = e.detail.value
    let payList = this.data.payList.map(item=>{
      item.checked = false
      if(value == item.key) item.checked = true
      return item
    })
    this.setData({
      payList,
      userPayNum:payList.find(item => {return item.key == value}).value
    })
  },
  resetRadio(){
    let payList = this.data.payList.map(item=>{
      item.checked = false
      return item
    })
    this.setData({
      payList
    })
  },
  checkout(){
    let userPayNum = parseFloat(this.data.userPayNum)
    console.log(userPayNum)
    if(!userPayNum || isNaN(userPayNum)){
      util.showModal('提示', '请输入正确的金额');
      return
    }
    if(userPayNum > this.data.pageInfo.waitPayMoney){
      util.showModal('提示', '输入金额超过代付金额请确认');
      return
    }
    let {orderId,orderSn} = this.data.options
    let params = {
      orderId,
      orderSn,
      action:12,
      moneyPaid:userPayNum,
      username:this.data.isAnonymous ? null : this.data.nickName,
      message:this.data.manageMsg
    }
    util.api('api/wxapp/order/pay/instead/submit',res=>{
      if( res.error === 0 && res.content && res.content.webPayVo){
        wx.requestPayment({
          timeStamp: res.content.webPayVo.timeStamp,
          nonceStr: res.content.webPayVo.nonceStr,
          package: res.content.webPayVo.package,
          signType: 'MD5',
          paySign: res.content.webPayVo.paySign,
          success: res => {
            util.toast_success('支付成功')
            util.jumpLink(
              `pages1/payment/payment${this.getUrlParams({
                orderSn,
                useInfo: JSON.stringify({ moneyPaid:params.moneyPaid })
              })}`,
              'redirectTo'
            )
          },
          fail: res => {
            console.log(res)
            util.jumpLink(`/pages/orderinfo/orderinfo?orderSn=${res.orderSn}`, 'redirectTo')
          },
          complete: res => {}
        })
      }
    },{...params})
  },
  getUrlParams(obj) {
    return Object.keys(obj).reduce((UrlStr, item, index) => {
      if (index !== 0) UrlStr += `&`
      return (UrlStr += `${item}=${obj[item]}`)
    }, '?')
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

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})