<template>
  <div class="addGoodsRecommendSort">
    <allGoodsSortHeaderTab :tabIndex="3"/>
  </div>
</template>

<script>
// 组件导入
import allGoodsSortHeaderTab from './allGoodsSortHeaderTab'
export default {
  name: 'addGoodsRecommendSort',
  components: {
    allGoodsSortHeaderTab
  }
}
</script>

<style scoped>
</style>
