// components/item/purchase_record/index.js
var purchase;
var p_count = 0;
var p_total = 0;
var pl;
var purchase_name;
var purchase_img;
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    goodsRecords: {
      type: Array,
      value: [],
      observer (val) {
        console.log(val)
        // 计算距离顶部的高度
        this.handleToCalculationTop(val)
      }
    }
  },
  data: {
    top_nav: 0,
    purchase: '',
    purchase_name: '',
    purchase_img: '',
    pl: '',
    show_purchase: true,
    p_count: 0,
    p_total: 0
  },
  /**
   * 组件的方法列表
   */
  methods: {
    handleToCalculationTop (val) {
      console.log(val)
      let that = this
      if (!val.length) return
      var top_nav = 0
      if (typeof wx.getMenuButtonBoundingClientRect === 'function') {
        top_nav = wx.getMenuButtonBoundingClientRect().bottom
      } else {
        wx.getSystemInfo({
          success: (res) => {
            top_nav = res.statusBarHeight * 3
          }
        })
      }
      purchase = val;
      purchase_name = purchase[0].username;
      purchase_img = purchase[0].userAvatar
      that.setData({
        purchase_name: purchase_name,
        purchase_img: purchase_img,
        top_nav: top_nav
      })
      pl = purchase.length;
      if (pl > 0) {
        console.log('begin');
        that.slideupshow(that, 1, 0, 0)
      }
    },
    slideupshow (that, opacity, delay, p_total) {
      console.log('kaishi');
      let params = '';
      let animation = wx.createAnimation({
        duration: 2000,
        timingFunction: 'ease',
        delay: delay
      });
      animation.translateY(0).step();
      animation.translateY(-120).step();
      params = animation.export();
      setTimeout(function () {
        that.setData({
          animation: params
        })
      }, 500)

    },
    backStart () {
      p_count++;
      if (p_count < 2) return false;
      p_total++;
      console.log('第一次动画结束！');
      p_count = 0;
      if (p_total >= pl) {
        this.setData({
          show_purchase: false,
        })
      } else {
        let parms1 = '';
        let animation1 = wx.createAnimation({
          duration: 0,
        });
        animation1.translateY(30).step();
        parms1 = animation1.export();
        purchase_name = purchase[p_total].username;
        purchase_img = purchase[p_total].userAvatar
        this.setData({
          animation: parms1,
          purchase_name: purchase_name,
          purchase_img: purchase_img
        })
        setTimeout(function () {
          this.slideupshow(this, 1, 0, p_total)
        }.bind(this), 200)
      }
    }
  }
})
