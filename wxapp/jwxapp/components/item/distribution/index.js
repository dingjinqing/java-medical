
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
      observer(newVal){
        this.initDistribution()
      }
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
    initDistribution(){
      
    }
  }
})
