<template>
  <div class="total-content">
    <div class="container">
      <allAnalysis class="distance" />
    </div>
  </div>
</template>
<script>
import allAnalysis from './allAnalysis'

export default {
  components: {allAnalysis},
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
