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
    grade: {
      type: String,
      value: '',
      observer (newVal, oldVal, changedPath) {
        console.log(newVal)
        this.handleToInit(newVal)
      }
    }
  },
  methods: {
    handleToInit (newVal) {
      console.log(newVal)

      util.api('/api/card/interests/info', res => {
        console.log(res)
        if (res.error === 0) {
          this.setData({
            cardInfo: res.content
          })
        }

      }, { grade: newVal })
    },
    bindClose () {
      this.triggerEvent('closeBeneFit', false)
    }
  }
})