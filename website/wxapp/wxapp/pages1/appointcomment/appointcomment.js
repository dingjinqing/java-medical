// pages/appointcomment/appointcomment.js
var util = require('../../utils/util.js');
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var service_id;
var shop_id;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    all_com: 'border-bottom: 2px solid #ff6666; color: #ff6666;',
    good_com: '',
    worse_com: '',
    worst_com: '',
    good_per: 0,
    worse_per: 0,
    worst_per: 0,
    all_num: 0,
    good_num: 0,
    worse_num: 0,
    worst_num: 0,
    imageUrl: app.globalData.imageUrl,
    service_comment_info: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
      service_id = options.service_id;
      shop_id = options.shop_id;
      var that = this;
      util.api('/api/wxapp/service/comment',function(res){
        that.data.all_com = "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor+";";
          var ser_comment = res.content;
          var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
          var re = new RegExp(strRegex);
          var com_reg = /^(.).+(.)$/g;
          for (var i in ser_comment.comment) {
            if (!re.test(ser_comment.comment[i].user_avatar)) {
              ser_comment.comment[i].user_avatar = imageUrl + ser_comment.comment[i].user_avatar;
            }
            ser_comment.comment[i].comm_img = JSON.parse(ser_comment.comment[i].comm_img);
            ser_comment.comment[i].name =ser_comment.comment[i].username.replace(com_reg, "$1**$2");
          }
          var total_comm_num = ser_comment.number[0].num + ser_comment.number[1].num + ser_comment.number[2].num;
          that.setData({
            service_comment_lv: ser_comment.number,
            service_comment_info: ser_comment.comment,
            total_comm_num: total_comm_num,
            all_com: that.data.all_com
          })
      },{shop_id:shop_id,service_id:service_id})
  },

  // 点击图片放大看
  clickComment: function (e) {
    var nowImgUrl = e.target.dataset.src;
    var arr = [];
    var commimgUrls = e.target.dataset.all;
    for (var i in commimgUrls) {
      arr.push(commimgUrls[i]); //属性
    }
    wx.previewImage({
      current: nowImgUrl, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },
  //切换tab
  change_com: function (e) {
    var that = this
    var ctype = e.currentTarget.dataset.ctype;
    util.api('/api/wxapp/service/comment?service_id=' + service_id, function (res) {
      var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
      var re = new RegExp(strRegex);
      var com_reg = /^(.).+(.)$/g;
      for (var i in res.content.comment) {
        if (!re.test(res.content.comment[i].user_avatar)) {
          res.content.comment[i].user_avatar = imageUrl + res.content.comment[i].user_avatar;
        }
        res.content.comment[i].comm_img = JSON.parse(res.content.comment[i].comm_img);
        res.content.comment[i].name = res.content.comment[i].username.replace(com_reg, "$1**$2");
      }
      that.setData({
        service_comment_info: res.content.comment
      });
      if (ctype == 'allpingjia') {
        that.setData({
          all_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          good_com: '',
          worse_com: '',
          worst_com: ''
        });
      }
      else if (ctype == 'haoping') {
        that.setData({
          good_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          all_com: '',
          worse_com: '',
          worst_com: ''
        })
      }
      else if (ctype == 'zhongping') {
        that.setData({
          worse_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          all_com: '',
          good_com: '',
          worst_com: ''
        })
      }
      else if (ctype == 'chaping') {
        that.setData({
          worst_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          all_com: '',
          good_com: '',
          worse_com: ''
        })
      }
    }, { pingjia: ctype });
  },
 

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: '/pages/appointcomment/appointcomment?service_id=' + service_id + "&shop_id=" + shop_id + "&invite_id=" + util.getCache('user_id'),
    }
  },
})