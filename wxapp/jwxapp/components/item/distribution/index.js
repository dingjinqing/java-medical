
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    activity:{
      type:Object,
      value:null
    },
    distribution:{
      type:Object,
      value:null,
      
    },
    price:{
      type:Number
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    initDistribution(data){
      let distributionShowData = []
      if(data.rebateRatio.fanliRatio || data.rebateRatio.rebateRatio){
        if(data.rebateRatio.fanliRatio){
          if(data.rebateRatio.selfPurchase === 1){
            distributionShowData = [{name:'预估自购及直接返利',type:1,price:this.getDistributionPrice(1)}]
          } else {
            distributionShowData = [{name:'预估直接返利',type:3,price:this.getDistributionPrice(3)}]
          }
        }
        if(data.rebateRatio.rebateRatio){
          distributionShowData = [...distributionShowData,{name:'预估间接返利',type:data.rebateRatio.selfPurchase === 1 ? 2 : 4,price:this.getDistributionPrice(data.rebateRatio.selfPurchase === 1 ? 2 : 4)}]
        }
      }
      this.setData({
        distributionShowData
      })
    },
    getDistributionPrice(type){
      let {fanliRatio,rebateRatio} = this.data.distribution.rebateRatio
      if(type === 1 || type === 3){
        let fanliPrice =  parseFloat(fanliRatio) / 100 * this.data.price;
        return parseFloat(fanliPrice).toFixed(2);
      } else if (type === 2 || type === 4){
        let rebatePrice = parseFloat(rebateRatio) / 100 * this.data.price;
        return parseFloat(rebatePrice).toFixed(2);
      }
    },
    showDistributionTips(e){
      let {type} = e.currentTarget.dataset
      let distributionTips = {
        title:'',
        content:''
      }
      switch (type) {
        case 1:
          distributionTips.title = '自购及直接返利说明'
          if(this.data.distribution.rebateRatio.firstRatio){
            let firstMoney = parseFloat(this.data.distribution.rebateRatio.firstRatio) / 100 * this.data.price
            firstMoney = parseFloat(firstMoney).toFixed(2);
            distributionTips.content = `A自己购买分销商品，或者分享给好友B，好友B购买后，分销员A即可获得直接返利。（注：好友B如果是未在店铺内下单的新用户则按首单返利比例返佣，预估返利金额￥${firstMoney}）`
          } else {
            distributionTips.content = `A自己购买分销商品，或者分享给好友B，好友B购买后，A即可获得直接返利。`
          }
          break;
        case 2:
          distributionTips.title = '间接返利说明'
          distributionTips.content = `A将商品分享给好友B，好友B将商品分享他的好友C，好友C购买了该商品,A即可获得间接返利（注：如果好友B是分销员，则好友B购买该商品，分销员A也可获得间接返利）`;
          break;
        case 3:
          distributionTips.title = '直接返利说明'
          distributionTips.content = `A将分销商品分享给好友B，好友B购买后,A可获得直接返利`;
          break;
        case 4:
          distributionTips.title = '间接返利说明'
          distributionTips.content = `A将商品分享给好友B,好友B将商品分享给他的好友C，好友C购买了该商品，A即可获得间接返利`;
          break;
      }
      util.showModal(distributionTips.title, distributionTips.content, function () { }, false,'知道了')
    }
  }
})
