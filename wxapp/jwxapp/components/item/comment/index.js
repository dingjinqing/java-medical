global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    comment:{
      type:Object,
      value:null,
      observer(val){
        if (val) this.init()
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {},

  /**
   * 组件的方法列表
   */
  methods: {
    init(){
      let { commStar } = this.data.comment.commentInfo
      let starArr = []
      for (let i = 1; i <= commStar;i++){
        starArr.push(i)
      }
      console.log(this.data.comment.commentLevelsInfo.find(item => item.type === 1).num)
      this.setData({
        starArr,
        positive: this.data.comment.commentLevelsInfo.find(item => item.type === 1).num,
        moderate: this.data.comment.commentLevelsInfo.find(item => item.type === 2).num,
        negative: this.data.comment.commentLevelsInfo.find(item => item.type === 3).num,
        hasPic: this.data.comment.commentLevelsInfo.find(item => item.type === 4).num,
        all: this.data.comment.commentLevelsInfo.filter(item=> item.type !== 4 ).reduce((total,nextVal)=>{return total += nextVal.num},0)
      })
    }
  }
});
