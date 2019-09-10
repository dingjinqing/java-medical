// pages/comment/comment.js
var app = getApp()
var util = require('../../utils/util.js');
var s = util.getImageUrl('');
var imageUrl = app.globalData.imageUrl;
var nroot = s.substring(0,(s.length - 1));
var Url = app.globalData.baseUrl;
var order_sn;
var src_down = imageUrl + 'image/wxapp/down_normal.png';
var src_up = imageUrl + 'image/wxapp/up_normal.png';
global.wxPage({
  /**
   * 页面的初始数据
   */
  data: {
    navbar: [
      '全部商品',
      '待评价',
      '已评价'
    ],
    imageUrl:app.globalData.imageUrl,
    img_len:0,
    image:false,//是否显示晒单的图片
    islogin:true,
    currentTab: 0,
    com_flag: false,//是否有订单
    root: util.getImageUrl(''),
    no_root: nroot,
    info:{
      anonymousflag:0,
      comm_img:[],
      commstar:0,
      comm_note:'',
    },//提交的信息
    flag:false,//是否匿名评价
    pass_commtag:[],//传递的标签
    star:[
      {
        show:true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      },
      {
        show: true
      }
    ]
  },
  navbarTap: function (e) {
    var that = this;
    this.setData({
      currentTab: e.currentTarget.dataset.idx
    })
    var flag = parseInt(e.currentTarget.dataset.idx);
    var comment_flag = 0;
    if(flag == 0){
      comment_flag = 10;
    }else if(flag == 1){
      comment_flag = 0;
    }else if(flag == 2){
      comment_flag = 1;
    }
    get_comment(that,comment_flag);
  },
  go_index:function(){
    util.navigateTo({
      url: '/pages/index/index'
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (opt) {
    if (!util.check_setting(opt)) return;
    var that = this;
    order_sn = opt.order_sn;
    get_comment(that,10);
  },
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },
  //选择评分
  choose_star:function(e){
    var that = this;
    var id = e.target.dataset.id;
    for (var i = 0; i < that.data.star.length;i++){
      if(i <= id){
        that.data.star[i].show = true;
      }else{
        that.data.star[i].show = false;
      }
    }
    that.data.info.commstar = parseInt(id) + 1;
    this.setData({
      star: that.data.star,
      info: that.data.info
    })
  },
  //上传图片
  upImage: function (e) {
    var that = this;
    var info = that.data.info;
    var url = util.getUrl('/api/wxapp/image/upload');
    let count = 9 - info.comm_img.length;
    wx.chooseImage({
      count: count, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (res) {
          for (var i = 0; i < tempFilePaths.length ; i++){
            util.uploadFile(url, tempFilePaths[i], { img_cat_id : -1 }, function (e) {
              console.log(e)
              var data = JSON.parse(e.data);
              info.comm_img.push(data.content[0].img_url);
              var img_len = parseInt(info.comm_img.length);
              that.setData({
                info: info,
                image: true,
                img_len: img_len
              })
            });
          }
        }
      }
    })
    // util.uploadImage(9, function (con) {
    //   var data = JSON.parse(con.data);
    //   for (var i in data.content){
    //     info.comm_img.push(data.content[i].img_url);
    //   }
    //   console.log(info)
    //   var img_len = parseInt(info.comm_img.length);
    //   that.setData({
    //     info: info,
    //     image: true,
    //     img_len: img_len
    //   })
    //   });
  },
  delImage:function(e){
    var that = this;
    var index = e.currentTarget.dataset.idx;
    var info = that.data.info;
    info.comm_img.splice(index, 1);
    var img_len = parseInt(info.comm_img.length);
    this.setData({
      info: info,
      img_len: img_len
    })
  },
  comm_note:function(e){
    var info = this.data.info;
    info.comm_note = e.detail.value;
    this.setData({
      info:info
    })
  },
  flag:function(){
    var info = this.data.info;
    if(this.data.flag == true){

      info.anonymousflag = 0;
      this.setData({
        info: info,
        flag:false
      })
    }else{
      info.anonymousflag = 1;
      this.setData({
        info: info,
        flag: true
      })
    }
  },
  //显示提交评价页面
  com_show:function(e){
    var id = e.target.dataset.id;
    var gId = e.target.dataset.itemid;
    var gid = e.target.dataset.gid;
    var osn = e.target.dataset.osn;
    var star = this.data.star;
    var order = this.data.order_completed;
    var info = this.data.info;
    info.gid = gid;
    info.osn = osn;
    for (var i = 0; i < order.length; i++) {
      for (var j = 0; j < order[i].goods.length; j++) {
        if (i == parseInt(id) && j == parseInt(gId)){
          if (order[i].goods[j].show == true){
            order[i].goods[j].show = false;
            order[i].goods[j].src = src_down;
          }else{
            order[i].goods[j].show = true;
            order[i].goods[j].src = src_up;
          }
          if(order[id].comment[gId] == null){
            info.anonymousflag = 0;
            info.comm_img = [];
            info.comm_note = '';
            info.commstar = 5;
            star = [
              {
                show: true
              },
              {
                show: true
              },
              {
                show: true
              },
              {
                show: true
              },
              {
                show: true
              }
            ]
          }
        }else{
          order[i].goods[j].show = false;
          order[i].goods[j].src = src_down;
        }
      }
    }
    this.setData({
      order_completed: order,
      info:info,
      star: star,
      flag:false,
      img_len:0
    })
  },
  //显示评论内容页面
  show_com_info: function (e) {
    var id = e.target.dataset.id;
    var gId = e.target.dataset.itemid;
    var gid = e.target.dataset.gid;
    var osn = e.target.dataset.osn;
    var order = this.data.order_completed;
    for (var i = 0; i < order.length; i++) {
      for (var j = 0; j < order[i].goods.length; j++) {
        if (i == parseInt(id) && j == parseInt(gId)) {
          if (order[i].goods[j].show_info == true) {
            order[i].goods[j].show_info = false;
            order[i].goods[j].src = src_down;
          } else {
            order[i].goods[j].show_info = true;
            order[i].goods[j].src = src_up;
          }
        } else {
          order[i].goods[j].show_info = false;
          order[i].goods[j].src = src_down;
        }
      }
    }
    this.setData({
      order_completed: order,
    })
  },
  //隐藏评价
  close_com_info: function (e) {
    var id = e.target.dataset.id;
    var gId = e.target.dataset.itemid;
    var gid = e.target.dataset.gid;
    var osn = e.target.dataset.osn;
    var order = this.data.order_completed;
    for (var i = 0; i < order.length; i++) {
      for (var j = 0; j < order[i].goods.length; j++) {
        order[i].goods[j].show_info = false;
      }
    }
    this.setData({
      order_completed: order,
    })
  },
  go_center:function(){
    wx.switchTab({
      url: '/pages/usercenter/usercenter',
    })
  },
  back: function (e) {
    var datas = JSON.stringify(e.currentTarget.dataset);
    util.navigateTo({ url: '/pages/orderlist/orderlist?datas=' + datas })
  },
   onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  //评价并继续
  good_commtag:function(e){
    var that = this;
    var order_sn = this.data.order_sn;
    var info = this.data.info;
    var comm = {};
    var img = {};
    var id = 0;
    info.form_id = e.detail.formId;
    info.open_id = util.getCache('openid');
    if (parseInt(info.commstar) == parseInt(0)){
      util.showModal('提示','请选择评分');
      return false;
    }
    if (info.comm_img != '') {
      info.comm_img = JSON.stringify(info.comm_img);
    }else{
      info.comm_img = info.comm_img.toString();
    }
    util.api('/api/wxapp/comment/add?user_id=' + util.getCache('user_id'),function(e){
      get_comment(that,10);
    },info);
  },
  giftInfo(e){
    var form_id = e.detail.formId;
    var open_id = util.getCache("openid");
    util.api("/api/wxapp/common/saveformid", function (res) { }, { form_id: form_id, open_id: open_id });
    let data = e.currentTarget.dataset;
    let commentInfo = this.data.order_completed[data.id].comment[data.itemid]
    console.log(commentInfo);
    if (commentInfo.commentAward.type != 5){
      const url = new Map([
        [1, ['pages/integral/integral']],
        [2, ['pages/couponlist/couponlist']],
        [3, ['pages/account/account']],
        [4, ['pages/lottery/lottery?lottery_id=' + commentInfo.commentAward.con]]
      ])
      let action = url.get(commentInfo.commentAward.type)
      util.jumpLink(action[0], 'navigateTo')
    } else {
      let custom = {}
      custom.link = commentInfo.commentAward.con.link
      custom.img_src = commentInfo.commentAward.con.img
      this.setData({
        show_act_custom:true,
        custom: custom
      })
    }
  },
  bindPreviewImage(e) {
    let src = e.currentTarget.dataset.src;
    let srcarr = e.currentTarget.dataset.srcarr;
    let arrs = [];
    for(let i in srcarr){
      arrs.push(srcarr[i])
    }
    if (src) {
      wx.previewImage({
        current: src,
        urls: arrs
      })
    }
  },
})
function get_comment(that,comment_flag){
  util.api('/api/wxapp/comment/user', function (e) {
    var order = e.content.order_completed;
    if (order.length > 0) {
      for (var i = 0; i < order.length; i++) {
        for (var j = 0; j < order[i].goods.length; j++) {

            order[i].goods[j].show = false;
            order[i].goods[j].src = src_down;
            if (order[i].comment[j]) {
              order[i].comment[j].commstar = parseInt(order[i].comment[j].commstar);
              if (parseInt(order[i].comment[j].anonymousflag) == 0) {
                order[i].comment[j].anonymousflag = false;
              } else {
                order[i].comment[j].anonymousflag = true;
              }
              if (order[i].comment[j].comm_img != '' || order[i].comment[j].comm_img != null) {
                order[i].comment[j].comm_img = JSON.parse(order[i].comment[j].comm_img);
              }
            }
            if (order[i].comment[j] && order[i].goods[j].comment_flag == 1){
              order[i].goods[j].show_info = true;
            }

        }
      }
      that.setData({
        order_completed: order,
        com_flag: true
      })
    } else {
      that.setData({
        com_flag: false
      })
    }
  }, { order_sn: order_sn,   comment_flag: comment_flag });
}
