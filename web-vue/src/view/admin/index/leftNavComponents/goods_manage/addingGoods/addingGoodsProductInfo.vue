<template>
  <div>
    <!--基本信息配置模块-->
    <div>
      <div class="title">基本信息</div>
      <basicInfo ref="basicInfo"/>
    </div>

    <!--库存/价格信息-->
    <div>
      <div class="title">库存/价格信息</div>
      <stockAndPriceInfo ref="stockAndPriceInfo"/>
    </div>

    <!--<stockAndPriceInfo ref="priceInfo" />-->
    <!--&lt;!&ndash; 配送信息 &ndash;&gt;-->
    <!--<div class="blockWrap">-->
    <!--<div class="title">配送信息</div>-->
    <!--</div>-->
    <!--<deliveryInfo />-->
    <!--&lt;!&ndash; 其他信息 &ndash;&gt;-->
    <!--<div class="blockWrap">-->
    <!--<div class="title">其他信息</div>-->
    <!--</div>-->
    <!--<otherInfo />-->
  </div>
</template>
<script>
// 组件导入
import basicInfo from './basicInfo'
import stockAndPriceInfo from './stockAndPriceInfo'

export default {
  components: {basicInfo, stockAndPriceInfo}
}
</script>
<style scoped>
  .title {
    font-weight: bold;
    height: 40px;
    background: #f8f8f8;
    line-height: 40px;
    width: 100%;
    padding-left: 10px;
    margin: 20px 0;
  }
</style>
