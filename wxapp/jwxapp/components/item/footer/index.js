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
      right:{name:'砍价拿',event:'checkBargain',}
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
        // this.setButtonStyle()
      }
    },
    products:{
      type:Array,
      value:null,
      observer(val){
        this.initFooter()
      }
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
      this.getCartNum()
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
      this.setButtonStyle()
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
    checkBargain(){
      if(!this.checkActStatus()) return
      if(this.checkPosition('right')) return
      this.goBargain()
    },
    checkGroup(){
      if(!this.checkActStatus()) return
      if(this.checkPosition('right')) return
      this.actCheckOut()
    },
    checkSkill(){
      if(!this.checkActStatus()) return
      if(this.checkPosition('right')) return
      this.actCheckOut()
    },
    checkPreSale(){
      if(!this.checkActStatus()) return
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
          console.log(this.data.productInfo)
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
      console.log(buttonData)
      return buttonData
    },
    getCartNum() {
      util.api('/api/wxapp/cart/list', res => {
        if (res.error === 0) {
          let {cartGoodsList:goodsList} = res.content
          let cartNum = goodsList.reduce((total,item,index)=>{
            return total += item.cartNumber
          },0)
          this.setData({
            cartNum
          })
        }
      })
    },
    // 添加购物车
    addCart() {
      let { goodsNum: goodsNumber, prdId } = this.data.productInfo
      util.api(
        "/api/wxapp/cart/add",
        res => {
          if (res.error == 0) {
            util.toast_success('添加成功')
            this.getCartNum()
          } else {
            util.showModal(this.$t("components.decorate.tips"), res.message);
            // util.toast_fail('添加失败')
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
    async actCheckOut(){
      let params = {
        goodsList: JSON.stringify([this.data.productInfo]), 
        activityType: this.data.activity ? this.data.activity.activityType : null, 
        activityId: this.data.activity ? this.data.activity.activityId : null
      }
      let actFlag = await this.checkActLimit(this.data.activity,this.data.productInfo)
      console.log(actFlag)
      if(!actFlag) return;
      if(this.data.activity && this.data.activity.activityType === 10){
        params.preSaleInfo = {...this.data.activity,preSalePrdInfo:this.data.productInfo.actProduct}
        delete params.preSaleInfo.preSalePrdMpVos
        params.preSaleInfo = JSON.stringify(params.preSaleInfo)
      }
      util.jumpLink(`pages/checkout/checkout${this.getUrlParams({ ...params })}`, "navigateTo")
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
    async checkActLimit(activity,productInfo){
      if(activity && activity.activityType === 5) return(await this.checkSkillLimit(activity,productInfo))
      return true
    },
    checkSkillLimit({activityId:skId},{prdId:productId,goodsNum: goodsNumber,goodsId}){
      return new Promise(resolve=>{
        util.api('/api/wxapp/seckill/check',res=>{
          console.log(res)
          if(res.error === 0 && res.content && res.content.state === 0){
            resolve(true)
          } else {
            let status =  {
              1:`抱歉，此秒杀活动不存在`,
              2:`抱歉，此秒杀活动已停用`,
              3:`抱歉，此秒杀活动未开始`,
              4:`抱歉，此秒杀活动已结束`,
              5:`抱歉，商品已抢光`,
              6:`抱歉，您已超出此活动购买限额。`,
              7:`抱歉，该秒杀活动为会员专享活动。请检查是否有对应会员卡`,
              8:`抱歉，当前选中规格库存已抢空`,
              9:`您好，您有待支付秒杀订单，请支付后再试。`
            }
            let tips = res.error === 0 ? status[res.content.state] : res.message
            if (res.content.state === 6) {
              if(res.content.diffNumber){
                tips += ('当前还可购买' + res.content.diffNumber + '个'); 
                util.showModal('提示',tips)
              } else {
                util.showModal('提示',tips,()=>{
                  util.jumpLink(`/pages/item/item?gid=${goodsId}`,'redirectTo')
                },false,'','原价购买')
              }
            } else  if (res.content.state === 9) {
              util.showModal('提示',tips,()=>{
                util.jumpLink(
                  `/pages/orderinfo/orderinfo?orderSn=${res.content.orderSn}`,
                  "navigateTo"
                );
              },true,'取消','去查看')
            } else {
              util.showModal('提示',tips)
            }
            resolve(false)
          }
        },{
          skId,
          productId,
          goodsNumber
        })
      })
    },
    setButtonStyle(){
      let activity = this.data.activity
      if(activity && [1,3,4,5,8,10].includes(activity.activityType) && [1,2,3,4,5,6].includes(activity.actState) && (this.data.position === 'footer' || this.data.triggerButton !== 'left')){
        console.log(this.data.buttonData)
        if(this.data.buttonData && this.data.buttonData.buttonInfo.right){
          this.setData({
            'buttonData.buttonInfo.right.style':'background-color:#666'
          })
        }
      } else {
        this.setData({
          'buttonData.buttonInfo.right.style':''
        })
      }
    },
    checkActStatus(){
      let activity = this.data.activity
      let goodsId = this.data.productInfo.goodsId
      if(activity && [1,3,4,5,8,10].includes(activity.activityType) && [1,2,3,4,5,6].includes(activity.actState) && (this.data.position === 'footer' || this.data.triggerButton !== 'left')){
          let tipStr = '';
          let ActName =  {1:'拼团',3:'砍价',4:'积分兑换',5:'秒杀',8:'拼团抽奖',10:'预售'};
          let actStatus = {1:'活动不存在',2:'活动已停用',3:'活动未开始',4:'活动已结束',5:'活动商品已抢光',6:'活动，您已达参与上限'};
          tipStr = `抱歉，该${ActName[activity.activityType]}${actStatus[activity.actState]}`
          util.showModal('提示',tipStr,()=>{
            util.jumpLink(`/pages/item/item?gid=${goodsId}`,'redirectTo')
          },false,'','原价购买')
          return false
      }
      return true
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
