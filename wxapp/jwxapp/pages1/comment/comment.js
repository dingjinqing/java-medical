// pages/comment/comment.js
var app = getApp()
var util = require('../../utils/util.js');
var s = util.getImageUrl('');
var imageUrl = app.globalData.imageUrl;
var nroot = s.substring(0, (s.length - 1));
var Url = app.globalData.baseUrl;
var order_sn = ''; // 如果是从单个商品跳转过来，订单编号
var src_down = imageUrl + 'image/wxapp/down_normal.png';
var src_up = imageUrl + 'image/wxapp/up_normal.png';
var order_completed = [];
var comment_flag = 0; // 待评论Or已评论
var userId = util.getCache('user_id')
global.wxPage({
  data: {
    navbar: [
      '待评价',
      '已评价'
    ],
    page: 1,
    last_page: 1,
    imageUrl: app.globalData.imageUrl,
    img_len: 0,
    image: false,//是否显示晒单的图片
    islogin: true,
    currentTab: 0, // 展示的Tab，0位待评价
    com_flag: false,//是否有订单
    root: util.getImageUrl(''),
    no_root: nroot,
    info: {
      anonymousflag: 0, // 是否匿名
      comm_img: [], // 晒图
      commstar: 5, // 评价几星
      commNote: '' // 评价文字
    },//提交的信息
    flag: false, // 是否选中匿名评价
    pass_commtag: [],//传递的标签
    order_completed: [],
    star: [
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
  },
  navbarTap: function (e) {
    var that = this;
    that.setData({
      currentTab: e.currentTarget.dataset.idx
    })
    that.data.page = 1;
    comment_flag = parseInt(e.currentTarget.dataset.idx);
    this.get_comment(that);
    this.hello()
  },
  hello() {
    console.log('hellll')
  },
  go_index: function () {
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
    wx.hideShareMenu(); // 隐藏转发按钮
    this.get_comment(that, 0);
  },
  //选择评分
  choose_star: function (e) {
    var that = this;
    var id = e.target.dataset.id;
    var star = e.target.dataset.star;
    var index = Number(e.target.dataset.index);
    console.log(index)
    for (var i = 0; i < star.length; i++) {
      if (i <= id) {
        star[i].show = true;
      } else {
        star[i].show = false;
      }
    }
    that.data.info.commstar = parseInt(id) + 1;
    var order_star = 'order_completed[' + index + '].star';
    this.setData({
      [order_star]: star,
      info: that.data.info
    })
  },
  // 上传图片
  upImage: function (e) {
    var that = this;
    var info = that.data.info;
    var url = util.getUrl('/api/wxapp/image/upload');
    let count = 9 - info.comm_img.length; // 最多可以选择的图片张数
    wx.chooseImage({
      count: count, // 默认9
      sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
        var tempFilePaths = res.tempFilePaths
        if (res) {
          for (var i = 0; i < tempFilePaths.length; i++) {
            util.uploadFile(url, tempFilePaths[i], { img_cat_id: -1 }, function (e) {
              var data = JSON.parse(e.data);
              console.log(data)
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
  },
  // 删除图片
  delImage: function (e) {
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
  // textarea 输入
  comm_note: function (e) {
    var info = this.data.info;
    info.commNote = e.detail.value;
    this.setData({
      info: info
    })
  },
  // 切换匿名
  flag: function () {
    var info = this.data.info;
    if (this.data.flag == true) {
      info.anonymousflag = 0;
      this.setData({
        info: info,
        flag: false
      })
    } else {
      info.anonymousflag = 1;
      this.setData({
        info: info,
        flag: true
      })
    }
  },
  // 展示评价
  show_com_info: function (e) {
    console.log(e)
    var gid = e.currentTarget.dataset.gid;
    var order = this.data.order_completed.map(function (item, index) {
      if (item.goodsId == gid) {
        item.show = !item.show;
        if (item.show) {
          item.src = src_up
        } else {
          item.src = src_down
        }
      } else {
        item.show = false
        item.src = src_down
      }
      return item
    })
    console.log(order)
    this.setData({
      order_completed: order,
    })
  },
  // 隐藏评价
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
  go_center: function () {
    wx.switchTab({
      url: '/pages/usercenter/usercenter',
    })
  },
  back: function (e) {
    var datas = JSON.stringify(e.currentTarget.dataset);
    util.navigateTo({ url: '/pages/orderlist/orderlist?datas=' + datas })
  },
  // 下拉刷新
  onPullDownRefresh: function () {
    console.log('下拉刷新...')
    wx.stopPullDownRefresh();
  },
  // 评价并继续
  good_commtag: function (e) {
    /**
     * goodsId // 商品id
     * userId // 用户id
     * orderSn // 订单号
     * commstar// 几星
     * commNote// 评价心得
     * commImg // 晒单图片数组转string
     * anonymousflag // 是否匿名
     * id // 评价有礼活动id
     * awardType // 评价有礼奖励类型
     * award // 评价有礼奖励内容
     */
    console.log(e)
    var that = this;
    var info = this.data.info;
    var item = e.detail.target.dataset.item
    let params = {
      form_id: e.detail.formId,
      goodsId: item.goodsId,
      userId: userId,
      orderSn: item.orderSn,
      commNote: e.detail.value.commNote,
      commstar: info.commstar,
      commImg: info.commImg,
      anonymousflag: info.anonymousflag,
      id: item.id, // 有奖活动id
      awardType: item.awardType, // 有奖活动类型
      award: item.award // 有奖活动内容
    }

    info.open_id = util.getCache('openid');
    if (parseInt(params.commstar) === 0) {
      util.showModal('提示', '请选择评分');
      return false;
    }
    if (info.commImg != '') {
      info.commImg = JSON.stringify(info.commImg);
    } else {
      info.commImg = info.commImg.toString();
    }
    console.log(params);
    // console.log(info)
    util.api('/api/wxapp/comment/add', function (res) {
      if (res.error === 0) {
        util.toast_success('评价成功');
        that.get_comment(that, 0);
      }
    }, params);
  },
  bindPreviewImage(e) {
    let src = e.currentTarget.dataset.src;
    let srcarr = e.currentTarget.dataset.srcarr;
    let arrs = [];
    for (let i in srcarr) {
      arrs.push(srcarr[i])
    }
    if (src) {
      wx.previewImage({
        current: src,
        urls: arrs
      })
    }
  },
  resetShow() {
    this.setData({
      show_act_custom: false,
    })
  },
  // 页面上拉触底事件的处理函数
  onReachBottom: function () {
    console.log('reachBottom....')
    // var that = this;
    // if (that.data.page == that.data.last_page) {
    //   return;
    // }
    // that.data.page = that.data.page + 1;
    // util.api('/api/wxapp/comment/list', function (e) {
    //   var order_completed = [];
    //   if (e.content && e.content.length > 0) {
    //     order_completed = e.content;
    //     for (var i = 0; i < order_completed.length; i++) {
    //       for (var j = 0; j < order_completed[i].goods.length; j++) {
    //         order_completed[i].goods[j].show = false;
    //         order_completed[i].goods[j].show_info = false;
    //         order_completed[i].goods[j].src = src_down;
    //         if (order_completed[i].comment[j]) {
    //           order_completed[i].comment[j].commstar = parseInt(order_completed[i].comment[j].commstar);
    //           if (parseInt(order_completed[i].comment[j].anonymousflag) == 0) {
    //             order_completed[i].comment[j].anonymousflag = false;
    //           } else {
    //             order_completed[i].comment[j].anonymousflag = true;
    //           }
    //           if (order_completed[i].comment[j].comm_img != "" ** order_completed[i].comment[j].comm_img != null) {
    //             order_completed[i].comment[j].comm_img = JSON.parse(order_completed[i].comment[j].comm_img);
    //           }
    //         }
    //       }
    //     }
    //     that.setData({
    //       order_completed: that.data.order_completed.concat(order_completed),
    //     })
    //   }
    // }, { userId: userId, commentFlag: comment_flag, orderSn: order_sn, page_no: that.data.page });
  },
  get_comment: function (that) {
    util.api('/api/wxapp/comment/list', function (res) {
      var order_completed = [];
      if (res.content && res.content.length > 0) {
        order_completed = res.content;
        order_completed.forEach(function (item, i) {
          item.show = false;
          item.src = src_down;
          item.commstar = Number(item.commstar);
          if (item.commentFlag === 1) {
            item.show = true;
            item.src = src_up;
          }
          item.star = JSON.parse(JSON.stringify(that.data.star))
          console.log(JSON.stringify(item.star))
          if (item.commstar && item.commstar < 6) {
            for (var i = 4; i > 0; i--) {
              if (i >= item.commstar) {
                item.star[i].show = false
              }
            }
          }
          console.log(item.star)
        })
      }
      that.setData({
        order_completed: order_completed,
      })
      console.log(order_completed)
    }, { userId: userId, commentFlag: comment_flag, orderSn: order_sn, page_no: that.data.page });
  }
})

