const util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    origin: {
      type: String,
      value: "item"
    },
    isDefaultPrd:Boolean,
    productInfo:{
      type: Object,
      value: null
    },
    triggerButton:{
      type:String,
      value:'',
      observer(val){
        console.log(val)
        if(val==='left'){
          this.setData({
            rightButtonShow:false,
            leftButtonShow:true,
            leftButtonName:'确定'
          })
        } else if(val==='right'){
          this.setData({
            leftButtonShow: false,
            rightButtonShow: true,
            rightButtonName: '立即购买'
          })
        } else {
          this.setData({
            rightButtonShow:true,
            leftButtonShow:true
          })
        }
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    rightButtonShow: true,
    leftButtonShow: true,
    leftButtonName:'加入购物车',
    rightButtonName:'立即购买'
  },

  /**
   * 组件的方法列表
   */
  methods: {
    leftClick(){
      if (this.checkOrigin('left')) return
      this.addCart()
    },
    rightClick(){
      if (this.checkOrigin('right')) return
      this.test()
    },
    // 添加购物车
    addCart() {
      let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          console.log(res);
          this.triggerEvent('close')
        },
        {
          goodsNumber: goodsNumber,
          prdId: prdId
        }
      );
    },
    checkOrigin(trigger){
      console.log(1111)
      if (this.data.origin === 'item' && !this.data.isDefaultPrd){
        this.triggerEvent('showSpecDialog', trigger)
        return true
      }
      return false
    },
    test() {
      util.jumpLink("pages/item/item", "navigateTo")
    },

    // 返回首页
    backHome() {
      util.jumpLink("pages/index/index", "redirectTo")
    },
    toCartList(){
      util.jumpLink("pages/cart/cart","navigateTo")
    }
  }
})
