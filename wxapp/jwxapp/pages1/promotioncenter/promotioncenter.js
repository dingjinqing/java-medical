// pages1/promotioncenter/promotioncenter.js
var util = require('../../utils/util.js')
var app = getApp()
var imageUrl = app.globalData.imageUrl;
var baseUrl = app.globalData.baseUrl;
var mobile = util.getCache('mobile');
var pro_info = [];
var pro_goods = [];
var pictorial;
var posterBase64 = '';
var sort_id = '';
var selectBrand = [];
var selectBrandName = [];
var activityGood = [];
var search_first = 1;
global.wxPage({

  /**
   * 页面的初始数据
   */
  data: {
    imageUrl: app.globalData.imageUrl,
    sort_order: "desc",
    sort_name: "",
    show_hidden: false,
    isShow: false,
    navigation_h: 0,
    show_change: false,
    show_which: 0,
    pro_info: [],
    searchText: '',
    pro_goods: [],
    page: 1,
    last_page: 1,
    share_good: false,
    choosed_index: "",
    is_share: 0,
    min_price: '',
    max_price: '',
    // fenlei 
    goodsCategory1: '',
    primaryfirst: [],
    primarysecond: [],
    selectedFlag: [],
    // 分类
    sort_id: '',
    dataFlag: false,
    goods_sort_name: '',
    parent_sort_id: '',
    // 品牌
    selectBrand: [],
    selectBrandName: [],
    // 活动商品
    list: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!util.check_setting(options)) return;
    var that = this;
    wx.hideShareMenu();
    search_first = 1;
    that.data.navigation_h = wx.getMenuButtonBoundingClientRect().bottom + 8;
    that.setData({
      navigation_h: that.data.navigation_h
    });
    pro_request(that);
  },
  //切换样式
  change_style_btn: function () {
    if (this.data.show_change == false) {
      this.data.show_change = true;
    } else {
      this.data.show_change = false;
    }
    this.setData({
      show_change: this.data.show_change
    })
  },
  toggle_style: function (e) {
    let this_style = e.currentTarget.dataset.style;
    if (this_style == 0) {
      this.data.show_which = 0
    } else if (this_style == 1) {
      this.data.show_which = 1
    } else {
      this.data.show_which = 2
    }
    this.setData({
      show_change: false,
      show_which: this.data.show_which
    })
  },
  //quxiao筛选
  not_play: function () {
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(500).step()
    this.setData({
      show_hidden: false,
      isShow: false,
      animationLsi: animation.export()
    })
  },
  //筛选框
  leftSlideIn: function () {
    var that = this;
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(-500).step()
    that.setData({
      animationLsi: animation.export(),
      show_hidden: true,
      isShow: true
    })
  },
  // 搜索和排序
  searchGoods: function (e) {
    this.setData({
      search: e.detail.value
    });
    this.data.searchText = e.detail.value;
    this.btnSearch();
  },
  switchOrder: function (e) {
    var sort_order = this.data.sort_order;
    var sort_name = this.data.sort_name;
    var now_sort_name = e.target.dataset.type;
    if (now_sort_name != sort_name) {
      if (now_sort_name != "shop_price") {
        this.setData({
          sort_name: "goods_sale_num",
          now_sort_name: "goods_sale_num",
          sort_order: "desc"
        })
      } else {
        this.setData({
          sort_name: "shop_price",
          now_sort_name: "shop_price",
          sort_order: "asc"
        });
      }
    } else {
      if (sort_order == "desc") {
        this.setData({
          sort_order: "asc"
        });
      } else {
        this.setData({
          sort_order: "desc"
        });
      }
    }
    this.btnSearch();
  },
  // 调到item
  to_item: function (e) {
    util.jumpLink('/pages/item/item?goods_id=' + e.currentTarget.dataset.goods_id);
  },
  // 分享框
  shareGoods: function (e) {
    var that = this;
    that.data.choosed_index = e.currentTarget.dataset.now_index;
    that.setData({
      choosed_index: that.data.choosed_index
    })
    setTimeout(function () {
      that.setData({
        share_good: true,
      })
    }, 500);

  },
  bindClose(e) {
    this.setData({
      share_good: false
    });
  },
  // 下载海报
  go_share: function (e) {
    var that = this;
    var now_inde = e.currentTarget.dataset.now_indexs;
    var now_goods_id = that.data.pro_goods[now_inde].goods_id;
    that.setData({
      choosed_index: now_inde
    })
    wx.showLoading({
      title: '生成中',
    })
    if (e.currentTarget.dataset.if_tor == 1) {
      var if_tor = 1;
    } else {
      var if_tor = 0;
    }
    // util.api('/api/wxapp/pictorial', function (res) {
    //   if (res.error == 0) {
    //     pictorial = res.content.pictorial;
    //     if (pictorial) {
    //       util.api('/api/wxapp/upayyun/image', function (res) {
    //         if (res.error == 0) {
    //           pictorial = imageUrl + pictorial + "!big";
    //           posterBase64 = res.content;
    //           that.setData({
    //             pictorial: posterBase64,
    //             if_tor: if_tor
    //           })
    //           wx.hideLoading();
    //           that.setData({
    //             is_share: 1,
    //             share_good: false,
    //           })
    //         }
    //       }, {
    //           image_path: pictorial
    //         });
    //     }
    //   } else {
    //     wx.hideLoading();
    //     util.toast_fail(res.message);
    //     return false;
    //   }
    // }, {
    //     action: 1,
    //     goods_id: now_goods_id
    //   })

  },
  not_show_share: function () {
    var that = this;
    that.setData({
      is_share: 0
    })
  },
  // 保存图片，复制推广语
  saveImgToPhotosAlbumTap: function (e) {
    var that = this;
    var now_indes = e.currentTarget.dataset.now_indexs;
    var now_goods_id = that.data.pro_goods[now_indes].goods_id;
    if (posterBase64) {
      if (e.currentTarget.dataset.iftor == 1) {
        util.base64ImageHandle(posterBase64, function (res) {
          wx.showLoading({
            title: '保存中',
          })
          // util.api('/api/wxapp/upayyun/image', function (res) {
          //   console.log(res.content.length);
          //   if (res.error == 0) {
          //     let le = res.content.length;
          //     let if_ok = 0;
          //     for (let i in res.content) {
          //       if_ok++;
          //       util.base64ImageHandle(res.content[i], function (res) {
          //         if (if_ok == le) {
          //           wx.hideLoading();
          //           that.save_photo_tips(that, now_indes);
          //         }
          //       });
          //     }
          //   }
          // }, {
          //     goods_id: now_goods_id
          //   });
        });
      } else {
        util.base64ImageHandle(posterBase64, function (res) {
          that.save_photo_tips(that, now_indes);
        });
      }
    } else {
      util.toast_fail('正在生成中...')
    }
  },
  save_photo_tips: function (that, now_indes) {
    var now_goods_name = that.data.pro_goods[now_indes].goods_name;
    var now_goods_price = that.data.pro_goods[now_indes].region_price;
    var promotion_language = that.data.pro_goods[now_indes].promotion_language;
    //复制
    console.log(now_goods_name);
    if (promotion_language != '' || now_goods_name != "") {
      wx.setClipboardData({
        data: now_goods_name + "   ￥" + now_goods_price + "，" + promotion_language,
        success: function (res) {
          wx.hideToast();
          that.setData({
            toastInfo: {
              icon: 'success',
              duration: 4000,
              title: '图片已保存到相册',
              content: now_goods_name + "   ￥" + now_goods_price + "，" + promotion_language + '  商品信息已复制'
            },
            copyComplete: true
          })
        }
      });
    } else {
      that.setData({
        toastInfo: {
          icon: 'success',
          duration: 2000,
          title: '图片已保存到相册'
        },
        copyComplete: true,
      })
    }
    that.setData({
      is_share: 0
    })
  },
  // 加载页面
  btnSearch: function (type = 1) {
    this.data.page = 1;
    var that = this;
    if (type == 2) {
      that.reset_search();
    }
    pro_request(that);
  },
  // 筛选
  //价格
  max_priceInput: function (e) {
    if (!/^\d+$/.test(e.detail.value)) {
      this.data.max_price = e.detail.value = '';
    } else {
      this.data.max_price = e.detail.value;
    }
    this.setData({
      max_price: this.data.max_price,
    })
  },
  min_priceInput: function (e) {
    if (!/^\d+$/.test(e.detail.value)) {
      this.data.min_price = e.detail.value = '';
    } else {
      this.data.min_price = e.detail.value;
    }
    this.setData({
      min_price: this.data.min_price,
    })
  },
  // 分类
  checkedCategory: function (e) {
    let animation3 = wx.createAnimation({
      duration: 1000,
      timingFunction: 'ease'
    })
    animation3.translateX(420).step()
    this.setData({
      categoryLsi: animation3.export()
    })

    if (sort_id == e.currentTarget.dataset.id) {
      sort_id = 0;
      this.data.goods_sort_name = '';
    } else {
      sort_id = e.currentTarget.dataset.id;
      this.data.goods_sort_name = e.currentTarget.dataset.sort;
    }
    this.setData({
      dataFlag: !e.currentTarget.dataset.flag,
      sort_id: sort_id,
      goods_sort_name: this.data.goods_sort_name
    });
  },
  // 点击分类更多时
  categoriesTap: function () {
    var firstSort = [];
    for (var i in this.data.primaryfirst) {
      firstSort.push(this.data.primaryfirst[i].sort_id);
      if (this.data.primaryfirst[i].sort_id == sort_id) {
        var haindex = i;
        this.data.selectedFlag[haindex] = true;
        break;
      } else if (this.data.primaryfirst[i].child != []) {
        for (var j in this.data.primaryfirst[i].child) {
          this.data.parent_sort_id = this.data.primaryfirst[i].child[j].parent_id;
          if (this.data.primaryfirst[i].child[j].sort_id == sort_id) {
            var haindex = firstSort.indexOf(this.data.parent_sort_id)
            this.data.selectedFlag[haindex] = true;
            break;
          }
        }
      }
    }
    var that = this;
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(-502).step()
    that.setData({
      parent_sort_id: this.data.parent_sort_id,
      categoryLsi: animation.export(),
      selectedFlag: this.data.selectedFlag
    })
  },
  no_category: function () {
    // 筛选
    let animation1 = wx.createAnimation({
      duration: 0,
      timingFunction: 'ease'
    })
    animation1.translateX(420).step()
    // 分类
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(402).step()
    this.setData({
      show_hidden: false,
      isShow: false,
      animationLsi: animation1.export(),
      categoryLsi: animation.export()
    })
  },
  // 点击更多分类的返回按钮时
  to_filter: function () {
    let animation2 = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation2.translateX(402).step()
    this.setData({
      categoryLsi: animation2.export()
    })
  },
  // 分类展开折叠选择
  changeToggle: function (e) {
    var index = e.currentTarget.dataset.index;
    if (this.data.selectedFlag[index]) {
      this.data.selectedFlag[index] = false;
    } else {
      this.data.selectedFlag[index] = true;
    }
    this.setData({
      selectedFlag: this.data.selectedFlag,
    })
  },
  clear_category: function (e) {
    sort_id = '';
    this.data.goods_sort_name = '';
    for (var g in this.data.selectedFlag) {
      if (this.data.selectedFlag[g]) {
        this.data.selectedFlag[g] = false;
      }
    }
    this.setData({
      selectedFlag: this.data.selectedFlag,
      sort_id: '',
      goods_sort_name: ''
    })
  },
  //品牌 
  activitybrand: function (e) {
    var brand_id;
    var that = this;
    if (e.currentTarget.dataset.brand_id) {
      brand_id = e.currentTarget.dataset.brand_id;
    } else {
      brand_id = e.detail.brand_id;
    }
    getBrand([brand_id], that)
  },
  // 点击品牌更多
  brandMoreTap: function () {
    var _this = this;
    var screen_height = wx.getSystemInfoSync().windowHeight;
    screen_height = screen_height - wx.getMenuButtonBoundingClientRect().bottom - 58
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(-502).step()
    _this.setData({
      brandLsi: animation.export(),
      right_height: screen_height
    })
  },
  no_brand: function () {
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(402).step()
    this.setData({
      brandLsi: animation.export()
    })
  },
  //活动商品
  activityGoods: function (e) {
    var data_id = e.currentTarget.dataset.id;
    var data_index = e.currentTarget.dataset.index;
    if (this.data.list[data_index].checked == 0) {
      this.data.list[data_index].checked = 1;
      activityGood.push(data_id);
    } else {
      activityGood.splice(activityGood.indexOf(data_id), 1);
      this.data.list[data_index].checked = 0;
    }
    this.setData({
      active_list: this.data.list,
    })
  },
  // chongzhi
  reset_search: function (e) {
    this.data.max_price = '';
    this.data.min_price = '';
    sort_id = '';
    activityGood = [];
    for (var i in this.data.list) {
      this.data.list[i].checked = '0';
    }
    selectBrand = [];
    selectBrandName = [];
    let brandList = this.data.goodsBrands1;
    for (let i in brandList) {
      brandList[i].isCheck = 0;
    }
    for (let i in this.data.goodsBrands) {
      for (let k in this.data.goodsBrands[i]) {
        this.data.goodsBrands[i][k].isCheck = 0;
      }
    }
    this.setData({
      max_price: '',
      min_price: '',
      sort_id: '',
      goods_sort_name: '',
      active_list: this.data.list,
      selectBrandName: selectBrandName,
      goodsBrands: this.data.goodsBrands ? this.data.goodsBrands : [],
      goodsBrands1: brandList ? brandList : []
    })
  },
  // 确定按钮
  to_search: function (e) {
    var that = this;
    if (parseInt(that.data.max_price) < parseInt(that.data.min_price)) {
      util.showModal("提示", '最高价不能小于最低价');
      return false;
    } else {
      let animation = wx.createAnimation({
        duration: 800,
        timingFunction: 'ease'
      })
      animation.translateX(420).step()
      that.setData({
        show_hidden: false,
        isShow: false,
        animationLsi: animation.export()
      })
    }
    that.btnSearch();
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.stopPullDownRefresh();
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    var that = this;
    wx.showLoading({
      title: '加载中',
    })
    that.setData({
      is_load: 1
    })
    if (that.data.page == that.data.last_page) {
      that.setData({
        is_load: 0
      })
      wx.hideLoading();
      return;
    }
    that.data.page = that.data.page + 1;
    // util.api('/api/wxapp/rebate/goods/list', function (res) {
    //   wx.hideLoading();
    //   if (res.error == 0) {
    //     pro_info = res.content;
    //     var pro_goods = [];
    //     if (pro_info.goods.data != "") {
    //       pro_goods = pro_info.goods.data
    //     } else {
    //       that.setData({
    //         is_load: 0
    //       })
    //     }
    //     that.data.list = res.content.params.activity_valid;
    //     that.setData({
    //       pro_info: pro_info,
    //       pro_goods: that.data.pro_goods.concat(pro_goods),
    //       goodsCategory1: res.content.params.first_two_sorts,
    //       sort_goods_flag: res.content.params.sorts_goods_flag,
    //       goodsBrands: res.content.params.brand_list,
    //       goodsBrands1: res.content.params.first_six_brand,
    //       active_list: that.data.list,
    //       primaryfirst: res.content.params.sorts,
    //     })
    //     getBrand(selectBrand, that, search_first++);
    //   } else {
    //     util.showModal("提示", res.message, function () { });
    //     return false;
    //   }
    // }, {
    //     search: that.data.searchText,
    //     pageNo: that.data.page,
    //     sort_name: that.data.sort_name,
    //     sort_order: that.data.sort_order,
    //     sort_id: sort_id,
    //     max_price: that.data.max_price,
    //     min_price: that.data.min_price,
    //     activity_type: JSON.stringify(activityGood),
    //     brands: JSON.stringify(selectBrand),
    //   });
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (e) {
    var that = this;
    var now_choosed = e.target.dataset.now_indexs;
    var now_named = that.data.pro_goods[now_choosed].goods_name;
    var now_img = that.data.pro_goods[now_choosed].goods_img;
    var now_goods_id = that.data.pro_goods[now_choosed].goods_id;
    var usernames = util.getCache('nickName');
    return {
      path: '/pages/item/item?goods_id=' + now_goods_id + '&invite_id=' + util.getCache('user_id'),
      title: usernames + '为您独家推荐' + now_named,
      imageUrl: now_img,
    }
  }
})
function pro_request(that) {
  // util.api('/api/wxapp/rebate/goods/list', function (res) {
  //   if (res.error == 0) {
  //     pro_info = res.content;
  //     var pro_goods = [];
  //     that.data.last_page = pro_info.goods.last_page;
  //     if (pro_info.goods.data != "") {
  //       pro_goods = pro_info.goods.data
  //     } else {
  //       that.setData({
  //         is_load: 0
  //       })
  //     }
  //     if (that.data.page == res.content.goods.last_page) {
  //       that.setData({
  //         is_load: 0
  //       })
  //     }
  //     that.data.list = res.content.params.activity_valid;
  //     that.setData({
  //       pro_info: pro_info,
  //       pro_goods: pro_goods,
  //       goodsCategory1: res.content.params.first_two_sorts,
  //       sort_goods_flag: res.content.params.sorts_goods_flag,
  //       goodsBrands: res.content.params.brand_list,
  //       goodsBrands1: res.content.params.first_six_brand,
  //       active_list: that.data.list,
  //       primaryfirst: res.content.params.sorts,
  //     })
  //     getBrand(selectBrand, that, search_first++);
  //   } else {
  //     util.showModal("提示", res.message, function () { });
  //     return false;
  //   }
  // }, {
  //     search: that.data.searchText,
  //     pageNo: that.data.page,
  //     sort_name: that.data.sort_name,
  //     sort_order: that.data.sort_order,
  //     sort_id: sort_id,
  //     max_price: that.data.max_price,
  //     min_price: that.data.min_price,
  //     activity_type: JSON.stringify(activityGood),
  //     brands: JSON.stringify(selectBrand),
  //   });
}
function getBrand(brand_id, that, search = 0) {
  if (brand_id != '' || brand_id.length != 0) {
    let brandList = that.data.goodsBrands1;
    let goodsBrands = that.data.goodsBrands;
    brand_id.forEach(function (id) {
      brandList.forEach(function (el) {
        if (el.id == id) {
          el.isCheck = el.isCheck ? 0 : 1;
        }
      })
      for (let i in goodsBrands) {
        for (let k in goodsBrands[i]) {
          if (goodsBrands[i][k].id == id) {
            goodsBrands[i][k].isCheck = goodsBrands[i][k].isCheck ? 0 : 1;
            if (goodsBrands[i][k].isCheck == 1) {
              !search && selectBrand.push(id)
              search <= 1 && selectBrandName.push(goodsBrands[i][k].brand_name)
            } else {
              let index = selectBrand.indexOf(id);
              if (index != -1) {
                selectBrand.splice(index, 1);
                selectBrandName.splice(index, 1);
              }
            }
          }
        }
      }
    })
    that.setData({
      goodsBrands: goodsBrands,
      goodsBrands1: brandList,
      selectBrandName: selectBrandName.join('；'),
    })
  }
}