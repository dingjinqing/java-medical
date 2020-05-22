const base = require("../../../components/popup/base/base.js");
const util = require('../../../utils/util.js');
global.wxComponent({
  mixins: [base],
  /**
   * 组件的属性列表
   */
  properties: {
    couponPackageList:Array
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
    goCouponPackageInfo(e){
      let {packId} = e.currentTarget.dataset
      util.jumpLink(`pages1/couponpackage/couponpackage?packId=${packId}`,'navigateTo')
    }
  }
})
