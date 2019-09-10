var util = require('../../utils/util.js');
var app = getApp()
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
var goods_id;
var reply_len;
global.wxPage({
  data: {
    all_com: 'border-bottom: 2px solid #ff6561; color: #ff676f;',
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
    good_comment_info: [],
    isFold: true,
    max: true
  },
  onLoad: function (option) {
    if (!util.check_setting(option)) return;
    goods_id = option.goods_id;
    var that = this;
      util.api('/api/wxapp/comment', function (res) {
        that.data.all_com = "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";";
        var gd = res.content;
        var strRegex = "^((https|http|ftp|rtsp|mms)?://)";
        var re = new RegExp(strRegex);
        var com_reg = /^(.).+(.)$/g;
        for (var i in gd.comment) {
          if (!re.test(gd.comment[i].user_avatar)) {
            gd.comment[i].user_avatar = imageUrl + gd.comment[i].user_avatar;
          }
          if (gd.comment[i].comm_img) {
            gd.comment[i].comm_img = JSON.parse(gd.comment[i].comm_img);
          }
          gd.comment[i].name = gd.comment[i].username.replace(com_reg, "$1**$2");
          gd.comment[i].isfold = false;
          gd.comment[i].max = true;
          if (gd.comment[i].answer != null) {
            var reply_len = gd.comment[i].answer;
            var blen = 0;
            for (var j = 0; j < reply_len.length; j++) {
              if ((reply_len.charCodeAt(j) & 0xff00) != 0) {
                blen++;
              }
              blen++;
              gd.comment[i].reply = blen;
            }
          }
        }
        var total_comm_num = gd.number[0].num + gd.number[1].num + gd.number[2].num;
        if (option.comment_type) that.change_com('', option.comment_type)
        if (!option.comment_type) {
          that.setData({
            all_com: that.data.all_com,
            good_comment_info: gd.comment
          })
        }
        that.setData({
          goods_comment_lv: gd.number,
          total_comm_num: total_comm_num,
        }, function () {
          that.getReplyRect(0);
        });
        that.data.good_comment_info = gd.comment;
      }, {
          goods_id: goods_id
        });
    
  },

  getReplyRect: function (count) {
    var that = this;
    if (count > 1) return;
    var arry = [];
    wx.createSelectorQuery().selectAll('.reply_text').boundingClientRect(function (rects) {
      if (rects == null || rects.length == 0) {
        setTimeout(function () {
          that.getReplyRect(count + 1);
        }, 100);
        return;
      }
      for (var i = 0; i < rects.length; i++) {
        arry.push({ id: parseInt(rects[i].id), height: rects[i].height });
      }
      for (var j = 0; j < that.data.good_comment_info.length; j++) {
        for (var k = 0; k < arry.length; k++) {
          if (that.data.good_comment_info[j].id == arry[k].id) {
            that.data.good_comment_info[j].height = arry[k].height;
            if (that.data.good_comment_info[j].height > 85) {
              that.data.good_comment_info[j].isfold = true;
            }
          }
        }
      }
      that.setData({
        good_comment_info: that.data.good_comment_info,
      })
    }).exec();
  },

  onShow: function () {

  },

  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },


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
  //评论显隐藏
  showReply: function (e) {
    var that = this;
    var index = e.target.dataset.index;
    that.data.good_comment_info[index].isfold = !e.target.dataset.isfold;
    that.data.good_comment_info[index].max = !e.target.dataset.max;
    that.setData({
      good_comment_info: that.data.good_comment_info,
    });
  },
  change_com: function (e,comment_type) {
    var that = this
    var ctype = comment_type ? comment_type : e.currentTarget.dataset.ctype;
    util.api('/api/wxapp/comment?goods_id=' + goods_id, function (res) {
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
        good_comment_info: res.content.comment
      });
      if (ctype == 'allpingjia') {
        that.setData({
          all_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          good_com: '',
          worse_com: '',
          worst_com: '',
          pic_com: ''
        });
      } else if (ctype == 'haoping') {
        that.setData({
          good_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          all_com: '',
          worse_com: '',
          worst_com: '',
          pic_com: ''
        })
      } else if (ctype == 'zhongping') {
        that.setData({
          worse_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          all_com: '',
          good_com: '',
          worst_com: '',
          pic_com:''
        })
      } else if (ctype == 'chaping') {
        that.setData({
          worst_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          all_com: '',
          good_com: '',
          worse_com: '',
          pic_com: ''
        })
      } else if (ctype == 'youtu') {
        that.setData({
          pic_com: "border-bottom:2rpx solid " + that.data.comColor + ";color:" + that.data.comColor + ";",
          all_com: '',
          good_com: '',
          worst_com: '',
          worse_com: ''
        })
      }
    }, {
        pingjia: ctype
      });
  },
})