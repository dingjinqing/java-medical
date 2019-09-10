var util = require("../../../utils/util.js")
var spec_mixin = require("../popup/spec/spec.js")
global.wxComponent({
  mixins: [spec_mixin],
  properties: {
    pageName: {
      type: String, //推荐商品类型 
      value: 'cart' //cart,bargainitem,groupbuyitem,new_search,orderlist,payment,search,item
    },
    page:{
      type:Number,
      value:1
    },
    last_page:{
      type:Number,
      value:1,
    },
  },

  methods: {
    init(){
      let that = this;
      let win_h = wx.getSystemInfoSync().windowHeight;
      let navigation_h = wx.getMenuButtonBoundingClientRect().bottom + 8;
      util.api('/api/wxapp/goods/recommend/new', function (res) {
        if (res.error == 0) {
          res.content.win_h = win_h;
          res.content.navigation_h = navigation_h;
          that.setData(res.content);
          that.data.last_page = res.content.recommend_goods.last_page;
          that.triggerEvent('reconmmendevent', { 
              hasRecommend: res.content.recommend_goods.length > 0 
            })
        }
      }, { page_name: this.data.pageName, page_no: this.data.page})
    },
    bindToItem: function (e) {
      var goods_types = e.currentTarget.dataset.goods_type;
      if (goods_types == 1) {
        var pin_group_id = e.currentTarget.dataset.pin_id;
        util.navigateTo({
          url: '/pages/groupbuyitem/groupbuyitem?pin_group_id=' + pin_group_id,
        })
      } else if (goods_types == 5) {
        var seckill_id = e.currentTarget.dataset.seckill_id;
        util.navigateTo({
          url: '/pages/seckillitem/seckillitem?sk_id=' + seckill_id,
        })
      } else if (goods_types == 3) {
        var bargin_ids = e.currentTarget.dataset.bargin_id;
        util.navigateTo({
          url: '/pages/bargainitem/bargainitem?bargain_id=' + bargin_ids,
        })
      } else if (goods_types == 10) {
        var presale_id = e.currentTarget.dataset.presale_id;
        util.navigateTo({
          url: '/pages/presaleitem/presaleitem?presale_id=' + presale_id,
        })
      } else {
        var goods_ids = e.currentTarget.dataset.goods_id;
        util.navigateTo({
          url: '/pages/item/item?goods_id=' + goods_ids,
        })
      }
    },
    loadData:function(){
      var that = this;
      if (that.data.page >= that.data.last_page) return;
      wx.showLoading({ title: '加载中...', mask: true })
      this.data.page = this.data.page + 1;
      util.api('/api/wxapp/goods/recommend/new', function (res) {
        wx.hideLoading();
        if (res.error == 0) {
          that.data.last_page = res.content.recommend_goods.last_page;
          that.data.recommend_goods.data = that.data.recommend_goods.data.concat(res.content.recommend_goods.data);
          that.setData(that.data);
          that.triggerEvent('reconmmendevent', {
            hasRecommend: res.content.recommend_goods.length > 0
          })
        }
      }, { page_name: this.data.pageName, page_no: this.data.page, recommend_goods_ids: this.data.recommend_goods_ids })
    }
  },
  attached() {
    this.init()
  },
  lifetimes: {
    attached() {
      this.init()
    },
  }
})