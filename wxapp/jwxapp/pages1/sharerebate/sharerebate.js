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
    let {goodsPrice,rebateId} = options
    console.log(goodsPrice)
    this.setData({
      goodsPrice,
      rebateId
    })
    let productList = {
        "6105": {
        "id": 256,
        "goods_id": 1180,
        "product_id": 6105,
        "advise_price": "100.00",
        "min_price": "90.00",
        "max_price": "200.00",
        "add_time": "2020-03-25 14:25:46",
        "spec_val_id": "5700",
        "spec_desc": "大小:小"
        },
        "6106": {
        "id": 257,
        "goods_id": 1180,
        "product_id": 6106,
        "advise_price": "150.00",
        "min_price": "140.00",
        "max_price": "200.00",
        "add_time": "2020-03-25 14:25:46",
        "spec_val_id": "5701",
        "spec_desc": "大小:大"
        }
      }
      this.setData({
        productList:Object.values(productList).map(item=>{
           item.prdPrice = item.advise_price
           return item
        })
      })
  },
  setPrdPrice(e){
    let {prdId} = e.currentTarget.dataset
    let {value:price} = e.detail 
    let targetIndex = this.data.productList.findIndex(item=>{return item.product_id === prdId})
    console.log(targetIndex)
    let target = this.data.productList[targetIndex]
    console.log(price,target.max_price)
    if(Number(price) > Number(target.max_price)){
      util.showModal('提示', '设置金额不能大于最高售价');
    } else if (Number(price) < Number(target.min_price)){
      util.showModal('提示', '设置金额不能小于最低售价');
    } else {
      this.setData({
        [`productList[${targetIndex}].prdPrice`]: Number(price).toFixed(2)
      })
    }
    this.setData({
      [`productList[${targetIndex}].prdPrice`]:Number(this.data.productList[targetIndex].prdPrice).toFixed(2)
    })
  },
  resetPrdPrice(e){
    let {prdId} = e.currentTarget.dataset
    let targetIndex = this.data.productList.findIndex(item=>{return item.product_id === prdId})
    this.setData({
      [`productList[${targetIndex}].prdPrice`]:this.data.productList[targetIndex].advise_price
    })
  },
  getMax () {
    let arr = this.data.productList.map(item=>{item.prdPrice})
    return Math.max(...arr)
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