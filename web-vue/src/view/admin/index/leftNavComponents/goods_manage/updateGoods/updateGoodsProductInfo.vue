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
    /* 修改商品数据初始化 */
    initDataForUpdate (goodsData) {
      this.$refs.basicInfo.initDataForUpdate(goodsData)
      this.$refs.stockAndPriceInfo.initDataForUpdate(goodsData)
      this.$refs.deliverAndOtherInfo.initDataForUpdate(goodsData)
    },
    /* 新增商品数据初始化 */
    initDataForInsert () {
      this.$refs.basicInfo.initDataForInsert()
      this.$refs.stockAndPriceInfo.initDataForInsert()
      this.$refs.deliverAndOtherInfo.initDataForInsert()
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
