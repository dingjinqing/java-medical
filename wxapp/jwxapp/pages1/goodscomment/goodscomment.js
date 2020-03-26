var util = require('../../utils/util.js')
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    commentTypeList:{
      0:"全部评价",
      1:"好评",
      2:"中评",
      3:"差评",
      4:"有图"
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {type,goodsId} = options
    this.setData({
      type,
      goodsId,
      chooseTarget:type
    })
    this.requestCommentList()
  },
  requestCommentList(){
    util.api('/api/wxapp/comment/goods',res=>{
      if(res.error === 0){
        this.setData({
          dataList:this.resetCommentList(res.content.comment),
          commentNum:this.getCommentNum(res.content.number)
        })
        console.log(this.data.dataList)
        console.log(this.data.commentNum)
      }
    },{
      type:this.data.type,
      goodsId:this.data.goodsId
    })
  },
  resetCommentList(dataList){
    return dataList.map(item=>{
      return {
        ...item,
        commentImageList:this.setCommentImage(item.commImg),
        commentStarNum:this.getStarList(item.commstar)
      }
    })
  },
  setCommentImage(commentImage){
    if(!commentImage) return false
    if(Array.isArray(commentImage)) return commentImage
    return JSON.parse(commentImage)
  },
  getStarList(commstar){
    let starList = []
    for(let i = 0; i < commstar;i++){
      starList.push(i)
    }
    return starList
  },
  getCommentNum(num){
    let AllNum = num.reduce((defaultNum,item)=>{
      if(item.type !== 4) defaultNum+=item.num
      return defaultNum
    },0)
    return [
      {type:0,num:AllNum},
      ...num
    ]   
  },
  toggleType(e){
    let {type} = e.currentTarget.dataset
    this.setData({
      type,
      chooseTarget:type
    })
    this.requestCommentList()
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