// pages/searchs/search.js
var util = require('../../utils/util.js');
var spec_mixin = require("../goodscommon/spec.js")
var app = getApp()
var Url = app.globalData.baseUrl;
var imageUrl = app.globalData.imageUrl;
var bottom_img = 0;
var searchText;
var top_display = 0;
var real_Height;
var goodsCategoryList = [];
var arr3 = [];
var dataFlag = false;
var goods_sort_name;
var parent_sort_id;
var form_id;
var cur_idx = '';
var group_idx = '';
var page_id = 0;
var search_first = 1;
var selectBrand = [];
var selectBrandName = [];
var coupon_sn = '';
var alias_code = '';
var is_rebate = 0;
var card_id = '';
// 控制更多分类展开折叠
var selectedFlag = [];
var goodsCategory;
var primarysecond = [];
var max_price = 0;
var min_price = 0;
var activityGood = [];
var Label = [];
var uc_resourse = '';
var navigation_h;

function contains(arr, obj) {
  var i = arr.length;
  while (i--) {
    if (arr[i] === obj) {
      return true;
    }
  }
  return false;
}
var is_sort;
var no_tips;
var goods = [];
var pin_goods = [];
var sort_id;
var is_load = 0;
var reco_goods = [];
var cur_idx_goods = '';
global.wxPage({
  mixins: [spec_mixin],
  /**
   * 页面的初始数据
   */
  data: {
    search: "",
    searcontent: [],
    sort_name: "",
    sort_order: "desc",
    imageUrl: app.globalData.imageUrl,
    isNull: false,
    is_sort: 0,
    page: 1,
    is_new: 1,
    last_page: 0,
    goods: [],
    pin_goods: [],
    is_load: 0,
    reco_goods: [],
    null_marginTop: "200rpx",
    null_marginBottom: "20rpx",
    footer_color: "#fff",
    goodslabelAfter: [],
    dataFlag: false,
    show_hidden: false,
    isShow: false,
    goodsCategory1: '',
    primaryfirst: [],
    primarysecond: [],
    arr3: [],
    list: [{
      listName: '活动商品',
      show: false,
      item: [{
        name: '多人拼团',
        id: '1',
        checked: '0'
      }, {
        name: '砍价',
        id: '3',
        checked: '0'
      }, {
        name: '秒杀',
        id: '5',
        checked: '0'
      }, {
        name: '限时降价',
        id: '6',
        checked: '0'
      }, {
        name: '加价购',
        id: '98',
        checked: '0'
      }, {
        name: '一口价',
        id: '99',
        checked: '0'
      }, {
        name: '首单特惠',
        id: '66',
        checked: '0'
      }]
    }, {
      listName: '商品标签',
      show: false,
      item: []
    }],
    selectedFlag: [],
    selectBrand: [],
    selectBrandName: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (!util.check_setting(options)) return;
    this.data.is_from = options.is_from || '';
    this.reset_search();
    var _this = this;
    if (options.invite_id > 0) {
      setParams(options.params, _this);
    } else {
      var cat_id = options.cat_id;
      sort_id = options.sort_id;
      if (options['coupon_sn']) {
        coupon_sn = options['coupon_sn'];
      } else {
        coupon_sn = '';
      }
      if (options['alias_code']) {
        alias_code = options['alias_code'];
      } else {
        alias_code = '';
      }
      console.log(alias_code);
      is_rebate = options['is_rebate'] ? options['is_rebate'] : 0;
      if (options['cur_idx']) {
        cur_idx = options['cur_idx'];
      } else {
        cur_idx = '';
      }
      if (options['group_idx']) {
        group_idx = options['group_idx'];
      } else {
        group_idx = '';
      }
      if (options['page_id']) {
        page_id = options['page_id'];
      } else {
        page_id = '';
      }
      if (options['card_id']) {
        card_id = options['card_id'];
      } else {
        card_id = '';
      }
      if (options['cur_idx_goods']) {
        cur_idx_goods = options['cur_idx_goods'];
      } else {
        cur_idx_goods = '';
      }
      if (options.sort_id) {
        sort_id = options.sort_id;
        is_sort = 1;
        no_tips = options.sort_names;
        this.setData({
          no_tips: no_tips,
          is_sort: is_sort
        })
      }
      selectBrand = options.brand_id ? [parseInt(options.brand_id)] : [];
      selectBrandName = [];
      Label = options.label_id ? [parseInt(options.label_id)] : [];
    }
    if (options.is_first) {
      activityGood.push(66);
    }

    uc_resourse = "";
    if (options.uc_resourse) {
      uc_resourse = options.uc_resourse
      this.setData({
        page_name: uc_resourse
      })
    }
    search_first = 1
    if (options.invite_id > 0) {
      if (searchText != '')
        this.setData({
          search: searchText
        });
    } else {
      searchText = _this.data.search;
    }
    wx.getSystemInfo({
      success: function(res) {
        real_Height = res.windowHeight * 1.5;
      },
    });
    if (!cat_id) {
      cat_id = "";
    }
    if (!sort_id) {
      sort_id = ""
    }
    let from_count_card = options.from_count_card ? options.from_count_card : 0;
    var win_h = wx.getSystemInfoSync().windowHeight;
    navigation_h = wx.getMenuButtonBoundingClientRect().bottom + 8;
    _this.setData({
      win_h: win_h,
      navigation_h: navigation_h,
      sort_name: _this.data.sort_name,
      sort_order: _this.data.sort_order,
      cat_id: cat_id,
      sort_id: sort_id,
      is_from: _this.data.is_from,
      from_count_card: from_count_card
    })
    if (!(options.invite_id > 0)) {
      var search = options.search;
      if (search) {
        this.setData({
          search: search
        });
        searchText = search;
      }
    }

    search_request(_this);
  },

  // 滑动一屏半时出现回到顶部按钮
  onPageScroll: function(e) {
    var that = this;
    if (e.scrollTop >= real_Height) {
      that.setData({
        top_display: 1
      })
    } else {
      that.setData({
        top_display: 0
      })
    }

  },
  goTop: function(e) { // 一键回到顶部
    if (wx.pageScrollTo) { //判断这个方法是否可用
      wx.pageScrollTo({
        scrollTop: 0
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
    wx.stopPullDownRefresh();
  },

  loadData: function() {
    var _this = this;
    if (_this.data.page >= _this.data.last_page) return;
    if (_this.data.is_load) return;
    _this.setData({
      is_load: 1
    })
    wx.showLoading({
      title: '加载中...',
      mask: true,
    })
    util.api('/api/wxapp/search', function(res) {
      wx.hideLoading();
      if (res.error == 0) {
        if (coupon_sn != '' || alias_code != '') {
          _this.setData({
            page_name: '券购搜索'
          })
        }
        if (res.content.pin_group_goods != '') {
          for (var i in res.content.pin_group_goods) {
            _this.data.pin_goods[i] = res.content.pin_group_goods[i];
          }
        }
        _this.setData({
          goods: _this.data.goods.concat(res.content.goods.data),
          page: res.content.goods.current_page,
          pin_goods: _this.data.pin_goods,
          searcontent: res.content,
          is_load: 0,
        });
      }
    }, {
      search: searchText,
      sort_name: _this.data.sort_name,
      sort_order: _this.data.sort_order,
      cat_id: _this.data.cat_id,
      pageNo: _this.data.page + 1,
      sort_id: sort_id,
      cur_idx: cur_idx,
      group_idx: group_idx,
      page_id: page_id,
      max_price: max_price,
      min_price: min_price,
      labels: JSON.stringify(Label),
      activity_type: JSON.stringify(activityGood),
      coupon_sn: coupon_sn,
      alias_code: alias_code,
      is_rebate: is_rebate,
      form_id: form_id,
      open_id: util.getCache('openid'),
      is_from: _this.data.is_from,
      brands: JSON.stringify(selectBrand)
    });
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {
    var params = {
      search: searchText,
      sort_name: this.data.sort_name,
      sort_order: this.data.sort_order,
      cat_id: this.data.cat_id,
      card_id: card_id,
      pageNo: this.data.page + 1,
      sort_id: sort_id,
      cur_idx: cur_idx,
      group_idx: group_idx,
      page_id: page_id,
      max_price: max_price,
      min_price: min_price,
      labels: Label.join(','),
      activity_type: activityGood.join(','),
      coupon_sn: coupon_sn,
      alias_code: alias_code,
      is_rebate: is_rebate,
      form_id: form_id,
      //open_id: util.getCache('openid'),
      is_from: this.data.is_from,
      brands: selectBrand.join(','),
      card_id: card_id
    };
    console.log(params)
    return {
      path: '/pages/searchs/search?params=' + JSON.stringify(params) + '&invite_id=' + util.getCache('user_id'),
    }
  },
  searchInput: function(e) {
    this.setData({
      search: e.detail.value
    });
    this.btnSearch(2);
  },
  btnSearch: function(type = 1) {
    this.data.page = 1;
    searchText = this.data.search;
    var _this = this;
    if (type == 2) {
      this.reset_search();
    }
    search_request(_this);
  },
  switchOrder: function(e) {
    var sort_order = this.data.sort_order;
    var sort_name = this.data.sort_name;
    var now_sort_name = e.target.dataset.type;

    if (now_sort_name != sort_name) {
      if (now_sort_name != "shop_price") {
        if (now_sort_name === 'min_score') {
          this.setData({
            sort_name: "min_score",
            now_sort_name: "min_score",
            sort_order: "desc"
          });
        } else {
          this.setData({
            sort_name: "goods_sale_num",
            now_sort_name: "goods_sale_num",
            sort_order: "desc"
          });
        }

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
  toDetail: function(e) {
    var goods_ids = e.currentTarget.dataset.goods_ids;
    var goods_type = e.currentTarget.dataset.goods_type;
    if (this.data.from_count_card == 1) {
      util.navigateTo({
        url: '/pages/item/item?goods_id=' + goods_ids + '&from_count_card=1',
      })
    } else if (this.data.is_from == 'integral') {
      var integral_goods_id = e.currentTarget.dataset.integral_goods_id;
      util.navigateTo({
        url: '/pages/integralitem/integralitem?integral_goods_id=' + integral_goods_id,
      })
    } else if (goods_type != 1 && goods_type != 3 && goods_type != 5 && goods_type != 10) {
      util.navigateTo({
        url: '/pages/item/item?goods_id=' + goods_ids,
      })
    } else if (goods_type == 1) {
      var pin_group_id = this.data.pin_goods[goods_ids].pin_activity_id;
      util.navigateTo({
        url: '/pages/groupbuyitem/groupbuyitem?pin_group_id=' + pin_group_id,
      })
    } else if (goods_type == 5) {
      var seckill_id = e.currentTarget.dataset.seckill_id;
      util.navigateTo({
        url: '/pages/seckillitem/seckillitem?sk_id=' + seckill_id,
      })
    } else if (goods_type == 3) {
      var is_prd = e.currentTarget.dataset.is_prd;
      var bargain_id = e.currentTarget.dataset.bargain_id;
      if (is_prd == 1) {
        util.navigateTo({
          url: '/pages/bargainitem/bargainitem?bargain_id=' + bargain_id,
        })
      } else if (is_prd == 0) {
        var choose_list = {};
        choose_list.prd_id = e.currentTarget.dataset.prd_id;
        choose_list.goods_id = e.currentTarget.dataset.goods_id;
        choose_list.goods_price = e.currentTarget.dataset.goods_price;
        choose_list.bargain_id = bargain_id;
        choose_list.user_id = util.getCache('user_id');
        util.api("/api/wxapp/bargain/apply", function(res) {
          if (res.error == 0) {
            util.navigateTo({
              url: "/pages/bargaininfo/bargaininfo?record_id=" + res.content.record_id + "&bargain_money=" + res.content.bargain_money
            })
          } else {
            util.showModal('提示', res.content);
          }
        }, {

          choose_list: JSON.stringify(choose_list)
        })
      }
    } else if (goods_type == 10) {
      var presale_id = e.currentTarget.dataset.presale_id;
      util.navigateTo({
        url: '/pages/presaleitem/presaleitem?presale_id=' + presale_id,
      })
    }
  },

  // 筛选
  leftSlideIn: function() {
    var _this = this;
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(-502).step()
    _this.setData({
      animationLsi: animation.export(),
      show_hidden: true,
      isShow: true
    })
  },
  listTap(e) {
    let Index = e.currentTarget.dataset.parentindex; //获取点击的下标值
    this.data.list[Index].show = !this.data.list[Index].show || false; //变换其打开、关闭的状态
    this.setData({
      list: this.data.list
    });
  },
  // 点击确定按钮时
  to_search: function(e) {
    var open_id = util.getCache('openid');
    if (e) {
      form_id = e.detail.formId;
    }

    var _this = this;
    if (parseInt(max_price) < parseInt(min_price)) {
      util.showModal("提示", '最高价不能小于最低价');
      return false;
    } else {
      let animation = wx.createAnimation({
        duration: 800,
        timingFunction: 'ease'
      })
      animation.translateX(420).step()
      _this.setData({
        show_hidden: false,
        isShow: false,
        animationLsi: animation.export()
      })
    }
    this.btnSearch();
  },
  // 点击分类更多时
  categoriesTap: function() {
    var firstSort = [];
    for (var i in this.data.primaryfirst) {
      firstSort.push(this.data.primaryfirst[i].sort_id);
      if (this.data.primaryfirst[i].sort_id == sort_id) {
        var haindex = i;
        selectedFlag[haindex] = true;
        break;
      } else if (this.data.primaryfirst[i].child != []) {
        for (var j in this.data.primaryfirst[i].child) {
          parent_sort_id = this.data.primaryfirst[i].child[j].parent_id;
          if (this.data.primaryfirst[i].child[j].sort_id == sort_id) {
            var haindex = firstSort.indexOf(parent_sort_id)
            selectedFlag[haindex] = true;
            break;
          }
        }
      }
    }
    var _this = this;
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(-502).step()
    _this.setData({
      parent_sort_id: parent_sort_id,
      categoryLsi: animation.export(),
      selectedFlag: selectedFlag
    })
  },
  // 点击品牌更多
  brandMoreTap: function() {
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
  // 分类展开折叠选择
  changeToggle: function(e) {
    var index = e.currentTarget.dataset.index;
    if (selectedFlag[index]) {
      selectedFlag[index] = false;
    } else {
      selectedFlag[index] = true;
    }
    this.setData({
      selectedFlag: selectedFlag,
    })
  },
  // 遮罩
  not_play: function() {
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(420).step()
    this.setData({
      show_hidden: false,
      isShow: false,
      animationLsi: animation.export()
    })
  },
  no_category: function() {
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
  no_brand: function() {
    let animation = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation.translateX(402).step()
    this.setData({
      brandLsi: animation.export()
    })
  },
  // 点击更多分类的返回按钮时
  to_filter: function() {
    let animation2 = wx.createAnimation({
      duration: 800,
      timingFunction: 'ease'
    })
    animation2.translateX(402).step()
    this.setData({
      categoryLsi: animation2.export()
    })
  },
  max_priceInput: function(e) {
    if (!/^\d+$/.test(e.detail.value)) {
      max_price = e.detail.value = '';
    } else {
      max_price = e.detail.value;
    }
    this.setData({
      max_price: e.detail.value,
    })
  },
  min_priceInput: function(e) {
    if (!/^\d+$/.test(e.detail.value)) {
      min_price = e.detail.value = '';
    } else {
      min_price = e.detail.value;
    }
    this.setData({
      min_price: e.detail.value,
    })
  },
  checkedCategory: function(e) {
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
      goods_sort_name = '';
    } else {
      sort_id = e.currentTarget.dataset.id;
      goods_sort_name = e.currentTarget.dataset.sort;
    }
    this.setData({
      dataFlag: !e.currentTarget.dataset.flag,
      sort_id: sort_id,
      goods_sort_name: goods_sort_name
    });
  },
  activityGoods: function(e) {
    var data_id = e.currentTarget.dataset.id;
    var data_name = e.currentTarget.dataset.name;
    var data_index = e.currentTarget.dataset.index;
    if (data_name === '活动商品') {
      if (this.data.list[0].item[data_index].checked == 0) {
        this.data.list[0].item[data_index].checked = 1;
        activityGood.push(data_id);
      } else {
        activityGood.splice(activityGood.indexOf(data_id), 1);
        this.data.list[0].item[data_index].checked = 0;
      }
    } else {
      if (data_index > 2) {
        if (this.data.goodslabelAfter[data_index - 3].checked == 0) {
          this.data.goodslabelAfter[data_index - 3].checked = 1;
          Label.push(data_id);
        } else {
          Label.splice(Label.indexOf(data_id), 1);
          this.data.goodslabelAfter[data_index - 3].checked = 0;
        }
      } else {
        if (this.data.list[1].item[data_index].checked == 0) {
          this.data.list[1].item[data_index].checked = 1;
          Label.push(data_id);
        } else {
          Label.splice(Label.indexOf(data_id), 1);
          this.data.list[1].item[data_index].checked = 0;
        }
      }
    }
    this.setData({
      list: this.data.list,
      goodslabelAfter: this.data.goodslabelAfter
    })
  },
  activitybrand: function(e) {
    var brand_id;
    var _this = this;
    if (e.currentTarget.dataset.brand_id) {
      brand_id = e.currentTarget.dataset.brand_id;
    } else {
      brand_id = e.detail.brand_id;
    }
    getBrand([brand_id], _this)
  },
  reset_search: function(e) {
    var open_id = util.getCache('openid');
    if (e) {
      form_id = e.detail.formId;
    }

    max_price = '';
    min_price = '';
    sort_id = '';
    Label = [];
    activityGood = [];
    for (var i in this.data.list) {
      for (var j in this.data.list[i].item) {
        this.data.list[i].item[j].checked = '0';
      }
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
    for (var k in this.data.goodslabelAfter) {
      this.data.goodslabelAfter[k].checked = '0';
    }
    this.setData({
      max_price: '',
      min_price: '',
      sort_id: '',
      goods_sort_name: '',
      list: this.data.list,
      goodslabelAfter: this.data.goodslabelAfter,
      selectBrandName: selectBrandName,
      goodsBrands: this.data.goodsBrands ? this.data.goodsBrands : [],
      goodsBrands1: brandList ? brandList : []
    })
    util.api("/api/wxapp/common/saveformid", '', {
      form_id: form_id,
      open_id: open_id
    });
  },
  clear_category: function(e) {
    sort_id = '';
    goods_sort_name = '';
    for (var g in selectedFlag) {
      if (selectedFlag[g]) {
        selectedFlag[g] = false;
      }
    }
    this.setData({
      selectedFlag: selectedFlag,
      sort_id: '',
      goods_sort_name: ''
    })
  },
  // 阻止背景滚动穿透，勿删
  preventMove() {},
  hasRecommend(e) {
    if (e.detail.hasRecommend) {
      this.setData({
        null_marginTop: "60rpx",
        null_marginBottom: "36rpx",
        footer_color: "#f5f5f5"
      })
    }
  },
  bindAddCart(e) {
    var that = this;
    util.api('/api/wxapp/popup/goods/data', function(res) {
      console.log(res)
      if (res.error == 0) {
        that.setData({
          goodsData: res.content
        })
        console.log(that.showSpec(0, 1))
      } else {
        util.showModal('提示', res.message);
      }
    }, {
      goods_id: e.currentTarget.dataset.goods_id
    })
  }
})

function search_request(_this) {
  console.log(selectBrand)
  wx.showLoading({
    title: '加载中...',
    mask: true,
  })
  var pagesnames;
  if (coupon_sn != '' || alias_code != '') {
    pagesnames = '券购搜索'
  } else if (uc_resourse != "") {
    pagesnames = uc_resourse
  } else {
    pagesnames = "店铺商品"
  }
  util.api('/api/wxapp/search', function(res) {
    wx.hideLoading();
    _this.setData({
      is_new: res.content.is_new,
      goods: res.content.goods.data,
      last_page: res.content.goods.last_page,
      page: res.content.goods.current_page,
      searcontent: res.content,
      pin_goods: res.content.pin_group_goods,
      isNull: res.content.goods.data.length <= 0,
      page_name: pagesnames,
    });

    _this.data.list[1].item = res.content.params.good_labels;
    for (var i in res.content.params.sorts) {
      selectedFlag[i] = false;
    }
    for (var i in _this.data.list[0].item) {
      _this.data.list[0].item[i].checked = 0;
      for (var j in activityGood) {
        if (_this.data.list[0].item[i].id == activityGood[j]) {
          _this.data.list[0].item[i].checked = 1;
          break;
        }
      }
    }
    let search_flag = (!max_price && !min_price && !sort_id && Label.length == 0 &&
      activityGood.length == 0) ? 1 : 0;
    _this.setData({
      goodsCategory1: res.content.params.first_two_sorts,
      goodsBrands: res.content.params.brand_list,
      goodsBrands1: res.content.params.first_six_brand,
      primaryfirst: res.content.params.sorts,
      selectedFlag: selectedFlag,
      list: _this.data.list,
      goodslabelAfter: res.content.params.good_labels.slice(3),
      coupon_sn: coupon_sn,
      sort_goods_flag: res.content.params.sorts_goods_flag,
      search_flag: search_flag,
    })
    if (sort_id > 0) {
      _this.setData({
        goods_sort_name: res.content.params.goods_sort_name
      })
    }
    getBrand(selectBrand, _this, search_first++)
  }, {
    search: searchText,
    sort_name: _this.data.sort_name,
    sort_order: _this.data.sort_order,
    cat_id: _this.data.cat_id,
    card_id: card_id,
    sort_id: sort_id,
    cur_idx: cur_idx,
    cur_idx_goods: cur_idx_goods,
    group_idx: group_idx,
    page_id: page_id,
    max_price: max_price,
    min_price: min_price,
    labels: JSON.stringify(Label),
    activity_type: JSON.stringify(activityGood),
    coupon_sn: coupon_sn,
    alias_code: alias_code,
    is_rebate: is_rebate,
    form_id: form_id,
    open_id: util.getCache('openid'),
    is_from: _this.data.is_from,
    brands: JSON.stringify(selectBrand),
  });
}

function getBrand(brand_id, that, search = 0) {
  if (brand_id != '' || brand_id.length != 0) {
    let brandList = that.data.goodsBrands1;
    let goodsBrands = that.data.goodsBrands;
    brand_id.forEach(function(id) {
      brandList.forEach(function(el) {
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

function setParams(paramsStr, that) {
  console.log(paramsStr);
  var params = JSON.parse(paramsStr);
  console.log(params);
  searchText = params.search;
  card_id = params.card_id;
  sort_id = params.sort_id;
  cur_idx = params.cur_idx;
  group_idx = params.group_idx;
  page_id = params.page_id;
  max_price = params.max_price;
  min_price = params.min_price;
  coupon_sn = params.coupon_sn;
  alias_code = params.alias_code;
  is_rebate = params.is_rebate;
  form_id = params.form_id;
  Label = params.labels ? params.labels.split(',') : [];
  activityGood = params.activity_type ? params.activity_type.split(',') : [];
  selectBrand = params.brands ? params.brands.split(',') : [];
  console.log('11111');
  console.log(activityGood);
  console.log(selectBrand);
  that.setData({
    sort_name: params.sort_name,
    sort_order: params.sort_order,
    cat_id: params.cat_id,
    is_from: params.is_form,
    min_price: min_price,
    max_price: max_price
  });
}