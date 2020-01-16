var base = require("../mixins/base.js");
var util = require("../../../utils/util.js")
var activity_goods = require("../common/activity_goods.js");
// var spec_mixin = require("../../item/popup/spec/spec.js");
var fix_top = require("../../common/fix_top.js");
global.wxComponent({
  mixins: [base, activity_goods, fix_top],
  ready () {
    var _this = this;
    this.getRect('.content_scroll_view').then(function (rect) {
      _this._nav_height = rect.height;
    })
  },
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'group+++++++++++++')
      // newVal.group_nav_index = 0;
      // newVal.navlen = newVal.group_names.length;
      // newVal.page_num = 1;
      // newVal.fixed = false;
      // newVal.scr_height = wx.getSystemInfoSync().windowHeight;
      // if (newVal.position_style == 1) {
      //   newVal.show_style = 6;
      // } else if (newVal.shop_style != 4) {
      //   newVal.show_style = parseInt(newVal.shop_style) - 1;
      // } else {
      //   newVal.show_style = newVal.shop_style;
      // }
    },
    // bindMenuClick(e) {
    //   var d = this.eventData(e);
    //   var _this = this;
    //   var m = this.data.m;
    //   if (d.click == 1) {
    //     util.jumpLink('/pages/searchs/search?cur_idx=' + m.idx + '&group_idx=' + m.group_nav_index + '&page_id=' + m.page_id);
    //   } else {
    //     m.group_nav_index = d.index;
    //     m.page_num = 1;
    //     util.api('/api/wxapp/get/group/goods', function(res) {
    //       if (res.error == 0) {
    //         var data = res.content;
    //         m.first_group_goods = data.goods_list;
    //         m.pin_group_goods = data.pin_group_goods;
    //         m.more_flag = data.more_flag;
    //         _this.$set();
    //       }
    //     }, {
    //       cur_idx: m.idx,
    //       group_idx: m.group_nav_index,
    //       page: m.page_id,
    //       page_num: m.page_num,
    //     });
    //   }
    // },
    onPageScroll (e) {
      var _this = this;
      var m = this.data.m;
      if (m.menu_style == 1) {
        this.getRect(`#${m.cur_idx}`).then(function (rect) {
          _this._nav_height = _this._nav_height || 0;
          var top = _this.getFixeTop();
          if (!m.fixed && (rect.top <= top && rect.bottom > top + _this._nav_height)) {
            m.fixed = true;
            m.fix_height = rect.height;
            m.top = top;
            _this.startFixed(_this.nav_height);
            _this.$set();
          } else {
            if (m.fixed && (rect.top > top || rect.bottom <= top + _this._nav_height)) {
              m.fixed = false;
              _this.stopFixed();
              _this.$set();
            }
          }
        });
      }
    }
  }
});