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
      left:{name:'单独购买',event:'checkBuy'},
      right:{name:'砍价拿',event:'ckeckBargain'}
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
      observer(newVal, oldVal) {
        if (newVal.goodsId && !oldVal) this.getCartNum()
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
        console.log(val)
      }
    },
    dealtAct: {
      type: Object,
      value: null,
      observer(val) {
      }
    }
  },
  lifetimes:{
    ready(){
      console.log(111)
      this.initFooter()
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    cartNum: 0,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    initFooter(){
      this.setData({
        buttonData : this.getButtonData(),
        leftStyle:`color:${(this.data.main_setting.comColor != "#ff6666" || !this.data.main_setting.comColor)?"#fff":"#f66"};background:${this.data.main_setting.commonColor};`,
        rightStyle:`color:#fff;background:${this.data.main_setting.comColor};`
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
        buttonData = this.data.activity && [1,3,5].includes(this.data.activity.activityType) ? actType[this.data.activity.activityType]['footerButtonName'] : actType['default']['footerButtonName']
        if(this.data.activity && this.data.activity.activityType === 1){
          buttonData['right'].name = buttonData['right'][`name-${this.data.activity.isGrouperCheap}`]
        }
      } else if (this.data.position === 'spec'){
        let position = this.data.activity && [1,3,5].includes(this.data.activity.activityType) ? actType[this.data.activity.activityType]['dialogButtonName'] : actType['default']['dialogButtonName']
        buttonData = this.data.triggerButton ? position[this.data.triggerButton] : position['default']
      }
      return buttonData
    },
    getCartNum() {
      // let { goodsId } = this.data.productInfo
      let that = this
      util.api('/api/wxapp/cart/goods/num', res => {
        if (res.error === 0) {
          that.setData({
            cartNum: res.content.goodsNum
          })
        }
      }, { })
    },
    // 添加购物车
    addCart() {
      let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          if (res.error == 0) {
            this.getCartNum()
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
    // 返回首页
    backHome() {
      util.jumpLink("pages/index/index", "redirectTo")
    },
    toCartList() {
      util.jumpLink("pages/cart/cart", "navigateTo")
    }
  }
})
