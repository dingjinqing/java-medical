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
      observer(newVal, oldVal) {
        console.log(newVal, '222')
        if (newVal) this.processModuleData(newVal);
      }
    }
  },
  methods: {
    // 处理模块渲染数据
    processModuleData(page_content) {
      if (!page_content) return;
      console.log(page_content, '111', module)
      let pageInfo = page_content.page_info || null;
      console.log(pageInfo)
      if (!pageInfo) return;
      let pageData = [];
      for (var idx in pageInfo) {
        module = pageInfo[idx];
        // 转化模块名称
        let componentName = this._convertComponentName(module['module_name']);
        if (!componentName) continue;
        module['component_name'] = componentName
        module['margin_bot'] = pageInfo.page_cfg.show_margin == 1 ? 0 : pageInfo.page_cfg.margin_val;
        module['main_setting'] = page_content.main_setting;

        module['imageUrl'] = 'http://jmpdevimg.weipubao.cn'
        pageData.push(module);
      }


      console.log(pageData)

      this.setData({
        pageData: pageData
      })
    },
    // 会员卡  card 模块点击
    onGetCardSuccess(card) {

    },
    //  模块名称汇总
    _convertComponentName(tmplateName) {
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