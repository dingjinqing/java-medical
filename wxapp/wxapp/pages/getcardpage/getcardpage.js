var util = require('../../utils/util.js');
var app = getApp();
var imageUrl = app.globalData.imageUrl;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: imageUrl,
    cardNum:'',
    cardPwd:'',
    cardCode:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      type: options.type ? options.type : 1
    })
    util.api("/api/wxapp/card/code/draw",(res)=>{
      if(res.error == 0){
        this.setData({
          shop_logo:res.content
        })
      }
    },{type:3})
  },
  getCardPwd(e){
    let code = e.detail.value.replace(/\s+/g, '');
    if(this.data.type == 1){
      this.setData({
        cardPwd: code
      })
    } else {
      this.setData({
        cardCode:code
      })
    }
  },
  getCardNum(e){
    let num = e.detail.value;
    this.setData({
      cardNum:num
    })
  },
  getCard(){
    console.log(this.data.type, this.data.cardNum, this.data.cardPwd, this.data.cardCode)
    util.api("/api/wxapp/card/code/draw", function (res) {
      if(res.error == 0){
        util.toast_success('领取成功',function(){
          setTimeout(function(){
            if (res.content.activation == 1) {
              util.navigateTo({
                url: '/pages/memberinfo/memberinfo?act=1&card_no=' + res.content.card_no + '&examine=' + res.content.examine
              })
            } else {
              util.navigateTo({
                url: '../usercardinfo/usercardinfo?card_list=1&card_no=' + res.content.card_no,
              })
            }
          },2000)
        })
      } else {
        util.showModal('提示', res.message);
      }
    }, { type: this.data.type, cardNo: this.data.cardNum, cardPwd: this.data.cardPwd, code: this.data.cardCode});
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