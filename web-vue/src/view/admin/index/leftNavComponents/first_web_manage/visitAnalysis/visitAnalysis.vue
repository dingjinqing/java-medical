<template>
  <div class="total-content">
    <div class="container">
      <allAnalysis class="distance" />
      <visitPage class="distance" />
    </div>
  </div>
</template>
<script>
import allAnalysis from './allAnalysis'
import visitPage from './visitPage'

export default {
  components: {allAnalysis, visitPage},
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
