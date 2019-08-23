
global.wxComponent({
  /**
   * 组件的属性列表
   */
  properties: {
    p1:String,
  },

  /**
   * 组件的初始数据
   */
  data: {
    d1:'c neibu'
  },
  
  // 以下是旧式的定义方式，可以保持对 <2.2.3 版本基础库的兼容
  attached: function () {
    // 在组件实例进入页面节点树时执行
    console.log("test attached");
  },
  detached: function () {
    // 在组件实例被从页面节点树移除时执行
    console.log("main detached");
  },
  

  /**
   * 组件的方法列表
   */
  methods: {
    onEvent(fromObj,eventName,...args){
      console.log("onEvent:", fromObj,eventName,args);
    }
  }
})
