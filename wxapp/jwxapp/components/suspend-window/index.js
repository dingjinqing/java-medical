// components/suspend-window/index.js
var util = require("../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   * "v-suspend-window": "/components/suspend-window/index"
   */
  properties: {
    page_id: {
      type: Number,
      value: 0,
      observer (value) {
        if (value != 0) {
          let that = this;
          util.api("/api/wxapp/suspend", function (res) {
            if (res.error == 0) {
              console.log(res, '悬浮窗');
              var gd = res.content;
              if (!gd) return
              var children_arr = gd.children_arr;
              that.transNavItem(0, gd.main_flag, children_arr)
              that.setData({
                gd: gd,
                children_arr: children_arr,
              })
            }
          }, {
            page: value,
          })
        }
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    if_active: 0,
    scrollTop: 0,
    if_show: 1,
  },
  /**
   * 组件的方法列表
   */
  methods: {
    // 点击主图标切换
    mainFlag (e) {
      var that = this;
      var flag = e.currentTarget.dataset.flag;
      var main_flag = e.currentTarget.dataset.main;
      console.log(flag)
      if (flag == 1) {
        that.transNavItem(flag, main_flag, that.data.children_arr)
        that.setData({
          if_active: 1
        })
      } else {
        that.transNavItem(flag, main_flag, that.data.children_arr)
        that.setData({
          if_active: 0
        })
      }
    },
    transNavItem (if_active, main_flag, children_arr) {
      var that = this;
      var children_arr_ordinary = [];
      var children_arr_independ = [];
      var reverse_ordinary = [];
      console.log(children_arr)
      if (children_arr) {
        for (var i = 0; i < children_arr.length; i++) {
          if (children_arr[i].own_flag != 1 && main_flag == 1) { // 不独立于主图显示并且开启了主图标
            children_arr_ordinary.push(children_arr[i]);
          } else {
            children_arr_independ.push(children_arr[i]); // 独立主图标
          }
        }
      }
      // children_arr_ordinary.reverse();
      children_arr_ordinary.forEach((item, index) => {
        if (if_active == 1 && children_arr.length <= 3) { // 展开
          children_arr_ordinary[index].translate = (index + 1) * 120;
        } else {
          children_arr_ordinary[index].translate = 0;
        }
      })
      children_arr_independ.forEach((item, index) => {
        if (if_active == 1 && children_arr.length <= 3) { // 展开
          children_arr_independ[index].translate = (index + 1 + children_arr_ordinary.length) * 120;
        } else {
          children_arr_independ[index].translate = 0;
        }
      })
      if (children_arr_independ.length > 0) {
        for (var j = 0; j < children_arr_independ.length; j++) {
          if (main_flag != 0 && (if_active == 0 || children_arr.length > 3)) { // 开启了主图标显示并且（收缩或者图标总数大于3）
            children_arr_independ[j].translate = (j + 1) * 120;
          }
          if (main_flag == 0) { // 主图表未开启
            children_arr_independ[j].translate = j * 120;
          }
        }
      }


      reverse_ordinary = children_arr_ordinary; // 不是独立主图标的项集合

      // children_arr_ordinary.reverse()
      console.log(children_arr_ordinary, children_arr_independ)
      that.setData({
        children_arr_ordinary: children_arr_ordinary,
        children_arr_independ: children_arr_independ,
        reverse_ordinary: reverse_ordinary
      })
    },
    bindToCart () {
      util.jumpLink('pages/cart/cart', 'navigateTo')
    },
    close_cover (e) {
      var that = this;
      that.setData({
        if_active: 0
      })
    },
    bindPhoneCall (e) {
      var mobiles = e.currentTarget.dataset.mobiles;
      wx.makePhoneCall({
        phoneNumber: mobiles
      })
    },
    bindToLink (e) {
      var link = e.currentTarget.dataset.custom;
      util.jumpLink(link);
    },

    bindGoTop (e) {
      // 一键回到顶部
      if (wx.pageScrollTo) {
        wx.pageScrollTo({
          scrollTop: 0
        })
      } else {
        util.alert('当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。')
      }
    },

    onPageScroll (ev) {
      var _this = this;
      var if_show;
      if (this.data.gd && this.data.gd.suspend_pattern == 2) {
        if (ev.scrollTop <= 0) {
          ev.scrollTop = 0;
        }
        if (ev.scrollTop > this.data.scrollTop || ev.scrollTop == wx.getSystemInfoSync().windowHeight) {
          if (this.data.if_show != 0) {
            _this.setData({
              if_show: 0
            })
          }
        } else {
          if (this.data.if_show != 1) {
            _this.setData({
              if_show: 1
            })
          }
        }
        setTimeout(function () {
          _this.setData({
            scrollTop: ev.scrollTop
          })
        }, 0)

      }

    },

  }
})