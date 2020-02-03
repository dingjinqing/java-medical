var util = require("../../../utils/util.js")

var base = {
  data: {
    m: {}
  },
  properties: {
    module: {
      type: Object,
      value: {},
      observer (newVal, oldVal, changedPath) {
        if (typeof this.onPropChange == 'function') {
          if (newVal) this.onPropChange(newVal, oldVal, changedPath);
        }
        // console.log(newVal)
        var data = {
          m: newVal
        };
        if (newVal && newVal.main_setting) {
          // main_setting 为主配置信息，即init.js初始化的信息
          var setting = newVal.main_setting;
          delete newVal.main_setting;
          // console.log(setting)
          data = Object.assign({}, data, setting);
        }
        // console.log(data)
        // console.log("observer:newVal", newVal, "data:", data, "changedPath:", changedPath);
        this.setData(data);
      }
    },

  },
  methods: {
    $set (data, cb) {
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
    handleToGoodsActivities (goodsData) {
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
              item.groupDiscount = itemC.discountPrice
          }

          // 处理价格上方显示的label条
          this.handleToActivitiesLabel(item, itemC, arr)
        })
        // console.log(arr)
        let newArr = []
        let specArr = []
        arr.forEach(item => {
          if (JSON.stringify(item) != '{}') {
            newArr.push(item)
          }
        })
        if (newArr.length > 2) {
          specArr.push(newArr[0])
          specArr.push(newArr[1])
          item.activityLabelData = specArr
          return
        }
        item.activityLabelData = newArr
      })
      // console.log(goodsData)
    },
    // 处理价格上方显示的label条
    handleToActivitiesLabel (item, itemC, arr) {
      let obj = {}
      switch (itemC.activityType) {
        case 1:
          obj.text = this.$t("components.decorate.assemble")
          break
        case 3:
          obj.text = this.$t("components.decorate.bargain")
          break
        case 5:
          obj.text = this.$t("components.decorate.seckill")
          break
        case 6:
          obj.text = this.$t("components.decorate.limitedPriceReduction")
          break
        case 10:
          obj.text = this.$t("components.decorate.advanceSale")
          break
        case 20:
          if (itemC.actCode === 'voucher') {
            if (itemC.useConsumeRestrict === 1) {
              obj.text = `${this.$t("components.decorate.full")}${itemC.leastConsume}${this.$t("components.decorate.reduce")}￥${itemC.denomination}`
            } else {
              obj.text = `${this.$t("components.decorate.coupon")}${this.$t("components.decorate.reduce")}￥${itemC.denomination}`
            }
          } else if (itemC.actCode === 'discount') {
            if (itemC.useConsumeRestrict === 1) {
              obj.text = `${this.$t("components.decorate.full")}${itemC.leastConsume}${this.$t("components.decorate.hit")}￥${itemC.denomination}`
            } else {
              obj.text = `${this.$t("components.decorate.coupon")}${this.$t("components.decorate.hit")}￥${itemC.denomination}${this.$t("decorate.fracture")}`
            }
          }
          break
        case 21:
          obj.text = this.$t("components.decorate.fullReduction")
          break
        case 22:
          obj.text = this.$t("components.decorate.fullMemberDiscount")
          break
        case 23:
          obj.text = this.$t("components.decorate.membershipExclusive")
          break
      }
      if ((itemC.activityType != 22) || (itemC.activityType != 18)) {
        arr.push(obj)
      }

      // console.log(item, itemC)
    }
  }
};

module.exports = base;