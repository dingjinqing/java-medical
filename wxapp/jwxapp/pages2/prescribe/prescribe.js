// pages2/prescribe/prescribe.js
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    theDateToday:getToday(),
    prescriptionInfo:{
      diagnose:{
        info:'',
        canClear:false
      }
    },
    goodsList:[
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      },
      {	
        goodsId: 397,	
          goodsName: "华为 HUAWEI nova 5 Pro 前置3200万人像超级夜景4800万AI四摄麒麟980芯片8GB+128GB绮",	
          goodsImg: "http://jmptestimg.weipubao.cn/upload/819021/image/20200527/xPKbEWZobOdlLxJAcydo.jpeg",	
          marketPrice: null,	
          shopPrice: 2399,	
          productId: 1797,	
          prdPrice: 2399,	
          prdNumber: 5,	
          prdDesc: "颜色:绮境森林;版本:8GB+128GB;选择版本:标准版",	
          prdImg: "",	
          goodsNumber: 2,	
          cartNumber: 2,	
          groupId: 1	
      }
    ]
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  clearInput(){
    this.setData({
      'prescriptionInfo.diagnose.canClear':false,
      'prescriptionInfo.diagnose.info' : ''
    })
  },
  getDiagnose({detail:{value}}){
    let canClear = false
    if(value) canClear = true
    this.setData({
      'prescriptionInfo.diagnose.canClear':canClear,
      'prescriptionInfo.diagnose.info' : value
    })
  },
  addGoods(){
    this.setData({
      showGoodsListDialog:true
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

function getToday(){
  let now = new Date()
  return `${now.getFullYear()}-${String(now.getMonth()+1).padStart(2,0)}-${now.getDate()}`
}