var util = require('../../utils/util.js')
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    dataList: null,
    pageParams: null,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let {type,goodsId} = options
    this.setData({
      commentTypeList:{
        0:this.$t("page1.goodsComment.allComment"),
        1:this.$t("page1.goodsComment.positive"),
        2:this.$t("page1.goodsComment.moderate"),
        3:this.$t("page1.goodsComment.negative"),
        4:this.$t("page1.goodsComment.hasPic")
      },
      type,
      goodsId,
      chooseTarget:type
    })
    this.requestCommentList()
  },
  requestCommentList(){
    let currentPage = this.data.pageParams
      ? this.data.pageParams.currentPage
      : 1;
    util.api('/api/wxapp/comment/goods',res=>{
      if(res.error === 0){
        this.setData({
          ['dataList[' + (parseInt(currentPage) - 1) + ']']:this.resetCommentList(res.content.comment.dataList),
          commentNum:this.getCommentNum(res.content.number),
          pageParams: res.content.comment.page,
        })
        this.getRating(this.data.commentNum)
      }
    },{
      currentPage,
      pageRows: 20,
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
    let imageArray = null
    try {
      imageArray = JSON.parse(commentImage)
    } catch (error) {
      imageArray = commentImage
    }
    imageArray = imageArray.map(item=>{
      return this.data.imageUrl + item
    })
    return imageArray
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
      chooseTarget:type,
      dataList:null,
      'pageParams.currentPage':1
    })
    this.requestCommentList()
  },
  getRating(allNum){
    let all = allNum.find(item=>item.type === 0).num
    let positive = allNum.find(item=>item.type === 1).num
    let moderate = allNum.find(item=>item.type === 2).num
    let negative = allNum.find(item=>item.type === 3).num
    this.setData({
      rating:{
        positive:this.getPercent(positive,all),
        moderate:this.getPercent(moderate,all),
        negative:this.getPercent(negative,all),
      }
    })
  },
  getPercent(curNum,totalNum){
    return (Math.round(curNum / totalNum * 10000) / 100.00)
  },
  previewImage(e){
    let {id,index} = e.currentTarget.dataset
    let target = this.data.dataList.flat().find(item=>item.id === id)
    wx.previewImage({
      urls:target.commentImageList,
      current:target.commentImageList[index]
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
    if (
      this.data.pageParams &&
      this.data.pageParams.currentPage === this.data.pageParams.lastPage
    )
      return;
    this.setData({
      'pageParams.currentPage': this.data.pageParams.currentPage + 1
    });
    this.requestList();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})