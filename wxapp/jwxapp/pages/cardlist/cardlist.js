let util = require('../../utils/util.js');
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    list:[
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'},
      {title:'猜猜这是谁建的会员卡'}
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },
  // 触摸开始
  handleTouchStart (e) {
    this.startX = e.touches[0].clientX;
    let idx = e.currentTarget.dataset.index;
    let target = `list[${idx}].startRight`
    this.setData({
      [target]: this.data.list[idx].x ? this.data.list[idx].x : 0
    })
  },
  // 触摸移动
  handleTouchMove (e) {
    var touch = e.touches[0];
    let idx = e.currentTarget.dataset.index;
    let target = `list[${idx}].x`
    let endX = touch.clientX;
    let right = 0;
    if ((endX - this.startX) < 0) {
      let startRight = this.data.list[idx].startRight;
      var change = this.startX - endX;
          startRight += change;
          if (startRight > 60) startRight = 60;
          right = startRight
    } else {
      var startRight = this.data.list[idx].startRight;
      var change = this.startX - endX;
          startRight += change;
          if (startRight < 0) startRight = 0;
          right  = startRight;
    }
    this.setData({
      [target]: right
    })
  },
  // 触摸结束
  handleTouchEnd(e){
    let idx = e.currentTarget.dataset.index;
    let target = `list[${idx}].x`
    let right = 0;
    this.data.list[idx].x  > 60 / 2 ? right = 60 : right = 0
    this.setData({
      [target]: right
    })
  },
  // 删除会员卡
  delCard(e){
    let card_no = e.currentTarget.dataset.card_no;
    util.showModal('', '您确定要删除该会员卡？', function () {
      var animate = '';
      util.api('/api/wxapp/card/del', function (res) {
        if (res.error === 0) {
          
        }
      }, { card_no: card_no })
    }, true, '取消', '确定')
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