<template>
  <div>
    <!--基本信息配置模块-->
    <basicInfo ref="basicInfo"/>
    <!--库存/价格信息-->
    <stockAndPriceInfo ref="stockAndPriceInfo"/>
    <!--配送信息-->
    <deliverAndOtherInfo ref="deliverAndOtherInfo"/>

  </div>
</template>
<script>
// 组件导入
import basicInfo from './basicInfo'
import stockAndPriceInfo from './stockAndPriceInfo'
import deliverAndOtherInfo from './deliverAndOtherInfo'
export default {
  components: {basicInfo, stockAndPriceInfo, deliverAndOtherInfo},
  methods: {
    /* 验证数据是否全部合法 */
    validateFormData () {
      return this.$refs.basicInfo.validateFormData() && this.$refs.stockAndPriceInfo.validateFormData() && this.$refs.deliverAndOtherInfo.validateFormData()
    },
    /* 验证数据是否全部合法 */
    getFormData () {
      let basicInfoData=this.$refs.basicInfo.getFormData()
      let stockAndPriceInfoData=this.$refs.stockAndPriceInfo.getFormData()
      let deliverAndOtherInfoData=this.$refs.deliverAndOtherInfo.getFormData()

      return{
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
