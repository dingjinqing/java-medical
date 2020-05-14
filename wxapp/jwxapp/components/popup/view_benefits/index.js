var util = require("../../../utils/util.js");
var base = require("../base/base.js")

global.wxComponent({
  mixins: [base],
  data: {
    benefitData: [],
    handleToCansel: false
  },
  properties: {
    show: {
      type: Boolean,
      value: false,
      observer (newVal, oldVal, changedPath) {
        console.log(newVal)
        this.setData({
          handleToCansel: newVal
        })
      }
    },
    benefitData: {
      type: Array,
      value: [],
      observer (newVal, oldVal, changedPath) {
        console.log(newVal)
        this.setData({
          benefitData: newVal
        })
      }
    }
  },
  methods: {
    bindClose () {
      this.triggerEvent('closeBeneFit', false)
    }
  }
})