<template>
  <div class="total-content">
    <div class="container">
      <yesterdayStatistics class="distance" />
      <visitTrendStatistics class="distance" />
    </div>
  </div>
</template>
<script>
import yesterdayStatistics from './yesterdayStatistics'
import visitTrendStatistics from './visitTrendStatistics'

export default {
  components: {yesterdayStatistics, visitTrendStatistics},
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
  /*.yesterday {*/
  /*  background-color: white;*/
  /*  margin: 10px;*/
  /*  height: 300px;*/
  /*}*/

  /*.basic {*/
  /*  background-color: white;*/
  /*  margin: 10px;*/
  /*  height: 300px;*/
  /*}*/

  /*.page {*/
  /*  background-color: white;*/
  /*  margin: 10px;*/
  /*  height: 300px;*/
  /*}*/
</style>
