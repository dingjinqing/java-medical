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
    // 处理商品活动
    handleToGoodsActivities(goodsData) {
      console.log(goodsData)
      // 处理商品图片底部出现的活动条
      goodsData.forEach((item, index) => {
        let arr = []
        item.goodsActivities.forEach((itemC, indexC) => {

          switch (itemC.activityType) {
            case 22:
              item.isMembershipExclusive = true
              break
            case 18:
              item.isFirstOrder = true
              break
            case 6:
              item.isLimitedPrice = true
              break
            case 3:
              item.isBargain = true
              break
            case 1:
              item.assemble = true
          }
          // 处理价格上方显示的label条
          this.handleToActivitiesLabel(item, itemC, arr)
        })
        item.activityLabelData = arr
      })
    },
    // 处理价格上方显示的label条
    handleToActivitiesLabel(item, itemC, arr) {
      // <view class='pin_flag'  style='color:{{comColor}};border-color:{{comColor}}'>{{itemC.activityType===1?'拼团':itemC.activityType===3?'砍价':itemC.activityType===5?'秒杀':itemC.activityType===6?'限时降价':itemC.activityType===10?'预售':itemC.activityType===18?'首单特惠':((itemC.activityType===19)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===1))?('满'+${itemC.leastConsume}+'减￥'+${itemC.denomination}):((itemC.activityType===19)&&(itemC.actCode==='voucher')&&(itemC.useConsumeRestrict===0))?('优惠卷减￥'+${itemC.denomination}):((itemC.activityType===19)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===1))?('满'+${itemC.leastConsume}+'打'+${itemC.denomination}+'折'):((itemC.activityType===19)&&(itemC.actCode==='discount')(itemC.useConsumeRestrict===0))?('优惠卷打'+${itemC.denomination}+'折'):itemC.activityType===20?'满减':itemC.activityType===21?'会员满减价':itemC.activityType===22?'会员专享':''}}</view>
      let obj = {}
      switch (itemC.activityType) {
        case 1:
          obj.text = '拼团'
          break
        case 3:
          obj.text = '砍价'
          break
        case 5:
          obj.text = '秒杀'
          break
        case 6:
          obj.text = '限时降价'
          break
        case 10:
          obj.text = '预售'
          break
        case 18:
          obj.text = '首单特惠'
          break
        case 19:
          if (itemC.actCode === 'voucher') {
            if (itemC.useConsumeRestrict === 1) {
              obj.text = `满${itemC.leastConsume}减￥${itemC.denomination}`
            } else {
              obj.text = `优惠卷减￥${itemC.denomination}`
            }
          } else if (itemC.actCode === 'discount') {
            if (itemC.useConsumeRestrict === 1) {
              obj.text = `满${itemC.leastConsume}打￥${itemC.denomination}`
            } else {
              obj.text = `优惠卷打￥${itemC.denomination}折`
            }
          }
          break
        case 20:
          obj.text = '满减'
          break
        case 21:
          obj.text = '会员满减价'
          break
        case 22:
          obj.text = '会员专享'
          break
      }
      arr.push(obj)
      console.log(item, itemC)
    }
  }
};

module.exports = base;