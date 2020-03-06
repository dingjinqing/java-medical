const util = require("../../../utils/util.js");
let actType = {
  1:{
    footerButtonName:{
      left:{name:'单独购买',event:'checkBuy'},
      right:{
        'name-0':'一键开团',
        'name-1':'团长优惠价',
        event:'checkGroup'
      }
    },
    dialogButtonName:{
      left:{
        right:{
            name:'立即购买',
            event:'toCheckOut'
        }
      },
      right:{
        right:{
          name:'一键开团',
          event:'actCheckOut'
        }
      },
      default:{
        right:{
          name:'一键开团',
          event:'actCheckOut'
        }
      }
    }
  },
  3:{
    footerButtonName:{
      left:{name:'单独购买',event:'checkBuy',},
      right:{name:'砍价拿',event:'ckeckBargain',}
    },
    dialogButtonName:{
      left:{
        left:{
          name:'加入购物车',
          event:'addCart'
        },
        right:{
          name:'立即购买',
          event:'toCheckOut'
        }
      },
      right:{
        right:{
          name:'砍价拿',
          event:'goBargain'
        }
      },
      default:{
        right:{
          name:'砍价拿',
          event:'goBargain'
        }
      }
    }
  },
  5:{
    footerButtonName:{
      right:{
        name:'立即购买',
        event:'checkSkill'
      }
    },
    dialogButtonName:{
      right:{
        right:{
          name:'立即购买',
          event:'actCheckOut'
        }
      },
      default:{
        right:{
          name:'立即购买',
          event:'actCheckOut'
        }
      }
    }
  },
  10:{
    footerButtonName:{
      left:{name:'单独购买',event:'checkBuy'},
      right:{name:'支付定金',event:'checkPreSale'}
    },
    dialogButtonName:{
      left:{
        left:{
          name:'加入购物车',
          event:'addCart'
        },
        right:{
          name:'立即购买',
          event:'toCheckOut'
        }
      },
      right:{
        right:{
          name:'支付定金',
          event:'actCheckOut'
        }
      },
      default:{
        right:{
          name:'支付定金',
          event:'actCheckOut'
        }
      }
    }
  },
  default:{
    footerButtonName:{
      left:{
        name:'加入购物车',
        event:'checkAddCart'
      },
      right:{
        name:'立即购买',
        event:'checkCheckout'
      }
    },
    dialogButtonName:{
      left:{
        left:{
          name:'确定',
          event:'addCart'
        }
      },
      right:{
        right:{
          name:'确定',
          event:'toCheckOut'
        }
      },
      default:{
        left:{
          name:'加入购物车',
          event:'addCart'
        },
        right:{
          name:'立即购买',
          event:'toCheckOut'
        }
      }
    }
  }
}
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    position: {
      type: String,
      value: "footer" //dialog
    },
    isDefaultPrd: Boolean,
    productInfo: {
      type: Object,
      value: null,
      observer(newVal) {
          console.log(newVal)
          this.initFooter()
      }
    },
    triggerButton: {
      type: String,
      value: ''
    },
    activity: {
      type: Object,
      value: null,
      observer(val) {
      }
    },
    dealtAct: {
      type: Object,
      value: null,
      observer(val) {
        this.initFooter()
      }
    },
    products:{
      type:Array,
      value:null
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    cartNum: 0,
  },
  lifetimes:{
    ready(){
      this.setData({
        leftStyle:`color:${(this.data.main_setting.comColor != "#ff6666" || !this.data.main_setting.comColor)?"#fff":"#f66"};background:${this.data.main_setting.commonColor};`,
        rightStyle:`color:#fff;background:${this.data.main_setting.comColor};`,
        notBuyRightStyle:'background:#666;'
      })
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    initFooter(){
      this.setData({
        buttonData : this.getButtonData()
      })
    },
    checkPosition(position) {
      if (this.data.position === 'footer' && !this.data.isDefaultPrd) {
        this.triggerEvent('showSpecDialog',position)
        return true
      }
      return false
    },
    checkAddCart(){
      if(this.checkPosition('left')) return
      this.addCart()
    },
    checkCheckout(){
      if(this.checkPosition('right')) return
      this.toCheckOut()
    },
    checkBuy(){
      if(this.checkPosition('left')) return
      this.toCheckOut()
    },
    ckeckBargain(){
      if(this.checkPosition('right')) return
      this.goBargain()
    },
    checkGroup(){
      if(this.checkPosition('right')) return
      this.actCheckOut()
    },
    checkSkill(){
      if(this.checkPosition('right')) return
      this.actCheckOut()
    },
    checkPreSale(){
      if(this.checkPosition('right')) return
      this.actCheckOut()
    },
    goBargain(){
      util.api('/api/wxapp/bargain/apply',res=>{
        if (res.error == 0) {
          var data = res.content;
          if (data.resultCode == 0) {
            var url = "/pages/bargaininfo/bargaininfo?record_id=" + data.recordId;
            util.jumpLink(url);
          } else if (data.resultCode == 1) {
            util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.doesNotExist"));
          } else if (data.resultCode == 2) {
            util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.activityDeactivated"));
          } else if (data.resultCode == 3) {
            util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.notStart"));
          } else if (data.resultCode == 4) {
            util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.activityEnded"));
          } else if (data.resultCode == 5) {
            util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.insufficientInventory"));
          } else if (data.resultCode == -1) {
            util.showModal(this.$t("components.decorate.tips"), this.$t("components.decorate.operationFailed"));
          }
        } else {
          
        }
      },{
        bargainId:this.data.activity.activityId,
        prdId:this.data.productInfo.prdId
      })
    },
    getButtonData(){
      let buttonData = {}
      if(this.data.position === 'footer'){
        buttonData['buttonInfo'] = this.data.activity && [1,3,5,10].includes(this.data.activity.activityType) ? actType[this.data.activity.activityType]['footerButtonName'] : actType['default']['footerButtonName']
        if(this.data.activity && this.data.activity.activityType === 1){
          buttonData['buttonInfo']['left'].top = `${this.data.isDefaultPrd ? `￥${this.data.products[0].prdRealPrice}` : this.getProducesMinPrice()}`
          buttonData['buttonInfo']['right'].name = buttonData['buttonInfo']['right'][`name-${this.data.activity.isGrouperCheap}`]
          buttonData['buttonInfo']['right'].top = `￥${this.data.activity.isGrouperCheap === 1 ? this.getGroupPirce('grouper') : this.getGroupPirce()}`
        }
        if(this.data.activity && this.data.activity.activityType === 3){
          buttonData['buttonInfo']['left'].top = `${this.data.isDefaultPrd ? `￥${this.data.products[0].prdRealPrice}` : this.getProducesMinPrice()}`
          buttonData['buttonInfo']['right'].top = `￥${this.data.activity.bargainPrice}`
        }
        if(this.data.activity && this.data.activity.activityType === 10){
            buttonData['buttonInfo']['right'].right = this.data.activity.preSaleType !== 1 ? `￥${this.data.productInfo.actProduct.depositPrice}` : `￥${this.data.productInfo.actProduct.preSalePrice}`
          if(!this.data.activity.originalBuy){
            delete buttonData['buttonInfo']['left']
          }
        }
      } else if (this.data.position === 'spec'){
        let position = this.data.activity && [1,3,5,10].includes(this.data.activity.activityType) ? actType[this.data.activity.activityType]['dialogButtonName'] : actType['default']['dialogButtonName']
        buttonData['buttonInfo'] = this.data.triggerButton ? position[this.data.triggerButton] : position['default']
        if(this.data.activity && this.data.activity.activityType === 3 && this.data.triggerButton === 'right'){
          buttonData['buttonInfo']['right'].left = `￥${this.data.activity.bargainPrice}`
        }
        if(this.data.activity && this.data.activity.activityType === 10 && this.data.triggerButton === 'right'){
          buttonData['buttonInfo']['right'].right = this.data.activity.preSaleType !== 1 ? `￥${this.data.productInfo.actProduct.depositPrice}` : `￥${this.data.productInfo.actProduct.preSalePrice}`
        }
      }
      buttonData.activityType = this.data.activity ? this.data.activity.activityType : null
      this.checkDealtAct(buttonData)
      return buttonData
    },
    // getCartNum() {
    //   // let { goodsId } = this.data.productInfo
    //   let that = this
    //   util.api('/api/wxapp/cart/goods/num', res => {
    //     if (res.error === 0) {
    //       that.setData({
    //         cartNum: res.content.goodsNum
    //       })
    //     }
    //   }, { })
    // },
    // 添加购物车
    addCart() {
      let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          if (res.error == 0) {
            util.toast_success('添加成功')
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
    
    toCheckOut() {
      util.jumpLink(`pages/checkout/checkout${this.getUrlParams({ goodsList: JSON.stringify([this.data.productInfo]) })}`, "navigateTo")
      this.triggerEvent('close')
    },
    actCheckOut(){
      util.jumpLink(`pages/checkout/checkout${this.getUrlParams({ goodsList: JSON.stringify([this.data.productInfo]), activityType: this.data.activity ? this.data.activity.activityType : null, activityId: this.data.activity ? this.data.activity.activityId : null })}`, "navigateTo")
      this.triggerEvent('close')
    },
    //整合参数
    getUrlParams(obj) {
      return Object.keys(obj).reduce((UrlStr, item, index) => {
        if (index !== 0) UrlStr += `&`
        return UrlStr += `${item}=${obj[item]}`
      }, '?')
    },
    getGroupPirce(type){
      let realPriceArr = null
      if(type === 'grouper'){
        realPriceArr = this.data.activity.groupBuyPrdMpVos.map(item=>{return item.grouperPrice})
      } else {
        realPriceArr = this.data.activity.groupBuyPrdMpVos.map(item=>{return item.groupPrice})
      }
      return this.getMin(realPriceArr)
    },
    getProducesMinPrice(){
     let realPriceArr = this.data.products.map(item => {return item.prdRealPrice})
     let minPrice = this.getMin(realPriceArr),maxPrice = this.getMax(realPriceArr)
     if(minPrice === maxPrice){
       return `￥${minPrice}`
     }
     return `￥${minPrice}~${maxPrice}`
    },
    getMin(arr) {
      return Math.min(...arr)
    },
    getMax(arr) {
      return Math.max(...arr)
    },
    checkDealtAct(buttonData){
      let {dealtAct,triggerButton} = this.data
      let {buttonInfo} = buttonData
      if(this.data.position === 'footer'){
        if(buttonData.activityType && buttonData.activityType === 1){
          if(dealtAct && dealtAct.error === 1){
            buttonInfo['left']['canBuy'] = true
            buttonInfo['right']['canBuy'] = false
            buttonInfo['right']['errorMessage'] = dealtAct.errorMessage
          } else if(dealtAct && dealtAct.error === 2){
            buttonInfo['left']['canBuy'] = false
            buttonInfo['right']['canBuy'] = false
            buttonInfo['left']['errorMessage'] = dealtAct.errorMessage
            buttonInfo['right']['errorMessage'] = dealtAct.errorMessage
          } else {
            buttonInfo['left']['canBuy'] = true
            buttonInfo['right']['canBuy'] = true
          }
        } else if (buttonData.activityType && buttonData.activityType === 3){
          if(dealtAct && dealtAct.error === 1){
            buttonInfo['left']['canBuy'] = true
            buttonInfo['right']['canBuy'] = false
            buttonInfo['right']['errorMessage'] = dealtAct.errorMessage
          } else if(dealtAct && dealtAct.error === 2){
            buttonInfo['left']['canBuy'] = false
            buttonInfo['right']['canBuy'] = false
            buttonInfo['left']['errorMessage'] = dealtAct.errorMessage
            buttonInfo['right']['errorMessage'] = dealtAct.errorMessage
          } else {
            buttonInfo['left']['canBuy'] = true
            buttonInfo['right']['canBuy'] = true
          }
        } else if (buttonData.activityType && buttonData.activityType === 5){
          if(dealtAct && dealtAct.error === 1){
            buttonInfo['right']['canBuy'] = false
            buttonInfo['right']['errorMessage'] = dealtAct.errorMessage
          } else if(dealtAct && dealtAct.error === 2){
            buttonInfo['right']['canBuy'] = false
            buttonInfo['right']['errorMessage'] = '活动商品库存为0'
          } else {
            buttonInfo['right']['canBuy'] = true
          }
        } else if (buttonData.activityType && buttonData.activityType === 10) {
          buttonInfo['right']['canBuy'] = true
        } else {
          buttonInfo['left']['canBuy'] = true
          buttonInfo['right']['canBuy'] = true
        }
      } else if(this.data.position === 'spec') {
        if(buttonData.activityType && buttonData.activityType === 1){
          if(triggerButton === 'right' || !triggerButton){
            if(dealtAct && dealtAct.error === 2){
              buttonInfo['right']['canBuy'] = false
            } else {
              buttonInfo['right']['canBuy'] = true
            }
          } else if(triggerButton === 'left') {
            if(dealtAct && dealtAct.error === 2){
              buttonInfo['right']['canBuy'] = false
            } else {
              buttonInfo['right']['canBuy'] = true
            }
          }
        } else if (buttonData.activityType && buttonData.activityType === 3){
          if(triggerButton === 'right' || !triggerButton){
            buttonInfo['right']['canBuy'] = true
          } else if (triggerButton === 'left') {
            if(dealtAct && dealtAct.error === 2){
              buttonInfo['left']['canBuy'] = false
              buttonInfo['right']['canBuy'] = false
              buttonInfo['left']['errorMessage'] = dealtAct.errorMessage
              buttonInfo['right']['errorMessage'] = dealtAct.errorMessage
            } else {
              buttonInfo['left']['canBuy'] = true
              buttonInfo['right']['canBuy'] = true
            }
          }
        } else if (buttonData.activityType && buttonData.activityType === 5){
          if(triggerButton === 'right' || !triggerButton){
            if(dealtAct && dealtAct.error === 1){
              buttonInfo['right']['canBuy'] = false
              buttonInfo['right']['errorMessage'] = dealtAct.errorMessage
            } else if(dealtAct && dealtAct.error === 2){
              buttonInfo['right']['canBuy'] = false
              buttonInfo['right']['errorMessage'] = '活动商品库存为0'
            } else {
              buttonInfo['right']['canBuy'] = true
            }
          }
        } else if (buttonData.activityType && buttonData.activityType === 10) {
          buttonInfo['right']['canBuy'] = true
        } else {
          if(triggerButton === 'right'){
            buttonInfo['right']['canBuy'] = true
          } else if (triggerButton === 'left'){
            buttonInfo['left']['canBuy'] = true
          } else {
            buttonInfo['left']['canBuy'] = true
            buttonInfo['right']['canBuy'] = true
          }
        }
      }
    },
    notBuyTips(){
      let {dealtAct} = this.data
      util.showModal(this.$t("components.decorate.tips"), dealtAct.errorMessage);
    },
    // 返回首页
    backHome() {
      util.jumpLink("pages/index/index", "redirectTo")
    },
    toCartList() {
      util.jumpLink("pages/cart/cart", "navigateTo")
    }
  }
})
