var util = require("../../../utils/util.js")

var base = {
  data: {
    m: {}
  },
  properties: {
    module: {
      type: Object,
      value: {},
      observer(newVal, oldVal, changedPath) {
        if (typeof this.onPropChange == 'function') {
          if (newVal) this.onPropChange(newVal, oldVal, changedPath);
        }
        console.log(newVal)
        var data = {
          m: newVal
        };
        if (newVal && newVal.main_setting) {
          // main_setting 为主配置信息，即init.js初始化的信息
          var setting = newVal.main_setting;
          delete newVal.main_setting;
          console.log(setting)
          data = Object.assign({}, data, setting);
        }
        console.log(data)
        // console.log("observer:newVal", newVal, "data:", data, "changedPath:", changedPath);
        this.setData(data);
      }
    },

  },
  methods: {
    $set(data, cb) {
      if (data) {
        data.m = data.m || this.data.m;
        this.setData(data, cb)
      } else {
        this.setData({
          m: this.data.m
        }, cb);
      }
    },
    // 处理活动
    handleToGoodsActivities(goodsData) {
      console.log(goodsData)
      goodsData.forEach((item, index) => {
        item.goodsActivities.forEach((itemC, indexC) => {
          switch (itemC.activityType) {
            case 22:
              item.isMembershipExclusive = true
          }

        })
      })
      console.log(goodsData)
    }
  }
};

module.exports = base;