var base = require("../../../popup/base/base.js");
var util = require("../../../../utils/util.js");
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    shareData:Object
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
    share(){
      // this.triggerEvent('shareMoments')
      this.bindClose()
    },
    async downloadPoster(e){
      const apiInfo = {
        1:{ //拼团
          api:'/api/wxapp/groupbuy/pictorial/info',
          params:['realPrice','linePrice','activityId','targetId','pageType']
        }, 
        3:{ //砍价
          api:'/api/wxapp/bargain/pictorial/info',
          params:['realPrice','linePrice','activityId','pageType']
        },
        6:{ //限时降价
          api:'/api/wxapp/reduceprice/pictorial/info',
          params:['realPrice','linePrice','activityId','targetId']
        },
        8:{ //拼团抽奖
          api:'/api/wxapp/groupdraw/pictorial/info',
          params:['realPrice','linePrice','activityId','targetId']
        },
        10:{ //定金膨胀
          api:'/api/wxapp/presale/pictorial/info',
          params:['realPrice','linePrice','activityId','targetId','depositPrice']
        },
        18:{ //首单特惠
          api:'/api/wxapp/firstspecial/pictorial/info',
          params:['realPrice','activityId','targetId']
        },
        'default':{ //普通商品
          api:'/api/wxapp/goods/pictorial/info',
          params:['realPrice','linePrice','activityId','targetId']
        } 
      }
      let target = [1,3,5,6,8,10,18].includes(this.data.shareData.activityType) ? apiInfo[this.data.shareData.activityType] : apiInfo['default']
      let params = this.filterObj(this.data.shareData,target.params)
      wx.showLoading({
        title: '生成中',
      })
      let goodsImage = e.currentTarget.dataset.isMultiple ? await this.requestGoodsImage(this.data.shareData.targetId) : []
      console.log(goodsImage)
      this.requestPictorial(target,params,goodsImage)
    },
    // 过滤需要的参数
    filterObj(obj, arr) {
      if (typeof obj !== "object" || !Array.isArray(arr)) {
        throw new Error("参数格式不正确");
      }
      const result = {};
      Object.keys(obj)
        .filter(key => arr.includes(key))
        .forEach(key => {
            result[key] = obj[key];
        });
      return result;
    },
    requestGoodsImage(targetId){
      return new Promise(resolve=>{
        util.api('/api/wxapp/goods/download/images',res=>{
          if(res.error === 0){
            resolve(res.content)
          }
        },{
          targetId
        })
      })
    },
    requestPictorial(target,params,goodsImage){ 
      util.api(target.api,res=>{
        if(res.error === 0 && res.content !== null){
          this.setData({
            showPoster:true,
            posterImage: [res.content,...goodsImage]
          })
          wx.hideLoading()
          this.bindClose()
        }
      },{
        ...params
      })
    }
  }
})
