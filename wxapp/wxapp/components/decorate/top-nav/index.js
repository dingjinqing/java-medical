var util = require("../../../utils/util.js")
var base = require("../mixins/base.js");
global.wxComponent({
  mixins: [base],
  data: {
    page_index:0,
    pageData: {},
    click_index:0,
  },
  methods: {  
    bindMenuClick(e) {
      var _this = this;
      var page_id = e.currentTarget.dataset.page_id;
      var page_index = e.currentTarget.dataset.index;
      _this.setData({
        page_index:page_index,
      })
      _this.requestDecoratePageData(page_id, true);
    },
    requestDecoratePageData(page_id, loading) {
      var _this = this;
      var page_id = page_id || 0;
      loading = loading || false;
      console.log(page_id)
      util.api('/api/wxapp/index', function (d) {
        console.log(d.content)
        _this.processModuleData(d.content)
      }, { page: page_id }, null, loading);
    },
    onPropChange(newVal, oldVal, changedPath) {
      console.log(newVal)
      var that = this;
      this.requestDecoratePageData(newVal.nav_group_arr[that.data.page_index].page_id)
    },
    processModuleData(page_content) {
      if (!page_content) return;
      var pageInfo = page_content.page_info || null;
      var pageData = [];
      var color = "#f5f5f5";
      var pageCfg = pageInfo.page_cfg;
      if (pageCfg) {
        if (pageCfg.bg_types == 0) {
          color = "background:" + pageCfg.page_bg_color;
        } else if (pageCfg.bg_types == 1) {
          color = "background:url(" + pageCfg.page_bg_image + ") repeat;background-size:100% auto";
        }
      }
      if (!pageInfo) return;
      var is_phone_block = 0;
      for (var idx in pageInfo) {
        module = pageInfo[idx];
        var componentName = this._convertComponentName(module['module_name']);
        if (!componentName) continue;

        if (module['module_name'] == 'm_phone' && module.show_type == 1) {
          is_phone_block = 1;
          module.is_float = true;
        }
        if (module['module_name'] == 'm_service') {
          module.is_float = true;
        }
        if (module['module_name'] == 'm_card'){
          if (module.status == 1 && module.hidden_card == 1) continue;
        }
        module.elapse_secs = 0;
        module.page_id = page_content.page_id;
        module.imgUrl = util.getImageUrl('');
        module.imageUrl = util.getImageUrl('');
        module['component_name'] = componentName;
        module['is_first_touch'] = 1;
        module['idx'] = idx;
        module['margin_bot'] = pageInfo.page_cfg.show_margin == 1 ? 0 : pageInfo.page_cfg.margin_val;
        module['main_setting'] = page_content.main_setting;
        module['page_cfg'] = pageInfo.page_cfg;
        pageData.push(module);
      }
      var _this = this;
      this.elapse_secs = 0;
      this.createTimer("interval", "decorate_elapse", function() {
        _this.elapse_secs++;
      });
      this._pageData = pageData;
      this._loadIndex = 0;
      this._loadFloat = false;
      this._loaded = {};
      this.setData({
        is_phone_block: is_phone_block,
        color: color,
        pageData: []
      });
      this.loadMoreData();
      this.startLoadingMoreTimer();
    },
    _timerConvertModule(m) {
      if (!m) return m;
      if (m.component_name == 'v-bargain' || m.component_name == 'v-pinlottery') {
        m.elapse_secs = this.elapse_secs
      }
      return m;
    },
    loadMoreData() {
      var data = {};
      var l = this._loaded = this._loaded || {};
      var _loaded_len = Object.keys(this._loaded).length;
      var d = this._pageData;
      var number = 0;
      var count = Math.max(_loaded_len, d.length);
      var loadMore = false;
      for (var i = this._loadIndex; i < count; i++) {
        var key = "pageData[" + i + "]";
        if (!l[i]) {
          // 添加悬浮组件 和 其他固定数量模块
          if (!d[i].is_float) {
            if (number < 2) {
              l[i] = data[key] = this._timerConvertModule(d[i]);
              loadMore = true;
              number++;
              this._loadIndex = i + 1;
              if (number == 2 && this._loadFloat) break;
            }
          } else {
            l[i] = data[key] = this._timerConvertModule(d[i]);
            loadMore = true;
          }
        } else {
          // 更新数据不同的模块
          if (!d[i] || l[i] && (JSON.stringify(d[i]) != JSON.stringify(l[i]))) {
            l[i] = data[key] = this._timerConvertModule(d[i]) || {};
          }
        }
      }
      this._loadFloat = true;
      this._loadedOk = !loadMore;
      if (this._loadedOk) this._loaded = this._pageData;
      if (Object.keys(data).length > 0) {
        console.log("loadMore:", data);
        var _this = this;
        this.setData(data, function() {
          _this.detectLoadingMore();
        });
      }
    },
    // 逐步加载其他模块，解决iphone滚动事件问题。
    startLoadingMoreTimer() {
      if (!this._op_system) this._op_system = wx.getSystemInfoSync().system;
      if (this._op_system.indexOf("iOS") == -1) return;
      var _this = this;
      this.createTimer("interval", "load_more", function () {
        if (_this._loadedOk) return;
        _this.loadMoreData();
      }, 1000);
    },
    detectLoadingMore() {
      if (this._gettingRect) return;
      if (this._loadedOk) return;
      if (!this._windowHeight) this._windowHeight = wx.getSystemInfoSync().windowHeight;
      var _this = this;
      this._gettingRect = true;
      this.getRect("#decorate").then(function(rect) {
        if (rect.height < _this._windowHeight || rect.bottom < _this._windowHeight * 2) {
          _this.loadMoreData();
        }
        _this._gettingRect = false;
      });
    },
    onGetCardSuccess(card) {
      // if (card.card_type != 2) return ;
      var cards = this.selectAllComponents(".v-card");
      for (var i in cards) {
        var m = cards[i].data.m;
        if (m.card_type == 2 && card.card_id != m.card_id) {
          m.status = -1;
          cards[i].setData({
            m: m
          })
        }
      }
    },
    // 上层调用
    onPageScroll(e) {
      this.detectLoadingMore();
    },
    _convertComponentName(tmplateName) {
      var modules = {
        "m_scroll_image": "v-carousel",
        "m_goods_search": "v-search",
        "m_image_guide": "v-imgnav",
        "m_dashed_line": "v-line",
        "m_blank": "v-space",
        "m_single_image": "v-imgone", // 兼容，已弃用
        "m_magic_cube": "v-window",
        "m_double_image": "v-imgtwo", // 兼容，已弃用
        "m_multi_image": "v-multiplot", // 兼容，已弃用
        "m_title": "v-title",
        'm_goods': "v-product",
        "m_shop": "v-shop",
        "m_text": "v-text",
        "m_image_small": "v-advertise", // 兼容，已弃用
        "m_phone": "v-phone",
        "m_service": "v-service",
        "m_card": "v-card",
        "m_coupon": "v-coupon",
        "m_map": "v-map",
        "m_bargain": "v-bargain",
        "m_video": "v-video",
        "m_integral": "v-integral",
        "m_hot_area": "v-hot-area",
        "m_seckill": "v-seckill",
        "m_pin_integration": "v-pinintegration",
        "m_group_draw": "v-pinlottery",
        "m_image_adver": "v-advimg",
        "m_goods_group": "v-goods-group",
        "m_text_image": "v-text-image",
        "m_official_accounts": "v-official-accounts",
        "m_shop_announce": "v-shop-announce",
        "m_rich_text": "v-rich-text"
      };
      return modules[tmplateName];
    }
  },

})