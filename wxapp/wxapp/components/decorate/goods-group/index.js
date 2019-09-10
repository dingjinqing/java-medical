var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");
var spec_mixin = require("../../item/popup/spec/spec.js")
global.wxComponent({
  mixins: [base, activity_goods, spec_mixin],
  data: {
    height: 0,
  },
  ready() {
    var height = 0
    if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
      height = wx.getMenuButtonBoundingClientRect().bottom
    } else {
      wx.getSystemInfo({
        success: (res) => {
          height = res.statusBarHeight * 3
        }
      })
    }
    this.setData({
      height: height
    })
  },
  methods: {
    onPropChange(newVal, oldVal, changedPath) {
      newVal.group_nav_index = 0;
      newVal.navlen = newVal.group_names.length;
      newVal.page_num = 1;
      newVal.fixed = false;
      newVal.scr_height = wx.getSystemInfoSync().windowHeight;
      if (newVal.position_style == 1) {
        newVal.show_style = 6;
      } else if (newVal.shop_style != 4) {
        newVal.show_style = parseInt(newVal.shop_style) - 1;
      } else {
        newVal.show_style = newVal.shop_style;
      }
    },
    bindMenuClick(e) {
      var d = this.eventData(e);
      var _this = this;
      var m = this.data.m;
      if (d.click == 1) {
        util.jumpLink('/pages/searchs/search?cur_idx=' + m.idx + '&group_idx=' + m.group_nav_index + '&page_id=' + m.page_id);
      } else {
        m.group_nav_index = d.index;
        m.page_num = 1;
        util.api('/api/wxapp/get/group/goods', function(res) {
          if (res.error == 0) {
            var data = res.content;
            m.first_group_goods = data.goods_list;
            m.pin_group_goods = data.pin_group_goods;
            m.more_flag = data.more_flag;
            _this.$set();
          }
        }, {
          cur_idx: m.idx,
          group_idx: m.group_nav_index,
          page: m.page_id,
          page_num: m.page_num,
        });
      }
    },
    onPageScroll(e) {
      var _this = this;
      var m = this.data.m;
      var height = '';
      this.getRect("#" + m.idx).then(function(rect) {
        if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
          height = wx.getMenuButtonBoundingClientRect().bottom + 8
        } else {
          wx.getSystemInfo({
            success: (res) => {
              height = res.statusBarHeight * 3
            }
          })
        }
        if (rect.top <= height && rect.top > -rect.height) {
          if (m.fixed == false) {
            m.fixed = true;
            _this.$set();
          }
        } else {
          if (m.fixed == true) {
            m.fixed = false;
            _this.$set();
          }
        }
      });
    }
  }
});