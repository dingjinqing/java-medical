<template>
  <div class="allGoodsRecommendSort">
    <allGoodsSortHeaderTab :tabIndex="1"/>
  </div>
</template>

<script>
// 组件导入
import allGoodsSortHeaderTab from './allGoodsSortHeaderTab'
export default {
  name: 'allGoodsRecommendSort',
  components: {
    allGoodsSortHeaderTab
  }
}
</script>

<style scoped>
</style>
