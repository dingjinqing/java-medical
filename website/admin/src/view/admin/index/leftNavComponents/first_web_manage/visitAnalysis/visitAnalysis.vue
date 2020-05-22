<template>
  <!-- 访问分析 -->
  <div class="total-content">
    <div class="container">
      <!-- 打开次数折线图 -->
      <allAnalysis class="distance" />
      <!-- 访问分布 -->
      <distribution class="distance" />
      <!-- 用户留存 -->
      <visitRetain class="distance" />
      <!-- 访问页面 -->
      <visitPage class="distance" />
    </div>
  </div>
</template>
<script>
import allAnalysis from './allAnalysis'
import visitPage from './visitPage'
import distribution from './distribution'
import visitRetain from './visitRetain'

export default {
  components: { allAnalysis, visitPage, distribution, visitRetain },
  data () {
    return {}
  }
}
</script>
<style lang="scss" scoped>
.total-content {
  font-size: 14px;
  margin-top: 10px;
  .container {
    position: relative;
    .distance {
      margin-left: 10px;
      margin-right: 10px;
      margin-bottom: 10px;
    }
  }
}
</style>
