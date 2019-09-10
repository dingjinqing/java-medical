<template>
  <div>
    <!--基本信息配置模块-->
    <updateBasicInfo ref="basicInfo" />
    <!--库存/价格信息-->
    <updateStockAndPriceInfo ref="stockAndPriceInfo" />
    <!--配送信息-->
    <updateDeliverAndOtherInfo ref="deliverAndOtherInfo" />

  </div>
</template>
<script>
// 组件导入
import updateBasicInfo from './updateBasicInfo'
import updateStockAndPriceInfo from './updateStockAndPriceInfo'
import updateDeliverAndOtherInfo from './updateDeliverAndOtherInfo'
export default {
  components: { updateBasicInfo, updateStockAndPriceInfo, updateDeliverAndOtherInfo },
  methods: {
    initData (goodsData) {
      this.$refs.basicInfo.initData(goodsData)
      this.$refs.stockAndPriceInfo.initData(goodsData)
      this.$refs.deliverAndOtherInfo.initData(goodsData)
    },
    /* 验证数据是否全部合法 */
    validateFormData () {
      return this.$refs.basicInfo.validateFormData() && this.$refs.stockAndPriceInfo.validateFormData() && this.$refs.deliverAndOtherInfo.validateFormData()
    },
    /* 验证数据是否全部合法 */
    getFormData () {
      let basicInfoData = this.$refs.basicInfo.getFormData()
      let stockAndPriceInfoData = this.$refs.stockAndPriceInfo.getFormData()
      let deliverAndOtherInfoData = this.$refs.deliverAndOtherInfo.getFormData()

      return {
        ...basicInfoData,
        ...stockAndPriceInfoData,
        ...deliverAndOtherInfoData
      }
    }
  }
}
</script>
<style scoped>
</style>
