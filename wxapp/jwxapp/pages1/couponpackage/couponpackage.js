const util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: util.getImageUrl("")
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({options})
    this.requestData()
  },
  requestData(){
    let {packId} = this.data.options
    util.api('/api/wxapp/coupon/pack',res=>{
      if(res.error === 0 && res.content){
        this.resetPackDoc(res.content)
        this.setData({
          pageInfo:res.content,
          buttonData:this.getButtonData(res.content)
        })
      }
    },{
      packId
    })
  },
  resetPackDoc({packList,buyCount}){
    packList.forEach(item=>{
      let packDoc = '';
      if(item.immediatelyGrantAmount){
        packDoc = `领取后立即发放${item.immediatelyGrantAmount}张`
      }
      if(item.timingEvery && item.timingAmount){
        let timingUnit = item.timingUnit === 1 ? '周' : (item.timingUnit === 2 ? '月' : '天');
        packDoc = packDoc ? packDoc+'，' : '';
        packDoc += `领取后每${item.timingEvery}${timingUnit}发放${item.timingAmount}张`
      }
      item.packDoc = packDoc
      let alGetCou = parseFloat(item.totalAmount) * buyCount;
      if(buyCount > 0 && alGetCou === item.grantCount){
        item.sendIconText='已发放'
      } else if (buyCount > 0 && alGetCou !== item.grantCount && item.grantCount != 0){
        item.sendIconText='部分发放'
      } else if (buyCount > 0 && item.grantCount === 0){
        item.sendIconText='待发放'
      }
    })
  },
  getButtonData({buyCount,packInfo}){
    if(buyCount === 0){
      let style = 'btn-buy'
      switch (packInfo.accessMode) {
        case 0:
          return {style,name:`${packInfo.accessCost}元购买`}
        case 1:
          return {style,name:`${packInfo.accessCost}积分购买`}
        case 2:
          return {style,name:`立即领取`}
      }
    }
    if(buyCount > 0 && (buyCount < packInfo.limitGetTimes || packInfo.limitGetTimes === 0)){
      let style = 'buy_again'
      switch (packInfo.accessMode) {
        case 0:
          return {style,name:`${packInfo.accessCost}元再次购买`}
        case 1:
          return {style,name:`${packInfo.accessCost}积分再次购买`}
        case 2:
          return {style,name:`再次领取`}
      }
    }
    return null
  },
  viewCouponList(){
    util.jumpLink("/pages/coupon/coupon",'navigateTo')
  },
  getCouponPackage(){
    let {packId} = this.data.options
    util.api('/api/wxapp/coupon/pack/tobuy',res=>{
      if(res.error === 0 && res.content){
        if(res.content.state === 0) {
          util.jumpLink(`/pages1/virtualCheckout/virtualCheckout?packId=${packId}`,'navigateTo')
        }
        if(res.content.state === 6) {
          util.toast_success('领取成功',()=>{
            this.requestData();
          })
        }
        if([1,2,3,4,5].includes(res.content.state)){
          let errorMessage = {
            1:`活动已结束`,
            2:`活动未开始`,
            3:`领取礼包达到上限`,
            4:`领取礼包达到上限`,
            5:`对不起，您的积分不足`
          }
          util.showModal("提示", errorMessage[res.content.state])
        }

      } else {
        util.showModal("提示", res.message);
      }
    },{
      packId
    })
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