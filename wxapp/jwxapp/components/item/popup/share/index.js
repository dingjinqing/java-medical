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
      this.triggerEvent('shareMoments')
      this.bindClose()
    },
    downloadPoster(e){
      let goodsImage = []
      if(e.currentTarget.dataset.isMultiple){
        goodsImage = this.data.shareData.goodsImgs
      }
      const apiInfo = {
        1:{
          api:'/api/wxapp/groupbuy/pictorial/info',
          params:['realPrice','linePrice','activityId']
        },
        3:{},
        5:{},
        default:{} //普通商品
      }
      let target = this.data.shareData.activityType ? apiInfo[this.data.shareData.activityType] : apiInfo['default']
      let params = this.filterObj(this.data.shareData,target.params)
      wx.showLoading({
        title: '生成中',
      })
      util.api(target.api,res=>{
        if(res.error === 0){
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

  }
})
