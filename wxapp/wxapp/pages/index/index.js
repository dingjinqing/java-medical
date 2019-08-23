import { user } from "../../common/service/user.js"

//index.js
//获取应用实例
const app = getApp()

global.wxPage({
  data: {
    motto: 'Hello World',

  },
  //事件处理函数
  OnBtnClick: function () {
    this.notify("*", "event.custom", "hello", "world", "ffff");
  },
  onLoad: function (opts) {
    console.log("Page Index onload", opts);
    user.wxLogin(function(d){
      console.log("wxLogin result:", d);
    }, opts);
  },
})
