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
    show_purchase: false,
    p_count: 0,
    p_total: 0
  },
  /**
   * 组件的方法列表
   */
  methods: {
    handleToCalculationTop (val) {
      let that = this
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
      purchase_img = purchase[0].user_avatar
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
    slideupshow: function (that, opacity, delay, p_total) {
      console.log('kaishi');
      let parms = '';
      let animation = wx.createAnimation({
        duration: 2000,
        timingFunction: 'ease',
        delay: delay
      });
      animation.translateY(0).step();
      animation.translateY(-120).step();
      parms = animation.export();
      that.setData({
        animation: parms
      })
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
        purchase_img = purchase[p_total].user_avatar
        this.setData({
          animation: parms1,
          purchase_name: purchase_name,
          purchase_img: purchase_img
        })
        setTimeout(function () {
          this.slideupshow(this, 1, 0, p_total)
        }.bind(this), 100)
      }
    },
    powerDrawer (e) {
      var currentStatu = e.currentTarget.dataset.statu;
      this.utilSpec(currentStatu)
    },
    utilSpec (currentStatu) {
      /* 动画部分 */
      // 第1步：创建动画实例
      var animation = wx.createAnimation({
        duration: 200, //动画时长
        timingFunction: "linear", //线性
        delay: 0 //0则不延迟
      });
      // 第2步：这个动画实例赋给当前的动画实例
      this.animation = animation;

      // 第3步：执行第一组动画
      animation.opacity(0).rotateX(-100).step();

      // 第4步：导出动画对象赋给数据对象储存
      this.setData({
        animationData: animation.export()
      })
      // 第5步：设置定时器到指定时候后，执行第二组动画
      setTimeout(function () {
        // 执行第二组动画
        animation.opacity(1).rotateX(0).step();
        // 给数据对象储存的第一组动画，更替为执行完第二组动画的动画对象
        this.setData({
          animationData: animation
        })
        //关闭
        if (currentStatu == "close") {
          this.setData({
            showModalStatus: false
          });
        }
      }.bind(this), 200)
      // 显示
      if (currentStatu == "open") {
        this.setData({
          showModalStatus: true
        });
      }
    },
  }
})
