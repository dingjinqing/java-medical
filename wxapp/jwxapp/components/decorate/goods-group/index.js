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
      console.log(rect)
      _this._nav_height = rect.height;
    })
  },
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'group+++++++++++++')
      if (!newVal.goodsListData) return
      // 初始数据处理
      this.handleToInitData(newVal)
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
    handleToInitData (initData) {
      console.log(initData, '++++++++++++++++++++++')
      // 分组名称处理
      if (initData.position_style == 0 && initData.group_display == 1) {
        initData.sort_group_arr.unshift({
          'group_name': '全部',
          "sort_id": null,
          "sort_type": -1,
          "group_goods_id": null,
          "is_all": null
        })
      }
      initData.navlen = initData.sort_group_arr.length;
      initData.group_nav_index = 0;
      console.log(initData.goodsListData)
      if (initData.goodsListData.length > 6) {
        initData.more_flag = 1
      } else {
        initData.more_flag = 0
      }

      // 处理label标签、从商品分组引入特殊处理
      this.handleToLabel(initData.goodsListData)
      // 处理活动
      this.handleToGoodsActivities(initData.goodsListData)
    },
    handleToLabel (goodsListData) {
      goodsListData.forEach((item, index) => {
        item.isGroup = 1
      })
    },
    bindMenuClick (e) {
      var d = this.eventData(e);
      console.log(d, d.index)

      var _this = this;
      var m = this.data.m;
      if (!m.group_nav_index) {
        m.group_nav_index = 0
      }
      console.log(m)
      let sortIds = []
      let brandIds = []
      let labelIds = []
      let goodsIds = []
      console.log(m.sort_group_arr)
      let obj = m.sort_group_arr[m.group_nav_index]
      console.log(obj)
      if (obj.sort_type == -1) {
        m.sort_group_arr.forEach((item, index) => {
          console.log(item)
          if (item.sort_type != -1) {
            switch (item.sort_type) {
              case '':
                sortIds.push(item.sort_id)
                break
              case '1':
                labelIds.push(item.sort_id)
                break
              case '2':
                brandIds.push(item.sort_id)
                break
            }
            if (item.is_all == 2) {
              if (item.group_goods_id && !isNaN(Number(item.group_goods_id))) {
                goodsIds.push(Number(item.group_goods_id))
              }
            }
          }
        })
      } else {
        switch (obj.sort_type) {
          case '':
            sortIds.push(obj.sort_id)
            break
          case '1':
            labelIds.push(obj.sort_id)
            break
          case '2':
            brandIds.push(obj.sort_id)
            break
        }
        if (obj.is_all == 2) {
          if (obj.group_goods_id && !isNaN(Number(obj.group_goods_id))) {
            goodsIds.push(Number(obj.group_goods_id))
          }
        }
      }
      console.log(sortIds, brandIds, labelIds, goodsIds)
      if (d.click == 1) {
        util.jumpLink(`/pages1/search/search${util.getUrlParams({
          pageFrom: 0,
          outerPageParam: JSON.stringify({
            sortIds,
            brandIds,
            labelIds,
            goodsIds
          })
        })}`);
      } else {
        m.group_nav_index = d.index;
        m.page_num = 1;
        let arr = []
        if (m.sort_group_arr[d.index].group_name === '全部') {
          m.sort_group_arr.forEach((item, index) => {
            let obj = {
              "sort_id": m.sort_group_arr[index].sort_id,
              "sort_type": m.sort_group_arr[index].sort_type,
              "group_goods_id": m.sort_group_arr[index].group_goods_id,
              "is_all": m.sort_group_arr[index].is_all
            }
            arr.push(obj)
          })

        } else {
          arr = [{
            "sort_id": m.sort_group_arr[d.index].sort_id,
            "sort_type": m.sort_group_arr[d.index].sort_type,
            "group_goods_id": m.sort_group_arr[d.index].group_goods_id,
            "is_all": m.sort_group_arr[d.index].is_all
          }]
        }
        util.api('/api/wxapp/goods/group/list', function (res) {
          console.log(res)
          if (res.error == 0) {
            var data = res.content;
            _this.handleToLabel(data)
            _this.handleToGoodsActivities(data)
            m.goodsListData = data;
            m.more_flag = data.more_flag;
            if (m.goodsListData.length > 6) {
              m.more_flag = 1
            } else {
              m.more_flag = 0
            }
            _this.$set();
          }
        }, {
          sort_group_arr: arr
        });
      }
    },
    onPageScroll (e) {
      var _this = this;
      var m = this.data.m;
      if (m.menu_style == 1) {
        console.log(m.cur_idx)
        this.getRect(`#c_${m.cur_idx}`).then(function (rect) {
          console.log(rect)
          _this._nav_height = _this._nav_height || 0;
          var top = _this.getFixeTop();
          console.log(rect.top, top, rect.bottom, top, _this._nav_height)
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
      console.log(m.fixed)
    }
  }
});