var base = require("../../../../components/popup/base/base.js");
global.wxComponent({
  mixins: [base],

  /**
   * 组件的属性列表
   */
  properties: {
    navHeight: Number
  },

  /**
   * 组件的初始数据
   */
  data: {
    brand_info: JSON.parse(JSON.stringify([{ "character": "B", "goodsBrands": [{ "id": 47, "brandName": "播", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/4qgecgSEmWxOdpjfJYIs.jpg", "ename": "bo" }] }, { "character": "C", "goodsBrands": [{ "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }, { "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }] }])),
   brand_keys: Object(JSON.parse(JSON.stringify([{ "character": "B", "goodsBrands": [{ "id": 47, "brandName": "播", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/4qgecgSEmWxOdpjfJYIs.jpg", "ename": "bo" }] }, { "character": "C", "goodsBrands": [{ "id": 48, "brandName": "coco", "logo": "http://jmpdevimg.weipubao.cn/upload/245547/image/20191202/DnwRPVOl6qO7vNSN9uGu.jpg", "ename": "coco" }] }])))
  },

  /**
   * 组件的方法列表
   */
  methods: {
    scrollToView(e) {
      console.log(e)
      let item_id = e.currentTarget.dataset.id == '#' ? 'order' : e.currentTarget.dataset.id;
      this.setData({
        item_id: item_id
      })
    }
  },
  observers: {
    'show': function (val) {
      let animation = wx.createAnimation({
        duration: 600,
        timingFunction: 'ease'
      })
      if (val === true) {
        animation.translateX(0).step()
      } else {
        animation.translateX('600rpx').step()
      }
      this.setData({
        animation: animation.export()
      })
    }
  }
});
