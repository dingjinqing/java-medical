<template>
  <div>
    <!--基本信息配置模块-->
    <addAndUpdateBasicInfo ref="basicInfo" />
    <!--库存/价格信息-->
    <addAndUpdateStockAndPriceInfo ref="stockAndPriceInfo" />
    <!--配送信息-->
    <addAndUpdateDeliverAndOtherInfo ref="deliverAndOtherInfo" />
  </div>
</template>
<script>
// 组件导入
import addAndUpdateBasicInfo from './addAndUpdateBasicInfo'
import addAndUpdateStockAndPriceInfo from './addAndUpdateStockAndPriceInfo'
import addAndUpdateDeliverAndOtherInfo from './addAndUpdateDeliverAndOtherInfo'
export default {
  components: { addAndUpdateBasicInfo, addAndUpdateStockAndPriceInfo, addAndUpdateDeliverAndOtherInfo },
  methods: {
    /* 修改商品数据初始化 */
    initDataForUpdate (goodsData) {
      this.$refs.basicInfo.initDataForUpdate(goodsData)
      this.$refs.stockAndPriceInfo.initDataForUpdate(goodsData)
      this.$refs.deliverAndOtherInfo.initDataForUpdate(goodsData)
    },
    /* 处理复制操作的数据 */
    disposeDataForCopy () {
      this.$refs.basicInfo.disposeDataForCopy()
      this.$refs.stockAndPriceInfo.disposeDataForCopy()
      this.$refs.deliverAndOtherInfo.disposeDataForCopy()
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
    /* 获取数据 */
    getFormData () {
      let basicInfoData = this.$refs.basicInfo.getFormData()
      let stockAndPriceInfoData = this.$refs.stockAndPriceInfo.getFormData()
      let deliverAndOtherInfoData = this.$refs.deliverAndOtherInfo.getFormData()
      // 默认规格
      if (!stockAndPriceInfoData.specInfoSwitch) {
        stockAndPriceInfoData.goodsSpecProducts[0].prdImg = basicInfoData.goodsImg
      }

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
