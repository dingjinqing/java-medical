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
      value: null,
      observer(newVal,oldVal){
        if (newVal.goodsId && !oldVal) this.getCartNum()
      }
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
    rightButtonName:'立即购买',
    cartNum:0
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
      this.toCheckOut()
    },
    getCartNum(){
      let { goodsId } = this.data.productInfo
      util.api('/api/wxapp/cart/goods/num',res=>{
        if(res.error === 0){
          this.setData({
            cartNum: res.content.goodsNum
          })
        }
      }, { goodsId })
    },
    // 添加购物车
    addCart() {
      let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          if(res.error === 0){
            util.toast_success('添加成功')
            this.getCartNum()
          } else {
            util.toast_fail('添加失败')
          }
          this.triggerEvent('close')
        },
        {
          goodsNumber: goodsNumber,
          prdId: prdId
        }
      );
    },
    checkOrigin(trigger){
      if (this.data.origin === 'item' && !this.data.isDefaultPrd){
        this.triggerEvent('showSpecDialog', trigger)
        return true
      }
      return false
    },
    toCheckOut() {
      util.jumpLink(`pages/checkout/checkout?goodsList=${JSON.stringify([this.data.productInfo])}`, "navigateTo")
      this.triggerEvent('close')
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
