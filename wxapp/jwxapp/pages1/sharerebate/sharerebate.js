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
    let {goodsPrice,rebateId,rebateRatio,goodsId} = options
    console.log(goodsPrice)
    this.setData({
      goodsPrice,
      rebateId,
      rebateRatio,
      goodsId
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
          })
        })
        this.getRebateInfo()
      }
    },{
      goodsId:this.data.goodsId
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
  getMax () {
    let arr = this.data.productList.map(item=>{return item.prdPrice})
    return Math.max(...arr)
  },
  getRebateInfo(){
    let rebateInfo = parseFloat(this.data.rebateRatio) / 100 * this.getMax()
    this.setData({
      rebateInfo:parseFloat(rebateInfo).toFixed(2)
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
    let rebateConfig = {}
    let date = new Date();
    rebateConfig['rebateTime'] = parseInt(date.getTime()/1000)
    rebateConfig['rebatePrice'] = this.data.productList.reduce((defaultData,item)=>{
      defaultData[item.prdId] = item.prdPrice
      return defaultData
    },{})
    console.log(`/pages/item/item${util.getUrlParams({
      gid:this.data.goodsId,
      inviteId:util.getCache("user_id"),
      rebateConfig:JSON.stringify(rebateConfig)
    })}`)
    return {
      title: '【特价专享】唯一渠道，专享价格，等你来抢！',
      path: `/pages/item/item${util.getUrlParams({
        gid:this.data.goodsId,
        inviteId:util.getCache("user_id"),
        rebateConfig
      })}`
    }
  }
})