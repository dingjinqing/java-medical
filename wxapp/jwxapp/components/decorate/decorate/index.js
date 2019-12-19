// components/decorate/decorate/index.js
var util = require("../../../utils/util.js")
global.wxComponent({
  externalClasses: [],
  /**
   * 页面的初始数据
   */
  data: {
    pageData: {}
  },
  properties: {
    // 模块数据
    page_content: {
      type: Object,
      value: {},
      observer (newVal, oldVal) {
        if (newVal) this.processModuleData(newVal);
      }
    }
  },
  methods: {
    // 处理模块渲染数据
    processModuleData (page_content) {
      if (!page_content) return;

      console.log(page_content, '111', module)
      let pageInfo = page_content.page_info || null;
      console.log(pageInfo)
      this.scene = page_content.scene || 0
      this.page_id = page_content.page_id;
      if (!pageInfo) return;
      let pageData = [];
      for (var idx in pageInfo) {
        module = pageInfo[idx];
        console.log(module)
        // 转化模块名称
        let componentName = this._convertComponentName(module['module_name']);
        if (!componentName) continue;
        // 模块名称
        module['component_name'] = componentName
        module.elapse_secs = 0;
        module['main_setting'] = page_content.main_setting;
        module['is_first_touch'] = 1;
        // 当前图片域名
        module['imageUrl'] = 'http://jmpdevimg.weipubao.cn'
        console.log(pageInfo)
        // 模块间距字段  
        module['margin_bot'] = pageInfo.page_cfg && pageInfo.page_cfg.show_margin == 1 ? pageInfo.page_cfg.margin_val : 0;
        // 是否需要请求数据
        module['need_request'] = module['need_request'] || false;
        pageData.push(module);
      }

      var _this = this;
      this.elapse_secs = 0;
      this.createTimer("interval", "decorate_elapse", function () {
        _this.elapse_secs++;
      });

      console.log(pageData)
      this._loadIndex = 0;   // 加载起点
      this._loadFloat = false;
      this._pageData = pageData;
      // 加载详细信息
      this.loadMoreData();

      this.startLoadingMoreTimer();
    },
    // 加载详细信息
    loadMoreData () {
      console.log(this._loaded)
      var data = {}
      var l = this._loaded = this._loaded || {}
      console.log(l)
      var _loaded_len = Object.keys(this._loaded).length;
      var d = this._pageData
      var number = 0;
      var count = Math.max(_loaded_len, d.length);
      var loadMore = false;
      var delayed = {};
      console.log(this._loadIndex, count)
      for (var i = this._loadIndex; i < count; i++) {
        console.log('循环次数', l, i)
        var key = "pageData[" + i + "]";
        if (!l[i]) {
          console.log('测试', d[i])
          // 添加悬浮组件 和 其他固定数量模块
          if (!d[i].is_float) {
            if (number < 2) {
              l[i] = data[key] = this._timerConvertModule(d[i]);
              console.log(l[i])
              if (l[i].need_request) delayed[i] = `c_${l[i].cur_idx}`;
              // delayed[i] = `c_${l[i].cur_idx}`
              loadMore = true;
              number++;
              this._loadIndex = i + 1;
              console.log(this._loadIndex)
              if (number == 2 && this._loadFloat) break;
            }
          } else {
            console.log('触发')
            l[i] = data[key] = this._timerConvertModule(d[i]);
            if (l[i].need_request) delayed[i] = l[i].cur_idx;
            loadMore = true;
          }
        } else {  // 则更新数据不同的模块
          console.log('l有值')
          if (!d[i] || l[i] && (JSON.stringify(d[i]) != JSON.stringify(l[i]))) {
            l[i] = data[key] = this._timerConvertModule(d[i]) || {};
            if (l[i].need_request) delayed[i] = l[i].cur_idx;
            console.log(l[i])
            // delayed[i] = `c_${l[i].cur_idx}`
          }
        }
      }
      console.log(delayed)
      this._loadFloat = true;
      this._loadedOk = !loadMore;
      if (this._loadedOk) this._loaded = this._pageData;
      console.log(data)
      if (Object.keys(data).length > 0) {
        console.log("loadMore:", data);
        var _this = this;
        this.setData(data, function () {
          _this.detectLoadingMore();
        });
        // 延迟加载
        for (var i in delayed) {
          this.requestPageModule(delayed[i]);
        }
      }
      // 请求数据
      // for (var i in delayed) {
      //   this.requestPageModule(delayed[i]);
      // }
    },
    // 请求装修模块详细数据
    requestPageModule (idx) {
      var _this = this;
      util.api('/api/wxapp/page/module', function (d) {
        console.log(d, _this.page_id)
        _this.refreshModule(idx, d.content);
      }, {
        page: _this.page_id,
        idx: idx,
        scene: _this.scene || 0
      });
    },
    // 更新模块数据
    refreshModule (idx, moduleContent) {
      var data = {};
      let cur_idx = Number(idx.split('_')[1])
      console.log(this._pageData, cur_idx)
      for (var i in this._pageData) {
        console.log(this._pageData[i], cur_idx, moduleContent, '合并数据')
        if (this._pageData[i].cur_idx == cur_idx) {
          console.log(this._pageData[i], moduleContent)
          this._pageData[i] = Object.assign({}, this._pageData[i], moduleContent);  // 数据合并
          console.log(this._pageData[i])
          this._pageData[i]['need_request'] = false;
          var key = "pageData[" + i + "]";
          data[key] = this._timerConvertModule(this._pageData[i]) || {};
          break;
        }
      }
      console.log(data)
      if (Object.keys(data).length > 0) {
        console.log("refreshModule:", data);
        this.setData(data);
      }

    },
    _timerConvertModule (m) {
      if (!m) return m;
      if (m.component_name == 'v-bargain' || m.component_name == 'v-pinlottery') {
        m.elapse_secs = this.elapse_secs
      }
      return m;
    },
    detectLoadingMore () {
      console.log('触发', this._gettingRect, this._loadedOk, this._windowHeight)
      if (this._gettingRect) return;
      if (this._loadedOk) return;
      if (!this._windowHeight) this._windowHeight = wx.getSystemInfoSync().windowHeight;
      console.log('发现加载')
      var _this = this;
      this._gettingRect = true;
      this.getRect("#decorate").then(function (rect) {
        console.log(rect)
        if (rect.height < _this._windowHeight || rect.bottom < _this._windowHeight * 1.5) {
          console.log('detectLoadingMore 触发')
          _this.loadMoreData();
        }
        _this._gettingRect = false;
      });
    },
    // 逐步加载其他模块，解决iphone滚动事件问题。
    startLoadingMoreTimer () {
      if (!this._op_system) this._op_system = wx.getSystemInfoSync().system;
      if (this._op_system.indexOf("iOS") == -1) return;
      var _this = this;
      this.createTimer("interval", "load_more", function () {
        if (_this._loadedOk) return;
        console.log('startLoadingMoreTimer 触发')
        _this.loadMoreData();
      }, 1000);
    },
    // 会员卡  
    onGetCardSuccess (card) {

    },
    //  模块名称汇总
    _convertComponentName (tmplateName) {
      let modules = {
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
        "m_rich_text": "v-rich-text",
        "m_topnav": "v-top-nav"
      };
      return modules[tmplateName];
    }
  }

})