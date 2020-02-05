// pages/comment/comment.js
var i18n = require("../../utils/i18n/i18n.js")
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
var comment_flag = 0; // 待评论0,已评论1
var userId = util.getCache('user_id')
global.wxPage({
  data: {
    navbar: [
      i18n.trans('page1.comment.comment'),// 待评价
      i18n.trans('page1.comment.reviewed')// 已评价
    ],
    page: 1,
    last_page: 1,
    imageUrl: util.getImageUrl(""),
    img_len: 0, // 添加了几张图片
    imageVisible: false,// 是否显示晒单的图片
    islogin: true,
    currentTab: 0, // 展示的Tab，0位待评价
    com_flag: false,// 是否有订单
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
    ],
    custom: {}
  },
  navbarTap: function (e) {
    var that = this;
    that.setData({
      currentTab: e.currentTarget.dataset.idx
    })
    that.data.page = 1;
    comment_flag = parseInt(e.currentTarget.dataset.idx);
    this.get_comment(that);
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
    order_sn = opt.order_sn || opt.orderSn;
    wx.hideShareMenu(); // 隐藏转发按钮
    this.get_comment(that, 0);
  },
  // 初始化数据
  get_comment: function (that, i) {
    if (i || i === 0) {
      comment_flag = i
    }
    util.api('/api/wxapp/comment/list', function (res) {
      var order_completed = [];
      if (res.content && res.content.length > 0) {
        order_completed = res.content;
        order_completed.forEach(function (item, i) {
          item.show = false;
          item.src = src_down;
          item.commstar = item.commstar ? Number(item.commstar) : 5;
          if (i == 0) {
            item.show = true;
            item.src = src_up;
          }
          item.star = JSON.parse(JSON.stringify(that.data.star))
          if (item.commstar && item.commstar < 6) {
            for (var i = 4; i > 0; i--) {
              if (i >= item.commstar) {
                item.star[i].show = false
              }
            }
          }
          item.anonymousflag = 0
          // 如果有评价有奖，优惠券id:xxx,name:xxx 格式化
          if (item.id && item.awardType == 2) {
            item.award = that.stringToObj(item.award)
          }
          if (item.commentFlag) {
            if (item.commImg) {
              var imgs = []
              if (item.commImg.indexOf('[') > -1) {
                imgs = JSON.parse(item.commImg)
              } else {
                imgs.push(item.commImg)
              }
              console.log('imgs:', imgs)
              item.comm_img = imgs
            }
          }
        })
      }
      that.setData({
        order_completed: order_completed
      })
    }, { userId: userId, commentFlag: comment_flag, orderSn: order_sn, page_no: that.data.page });
  },
  // string 转 obj
  stringToObj (str) {
    let string = str.trim()
    let strArr = string.split(',')
    let result = {}
    strArr.forEach(function (item, i) {
      let itemArr = item.split(':')
      console.log(itemArr)
      result[itemArr[0]] = itemArr[1]
    })
    return result;
  },
  /**
   *  生命周期函数--监听页面显示
   */
  onShow: function () {
  },
  //选择评分
  choose_star: function (e) {
    var that = this;
    var flag = e.currentTarget.dataset.flag;
    var id = e.target.dataset.id;// 点击的第几个星星
    var star = e.target.dataset.star;
    var index = Number(e.target.dataset.index);
    var commstar = e.target.dataset.commstar;
    if (flag == 1) {
      return false;
    }
    for (var i = 0; i < star.length; i++) {
      if (i <= id) {
        star[i].show = true;
      } else {
        star[i].show = false;
      }
    }
    commstar = parseInt(id) + 1;
    var order_star = 'order_completed[' + index + '].star';
    var order_commstar = 'order_completed[' + index + '].commstar';
    this.setData({
      [order_star]: star,
      [order_commstar]: commstar
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
            (function (i) {
              wx.getImageInfo({
                src: tempFilePaths[i],
                success: function (obj) {
                  console.log(obj)
                  var params = {
                    userId: util.getCache('user_id'),
                    imgCatId: -1,
                    needImgWidth: obj.width,
                    needImgHeight: obj.height
                  }
                  util.uploadFile(url, tempFilePaths[i], params, function (e) {
                    var data = JSON.parse(e.data);
                    if (data.error === 0) {
                      info.comm_img.push(data.content.imgPath);
                      var img_len = parseInt(info.comm_img.length);
                      that.setData({
                        info: info,
                        imageVisible: true,
                        img_len: img_len
                      })
                    } else {
                      util.toast_fail(data.message)
                    }
                  });
                },
                fail: function (err) {
                  throw err
                }
              })
            })(i)
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
    info.commNote = e.detail.value.trim();
    this.setData({
      info: info
    })
  },
  // 切换匿名
  flag: function (e) {
    var anonymousflag = e.detail.value;
    var index = e.currentTarget.dataset.index;
    var order_completedFlag = 'order_completed[' + index + '].anonymousflag';
    if (this.data.flag == true) {
      anonymousflag = 0;
      this.setData({
        [order_completedFlag]: anonymousflag,
        flag: false
      })
    } else {
      anonymousflag = 1;
      this.setData({
        [order_completedFlag]: anonymousflag,
        flag: true
      })
    }
  },
  // 展示评价
  show_com_info: function (e) {
    var gid = e.currentTarget.dataset.gid;
    var index = e.currentTarget.dataset.index;
    var orderSn = e.currentTarget.dataset.osn;
    var order = this.data.order_completed.map(function (item, index) {
      if (item.orderSn == orderSn && item.goodsId == gid) {
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
  /**
   * 评价并继续，发表评价
   */
  good_commtag: function (e) {
    // 防抖函数
    util.throttle(this.evaluate, 1500)(e)
  },
  // 发表评价
  evaluate: function (e) {
    var that = this;
    var info = this.data.info;
    var item = e.detail.target.dataset.item
    let params = {
      goodsId: item.goodsId,
      userId: userId,
      orderSn: item.orderSn,
      commNote: e.detail.value.commNote,
      commstar: item.commstar,
      commImg: JSON.stringify(info.comm_img),
      anonymousflag: item.anonymousflag,
      id: item.id, // 有奖活动id
      awardType: item.awardType, // 有奖活动类型
      award: item.award, // 有奖活动内容
      recId: item.recId,
      prdId: item.prdId,
      prdDesc: item.prdDesc
    }
    if (item.id) {
      if (item.awardType === 2) {
        params.award = item.id
      } else if (item.awardType === 5) {
        params.award = JSON.stringify(item.award)
      }
    }
    info.open_id = util.getCache('openid');
    if (parseInt(params.commstar) === 0) {
      util.showModal(i18n.trans('common.tip'), i18n.trans('page1.comment.selectRating'));
      return false;
    }
    if (info.commImg != '') {
      info.commImg = JSON.stringify(info.commImg);
    } else {
      info.commImg = info.commImg.toString();
    }
    if (params.commNote == '') {
      params.commNote = null
    }
    console.log(params);
    util.api('/api/wxapp/comment/add', function (res) {
      if (res.error === 0) {
        util.toast_success(that.$t('page1.comment.reviewSuccess'));
        that.setData({
          info: {
            anonymousflag: 0,
            comm_img: [],
            commstar: 5,
            commNote: ''
          }
        })
        that.get_comment(that, 0);
      }
    }, params);
  },
  // 查看活动奖励
  giftInfo (e) {
    let data = e.currentTarget.dataset;
    let commentInfo = this.data.order_completed[data.itemid];
    if (commentInfo.awardType != 5) {
      const url = new Map([
        [1, ['pages1/integral/integral']], // 积分详情页
        [2, ['pages/coupon/coupon']], // 优惠券列表
        [3, ['pages/account/account']], // 余额详情页
        [4, ['pages1/lottery/lottery?lottery_id=' + commentInfo.award]] // 幸运大抽奖 活动链接
      ])
      let action = url.get(commentInfo.awardType)
      util.jumpLink(action[0], 'navigateTo')
    } else {
      let link = commentInfo.award.split(',')
      let path = link[0].slice(5, link[0].length)
      let img = link[1].slice(4, link[1].length)
      let custom = {
        img_src: img,
        link: path
      }
      this.setData({
        show_act_custom: true,
        custom: custom
      })
    }
  },
  // 预览图片
  bindPreviewImage (e) {
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
  // 关闭自定义奖励弹窗
  resetShow () {
    this.setData({
      show_act_custom: false,
    })
  },
  // 页面上拉触底事件的处理函数
  onReachBottom: function () {
    console.log('reachBottom....')
  }
})

