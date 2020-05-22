var base = require("../mixins/base.js");

global.wxComponent({
  mixins: [base],
  methods: {
    onPropChange (newVal, oldVal, changedPath) {
      console.log(newVal, 'map++++++++++++++++++++++++++++')
      newVal.markers = [{
        iconPath: "/images/map_icon.png",
        id: 0,
        latitude: newVal.latitude,
        longitude: newVal.longitude,
        width: 15,
        height: 15
      }]
    },
    bindShowMap (e) {
      var m = this.data.m;
      wx.openLocation({
        latitude: parseFloat(m.latitude),
        longitude: parseFloat(m.longitude),
        scale: 28,
        name: m.address,
        address: m.province + " " + m.city + " " + m.area + " " + m.address
      })
    }
  }
});