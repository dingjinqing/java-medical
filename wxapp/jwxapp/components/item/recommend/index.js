
const util = require("../../../utils/util.js");
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    pageName: {
      type: String,
      value: null, //cart,bargainitem,groupbuyitem,new_search,orderlist,payment,search,item
    },
    pageParams: {
      type: Object,
      value: null,
      observer(res){
        
      }
    }
  },
  /**
   * 组件的初始数据
   */
  data: {
    currentPage:1
  },

  /**
   * 组件的方法列表
   */
  methods: {
    requestData() {
      util.api('/api/wxapp/goods/recommend',res=>{
        if(res.error === 0 && res.content !== null){
          this.setData({
            ['dataList[' + (parseInt(this.data.currentPage) - 1) + ']']: res.content.recommendGoods,
            currentPage:++this.data.currentPage
          });
        }
      },{
        pageName:this.data.pageName,
        pageNum:this.data.currentPage,
        pageSize:20
      })
    },
    resetDataList(){
      this.setData({
        dataList:null
      })
      return this;
    },
    resetPage(){
      this.setData({
        currentPage:1,
      })
      return this;
    }
  }
});
