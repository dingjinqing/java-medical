<template>
  <div class="total-content">
    <div class="container">
      <historyStatistics class="distance" />
      <hotWordsStatistics class="distance" />
    </div>
  </div>
</template>
<script>
import historyStatistics from './historyStatistics'
import hotWordsStatistics from './hotWordsStatistics'
export default {
  components: {historyStatistics, hotWordsStatistics},
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
