
<template>
  <div class="userStatisticss">
    <div class="userContainer">
      <overviewAndTrend class="distance" />
      <userActive class="distance" />
      <memberStatistics class="distance" />
      <userAnalysis class="distance" />
      <userBuy />
    </div>
  </div>
</template>

<script>
import overviewAndTrend from './overviewAndTrend'
import userActive from './userActive'
import memberStatistics from './memberStatistics'
import userAnalysis from './userAnalysis'
import userBuy from './userBuy'

export default {
  components: { overviewAndTrend, userActive, memberStatistics, userAnalysis, userBuy },
  data () {
    return {

    }
  }
}

</script>
<style lang="scss" scoped>
.userStatisticss {
  font-size: 14px;
  margin-top: 10px;
  .userContainer {
    position: relative;
    .distance {
      margin-bottom: 10px;
    }
  }
}
</style>
