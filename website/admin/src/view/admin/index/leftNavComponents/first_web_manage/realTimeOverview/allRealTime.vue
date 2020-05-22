<template>
  <!-- 实时概况 -->
  <div class="total-content">
    <div class="container">
<!--       实时概况 -->
      <realTime class="distance" />
      <!-- 核心指标 -->
      <coreIndicator class="distance" />
    </div>
  </div>
</template>
<script>
import realTime from './realTime'
import coreIndicator from './coreIndicator'
export default {
  components: {realTime, coreIndicator},
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
