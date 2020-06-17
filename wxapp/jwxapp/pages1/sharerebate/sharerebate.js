const util = require("../../utils/util.js");
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {goodsPrice,rebateId,rebateRatio,goodsId,linePrice = null} = options
    console.log(goodsPrice)
    this.setData({
      goodsPrice,
      rebateId,
      rebateRatio:this.getMax(Object.values(rebateRatio).filter(item => item > 0)),
      goodsId,
      linePrice
    })
    this.initShareRebateData()
  },
  initShareRebateData(){
    util.api('/api/wxapp/distribution/rebate/goods/config',res=>{
      if(res.error === 0 && res.content){
        this.setData({
          goodsInfo:res.content.goods,
          productList:res.content.rebatePrice.map(item=>{
            item.prdPrice = item.advisePrice
            return item
          }),
        })
        this.getRebateInfo()
        this.getShareImage()
        this.getCouponData(res.content.couponList)
      }
    },{
      goodsId:this.data.goodsId,
      rebateId:this.data.rebateId
    })
  },
  setPrdPrice(e){
    let {prdId} = e.currentTarget.dataset
    let {value:price} = e.detail 
    let targetIndex = this.data.productList.findIndex(item=>{return item.prdId === prdId})
    let target = this.data.productList[targetIndex]
    if(Number(price) > Number(target.maxPrice)){
      util.showModal('提示', '设置金额不能大于最高售价');
    } else if (Number(price) < Number(target.minPrice)){
      util.showModal('提示', '设置金额不能小于最低售价');
    } else {
      this.setData({
        [`productList[${targetIndex}].prdPrice`]: Number(price).toFixed(2)
      })
    }
    this.setData({
      [`productList[${targetIndex}].prdPrice`]:Number(this.data.productList[targetIndex].prdPrice).toFixed(2)
    })
    this.getRebateInfo()
  },
  resetPrdPrice(e){
    let {prdId} = e.currentTarget.dataset
    let targetIndex = this.data.productList.findIndex(item=>{return item.prdId === prdId})
    this.setData({
      [`productList[${targetIndex}].prdPrice`]:this.data.productList[targetIndex].advisePrice
    })
    this.getRebateInfo()
  },
  getMax (arr) {
    return Math.max(...arr)
  },
  getRebateInfo(){
    let rebateInfo = parseFloat(this.data.rebateRatio) / 100 * this.getMax(this.data.productList.map(item=>{return item.prdPrice}))
    this.setData({
      rebateInfo:parseFloat(rebateInfo).toFixed(2)
    })
  },
  getShareImage(){
    util.api('/api/wxapp/rebate/share/info',res=>{
      if(res.error === 0){
        this.setData({
          shareData:{
            title:res.content.shareDoc,
            imageUrl:res.content.imgUrl
          }
        })
      }
      console.log(res)
    },{
      targetId:this.data.goodsId,
      linePrice:this.data.linePrice
    })
  },
  getCouponData(couponList){
    if(!couponList || couponList.length === 0) return
    this.setData({
      couponData:this.resetCouponData(couponList),
      giveCoupon:true
    })
  },
  resetCouponData(couponList){
    return couponList.map(item=>{
      item.canUseTime = this.getTimeLimit(item)
      return item
    })
  },
  getTimeLimit(couponItem){
    if(couponItem.validityType === 0){
      return `${couponItem.startTime} —— ${couponItem.endTime}`
    } else {
      return `领取后${couponItem.validity > 0 ? couponItem.validity + '天' : ''}${couponItem.validityHour > 0 ? couponItem.validityHour + '小时' : ''}${couponItem.validityMinute > 0 ? couponItem.validityMinute + '分钟' : ''}内有效`
    }
  },
  toogleCouponGive(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      giveCoupon:!!Number(type)
    })
  },
  chooseCouponData(e){
    let {id} = e.currentTarget.dataset
    let targetIndex = this.data.couponData.findIndex(item=>item.id === id)
    this.setData({
      [`couponData[${targetIndex}].checked`]: !this.data.couponData[targetIndex].checked
    })
    this.setData({
      checkedCouponIds:this.data.couponData.filter(item=>item.checked === true).map(item=>item.id)
    })
    console.log(this.data.checkedCouponIds)
  },
  openShareDialog(){
    let rebateConfig = {}
    let date = new Date();
    if(this.data.giveCoupon && this.data.checkedCouponIds && this.data.checkedCouponIds.length > 0) rebateConfig['couponIds'] = this.data.checkedCouponIds.join(',')
    rebateConfig['rebateTime'] = parseInt(date.getTime()/1000)
    rebateConfig['rebatePrice'] = this.data.productList.reduce((defaultData,item)=>{
      defaultData[item.prdId] = item.prdPrice
      return defaultData
    },{})
    
    this.setData({
      customShareData:{
        gid:this.data.goodsId,
        inviteId:util.getCache("user_id"),
        realPrice:this.getMin(Object.values(rebateConfig.rebatePrice)),
        rebateConfig:JSON.stringify(rebateConfig)
      },
      showShareDialog:true
    })
  },
  getMin (arr) {
    return Math.min(...arr)
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
    console.log(`/pages/item/item${util.getUrlParams({
      ...this.data.customShareData
    })}`)
    return {
      path: `/pages/item/item${util.getUrlParams({
        ...this.data.customShareData
      })}`,
      ...this.data.shareData
    }
  }
})